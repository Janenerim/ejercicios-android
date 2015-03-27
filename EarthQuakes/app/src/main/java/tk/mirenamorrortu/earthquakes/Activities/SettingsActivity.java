package tk.mirenamorrortu.earthquakes.Activities;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import tk.mirenamorrortu.earthquakes.Fragments.SettingsFragment;
import tk.mirenamorrortu.earthquakes.R;

/**
 * Created by Bafi on 26/03/15.
 */
public class SettingsActivity extends PreferenceActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //que esté tachado significa que está deprecated y en cualquier momento podría desaparecer
        //addPreferencesFromResource(R.xml.userpreferences);

        //La mejor forma es crear un Fragmento y asignarle el screen que teníamos:
        //Display the fragment as the main content
        getFragmentManager()
                .beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();

    }
}
