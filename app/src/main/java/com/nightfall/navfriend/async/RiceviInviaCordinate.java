package com.nightfall.navfriend.async;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.nightfall.navfriend.data.Coordinates;
import com.nightfall.navfriend.data.trasferCoordinates;
import com.nightfall.navfriend.data.Travel;
import com.nightfall.navfriend.data.User;
import com.nightfall.navfriend.manager.MarkerManager;
import com.nightfall.navfriend.manager.RestServiceManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import us.monoid.json.JSONArray;
import us.monoid.json.JSONException;
import us.monoid.web.JSONResource;
import us.monoid.web.Resty;

import static us.monoid.web.Resty.content;
import static us.monoid.web.Resty.put;

/**
 * Created by Dev on 12/05/2015.
 */
public class RiceviInviaCordinate extends AsyncTask<Coordinates, Void, List<trasferCoordinates>> {
    private final User user;
    private final Travel travel;
    private final String sessionId;
    Activity activity;
    GoogleMap map;

    public RiceviInviaCordinate(String id, Activity activity, GoogleMap map, User user, Travel travel){
        this.sessionId = id;
        this.activity=activity;
        this.map = map;
        this.user = user;
        this.travel = travel;
    }

    @Override
    protected List<trasferCoordinates> doInBackground(Coordinates... c) {
        Log.i("ricevi_invia", "partenza...");

        trasferCoordinates position = new trasferCoordinates(c[0], user, travel);

        List<trasferCoordinates> friendsCoordinate = new ArrayList<>();

        try {
            Gson gson = new Gson();
            String json = gson.toJson(position);

            Resty resty = new Resty();

            JSONResource res = resty.json(RestServiceManager.getInstance().getURI("getposition"), put(content(json)));
            JSONArray array = res.array();
            for (int i = 0; i < array.length(); i++) {
                String elem = array.getJSONObject(i).toString();
                friendsCoordinate.add(gson.fromJson(elem, trasferCoordinates.class));

            }

            Log.i("ricevi_invia", "restituzione risultati...");

            return friendsCoordinate;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<trasferCoordinates> friendsCoordinate) {

        MarkerManager.getInstance().clearMarkers(sessionId);

        Log.println(Log.INFO, "ricevi_invia", "onPostExecute - successo");
        for (trasferCoordinates succes : friendsCoordinate) {

            Marker marker = map.addMarker(new MarkerOptions()
                    .title(succes.getUser().getEmail())
                    .position(succes.getPosition().getLatLng())
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

            MarkerManager.getInstance().addMarker(sessionId, marker);

            Log.println(Log.INFO, "ricevi_invia", succes.getUser().getEmail()+": "+succes.getPosition().getLatitude() + "," + succes.getPosition().getLongitude());
        }

    }
}
