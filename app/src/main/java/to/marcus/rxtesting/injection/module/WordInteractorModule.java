package to.marcus.rxtesting.injection.module;

import dagger.Module;
import dagger.Provides;
import to.marcus.rxtesting.data.interactor.WordInteractorImpl;
import to.marcus.rxtesting.injection.Activity;

/**
 * Created by marcus on 9/2/2015
 */
@Module
public class WordInteractorModule {

    @Provides @Activity
    WordInteractorImpl provideInteractorImpl(){
        return new WordInteractorImpl();
    }
}
