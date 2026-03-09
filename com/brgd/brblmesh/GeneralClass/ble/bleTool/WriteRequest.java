package com.brgd.brblmesh.GeneralClass.ble.bleTool;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.os.Handler;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.Request;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.callback.BeforeCallback;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.callback.DataSentCallback;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.callback.FailCallback;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.callback.InvalidRequestCallback;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.callback.SuccessCallback;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.callback.WriteProgressCallback;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.data.Data;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.data.DataSplitter;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.data.DefaultMtuSplitter;
import java.util.Arrays;

/* JADX INFO: loaded from: classes.dex */
public final class WriteRequest extends SimpleValueRequest<DataSentCallback> implements Operation {
    private static final DataSplitter MTU_SPLITTER = new DefaultMtuSplitter();
    private boolean complete;
    private int count;
    private byte[] currentChunk;
    private final byte[] data;
    private DataSplitter dataSplitter;
    private byte[] nextChunk;
    private WriteProgressCallback progressCallback;
    private final int writeType;

    WriteRequest(Request.Type type) {
        this(type, null);
    }

    WriteRequest(Request.Type type, BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        super(type, bluetoothGattCharacteristic);
        this.count = 0;
        this.data = null;
        this.writeType = 0;
        this.complete = true;
    }

    WriteRequest(Request.Type type, BluetoothGattCharacteristic bluetoothGattCharacteristic, byte[] bArr, int i, int i2, int i3) {
        super(type, bluetoothGattCharacteristic);
        this.count = 0;
        this.complete = false;
        this.data = Bytes.copy(bArr, i, i2);
        this.writeType = i3;
    }

    WriteRequest(Request.Type type, BluetoothGattCharacteristic bluetoothGattCharacteristic, byte[] bArr, int i, int i2) {
        super(type, bluetoothGattCharacteristic);
        this.count = 0;
        this.complete = false;
        this.data = Bytes.copy(bArr, i, i2);
        this.writeType = 0;
    }

    WriteRequest(Request.Type type, BluetoothGattDescriptor bluetoothGattDescriptor, byte[] bArr, int i, int i2) {
        super(type, bluetoothGattDescriptor);
        this.count = 0;
        this.complete = false;
        this.data = Bytes.copy(bArr, i, i2);
        this.writeType = 2;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.Request
    public WriteRequest setRequestHandler(RequestHandler requestHandler) {
        super.setRequestHandler(requestHandler);
        return this;
    }

    @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.Request
    public WriteRequest setHandler(Handler handler) {
        super.setHandler(handler);
        return this;
    }

    @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.Request
    public WriteRequest done(SuccessCallback successCallback) {
        super.done(successCallback);
        return this;
    }

    @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.Request
    public WriteRequest fail(FailCallback failCallback) {
        super.fail(failCallback);
        return this;
    }

    @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.Request
    public WriteRequest invalid(InvalidRequestCallback invalidRequestCallback) {
        super.invalid(invalidRequestCallback);
        return this;
    }

    @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.Request
    public WriteRequest before(BeforeCallback beforeCallback) {
        super.before(beforeCallback);
        return this;
    }

    @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.SimpleValueRequest
    public WriteRequest with(DataSentCallback dataSentCallback) {
        super.with(dataSentCallback);
        return this;
    }

    public WriteRequest split(DataSplitter dataSplitter) {
        this.dataSplitter = dataSplitter;
        this.progressCallback = null;
        return this;
    }

    public WriteRequest split(DataSplitter dataSplitter, WriteProgressCallback writeProgressCallback) {
        this.dataSplitter = dataSplitter;
        this.progressCallback = writeProgressCallback;
        return this;
    }

    public WriteRequest split() {
        this.dataSplitter = MTU_SPLITTER;
        this.progressCallback = null;
        return this;
    }

    public WriteRequest split(WriteProgressCallback writeProgressCallback) {
        this.dataSplitter = MTU_SPLITTER;
        this.progressCallback = writeProgressCallback;
        return this;
    }

    void forceSplit() {
        if (this.dataSplitter == null) {
            split();
        }
    }

    byte[] getData(int i) {
        byte[] bArr;
        DataSplitter dataSplitter = this.dataSplitter;
        if (dataSplitter == null || (bArr = this.data) == null) {
            this.complete = true;
            byte[] bArr2 = this.data;
            this.currentChunk = bArr2;
            return bArr2;
        }
        int i2 = this.writeType != 4 ? i - 3 : i - 12;
        byte[] bArrChunk = this.nextChunk;
        if (bArrChunk == null) {
            bArrChunk = dataSplitter.chunk(bArr, this.count, i2);
        }
        if (bArrChunk != null) {
            this.nextChunk = this.dataSplitter.chunk(this.data, this.count + 1, i2);
        }
        if (this.nextChunk == null) {
            this.complete = true;
        }
        this.currentChunk = bArrChunk;
        return bArrChunk;
    }

    boolean notifyPacketSent(final BluetoothDevice bluetoothDevice, final byte[] bArr) {
        this.handler.post(new Runnable() { // from class: com.brgd.brblmesh.GeneralClass.ble.bleTool.WriteRequest$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$notifyPacketSent$0(bluetoothDevice, bArr);
            }
        });
        this.count++;
        if (this.complete) {
            this.handler.post(new Runnable() { // from class: com.brgd.brblmesh.GeneralClass.ble.bleTool.WriteRequest$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$notifyPacketSent$1(bluetoothDevice);
                }
            });
        }
        return Arrays.equals(bArr, this.currentChunk);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$notifyPacketSent$0(BluetoothDevice bluetoothDevice, byte[] bArr) {
        WriteProgressCallback writeProgressCallback = this.progressCallback;
        if (writeProgressCallback != null) {
            writeProgressCallback.onPacketSent(bluetoothDevice, bArr, this.count);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$notifyPacketSent$1(BluetoothDevice bluetoothDevice) {
        if (this.valueCallback != 0) {
            ((DataSentCallback) this.valueCallback).onDataSent(bluetoothDevice, new Data(this.data));
        }
    }

    boolean hasMore() {
        return !this.complete;
    }

    int getWriteType() {
        return this.writeType;
    }
}
