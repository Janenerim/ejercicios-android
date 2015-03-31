package tk.mirenamorrortu.earthquakes.Fragments;


import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
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

    AlarmManager alarmManager;
    Context context;
    PendingIntent alarmIntent;



    private void setAlarm(long interval) {
        // Declarar el tipo de Alarma
        int alarmType =  AlarmManager.RTC;

        // Creamos el Intent de la Alarma y el pendiente

        String ALARM_DOWNLOAD = context.getString(R.string.ALARM_DOWNLOAD_KEY);
        Intent alarm = new Intent(ALARM_DOWNLOAD);
        alarmIntent = PendingIntent.getService(context, 0, alarm,0);
        // Activamos la Alarma
        alarmManager.setRepeating(alarmType, 0, interval,alarmIntent);
    }

    private void unSetAlarm(){
        alarmManager.cancel(alarmIntent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs.registerOnSharedPreferenceChangeListener(this);

        //Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.userpreferences);
        //Cogemos el manager Alarm
        alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        String PREF_AUTO_UPDATE = getString(R.string.autorefresh_id);
        String PREF_UPDATE_INTERVAL = getString(R.string.frequency);
        String PREF_MIN_MAG = getString(R.string.min_magnitude);
        if(key.equals(PREF_AUTO_UPDATE)){
            // Star/Stop

            //Get Preferences
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
            Boolean setAlarm = Boolean.parseBoolean(prefs.getString(context.getString(R.string.autorefresh), "true"));
            if (setAlarm) {
                //START: hay que poner la alarma
                long freq = Long.parseLong(prefs.getString(context.getString(R.string.autorefresh), "1"));
                freq = freq * 60000;
                setAlarm(freq);
            }
            else{
                //STOP: hay que quitar la alarma
                unSetAlarm();
            }
        } else if(key.equals(PREF_UPDATE_INTERVAL)){
            // Change auto refresh interval
            //Paramos la alarma anterior
            unSetAlarm();
            //Get Preferences
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
            long freq = Long.parseLong(prefs.getString(context.getString(R.string.autorefresh), "1"));
            freq = freq * 60000;
            //activamos la alarma con la nueva frecuencia
            setAlarm(freq);

        }
    }
}
