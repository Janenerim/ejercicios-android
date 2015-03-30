package tk.mirenamorrortu.earthquakes.task;

import android.content.Context;
import android.os.AsyncTask;
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

import tk.mirenamorrortu.earthquakes.DataBase.EarthQuakesDB;
import tk.mirenamorrortu.earthquakes.Model.Coordinate;
import tk.mirenamorrortu.earthquakes.Model.EarthQuake;


/**
 * Created by Bafi on 25/03/15.
 */
public class DownloadEarQuakesTask extends AsyncTask <String, EarthQuake, Integer>{

    private EarthQuakesDB earthQuakeDB;

    public interface AddEarthQuakeInterface{
        public void notifyTotal(int total);
    }

    private final String EARTHQUAKE = "EARTHQUAKE";
    private AddEarthQuakeInterface target;
    public DownloadEarQuakesTask(Context context, AddEarthQuakeInterface target){
        this.target = target;

        earthQuakeDB = new EarthQuakesDB(context);
    }

    @Override
    protected Integer doInBackground(String... urls) {
        Integer count = null;
        if (urls.length > 0){
            count = updateEarthQuakes(urls[0]);
        }
        return count;
    }

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
                    processEarthQuakeTask(earthquakes.getJSONObject(i));
                }
            }
        } catch (MalformedURLException e) {
            Log.d("TAG", "Malformed	URL	Exception.", e);
        } catch (IOException e) {
            Log.d("TAG", "IO	Exception.", e);
        } catch (JSONException e) {
            Log.d("TAG", "JSON Exception", e);
        }
        return count;
    }

    private void processEarthQuakeTask(JSONObject jsonObject) {
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

            earthQuakeDB.addEarthQuakeToDB(eQ);

            publishProgress(eQ);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onProgressUpdate(EarthQuake... earthQuakes) {
        super.onProgressUpdate(earthQuakes);
    }

    @Override
    protected void onPostExecute(Integer count) {
        super.onPostExecute(count);
        target.notifyTotal(count);
    }
}
