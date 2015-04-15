package tk.mirenamorrortu.earthquakes.Services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.IBinder;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import tk.mirenamorrortu.earthquakes.providers.EarthQuakesDB;
import tk.mirenamorrortu.earthquakes.MainActivity;
import tk.mirenamorrortu.earthquakes.Model.Coordinate;
import tk.mirenamorrortu.earthquakes.Model.EarthQuake;
import tk.mirenamorrortu.earthquakes.R;

public class DownloadEarthquakeService extends Service {

    int cont;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    private EarthQuakesDB earthQuakeDB;
    private final String EARTHQUAKE = "EARTHQUAKE";

    @Override
    public void onCreate() {
        super.onCreate();
        earthQuakeDB = new EarthQuakesDB(this);
        cont = -1;

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        /* asi nos falla por que se ejecuta con el Main y hay que hacerlo en un Threath
        updateEarthQuakes(getString(R.string.eartquakes_url));*/

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d ("SERVICE", "Lanzado el servicio");
                updateEarthQuakes(getString(R.string.eartquakes_url));
            }
        });
        t.start();

        return Service.START_STICKY;
    }

    //private

    private Integer updateEarthQuakes(String earthquakesFeed) {
        JSONObject json;
        Integer count = 0;

        try {
            URL url = new URL(earthquakesFeed);

            // Create a new HTTP URL connection
            URLConnection connection = url.openConnection();
            HttpURLConnection httpConnection = (HttpURLConnection) connection;

            int responseCode = httpConnection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader streamReader = new BufferedReader(
                        new InputStreamReader(
                                httpConnection.getInputStream(), "UTF-8"));
                StringBuilder responseStrBuilder = new StringBuilder();

                String inputStr;
                while ((inputStr = streamReader.readLine()) != null)
                    responseStrBuilder.append(inputStr);

                json = new JSONObject(responseStrBuilder.toString());
                JSONArray earthquakes = json.getJSONArray("features");

                count = earthquakes.length();

                for (int i = earthquakes.length() - 1; i >= 0; i--) {
                    long r = processEarthQuakeTask(earthquakes.getJSONObject(i));
                    if (r != -1) {cont++;}
                }
            }
        } catch (MalformedURLException e) {
            Log.d("TAG", "Malformed	URL	Exception.", e);
        } catch (IOException e) {
            Log.d("TAG", "IO	Exception.", e);
        } catch (JSONException e) {
            Log.d("TAG", "JSON Exception", e);
        }
        if (cont > -1){
            Log.d("SERVICE", "Añadidos " + String.valueOf(cont) + "Terremotos");
            sendNotification(count);
        }
        else{
            Log.d("SERVICE", "No se han añadido terremotos");
        }
        return count;
    }

    private void sendNotification(int count) {
        Intent intentToFire = new Intent (this, MainActivity.class);
        PendingIntent activityIntent = PendingIntent.getActivity(this,0, intentToFire,0 );

        Notification.Builder builder = new Notification.Builder(DownloadEarthquakeService.this);
        builder.setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(getString(R.string.num_earthquakes, count))
                .setWhen(System.currentTimeMillis())
                .setDefaults(Notification.DEFAULT_SOUND)
                .setAutoCancel(true)
                .setSound(
                        RingtoneManager.getDefaultUri(
                                RingtoneManager.TYPE_NOTIFICATION))
                .setContentIntent(activityIntent);
        Notification notification = builder.getNotification();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        int NOTIFICATION_REF = 1;
        notificationManager.notify(NOTIFICATION_REF, notification);

    }

    private long processEarthQuakeTask(JSONObject jsonObject) {
        long resadd;
        try {
            String id = jsonObject.getString("id");

            JSONArray jsonCoords = jsonObject.getJSONObject("geometry").getJSONArray("coordinates");

            Coordinate coords = new Coordinate(jsonCoords.getDouble(0), jsonCoords.getDouble(1),jsonCoords.getDouble(2));

            JSONObject properties = jsonObject.getJSONObject("properties");
            EarthQuake eQ = new EarthQuake();

            eQ.setCoords(coords);
            eQ.setId(id);
            eQ.setMagnitude(properties.getDouble("mag"));
            eQ.setPlace(properties.getString("place"));
            eQ.setTime(properties.getLong("time"));
            eQ.setUrl(properties.getString("url"));

            Log.d(EARTHQUAKE, eQ.toString());

            resadd= earthQuakeDB.addEarthQuakeToDB(eQ);

            //publishProgress(eQ);

        } catch (JSONException e) {
            e.printStackTrace();
            resadd = -1;
        }
        return resadd;
    }

}
