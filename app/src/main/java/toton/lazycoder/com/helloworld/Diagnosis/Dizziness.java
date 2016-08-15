package toton.lazycoder.com.helloworld.Diagnosis;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import toton.lazycoder.com.helloworld.R;
import toton.lazycoder.com.helloworld.Utility.SpinnerUtility;


public class Dizziness extends Fragment implements View.OnClickListener {

    static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    JSONObject info;
    private static final String INFO = "INFO";
    JSONArray infoType;
    JSONArray infoPos;
    JSONArray infoSym;

    EditText duration;
    EditText Type;
    EditText Start;
    EditText Relief;
    EditText Position;
    EditText OtherSym;

    public Dizziness() {
        // Required empty public constructor
    }

    public static Dizziness newInstance(String param1, String param2) {
        Dizziness fragment = new Dizziness();
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

        View view = inflater.inflate(R.layout.fragment_dizziness, container, false);
        view.findViewById(R.id.backButtonAbdominalPain).setOnClickListener(this);
        view.findViewById(R.id.continueButtonAbdominalPain).setOnClickListener(this);
        view.findViewById(R.id.checkBox_dizzinessDay).setOnClickListener(this);
        view.findViewById(R.id.checkBox_dizzinessMorning).setOnClickListener(this);
        view.findViewById(R.id.checkBox_dizzinessEvening).setOnClickListener(this);
        view.findViewById(R.id.checkBox_dizzinessNight).setOnClickListener(this);
        view.findViewById(R.id.checkBox_dizzinessLying).setOnClickListener(this);
        view.findViewById(R.id.checkBox_dizzinessStanding).setOnClickListener(this);
        view.findViewById(R.id.checkBox_dizzinessNeck).setOnClickListener(this);
        view.findViewById(R.id.checkBox_dizzinessEye).setOnClickListener(this);
        view.findViewById(R.id.checkBox_dizzinessVomiting).setOnClickListener(this);
        view.findViewById(R.id.checkBox_dizzinessChest).setOnClickListener(this);
        view.findViewById(R.id.checkBox_dizzinessBreath).setOnClickListener(this);
        view.findViewById(R.id.checkBox_dizzinessEar).setOnClickListener(this);
        view.findViewById(R.id.checkBox_dizzinessDay).setOnClickListener(this);

        OtherSym = (EditText)view.findViewById(R.id.editText_dizzinessOtherSymptom);
        duration = (EditText)view.findViewById(R.id.editText_dizzinessDuration);
        Type = (EditText)view.findViewById(R.id.editText_dizzinessOtherType);
        Start = (EditText)view.findViewById(R.id.editText_dizzinessStart);
        Relief = (EditText)view.findViewById(R.id.editText_dizzinessRelief);
        Position = (EditText)view.findViewById(R.id.editText_dizzinessOtherRelation);

        SpinnerUtility.SpinnerCreate(this,view,info,R.id.spinner_dizzinessNature,R.array.dizzinessNature,"Occurs");

        SpinnerUtility.SpinnerCreate(this,view,info,R.id.spinner_dizzinessFainting,R.array.YesNoString,"H/O Fainting");

        SpinnerUtility.SpinnerCreate(this,view,info,R.id.spinner_dizzinessFall,R.array.YesNoString,"H/O Fall");

        SpinnerUtility.SpinnerCreate(this,view,info,R.id.spinner_dizzinessVision,R.array.dizzinessVision,"Vision");

        SpinnerUtility.SpinnerCreate(this,view,info,R.id.spinner_dizzinessHearing,R.array.dizzinessHearing,"Hearing");

        return view;
    }

    @Override
    public void onClick(View view) {

        Communicator activity = (Communicator) getActivity();
        switch (view.getId()) {
            case R.id.backButtonAbdominalPain:
                activity.communicate(Communicator.Response.BACK, null);
                break;

            case R.id.continueButtonAbdominalPain:
                if (OtherSym.getText().length() != 0) {
                    infoSym.put(OtherSym.getText().toString());
                }
                if (Type.getText().length() != 0) {
                    infoType.put(Type.getText().toString());
                }
                if (Position.getText().length() != 0) {
                    infoPos.put(Position.getText().toString());
                }

                try {
                    info.put("Duration", duration.getText().toString() + "days");
                    info.put("Other Symptoms", infoSym);
                    info.put("Felt during", infoType);
                    info.put("Related with", infoPos);
                    if(Start.getText().length() != 0)
                    {
                        info.put("Brought on by", Start.getText().toString());
                    }
                    if(Relief.getText().length() != 0)
                    {
                        info.put("Brought on by", Relief.getText().toString());
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                activity.communicate(Communicator.Response.CONTINUE, info);
                break;

            case R.id.checkBox_dizzinessDay:
                if(((CheckBox)view).isChecked())
                {
                    infoType.put("Whole Day");
                }
                else {
                    try {
                        for (int i = 0; i < infoType.length(); i++) {
                            if (infoType.get(i) == "Whole Day") {
                                infoType.remove(i);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;

            case R.id.checkBox_dizzinessMorning:
                if(((CheckBox)view).isChecked())
                {
                    infoType.put("Morning");
                }
                else {
                    try {
                        for (int i = 0; i < infoType.length(); i++) {
                            if (infoType.get(i) == "Morning") {
                                infoType.remove(i);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;

            case R.id.checkBox_dizzinessEvening:
                if(((CheckBox)view).isChecked())
                {
                    infoType.put("Evening");
                }
                else {
                    try {
                        for (int i = 0; i < infoType.length(); i++) {
                            if (infoType.get(i) == "Evening") {
                                infoType.remove(i);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;

            case R.id.checkBox_dizzinessNight:
                if(((CheckBox)view).isChecked())
                {
                    infoType.put("Night");
                }
                else {
                    try {
                        for (int i = 0; i < infoType.length(); i++) {
                            if (infoType.get(i) == "Night") {
                                infoType.remove(i);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;

            case R.id.checkBox_dizzinessLying:
                if(((CheckBox)view).isChecked())
                {
                    infoPos.put("Lying Down");
                }
                else {
                    try {
                        for (int i = 0; i < infoPos.length(); i++) {
                            if (infoPos.get(i) == "Lying Down") {
                                infoPos.remove(i);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;

            case R.id.checkBox_dizzinessStanding:
                if(((CheckBox)view).isChecked())
                {
                    infoPos.put("Standing Up");
                }
                else {
                    try {
                        for (int i = 0; i < infoPos.length(); i++) {
                            if (infoPos.get(i) == "Standing Up") {
                                infoPos.remove(i);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;

            case R.id.checkBox_dizzinessNeck:
                if(((CheckBox)view).isChecked())
                {
                    infoPos.put("Moving Neck");
                }
                else {
                    try {
                        for (int i = 0; i < infoPos.length(); i++) {
                            if (infoPos.get(i) == "Moving Neck") {
                                infoPos.remove(i);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;

            case R.id.checkBox_dizzinessEye:
                if(((CheckBox)view).isChecked())
                {
                    infoPos.put("Opening Eyes");
                }
                else {
                    try {
                        for (int i = 0; i < infoPos.length(); i++) {
                            if (infoPos.get(i) == "Opening Eyes") {
                                infoPos.remove(i);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;

            case R.id.checkBox_dizzinessVomiting:
                if(((CheckBox)view).isChecked())
                {
                    infoSym.put("Vomiting");
                }
                else {
                    try {
                        for (int i = 0; i < infoSym.length(); i++) {
                            if (infoSym.get(i) == "Vomiting") {
                                infoSym.remove(i);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;

            case R.id.checkBox_dizzinessChest:
                if(((CheckBox)view).isChecked())
                {
                    infoSym.put("Chest Pain");
                }
                else {
                    try {
                        for (int i = 0; i < infoSym.length(); i++) {
                            if (infoSym.get(i) == "Chest Pain") {
                                infoSym.remove(i);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;

            case R.id.checkBox_dizzinessEar:
                if(((CheckBox)view).isChecked())
                {
                    infoSym.put("Pain in the ear");
                }
                else {
                    try {
                        for (int i = 0; i < infoSym.length(); i++) {
                            if (infoSym.get(i) == "Pain in the ear") {
                                infoSym.remove(i);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;

            case R.id.checkBox_dizzinessBreath:
                if(((CheckBox)view).isChecked())
                {
                    infoSym.put("Breathlessness");
                }
                else {
                    try {
                        for (int i = 0; i < infoSym.length(); i++) {
                            if (infoSym.get(i) == "Breathlessness") {
                                infoSym.remove(i);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


}
