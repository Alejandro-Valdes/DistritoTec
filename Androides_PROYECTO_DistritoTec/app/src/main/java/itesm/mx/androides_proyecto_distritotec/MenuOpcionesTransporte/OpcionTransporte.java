package itesm.mx.androides_proyecto_distritotec.MenuOpcionesTransporte;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.parse.ParseUser;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;


import itesm.mx.androides_proyecto_distritotec.GoogleMaps.MapsActivityRoute;

import itesm.mx.androides_proyecto_distritotec.LoginSingup.LoginSignupActivity;

import itesm.mx.androides_proyecto_distritotec.R;
import itesm.mx.androides_proyecto_distritotec.SideBar.Configuracion;
import itesm.mx.androides_proyecto_distritotec.SideBar.Informacion;
import itesm.mx.androides_proyecto_distritotec.SideBar.NavDrawerItem;
import itesm.mx.androides_proyecto_distritotec.SideBar.NavDrawerListAdapter;
import itesm.mx.androides_proyecto_distritotec.SideBar.Notificaciones;

/**
 * Route
 *
 * Clase que provee la informacion de las rutas para la base de datos
 *
 * @author Jose Eduardo Elizondo Lozano A01089591
 * @author Oliver Alejandro Martínez Quiroz A01280416
 * @author Jesús Alejandro Valdés Valdés A0099044
 *
 * Version 1.0
 *
 */
public class OpcionTransporte extends ActionBarActivity {

    /**
     * Layouts
     */
    TextView tvUser; // Nombre del usuario
    TextView tvWelcome; // Bienvenida

    /**
     * Lista Expandible
     */
    HashMap<String, List<String>> hmOpcionesTransporte;     // HashMap <nombre, List>
    List<String> liOpciones; // Opciones Padre: Expreso, Circuito
    ExpandableListView expList; // Lista expandible
    OpcionesTransporteAdapter adapter; // Adaptador para los items de la lista expandible

    /**
     * Rutas favoritas
     */
    List<Route> liRoutes; // Lista de rutas
    List<String> liRoutesStr; // Lista de nombres de cada ruta
    RouteOperations dao = new RouteOperations(this); // Route operation
    String strRouteName; // Nombre de la ruta

    /**
     * Side Bar
     */
    private DrawerLayout mDrawerLayout; // Layout
    private ListView mDrawerList; // Lista
    private ActionBarDrawerToggle mDrawerToggle; // Suport Action Bar
    private CharSequence mDrawerTitle; // Titulo del nav bar
    private CharSequence mTitle; // Titulo de la app

    int intHora = Calendar.getInstance().get(Calendar.HOUR_OF_DAY); // Hora del dia


    /**
     * Metodo onCreate que entra cada que se crea la aplicacion
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.opciontransporte);

        tvUser = (TextView) findViewById(R.id.tvUserName); // TextView Nombre Usuario
        tvWelcome = (TextView) findViewById(R.id.tvWelcome); // TextView Welcome
        expList = (ExpandableListView) findViewById(R.id.expList); // ExpandableListView List view
        liRoutes = new ArrayList<>(); // Arreglo de rutas favoritas

        // Bienvenida
        String strGretting = "Good ";
        if (intHora <= 12) {
            strGretting += "morning";
        } else if (intHora < 20) {
            strGretting += "afternoon";
        } else {
            strGretting += "night";
        }
        tvWelcome.setText(strGretting); // Se asigna la bienvenida al TextView

        // Obtenemos los datos del usuario
        ParseUser currentUser = ParseUser.getCurrentUser();

        // Asignamos el nombre del usuario al TextView
        tvUser.setText(currentUser.getUsername());

        /**
         * Child Click Listener
         */

        // Click Listener en los items hijos de la lista expandible
        expList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Intent intentMap = new Intent(OpcionTransporte.this, MapsActivityRoute.class);

                Context context = getApplicationContext();
                ConnectivityManager cm =
                        (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

                intentMap.putExtra("routeName",
                        parent.getExpandableListAdapter()
                                .getChild(groupPosition, childPosition).toString());

                if(isConnected) {
                    startActivity(intentMap);
                } else {
                    Toast.makeText(OpcionTransporte.this,
                            "Verifique conexion a internet", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });

        /**
         * Child Long Click Listener
         */

        // Long click en los items hijos de la lista expandible
        expList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                int itemType = ExpandableListView.getPackedPositionType(id);

                // Si el id es igual a 2 es una ruta favorita y se quiere borrar
                if(ExpandableListView.getPackedPositionGroup(id) == 2) {
                    // Se saca la posicion del hijo
                    int iGroupPosition = ExpandableListView.getPackedPositionGroup(id);
                    int iChildPosition = ExpandableListView.getPackedPositionChild(id);

                    // Se obtiene el nombre de la ruta
                    strRouteName = adapter.getChild(iGroupPosition, iChildPosition).toString();

                    // Lo quitamos de la Lista de favoritos
                    removeFavRoute(strRouteName);
                    expList.expandGroup(ExpandableListView.getPackedPositionGroup(id));

                    return true;

                    // Agregamos ruta a lista de favoritos
                } else if (itemType == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
                    // Se saca la posicion del hijo
                    int iGroupPosition = ExpandableListView.getPackedPositionGroup(id);
                    int iChildPosition = ExpandableListView.getPackedPositionChild(id);

                    // Se obtiene el nombre de la ruta
                    strRouteName = adapter.getChild(iGroupPosition, iChildPosition).toString();

                    // Lo agregomos a la lista de favoritos
                    addFavRoute(strRouteName);
                    expList.expandGroup(2);
                    expList.expandGroup(ExpandableListView.getPackedPositionGroup(id));

                    return true;
                }
                return true;
            }
        });

        /**
         * Side Bar Menu
         */

        // Titulo del Action Bar
        mTitle = mDrawerTitle = getTitle();

        // Items del Side Bar
        String[] navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

        // Iconos de los items del Side Bar
        TypedArray navMenuIcons = getResources()
                .obtainTypedArray(R.array.nav_drawer_icons);

        // Views
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

        // Arreglo de items del Menu
        ArrayList<NavDrawerItem> navDrawerItems = new ArrayList<>();

        // Aqui se agregan los items del Side Bar
        // Notificaciones
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1), true, "2"));
        // Informacion
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
        // Configuracion
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));
        // Logout
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1)));

        // Recicla el arreglo
        navMenuIcons.recycle();

        // Se le pone el adaptador al NavDrawer
        NavDrawerListAdapter adapterSideBar = new NavDrawerListAdapter(getApplicationContext(),
                navDrawerItems);
        mDrawerList.setAdapter(adapterSideBar);

        // Habilita los iconos y les da la propiedad de Boton
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_drawer);
        getSupportActionBar().setHomeButtonEnabled(true);


        // Drawer toolBar
        Toolbar toolbar = new Toolbar(this);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                toolbar, // Icono Nav Menu
                R.string.app_name, // Nav Abierto
                R.string.app_name // Nav Cerrado
        ){
            // Cuando se cierra el Side Bar
            public void onDrawerClosed(View view) {
                invalidateOptionsMenu();
            }

            // Cuando se abre el Side Bar
            public void onDrawerOpened(View drawerView) {
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        // Click listener de la lista de Items
        mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

    }

    /**
     * Menu Inferior
     */

    /**
     * Metodo que crea menu
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Menu inflater
        return true;
    }

    /**
     * Metodo para saber que se esta seleccionando
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action bar actions click si doy clic en logout me salgo
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /***
     * onPrepareOptionsMenu
     *
     * Usar este metodo en caso de tener Sidebar Menu y un menu inferior
     *
     * @param menu
     *
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // Si el nav esta abierto, esconde los items del menu inferior
        //boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        //menu.findItem(R.id.Logout).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    /**
     * setTitle
     *
     * Este metodo asigna el titulo al Action bar
     *
     * @param title
     */
    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    /**
     * onPostCreate
     *
     * @param savedInstanceState
     */
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Syncroniza el estado Toggle despues de que ocurrio onRestoreInstanceState
        mDrawerToggle.syncState();
    }

    /**
     * onConfigurationChanged
     *
     * @param newConfig
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // En caso de que cambie la configuracion se pasa el toggle
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    /**
     * Slide menu item click listener
     * */
    private class SlideMenuClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // Despliega la actividad conforme a la posicion
            displayView(position);
        }
    }

    /**
     * displayView
     *
     * Despliega el fragmento selecionado del menu
     *
     * @param position
     */
    private void displayView(int position) {
        // Llamadas a las actividades correspondientes
        switch (position) {
            case 0:
                Intent intentNotificaciones = new Intent(OpcionTransporte.this, Notificaciones.class);
                startActivity(intentNotificaciones);
                break;
            case 1:
                Intent intentConfiguracion = new Intent(OpcionTransporte.this, Configuracion.class);
                startActivity(intentConfiguracion);
                break;
            case 2:
                Intent intentInformacion = new Intent(OpcionTransporte.this, Informacion.class);
                startActivity(intentInformacion);
                break;
            case 3:
                ParseUser.logOut();
                Intent intentRegresar = new Intent (OpcionTransporte.this, LoginSignupActivity.class);
                startActivity(intentRegresar);
                finish();
            default:
                break;
        }
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    /**
     * Metodo removeFavRoute para quitar una ruta de la lista de favoritas
     * @param strRouteName
     */
    public void removeFavRoute (String strRouteName){
        // Route operations para borrar
        boolean result = dao.deleteRoute(strRouteName);

        // En caso de ser borrada muestra mensaje de exito
        if(result){
            Toast.makeText(OpcionTransporte.this, strRouteName +
                    " a sido eliminado de favoritos", Toast.LENGTH_SHORT).show();
            updateDB();
        } else {
            Toast.makeText(OpcionTransporte.this,"Algo salio mal", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Metodo addFavRoute
     * Agrega una ruta a la lista de favs
     * @param strRouteName
     */
    public void addFavRoute (String strRouteName){

        // LLamo a todas las rutas
        liRoutesStr = dao.getAllRoutesStr();

        // Si no esta vacio
        if(!strRouteName.equals("")){
            // Creo una nueva ruta
            Route route = new Route(strRouteName);
            // Verifico que no exista ya

            if(!liRoutesStr.contains(route.getName())) {
                dao.addRoute(route);
                Toast.makeText(OpcionTransporte.this, strRouteName +
                        " a sido agregado a favoritos", Toast.LENGTH_SHORT).show();
                updateDB();
                /*https://github.com/commonsguy/cw-android/blob/master/Database/Constants/src/com/commonsware/android/constants/ConstantsBrowser.java*/
            }
            else {
                Toast.makeText(OpcionTransporte.this, strRouteName +
                        " es parte de favoritos", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * updateDB actualiza la base de datos
     */
    public void updateDB() {
        // Obtengo todas las rutas
        //liRoutes = dao.getAllRoutes();

        // Obtengo una lista de todas las rutas en forma de string
        liRoutesStr = dao.getAllRoutesStr();

        // Obtengo la info que ira en la lista expandible
        hmOpcionesTransporte = OpcionesTransporteProvider.getInfo(liRoutesStr);

        // Creo lista de los padres de la lista expnadible
        liOpciones = new ArrayList<>(hmOpcionesTransporte.keySet());

        // Adaptador que crea lista expandible
        adapter = new OpcionesTransporteAdapter(this, hmOpcionesTransporte, liOpciones);

        expList.setAdapter(adapter);
    }


    /**
     * onResume
     * Mantiene integridad de db
     */
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

    /**
     * onPause
     * Manitene integridad db
     */
    @Override
    protected void onPause() {
        dao.close();
        super.onPause();
    }

}
