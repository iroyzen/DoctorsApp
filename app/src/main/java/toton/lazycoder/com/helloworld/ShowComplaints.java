package toton.lazycoder.com.helloworld;

import android.os.Bundle;
import android.app.Activity;
import android.os.Environment;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class ShowComplaints extends Activity {


    JSONObject Patient;
    EditText Complaints;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_complaints);

        Complaints = (EditText) findViewById(R.id.textView_ShowComplaints);

        try {
            Patient = new JSONObject(getIntent().getStringExtra("Patient"));

        } catch (Exception e) {
            e.printStackTrace();
        }

        if(Complaints.toString().length()!=0)
        {
            Complaints.setText(Patient.toString());
        }


        String content = Patient.toString();
        try {
            //File f1=new File(Environment.getExternalStorageDirectory()+"/Android/Doctor/Abc.txt");
            File myDir = new File(Environment.getExternalStorageDirectory(), "/Android/Doctor");
            if (!myDir.exists()) {
                if (!(myDir.mkdir())) //directory is created;
                {
                    Toast.makeText(this, "Couldn't create directory", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(this, "Created directory"+Environment.getExternalStorageDirectory()+"/Android/Doctor", Toast.LENGTH_SHORT).show();
                }
            }
            Toast.makeText(this, ""+myDir, Toast.LENGTH_SHORT).show();
            File f1 = File.createTempFile(
                    "ABC",  /* prefix */
                    ".txt",         /* suffix */
                    myDir      /* directory */
            );
            FileWriter fw = new FileWriter(f1.getName(),true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content);
            bw.close();
            if(Complaints.toString().length()!=0)
            {
                Complaints.setText(Patient.toString());
            }
        }catch (Exception e)
        {
            Toast.makeText(this,e+"",Toast.LENGTH_SHORT).show();
        }
    }

}
