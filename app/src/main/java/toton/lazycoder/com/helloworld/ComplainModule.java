package toton.lazycoder.com.helloworld;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import toton.lazycoder.com.helloworld.Adapter.AlreadyDiagnosedListAdapter;
import toton.lazycoder.com.helloworld.Adapter.SelectedNoneSpinnerAdapter;
import toton.lazycoder.com.helloworld.Diagnosis.AbdomenSwelling;
import toton.lazycoder.com.helloworld.Diagnosis.AbdominalPain;
import toton.lazycoder.com.helloworld.Diagnosis.AcidityIndigestion;
import toton.lazycoder.com.helloworld.Diagnosis.BackPain;
import toton.lazycoder.com.helloworld.Diagnosis.BleedingWithStool;
import toton.lazycoder.com.helloworld.Diagnosis.Boils;
import toton.lazycoder.com.helloworld.Diagnosis.BreastPain;
import toton.lazycoder.com.helloworld.Diagnosis.ChestPain;
import toton.lazycoder.com.helloworld.Diagnosis.CommonCold;
import toton.lazycoder.com.helloworld.Diagnosis.Communicator;
import toton.lazycoder.com.helloworld.Diagnosis.Cough;
import toton.lazycoder.com.helloworld.Diagnosis.Diarrhoea;
import toton.lazycoder.com.helloworld.Diagnosis.DifficultyBreathing;
import toton.lazycoder.com.helloworld.Diagnosis.Dizziness;
import toton.lazycoder.com.helloworld.Diagnosis.EarPain;
import toton.lazycoder.com.helloworld.Diagnosis.Fainting;
import toton.lazycoder.com.helloworld.Diagnosis.Fever;
import toton.lazycoder.com.helloworld.Diagnosis.GeneralWeakness;
import toton.lazycoder.com.helloworld.Diagnosis.Headache;
import toton.lazycoder.com.helloworld.Diagnosis.Injury;
import toton.lazycoder.com.helloworld.Diagnosis.JointPain;
import toton.lazycoder.com.helloworld.Diagnosis.NothingSelectedSpinnerAdapter;
import toton.lazycoder.com.helloworld.Diagnosis.OtherSwelling;
import toton.lazycoder.com.helloworld.Diagnosis.Palpitation;
import toton.lazycoder.com.helloworld.Diagnosis.ParticularWeakness;
import toton.lazycoder.com.helloworld.Diagnosis.PerianalPain;
import toton.lazycoder.com.helloworld.Diagnosis.ScrotalPain;
import toton.lazycoder.com.helloworld.Diagnosis.SkinRash;
import toton.lazycoder.com.helloworld.Diagnosis.TeethPain;
import toton.lazycoder.com.helloworld.Diagnosis.ThroatPain;
import toton.lazycoder.com.helloworld.Diagnosis.ThroatSwelling;
import toton.lazycoder.com.helloworld.Diagnosis.Ulcer;
import toton.lazycoder.com.helloworld.Diagnosis.Vomiting;
import toton.lazycoder.com.helloworld.Diagnosis.YellowUrine;

public class ComplainModule extends AppCompatActivity implements Communicator, View.OnClickListener{

    Spinner categorySpinner, subcategorySpinner;
    ListView alreadyDiagnosedList;

    AlreadyDiagnosedListAdapter alreadyDiagnosedAdapter;

    JSONObject Patient;
    JSONObject Section3=new JSONObject();
    JSONObject Category;
    JSONObject Pain = new JSONObject();
    JSONObject Swelling = new JSONObject();
    JSONObject DifficultyBreathing = new JSONObject();
    JSONObject Fever = new JSONObject();
    JSONObject Diarrhoea = new JSONObject();
    JSONObject Vomiting = new JSONObject();
    JSONObject Dizziness = new JSONObject();
    JSONObject AcidityIndigestion = new JSONObject();
    JSONObject YellowUrine = new JSONObject();
    JSONObject SkinRash = new JSONObject();
    JSONObject BleedingStool = new JSONObject();
    JSONObject GynaeProblems = new JSONObject();
    JSONObject Injury = new JSONObject();
    JSONObject Boils = new JSONObject();
    JSONObject Ulcer = new JSONObject();
    JSONObject Palpitation = new JSONObject();
    JSONObject Fainting = new JSONObject();
    JSONObject Weakness = new JSONObject();
    JSONObject Cough = new JSONObject();
    JSONObject CommonCold = new JSONObject();

    FragmentManager fragmentManager;
    HashMap<String, Diagnosis> completedFragments = new HashMap<>();
    List<String> diagnosed = new ArrayList<>();

    JSONArray PhyExamInfo;

    JSONObject Section2;
    JSONObject Section1;
    TextView Name;
    TextView RegNo;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complain_module);

        PhyExamInfo = new JSONArray();

        Category = new JSONObject();
        try {
            Patient = new JSONObject(getIntent().getStringExtra("Patient"));
            Section2 = new JSONObject(Patient.get("Section 2").toString());
            Section1 = new JSONObject(Patient.get("Section 1").toString());
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        Button Finish = (Button)findViewById(R.id.finish);
        Finish.setOnClickListener(this);

        Name = (TextView)findViewById(R.id.Name);
        RegNo = (TextView)findViewById(R.id.RegNo);

        try {
            String NameHolder="Name : "+Section2.get("Name").toString();
            String RegHolder="Patient Id : "+Section1.get("PatientID").toString();
            Name.setText(NameHolder);
            RegNo.setText(RegHolder);
        }catch (JSONException e)
        {
            e.printStackTrace();
        }


        fragmentManager = getFragmentManager();

        categorySpinner = (Spinner) findViewById(R.id.category);
        subcategorySpinner = (Spinner) findViewById(R.id.subcategory);
        alreadyDiagnosedList = (ListView) findViewById(R.id.already_diagnosed_list);

        alreadyDiagnosedAdapter = new AlreadyDiagnosedListAdapter(this, R.drawable.red_cross, diagnosed);
        alreadyDiagnosedList.setAdapter(alreadyDiagnosedAdapter);

        //List<String> cat = Arrays.asList(getResources().getStringArray(R.array.main_category));
        //categorySpinner.setAdapter(new SelectedNoneSpinnerAdapter(this, cat));
        //categorySpinner.setSelection(cat.size() - 1);

        ArrayAdapter<CharSequence> dataAdapter1 = ArrayAdapter.createFromResource(this, R.array.main_category, android.R.layout.simple_spinner_item);
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(new NothingSelectedSpinnerAdapter(dataAdapter1, R.layout.spinner_no_category_selected, this));


        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                position=position-1;
                if(position != -1){
                    subcategorySpinner.setVisibility(View.VISIBLE);
                    setSubcategorySpinnerOptionsAndSelection(subcategorySpinner, position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent){

            }
        });

    }

    private void setSubcategorySpinnerOptionsAndSelection(Spinner subcategorySpinner, final int category){
        int[] subcategory = {R.array.pain_subcategory,
                R.array.swelling_subcategory,
                R.array.difficulty_in_breathing_subcategory,
                R.array.fever_subcategory,
                R.array.diarrhoea_subcategory,
                R.array.vomiting_subcategory,
                R.array.dizziness_subcategory,
                R.array.acidity_indigestion_subcategory,
                R.array.yellow_urine_subcategory,
                R.array.skin_rash_subcategory,
                R.array.bleeding_wth_stool_subcategory,
                R.array.gynae_problems_subcategory,
                R.array.injury_subcategory,
                R.array.boils__subcategory,
                R.array.ulcer_subcategory,
                R.array.palpitation_subcategory,
                R.array.fainting_subcategory,
                R.array.weakness_subcategory,
                R.array.cough_subcategory,
                R.array.common_cold_subcategory
        };

        List<String> options = Arrays.asList(getResources().getStringArray(subcategory[category]));
        subcategorySpinner.setAdapter(new SelectedNoneSpinnerAdapter(getApplicationContext(), options));
        subcategorySpinner.setSelection(options.size() - 1, false);

        subcategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int subcategory, long id){
                Fragment f = fragmentManager.findFragmentById(R.id.diagnosis);
                if(f != null){
                    Toast.makeText(ComplainModule.this, "Alert", Toast.LENGTH_SHORT).show();
                }

                TextView clicked = (TextView) view;
                onDiagnosisStart(clicked.getText().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent){

            }
        });
    }
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.finish) {

            try{
                if(Pain.length()!=0)
                    Category.put("Pain", Pain);

                if(Swelling.length()!=0)
                    Category.put("Swelling",Swelling);

                if(DifficultyBreathing.length()!=0)
                    Category.put("Difficulty in Breathing",DifficultyBreathing);

                if(Fever.length()!=0)
                    Category.put("Fever",Fever);

                if(Diarrhoea.length()!=0)
                    Category.put("Diarrhoea",Diarrhoea);

                if(Vomiting.length()!=0)
                    Category.put("Vomiting",Vomiting);

                if(Dizziness.length()!=0)
                    Category.put("Dizziness",Dizziness);

                if(AcidityIndigestion.length()!=0)
                    Category.put("Acidity or Indigestion",AcidityIndigestion);

                if(YellowUrine.length()!=0)
                    Category.put("Yellow Urine",YellowUrine);

                if(SkinRash.length()!=0)
                    Category.put("Skin Rash",SkinRash);

                if(BleedingStool.length()!=0)
                    Category.put("Bleeding with Stool",BleedingStool);

                if(GynaeProblems.length()!=0)
                    Category.put("Gynae Problems",GynaeProblems);

                if(Injury.length()!=0)
                    Category.put("Injury",Injury);

                if(Boils.length()!=0)
                    Category.put("Boils",Boils);

                if(Ulcer.length()!=0)
                    Category.put("Ulcer",Ulcer);

                if(Palpitation.length()!=0)
                    Category.put("Palpitation",Palpitation);

                if(Fainting.length()!=0)
                    Category.put("Fainting",Fainting);

                if(Weakness.length()!=0)
                    Category.put("Weakness",Weakness);

                if(Cough.length()!=0)
                    Category.put("Cough",Cough);

                if(CommonCold.length()!=0)
                    Category.put("Common Cold",CommonCold);

                Section3.put("Complaint",Category);
                Patient.put("Section 3",Section3);

            }catch (Exception e)
            {
                e.printStackTrace();
            }

            Intent i = new Intent(this,ObservationAndExamination.class);
            i.putExtra("ExamValue",PhyExamInfo.toString());
            i.putExtra("Patient",Patient.toString());
            startActivity(i);
        }
    }

    @Override
    public void communicate(Response response, JSONObject info){
        if(response == Response.BACK){

            Fragment f = fragmentManager.findFragmentById(R.id.diagnosis);
            if(f != null && f.isVisible()){
                Toast.makeText(ComplainModule.this, "Alert", Toast.LENGTH_SHORT).show();
            }

            onDiagnosisCancel();
        }else if(response == Response.RESET){
            onDiagnosisReset();
        }else if(response == Response.CONTINUE){
            onDiagnosisComplete(info);
        }
    }

    public void onDiagnosisStart(String tag){
        setToolBarTitle(tag);

        Diagnosis current;
        if(!completedFragments.containsKey(tag)){
            current = new Diagnosis(instantiateFragmentByTag(tag), new JSONObject());
        }else {
            current = completedFragments.get(tag);

            if(!current.fragment.isVisible())
            {
                Bundle bundle = new Bundle();
                bundle.putString("INFO", current.info.toString());
                current.fragment.setArguments(bundle);
            }
        }
        if(current.fragment!=null && !(current.fragment.isVisible()))
        {
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.replace(R.id.diagnosis, current.fragment, tag).commit();
        }
    }

    private void onDiagnosisCancel(){
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.remove(fragmentManager.findFragmentById(R.id.diagnosis)).commit();
        setToolBarTitle("Diagnosis");
    }

    private void onDiagnosisReset(){
        Fragment f = fragmentManager.findFragmentById(R.id.diagnosis);
        String tag = f.getTag();
        if(completedFragments.containsKey(tag)){
            Diagnosis d = completedFragments.get(tag);
            d.info = null;
            completedFragments.put(tag, d);
        }
    }

    private void onDiagnosisComplete(JSONObject info){
        setToolBarTitle("Diagnosis");
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment fragment = fragmentManager.findFragmentById(R.id.diagnosis);
        String tag = fragment.getTag();
        completedFragments.put(tag, new Diagnosis(fragment, info));
        saveInProperCategory(tag,info);
        ft.remove(fragment).commit();

        if(!diagnosed.contains(tag)){
            diagnosed.add(tag);
            alreadyDiagnosedAdapter.notifyDataSetChanged();
        }
    }

    public void deleteFragment(String tag){
        completedFragments.remove(tag);
        Category.remove(tag);
    }

    private void setToolBarTitle(String title){
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            setTitle(title);
        }
    }

    private Fragment instantiateFragmentByTag(String tag){
        if(tag.equals("Headache"))
        {
            if(!AlreadyInList("Head Exam"))
            {
                PhyExamInfo.put("Head Exam");
            }
            if(!AlreadyInList("Eye Exam"))
            {
                PhyExamInfo.put("Eye Exam");
            }
            return new Headache();
        }else if(tag.equals("Ear Pain"))
        {
            if(!AlreadyInList("Ear Exam"))
            {
                PhyExamInfo.put("Ear Exam");
            }
            return new EarPain();
        }else if(tag.equals("Teeth Pain")){
            if(!AlreadyInList("Mouth Exam")) {
                PhyExamInfo.put("Mouth Exam");
            }
            return new TeethPain();
        }else if(tag.equals("Throat Pain"))
        {
            if(!AlreadyInList("Neck Exam")) {
                PhyExamInfo.put("Neck Exam");
            }
            if(!AlreadyInList("Mouth Exam")) {
                PhyExamInfo.put("Mouth Exam");
            }
            return new ThroatPain();
        }else if(tag.equals("Back Pain"))
        {
            if(!AlreadyInList("Back Exam")) {
                PhyExamInfo.put("Back Exam");
            }
            if(!AlreadyInList("Leg Exam")) {
                PhyExamInfo.put("Leg Exam");
            }

            return new BackPain();
        }else if(tag.equals("Joint Pain"))
        {
            if(!AlreadyInList("Any location Exam")) {
                PhyExamInfo.put("Any location Exam");
            }
            if(!AlreadyInList("Leg Exam")) {
                PhyExamInfo.put("Leg Exam");
            }
            return new JointPain();
        }else if(tag.equals("Chest Pain"))
        {
            if(!AlreadyInList("Chest Exam")) {
                PhyExamInfo.put("Chest Exam");
            }
            return new ChestPain();
        }else if(tag.equals("Abdominal Pain")){
            if(!AlreadyInList("Abdomen Exam")) {
                PhyExamInfo.put("Abdomen Exam");
            }
            return new AbdominalPain();
        }else if(tag.equals("Breast Pain")){
            if(!AlreadyInList("Breast Exam")) {
                PhyExamInfo.put("Breast Exam");
            }
            return new BreastPain();
        }else if(tag.equals("Scrotal Pain")){
            if(!AlreadyInList("Scrotal Exam")) {
                PhyExamInfo.put("Scrotal Exam");
            }
            return new ScrotalPain();
        }else if(tag.equals("Perianal Pain"))
        {
            if(!AlreadyInList("Rectal Area Exam")) {
                PhyExamInfo.put("Rectal Area Exam");
            }
            return new PerianalPain();
        }else if(tag.equals("Abdomen Swelling"))
        {
            if(!AlreadyInList("Abdomen Exam"))
            {
                PhyExamInfo.put("Abdomen Exam");
            }
            return new AbdomenSwelling();

        }else if(tag.equals("Throat Swelling"))
        {
            if(!AlreadyInList("Mouth Exam")) {
                PhyExamInfo.put("Mouth Exam");
            }
            return new ThroatSwelling();
        }else if(tag.equals("Others Swelling"))
        {
            return new OtherSwelling();
        }else if(tag.equals("Acidity/Indigestion"))
        {
            if(!AlreadyInList("Abdomen Exam")) {
                PhyExamInfo.put("Abdomen Exam");
            }
            return new AcidityIndigestion();
        }else if(tag.equals("Difficulty in Breathing"))
        {
            if(!AlreadyInList("Face Exam")) {
                PhyExamInfo.put("Face Exam");
            }
            if(!AlreadyInList("Hand Exam")) {
                PhyExamInfo.put("Hand Exam");
            }
            if(!AlreadyInList("Chest Exam")) {
                PhyExamInfo.put("Chest Exam");
            }
            return new DifficultyBreathing();
        }else if(tag.equals("Diarrhoea"))
        {
            if(!AlreadyInList("Abdomen Exam")) {
                PhyExamInfo.put("Abdomen Exam");
            }
            return new Diarrhoea();
        }else if(tag.equals("Vomiting"))
        {
            if(!AlreadyInList("Abdomen Exam")) {
                PhyExamInfo.put("Abdomen Exam");
            }
            return new Vomiting();
        }else if(tag.equals("Dizziness"))
        {
            if(!AlreadyInList("Arm Exam")) {
                PhyExamInfo.put("Arm Exam");
            }
            if(!AlreadyInList("Chest Exam")) {
                PhyExamInfo.put("Chest Exam");
            }
            return new Dizziness();
        }else if(tag.equals("Yellow Urine"))
        {
            if(!AlreadyInList("Abdomen Exam")) {
                PhyExamInfo.put("Abdomen Exam");
            }
            return new YellowUrine();
        }else if(tag.equals("Skin Rash")){
            if(!AlreadyInList("Any location Exam")) {
                PhyExamInfo.put("Any location Exam");
            }
            return new SkinRash();
        }else if(tag.equals("Bleeding with Stool"))
        {
            if(!AlreadyInList("Rectal Area Exam")) {
                PhyExamInfo.put("Rectal Area Exam");
            }
            return new BleedingWithStool();
        }else if(tag.equals("Injury"))
        {
            if(!AlreadyInList("Head Exam"))
            {
                PhyExamInfo.put("Head Exam");
            }
            return new Injury();
        }else if(tag.equals("Common Cold")){
            return new CommonCold();
        }else if(tag.equals("Cough"))
        {
            if(!AlreadyInList("Chest Exam"))
            {
                PhyExamInfo.put("Chest Exam");
            }
            return new Cough();
        }else if(tag.equals("Fever"))
        {
            if(!AlreadyInList("Mouth Exam"))
            {
                PhyExamInfo.put("Mouth Exam");
            }
            if(!AlreadyInList("Chest Exam")) {
                PhyExamInfo.put("Chest Exam");
            }
            return new Fever();
        }else if(tag.equals("Boils")) {
            if(!AlreadyInList("Any location Exam")) {
                PhyExamInfo.put("Any location Exam");
            }
            return new Boils();
        }else if(tag.equals("Ulcer")) {
            if(!AlreadyInList("Any location Exam")) {
                PhyExamInfo.put("Any location Exam");
            }
            return new Ulcer();
        }else if(tag.equals("Palpitation"))
        {
            if(!AlreadyInList("Arm Exam")) {
                PhyExamInfo.put("Arm Exam");
            }
            return new Palpitation();
        }else if(tag.equals("Fainting"))
        {
            if(!AlreadyInList("Arm Exam")) {
                PhyExamInfo.put("Arm Exam");
            }
            return new Fainting();
        }else if(tag.equals("General Weakness"))
        {
            if(!AlreadyInList("Hand Exam")) {
                PhyExamInfo.put("Hand Exam");
            }
            return new GeneralWeakness();
        }else if(tag.equals("Particular Weakness"))
        {
            return new ParticularWeakness();
        }

        return null;
    }

    class Diagnosis{
        public Fragment fragment;
        public JSONObject info;

        public Diagnosis(Fragment fragment, JSONObject info){
            this.fragment = fragment;
            this.info = info;
        }
    }

    public boolean AlreadyInList(String Tag)
    {
        try{
            for(int i=0;i<PhyExamInfo.length();i++)
            {
                if(PhyExamInfo.get(i).equals(Tag))
                return true;
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }

    public void saveInProperCategory(String tag, JSONObject complain) {

        if(tag.equals("Headache") || tag.equals("Ear Pain") || tag.equals("Teeth Pain") || tag.equals("Throat Pain") || tag.equals("Back Pain") || tag.equals("Joint Pain") ||tag.equals("Chest Pain") ||tag.equals("Abdominal Pain") ||tag.equals("Breast Pain") ||tag.equals("Scrotal Pain") ||tag.equals("Perianal Pain"))
        {
            try{
                Pain.put(tag, complain);
            }catch(Exception e) {
                e.printStackTrace();
            }
        }
        else if (tag.equals("Throat Swelling") || tag.equals("Abdomen Swelling") || tag.equals("Others Swelling"))
        {
            try{
                Swelling.put(tag, complain);
            }catch(Exception e) {
                e.printStackTrace();
            }
        }
        else if(tag.equals("Difficulty in Breathing"))
        {
            try{
                DifficultyBreathing.put(tag, complain);
            }catch(Exception e) {
                e.printStackTrace();
            }
        }
        else if(tag.equals("Fever"))
        {
            try{
                Fever.put(tag, complain);
            }catch(Exception e) {
                e.printStackTrace();
            }
        }
        else if(tag.equals("Diarrhoea"))
        {
            try{
                Diarrhoea.put(tag, complain);
            }catch(Exception e) {
                e.printStackTrace();
            }
        }
        else if(tag.equals("Vomiting"))
        {
            try{
                Vomiting.put(tag, complain);
            }catch(Exception e) {
                e.printStackTrace();
            }
        }
        else if(tag.equals("Dizziness"))
        {
            try{
                Dizziness.put(tag, complain);
            }catch(Exception e) {
                e.printStackTrace();
            }
        }
        else if(tag.equals("Acidity/Indigestion"))
        {
            try{
                AcidityIndigestion.put(tag, complain);
            }catch(Exception e) {
                e.printStackTrace();
            }
        }
        else if(tag.equals("Yellow Urine"))
        {
            try{
                YellowUrine.put(tag, complain);
            }catch(Exception e) {
                e.printStackTrace();
            }
        }
        else if(tag.equals("Skin Rash"))
        {
            try{
                SkinRash.put(tag, complain);
            }catch(Exception e) {
                e.printStackTrace();
            }
        }
        else if(tag.equals("Bleeding with Stool"))
        {
            try{
                BleedingStool.put(tag, complain);
            }catch(Exception e) {
                e.printStackTrace();
            }
        }
        else if(tag.equals("Gynae Problems"))
        {
            try{
                GynaeProblems.put(tag, complain);
            }catch(Exception e) {
                e.printStackTrace();
            }
        }
        else if(tag.equals("Injury"))
        {
            try{
                Injury.put(tag, complain);
            }catch(Exception e) {
                e.printStackTrace();
            }
        }
        else if(tag.equals("Boils"))
        {
            try{
                Boils.put(tag, complain);
            }catch(Exception e) {
                e.printStackTrace();
            }
        }
        else if(tag.equals("Ulcer"))
        {
            try{
                Ulcer.put(tag, complain);
            }catch(Exception e) {
                e.printStackTrace();
            }
        }
        else if(tag.equals("Palpitation"))
        {
            try{
                Palpitation.put(tag, complain);
            }catch(Exception e) {
                e.printStackTrace();
            }
        }
        else if(tag.equals("Fainting")) {
            try {
                Fainting.put(tag, complain);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if(tag.equals("General Weakness")|| tag.equals("Particular Weakness"))
        {
            try{
                Weakness.put(tag, complain);
            }catch(Exception e) {
                e.printStackTrace();
            }
        }
        else if(tag.equals("Cough"))
        {
            try{
                Cough.put(tag, complain);
            }catch(Exception e) {
                e.printStackTrace();
            }
        }
        else if(tag.equals("Common Cold"))
        {
            try{
                CommonCold.put(tag, complain);
            }catch(Exception e) {
                e.printStackTrace();
            }
        }


    }
}