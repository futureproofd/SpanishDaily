package to.marcus.rxtesting.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by marcus on 10/16/2015.
 */
public class ToastUtility {

    public static void notify(Context context, String notification){
        Toast.makeText(context, notification, Toast.LENGTH_SHORT).show();
    }
}
