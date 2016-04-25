package to.marcus.SpanishDaily.injection.component;

import javax.inject.Singleton;

import dagger.Component;
import to.marcus.SpanishDaily.BaseApplication;
import to.marcus.SpanishDaily.injection.module.BaseAppModule;
import to.marcus.SpanishDaily.model.AppPreferences;
import to.marcus.SpanishDaily.model.WordStorage;
import to.marcus.SpanishDaily.model.repository.Repository;

/**
 * Created by marcus on 9/2/2015
 */

@Singleton
@Component(modules = BaseAppModule.class)
public interface BaseAppComponent {
    BaseApplication getBaseApplication();
    WordStorage getWordStorage();
    AppPreferences getAppPreferences();
    Repository getRepository();
}
