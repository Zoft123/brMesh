package com.ashokvarma.bottomnavigation;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.graphics.drawable.DrawableCompat;

/* JADX INFO: loaded from: classes.dex */
abstract class BottomNavigationTab extends FrameLayout {
    protected BadgeItem badgeItem;
    BadgeTextView badgeView;
    View containerView;
    FrameLayout iconContainerView;
    ImageView iconView;
    boolean isActive;
    protected boolean isInActiveIconSet;
    protected boolean isNoTitleMode;
    TextView labelView;
    protected int mActiveColor;
    protected int mActiveWidth;
    protected int mBackgroundColor;
    protected Drawable mCompactIcon;
    protected Drawable mCompactInActiveIcon;
    protected int mInActiveColor;
    protected int mInActiveWidth;
    protected String mLabel;
    protected int mPosition;
    protected int paddingTopActive;
    protected int paddingTopInActive;

    protected abstract void setNoTitleIconContainerParams(FrameLayout.LayoutParams layoutParams);

    protected abstract void setNoTitleIconParams(FrameLayout.LayoutParams layoutParams);

    public BottomNavigationTab(Context context) {
        this(context, null);
    }

    public BottomNavigationTab(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public BottomNavigationTab(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.isInActiveIconSet = false;
        this.isActive = false;
        init();
    }

    public BottomNavigationTab(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.isInActiveIconSet = false;
        this.isActive = false;
        init();
    }

    void init() {
        setLayoutParams(new ViewGroup.LayoutParams(-2, -1));
    }

    public void setIsNoTitleMode(boolean z) {
        this.isNoTitleMode = z;
    }

    public boolean getIsNoTitleMode() {
        return this.isNoTitleMode;
    }

    public void setActiveWidth(int i) {
        this.mActiveWidth = i;
    }

    public void setInactiveWidth(int i) {
        this.mInActiveWidth = i;
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        layoutParams.width = this.mInActiveWidth;
        setLayoutParams(layoutParams);
    }

    public void setIcon(Drawable drawable) {
        this.mCompactIcon = DrawableCompat.wrap(drawable);
    }

    public void setInactiveIcon(Drawable drawable) {
        this.mCompactInActiveIcon = DrawableCompat.wrap(drawable);
        this.isInActiveIconSet = true;
    }

    public void setLabel(String str) {
        this.mLabel = str;
        this.labelView.setText(str);
    }

    public void setActiveColor(int i) {
        this.mActiveColor = i;
    }

    public int getActiveColor() {
        return this.mActiveColor;
    }

    public void setInactiveColor(int i) {
        this.mInActiveColor = i;
        this.labelView.setTextColor(i);
    }

    public void setItemBackgroundColor(int i) {
        this.mBackgroundColor = i;
    }

    public void setPosition(int i) {
        this.mPosition = i;
    }

    public void setBadgeItem(BadgeItem badgeItem) {
        this.badgeItem = badgeItem;
    }

    public int getPosition() {
        return this.mPosition;
    }

    public void select(boolean z, int i) {
        this.isActive = true;
        ValueAnimator valueAnimatorOfInt = ValueAnimator.ofInt(this.containerView.getPaddingTop(), this.paddingTopActive);
        valueAnimatorOfInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.ashokvarma.bottomnavigation.BottomNavigationTab.1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                BottomNavigationTab.this.containerView.setPadding(BottomNavigationTab.this.containerView.getPaddingLeft(), ((Integer) valueAnimator.getAnimatedValue()).intValue(), BottomNavigationTab.this.containerView.getPaddingRight(), BottomNavigationTab.this.containerView.getPaddingBottom());
            }
        });
        valueAnimatorOfInt.setDuration(i);
        valueAnimatorOfInt.start();
        this.iconView.setSelected(true);
        if (z) {
            this.labelView.setTextColor(this.mActiveColor);
        } else {
            this.labelView.setTextColor(this.mBackgroundColor);
        }
        BadgeItem badgeItem = this.badgeItem;
        if (badgeItem != null) {
            badgeItem.select();
        }
    }

    public void unSelect(boolean z, int i) {
        this.isActive = false;
        ValueAnimator valueAnimatorOfInt = ValueAnimator.ofInt(this.containerView.getPaddingTop(), this.paddingTopInActive);
        valueAnimatorOfInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.ashokvarma.bottomnavigation.BottomNavigationTab.2
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                BottomNavigationTab.this.containerView.setPadding(BottomNavigationTab.this.containerView.getPaddingLeft(), ((Integer) valueAnimator.getAnimatedValue()).intValue(), BottomNavigationTab.this.containerView.getPaddingRight(), BottomNavigationTab.this.containerView.getPaddingBottom());
            }
        });
        valueAnimatorOfInt.setDuration(i);
        valueAnimatorOfInt.start();
        this.labelView.setTextColor(this.mInActiveColor);
        this.iconView.setSelected(false);
        BadgeItem badgeItem = this.badgeItem;
        if (badgeItem != null) {
            badgeItem.unSelect();
        }
    }

    public void initialise(boolean z) {
        this.iconView.setSelected(false);
        if (this.isInActiveIconSet) {
            StateListDrawable stateListDrawable = new StateListDrawable();
            stateListDrawable.addState(new int[]{android.R.attr.state_selected}, this.mCompactIcon);
            stateListDrawable.addState(new int[]{-16842913}, this.mCompactInActiveIcon);
            stateListDrawable.addState(new int[0], this.mCompactInActiveIcon);
            this.iconView.setImageDrawable(stateListDrawable);
        } else {
            if (z) {
                Drawable drawable = this.mCompactIcon;
                int[][] iArr = {new int[]{android.R.attr.state_selected}, new int[]{-16842913}, new int[0]};
                int i = this.mActiveColor;
                int i2 = this.mInActiveColor;
                DrawableCompat.setTintList(drawable, new ColorStateList(iArr, new int[]{i, i2, i2}));
            } else {
                Drawable drawable2 = this.mCompactIcon;
                int[][] iArr2 = {new int[]{android.R.attr.state_selected}, new int[]{-16842913}, new int[0]};
                int i3 = this.mBackgroundColor;
                int i4 = this.mInActiveColor;
                DrawableCompat.setTintList(drawable2, new ColorStateList(iArr2, new int[]{i3, i4, i4}));
            }
            this.iconView.setImageDrawable(this.mCompactIcon);
        }
        if (this.isNoTitleMode) {
            this.labelView.setVisibility(8);
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.iconContainerView.getLayoutParams();
            layoutParams.gravity = 17;
            setNoTitleIconContainerParams(layoutParams);
            this.iconContainerView.setLayoutParams(layoutParams);
            FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) this.iconView.getLayoutParams();
            setNoTitleIconParams(layoutParams2);
            this.iconView.setLayoutParams(layoutParams2);
        }
    }
}
