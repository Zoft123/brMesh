package com.brgd.brblmesh.GeneralClass.ble.bleTool;

import android.bluetooth.BluetoothDevice;
import android.os.Handler;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.Request;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.callback.BeforeCallback;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.callback.FailCallback;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.callback.InvalidRequestCallback;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.callback.SuccessCallback;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.exception.BluetoothDisabledException;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.exception.DeviceDisconnectedException;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.exception.InvalidRequestException;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.exception.RequestFailedException;
import java.util.Deque;
import java.util.LinkedList;

/* JADX INFO: loaded from: classes.dex */
public class RequestQueue extends Request {
    private final Deque<Request> requests;

    RequestQueue() {
        super(Request.Type.SET);
        this.requests = new LinkedList();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.Request
    public RequestQueue setRequestHandler(RequestHandler requestHandler) {
        super.setRequestHandler(requestHandler);
        return this;
    }

    @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.Request
    public RequestQueue setHandler(Handler handler) {
        super.setHandler(handler);
        return this;
    }

    @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.Request
    public RequestQueue done(SuccessCallback successCallback) {
        super.done(successCallback);
        return this;
    }

    @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.Request
    public RequestQueue fail(FailCallback failCallback) {
        super.fail(failCallback);
        return this;
    }

    @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.Request
    public RequestQueue invalid(InvalidRequestCallback invalidRequestCallback) {
        super.invalid(invalidRequestCallback);
        return this;
    }

    @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.Request
    public RequestQueue before(BeforeCallback beforeCallback) {
        super.before(beforeCallback);
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public RequestQueue add(Operation operation) {
        if (operation instanceof Request) {
            Request request = (Request) operation;
            if (request.enqueued) {
                throw new IllegalStateException("Request already enqueued");
            }
            request.internalFail(new FailCallback() { // from class: com.brgd.brblmesh.GeneralClass.ble.bleTool.RequestQueue$$ExternalSyntheticLambda0
                @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.callback.FailCallback
                public final void onRequestFailed(BluetoothDevice bluetoothDevice, int i) {
                    this.f$0.notifyFail(bluetoothDevice, i);
                }
            });
            this.requests.add(request);
            request.enqueued = true;
            return this;
        }
        throw new IllegalArgumentException("Operation does not extend Request");
    }

    void addFirst(Request request) {
        this.requests.addFirst(request);
    }

    public int size() {
        return this.requests.size();
    }

    public boolean isEmpty() {
        return this.requests.isEmpty();
    }

    public void cancelQueue() {
        this.requests.clear();
    }

    public final void await() throws InterruptedException, InvalidRequestException, DeviceDisconnectedException, BluetoothDisabledException, RequestFailedException {
        assertNotMainThread();
        BeforeCallback beforeCallback = this.beforeCallback;
        SuccessCallback successCallback = this.successCallback;
        FailCallback failCallback = this.failCallback;
        try {
            this.syncLock.close();
            Request.RequestCallback requestCallback = new Request.RequestCallback();
            this.beforeCallback = null;
            done((SuccessCallback) requestCallback).fail((FailCallback) requestCallback).invalid((InvalidRequestCallback) requestCallback).enqueue();
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
            if (requestCallback.status == -5) {
                throw new InterruptedException();
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

    Request getNext() {
        try {
            return this.requests.remove();
        } catch (Exception unused) {
            return null;
        }
    }

    boolean hasMore() {
        return (this.finished || this.requests.isEmpty()) ? false : true;
    }
}
