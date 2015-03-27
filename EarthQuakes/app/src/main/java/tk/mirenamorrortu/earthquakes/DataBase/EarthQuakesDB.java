package tk.mirenamorrortu.earthquakes.DataBase;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import tk.mirenamorrortu.earthquakes.Model.EarthQuake;

/**
 * Created by Bafi on 27/03/15.
 */
public class EarthQuakesDB {

    private EarthQuakeOpenHelper helper;
    private SQLiteDatabase db;

    //instanciamos el OpenHelperDB de android con nuestra Base de Datos
    public EarthQuakesDB (Context context){
        this.helper = new EarthQuakeOpenHelper(context, EarthQuakeOpenHelper.DATABASE_NAME, null, EarthQuakeOpenHelper.DATABASE_VERSION);
        this.db = helper.getWritableDatabase();
    }

    //Describimos la clase privada que manejará el helper
    private static class EarthQuakeOpenHelper extends SQLiteOpenHelper{

        private static final String DATABASE_NAME = "earthquakes.db";
        private static final String DATABASE_TABLE = "EARTHQUAKES";
        private static final Integer DATABASE_VERSION = 1;
        private static final String DATABASE_CREATE = "CREATE TABLE " + DATABASE_TABLE +
                "(_id TEXT PRIMARY KEY, place TEXT, magnitude REAL, lat REAL, long REAL, depth REAL, url TEXT, time INTEGER)";

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
