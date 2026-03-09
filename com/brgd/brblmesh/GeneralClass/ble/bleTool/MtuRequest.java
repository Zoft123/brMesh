package com.brgd.brblmesh.GeneralClass.ble.bleTool;

import android.bluetooth.BluetoothDevice;
import android.os.Handler;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.Request;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.callback.BeforeCallback;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.callback.FailCallback;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.callback.InvalidRequestCallback;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.callback.MtuCallback;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.callback.SuccessCallback;

/* JADX INFO: loaded from: classes.dex */
public final class MtuRequest extends SimpleValueRequest<MtuCallback> implements Operation {
    private final int value;

    MtuRequest(Request.Type type, int i) {
        super(type);
        i = i < 23 ? 23 : i;
        this.value = i > 517 ? 517 : i;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.Request
    public MtuRequest setRequestHandler(RequestHandler requestHandler) {
        super.setRequestHandler(requestHandler);
        return this;
    }

    @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.Request
    public MtuRequest setHandler(Handler handler) {
        super.setHandler(handler);
        return this;
    }

    @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.Request
    public MtuRequest done(SuccessCallback successCallback) {
        super.done(successCallback);
        return this;
    }

    @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.Request
    public MtuRequest fail(FailCallback failCallback) {
        super.fail(failCallback);
        return this;
    }

    @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.Request
    public MtuRequest invalid(InvalidRequestCallback invalidRequestCallback) {
        super.invalid(invalidRequestCallback);
        return this;
    }

    @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.Request
    public MtuRequest before(BeforeCallback beforeCallback) {
        super.before(beforeCallback);
        return this;
    }

    @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.SimpleValueRequest
    public MtuRequest with(MtuCallback mtuCallback) {
        super.with(mtuCallback);
        return this;
    }

    void notifyMtuChanged(final BluetoothDevice bluetoothDevice, final int i) {
        this.handler.post(new Runnable() { // from class: com.brgd.brblmesh.GeneralClass.ble.bleTool.MtuRequest$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$notifyMtuChanged$0(bluetoothDevice, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$notifyMtuChanged$0(BluetoothDevice bluetoothDevice, int i) {
        if (this.valueCallback != 0) {
            ((MtuCallback) this.valueCallback).onMtuChanged(bluetoothDevice, i);
        }
    }

    int getRequiredMtu() {
        return this.value;
    }
}
