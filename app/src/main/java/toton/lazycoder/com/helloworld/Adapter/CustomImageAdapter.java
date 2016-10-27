package toton.lazycoder.com.helloworld.Adapter;

/**
 * Created by ROY on 25-09-2016.
 */
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import toton.lazycoder.com.helloworld.R;
import toton.lazycoder.com.helloworld.ObservationAndExamination;
import toton.lazycoder.com.helloworld.Adapter.GetSet;

import java.io.File;
import java.util.List;

public class CustomImageAdapter extends BaseAdapter {

    List<GetSet> _data;
    Context _c;
    ViewHolder v;

    public CustomImageAdapter(List<GetSet> getData, Context context) {
        _data = getData;
        _c = context;
    }

    @Override
    public int getCount() {
        return _data.size();
    }

    @Override
    public Object getItem(int position) {
        return _data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            LayoutInflater li = (LayoutInflater) _c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = li.inflate(R.layout.document_pics_list, null);
        } else {
            view = convertView;
        }

        v = new ViewHolder();
        v.removeImage = (ImageButton) view.findViewById(R.id.cancel);
        v.parcelName = (TextView) view.findViewById(R.id.parcelName);
        //v.label = (TextView) view.findViewById(R.id.imageFor);
        v.imageView = (ImageView) view.findViewById(R.id.imgPrv);

        // Set data in listView
        final GetSet dataSet = (GetSet) _data.get(position);

        dataSet.setListItemPosition(position);

        if (!dataSet.isHaveImage()) {
            Bitmap icon = BitmapFactory.decodeResource(_c.getResources(), R.mipmap.ic_launcher);
            v.imageView.setImageBitmap(icon);
        } else {
            v.imageView.setImageBitmap(dataSet.getImage());
        }
        v.parcelName.setText(dataSet.getLabel());
        //v.label.setText(dataSet.getSubtext());
        if (dataSet.isStatus()) {
            v.removeImage.setVisibility(View.GONE);
        } else {
            v.removeImage.setVisibility(View.VISIBLE);
        }

        v.removeImage.setFocusable(false);


        v.imageView.setOnClickListener(new View.OnClickListener(){
        @Override
            public void onClick(View v)
            {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.parse("file://" + "/storage/sdcard0/Android/Doctor/"+ dataSet.getLabel()), "image/*");
                _c.startActivity(intent);
            }

        });

        v.removeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //dataSet.setStatus(true);
                //dataSet.setHaveImage(false);
                File file=new File("/storage/sdcard0/Android/Doctor/"+ dataSet.getLabel());
                boolean delete = file.delete();
                _data.remove(position);
                notifyDataSetChanged();
            }
        });


        return view;
    }

    /**
     * @param position Get position of of object
     * @param imageSrc set image in imageView
     */
    public void setImageInItem(int position, Bitmap imageSrc, String imagePath) {
        GetSet dataSet = (GetSet) _data.get(position);
        dataSet.setImage(imageSrc);
        dataSet.setStatus(false);
        dataSet.setHaveImage(true);
        notifyDataSetChanged();
    }

    static class ViewHolder {
        ImageView imageView;
        TextView label, parcelName;
        ImageButton removeImage;
    }

}
