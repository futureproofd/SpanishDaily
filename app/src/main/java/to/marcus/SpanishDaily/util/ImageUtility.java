package to.marcus.SpanishDaily.util;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

/**
 * Created by marcus on 10/16/2015
 */
public class ImageUtility {
    private static final int MEAN_WIDTH = 380;
    private static final int MEAN_HEIGHT = 265;
    private static final int DOUBLE_AVG_SIZE = 802800;

    public static ByteArrayOutputStream getImage(ImageView imageView){
        Drawable mDrawable = imageView.getDrawable();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        checkSize(((BitmapDrawable)mDrawable).getBitmap()).compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream;
    }

    public static ByteArrayOutputStream convertImage(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        checkSize(bitmap).compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream;
    }

    //Scale image if danger of transactionTooLargeException
    private static Bitmap checkSize(Bitmap data){
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT){
            if(data.getByteCount() > DOUBLE_AVG_SIZE)
                data = Bitmap.createScaledBitmap(data, MEAN_WIDTH, MEAN_HEIGHT, true);
        }else if((Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) && (data.getAllocationByteCount() > DOUBLE_AVG_SIZE)){
                data = Bitmap.createScaledBitmap(data, MEAN_WIDTH, MEAN_HEIGHT, true);
        }
        return data;
    }

}
