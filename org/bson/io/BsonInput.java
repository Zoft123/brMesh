package org.bson.io;

import java.io.Closeable;
import org.bson.types.ObjectId;

/* JADX INFO: loaded from: classes4.dex */
public interface BsonInput extends Closeable {
    @Override // java.io.Closeable, java.lang.AutoCloseable
    void close();

    BsonInputMark getMark(int i);

    int getPosition();

    boolean hasRemaining();

    @Deprecated
    void mark(int i);

    byte readByte();

    void readBytes(byte[] bArr);

    void readBytes(byte[] bArr, int i, int i2);

    String readCString();

    double readDouble();

    int readInt32();

    long readInt64();

    ObjectId readObjectId();

    String readString();

    @Deprecated
    void reset();

    void skip(int i);

    void skipCString();
}
