package tk.mirenamorrortu.earthquakes.Model;

import android.graphics.Color;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

/**
 * Created by Bafi on 14/04/15.
 */
public class EarthQuakesColors {

    private int colorf;
    private int colort;
    private float icon;

    private static final int ROJO = Color.rgb(255, 0, 0);
    private static final int NARANJA = Color.rgb (255,128,0);
    private static final int AMARILLO = Color.rgb (255,255,0);
    private static final int VERDE = Color.rgb(0,255,0);
    private static final int AZUL = Color.rgb(0,0,255);
    private static final int GRIS = Color.rgb(160,160,160);
    private static final int NEGRO = Color.rgb(0,0,0);
    private static final int BLANCO = Color.rgb(255,255,255);

    private static final float ICONO_ROJO = BitmapDescriptorFactory.HUE_RED;
    private static final float ICONO_NARANJA = BitmapDescriptorFactory.HUE_ORANGE;
    private static final float ICONO_AMARILLO = BitmapDescriptorFactory.HUE_YELLOW;
    private static final float ICONO_VERDE = BitmapDescriptorFactory.HUE_GREEN;
    private static final float ICONO_AZUL = BitmapDescriptorFactory.HUE_BLUE;
    private static final float ICONO_CYAN = BitmapDescriptorFactory.HUE_CYAN;
    private static final float ICONO_VIOLETA = BitmapDescriptorFactory.HUE_VIOLET;
    private static final float ICONO_ROSA = BitmapDescriptorFactory.HUE_ROSE;


    public int getColortext (){
       return colort;
    }
    public int getColorbackground (){
        return colorf;
    }
    public float getColourMarker(){
        return icon;
    }

    public EarthQuakesColors(Double earthquakemagnitude) {
        if (earthquakemagnitude < 0){
            colorf = BLANCO;
            colort = NEGRO;
            icon = ICONO_ROSA;
        }else if (earthquakemagnitude >= 0 &&  earthquakemagnitude < 1.5){
            colorf = GRIS;
            colort = BLANCO;
            icon = ICONO_CYAN;
        }else if (earthquakemagnitude >= 1.5 && earthquakemagnitude < 2.5){
            colorf = AZUL;
            colort = BLANCO;
            icon = ICONO_AZUL;
        }else if(earthquakemagnitude >= 2.5 && earthquakemagnitude < 3.5){
            colorf = VERDE;
            colort = BLANCO;
            icon = ICONO_VERDE;
        }else if (earthquakemagnitude >= 3.5 && earthquakemagnitude < 4.5){
            colorf = AMARILLO;
            colort = NEGRO;
            icon = ICONO_AMARILLO;
        }else if(earthquakemagnitude >= 4.5 && earthquakemagnitude < 5.5){
            colorf = NARANJA;
            colort = BLANCO;
            icon = ICONO_NARANJA;
        }else if(earthquakemagnitude >= 5.5 && earthquakemagnitude < 6.5){
            colorf = ROJO;
            colort = BLANCO;
            icon = ICONO_ROJO;
        }else {
            colorf = NEGRO;
            colort = ROJO;
            icon = ICONO_VIOLETA;
        }
    }
}
