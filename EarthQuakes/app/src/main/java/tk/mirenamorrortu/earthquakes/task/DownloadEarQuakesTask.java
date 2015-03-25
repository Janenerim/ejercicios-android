package tk.mirenamorrortu.earthquakes.task;

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
import tk.mirenamorrortu.earthquakes.Model.Coordinate;
import tk.mirenamorrortu.earthquakes.Model.EarthQuake;


/**
 * Created by Bafi on 25/03/15.
 */
public class DownloadEarQuakesTask extends AsyncTask <String, EarthQuake, Integer>{

    public interface AddEarthQuakeInterface{
        public void AddEarthQuake(EarthQuake earthQuake);
    }

    private final String EARTQUAKE = "EARTHQUAKE";
    private AddEarthQuakeInterface target;
    public DownloadEarQuakesTask(AddEarthQuakeInterface target){
        this.target = target;
    }

    @Override
    protected Integer doInBackground(String... urls) {
        if (urls.length > 0){
            updateEarthQuakes(urls[0]);
        }
        return null;
    }

    private void updateEarthQuakes(String earthquakesFeed) {
        JSONObject json;

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

            Log.d(EARTQUAKE, eQ.toString());

            publishProgress(eQ);


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onProgressUpdate(EarthQuake... earthQuakes) {
        super.onProgressUpdate(earthQuakes);
        target.AddEarthQuake(earthQuakes[0]);
    }
}
