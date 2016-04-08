package to.marcus.rxtesting.injection.component;

import android.content.Context;

import dagger.Component;
import to.marcus.rxtesting.injection.Activity;
import to.marcus.rxtesting.injection.module.ActivityModule;
import to.marcus.rxtesting.ui.activity.DetailActivity;
import to.marcus.rxtesting.ui.activity.HomeActivity;

/**
 * Created by marcus on 9/2/2015
 */
@Activity
@Component(dependencies = BaseAppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    void injectHome(HomeActivity activity);
    void injectDetail(DetailActivity activity);
    Context context();
}
