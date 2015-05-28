package com.nightfall.navfriend.async;

import android.app.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import android.widget.Toast;

import com.google.gson.Gson;
import com.nightfall.navfriend.Main;
import com.nightfall.navfriend.manager.RestServiceManager;
import com.nightfall.navfriend.menu;
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
public class LogInConnection extends AsyncTask<Void, Void, RequestSuccess>/*implements Runnable */{
    private User user;
    private Activity activity;
    private Main mainclass;
    private ProgressDialog dialog;
    public LogInConnection(User user, Activity activity) {
        this.activity = activity;
        this.user = user;
        mainclass = (Main) activity;
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
    protected RequestSuccess doInBackground(Void... params) {
        RequestSuccess success;
        try {

            String json = new Gson().toJson(user);
            Log.i("JSON", "effettuando la richiesta..." + json);
            Resty rest=new Resty();
            JSONResource resource = rest.json(RestServiceManager.getInstance().getURI("login"), put(content(json)));
            System.out.println(resource.toObject().toString());
            success = new Gson().fromJson(resource.toObject().toString(), RequestSuccess.class);

            System.out.println("richiesta effettuata");

            return success;


        } catch (IOException e) {
            e.printStackTrace();
        }catch (JSONException e){
            e.printStackTrace();
        }
        return new RequestSuccess(false, "errore nella richiesta");
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(RequestSuccess success) {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }

        if(success.isStatus()){
            Intent intent = new Intent(activity, menu.class);
            //Intent intent = new Intent(activity, FriendSelector.class);

            Bundle bundle = new Bundle();
            bundle.putSerializable("user", user);
            intent.putExtras(bundle);
            mainclass.startActivity(intent);
        }else {
            Toast.makeText(activity.getBaseContext(), success.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
