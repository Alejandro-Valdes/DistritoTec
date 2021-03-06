package itesm.mx.androides_proyecto_distritotec.LoginSingup;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import itesm.mx.androides_proyecto_distritotec.R;

/**
 * SingupActivity
 *
 * Clase que se encarga del registro del usuario
 *
 * @author José Eduardo Elizondo Lozano A01089591
 * @author Jesús Alejandro Valdés Valdés A0099044
 * @author Oliver Alejandro Martínez Quiroz A01280416
 * @version 1.0
 */

public class SignupActivity extends ActionBarActivity {

    EditText usernameET; // Campo usuario
    EditText passwordET; // Campo contraseña
    EditText passwordRepET; // Campo repite contraseña
    EditText emailET; // Campo email
    Button signupButton; // boton Singup
    String password; // Contraseña
    String passwordRep; // Repite contraseña
    String email; // Email
    String usuario; // nombre usuario

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getSupportActionBar().hide(); // Esconde el SupportActionBar

        usernameET = (EditText) findViewById(R.id.usuarioET);
        passwordET = (EditText) findViewById(R.id.passwordET);
        passwordRepET = (EditText) findViewById(R.id.passwordRepET);
        emailET = (EditText) findViewById(R.id.emailET);
        signupButton = (Button) findViewById(R.id.signupButton);

        signupButton.setOnClickListener(new View.OnClickListener() {

            /**
             * onClick
             *
             * Metodo que verifica si el usuario hace click en el boton Singup
             *
             * @Param v de tipo <code>View</code> recibe el view donde se realizo el click
             * @return void
             */
            @Override
            public void onClick(View v) {
                // El input del usuario de pasa a las variables correspontientes
                password = passwordET.getText().toString(); // Password
                passwordRep = passwordRepET.getText().toString(); // Password
                usuario = usernameET.getText().toString(); // Username
                email = emailET.getText().toString(); // Email

                Context context = getApplicationContext();
                ConnectivityManager cm =
                        (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

                // Verifica que los campos no se dejen vacios
                if(usuario.equals("") || password.equals("") || passwordRep.equals("") ||
                        email.equals("")){
                    Toast.makeText(getApplicationContext(),
                            "Porfavor llena todos los campos", Toast.LENGTH_LONG).show();
                } else if (!password.equals(passwordRep)) {
                    Toast.makeText(getApplicationContext(),
                            "Contraseñas diferentes", Toast.LENGTH_LONG).show();
                } else {
                    // Se crea un nuevo usuario y se guarda su informacion
                    ParseUser user = new ParseUser();
                    user.setUsername(usuario); // UserName
                    user.setPassword(password); // Password
                    user.setEmail(email);

                    /* Conecta al ausuario a la aplicacion mientras despliega un mensaje de exito
                     * o de error
                     */
                    // Checa si se tiene conexión a internet para mandar el registro
                    if(isConnected) {
                        final ProgressDialog dialog = ProgressDialog.show(SignupActivity.this,
                                "Espere porfavor","Registrando usuario",true,false);

                        user.signUpInBackground(new SignUpCallback() {

                            /**
                             * done
                             *
                             * Metodo que verifica si hubo error a la hora de crear el usuario
                             *
                             * @Param e de tipo <code>ParseException</code> si no hay exception marca
                             * registro exitoso de lo contrario marca error.
                             * @return void
                             */
                            @Override
                            public void done(ParseException e) {
                                dialog.dismiss();
                                if(e == null){
                                    Toast.makeText(getApplicationContext(),"Registro exitoso!",
                                            Toast.LENGTH_LONG).show();
                                    finish();
                                } else {
                                    Toast.makeText(getApplicationContext(),"Hubo un error, " +
                                            "intenta de nuevo", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    } else {
                        Toast.makeText(SignupActivity.this,
                                "Verifique conexion a internet", Toast.LENGTH_SHORT).show();
                    }

                }
            }

        });

    }

}
