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

public class ParticularWeakness extends Fragment implements View.OnClickListener{

    private static final String INFO = "INFO";
    JSONObject info;

    @Override
    public void onClick(View v){
        if(v.getId() == R.id.backButtonParticularWeakness){
            Communicator activity = (Communicator) getActivity();
            activity.communicate(Communicator.Response.BACK, null);
        }else if(v.getId() == R.id.continueButtonParticularWeakness){
            Communicator activity = (Communicator) getActivity();
            activity.communicate(Communicator.Response.CONTINUE, info);
        }else if(v.getId() == R.id.whereParticularWeakness){
            DialogUtility.showMultiSelectSpinner(getActivity(), info, "Where", getString(R.string.where),
                    R.array.select_or_describe_limbs, -1, -1, 3);
        }else if(v.getId() == R.id.durationParticularWeakness){
            DialogUtility.showNumberPickerDialog(getActivity(), info, "Duration", getString(R.string.duration));
        }else if(v.getId() == R.id.hoInjuryParticularWeakness){
            DialogUtility.showYesNoDialog(getActivity(), info, "H/O Injury", getString(R.string.h_o_injury));
        }else if(v.getId() == R.id.howStartedParticularWeakness){
            DialogUtility.showEditTextDialog(getActivity(), info, "How Started", getString(R.string.how_started));
        }else if(v.getId() == R.id.progressParticularWeakness){
            DialogUtility.showSingleSelectSpinner(getActivity(), info, "Progress", getString(R.string.progress),
                    R.array.particular_weakness_progress);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_particular_weakness, container, false);

        AnimateDiagnosisFragment.animateViewsIn(getActivity(), view, R.id.root);

        view.findViewById(R.id.backButtonParticularWeakness).setOnClickListener(this);
        view.findViewById(R.id.continueButtonParticularWeakness).setOnClickListener(this);
        view.findViewById(R.id.durationParticularWeakness).setOnClickListener(this);
        view.findViewById(R.id.whereParticularWeakness).setOnClickListener(this);
        view.findViewById(R.id.progressParticularWeakness).setOnClickListener(this);
        view.findViewById(R.id.hoInjuryParticularWeakness).setOnClickListener(this);
        view.findViewById(R.id.howStartedParticularWeakness).setOnClickListener(this);

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