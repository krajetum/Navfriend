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


public class MapNavfriend extends Activity
        implements OnMapReadyCallback {

    private Float longitude;
    private Float latitude;


    User user;
    Travel travel;

    private GoogleMap map;
    private LocationManager locationManager;
    private String provider;
    private Criteria criteria;
    private MyLocationListener mylistener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_map_navfriend);

        user = (User) getIntent().getSerializableExtra("user");
        travel = (Travel) getIntent().getSerializableExtra("travel");

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);   //default

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

        new Thread() {
            @Override
            public void run() {
                while(!isInterrupted())  {
                    try {
                        Thread.sleep(2500);
                    } catch (InterruptedException e) {
                        break;
                    }
                    Location location = locationManager.getLastKnownLocation(provider);
                    Float lat = new Float(location.getLatitude());
                    Float lon = new Float(location.getLongitude());

                    new  RiceviInviaCordinate(
                            mylistener.listenerId,
                            MapNavfriend.this,
                            map,
                            user,
                            travel).execute(new Coordinates(lat, lon));
                }
            }
        }.start();
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

    }

    private class MyLocationListener implements LocationListener {
        private String listenerId;

        private MyLocationListener() {
            listenerId = "listener" +hashCode();
        }

        @Override
        public void onLocationChanged(Location location) {

            latitude = new Float(location.getLatitude());
            longitude = new Float(location.getLongitude());


            if(map!=null)
                new  RiceviInviaCordinate(listenerId, MapNavfriend.this, map, user, travel).execute(new Coordinates(latitude, longitude));

            Log.i("location_listener", "Location changed! ");

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
