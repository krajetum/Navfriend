package com.nightfall.navfriend;

import android.util.Log;

import com.google.gson.Gson;
import com.nightfall.navfriend.data.User;
import java.io.IOException;
import us.monoid.web.Resty;
import static us.monoid.web.Resty.content;
import static us.monoid.web.Resty.put;

/**
 * Created by Dev on 21/04/2015.
 */
public class LogInConnection implements Runnable {
    private User user;

    public LogInConnection(User user) {
        this.user = user;
    }

    @Override
    public void run() {
        try {

            String json = new Gson().toJson(user);
            Log.i("JSON", "effettuando la richiesta..." + json);
            Resty rest=new Resty().json("http://192.168.201.118:8182/login", put(content(json)));
            System.out.println("richiesta effettuata");


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
