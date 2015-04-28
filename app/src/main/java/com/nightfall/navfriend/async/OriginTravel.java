package com.nightfall.navfriend.async;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.nightfall.navfriend.data.Coordinates;
import com.nightfall.navfriend.data.RequestSuccess;
import com.nightfall.navfriend.data.TrasferTravel;
import com.nightfall.navfriend.data.Travel;
import com.nightfall.navfriend.data.User;

import java.io.IOException;
import java.util.List;

import us.monoid.json.JSONException;
import us.monoid.web.JSONResource;
import us.monoid.web.Resty;

import static us.monoid.web.Resty.content;
import static us.monoid.web.Resty.data;
import static us.monoid.web.Resty.form;
import static us.monoid.web.Resty.*;
import us.monoid.web.Resty.*;

/**
 * Created by Dev on 28/04/2015.
 */
public class OriginTravel extends AsyncTask<Void, Void, Travel> {

    private Activity activity;
    private User user;
    private String descrizione;
    private Coordinates destinazione;
    private ProgressDialog dialog;
    public OriginTravel(Activity activity, User user, String descrizione, Coordinates destinazione){
        this.activity = activity;
        this.user  = user;
        this.descrizione = descrizione;
        this.destinazione = destinazione;
        dialog = new ProgressDialog(activity);
    }

    /** progress dialog to show user that the backup is processing. */
    /** application context. */
    @Override
    protected void onPreExecute() {
        this.dialog.setMessage("Please wait");
        this.dialog.show();
    }

    @Override
    protected Travel doInBackground(Void... params) {

        Travel travel;
        try {



            TrasferTravel trasferTravel = new TrasferTravel(user, destinazione, descrizione);
            String json = new Gson().toJson(trasferTravel);

            Resty rest=new Resty();
            JSONResource resource = rest.json("http://192.168.201.111:8182/newtravel", put(content(json)));
            System.out.println(resource.toObject().toString());
            travel = new Gson().fromJson(resource.toObject().toString(), Travel.class);

            System.out.println("richiesta effettuata");

            return travel;


        } catch (IOException e) {
            e.printStackTrace();
        }catch (JSONException e){
            e.printStackTrace();
        }
        return null;
    }



    @Override
    protected void onPostExecute(Travel travel) {
        Log.i("SUCCESS", travel.getOwner());
        Log.i("SUCCESS", travel.getDescrizione());

        List<User> user = travel.getGuest();

        for(User u : user){
            Log.println(Log.ASSERT,"SUCCESS" ,u.getEmail());
        }

        if (dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
