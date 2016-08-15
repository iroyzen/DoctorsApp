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

import toton.lazycoder.com.helloworld.R;
import toton.lazycoder.com.helloworld.Utility.SpinnerUtility;

public class Fever extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    JSONObject info;
    JSONArray infoType;
    private static final String INFO = "INFO";

    EditText duration;
    EditText OtherType;
    EditText Pain;
    EditText OtherSym;
    EditText Start;
    EditText Relief;

    public Fever() {
        // Required empty public constructor
    }

    public static Fever newInstance(String param1, String param2) {
        Fever fragment = new Fever();
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
        View view = inflater.inflate(R.layout.fragment_fever, container, false);
        view.findViewById(R.id.backButtonAbdominalPain).setOnClickListener(this);
        view.findViewById(R.id.continueButtonAbdominalPain).setOnClickListener(this);
        view.findViewById(R.id.checkBox_feverAllDay).setOnClickListener(this);
        view.findViewById(R.id.checkBox_feverMorning).setOnClickListener(this);
        view.findViewById(R.id.checkBox_feverEvening).setOnClickListener(this);
        view.findViewById(R.id.checkBox_feverNight).setOnClickListener(this);
        view.findViewById(R.id.feverPainNo).setOnClickListener(this);

        duration = (EditText)view.findViewById(R.id.editText_feverDuration);
        OtherType = (EditText)view.findViewById(R.id.editText_feverOtherType);
        Pain = (EditText)view.findViewById(R.id.editText_feverPain);
        OtherSym = (EditText)view.findViewById(R.id.editText_feverOtherSymptom);
        Start = (EditText)view.findViewById(R.id.editText_feverStart);
        Relief = (EditText)view.findViewById(R.id.editText_feverRelief);

        SpinnerUtility.SpinnerCreate(this,view,info,R.id.spinner_feverNature,R.array.feverNature,"Felt on");

        SpinnerUtility.SpinnerCreate(this,view,info,R.id.spinner_feverHowMuch,R.array.feverHowMuch,"Grade");

        SpinnerUtility.SpinnerCreate(this,view,info,R.id.spinner_feverShivers,R.array.YesNoString,"Has Shivers");

        SpinnerUtility.SpinnerCreate(this,view,info,R.id.spinner_feverCough,R.array.YesNoString,"Cough");

        SpinnerUtility.SpinnerCreate(this,view,info,R.id.spinner_feverCoughBleed,R.array.YesNoString,"Haemoptysis");

        SpinnerUtility.SpinnerCreate(this,view,info,R.id.spinner_feverWeak,R.array.YesNoString,"General Weakness");

        SpinnerUtility.SpinnerCreate(this,view,info,R.id.spinner_feverWeight,R.array.YesNoString,"Weight Loss");

        SpinnerUtility.SpinnerCreate(this,view,info,R.id.spinner_feverUrine,R.array.YesNoString,"Dysuria");
;
        return view;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
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
                if (OtherType.getText().length() != 0) {
                    infoType.put(OtherType.getText().toString());
                }

                try {
                    if (duration.getText().length() != 0) {
                        info.put("Duration", duration.getText() + "days");
                    }
                    info.put("Present", infoType);

                    if(Pain.getText().length()!=0)
                    {
                        info.put("Pain", Pain.getText().toString());
                    }

                    if(OtherSym.getText().length()!=0)
                    {
                        info.put("Other Symptoms", OtherSym.getText().toString());
                    }

                    if(Start.getText().length()!=0)
                    {
                        info.put("Brought on by",Start.getText().toString());
                    }

                    if(Relief.getText().length()!=0)
                    {
                        info.put("Relieved by",Relief.getText().toString());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                activity.communicate(Communicator.Response.CONTINUE, info);
                break;

            case R.id.checkBox_feverAllDay:
                if (((CheckBox) view).isChecked()) {
                    infoType.put("All day");
                } else {
                    try {
                        for (int i = 0; i < infoType.length(); i++) {
                            if (infoType.get(i) == "All day") {
                                infoType.remove(i);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;

            case R.id.checkBox_feverMorning:
                if (((CheckBox) view).isChecked()) {
                    infoType.put("Morning");
                } else {
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

            case R.id.checkBox_feverEvening:
                if (((CheckBox) view).isChecked()) {
                    infoType.put("Evening");
                } else {
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

            case R.id.checkBox_feverNight:
                if (((CheckBox) view).isChecked()) {
                    infoType.put("Night");
                } else {
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

            case R.id.feverPainNo:
                try{
                    info.put("Pain", "No");
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
                break;
        }
    }

}
