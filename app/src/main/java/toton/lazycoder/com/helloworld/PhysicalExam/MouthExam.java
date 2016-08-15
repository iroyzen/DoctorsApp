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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MouthExam#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MouthExam extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    JSONObject info;
    private static final String INFO = "INFO";

    public MouthExam() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MouthExam.
     */
    // TODO: Rename and change types and number of parameters
    public static MouthExam newInstance(String param1, String param2) {
        MouthExam fragment = new MouthExam();
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
        View view = inflater.inflate(R.layout.fragment_mouth_exam, container, false);
        view.findViewById(R.id.Save_button).setOnClickListener(this);

        SpinnerUtility.SpinnerCreate(this,view,info,R.id.spinner_TongueDry,R.array.YesNoExamString,"Tongue Dry");
        SpinnerUtility.SpinnerCreate(this,view,info,R.id.spinner_TeethDiscolour,R.array.Teeth,"Teeth Discoloration");
        SpinnerUtility.SpinnerCreate(this,view,info,R.id.spinner_SwellingGum,R.array.YesNoExamString,"Swelling GUm");
        SpinnerUtility.SpinnerCreate(this,view,info,R.id.spinner_ThroatRedness,R.array.ThroatRedness,"Throat Redness/Swelling");

        return view;
    }

    public void onClick(View view)
    {
        PhysicalExamModule activity = (PhysicalExamModule) getActivity();
        if(view.getId()==R.id.Save_button)
        {
            activity.communicate(Communicator.Response.CONTINUE, info);
        }
    }

}
