package com.google.android.material.transition;

import android.content.Context;
import android.transition.Transition;
import android.transition.TransitionSet;

/* JADX INFO: loaded from: classes.dex */
abstract class MaterialTransitionSet<T extends Transition> extends TransitionSet {
    protected Context context;
    private T primaryTransition;
    private Transition secondaryTransition;

    abstract T getDefaultPrimaryTransition();

    abstract Transition getDefaultSecondaryTransition();

    MaterialTransitionSet() {
    }

    protected void initialize(Context context) {
        this.context = context;
        T t = (T) getDefaultPrimaryTransition();
        this.primaryTransition = t;
        addTransition(t);
        setSecondaryTransition(getDefaultSecondaryTransition());
    }

    public T getPrimaryTransition() {
        return this.primaryTransition;
    }

    public Transition getSecondaryTransition() {
        return this.secondaryTransition;
    }

    public void setSecondaryTransition(Transition transition) {
        TransitionUtils.maybeRemoveTransition(this, this.secondaryTransition);
        this.secondaryTransition = transition;
        TransitionUtils.maybeAddTransition(this, transition);
    }
}
