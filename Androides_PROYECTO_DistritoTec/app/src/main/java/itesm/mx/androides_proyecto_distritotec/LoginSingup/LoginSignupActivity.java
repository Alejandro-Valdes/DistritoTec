package itesm.mx.androides_proyecto_distritotec.LoginSingup;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import itesm.mx.androides_proyecto_distritotec.MenuOpcionesTransporte.OpcionTransporte;
import itesm.mx.androides_proyecto_distritotec.R;

/**
 * LoginSingupActivity
 *
 * Clase que se encarga de manejar la informacion de login para poder acceder a la aplicacion
 *
 * @author Jose Eduardo Elizondo Lozano A01089591
 * @author Oliver Alejandro Martínez Quiroz A01280416
 * @author Jesús Alejandro Valdés Valdés A0099044
 *
 * Version 1.0
 *
 */

public class LoginSignupActivity extends ActionBarActivity {

    Button btnLogin; // Boton Login
    Button btnSignup; // Boton Singup
    Button btnForgot; // Boton Forgot Password
    EditText etPassword; // EditText Password
    EditText etUsername; // EditText Username
    String strUsername; // String que guarda el nombre del usuario
    String strPassword; // String que guarda la contraseña del usuario

    /**
     * onCreate
     *
     * Metodo encargado de crear la actividad de LoginSingup
     *
     * @Param savedInstanceState de tipo <code>Bundle</code> se encarga de guardar la informacion
     * de la actividad LoginSingup.
     * @return void
     */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginsignup); // Se le asgina el layout a la actividad

        /* Se asignan los views a su variable correspontiendte */
        btnLogin = (Button)findViewById(R.id.btnLogin); // Boton Login
        btnSignup = (Button)findViewById(R.id.btnSignup); // Boton Singup
        btnForgot = (Button)findViewById(R.id.btnForgot); // Boton Singup
        etUsername = (EditText)findViewById(R.id.etUserName); // EditText UserName
        etPassword = (EditText)findViewById(R.id.etPassword); // EditText Password

        getSupportActionBar().hide(); // Esconde el SupportActionBar

        // Metodo on ClickListener para el Boton Login
        btnLogin.setOnClickListener(new View.OnClickListener() {
            /**
             * onClick
             *
             * Metodo que verifica si el usuario hace click en el boton Login
             *
             * @Param v de tipo <code>View</code> recibe el view donde se realizo el click
             * @return void
             */
            @Override
            public void onClick(View v) {
                Context context = getApplicationContext();
                ConnectivityManager cm =
                        (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

                if(isConnected) {
                    // El input del usuario de pasa a las variables correspontientes
                    strPassword = etPassword.getText().toString(); // UserName
                    strUsername = etUsername.getText().toString(); // Password

                    if(strUsername.equals("") || strPassword.equals("")){
                        Toast.makeText(getApplicationContext(), "Favor de llenar todos los campos",
                                Toast.LENGTH_SHORT).show();
                    }

                    else {
                        // Metodo logInInBackground para verificar los datos de ParseUser
                        ParseUser.logInInBackground(strUsername, strPassword,
                                // LLamada LogInCallback
                                new LogInCallback() {

                                    /**
                                     * done
                                     * <p/>
                                     * Metodo que verifica si los datos de usuario son correctos y en caso
                                     * de serlo lo deja accesar a la actividad OpcionTransporte de lo
                                     * contrario le pedira que cheque su informacion o que se registre.
                                     *
                                     * @return void
                                     * @Param parseUser de tipo <code>ParseUser</code> guarda la informaicon
                                     * del usuario
                                     * @Param e de tipo <code>ParseException</code> no se usa
                                     */
                                    @Override
                                    public void done(ParseUser parseUser, ParseException e) {
                                        if (parseUser != null) {

                                            Intent intent = new Intent(LoginSignupActivity.this,
                                                    OpcionTransporte.class);
                                            startActivity(intent); // Llama a la actividad OpcionTransporte

                                            Toast.makeText(getApplicationContext(), "Conectado",
                                                    Toast.LENGTH_SHORT).show();
                                            finish(); // Termina la actividad
                                        } else {
                                            Toast.makeText(getApplicationContext(), "Verifica tu informacion,"
                                                    + " o registrate", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                } else {
                    Toast.makeText(LoginSignupActivity.this,
                            "Verifique conexion a internet", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Metodo on ClickListener para el Boton Singup
        btnSignup.setOnClickListener(new View.OnClickListener() {
          @Override
        public void onClick(View v){
              Intent RegisterIntent = new Intent(LoginSignupActivity.this, SignupActivity.class);
              startActivity(RegisterIntent);
          }
        });

        btnForgot.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent ForgotIntent = new Intent(LoginSignupActivity.this, ForgotPassword.class);
                startActivity(ForgotIntent);
            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            finish();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}

