package toton.lazycoder.com.helloworld.GeneralObservation;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import toton.lazycoder.com.helloworld.Diagnosis.Communicator;
import toton.lazycoder.com.helloworld.ObservationAndExamination;
import toton.lazycoder.com.helloworld.R;

import static android.support.design.R.id.checkbox;

/**
 * A simple {@link Fragment} subclass.
 */
public class MedicalHistory extends Fragment implements View.OnClickListener {


    JSONObject medicalHistory;

    CheckBox BloodPressure;
    View view;

    public MedicalHistory() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        medicalHistory=new JSONObject();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_medical_history, container, false);

        BloodPressure=(CheckBox)view.findViewById(R.id.checkBox_BloodPressure);

        view.findViewById(R.id.Next).setOnClickListener(this);

        return view;
    }

    public void onClick(View NewView)
    {
        int id = NewView.getId();

        switch (id)
        {
            case R.id.Next:

                try{

                    //Medical History
                    if(BloodPressure.isChecked())
                    {
                        medicalHistory.put("High Blood Pressure","Yes");
                    }else
                    {
                        medicalHistory.put("High Blood Pressure",null);
                    }

                    if(((CheckBox)view.findViewById(R.id.checkBox_HeartAttack)).isChecked())
                    {
                        medicalHistory.put("Heart Attack","Yes");
                    }else
                    {
                        medicalHistory.put("Heart Attack",null);
                    }

                    if(((CheckBox)view.findViewById(R.id.checkBox_Stroke)).isChecked())
                    {
                        medicalHistory.put("Stroke","Yes");
                    }else
                    {
                        medicalHistory.put("Stroke",null);
                    }

                    if(((CheckBox)view.findViewById(R.id.checkBox_Diabetes)).isChecked())
                    {
                        medicalHistory.put("Diabetes","Yes");
                    }else
                    {
                        medicalHistory.put("Diabetes",null);
                    }

                    if(((CheckBox)view.findViewById(R.id.checkBox_Asthma)).isChecked())
                    {
                        medicalHistory.put("Asthma","Yes");
                    }else
                    {
                        medicalHistory.put("Asthma",null);
                    }

                    if(((CheckBox)view.findViewById(R.id.checkBox_TB)).isChecked())
                    {
                        medicalHistory.put("TB","Yes");
                    }else
                    {
                        medicalHistory.put("TB",null);
                    }

                    if(((CheckBox)view.findViewById(R.id.checkBox_Cancer)).isChecked())
                    {
                        medicalHistory.put("Cancer","Yes");
                    }else
                    {
                        medicalHistory.put("Cancer",null);
                    }

                    if(((CheckBox)view.findViewById(R.id.checkBox_Operation)).isChecked())
                    {
                        medicalHistory.put("Operation","Yes");
                    }else
                    {
                        medicalHistory.put("Operation",null);
                    }
                    if(((EditText)view.findViewById(R.id.editText_HistoryOther)).getText().toString().length()!=0)
                        medicalHistory.put("Other",((EditText)view.findViewById(R.id.editText_HistoryOther)).getText().toString());
                    else
                        medicalHistory.put("Other",null);

                    //Habits
                    if(((CheckBox)view.findViewById(R.id.checkBox_Smoking)).isChecked())
                    {
                        medicalHistory.put("Smoking","Yes");
                    }else
                    {
                        medicalHistory.put("Smoking",null);
                    }

                    if(((CheckBox)view.findViewById(R.id.checkBox_Tobacco)).isChecked())
                    {
                        medicalHistory.put("Tobacco Chewing","Yes");
                    }else
                    {
                        medicalHistory.put("Tobacco Chewing",null);
                    }


                }catch(JSONException e)
                {
                    e.printStackTrace();
                }

                ObservationAndExamination activity = (ObservationAndExamination) getActivity();
                activity.communicate(Communicator.Response.CONTINUE, medicalHistory, 1);

                break;
        }
    }
}
