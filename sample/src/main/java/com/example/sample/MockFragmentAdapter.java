package com.example.sample;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by trijckaert on 08/04/16.
 */
public class MockFragmentAdapter extends FragmentPagerAdapter  {
    public MockFragmentAdapter(final FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return MockFragment.newInstance();
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(final int position) {
        String title = "";
        switch (position) {
            case 0:
                title = "Hoogtepunten";
                break;
            case 1:
                title = "Nieuws";
                break;
            case 2:
                title = "Video";
                break;
        }
        return title;
    }
}
