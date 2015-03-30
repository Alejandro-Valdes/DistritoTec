package itesm.mx.androides_proyecto_distritotec.MenuOpcionesTransporte;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseUser;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import itesm.mx.androides_proyecto_distritotec.R;

/**
 * Created by Alejandro Valdes on 28-Mar-15.
 */
public class OpcionTransporte extends ActionBarActivity{

    //Declaracion variables
    TextView tvUser;
    TextView tvWelcome;
    String strUserName;
    int intHora = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);

    //Variables para lista extendible
    HashMap<String, List<String>> hmOpcionesTransporte;
    List<String> liOpciones;
    ExpandableListView expList;
    OpcionesTransporteAdapter adapter;

    //variables rutas favoritas
    ListView lvFavs;
    List<Route> liRoutes;
    RouteOperations dao = new RouteOperations(this);
    String strRouteName;
    RouteAdapter adaptadorRuta;

    //
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.opciontransporte);

        tvUser = (TextView) findViewById(R.id.tvUserName);
        tvWelcome = (TextView) findViewById(R.id.tvWelcome);

        String strGretting = "Good ";

        if(intHora <= 12){
            strGretting += "morning";
        }

        else if(intHora <= 20){
            strGretting += "afternoon";
        }

        else{
            strGretting += "night";
        }

        tvWelcome.setText(strGretting);

        //variables lista expandible
        expList = (ExpandableListView)findViewById(R.id.expList);
        hmOpcionesTransporte = OpcionesTransporteProvider.getInfo();
        liOpciones = new ArrayList<String>(hmOpcionesTransporte.keySet());
        adapter = new OpcionesTransporteAdapter(this, hmOpcionesTransporte, liOpciones);
        expList.setAdapter(adapter);

        //variables lista fav
        lvFavs = (ListView) findViewById(R.id.lvFavs);
        liRoutes = new ArrayList<Route>();


        expList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                return true;
            }

        });

        //long clic para agregar a favoritos
        expList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if(ExpandableListView.getPackedPositionType(id) == ExpandableListView.PACKED_POSITION_TYPE_CHILD){
                    int iGroupPosition = ExpandableListView.getPackedPositionGroup(id);
                    int iChildPosition = ExpandableListView.getPackedPositionChild(id);

                    strRouteName = adapter.getChild(iGroupPosition, iChildPosition).toString();
                    addFavRoute(view, strRouteName);
                    return true;
                }
                return false;
            }
        });

        //onLongClic para quitar lista favoritos
        lvFavs.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String text = ((TextView)view).getText().toString();
                removeFavRoute(view, text);
                updateDB();
                return false;
            }
        });

        //obten datos de usuario
        ParseUser currentUser = ParseUser.getCurrentUser();
        strUserName = currentUser.getUsername().toString();

        tvUser.setText(strUserName);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.expreso_circuito, menu);
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

    public void removeFavRoute (View view, String strRouteName){
        boolean result = dao.deleteRoute(strRouteName);

        if(result){
            Toast.makeText(OpcionTransporte.this, strRouteName + " a sido eliminado de favoritos", Toast.LENGTH_SHORT).show();
            updateDB();
        }
    }

    public void addFavRoute (View view, String strRouteName){

        if(!strRouteName.equals("")){
            Route route = new Route(strRouteName);
            dao.addRoute(route);

            Toast.makeText(OpcionTransporte.this, strRouteName + " a sido agregado a favoritos", Toast.LENGTH_SHORT).show();
            updateDB();
        }
    }

    public void updateDB() {
        liRoutes = dao.getAllRoutes();
        adaptadorRuta = new RouteAdapter(OpcionTransporte.this,
                R.layout.activity_row, liRoutes);

        lvFavs.setAdapter(adaptadorRuta);
    }


    @Override
    protected void onResume() {
        try {
            dao.open();
            updateDB();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        dao.close();
        super.onPause();
    }

}
