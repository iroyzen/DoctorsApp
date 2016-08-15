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

/**
 * Created by ROY on 10-07-2016.
 */
public class DocumentPicsListAdapter extends BaseAdapter {
    List<String> docs;
    Context context;
    Bitmap deleteBitmap;

    public DocumentPicsListAdapter(Context context, int icon, List<String> diseases){
        deleteBitmap = BitmapFactory.decodeResource(context.getResources(), icon);
        this.docs = diseases;
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
        return docs.size();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent){

        final ViewHolder holder;

        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.document_pics_list, null);

            holder = new ViewHolder();
            holder.docs = (TextView) convertView.findViewById(R.id.docs);
            holder.delete = (ImageView) convertView.findViewById(R.id.delete);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.docs.setText(docs.get(position));
        holder.delete.setImageBitmap(deleteBitmap);

        holder.docs.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

            }
        });

        return convertView;
    }

    static class ViewHolder{
        TextView docs;
        ImageView delete;
    }
}
