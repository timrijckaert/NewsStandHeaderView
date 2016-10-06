package be.vrt.mobile.android.sporza.voetbal.ui.widget.slf;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
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
/**
 * Created by trijckaert on 08/04/16.
 */
public class HeaderIconView extends LinearLayout {
    private static final double CIRCULAR_REVEAL_COLOR_LIGHTENER_FACTOR = .1;
    private static final int MAX_BRIGHTNESS = 255;

    private final ImageView bubble;
    private final View circularReveal;

    private final GradientDrawable bubbleBackground;

    private TransitionListener listener;
    private AnimatorSet headerAnimation;

    //<editor-fold desc="Chaining Constructors">
    public HeaderIconView(final Context context) {
        this(context, null);
    }

    public HeaderIconView(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }
    //</editor-fold>

    public HeaderIconView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        final LayoutInflater layoutInflater = LayoutInflater.from(context);
        final View view = layoutInflater.inflate(R.layout.composite_header_image, this, true);

        bubble = (ImageView) view.findViewById(R.id.bubble);
        circularReveal = view.findViewById(R.id.circular_reveal);
        bubbleBackground = (GradientDrawable) bubble.getBackground().mutate();
    }

    //<editor-fold desc="Setters">
    public void setBubbleIcon(@NonNull final Drawable drawable) {
        bubble.setImageDrawable(drawable);
        bubbleBackground.invalidateSelf();
    }

    public void setBubbleBackground(@ColorInt final int color) {
        bubbleBackground.setColor(color);
        bubbleBackground.invalidateSelf();
    }

    public void setTransitionListener(final TransitionListener listener) {
        this.listener = listener;
    }
    //</editor-fold>

    public void nextTab(@DrawableRes final Drawable drawable,
                        @ColorInt final int color) {
        final AnimatorSet shrinkBubble = getShrinkBubbleAnimator(drawable, color);
        final AnimatorSet growAndReveal = getGrowBubbleAndRevealAnimator(color);
        final AnimatorSet fadeOutCircleReveal = getFadeOutCircleReveal();


        if (headerAnimation == null) {
            headerAnimation = new AnimatorSet();
            headerAnimation.playSequentially(shrinkBubble, growAndReveal, fadeOutCircleReveal);
            headerAnimation.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(final Animator animation) {
                    headerAnimation = null;
                }
            });
            headerAnimation.start();
        } else if (headerAnimation.isRunning()) {
            headerAnimation.cancel(); //internally calls onAnimationEnd() no infinite loop here
            nextTab(drawable, color);
        }
    }

    //<editor-fold desc="Animators">
    private AnimatorSet getGrowBubbleAndRevealAnimator(final int color) {
        final AnimatorSet growBubble = getGrowBubbleAnimator();
        final AnimatorSet revealAnimation = getRevealAnimator(color);

        AnimatorSet growAndRevealAnimation = new AnimatorSet();
        growAndRevealAnimation.playTogether(growBubble, revealAnimation);
        return growAndRevealAnimation;
    }

    private AnimatorSet getShrinkBubbleAnimator(final Drawable icon, final int color) {
        AnimatorSet shrink = (AnimatorSet) AnimatorInflater.loadAnimator(this.getContext(), R.animator.bubble_shrink);
        shrink.setTarget(bubble);
        shrink.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(final Animator animation) {
                setBubbleIcon(icon);
                setBubbleBackground(color);
            }
        });
        return shrink;
    }

    private AnimatorSet getGrowBubbleAnimator() {
        AnimatorSet grow = (AnimatorSet) AnimatorInflater.loadAnimator(this.getContext(), R.animator.bubble_grow);
        grow.setTarget(bubble);
        grow.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(final Animator animation) {
                fireTransitionEnd();
            }
        });
        return grow;
    }

    private AnimatorSet getRevealAnimator(final int color) {
        AnimatorSet reveal = (AnimatorSet) AnimatorInflater.loadAnimator(this.getContext(), R.animator.circular_reveal);
        reveal.setTarget(circularReveal);
        reveal.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(final Animator animation) {
                final GradientDrawable circularBackground = (GradientDrawable) circularReveal.getBackground().mutate();
                circularBackground.setColor(lighten(color, CIRCULAR_REVEAL_COLOR_LIGHTENER_FACTOR));
                circularReveal.setVisibility(VISIBLE);
            }
        });
        return reveal;
    }

    private AnimatorSet getFadeOutCircleReveal() {
        AnimatorSet fadeOut = (AnimatorSet) AnimatorInflater.loadAnimator(this.getContext(), R.animator.circular_reveal_fade_out);
        fadeOut.setTarget(circularReveal);
        fadeOut.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(final Animator animation) {
                circularReveal.setVisibility(GONE);
            }
        });
        return fadeOut;
    }
    //</editor-fold>

    //<editor-fold desc="Helpers">
    private int lighten(final int color, final double fraction) {
        final int lighterRed = lightenColor(Color.red(color), fraction);
        final int lighterGreen = lightenColor(Color.green(color), fraction);
        final int lighterBlue = lightenColor(Color.blue(color), fraction);
        return Color.argb(Color.alpha(color), lighterRed, lighterGreen, lighterBlue);
    }

    private int lightenColor(final int color, final double fraction) {
        return (int) Math.min(color + (color * fraction), MAX_BRIGHTNESS);
    }

    private void fireTransitionEnd() {
        if (listener != null) {
            listener.onBubbleGrowingComplete();
        }
    }
    //</editor-fold>

    interface TransitionListener {
        void onBubbleGrowingComplete();
    }
}