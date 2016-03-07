package to.marcus.rxtesting.ui.fragment;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.leakcanary.RefWatcher;

import to.marcus.rxtesting.BaseApplication;
import to.marcus.rxtesting.R;

/**
 * Created by marcus on 10/21/2015
 */
public class OptionsFragment extends PreferenceFragment{

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_options);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        RefWatcher refWatcher = BaseApplication.getRefWatcher(getActivity());
        refWatcher.watch(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = super.onCreateView(inflater, container, savedInstanceState);
        if(view != null)
            view.setBackgroundColor(getResources().getColor(android.R.color.white));
        return view;
    }
}
