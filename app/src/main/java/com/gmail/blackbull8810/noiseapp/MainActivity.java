package com.gmail.blackbull8810.noiseapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private GauseFragment gauseFragment;
    private ChartFragment chartFragment;
    private ThirdFragment thirdFragment;
    private ViewPagerAdapter adapter;
    private MenuItem prevMenuItem;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_home:

                    viewPager.setCurrentItem(0);
                    break;

                case R.id.navigation_chart:

                    viewPager.setCurrentItem(1);
                    break;

                case R.id.navigation_history:

                    viewPager.setCurrentItem(2);
                    break;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        viewPager = (ViewPager) findViewById(R.id.viewPager);

        setupViewPager(viewPager);

        //PageChange 리스너..
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                }
                else
                {
                    navigation.getMenu().getItem(1).setChecked(false);
                }
                Log.d("page", "onPageSelected: "+position);
                navigation.getMenu().getItem(position).setChecked(true);
                prevMenuItem = navigation.getMenu().getItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        }); //리스너 끝

    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        gauseFragment = new GauseFragment();
        chartFragment = new ChartFragment();
        thirdFragment = new ThirdFragment();
        adapter.addFragment(gauseFragment);
        adapter.addFragment(chartFragment);
        adapter.addFragment(thirdFragment);
        viewPager.setAdapter(adapter);
//        viewPager.setOffscreenPageLimit(2);
    }

}
