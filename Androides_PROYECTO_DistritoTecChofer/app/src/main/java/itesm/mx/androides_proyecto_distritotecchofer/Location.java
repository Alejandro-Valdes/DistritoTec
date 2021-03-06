package itesm.mx.androides_proyecto_distritotecchofer;

import android.content.Context;
import android.content.Intent;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;


public class Location extends ActionBarActivity  implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    GoogleApiClient mGoogleApiClient;

    LocationListener locationListener;
    LocationManager locationManager;

    ParseObject ExpresoLocation = new ParseObject("Routes");
    ParseQuery<ParseObject> query = ParseQuery.getQuery("Routes");

    TextView tvRoute;
    TextView tvLong;
    TextView tvLat;

    String strRoute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        Parse.initialize(this, "Pc66LZ9sLsdgr10KSJPD1qTwO74Ov6NWvDOTcYJb",
                "huXDJ9jUWe0zP1dDOFMCByyUAZ5RflNR9mN1pGly");

        Intent intent = getIntent();
        strRoute = intent.getStringExtra("strRoute").trim();

        onConnected(savedInstanceState);
        buildGoogleApiClient();

        switch (strRoute){
            case "Guadalupe":
                ExpresoLocation.setObjectId("gZp7gXVYJs");
                break;
            case "San Nicolas":
                ExpresoLocation.setObjectId("ZPNpXRmFl4");
                break;
            case "Galerias":
                ExpresoLocation.setObjectId("PUUe9yVvxh");
                break;
            case "Valle":
                ExpresoLocation.setObjectId("VJhJgBjYF5");
                break;
            case "Colonia Roma":
                ExpresoLocation.setObjectId("3L93E77fHG");
                break;
            case "Villas TEC":
                ExpresoLocation.setObjectId("Qr6lDLZI3d");
                break;
            case "Altavista":
                ExpresoLocation.setObjectId("ZpD2sGQYIr");
                break;
            default:
                ExpresoLocation.setObjectId("VJhJgBjYF5");
                break;
        }




        tvRoute = (TextView) findViewById(R.id.tvRuta);
        tvLat = (TextView) findViewById(R.id.tvLat);
        tvLong = (TextView) findViewById(R.id.tvLong);



        tvRoute.setText(strRoute);

    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onConnected(Bundle bundle) {
        try {
            Thread.sleep(1000);                 //1000 milliseconds is one second.
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        // Define a listener that responds to location updates
        locationListener = new LocationListener() {

            public void onLocationChanged(android.location.Location location) {
                Double templon = location.getLongitude();
                Double templat = location.getLatitude();
                String auxlon = templon.toString();
                String auxlat = templat.toString();

                ExpresoLocation.put("longitud", auxlon);
                ExpresoLocation.put("latitud", auxlat);

                ExpresoLocation.saveEventually();

                tvLong.setText(ExpresoLocation.getString("longitud"));
                tvLat.setText(ExpresoLocation.getString("latitud"));
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {}

            public void onProviderEnabled(String provider) {}

            public void onProviderDisabled(String provider) {}
        };

        // Register the listener with the Location Manager to receive location updates
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1,0, locationListener);

    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        if(locationManager != null && locationListener != null)
        {
            locationManager.removeUpdates(locationListener);
        }


    }

}
