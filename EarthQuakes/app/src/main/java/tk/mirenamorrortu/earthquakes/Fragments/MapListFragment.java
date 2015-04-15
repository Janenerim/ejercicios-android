package tk.mirenamorrortu.earthquakes.Fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import tk.mirenamorrortu.earthquakes.Abstracts.AbstractMapFragment;
import tk.mirenamorrortu.earthquakes.R;
import tk.mirenamorrortu.earthquakes.task.DownloadEarQuakesTask;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapListFragment extends AbstractMapFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void getData() {
        this.setEarthQuakes(this.earthQuakeDB.GetEarthQuakesFilerByMag(this.getMinMagPref()));
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_refresh) {
            Intent download = new Intent(getActivity(), DownloadEarQuakesTask.class);
            getActivity().startService(download);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_refresh, menu);
    }
}
