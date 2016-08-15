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

public class Headache extends Fragment implements View.OnClickListener{

    private static final String INFO = "INFO";
    JSONObject info;

    @Override
    public void onClick(View v){
        if(v.getId() == R.id.backButtonHeadache){
            Communicator activity = (Communicator) getActivity();
            activity.communicate(Communicator.Response.BACK, null);
        }else if(v.getId() == R.id.continueButtonHeadache){
            Communicator activity = (Communicator) getActivity();
            activity.communicate(Communicator.Response.CONTINUE, info);
        }else if(v.getId() == R.id.durationHeadache){
            DialogUtility.showNumberPickerDialog(getActivity(), info, "Duration", getString(R.string.duration));
        }else if(v.getId() == R.id.startedAtHeadache){
            DialogUtility.showMultiSelectSpinner(getActivity(), info, "Started At", getString(R.string.started_at), R.array.headache_places, -1, 5, -1);
        }else if(v.getId() == R.id.nowAtHeadache){
            DialogUtility.showMultiSelectSpinner(getActivity(), info, "Now At", getString(R.string.now_at), R.array.headache_places, -1, 5, -1);
        }else if(v.getId() == R.id.howStartedHeadache){
            DialogUtility.showSingleSelectSpinner(getActivity(), info, "How Started", getString(R.string.how_started), R.array.pain_how_started);
        }else if(v.getId() == R.id.intensityHeadache){
            DialogUtility.showSingleSelectSpinner(getActivity(), info, "Intensity", getString(R.string.intensity), R.array.pain_intensity);
        }else if(v.getId() == R.id.natureHeadache){
            DialogUtility.showSingleSelectSpinner(getActivity(), info, "Nature", getString(R.string.nature), R.array.pain_nature);
        }else if(v.getId() == R.id.broughtOnByHeadache){
            DialogUtility.showSingleSelectSpinner(getActivity(), info, "Brought On By", getString(R.string.brought_on_by), R.array.headache_brought_on_by);
        }else if(v.getId() == R.id.relievedByHeadache){
            DialogUtility.showSingleSelectSpinner(getActivity(), info, "Relieved By", getString(R.string.relieved_by), R.array.none_others_array);
        }else if(v.getId() == R.id.symptomsHeadache){
            DialogUtility.showMultiSelectSpinner(getActivity(), info, "Symptoms", getString(R.string.symptoms), R.array.headache_symptoms, 0, -1, 3);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_headache, container, false);

        AnimateDiagnosisFragment.animateViewsIn(getActivity(), view, R.id.root);

        view.findViewById(R.id.backButtonHeadache).setOnClickListener(this);
        view.findViewById(R.id.continueButtonHeadache).setOnClickListener(this);
        view.findViewById(R.id.durationHeadache).setOnClickListener(this);
        view.findViewById(R.id.startedAtHeadache).setOnClickListener(this);
        view.findViewById(R.id.nowAtHeadache).setOnClickListener(this);
        view.findViewById(R.id.howStartedHeadache).setOnClickListener(this);
        view.findViewById(R.id.intensityHeadache).setOnClickListener(this);
        view.findViewById(R.id.natureHeadache).setOnClickListener(this);
        view.findViewById(R.id.broughtOnByHeadache).setOnClickListener(this);
        view.findViewById(R.id.relievedByHeadache).setOnClickListener(this);
        view.findViewById(R.id.symptomsHeadache).setOnClickListener(this);

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