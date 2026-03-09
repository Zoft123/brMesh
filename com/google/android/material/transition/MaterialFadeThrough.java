package com.google.android.material.transition;

import android.content.Context;
import android.transition.Transition;
import com.google.android.material.animation.AnimationUtils;

/* JADX INFO: loaded from: classes.dex */
public class MaterialFadeThrough extends MaterialTransitionSet<FadeThrough> {
    private static final float DEFAULT_START_SCALE = 0.92f;

    @Override // com.google.android.material.transition.MaterialTransitionSet
    public /* bridge */ /* synthetic */ Transition getSecondaryTransition() {
        return super.getSecondaryTransition();
    }

    @Override // com.google.android.material.transition.MaterialTransitionSet
    public /* bridge */ /* synthetic */ void setSecondaryTransition(Transition transition) {
        super.setSecondaryTransition(transition);
    }

    public static MaterialFadeThrough create(Context context) {
        MaterialFadeThrough materialFadeThrough = new MaterialFadeThrough();
        materialFadeThrough.initialize(context);
        return materialFadeThrough;
    }

    private MaterialFadeThrough() {
        setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.android.material.transition.MaterialTransitionSet
    public FadeThrough getDefaultPrimaryTransition() {
        return new FadeThrough();
    }

    @Override // com.google.android.material.transition.MaterialTransitionSet
    Transition getDefaultSecondaryTransition() {
        Scale scale = new Scale();
        scale.setMode(1);
        scale.setIncomingStartScale(DEFAULT_START_SCALE);
        return scale;
    }
}
