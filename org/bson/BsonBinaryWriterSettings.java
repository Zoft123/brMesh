package org.bson;

/* JADX INFO: loaded from: classes4.dex */
public class BsonBinaryWriterSettings {
    private final int maxDocumentSize;

    public BsonBinaryWriterSettings(int i) {
        this.maxDocumentSize = i;
    }

    public BsonBinaryWriterSettings() {
        this(Integer.MAX_VALUE);
    }

    public int getMaxDocumentSize() {
        return this.maxDocumentSize;
    }
}
