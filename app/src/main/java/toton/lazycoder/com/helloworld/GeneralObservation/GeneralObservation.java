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
import android.util.Base64;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
import toton.lazycoder.com.helloworld.Utility.Globals;
import toton.lazycoder.com.helloworld.Utility.SpinnerUtility;




public class GeneralObservation extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    String mCurrentPhotoPath;

    JSONObject info;
    JSONObject images;
    JSONArray PrevPres;
    JSONArray XRay;
    JSONArray USG;
    JSONArray LabReport;
    JSONArray Others;

    String DocumentWhich;

    EditText BMI;
    EditText Weight;
    EditText Height;
    EditText Pulse;
    EditText BPL;
    EditText BPH;
    EditText Temperature;
    EditText SPO2;

    float BMICalc;
    float WeightCalc;
    float HeightCalc;
    private static DecimalFormat BMIshow = new DecimalFormat(".##");
    static final int PrevPresCode=1;
    static final int XRayCode=2;
    static final int USGCode=3;
    static final int LabRepCode=4;
    static final int OtherCode=5;

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
    int XRayCount;
    int PrevPresCount;
    int USGCount;
    int LabReportCount;
    int OtherCount;

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
        images = new JSONObject();
        PrevPres = new JSONArray();
        XRay = new JSONArray();
        USG = new JSONArray();
        LabReport = new JSONArray();
        Others = new JSONArray();
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
        PrevPresCount=0;
        XRayCount=0;
        USGCount=0;
        LabReportCount=0;
        OtherCount=0;

        BMI = (EditText) view.findViewById(R.id.editText_BMI);
        Weight = (EditText) view.findViewById(R.id.editText_Weight);
        Height = (EditText) view.findViewById(R.id.editText_Height);
        Pulse = (EditText) view.findViewById(R.id.editText_Pulse);
        BPL = (EditText) view.findViewById(R.id.editText_BP1);
        BPH = (EditText) view.findViewById(R.id.editText_BP2);
        Temperature = (EditText) view.findViewById(R.id.editText_Temperature);
        SPO2 = (EditText) view.findViewById(R.id.editText_SPO2);

        Weight.addTextChangedListener(inputTextWatcher);
        Height.addTextChangedListener(inputTextWatcher);

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
                    info.put("BP",BPL.getText().toString()+"-"+BPH.getText().toString());
                    info.put("Temperature",Temperature.getText().toString());
                    info.put("SPO2", SPO2.getText().toString());

                    if(PrevPres.length()!=0)
                        images.put("Previous Prescription", PrevPres);

                    if(XRay.length()!=0)
                        images.put("XRay",XRay);

                    if(USG.length()!=0)
                        images.put("USG",USG);

                    if(LabReport.length()!=0)
                        images.put("Lab Report",LabReport);

                    if(Others.length()!=0)
                        images.put("Others",Others);

                }catch(Exception e)
                {
                    e.printStackTrace();
                }
                Globals.DocumentImages=images;
                ObservationAndExamination activity = (ObservationAndExamination) getActivity();
                activity.communicate(Communicator.Response.CONTINUE, info, 0);
                break;

            case R.id.buttonPrevPres:
                dispatchTakePictureIntent("Previous Presciption_", PrevPresCode, PrevPresCount);

                break;
            case R.id.buttonXray:
                dispatchTakePictureIntent("Previous XRay_", XRayCode, XRayCount);

                break;
            case R.id.buttonUSG:
                dispatchTakePictureIntent("Previous USG_", USGCode, USGCount);

                break;
            case R.id.buttonLabReport:
                dispatchTakePictureIntent("Previous Lab Reports_", LabRepCode, LabReportCount);

                break;
            case R.id.buttonOthers:
                dispatchTakePictureIntent("Others_", OtherCode, OtherCount);

                break;
        }

    }



    private void onCaptureImageResult(Intent data) {
        final int THUMBSIZE = 64;
        String photopath="/storage/sdcard0/Android/Doctor/"+imageTempName;
        Bitmap imageBitmap = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(photopath), THUMBSIZE, THUMBSIZE);

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
        if (requestCode == PrevPresCode && resultCode == Activity.RESULT_OK) {

            onCaptureImageResult(data);
            try {
                PrevPres.put(EncodeImage("/storage/sdcard0/Android/Doctor/" + imageTempName));
            }catch (IOException e)
            {
                e.printStackTrace();;
            }
            PrevPresCount++;
        }
        else if (requestCode == XRayCode && resultCode == Activity.RESULT_OK)
        {
            onCaptureImageResult(data);
            try {
                XRay.put(EncodeImage("/storage/sdcard0/Android/Doctor/" + imageTempName));
            }catch (IOException e)
            {
                e.printStackTrace();;
            }
            XRayCount++;
        }
        else if (requestCode == USGCode && resultCode == Activity.RESULT_OK)
        {
            onCaptureImageResult(data);
            try {
                USG.put(EncodeImage("/storage/sdcard0/Android/Doctor/" + imageTempName));
            }catch (IOException e)
            {
                e.printStackTrace();;
            }
            USGCount++;
        }
        else if (requestCode == LabRepCode && resultCode == Activity.RESULT_OK)
        {

            onCaptureImageResult(data);
            try {
                LabReport.put(EncodeImage("/storage/sdcard0/Android/Doctor/" + imageTempName));
            }catch (IOException e)
            {
                e.printStackTrace();;
            }
            LabReportCount++;
        }
        else if (requestCode == OtherCode && resultCode == Activity.RESULT_OK)
        {
            onCaptureImageResult(data);
            try {
                Others.put(EncodeImage("/storage/sdcard0/Android/Doctor/" + imageTempName));
            }catch (IOException e)
            {
                e.printStackTrace();;
            }
            OtherCount++;
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

    private File createImageFile(String imageFileName, int respCount) throws IOException {

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
        File image= new File(myDir+"/"+imageFileName+(respCount)+".jpg");
        imageTempName=imageFileName+(respCount)+".jpg";

        return image;
    }

    private void dispatchTakePictureIntent(String imageFileName, int respCode, int respCount) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile(imageFileName, respCount);
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

                startActivityForResult(takePictureIntent, respCode);
            }
        }
    }

    public String EncodeImage(String filepath) throws IOException
    {
        InputStream inputStream;
            inputStream = new FileInputStream(filepath);//You can get an inputStream using any IO API

            byte[] bytes;
            byte[] buffer = new byte[8192];
            int bytesRead;
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            try {
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    output.write(buffer, 0, bytesRead);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            bytes = output.toByteArray();
            String encodedString = Base64.encodeToString(bytes, Base64.DEFAULT);
            return encodedString;
    }
    public String EncodeImageComp(String filepath) throws IOException
    {
        Bitmap bm = BitmapFactory.decodeFile(filepath);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
        byte[] byteArrayImage = baos.toByteArray();

        String encodedImage = Base64.encodeToString(byteArrayImage, Base64.DEFAULT);
        return encodedImage;
    }
}

