package com.nightfall.navfriend;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.nightfall.navfriend.async.AddUserToTravel;
import com.nightfall.navfriend.async.OriginTravel;
import com.nightfall.navfriend.data.Travel;
import com.nightfall.navfriend.data.User;
import com.nightfall.navfriend.data.UserTravel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class FriendSelector extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_selector);

        final Travel travel = (Travel) getIntent().getSerializableExtra("travel");

        List<User> userArrayList = travel.getGuest();

        List<String> stringy = new ArrayList<>();

        for(User user : userArrayList){
            stringy.add(user.getEmail());
        }

        ArrayAdapter<String> testy = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, stringy);

        final ListView view = (ListView) findViewById(R.id.FriendListSelector);
        view.setAdapter(testy);
        view.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        Button avanti = (Button) findViewById(R.id.FriendListSelector_Avanti);
        avanti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<User> users = new ArrayList<>();

                int len = view.getCount();
                SparseBooleanArray checked = view.getCheckedItemPositions();
                for (int i = 0; i < len; i++) {
                    int position = checked.keyAt(i);
                    if (checked.valueAt(i)) {
                        users.add(new User(view.getItemAtPosition(position).toString(), ""));
                    }
                }

                Travel apptravel = new Travel(travel.getOwner(), travel.getDescrizione(), travel.getDestinazione());
                apptravel.setID(travel.getID());

                for(User appUser : users)
                    apptravel.addUser(appUser);

                UserTravel userTravel = new UserTravel();
                userTravel.setTravel(apptravel);
                userTravel.setUser((User) getIntent().getSerializableExtra("user"));
                new AddUserToTravel(FriendSelector.this).execute(userTravel);

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_friend_selector, menu);
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
