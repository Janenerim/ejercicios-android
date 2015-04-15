package tk.mirenamorrortu.earthquakes;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.DecimalFormat;

import tk.mirenamorrortu.earthquakes.providers.EarthQuakesDB;
import tk.mirenamorrortu.earthquakes.Fragments.EarthQuakeListFragment;
import tk.mirenamorrortu.earthquakes.Fragments.MapDetailFragmet;
import tk.mirenamorrortu.earthquakes.Model.Coordinate;
import tk.mirenamorrortu.earthquakes.Model.EarthQuake;


public class DetailEarthQuake extends Activity {

    RelativeLayout detail;

    TextView magnitud;
    TextView _id;
    TextView Fecha;
    TextView Place;
    TextView _url;
    MapDetailFragmet maps;

    Coordinate coords;

    EarthQuake eq;
    private String ID_EARTHQUAKE = "ID_EARTHQUAKE";

    private void SetViews(){
        magnitud = (TextView) findViewById(R.id.mag_txt);
        _id = (TextView) findViewById(R.id.id_txt);
        Fecha = (TextView) findViewById(R.id.date_txt);
        Place = (TextView) findViewById(R.id.place_txt);
        _url = (TextView) findViewById(R.id.url_txt);
        maps = (MapDetailFragmet) getFragmentManager().findFragmentById(R.id.map_detail_fragmet);
        detail = (RelativeLayout) findViewById(R.id.DetailFragment);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent detailIntent = getIntent();
        //Si nos pasaran el id del terremoto, podríamos pedirselo a la BD
        EarthQuakesDB db = new EarthQuakesDB(this);
        eq = db.GetEarthQuake(detailIntent.getStringExtra(EarthQuakeListFragment.ID_EARTHQUAKE));
        // eq = detailIntent.getParcelableExtra(EarthQuakeListFragment.EARTHQUAKE);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_earth_quake);

        SetViews();

        maps.setEarthQuake(eq);

        populateView();
        //showMap(eq);
    }

/*    private void showMap(EarthQuake earthQuake){
        maps.setEarthQuake(earthQuake);
    }*/

    private void populateView(){
        DecimalFormat precision = new DecimalFormat("0.00");
        magnitud.setText(precision.format(eq.getMagnitude()));
        setmagnitudebackground(eq.getMagnitude());
        _id.setText(eq.getId());
        Fecha.setText(eq.getTimeFormatted());
        Place.setText(eq.getPlace());
        _url.setText(eq.getUrl());
        this.coords = eq.getCoords();


        detail.setOnClickListener(clickDetail);
    }

    View.OnClickListener clickDetail = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //Al hacer click abrimos la página web asociada al terremoto
            String url = eq.getUrl();
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        }
    };
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
