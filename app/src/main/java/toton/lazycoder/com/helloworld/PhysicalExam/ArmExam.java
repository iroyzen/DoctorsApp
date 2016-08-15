package toton.lazycoder.com.helloworld.PhysicalExam;


import android.app.FragmentManager;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONException;
import org.json.JSONObject;

import toton.lazycoder.com.helloworld.Diagnosis.Communicator;
import toton.lazycoder.com.helloworld.PhysicalExamModule;
import toton.lazycoder.com.helloworld.R;
import toton.lazycoder.com.helloworld.Utility.SpinnerUtility;

public class ArmExam extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    JSONObject info;
    private static final String INFO = "INFO";


    public ArmExam() {

    }

    public static ArmExam newInstance(String param1, String param2) {
        ArmExam fragment = new ArmExam();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_arm_exam, container, false);
        view.findViewById(R.id.Save_button).setOnClickListener(this);

        SpinnerUtility.SpinnerCreate(this,view,info,R.id.spinner_PinchTheSkin,R.array.PinchSkin,"Pinch Skin");

        return view;

    }
    @Override
    public void onClick(View view)
    {
        FragmentManager fm = getFragmentManager();
        PhysicalExamModule activity = (PhysicalExamModule) getActivity();
        if(view.getId()==R.id.Save_button)
        {
            activity.communicate(Communicator.Response.CONTINUE, info);
        }
    }


}
