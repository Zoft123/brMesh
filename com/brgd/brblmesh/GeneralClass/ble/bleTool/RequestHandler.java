package com.brgd.brblmesh.GeneralClass.ble.bleTool;

/* JADX INFO: loaded from: classes.dex */
abstract class RequestHandler implements CallbackHandler {
    abstract void cancelQueue();

    abstract void enqueue(Request request);

    abstract void onRequestTimeout(TimeoutableRequest timeoutableRequest);

    RequestHandler() {
    }
}
