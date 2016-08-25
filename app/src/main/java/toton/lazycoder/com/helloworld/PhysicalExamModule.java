package toton.lazycoder.com.helloworld;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import toton.lazycoder.com.helloworld.Diagnosis.Communicator;
import toton.lazycoder.com.helloworld.PhysicalExam.AbdomenExam;
import toton.lazycoder.com.helloworld.PhysicalExam.AnkleExam;
import toton.lazycoder.com.helloworld.PhysicalExam.ArmExam;
import toton.lazycoder.com.helloworld.PhysicalExam.BackExam;
import toton.lazycoder.com.helloworld.PhysicalExam.BreastExam;
import toton.lazycoder.com.helloworld.PhysicalExam.ChestExam;
import toton.lazycoder.com.helloworld.PhysicalExam.EarExam;
import toton.lazycoder.com.helloworld.PhysicalExam.EyeExam;
import toton.lazycoder.com.helloworld.PhysicalExam.FaceExam;
import toton.lazycoder.com.helloworld.PhysicalExam.HandExam;
import toton.lazycoder.com.helloworld.PhysicalExam.HeadExam;
import toton.lazycoder.com.helloworld.PhysicalExam.LegExam;
import toton.lazycoder.com.helloworld.PhysicalExam.MouthExam;
import toton.lazycoder.com.helloworld.PhysicalExam.NeckExam;
import toton.lazycoder.com.helloworld.PhysicalExam.RectalAreaExam;
import toton.lazycoder.com.helloworld.PhysicalExam.ScrotalExam;
import toton.lazycoder.com.helloworld.PhysicalExam.Test;
import toton.lazycoder.com.helloworld.R;

public class PhysicalExamModule extends AppCompatActivity implements AdapterView.OnItemClickListener, Communicator, View.OnClickListener {

    public HashMap<String, Exams> completedExams = new HashMap<>();
    public List<String> checked = new ArrayList<>();

    public JSONArray PhyExamInfo = new JSONArray();
    public JSONObject Patient;
    JSONObject PhysicalExam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_physical_exam_module);

        Button Finish = (Button)findViewById(R.id.finish);
        Finish.setOnClickListener(this);
        PhysicalExam = new JSONObject();

        try {
            PhyExamInfo = new JSONArray(getIntent().getStringExtra("ExamValue"));
            Patient = new JSONObject(getIntent().getStringExtra("Patient"));

        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = 0; i < PhyExamInfo.length(); i++) {
            try {
                Toast.makeText(this, "" + PhyExamInfo.get(i) + "", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        List<String> ExamList = new ArrayList<String>();

        for (int i = 0; i < PhyExamInfo.length(); i++) {
            try {
                Toast.makeText(this, "" + PhyExamInfo.get(i) + "", Toast.LENGTH_SHORT).show();
                ExamList.add(PhyExamInfo.get(i).toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.physical_exam_list, ExamList);
        ListView listView = (ListView) findViewById(R.id.Physical_Exam_List);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.finish) {

            try{
                Patient.put("Physical Exam",PhysicalExam);
            }catch(Exception e)
            {
                e.printStackTrace();
            }

            Intent i = new Intent(this,ShowComplaints.class);
            if(Patient!=null) {
                i.putExtra("Patient", Patient.toString());
            }
            startActivity(i);
        }
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        String tag = parent.getItemAtPosition(position).toString();
        Exams SavedExam;
        if (!completedExams.containsKey(tag)) {
            SavedExam = new Exams(instantiateFragmentByTag(tag),new JSONObject());
        } else {
            SavedExam = (completedExams.get(tag));
            Bundle bundle = new Bundle();
            bundle.putString("INFO", SavedExam.info.toString());
            SavedExam.fragment.setArguments(bundle);
        }
        FragmentManager fm_1 = getFragmentManager();
        FragmentTransaction ft_1 = fm_1.beginTransaction();
        if(fm_1.findFragmentById(R.id.Physical_Exam)!=null)
            ft_1.remove(fm_1.findFragmentById(R.id.Physical_Exam)).commit();
        if (SavedExam.fragment != null)
        {
            FragmentManager fm_2 = getFragmentManager();
            FragmentTransaction ft_2 = fm_2.beginTransaction();
            ft_2.add(R.id.Physical_Exam, SavedExam.fragment, tag).commit();
        }

        Toast.makeText(parent.getContext(), tag, Toast.LENGTH_SHORT).show();
    }


    private Fragment instantiateFragmentByTag(String tag) {
        if (tag.equals("Head Exam"))
        {
            return new HeadExam();
        } else if (tag.equals("Ear Exam"))
        {
            return new EarExam();
        } else if (tag.equals("Mouth Exam"))
        {
            return  new MouthExam();
        }else if (tag.equals("Breast Exam"))
        {
            return  new BreastExam();
        }else if (tag.equals("Chest Exam"))
        {
            return  new ChestExam();
        }else if (tag.equals("Eye Exam"))
        {
            return  new EyeExam();
        }else if (tag.equals("Face Exam"))
        {
            return  new FaceExam();
        }else if (tag.equals("Hand Exam"))
        {
            return  new HandExam();
        } else if (tag.equals("Ankle Exam"))
        {
            return  new AnkleExam();
        }else if (tag.equals("Neck Exam"))
        {
            return  new NeckExam();
        }else if (tag.equals("Scrotal Exam"))
        {
            return  new ScrotalExam();
        }else if (tag.equals("Abdomen Exam"))
        {
            return  new AbdomenExam();
        }else if (tag.equals("Arm Exam"))
        {
            return  new ArmExam();
        }else if (tag.equals("Back Exam"))
        {
            return  new BackExam();
        }else if (tag.equals("Leg Exam"))
        {
            return  new LegExam();
        }else if (tag.equals("Rectal Area Exam"))
        {
            return  new RectalAreaExam();
        }
        return null;
    }

    @Override
    public void communicate(Response response, JSONObject info) {
        if (response == Response.BACK)
        {

        }
        else if (response == Response.RESET)
        {

        }
        else if (response == Response.CONTINUE)
        {

            FragmentManager fm = getFragmentManager();
            Fragment current = fm.findFragmentById(R.id.Physical_Exam);
            String tag = current.getTag();
            try{
                PhysicalExam.put(tag,info);
            }catch(Exception e)
            {
                e.printStackTrace();
            }
            completedExams.put(tag, new Exams(current,info));
            FragmentTransaction ft = fm.beginTransaction();
            ft.remove(fm.findFragmentById(R.id.Physical_Exam)).commit();
        }

    }
    class Exams{
        public Fragment fragment;
        public JSONObject info;

        public Exams(Fragment fragment, JSONObject info){
            this.fragment = fragment;
            this.info = info;
        }
    }

}
