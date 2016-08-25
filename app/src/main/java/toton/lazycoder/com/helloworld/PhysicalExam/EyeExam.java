package toton.lazycoder.com.helloworld.PhysicalExam;


import android.app.FragmentManager;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import org.json.JSONException;
import org.json.JSONObject;

import toton.lazycoder.com.helloworld.Diagnosis.Communicator;
import toton.lazycoder.com.helloworld.PhysicalExamModule;
import toton.lazycoder.com.helloworld.R;
import toton.lazycoder.com.helloworld.Utility.SpinnerUtility;
import toton.lazycoder.com.helloworld.Utility.Toggle;

public class EyeExam extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    JSONObject info;
    private static final String INFO = "INFO";

    LinearLayout jaundice_body,pallor_body,pupil_body;

    public EyeExam() {
        // Required empty public constructor
    }


    public static EyeExam newInstance(String param1, String param2) {
        EyeExam fragment = new EyeExam();
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

        View view = inflater.inflate(R.layout.fragment_eye_exam, container, false);
        view.findViewById(R.id.Save_button).setOnClickListener(this);
        view.findViewById(R.id.jaundice_header).setOnClickListener(this);
        view.findViewById(R.id.pallor_header).setOnClickListener(this);
        view.findViewById(R.id.pupil_header).setOnClickListener(this);

        jaundice_body = (LinearLayout)view.findViewById(R.id.jaundice_body);
        pallor_body = (LinearLayout)view.findViewById(R.id.pallor_body);
        pupil_body = (LinearLayout)view.findViewById(R.id.pupil_body);

        jaundice_body.setVisibility(View.GONE);
        pallor_body.setVisibility(View.GONE);
        pupil_body.setVisibility(View.GONE);

        SpinnerUtility.SpinnerCreate(this,view,info,R.id.spinner_Jaundice,R.array.YesNoExamString,"Jaundice");
        SpinnerUtility.SpinnerCreate(this,view,info,R.id.spinner_EyePallor,R.array.YesNoExamString,"Pallor");
        SpinnerUtility.SpinnerCreate(this,view,info,R.id.spinner_Pupil,R.array.Pupil,"Pupil");

        return view;
    }
    public void onClick(View view)
    {
        PhysicalExamModule activity = (PhysicalExamModule) getActivity();
        int id=view.getId();

        switch(id)
        {
            case R.id.Save_button :
            {
                activity.communicate(Communicator.Response.CONTINUE, info);
                break;
            }

            case R.id.jaundice_header :
            {
                Toggle.toggle_contents(getActivity(),jaundice_body);
                break;
            }

            case R.id.pallor_header :
            {
                Toggle.toggle_contents(getActivity(),pallor_body);
                break;
            }

            case R.id.pupil_header :
            {
                Toggle.toggle_contents(getActivity(),pupil_body);
                break;
            }
        }

    }

}
