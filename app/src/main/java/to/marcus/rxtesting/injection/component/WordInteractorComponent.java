package to.marcus.rxtesting.injection.component;

import android.content.Context;

import dagger.Component;
import to.marcus.rxtesting.HomeActivity;
import to.marcus.rxtesting.data.api.WebParser;
import to.marcus.rxtesting.data.interactor.InteractorImpl;
import to.marcus.rxtesting.injection.Activity;
import to.marcus.rxtesting.injection.module.ActivityModule;
import to.marcus.rxtesting.injection.module.WordInteractorModule;

/**
 * Created by marcus on 9/2/2015
 */
@Activity
@Component(dependencies = BaseAppComponent.class, modules = {WordInteractorModule.class, ActivityModule.class})
public interface WordInteractorComponent extends ActivityComponent {
    void inject(HomeActivity activity);

    Context activityContext();
    InteractorImpl getWordInteractor();
}
