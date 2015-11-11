package to.marcus.rxtesting.util;

import android.annotation.TargetApi;
import android.os.Build;
import android.transition.Fade;
import android.transition.Transition;

/**
 * Created by marcus on 11/9/2015
 */
public class TransitionUtility {

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static Transition makeEnterTransition(){
        Transition fade = new Fade();
        fade.excludeTarget(android.R.id.navigationBarBackground, true);
        fade.excludeTarget(android.R.id.statusBarBackground, true);
        return fade;
    }
}

