package tk.mirenamorrortu.earthquakes.providers;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import tk.mirenamorrortu.earthquakes.Model.Coordinate;
import tk.mirenamorrortu.earthquakes.Model.EarthQuake;
import tk.mirenamorrortu.earthquakes.R;

/**
 * Created by Bafi on 27/03/15.
 */
public class EarthQuakesDB {

    private Context context;
    private EarthQuakesProvider helper;

    private static final String ERROR_INSERT_BD = "ERRINSDB";
    private static final String EARTHQUAKES = "EARTHQUAKES";

    private SQLiteDatabase db;
    private String[] result_columns = new String[] {
            EarthQuakesProvider.Columns._ID, EarthQuakesProvider.Columns.MAG_COLUMN_NAME,
            EarthQuakesProvider.Columns.PLACE_COLUMN_NAME, EarthQuakesProvider.Columns.TIME_COLUMN_NAME,
            EarthQuakesProvider.Columns.URL_COLUMN_NAME, EarthQuakesProvider.Columns.LAT_COLUMN_NAME,
            EarthQuakesProvider.Columns.LONG_COLUMN_NAME, EarthQuakesProvider.Columns.DEPTH_COLUMN_NAME};

    //instanciamos el OpenHelperDB de android con nuestra Base de Datos
    public EarthQuakesDB (Context context){
        this.context = context;
        this.helper = new EarthQuakesProvider();
    }

    public HashMap<String, Integer> getColumnsIndex (String [] result_columns, Cursor cursor){
        //buscamos el índice de las columnas con el nombre de columna que tenemos
        HashMap <String, Integer> res = new HashMap<String, Integer>();
        for (int i = 0; i < result_columns.length; i++){
            res.put(result_columns[i] , cursor.getColumnIndex(result_columns[i]) );
        }
        return res;
    }

    private ArrayList<EarthQuake> ConvertirConsultaTipoEarthQuake (Cursor cursor){
        ArrayList<EarthQuake> earthQuakes = new ArrayList<EarthQuake>();
        if (cursor != null && cursor.getCount() > 0){
            //cursor.moveToFirst();
            HashMap<String, Integer> colNameIndex = getColumnsIndex(result_columns, cursor);
            while (cursor.moveToNext()){
                //Cogemos los datos del terremoto
                EarthQuake eq = CursorToEarthQuake (cursor, colNameIndex);
                earthQuakes.add(eq);
            }
            cursor.close();
        }
        return earthQuakes;
    }

    public EarthQuake GetEarthQuake (String idEarthQuake){
        ContentResolver cr = context.getContentResolver();
        Cursor consulta = getEarthQuakesbyId(idEarthQuake);
        if (consulta!= null && consulta.getCount() > 0){
            HashMap<String, Integer> colum_index = getColumnsIndex(result_columns, consulta);
            consulta.moveToFirst();
            EarthQuake eq = CursorToEarthQuake(consulta, colum_index);
            consulta.close();
            return eq;
        }
        return null;
    }

    private Cursor getEarthQuakesbyId(String idEarthQuake) {

        ContentResolver cr = context.getContentResolver();
        String where = EarthQuakesProvider.Columns._ID + " = ?";
        String[] whereArgs = {idEarthQuake};
        return cr.query(EarthQuakesProvider.CONTENT_URI, result_columns, where, whereArgs, "");
    }

    public List<EarthQuake> GetAllEarthQuakes(){
        return ConvertirConsultaTipoEarthQuake(getEarthQuakes());
    }

    public List<EarthQuake> GetEarthQuakesFilerByMag(Double minMag){
        return ConvertirConsultaTipoEarthQuake(getEarthQuakesMinMag(minMag, EarthQuakesProvider.OrderBy.ORDER_BY_MAG, -1));
    }

    private Cursor getEarthQuakes (){
        ContentResolver cr = context.getContentResolver();
        return cr.query(EarthQuakesProvider.CONTENT_URI, result_columns, "", null, "");
        /*return db.query(EarthQuakeOpenHelper.DATABASE_TABLE, result_columns, null, null, null, null, null);*/
    }
    private Cursor getEarthQuakesMinMag(Double minMag, int orderBy, int group){
        // Clausula Where!!!
        ContentResolver cr = context.getContentResolver();

        String where = null;
        String[] whereArgs;
        if (minMag != null) {
            where = EarthQuakesProvider.Columns.MAG_COLUMN_NAME + " > ?";
            String[] wArgs = {
                   String.valueOf(minMag.toString()),
            };
            whereArgs = wArgs;
        }
        else{
            String[] wArgs = null;
            whereArgs = wArgs;

        }
        //Replace these with valid SQL statements as necessary.
        String	order;
        switch (orderBy){
            case EarthQuakesProvider.OrderBy.ORDER_BY_MAG:
                order = EarthQuakesProvider.Columns.MAG_COLUMN_NAME;
                break;
            case EarthQuakesProvider.OrderBy.ORDER_BY_LONG:
                order = EarthQuakesProvider.Columns.LONG_COLUMN_NAME;
                break;
            case EarthQuakesProvider.OrderBy.ORDER_BY_LAT:
                order = EarthQuakesProvider.Columns.LAT_COLUMN_NAME;
                break;
            case EarthQuakesProvider.OrderBy.ORDER_BY_DEPTH:
                order = EarthQuakesProvider.Columns.DEPTH_COLUMN_NAME;
                break;
            case EarthQuakesProvider.OrderBy.ORDER_BY_TIME:
                order = EarthQuakesProvider.Columns.TIME_COLUMN_NAME + " DESC";
                break;
            default:
                order = null;
                break;
        }

        return cr.query(EarthQuakesProvider.CONTENT_URI, result_columns, where, whereArgs, order);
    }

    public long addEarthQuakeToDB(EarthQuake eq) {
        //podemos coger el número de la fila en la que se ha añadido si fuera necesario!!
        return AddEarthQuake(eq);
    }

    private long AddEarthQuake (EarthQuake eq) {
        long row = -1;

        ContentValues newEQBD = EarthQuakeToContentValues(eq);

        if (!isEarthQuakeOnDataBase(eq.getId())) {
            ContentResolver cr = context.getContentResolver();

            cr.insert(EarthQuakesProvider.CONTENT_URI, newEQBD);
            Log.d("EARTHQUAKE_NEW", "New EarthQuake added");

        }else{
            Log.d("BD", "ID: " + eq.getId() + " ya está en la BD");
        }

        return row;
    }

    public boolean isEarthQuakeOnDataBase (String id){
        Cursor cons = getEarthQuakesbyId(id);
        String s = "null";
        int cont = -1;
        boolean res = false;
        try{
            if (cons != null){
                s = "NOTNULL";
                cont = cons.getCount();
                if (cont > 0){
                    res = true;
                }else{
                    res = false;
                }
            }else{
                res = false;
            }
        }catch (Exception ex){
            Log.d ("ERROR", "¿¿¿PORQUE????");
            Log.d ("ERROR", "cons: " + s );
            Log.d ("ERROR", "cons.getCount(): " + cont );
        }
        cons.close();
        return res;
    }

    private ContentValues EarthQuakeToContentValues (EarthQuake eq){
        ContentValues newEQBD = new ContentValues();
        newEQBD.put(EarthQuakesProvider.Columns._ID, eq.getId());
        newEQBD.put(EarthQuakesProvider.Columns.MAG_COLUMN_NAME, eq.getMagnitude());
        newEQBD.put(EarthQuakesProvider.Columns.PLACE_COLUMN_NAME, eq.getPlace());
        newEQBD.put(EarthQuakesProvider.Columns.TIME_COLUMN_NAME, eq.getTime().getTime());
        newEQBD.put(EarthQuakesProvider.Columns.URL_COLUMN_NAME, eq.getUrl());
        newEQBD.put(EarthQuakesProvider.Columns.LAT_COLUMN_NAME, eq.getCoords().getLat());
        newEQBD.put(EarthQuakesProvider.Columns.LONG_COLUMN_NAME, eq.getCoords().getLng());
        newEQBD.put(EarthQuakesProvider.Columns.DEPTH_COLUMN_NAME, eq.getCoords().getDepth());
        return newEQBD;
    }

    private EarthQuake CursorToEarthQuake (Cursor cursor, HashMap<String, Integer> colNameIndex){
        EarthQuake eq = null;
        if (cursor != null){
            eq = new EarthQuake();
            eq.setId(cursor.getString(colNameIndex.get(result_columns[0])));
            eq.setMagnitude(cursor.getDouble(colNameIndex.get(result_columns[1])));
            eq.setPlace(cursor.getString(colNameIndex.get(result_columns[2])));
            eq.setTime(cursor.getLong(colNameIndex.get(result_columns[3])));
            eq.setUrl(cursor.getString(colNameIndex.get(result_columns[4])));

            Coordinate coords = new Coordinate(
                    cursor.getDouble(colNameIndex.get(result_columns[5])),
                    cursor.getDouble(colNameIndex.get(result_columns[6])),
                    cursor.getDouble(colNameIndex.get(result_columns[7]))
            );
            eq.setCoords(coords);
        }
        return eq;
    }

}
