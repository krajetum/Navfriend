package com.nightfall.navfriend;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nightfall.navfriend.data.RequestSuccess;
import com.nightfall.navfriend.data.User;
import java.io.IOException;

import us.monoid.json.JSONException;
import us.monoid.web.JSONResource;
import us.monoid.web.Resty;
import static us.monoid.web.Resty.content;
import static us.monoid.web.Resty.put;

/**
 * Created by Dev on 21/04/2015.
 */
public class LogInConnection implements Runnable {
    private User user;
    private Activity activity;

    public LogInConnection(User user, Activity activity) {
        this.activity = activity;
        this.user = user;
    }

    @Override
    public void run() {
        try {

            String json = new Gson().toJson(user);
            Log.i("JSON", "effettuando la richiesta..." + json);
            Resty rest=new Resty();
            JSONResource resource = rest.json("http://192.168.201.118:8182/login", put(content(json)));
            System.out.println(resource.toObject().toString());
            final RequestSuccess success = new Gson().fromJson(resource.toObject().toString(), RequestSuccess.class);

            System.out.println("richiesta effettuata");

            activity.runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(activity.getBaseContext(), success.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        } catch (IOException e) {
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(activity.getBaseContext(), "Error", Toast.LENGTH_LONG).show();
                }
            });
            e.printStackTrace();
        }catch (JSONException e){
            e.printStackTrace();
        }
    }
}
