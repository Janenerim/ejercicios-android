package tk.mirenamorrortu.earthquakes.Fragments;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import tk.mirenamorrortu.earthquakes.Activities.SettingsActivity;
import tk.mirenamorrortu.earthquakes.MainActivity;
import tk.mirenamorrortu.earthquakes.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        prefs.registerOnSharedPreferenceChangeListener(this);

        //Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.userpreferences);
    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        String PREF_AUTO_UPDATE = getString(R.string.autorefresh_id);
        String PREF_UPDATE_INTERVAL = getString(R.string.frequency);
        String PREF_MIN_MAG = getString(R.string.min_mag);
        if(key.equals(PREF_AUTO_UPDATE)){
            // Star/Stop
        } else if(key.equals(PREF_UPDATE_INTERVAL)){
            // Change auto refresh interval
        }

    }
}
