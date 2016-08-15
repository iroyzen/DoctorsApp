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

public class ScrotalPain extends Fragment implements View.OnClickListener{

    private static final String INFO = "INFO";
    JSONObject info;

    @Override
    public void onClick(View v){
        if(v.getId() == R.id.backButtonScrotalPain){
            Communicator activity = (Communicator) getActivity();
            activity.communicate(Communicator.Response.BACK, null);
        }else if(v.getId() == R.id.continueButtonScrotalPain){
            Communicator activity = (Communicator) getActivity();
            activity.communicate(Communicator.Response.CONTINUE, info);
        }else if(v.getId() == R.id.durationScrotalPain){
            DialogUtility.showNumberPickerDialog(getActivity(), info, "Duration", getString(R.string.duration));
        }else if(v.getId() == R.id.startedAtScrotalPain){
            DialogUtility.showMultiSelectSpinner(getActivity(), info, "Started At", getString(R.string.started_at), R.array.scrotal_pain_started_at, -1, 2, -1);
        }else if(v.getId() == R.id.nowAtScrotalPain){
            DialogUtility.showSingleSelectSpinner(getActivity(), info, "Now At", getString(R.string.now_at), R.array.scrotal_pain_now_at);
        }else if(v.getId() == R.id.howStartedScrotalPain){
            DialogUtility.showSingleSelectSpinner(getActivity(), info, "How Started", getString(R.string.how_started), R.array.pain_how_started);
        }else if(v.getId() == R.id.intensityScrotalPain){
            DialogUtility.showSingleSelectSpinner(getActivity(), info, "Intensity", getString(R.string.intensity), R.array.pain_intensity);
        }else if(v.getId() == R.id.natureScrotalPain){
            DialogUtility.showSingleSelectSpinner(getActivity(), info, "Nature", getString(R.string.nature), R.array.pain_nature);
        }else if(v.getId() == R.id.broughtOnByScrotalPain){
            DialogUtility.showSingleSelectSpinner(getActivity(), info, "Brought On By", getString(R.string.brought_on_by), R.array.scrotal_pain_brought_on_by);
        }else if(v.getId() == R.id.relievedByScrotalPain){
            DialogUtility.showSingleSelectSpinner(getActivity(), info, "Relieved By", getString(R.string.relieved_by), R.array.back_pain_relieved_by);
        }else if(v.getId() == R.id.symptomsScrotalPain){
            DialogUtility.showMultiSelectSpinner(getActivity(), info, "Symptoms", getString(R.string.symptoms), R.array.scrotal_pain_symptoms, 0, -1, 3);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_scrotal_pain, container, false);

        AnimateDiagnosisFragment.animateViewsIn(getActivity(), view, R.id.root);

        view.findViewById(R.id.backButtonScrotalPain).setOnClickListener(this);
        view.findViewById(R.id.continueButtonScrotalPain).setOnClickListener(this);
        view.findViewById(R.id.durationScrotalPain).setOnClickListener(this);
        view.findViewById(R.id.startedAtScrotalPain).setOnClickListener(this);
        view.findViewById(R.id.nowAtScrotalPain).setOnClickListener(this);
        view.findViewById(R.id.howStartedScrotalPain).setOnClickListener(this);
        view.findViewById(R.id.intensityScrotalPain).setOnClickListener(this);
        view.findViewById(R.id.natureScrotalPain).setOnClickListener(this);
        view.findViewById(R.id.broughtOnByScrotalPain).setOnClickListener(this);
        view.findViewById(R.id.relievedByScrotalPain).setOnClickListener(this);
        view.findViewById(R.id.symptomsScrotalPain).setOnClickListener(this);

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