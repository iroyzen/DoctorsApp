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

public class Cough extends Fragment implements View.OnClickListener{

    private static final String INFO = "INFO";
    JSONObject info;

    @Override
    public void onClick(View v){
        if(v.getId() == R.id.backButtonCough){
            Communicator activity = (Communicator) getActivity();
            activity.communicate(Communicator.Response.BACK, null);
        }else if(v.getId() == R.id.continueButtonCough){
            Communicator activity = (Communicator) getActivity();
            activity.communicate(Communicator.Response.CONTINUE, info);
        }else if(v.getId() == R.id.durationCough){
            DialogUtility.showNumberPickerDialog(getActivity(), info, "Duration", getString(R.string.duration));
        }else if(v.getId() == R.id.feverCough){
            DialogUtility.showYesNoDialog(getActivity(), info, "Fever", getString(R.string.fever));
        }else if(v.getId() == R.id.expectorationCough){
            DialogUtility.showYesNoDialog(getActivity(), info, "Expectoration", getString(R.string.expectoration));
        }else if(v.getId() == R.id.runningNoseCough){
            DialogUtility.showYesNoDialog(getActivity(), info, "Running Nose", getString(R.string.running_nose));
        }else if(v.getId() == R.id.itchyThroatCough){
            DialogUtility.showYesNoDialog(getActivity(), info, "Itchy Throat", getString(R.string.itchy_throat));
        }else if(v.getId() == R.id.difficultyInBreathingCough){
            DialogUtility.showYesNoDialog(getActivity(), info, "Difficulty in Breathing", getString(R.string.difficulty_in_breathing));
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_cough_diag, container, false);

        AnimateDiagnosisFragment.animateViewsIn(getActivity(), view, R.id.root);

        view.findViewById(R.id.backButtonCough).setOnClickListener(this);
        view.findViewById(R.id.continueButtonCough).setOnClickListener(this);
        view.findViewById(R.id.difficultyInBreathingCough).setOnClickListener(this);
        view.findViewById(R.id.durationCough).setOnClickListener(this);
        view.findViewById(R.id.expectorationCough).setOnClickListener(this);
        view.findViewById(R.id.feverCough).setOnClickListener(this);
        view.findViewById(R.id.runningNoseCough).setOnClickListener(this);
        view.findViewById(R.id.itchyThroatCough).setOnClickListener(this);

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