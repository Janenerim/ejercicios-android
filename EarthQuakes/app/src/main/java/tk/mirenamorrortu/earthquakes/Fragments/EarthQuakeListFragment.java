package tk.mirenamorrortu.earthquakes.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.jar.Manifest;

import tk.mirenamorrortu.earthquakes.Adaters.EarthQuakeAdapter;
import tk.mirenamorrortu.earthquakes.DetailEarthQuake;
import tk.mirenamorrortu.earthquakes.Model.EarthQuake;
import tk.mirenamorrortu.earthquakes.R;
import tk.mirenamorrortu.earthquakes.task.DownloadEarQuakesTask;


/**
 * A fragment representing a list of EarthQuakes.
 */
public class EarthQuakeListFragment extends ListFragment implements DownloadEarQuakesTask.AddEarthQuakeInterface{


    private ArrayList<EarthQuake> earthQuakes;
    private ArrayAdapter aa;

    public static final String EARTHQUAKE = "EARTHQUAKE";

    public void AddEarthQuake(EarthQuake earthQuake){
        earthQuakes.add(0,earthQuake);
        aa.notifyDataSetChanged();
    }

    @Override
    public void notifyTotal(int total) {
        String msg = getString(R.string.num_earthquakes, total);
        Toast t = Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT);
        t.show();
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

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        EarthQuake eq = earthQuakes.get(position);

        /*
        //Si quisieramos eliminar el item al pulsar sobre el...
        todos.remove(position);
        aa.notifyDataSetChanged();*/

        Intent detailIntent = new Intent(getActivity(), DetailEarthQuake.class);
        detailIntent.putExtra(EARTHQUAKE, eq);


        startActivity(detailIntent);
    }

}
