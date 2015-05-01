package itesm.mx.androides_proyecto_distritotec.MenuOpcionesTransporte;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * OpcionTransporteProvider
 *
 * Clase que provee la lista de las rutas disponibles
 *
 * @author Jose Eduardo Elizondo Lozano A01089591
 * @author Oliver Alejandro Martínez Quiroz A01280416
 * @author Jesús Alejandro Valdés Valdés A0099044
 *
 * Version 1.0
 *
 */
public class OpcionesTransporteProvider {

    /**
     * getInfo
     *
     * @param liFavs
     * @return un valor <codeHashMap<String, List<String >></code> la lista de las rutas disponibles
     */
    public static HashMap<String, List<String >> getInfo(List<String> liFavs){

        HashMap<String, List<String>> hmOpcionesTransporte = new HashMap<String, List<String>>();

        // Lista del Expreso TEC
        List<String> liExpreso = new ArrayList<String>();
        liExpreso.add("Valle");
        liExpreso.add("Galerias");
        liExpreso.add("San Nicolas");
        liExpreso.add("Guadalupe");

        // Lista del Circuito TEC
        List<String> liCircuito = new ArrayList<String>();
        liCircuito.add("Villas TEC");
        liCircuito.add("Colonia Roma");
        liCircuito.add("Garza Sada");

        // Nombre de las listas padres
        hmOpcionesTransporte.put("   Circuito", liCircuito);
        hmOpcionesTransporte.put("   Expreso", liExpreso);
        hmOpcionesTransporte.put("   Favoritos", liFavs);

        return hmOpcionesTransporte;
    }
}
