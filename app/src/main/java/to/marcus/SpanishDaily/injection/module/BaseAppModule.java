package to.marcus.SpanishDaily.injection.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import to.marcus.SpanishDaily.BaseApplication;
import to.marcus.SpanishDaily.model.AppPreferences;
import to.marcus.SpanishDaily.model.WordStorage;
import to.marcus.SpanishDaily.model.repository.Repository;
import to.marcus.SpanishDaily.model.repository.RepositoryImpl;

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
        return new WordStorage(mBaseApplication, mBaseApplication.isFirstRun());
    }

    @Provides @Singleton AppPreferences provideAppPreferences(){
        return new AppPreferences(mBaseApplication);
    }

    @Provides @Singleton Repository provideRepository(WordStorage wordStorage
            ,AppPreferences appPreferences){
        return new RepositoryImpl(wordStorage, appPreferences);
    }

}
