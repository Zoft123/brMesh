package com.brgd.brblmesh.GeneralClass.ble.bleTool;

import android.content.Context;
import android.os.Handler;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.BleManagerCallbacks;

/* JADX INFO: loaded from: classes.dex */
@Deprecated
public abstract class LegacyBleManager<E extends BleManagerCallbacks> extends BleManager {
    protected E mCallbacks;

    public LegacyBleManager(Context context) {
        super(context);
    }

    public LegacyBleManager(Context context, Handler handler) {
        super(context, handler);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.BleManager
    public void setGattCallbacks(BleManagerCallbacks bleManagerCallbacks) {
        super.setGattCallbacks(bleManagerCallbacks);
        this.mCallbacks = bleManagerCallbacks;
    }
}
