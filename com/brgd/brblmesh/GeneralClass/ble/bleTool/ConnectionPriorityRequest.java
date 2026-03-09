package com.brgd.brblmesh.GeneralClass.ble.bleTool;

import android.bluetooth.BluetoothDevice;
import android.os.Handler;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.Request;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.callback.BeforeCallback;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.callback.ConnectionPriorityCallback;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.callback.FailCallback;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.callback.InvalidRequestCallback;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.callback.SuccessCallback;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.exception.BluetoothDisabledException;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.exception.DeviceDisconnectedException;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.exception.InvalidRequestException;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.exception.RequestFailedException;

/* JADX INFO: loaded from: classes.dex */
public final class ConnectionPriorityRequest extends SimpleValueRequest<ConnectionPriorityCallback> implements Operation {
    public static final int CONNECTION_PRIORITY_BALANCED = 0;
    public static final int CONNECTION_PRIORITY_HIGH = 1;
    public static final int CONNECTION_PRIORITY_LOW_POWER = 2;
    private final int value;

    ConnectionPriorityRequest(Request.Type type, int i) {
        super(type);
        this.value = (i < 0 || i > 2) ? 0 : i;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.Request
    public ConnectionPriorityRequest setRequestHandler(RequestHandler requestHandler) {
        super.setRequestHandler(requestHandler);
        return this;
    }

    @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.Request
    public ConnectionPriorityRequest setHandler(Handler handler) {
        super.setHandler(handler);
        return this;
    }

    @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.Request
    public ConnectionPriorityRequest done(SuccessCallback successCallback) {
        super.done(successCallback);
        return this;
    }

    @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.Request
    public ConnectionPriorityRequest fail(FailCallback failCallback) {
        super.fail(failCallback);
        return this;
    }

    @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.Request
    public ConnectionPriorityRequest invalid(InvalidRequestCallback invalidRequestCallback) {
        super.invalid(invalidRequestCallback);
        return this;
    }

    @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.Request
    public ConnectionPriorityRequest before(BeforeCallback beforeCallback) {
        super.before(beforeCallback);
        return this;
    }

    @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.SimpleValueRequest
    public ConnectionPriorityRequest with(ConnectionPriorityCallback connectionPriorityCallback) {
        super.with(connectionPriorityCallback);
        return this;
    }

    @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.SimpleValueRequest
    public <E extends ConnectionPriorityCallback> E await(Class<E> cls) throws InvalidRequestException, DeviceDisconnectedException, BluetoothDisabledException, RequestFailedException {
        return (E) super.await((Class) cls);
    }

    @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.SimpleValueRequest
    public <E extends ConnectionPriorityCallback> E await(E e) throws InvalidRequestException, DeviceDisconnectedException, BluetoothDisabledException, RequestFailedException {
        return (E) super.await(e);
    }

    void notifyConnectionPriorityChanged(BluetoothDevice bluetoothDevice, int i, int i2, int i3) {
        if (this.valueCallback != 0) {
            ((ConnectionPriorityCallback) this.valueCallback).onConnectionUpdated(bluetoothDevice, i, i2, i3);
        }
    }

    int getRequiredPriority() {
        return this.value;
    }
}
