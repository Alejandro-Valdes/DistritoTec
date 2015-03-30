package itesm.mx.androides_proyecto_distritotec.MenuOpcionesTransporte;

/**
 * Route
 *
 * Clase que provee la informacion de las rutas para la base de datos
 *
 * @author Jose Eduardo Elizondo Lozano A01089591
 * @author Oliver Alejandro Martínez Quiroz A01280416
 * @author Jesús Alejandro Valdés Valdés A0099044
 *
 * Version 1.0
 *
 */
public class Route {
    private int id; // ID de la ruta
    private String name; // Nombre de la ruta

    /**
     * Route
     *
     * Metodo constructor para crear rutas
     *
     * @param id
     * @param name
     */
    public Route(int id, String name){
        this.id = id;
        this.name = name;
    }

    /**
     * Route
     *
     * Metodo constructor para crear rutas
     *
     * @param name
     */
    public Route(String name){
        this.name = name;
    }

    /**
     * setId
     *
     * Método para asignar un id
     *
     * @param id
     */
    public void setId(int id){this.id = id;};

    /**
     * getId
     *
     * Metodo que regresa el id
     *
     * @return un valor <code>int</code> el id de la ruta
     */
    public int getId(){return this.id;}

    /**
     * setName
     *
     * Metodo que asigna el nombre de la ruta
     *
     * @param name
     */
    public void setName(String name){this.name= name;};

    /**
     * getName
     *
     * Metodo que regresa el nombre de la ruta
     *
     * @return un valor <code>String</code> el nombre de la ruta
     */
    public String getName(){return this.name;};
}
