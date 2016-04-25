package to.marcus.SpanishDaily.injection.component;

import android.content.Context;

import dagger.Component;
import to.marcus.SpanishDaily.data.interactor.WordInteractorImpl;
import to.marcus.SpanishDaily.injection.Activity;
import to.marcus.SpanishDaily.injection.module.ActivityModule;
import to.marcus.SpanishDaily.injection.module.WordInteractorModule;
import to.marcus.SpanishDaily.service.WordNotificationService;
import to.marcus.SpanishDaily.ui.activity.BaseActivity;
import to.marcus.SpanishDaily.ui.activity.DetailActivity;
import to.marcus.SpanishDaily.ui.activity.HomeActivity;

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
