package toton.lazycoder.com.helloworld.Diagnosis;

import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import toton.lazycoder.com.helloworld.R;
import toton.lazycoder.com.helloworld.Utility.SpinnerUtility;


public class AbdomenSwelling extends Fragment implements View.OnClickListener {

    static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    JSONObject info;
    JSONArray infoSub;
    private static final String INFO = "INFO";

    EditText OtherSym;
    EditText duration;

    public static AbdomenSwelling newInstance(String param1, String param2) {
        AbdomenSwelling fragment = new AbdomenSwelling();
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
        infoSub= new JSONArray();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_abdomen_swelling, container, false);

        view.findViewById(R.id.backButtonAbdominalPain).setOnClickListener(this);
        view.findViewById(R.id.continueButtonAbdominalPain).setOnClickListener(this);
        view.findViewById(R.id.SingleSwellAbdomen).setOnClickListener(this);
        view.findViewById(R.id.MultipleSwellAbdomen).setOnClickListener(this);
        view.findViewById(R.id.abdomenSwellDiffMov).setOnClickListener(this);
        view.findViewById(R.id.abdomenSwellDiffWork).setOnClickListener(this);

        OtherSym = (EditText)view.findViewById(R.id.editText_abdomenSwellOtherSymp);
        duration = (EditText)view.findViewById(R.id.editText_abdomenSwellDuration);

        SpinnerUtility.SpinnerCreate(this,view,info,R.id.spinner_abdomenSwellPos,R.array.abdomenSwellPos,"Position");

        SpinnerUtility.SpinnerCreate(this,view,info,R.id.spinner_abdomenSwellStart,R.array.abdomenSwellStart,"Start");

        SpinnerUtility.SpinnerCreate(this,view,info,R.id.spinner_abdomenSwellPain,R.array.YesNoString,"Painful");

        SpinnerUtility.SpinnerCreate(this,view,info,R.id.spinner_abdomenSwellSize,R.array.abdomenSwellSize,"Change in size");

        SpinnerUtility.SpinnerCreate(this,view,info,R.id.spinner_abdomenSwellStart,R.array.abdomenSwellStart,"Start");

        SpinnerUtility.SpinnerCreate(this,view,info,R.id.spinner_abdomenSwellSkin,R.array.abdomenSwellSkin,"Skin colour");

        SpinnerUtility.SpinnerCreate(this,view,info,R.id.spinner_abdomenSwellHistory,R.array.YesNoString,"History");

        SpinnerUtility.SpinnerCreate(this,view,info,R.id.spinner_abdomenSwellGeneral,R.array.YesNoString,"General");

        SpinnerUtility.SpinnerCreate(this,view,info,R.id.spinner_abdomenSwellWeight,R.array.YesNoString,"Weight");

        SpinnerUtility.SpinnerCreate(this,view,info,R.id.spinner_abdomenSwellBowel,R.array.abdomenSwellBowel,"Bowel");

        SpinnerUtility.SpinnerCreate(this,view,info,R.id.spinner_abdomenSwellUrine,R.array.YesNoString,"Urine");

        SpinnerUtility.SpinnerCreate(this,view,info,R.id.spinner_abdomenSwellVomit,R.array.YesNoString,"Vomit");

        SpinnerUtility.SpinnerCreate(this,view,info,R.id.spinner_abdomenSwellAppetite,R.array.abdomenSwellAppetite,"Appetite");

        SpinnerUtility.SpinnerCreate(this,view,info,R.id.spinner_abdomenSwellCoughSize,R.array.abdomenSwellCoughSize,"Change in Size");


        return view;
    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
    @Override
    public void onClick(View view) {

        Communicator activity = (Communicator) getActivity();
        switch(view.getId())
        {
            case R.id.backButtonAbdominalPain:
                activity.communicate(Communicator.Response.BACK, null);
                break;

            case  R.id.continueButtonAbdominalPain:
                if(OtherSym.getText()!=null) {
                    infoSub.put(OtherSym.getText());
                }

                try {
                    info.put("Duration", duration.getText() + "days");
                    info.put("Other Symptoms",infoSub);
                }catch (Exception e)
                {
                    e.printStackTrace();
                }

                activity.communicate(Communicator.Response.CONTINUE, info);
                break;

            case R.id.SingleSwellAbdomen:
                try{
                    info.put("No. of Swellings", "Single");
                } catch(Exception e)
                {
                    e.printStackTrace();
                }
                break;

            case R.id.MultipleSwellAbdomen:
                try{
                    info.put("No. of Swellings", "Multiple");
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
                break;

            case R.id.abdomenSwellDiffMov:
                if(((CheckBox)view).isChecked())
                {
                    infoSub.put("Difficulty in Movement");
                }
                else {
                    try {
                        for (int i = 0; i < infoSub.length(); i++) {
                            if (infoSub.get(i) == "Difficulty in Movement") {
                                infoSub.remove(i);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;

            case R.id.abdomenSwellDiffWork:
                if(((CheckBox)view).isChecked())
                {
                    infoSub.put("Difficulty in Work");
                }
                else {
                    try {
                        for (int i = 0; i < infoSub.length(); i++) {
                            if (infoSub.get(i) == "Difficulty in Work") {
                                infoSub.remove(i);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

}
