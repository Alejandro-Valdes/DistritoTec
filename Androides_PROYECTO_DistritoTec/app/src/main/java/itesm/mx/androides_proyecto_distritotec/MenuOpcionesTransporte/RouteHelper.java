package itesm.mx.androides_proyecto_distritotec.MenuOpcionesTransporte;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Alejandro Valdes on 29-Mar-15.
 */
public class RouteHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "routesDB.db";
    public static final String TABLE_ROUTE = "routes";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";

    public RouteHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //comandos SQL
        String CREATE_ROUTES_TABLE = "CREATE TABLE "+TABLE_ROUTE+"("+
                COLUMN_ID+" INTEGER PRIMARY KEY,"+
                COLUMN_NAME+" TEXT" + ")";

        //LO EJECUTA
        db.execSQL(CREATE_ROUTES_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(RouteHelper.class.getName(),
                "Upgrading database from version " +
                        oldVersion + " to " + newVersion +
                            ", which will destroy all old data");

        db.execSQL("DROP TABLE IF EXISTS "+TABLE_ROUTE);
        onCreate(db);
    }
}
