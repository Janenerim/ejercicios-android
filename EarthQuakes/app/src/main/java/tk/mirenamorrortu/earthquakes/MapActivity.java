package tk.mirenamorrortu.earthquakes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import tk.mirenamorrortu.earthquakes.DataBase.EarthQuakesDB;
import tk.mirenamorrortu.earthquakes.Fragments.EarthQuakeListFragment;
import tk.mirenamorrortu.earthquakes.Model.Coordinate;
import tk.mirenamorrortu.earthquakes.Model.EarthQuake;


public class MapActivity extends Activity {

    private List EarthQuakes;
    private SharedPreferences prefs;
    private Context context;
    private List mapMarks;
    private MapFragment map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        map = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        configureMap();
        context = getBaseContext();
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        EarthQuakesDB db = new EarthQuakesDB(this);

        //Nos pasaran el id del terremoto, se lo pedimos a la BD
        Intent detailIntent = getIntent();

        EarthQuakes = loadEarthQuakes(db);
        String eqId = detailIntent.getStringExtra(EarthQuakeListFragment.ID_EARTHQUAKE);

        //loadMapMarks(eqId);
        //EarthQuake eq = db.GetEarthQuake(eqId);
        MarkerOptions mark = createMapMark(0D, 0D);
        map.getMap().addMarker(mark);
        focusCoords(0D, 0D);
    }

    private void configureMap(){
        map.getMap().setMapType(GoogleMap.MAP_TYPE_NORMAL);
        /*map.getMap().setMapType(GoogleMap.MAP_TYPE_HYBRID);
        map.getMap().setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        map.getMap().setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        int type = map.getMap().getMapType();*/
    }

    private List loadEarthQuakes (EarthQuakesDB earthQuakeDB){
        List earthquakes = new ArrayList<EarthQuake>();
        Double minmag = getMinMagPref();
        earthquakes.clear();
        earthquakes.addAll(earthQuakeDB.GetEarthQuakesFilerByMag(minmag));
        return earthquakes;
    }

    private Double getMinMagPref(){
        return Double.parseDouble(prefs.getString(context.getString(R.string.min_mag),"0"));
    }

    private void loadMapMarks(String EarthQuakeId){
        mapMarks = new ArrayList<MarkerOptions>();
        int j = -1;
        for (int i=0; i< EarthQuakes.size(); i++){
            MarkerOptions markerOptions = createMapMark((EarthQuake) EarthQuakes.get(i));
            mapMarks.add(i,markerOptions);
            if (((EarthQuake)EarthQuakes.get(i)).getId().equals(EarthQuakeId)){
                j=i;
            }
            map.getMap().addMarker(markerOptions);
        }
        focusMapMark(j);
    }

    private void focusMapMark(int j) {
        EarthQuake eq = (EarthQuake) EarthQuakes.get(j);
        LatLng focusMark = new LatLng(eq.getCoords().getLat(),eq.getCoords().getLng());

        CameraPosition camPos = new CameraPosition.Builder().target(focusMark)
                .zoom(20)
                .bearing(0)
                .tilt(90)
                .build();
        CameraUpdate camUpd = CameraUpdateFactory.newCameraPosition(camPos);
        map.getMap().animateCamera(camUpd);
    }

    private void focusCoords(Coordinate coords) {

        LatLng focusMark = new LatLng(coords.getLat(),coords.getLng());

        CameraPosition camPos = new CameraPosition.Builder().target(focusMark)
                .zoom(10f)
                .build();
        CameraUpdate camUpd = CameraUpdateFactory.newCameraPosition(camPos);
        map.getMap().animateCamera(camUpd);
    }
    private void focusCoords(Double latitude, Double longitude) {

        LatLng focusMark = new LatLng(latitude,longitude);

        CameraPosition camPos = new CameraPosition.Builder().target(focusMark)
                .zoom(10f)
                .build();
        CameraUpdate camUpd = CameraUpdateFactory.newCameraPosition(camPos);
        map.getMap().animateCamera(camUpd);
    }

    private MarkerOptions createMapMark(EarthQuake eq){
        MarkerOptions marker = new MarkerOptions()
                .position(new LatLng(eq.getCoords().getLat(),eq.getCoords().getLng())).title(eq.getUrl());
        return marker;
    }
    private MarkerOptions createMapMark(Double latitude, Double longitude){
        LatLng pos = new LatLng(latitude, longitude);
        MarkerOptions marker = new MarkerOptions()
                .position(pos);
        return marker;
    }

    private EarthQuake getEarthQuake(String earthQuakeId) {
        int j = -1;
        for (int i=0; i< EarthQuakes.size(); i++){
            if (((EarthQuake)EarthQuakes.get(i)).getId().equals(earthQuakeId)){
                j = i;
                i = EarthQuakes.size();
            }
        }
        return (EarthQuake)EarthQuakes.get(j);
    }
}
