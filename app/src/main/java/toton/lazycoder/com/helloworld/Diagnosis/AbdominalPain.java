package toton.lazycoder.com.helloworld.Diagnosis;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONException;
import org.json.JSONObject;

import toton.lazycoder.com.helloworld.ComplainModule;
import toton.lazycoder.com.helloworld.R;
import toton.lazycoder.com.helloworld.Utility.AnimateDiagnosisFragment;
import toton.lazycoder.com.helloworld.Utility.DialogUtility;

public class AbdominalPain extends Fragment implements View.OnClickListener{

    private static final String INFO = "INFO";
    JSONObject info ;

    @Override
    public void onClick(View v){
        if(v.getId() == R.id.backButtonAbdominalPain){
            Communicator activity = (Communicator) getActivity();
            activity.communicate(Communicator.Response.BACK, null);
        }else if(v.getId() == R.id.continueButtonAbdominalPain){
            ComplainModule activity = (ComplainModule) getActivity();
            activity.communicate(Communicator.Response.CONTINUE, info);
        }else if(v.getId() == R.id.durationAbdominalPain){
            DialogUtility.showNumberPickerDialog(getActivity(), info, "Duration", getString(R.string.duration));
        }else if(v.getId() == R.id.startedAtAbdominalPain){
            DialogUtility.showMultiSelectSpinner(getActivity(), info, "Started At", getString(R.string.started_at), R.array.abdominal_pain_started_at, -1, 9, -1);
        }else if(v.getId() == R.id.nowAtAbdominalPain){
            DialogUtility.showMultiSelectSpinner(getActivity(), info, "Now At", getString(R.string.now_at), R.array.abdominal_pain_now_at, 0, 10, -1);
        }else if(v.getId() == R.id.howStartedAbdominalPain){
            DialogUtility.showSingleSelectSpinner(getActivity(), info, "How Started", getString(R.string.how_started), R.array.pain_how_started);
        }else if(v.getId() == R.id.intensityAbdominalPain){
            DialogUtility.showSingleSelectSpinner(getActivity(), info, "Intensity", getString(R.string.intensity), R.array.pain_intensity);
        }else if(v.getId() == R.id.natureAbdominalPain){
            DialogUtility.showSingleSelectSpinner(getActivity(), info, "Nature", getString(R.string.nature), R.array.pain_nature);
        }else if(v.getId() == R.id.broughtOnByAbdominalPain){
            DialogUtility.showSingleSelectSpinner(getActivity(), info, "Brought On By", getString(R.string.brought_on_by), R.array.abdominal_pain_brought_on_by);
        }else if(v.getId() == R.id.relievedByAbdominalPain){
            DialogUtility.showSingleSelectSpinner(getActivity(), info, "Relieved By", getString(R.string.relieved_by), R.array.abdominal_pain_relieved_by);
        }else if(v.getId() == R.id.symptomsAbdominalPain){
            DialogUtility.showMultiSelectSpinner(getActivity(), info, "Symptoms", getString(R.string.symptoms), R.array.abdominal_pain_symptoms, 0, -1, 8);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_abdominal_pain, container, false);

        AnimateDiagnosisFragment.animateViewsIn(getActivity(), view, R.id.root);

        view.findViewById(R.id.backButtonAbdominalPain).setOnClickListener(this);
        view.findViewById(R.id.continueButtonAbdominalPain).setOnClickListener(this);
        view.findViewById(R.id.durationAbdominalPain).setOnClickListener(this);
        view.findViewById(R.id.startedAtAbdominalPain).setOnClickListener(this);
        view.findViewById(R.id.nowAtAbdominalPain).setOnClickListener(this);
        view.findViewById(R.id.howStartedAbdominalPain).setOnClickListener(this);
        view.findViewById(R.id.intensityAbdominalPain).setOnClickListener(this);
        view.findViewById(R.id.natureAbdominalPain).setOnClickListener(this);
        view.findViewById(R.id.broughtOnByAbdominalPain).setOnClickListener(this);
        view.findViewById(R.id.relievedByAbdominalPain).setOnClickListener(this);
        view.findViewById(R.id.symptomsAbdominalPain).setOnClickListener(this);

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