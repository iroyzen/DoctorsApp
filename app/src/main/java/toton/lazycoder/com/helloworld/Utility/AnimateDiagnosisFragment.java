package toton.lazycoder.com.helloworld.Utility;

import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;

import toton.lazycoder.com.helloworld.R;

public class AnimateDiagnosisFragment{

    public static void animateViewsIn(Context context, View parentView, int rootID){

        if(hasMinSdkVersion(context, 21)){
            ViewGroup root = (ViewGroup) parentView.findViewById(rootID);

            int count = root.getChildCount();
            float offset = context.getResources().getDimensionPixelSize(R.dimen.offset_y);
            Interpolator interpolator = AnimationUtils.loadInterpolator(context, android.R.interpolator.linear_out_slow_in);

            for (int i = 0; i < count; i++) {
                View v = root.getChildAt(i);
                v.setVisibility(View.VISIBLE);
                v.setTranslationY(offset);
                v.setAlpha(0.85f);

                v.animate()
                        .translationY(0f)
                        .alpha(1f)
                        .setInterpolator(interpolator)
                        .setDuration(1000L)
                        .start();

                offset *= 1.5f;
            }
        }
    }

    private static boolean hasMinSdkVersion(Context context, int requiredMinSdkVersion){
        return Build.VERSION.SDK_INT >= requiredMinSdkVersion;
    }
}
