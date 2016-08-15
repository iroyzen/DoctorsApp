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
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import toton.lazycoder.com.helloworld.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Vomiting.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Vomiting#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Vomiting extends Fragment implements OnItemSelectedListener, View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    JSONObject info;
    private static final String INFO = "INFO";

    public Vomiting() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Vomiting.
     */
    // TODO: Rename and change types and number of parameters
    public static Vomiting newInstance(String param1, String param2) {
        Vomiting fragment = new Vomiting();
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
        View view = inflater.inflate(R.layout.fragment_vomiting, container, false);
        view.findViewById(R.id.backButtonAbdominalPain).setOnClickListener(this);
        view.findViewById(R.id.continueButtonAbdominalPain).setOnClickListener(this);

        Spinner spinner1 = (Spinner) view.findViewById(R.id.spinner_vomitingNature);
        spinner1.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> dataAdapter1 = ArrayAdapter.createFromResource(getActivity(), R.array.vomitingNature, android.R.layout.simple_spinner_item);
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(new NothingSelectedSpinnerAdapter(dataAdapter1, R.layout.spinner_row_nothing_selected, getActivity()));

        Spinner spinner2 = (Spinner) view.findViewById(R.id.spinner_vomitingAppetite);
        spinner2.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> dataAdapter2 = ArrayAdapter.createFromResource(getActivity(), R.array.vomitingAppetite, android.R.layout.simple_spinner_item);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(new NothingSelectedSpinnerAdapter(dataAdapter2, R.layout.spinner_row_nothing_selected, getActivity()));

        Spinner spinner3 = (Spinner) view.findViewById(R.id.spinner_vomitingBlood);
        spinner3.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> dataAdapter3 = ArrayAdapter.createFromResource(getActivity(), R.array.YesNoString, android.R.layout.simple_spinner_item);
        dataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(new NothingSelectedSpinnerAdapter(dataAdapter3, R.layout.spinner_row_nothing_selected, getActivity()));

        return view;

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.backButtonAbdominalPain) {
            Communicator activity = (Communicator) getActivity();
            activity.communicate(Communicator.Response.BACK, null);
        } else if (view.getId() == R.id.continueButtonAbdominalPain) {
            Communicator activity = (Communicator) getActivity();
            activity.communicate(Communicator.Response.CONTINUE, info);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        if(!(position==0))
        {
            String item = parent.getItemAtPosition(position).toString();
            // Showing selected spinner item
            Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
        }
    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

}
