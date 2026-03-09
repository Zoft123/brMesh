package com.brgd.brblmesh.GeneralClass.ble.bleTool;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.os.Handler;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.Request;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.callback.BeforeCallback;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.callback.DataReceivedCallback;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.callback.FailCallback;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.callback.InvalidRequestCallback;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.callback.ReadProgressCallback;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.callback.SuccessCallback;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.callback.profile.ProfileReadResponse;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.data.Data;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.data.DataFilter;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.data.DataMerger;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.data.DataStream;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.exception.BluetoothDisabledException;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.exception.DeviceDisconnectedException;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.exception.InvalidDataException;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.exception.InvalidRequestException;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.exception.RequestFailedException;

/* JADX INFO: loaded from: classes.dex */
public final class WaitForValueChangedRequest extends AwaitingRequest<DataReceivedCallback> implements Operation {
    private boolean bluetoothDisabled;
    private DataStream buffer;
    private int count;
    private DataMerger dataMerger;
    private boolean deviceDisconnected;
    private DataFilter filter;
    private ReadProgressCallback progressCallback;

    WaitForValueChangedRequest(Request.Type type, BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        super(type, bluetoothGattCharacteristic);
        this.count = 0;
    }

    WaitForValueChangedRequest(Request.Type type, BluetoothGattDescriptor bluetoothGattDescriptor) {
        super(type, bluetoothGattDescriptor);
        this.count = 0;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.TimeoutableRequest, com.brgd.brblmesh.GeneralClass.ble.bleTool.Request
    public WaitForValueChangedRequest setRequestHandler(RequestHandler requestHandler) {
        super.setRequestHandler(requestHandler);
        return this;
    }

    @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.TimeoutableRequest, com.brgd.brblmesh.GeneralClass.ble.bleTool.Request
    public WaitForValueChangedRequest setHandler(Handler handler) {
        super.setHandler(handler);
        return this;
    }

    @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.TimeoutableValueRequest, com.brgd.brblmesh.GeneralClass.ble.bleTool.TimeoutableRequest
    public WaitForValueChangedRequest timeout(long j) {
        super.timeout(j);
        return this;
    }

    @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.Request
    public WaitForValueChangedRequest done(SuccessCallback successCallback) {
        super.done(successCallback);
        return this;
    }

    @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.Request
    public WaitForValueChangedRequest fail(FailCallback failCallback) {
        super.fail(failCallback);
        return this;
    }

    @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.Request
    public WaitForValueChangedRequest invalid(InvalidRequestCallback invalidRequestCallback) {
        super.invalid(invalidRequestCallback);
        return this;
    }

    @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.Request
    public WaitForValueChangedRequest before(BeforeCallback beforeCallback) {
        super.before(beforeCallback);
        return this;
    }

    @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.TimeoutableValueRequest
    public WaitForValueChangedRequest with(DataReceivedCallback dataReceivedCallback) {
        super.with(dataReceivedCallback);
        return this;
    }

    @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.AwaitingRequest
    public AwaitingRequest<DataReceivedCallback> trigger(Operation operation) {
        super.trigger(operation);
        return this;
    }

    public WaitForValueChangedRequest filter(DataFilter dataFilter) {
        this.filter = dataFilter;
        return this;
    }

    public WaitForValueChangedRequest merge(DataMerger dataMerger) {
        this.dataMerger = dataMerger;
        this.progressCallback = null;
        return this;
    }

    public WaitForValueChangedRequest merge(DataMerger dataMerger, ReadProgressCallback readProgressCallback) {
        this.dataMerger = dataMerger;
        this.progressCallback = readProgressCallback;
        return this;
    }

    public <E extends ProfileReadResponse> E awaitValid(E e) throws InterruptedException, InvalidDataException, InvalidRequestException, DeviceDisconnectedException, BluetoothDisabledException, RequestFailedException {
        E e2 = (E) await(e);
        if (e2 == null || e2.isValid()) {
            return e2;
        }
        throw new InvalidDataException(e2);
    }

    public <E extends ProfileReadResponse> E awaitValid(Class<E> cls) throws InterruptedException, InvalidDataException, InvalidRequestException, DeviceDisconnectedException, BluetoothDisabledException, RequestFailedException {
        E e = (E) await((Class) cls);
        if (e == null || e.isValid()) {
            return e;
        }
        throw new InvalidDataException(e);
    }

    @Deprecated
    public <E extends ProfileReadResponse> E awaitValid(Class<E> cls, long j) throws InterruptedException, InvalidDataException, InvalidRequestException, DeviceDisconnectedException, BluetoothDisabledException, RequestFailedException {
        return (E) timeout(j).awaitValid(cls);
    }

    @Deprecated
    public <E extends ProfileReadResponse> E awaitValid(E e, long j) throws InterruptedException, InvalidDataException, InvalidRequestException, DeviceDisconnectedException, BluetoothDisabledException, RequestFailedException {
        return (E) timeout(j).awaitValid(e);
    }

    boolean matches(byte[] bArr) {
        DataFilter dataFilter = this.filter;
        return dataFilter == null || dataFilter.filter(bArr);
    }

    void notifyValueChanged(final BluetoothDevice bluetoothDevice, final byte[] bArr) {
        final DataReceivedCallback dataReceivedCallback = (DataReceivedCallback) this.valueCallback;
        if (dataReceivedCallback == null) {
            return;
        }
        if (this.dataMerger == null) {
            final Data data = new Data(bArr);
            this.handler.post(new Runnable() { // from class: com.brgd.brblmesh.GeneralClass.ble.bleTool.WaitForValueChangedRequest$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    dataReceivedCallback.onDataReceived(bluetoothDevice, data);
                }
            });
            return;
        }
        final int i = this.count;
        this.handler.post(new Runnable() { // from class: com.brgd.brblmesh.GeneralClass.ble.bleTool.WaitForValueChangedRequest$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$notifyValueChanged$1(bluetoothDevice, bArr, i);
            }
        });
        if (this.buffer == null) {
            this.buffer = new DataStream();
        }
        DataMerger dataMerger = this.dataMerger;
        DataStream dataStream = this.buffer;
        int i2 = this.count;
        this.count = i2 + 1;
        if (dataMerger.merge(dataStream, bArr, i2)) {
            final Data data2 = this.buffer.toData();
            this.handler.post(new Runnable() { // from class: com.brgd.brblmesh.GeneralClass.ble.bleTool.WaitForValueChangedRequest$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    dataReceivedCallback.onDataReceived(bluetoothDevice, data2);
                }
            });
            this.buffer = null;
            this.count = 0;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$notifyValueChanged$1(BluetoothDevice bluetoothDevice, byte[] bArr, int i) {
        ReadProgressCallback readProgressCallback = this.progressCallback;
        if (readProgressCallback != null) {
            readProgressCallback.onPacketReceived(bluetoothDevice, bArr, i);
        }
    }

    boolean hasMore() {
        return this.count > 0;
    }
}
