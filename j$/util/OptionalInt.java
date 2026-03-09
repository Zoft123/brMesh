package j$.util;

import java.util.NoSuchElementException;

/* JADX INFO: loaded from: classes3.dex */
public final class OptionalInt {
    private static final OptionalInt EMPTY = new OptionalInt();
    private final boolean isPresent;
    private final int value;

    private OptionalInt() {
        this.isPresent = false;
        this.value = 0;
    }

    public static OptionalInt empty() {
        return EMPTY;
    }

    private OptionalInt(int i) {
        this.isPresent = true;
        this.value = i;
    }

    public static OptionalInt of(int i) {
        return new OptionalInt(i);
    }

    public int getAsInt() {
        if (!this.isPresent) {
            throw new NoSuchElementException("No value present");
        }
        return this.value;
    }

    public boolean isPresent() {
        return this.isPresent;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof OptionalInt)) {
            return false;
        }
        OptionalInt optionalInt = (OptionalInt) obj;
        boolean z = this.isPresent;
        return (z && optionalInt.isPresent) ? this.value == optionalInt.value : z == optionalInt.isPresent;
    }

    public int hashCode() {
        if (this.isPresent) {
            return this.value;
        }
        return 0;
    }

    public String toString() {
        return this.isPresent ? String.format("OptionalInt[%s]", Integer.valueOf(this.value)) : "OptionalInt.empty";
    }
}
