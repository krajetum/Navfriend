package com.nightfall.navfriend;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nightfall.navfriend.async.RiceviInviaCordinate;
import com.nightfall.navfriend.data.Coordinates;
import com.nightfall.navfriend.data.Travel;
import com.nightfall.navfriend.data.User;


public class MapNavfriend_old extends Activity
        implements OnMapReadyCallback {

    private Float longitude;
    private Float latitude;


    User user;
    Travel travel;
//    private GoogleApiClient apiClient;
    private GoogleMap map;
    private LocationManager locationManager;
    private String provider;
    private Criteria criteria;
    private MyLocationListener mylistener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        apiClient = new GoogleApiClient.Builder(this)
//                .addConnectionCallbacks(this)
//                .addOnConnectionFailedListener(this)
//                .addApi(LocationServices.API)
//                .build();


        setContentView(R.layout.activity_map_navfriend);

        user = (User) getIntent().getSerializableExtra("user");
        travel = (Travel) getIntent().getSerializableExtra("travel");

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);   //default


        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        for (String s : locationManager.getAllProviders()) {
            Log.i("location_listener", "Provider: " + s);
        }

        provider = "network";
//        provider = locationManager.getProvider(criteria, false);

        Location location = locationManager.getLastKnownLocation(provider);
        mylistener = new MyLocationListener();

        if (location != null) {
            mylistener.onLocationChanged(location);
        } else {
            // leads to the settings because there is no last known location
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }
        // location updates: at least 1 meter and 200millsecs change
        locationManager.requestLocationUpdates(provider, 200, 1, mylistener);

        


/*

        // Acquire a reference to the system Location Manager
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        // Define a listener that responds to location updates
        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location provider.
                setLocation(location);
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {}

            public void onProviderEnabled(String provider) {}

            public void onProviderDisabled(String provider) {}
        };

        // Register the listener with the Location Manager to receive location updates
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 50, locationListener);
        */

    }



    @Override
    public void onMapReady(GoogleMap map) {
        this.map = map;

        LatLng dest = new LatLng(travel.getDestinazione().getLatitude(), travel.getDestinazione().getLongitude());

        map.setMyLocationEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(dest, 13));

        map.addMarker(  new MarkerOptions()
                        .title("Destinazione")
                        .snippet(travel.getDescrizione())
                        .position(dest)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));


//        apiClient.connect();
    }

/*
    @Override
    public void onConnected(Bundle bundle) {
        Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(apiClient);
        if (lastLocation != null) {

              new PositionPollerTask(this, map, apiClient, user, travel).execute();

//            new  RiceviInviaCordinate(this, map, apiClient, user, travel).execute();
//            new  RiceviInviaCordinate(this, map).execute(
//                            new trasferCoordinates(
//                                    new Coordinates(new Float(lastLocation.getLatitude()), new Float(lastLocation.getLongitude())),
//                                    user,
//                                    travel));

        }
    }
*/

    private class MyLocationListener implements LocationListener {
        private String listenerId;

        private MyLocationListener() {
            listenerId = "listener" +hashCode();
        }

        @Override
        public void onLocationChanged(Location location) {
            // Initialize the location fields
            latitude = new Float(location.getLatitude());
            longitude = new Float(location.getLongitude());
//            provText.setText(provider + " provider has been selected.");

            if(map!=null)
                new  RiceviInviaCordinate(listenerId, MapNavfriend_old.this, map, user, travel).execute(new Coordinates(latitude, longitude));

            Log.i("location_listener", "Location changed! ");
//            Toast.makeText(MapNavfriend.this,  "Location changed!", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.i("location_listener", provider + "'s status changed to "+status +"!");
        }

        @Override
        public void onProviderEnabled(String provider) {
            Log.i("location_listener", "Provider " + provider + " enabled!");
        }

        @Override
        public void onProviderDisabled(String provider) {
            Log.i("location_listener", "Provider " + provider + " disabled!");
        }
    }

}
