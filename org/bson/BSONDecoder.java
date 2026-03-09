package org.bson;

import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: loaded from: classes4.dex */
public interface BSONDecoder {
    int decode(InputStream inputStream, BSONCallback bSONCallback) throws IOException;

    int decode(byte[] bArr, BSONCallback bSONCallback);

    BSONObject readObject(InputStream inputStream) throws IOException;

    BSONObject readObject(byte[] bArr);
}
