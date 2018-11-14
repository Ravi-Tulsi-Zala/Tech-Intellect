package com.example.trailblazers.techintellect;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class HomeScreen extends AppCompatActivity implements Tab1.OnFragmentInteractionListener , Tab2.OnFragmentInteractionListener {

    Boolean vibrateSwitchOn; //added by Aravind
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);

        TabLayout tabLayout = findViewById(R.id.tablayout);
        tabLayout.addTab(tabLayout.newTab().setText("Learn"));
        tabLayout.addTab(tabLayout.newTab().setText("Dashboard"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        final ViewPager vp = findViewById(R.id.viewpager);
        final PagerAdapter pa = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        vp.setAdapter(pa);
        vp.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                vp.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    //Added by Aravind for settings menu - Starts

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.settings_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.action_settings:
                Intent settings = new Intent(this,SettingsActivity.class);
                startActivityForResult(settings,1);
                break;
            case R.id.action_settings_logout:
                break;
            default:


        }

        return super.onOptionsItemSelected(item);
    }
    //Fetches the switch value for vibration
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                boolean switchVal = data.getBooleanExtra("switchVal",false);
                vibrateSwitchOn = switchVal;
                if(vibrateSwitchOn)
                   Toast.makeText(getApplicationContext(),"Vibrate is on", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(),"Vibrate is off", Toast.LENGTH_SHORT).show();

            }
        }
    }

    //Added by Aravind for settings menu - Ends
}
