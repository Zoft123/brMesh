package org.bson;

/* JADX INFO: loaded from: classes4.dex */
public class BsonWriterSettings {
    private final int maxSerializationDepth;

    public BsonWriterSettings(int i) {
        this.maxSerializationDepth = i;
    }

    public BsonWriterSettings() {
        this(1024);
    }

    public int getMaxSerializationDepth() {
        return this.maxSerializationDepth;
    }
}
