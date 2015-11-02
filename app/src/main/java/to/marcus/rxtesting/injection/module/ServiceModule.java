package to.marcus.rxtesting.injection.module;

import android.content.Context;
import dagger.Module;
import dagger.Provides;

/**
 * Created by marcus on 10/30/2015
 */
@Module
public class ServiceModule {
    private final Context mContext;

    public ServiceModule(Context context){
        this.mContext = context;
    }

    @Provides
    Context provideServiceContext(){
        return mContext;
    }


}
