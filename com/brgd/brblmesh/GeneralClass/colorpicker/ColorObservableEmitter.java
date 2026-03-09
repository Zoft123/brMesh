package com.brgd.brblmesh.GeneralClass.colorpicker;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
class ColorObservableEmitter implements ColorObservable {
    private int color;
    private final List<ColorObserver> observers = new ArrayList();

    ColorObservableEmitter() {
    }

    @Override // com.brgd.brblmesh.GeneralClass.colorpicker.ColorObservable
    public void subscribe(ColorObserver colorObserver) {
        if (colorObserver == null) {
            return;
        }
        this.observers.add(colorObserver);
    }

    @Override // com.brgd.brblmesh.GeneralClass.colorpicker.ColorObservable
    public void unsubscribe(ColorObserver colorObserver) {
        if (colorObserver == null) {
            return;
        }
        this.observers.remove(colorObserver);
    }

    @Override // com.brgd.brblmesh.GeneralClass.colorpicker.ColorObservable
    public int getColor() {
        return this.color;
    }

    void onColor(int i, boolean z, boolean z2) {
        this.color = i;
        Iterator<ColorObserver> it = this.observers.iterator();
        while (it.hasNext()) {
            it.next().onColor(i, z, z2);
        }
    }
}
