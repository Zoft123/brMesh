package org.bson.types;

import java.io.Serializable;
import java.util.Date;

/* JADX INFO: loaded from: classes4.dex */
public final class BSONTimestamp implements Comparable<BSONTimestamp>, Serializable {
    private static final long serialVersionUID = -3268482672267936464L;
    private final int inc;
    private final Date time;

    public BSONTimestamp() {
        this.inc = 0;
        this.time = null;
    }

    public BSONTimestamp(int i, int i2) {
        this.time = new Date(((long) i) * 1000);
        this.inc = i2;
    }

    public int getTime() {
        Date date = this.time;
        if (date == null) {
            return 0;
        }
        return (int) (date.getTime() / 1000);
    }

    public int getInc() {
        return this.inc;
    }

    public String toString() {
        return "TS time:" + this.time + " inc:" + this.inc;
    }

    @Override // java.lang.Comparable
    public int compareTo(BSONTimestamp bSONTimestamp) {
        int inc;
        int inc2;
        if (getTime() != bSONTimestamp.getTime()) {
            inc = getTime();
            inc2 = bSONTimestamp.getTime();
        } else {
            inc = getInc();
            inc2 = bSONTimestamp.getInc();
        }
        return inc - inc2;
    }

    public int hashCode() {
        return ((this.inc + 31) * 31) + getTime();
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof BSONTimestamp) {
            BSONTimestamp bSONTimestamp = (BSONTimestamp) obj;
            if (getTime() == bSONTimestamp.getTime() && getInc() == bSONTimestamp.getInc()) {
                return true;
            }
        }
        return false;
    }
}
