package com.ashokvarma.bottomnavigation.behaviour;

import android.view.View;
import android.view.animation.Interpolator;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class BottomNavBarFabBehaviour extends CoordinatorLayout.Behavior<FloatingActionButton> {
    static final Interpolator FAST_OUT_SLOW_IN_INTERPOLATOR = new FastOutSlowInInterpolator();
    ViewPropertyAnimatorCompat mFabTranslationYAnimator;

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public boolean layoutDependsOn(CoordinatorLayout coordinatorLayout, FloatingActionButton floatingActionButton, View view) {
        return isDependent(view) || super.layoutDependsOn(coordinatorLayout, floatingActionButton, view);
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public boolean onLayoutChild(CoordinatorLayout coordinatorLayout, FloatingActionButton floatingActionButton, int i) {
        coordinatorLayout.onLayoutChild(floatingActionButton, i);
        updateFabTranslationForBottomNavigationBar(coordinatorLayout, floatingActionButton, null);
        return super.onLayoutChild(coordinatorLayout, floatingActionButton, i);
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public boolean onDependentViewChanged(CoordinatorLayout coordinatorLayout, FloatingActionButton floatingActionButton, View view) {
        if (isDependent(view)) {
            updateFabTranslationForBottomNavigationBar(coordinatorLayout, floatingActionButton, view);
            return false;
        }
        return super.onDependentViewChanged(coordinatorLayout, floatingActionButton, view);
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public void onDependentViewRemoved(CoordinatorLayout coordinatorLayout, FloatingActionButton floatingActionButton, View view) {
        if (isDependent(view)) {
            updateFabTranslationForBottomNavigationBar(coordinatorLayout, floatingActionButton, view);
        }
    }

    private boolean isDependent(View view) {
        return (view instanceof BottomNavigationBar) || (view instanceof Snackbar.SnackbarLayout);
    }

    private void updateFabTranslationForBottomNavigationBar(CoordinatorLayout coordinatorLayout, FloatingActionButton floatingActionButton, View view) {
        float fabTranslationYForSnackBar = getFabTranslationYForSnackBar(coordinatorLayout, floatingActionButton);
        float[] fabTranslationYForBottomNavigationBar = getFabTranslationYForBottomNavigationBar(coordinatorLayout, floatingActionButton);
        float f = fabTranslationYForBottomNavigationBar[0];
        float f2 = fabTranslationYForBottomNavigationBar[1];
        if (fabTranslationYForSnackBar >= f) {
            fabTranslationYForSnackBar = f;
        }
        float translationY = floatingActionButton.getTranslationY();
        ensureOrCancelAnimator(floatingActionButton);
        if (floatingActionButton.isShown() && Math.abs(translationY - fabTranslationYForSnackBar) > floatingActionButton.getHeight() * 0.667f) {
            this.mFabTranslationYAnimator.translationY(fabTranslationYForSnackBar).start();
        } else {
            floatingActionButton.setTranslationY(fabTranslationYForSnackBar);
        }
    }

    private float[] getFabTranslationYForBottomNavigationBar(CoordinatorLayout coordinatorLayout, FloatingActionButton floatingActionButton) {
        List<View> dependencies = coordinatorLayout.getDependencies(floatingActionButton);
        int size = dependencies.size();
        float fMin = 0.0f;
        float height = 0.0f;
        for (int i = 0; i < size; i++) {
            View view = dependencies.get(i);
            if (view instanceof BottomNavigationBar) {
                height = view.getHeight();
                fMin = Math.min(fMin, view.getTranslationY() - height);
            }
        }
        return new float[]{fMin, height};
    }

    private float getFabTranslationYForSnackBar(CoordinatorLayout coordinatorLayout, FloatingActionButton floatingActionButton) {
        List<View> dependencies = coordinatorLayout.getDependencies(floatingActionButton);
        int size = dependencies.size();
        float fMin = 0.0f;
        for (int i = 0; i < size; i++) {
            View view = dependencies.get(i);
            if ((view instanceof Snackbar.SnackbarLayout) && coordinatorLayout.doViewsOverlap(floatingActionButton, view)) {
                fMin = Math.min(fMin, view.getTranslationY() - view.getHeight());
            }
        }
        return fMin;
    }

    private void ensureOrCancelAnimator(FloatingActionButton floatingActionButton) {
        ViewPropertyAnimatorCompat viewPropertyAnimatorCompat = this.mFabTranslationYAnimator;
        if (viewPropertyAnimatorCompat == null) {
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompatAnimate = ViewCompat.animate(floatingActionButton);
            this.mFabTranslationYAnimator = viewPropertyAnimatorCompatAnimate;
            viewPropertyAnimatorCompatAnimate.setDuration(400L);
            this.mFabTranslationYAnimator.setInterpolator(FAST_OUT_SLOW_IN_INTERPOLATOR);
            return;
        }
        viewPropertyAnimatorCompat.cancel();
    }
}
