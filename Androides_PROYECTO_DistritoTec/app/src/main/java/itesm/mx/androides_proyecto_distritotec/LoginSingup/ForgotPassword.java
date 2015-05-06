package itesm.mx.androides_proyecto_distritotec.LoginSingup;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;

import itesm.mx.androides_proyecto_distritotec.R;

/**
 * ForogotPassword
 *
 * Clase que se encarga de mandar un request a Parse para cambiar la clave del usuario
 *
 * @author José Eduardo Elizondo Lozano A01089591
 * @author Jesús Alejandro Valdés Valdés A0099044
 * @author Oliver Alejandro Martínez Quiroz A01280416
 * @version 1.0
 */

public class ForgotPassword extends ActionBarActivity {

    EditText emailET; // Campo donde se ingresa el correo
    Button btnSendEmail; // boton Enviar mail
    String email; // Correo del usuario

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);
        getSupportActionBar().hide();
        emailET = (EditText) findViewById(R.id.emailET);
        btnSendEmail = (Button) findViewById(R.id.btnSendEmail);

        // Click Listener del boton para mandar el correo
        btnSendEmail.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Context context = getApplicationContext();
                ConnectivityManager cm =
                        (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                // Verifica que se tenga conexion a internet para mandar el request
                boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
                email = emailET.getText().toString();

                // Si se tiene conexión manda el request a Parse para el cambio de contraeña
                if(isConnected) {
                    forgotPassword(email);
                } else { // En caso de no tener itnernet
                    Toast.makeText(ForgotPassword.this,
                            "Verifique conexion a internet", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    /**
     * forgotPassword
     *
     * Método que recibe el correo del usuario y hace el request a parse
     *
     * @param email
     */
    public void forgotPassword(String email) {
        ParseUser.requestPasswordResetInBackground(email, new UserForgotPasswordCallback());
    }

    /**
     * UserForgotPasswordCallback
     *
     * Método que recibe si la operación se hizo o fallo
     */
    private class UserForgotPasswordCallback implements RequestPasswordResetCallback {
        public UserForgotPasswordCallback(){
            super();
        }

        @Override
        public void done(ParseException e) {
            if (e == null) {
                Toast.makeText(getApplicationContext(), "Checa tu correo para cambiar la contraseña", Toast.LENGTH_LONG).show();
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "No se pudo mandar el correo intenta de nuevo", Toast.LENGTH_LONG).show();
            }
        }
    }

}
