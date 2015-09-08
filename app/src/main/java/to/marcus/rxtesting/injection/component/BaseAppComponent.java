package to.marcus.rxtesting.injection.component;

import javax.inject.Singleton;

import dagger.Component;
import to.marcus.rxtesting.BaseApplication;
import to.marcus.rxtesting.injection.module.ApplicationModule;
import to.marcus.rxtesting.model.repository.Repository;

/**
 * Created by marcus on 9/2/2015
 */
@Singleton
@Component(modules = ApplicationModule.class)
public interface BaseAppComponent {
    BaseApplication app();
    Repository repository();
}
