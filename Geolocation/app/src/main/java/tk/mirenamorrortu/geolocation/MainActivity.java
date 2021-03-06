package tk.mirenamorrortu.geolocation;

import android.content.Context;
import android.location.Criteria;

import android.location.Location;
import android.location.LocationManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.text.DecimalFormat;

import tk.mirenamorrortu.geolocation.listeners.LocationListener;


public class MainActivity extends ActionBarActivity implements LocationListener.AddLocationInterface{

    private TextView lblLatitude;
    private TextView lblLongitude;
    private TextView lblSpeed;
    private TextView lblAltitude;

    private String provider;
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        lblAltitude = (TextView)findViewById(R.id.idAltitude);
        lblLatitude = (TextView)findViewById(R.id.idLatitude);
        lblLongitude = (TextView)findViewById(R.id.idLongitude);
        lblSpeed = (TextView)findViewById(R.id.idSpeed);

        getLocationProvider();

        listenLocationChanges();

    }

    private void listenLocationChanges() {
        int t = 5000; //milliseconds
        int distance = 5; //meters

        LocationListener listener = new LocationListener(this);

        locationManager.requestLocationUpdates(provider, t, distance, listener);
    }

    private void getLocationProvider() {


        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setSpeedRequired(true);
        criteria.setAltitudeRequired(true);

        provider = locationManager.getBestProvider(criteria, false);

        Log.d("GEO", provider);
    }


    @Override
    public void addLocation(Location location) {
        Log.d("GPS", "AddLocation on MainActivity");
        DecimalFormat df = new DecimalFormat("0.00##");
        Log.d("GPS", "Altitude: " + String.valueOf(location.getAltitude()));
        Log.d("GPS", "Speed: " + String.valueOf(location.getSpeed()));
        Log.d("GPS", "Longitude: " + String.valueOf(location.getLongitude()));
        Log.d("GPS", "Latitude: " + String.valueOf(location.getLatitude()));

        lblAltitude.setText(df.format(location.getAltitude()));
        lblSpeed.setText(df.format(location.getSpeed()));
        lblLongitude.setText(df.format(location.getLongitude()));
        lblLatitude.setText(df.format(location.getLatitude()));
    }
}
