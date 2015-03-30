package itesm.mx.androides_proyecto_distritotec;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseUser;

import itesm.mx.androides_proyecto_distritotec.LoginSingup.LoginSignupActivity;
import itesm.mx.androides_proyecto_distritotec.MenuOpcionesTransporte.OpcionTransporte;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //codigos de inicializacion
        Parse.initialize(this, "Pc66LZ9sLsdgr10KSJPD1qTwO74Ov6NWvDOTcYJb",
                "huXDJ9jUWe0zP1dDOFMCByyUAZ5RflNR9mN1pGly");

        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();

        defaultACL.setPublicReadAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);

        //Determicar si el usuario es anonimo
        if(ParseAnonymousUtils.isLinked(ParseUser.getCurrentUser())){
            //si es anonimo, lo mandamos a la actividad LoginSignUP
            Intent intent = new Intent(MainActivity.this, LoginSignupActivity.class);
            startActivity(intent);
            finish();
        } else {
            //Si el usuario no es un usuario anonimo
            //hacemos un get de los datos del usuario
            ParseUser currentUser = ParseUser.getCurrentUser();
            if(currentUser != null) {
                //Enviamos al usuario que no es anonimo a OpcionTransporte
                Intent intent = new Intent(MainActivity.this, OpcionTransporte.class);
                startActivity(intent);
                finish();
            }
        }
    }
}
