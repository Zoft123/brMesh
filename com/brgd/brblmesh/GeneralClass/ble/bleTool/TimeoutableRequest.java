package com.brgd.brblmesh.GeneralClass.ble.bleTool;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.os.Handler;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.Request;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.callback.FailCallback;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.callback.SuccessCallback;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.exception.BluetoothDisabledException;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.exception.DeviceDisconnectedException;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.exception.InvalidRequestException;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.exception.RequestFailedException;

/* JADX INFO: loaded from: classes.dex */
public abstract class TimeoutableRequest extends Request {
    protected long timeout;
    private Runnable timeoutCallback;

    TimeoutableRequest(Request.Type type) {
        super(type);
    }

    TimeoutableRequest(Request.Type type, BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        super(type, bluetoothGattCharacteristic);
    }

    TimeoutableRequest(Request.Type type, BluetoothGattDescriptor bluetoothGattDescriptor) {
        super(type, bluetoothGattDescriptor);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.Request
    public TimeoutableRequest setRequestHandler(RequestHandler requestHandler) {
        super.setRequestHandler(requestHandler);
        return this;
    }

    @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.Request
    public TimeoutableRequest setHandler(Handler handler) {
        super.setHandler(handler);
        return this;
    }

    public TimeoutableRequest timeout(long j) {
        if (this.timeoutCallback != null) {
            throw new IllegalStateException("Request already started");
        }
        this.timeout = j;
        return this;
    }

    @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.Request
    public final void enqueue() {
        super.enqueue();
    }

    @Deprecated
    public final void enqueue(long j) {
        timeout(j).enqueue();
    }

    public final void await() throws InterruptedException, InvalidRequestException, DeviceDisconnectedException, BluetoothDisabledException, RequestFailedException {
        assertNotMainThread();
        SuccessCallback successCallback = this.successCallback;
        FailCallback failCallback = this.failCallback;
        try {
            this.syncLock.close();
            Request.RequestCallback requestCallback = new Request.RequestCallback();
            done(requestCallback).fail(requestCallback).invalid(requestCallback).enqueue();
            if (!this.syncLock.block(this.timeout)) {
                throw new InterruptedException();
            }
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
            this.successCallback = successCallback;
            this.failCallback = failCallback;
        }
    }

    @Deprecated
    public final void await(long j) throws InterruptedException, InvalidRequestException, DeviceDisconnectedException, BluetoothDisabledException, RequestFailedException {
        timeout(j).await();
    }

    @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.Request
    void notifyStarted(final BluetoothDevice bluetoothDevice) {
        if (this.timeout > 0) {
            this.timeoutCallback = new Runnable() { // from class: com.brgd.brblmesh.GeneralClass.ble.bleTool.TimeoutableRequest$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$notifyStarted$0(bluetoothDevice);
                }
            };
            this.handler.postDelayed(this.timeoutCallback, this.timeout);
        }
        super.notifyStarted(bluetoothDevice);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$notifyStarted$0(BluetoothDevice bluetoothDevice) {
        this.timeoutCallback = null;
        if (this.finished) {
            return;
        }
        notifyFail(bluetoothDevice, -5);
        this.requestHandler.onRequestTimeout(this);
    }

    @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.Request
    boolean notifySuccess(BluetoothDevice bluetoothDevice) {
        if (!this.finished) {
            this.handler.removeCallbacks(this.timeoutCallback);
            this.timeoutCallback = null;
        }
        return super.notifySuccess(bluetoothDevice);
    }

    @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.Request
    void notifyFail(BluetoothDevice bluetoothDevice, int i) {
        if (!this.finished) {
            this.handler.removeCallbacks(this.timeoutCallback);
            this.timeoutCallback = null;
        }
        super.notifyFail(bluetoothDevice, i);
    }

    @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.Request
    void notifyInvalidRequest() {
        if (!this.finished) {
            this.handler.removeCallbacks(this.timeoutCallback);
            this.timeoutCallback = null;
        }
        super.notifyInvalidRequest();
    }
}
