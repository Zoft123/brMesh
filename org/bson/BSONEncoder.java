package org.bson;

import org.bson.io.OutputBuffer;

/* JADX INFO: loaded from: classes4.dex */
public interface BSONEncoder {
    void done();

    byte[] encode(BSONObject bSONObject);

    int putObject(BSONObject bSONObject);

    void set(OutputBuffer outputBuffer);
}
