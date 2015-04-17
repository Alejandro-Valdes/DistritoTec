package itesm.mx.androides_proyecto_distritotec.GoogleMaps;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
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

import itesm.mx.androides_proyecto_distritotec.R;

public class MapsActivity extends FragmentActivity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    static final LatLng ITESM = new LatLng(25.649713, -100.290032);
    GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();
        onConnected(savedInstanceState);
        buildGoogleApiClient();

    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ITESM, 15));
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    private void setUpMap() {
        Marker itesm = mMap.addMarker(new MarkerOptions()
                .position(ITESM)
                .title("Itesm")
                .snippet("hi!")
                .icon(BitmapDescriptorFactory
                        .fromResource(R.drawable.transport)));
        mMap.setMyLocationEnabled(true);
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
            Thread.sleep(2000);                 //1000 milliseconds is one second.
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        // Define a listener that responds to location updates
        LocationListener locationListener = new LocationListener() {

            public void onLocationChanged(Location location) {
                Double templon = location.getLongitude();
                Double templat = location.getLatitude();
                String auxlon = templon.toString();
                String auxlat = templat.toString();

                Toast.makeText(getApplicationContext(), auxlon + " " + auxlat,
                        Toast.LENGTH_SHORT).show();
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {}

            public void onProviderEnabled(String provider) {}

            public void onProviderDisabled(String provider) {}
        };

        // Register the listener with the Location Manager to receive location updates
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1, 1, locationListener);
    }

    @Override
    public void onConnectionSuspended(int i) {
        //
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        //
    }
}
