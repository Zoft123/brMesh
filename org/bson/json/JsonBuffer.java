package org.bson.json;

/* JADX INFO: loaded from: classes4.dex */
interface JsonBuffer {
    void discard(int i);

    int getPosition();

    int mark();

    int read();

    void reset(int i);

    void unread(int i);
}
