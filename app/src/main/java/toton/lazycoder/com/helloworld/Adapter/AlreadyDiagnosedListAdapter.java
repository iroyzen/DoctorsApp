package toton.lazycoder.com.helloworld.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import toton.lazycoder.com.helloworld.ComplainModule;
import toton.lazycoder.com.helloworld.R;

public class AlreadyDiagnosedListAdapter extends BaseAdapter{

    List<String> diseases;
    Context context;
    Bitmap deleteBitmap;

    public AlreadyDiagnosedListAdapter(Context context, int icon, List<String> diseases){
        deleteBitmap = BitmapFactory.decodeResource(context.getResources(), icon);
        this.diseases = diseases;
        this.context = context;
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public Object getItem(int position){
        return position;
    }

    @Override
    public int getCount(){
        return diseases.size();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent){

        final ViewHolder holder;

        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.already_diagnosed_item, null);

            holder = new ViewHolder();
            holder.disease = (TextView) convertView.findViewById(R.id.disease);
            holder.delete = (ImageView) convertView.findViewById(R.id.delete);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.disease.setText(diseases.get(position));
        holder.delete.setImageBitmap(deleteBitmap);

        holder.disease.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                ComplainModule complainModule = (ComplainModule) context;
                String tag = holder.disease.getText().toString();
                complainModule.onDiagnosisStart(tag);
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // ToDo : Show confirmation dialog instead of Toast
                Toast.makeText(context, "Alert", Toast.LENGTH_SHORT).show();
                diseases.remove(position);
                ComplainModule complainModule = (ComplainModule) context;
                complainModule.deleteFragment(holder.disease.getText().toString());
                notifyDataSetInvalidated();
            }
        });

        return convertView;
    }

    static class ViewHolder{
        TextView disease;
        ImageView delete;
    }
}