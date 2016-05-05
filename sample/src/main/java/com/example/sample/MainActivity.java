package com.example.sample;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import be.vrt.mobile.android.sporza.voetbal.ui.widget.slf.NewsStandHeaderView;
import butterknife.Bind;
import butterknife.ButterKnife;

import static java.util.Arrays.asList;

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

        //At any time you can push urls to the header
        //If there are no images found for the position the view falls back to the fallback images
        newsStandHeaderView.setImages(0, asList(
                "http://sporza.be/polopoly_fs/1.2648002!image/4084014349.jpg_gen/derivatives/landscape670/4084014349.jpg",
                "http://sporza.be//polopoly_fs/1.2648048!image/755200629.jpg_gen/derivatives/landscape670/755200629.jpg",
                "http://sporza.be/polopoly_fs/1.2647785!image/1278319731.jpg_gen/derivatives/landscape670/1278319731.jpg"
        ));

        newsStandHeaderView.setImages(1, asList(
                "http://sporza.be/polopoly_fs/1.2648028!image/1748605926.jpg_gen/derivatives/landscape470/1748605926.jpg",
                "http://sporza.be/polopoly_fs/1.2647651!image/3778223254.jpg_gen/derivatives/landscape670/3778223254.jpg",
                "http://sporza.be/polopoly_fs/1.2647583!image/2125240926.jpg_gen/derivatives/landscape670/2125240926.jpg"
        ));
    }
}