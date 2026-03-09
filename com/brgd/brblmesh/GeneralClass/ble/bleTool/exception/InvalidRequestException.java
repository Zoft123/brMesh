package com.brgd.brblmesh.GeneralClass.ble.bleTool.exception;

import com.brgd.brblmesh.GeneralClass.ble.bleTool.Request;

/* JADX INFO: loaded from: classes.dex */
public final class InvalidRequestException extends Exception {
    private final Request request;

    public InvalidRequestException(Request request) {
        super("Invalid request");
        this.request = request;
    }

    public Request getRequest() {
        return this.request;
    }
}
