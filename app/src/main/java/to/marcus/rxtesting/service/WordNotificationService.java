package to.marcus.rxtesting.service;

import android.app.IntentService;
import android.content.Intent;

import javax.inject.Inject;

import to.marcus.rxtesting.BaseApplication;
import to.marcus.rxtesting.injection.component.DaggerWordInteractorComponent;
import to.marcus.rxtesting.injection.module.ActivityModule;
import to.marcus.rxtesting.injection.module.WordInteractorModule;
import to.marcus.rxtesting.model.repository.RepositoryImpl;
import to.marcus.rxtesting.presenter.HomePresenterImpl;
import to.marcus.rxtesting.util.DateUtility;

/**
 * Created by marcus on 10/30/2015
 */
public class WordNotificationService extends IntentService {
    @Inject HomePresenterImpl mHomePresenterImpl;
    @Inject RepositoryImpl mRepository;

    public WordNotificationService(){
        super("WordNotificationService");
    }

    @Override
    public void onCreate(){
        super.onCreate();
        initInjector();
    }

    private void initInjector(){
        BaseApplication baseApplication = (BaseApplication)getApplication();
        DaggerWordInteractorComponent.builder()
                .activityModule(new ActivityModule(this))
                .baseAppComponent(baseApplication.getBaseAppComponent())
                .wordInteractorModule(new WordInteractorModule())
                .build().injectService(this);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if(mHomePresenterImpl.isWordStale()){
            //make notification
        }
    }
}
