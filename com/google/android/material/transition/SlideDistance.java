package com.google.android.material.transition;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.transition.TransitionValues;
import android.transition.Visibility;
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.view.ViewCompat;
import com.google.android.material.R;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* JADX INFO: loaded from: classes.dex */
public class SlideDistance extends Visibility {
    private int slideDistance;
    private int slideEdge;

    @Retention(RetentionPolicy.SOURCE)
    public @interface GravityFlag {
    }

    public SlideDistance(Context context, int i) {
        this.slideEdge = i;
        this.slideDistance = context.getResources().getDimensionPixelSize(R.dimen.mtrl_transition_shared_axis_slide_distance);
    }

    public int getSlideEdge() {
        return this.slideEdge;
    }

    public void setSlideEdge(int i) {
        this.slideEdge = i;
    }

    public int getSlideDistance() {
        return this.slideDistance;
    }

    public void setSlideDistance(int i) {
        this.slideDistance = i;
    }

    @Override // android.transition.Visibility
    public Animator onAppear(ViewGroup viewGroup, View view, TransitionValues transitionValues, TransitionValues transitionValues2) {
        return createTranslationAppearAnimator(viewGroup, view);
    }

    @Override // android.transition.Visibility
    public Animator onDisappear(ViewGroup viewGroup, View view, TransitionValues transitionValues, TransitionValues transitionValues2) {
        return createTranslationDisappearAnimator(viewGroup, view);
    }

    private Animator createTranslationAppearAnimator(View view, View view2) {
        int i = this.slideEdge;
        if (i == 3) {
            return createTranslationXAnimator(view2, this.slideDistance, 0.0f);
        }
        if (i == 5) {
            return createTranslationXAnimator(view2, -this.slideDistance, 0.0f);
        }
        if (i == 48) {
            return createTranslationYAnimator(view2, -this.slideDistance, 0.0f);
        }
        if (i == 80) {
            return createTranslationYAnimator(view2, this.slideDistance, 0.0f);
        }
        if (i == 8388611) {
            return createTranslationXAnimator(view2, isRtl(view) ? this.slideDistance : -this.slideDistance, 0.0f);
        }
        if (i == 8388613) {
            return createTranslationXAnimator(view2, isRtl(view) ? -this.slideDistance : this.slideDistance, 0.0f);
        }
        throw new IllegalArgumentException("Invalid slide direction: " + this.slideEdge);
    }

    private Animator createTranslationDisappearAnimator(View view, View view2) {
        int i = this.slideEdge;
        if (i == 3) {
            return createTranslationXAnimator(view2, 0.0f, -this.slideDistance);
        }
        if (i == 5) {
            return createTranslationXAnimator(view2, 0.0f, this.slideDistance);
        }
        if (i == 48) {
            return createTranslationYAnimator(view2, 0.0f, this.slideDistance);
        }
        if (i == 80) {
            return createTranslationYAnimator(view2, 0.0f, -this.slideDistance);
        }
        if (i == 8388611) {
            return createTranslationXAnimator(view2, 0.0f, isRtl(view) ? -this.slideDistance : this.slideDistance);
        }
        if (i == 8388613) {
            return createTranslationXAnimator(view2, 0.0f, isRtl(view) ? this.slideDistance : -this.slideDistance);
        }
        throw new IllegalArgumentException("Invalid slide direction: " + this.slideEdge);
    }

    private static Animator createTranslationXAnimator(View view, float f, float f2) {
        return ObjectAnimator.ofPropertyValuesHolder(view, PropertyValuesHolder.ofFloat((Property<?, Float>) View.TRANSLATION_X, f, f2));
    }

    private static Animator createTranslationYAnimator(View view, float f, float f2) {
        return ObjectAnimator.ofPropertyValuesHolder(view, PropertyValuesHolder.ofFloat((Property<?, Float>) View.TRANSLATION_Y, f, f2));
    }

    private static boolean isRtl(View view) {
        return ViewCompat.getLayoutDirection(view) == 1;
    }
}
