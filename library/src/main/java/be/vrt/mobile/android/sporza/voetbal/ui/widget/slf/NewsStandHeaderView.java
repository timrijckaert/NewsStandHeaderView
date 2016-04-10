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

import java.util.HashMap;

/**
 * Created by trijckaert on 09/04/16.
 */
public class NewsStandHeaderView extends FrameLayout implements ViewPager.OnPageChangeListener {

    private final PanningImageView panningImage;
    private final HeaderIconView headerBubble;
    private final HashMap<Integer, Drawable> icons = new HashMap<>();
    private final HashMap<Integer, Integer> colors = new HashMap<>();

    private NewsStandHeaderViewConfiguratorAdapter adapter;

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
        final LayoutInflater layoutInflater = LayoutInflater.from(context);
        final View view = layoutInflater.inflate(R.layout.composite_newsstand_header, this, true);

        panningImage = (PanningImageView) view.findViewById(R.id.panningImage);
        headerBubble = (HeaderIconView) view.findViewById(R.id.headerBubble);
    }

    public void setupWithViewPager(final ViewPager viewPager) {
        if (viewPager != null) {
            viewPager.addOnPageChangeListener(this);

            if (viewPager.getAdapter() != null) {
                final PagerAdapter adapter = viewPager.getAdapter();
                if (adapter instanceof NewsStandHeaderViewConfiguratorAdapter) {
                    this.adapter = (NewsStandHeaderViewConfiguratorAdapter) adapter;
                    initializeHeaderBubble();
                } else {
                    throw new RuntimeException("Adapter must implement NewsStandHeaderViewConfiguratorAdapter");
                }
            }
        }
    }

    private void initializeHeaderBubble() {
        final int color = adapter.getColor(0);
        final Drawable icon = adapter.getIcon(0);
        colors.put(0, color);
        icons.put(0, icon);

        headerBubble.setBubbleBackground(color);
        headerBubble.setBubbleIcon(icon);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

    @Override
    public void onPageSelected(int position) {
        final int color = getColorByPosition(position);
        final Drawable icon = getDrawableByPosition(position);

        headerBubble.nextTab(icon, color);
    }

    private int getColorByPosition(final int position) {
        int color;
        if (colors.containsValue(position)) {
            color = colors.get(position);
        } else {
            color = adapter.getColor(position);
            colors.put(position, color);
        }
        return color;
    }

    private Drawable getDrawableByPosition(final int position) {
        Drawable drawable;
        if (icons.containsValue(position)){
            drawable = icons.get(position);
        }else{
            drawable = adapter.getIcon(position);
            icons.put(position, drawable);
        }
        return drawable;
    }

    @Override
    public void onPageScrollStateChanged(int state) {}

    public interface NewsStandHeaderViewConfiguratorAdapter {
        int getColor(final int position);

        Drawable getIcon(final int position);
    }
}