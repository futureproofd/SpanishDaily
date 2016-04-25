package to.marcus.SpanishDaily.injection.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import to.marcus.SpanishDaily.injection.Activity;

/**
 * Created by marcus on 9/2/2015
 */
@Module
public class ActivityModule {

    private final Context mContext;

    public ActivityModule(Context context){
        this.mContext = context;
    }

    @Provides
    @Activity
    Context provideActivityContext(){
        return mContext;
    }
}
