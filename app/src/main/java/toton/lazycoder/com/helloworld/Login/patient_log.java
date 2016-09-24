package toton.lazycoder.com.helloworld.Login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import toton.lazycoder.com.helloworld.R;

/**
 * Created by ROY on 7/1/2016.
 */

public class patient_log extends AppCompatActivity implements View.OnClickListener{

    JSONObject Login;
    JSONObject Patient;

    Button Submit;

    EditText PatientID;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        try {
            Login = new JSONObject(getIntent().getStringExtra("Login"));
            Patient=new JSONObject();

        } catch (Exception e) {
            e.printStackTrace();
        }

        setContentView(R.layout.patient_login);

        PatientID=(EditText)findViewById(R.id.patientID);

        Submit = (Button)findViewById(R.id.button5);
        Submit.setOnClickListener(this);

        System.out.println();
    }

    public void onClick(View view)
    {
        int id =view.getId();

        switch(id)
        {
            case R.id.button5:
            {
                try{
                    Login.put("PatientID", PatientID.getText().toString());

                    DateFormat df = new SimpleDateFormat("dd-MM-yy",java.util.Locale.getDefault());
                    DateFormat tf = new SimpleDateFormat("HH:mm:ss",java.util.Locale.getDefault());
                    Date dateobj = new Date();
                    Login.put("Date",df.format(dateobj));
                    Login.put("Time",tf.format(dateobj));

                    Patient.put("Section 1", Login);
                }catch (JSONException e)
                {
                    e.printStackTrace();
                }


                Intent i = new Intent(patient_log.this,Demographics.class);
                i.putExtra("Patient",Patient.toString());
                startActivity(i);
                break;
            }
        }
    }
}
