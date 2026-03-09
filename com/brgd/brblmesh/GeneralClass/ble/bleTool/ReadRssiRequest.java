package com.brgd.brblmesh.GeneralClass.ble.bleTool;

import android.bluetooth.BluetoothDevice;
import android.os.Handler;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.Request;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.callback.BeforeCallback;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.callback.FailCallback;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.callback.InvalidRequestCallback;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.callback.RssiCallback;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.callback.SuccessCallback;

/* JADX INFO: loaded from: classes.dex */
public final class ReadRssiRequest extends SimpleValueRequest<RssiCallback> implements Operation {
    ReadRssiRequest(Request.Type type) {
        super(type);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.Request
    public ReadRssiRequest setRequestHandler(RequestHandler requestHandler) {
        super.setRequestHandler(requestHandler);
        return this;
    }

    @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.Request
    public ReadRssiRequest setHandler(Handler handler) {
        super.setHandler(handler);
        return this;
    }

    @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.Request
    public ReadRssiRequest done(SuccessCallback successCallback) {
        super.done(successCallback);
        return this;
    }

    @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.Request
    public ReadRssiRequest fail(FailCallback failCallback) {
        super.fail(failCallback);
        return this;
    }

    @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.Request
    public ReadRssiRequest invalid(InvalidRequestCallback invalidRequestCallback) {
        super.invalid(invalidRequestCallback);
        return this;
    }

    @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.Request
    public ReadRssiRequest before(BeforeCallback beforeCallback) {
        super.before(beforeCallback);
        return this;
    }

    @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.SimpleValueRequest
    public ReadRssiRequest with(RssiCallback rssiCallback) {
        super.with(rssiCallback);
        return this;
    }

    void notifyRssiRead(final BluetoothDevice bluetoothDevice, final int i) {
        this.handler.post(new Runnable() { // from class: com.brgd.brblmesh.GeneralClass.ble.bleTool.ReadRssiRequest$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$notifyRssiRead$0(bluetoothDevice, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$notifyRssiRead$0(BluetoothDevice bluetoothDevice, int i) {
        if (this.valueCallback != 0) {
            ((RssiCallback) this.valueCallback).onRssiRead(bluetoothDevice, i);
        }
    }
}
