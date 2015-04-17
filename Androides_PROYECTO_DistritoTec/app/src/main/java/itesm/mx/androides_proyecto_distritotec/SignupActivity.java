package itesm.mx.androides_proyecto_distritotec;

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


public class SignupActivity extends ActionBarActivity {

    EditText usernameET;
    EditText passwordET;
    Button signupButton;
    String password;
    String usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        usernameET = (EditText) findViewById(R.id.usuarioET);
        passwordET = (EditText) findViewById(R.id.passwordET);
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
                usuario = usernameET.getText().toString(); // Username

                // Verifica que los campos no se dejen vacios
                if(usuario.equals("") || password.equals("")){
                    Toast.makeText(getApplicationContext(),
                            "Porfavor llena todos los campos", Toast.LENGTH_LONG).show();
                } else {
                    // Se crea un nuevo usuario y se guarda su informacion
                    ParseUser user = new ParseUser();
                    user.setUsername(usuario); // UserName
                    user.setPassword(password); // Password

                    /* Conecta al ausuario a la aplicacion mientras despliega un mensaje de exito
                     * o de error
                     */
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
                            if(e == null){
                                Toast.makeText(getApplicationContext(),"Registro exitoso!",
                                        Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getApplicationContext(),"Hubo un error, " +
                                        "intenta de nuevo", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }

        });

    }

}
