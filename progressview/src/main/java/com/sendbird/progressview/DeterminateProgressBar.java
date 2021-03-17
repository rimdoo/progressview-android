package com.sendbird.progressview;

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.BlendMode;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;

import static java.lang.Math.min;

public class DeterminateProgressBar extends View {

    private int min;
    private int max;
    private float progress;
    private int secondaryProgress;
    private boolean indeterminate;
    private boolean onlyIndeterminate;
    private ValueAnimator animator;
    private long duration;
    private long startDelay;
    private TimeInterpolator interpolator = new LinearInterpolator();
    private int behavior;
    private int minWidth;
    private int maxWidth;
    private int minHeight;
    private int maxHeight;
    private Drawable progressDrawable;
    private final RectF rectF = new RectF();
    private Paint backgroundPaint = new Paint();
    private Paint progressPaint = new Paint();
    private float barWidth = 100;

    public DeterminateProgressBar(Context context) {
        this(context, null);
    }

    public DeterminateProgressBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, R.attr.progressBarStyle);
    }

    public DeterminateProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initProgressBar();

        final TypedArray a = context.obtainStyledAttributes(
                attrs, R.styleable.ProgressView, defStyleAttr, 0);

        backgroundPaint.setColor(Color.rgb(241, 0, 0));
        backgroundPaint.setStrokeWidth(barWidth);
        backgroundPaint.setStyle(Paint.Style.STROKE);
        backgroundPaint.setAntiAlias(true);
        progressPaint.setColor(Color.argb(100, 255, 200, 0));
        progressPaint.setStrokeWidth(barWidth);
        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setAntiAlias(true);
    }

//    public DeterminateProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        int height = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
        int min = min(width, height);
        setMeasuredDimension(min, min);
        rectF.set(0 + barWidth/2, 0 + barWidth/2, min - barWidth/2, min - barWidth/2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawOval(rectF, backgroundPaint);
        canvas.drawArc(rectF, 270f, (progress * 360f) / 100f, false, progressPaint);
    }

    public void setProgress(int progress) {
        if (animator != null) {
            animator.cancel();
            animator = null;
        }
        animator = new ValueAnimator();
        animator.setFloatValues(this.progress, progress);
        animator.setDuration(duration);
        animator.setInterpolator(interpolator);
        animator.setStartDelay(startDelay);
        animator.setRepeatMode(behavior);
        animator.addUpdateListener(animation -> {
            DeterminateProgressBar.this.progress = (float) animation.getAnimatedValue();
            invalidate();
        });
        animator.start();
    }

    private void initProgressBar() {
        min = 0;
        max = 100;
        progress = 0;
        secondaryProgress = 0;
        indeterminate = false;
        onlyIndeterminate = false;
        duration = 1000;
        startDelay = 0;
        behavior = AlphaAnimation.RESTART;
        minWidth = 24;
        maxWidth = 48;
        minHeight = 24;
        maxHeight = 48;
    }

    private static class ProgressTintInfo {
        ColorStateList indeterminateTintList;
        BlendMode indeterminateBlendMode;
        boolean hasIndeterminateTint;
        boolean hasIndeterminateTintMode;

        ColorStateList progressTintList;
        BlendMode progressBlendMode;
        boolean hasProgressTint;
        boolean hasProgressTintMode;

        ColorStateList progressBackgroundTintList;
        BlendMode progressBackgroundBlendMode;
        boolean hasProgressBackgroundTint;
        boolean hasProgressBackgroundTintMode;

        ColorStateList secondaryProgressTintList;
        BlendMode secondaryProgressBlendMode;
        boolean hasSecondaryProgressTint;
        boolean hasSecondaryProgressTintMode;
    }
}
