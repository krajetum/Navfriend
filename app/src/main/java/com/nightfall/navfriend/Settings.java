package com.nightfall.navfriend;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.nightfall.navfriend.manager.RestServiceManager;


public class Settings extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        final EditText address = (EditText) findViewById(R.id.AddressField);
        final EditText port = (EditText) findViewById(R.id.PortField);
        Button button = (Button) findViewById(R.id.Submit);

        address.setText(RestServiceManager.getInstance().getAddress());
        port.setText("" + RestServiceManager.getInstance().getPort());

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RestServiceManager.getInstance().setAddress(address.getText().toString());
                RestServiceManager.getInstance().setPort(Integer.parseInt(port.getText().toString()));
                finish();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return super.onOptionsItemSelected(item);
    }
}
