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

/*  DistritoTEC
	Copyright (C) 2014 - ITESM

	This program is free software: you can redistribute it and/or modify
	it under the terms of the GNU General Public License as published by
	the Free Software Foundation, either version 3 of the License, or
	(at your option) any later version.

	This program is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
	GNU General Public License for more details.

	You should have received a copy of the GNU General Public License
	along with this program.  If not, see <http://www.gnu.org/licenses/>.


Authors:

   ITESM representatives
	Ing. Martha Sordia Salinas <msordia@itesm.mx>
    Ing. Mario de la Fuente <mario.delafuente@itesm.mx>

   ITESM students
	Jose Eduardo Elizondo Lozano eduardolozano1993@gmail.com
	Jesús Alejandro Valdés Valdés alejandro.valdesval@gmail.com
	Oliver Alejandro Martínez Quiroz oliver.mtz10@gmail.com
 */
/**
 * MainActivity
 *
 * Clase
 *
 * @author Jose Eduardo Elizondo Lozano A01089591
 * @author Oliver Alejandro Martínez Quiroz A01280416
 * @author Jesús Alejandro Valdés Valdés A0099044
 *
 * Version 1.0
 *
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Codigo de inicializacion
        Parse.initialize(this, "Pc66LZ9sLsdgr10KSJPD1qTwO74Ov6NWvDOTcYJb",
                "huXDJ9jUWe0zP1dDOFMCByyUAZ5RflNR9mN1pGly");

        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();

        defaultACL.setPublicReadAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);

        // Determina si el usuario es anondimo
        if(ParseAnonymousUtils.isLinked(ParseUser.getCurrentUser())){

            // Si es anonimo, lo mandamos a la actividad LoginSignUP
            Intent intent = new Intent(MainActivity.this, LoginSignupActivity.class);
            startActivity(intent);
            finish();
        } else {

            // Si el usuario no es un usuario anonimo hacemos un get de los datos del usuario
            ParseUser currentUser = ParseUser.getCurrentUser();
            if(currentUser != null) {

                // Enviamos al usuario que no es anonimo a OpcionTransporte
                Intent intent = new Intent(MainActivity.this, OpcionTransporte.class);
                startActivity(intent);
                finish();
            }
        }
    }
}
