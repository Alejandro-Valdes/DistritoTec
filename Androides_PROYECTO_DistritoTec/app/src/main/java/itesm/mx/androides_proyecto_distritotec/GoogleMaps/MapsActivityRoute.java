package itesm.mx.androides_proyecto_distritotec.GoogleMaps;

import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import itesm.mx.androides_proyecto_distritotec.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.LogRecord;

import org.json.JSONObject;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.parse.GetCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class MapsActivityRoute extends FragmentActivity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {


    GoogleApiClient mGoogleApiClient;
    ParseObject ExpresoLocation = new ParseObject("Routes");
    ParseQuery<ParseObject> query = ParseQuery.getQuery("Routes");

    GoogleMap map;
    ArrayList<LatLng> markerPoints;
    MarkerOptions options = new MarkerOptions();
    LatLng ITESM = new LatLng(25.649713, -100.290032);
    LatLng initialPos = new LatLng(25.656848, -100.2826138);

    LatLng waypoint1;
    LatLng waypoint2;
    LatLng waypoint3;
    LatLng waypoint4;
    LatLng waypoint5;
    LatLng waypoint6;
    LatLng waypoint7;
    LatLng waypoint8;

    //timer

    int iTime = 3000; //3 second
    Handler hHandler;
    Runnable rRunnable;

    Marker mCamion;
    LatLng latLngCamion;
    double dLat = 0;
    double dLong = 0;
    boolean bGo = true;

    String strRouteName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_activity_route);

        Bundle bundle = getIntent().getExtras();

        strRouteName = bundle.getString("routeName").trim();

        Toast.makeText(getApplicationContext(),
                "Ruta " + strRouteName,Toast.LENGTH_SHORT).show();

        onConnected(savedInstanceState);
        buildGoogleApiClient();


        // Getting reference to SupportMapFragment of the activity_main
        SupportMapFragment fm = (SupportMapFragment)getSupportFragmentManager().
                findFragmentById(R.id.mapRoute);

        // Getting Map for the SupportMapFragment
        map = fm.getMap();
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(ITESM, 14));

        if(map!=null){
            // Enable MyLocation Button in the Map
            map.setMyLocationEnabled(true);
            setMap(strRouteName);
        }


    //QUE DEPENDA DEL CAMION

        ExpresoLocation.setObjectId("VJhJgBjYF5");

        hHandler = new Handler();

        rRunnable = new Runnable(){
            @Override
            public void run() {
                if(bGo)
                    updateStatus();
                hHandler.postDelayed(rRunnable, iTime);
            }
        };

        rRunnable.run();

    }

    private void updateStatus() {
        if(mCamion != null){
            bGo = false;
            mCamion.remove();
        }

        query.getInBackground(ExpresoLocation.getObjectId(),new GetCallback<ParseObject>() {

            @Override
            public void done(ParseObject parseObject, com.parse.ParseException e) {
                if (e == null) {

                    String strLat = parseObject.getString("latitud");
                    String strLong = parseObject.getString("longitud");
                    dLat = Double.parseDouble(strLat);
                    dLong = Double.parseDouble(strLong);


                } else {
                    e.printStackTrace();
                }

                if(dLat!=0 & dLong!=0) {

                    latLngCamion = new LatLng(dLat, dLong);


                    mCamion = map.addMarker(options.position(latLngCamion).icon(BitmapDescriptorFactory
                            .fromResource(R.drawable.transport)));

                    bGo = true;
                }
            }
        });
    }

    public void setMap (String strName){

        // Initializing
        markerPoints = new ArrayList<LatLng>();

        switch (strName) {
            case "Valle":
                initialPos = new LatLng(25.655767,-100.385106);
                waypoint1 = new LatLng(25.655767,-100.385106);
                waypoint2 = new LatLng(25.667414,-100.379827);
                waypoint3 = new LatLng(25.664958,-100.374935);
                waypoint4 = new LatLng(25.663952,-100.358112);
                waypoint5 = new LatLng(25.652501,-100.358198);
                waypoint6 = new LatLng(25.659968,-100.349379);
                waypoint7 = new LatLng(25.644222,-100.325861);
                waypoint8 = new LatLng(25.614661,-100.271144);
                break;

            case "Galerias":
                initialPos = new LatLng(25.695228,-100.372505);
                waypoint1 = new LatLng(25.689833,-100.372478);
                waypoint2 = new LatLng(25.686846,-100.369956);
                waypoint3 = new LatLng(25.681605,-100.370375);
                waypoint4 = new LatLng(25.681763,-100.365491);
                waypoint5 = new LatLng(25.683368,-100.364496);
                waypoint6 = new LatLng(25.685611,-100.365217);
                waypoint7 = new LatLng(25.682551,-100.355365);
                waypoint8 = new LatLng(25.665682,-100.297413);
                break;

            case "San Nicolas":
                initialPos = new LatLng(25.768563,-100.272694);
                waypoint1 = new LatLng(25.768563,-100.272694);
                waypoint2 = new LatLng(25.754601,-100.277458);
                waypoint3 = new LatLng(25.746059,-100.269282);
                waypoint4 = new LatLng(25.742145,-100.295413);
                waypoint5 = new LatLng(25.742720,-100.306501);
                waypoint6 = new LatLng(25.742014,-100.312659);
                waypoint7 = new LatLng(25.736883,-100.310379);
                waypoint8 = new LatLng(25.665132, -100.320840);
                break;

            case "Guadalupe":
                initialPos = new LatLng(25.722479,-100.215279);
                waypoint1 = new LatLng(25.722479,-100.215279);
                waypoint2 = new LatLng(25.706268,-100.224388);
                waypoint3 = new LatLng(25.702894,-100.231791);
                waypoint4 = new LatLng(25.702537,-100.254589);
                waypoint5 = new LatLng(25.692627,-100.254664);
                waypoint6 = new LatLng(25.686517,-100.261939);
                waypoint7 = new LatLng(25.672564,-100.286722);
                waypoint8 = new LatLng(25.657256, -100.280564);
                break;

            //la del valle
            default:
                initialPos = new LatLng(25.655767,-100.385106);
                waypoint1 = new LatLng(25.655767,-100.385106);
                waypoint2 = new LatLng(25.667414,-100.379827);
                waypoint3 = new LatLng(25.664958,-100.374935);
                waypoint4 = new LatLng(25.663952,-100.358112);
                waypoint5 = new LatLng(25.652501,-100.358198);
                waypoint6 = new LatLng(25.659968,-100.349379);
                waypoint7 = new LatLng(25.644222,-100.325861);
                waypoint8 = new LatLng(25.614661,-100.271144);
                break;



        }

        markerPoints.add(initialPos);
        map.addMarker(options.position(initialPos).icon(BitmapDescriptorFactory.
                defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

        markerPoints.add(ITESM);
        map.addMarker(options.position(ITESM).icon(BitmapDescriptorFactory.
                defaultMarker(BitmapDescriptorFactory.HUE_RED)));

        markerPoints.add(waypoint1);
        markerPoints.add(waypoint2);
        markerPoints.add(waypoint3);
        markerPoints.add(waypoint4);
        markerPoints.add(waypoint5);
        markerPoints.add(waypoint6);
        markerPoints.add(waypoint7);
        markerPoints.add(waypoint8);


        String url = getDirectionsUrl(markerPoints.get(0), markerPoints.get(1));

        DownloadTask downloadTask = new DownloadTask();

        downloadTask.execute(url);

    }

    private void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }


    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    private String getDirectionsUrl(LatLng origin,LatLng dest){

        // Origin of route
        String str_origin = "origin="+origin.latitude+","+origin.longitude;

        // Destination of route
        String str_dest = "destination="+dest.latitude+","+dest.longitude;

        // Sensor enabled
        String sensor = "sensor=false";

        // Building the parameters to the web service
        String waypoints = "waypoints="+
                waypoint1.latitude+","+waypoint1.longitude+"|"+
                waypoint2.latitude+","+waypoint2.longitude+"|"+
                waypoint3.latitude+","+waypoint3.longitude+"|"+
                waypoint4.latitude+","+waypoint4.longitude+"|"+
                waypoint5.latitude+","+waypoint5.longitude+"|"+
                waypoint6.latitude+","+waypoint6.longitude+"|"+
                waypoint7.latitude+","+waypoint7.longitude+"|"+
                waypoint8.latitude+","+waypoint8.longitude;

                String parameters = str_origin +"&"+str_dest+"&"+waypoints+"&"+sensor;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/"+output+"?"+parameters;

        System.out.println(url);
        return url;
    }
    /** A method to download json data from url */
    private String downloadUrl(String strUrl) throws IOException{
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while( ( line = br.readLine()) != null){
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        }catch(Exception e){
            Log.d("Exception while downloading url", e.toString());
        }finally{
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }


    // Fetches data from url passed
    private class DownloadTask extends AsyncTask<String, Void, String>{

        // Downloading data in non-ui thread
        @Override
        protected String doInBackground(String... url) {

            // For storing data from web service
            String data = "";

            try{
                // Fetching the data from web service
                data = downloadUrl(url[0]);
            }catch(Exception e){
                Log.d("Background Task",e.toString());
            }
            return data;
        }

        // Executes in UI thread, after the execution of
        // doInBackground()
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();

            // Invokes the thread for parsing the JSON data
            parserTask.execute(result);
        }
    }

    /** A class to parse the Google Places in JSON format */
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String,String>>> >{

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try{
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();

                // Starts parsing data
                routes = parser.parse(jObject);
            }catch(Exception e){
                e.printStackTrace();
            }
            return routes;
        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points = null;
            PolylineOptions lineOptions = null;
            MarkerOptions markerOptions = new MarkerOptions();

            // Traversing through all the routes
            for(int i=0;i<result.size();i++){
                points = new ArrayList<LatLng>();
                lineOptions = new PolylineOptions();

                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);

                // Fetching all the points in i-th route
                for(int j=0;j<path.size();j++){
                    HashMap<String,String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                lineOptions.width(6);
                lineOptions.color(Color.RED);
            }

            // Drawing polyline in the Google Map for the i-th route
            map.addPolyline(lineOptions);
        }
    }

}