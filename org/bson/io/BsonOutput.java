package org.bson.io;

import java.io.Closeable;
import org.bson.types.ObjectId;

/* JADX INFO: loaded from: classes4.dex */
public interface BsonOutput extends Closeable {
    void close();

    int getPosition();

    int getSize();

    void truncateToPosition(int i);

    void writeByte(int i);

    void writeBytes(byte[] bArr);

    void writeBytes(byte[] bArr, int i, int i2);

    void writeCString(String str);

    void writeDouble(double d);

    void writeInt32(int i);

    void writeInt32(int i, int i2);

    void writeInt64(long j);

    void writeObjectId(ObjectId objectId);

    void writeString(String str);
}
