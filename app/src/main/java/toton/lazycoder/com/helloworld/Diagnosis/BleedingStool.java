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

public class BleedingStool extends Fragment implements View.OnClickListener{

    private static final String INFO = "INFO";
    JSONObject info;

    @Override
    public void onClick(View v){
        if(v.getId() == R.id.backButtonBleedingStool){
            Communicator activity = (Communicator) getActivity();
            activity.communicate(Communicator.Response.BACK, null);
        }else if(v.getId() == R.id.continueButtonBleedingStool){
            Communicator activity = (Communicator) getActivity();
            activity.communicate(Communicator.Response.CONTINUE, info);
        }else if(v.getId() == R.id.colorBleedingStool){
            DialogUtility.showSingleSelectSpinner(getActivity(), info, "Colour", getString(R.string.colour), R.array.bleeding_stool_color);
        }else if(v.getId() == R.id.amountBleedingStool){
            DialogUtility.showSingleSelectSpinner(getActivity(), info, "Amount", getString(R.string.amount), R.array.bleeding_stool_amount);
        }else if(v.getId() == R.id.painDuringPassingStoolBleedingStool){
            DialogUtility.showYesNoDialog(getActivity(), info, "Pain during passing stool", getString(R.string.pain_during_passing_stool));
        }else if(v.getId() == R.id.changeInBowelHabitBleedingStool){
            DialogUtility.showYesNoDialog(getActivity(), info, "Change In Bowel Habit", getString(R.string.change_in_bowel_habit));
        }else if(v.getId() == R.id.constipationBleedingStool){
            DialogUtility.showYesNoDialog(getActivity(), info, "Constipation", getString(R.string.constipation));
        }else if(v.getId() == R.id.diarrhoeaBleedingStool){
            DialogUtility.showYesNoDialog(getActivity(), info, "Diarrhoea", getString(R.string.diarrhoea));
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_bleeding_stool_diag, container, false);

        AnimateDiagnosisFragment.animateViewsIn(getActivity(), view, R.id.root);

        view.findViewById(R.id.amountBleedingStool).setOnClickListener(this);
        view.findViewById(R.id.changeInBowelHabitBleedingStool).setOnClickListener(this);
        view.findViewById(R.id.colorBleedingStool).setOnClickListener(this);
        view.findViewById(R.id.constipationBleedingStool).setOnClickListener(this);
        view.findViewById(R.id.painDuringPassingStoolBleedingStool).setOnClickListener(this);
        view.findViewById(R.id.diarrhoeaBleedingStool).setOnClickListener(this);
        view.findViewById(R.id.backButtonBleedingStool).setOnClickListener(this);
        view.findViewById(R.id.continueButtonBleedingStool).setOnClickListener(this);

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