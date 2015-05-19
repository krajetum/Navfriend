package com.nightfall.navfriend.async;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.nightfall.navfriend.data.RequestSuccess;
import com.nightfall.navfriend.data.Travel;
import com.nightfall.navfriend.data.trasferCoordinates;

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
public class RiceviInviaCordinate extends AsyncTask<trasferCoordinates, Void, List<trasferCoordinates>> {
    Activity activity;
    GoogleMap map;
    trasferCoordinates position;
    public RiceviInviaCordinate(Activity activity, GoogleMap map){this.activity=activity;this.map = map;}

    @Override
    protected List<trasferCoordinates> doInBackground(trasferCoordinates... params) {
        position = params[0];
        List<trasferCoordinates> request = new ArrayList<>();

        try {
            Gson gson = new Gson();
            String json = gson.toJson(position);

            Resty resty = new Resty();

            JSONResource res = resty.json("http://192.168.201.116:8182/getposition", put(content(json)));
            JSONArray array = res.array();
            for(int i = 0; i<array.length();i++) {
                String elem = array.getJSONObject(i).toString();
                request.add(gson.fromJson(elem, trasferCoordinates.class));

            }

            return request;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void onPostExecute(List<trasferCoordinates> success) {
        if(success != null){


            Log.println(Log.INFO, "ricevi_invia", "successo");
            for (trasferCoordinates succes : success) {

                map.addMarker(  new MarkerOptions()
                        .title(succes.getUser().getEmail())
                        .position(succes.getPosition().getLatLng())
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

                Log.println(Log.INFO, "ricevi_invia", succes.getUser().getEmail()+": "+succes.getPosition().getLatitude() + "," + succes.getPosition().getLongitude());
            }
        }else{
            Log.println(Log.INFO, "ricevi_invia", "errore");
        }
    }
}
