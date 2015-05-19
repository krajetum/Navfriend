package com.nightfall.navfriend;


import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.nightfall.navfriend.async.RiceviInviaCordinate;
import com.nightfall.navfriend.data.Coordinates;
import com.nightfall.navfriend.data.Travel;
import com.nightfall.navfriend.data.User;
import com.nightfall.navfriend.data.trasferCoordinates;


public class TestAct extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        final Travel travel= (Travel) getIntent().getSerializableExtra("travel");
        final User user= (User) getIntent().getSerializableExtra("user");
        /*
        final EditText lat= (EditText) findViewById(R.id.lat);
        final EditText lon= (EditText) findViewById(R.id.lon);

        Button button= (Button) findViewById(R.id.button);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new RiceviInviaCordinate(TestAct.this).execute(
                            new trasferCoordinates(
                                    new Coordinates(Float.parseFloat(lat.getText().toString()),Float.parseFloat(lon.getText().toString())),
                                    user,
                                    travel));
                }
            });
        */
        



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_test, menu);
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
