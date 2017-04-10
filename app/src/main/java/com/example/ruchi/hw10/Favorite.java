package com.example.ruchi.hw10;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Ruchi on 11/19/2016.
 */

public class Favorite extends Fragment {

    View favoriteView;
    TabLayout tabLayout;
    ViewPager viewPager;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        favoriteView= inflater.inflate(R.layout.tablayout,container,false);
        tabLayout = (TabLayout) favoriteView.findViewById(R.id.tabs);
        viewPager = (ViewPager) favoriteView.findViewById(R.id.viewpager);
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
        return favoriteView;
    }

    class MyAdapter extends FragmentPagerAdapter {
        String fragment[] = {"Legislators", "Bills", "Committees"};

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
                    return new LegisFav();
                case 1:
                    return new BillFav();
                case 2:
                    return new CommFav();
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
                    return "LEGISLATORS";
                case 1:
                    return "BILLS";
                case 2:
                    return "COMMITTEES";
            }
            return null;
        }
    }
}
