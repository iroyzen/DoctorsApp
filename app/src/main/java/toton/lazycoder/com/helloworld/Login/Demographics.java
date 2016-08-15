package toton.lazycoder.com.helloworld.Login;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import toton.lazycoder.com.helloworld.ComplainModule;
import toton.lazycoder.com.helloworld.R;

/**
 * Created by juny on 7/1/2016.
 */

public class Demographics extends Activity implements View.OnClickListener {

    Button b;
    Button ImageCapture;
    private static final int CAMERA_REQUEST = 1888;
    private ImageView patientImage;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demographics);
        b = (Button)findViewById(R.id.submit);
        b.setOnClickListener(this);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        ImageCapture = (Button)findViewById(R.id.captureImage);
        ImageCapture.setOnClickListener(this);

        patientImage = (ImageView)findViewById(R.id.Patient_Image);
    }
    @Override
    public void onClick(View view)
    {
        if(view.getId()==R.id.submit)
        {
            Intent i = new Intent(Demographics.this,ComplainModule.class);
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
