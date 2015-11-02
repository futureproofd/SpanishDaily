package to.marcus.rxtesting.injection.component;

import android.content.Context;
import dagger.Component;
import to.marcus.rxtesting.service.WordNotificationService;
import to.marcus.rxtesting.ui.activity.BaseActivity;
import to.marcus.rxtesting.ui.activity.DetailActivity;
import to.marcus.rxtesting.ui.activity.HomeActivity;
import to.marcus.rxtesting.data.interactor.WordInteractorImpl;
import to.marcus.rxtesting.injection.Activity;
import to.marcus.rxtesting.injection.module.ActivityModule;
import to.marcus.rxtesting.injection.module.WordInteractorModule;

/**
 * Created by marcus on 9/2/2015
 */
@Activity
@Component(dependencies = BaseAppComponent.class, modules = {
            WordInteractorModule.class
            ,ActivityModule.class
        })
public interface WordInteractorComponent extends ActivityComponent{
    void injectHome(HomeActivity activity);
    void injectBase(BaseActivity activity);
    void injectDetail(DetailActivity activity);
    void injectService(WordNotificationService service);
    Context activityContext();
    WordInteractorImpl getWordInteractor();
}
