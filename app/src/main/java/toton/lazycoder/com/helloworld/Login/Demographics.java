package toton.lazycoder.com.helloworld.Login;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

import org.json.JSONException;
import org.json.JSONObject;

import toton.lazycoder.com.helloworld.ComplainModule;
import toton.lazycoder.com.helloworld.R;

/**
 * Created by ROY on 7/1/2016.
 */

public class Demographics extends Activity implements View.OnClickListener {

    Button b;
    Button ImageCapture;

    RadioButton Male;
    RadioButton Female;

    EditText Name;
    EditText SDWName;
    EditText Age;
    EditText Address;
    EditText Occupation;
    EditText Phone;
    EditText BloodGroup;

    CheckBox SO;
    CheckBox DO;
    CheckBox WO;

    private static final int CAMERA_REQUEST = 1888;
    private ImageView patientImage;

    JSONObject info;
    JSONObject demographics;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demographics);

        info=new JSONObject();
        demographics = new JSONObject();

        b = (Button)findViewById(R.id.submit);
        b.setOnClickListener(this);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        Male=(RadioButton)findViewById(R.id.radioButtonMale);
        Female=(RadioButton)findViewById(R.id.radioButtonFemale);

        SO = (CheckBox)findViewById(R.id.checkBoxSO);
        DO = (CheckBox)findViewById(R.id.checkBoxDO);
        WO = (CheckBox)findViewById(R.id.checkBoxWO);

        Name = (EditText)findViewById(R.id.PatientName);
        SDWName=(EditText)findViewById(R.id.editTextSDW);
        Age =(EditText)findViewById(R.id.editTextAge);
        Address = (EditText)findViewById(R.id.Address);
        Occupation=(EditText)findViewById(R.id.editTextOcc);
        Phone=(EditText)findViewById(R.id.editTextPhone);
        BloodGroup=(EditText)findViewById(R.id.editTextBloodGrp);


        ImageCapture = (Button)findViewById(R.id.captureImage);
        ImageCapture.setOnClickListener(this);

        DO.setOnClickListener(this);
        SO.setOnClickListener(this);
        WO.setOnClickListener(this);

        patientImage = (ImageView)findViewById(R.id.Patient_Image);
    }
    @Override
    public void onClick(View view)
    {
        int id = view.getId();
        switch(id)
        {
            case R.id.submit:
            {
                try {
                    demographics.put("Name", Name.getText().toString());

                    if(SO.isChecked())
                        demographics.put("S/O", SDWName.getText().toString());
                    else if(DO.isChecked())
                        demographics.put("D/O", SDWName.getText().toString());
                    else if(WO.isChecked())
                        demographics.put("W/O", SDWName.getText().toString());

                    if(Male.isChecked())
                    {
                        demographics.put("Gender", "Male");
                    }
                    else if(Female.isChecked())
                    {
                        demographics.put("Gender", "Female");
                    }

                    demographics.put("Age", Age.getText().toString());
                    demographics.put("Occupation", Occupation.getText().toString());
                    demographics.put("Phone", Phone.getText().toString());
                    demographics.put("Blood Group", BloodGroup.getText().toString());

                    info.put("Section 2",demographics);

                }catch (JSONException e)
                {
                    e.printStackTrace();
                }
                Intent i = new Intent(Demographics.this,ComplainModule.class);
                i.putExtra("Patient",info.toString());
                startActivity(i);
                break;
            }

            case R.id.captureImage:
            {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
                break;
            }

            case R.id.checkBoxSO:
            {
                SO.setChecked(true);
                DO.setChecked(false);
                WO.setChecked(false);
                break;
            }

            case R.id.checkBoxDO:
            {
                DO.setChecked(true);
                SO.setChecked(false);
                WO.setChecked(false);
                break;
            }

            case R.id.checkBoxWO:
            {
                WO.setChecked(true);
                DO.setChecked(false);
                SO.setChecked(false);
                break;
            }


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
