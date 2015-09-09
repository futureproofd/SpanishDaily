package to.marcus.rxtesting.injection.component;

import javax.inject.Singleton;

import dagger.Component;
import to.marcus.rxtesting.BaseApplication;
import to.marcus.rxtesting.injection.module.BaseAppModule;
import to.marcus.rxtesting.model.WordStorage;
import to.marcus.rxtesting.model.repository.Repository;

/**
 * Created by marcus on 9/2/2015
 */

@Singleton
@Component(modules = BaseAppModule.class)
public interface BaseAppComponent {
    BaseApplication getBaseApplication();
    WordStorage getWordStorage();
    Repository getRepository();
}
