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

import org.json.JSONException;
import org.json.JSONObject;

import toton.lazycoder.com.helloworld.R;
import toton.lazycoder.com.helloworld.Utility.SpinnerUtility;

public class Fainting extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    JSONObject info;
    private static final String INFO = "INFO";

    EditText Episodes;
    EditText Interval;
    EditText HowLong;
    EditText Fits;
    EditText Start;
    EditText Relief;

    public Fainting() {

    }

    public static Fainting newInstance(String param1, String param2) {
        Fainting fragment = new Fainting();
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

        View view = inflater.inflate(R.layout.fragment_fainting, container, false);
        view.findViewById(R.id.backButtonAbdominalPain).setOnClickListener(this);
        view.findViewById(R.id.continueButtonAbdominalPain).setOnClickListener(this);
        view.findViewById(R.id.checkBox_faintingUnconsciousNo).setOnClickListener(this);
        view.findViewById(R.id.checkBox_faintingFitsNo).setOnClickListener(this);

        Episodes = (EditText)view.findViewById(R.id.editText_faintingEpisodes);
        Interval = (EditText)view.findViewById(R.id.editText_faintingInterval);
        HowLong = (EditText)view.findViewById(R.id.editText_faintingUnconsciousDuration);
        Fits = (EditText)view.findViewById(R.id.editText_faintingFits);
        Start = (EditText)view.findViewById(R.id.editText_faintingStart);
        Relief = (EditText)view.findViewById(R.id.editText_faintingRelief);

        SpinnerUtility.SpinnerCreate(this,view,info,R.id.spinner_faintingFall,R.array.YesNoString,"Had a fall");

        SpinnerUtility.SpinnerCreate(this,view,info,R.id.spinner_faintingDizziness,R.array.YesNoString,"Dizziness seen");

        return view;
    }

    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onClick(View view) {

        Communicator activity = (Communicator) getActivity();
        switch(view.getId()) {
            case R.id.backButtonAbdominalPain:
                activity.communicate(Communicator.Response.BACK, null);
                break;

            case R.id.continueButtonAbdominalPain:

                try {
                    if(Episodes.getText().length()!=0)
                        info.put("Episodes", Episodes.getText().toString());

                    if(Interval.getText().length()!=0)
                       info.put("Interval between episodes", Interval.getText().toString());

                    if(((CheckBox)view.findViewById(R.id.checkBox_faintingUnconsciousYes)).isChecked())
                    {
                        if(HowLong.getText().length()!=0) {
                            info.put("Conscious Lost","for"+HowLong.getText().toString());
                        }
                        else {
                            info.put("Conscious Lost", "Yes");
                        }

                    }
                    if(((CheckBox)view.findViewById(R.id.checkBox_faintingFitsYes)).isChecked())
                    {
                        if(Fits.getText().length()!=0) {
                            info.put("Fits","Yes"+"(was witnessed)");
                        }
                        else {
                            info.put("Fits", "Yes");
                        }

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

            case R.id.checkBox_faintingFitsNo:
                try {
                    info.put("Fits", "No");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case R.id.checkBox_faintingUnconsciousNo:
                try {
                    info.put("Conscious Lost", "No");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }


}
