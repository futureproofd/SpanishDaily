package to.marcus.rxtesting.util;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.DrawerLayout;
import android.widget.ImageView;
import java.io.ByteArrayOutputStream;

import to.marcus.rxtesting.R;

/**
 * Created by marcus on 10/16/2015
 */
public class ImageUtility {

    public static ByteArrayOutputStream getImage(ImageView imageView){
        //todo if the imageView is unavailable, check for null
        Drawable mDrawable = imageView.getDrawable();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        ((BitmapDrawable)mDrawable).getBitmap().compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream;
    }

    public static ByteArrayOutputStream convertImage(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream;
    }
}
