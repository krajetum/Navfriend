package com.nightfall.navfriend.async;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nightfall.navfriend.MapNavfriend;
import com.nightfall.navfriend.data.Travel;
import com.nightfall.navfriend.data.User;
import com.nightfall.navfriend.manager.RestServiceManager;

import java.io.IOException;

import us.monoid.json.JSONException;
import us.monoid.web.JSONResource;
import us.monoid.web.Resty;

import static us.monoid.web.Resty.content;
import static us.monoid.web.Resty.put;

/**
 * Created by Dev on 19/05/2015.
 */
public class ControlloPartecipa extends AsyncTask<User, Void, Travel> {
    private Activity activity;
    private User user;
    private ProgressDialog dialog;
    public ControlloPartecipa(Activity activity){
        this.activity = activity;
        dialog = new ProgressDialog(activity);
    }

    @Override
    protected void onPreExecute() {
        this.dialog.setMessage("Please wait");
        this.dialog.show();
    }

    @Override
    protected Travel doInBackground(User... params) {
        user = params[0];

        String json = new Gson().toJson(user);

        Resty resty = new Resty();
        try {

            JSONResource res = resty.json(RestServiceManager.getInstance().getURI("gettravelforuser"), put(content(json)));
            if(res==null){
                return null;
            }else {
                Travel travel = new Gson().fromJson(res.toObject().toString(), Travel.class);
                return travel;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return null;
    }


    @Override
    protected void onPostExecute(Travel aVoid) {

        if (dialog.isShowing()) {
            dialog.dismiss();
        }

        if(aVoid == null){
            Toast.makeText(activity,"Forever Alone", Toast.LENGTH_LONG);
        }else{
            Intent intent = new Intent(activity, MapNavfriend.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("user", user);
            bundle.putSerializable("travel", aVoid);
            intent.putExtras(bundle);
            activity.startActivity(intent);
        }

    }
}
