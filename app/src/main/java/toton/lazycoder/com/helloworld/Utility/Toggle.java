package toton.lazycoder.com.helloworld.Utility;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import toton.lazycoder.com.helloworld.R;

/**
 * Created by ROY on 25-08-2016.
 */
public class Toggle {

    public static void toggle_contents(Context context, LinearLayout contents)
    {
        if(contents.isShown()){
            slide_up(context, contents);
            contents.setVisibility(View.GONE);
        }
        else{
            contents.setVisibility(View.VISIBLE);
            slide_down(context, contents);
        }
    }

    public static void toggle_relative_contents(Context context, RelativeLayout contents)
    {
        if(contents.isShown()){
            slide_up(context, contents);
            contents.setVisibility(View.GONE);
        }
        else{
            contents.setVisibility(View.VISIBLE);
            slide_down(context, contents);
        }
    }

    public static void slide_down(Context ctx, View v){

        Animation a = AnimationUtils.loadAnimation(ctx, R.anim.slide_down);
        if(a != null){
            a.reset();
            if(v != null){
                v.clearAnimation();
                v.startAnimation(a);
            }
        }
    }

    public static void slide_up(Context ctx, View v){

        Animation a = AnimationUtils.loadAnimation(ctx, R.anim.slide_up);
        if(a != null){
            a.reset();
            if(v != null){
                v.clearAnimation();
                v.startAnimation(a);
            }
        }
    }
}
