package com.nightfall.navfriend;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.nightfall.navfriend.async.ControlloPartecipa;
import com.nightfall.navfriend.data.User;


public class menu extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Button crea = (Button) findViewById(R.id.menu_crea);
        Button partecipa = (Button) findViewById(R.id.menu_partecipa);

        final User user = (User) getIntent().getSerializableExtra("user");
        final Bundle bundle = new Bundle();
        bundle.putSerializable("user", user);

        crea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(menu.this, CreateTravel.class);
                intent.putExtras(bundle);
                menu.this.startActivity(intent);

            }
        });

        partecipa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ControlloPartecipa(menu.this).execute(user);
            }
        });



    }


    @Override
    public boolean onCreateOptionsMenu(android.view.Menu Menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_menu, Menu);
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
