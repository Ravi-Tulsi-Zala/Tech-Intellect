package com.example.trailblazers.techintellect;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

public class HomeScreen extends AppCompatActivity implements Tab1.OnFragmentInteractionListener , Tab2.OnFragmentInteractionListener {

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
}
