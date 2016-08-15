package toton.lazycoder.com.helloworld.Diagnosis;

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


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Diarrhoea.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Diarrhoea#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Diarrhoea extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    JSONObject info;
    JSONArray infoSub;
    private static final String INFO = "INFO";

    EditText duration;
    EditText OtherStool;
    EditText frequency;
    EditText OtherSym;
    EditText medication;

    public Diarrhoea() {
        // Required empty public constructor
    }

    public static Diarrhoea newInstance(String param1, String param2) {
        Diarrhoea fragment = new Diarrhoea();
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
        try {
            if (savedInstanceState != null && savedInstanceState.containsKey(INFO)) {
                info = new JSONObject(savedInstanceState.getString(INFO));
            } else if (getArguments() != null && getArguments().containsKey("INFO")) {
                info = new JSONObject(getArguments().getString(INFO));
            } else {
                info = new JSONObject();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        infoSub = new JSONArray();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_diarrhoea, container, false);
        view.findViewById(R.id.backButtonAbdominalPain).setOnClickListener(this);
        view.findViewById(R.id.continueButtonAbdominalPain).setOnClickListener(this);
        view.findViewById(R.id.checkBox_diarrhoeaWatery).setOnClickListener(this);
        view.findViewById(R.id.checkBox_diarrhoeaSoft).setOnClickListener(this);
        view.findViewById(R.id.checkBox_diarrhoeaIllFormed).setOnClickListener(this);
        view.findViewById(R.id.checkBox_diarrhoeaPain).setOnClickListener(this);
        view.findViewById(R.id.checkBox_diarrhoeaFever).setOnClickListener(this);
        view.findViewById(R.id.checkBox_diarrhoeaVomit).setOnClickListener(this);

        duration = (EditText) view.findViewById(R.id.editText_diarrhoeaDuration);
        OtherStool = (EditText) view.findViewById(R.id.editText_diarrhoeaStoolOther);
        frequency = (EditText) view.findViewById(R.id.editText_diarrhoeaFrequency);
        medication = (EditText) view.findViewById(R.id.editText_diarrhoeaMedication);

        SpinnerUtility.SpinnerCreate(this, view, info, R.id.spinner_diarrhoeaNature, R.array.diarrhoeaNature, "Nature");

        SpinnerUtility.SpinnerCreate(this, view, info, R.id.spinner_diarrhoeaBlood, R.array.YesNoString, "Blood");

        SpinnerUtility.SpinnerCreate(this, view, info, R.id.spinner_diarrhoeaFood, R.array.YesNoString, "Related with food");

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
                if (OtherSym.getText() != null) {
                    infoSub.put(OtherSym.getText());
                }

                try {
                    info.put("Duration: ", duration.getText() + "days");
                    info.put("Other Symptoms", infoSub);
                    if (OtherStool.getText() != null) {
                        info.put("Stool type", OtherStool.getText());
                    }
                    if (frequency.getText() != null) {
                        info.put("Frequency", frequency.getText());
                    }
                    if(OtherStool.getText()!=null)
                    {
                        info.put("Stool type",OtherStool.getText());
                    }
                    if(medication.getText()!=null)
                    {
                        info.put("Medication",medication.getText());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                activity.communicate(Communicator.Response.CONTINUE, info);
                break;

            case R.id.checkBox_diarrhoeaWatery:
                if (((CheckBox) view).isChecked())
                    try {
                        info.put("Stool type", "Watery");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                break;

            case R.id.checkBox_diarrhoeaSoft:
                if (((CheckBox) view).isChecked())
                    try {
                        info.put("Stool type", "Soft");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                break;

            case R.id.checkBox_diarrhoeaIllFormed:
                if (((CheckBox) view).isChecked())
                    try {
                        info.put("Stool type", "Ill Formed");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                break;

            case R.id.checkBox_diarrhoeaPain:
                if (((CheckBox) view).isChecked()) {
                    infoSub.put("Abdominal Pain");
                } else {
                    try {
                        for (int i = 0; i < infoSub.length(); i++) {
                            if (infoSub.get(i) == "Abdominal Pain") {
                                infoSub.remove(i);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;

            case R.id.checkBox_diarrhoeaFever:
                if (((CheckBox) view).isChecked()) {
                    infoSub.put("Fever");
                } else {
                    try {
                        for (int i = 0; i < infoSub.length(); i++) {
                            if (infoSub.get(i) == "Fever") {
                                infoSub.remove(i);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;

            case R.id.checkBox_diarrhoeaVomit:
                if (((CheckBox) view).isChecked()) {
                    infoSub.put("Vomiting");
                } else {
                    try {
                        for (int i = 0; i < infoSub.length(); i++) {
                            if (infoSub.get(i) == "Vomiting") {
                                infoSub.remove(i);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
        }


    }
}
