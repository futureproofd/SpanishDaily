package to.marcus.rxtesting.data.cache;

import android.content.Context;
import com.squareup.picasso.Downloader;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

/**
 * Created by marcus on 2/8/2016
 * To override the default caching policy of Picasso.with instance
 * Also used to test debugging with setIndicatiorsEnabled
 */
public class PicassoCache{

    private static Picasso sInstance = null;

    private PicassoCache(Context context){
        Downloader downloader = new OkHttpDownloader(context, Integer.MAX_VALUE);
        Picasso.Builder builder = new Picasso.Builder(context);
        builder.downloader(downloader);
        sInstance = builder.build();
        sInstance.setIndicatorsEnabled(true);
    }

    public static Picasso getPicassoInstance(Context context){
        if(sInstance == null){
            new PicassoCache(context);
            return sInstance;
        }
        return sInstance;
    }
}
