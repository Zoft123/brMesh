package com.ashokvarma.bottomnavigation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewAnimationUtils;

/* JADX INFO: loaded from: classes.dex */
class BottomNavigationHelper {
    private BottomNavigationHelper() {
    }

    static int[] getMeasurementsForFixedMode(Context context, int i, int i2, boolean z) {
        int[] iArr = new int[2];
        int dimension = (int) context.getResources().getDimension(R.dimen.fixed_min_width_small_views);
        int dimension2 = (int) context.getResources().getDimension(R.dimen.fixed_min_width);
        int i3 = i / i2;
        if (i3 < dimension && z) {
            dimension2 = (int) context.getResources().getDimension(R.dimen.fixed_min_width);
        } else if (i3 <= dimension2) {
            dimension2 = i3;
        }
        iArr[0] = dimension2;
        return iArr;
    }

    static int[] getMeasurementsForShiftingMode(Context context, int i, int i2, boolean z) {
        int i3;
        int i4;
        double d;
        int dimension = (int) context.getResources().getDimension(R.dimen.shifting_min_width_inactive);
        int dimension2 = (int) context.getResources().getDimension(R.dimen.shifting_max_width_inactive);
        double d2 = dimension;
        double d3 = i2;
        double d4 = 0.5d + d3;
        double d5 = dimension2;
        double d6 = 0.75d + d3;
        double d7 = d5 * d6;
        double d8 = i;
        if (d8 < d2 * d4) {
            if (z) {
                i4 = (int) (d2 * 1.5d);
                i3 = dimension;
            } else {
                i3 = (int) (d8 / d4);
                d = ((double) i3) * 1.5d;
                i4 = (int) d;
            }
        } else if (d8 > d7) {
            i4 = (int) (d5 * 1.75d);
            i3 = dimension2;
        } else {
            double d9 = d3 + 0.625d;
            double d10 = d2 * d9;
            double d11 = d2 * d6;
            int i5 = (int) (d8 / d4);
            int i6 = (int) (((double) i5) * 1.5d);
            if (d8 > d10) {
                int i7 = (int) (d8 / d9);
                int i8 = (int) (((double) i7) * 1.625d);
                if (d8 > d11) {
                    i3 = (int) (d8 / d6);
                    d = ((double) i3) * 1.75d;
                    i4 = (int) d;
                } else {
                    i3 = i7;
                    i4 = i8;
                }
            } else {
                i3 = i5;
                i4 = i6;
            }
        }
        return new int[]{i3, i4};
    }

    static void bindTabWithData(BottomNavigationItem bottomNavigationItem, BottomNavigationTab bottomNavigationTab, BottomNavigationBar bottomNavigationBar) {
        Drawable inactiveIcon;
        Context context = bottomNavigationBar.getContext();
        bottomNavigationTab.setLabel(bottomNavigationItem.getTitle(context));
        bottomNavigationTab.setIcon(bottomNavigationItem.getIcon(context));
        int activeColor = bottomNavigationItem.getActiveColor(context);
        int inActiveColor = bottomNavigationItem.getInActiveColor(context);
        if (activeColor != 0) {
            bottomNavigationTab.setActiveColor(activeColor);
        } else {
            bottomNavigationTab.setActiveColor(bottomNavigationBar.getActiveColor());
        }
        if (inActiveColor != 0) {
            bottomNavigationTab.setInactiveColor(inActiveColor);
        } else {
            bottomNavigationTab.setInactiveColor(bottomNavigationBar.getInActiveColor());
        }
        if (bottomNavigationItem.isInActiveIconAvailable() && (inactiveIcon = bottomNavigationItem.getInactiveIcon(context)) != null) {
            bottomNavigationTab.setInactiveIcon(inactiveIcon);
        }
        bottomNavigationTab.setItemBackgroundColor(bottomNavigationBar.getBackgroundColor());
        BadgeItem badgeItem = bottomNavigationItem.getBadgeItem();
        if (badgeItem != null) {
            badgeItem.bindToBottomTab(bottomNavigationTab);
        }
    }

    static void setBackgroundWithRipple(View view, final View view2, final View view3, final int i, int i2) {
        int x = (int) (view.getX() + (view.getMeasuredWidth() / 2));
        int measuredHeight = view.getMeasuredHeight() / 2;
        int width = view2.getWidth();
        view2.clearAnimation();
        view3.clearAnimation();
        Animator animatorCreateCircularReveal = ViewAnimationUtils.createCircularReveal(view3, x, measuredHeight, 0.0f, width);
        animatorCreateCircularReveal.setDuration(i2);
        animatorCreateCircularReveal.addListener(new AnimatorListenerAdapter() { // from class: com.ashokvarma.bottomnavigation.BottomNavigationHelper.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                onCancel();
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
                onCancel();
            }

            private void onCancel() {
                view2.setBackgroundColor(i);
                view3.setVisibility(8);
            }
        });
        view3.setBackgroundColor(i);
        view3.setVisibility(0);
        animatorCreateCircularReveal.start();
    }
}
