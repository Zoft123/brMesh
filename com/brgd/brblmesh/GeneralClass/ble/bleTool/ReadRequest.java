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
public final class ReadRequest extends SimpleValueRequest<DataReceivedCallback> implements Operation {
    private DataStream buffer;
    private int count;
    private DataMerger dataMerger;
    private DataFilter filter;
    private ReadProgressCallback progressCallback;

    ReadRequest(Request.Type type) {
        super(type);
        this.count = 0;
    }

    ReadRequest(Request.Type type, BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        super(type, bluetoothGattCharacteristic);
        this.count = 0;
    }

    ReadRequest(Request.Type type, BluetoothGattDescriptor bluetoothGattDescriptor) {
        super(type, bluetoothGattDescriptor);
        this.count = 0;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.Request
    public ReadRequest setRequestHandler(RequestHandler requestHandler) {
        super.setRequestHandler(requestHandler);
        return this;
    }

    @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.Request
    public ReadRequest setHandler(Handler handler) {
        super.setHandler(handler);
        return this;
    }

    @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.Request
    public ReadRequest done(SuccessCallback successCallback) {
        super.done(successCallback);
        return this;
    }

    @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.Request
    public ReadRequest fail(FailCallback failCallback) {
        super.fail(failCallback);
        return this;
    }

    @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.Request
    public ReadRequest invalid(InvalidRequestCallback invalidRequestCallback) {
        super.invalid(invalidRequestCallback);
        return this;
    }

    @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.Request
    public ReadRequest before(BeforeCallback beforeCallback) {
        super.before(beforeCallback);
        return this;
    }

    @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.SimpleValueRequest
    public ReadRequest with(DataReceivedCallback dataReceivedCallback) {
        super.with(dataReceivedCallback);
        return this;
    }

    public ReadRequest filter(DataFilter dataFilter) {
        this.filter = dataFilter;
        return this;
    }

    public ReadRequest merge(DataMerger dataMerger) {
        this.dataMerger = dataMerger;
        this.progressCallback = null;
        return this;
    }

    public ReadRequest merge(DataMerger dataMerger, ReadProgressCallback readProgressCallback) {
        this.dataMerger = dataMerger;
        this.progressCallback = readProgressCallback;
        return this;
    }

    public <E extends ProfileReadResponse> E awaitValid(Class<E> cls) throws InvalidDataException, InvalidRequestException, DeviceDisconnectedException, BluetoothDisabledException, RequestFailedException {
        E e = (E) await((Class) cls);
        if (e.isValid()) {
            return e;
        }
        throw new InvalidDataException(e);
    }

    public <E extends ProfileReadResponse> E awaitValid(E e) throws InvalidDataException, InvalidRequestException, DeviceDisconnectedException, BluetoothDisabledException, RequestFailedException {
        await(e);
        if (e.isValid()) {
            return e;
        }
        throw new InvalidDataException(e);
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
            this.handler.post(new Runnable() { // from class: com.brgd.brblmesh.GeneralClass.ble.bleTool.ReadRequest$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    dataReceivedCallback.onDataReceived(bluetoothDevice, data);
                }
            });
            return;
        }
        this.handler.post(new Runnable() { // from class: com.brgd.brblmesh.GeneralClass.ble.bleTool.ReadRequest$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$notifyValueChanged$1(bluetoothDevice, bArr);
            }
        });
        if (this.buffer == null) {
            this.buffer = new DataStream();
        }
        DataMerger dataMerger = this.dataMerger;
        DataStream dataStream = this.buffer;
        int i = this.count;
        this.count = i + 1;
        if (dataMerger.merge(dataStream, bArr, i)) {
            final Data data2 = this.buffer.toData();
            this.handler.post(new Runnable() { // from class: com.brgd.brblmesh.GeneralClass.ble.bleTool.ReadRequest$$ExternalSyntheticLambda2
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
    public /* synthetic */ void lambda$notifyValueChanged$1(BluetoothDevice bluetoothDevice, byte[] bArr) {
        ReadProgressCallback readProgressCallback = this.progressCallback;
        if (readProgressCallback != null) {
            readProgressCallback.onPacketReceived(bluetoothDevice, bArr, this.count);
        }
    }

    boolean hasMore() {
        return this.count > 0;
    }
}
