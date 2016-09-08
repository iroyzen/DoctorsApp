package toton.lazycoder.com.helloworld.Login;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import org.json.JSONException;
import org.json.JSONObject;

import toton.lazycoder.com.helloworld.ComplainModule;
import toton.lazycoder.com.helloworld.R;

/**
 * Created by juny on 7/1/2016.
 */

public class Demographics extends Activity implements View.OnClickListener {

    Button b;
    Button ImageCapture;
    EditText Name;
    private static final int CAMERA_REQUEST = 1888;
    private ImageView patientImage;

    JSONObject info;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demographics);

        info=new JSONObject();

        b = (Button)findViewById(R.id.submit);
        b.setOnClickListener(this);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        Name = (EditText)findViewById(R.id.PatientName);

        ImageCapture = (Button)findViewById(R.id.captureImage);
        ImageCapture.setOnClickListener(this);

        patientImage = (ImageView)findViewById(R.id.Patient_Image);
    }
    @Override
    public void onClick(View view)
    {
        if(view.getId()==R.id.submit)
        {
            try {
                info.put("Name", Name.getText().toString());
            }catch (JSONException e)
            {
                e.printStackTrace();
            }
            Intent i = new Intent(Demographics.this,ComplainModule.class);
            i.putExtra("Patient",info.toString());
            startActivity(i);
        }else if(view.getId()==R.id.captureImage)
        {
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAMERA_REQUEST);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            patientImage.setImageBitmap(photo);
        }
    }
}
