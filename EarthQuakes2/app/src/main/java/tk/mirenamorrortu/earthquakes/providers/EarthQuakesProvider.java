package tk.mirenamorrortu.earthquakes.providers;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.provider.BaseColumns;

public class EarthQuakesProvider extends ContentProvider {

    public static final String URI = "tk.mirenamorrortu.provider.EarthQuakes";
    public static final Uri CONTENT_URI = Uri.parse("content://" + URI + "/earthquakes");

    /*Si tubieramos más de una URI, porque vamos a facilitar más cosas que los terremotos, necesitamos un UriMatcher
    * para saber a cual está accediendo
    *
    * private static final UriMatcher uriMatcher;
    *
    * static {
    *   uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    *   uriMatcher.addURI("tk.mirenamorrortu.provider.EarthQuakes",
    *                       "elements", ALLEARTHQUAKES);
    *   }
    *
    * */

    private EarthQuakeOpenHelper earthQuakeOpenHelper;
    private static final int ALLROWS = 1;
    private static final int SINGLE_ROW = 2;
    public String getDataBaseName (){
        return EarthQuakeOpenHelper.DATABASE_NAME;
    }
    private static final UriMatcher uriMatcher;
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(URI, "earthquakes", 1);
        uriMatcher.addURI(URI, "earthquakes/#", 2);
    }

    @Override
    public boolean onCreate() {
        earthQuakeOpenHelper = new EarthQuakeOpenHelper(getContext(), EarthQuakeOpenHelper.DATABASE_NAME, null, EarthQuakeOpenHelper.DATABASE_VERSION);

        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db;
        try {
            db = earthQuakeOpenHelper.getWritableDatabase();
        } catch (Exception ex){
            db = earthQuakeOpenHelper.getReadableDatabase();
        }

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        switch (uriMatcher.match(uri)){
            case SINGLE_ROW:
                String ROW_ID =uri.getPathSegments().get(1);

                queryBuilder.appendWhere(Columns._ID + " = ?");
                selectionArgs = new String[]{ ROW_ID };
                break;
            default:
                break;
        }
        queryBuilder.setTables(EarthQuakeOpenHelper.DATABASE_TABLE);

        Cursor cursor = queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);

        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)){
            case ALLROWS:
                return "vnd.android.cursor.dir/vnd.mirenamorrortu.providers.EarthQuakes";
            case SINGLE_ROW:
                return "vnd.android.cursor.item/vnd.mirenamorrortu.providers.EarthQuakes";
            default:
                throw new UnsupportedOperationException(uri + " Not Supported");
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = earthQuakeOpenHelper.getWritableDatabase();

/*        switch (uriMatcher.match(uri)){
            case ALL_EARTHQUAKES_ROWS:
                String table = EarthQuakeOpenHelper.DATABASE_TABLE;
                break;
            default:
                break;
        }*/
        long id = db.insert(EarthQuakeOpenHelper.DATABASE_TABLE, null, values);
        if (id > -1){
            Uri insertedId = ContentUris.withAppendedId(CONTENT_URI, id);
            getContext().getContentResolver().notifyChange(insertedId, null);
            return insertedId;
        }else{
            return null;
        }

    }



    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public static class Columns implements BaseColumns{
        //private static final String ID_COLUMN_NAME = "_id";
        public static final String PLACE_COLUMN_NAME = "place";
        public static final String MAG_COLUMN_NAME = "magnitude";
        public static final String LAT_COLUMN_NAME = "lat";
        public static final String LONG_COLUMN_NAME = "long";
        public static final String DEPTH_COLUMN_NAME = "depth";
        public static final String URL_COLUMN_NAME = "url";
        public static final String TIME_COLUMN_NAME = "time";
    }
    public static class OrderBy{
        public static final int ORDER_BY_MAG = 0;
        public static final int ORDER_BY_LONG = 1;
        public static final int ORDER_BY_LAT = 2;
        public static final int ORDER_BY_DEPTH = 3;
        public static final int ORDER_BY_TIME = 4;
    }

    public static String[] getallprojection(){
        String [] pj = new String[] {
               Columns._ID, Columns.PLACE_COLUMN_NAME, Columns.MAG_COLUMN_NAME,
               Columns.LAT_COLUMN_NAME, Columns.LONG_COLUMN_NAME, Columns.DEPTH_COLUMN_NAME,
                Columns.URL_COLUMN_NAME, Columns.TIME_COLUMN_NAME
        };
        return pj;
    }

    private static class EarthQuakeOpenHelper extends SQLiteOpenHelper {
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

}
