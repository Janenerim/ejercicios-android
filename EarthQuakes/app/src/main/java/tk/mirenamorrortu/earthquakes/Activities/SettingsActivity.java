package tk.mirenamorrortu.earthquakes.Activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;

import tk.mirenamorrortu.earthquakes.Fragments.SettingsFragment;
import tk.mirenamorrortu.earthquakes.Managers.EarthQuakeAlarmManager;
import tk.mirenamorrortu.earthquakes.R;
import tk.mirenamorrortu.earthquakes.Services.DownloadEarthquakeService;

/**
 * Created by Bafi on 26/03/15.
 */
public class SettingsActivity extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener{

    AlarmManager alarmManager;
    PendingIntent alarmIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.registerOnSharedPreferenceChangeListener(this);

        //que esté tachado significa que está deprecated y en cualquier momento podría desaparecer
        //addPreferencesFromResource(R.xml.userpreferences);

        //La mejor forma es crear un Fragmento y asignarle el screen que teníamos:
        //Display the fragment as the main content
        getFragmentManager()
                .beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();

    }
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        String PREF_AUTO_UPDATE = getString(R.string.autorefresh_id);
        String PREF_UPDATE_INTERVAL = getString(R.string.frequency);
        /*String PREF_MIN_MAG = getString(R.string.min_magnitude);*/
        if(key.equals(PREF_AUTO_UPDATE)){
            // Star/Stop

            //Get Preferences
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
            Boolean setAlarm = Boolean.parseBoolean(prefs.getString(getString(R.string.autorefresh), "true"));
            if (setAlarm) {
                //START: hay que poner la alarma
                long freq = Long.parseLong(prefs.getString(getString(R.string.autorefresh), "1"));
                freq = freq * 60000;
                EarthQuakeAlarmManager.setAlarm(this, freq);
            }
            else{
                //STOP: hay que quitar la alarma
                EarthQuakeAlarmManager.unSetAlarm(this);
            }
        } else if(key.equals(PREF_UPDATE_INTERVAL)){
            // Change auto refresh interval
            //Paramos la alarma anterior
            EarthQuakeAlarmManager.unSetAlarm(this);
            //Get Preferences
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
            long freq = Long.parseLong(prefs.getString(this.getString(R.string.frequency), "1"));
            freq = freq * 60000;
            //activamos la alarma con la nueva frecuencia
            EarthQuakeAlarmManager.updateAlarm(this, freq);
        }
    }



}
