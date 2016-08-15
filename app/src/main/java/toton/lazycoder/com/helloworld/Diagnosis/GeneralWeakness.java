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

public class GeneralWeakness extends Fragment implements View.OnClickListener{

    private static final String INFO = "INFO";
    JSONObject info;

    @Override
    public void onClick(View v){
        if(v.getId() == R.id.backButtonGeneralWeakness){
            Communicator activity = (Communicator) getActivity();
            activity.communicate(Communicator.Response.BACK, null);
        }else if(v.getId() == R.id.continueButtonGeneralWeakness){
            Communicator activity = (Communicator) getActivity();
            activity.communicate(Communicator.Response.CONTINUE, info);
        }else if(v.getId() == R.id.durationGeneralWeakness){
            DialogUtility.showNumberPickerDialog(getActivity(), info, "Duration", getString(R.string.duration));
        }else if(v.getId() == R.id.appetiteGeneralWeakness){
            DialogUtility.showSingleSelectSpinner(getActivity(), info, "Appetite", getString(R.string.appetite), R.array.weakness_appetite);
        }else if(v.getId() == R.id.weightGeneralWeakness){
            DialogUtility.showSingleSelectSpinner(getActivity(), info, "Weight", getString(R.string.weight), R.array.weakness_weight);
        }else if(v.getId() == R.id.abdominalPainGeneralWeakness){
            DialogUtility.showYesNoDialog(getActivity(), info, "Abdominal Pain", getString(R.string.abdominal_pain));
        }else if(v.getId() == R.id.chestPainGeneralWeakness){
            DialogUtility.showYesNoDialog(getActivity(), info, "Chest Pain", getString(R.string.chest_pain));
        }else if(v.getId() == R.id.feverGeneralWeakness){
            DialogUtility.showYesNoDialog(getActivity(), info, "Fever", getString(R.string.fever));
        }else if(v.getId() == R.id.coughGeneralWeakness){
            DialogUtility.showYesNoDialog(getActivity(), info, "Cough", getString(R.string.cough));
        }else if(v.getId() == R.id.diarrhoeaGeneralWeakness){
            DialogUtility.showYesNoDialog(getActivity(), info, "Diarrhoea", getString(R.string.diarrhoea));
        }else if(v.getId() == R.id.constipationGeneralWeakness){
            DialogUtility.showYesNoDialog(getActivity(), info, "Constipation", getString(R.string.constipation));
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_general_weakness_diag, container, false);

        AnimateDiagnosisFragment.animateViewsIn(getActivity(), view, R.id.root);

        view.findViewById(R.id.backButtonGeneralWeakness).setOnClickListener(this);
        view.findViewById(R.id.continueButtonGeneralWeakness).setOnClickListener(this);
        view.findViewById(R.id.durationGeneralWeakness).setOnClickListener(this);
        view.findViewById(R.id.appetiteGeneralWeakness).setOnClickListener(this);
        view.findViewById(R.id.weightGeneralWeakness).setOnClickListener(this);
        view.findViewById(R.id.abdominalPainGeneralWeakness).setOnClickListener(this);
        view.findViewById(R.id.chestPainGeneralWeakness).setOnClickListener(this);
        view.findViewById(R.id.feverGeneralWeakness).setOnClickListener(this);
        view.findViewById(R.id.coughGeneralWeakness).setOnClickListener(this);
        view.findViewById(R.id.diarrhoeaGeneralWeakness).setOnClickListener(this);
        view.findViewById(R.id.constipationGeneralWeakness).setOnClickListener(this);

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