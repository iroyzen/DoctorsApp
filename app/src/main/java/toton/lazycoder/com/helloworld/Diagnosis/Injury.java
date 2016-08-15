package toton.lazycoder.com.helloworld.Diagnosis;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import toton.lazycoder.com.helloworld.ComplainModule;
import toton.lazycoder.com.helloworld.R;
import toton.lazycoder.com.helloworld.Utility.SpinnerUtility;

public class Injury extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    JSONObject info;
    private static final String INFO = "INFO";

    JSONArray infoReason;
    JSONArray infoProblem;

    EditText Position;
    EditText When;
    EditText Height;
    EditText Cut;
    EditText Violence;
    EditText OtherReason;
    EditText CantMove;

    public Injury() {
    }

    public static Injury newInstance(String param1, String param2) {
        Injury fragment = new Injury();
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
        infoReason = new JSONArray();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_injury, container, false);
        view.findViewById(R.id.backButtonAbdominalPain).setOnClickListener(this);
        view.findViewById(R.id.continueButtonAbdominalPain).setOnClickListener(this);
        view.findViewById(R.id.checkBox_injuryFallHome).setOnClickListener(this);
        view.findViewById(R.id.checkBox_injuryFallRoad).setOnClickListener(this);
        view.findViewById(R.id.checkBox_injuryFallHeight).setOnClickListener(this);
        view.findViewById(R.id.checkBox_injuryHitCar).setOnClickListener(this);
        view.findViewById(R.id.checkBox_injuryHitBike).setOnClickListener(this);
        view.findViewById(R.id.checkBox_injuryHitCycle).setOnClickListener(this);
        view.findViewById(R.id.checkBox_injuryCrushedMachine).setOnClickListener(this);
        view.findViewById(R.id.checkBox_injuryCut).setOnClickListener(this);
        view.findViewById(R.id.checkBox_injuryViolence).setOnClickListener(this);
        view.findViewById(R.id.checkBox_injuryCantWalk).setOnClickListener(this);
        view.findViewById(R.id.checkBox_injuryCantMove).setOnClickListener(this);
        view.findViewById(R.id.checkBox_injuryPain).setOnClickListener(this);

        Position = (EditText)view.findViewById(R.id.editText_injuryPos);
        When = (EditText)view.findViewById(R.id.editText_injuryWhen);
        Height = (EditText)view.findViewById(R.id.editText_injuryFallHeight);
        Cut = (EditText)view.findViewById(R.id.editText_injuryCut);
        Violence = (EditText)view.findViewById(R.id.editText_injuryViolence);
        OtherReason = (EditText)view.findViewById(R.id.editText_injuryOther);
        CantMove = (EditText)view.findViewById(R.id.editText_injuryCantMove);

        SpinnerUtility.SpinnerCreate(this,view,info,R.id.spinner_injuryBleed,R.array.YesNoString,"Felt on");

        return view;
    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onClick(View view) {

        Communicator activity = (Communicator) getActivity();
        switch (view.getId()) {
            case R.id.backButtonAbdominalPain:
                activity.communicate(Communicator.Response.BACK, null);
                break;

            case R.id.continueButtonAbdominalPain:

                try {
                    if (Position.getText().length() != 0) {
                        info.put("Position", Position.getText().toString());
                    }

                    if (When.getText().length() != 0) {
                        info.put("When", "sustained "+When.getText().toString()+"days ago");
                    }

                    if(((CheckBox)view.findViewById(R.id.checkBox_injuryFallHeight)).isChecked())
                    {
                        if(Height.getText().length()!=0) {
                            infoReason.put("Fall from height"+"("+Height.getText().toString()+")");
                        }
                        else {
                            infoReason.put("Fall from height");
                        }

                    }

                    if(((CheckBox)view.findViewById(R.id.checkBox_injuryCut)).isChecked())
                    {
                        if(Cut.getText().length()!=0) {
                            infoReason.put("Cut"+"("+Cut.getText().toString()+")");
                        }
                        else {
                            infoReason.put("Cut");
                        }

                    }

                    if(((CheckBox)view.findViewById(R.id.checkBox_injuryViolence)).isChecked())
                    {
                        if(Violence.getText().length()!=0) {
                            infoReason.put("Violence"+"("+Violence.getText().toString()+")");
                        }
                        else {
                            infoReason.put("Violence");
                        }

                    }

                    if(OtherReason.getText().length()!=0) {
                        infoReason.put(Violence.getText().toString());
                    }

                    info.put("Due to", infoReason);

                    if(((CheckBox)view.findViewById(R.id.checkBox_injuryCantMove)).isChecked())
                    {
                        if(Violence.getText().length()!=0) {
                            infoProblem.put("Cant move"+"("+CantMove.getText().toString()+")");
                        }
                        else {
                            infoProblem.put("Cant move");
                        }

                    }

                    info.put("Problem",infoProblem);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                activity.communicate(Communicator.Response.CONTINUE, info);
                break;

            case R.id.checkBox_injuryFallHome:
                if (((CheckBox) view).isChecked()) {
                    infoReason.put("Fall at home");
                } else {
                    removeElementArray(infoReason,"Fall at home");
                }
                break;

            case R.id.checkBox_injuryFallRoad:
                if (((CheckBox) view).isChecked()) {
                    infoReason.put("Fall on road");
                } else {
                    removeElementArray(infoReason,"Fall on road");
                }
                break;

            case R.id.checkBox_injuryHitCar:
                if (((CheckBox) view).isChecked()) {
                    infoReason.put("Hit by car");
                } else {
                    removeElementArray(infoReason,"Hit by car");
                }
                break;

            case R.id.checkBox_injuryHitBike:
                if (((CheckBox) view).isChecked()) {
                    infoReason.put("Hit by bike");
                } else {
                    removeElementArray(infoReason,"Hit by bike");
                }
                break;

            case R.id.checkBox_injuryHitCycle:
                if (((CheckBox) view).isChecked()) {
                    infoReason.put("Hit by cycle");
                } else {
                    removeElementArray(infoReason,"Hit by cycle");
                }
                break;

            case R.id.checkBox_injuryCrushedMachine:
                if (((CheckBox) view).isChecked()) {
                    infoReason.put("Crushed by machine");
                } else {
                    removeElementArray(infoReason,"Crushed by machine");
                }
                break;

            case R.id.checkBox_injuryCantWalk:
                if (((CheckBox) view).isChecked()) {
                    infoProblem.put("Cant Walk");
                } else {
                    removeElementArray(infoProblem,"Cant Walk");
                }
                break;

            case R.id.checkBox_injuryPain:
                if (((CheckBox) view).isChecked()) {
                    infoProblem.put("Pain");
                } else {
                    removeElementArray(infoProblem,"Pain");
                }
                break;




        }
    }

    void removeElementArray(final JSONArray arr,String value)
    {
        try {
            for (int i = 0; i <arr.length(); i++) {
                if (arr.get(i).equals(value)) {
                    arr.remove(i);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
