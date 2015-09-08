package to.marcus.rxtesting.injection.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import to.marcus.rxtesting.BaseApplication;
import to.marcus.rxtesting.model.repository.Repository;
import to.marcus.rxtesting.model.repository.RepositoryImpl;

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

    @Provides @Singleton Repository provideRepository(RepositoryImpl repository){return repository;}

}
