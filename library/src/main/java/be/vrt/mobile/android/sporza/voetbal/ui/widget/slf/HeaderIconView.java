package be.vrt.mobile.android.sporza.voetbal.ui.widget.slf;

import android.animation.Animator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.flaviofaria.kenburnsview.R;

/**
 * Created by trijckaert on 08/04/16.
 */
public class HeaderIconView extends LinearLayout {

    private final ImageView bubble;
    private final GradientDrawable bubbleBackground;
    private final static int SHRINK_ANIMATION_DURATION = 300;

    //<editor-fold desc="Constructors">
    public HeaderIconView(final Context context) {
        this(context, null);
    }

    public HeaderIconView(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HeaderIconView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }
    //</editor-fold>

    public HeaderIconView(final Context context, final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        final LayoutInflater layoutInflater = LayoutInflater.from(context);
        final View view = layoutInflater.inflate(R.layout.composite_header_image, this, true);

        bubble = (ImageView) view.findViewById(R.id.bubble);
        bubbleBackground = (GradientDrawable) bubble.getBackground().mutate();
    }

    public void setBubbleIcon(@DrawableRes final int iconResId) {
        bubble.setImageResource(iconResId);
        bubbleBackground.invalidateSelf();
    }

    public void setBubbleBackground(@ColorInt final int color) {
        bubbleBackground.setColor(color);
        bubbleBackground.invalidateSelf();
    }

    public void nextTab(@DrawableRes final int iconResId, @ColorInt final int color) {
        shrinkBubble(iconResId, color);
    }

    private void shrinkBubble(final int iconResId, final int color) {
        bubble.animate()
                .scaleX(0f)
                .scaleY(0f)
                .setDuration(SHRINK_ANIMATION_DURATION)
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        setBubbleIcon(iconResId);
                        setBubbleBackground(color);
                        growBubble();

                        //revealAnimation(color)
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                })
                .start();
    }

    private void growBubble() {
        bubble.animate()
                .scaleX(1f)
                .scaleY(1f)
                .setDuration(SHRINK_ANIMATION_DURATION)
                .start();
    }
}