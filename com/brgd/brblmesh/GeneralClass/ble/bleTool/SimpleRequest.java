package com.brgd.brblmesh.GeneralClass.ble.bleTool;

import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.Request;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.callback.BeforeCallback;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.callback.FailCallback;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.callback.SuccessCallback;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.exception.BluetoothDisabledException;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.exception.DeviceDisconnectedException;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.exception.InvalidRequestException;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.exception.RequestFailedException;

/* JADX INFO: loaded from: classes.dex */
public class SimpleRequest extends Request {
    SimpleRequest(Request.Type type) {
        super(type);
    }

    SimpleRequest(Request.Type type, BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        super(type, bluetoothGattCharacteristic);
    }

    SimpleRequest(Request.Type type, BluetoothGattDescriptor bluetoothGattDescriptor) {
        super(type, bluetoothGattDescriptor);
    }

    public final void await() throws InvalidRequestException, DeviceDisconnectedException, BluetoothDisabledException, RequestFailedException {
        assertNotMainThread();
        BeforeCallback beforeCallback = this.beforeCallback;
        SuccessCallback successCallback = this.successCallback;
        FailCallback failCallback = this.failCallback;
        try {
            this.syncLock.close();
            Request.RequestCallback requestCallback = new Request.RequestCallback();
            this.beforeCallback = null;
            done(requestCallback).fail(requestCallback).invalid(requestCallback).enqueue();
            this.syncLock.block();
            if (requestCallback.isSuccess()) {
                return;
            }
            if (requestCallback.status == -1) {
                throw new DeviceDisconnectedException();
            }
            if (requestCallback.status == -100) {
                throw new BluetoothDisabledException();
            }
            if (requestCallback.status == -1000000) {
                throw new InvalidRequestException(this);
            }
            throw new RequestFailedException(this, requestCallback.status);
        } finally {
            this.beforeCallback = beforeCallback;
            this.successCallback = successCallback;
            this.failCallback = failCallback;
        }
    }
}
