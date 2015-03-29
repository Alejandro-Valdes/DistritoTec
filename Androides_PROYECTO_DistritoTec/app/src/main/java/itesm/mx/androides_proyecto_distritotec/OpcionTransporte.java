package itesm.mx.androides_proyecto_distritotec;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.parse.ParseUser;

/**
 * Created by Alejandro Valdes on 28-Mar-15.
 */
public class OpcionTransporte extends ActionBarActivity {

    //Declaracion variables
    TextView tvUser;
    String strUserName;

    //
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.opciontransporte);
        tvUser = (TextView) findViewById(R.id.tvUserName);

        //obten datos de usuario
        ParseUser currentUser = ParseUser.getCurrentUser();
        strUserName = currentUser.getUsername().toString();

        tvUser.setText(strUserName);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.Logout) {
            ParseUser.logOut();
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
