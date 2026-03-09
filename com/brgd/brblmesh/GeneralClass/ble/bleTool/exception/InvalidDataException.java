package com.brgd.brblmesh.GeneralClass.ble.bleTool.exception;

import com.brgd.brblmesh.GeneralClass.ble.bleTool.callback.profile.ProfileReadResponse;

/* JADX INFO: loaded from: classes.dex */
public final class InvalidDataException extends Exception {
    private final ProfileReadResponse response;

    public InvalidDataException(ProfileReadResponse profileReadResponse) {
        this.response = profileReadResponse;
    }

    public ProfileReadResponse getResponse() {
        return this.response;
    }
}
