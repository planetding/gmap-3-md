package org.planetding.gmap_3_md;

import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.UiSettings;

import com.google.android.gms.location.LocationListener;

import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

/** from web
package com.example.google.maps.demo;
**/
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.widget.Toast;



import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;



/** added by MD */
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

/** import org.planetding.gmap_3_md.R; */
/**
 * @author saxman MainActivity

public class gmap3Activity extends FragmentActivity {
    private static final String LOG_TAG = "ExampleApp";

    private static final String SERVICE_URL = "YOUR DRIVE SERVICE URL";

    protected GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     //   setContentView(R.layout.activity_main);
        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {
        if (map == null) {
            map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            if (map != null) {
                setUpMap();
            }
        }
    }

    private void setUpMap() {
        // Retrieve the city data from the web service
        // In a worker thread since it's a network operation.
        new Thread(new Runnable() {
            public void run() {
                try {
                    retrieveAndAddCities();
                } catch (IOException e) {
                    Log.e(LOG_TAG, "Cannot retrive cities", e);
                    return;
                }
            }
        }).start();
    }

    protected void retrieveAndAddCities() throws IOException {
        HttpURLConnection conn = null;
        final StringBuilder json = new StringBuilder();
        try {
            // Connect to the web service
            URL url = new URL(SERVICE_URL);
            conn = (HttpURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());

            // Read the JSON data into the StringBuilder
            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                json.append(buff, 0, read);
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error connecting to service", e);
            throw new IOException("Error connecting to service", e);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        // Create markers for the city data.
        // Must run this on the UI thread since it's a UI operation.
        runOnUiThread(new Runnable() {
            public void run() {
                try {
                    createMarkersFromJson(json.toString());
                } catch (JSONException e) {
                    Log.e(LOG_TAG, "Error processing JSON", e);
                }
            }
        });
    }

    void createMarkersFromJson(String json) throws JSONException {
        // De-serialize the JSON string into an array of city objects
        JSONArray jsonArray = new JSONArray(json);
        for (int i = 0; i < jsonArray.length(); i++) {
            // Create a marker for each city in the JSON data.
            JSONObject jsonObj = jsonArray.getJSONObject(i);
            map.addMarker(new MarkerOptions()
                    .title(jsonObj.getString("name"))
                    .snippet(Integer.toString(jsonObj.getInt("population")))
                    .position(new LatLng(
                            jsonObj.getJSONArray("latlng").getDouble(0),
                            jsonObj.getJSONArray("latlng").getDouble(1)
                    ))
            );
        }
    }
}

/**originally extends: FragmentActivity, changed to AppCompatActivity, and the missing title bar showed up! MD 4-25-17 */

public class gmap3Activity extends AppCompatActivity implements OnMapReadyCallback
      //  ConnectionCallbacks
        // , OnConnectionFailedListener
    {

    private GoogleMap mMap;
    public double mLat, mLng;
/**
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gmap3);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }
 */

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
      //  mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        if (mLastLocation !=null) {
               mLat = mLastLocation.getLatitude();
               mLng = mLastLocation.getLongitude();
        }
        else {
            mLat = 20;
            mLng = 20;
        }

     //   LatLng currentLL = new LatLng(String.valueOf(mLastLocation.getLatitude(),String.valueOf(mLastLocation.getLatitude());
        LatLng currentLL = new LatLng(mLat,mLng);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLL));

        //added by MD to add the center map button to my current location, and add a blue dot to my current location
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);

       // mMap.moveCamera(CameraUpdateFactory.newLatLng( ));
    }


/**
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        // LatLng sydney = new LatLng(-34, 151);
        // mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        // mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

 if (mMap != null) {
 int permission = ContextCompat.checkSelfPermission(this,
 Manifest.permission.ACCESS_FINE_LOCATION);

 if (permission == PackageManager.PERMISSION_GRANTED) {
 mMap.setMyLocationEnabled(true);
 } else {
 requestPermission(
 Manifest.permission.ACCESS_FINE_LOCATION,
 LOCATION_REQUEST_CODE);
 }
 }
    */


    private static final String LOG_TAG = "ExampleApp";

    private static final String SERVICE_URL = "https://script.google.com/macros/s/AKfycbwNZez9zpm9afZ8_4OuAmSyW4BjenPJvN_5COkAJKUGbEa0fkg/exec";

    protected GoogleMap map;

    /**
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gmap3);
        setUpMapIfNeeded();
    }
*/

//copied from another file oncreate, with the MapFragment, with this, the starting map is then oriented to the

    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;

        protected LocationRequest mLocationRequest;

        private final int[] MAP_TYPES = { GoogleMap.MAP_TYPE_SATELLITE,
            GoogleMap.MAP_TYPE_NORMAL,
            GoogleMap.MAP_TYPE_HYBRID,
            GoogleMap.MAP_TYPE_TERRAIN,
            GoogleMap.MAP_TYPE_NONE };
        private int curMapTypeIndex = 0;

        protected void createLocationRequest() {
            mLocationRequest = new LocationRequest();

            // Sets the desired interval for active location updates. This interval is
            // inexact. You may not receive updates at all if no location sources are available, or
            // you may receive them slower than requested. You may also receive updates faster than
            // requested if other applications are requesting location at a faster interval.
            mLocationRequest.setInterval(30);

            // Sets the fastest rate for active location updates. This interval is exact, and your
            // application will never receive updates faster than this value.
            mLocationRequest.setFastestInterval(10);

            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gmap3);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // To connect to the API, you need to create an instance of the Google Play services API client.
        // For details about using the client, see the guide to Accessing Google APIs.
        // In your activity's onCreate() method, create an instance of Google API Client,
        // using the GoogleApiClient.Builder class to add the LocationServices API, as the following code snippet shows.
        // Create an instance of GoogleAPIClient.
        /**       if (mGoogleApiClient == null) {
         mGoogleApiClient = new GoogleApiClient.Builder(this)
         .addConnectionCallbacks(this)
         //         .addOnConnectionFailedListener(this)
         .addApi(LocationServices.API)
         .build();
         }

         LocationRequest locationRequest  = LocationRequest.create()
         .setNumUpdates(1)
         .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
         .setInterval(0);

         //       LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,locationRequest, this);

         //       LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

         mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);


         **/
    }
    /**

    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

     */
    /**
     * Runs when a GoogleApiClient object successfully connects.

    @Override
    public void onConnected(Bundle connectionHint) {
        // Provides a simple way of getting a device's location and is well suited for
        // applications that do not require a fine-grained location and that do not need location
        // updates. Gets the best and most recent location currently available, which may be null
        // in rare cases when a location is not available.
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
      /**  if (mLastLocation != null) {
            mLatitudeText.setText(String.format("%s: %f", mLatitudeLabel,
                    mLastLocation.getLatitude()));
            mLongitudeText.setText(String.format("%s: %f", mLongitudeLabel,
                    mLastLocation.getLongitude()));
        } else {
            // Toast.makeText(this, R.string.no_location_detected, Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(gmap3Activity.this,
                "onConnectionSuspended: " + String.valueOf(i),
                Toast.LENGTH_LONG).show();
    }
     **/

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {
        if (map == null) {
            map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
            if (map != null) {
                setUpMap();
            }
        }
    }

    private void setUpMap() {
        // added by MD to set up map configuration
       UiSettings UiSettings = map.getUiSettings();
      //  UiSettings.setZoomControlsEnabled(false);
      //  UiSettings.setMyLocationButtonEnabled( false );
      //  UiSettings.setScrollGesturesEnabled(false);
       // UiSettings.setZoomGesturesEnabled(false);
       // UiSettings.setTiltGesturesEnabled(false);
       // UiSettings.setRotateGesturesEnabled(false);

        // Retrieve the city data from the web service
        // In a worker thread since it's a network operation.



        new Thread(new Runnable() {
            public void run() {
                try {
                    retrieveAndAddCities();
                } catch (IOException e) {
                    Log.e(LOG_TAG, "Cannot retrieve cities", e);
                    return;
                }
            }
        }).start();
    }

    protected void retrieveAndAddCities() throws IOException {
        HttpURLConnection conn = null;
        final StringBuilder json = new StringBuilder();
        try {
            // Connect to the web service
            URL url = new URL(SERVICE_URL);
            conn = (HttpURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());

            // Read the JSON data into the StringBuilder
            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                json.append(buff, 0, read);
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error connecting to service", e);
            throw new IOException("Error connecting to service", e);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        // Create markers for the city data.
        // Must run this on the UI thread since it's a UI operation.
        runOnUiThread(new Runnable() {
            public void run() {
                try {
                    createMarkersFromJson(json.toString());
                } catch (JSONException e) {
                    Log.e(LOG_TAG, "Error processing JSON", e);
                }
            }
        });
    }

    void createMarkersFromJson(String json) throws JSONException {
        // De-serialize the JSON string into an array of city objects
        JSONArray jsonArray = new JSONArray(json);
        for (int i = 0; i < jsonArray.length(); i++) {
            // Create a marker for each city in the JSON data.
            JSONObject jsonObj = jsonArray.getJSONObject(i);
            map.addMarker(new MarkerOptions()
//                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW))
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.en))
                    .title("<a href='" + jsonObj.getString("name" )+ "'>" + "video" + "</a>" )
                    .snippet(Integer.toString(jsonObj.getInt("population")))
                    .draggable(true)
        //            .snippet(jsonObj.getString("myurl"))
                    .position(new LatLng(
                            jsonObj.getJSONArray("latlng").getDouble(0),
                            jsonObj.getJSONArray("latlng").getDouble(1)
                    ))
            );
        }
    }

        /** these are about menu added
         * **/

        private void toggleTraffic() {
            map.setTrafficEnabled( !map.isTrafficEnabled() );
        }

        private void cycleMapType() {
            if( curMapTypeIndex < MAP_TYPES.length - 1 ) {
                curMapTypeIndex++;
            } else {
                curMapTypeIndex = 0;
            }

            map.setMapType( MAP_TYPES[curMapTypeIndex] );
        }

        /** To instruct Android to use your options menu, open the Activity class you want it to appear with.
     * Add the following method to your Java code, inside the class declaration and after the "onCreate" method
       **/
        public boolean onCreateOptionsMenu(Menu menu) {

            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_main, menu);
            return true;
        }

       /** To respond to user interaction with your menu, you first need to detect it within your Activity class.
        * Add the following method outline after the "onCreateOptionsMenu" method.
        * Inside this method, which returns a boolean value, you can add code to respond to each particular item.
        * The system will automatically call the "onOptionsItemSelected" method when the user chooses any of the options menu items.
        **/
        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_clear: {
      //              getMap().clear();
                    return true;
                }
                case R.id.action_circle: {
      //              drawCircle( new LatLng( mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude() ) );
                    return true;
                }
                case R.id.action_polygon: {
      //              drawPolygon( new LatLng( mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude() ) );
                    return true;
                }
                case R.id.action_overlay: {
      //              drawOverlay( new LatLng( mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude() ), 250, 250 );
                    return true;
                }
                case R.id.action_traffic: {
                    toggleTraffic();
                    return true;
                }
                case R.id.action_cycle_map_type: {
                    cycleMapType();
                    return true;
                }
                default:
                    return super.onOptionsItemSelected(item);
            }

            }



}
/**/