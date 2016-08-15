package toton.lazycoder.com.helloworld.Diagnosis;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import toton.lazycoder.com.helloworld.R;
import toton.lazycoder.com.helloworld.Utility.SpinnerUtility;


public class YellowUrine extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    JSONObject info;
    private static final String INFO = "INFO";

    EditText duration;
    EditText Colour;

    public YellowUrine() {
    }

    public static YellowUrine newInstance(String param1, String param2) {
        YellowUrine fragment = new YellowUrine();
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

        View view = inflater.inflate(R.layout.fragment_yellow_urine, container, false);
        view.findViewById(R.id.backButtonAbdominalPain).setOnClickListener(this);
        view.findViewById(R.id.continueButtonAbdominalPain).setOnClickListener(this);

        duration = (EditText)view.findViewById(R.id.editText_yellowUrineDuration);
        Colour = (EditText)view.findViewById(R.id.editText_yellowUrineStool);


        SpinnerUtility.SpinnerCreate(this,view,info,R.id.spinner_yellowUrinePain,R.array.YesNoString,"Abdominal pain");

        SpinnerUtility.SpinnerCreate(this,view,info,R.id.spinner_yellowUrineFever,R.array.YesNoString,"Fever");

        SpinnerUtility.SpinnerCreate(this,view,info,R.id.spinner_yellowUrineBurning,R.array.YesNoString,"Burning with urine");

        SpinnerUtility.SpinnerCreate(this,view,info,R.id.spinner_yellowUrineWeakness,R.array.YesNoString,"General weakness");


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
                    if (duration.getText().length() != 0)
                        info.put("Duration", duration.getText().toString() + "days");

                    if (Colour.getText().length() != 0)
                        info.put("Colour of stool", Colour.getText().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                activity.communicate(Communicator.Response.CONTINUE, info);
                break;

        }
    }
}
