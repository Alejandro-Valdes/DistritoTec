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
 * Created by Alejandro Valdes on 29-Mar-15.
 */
public class RouteAdapter extends ArrayAdapter<Route>{
    private Context context;
    int iResourceID;
    List<Route> liRoutes;

    public RouteAdapter(Context context, int iResourceID, List<Route> liRoutes) {
        super(context, iResourceID, liRoutes);
        this.context = context;
        this.iResourceID = iResourceID;
        this.liRoutes = liRoutes;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;

        //convertView --> vista a resura si es nulo se crea
        if(row == null){
            LayoutInflater inflater =
                    (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(iResourceID, parent, false);
        }

        //obtiene las variables del layout del renglon
        TextView tvRouteName = (TextView)row.findViewById(R.id.tvRow);


        //obtiene el elemento de la lista de rutas
        Route route = liRoutes.get(position);

        tvRouteName.setText(route.getName());
        return row;
    }


}
