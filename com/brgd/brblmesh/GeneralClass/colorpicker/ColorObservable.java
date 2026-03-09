package com.brgd.brblmesh.GeneralClass.colorpicker;

/* JADX INFO: loaded from: classes.dex */
public interface ColorObservable {
    int getColor();

    void subscribe(ColorObserver colorObserver);

    void unsubscribe(ColorObserver colorObserver);
}
