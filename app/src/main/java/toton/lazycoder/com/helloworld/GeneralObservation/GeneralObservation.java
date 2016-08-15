package toton.lazycoder.com.helloworld.GeneralObservation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import toton.lazycoder.com.helloworld.Adapter.AlreadyDiagnosedListAdapter;
import toton.lazycoder.com.helloworld.Adapter.DocumentPicsListAdapter;
import toton.lazycoder.com.helloworld.Diagnosis.Communicator;
import toton.lazycoder.com.helloworld.Diagnosis.NothingSelectedSpinnerAdapter;
import toton.lazycoder.com.helloworld.ObservationAndExamination;
import toton.lazycoder.com.helloworld.R;
import toton.lazycoder.com.helloworld.Utility.SpinnerUtility;


public class GeneralObservation extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private static final int CAMERA_REQUEST = 1888;
    String mCurrentPhotoPath;

    JSONObject info;

    String DocumentWhich;

    TextView BMI;
    EditText Weight;
    EditText Height;
    EditText Pulse;
    EditText BP;
    EditText Temperature;
    EditText PastHistory;

    float BMICalc;
    float WeightCalc;
    float HeightCalc;
    private static DecimalFormat BMIshow = new DecimalFormat(".##");
    static final int REQUEST_TAKE_PHOTO = 1;

    List<String> documents = new ArrayList<>();
    ListView documentPicList;
    DocumentPicsListAdapter documentPicsListAdapter;

    public GeneralObservation() {
    }


    public static GeneralObservation newInstance(String param1, String param2) {
        GeneralObservation fragment = new GeneralObservation();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        info = new JSONObject();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_general_observation, container, false);
        view.findViewById(R.id.backButton).setOnClickListener(this);
        view.findViewById(R.id.continueButton).setOnClickListener(this);
        BMI = (TextView) view.findViewById(R.id.textView_BMI);
        Weight = (EditText) view.findViewById(R.id.editText_Weight);
        Height = (EditText) view.findViewById(R.id.editText_Height);
        Pulse = (EditText) view.findViewById(R.id.editText_Pulse);
        BP = (EditText) view.findViewById(R.id.editText_BP);
        Temperature = (EditText) view.findViewById(R.id.editText_Temperature);
        PastHistory = (EditText) view.findViewById(R.id.editText_PastMedicalHistory);

        Weight.addTextChangedListener(inputTextWatcher);
        Height.addTextChangedListener(inputTextWatcher);

        Spinner spinner = (Spinner) view.findViewById(R.id.spinner_PreviousDocuments);
        ArrayAdapter<CharSequence> dataAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.previousDocuments, android.R.layout.simple_spinner_item);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(new NothingSelectedSpinnerAdapter(dataAdapter, R.layout.spinner_row_nothing_selected, getActivity()));
        spinner.setOnItemSelectedListener(this);

        documentPicList = (ListView) view.findViewById(R.id.document_pic_list);
        documentPicsListAdapter = new DocumentPicsListAdapter(getActivity(), R.drawable.red_cross, documents);
        documentPicList.setAdapter(documentPicsListAdapter);

        return view;
    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.backButton) {
            //Communicator activity = (Communicator) getActivity();
            //activity.communicate(Communicator.Response.BACK, null);
        } else if (view.getId() == R.id.continueButton) {
            try{
                info.put("Height",Height.getText().toString());
                info.put("Weight",Weight.getText().toString());
                info.put("BMI",BMI.getText().toString());
                info.put("Pulse",Pulse.getText().toString());
                info.put("BP",BP.getText().toString());
                info.put("Temperature",Temperature.getText().toString());
                info.put("Past Medical History",PastHistory.getText().toString());

            }catch(Exception e)
            {
                e.printStackTrace();
            }

            ObservationAndExamination activity = (ObservationAndExamination) getActivity();
            activity.communicate(Communicator.Response.CONTINUE, info);
        }
    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if (!(position == 0)) {
            String item = parent.getItemAtPosition(position).toString();
            dispatchTakePictureIntent();
            /*Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAMERA_REQUEST);
            Toast.makeText(parent.getContext(), item, Toast.LENGTH_SHORT).show();*/
        }
    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == Activity.RESULT_OK) {
            //Bitmap photo = (Bitmap) data.getExtras().get("data");
            documents.add(mCurrentPhotoPath);
            documentPicsListAdapter.notifyDataSetChanged();
        }
    }

    TextWatcher inputTextWatcher = new TextWatcher() {
        public void afterTextChanged(Editable s) {
            if (Weight.getText().length() != 0 && Height.getText().length() != 0) {
                WeightCalc = Float.parseFloat(Weight.getText().toString());
                HeightCalc = (Float.parseFloat(Height.getText().toString())) / 100;
                BMICalc = (WeightCalc / (HeightCalc * HeightCalc));
                BMI.setText(BMIshow.format(BMICalc));
            }
            else
                BMI.setText("");
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

    };

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        //File myDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        //File myDir = new File("/storage/sdcard0/Android/Doctor");
        File myDir = new File(Environment.getExternalStorageDirectory(), "/Android/Doctor");
        if (!myDir.exists()) {
            if (!(myDir.mkdir())) //directory is created;
            {
                Toast.makeText(getActivity(), "Couldn't create directory", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(getActivity(), "Created directory"+Environment.getExternalStorageDirectory()+"/Android/Doctor", Toast.LENGTH_SHORT).show();
            }
        }
        Toast.makeText(getActivity(), ""+myDir, Toast.LENGTH_SHORT).show();
        File image = File.createTempFile(
            imageFileName,  /* prefix */
               ".jpg",         /* suffix */
              myDir      /* directory */
        );



        // Save a file: path for use with ACTION_VIEW intents
        Toast.makeText(getActivity(), "Passed1", Toast.LENGTH_SHORT).show();
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                Toast.makeText(getActivity(), "Couldn't create file"+ex, Toast.LENGTH_SHORT).show();
                ex.printStackTrace();
            }
            Toast.makeText(getActivity(), "Passed2", Toast.LENGTH_SHORT).show();
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getActivity(),
                        "com.example.android.fileprovider",
                        photoFile);
                Toast.makeText(getActivity(), "Passed3", Toast.LENGTH_SHORT).show();
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

}

