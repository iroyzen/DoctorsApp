package toton.lazycoder.com.helloworld.GeneralObservation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import toton.lazycoder.com.helloworld.Adapter.AlreadyDiagnosedListAdapter;
import toton.lazycoder.com.helloworld.Adapter.CustomImageAdapter;
import toton.lazycoder.com.helloworld.Adapter.DocumentPicsListAdapter;
import toton.lazycoder.com.helloworld.Adapter.GetSet;
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

    private static final int CAMERA_REQUEST = 1;
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

    LinearLayout myScroll;

    List<String> documents = new ArrayList<>();
    ListView documentPicList;
    DocumentPicsListAdapter documentPicsListAdapter;

    CustomImageAdapter customImageAdapter;
    ArrayList<GetSet> getSets;
    ListView listView;

    // Temp save listItem position
    int position;

    int imageCount;
    String imageTempName;
    String[] imageFor;

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
        view.findViewById(R.id.buttonPrevPres).setOnClickListener(this);
        view.findViewById(R.id.buttonXray).setOnClickListener(this);
        view.findViewById(R.id.buttonUSG).setOnClickListener(this);
        view.findViewById(R.id.buttonLabReport).setOnClickListener(this);
        view.findViewById(R.id.buttonOthers).setOnClickListener(this);

        myScroll=(LinearLayout)view.findViewById(R.id.myScroll);

        imageCount=0;
        BMI = (TextView) view.findViewById(R.id.textView_BMI);
        Weight = (EditText) view.findViewById(R.id.editText_Weight);
        Height = (EditText) view.findViewById(R.id.editText_Height);
        Pulse = (EditText) view.findViewById(R.id.editText_Pulse);
        BP = (EditText) view.findViewById(R.id.editText_BP);
        Temperature = (EditText) view.findViewById(R.id.editText_Temperature);
        PastHistory = (EditText) view.findViewById(R.id.editText_PastMedicalHistory);

        Weight.addTextChangedListener(inputTextWatcher);
        Height.addTextChangedListener(inputTextWatcher);

        /*documentPicList = (ListView) view.findViewById(R.id.document_pic_list);
        documentPicsListAdapter = new DocumentPicsListAdapter(getActivity(), R.drawable.red_cross, documents);
        documentPicList.setAdapter(documentPicsListAdapter);*/

        listView = (ListView) view.findViewById(R.id.document_pic_list);
        getSets = new ArrayList<GetSet>();
        imageFor = getResources().getStringArray(R.array.imageFor);

        customImageAdapter = new CustomImageAdapter(getSets, getActivity());
        listView.setAdapter(customImageAdapter);

        return view;
    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onClick(View view) {

        int ID = view.getId();

        switch (ID)
        {
            case R.id.backButton:
                break;

            case R.id.continueButton:
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
                break;

            case R.id.buttonPrevPres:
                dispatchTakePictureIntent("Previous Presciption_");

                break;
            case R.id.buttonXray:
                dispatchTakePictureIntent("Previous Xray_");

                break;
            case R.id.buttonUSG:
                dispatchTakePictureIntent("Previous USG_");

                break;
            case R.id.buttonLabReport:
                dispatchTakePictureIntent("Previous Lab Reports_");

                break;
            case R.id.buttonOthers:
                dispatchTakePictureIntent("Others_");

                break;
        }

    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if (!(position == 0)) {
            String item = parent.getItemAtPosition(position).toString();
            int i=0;
            captureImage(i++,"newtest"+i+".jpg");
            //dispatchTakePictureIntent();
        }
    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    public void captureImage(int pos, String imageName) {
        position = pos;
        imageTempName = imageName;
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_TAKE_PHOTO);
    }

    private void onCaptureImageResult(Intent data) {
        final int THUMBSIZE = 64;
        String photopath="/storage/sdcard0/Android/Doctor/"+imageTempName;
        Bitmap imageBitmap = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(photopath),
                THUMBSIZE, THUMBSIZE);

        /*Bundle extras = data.getExtras();
        Bitmap imageBitmap = (Bitmap) extras.get("data");


        // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
        Uri tempUri = getImageUri(getActivity().getApplicationContext(), imageBitmap, imageTempName);
        String picturePath = getRealPathFromURI(tempUri);*/

        GetSet inflate = new GetSet();
        // Global Values
        inflate.setUid(String.valueOf(imageCount));

        inflate.setLabel(imageTempName);
        inflate.setHaveImage(false);
        inflate.setStatus(true);

        getSets.add(inflate);
        customImageAdapter.setImageInItem(position, imageBitmap, photopath);
        imageCount++;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == Activity.RESULT_OK) {

            onCaptureImageResult(data);
            //documents.add(mCurrentPhotoPath);
            //documentPicsListAdapter.notifyDataSetChanged();
            //imageCount++;
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage, String imageName) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, imageName, null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    public Bitmap convertSrcToBitmap(String imageSrc) {
        Bitmap myBitmap = null;
        File imgFile = new File(imageSrc);
        if (imgFile.exists()) {
            myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
        }
        return myBitmap;
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

    private File createImageFile(String imageFileName) throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
        //String imageFileName = "JPEG_" + timeStamp + "_";

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
        File image= new File(myDir+"/"+imageFileName+(imageCount)+".jpg");
        imageTempName=imageFileName+(imageCount)+".jpg";
        /*File image = File.createTempFile(
            imageFileName+(imageCount++),
               ".jpg",
              myDir
        );*/



        // Save a file: path for use with ACTION_VIEW intents
        Toast.makeText(getActivity(), "Passed1", Toast.LENGTH_SHORT).show();
        mCurrentPhotoPath = "file://" + image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntent(String imageFileName) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile(imageFileName);
            } catch (IOException ex) {
                Toast.makeText(getActivity(), "Couldn't create file"+ex, Toast.LENGTH_SHORT).show();
                ex.printStackTrace();
            }

            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getActivity(),
                        "com.example.android.fileprovider",
                        photoFile);


                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

                List<ResolveInfo> resInfoList = getActivity().getPackageManager().queryIntentActivities(takePictureIntent, PackageManager.MATCH_DEFAULT_ONLY);
                for (ResolveInfo resolveInfo : resInfoList) {
                    String packageName = resolveInfo.activityInfo.packageName;
                    getActivity().grantUriPermission(packageName, photoURI, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                }
                position=imageCount;

                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }



}

