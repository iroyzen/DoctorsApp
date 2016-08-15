package toton.lazycoder.com.helloworld.Login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import toton.lazycoder.com.helloworld.R;

/**
 * Created by juny on 7/1/2016.
 */

public class patient_log extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContentView(R.layout.patient_login);
        Button b = (Button)findViewById(R.id.button5);
    }
    public void Complain(View v)
    {
        Intent i = new Intent(patient_log.this,Demographics.class);
        startActivity(i);
    }
}
