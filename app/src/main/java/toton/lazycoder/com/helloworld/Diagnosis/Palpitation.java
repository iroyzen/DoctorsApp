package toton.lazycoder.com.helloworld.Diagnosis;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import toton.lazycoder.com.helloworld.R;
import toton.lazycoder.com.helloworld.Utility.SpinnerUtility;

public class Palpitation extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    JSONObject info;
    private static final String INFO = "INFO";

    EditText duration;
    EditText HowLong;
    EditText Symptom;
    EditText Start;
    EditText Relief;

    public Palpitation() {
    }


    public static Palpitation newInstance(String param1, String param2) {
        Palpitation fragment = new Palpitation();
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

        View view = inflater.inflate(R.layout.fragment_palpitation, container, false);
        view.findViewById(R.id.backButtonAbdominalPain).setOnClickListener(this);
        view.findViewById(R.id.continueButtonAbdominalPain).setOnClickListener(this);

        duration = (EditText)view.findViewById(R.id.editText_palpitationDuration);
        HowLong = (EditText)view.findViewById(R.id.editText_palpitationHowLong);
        Symptom = (EditText)view.findViewById(R.id.editText_palpitationOtherSymptom);
        Start = (EditText)view.findViewById(R.id.editText_palpitationStart);
        Relief = (EditText)view.findViewById(R.id.editText_palpitationRelief);

        SpinnerUtility.SpinnerCreate(this,view,info,R.id.spinner_palpitationType,R.array.palpitationType,"Felt");

        SpinnerUtility.SpinnerCreate(this,view,info,R.id.spinner_palpitationDizziness,R.array.YesNoString,"Dizziness");

        SpinnerUtility.SpinnerCreate(this,view,info,R.id.spinner_palpitationFaint,R.array.YesNoString,"Fainting");

        SpinnerUtility.SpinnerCreate(this,view,info,R.id.spinner_palpitationFall,R.array.YesNoString,"Had a fall");

        return view;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onClick(View view) {

        Communicator activity = (Communicator) getActivity();
        switch(view.getId()) {
            case R.id.backButtonAbdominalPain:
                activity.communicate(Communicator.Response.BACK, null);
                break;

            case R.id.continueButtonAbdominalPain:

                try {
                    if (duration.getText().length() != 0)
                        info.put("Duration", duration.getText().toString());

                    if (HowLong.getText().length() != 0)
                        info.put("Lasts", HowLong.getText().toString());

                    if (Symptom.getText().length() != 0)
                        info.put("Other Symptoms", Symptom.getText().toString());

                    if (Start.getText().length() != 0) {
                        info.put("Brought on by", Start.getText().toString());
                    }
                    if (Relief.getText().length() != 0) {
                        info.put("Relieved by", Relief.getText().toString());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                activity.communicate(Communicator.Response.CONTINUE, info);
                break;
        }
    }

}
