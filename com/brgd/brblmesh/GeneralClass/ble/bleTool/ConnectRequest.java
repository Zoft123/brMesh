package com.brgd.brblmesh.GeneralClass.ble.bleTool;

import android.bluetooth.BluetoothDevice;
import android.os.Handler;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.Request;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.callback.BeforeCallback;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.callback.FailCallback;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.callback.InvalidRequestCallback;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.callback.SuccessCallback;

/* JADX INFO: loaded from: classes.dex */
public class ConnectRequest extends TimeoutableRequest {
    private int attempt;
    private boolean autoConnect;
    private int delay;
    private BluetoothDevice device;
    private int preferredPhy;
    private int retries;

    ConnectRequest(Request.Type type, BluetoothDevice bluetoothDevice) {
        super(type);
        this.attempt = 0;
        this.retries = 0;
        this.delay = 0;
        this.autoConnect = false;
        this.device = bluetoothDevice;
        this.preferredPhy = 1;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.TimeoutableRequest, com.brgd.brblmesh.GeneralClass.ble.bleTool.Request
    public ConnectRequest setRequestHandler(RequestHandler requestHandler) {
        super.setRequestHandler(requestHandler);
        return this;
    }

    @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.TimeoutableRequest, com.brgd.brblmesh.GeneralClass.ble.bleTool.Request
    public ConnectRequest setHandler(Handler handler) {
        super.setHandler(handler);
        return this;
    }

    @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.TimeoutableRequest
    public ConnectRequest timeout(long j) {
        super.timeout(j);
        return this;
    }

    @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.Request
    public ConnectRequest done(SuccessCallback successCallback) {
        super.done(successCallback);
        return this;
    }

    @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.Request
    public ConnectRequest fail(FailCallback failCallback) {
        super.fail(failCallback);
        return this;
    }

    @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.Request
    public ConnectRequest invalid(InvalidRequestCallback invalidRequestCallback) {
        super.invalid(invalidRequestCallback);
        return this;
    }

    @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.Request
    public ConnectRequest before(BeforeCallback beforeCallback) {
        super.before(beforeCallback);
        return this;
    }

    public ConnectRequest retry(int i) {
        this.retries = i;
        this.delay = 0;
        return this;
    }

    public ConnectRequest retry(int i, int i2) {
        this.retries = i;
        this.delay = i2;
        return this;
    }

    public ConnectRequest useAutoConnect(boolean z) {
        this.autoConnect = z;
        return this;
    }

    public ConnectRequest usePreferredPhy(int i) {
        this.preferredPhy = i;
        return this;
    }

    public BluetoothDevice getDevice() {
        return this.device;
    }

    int getPreferredPhy() {
        return this.preferredPhy;
    }

    boolean canRetry() {
        int i = this.retries;
        if (i <= 0) {
            return false;
        }
        this.retries = i - 1;
        return true;
    }

    boolean isFirstAttempt() {
        int i = this.attempt;
        this.attempt = i + 1;
        return i == 0;
    }

    int getRetryDelay() {
        return this.delay;
    }

    boolean shouldAutoConnect() {
        return this.autoConnect;
    }
}
