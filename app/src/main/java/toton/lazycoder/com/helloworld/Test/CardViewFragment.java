package toton.lazycoder.com.helloworld.Test;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONException;
import org.json.JSONObject;

import toton.lazycoder.com.helloworld.R;
import toton.lazycoder.com.helloworld.Utility.DialogUtility;

public class CardViewFragment extends Fragment implements View.OnClickListener{

    private static final String INFO = "INFO";
    JSONObject info;


    @Override
    public void onClick(View v){
        if(v.getId() == R.id.durationAbdominalPain){
            DialogUtility.showNumberPickerDialog(getActivity(), info, "Duration", getString(R.string.duration));
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.card_view_fragment, container, false);

        view.findViewById(R.id.durationAbdominalPain).setOnClickListener(this);

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null && savedInstanceState.containsKey(INFO)){
            try{
                info = new JSONObject(savedInstanceState.getString(INFO));
            }catch(JSONException e){
                e.printStackTrace();
            }
        }else {
            info = new JSONObject();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putString(INFO, info.toString());
    }
}