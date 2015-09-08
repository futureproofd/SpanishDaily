package to.marcus.rxtesting.injection.module;

import dagger.Module;
import dagger.Provides;
import to.marcus.rxtesting.data.interactor.InteractorImpl;
import to.marcus.rxtesting.injection.Activity;
import to.marcus.rxtesting.model.repository.Repository;

/**
 * Created by marcus on 9/2/2015
 */
@Module
public class WordInteractorModule {

    public WordInteractorModule(){

    }

    //some model data stuff
    //such as the getChracterInformatonUseCase
        //the getCharacterInformationUseCase class will have @Inject on the constructor
    //this module is then added to the Activity class through the initInjector()

    /*
    @Provides @Activity
    WebParser provideWebParser(){
        return new WebParser();
    }

    */

    @Provides @Activity
    InteractorImpl provideInteractorImpl(Repository repository){
        return new InteractorImpl(repository);
    }
}
