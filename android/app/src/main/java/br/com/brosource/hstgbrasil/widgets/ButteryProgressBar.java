package br.com.brosource.hstgbrasil.widgets;

/**
 * Created by haroldo on 9/19/14.
 */

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Interpolator;

import br.com.brosource.hstgbrasil.R;

public class ButteryProgressBar extends View {
    /**
     * The baseline width that the other constants below are optimized for.
     */
    private static final int BASE_WIDTH_DP = 300;
    /**
     * A reasonable animation duration for the given width above. It will be weakly scaled up and
     * down for wider and narrower widths, respectively-- the goal is to provide a relatively
     * constant detent velocity.
     */
    private static final int BASE_DURATION_MS = 500;
    /**
     * A reasonable number of detents for the given width above. It will be weakly scaled up and
     * down for wider and narrower widths, respectively.
     */
    private static final int BASE_SEGMENT_COUNT = 5;
    private static final int DEFAULT_BAR_HEIGHT_DP = 4;
    private static final int DEFAULT_DETENT_WIDTH_DP = 3;
    private final GradientDrawable mShadow;
    private final ValueAnimator mAnimator;
    private final Paint mPaint = new Paint();
    private final int mBarColor;
    private final int mSolidBarHeight;
    private final int mSolidBarDetentWidth;
    private final float mDensity;
    private int mSegmentCount;

    public ButteryProgressBar(Context c) {
        this(c, null);
    }

    public ButteryProgressBar(Context c, AttributeSet attrs) {
        super(c, attrs);
        mDensity = c.getResources().getDisplayMetrics().density;
        mBarColor = c.getResources().getColor(R.color.azul);
        mSolidBarHeight = Math.round(DEFAULT_BAR_HEIGHT_DP * mDensity);
        mSolidBarDetentWidth = Math.round(DEFAULT_DETENT_WIDTH_DP * mDensity);
        mAnimator = new ValueAnimator();
        mAnimator.setFloatValues(1.0f, 2.0f);
        mAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mAnimator.setInterpolator(new ExponentialInterpolator());
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                invalidate();
            }
        });
        mPaint.setColor(mBarColor);
        mShadow = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[]{(mBarColor & 0x00ffffff) | 0x22000000, 0});
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        if (changed) {
            final int w = getWidth();
            mShadow.setBounds(0, mSolidBarHeight, w, getHeight() - mSolidBarHeight);
            final float widthMultiplier = w / mDensity / BASE_WIDTH_DP;

            final float durationMult = 0.3f * (widthMultiplier - 1) + 1;
            final float segmentMult = 0.1f * (widthMultiplier - 1) + 1;
            mAnimator.setDuration((int) (BASE_DURATION_MS * durationMult));
            mSegmentCount = (int) (BASE_SEGMENT_COUNT * segmentMult);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (!mAnimator.isStarted()) {
            return;
        }
        mShadow.draw(canvas);
        final float val = (Float) mAnimator.getAnimatedValue();
        final int w = getWidth();
        final int offset = w >> mSegmentCount - 1;
        for (int i = 0; i < mSegmentCount; i++) {
            final float l = val * (w >> (i + 1));
            final float r = (i == 0) ? w + offset : l * 2;
            canvas.drawRect(l + mSolidBarDetentWidth - offset, 0, r - offset, mSolidBarHeight, mPaint);
        }
    }

    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (visibility == VISIBLE) {
            start();
        } else {
            stop();
        }
    }

    private void start() {
        if (mAnimator == null) {
            return;
        }
        mAnimator.start();
    }

    private void stop() {
        if (mAnimator == null) {
            return;
        }
        mAnimator.cancel();
    }

    private static class ExponentialInterpolator implements Interpolator {
        @Override
        public float getInterpolation(float input) {
            return (float) Math.pow(2.0, input) - 1;
        }
    }
}

