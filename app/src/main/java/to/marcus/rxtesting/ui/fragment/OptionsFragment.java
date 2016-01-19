package to.marcus.rxtesting.ui.fragment;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = super.onCreateView(inflater, container, savedInstanceState);
        if(view != null)
            view.setBackgroundColor(getResources().getColor(android.R.color.white));
        return view;
    }
}
