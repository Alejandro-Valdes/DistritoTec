package itesm.mx.androides_proyecto_distritotec.MenuOpcionesTransporte;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * RouteOperations
 *
 * Clase que se encarga de todas las operaciones de la base de datos
 *
 * @author Jose Eduardo Elizondo Lozano A01089591
 * @author Oliver Alejandro Martínez Quiroz A01280416
 * @author Jesús Alejandro Valdés Valdés A0099044
 *
 * Version 1.0
 *
 */
public class RouteOperations {

    private SQLiteDatabase db; // Base de datos
    private RouteHelper dbHelper; // RouteHelper

    public static final String TABLE_ROUTE = "routes"; // Rutas de la base de datos
    public static final String COLUMN_ID = "_id"; // Id de la tupla
    public static final String COLUMN_NAME = "name"; // Nombre de la columna

    /**
     * Route Operations
     *
     * Metodo constructor RouteOperations
     *
     * @param context
     */
    public RouteOperations(Context context){
        dbHelper = new RouteHelper(context);
    }

    /**
     * open
     *
     * Metodo que abre la base de datos
     *
     * @throws SQLException
     */
    public void open() throws SQLException{
        // Obtiene la base de datos para leer y escribir
        db = dbHelper.getWritableDatabase();
    }

    /**
     * close
     *
     * Metodo que cierra la base de datos
     *
     * @return void
     */
    public void close(){db.close();};

    /**
     * addRoute
     *
     * Metodo que agrega una ruta a la base de datos
     *
     * @param route
     * @return void
     */
    public void addRoute(Route route) {
        // Inserta la ruta en la tabla
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, route.getName());
        db.insert(TABLE_ROUTE, null, values);
    }

    /**
     * deleteRoute
     *
     * Metodo que borra una ruta de la base de datos
     *
     * @param strRouteName
     * @return un valor <code>boolean</code> depende del resultado de la operacion
     */
    public boolean deleteRoute(String strRouteName) {
        boolean result = false;
        // Se hace un query con el nombre de la ruta
        String query = "SELECT * FROM " + TABLE_ROUTE +
                " WHERE " + COLUMN_NAME + "= \""+strRouteName+"\"";

        Cursor cursor = db.rawQuery(query, null);

        // Remueve la ruta de la base de datos
        if(cursor.moveToFirst()) {
            int id = Integer.parseInt(cursor.getString(0));
            db.delete(TABLE_ROUTE, COLUMN_ID + " = ?",
                    new String[] {String.valueOf(id)});
            cursor.close();
            result = true;
        }
        return result;
    }

    /**
     * getAllResources
     *
     * Metodo que regresa una lista con todas las rutas
     *
     * @return un valor <code>List<Route></code> lista con todas las rutas
     */
    public List<Route> getAllRoutes(){
        // Se crea la lista para guardar todas las rutas
        List<Route> liRoutes = new ArrayList<Route>();

        // Query para seleccionar todas las tuplas
        String selectQuery = "SELECT * FROM "+ TABLE_ROUTE;

        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do
            {
                Route route = new Route(Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1));

                liRoutes.add(route);
            } while (cursor.moveToNext()); // Mientras existan rutas agregalas a liRoutes
        }
        cursor.close();
        return liRoutes;
    }

    /**
     * getAllRoutesStr
     *
     * Metodo que guarda el nombre de las rutas en una lista
     *
     * @return un valor <code>List<Route></code> lista con todos los nombres de las rutas
     */
    public List<String> getAllRoutesStr(){
        // Se crea la lista para guardar todas las rutas
        List<String> liRoutes = new ArrayList<String>();

        // Query para seleccionar todas las tuplas
        String selectQuery = "SELECT * FROM "+ TABLE_ROUTE;

        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do
            {
                Route route = new Route(Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1));

                liRoutes.add(route.getName());
            } while (cursor.moveToNext());// Mientras existan rutas agregalas a liRoutes
        }

        // En caso de estar vacia
        if(liRoutes == null){
            liRoutes.add("Empty");
        }
        cursor.close();
        return liRoutes;
    }
}
