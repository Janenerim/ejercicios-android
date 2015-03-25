package tk.mirenamorrortu.earthquakes.Fragments;

import android.os.Bundle;
import android.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import tk.mirenamorrortu.earthquakes.Adaters.EarthQuakeAdapter;
import tk.mirenamorrortu.earthquakes.Model.EarthQuake;
import tk.mirenamorrortu.earthquakes.R;
import tk.mirenamorrortu.earthquakes.task.DownloadEarQuakesTask;


/**
 * A fragment representing a list of EarthQuakes.
 */
public class EarthQuakeListFragment extends ListFragment implements DownloadEarQuakesTask.AddEarthQuakeInterface{


    private ArrayList<EarthQuake> earthQuakes;
    private ArrayAdapter aa;

    public void AddEarthQuake(EarthQuake earthQuake){
        earthQuakes.add(0,earthQuake);
        aa.notifyDataSetChanged();
    }


    /*como va a tardar en obtener los datos desde inet, lo hacemos cuanto antes, y como no nos haceç
    falta la vista, porque luego tenemos el notify, lo hacemos en el on
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        earthQuakes = new ArrayList<EarthQuake>();

        DownloadEarQuakesTask task = new DownloadEarQuakesTask(this);
        task.execute(getString(R.string.eartquakes_url));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = super.onCreateView(inflater, container, savedInstanceState);

        aa = new EarthQuakeAdapter(getActivity(), R.layout.earthquake_item, earthQuakes);
        setListAdapter(aa);

        return layout;
    }

}
