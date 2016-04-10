package com.example.sample;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;

import be.vrt.mobile.android.sporza.voetbal.ui.widget.slf.NewsStandHeaderView;

/**
 * Created by trijckaert on 08/04/16.
 */
public class MockFragmentAdapter extends FragmentPagerAdapter implements NewsStandHeaderView.NewsStandHeaderViewConfiguratorAdapter {
    private final Context context;

    public MockFragmentAdapter(
            final Context context,
            final FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(final int position) {
        MockFragment mockFragment = MockFragment.newInstance();
        return mockFragment;
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

    @Override
    public int getColor(int position) {
        int color = 0;
        switch (position) {
            case 0:
                color = ContextCompat.getColor(context, R.color.colorAccent);
                break;
            case 1:
                color = ContextCompat.getColor(context, R.color.colorPrimary);
                break;
            case 2:
                color = ContextCompat.getColor(context, R.color.colorGreen);
                break;
        }
        return color;
    }

    @Override
    public Drawable getIcon(int position) {
        Drawable icon = null;
        switch (position) {
            case 0:
                icon = ContextCompat.getDrawable(context, R.drawable.ic_icon_1);
                break;
            case 1:
                icon = ContextCompat.getDrawable(context, R.drawable.ic_icon_2);
                break;
            case 2:
                icon = ContextCompat.getDrawable(context, R.drawable.ic_icon_1);
                break;
        }
        return icon;
    }
}