package org.bson.io;

import com.alibaba.fastjson.asm.Opcodes;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import org.bson.BsonSerializationException;
import org.bson.ByteBuf;
import org.bson.types.ObjectId;

/* JADX INFO: loaded from: classes4.dex */
public abstract class OutputBuffer extends OutputStream implements BsonOutput {
    @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable, org.bson.io.BsonOutput
    public void close() {
    }

    public abstract List<ByteBuf> getByteBuffers();

    public abstract int pipe(OutputStream outputStream) throws IOException;

    public abstract void truncateToPosition(int i);

    protected abstract void write(int i, int i2);

    @Override // java.io.OutputStream
    public void write(byte[] bArr) {
        write(bArr, 0, bArr.length);
    }

    @Override // java.io.OutputStream
    public void write(byte[] bArr, int i, int i2) {
        writeBytes(bArr, i, i2);
    }

    @Override // org.bson.io.BsonOutput
    public void writeBytes(byte[] bArr) {
        writeBytes(bArr, 0, bArr.length);
    }

    @Override // org.bson.io.BsonOutput
    public void writeInt32(int i) {
        write(i);
        write(i >> 8);
        write(i >> 16);
        write(i >> 24);
    }

    @Override // org.bson.io.BsonOutput
    public void writeInt32(int i, int i2) {
        write(i, i2);
        write(i + 1, i2 >> 8);
        write(i + 2, i2 >> 16);
        write(i + 3, i2 >> 24);
    }

    @Override // org.bson.io.BsonOutput
    public void writeInt64(long j) {
        write((byte) (j & 255));
        write((byte) ((j >> 8) & 255));
        write((byte) ((j >> 16) & 255));
        write((byte) ((j >> 24) & 255));
        write((byte) ((j >> 32) & 255));
        write((byte) ((j >> 40) & 255));
        write((byte) ((j >> 48) & 255));
        write((byte) ((j >> 56) & 255));
    }

    @Override // org.bson.io.BsonOutput
    public void writeDouble(double d) {
        writeLong(Double.doubleToRawLongBits(d));
    }

    @Override // org.bson.io.BsonOutput
    public void writeString(String str) {
        writeInt(0);
        writeInt32((getPosition() - r2) - 4, writeCharacters(str, false));
    }

    @Override // org.bson.io.BsonOutput
    public void writeCString(String str) {
        writeCharacters(str, true);
    }

    @Override // org.bson.io.BsonOutput
    public void writeObjectId(ObjectId objectId) {
        write(objectId.toByteArray());
    }

    public int size() {
        return getSize();
    }

    public byte[] toByteArray() {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(size());
            pipe(byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("should be impossible", e);
        }
    }

    @Override // java.io.OutputStream
    public void write(int i) {
        writeByte(i);
    }

    public void writeInt(int i) {
        writeInt32(i);
    }

    public String toString() {
        return getClass().getName() + " size: " + size() + " pos: " + getPosition();
    }

    public void writeLong(long j) {
        writeInt64(j);
    }

    private int writeCharacters(String str, boolean z) {
        int length = str.length();
        int iCharCount = 0;
        int i = 0;
        while (iCharCount < length) {
            int iCodePointAt = Character.codePointAt(str, iCharCount);
            if (z && iCodePointAt == 0) {
                throw new BsonSerializationException(String.format("BSON cstring '%s' is not valid because it contains a null character at index %d", str, Integer.valueOf(iCharCount)));
            }
            if (iCodePointAt < 128) {
                write((byte) iCodePointAt);
                i++;
            } else if (iCodePointAt < 2048) {
                write((byte) ((iCodePointAt >> 6) + Opcodes.CHECKCAST));
                write((byte) ((iCodePointAt & 63) + 128));
                i += 2;
            } else if (iCodePointAt < 65536) {
                write((byte) ((iCodePointAt >> 12) + 224));
                write((byte) (((iCodePointAt >> 6) & 63) + 128));
                write((byte) ((iCodePointAt & 63) + 128));
                i += 3;
            } else {
                write((byte) ((iCodePointAt >> 18) + 240));
                write((byte) (((iCodePointAt >> 12) & 63) + 128));
                write((byte) (((iCodePointAt >> 6) & 63) + 128));
                write((byte) ((iCodePointAt & 63) + 128));
                i += 4;
            }
            iCharCount += Character.charCount(iCodePointAt);
        }
        write(0);
        return i + 1;
    }
}
