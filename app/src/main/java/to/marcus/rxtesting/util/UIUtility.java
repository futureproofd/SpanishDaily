package to.marcus.rxtesting.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by marcus on 10/16/2015
 */
public class UIUtility {

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void setTranslucentStatusBar(Activity activity){
        Window w = activity.getWindow();
        w.setFlags(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        w.setFlags(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }

    public static int convertBoolean(boolean b){
        return (b) ? 2 : 1;
    }

    //If there is more than one example (two "." occurrences)
    public static String formatStringByExample(String example){
        if(example.length() - example.replace(".", "").length() > 1){
            return example.replaceFirst("[.]","\n\n");
        }else{
            return example;
        }
    }

}
