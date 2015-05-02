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
 * Created by jose on 30/04/15.
 */
public class ForgotPassword extends ActionBarActivity {

    EditText emailET;
    Button btnSendEmail;
    String email;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);
        getSupportActionBar().hide();
        emailET = (EditText) findViewById(R.id.emailET);
        btnSendEmail = (Button) findViewById(R.id.btnSendEmail);

        btnSendEmail.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Context context = getApplicationContext();
                ConnectivityManager cm =
                        (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
                email = emailET.getText().toString();

                if(isConnected) {
                    forgotPassword(email);
                } else {
                    Toast.makeText(ForgotPassword.this,
                            "Verifique conexion a internet", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    public void forgotPassword(String email) {
        //postEvent(new UserForgotPasswordStartEvent());
        ParseUser.requestPasswordResetInBackground(email, new UserForgotPasswordCallback());
    }

    private class UserForgotPasswordCallback implements RequestPasswordResetCallback {
        public UserForgotPasswordCallback(){
            super();
        }

        @Override
        public void done(ParseException e) {
            if (e == null) {
                Toast.makeText(getApplicationContext(), "Successfully sent link to your email for reset Password", Toast.LENGTH_LONG).show();
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "Failed to sent link to your email for reset Password", Toast.LENGTH_LONG).show();

            }
        }
    }

}
