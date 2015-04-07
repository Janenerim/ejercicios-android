package tk.mirenamorrortu.app;

import android.app.Activity;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

public class MainActivity extends Activity implements GoogleApiClient.ConnectionCallbacks,
GoogleApiClient.OnConnectionFailedListener, LocationListener{

    private GoogleApiClient googleApiClient;
    private boolean conectado;
    private TextView estado;
    private TextView geodata;
    private Location mLastLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        estado = (TextView) findViewById(R.id.status);
        geodata = (TextView) findViewById(R.id.location);

        conectado = false;

    }

    public void configura(){
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
    }
    public void cogeLocalizacion(View v){
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }


    private void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, (com.google.android.gms.location.LocationListener) this);
        //conectado = false;
    }
    @Override
    protected void onStop() {
        googleApiClient.disconnect();
        super.onStop();

    }

    @Override
    public void onConnected (Bundle connectionHint){
        Log.d("GPS", "Conectado");
        conectado = true;
        estado.setText("Conectado");

    }

    @Override
    public void onConnectionSuspended(int i) {
        conectado = false;
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        conectado = false;
    }
}
