package tk.mirenamorrortu.earthquakes.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.ListFragment;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import tk.mirenamorrortu.earthquakes.Adaters.EarthQuakeAdapter;
import tk.mirenamorrortu.earthquakes.providers.EarthQuakesDB;
import tk.mirenamorrortu.earthquakes.DetailEarthQuake;
import tk.mirenamorrortu.earthquakes.MainActivity;
import tk.mirenamorrortu.earthquakes.Model.EarthQuake;
import tk.mirenamorrortu.earthquakes.R;
import tk.mirenamorrortu.earthquakes.task.DownloadEarQuakesTask;


/**
 * A fragment representing a list of EarthQuakes.
 */
public class EarthQuakeListFragment extends ListFragment /*implements DownloadEarQuakesTask.AddEarthQuakeInterface*/{


    private ArrayList<EarthQuake> earthQuakes;
    private ArrayAdapter aa;
    private Context context;
    private SharedPreferences prefs;
    private EarthQuakesDB earthQuakeDB;


    public static final String EARTHQUAKE = "EARTHQUAKE";
    public static final String ID_EARTHQUAKE ="ID";

    private Double getMinMagPref(){
        return Double.parseDouble(prefs.getString(context.getString(R.string.min_mag),"0"));
    }

    /*@Override
    public void notifyTotal(int total) {
        String msg = getString(R.string.num_earthquakes, total);
        Toast t = Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT);
        t.show();
    }*/
    /*como va a tardar en obtener los datos desde inet, lo hacemos cuanto antes, y como no nos haceç
        falta la vista, porque luego tenemos el notify
    */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        earthQuakes = new ArrayList<EarthQuake>();
        earthQuakeDB = new EarthQuakesDB(this.getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = super.onCreateView(inflater, container, savedInstanceState);

        this.context = this.getActivity();
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
/*        if (savedInstanceState != null){
            restore_state(savedInstanceState);
        }*/
        aa = new EarthQuakeAdapter(getActivity(), R.layout.earthquake_item, earthQuakes);
        setListAdapter(aa);
        //cargarEarthQuakesData();
        cargarEarthQuakesData_bypreferences();
        return layout;
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

    private void cargarEarthQuakesData() {
        earthQuakes.clear();
        earthQuakes.addAll(earthQuakeDB.GetAllEarthQuakes());
        aa.notifyDataSetChanged();
    }

    private void cargarEarthQuakesData_bypreferences() {
        Double minmag = getMinMagPref();
        earthQuakes.clear();
        earthQuakes.addAll(earthQuakeDB.GetEarthQuakesFilerByMag(minmag));
        aa.notifyDataSetChanged();
    }

    private void actualizarlista(){
        cargarEarthQuakesData();
    }
    private void actualizarlista_bypreferences(){
        cargarEarthQuakesData_bypreferences();
    }
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        EarthQuake eq = earthQuakes.get(position);

        Intent detailIntent = new Intent(getActivity(), DetailEarthQuake.class);

        //podríamos pasar solo el id y que el detalle se descargue la info;
        detailIntent.putExtra(ID_EARTHQUAKE, eq.getId());
        //detailIntent.putExtra(EARTHQUAKE, eq);

        startActivity(detailIntent);
    }


    /*@Override
    public void ActualizarLista() {
        //this.actualizarlista();
        this.actualizarlista_bypreferences();
    }*/

    @Override
    public void onResume() {
        super.onResume();
        this.actualizarlista_bypreferences();
    }

}
