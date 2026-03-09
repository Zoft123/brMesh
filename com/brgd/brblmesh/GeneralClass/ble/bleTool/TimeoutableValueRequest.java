package com.brgd.brblmesh.GeneralClass.ble.bleTool;

import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.Request;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.exception.BluetoothDisabledException;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.exception.DeviceDisconnectedException;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.exception.InvalidRequestException;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.exception.RequestFailedException;

/* JADX INFO: loaded from: classes.dex */
public abstract class TimeoutableValueRequest<T> extends TimeoutableRequest {
    T valueCallback;

    TimeoutableValueRequest(Request.Type type) {
        super(type);
    }

    TimeoutableValueRequest(Request.Type type, BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        super(type, bluetoothGattCharacteristic);
    }

    TimeoutableValueRequest(Request.Type type, BluetoothGattDescriptor bluetoothGattDescriptor) {
        super(type, bluetoothGattDescriptor);
    }

    @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.TimeoutableRequest
    public TimeoutableValueRequest<T> timeout(long j) {
        super.timeout(j);
        return this;
    }

    public TimeoutableValueRequest<T> with(T t) {
        this.valueCallback = t;
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <E extends T> E await(E e) throws InterruptedException, InvalidRequestException, DeviceDisconnectedException, BluetoothDisabledException, RequestFailedException {
        assertNotMainThread();
        T t = this.valueCallback;
        try {
            with(e).await();
            return e;
        } finally {
            this.valueCallback = t;
        }
    }

    public <E extends T> E await(Class<E> cls) throws InterruptedException, InvalidRequestException, DeviceDisconnectedException, BluetoothDisabledException, RequestFailedException {
        assertNotMainThread();
        try {
            return (E) await(cls.newInstance());
        } catch (IllegalAccessException unused) {
            throw new IllegalArgumentException("Couldn't instantiate " + cls.getCanonicalName() + " class. Is the default constructor accessible?");
        } catch (InstantiationException unused2) {
            throw new IllegalArgumentException("Couldn't instantiate " + cls.getCanonicalName() + " class. Does it have a default constructor with no arguments?");
        }
    }

    @Deprecated
    public <E extends T> E await(Class<E> cls, long j) throws InterruptedException, InvalidRequestException, DeviceDisconnectedException, BluetoothDisabledException, RequestFailedException {
        return (E) timeout(j).await((Class) cls);
    }

    @Deprecated
    public <E extends T> E await(E e, long j) throws InterruptedException, InvalidRequestException, DeviceDisconnectedException, BluetoothDisabledException, RequestFailedException {
        return (E) timeout(j).await(e);
    }
}
