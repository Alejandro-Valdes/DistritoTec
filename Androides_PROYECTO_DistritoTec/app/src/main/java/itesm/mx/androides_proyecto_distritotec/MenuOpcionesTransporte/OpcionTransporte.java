package itesm.mx.androides_proyecto_distritotec.MenuOpcionesTransporte;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
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

import itesm.mx.androides_proyecto_distritotec.R;
import itesm.mx.androides_proyecto_distritotec.SideBar.HomeFragment;
import itesm.mx.androides_proyecto_distritotec.SideBar.NavDrawerItem;
import itesm.mx.androides_proyecto_distritotec.SideBar.NavDrawerListAdapter;
import itesm.mx.androides_proyecto_distritotec.SideBar.NotificacionesFragment;

public class OpcionTransporte extends ActionBarActivity {

    // ####################### LOG_TAG ################################ //
    //Por si queremos debuggear
    private static final String LOG_TAG = "";

    // ####################### VARIABLES MENU PRINCIPAL ################################ //
    TextView tvUser;    //nombre usuario
    TextView tvWelcome;     //bienvenica
    int intHora = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);     //saber como bienvenir

    // ####################### VARIABLES LISTA EXPANDIBLE ################################ //
    HashMap<String, List<String>> hmOpcionesTransporte;     //hash map nombre, lista
    List<String> liOpciones;        //seran las opciones padre
    ExpandableListView expList;     //expandable list view
    OpcionesTransporteAdapter adapter;      //adaptador para items de expnadable view

    // ####################### VARIABLES RUTAS FAV ################################ //
    List<Route> liRoutes;   //lista de rutas como rutas
    List<String> liRoutesStr;   //lista de nombres de rutas
    RouteOperations dao = new RouteOperations(this);        //variable de operaciones de ruta
    String strRouteName;        //nombre de la ruta
    //RouteAdapter adaptadorRuta; se usaba con listView podemos borrar clase

    // ####################### VARIABLES SIDE BAR ################################ //
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    // nav drawer title
    private CharSequence mDrawerTitle;

    // used to store app title
    private CharSequence mTitle;

    // slide menu items
    private String[] navMenuTitles;

    /**
     * Metodo onCreate que entra cada que se crea la aplicacion
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.opciontransporte);

        //Busco las variables de bienvenida
        tvUser = (TextView) findViewById(R.id.tvUserName);
        tvWelcome = (TextView) findViewById(R.id.tvWelcome);

        //variables lista expandible
        expList = (ExpandableListView) findViewById(R.id.expList);

        //variables lista fav
        liRoutes = new ArrayList<>();


        //Para saber que horas son y bienvenir mejor
        String strGretting = "Good ";
        if (intHora <= 12) {
            strGretting += "morning";
        } else if (intHora < 20) {
            strGretting += "afternoon";
        } else {
            strGretting += "night";
        }
        //pongo mi bienvenida
        tvWelcome.setText(strGretting);

        // ####################### CLICS LISTA EXPANDIBLE ################################ //

        //Click listener normal esto nos va a llevar a otras actividades con mapas
        expList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                return true;
            }

        });

        //long clic en los hijos de la lista expandible
        expList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                int itemType = ExpandableListView.getPackedPositionType(id);

                //si el papa id es igual a 2 es favoritos y quiero borrar
                if(ExpandableListView.getPackedPositionGroup(id) == 2){
                    //saco la posicion de padre e hijo
                    int iGroupPosition = ExpandableListView.getPackedPositionGroup(id);
                    int iChildPosition = ExpandableListView.getPackedPositionChild(id);

                    //saco el nombre de la ruta
                    strRouteName = adapter.getChild(iGroupPosition, iChildPosition).toString();
                    //lo quito de la lista de favoritos
                    removeFavRoute(strRouteName);
                    return true;
                }

                //En este caso quiero agregar a favs
                else if (itemType == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
                    //saco la posicion de padre e hijo
                    int iGroupPosition = ExpandableListView.getPackedPositionGroup(id);
                    int iChildPosition = ExpandableListView.getPackedPositionChild(id);

                    //saco el nombre de la ruta
                    strRouteName = adapter.getChild(iGroupPosition, iChildPosition).toString();
                    //lo agrego a la lista de favoritos
                    addFavRoute(strRouteName);
                    return true;
                }
                return false;
            }
        });


        //obten datos de usuario
        ParseUser currentUser = ParseUser.getCurrentUser();
        tvUser.setText(currentUser.getUsername().toString());


        // ####################### SIDE BAR MENU ################################ //

        // Titulo del Item
        mTitle = mDrawerTitle = getTitle();

        // Items del Side Bar
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

        // Iconos de los items del Side Bar
        TypedArray navMenuIcons = getResources()
                .obtainTypedArray(R.array.nav_drawer_icons);

        // Views
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

        ArrayList<NavDrawerItem> navDrawerItems = new ArrayList<NavDrawerItem>();

        // Aqui se agregan los items del Side Bar
        // Notificaciones
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1), true, "2"));
        // Informacion
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
        // Configuracion
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));


        // Recicla el arreglo
        navMenuIcons.recycle();

        // Se le pone el adaptador al NavDrawer
        NavDrawerListAdapter adapterSideBar = new NavDrawerListAdapter(getApplicationContext(),
                navDrawerItems);
        mDrawerList.setAdapter(adapterSideBar);

        // Habilita los iconos y les da la propiedad de Boton
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        // Checa como funciona este toolbar!!
        Toolbar toolbar = new Toolbar(this);
        toolbar.setLogo(R.drawable.ic_action_expand);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                toolbar, //nav menu toggle icon
                R.string.app_name, // nav drawer open - description for accessibility
                R.string.app_name // nav drawer close - description for accessibility
        ){
            // Cuando se cierra el Side Bar
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(mTitle);
                invalidateOptionsMenu();
            }

            // Cuando se abre el Side Bar
            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        // Click listener de la lista de Items
        mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

    }

    // ####################### Menu tradicional ################################ //

    /**
     * Metodo que crea menu
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            case R.id.Logout:
                ParseUser.logOut();
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /***
     * Called when invalidateOptionsMenu() is triggered
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // if nav drawer is opened, hide the action items
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
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
     * Diplaying fragment view for selected nav drawer list item
     * */
    private void displayView(int position) {
        // update the main content by replacing fragments
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new NotificacionesFragment();
                getSupportActionBar().setTitle("Notificaciones");
                break;
            case 1:
                fragment = new HomeFragment();
                getSupportActionBar().setTitle("Configuracion");
                break;
            case 2:
                fragment = new HomeFragment();
                getSupportActionBar().setTitle("Informacion");
                break;
            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_container, fragment).commit();

            // update selected item and title, then close the drawer
            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            setTitle(navMenuTitles[position]);
            mDrawerLayout.closeDrawer(mDrawerList);
        } else {
            // error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
        }
    }

    /**
     * Metodo removeFavRoute para quitar una ruta de la lista de favoritas
     * @param strRouteName
     */
    public void removeFavRoute (String strRouteName){
        //usa Route operations para borrar
        boolean result = dao.deleteRoute(strRouteName);

        //mensaje de que fue exitoso
        if(result){
            Toast.makeText(OpcionTransporte.this, strRouteName +
                    " a sido eliminado de favoritos", Toast.LENGTH_SHORT).show();
            updateDB();
        }
    }

    /**
     * Metodo addFavRoute
     * Agrega una ruta a la lista de favs
     * @param strRouteName
     */
    public void addFavRoute (String strRouteName){

        //llamo a todas las rutas
        liRoutesStr = dao.getAllRoutesStr();

        //si no esta vacio
        if(!strRouteName.equals("")){
            //creo una nueva ruta
            Route route = new Route(strRouteName);
            //verifico que no exista ya


            if(!liRoutesStr.contains(route.getName())) {
                dao.addRoute(route);
                Toast.makeText(OpcionTransporte.this, strRouteName +
                        " a sido agregado a favoritos", Toast.LENGTH_SHORT).show();
                updateDB();
            }
            else {
                Toast.makeText(OpcionTransporte.this, strRouteName +
                        " es parte de favoritos", Toast.LENGTH_SHORT).show();
            }


        }
    }

    /**
     * updateDB sirve para updatera la database con cada cambio
     */
    public void updateDB() {
        //obtengo todas las rutas
        liRoutes = dao.getAllRoutes();

        //obtengo una lista de todas las rutas en forma de string
        liRoutesStr = dao.getAllRoutesStr();
        //obtengo la info que ira en la lista expandible
        hmOpcionesTransporte = OpcionesTransporteProvider.getInfo(liRoutesStr);
        //creo lista de los padres de la lista expnadible
        liOpciones = new ArrayList<String>(hmOpcionesTransporte.keySet());
        //adaptador que crea lista expandible
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
