package to.marcus.rxtesting.injection.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import to.marcus.rxtesting.BaseApplication;

/**
 * Created by marcus on 9/2/2015
 */

@Module
public class ApplicationModule {

    private final BaseApplication mBaseApplication;

    public ApplicationModule(BaseApplication baseApplication){
        this.mBaseApplication = baseApplication;
    }

    @Provides @Singleton BaseApplication provideBaseApplicationContext(){return mBaseApplication;}
}
