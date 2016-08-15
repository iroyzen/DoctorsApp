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

import org.json.JSONException;
import org.json.JSONObject;

import toton.lazycoder.com.helloworld.R;
import toton.lazycoder.com.helloworld.Utility.SpinnerUtility;

public class SkinRash extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    JSONObject info;
    private static final String INFO = "INFO";

    EditText Where;
    EditText size_1;
    EditText size_2;
    EditText number;
    EditText Colour;


    public SkinRash() {
    }

    public static SkinRash newInstance(String param1, String param2) {
        SkinRash fragment = new SkinRash();
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

        View view = inflater.inflate(R.layout.fragment_skin_rash, container, false);
        view.findViewById(R.id.backButtonAbdominalPain).setOnClickListener(this);
        view.findViewById(R.id.continueButtonAbdominalPain).setOnClickListener(this);

        Where = (EditText)view.findViewById(R.id.editText_skinRashPos);
        size_1 = (EditText)view.findViewById(R.id.editText_skinRashSize1);
        size_2 = (EditText)view.findViewById(R.id.editText_skinRashSize2);
        number = (EditText)view.findViewById(R.id.editText_skinRashMultiple);
        Colour = (EditText)view.findViewById(R.id.editText_skinRashColour);

        SpinnerUtility.SpinnerCreate(this,view,info,R.id.spinner_skinRashSurface,R.array.skinRashSurface,"Surface is");

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
                    if (Where.getText().length() != 0)
                        info.put("Rash over", Where.getText().toString());

                    if (size_1.getText().length() != 0)
                        info.put("Size", size_1.getText().toString() + "cm X " + size_2.getText().toString() + "cm");

                    if (number.getText().length() != 0) {
                        info.put("How many", "Multiple(" + number.getText().toString() + ")");
                    }
                    if (Colour.getText().length() != 0) {
                        info.put("Colour", Colour.getText().toString());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                activity.communicate(Communicator.Response.CONTINUE, info);
                break;
        }

    }
}
