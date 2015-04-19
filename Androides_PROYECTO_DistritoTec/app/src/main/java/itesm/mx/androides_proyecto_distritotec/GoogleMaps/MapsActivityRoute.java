package itesm.mx.androides_proyecto_distritotec.GoogleMaps;

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
    Double templon, templat;
    LatLng ITESM = new LatLng(25.649713, -100.290032);
    LatLng myPos = new LatLng(25.656848, -100.2826138);

    LatLng waypoint1;
    LatLng waypoint2;
    LatLng waypoint3;
    LatLng waypoint4;
    LatLng waypoint5;
    LatLng waypoint6;
    LatLng waypoint7;
    LatLng waypoint8;

    //timer
    int iTime = 5000; //5 second
    Handler hHandler;
    Runnable rRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_activity_route);

        onConnected(savedInstanceState);
        buildGoogleApiClient();

        ExpresoLocation.setObjectId("VJhJgBjYF5");

        hHandler = new Handler();

        rRunnable = new Runnable(){
            @Override
            public void run() {
                updateStatus();
                hHandler.postDelayed(rRunnable, iTime);
            }
        };

        rRunnable.run();

        // Initializing
        markerPoints = new ArrayList<LatLng>();

        // Getting reference to SupportMapFragment of the activity_main
        SupportMapFragment fm = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.mapRoute);

        // Getting Map for the SupportMapFragment
        map = fm.getMap();
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(ITESM, 14));

        if(map!=null){
            // Enable MyLocation Button in the Map
            //map.setMyLocationEnabled(true);

            MarkerOptions options = new MarkerOptions();

            //TEST RUTA VALLE
            myPos = new LatLng(25.655767,-100.385106);

            markerPoints.add(myPos);
            markerPoints.add(ITESM);


            map.addMarker(options.position(myPos).icon(BitmapDescriptorFactory.
                    defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
            map.addMarker(options.position(ITESM).icon(BitmapDescriptorFactory.
                    defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

            //TEST WAYPOINTS
            waypoint1 = new LatLng(25.655767,-100.385106);
            markerPoints.add(waypoint1);

            map.addMarker(options.position(waypoint1));

            waypoint2 = new LatLng(25.667414,-100.379827);
            markerPoints.add(waypoint2);
            //map.addMarker(options.position(waypoint2));

            waypoint3 = new LatLng(25.664958,-100.374935);
            markerPoints.add(waypoint3);
            //map.addMarker(options.position(waypoint3));

            waypoint4 = new LatLng(25.663952,-100.358112);
            markerPoints.add(waypoint4);
            //map.addMarker(options.position(waypoint4));

            waypoint5 = new LatLng(25.652501,-100.358198);
            markerPoints.add(waypoint5);
            //map.addMarker(options.position(waypoint5));

            waypoint6 = new LatLng(25.659968,-100.349379);
            markerPoints.add(waypoint6);
            //map.addMarker(options.position(waypoint6));

            waypoint7 = new LatLng(25.644222,-100.325861);
            markerPoints.add(waypoint7);
            //map.addMarker(options.position(waypoint7));

            waypoint8 = new LatLng(25.614661,-100.271144);
            markerPoints.add(waypoint8);
            //map.addMarker(options.position(waypoint8));


            String url = getDirectionsUrl(markerPoints.get(0), markerPoints.get(1));

            DownloadTask downloadTask = new DownloadTask();

            downloadTask.execute(url);

        }
    }

    private void updateStatus() {

        query.getInBackground(ExpresoLocation.getObjectId(),new GetCallback<ParseObject>() {

            @Override
            public void done(ParseObject parseObject, com.parse.ParseException e) {
                if (e == null) {
                    Toast.makeText(getApplicationContext(), "Lon: " +
                            parseObject.getString("longitud") + "\n" +
                            "Lat: " + parseObject.getString("latitud"), Toast.LENGTH_LONG).show();
                } else {
                    e.printStackTrace();
                }
            }
        });
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