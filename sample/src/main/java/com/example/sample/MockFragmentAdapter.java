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

    @Override
    public int getColor(final int position) {
        int color = 0;
        switch (position) {
            case 0:
                color = ContextCompat.getColor(context, R.color.colorPrimary);
                break;
            case 1:
                color = ContextCompat.getColor(context, R.color.colorPrimary2);
                break;
            case 2:
                color = ContextCompat.getColor(context, R.color.colorPrimary3);
                break;
        }
        return color;
    }

    @Override
    public Drawable getIcon(final int position) {
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

    @Override
    public int getFallbackDrawableResId(final int position) {
        int fallbackResId = R.drawable.headerfallback;
        switch (position) {
            case 0:
                fallbackResId = R.drawable.headerfallback;
                break;
            case 1:
                fallbackResId = R.drawable.headerfallback_news;
                break;
            case 2:
                fallbackResId = R.drawable.headerfallback_video;
                break;
        }
        return fallbackResId;
    }
}