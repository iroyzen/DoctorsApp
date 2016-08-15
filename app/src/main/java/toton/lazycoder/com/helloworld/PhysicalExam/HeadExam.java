package toton.lazycoder.com.helloworld.PhysicalExam;


import android.app.FragmentManager;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import toton.lazycoder.com.helloworld.Diagnosis.Communicator;
import toton.lazycoder.com.helloworld.PhysicalExamModule;
import toton.lazycoder.com.helloworld.R;
import toton.lazycoder.com.helloworld.Utility.MultiSelectSpinnerUtility;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HeadExam#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HeadExam extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    JSONObject info;
    private static final String INFO = "INFO";

    TextView Text1;
    TextView Text2;

    public HeadExam() {
        // Required empty public constructor
    }

    public static HeadExam newInstance(String param1, String param2) {
        HeadExam fragment = new HeadExam();
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
        View view = inflater.inflate(R.layout.fragment_head_exam, container, false);
        view.findViewById(R.id.Save_button).setOnClickListener(this);
        Text1 = (TextView) view.findViewById(R.id.multiSelect_HeadInjury);
        Text1.setOnClickListener(this);

        Text2 = (TextView) view.findViewById(R.id.multiSelect_HeadSwelling);
        Text2.setOnClickListener(this);


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
        }else if(view.getId() == R.id.multiSelect_HeadInjury)
        {
            MultiSelectSpinnerUtility.showMultiSelectSpinner(getActivity(),info,"Head Injury","Head Injury",Text1, R.array.HeadInjury,2,-1,-1);

        }else if(view.getId() == R.id.multiSelect_HeadSwelling)
        {
            MultiSelectSpinnerUtility.showMultiSelectSpinner(getActivity(),info,"Head Swelling","Head Swelling",Text2, R.array.HeadSwelling,2,-1,-1);
        }
    }

}
