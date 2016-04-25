package to.marcus.SpanishDaily;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.util.Log;

import to.marcus.SpanishDaily.injection.component.BaseAppComponent;
import to.marcus.SpanishDaily.injection.component.DaggerBaseAppComponent;
import to.marcus.SpanishDaily.injection.module.BaseAppModule;
import to.marcus.SpanishDaily.model.WordStorage;

/**
 * Created by marcus on 9/2/2015
 */

public class BaseApplication extends Application{
    private final static String TAG = BaseApplication.class.getSimpleName();
    private static boolean firstRun;
    private BaseAppComponent mBaseAppComponent;

    @Override
    public void onCreate(){
        super.onCreate();
        initInjector();
        initInstallDefaults();
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

    //Run once on initial install
    @SuppressLint("CommitPrefEdits")
    private void initInstallDefaults(){
        SharedPreferences sharedPrefs = getSharedPreferences("version", 0);
        int savedVersionCode = sharedPrefs.getInt("VersionCode", 0);
        int gradleAppVersion = -1;

        //get build.gradle app version
        try{
            gradleAppVersion = getPackageManager().getPackageInfo(getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e){
            Log.i(TAG, "no version code for application " + e);
        }
        if(savedVersionCode == gradleAppVersion){
            Log.i(TAG, "application version is up-to-date");
            this.firstRun = false;
        }else if(savedVersionCode == 0){
            Log.i(TAG, "First time run");
            SharedPreferences.Editor sharedPrefsEditor = sharedPrefs.edit();
            sharedPrefsEditor.putInt("VersionCode", gradleAppVersion);
            sharedPrefsEditor.commit();
            this.firstRun = true;
        }
    }

    /*
    Toggle default dataSet
     */
    public static boolean isFirstRun(){
        return firstRun;
    }


}
