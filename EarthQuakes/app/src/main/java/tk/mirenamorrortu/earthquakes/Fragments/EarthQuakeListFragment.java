package tk.mirenamorrortu.earthquakes.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.ListFragment;
import android.preference.PreferenceManager;
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
   /* private Context context;
    private SharedPreferences prefs;*/

    public static final String EARTHQUAKE = "EARTHQUAKE";

   /* private Double getMinMagPref(){
        return Double.parseDouble(prefs.getString(context.getString(R.string.min_mag),"0"));
    }*/


    @Override
    public void notifyTotal(int total) {
        String msg = getString(R.string.num_earthquakes, total);
        Toast t = Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT);
        t.show();
    }
    /*como va a tardar en obtener los datos desde inet, lo hacemos cuanto antes, y como no nos haceç
        falta la vista, porque luego tenemos el notify
    */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        earthQuakes = new ArrayList<EarthQuake>();
        /*prefs = PreferenceManager.getDefaultSharedPreferences(context);*/

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = super.onCreateView(inflater, container, savedInstanceState);
        /*this.context = this.getActivity().getBaseContext();*/
        getEarthQuakesData();
        aa = new EarthQuakeAdapter(getActivity(), R.layout.earthquake_item, earthQuakes);
        setListAdapter(aa);
        return layout;
    }

    private void getEarthQuakesData() {

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        EarthQuake eq = earthQuakes.get(position);

        Intent detailIntent = new Intent(getActivity(), DetailEarthQuake.class);
        detailIntent.putExtra(EARTHQUAKE, eq);

        startActivity(detailIntent);
    }



}
