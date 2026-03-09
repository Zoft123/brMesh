package org.bson.json;

/* JADX INFO: loaded from: classes4.dex */
class JsonStringBuffer implements JsonBuffer {
    private final String buffer;
    private boolean eof;
    private int position;

    @Override // org.bson.json.JsonBuffer
    public void discard(int i) {
    }

    JsonStringBuffer(String str) {
        this.buffer = str;
    }

    @Override // org.bson.json.JsonBuffer
    public int getPosition() {
        return this.position;
    }

    @Override // org.bson.json.JsonBuffer
    public int read() {
        if (this.eof) {
            throw new JsonParseException("Trying to read past EOF.");
        }
        if (this.position >= this.buffer.length()) {
            this.eof = true;
            return -1;
        }
        String str = this.buffer;
        int i = this.position;
        this.position = i + 1;
        return str.charAt(i);
    }

    @Override // org.bson.json.JsonBuffer
    public void unread(int i) {
        this.eof = false;
        if (i == -1 || this.buffer.charAt(this.position - 1) != i) {
            return;
        }
        this.position--;
    }

    @Override // org.bson.json.JsonBuffer
    public int mark() {
        return this.position;
    }

    @Override // org.bson.json.JsonBuffer
    public void reset(int i) {
        if (i > this.position) {
            throw new IllegalStateException("mark cannot reset ahead of position, only back");
        }
        this.position = i;
    }
}
