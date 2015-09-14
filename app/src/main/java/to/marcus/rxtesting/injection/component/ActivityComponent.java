package to.marcus.rxtesting.injection.component;

import android.content.Context;

import dagger.Component;
import to.marcus.rxtesting.ui.HomeActivity;
import to.marcus.rxtesting.injection.Activity;
import to.marcus.rxtesting.injection.module.ActivityModule;

/**
 * Created by mplienegger on 9/2/2015.
 */
@Activity
@Component(dependencies = BaseAppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(HomeActivity activity);
    Context context();
}
