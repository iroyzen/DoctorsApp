package toton.lazycoder.com.helloworld;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import toton.lazycoder.com.helloworld.Diagnosis.Communicator;
import toton.lazycoder.com.helloworld.GeneralObservation.FamilyHistory;
import toton.lazycoder.com.helloworld.GeneralObservation.GeneralObservation;
import toton.lazycoder.com.helloworld.GeneralObservation.MedicalHistory;
import toton.lazycoder.com.helloworld.Utility.Globals;

public class ObservationAndExamination extends AppCompatActivity {

    FragmentManager fragmentManager;
    JSONArray PhyExamInfo = new JSONArray();
    JSONObject Patient;
    JSONObject Section3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observation_and_examination);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        try{
            PhyExamInfo = new JSONArray(getIntent().getStringExtra("ExamValue"));
            Patient = new JSONObject(getIntent().getStringExtra("Patient"));
            Section3 = new JSONObject(Patient.get("Section 3").toString());
        }catch (Exception e)
        {
            e.printStackTrace();
        }

        fragmentManager = getFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        GeneralObservation START = new GeneralObservation();
        ft.add(R.id.ObservationAndExam,START).commit();
    }


    public void communicate(Communicator.Response response, JSONObject info, int id){

        if(response == Communicator.Response.CONTINUE)
        {

            if(id==0) {
                try {
                    Section3.put("General Observation", info);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.ObservationAndExam,new MedicalHistory()).commit();

            }
            else if(id == 1)
            {
                try {
                    Section3.put("Medical History", info);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.ObservationAndExam,new FamilyHistory()).commit();


            }
            else if(id == 2)
            {
                try {
                    Section3.put("Family History", info);
                    Patient.put("Section 3",Section3);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Intent i = new Intent(this, PhysicalExamModule.class);
                if (PhyExamInfo.length() != 0) {
                    i.putExtra("ExamValue", PhyExamInfo.toString());
                }
                i.putExtra("Patient", Patient.toString());
                //Globals.PatientData=Patient;
                startActivity(i);
            }
        }
    }

}

