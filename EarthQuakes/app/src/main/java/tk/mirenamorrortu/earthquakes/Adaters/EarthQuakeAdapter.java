package tk.mirenamorrortu.earthquakes.Adaters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import tk.mirenamorrortu.earthquakes.Model.Coordinate;
import tk.mirenamorrortu.earthquakes.Model.EarthQuake;
import tk.mirenamorrortu.earthquakes.R;

/**
 * Created by Bafi on 25/03/15.
 */
public class EarthQuakeAdapter extends ArrayAdapter<EarthQuake> {

    private int resource;
    private TextView magnitud;


    public EarthQuakeAdapter(Context context, int resource, List<EarthQuake> objects) {
        super(context, resource, objects);

        this.resource = resource;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RelativeLayout layout;


        if(convertView == null){
            //si no existe la vista, la creamos
            layout = new RelativeLayout(getContext());

            LayoutInflater li;
            String inflater = Context.LAYOUT_INFLATER_SERVICE;

            li = (LayoutInflater) getContext().getSystemService(inflater);
            li.inflate(resource, layout, true);
        }
        else{
            layout = (RelativeLayout)convertView;
        }

        EarthQuake item = getItem(position);

        magnitud = (TextView) layout.findViewById(R.id.mag_txt);
        TextView fecha = (TextView) layout.findViewById(R.id.date_txt);
        TextView lugar = (TextView) layout.findViewById(R.id.place_txt);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

        Double mag = item.getMagnitude();
        setMagColor(mag);
        DecimalFormat precision = new DecimalFormat("0.00");
        magnitud.setText(precision.format(mag));

        lugar.setText(item.getPlace());
        fecha.setText(sdf.format(item.getTime()));

        Coordinate coords = item.getCoords();
        //pintar las coordenadas en el mapa.

        return layout;
    }


    private static final int ROJO = Color.rgb(255,0,0);
    private static final int NARANJA = Color.rgb (255,128,0);
    private static final int AMARILLO = Color.rgb (255,255,0);
    private static final int VERDE = Color.rgb(0,255,0);
    private static final int AZUL = Color.rgb(0,0,255);
    private static final int GRIS = Color.rgb(160,160,160);
    private static final int NEGRO = Color.rgb(0,0,0);
    private static final int BLANCO = Color.rgb(255,255,255);

    private void setMagColor (Double mag){
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

}
