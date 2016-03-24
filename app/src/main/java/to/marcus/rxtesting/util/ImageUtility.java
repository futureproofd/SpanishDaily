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
    private final int MEAN_WIDTH = 380;
    private final int MEAN_HEIGHT = 265;
    private final int DOUBLE_AVG_SIZE = 802800;

    public static ByteArrayOutputStream getImage(ImageView imageView){
        //todo if the imageView is unavailable, check for null
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
        if(data.getAllocationByteCount() > DOUBLE_AVG_SIZE){
            return Bitmap.createScaledBitmap(data, MEAN_WIDTH, MEAN_HEIGHT, true);
        }else{
            return data;
        }
    }

}
