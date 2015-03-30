package itesm.mx.androides_proyecto_distritotec.MenuOpcionesTransporte;

/**
 * Created by Alejandro Valdes on 29-Mar-15.
 */
public class Route {
    private int id;
    private String name;

    public Route(int id, String name){
        this.id = id;
        this.name = name;
    }

    public Route(String name){
        this.name = name;
    }

    public void setId(int id){this.id = id;};
    public int getId(){return this.id;}

    public void setName(String name){this.name= name;};
    public String getName(){return this.name;};
}
