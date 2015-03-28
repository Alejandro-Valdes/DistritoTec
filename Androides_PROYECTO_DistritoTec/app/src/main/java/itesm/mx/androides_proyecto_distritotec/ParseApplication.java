package itesm.mx.androides_proyecto_distritotec;

/**
 * Created by Alejandro Valdes on 28-Mar-15.
 */

/**
 * Clase que sirve para verificar nuestra app dentro de parse
 */


import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseUser;
import android.app.Application;

public class ParseApplication extends Application {

    @Override
    public void onCreate(){
        super.onCreate();

        //codigos de inicializacion
        Parse.initialize(this,"7w5XCpgdU1WmdktjpSgM2cuYMYrRdCtm70j3odNz",
                "0q860t7vxPtNEPcfwUqY1F45n3BJpMVHEYSdJYu1");

        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();

        defaultACL.setPublicReadAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);
    }
}
