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

    private static final String LOG_TAG = "";
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.opciontransporte);

        tvUser = (TextView) findViewById(R.id.tvUserName);
        tvWelcome = (TextView) findViewById(R.id.tvWelcome);

        //variables lista expandible
        expList = (ExpandableListView) findViewById(R.id.expList);
        hmOpcionesTransporte = OpcionesTransporteProvider.getInfo();
        liOpciones = new ArrayList<String>(hmOpcionesTransporte.keySet());
        adapter = new OpcionesTransporteAdapter(this, hmOpcionesTransporte, liOpciones);
        expList.setAdapter(adapter);
        registerForContextMenu(expList);

        //variables lista fav
        lvFavs = (ListView) findViewById(R.id.lvFavs);
        liRoutes = new ArrayList<Route>();
        registerForContextMenu(lvFavs);


        String strGretting = "Good ";
        if (intHora <= 12) {
            strGretting += "morning";
        } else if (intHora < 20) {
            strGretting += "afternoon";
        } else {
            strGretting += "night";
        }
        tvWelcome.setText(strGretting);


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
                if (ExpandableListView.getPackedPositionType(id) == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
                    int iGroupPosition = ExpandableListView.getPackedPositionGroup(id);
                    int iChildPosition = ExpandableListView.getPackedPositionChild(id);

                    strRouteName = adapter.getChild(iGroupPosition, iChildPosition).toString();
                    addFavRoute(view, strRouteName);
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

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){

        // Checa si este menu inflater jala como deberia
        //getMenuInflater().inflate(R.menu.menu_main, menu);

        if(v.getId() == R.id.lvFavs){
            Log.i(LOG_TAG,"Entered the favRutas");
            getMenuInflater().inflate(R.menu.menu_context_rutasfav, menu);
        }

        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        AdapterView.AdapterContextMenuInfo info =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        int id = item.getItemId();

        if(id == R.id.addFav) {
            return true;
        }

        else if(id == R.id.removeFav) {
            Route text = liRoutes.get(info.position);
            Log.i(LOG_TAG,""+text);
            removeFavRoute(info.targetView, text.getName());
            updateDB();
            return true;
        }

        return super.onContextItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action bar actions click                           SI NO JALAN LOS BOTONES CHECA ESTO
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

        /*
        int id = item.getItemId();
        if (id == R.id.Logout) {
            ParseUser.logOut();
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);*/
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
