package com.nightfall.navfriend;

import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.nightfall.navfriend.async.OriginTravel;
import com.nightfall.navfriend.data.Coordinates;
import com.nightfall.navfriend.data.User;

import java.io.IOException;
import java.util.List;


public class CreateTravel extends ActionBarActivity {

    private Coordinates coordinates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_travel);

        EditText dest = (EditText) findViewById(R.id.destination_text);
        final EditText descr = (EditText) findViewById(R.id.descrizione_text);

        Button next = (Button) findViewById(R.id.avanti_button);
        Button back = (Button) findViewById(R.id.indietro_button);

        final User user = (User) getIntent().getSerializableExtra("user");
        Geocoder geocoder = new Geocoder(this);
        List<Address> addresses = null;
        coordinates = null;
        float latitude;
        float longitude;
        try {
            addresses = geocoder.getFromLocationName(dest.getText().toString(), 1);
            if (addresses.size() > 0) {
                latitude = new Float(addresses.get(0).getLatitude());
                longitude = new Float(addresses.get(0).getLongitude());
                coordinates = new Coordinates(longitude, latitude);
            } else {
                coordinates = new Coordinates(0, 0);
            }
        }catch (IOException ex){
            ex.printStackTrace();
        }

            next.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
                new OriginTravel(CreateTravel.this, user, descr.getText().toString(), coordinates).execute();
           }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_travel, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
