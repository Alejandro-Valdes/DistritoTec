package itesm.mx.androides_proyecto_distritotec.MenuOpcionesTransporte;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Alejandro Valdes on 28-Mar-15.
 */
public class OpcionesTransporteProvider {


    public static HashMap<String, List<String >> getInfo(List<String> liFavs){

        HashMap<String, List<String>> hmOpcionesTransporte = new HashMap<String, List<String>>();

        List<String> liExpreso = new ArrayList<String>();
        liExpreso.add("Valle");
        liExpreso.add("Galerias");
        liExpreso.add("San Nicolas");

        List<String> liCircuito = new ArrayList<String>();
        liCircuito.add("Villas TEC");
        liCircuito.add("Colonia Roma");
        liCircuito.add("Garza Sada");

        hmOpcionesTransporte.put("   Expreso", liExpreso);
        hmOpcionesTransporte.put("   Circuito", liCircuito);

        if(liFavs!=null){
            hmOpcionesTransporte.put("  Favoritos", liFavs);
        }

        return hmOpcionesTransporte;
    }
}
