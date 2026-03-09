package com.brgd.brblmesh.GeneralClass.ble.bleTool;

import android.os.Handler;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.Request;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.callback.BeforeCallback;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.callback.FailCallback;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.callback.InvalidRequestCallback;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.callback.SuccessCallback;

/* JADX INFO: loaded from: classes.dex */
public class DisconnectRequest extends TimeoutableRequest {
    DisconnectRequest(Request.Type type) {
        super(type);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.TimeoutableRequest, com.brgd.brblmesh.GeneralClass.ble.bleTool.Request
    public DisconnectRequest setRequestHandler(RequestHandler requestHandler) {
        super.setRequestHandler(requestHandler);
        return this;
    }

    @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.TimeoutableRequest, com.brgd.brblmesh.GeneralClass.ble.bleTool.Request
    public DisconnectRequest setHandler(Handler handler) {
        super.setHandler(handler);
        return this;
    }

    @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.TimeoutableRequest
    public DisconnectRequest timeout(long j) {
        super.timeout(j);
        return this;
    }

    @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.Request
    public DisconnectRequest done(SuccessCallback successCallback) {
        super.done(successCallback);
        return this;
    }

    @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.Request
    public DisconnectRequest fail(FailCallback failCallback) {
        super.fail(failCallback);
        return this;
    }

    @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.Request
    public DisconnectRequest invalid(InvalidRequestCallback invalidRequestCallback) {
        super.invalid(invalidRequestCallback);
        return this;
    }

    @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.Request
    public DisconnectRequest before(BeforeCallback beforeCallback) {
        super.before(beforeCallback);
        return this;
    }
}
