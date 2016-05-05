package be.vrt.mobile.android.sporza.voetbal.ui.widget.slf;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by trijckaert on 12/04/16.
 */
public class SquaredView extends ImageView {
    public SquaredView(final Context context) {
        super(context);
    }

    public SquaredView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        final int orientation = Resources.getSystem().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth());
        } else {
            setMeasuredDimension(getMeasuredHeight(), getMeasuredHeight());
        }
    }
}