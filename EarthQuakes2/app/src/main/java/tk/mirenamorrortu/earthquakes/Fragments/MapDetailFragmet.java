package tk.mirenamorrortu.earthquakes.Fragments;

import android.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import tk.mirenamorrortu.earthquakes.Abstracts.AbstractMapFragment;
import tk.mirenamorrortu.earthquakes.Model.EarthQuake;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapDetailFragmet extends AbstractMapFragment {

    private EarthQuake eq;

    public void setEarthQuake(EarthQuake earthQuake) {
        this.eq = earthQuake;
        this.getData();
    }
    public void setEarthQuake(String idEarthQuake) {
        this.eq = this.earthQuakeDB.GetEarthQuake(idEarthQuake);
        this.getData();
    }

    @Override
    public void getData() {
        List<EarthQuake> earthQuakes = new ArrayList<EarthQuake>();
        if (eq == null){
            String id = getActivity().getIntent().getStringExtra(EarthQuakeListFragment.ID_EARTHQUAKE);
            setEarthQuake(id);
        }
        earthQuakes.add(eq);
        this.setEarthQuakes(earthQuakes);
    }

}
