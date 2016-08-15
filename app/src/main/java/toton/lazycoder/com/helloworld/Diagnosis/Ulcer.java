package toton.lazycoder.com.helloworld.Diagnosis;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import toton.lazycoder.com.helloworld.R;

public class Ulcer extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    JSONObject info;
    JSONArray infoSub;
    private static final String INFO = "INFO";

    EditText duration;
    EditText Position;
    EditText OtherStart;
    EditText Pain;

    public Ulcer() {
        // Required empty public constructor
    }

    public static Ulcer newInstance(String param1, String param2) {
        Ulcer fragment = new Ulcer();
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
        infoSub = new JSONArray();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_ulcer, container, false);
        view.findViewById(R.id.backButtonAbdominalPain).setOnClickListener(this);
        view.findViewById(R.id.continueButtonAbdominalPain).setOnClickListener(this);
        view.findViewById(R.id.checkBox_ulcerInjury).setOnClickListener(this);
        view.findViewById(R.id.checkBox_UlcerOnItsOwn).setOnClickListener(this);
        view.findViewById(R.id.checkBox_ulcerOtherStart).setOnClickListener(this);
        view.findViewById(R.id.checkBox_ulcerInjury).setOnClickListener(this);
        view.findViewById(R.id.checkBox_ulcerPainNo).setOnClickListener(this);
        view.findViewById(R.id.checkBox_ulcerPainYes).setOnClickListener(this);

        duration = (EditText)view.findViewById(R.id.editText_ulcerDuration);
        Position = (EditText)view.findViewById(R.id.editText_ulcerPos);
        OtherStart = (EditText)view.findViewById(R.id.editText_ulcerOtherStart);
        Pain = (EditText)view.findViewById(R.id.editText_ulcerPain);

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
                if (OtherStart.getText().length() != 0) {
                    infoSub.put(OtherStart.getText().toString());
                }

                try {
                    if (duration.getText().length() != 0)
                        info.put("Duration", duration.getText() + "days");

                    if (Position.getText().length() != 0)
                        info.put("Situated in", Position.getText().toString());

                    info.put("Started by", infoSub);

                    if (((CheckBox) view.findViewById(R.id.checkBox_ulcerPainYes)).isChecked()) {
                        if (Pain.getText().length() != 0) {
                            info.put("Pain", "Painful and " + Pain.getText().toString() + "in nature");
                        } else {
                            info.put("Pain", "Painful");
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                activity.communicate(Communicator.Response.CONTINUE, info);
                break;

            case R.id.checkBox_ulcerPainNo:
                if (((CheckBox) view).isChecked()) {
                    try {
                        info.put("Pain", "No");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            case R.id.checkBox_ulcerInjury:
                if (((CheckBox) view).isChecked()) {
                    infoSub.put("Injury");
                } else {
                    removeElementArray(infoSub, "Injury");
                }
                break;

            case R.id.checkBox_UlcerOnItsOwn:
                if (((CheckBox) view).isChecked()) {
                    infoSub.put("On its own");
                } else {
                    removeElementArray(infoSub, "On its own");
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
