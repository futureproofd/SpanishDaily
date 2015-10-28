package to.marcus.rxtesting.injection.module;

import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import to.marcus.rxtesting.BaseApplication;
import to.marcus.rxtesting.model.AppPreferences;
import to.marcus.rxtesting.model.WordStorage;
import to.marcus.rxtesting.model.repository.Repository;
import to.marcus.rxtesting.model.repository.RepositoryImpl;

/**
 * Created by marcus on 9/2/2015
 */

@Module
public class BaseAppModule {

    private final BaseApplication mBaseApplication;

    public BaseAppModule(BaseApplication baseApplication) {
        this.mBaseApplication = baseApplication;
    }

    @Provides @Singleton
    BaseApplication provideBaseApplicationContext() {
        return mBaseApplication;
    }

    @Provides @Singleton WordStorage provideWordStorage(){
        return new WordStorage(mBaseApplication);
    }

    @Provides @Singleton AppPreferences provideAppPreferences(){
        return new AppPreferences(mBaseApplication);
    }

    @Provides @Singleton Repository provideRepository(WordStorage wordStorage
            ,AppPreferences appPreferences){
        return new RepositoryImpl(wordStorage, appPreferences);
    }

}
