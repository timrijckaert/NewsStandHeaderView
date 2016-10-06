package be.vrt.mobile.android.sporza.voetbal.ui.widget.slf;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by trijckaert on 09/04/16.
 */
public class NewsStandHeaderView
        extends RelativeLayout
        implements PanningImageView.TransitionListener, HeaderIconView.TransitionListener, ViewPager.OnPageChangeListener {

    private static final int TRANSITIONS_BEFORE_SWITCHING_THRESHOLD = 3;
    private final PanningImageView panningImage;
    private final HeaderIconView headerBubble;
    private final SparseArray<Drawable> iconsReference = new SparseArray<>();
    private final SparseArray<Integer> colorsReference = new SparseArray<>();
    private final SparseArray<List<String>> headerViewImageReference = new SparseArray<>();
    private int transitionsCount = 0;

    private NewsStandHeaderViewConfiguratorAdapter adapter;

    private int currentImageIndex = 0;
    private int currentPageIndex;
    private Picasso picasso;

    //<editor-fold desc="Chaining Constructors">
    public NewsStandHeaderView(final Context context) {
        this(context, null);
    }

    public NewsStandHeaderView(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }
    //</editor-fold>

    public NewsStandHeaderView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        final LayoutInflater layoutInflater = LayoutInflater.from(context);
        final View view = layoutInflater.inflate(R.layout.composite_newsstand_header, this, true);

        panningImage = (PanningImageView) view.findViewById(R.id.panningImage);
        headerBubble = (HeaderIconView) view.findViewById(R.id.headerBubble);

        panningImage.setTransitionListener(this);
        headerBubble.setTransitionListener(this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (!this.isInEditMode()) {
            picasso = Picasso.with(getContext());
        }
    }

    @Override
    public void onTransitionStart(final Transition transition) {
    }

    @Override
    public void onTransitionEnd(final Transition transition) {
        transitionsCount++;
        if (transitionsCount == TRANSITIONS_BEFORE_SWITCHING_THRESHOLD) {
            loadBackgroundImage();
        }
    }

    @Override
    public void onBubbleGrowingComplete() {
        loadBackgroundImage();
    }

    public void setupWithViewPager(final ViewPager viewPager) {
        if (viewPager != null) {
            if (viewPager.getAdapter() != null) {
                final PagerAdapter viewPagerAdapter = viewPager.getAdapter();
                if (viewPagerAdapter instanceof NewsStandHeaderViewConfiguratorAdapter) {
                    this.adapter = (NewsStandHeaderViewConfiguratorAdapter) viewPagerAdapter;
                    initializeViews();
                    viewPager.addOnPageChangeListener(this);
                } else {
                    throw new RuntimeException("Adapter must implement NewsStandHeaderViewConfiguratorAdapter");
                }
            }
        }
    }

    public void setImages(final int position, final List<String> contentUrlDTOs) {
        final long seed = System.nanoTime();
        Collections.shuffle(contentUrlDTOs, new Random(seed));
        headerViewImageReference.put(position, contentUrlDTOs);
    }

    private void initializeViews() {
        final int color = adapter.getColor(0);
        final Drawable icon = adapter.getIcon(0);

        colorsReference.put(0, color);
        iconsReference.put(0, icon);

        headerBubble.setBubbleBackground(color);
        headerBubble.setBubbleIcon(icon);

        headerBubble.nextTab(icon, color);

        loadFallBackBackground();

        currentImageIndex = 0;
    }

    private void loadFallBackBackground() {
        final Transformation tintTransformation = new ColorTintingGrayScaleTransformation(getColorByPosition(currentPageIndex));
        picasso.load(adapter.getFallbackDrawableResId(currentPageIndex))
                .transform(tintTransformation)
                .into(panningImage);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(final int position) {
        currentPageIndex = position;
        currentImageIndex = -1;

        final int color = getColorByPosition(position);
        final Drawable icon = getDrawableByPosition(position);

        headerBubble.nextTab(icon, color);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private int getColorByPosition(final int position) {
        int color;
        final Integer colorInt = colorsReference.get(position, -1);
        if (colorInt != -1) {
            color = colorsReference.get(position);
        } else {
            color = adapter.getColor(position);
            colorsReference.put(position, color);
        }
        return color;
    }

    private Drawable getDrawableByPosition(final int position) {
        Drawable drawable;
        final Drawable icon = iconsReference.get(position, null);
        if (icon != null) {
            drawable = iconsReference.get(position);
        } else {
            drawable = adapter.getIcon(position);
            iconsReference.put(position, drawable);
        }
        return drawable;
    }

    private void loadBackgroundImage() {
        if (headerViewImageReference.size() == 0
                || headerViewImageReference.get(currentPageIndex) == null
                || headerViewImageReference.get(currentPageIndex).size() == 0) {
            loadFallBackBackground();
            return;
        }

        if (headerViewImageReference.get(currentPageIndex).size() <= currentImageIndex + 1) {
            currentImageIndex = -1;
        }

        currentImageIndex++;
        final Transformation tintTransformation = new ColorTintingGrayScaleTransformation(getColorByPosition(currentPageIndex));
        picasso.load(headerViewImageReference.get(currentPageIndex).get(currentImageIndex))
                .transform(tintTransformation)
                .into(panningImage);

        transitionsCount = 0;
    }

    public interface NewsStandHeaderViewConfiguratorAdapter {
        int getColor(int position);

        Drawable getIcon(int position);

        int getFallbackDrawableResId(int position);
    }
}