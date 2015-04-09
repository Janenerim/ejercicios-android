package tk.mirenamorrortu.earthquakes.Fragments;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import java.util.List;
import tk.mirenamorrortu.earthquakes.Model.EarthQuake;
import tk.mirenamorrortu.earthquakes.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragmentGoogle extends MapFragment implements GoogleMap.OnMapLoadedCallback{

    private GoogleMap map;
    private List<EarthQuake> earthQuakes;

    public void setEarthQuakes (List<EarthQuake> earthQuakes){
        this.earthQuakes = earthQuakes;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = super.onCreateView(inflater, container, savedInstanceState);
        map = getMap();
        map.setOnMapLoadedCallback(this);
        return layout;
    }


    @Override
    public void onMapLoaded() {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();

        map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

        for (int i = 0; i < earthQuakes.size(); i++){
            EarthQuake earthQuake = earthQuakes.get(i);
            LatLng point = new LatLng(earthQuake.getCoords().getLng(), earthQuake.getCoords().getLat());

            MarkerOptions marker = new MarkerOptions()
                    .position(point)
                    .title(earthQuake.getMagnitudeFormatted().concat(" ").concat(earthQuake.getPlace()))
                    .snippet(earthQuake.getCoords().toString());

            map.addMarker(marker);
            builder.include(point);
        }

        LatLngBounds bounds = builder.build();

        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 10);
        map.moveCamera(cu);

    }
}
