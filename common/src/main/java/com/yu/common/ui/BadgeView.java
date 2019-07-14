package com.yu.common.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.TabWidget;

import com.yu.common.R;

public class BadgeView extends AppCompatTextView {
    private boolean mHideOnNull = true;

    public BadgeView(Context context) {
        this(context, null);
    }

    public BadgeView(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.textViewStyle);
    }

    public BadgeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    private void init(AttributeSet attrs) {

        if (!(getLayoutParams() instanceof LayoutParams)) {
            LayoutParams layoutParams =
                    new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,
                            Gravity.LEFT | Gravity.TOP);
            setLayoutParams(layoutParams);
        }
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.BadgeView);
        if (typedArray != null) {
            int badgeBackground = typedArray.getColor(R.styleable.BadgeView_badge_text_background,
                    Color.parseColor("#EB96F4"));
            float badgeRadius = typedArray.getDimension(R.styleable.BadgeView_badge_radius, 9);
            setBackground(badgeRadius, badgeBackground);
            setGravity(Gravity.CENTER);
            setHideOnNull(true);
            setBadgeCount(0);
            typedArray.recycle();
        }
    }

    private void setBackground(float dipRadius, int badgeColor) {
        int radius = dip2Px(dipRadius);
        float[] radiusArray =
                new float[]{radius, radius, radius, radius, radius, radius, radius, radius};

        RoundRectShape roundRect = new RoundRectShape(radiusArray, null, null);
        ShapeDrawable bgDrawable = new ShapeDrawable(roundRect);
        bgDrawable.getPaint().setColor(badgeColor);
        setBackground(bgDrawable);
    }

    /**
     * 判断是否影藏
     */
    public boolean isHideOnNull() {
        return mHideOnNull;
    }

    /**
     * 设置影藏
     */
    public BadgeView setHideOnNull(boolean hideOnNull) {
        mHideOnNull = hideOnNull;
        setText(getText());
        return this;
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        if (isHideOnNull() && (text == null || text.toString().equalsIgnoreCase("0"))) {
            setVisibility(View.GONE);
        } else {
            setVisibility(View.VISIBLE);
        }
        super.setText(text, type);
    }

    public BadgeView setBadgeCount(int count) {
        setHideOnNull(count == 0);
        setText(String.valueOf(count));
        return this;
    }

    public BadgeView setBadgeCount(int count, String other) {
        setHideOnNull(count == 0);
        setText(count > 99 ? count + other : String.valueOf(count));
        return this;
    }

    public void setBadgeGravity(int gravity) {
        LayoutParams params = (LayoutParams) getLayoutParams();
        params.gravity = gravity;
        setLayoutParams(params);
    }

    public int getBadgeGravity() {
        LayoutParams params = (LayoutParams) getLayoutParams();
        return params.gravity;
    }

    public void setBadgeMargin(int dipMargin) {
        setBadgeMargin(dipMargin, dipMargin, dipMargin, dipMargin);
    }

    public void setBadgeMargin(int leftDipMargin, int topDipMargin, int rightDipMargin,
                               int bottomDipMargin) {
        LayoutParams params = (LayoutParams) getLayoutParams();
        params.leftMargin = dip2Px(leftDipMargin);
        params.topMargin = dip2Px(topDipMargin);
        params.rightMargin = dip2Px(rightDipMargin);
        params.bottomMargin = dip2Px(bottomDipMargin);
        setLayoutParams(params);
    }

    public int[] getBadgeMargin() {
        LayoutParams params = (LayoutParams) getLayoutParams();
        return new int[]{
                params.leftMargin, params.topMargin, params.rightMargin, params.bottomMargin
        };
    }

    public void setTargetView(TabWidget target, int tabIndex) {
        View tabView = target.getChildTabViewAt(tabIndex);
        setTargetView(tabView);
    }

    public void setTargetView(View target) {
        if (getParent() != null) {
            ((ViewGroup) getParent()).removeView(this);
        }

        if (target == null) {
            return;
        }

        if (target.getParent() instanceof FrameLayout) {
            ((FrameLayout) target.getParent()).addView(this);
        } else if (target.getParent() instanceof ViewGroup) {
            // use a new Framelayout container for adding badge
            ViewGroup parentContainer = (ViewGroup) target.getParent();
            int groupIndex = parentContainer.indexOfChild(target);
            parentContainer.removeView(target);

            FrameLayout badgeContainer = new FrameLayout(getContext());
            ViewGroup.LayoutParams parentLayoutParams = target.getLayoutParams();

            badgeContainer.setLayoutParams(parentLayoutParams);
            target.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));

            parentContainer.addView(badgeContainer, groupIndex, parentLayoutParams);
            badgeContainer.addView(target);

            badgeContainer.addView(this);
        } else if (target.getParent() == null) {
            Log.e(getClass().getSimpleName(), "ParentView is needed");
        }
    }

    private int dip2Px(float dip) {
        return (int) (dip * getContext().getResources().getDisplayMetrics().density + 0.5f);
    }
}