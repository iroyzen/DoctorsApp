package toton.lazycoder.com.helloworld.Diagnosis;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import toton.lazycoder.com.helloworld.ComplainModule;
import toton.lazycoder.com.helloworld.R;


public class Boils extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    JSONObject info;
    JSONArray infoSub;
    private static final String INFO = "INFO";

    EditText duration;
    EditText position;
    EditText OtherStart;
    EditText Pain;

    public Boils() {
        // Required empty public constructor
    }

    public static Boils newInstance(String param1, String param2) {
        Boils fragment = new Boils();
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

        View view = inflater.inflate(R.layout.fragment_boils, container, false);
        view.findViewById(R.id.backButtonAbdominalPain).setOnClickListener(this);
        view.findViewById(R.id.continueButtonAbdominalPain).setOnClickListener(this);

        duration=(EditText)view.findViewById(R.id.editText_boilsDuration);
        position=(EditText)view.findViewById(R.id.editText_boilsPos);
        OtherStart=(EditText)view.findViewById(R.id.editText_boilsOtherStart);
        Pain=(EditText)view.findViewById(R.id.editText_boilsPain);

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
                if (OtherStart.getText() != null) {
                    infoSub.put(OtherStart.getText());
                }

                try {
                    if (duration.getText() != null)
                        info.put("Duration: ", duration.getText() + "days");

                    info.put("Position:", position.getText());
                    info.put("Started by",infoSub);
                    if(Pain.getText()!=null)
                    {
                        info.put("Pain",Pain.getText());
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                activity.communicate(Communicator.Response.CONTINUE, info);
                break;

            case R.id.checkBox_boilsInjury:
                if (((CheckBox) view).isChecked()) {
                    infoSub.put("Injury");
                } else {
                    try {
                        for (int i = 0; i < infoSub.length(); i++) {
                            if (infoSub.get(i) == "Injury") {
                                infoSub.remove(i);
                                break;
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;

            case R.id.checkBox_boilsOnItsOwn:
                if (((CheckBox) view).isChecked()) {
                    infoSub.put("On its own");
                } else {
                    try {
                        for (int i = 0; i < infoSub.length(); i++) {
                            if (infoSub.get(i) == "On its own") {
                                infoSub.remove(i);
                                break;
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;

            case R.id.checkBox_boilsPainNo:
                try {
                    info.put("Pain", "No");
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
        }
    }


}
