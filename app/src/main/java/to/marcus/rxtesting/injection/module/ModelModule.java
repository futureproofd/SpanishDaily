package to.marcus.rxtesting.injection.module;

import dagger.Module;
import dagger.Provides;
import to.marcus.rxtesting.data.ParserTest;
import to.marcus.rxtesting.injection.Activity;

/**
 * Created by marcus on 9/2/2015
 */
@Module
public class ModelModule {

    public ModelModule(){

    }

    //some model data stuff
    //such as the getChracterInformatonUseCase
        //the getCharacterInformationUseCase class will have @Inject on the constructor
    //this module is then added to the Activity class through the initInjector()

    @Provides @Activity
    ParserTest provideParserTest(){
        return new ParserTest();
    }
}
