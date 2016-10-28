package toton.lazycoder.com.helloworld.GeneralObservation;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import org.json.JSONException;
import org.json.JSONObject;

import toton.lazycoder.com.helloworld.Diagnosis.Communicator;
import toton.lazycoder.com.helloworld.ObservationAndExamination;
import toton.lazycoder.com.helloworld.R;
import toton.lazycoder.com.helloworld.Utility.Toggle;

import static android.view.View.GONE;


public class FamilyHistory extends Fragment implements View.OnClickListener{


    JSONObject familyHistory;
    JSONObject Mother;
    JSONObject Father;
    JSONObject Brother;
    JSONObject Sister;
    JSONObject Other;
    

    RelativeLayout MotherHistory;
    RelativeLayout FatherHistory;
    RelativeLayout BrotherHistory;
    RelativeLayout SisterHistory;
    RelativeLayout OtherHistory;

    View view;
    
    public FamilyHistory() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        familyHistory = new JSONObject();
        Mother = new JSONObject();
        Father = new JSONObject();
        Brother = new JSONObject();
        Sister = new JSONObject();
        Other = new JSONObject();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_family_history, container, false);
        
        view.findViewById(R.id.button_Mother).setOnClickListener(this);
        view.findViewById(R.id.button_Father).setOnClickListener(this);
        view.findViewById(R.id.button_Brother).setOnClickListener(this);
        view.findViewById(R.id.button_Sister).setOnClickListener(this);
        view.findViewById(R.id.button_Other).setOnClickListener(this);
        view.findViewById(R.id.Next_Button).setOnClickListener(this);
        
        MotherHistory = (RelativeLayout)view.findViewById(R.id.Mother_History);
        FatherHistory = (RelativeLayout)view.findViewById(R.id.Father_History);
        BrotherHistory = (RelativeLayout)view.findViewById(R.id.Brother_History);
        SisterHistory = (RelativeLayout)view.findViewById(R.id.Sister_History);
        OtherHistory = (RelativeLayout)view.findViewById(R.id.Other_History);
        
        MotherHistory.setVisibility(GONE);
        FatherHistory.setVisibility(GONE);
        BrotherHistory.setVisibility(GONE);
        SisterHistory.setVisibility(GONE);
        OtherHistory.setVisibility(GONE);
        
        return view;
    }

    public  void onClick(View NewView)
    {
        ObservationAndExamination activity = (ObservationAndExamination)getActivity();
        int id=NewView.getId();

        switch(id)
        {
            case R.id.Next_Button :
            {
                try{

                    //Family Medical History
                    if(((CheckBox)view.findViewById(R.id.Mother_BP)).isChecked())
                    {
                        Mother.put("High Blood Pressure","Yes");
                    }else
                    {
                        Mother.put("High Blood Pressure",null);
                    }

                    if(((CheckBox)view.findViewById(R.id.Mother_HeartAttack)).isChecked())
                    {
                        Mother.put("Heart Attack","Yes");
                    }else
                    {
                        Mother.put("Heart Attack",null);
                    }

                    if(((CheckBox)view.findViewById(R.id.Mother_Stroke)).isChecked())
                    {
                        Mother.put("Stroke","Yes");
                    }else
                    {
                        Mother.put("Stroke",null);
                    }

                    if(((CheckBox)view.findViewById(R.id.Mother_Diabetes)).isChecked())
                    {
                        Mother.put("Diabetes","Yes");
                    }else
                    {
                        Mother.put("Diabetes",null);
                    }

                    if(((CheckBox)view.findViewById(R.id.Mother_Asthma)).isChecked())
                    {
                        Mother.put("Asthma","Yes");
                    }else
                    {
                        Mother.put("Asthma",null);
                    }

                    if(((CheckBox)view.findViewById(R.id.Mother_TB)).isChecked())
                    {
                        Mother.put("TB","Yes");
                    }else
                    {
                        Mother.put("TB",null);
                    }

                    if(((CheckBox)view.findViewById(R.id.Mother_Cancer)).isChecked())
                    {
                        Mother.put("Cancer","Yes");
                    }else
                    {
                        Mother.put("Cancer",null);
                    }
                    if(((EditText)view.findViewById(R.id.editText_MotherOther)).getText().toString().length()!=0)
                    Mother.put("Other",((EditText)view.findViewById(R.id.editText_MotherOther)).getText().toString());
                    else
                    Mother.put("Other",null);

                    //Father's Medical History
                    if(((CheckBox)view.findViewById(R.id.Father_BP)).isChecked())
                    {
                        Father.put("High Blood Pressure","Yes");
                    }else
                    {
                        Father.put("High Blood Pressure",null);
                    }

                    if(((CheckBox)view.findViewById(R.id.Father_HeartAttack)).isChecked())
                    {
                        Father.put("Heart Attack","Yes");
                    }else
                    {
                        Father.put("Heart Attack",null);
                    }

                    if(((CheckBox)view.findViewById(R.id.Father_Stroke)).isChecked())
                    {
                        Father.put("Stroke","Yes");
                    }else
                    {
                        Father.put("Stroke",null);
                    }

                    if(((CheckBox)view.findViewById(R.id.Father_Diabetes)).isChecked())
                    {
                        Father.put("Diabetes","Yes");
                    }else
                    {
                        Father.put("Diabetes",null);
                    }

                    if(((CheckBox)view.findViewById(R.id.Father_Asthma)).isChecked())
                    {
                        Father.put("Asthma","Yes");
                    }else
                    {
                        Father.put("Asthma",null);
                    }

                    if(((CheckBox)view.findViewById(R.id.Father_TB)).isChecked())
                    {
                        Father.put("TB","Yes");
                    }else
                    {
                        Father.put("TB",null);
                    }

                    if(((CheckBox)view.findViewById(R.id.Father_Cancer)).isChecked())
                    {
                        Father.put("Cancer","Yes");
                    }else
                    {
                        Father.put("Cancer",null);
                    }

                    if(((EditText)view.findViewById(R.id.editText_FatherOther)).getText().toString().length()!=0)
                    Father.put("Other",((EditText)view.findViewById(R.id.editText_FatherOther)).getText().toString());
                    else
                    Father.put("Other",null);

                    //Brother's Medical History
                    if(((CheckBox)view.findViewById(R.id.Brother_BP)).isChecked())
                    {
                        Brother.put("High Blood Pressure","Yes");
                    }else
                    {
                        Brother.put("High Blood Pressure",null);
                    }

                    if(((CheckBox)view.findViewById(R.id.Brother_HeartAttack)).isChecked())
                    {
                        Brother.put("Heart Attack","Yes");
                    }else
                    {
                        Brother.put("Heart Attack",null);
                    }

                    if(((CheckBox)view.findViewById(R.id.Brother_Stroke)).isChecked())
                    {
                        Brother.put("Stroke","Yes");
                    }else
                    {
                        Brother.put("Stroke",null);
                    }

                    if(((CheckBox)view.findViewById(R.id.Brother_Diabetes)).isChecked())
                    {
                        Brother.put("Diabetes","Yes");
                    }else
                    {
                        Brother.put("Diabetes",null);
                    }

                    if(((CheckBox)view.findViewById(R.id.Brother_Asthma)).isChecked())
                    {
                        Brother.put("Asthma","Yes");
                    }else
                    {
                        Brother.put("Asthma",null);
                    }

                    if(((CheckBox)view.findViewById(R.id.Brother_TB)).isChecked())
                    {
                        Brother.put("TB","Yes");
                    }else
                    {
                        Brother.put("TB",null);
                    }

                    if(((CheckBox)view.findViewById(R.id.Brother_Cancer)).isChecked())
                    {
                        Brother.put("Cancer","Yes");
                    }else
                    {
                        Brother.put("Cancer",null);
                    }

                    if(((EditText)view.findViewById(R.id.editText_BrotherOther)).getText().toString().length()!=0)
                    Brother.put("Other",((EditText)view.findViewById(R.id.editText_BrotherOther)).getText().toString());
                    else
                    Brother.put("Other",null);

                    //Sister's Medical History
                    if(((CheckBox)view.findViewById(R.id.Sister_BP)).isChecked())
                    {
                        Sister.put("High Blood Pressure","Yes");
                    }else
                    {
                        Sister.put("High Blood Pressure",null);
                    }

                    if(((CheckBox)view.findViewById(R.id.Sister_HeartAttack)).isChecked())
                    {
                        Sister.put("Heart Attack","Yes");
                    }else
                    {
                        Sister.put("Heart Attack",null);
                    }

                    if(((CheckBox)view.findViewById(R.id.Sister_Stroke)).isChecked())
                    {
                        Sister.put("Stroke","Yes");
                    }else
                    {
                        Sister.put("Stroke",null);
                    }

                    if(((CheckBox)view.findViewById(R.id.Sister_Diabetes)).isChecked())
                    {
                        Sister.put("Diabetes","Yes");
                    }else
                    {
                        Sister.put("Diabetes",null);
                    }

                    if(((CheckBox)view.findViewById(R.id.Sister_Asthma)).isChecked())
                    {
                        Sister.put("Asthma","Yes");
                    }else
                    {
                        Sister.put("Asthma",null);
                    }

                    if(((CheckBox)view.findViewById(R.id.Sister_TB)).isChecked())
                    {
                        Sister.put("TB","Yes");
                    }else
                    {
                        Sister.put("TB",null);
                    }

                    if(((CheckBox)view.findViewById(R.id.Sister_Cancer)).isChecked())
                    {
                        Sister.put("Cancer","Yes");
                    }else
                    {
                        Sister.put("Cancer",null);
                    }

                    if(((EditText)view.findViewById(R.id.editText_SisterOther)).getText().toString().length()!=0)
                    Sister.put("Other",((EditText)view.findViewById(R.id.editText_SisterOther)).getText().toString());
                    else
                    Sister.put("Other",null);

                    //Other's Medical History
                    if(((CheckBox)view.findViewById(R.id.Other_BP)).isChecked())
                    {
                        Other.put("High Blood Pressure","Yes");
                    }else
                    {
                        Other.put("High Blood Pressure",null);
                    }

                    if(((CheckBox)view.findViewById(R.id.Other_HeartAttack)).isChecked())
                    {
                        Other.put("Heart Attack","Yes");
                    }else
                    {
                        Other.put("Heart Attack",null);
                    }

                    if(((CheckBox)view.findViewById(R.id.Other_Stroke)).isChecked())
                    {
                        Other.put("Stroke","Yes");
                    }else
                    {
                        Other.put("Stroke",null);
                    }

                    if(((CheckBox)view.findViewById(R.id.Other_Diabetes)).isChecked())
                    {
                        Other.put("Diabetes","Yes");
                    }else
                    {
                        Other.put("Diabetes",null);
                    }

                    if(((CheckBox)view.findViewById(R.id.Other_Asthma)).isChecked())
                    {
                        Other.put("Asthma","Yes");
                    }else
                    {
                        Other.put("Asthma",null);
                    }

                    if(((CheckBox)view.findViewById(R.id.Other_TB)).isChecked())
                    {
                        Other.put("TB","Yes");
                    }else
                    {
                        Other.put("TB",null);
                    }

                    if(((CheckBox)view.findViewById(R.id.Other_Cancer)).isChecked())
                    {
                        Other.put("Cancer","Yes");
                    }else
                    {
                        Other.put("Cancer",null);
                    }
                    if(((EditText)view.findViewById(R.id.editText_OtherOther)).getText().toString().length()!=0)
                    Other.put("Other",((EditText)view.findViewById(R.id.editText_OtherOther)).getText().toString());
                    else
                    Other.put("Other",null);

                    Other.put("Relation",((EditText)view.findViewById(R.id.editText_OtherRelation)).getText().toString());

                    familyHistory.put("Mother",Mother);
                    familyHistory.put("Father",Father);
                    familyHistory.put("Sister",Sister);
                    familyHistory.put("Brother",Brother);
                    familyHistory.put("Other",Other);

                }catch(JSONException e)
                {
                    e.printStackTrace();
                }
                
                activity.communicate(Communicator.Response.CONTINUE, familyHistory, 2);
                break;
            }

            case R.id.button_Mother :
            {
                Toggle.toggle_relative_contents(getActivity(),MotherHistory);
                break;
            }

            case R.id.button_Father :
            {
                Toggle.toggle_relative_contents(getActivity(),FatherHistory);
                break;
            }

            case R.id.button_Brother :
            {
                Toggle.toggle_relative_contents(getActivity(),BrotherHistory);
                break;
            }

            case R.id.button_Sister:
            {
                Toggle.toggle_relative_contents(getActivity(),SisterHistory);
                break;
            }

            case R.id.button_Other :
            {
                Toggle.toggle_relative_contents(getActivity(),OtherHistory);
                break;
            }
        }
    }
}
