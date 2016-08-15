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

public class PerianalPain extends Fragment implements View.OnClickListener{

    private static final String INFO = "INFO";
    JSONObject info;

    @Override
    public void onClick(View v){
        if(v.getId() == R.id.backButtonPerianalPain){
            Communicator activity = (Communicator) getActivity();
            activity.communicate(Communicator.Response.BACK, null);
        }else if(v.getId() == R.id.continueButtonPerianalPain){
            Communicator activity = (Communicator) getActivity();
            activity.communicate(Communicator.Response.CONTINUE, info);
        }else if(v.getId() == R.id.durationPerianalPain){
            DialogUtility.showNumberPickerDialog(getActivity(), info, "Duration", getString(R.string.duration));
        }else if(v.getId() == R.id.startedAtPerianalPain){
            DialogUtility.showSingleSelectSpinner(getActivity(), info, "Started At", getString(R.string.started_at), R.array.perianal_pain_started_at);
        }else if(v.getId() == R.id.nowAtPerianalPain){
            DialogUtility.showSingleSelectSpinner(getActivity(), info, "Now At", getString(R.string.now_at), R.array.perianal_pain_now_at);
        }else if(v.getId() == R.id.howStartedPerianalPain){
            DialogUtility.showSingleSelectSpinner(getActivity(), info, "How Started", getString(R.string.how_started), R.array.pain_how_started);
        }else if(v.getId() == R.id.intensityPerianalPain){
            DialogUtility.showSingleSelectSpinner(getActivity(), info, "Intensity", getString(R.string.intensity), R.array.pain_intensity);
        }else if(v.getId() == R.id.naturePerianalPain){
            DialogUtility.showSingleSelectSpinner(getActivity(), info, "Nature", getString(R.string.nature), R.array.pain_nature);
        }else if(v.getId() == R.id.broughtOnByPerianalPain){
            DialogUtility.showSingleSelectSpinner(getActivity(), info, "Brought On By", getString(R.string.brought_on_by), R.array.perianal_pain_brought_on_by);
        }else if(v.getId() == R.id.relievedByPerianalPain){
            DialogUtility.showSingleSelectSpinner(getActivity(), info, "Relieved By", getString(R.string.relieved_by), R.array.back_pain_relieved_by);
        }else if(v.getId() == R.id.symptomsPerianalPain){
            DialogUtility.showMultiSelectSpinner(getActivity(), info, "Symptoms", getString(R.string.symptoms), R.array.perianal_pain_symptoms, 0, -1, 4);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_perianal_pain, container, false);

        AnimateDiagnosisFragment.animateViewsIn(getActivity(), view, R.id.root);

        view.findViewById(R.id.backButtonPerianalPain).setOnClickListener(this);
        view.findViewById(R.id.continueButtonPerianalPain).setOnClickListener(this);
        view.findViewById(R.id.durationPerianalPain).setOnClickListener(this);
        view.findViewById(R.id.startedAtPerianalPain).setOnClickListener(this);
        view.findViewById(R.id.nowAtPerianalPain).setOnClickListener(this);
        view.findViewById(R.id.howStartedPerianalPain).setOnClickListener(this);
        view.findViewById(R.id.intensityPerianalPain).setOnClickListener(this);
        view.findViewById(R.id.naturePerianalPain).setOnClickListener(this);
        view.findViewById(R.id.broughtOnByPerianalPain).setOnClickListener(this);
        view.findViewById(R.id.relievedByPerianalPain).setOnClickListener(this);
        view.findViewById(R.id.symptomsPerianalPain).setOnClickListener(this);

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