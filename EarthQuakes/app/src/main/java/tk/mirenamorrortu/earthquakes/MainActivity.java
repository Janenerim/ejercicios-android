package tk.mirenamorrortu.earthquakes;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

import tk.mirenamorrortu.earthquakes.Activities.SettingsActivity;
import tk.mirenamorrortu.earthquakes.Model.EarthQuake;
import tk.mirenamorrortu.earthquakes.Services.DownloadEarthquakeService;
import tk.mirenamorrortu.earthquakes.task.DownloadEarQuakesTask;


public class MainActivity extends ActionBarActivity implements DownloadEarQuakesTask.AddEarthQuakeInterface{
    public static final int PREFS_ACTIVITY = 0;

    public interface ActualizarListaInterface{
        public void ActualizarLista();
    }

    private ActualizarListaInterface target;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        target = (ActualizarListaInterface) this.getFragmentManager().findFragmentById(R.id.fragment);
        downloadEarthQuakes();
    }

    private void downloadEarthQuakes() {
        /*Con AsynTask
        DownloadEarQuakesTask task = new DownloadEarQuakesTask(this.getBaseContext(), this);
        task.execute(getString(R.string.eartquakes_url));*/

        Intent Download = new Intent (this, DownloadEarthquakeService.class);
        startService(Download);
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
            Intent preferences = new Intent(this, SettingsActivity.class);

            startActivityForResult(preferences, PREFS_ACTIVITY);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void notifyTotal(int total) {
        String msg = getString(R.string.num_earthquakes, total);
        Toast t = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        t.show();
        target.ActualizarLista();

    }


}
