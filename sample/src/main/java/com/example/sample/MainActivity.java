package com.example.sample;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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

    public Drawable getImageFiltered(Context context, int res, int color) {
        // load image:
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), res);
        BitmapDrawable drawable = new BitmapDrawable(context.getResources(), bitmap);

        // create matrix to convert to greyscale:
        ColorMatrix greyscaleMatrix = new ColorMatrix();
        greyscaleMatrix.setSaturation(0);

        // create matrix to colorize and apply transluceny:
        ColorMatrix colorizeMatrix = new ColorMatrix();
        color = context.getResources().getColor(color);
        colorizeMatrix.setScale(Color.red(color) / 255.0f,
                Color.green(color) / 255.0f,
                Color.blue(color) / 255.0f,
                Color.alpha(color) / 255.0f); // <- try setting this to 1.0f for no translucency

        // concatenate the two matrices and create a ColorMatrixColorFilter from the result:
        greyscaleMatrix.postConcat(colorizeMatrix);
        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(greyscaleMatrix);

        // apply the filter:
        drawable.setColorFilter(filter);
        return drawable;
    }
}
