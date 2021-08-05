package com.san.sansample.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.san.sansample.R;

public class RectFrameLayout extends FrameLayout {

    private float mRatio = 1 / 1.91f; // 1.91(width) : 1(height)

    public RectFrameLayout(Context context) {
        this(context, null);
    }

    public RectFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RectFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.RectFrame);
        if (a != null) {
            mRatio = a.getFloat(0, 0.4f);
            a.recycle();
        }
    }

    public void setRatio(float ratio) {
        if (mRatio != ratio) {
            mRatio = ratio;
            invalidate();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mRatio <= 0) {
            super.onMeasure(widthMeasureSpec,heightMeasureSpec);
            return;
        }
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = (int) (MeasureSpec.getSize(widthMeasureSpec) * mRatio);
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(widthSize, MeasureSpec.EXACTLY);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize, MeasureSpec.EXACTLY);
        int count = getChildCount();
        int maxWidth = 0;
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (getConsiderGoneChildrenWhenMeasuring() || child.getVisibility() != View.GONE) {
                measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0);
                maxWidth = Math.max(maxWidth, child.getMeasuredWidth());
            }
        }
        Rect foregroundPadding = new Rect();
        if (getForeground() != null) {
            getForeground().getPadding(foregroundPadding);
        }
        maxWidth = maxWidth + getPaddingLeft() + getPaddingRight() + foregroundPadding.left + foregroundPadding.right;
        maxWidth = Math.max(maxWidth, getSuggestedMinimumWidth());

        Drawable drawable = getForeground();
        if (drawable != null) {
            maxWidth = Math.max(maxWidth, drawable.getMinimumWidth());
        }

        int measuredWidth = resolveSize(maxWidth, widthMeasureSpec);
        setMeasuredDimension(measuredWidth, (int) (measuredWidth * mRatio));
    }

}
