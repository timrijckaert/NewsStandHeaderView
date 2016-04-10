package be.vrt.mobile.android.sporza.voetbal.ui.widget.slf;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.flaviofaria.kenburnsview.R;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

/**
 * Created by trijckaert on 09/04/16.
 */
public class NewsStandHeaderView extends FrameLayout implements ViewPager.OnPageChangeListener, PanningImageView.TransitionListener {

    private final PanningImageView panningImage;
    private final HeaderIconView headerBubble;
    private final HashMap<Integer, Drawable> iconsReferenceMap = new HashMap<>();
    private final HashMap<Integer, Integer> colorsReferenceMap = new HashMap<>();
    private final Picasso picasso;
    private List<String> imageURLs;

    private NewsStandHeaderViewConfiguratorAdapter adapter;
    private int currentImageIndex = 0;

    //<editor-fold desc="Chaining Constructors">
    public NewsStandHeaderView(final Context context) {
        this(context, null);
    }

    public NewsStandHeaderView(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NewsStandHeaderView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }
    //</editor-fold>

    public NewsStandHeaderView(final Context context, final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        picasso = Picasso.with(getContext());
        final LayoutInflater layoutInflater = LayoutInflater.from(context);
        final View view = layoutInflater.inflate(R.layout.composite_newsstand_header, this, true);

        panningImage = (PanningImageView) view.findViewById(R.id.panningImage);
        headerBubble = (HeaderIconView) view.findViewById(R.id.headerBubble);

        panningImage.setTransitionListener(this);
    }

    public void setupWithViewPager(final ViewPager viewPager) {
        if (viewPager != null) {
            viewPager.addOnPageChangeListener(this);

            if (viewPager.getAdapter() != null) {
                final PagerAdapter adapter = viewPager.getAdapter();
                if (adapter instanceof NewsStandHeaderViewConfiguratorAdapter) {
                    this.adapter = (NewsStandHeaderViewConfiguratorAdapter) adapter;
                    initializeViews();
                } else {
                    throw new RuntimeException("Adapter must implement NewsStandHeaderViewConfiguratorAdapter");
                }
            }
        }
    }

    private void initializeViews() {
        final int color = adapter.getColor(0);
        final Drawable icon = adapter.getIcon(0);
        imageURLs = adapter.getBackgroundImages(0);

        colorsReferenceMap.put(0, color);
        iconsReferenceMap.put(0, icon);

        headerBubble.setBubbleBackground(color);
        headerBubble.setBubbleIcon(icon);

        if (imageURLs.size() > 0) {
            picasso.load(imageURLs.get(0))
                    .error(R.drawable.ic_error_bg)
                    .into(panningImage);
            currentImageIndex = 0;
        }
    }

    @Override
    public void onPageScrolled(final int position, final float positionOffset, final int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(final int position) {
        final int color = getColorByPosition(position);
        final Drawable icon = getDrawableByPosition(position);

        headerBubble.nextTab(icon, color);
    }

    private int getColorByPosition(final int position) {
        int color;
        if (colorsReferenceMap.containsValue(position)) {
            color = colorsReferenceMap.get(position);
        } else {
            color = adapter.getColor(position);
            colorsReferenceMap.put(position, color);
        }
        return color;
    }

    private Drawable getDrawableByPosition(final int position) {
        Drawable drawable;
        if (iconsReferenceMap.containsValue(position)) {
            drawable = iconsReferenceMap.get(position);
        } else {
            drawable = adapter.getIcon(position);
            iconsReferenceMap.put(position, drawable);
        }
        return drawable;
    }

    @Override
    public void onPageScrollStateChanged(final int state) {
    }

    @Override
    public void onTransitionStart(final Transition transition) {
    }

    @Override
    public void onTransitionEnd(final Transition transition) {
        currentImageIndex++;
        picasso.load(imageURLs.get(currentImageIndex))
                .error(R.drawable.ic_error_bg)
                .into(panningImage);
    }

    public interface NewsStandHeaderViewConfiguratorAdapter extends ImageProvidable {
        int getColor(final int position);

        Drawable getIcon(final int position);
    }
}