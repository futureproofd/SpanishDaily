package to.marcus.rxtesting.model;

import android.content.Context;
import android.util.Log;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by marcus on 25/10/15
 */
public class AppPreferences{
    private static final String TAG = AppPreferences.class.getSimpleName();
    private static final String KEY_WIRELESS = "key_wireless";
    private static final String KEY_PULL = "key_pull";
    private static final String KEY_NOTIFY = "key_notify";
    private Map<String, Boolean> mPreferences;
    private ObjectSerializer mSerializer;
    private static final String APP_PREFERENCES = "app_prefs.json";

    public AppPreferences(Context appContext){
        mSerializer = new ObjectSerializer(appContext, APP_PREFERENCES);
        try{
            mPreferences = mSerializer.loadPreferences();
        }catch(Exception e){
            setDefaultPrefs();
        }
    }

    public boolean savePrefsToJSON(){
        try{
            Log.i(TAG, "saving Prefs to JSON");
            mSerializer.savePreferences(mPreferences);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    public void setWirelessPref(String key, boolean value){
        mPreferences.put(key, value);
        savePrefsToJSON();
    }

    public boolean getWirelessPref(){
        return (mPreferences.get(KEY_WIRELESS));
    }

    public void setNotifyPref(String key, boolean value){
        mPreferences.put(KEY_NOTIFY, value);
        savePrefsToJSON();
    }

    public boolean getNotifyPref(){
        return mPreferences.get(KEY_NOTIFY);
    }

    public void setPullPref(String key, boolean value){
        mPreferences.put(KEY_PULL, value);
        savePrefsToJSON();
    }

    public boolean getPullPref(){
        return mPreferences.get(KEY_PULL);
    }

    private void setDefaultPrefs(){
        mPreferences = new HashMap<>();
        mPreferences.put(KEY_WIRELESS,false);
        mPreferences.put(KEY_NOTIFY,true);
        mPreferences.put(KEY_PULL,true);
        savePrefsToJSON();
    }

}
