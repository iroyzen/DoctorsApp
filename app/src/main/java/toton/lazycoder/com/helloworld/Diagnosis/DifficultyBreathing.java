package toton.lazycoder.com.helloworld.Diagnosis;


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

import toton.lazycoder.com.helloworld.ComplainModule;
import toton.lazycoder.com.helloworld.R;
import toton.lazycoder.com.helloworld.Utility.SpinnerUtility;

public class DifficultyBreathing extends Fragment implements View.OnClickListener   {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    JSONObject info;
    JSONArray infoSym;
    JSONArray infoStart;
    JSONArray infoRelief;
    private static final String INFO = "INFO";

    EditText duration;
    EditText OtherStart;
    EditText OtherRelief;
    EditText Cough;
    EditText OtherSym;

    public DifficultyBreathing() {

    }

    public static DifficultyBreathing newInstance(String param1, String param2) {
        DifficultyBreathing fragment = new DifficultyBreathing();
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
        infoSym=new JSONArray();
        infoStart=new JSONArray();
        infoRelief=new JSONArray();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_difficulty_breathing, container, false);
        view.findViewById(R.id.backButtonAbdominalPain).setOnClickListener(this);
        view.findViewById(R.id.continueButtonAbdominalPain).setOnClickListener(this);
        view.findViewById(R.id.checkBox_difficultyBreathingExertion).setOnClickListener(this);
        view.findViewById(R.id.checkBox_difficultyBreathingStairs).setOnClickListener(this);
        view.findViewById(R.id.checkBox_difficultyBreathingRest).setOnClickListener(this);
        view.findViewById(R.id.checkBox_difficultyBreathingSit).setOnClickListener(this);
        view.findViewById(R.id.difficultyBreathingCoughNo).setOnClickListener(this);
        view.findViewById(R.id.checkBox_difficultyBreathingWeak).setOnClickListener(this);
        view.findViewById(R.id.checkBox_difficultyBreathingFever).setOnClickListener(this);

        duration=(EditText)view.findViewById(R.id.editText_difficultyBreathingDuration);
        OtherStart=(EditText)view.findViewById(R.id.editText_difficultyBreathingOtherReason);
        OtherRelief=(EditText)view.findViewById(R.id.editText_difficultyBreathingOtherRelief);
        OtherSym=(EditText)view.findViewById(R.id.editText_difficultyBreathingOtherSymptom);
        Cough=(EditText)view.findViewById(R.id.editText_difficultyBreathingCough);

        SpinnerUtility.SpinnerCreate(this,view,info,R.id.spinner_difficultyBreathingProgress,R.array.difficultyBreathingProgress,"Progress");

        SpinnerUtility.SpinnerCreate(this,view,info,R.id.spinner_difficultyBreathingWake,R.array.YesNoString,"Night exacerbation");

        SpinnerUtility.SpinnerCreate(this,view,info,R.id.spinner_difficultyBreathingChest,R.array.YesNoString,"Associated with Chest Pain");

        return view;
    }

    @Override
    public void onClick(View view) {

        ComplainModule activity = (ComplainModule) getActivity();
        switch(view.getId()) {
            case R.id.backButtonAbdominalPain:
                activity.communicate(Communicator.Response.BACK, null);
                break;

            case R.id.continueButtonAbdominalPain:
                if (OtherSym.getText().length() != 0) {
                    infoSym.put(OtherSym.getText().toString());
                }
                if (OtherStart.getText().length() != 0) {
                    infoSym.put(OtherStart.getText().toString());
                }
                if (OtherRelief.getText().length() != 0) {
                    infoRelief.put(OtherRelief.getText().toString());
                }

                try {
                    if(duration.getText().length() != 0)
                    info.put("Duration: ", duration.getText().toString() + "days");

                    info.put("Other Symptoms", infoSym);
                    info.put("Brought on by",infoStart);
                    info.put("Relieved by",infoRelief);
                    if(Cough.getText().length() != 0)
                    {
                        info.put("Associated with cough",Cough.getText().toString());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                activity.communicate(Communicator.Response.CONTINUE, info);
                break;

            case R.id.checkBox_difficultyBreathingExertion:
                if (((CheckBox) view).isChecked()) {
                    infoStart.put("Exertion");
                } else {
                    try {
                        for (int i = 0; i < infoStart.length(); i++) {
                            if (infoStart.get(i) == "Difficulty in Movement") {
                                infoStart.remove(i);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;

            case R.id.checkBox_difficultyBreathingStairs:
                if (((CheckBox) view).isChecked()) {
                    infoStart.put("Climbing Stairs");
                } else {
                    try {
                        for (int i = 0; i < infoStart.length(); i++) {
                            if (infoStart.get(i) == "Climbing Stairs") {
                                infoStart.remove(i);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;

            case R.id.checkBox_difficultyBreathingRest:
                if (((CheckBox) view).isChecked()) {
                    infoRelief.put("Rest");
                } else {
                    try {
                        for (int i = 0; i < infoRelief.length(); i++) {
                            if (infoRelief.get(i) == "Rest") {
                                infoRelief.remove(i);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;

            case R.id.checkBox_difficultyBreathingSit:
                if (((CheckBox) view).isChecked()) {
                    infoRelief.put("Sitting Up");
                } else {
                    try {
                        for (int i = 0; i < infoRelief.length(); i++) {
                            if (infoRelief.get(i) == "Sitting Up") {
                                infoRelief.remove(i);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;

            case R.id.difficultyBreathingCoughNo:
                try{
                    info.put("Associated with cough", "No");
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
                break;

            case R.id.checkBox_difficultyBreathingWeak:
                if (((CheckBox) view).isChecked()) {
                    infoSym.put("General Weakness");
                } else {
                    try {
                        for (int i = 0; i < infoRelief.length(); i++) {
                            if (infoRelief.get(i) == "General Weakness") {
                                infoRelief.remove(i);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;

            case R.id.checkBox_difficultyBreathingFever:
                if (((CheckBox) view).isChecked()) {
                    infoSym.put("Fever");
                } else {
                    try {
                        for (int i = 0; i < infoRelief.length(); i++) {
                            if (infoRelief.get(i) == "Fever") {
                                infoRelief.remove(i);
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
