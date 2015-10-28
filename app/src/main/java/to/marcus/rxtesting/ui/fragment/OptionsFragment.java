package to.marcus.rxtesting.ui.fragment;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import to.marcus.rxtesting.R;

/**
 * Created by marcus on 10/21/2015
 */
public class OptionsFragment extends PreferenceFragment{
    private static final String TAG = OptionsFragment.class.getSimpleName();
    private ListPreference mListPreference;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Log.i(TAG, "created");
        addPreferencesFromResource(R.xml.pref_options);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = super.onCreateView(inflater, container, savedInstanceState);
        view.setBackgroundColor(getResources().getColor(android.R.color.white));
        mListPreference = (ListPreference) getPreferenceManager().findPreference("list_preference");
        mListPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue){
                return true;
            }
        });
        return view;
    }

}
