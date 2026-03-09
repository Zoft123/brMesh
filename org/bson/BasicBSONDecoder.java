package org.bson;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import org.bson.io.Bits;
import org.bson.io.ByteBufferBsonInput;

/* JADX INFO: loaded from: classes4.dex */
public class BasicBSONDecoder implements BSONDecoder {
    @Override // org.bson.BSONDecoder
    public BSONObject readObject(byte[] bArr) {
        BasicBSONCallback basicBSONCallback = new BasicBSONCallback();
        decode(bArr, basicBSONCallback);
        return (BSONObject) basicBSONCallback.get();
    }

    @Override // org.bson.BSONDecoder
    public BSONObject readObject(InputStream inputStream) throws IOException {
        return readObject(readFully(inputStream));
    }

    @Override // org.bson.BSONDecoder
    public int decode(byte[] bArr, BSONCallback bSONCallback) {
        BsonBinaryReader bsonBinaryReader = new BsonBinaryReader(new ByteBufferBsonInput(new ByteBufNIO(ByteBuffer.wrap(bArr))));
        try {
            new BSONCallbackAdapter(new BsonWriterSettings(), bSONCallback).pipe(bsonBinaryReader);
            return bsonBinaryReader.getBsonInput().getPosition();
        } finally {
            bsonBinaryReader.close();
        }
    }

    @Override // org.bson.BSONDecoder
    public int decode(InputStream inputStream, BSONCallback bSONCallback) throws IOException {
        return decode(readFully(inputStream), bSONCallback);
    }

    private byte[] readFully(InputStream inputStream) throws IOException {
        byte[] bArr = new byte[4];
        Bits.readFully(inputStream, bArr);
        int i = Bits.readInt(bArr);
        byte[] bArr2 = new byte[i];
        System.arraycopy(bArr, 0, bArr2, 0, 4);
        Bits.readFully(inputStream, bArr2, 4, i - 4);
        return bArr2;
    }
}
