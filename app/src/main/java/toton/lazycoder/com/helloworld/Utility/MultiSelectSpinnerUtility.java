package toton.lazycoder.com.helloworld.Utility;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import toton.lazycoder.com.helloworld.R;

/**
 * Created by ROY on 09-07-2016.
 */
public class MultiSelectSpinnerUtility {

    final static String DESCRIBED = "OTHERS : ";
    final static int DESCRIBED_LENGTH = DESCRIBED.length();


    public static void showMultiSelectSpinner(final Context context, final JSONObject info, final String tag, final String title,
                                              final TextView Text,final int optionsID, final Integer noneIndex, final Integer allIndex, final Integer othersIndex){

        final List<String> options = Arrays.asList(getEnglishStringArray(context, optionsID));
        final boolean[] checked = new boolean[options.size()];
        String othersDescribed = null;

        try{
            JSONArray alreadySelected = info.getJSONArray(tag);
            if(alreadySelected != null){
                for (int i = 0; i < alreadySelected.length(); i++) {
                    int index = options.indexOf(alreadySelected.getString(i));
                    if(index == -1){
                        checked[othersIndex] = true;
                        othersDescribed = alreadySelected.getString(i);
                    }else {
                        checked[index] = true;
                    }
                }
            }
        }catch(JSONException e){
            e.printStackTrace();
        }

        final String described = othersDescribed;
        AlertDialog.Builder menu = new AlertDialog.Builder(context).setTitle(title);
        menu.setMultiChoiceItems(optionsID, null, null);
        menu.setPositiveButton(context.getString(R.string.done), new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which){
                AlertDialog alertDialog = (AlertDialog) dialog;
                final ListView listView = alertDialog.getListView();

                JSONArray otherInputs = new JSONArray();
                if(noneIndex >= 0 && listView.isItemChecked(noneIndex)){
                    otherInputs.put(options.get(noneIndex));
                }else {
                    for (int i = 0; i < options.size(); i++) {
                        if(listView.isItemChecked(i) && othersIndex != i){
                            otherInputs.put(options.get(i));
                        }
                    }
                }
                if(othersIndex >= 0 && listView.isItemChecked(othersIndex)){
                    getOthersInputFromEditText(context, info, tag, title, otherInputs, described);
                }else {
                    try{
                        info.put(tag, otherInputs);
                    }catch(JSONException e){
                        e.printStackTrace();
                    }
                }
                Toast.makeText(context, otherInputs.toString(), Toast.LENGTH_SHORT).show();
                Text.setText("[ Selected ]");
            }

            private void getOthersInputFromEditText(final Context context, final JSONObject info, final String tag, final String title, final JSONArray otherInputs, final String described){
                AlertDialog.Builder menu = new AlertDialog.Builder(context).setTitle(title);

                final EditText editText = new EditText(context);
                if(described != null){
                    editText.setText(described.substring(DESCRIBED_LENGTH));
                }
                menu.setView(editText);

                menu.setPositiveButton(context.getString(R.string.done), new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        String input = editText.getText().toString();
                        if(input.length() > 0){
                            otherInputs.put(DESCRIBED + input);
                        }
                        if(otherInputs.length() > 0){
                            try{
                                info.put(tag, otherInputs);
                            }catch(JSONException e){
                                e.printStackTrace();
                            }
                        }else {
                            info.remove(tag);
                        }
                    }
                });

                menu.setNegativeButton(context.getString(R.string.cancel), new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                    }
                });

                menu.create().show();
            }
        });
        menu.setNegativeButton(context.getString(R.string.cancel), new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which){
            }
        });
        AlertDialog alertDialog = menu.create();
        alertDialog.show();

        final ListView listView = alertDialog.getListView();
        for (int i = 0; i < options.size(); i++) {
            listView.setItemChecked(i, checked[i]);
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                if(position == noneIndex && listView.isItemChecked(position)){
                    for (int i = 0; i < options.size(); i++) {
                        listView.setItemChecked(i, false);
                    }
                    listView.setItemChecked(noneIndex, true);
                }else if(position == allIndex){
                    boolean isChecked = listView.isItemChecked(position);
                    for (int i = 0; i < options.size(); i++) {
                        if(i != noneIndex && i != othersIndex){
                            listView.setItemChecked(i, isChecked);
                        }
                    }
                    if(isChecked){
                        listView.setItemChecked(noneIndex, false);
                    }else {
                        listView.setItemChecked(noneIndex, isAllUnChecked());
                    }
                }else if(position == othersIndex){
                    if(listView.isItemChecked(position)){
                        listView.setItemChecked(noneIndex, false);
                    }else {
                        listView.setItemChecked(noneIndex, isAllUnChecked());
                    }
                }else {
                    boolean isChecked = listView.isItemChecked(position);
                    if(isChecked){
                        listView.setItemChecked(noneIndex, false);
                        listView.setItemChecked(allIndex, isAllChecked());
                    }else {
                        listView.setItemChecked(allIndex, false);
                        listView.setItemChecked(noneIndex, isAllUnChecked());
                    }
                }
            }

            private boolean isAllUnChecked(){
                boolean noneChecked = false;
                for (int i = 0; i < options.size(); i++) {
                    if(i != noneIndex){
                        noneChecked = noneChecked || listView.isItemChecked(i);
                    }
                }
                return !noneChecked;
            }

            private boolean isAllChecked(){
                boolean checked = true;
                for (int i = 0; i < options.size(); i++) {
                    if(i != noneIndex && i != othersIndex && i != allIndex){
                        checked = checked && listView.isItemChecked(i);
                    }
                }
                return checked;
            }
        });
    }
    private static String[] getEnglishStringArray(Context context, int arrayID){
        Resources resources = context.getResources();
        Configuration config = new Configuration(resources.getConfiguration());
        String language = config.locale.getLanguage();
        config.locale = new Locale("en");
        resources = new Resources(context.getAssets(), new DisplayMetrics(), config);
        String[] array = resources.getStringArray(arrayID);
        config.locale = new Locale(language);
        resources = new Resources(context.getAssets(), new DisplayMetrics(), config);

        return array;
    }
}
