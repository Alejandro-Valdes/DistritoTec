package itesm.mx.androides_proyecto_distritotec.ParseApplication;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseUser;
import android.app.Application;

/**
 * ParseApplication
 *
 * Clase que se encarga de Parsear los datos
 *
 * @author Jose Eduardo Elizondo Lozano A01089591
 * @author Oliver Alejandro Martínez Quiroz A01280416
 * @author Jesús Alejandro Valdés Valdés A0099044
 *
 * Version 1.0
 *
 */
public class ParseApplication extends Application {

    /**
     * onCreate
     *
     * Metodo que crea la aplicacion Parse
     *
     * @return void
     */
    @Override
    public void onCreate(){
        super.onCreate();

        // Codidigo de inicializacion
        Parse.initialize(this,"7w5XCpgdU1WmdktjpSgM2cuYMYrRdCtm70j3odNz",
                "0q860t7vxPtNEPcfwUqY1F45n3BJpMVHEYSdJYu1");
        
        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();

        defaultACL.setPublicReadAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);
    }
}
