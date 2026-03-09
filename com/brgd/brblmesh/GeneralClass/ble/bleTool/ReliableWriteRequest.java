package com.brgd.brblmesh.GeneralClass.ble.bleTool;

import android.os.Handler;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.callback.BeforeCallback;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.callback.FailCallback;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.callback.InvalidRequestCallback;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.callback.SuccessCallback;

/* JADX INFO: loaded from: classes.dex */
public final class ReliableWriteRequest extends RequestQueue {
    private boolean cancelled;
    private boolean closed;
    private boolean initialized;

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.RequestQueue, com.brgd.brblmesh.GeneralClass.ble.bleTool.Request
    public ReliableWriteRequest setRequestHandler(RequestHandler requestHandler) {
        super.setRequestHandler(requestHandler);
        return this;
    }

    @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.RequestQueue, com.brgd.brblmesh.GeneralClass.ble.bleTool.Request
    public ReliableWriteRequest setHandler(Handler handler) {
        super.setHandler(handler);
        return this;
    }

    @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.RequestQueue, com.brgd.brblmesh.GeneralClass.ble.bleTool.Request
    public ReliableWriteRequest done(SuccessCallback successCallback) {
        super.done(successCallback);
        return this;
    }

    @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.RequestQueue, com.brgd.brblmesh.GeneralClass.ble.bleTool.Request
    public ReliableWriteRequest fail(FailCallback failCallback) {
        super.fail(failCallback);
        return this;
    }

    @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.RequestQueue, com.brgd.brblmesh.GeneralClass.ble.bleTool.Request
    public ReliableWriteRequest invalid(InvalidRequestCallback invalidRequestCallback) {
        super.invalid(invalidRequestCallback);
        return this;
    }

    @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.RequestQueue, com.brgd.brblmesh.GeneralClass.ble.bleTool.Request
    public ReliableWriteRequest before(BeforeCallback beforeCallback) {
        super.before(beforeCallback);
        return this;
    }

    @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.RequestQueue
    public ReliableWriteRequest add(Operation operation) {
        super.add(operation);
        if (operation instanceof WriteRequest) {
            ((WriteRequest) operation).forceSplit();
        }
        return this;
    }

    @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.RequestQueue
    public void cancelQueue() {
        this.cancelled = true;
        super.cancelQueue();
    }

    public void abort() {
        cancelQueue();
    }

    @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.RequestQueue
    public int size() {
        int size = super.size();
        if (!this.initialized) {
            size++;
        }
        return !this.closed ? size + 1 : size;
    }

    @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.RequestQueue
    Request getNext() {
        if (!this.initialized) {
            this.initialized = true;
            return newBeginReliableWriteRequest();
        }
        if (super.isEmpty()) {
            this.closed = true;
            if (this.cancelled) {
                return newAbortReliableWriteRequest();
            }
            return newExecuteReliableWriteRequest();
        }
        return super.getNext();
    }

    @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.RequestQueue
    boolean hasMore() {
        if (!this.initialized) {
            return super.hasMore();
        }
        return !this.closed;
    }
}
