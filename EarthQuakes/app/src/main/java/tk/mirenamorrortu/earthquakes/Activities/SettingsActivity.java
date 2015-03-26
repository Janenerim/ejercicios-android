package tk.mirenamorrortu.earthquakes.Activities;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import tk.mirenamorrortu.earthquakes.R;

/**
 * Created by Bafi on 26/03/15.
 */
public class SettingsActivity extends PreferenceActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.userpreferences);

        //Display

    }
}
