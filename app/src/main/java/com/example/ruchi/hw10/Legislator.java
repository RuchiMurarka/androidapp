package com.example.ruchi.hw10;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Ruchi on 11/19/2016.
 */

public class Legislator extends Fragment {

    View legisView;
    TabLayout tabLayout;
    ViewPager viewPager;
   // private RequestQueue requestQueue;
    private static Legislator legisInstance;
    //final String legisURL="https://helloworld-09.appspot.com/index.php?type";
    //private LegisJsonListener legisJsonListener;


    private TextView textView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        legisView= inflater.inflate(R.layout.tablayout,container,false);
        tabLayout = (TabLayout) legisView.findViewById(R.id.tabs);
        viewPager = (ViewPager) legisView.findViewById(R.id.viewpager);
        viewPager.setAdapter(new MyAdapter(getChildFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){


            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());


            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
        });

        return legisView;

    }

    class MyAdapter extends FragmentPagerAdapter {
        String fragment[] = {"By State", "House", "Senate"};

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * Return fragment with respect to Position .
         */

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new By_states();
                case 1:
                    return new By_house();
                case 2:
                    return new By_senate();
            }
            return null;
        }

        @Override
        public int getCount() {

            return fragment.length;

        }

        /**
         * This method returns the title of the tab according to the position.
         */

        @Override
        public CharSequence getPageTitle(int position) {

            switch (position) {
                case 0:
                    return "By States";
                case 1:
                    return "House";
                case 2:
                    return "Senate";
            }
            return null;
        }
    }
}
