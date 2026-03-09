package com.ashokvarma.bottomnavigation;

import android.view.View;
import android.widget.FrameLayout;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;
import androidx.core.view.ViewPropertyAnimatorListener;
import androidx.recyclerview.widget.ItemTouchHelper;
import com.ashokvarma.bottomnavigation.BadgeItem;
import com.google.android.material.badge.BadgeDrawable;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes.dex */
abstract class BadgeItem<T extends BadgeItem<T>> {
    private boolean mHideOnSelect;
    private WeakReference<BadgeTextView> mTextViewRef;
    private int mGravity = BadgeDrawable.TOP_END;
    private boolean mIsHidden = false;
    private int mAnimationDuration = ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION;

    abstract void bindToBottomTabInternal(BottomNavigationTab bottomNavigationTab);

    abstract T getSubInstance();

    BadgeItem() {
    }

    public T setGravity(int i) {
        this.mGravity = i;
        if (isWeakReferenceValid()) {
            BadgeTextView badgeTextView = this.mTextViewRef.get();
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) badgeTextView.getLayoutParams();
            layoutParams.gravity = i;
            badgeTextView.setLayoutParams(layoutParams);
        }
        return (T) getSubInstance();
    }

    public T setHideOnSelect(boolean z) {
        this.mHideOnSelect = z;
        return (T) getSubInstance();
    }

    public T setAnimationDuration(int i) {
        this.mAnimationDuration = i;
        return (T) getSubInstance();
    }

    void bindToBottomTab(BottomNavigationTab bottomNavigationTab) {
        bottomNavigationTab.badgeView.clearPrevious();
        if (bottomNavigationTab.badgeItem != null) {
            bottomNavigationTab.badgeItem.setTextView(null);
        }
        bottomNavigationTab.setBadgeItem(this);
        setTextView(bottomNavigationTab.badgeView);
        bindToBottomTabInternal(bottomNavigationTab);
        bottomNavigationTab.badgeView.setVisibility(0);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) bottomNavigationTab.badgeView.getLayoutParams();
        layoutParams.gravity = getGravity();
        bottomNavigationTab.badgeView.setLayoutParams(layoutParams);
        if (isHidden()) {
            hide();
        }
    }

    private T setTextView(BadgeTextView badgeTextView) {
        this.mTextViewRef = new WeakReference<>(badgeTextView);
        return (T) getSubInstance();
    }

    int getGravity() {
        return this.mGravity;
    }

    boolean isHideOnSelect() {
        return this.mHideOnSelect;
    }

    WeakReference<BadgeTextView> getTextView() {
        return this.mTextViewRef;
    }

    boolean isWeakReferenceValid() {
        WeakReference<BadgeTextView> weakReference = this.mTextViewRef;
        return (weakReference == null || weakReference.get() == null) ? false : true;
    }

    void select() {
        if (this.mHideOnSelect) {
            hide(true);
        }
    }

    void unSelect() {
        if (this.mHideOnSelect) {
            show(true);
        }
    }

    public T toggle() {
        return (T) toggle(true);
    }

    public T toggle(boolean z) {
        if (this.mIsHidden) {
            return (T) show(z);
        }
        return (T) hide(z);
    }

    public T show() {
        return (T) show(true);
    }

    public T show(boolean z) {
        this.mIsHidden = false;
        if (isWeakReferenceValid()) {
            BadgeTextView badgeTextView = this.mTextViewRef.get();
            if (z) {
                badgeTextView.setScaleX(0.0f);
                badgeTextView.setScaleY(0.0f);
                badgeTextView.setVisibility(0);
                ViewPropertyAnimatorCompat viewPropertyAnimatorCompatAnimate = ViewCompat.animate(badgeTextView);
                viewPropertyAnimatorCompatAnimate.cancel();
                viewPropertyAnimatorCompatAnimate.setDuration(this.mAnimationDuration);
                viewPropertyAnimatorCompatAnimate.scaleX(1.0f).scaleY(1.0f);
                viewPropertyAnimatorCompatAnimate.setListener(null);
                viewPropertyAnimatorCompatAnimate.start();
            } else {
                badgeTextView.setScaleX(1.0f);
                badgeTextView.setScaleY(1.0f);
                badgeTextView.setVisibility(0);
            }
        }
        return (T) getSubInstance();
    }

    public T hide() {
        return (T) hide(true);
    }

    public T hide(boolean z) {
        this.mIsHidden = true;
        if (isWeakReferenceValid()) {
            BadgeTextView badgeTextView = this.mTextViewRef.get();
            if (z) {
                ViewPropertyAnimatorCompat viewPropertyAnimatorCompatAnimate = ViewCompat.animate(badgeTextView);
                viewPropertyAnimatorCompatAnimate.cancel();
                viewPropertyAnimatorCompatAnimate.setDuration(this.mAnimationDuration);
                viewPropertyAnimatorCompatAnimate.scaleX(0.0f).scaleY(0.0f);
                viewPropertyAnimatorCompatAnimate.setListener(new ViewPropertyAnimatorListener() { // from class: com.ashokvarma.bottomnavigation.BadgeItem.1
                    @Override // androidx.core.view.ViewPropertyAnimatorListener
                    public void onAnimationStart(View view) {
                    }

                    @Override // androidx.core.view.ViewPropertyAnimatorListener
                    public void onAnimationEnd(View view) {
                        view.setVisibility(8);
                    }

                    @Override // androidx.core.view.ViewPropertyAnimatorListener
                    public void onAnimationCancel(View view) {
                        view.setVisibility(8);
                    }
                });
                viewPropertyAnimatorCompatAnimate.start();
            } else {
                badgeTextView.setVisibility(8);
            }
        }
        return (T) getSubInstance();
    }

    public boolean isHidden() {
        return this.mIsHidden;
    }
}
