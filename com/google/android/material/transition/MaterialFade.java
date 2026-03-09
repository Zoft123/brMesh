package com.google.android.material.transition;

import android.content.Context;
import android.transition.Fade;
import android.transition.Transition;
import com.google.android.material.animation.AnimationUtils;

/* JADX INFO: loaded from: classes.dex */
public class MaterialFade extends MaterialTransitionSet<Fade> {
    private static final long DEFAULT_DURATION_ENTER = 150;
    private static final long DEFAULT_DURATION_ENTER_FADE = 45;
    private static final long DEFAULT_DURATION_RETURN = 75;
    private static final float DEFAULT_START_SCALE = 0.8f;

    @Override // com.google.android.material.transition.MaterialTransitionSet
    public /* bridge */ /* synthetic */ Transition getSecondaryTransition() {
        return super.getSecondaryTransition();
    }

    @Override // com.google.android.material.transition.MaterialTransitionSet
    public /* bridge */ /* synthetic */ void setSecondaryTransition(Transition transition) {
        super.setSecondaryTransition(transition);
    }

    public static MaterialFade create(Context context) {
        return create(context, true);
    }

    public static MaterialFade create(Context context, boolean z) {
        MaterialFade materialFade = new MaterialFade(z);
        materialFade.initialize(context);
        if (z) {
            materialFade.getPrimaryTransition().setDuration(DEFAULT_DURATION_ENTER_FADE);
        }
        return materialFade;
    }

    private MaterialFade(boolean z) {
        setDuration(z ? 150L : 75L);
        setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.android.material.transition.MaterialTransitionSet
    public Fade getDefaultPrimaryTransition() {
        return new Fade();
    }

    @Override // com.google.android.material.transition.MaterialTransitionSet
    Transition getDefaultSecondaryTransition() {
        Scale scale = new Scale();
        scale.setMode(1);
        scale.setIncomingStartScale(0.8f);
        return scale;
    }
}
