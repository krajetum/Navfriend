package com.nightfall.navfriend.manager;

import android.util.Log;

import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Dev on 28/05/2015.
 */
public class MarkerManager {
    private static MarkerManager instance;

    private Map<String, List<Marker>> map;

    private MarkerManager() {
        map = new HashMap<>();
    }

    public static MarkerManager getInstance() {
        if (instance == null) {
            instance = new MarkerManager();
        }
        return instance;
    }

    public void addMarker(String id, Marker marker) {
        if (!map.containsKey(id)) {
            Log.i("markermgr", "creazione mappa per sessione " + id);
            map.put(id, new ArrayList<Marker>());
        }
        Log.i("markermgr", "inserimento marker per session " + id);
        map.get(id).add(marker);
    }

    public void clearMarkers(String id) {
        if (map.containsKey(id)) {
            Log.i("markermgr", "clearing markers for id " + id);
            for (Marker marker : map.get(id)) {
                marker.remove();
            }
            map.get(id).clear();
        }
    }
}
