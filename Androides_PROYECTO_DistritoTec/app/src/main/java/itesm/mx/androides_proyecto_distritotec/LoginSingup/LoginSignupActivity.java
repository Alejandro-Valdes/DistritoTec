package itesm.mx.androides_proyecto_distritotec.LoginSingup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import itesm.mx.androides_proyecto_distritotec.MenuOpcionesTransporte.OpcionTransporte;
import itesm.mx.androides_proyecto_distritotec.R;

/**
 * Created by Alejandro Valdes on 28-Mar-15.
 */
public class LoginSignupActivity extends ActionBarActivity {
    //Deblaracion de variables
    Button btnLogin;
    Button btnSignup;
    EditText etPassword;
    EditText etUsername;

    String strUsername;
    String strPassword;

    //al abrir la activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginsignup);

        //inicializacion variables
        btnLogin = (Button)findViewById(R.id.btnLogin);
        btnSignup = (Button)findViewById(R.id.btnSignup);
        etUsername = (EditText)findViewById(R.id.etUserName);
        etPassword = (EditText)findViewById(R.id.etPassword);

        getSupportActionBar().hide();

        //onClickListeners para los botones
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                strPassword = etPassword.getText().toString();
                strUsername = etUsername.getText().toString();

                //verificar datos con parse
                ParseUser.logInInBackground(strUsername, strPassword,
                        new LogInCallback() {
                            @Override
                            public void done(ParseUser parseUser, ParseException e) {
                                if(parseUser != null){
                                    Intent intent = new Intent(LoginSignupActivity.this,
                                            OpcionTransporte.class);
                                    startActivity(intent);

                                    Toast.makeText(getApplicationContext(),"Logged in",
                                            Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(getApplicationContext(),"Check your credentials,"
                                                    +" or sign up", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strPassword = etPassword.getText().toString();
                strUsername = etUsername.getText().toString();

                //deteccion de error, tiene que llenar
                if(etUsername.equals("") || etPassword.equals("")){
                    Toast.makeText(getApplicationContext(),
                            "Please fill in all the fields",Toast.LENGTH_LONG).show();
                } else {
                    //saves the data into parse
                    ParseUser user = new ParseUser();
                    user.setUsername(strUsername);
                    user.setPassword(strPassword);

                    //signup al usuario nuevo
                    user.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if(e == null){
                                Toast.makeText(getApplicationContext(),"You can now login",
                                        Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getApplicationContext(),"There was an error, " +
                                        "please try again", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });

    }
}
