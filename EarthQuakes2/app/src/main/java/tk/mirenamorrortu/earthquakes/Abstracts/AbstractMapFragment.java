package tk.mirenamorrortu.earthquakes.Abstracts;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import java.util.List;
import tk.mirenamorrortu.earthquakes.providers.EarthQuakesDB;
import tk.mirenamorrortu.earthquakes.Model.EarthQuake;
import tk.mirenamorrortu.earthquakes.R;

/**
 * Created by Bafi on 13/04/15.
 */
public abstract class AbstractMapFragment extends MapFragment implements GoogleMap.OnMapLoadedCallback {
    private GoogleMap map;
    private List<EarthQuake> earthQuakes;
    protected EarthQuakesDB earthQuakeDB;
    private SharedPreferences prefs;
    protected Context context;
    protected Double getMinMagPref(){
        return Double.parseDouble(prefs.getString(context.getString(R.string.min_mag),"0"));
    }

    protected final int MAP_TYPE_TERRAIN = GoogleMap.MAP_TYPE_TERRAIN;
    protected final int MAP_TYPE_HYBRID = GoogleMap.MAP_TYPE_HYBRID;
    protected final int MAP_TYPE_NONE = GoogleMap.MAP_TYPE_NONE;
    protected final int MAP_TYPE_NORMAL = GoogleMap.MAP_TYPE_NORMAL;
    protected final int MAP_TYPE_SATELLITE = GoogleMap.MAP_TYPE_SATELLITE;

    protected void setEarthQuakes (List<EarthQuake> earthQuakes){
        this.earthQuakes = earthQuakes;
    }

    private MarkerOptions createMarkerOptionsfromEarthQuake (EarthQuake earthQuake){
        LatLng point = new LatLng(earthQuake.getCoords().getLng(), earthQuake.getCoords().getLat());
        MarkerOptions marker = new MarkerOptions()
                .position(point)
                .icon(BitmapDescriptorFactory.defaultMarker(earthQuake.getMarkerColor()))
                        .title(earthQuake.getMagnitudeFormatted().concat(" ").concat(earthQuake.getPlace()))
                        .snippet(earthQuake.getCoords().toString());
        return marker;
    }
    private void setCameraUpdate (MarkerOptions marker){
        LatLng point = marker.getPosition();
        CameraPosition camPos = new CameraPosition.Builder().target(point)
                .zoom(15)
                .bearing(0)
                .tilt(90)
                .build();
        CameraUpdate cu = CameraUpdateFactory.newCameraPosition(camPos);
        map.animateCamera(cu);
    }
    private void setCameraUpdate (){
        LatLng point = new LatLng(0D, 0D);
        CameraPosition camPos = new CameraPosition.Builder().target(point)
                .zoom(15)
                .bearing(0)
                .tilt(90)
                .build();
        CameraUpdate cu = CameraUpdateFactory.newCameraPosition(camPos);
        map.animateCamera(cu);
    }
    private void setMapType (int MapType){
        map.setMapType(MapType);
    }
    private void setCameraUpdate (LatLngBounds bounds){
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 1);
        map.animateCamera(cu);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = super.onCreateView(inflater, container, savedInstanceState);
        context = this.getActivity();
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        earthQuakeDB = new EarthQuakesDB(this.getActivity());
        return layout;
    }

    @Override
    public void onMapLoaded(){
        map.clear();
        getData();

        setMapType(MAP_TYPE_TERRAIN);
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        LatLngBounds bounds;

        MarkerOptions lastMarker = new MarkerOptions();
        if (earthQuakes != null && earthQuakes.size() > 0) {
            for (int i = 0; i < earthQuakes.size(); i++) {
                EarthQuake earthQuake = earthQuakes.get(i);
                MarkerOptions marker = createMarkerOptionsfromEarthQuake(earthQuake);
                map.addMarker(marker);
                builder.include(marker.getPosition());
                lastMarker = marker;
            }
            bounds = builder.build();
        }else{
            bounds = null;
        }

        if (earthQuakes == null || (earthQuakes != null && earthQuakes.size() == 0)){
            setCameraUpdate();
            String msg = getString(R.string.no_data);
            Toast t = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
            t.show();
        }else if (earthQuakes.size() == 1) {
            setCameraUpdate(lastMarker);
        }else{
            setCameraUpdate(bounds);
        }
    }

    @Override
    public void onResume() {

        super.onResume();
        setupMapIfNeeded();
        map.setOnMapLoadedCallback(this);
    }

    private void setupMapIfNeeded() {
        if (map == null){
            map = getMap();
        }
    }

    protected abstract void getData();

}
