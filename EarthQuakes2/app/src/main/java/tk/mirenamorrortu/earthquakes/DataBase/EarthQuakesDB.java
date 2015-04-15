package tk.mirenamorrortu.earthquakes.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import tk.mirenamorrortu.earthquakes.Model.Coordinate;
import tk.mirenamorrortu.earthquakes.Model.EarthQuake;

/**
 * Created by Bafi on 27/03/15.
 */
public class EarthQuakesDB {

    private static final String ERROR_INSERT_BD = "ERRINSDB";
    private EarthQuakeOpenHelper helper;
    private SQLiteDatabase db;
    private String[] result_columns = new String[] {
            EarthQuakeOpenHelper.ID_COLUMN_NAME, EarthQuakeOpenHelper.MAG_COLUMN_NAME,
            EarthQuakeOpenHelper.PLACE_COLUMN_NAME, EarthQuakeOpenHelper.TIME_COLUMN_NAME,
            EarthQuakeOpenHelper.URL_COLUMN_NAME, EarthQuakeOpenHelper.LAT_COLUMN_NAME,
            EarthQuakeOpenHelper.LONG_COLUMN_NAME, EarthQuakeOpenHelper.DEPTH_COLUMN_NAME};

    //instanciamos el OpenHelperDB de android con nuestra Base de Datos
    public EarthQuakesDB (Context context){
        this.helper = new EarthQuakeOpenHelper(context, EarthQuakeOpenHelper.DATABASE_NAME, null, EarthQuakeOpenHelper.DATABASE_VERSION);
        this.db = helper.getWritableDatabase();
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
        String where = EarthQuakeOpenHelper.ID_COLUMN_NAME + " = ?";
        String[] whereArgs = {idEarthQuake};
        return db.query(EarthQuakeOpenHelper.DATABASE_TABLE, result_columns, where, whereArgs, null, null, null);
    }

    public List<EarthQuake> GetAllEarthQuakes(){
        return ConvertirConsultaTipoEarthQuake(getEarthQuakes());
    }

    public List<EarthQuake> GetEarthQuakesFilerByMag(Double minMag){
        return ConvertirConsultaTipoEarthQuake(getEarthQuakesMinMag(minMag, EarthQuakeOpenHelper.ORDER_BY_MAG, -1));
    }

    private Cursor getEarthQuakes (){
        return db.query(EarthQuakeOpenHelper.DATABASE_TABLE, result_columns, null, null, null, null, null);
    }

    private Cursor getEarthQuakesMinMag(Double minMag, int orderBy, int group){
        // Clausula Where!!!
        String where = null;
        String[] whereArgs;
        if (minMag != null) {
            where = EarthQuakeOpenHelper.MAG_COLUMN_NAME + " > ?";
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


        String	groupBy;
        switch (group){
            case EarthQuakeOpenHelper.ORDER_BY_MAG:
                groupBy = EarthQuakeOpenHelper.MAG_COLUMN_NAME;
                break;
            case EarthQuakeOpenHelper.ORDER_BY_LONG:
                groupBy = EarthQuakeOpenHelper.LONG_COLUMN_NAME;
                break;
            case EarthQuakeOpenHelper.ORDER_BY_LAT:
                groupBy = EarthQuakeOpenHelper.LAT_COLUMN_NAME;
                break;
            case EarthQuakeOpenHelper.ORDER_BY_DEPTH:
                groupBy = EarthQuakeOpenHelper.DEPTH_COLUMN_NAME;
                break;
            case EarthQuakeOpenHelper.ORDER_BY_TIME:
                groupBy = EarthQuakeOpenHelper.TIME_COLUMN_NAME;
                break;
            default:
                groupBy = null;
                break;
        }
        String	having	=	null;
        String	order;
        switch (orderBy){
            case EarthQuakeOpenHelper.ORDER_BY_MAG:
                order = EarthQuakeOpenHelper.MAG_COLUMN_NAME;
                break;
            case EarthQuakeOpenHelper.ORDER_BY_LONG:
                order = EarthQuakeOpenHelper.LONG_COLUMN_NAME;
                break;
            case EarthQuakeOpenHelper.ORDER_BY_LAT:
                order = EarthQuakeOpenHelper.LAT_COLUMN_NAME;
                break;
            case EarthQuakeOpenHelper.ORDER_BY_DEPTH:
                order = EarthQuakeOpenHelper.DEPTH_COLUMN_NAME;
                break;
            case EarthQuakeOpenHelper.ORDER_BY_TIME:
                order = EarthQuakeOpenHelper.TIME_COLUMN_NAME + " DESC";
                break;
            default:
                order = null;
                break;
        }
        return db.query(EarthQuakeOpenHelper.DATABASE_TABLE, result_columns, where, whereArgs, groupBy, having, order);
    }

    public long addEarthQuakeToDB(EarthQuake eq) {
        //podemos coger el número de la fila en la que se ha añadido si fuera necesario!!
        return AddEarthQuake(eq);
    }

    private long AddEarthQuake (EarthQuake eq) {
        long row = -1;
        String id = eq.getId().toString();
        if (!isEarthQuakeOnDataBase(id)){
            Log.d("BD", "ID: " + id + " no está, lo añadimos");
            ContentValues newEQBD = EarthQuakeToContentValues(eq);
            try{
                row = db.insertOrThrow(EarthQuakeOpenHelper.DATABASE_TABLE, null, newEQBD);
                Log.d("EARTHQUAKE_NEW", "row : " + String.valueOf(row));
            }catch (Exception ex){
                Log.d("ERROR_INSERT_BD", "Error al insertar en la BD: " + ex.getMessage());
            }
        }
        else{
            Log.d("BD", "ID: " + id + " ya está en la BD");
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
        newEQBD.put(EarthQuakeOpenHelper.ID_COLUMN_NAME, eq.getId());
        newEQBD.put(EarthQuakeOpenHelper.MAG_COLUMN_NAME, eq.getMagnitude());
        newEQBD.put(EarthQuakeOpenHelper.PLACE_COLUMN_NAME, eq.getPlace());
        newEQBD.put(EarthQuakeOpenHelper.TIME_COLUMN_NAME, eq.getTime().getTime());
        newEQBD.put(EarthQuakeOpenHelper.URL_COLUMN_NAME, eq.getUrl());
        newEQBD.put(EarthQuakeOpenHelper.LAT_COLUMN_NAME, eq.getCoords().getLat());
        newEQBD.put(EarthQuakeOpenHelper.LONG_COLUMN_NAME, eq.getCoords().getLng());
        newEQBD.put(EarthQuakeOpenHelper.DEPTH_COLUMN_NAME, eq.getCoords().getDepth());
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
    //Describimos la clase privada que manejará el helper
    private static class EarthQuakeOpenHelper extends SQLiteOpenHelper{

        private static final String DATABASE_NAME = "earthquakes.db";
        private static final String DATABASE_TABLE = "EARTHQUAKES";
        private static final Integer DATABASE_VERSION = 1;
        private static final String DATABASE_CREATE = "CREATE TABLE " + DATABASE_TABLE +
                "(_id TEXT PRIMARY KEY, place TEXT, magnitude REAL, lat REAL, long REAL, depth REAL, url TEXT, time INTEGER)";
        private static final String ID_COLUMN_NAME = "_id";
        private static final String PLACE_COLUMN_NAME = "place";
        private static final String MAG_COLUMN_NAME = "magnitude";
        private static final String LAT_COLUMN_NAME = "lat";
        private static final String LONG_COLUMN_NAME = "long";
        private static final String DEPTH_COLUMN_NAME = "depth";
        private static final String URL_COLUMN_NAME = "url";
        private static final String TIME_COLUMN_NAME = "time";

        private static final int ORDER_BY_MAG = 0;
        private static final int ORDER_BY_LONG = 1;
        private static final int ORDER_BY_LAT = 2;
        private static final int ORDER_BY_DEPTH = 3;
        private static final int ORDER_BY_TIME = 4;

        public EarthQuakeOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE );
            onCreate(db);
        }
    }


    //Creamos las funciones que necesitemos de acceso, modificación, etc...
}
