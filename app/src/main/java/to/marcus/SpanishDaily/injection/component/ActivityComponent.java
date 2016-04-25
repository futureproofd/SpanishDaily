package to.marcus.SpanishDaily.injection.component;

import android.content.Context;

import dagger.Component;
import to.marcus.SpanishDaily.injection.Activity;
import to.marcus.SpanishDaily.injection.module.ActivityModule;
import to.marcus.SpanishDaily.ui.activity.DetailActivity;
import to.marcus.SpanishDaily.ui.activity.HomeActivity;

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
