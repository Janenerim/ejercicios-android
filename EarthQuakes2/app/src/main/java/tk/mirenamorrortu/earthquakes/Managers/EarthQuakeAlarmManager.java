package tk.mirenamorrortu.earthquakes.Managers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import tk.mirenamorrortu.earthquakes.Services.DownloadEarthquakeService;

/**
 * Created by Bafi on 1/04/15.
 */
public class EarthQuakeAlarmManager {


    public static void setAlarm(Context context, long interval) {

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);

        // Declarar el tipo de Alarma
        int alarmType =  android.app.AlarmManager.RTC;
        // Creamos el Intent de la Alarma y el pendiente
        Intent Download = new Intent (context, DownloadEarthquakeService.class);

        //startService(Download);
        PendingIntent alarmIntent = PendingIntent.getService(context, 0, Download, 0);

        // Activamos la Alarma
        alarmManager.setRepeating(alarmType, 0, interval,alarmIntent);
        Log.d("ALARM", "Alarm SET frequency: " + String.valueOf(interval) + " ms");
    }

    public static void unSetAlarm(Context context){
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);

        // Declarar el tipo de Alarma
        int alarmType =  android.app.AlarmManager.RTC;
        // Creamos el Intent de la Alarma y el pendiente
        Intent Download = new Intent (context, DownloadEarthquakeService.class);
        //startService(Download);
        PendingIntent alarmIntent = PendingIntent.getService(context, 0, Download, 0);

        alarmManager.cancel(alarmIntent);
        Log.d("ALARM", "Alarm UNSET");
    }

    public static void updateAlarm (Context context, long interval){
        setAlarm(context, interval);
    }
}
