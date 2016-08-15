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

public class BackPain extends Fragment implements View.OnClickListener{

    private static final String INFO = "INFO";
    JSONObject info;

    @Override
    public void onClick(View v){
        if(v.getId() == R.id.backButtonBackPain){
            Communicator activity = (Communicator) getActivity();
            activity.communicate(Communicator.Response.BACK, null);
        }else if(v.getId() == R.id.continueButtonBackPain){
            Communicator activity = (Communicator) getActivity();
            activity.communicate(Communicator.Response.CONTINUE, info);
        }else if(v.getId() == R.id.durationBackPain){
            DialogUtility.showNumberPickerDialog(getActivity(), info, "Duration", getString(R.string.duration));
        }else if(v.getId() == R.id.startedAtBackPain){
            DialogUtility.showMultiSelectSpinner(getActivity(), info, "Started At", getString(R.string.started_at), R.array.back_pain_started_at, -1, 6, -1);
        }else if(v.getId() == R.id.nowAtBackPain){
            DialogUtility.showMultiSelectSpinner(getActivity(), info, "Now At", getString(R.string.now_at), R.array.back_pain_now_at, 0, -1, 7);
        }else if(v.getId() == R.id.howStartedBackPain){
            DialogUtility.showSingleSelectSpinner(getActivity(), info, "How Started", getString(R.string.how_started), R.array.pain_how_started);
        }else if(v.getId() == R.id.intensityBackPain){
            DialogUtility.showSingleSelectSpinner(getActivity(), info, "Intensity", getString(R.string.intensity), R.array.pain_intensity);
        }else if(v.getId() == R.id.natureBackPain){
            DialogUtility.showSingleSelectSpinner(getActivity(), info, "Nature", getString(R.string.nature), R.array.pain_nature);
        }else if(v.getId() == R.id.broughtOnByBackPain){
            DialogUtility.showSingleSelectSpinner(getActivity(), info, "Brought On By", getString(R.string.brought_on_by), R.array.back_pain_brought_on_by);
        }else if(v.getId() == R.id.relievedByBackPain){
            DialogUtility.showMultiSelectSpinner(getActivity(), info, "Relieved By", getString(R.string.relieved_by), R.array.back_pain_relieved_by, 0, -1, 2);
        }else if(v.getId() == R.id.symptomsBackPain){
            DialogUtility.showMultiSelectSpinner(getActivity(), info, "Symptoms", getString(R.string.symptoms), R.array.back_pain_symptoms, 0, -1, 4);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_back_pain, container, false);

        AnimateDiagnosisFragment.animateViewsIn(getActivity(), view, R.id.root);

        view.findViewById(R.id.backButtonBackPain).setOnClickListener(this);
        view.findViewById(R.id.continueButtonBackPain).setOnClickListener(this);
        view.findViewById(R.id.durationBackPain).setOnClickListener(this);
        view.findViewById(R.id.startedAtBackPain).setOnClickListener(this);
        view.findViewById(R.id.nowAtBackPain).setOnClickListener(this);
        view.findViewById(R.id.howStartedBackPain).setOnClickListener(this);
        view.findViewById(R.id.intensityBackPain).setOnClickListener(this);
        view.findViewById(R.id.natureBackPain).setOnClickListener(this);
        view.findViewById(R.id.broughtOnByBackPain).setOnClickListener(this);
        view.findViewById(R.id.relievedByBackPain).setOnClickListener(this);
        view.findViewById(R.id.symptomsBackPain).setOnClickListener(this);

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