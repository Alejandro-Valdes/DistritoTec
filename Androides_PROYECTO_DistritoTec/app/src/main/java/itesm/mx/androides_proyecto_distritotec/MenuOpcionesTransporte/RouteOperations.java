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
 * Created by Alejandro Valdes on 29-Mar-15.
 */
public class RouteOperations {

    private SQLiteDatabase db;
    private RouteHelper dbHelper;

    public static final String TABLE_ROUTE = "routes";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";

    public RouteOperations(Context context){
        dbHelper = new RouteHelper(context);
    }

    public void open() throws SQLException{
        //get db for read and write
        db = dbHelper.getWritableDatabase();
    }

    public void close(){db.close();};

    public void addRoute(Route route) {
        ContentValues values = new ContentValues();

        values.put(COLUMN_NAME, route.getName());

        db.insert(TABLE_ROUTE, null, values);
    }

    public boolean deleteRoute(String strRouteName) {
        boolean result = false;

        String query = "SELECT * FROM " + TABLE_ROUTE +
                " WHERE " + COLUMN_NAME + "= \""+strRouteName+"\"";

        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()) {
            int id = Integer.parseInt(cursor.getString(0));
            db.delete(TABLE_ROUTE, COLUMN_ID + " = ?",
                    new String[] {String.valueOf(id)});
            cursor.close();
            result = true;
        }
        return result;
    }

    public List<Route> getAllRoutes(){

        List<Route> liRoutes = new ArrayList<Route>();

        String selectQuery = "SELECT * FROM "+ TABLE_ROUTE;

        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do
            {
                Route route = new Route(Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1));

                liRoutes.add(route);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return liRoutes;

    }

    public List<String> getAllRoutesStr(){

        List<String> liRoutes = new ArrayList<String>();

        String selectQuery = "SELECT * FROM "+ TABLE_ROUTE;

        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do
            {
                Route route = new Route(Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1));

                liRoutes.add(route.getName());
            } while (cursor.moveToNext());
        }


        if(liRoutes == null){
            liRoutes.add("Empty");
        }
        cursor.close();
        return liRoutes;

    }

}
