package toton.lazycoder.com.helloworld.PhysicalExam;


import android.app.FragmentManager;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import toton.lazycoder.com.helloworld.Diagnosis.Communicator;
import toton.lazycoder.com.helloworld.PhysicalExamModule;
import toton.lazycoder.com.helloworld.R;
import toton.lazycoder.com.helloworld.Utility.DialogUtility;
import toton.lazycoder.com.helloworld.Utility.MultiSelectSpinnerUtility;
import toton.lazycoder.com.helloworld.Utility.SpinnerUtility;

public class AbdomenExam extends Fragment  implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    JSONObject info;
    private static final String INFO = "INFO";
    TextView Text1;
    TextView Text2;
    TextView Text3;
    public AbdomenExam() {
        // Required empty public constructor
    }

    public static AbdomenExam newInstance(String param1, String param2) {
        AbdomenExam fragment = new AbdomenExam();
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
        View view = inflater.inflate(R.layout.fragment_abdomen_exam, container, false);
        view.findViewById(R.id.Save_button).setOnClickListener(this);
        view.findViewById(R.id.spinner_LumpShape_test).setOnClickListener(this);
        Text1 = (TextView) view.findViewById(R.id.multiSelect_LumpPosition);
        Text1.setOnClickListener(this);

        Text2 = (TextView) view.findViewById(R.id.multiSelect_ScarPosition);
        Text2.setOnClickListener(this);

        Text3 = (TextView) view.findViewById(R.id.multiSelect_TendernessPosition);
        Text3.setOnClickListener(this);

        SpinnerUtility.SpinnerCreate(this,view,info,R.id.spinner_VisibleScars,R.array.YesNoExamString,"Visible Scars");
        SpinnerUtility.SpinnerCreate(this,view,info,R.id.spinner_Distention,R.array.YesNoExamString,"Distention");
        SpinnerUtility.SpinnerCreate(this,view,info,R.id.spinner_AbdomenTenderness,R.array.YesNoExamString,"Tenderness");
        SpinnerUtility.SpinnerCreate(this,view,info,R.id.spinner_abdomenLumps,R.array.YesNoExamString,"Lumps");
        SpinnerUtility.SpinnerCreate(this,view,info,R.id.spinner_LumpShape,R.array.LumpShape,"Lumps Shape");
        SpinnerUtility.SpinnerCreate(this,view,info,R.id.spinner_LumpSurface,R.array.LumpSurface,"Lump Surface");
        SpinnerUtility.SpinnerCreate(this,view,info,R.id.spinner_LumpFeel,R.array.LumpFeel,"Feels");
        SpinnerUtility.SpinnerCreate(this,view,info,R.id.spinner_LumpPain,R.array.YesNoExamString,"Lump Painful");
        SpinnerUtility.SpinnerCreate(this,view,info,R.id.spinner_LumpSkin,R.array.YesNoExamString,"Change in skin Colour");
        SpinnerUtility.SpinnerCreate(this,view,info,R.id.spinner_LumpSwell,R.array.LumpSwell,"Swelling");
        SpinnerUtility.SpinnerCreate(this,view,info,R.id.spinner_LumpMove,R.array.LumpMove,"Movement");
        SpinnerUtility.SpinnerCreate(this,view,info,R.id.spinner_LumpSize,R.array.LumpSize,"Size on cough");
        SpinnerUtility.SpinnerCreate(this,view,info,R.id.spinner_LumpLieSize,R.array.LumpLieSize,"Size on lying down");

        try
        {
            if((info.getJSONArray("Lump Position")).length()!=0)
            {
                Text1.setText("[ Selected ]");
            }
            if((info.getJSONArray("Scar Position")).length()!=0)
            {
                Text2.setText("[ Selected ]");
            }
            if((info.getJSONArray("Tenderness Position")).length()!=0)
            {
                Text3.setText("[ Selected ]");
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }

        return view;
    }

    public void onClick(View view)
    {
        FragmentManager fm = getFragmentManager();
        Communicator activity = (Communicator) getActivity();
        if(view.getId()==R.id.Save_button)
        {
            activity.communicate(Communicator.Response.CONTINUE, info);
        }
        if(view.getId()==R.id.multiSelect_LumpPosition)
        {
            MultiSelectSpinnerUtility.showMultiSelectSpinner(getActivity(),info,"Lump Position","Lump Position",Text1, R.array.Position,10,9,-1);
        }
        if(view.getId()==R.id.multiSelect_ScarPosition)
        {
            MultiSelectSpinnerUtility.showMultiSelectSpinner(getActivity(),info,"Scar Position","Scar Position",Text2, R.array.Position,10,9,-1);
        }
        if(view.getId()==R.id.multiSelect_TendernessPosition)
        {
            MultiSelectSpinnerUtility.showMultiSelectSpinner(getActivity(),info,"Tenderness Position","Tenderness Position",Text3, R.array.Position,10,9,-1);
        }
    }
}
