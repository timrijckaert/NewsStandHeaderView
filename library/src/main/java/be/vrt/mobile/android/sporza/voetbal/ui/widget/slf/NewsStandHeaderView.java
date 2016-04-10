package be.vrt.mobile.android.sporza.voetbal.ui.widget.slf;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by trijckaert on 09/04/16.
 */
public class NewsStandHeaderView extends FrameLayout {

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

    }
}