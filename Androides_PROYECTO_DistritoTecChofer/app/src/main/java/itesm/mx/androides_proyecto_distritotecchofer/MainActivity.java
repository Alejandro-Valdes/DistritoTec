package itesm.mx.androides_proyecto_distritotecchofer;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;



public class MainActivity extends ActionBarActivity {

    ListView lvRutas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onResume();

        setContentView(R.layout.activity_main);

        lvRutas = (ListView) findViewById(R.id.lvRutas);


        String[] arrRoutes = new String[] {
                "Valle",
                "Galerias",
                "San Nicolas",
                "Guadalupe",
                "Villas TEC",
                "Colonia Roma",
                "Altavista"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                R.layout.activity_row,
                R.id.tvRow,
                arrRoutes
        );

        lvRutas.setAdapter(adapter);

        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String strRoute = ((TextView) view).getText().toString();
                Intent intent = new Intent(MainActivity.this, Location.class);
                intent.putExtra("strRoute", strRoute);
                startActivity(intent);
            }
        };

        lvRutas.setOnItemClickListener(itemClickListener);
    }

}
