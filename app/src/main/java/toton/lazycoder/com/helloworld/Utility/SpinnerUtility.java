package toton.lazycoder.com.helloworld.Utility;

import android.app.Fragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import toton.lazycoder.com.helloworld.Diagnosis.NothingSelectedSpinnerAdapter;
import toton.lazycoder.com.helloworld.R;

/**
 * Created by ROY on 05-07-2016.
 */
public class SpinnerUtility {


    static public void SpinnerCreate(Fragment fragment, View view, final JSONObject info, int id, int array_id, final String tag)
    {
        Spinner spinner = (Spinner) view.findViewById(id);
        ArrayAdapter<CharSequence> dataAdapter = ArrayAdapter.createFromResource(fragment.getActivity(), array_id, android.R.layout.simple_spinner_item);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(new NothingSelectedSpinnerAdapter(dataAdapter, R.layout.spinner_row_nothing_selected, fragment.getActivity()));

        spinner.setOnItemSelectedListener(new OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(!(position==0))
                {
                    String item = parent.getItemAtPosition(position).toString();
                    try{
                        info.put(tag,item);
                    }catch(Exception e)
                    {
                        e.printStackTrace();
                    }
                    Toast.makeText(parent.getContext(),tag +" : "+ item, Toast.LENGTH_SHORT).show();
                }
            }
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });
    }



}
