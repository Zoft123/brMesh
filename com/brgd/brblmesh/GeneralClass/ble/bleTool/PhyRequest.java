package com.brgd.brblmesh.GeneralClass.ble.bleTool;

import android.bluetooth.BluetoothDevice;
import android.os.Handler;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.Request;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.callback.BeforeCallback;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.callback.FailCallback;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.callback.InvalidRequestCallback;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.callback.PhyCallback;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.callback.SuccessCallback;

/* JADX INFO: loaded from: classes.dex */
public final class PhyRequest extends SimpleValueRequest<PhyCallback> implements Operation {
    public static final int PHY_LE_1M_MASK = 1;
    public static final int PHY_LE_2M_MASK = 2;
    public static final int PHY_LE_CODED_MASK = 4;
    public static final int PHY_OPTION_NO_PREFERRED = 0;
    public static final int PHY_OPTION_S2 = 1;
    public static final int PHY_OPTION_S8 = 2;
    private final int phyOptions;
    private final int rxPhy;
    private final int txPhy;

    PhyRequest(Request.Type type) {
        super(type);
        this.txPhy = 0;
        this.rxPhy = 0;
        this.phyOptions = 0;
    }

    PhyRequest(Request.Type type, int i, int i2, int i3) {
        super(type);
        i = (i & (-8)) > 0 ? 1 : i;
        i2 = (i2 & (-8)) > 0 ? 1 : i2;
        i3 = (i3 < 0 || i3 > 2) ? 0 : i3;
        this.txPhy = i;
        this.rxPhy = i2;
        this.phyOptions = i3;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.Request
    public PhyRequest setRequestHandler(RequestHandler requestHandler) {
        super.setRequestHandler(requestHandler);
        return this;
    }

    @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.Request
    public PhyRequest setHandler(Handler handler) {
        super.setHandler(handler);
        return this;
    }

    @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.Request
    public PhyRequest done(SuccessCallback successCallback) {
        super.done(successCallback);
        return this;
    }

    @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.Request
    public PhyRequest fail(FailCallback failCallback) {
        super.fail(failCallback);
        return this;
    }

    @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.Request
    public PhyRequest invalid(InvalidRequestCallback invalidRequestCallback) {
        super.invalid(invalidRequestCallback);
        return this;
    }

    @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.Request
    public PhyRequest before(BeforeCallback beforeCallback) {
        super.before(beforeCallback);
        return this;
    }

    @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.SimpleValueRequest
    public PhyRequest with(PhyCallback phyCallback) {
        super.with(phyCallback);
        return this;
    }

    void notifyPhyChanged(final BluetoothDevice bluetoothDevice, final int i, final int i2) {
        this.handler.post(new Runnable() { // from class: com.brgd.brblmesh.GeneralClass.ble.bleTool.PhyRequest$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$notifyPhyChanged$0(bluetoothDevice, i, i2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$notifyPhyChanged$0(BluetoothDevice bluetoothDevice, int i, int i2) {
        if (this.valueCallback != 0) {
            ((PhyCallback) this.valueCallback).onPhyChanged(bluetoothDevice, i, i2);
        }
    }

    void notifyLegacyPhy(final BluetoothDevice bluetoothDevice) {
        this.handler.post(new Runnable() { // from class: com.brgd.brblmesh.GeneralClass.ble.bleTool.PhyRequest$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$notifyLegacyPhy$1(bluetoothDevice);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$notifyLegacyPhy$1(BluetoothDevice bluetoothDevice) {
        if (this.valueCallback != 0) {
            ((PhyCallback) this.valueCallback).onPhyChanged(bluetoothDevice, 1, 1);
        }
    }

    int getPreferredTxPhy() {
        return this.txPhy;
    }

    int getPreferredRxPhy() {
        return this.rxPhy;
    }

    int getPreferredPhyOptions() {
        return this.phyOptions;
    }
}
