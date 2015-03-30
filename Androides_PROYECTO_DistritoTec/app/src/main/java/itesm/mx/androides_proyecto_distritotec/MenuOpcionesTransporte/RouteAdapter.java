package itesm.mx.androides_proyecto_distritotec.MenuOpcionesTransporte;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import itesm.mx.androides_proyecto_distritotec.R;

/**
 * RouteAdapter
 *
 * Clase adaptador de las rutas, obtiene los elementos de la ListView
 *
 * @author Jose Eduardo Elizondo Lozano A01089591
 * @author Oliver Alejandro Martínez Quiroz A01280416
 * @author Jesús Alejandro Valdés Valdés A0099044
 *
 * Version 1.0
 *
 */
public class RouteAdapter extends ArrayAdapter<Route>{
    private Context context; // Contexto de la aplicacion
    int iResourceID; // Id del recurso
    List<Route> liRoutes; // Lista de las rutas

    /**
     * RouteAdapter
     *
     * Metodo constructor que crea un adaptador tipo RouteAdapter
     *
     * @param context
     * @param iResourceID
     * @param liRoutes
     */
    public RouteAdapter(Context context, int iResourceID, List<Route> liRoutes) {
        super(context, iResourceID, liRoutes);
        this.context = context;
        this.iResourceID = iResourceID;
        this.liRoutes = liRoutes;
    }

    /**
     * getView
     *
     * @param position
     * @param convertView
     * @param parent
     * @return un valor <code>View</code>
     */
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;

        // Si la lista esta vacia le pone la informacion pasada como parametros
        if(row == null){
            LayoutInflater inflater =
                    (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(iResourceID, parent, false);
        }

        // Obtiene las variables del layout del renglon
        TextView tvRouteName = (TextView)row.findViewById(R.id.tvRow);

        // Obtiene el elemento de la lista de rutas
        Route route = liRoutes.get(position);

        // Asigna la ruta al TextView
        tvRouteName.setText(route.getName());
        return row;
    }
}
