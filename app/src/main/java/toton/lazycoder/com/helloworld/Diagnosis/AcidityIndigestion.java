package toton.lazycoder.com.helloworld.Diagnosis;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import toton.lazycoder.com.helloworld.R;
import toton.lazycoder.com.helloworld.Utility.SpinnerUtility;


public class AcidityIndigestion extends Fragment implements View.OnClickListener {

    static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    JSONObject info;
    private static final String INFO = "INFO";
    EditText duration;
    EditText worsened;
    EditText relief;

    public AcidityIndigestion() {

    }

    public static AcidityIndigestion newInstance(String param1, String param2) {
        AcidityIndigestion fragment = new AcidityIndigestion();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        try{
            if(savedInstanceState != null && savedInstanceState.containsKey(INFO)){
                info = new JSONObject(savedInstanceState.getString(INFO));
            }else if(getArguments() != null && getArguments().containsKey("INFO")){
                info = new JSONObject(getArguments().getString(INFO));
            }else {
                info = new JSONObject();
            }
        }catch(JSONException e){
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_acidity_indigestion, container, false);
        view.findViewById(R.id.backButtonAbdominalPain).setOnClickListener(this);
        view.findViewById(R.id.continueButtonAbdominalPain).setOnClickListener(this);

        duration = (EditText)view.findViewById(R.id.editText_acidityIndigestionDuration);
        worsened = (EditText)view.findViewById(R.id.editText_acidityIndigestionWorsen);
        relief = (EditText)view.findViewById(R.id.editText_acidityIndigestionRelief);

        SpinnerUtility.SpinnerCreate(this,view,info,R.id.spinner_acidityIndigestionPain,R.array.YesNoString,"Pain");

        SpinnerUtility.SpinnerCreate(this,view,info,R.id.spinner_acidityIndigestionVomiting,R.array.YesNoString,"Vomiting");

        SpinnerUtility.SpinnerCreate(this,view,info,R.id.spinner_acidityIndigestionNausea,R.array.YesNoString,"Nausea");

        SpinnerUtility.SpinnerCreate(this,view,info,R.id.spinner_acidityIndigestionAppetite,R.array.acidityIndigestionAppetite,"Appetite");

        SpinnerUtility.SpinnerCreate(this,view,info,R.id.spinner_acidityIndigestionConstipation,R.array.YesNoString,"Constipation");

        SpinnerUtility.SpinnerCreate(this,view,info,R.id.spinner_acidityIndigestionDiarrhoea,R.array.YesNoString,"Diarrhoea");

        SpinnerUtility.SpinnerCreate(this,view,info,R.id.spinner_acidityIndigestionJaundice,R.array.YesNoString,"Jaundice");

        SpinnerUtility.SpinnerCreate(this,view,info,R.id.spinner_acidityIndigestionAlcohol,R.array.YesNoString,"Alcohol Consumption");

        SpinnerUtility.SpinnerCreate(this,view,info,R.id.spinner_acidityIndigestionSmoke,R.array.YesNoString,"Smokes");

        SpinnerUtility.SpinnerCreate(this,view,info,R.id.spinner_acidityIndigestionWeight,R.array.acidityIndigestionWeight,"Weight change");

        return view;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    public void onClick(View view) {

        Communicator activity = (Communicator) getActivity();

        switch(view.getId()) {
            case R.id.backButtonAbdominalPain:
                activity.communicate(Communicator.Response.BACK, null);
                break;

            case R.id.continueButtonAbdominalPain:

                try {
                    if (duration.getText() != null) {
                        info.put("Duration", duration.getText());
                    }
                    if(worsened.getText()!=null){
                        info.put("Worsened by", worsened.getText());
                    }
                    if(relief.getText()!=null){
                        info.put("Relieved by",relief.getText());
                    }
                }catch (Exception e)
                {
                    e.printStackTrace();
                }

                activity.communicate(Communicator.Response.CONTINUE, info);
                break;

        }
    }


}
