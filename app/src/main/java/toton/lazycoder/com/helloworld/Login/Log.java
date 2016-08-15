package toton.lazycoder.com.helloworld.Login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;

import toton.lazycoder.com.helloworld.R;

/**
 * Created by juny on 6/30/2016.
 */

public class Log extends AppCompatActivity {
    EditText t1,t2;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kiosk_login);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        File myDir = new File(getCacheDir(), "com.DoctorsApp.files");
        if (!myDir.exists()) {
            if (!(myDir.mkdir())) //directory is created;
            {
                Toast.makeText(this, "Couldn't create directory", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(this, "Created directory"+getCacheDir()+"/"+"com.DoctorsApp.files", Toast.LENGTH_SHORT).show();
            }
        }
        Button b = (Button) findViewById(R.id.login);
        t1 = (EditText) findViewById(R.id.uid);
        t2 = (EditText) findViewById(R.id.password);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean bool = check(t1.getText().toString(), t2.getText().toString());
                if (bool) {
                    Intent i = new Intent(Log.this, patient_log.class);
                    startActivity(i);
                }
            }
        });

    }
    public boolean check(String t1,String t2)
    {
        return true;
    }

}
