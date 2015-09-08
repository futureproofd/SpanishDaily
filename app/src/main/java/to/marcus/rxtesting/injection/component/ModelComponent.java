package to.marcus.rxtesting.injection.component;

import android.content.Context;

import dagger.Component;
import to.marcus.rxtesting.HomeActivity;
import to.marcus.rxtesting.data.ParserTest;
import to.marcus.rxtesting.injection.Activity;
import to.marcus.rxtesting.injection.module.ActivityModule;
import to.marcus.rxtesting.injection.module.ModelModule;

/**
 * Created by mplienegger on 9/2/2015.
 */
@Activity
@Component(dependencies = BaseAppComponent.class, modules = {ModelModule.class, ActivityModule.class})
public interface ModelComponent extends ActivityComponent {
    void inject(HomeActivity activity);
    Context activityContext();
    ParserTest getParserTest();
}
