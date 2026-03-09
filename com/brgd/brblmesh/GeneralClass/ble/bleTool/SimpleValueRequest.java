package com.brgd.brblmesh.GeneralClass.ble.bleTool;

import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.Request;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.exception.BluetoothDisabledException;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.exception.DeviceDisconnectedException;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.exception.InvalidRequestException;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.exception.RequestFailedException;

/* JADX INFO: loaded from: classes.dex */
public abstract class SimpleValueRequest<T> extends SimpleRequest {
    T valueCallback;

    SimpleValueRequest(Request.Type type) {
        super(type);
    }

    SimpleValueRequest(Request.Type type, BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        super(type, bluetoothGattCharacteristic);
    }

    SimpleValueRequest(Request.Type type, BluetoothGattDescriptor bluetoothGattDescriptor) {
        super(type, bluetoothGattDescriptor);
    }

    public SimpleValueRequest<T> with(T t) {
        this.valueCallback = t;
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <E extends T> E await(E e) throws InvalidRequestException, DeviceDisconnectedException, BluetoothDisabledException, RequestFailedException {
        assertNotMainThread();
        T t = this.valueCallback;
        try {
            with(e).await();
            return e;
        } finally {
            this.valueCallback = t;
        }
    }

    public <E extends T> E await(Class<E> cls) throws InvalidRequestException, DeviceDisconnectedException, BluetoothDisabledException, RequestFailedException {
        assertNotMainThread();
        try {
            return (E) await(cls.newInstance());
        } catch (IllegalAccessException unused) {
            throw new IllegalArgumentException("Couldn't instantiate " + cls.getCanonicalName() + " class. Is the default constructor accessible?");
        } catch (InstantiationException unused2) {
            throw new IllegalArgumentException("Couldn't instantiate " + cls.getCanonicalName() + " class. Does it have a default constructor with no arguments?");
        }
    }
}
