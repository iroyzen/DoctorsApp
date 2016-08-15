package toton.lazycoder.com.helloworld.Diagnosis;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONException;
import org.json.JSONObject;

import toton.lazycoder.com.helloworld.R;
import toton.lazycoder.com.helloworld.Utility.SpinnerUtility;


public class BleedingWithStool extends Fragment implements View.OnClickListener{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    JSONObject info;
    private static final String INFO = "INFO";

    public BleedingWithStool() {
        // Required empty public constructor
    }

    public static BleedingWithStool newInstance(String param1, String param2) {
        BleedingWithStool fragment = new BleedingWithStool();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_bleeding_with_stool, container, false);
        view.findViewById(R.id.backButtonAbdominalPain).setOnClickListener(this);
        view.findViewById(R.id.continueButtonAbdominalPain).setOnClickListener(this);

        SpinnerUtility.SpinnerCreate(this,view,info,R.id.spinner_bleedingWithStoolColour,R.array.bleedingWithStoolColour,"Stool Colour");

        SpinnerUtility.SpinnerCreate(this,view,info,R.id.spinner_bleedingWithStoolAmount,R.array.bleedingWithStoolAmount,"Bleeding Amount");

        SpinnerUtility.SpinnerCreate(this,view,info,R.id.spinner_bleedingWithStoolPain,R.array.YesNoString,"Pain");


        SpinnerUtility.SpinnerCreate(this,view,info,R.id.spinner_bleedingWithStoolBowel,R.array.YesNoString,"Change in bowel habit");


        SpinnerUtility.SpinnerCreate(this,view,info,R.id.spinner_bleedingWithStoolConstipation,R.array.YesNoString,"Constipation");


        SpinnerUtility.SpinnerCreate(this,view,info,R.id.spinner_bleedingWithStoolDiarrhoea,R.array.YesNoString,"Diarrhoea");

        return view;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.backButtonAbdominalPain) {
            Communicator activity = (Communicator) getActivity();
            activity.communicate(Communicator.Response.BACK, null);
        } else if (view.getId() == R.id.continueButtonAbdominalPain) {
            Communicator activity = (Communicator) getActivity();
            activity.communicate(Communicator.Response.CONTINUE, info);
        }
    }


}
