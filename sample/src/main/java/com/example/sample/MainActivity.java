package com.example.sample;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import be.vrt.mobile.android.sporza.voetbal.ui.widget.slf.NewsStandHeaderView;
import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tablayout)
    TabLayout tabLayout;
    @Bind(R.id.viewpager)
    ViewPager viewPager;
    @Bind(R.id.newsstandheaderview)
    NewsStandHeaderView newsStandHeaderView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        final MockFragmentAdapter adapter = new MockFragmentAdapter(this.getBaseContext(), this.getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        newsStandHeaderView.setupWithViewPager(viewPager);
//        panningImageView.setImageDrawable(getImageFiltered(getApplicationContext(), R.drawable.example, R.color.colorPrimary));
    }
}
