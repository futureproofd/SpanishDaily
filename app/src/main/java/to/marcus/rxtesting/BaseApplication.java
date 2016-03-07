package to.marcus.rxtesting;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.util.Log;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import to.marcus.rxtesting.injection.component.BaseAppComponent;
import to.marcus.rxtesting.injection.component.DaggerBaseAppComponent;
import to.marcus.rxtesting.injection.module.BaseAppModule;

/**
 * Created by marcus on 9/2/2015
 */

public class BaseApplication extends Application{
    private final static String TAG = BaseApplication.class.getSimpleName();
    private BaseAppComponent mBaseAppComponent;
    //testing
    private RefWatcher refWatcher;

    @Override
    public void onCreate(){
        super.onCreate();
        initInjector();
        initInstallDefaults();
        refWatcher = LeakCanary.install(this);
    }

    private void initInjector(){
        mBaseAppComponent  = DaggerBaseAppComponent
                .builder()
                .baseAppModule(new BaseAppModule(this))
                .build();
    }

    public BaseAppComponent getBaseAppComponent(){
        return mBaseAppComponent;
    }

    //testing
    public static RefWatcher getRefWatcher(Context context){
        BaseApplication app = (BaseApplication) context.getApplicationContext();
        return app.refWatcher;
    }

    //Run once on initial install
    private void initInstallDefaults(){
        SharedPreferences sharedPrefs = getSharedPreferences("version", 0);
        int currentVersion = 2; //sharedPrefs.getInt("VersionCode", 0);
        int appVersion = 0;

        //attempt to get versionCode
        try{
            appVersion = getPackageManager().getPackageInfo(getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e){
            Log.i(TAG, "no version code for application " + e);
        }

        if(currentVersion == appVersion){
            Log.i(TAG, "version up to date, defaults set");
        }else{
            Log.i(TAG, "new version");
            SharedPreferences.Editor sharedPrefsEditor = sharedPrefs.edit();
            sharedPrefsEditor.putInt("VersionCode", appVersion);
            sharedPrefsEditor.commit();
        }
    }

}
