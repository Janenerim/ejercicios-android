package tk.mirenamorrortu.earthquakes;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.webkit.WebViewFragment;
import android.widget.TextView;

import java.text.DecimalFormat;

import tk.mirenamorrortu.earthquakes.DataBase.EarthQuakesDB;
import tk.mirenamorrortu.earthquakes.Fragments.EarthQuakeListFragment;
import tk.mirenamorrortu.earthquakes.Model.EarthQuake;


public class DetailEarthQuake extends ActionBarActivity {

    TextView magnitud;
    TextView _id;
    TextView Fecha;
    TextView Place;
    TextView _url;
    WebViewFragment maps;

    EarthQuake eq;

    private void SetViews(){
        magnitud = (TextView) findViewById(R.id.mag_txt);
        _id = (TextView) findViewById(R.id.id_txt);
        Fecha = (TextView) findViewById(R.id.date_txt);
        Place = (TextView) findViewById(R.id.place_txt);
        _url = (TextView) findViewById(R.id.url_txt);
        maps = (WebViewFragment) getFragmentManager().findFragmentById(R.id.maps_frag);
        //maps = (Fragment) findViewById(R.id.maps_frag);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_earth_quake);

        SetViews();

        Intent detailIntent = getIntent();

        //Si nos pasaran el id del terremoto, podríamos pedirselo a la BD
        EarthQuakesDB db = new EarthQuakesDB(this);
        eq = db.GetEarthQuake(detailIntent.getStringExtra(EarthQuakeListFragment.ID_EARTHQUAKE));
       // eq = detailIntent.getParcelableExtra(EarthQuakeListFragment.EARTHQUAKE);

        populateView();
    }

    private void populateView(){
        DecimalFormat precision = new DecimalFormat("0.00");
        magnitud.setText(precision.format(eq.getMagnitude()));
        setmagnitudebackground(eq.getMagnitude());
        _id.setText(eq.getId());
        Fecha.setText(eq.getTimeFormatted());
        Place.setText(eq.getPlace());
        _url.setText(eq.getUrl());

        //maps.setCoords (eq.getCoords);
/*        String url_gogle_maps_coords = getString(R.string.google_url);
        url_gogle_maps_coords = url_gogle_maps_coords + eq.getCoords().getLat() + ", " + eq.getCoords().getLng();
        maps.init(url_gogle_maps_coords);
        getFragmentManager().beginTransaction().add(android.R.id.content, maps).commit();
       */


    }

    private static final int ROJO = Color.rgb(255,0,0);
    private static final int NARANJA = Color.rgb (255,128,0);
    private static final int AMARILLO = Color.rgb (255,255,0);
    private static final int VERDE = Color.rgb(0,255,0);
    private static final int AZUL = Color.rgb(0,0,255);
    private static final int GRIS = Color.rgb(160,160,160);
    private static final int NEGRO = Color.rgb(0,0,0);
    private static final int BLANCO = Color.rgb(255,255,255);

    private void setmagnitudebackground (Double mag){
        int colorf;
        int colort;
        if (mag < 0){
            colorf = BLANCO;
            colort = NEGRO;
        }else if (mag >= 0 &&  mag < 1.5){
            colorf = GRIS;
            colort = BLANCO;
        }else if (mag >= 1.5 && mag < 2.5){
            colorf = AZUL;
            colort = BLANCO;
        }else if(mag >= 2.5 && mag < 3.5){
            colorf = VERDE;
            colort = BLANCO;
        }else if (mag >= 3.5 && mag < 4.5){
            colorf = AMARILLO;
            colort = BLANCO;
        }else if(mag >= 4.5 && mag < 5.5){
            colorf = NARANJA;
            colort = BLANCO;
        }else if(mag >= 5.5 && mag < 6.5){
            colorf = ROJO;
            colort = BLANCO;
        }else {
            colorf = NEGRO;
            colort = ROJO;
        }
        magnitud.setBackgroundColor(colorf);
        magnitud.setTextColor(colort);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
