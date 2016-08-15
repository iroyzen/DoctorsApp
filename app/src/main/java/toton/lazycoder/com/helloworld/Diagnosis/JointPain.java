package toton.lazycoder.com.helloworld.Diagnosis;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONException;
import org.json.JSONObject;

import toton.lazycoder.com.helloworld.R;
import toton.lazycoder.com.helloworld.Utility.AnimateDiagnosisFragment;
import toton.lazycoder.com.helloworld.Utility.DialogUtility;

public class JointPain extends Fragment implements View.OnClickListener{

    private static final String INFO = "INFO";
    JSONObject info;

    @Override
    public void onClick(View v){
        if(v.getId() == R.id.backButtonJointPain){
            Communicator activity = (Communicator) getActivity();
            activity.communicate(Communicator.Response.BACK, null);
        }else if(v.getId() == R.id.continueButtonJointPain){
            Communicator activity = (Communicator) getActivity();
            activity.communicate(Communicator.Response.CONTINUE, info);
        }else if(v.getId() == R.id.durationJointPain){
            DialogUtility.showNumberPickerDialog(getActivity(), info, "Duration", getString(R.string.duration));
        }else if(v.getId() == R.id.startedAtJointPain){
            // showMultiSelectSpinner should be edited to make UX better
            // Parameters should be modified as well
            DialogUtility.showMultiSelectSpinner(getActivity(), info, "Started At", getString(R.string.started_at), R.array.joint_pain_started_at, -100, -100, -100);
        }else if(v.getId() == R.id.nowAtJointPain){
            // showMultiSelectSpinner should be edited to make UX better
            // Parameters should be modified as well
            DialogUtility.showMultiSelectSpinner(getActivity(), info, "Now At", getString(R.string.now_at), R.array.joint_pain_now_at, -100, -100, -100);
        }else if(v.getId() == R.id.howStartedJointPain){
            DialogUtility.showSingleSelectSpinner(getActivity(), info, "How Started", getString(R.string.how_started), R.array.pain_how_started);
        }else if(v.getId() == R.id.intensityJointPain){
            DialogUtility.showSingleSelectSpinner(getActivity(), info, "Intensity", getString(R.string.intensity), R.array.pain_intensity);
        }else if(v.getId() == R.id.natureJointPain){
            DialogUtility.showSingleSelectSpinner(getActivity(), info, "Nature", getString(R.string.nature), R.array.pain_nature);
        }else if(v.getId() == R.id.broughtOnByJointPain){
            DialogUtility.showSingleSelectSpinner(getActivity(), info, "Brought On By", getString(R.string.brought_on_by), R.array.joint_pain_brought_on_relieved_by);
        }else if(v.getId() == R.id.relievedByJointPain){
            DialogUtility.showSingleSelectSpinner(getActivity(), info, "Relieved By", getString(R.string.relieved_by), R.array.joint_pain_brought_on_relieved_by);
        }else if(v.getId() == R.id.symptomsJointPain){
            DialogUtility.showMultiSelectSpinner(getActivity(), info, "Symptoms", getString(R.string.symptoms), R.array.joint_pain_symptoms, 0, -1, 5);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_joint_pain, container, false);

        AnimateDiagnosisFragment.animateViewsIn(getActivity(), view, R.id.root);

        view.findViewById(R.id.backButtonJointPain).setOnClickListener(this);
        view.findViewById(R.id.continueButtonJointPain).setOnClickListener(this);
        view.findViewById(R.id.durationJointPain).setOnClickListener(this);
        view.findViewById(R.id.startedAtJointPain).setOnClickListener(this);
        view.findViewById(R.id.nowAtJointPain).setOnClickListener(this);
        view.findViewById(R.id.howStartedJointPain).setOnClickListener(this);
        view.findViewById(R.id.intensityJointPain).setOnClickListener(this);
        view.findViewById(R.id.natureJointPain).setOnClickListener(this);
        view.findViewById(R.id.broughtOnByJointPain).setOnClickListener(this);
        view.findViewById(R.id.relievedByJointPain).setOnClickListener(this);
        view.findViewById(R.id.symptomsJointPain).setOnClickListener(this);

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
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
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putString(INFO, info.toString());
    }
}