package to.marcus.rxtesting.util;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import java.io.ByteArrayOutputStream;

/**
 * Created by marcus on 10/16/2015
 */
public class ImageUtility {

    public static ByteArrayOutputStream getImage(ImageView imageView){
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
