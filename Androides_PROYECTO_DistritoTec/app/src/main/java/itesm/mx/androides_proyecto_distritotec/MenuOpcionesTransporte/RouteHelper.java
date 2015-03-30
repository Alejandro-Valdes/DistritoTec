package itesm.mx.androides_proyecto_distritotec.MenuOpcionesTransporte;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * RouteHelper
 *
 * Clase que guardara la informacion en la base de datos
 *
 * @author Jose Eduardo Elizondo Lozano A01089591
 * @author Oliver Alejandro Martínez Quiroz A01280416
 * @author Jesús Alejandro Valdés Valdés A0099044
 *
 * Version 1.0
 *
 */
public class RouteHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1; // Version de la base de datos
    private static final String DATABASE_NAME = "routesDB.db"; // Nombre de la base de datos
    public static final String TABLE_ROUTE = "routes"; // Tabla con las rutas
    public static final String COLUMN_ID = "_id"; // Id de cada tupla
    public static final String COLUMN_NAME = "name";  // Nombre de la columna

    /**
     * RouteHelper
     *
     * Metodo constructor de RouteHelper
     *
     * @param context
     */
    public RouteHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * onCreate
     *
     * Metodo que crea las tablas de la base de datos
     *
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crea la tabla con la informacion
        String CREATE_ROUTES_TABLE = "CREATE TABLE "+TABLE_ROUTE+"("+
                COLUMN_ID+" INTEGER PRIMARY KEY,"+
                COLUMN_NAME+" TEXT" + ")";

        // Ejecuta la base de datos
        db.execSQL(CREATE_ROUTES_TABLE);
    }

    /**
     * onUpgrade
     *
     * Metodo para actualizar la base de datos, cambiar de version
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(RouteHelper.class.getName(),
                "Upgrading database from version " +
                        oldVersion + " to " + newVersion +
                            ", which will destroy all old data");

        // Ejecuta el upgrade en la base de datos
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_ROUTE);
        onCreate(db);
    }
}
