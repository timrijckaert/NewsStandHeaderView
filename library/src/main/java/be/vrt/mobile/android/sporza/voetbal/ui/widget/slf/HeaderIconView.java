package be.vrt.mobile.android.sporza.voetbal.ui.widget.slf;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
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

    private final static int SHRINK_ANIMATION_DURATION = 350;
    private final static int CIRCULAR_SCALE_ANIMATION_DURATION = 550;
    private static final int CIRCULAR_FADE_OUT_ANIMATION_DURATION = 600;

    private final ImageView bubble;
    private final View circularReveal;

    private final GradientDrawable bubbleBackground;
    private TransitionListener listener;

    //<editor-fold desc="Chaining Constructors">
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
        circularReveal = view.findViewById(R.id.circular_reveal);
        bubbleBackground = (GradientDrawable) bubble.getBackground().mutate();
    }

    public void setBubbleIcon(@NonNull final Drawable drawable) {
        bubble.setImageDrawable(drawable);
        bubbleBackground.invalidateSelf();
    }

    public void setBubbleBackground(@ColorInt final int color) {
        bubbleBackground.setColor(color);
        bubbleBackground.invalidateSelf();
    }

    public void setTransitionListener(TransitionListener listener) {
        this.listener = listener;
    }

    public void nextTab(@DrawableRes final Drawable drawable, @ColorInt final int color) {
        shrinkBubble(drawable, color);
    }

    private void shrinkBubble(final Drawable icon, final int color) {
        bubble.animate()
                .scaleX(0f)
                .scaleY(0f)
                .setDuration(SHRINK_ANIMATION_DURATION)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(final Animator animation) {
                        setBubbleIcon(icon);
                        setBubbleBackground(color);
                        growBubble();

                        revealAnimation(color);
                    }
                })
                .start();
    }

    private void growBubble() {
        bubble.animate()
                .scaleX(1f)
                .scaleY(1f)
                .setDuration(SHRINK_ANIMATION_DURATION)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        fireTransitionEnd();
                    }
                })
                .start();
    }

    private void fireTransitionEnd() {
        if (listener != null) {
            listener.onBubbleGrowingComplete();
        }
    }

    private void revealAnimation(final int color) {
        final GradientDrawable circularBackground = (GradientDrawable) circularReveal.getBackground().mutate();
        circularBackground.setColor(lighten(color, .1));
        circularReveal.setAlpha(0.5f);
        circularReveal.setVisibility(VISIBLE);

        circularReveal.animate()
                .scaleX(10f)
                .scaleY(10f)
                .alpha(0.9f)
                .setDuration(CIRCULAR_SCALE_ANIMATION_DURATION)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        fadeOutCircleReveal();
                    }
                })
                .start();
    }

    private void fadeOutCircleReveal() {
        circularReveal.animate()
                .alpha(1f)
                .alpha(0f)
                .setDuration(CIRCULAR_FADE_OUT_ANIMATION_DURATION)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        circularReveal.setScaleX(0);
                        circularReveal.setScaleY(0);
                        circularReveal.setVisibility(GONE);
                    }
                })
                .start();
    }

    private int lighten(final int color, final double fraction) {
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        red = lightenColor(red, fraction);
        green = lightenColor(green, fraction);
        blue = lightenColor(blue, fraction);
        int alpha = Color.alpha(color);
        return Color.argb(alpha, red, green, blue);
    }

    private int lightenColor(final int color, final double fraction) {
        return (int) Math.min(color + (color * fraction), 255);
    }

    interface TransitionListener {
        void onBubbleGrowingComplete();
    }
}