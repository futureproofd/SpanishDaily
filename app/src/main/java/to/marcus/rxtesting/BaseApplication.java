package to.marcus.rxtesting;

import android.app.Application;
import to.marcus.rxtesting.injection.component.BaseAppComponent;
import to.marcus.rxtesting.injection.component.DaggerBaseAppComponent;
import to.marcus.rxtesting.injection.module.ApplicationModule;

/**
 * Created by marcus on 9/2/2015
 */

public class BaseApplication extends Application{

    private BaseAppComponent mBaseAppComponent;

    @Override
    public void onCreate(){
        super.onCreate();
        initInjector();
    }

    private void initInjector(){
        mBaseAppComponent = DaggerBaseAppComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public BaseAppComponent getBaseAppComponent(){
        return mBaseAppComponent;
    }

}
