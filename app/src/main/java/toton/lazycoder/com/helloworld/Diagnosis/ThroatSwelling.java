package toton.lazycoder.com.helloworld.Diagnosis;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;
import toton.lazycoder.com.helloworld.R;
import toton.lazycoder.com.helloworld.Utility.SpinnerUtility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



public class ThroatSwelling extends Fragment implements View.OnClickListener {

    static final String ARG_PARAM1 = "param1";
    static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    JSONObject info;
    JSONArray infoSub;
    private static final String INFO = "INFO";

    EditText OtherSym;
    EditText duration;

    public ThroatSwelling() {
    }

    public static ThroatSwelling newInstance(String param1, String param2) {
        ThroatSwelling fragment = new ThroatSwelling();
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

        View view = inflater.inflate(R.layout.fragment_throat_swelling, container, false);
        view.findViewById(R.id.backButtonAbdominalPain).setOnClickListener(this);
        view.findViewById(R.id.continueButtonAbdominalPain).setOnClickListener(this);
        view.findViewById(R.id.SingleSwellThroat).setOnClickListener(this);
        view.findViewById(R.id.MultipleSwellThroat).setOnClickListener(this);
        view.findViewById(R.id.throatSwellDiffMov).setOnClickListener(this);
        view.findViewById(R.id.throatSwellDiffWork).setOnClickListener(this);
        ;

        OtherSym = (EditText) view.findViewById(R.id.editText_throatSwellOtherSymp);
        duration = (EditText) view.findViewById(R.id.editText_throatSwellDuration);

        SpinnerUtility.SpinnerCreate(this, view, info, R.id.spinner_throatSwellPos, R.array.throatSwellPos, "Position");

        SpinnerUtility.SpinnerCreate(this, view, info, R.id.spinner_throatSwellStart, R.array.throatSwellStart, "Start");

        SpinnerUtility.SpinnerCreate(this, view, info, R.id.spinner_throatSwellPain, R.array.YesNoString, "Painful");

        SpinnerUtility.SpinnerCreate(this, view, info, R.id.spinner_throatSwellSize, R.array.throatSwellSize, "Change in size");

        SpinnerUtility.SpinnerCreate(this, view, info, R.id.spinner_throatSwellSkin, R.array.throatSwellSkin, "Skin colour");

        SpinnerUtility.SpinnerCreate(this, view, info, R.id.spinner_throatSwellHistory, R.array.YesNoString, "H/O Injury");

        SpinnerUtility.SpinnerCreate(this, view, info, R.id.spinner_throatSwellSweat, R.array.YesNoString, "Excessive sweating");

        SpinnerUtility.SpinnerCreate(this, view, info, R.id.spinner_throatSwellConstipation, R.array.YesNoString, "Constipation");

        SpinnerUtility.SpinnerCreate(this, view, info, R.id.spinner_throatSwellPeriod, R.array.YesNoString, "Period problem");

        SpinnerUtility.SpinnerCreate(this, view, info, R.id.spinner_throatSwellAppetite, R.array.YesNoString, "Excessive appetite");

        SpinnerUtility.SpinnerCreate(this, view, info, R.id.spinner_throatSwellWeight, R.array.YesNoString, "Change in weight");

        SpinnerUtility.SpinnerCreate(this, view, info, R.id.spinner_throatSwellWeak, R.array.YesNoString, "General weakness");

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
                    infoSub.put(OtherSym.getText().toString());
                }

                try {
                    info.put("Duration", duration.getText().toString() + "days");
                    info.put("Other Symptoms", infoSub);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                activity.communicate(Communicator.Response.CONTINUE, info);
                break;

            case R.id.SingleSwellThroat:
                try {
                    info.put("No. of Swellings", "Single");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case R.id.MultipleSwellThroat:
                try {
                    info.put("No. of Swellings", "Multiple");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case R.id.throatSwellDiffMov:
                if (((CheckBox) view).isChecked()) {
                    infoSub.put("Difficulty in Movement");
                } else {
                    try {
                        for (int i = 0; i < infoSub.length(); i++) {
                            if (infoSub.get(i) == "Difficulty in Movement") {
                                infoSub.remove(i);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;

            case R.id.throatSwellDiffWork:
                if (((CheckBox) view).isChecked()) {
                    infoSub.put("Difficulty in Work");
                } else {
                    try {
                        for (int i = 0; i < infoSub.length(); i++) {
                            if (infoSub.get(i) == "Difficulty in Work") {
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
