# NewsStandHeaderView
A view that was inspired by [Google's Play Kiosk](https://play.google.com/store/apps/details?id=com.google.android.apps.magazines).
The header automatically sets itself up with the help of an ```ViewPager```.
Provided a fully working sample.

![GIF showing fancy animation the NewsStandHeaderView provides](http://i.imgur.com/ybujP3g.gif)

# Setup :

Add the ```NewsStandHeaderView``` in your ```CollapsingToolbarLayout``` or ```AppBarLayout```.

``` xml
<android.support.design.widget.CoordinatorLayout>
    <android.support.design.widget.AppBarLayout>
        <android.support.design.widget.CollapsingToolbarLayout>

            <be.vrt.mobile.android.sporza.voetbal.ui.widget.slf.NewsStandHeaderView
                android:id="@+id/newsstandheaderview"
                android:layout_width="match_parent"
                android:layout_height="match_parent" />

            <android.support.v7.widget.Toolbar/>
            <android.support.design.widget.TabLayout />
        </android.support.design.widget.CollapsingToolbarLayout>
    </android.support.design.widget.AppBarLayout>
    <android.support.v4.view.ViewPager />

</android.support.design.widget.CoordinatorLayout>
```

add the attached ```ViewPager```.
You can call ```setupWithViewPager``` similar to ```TabLayout```'s [setupWithViewPager](http://developer.android.com/reference/android/support/design/widget/TabLayout.html#setupWithViewPager(android.support.v4.view.ViewPager))

``` java
@Bind(R.id.tablayout)
TabLayout tabLayout;
@Bind(R.id.viewpager)
ViewPager viewPager;
@Bind(R.id.newsstandheaderview)
NewsStandHeaderView newsStandHeaderView;

@Override
protected void onCreate(Bundle savedInstanceState) {
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
    tabLayout.setupWithViewPager(viewPager);
    newsStandHeaderView.setupWithViewPager(viewPager);
}
```

your ```ViewPager```'s adapter will need to implement [NewsStandHeaderView.NewsStandHeaderViewConfiguratorAdapter](https://github.com/timrijckaert/NewsStandHeaderView/blob/906f7f9028dd3d8c9c3712ac0169b620a9eee9f9/sample/src/main/java/com/example/sample/MockFragmentAdapter.java)
to provide the correct colors and fallback images.

# Images

You can push images to the header with ```setImages(int position of tab where the images should be displayed, List<String>)```.

``` java
newsStandHeaderView.setImages(0, asList(
                "http://sporza.be/polopoly_fs/1.2648002!image/4084014349.jpg_gen/derivatives/landscape670/4084014349.jpg",
                "http://sporza.be//polopoly_fs/1.2648048!image/755200629.jpg_gen/derivatives/landscape670/755200629.jpg",
                "http://sporza.be/polopoly_fs/1.2647785!image/1278319731.jpg_gen/derivatives/landscape670/1278319731.jpg"
        ));
```

# Dependencies
* [Picasso][picasso] (from Square)
* [KenBurnsView][kenburnsview] (from flavioarfaria)

# Contributors
* Special thanks to [Filip Maelbrancke (filipmaelbrancke)]https://github.com/filipmaelbrancke)

[picasso]: https://github.com/square/picasso
[kenburnsview]: https://github.com/flavioarfaria/KenBurnsView