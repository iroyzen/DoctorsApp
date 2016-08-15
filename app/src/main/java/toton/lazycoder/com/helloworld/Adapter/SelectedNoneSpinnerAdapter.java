package toton.lazycoder.com.helloworld.Adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;

import java.util.List;

public class SelectedNoneSpinnerAdapter extends ArrayAdapter<String>{

    public SelectedNoneSpinnerAdapter(Context context, List<String> objects){
        super(context, android.R.layout.simple_spinner_item, objects);
        setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }

    @Override
    public int getCount(){
        int count = super.getCount();

        if(count == 0) return 0;
        else if(count == 1) return 1;
        else return  count - 1;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent){
        return super.getDropDownView(position, convertView, parent);
    }

    @Override
    public long getItemId(int position){
        return position;
    }
}
