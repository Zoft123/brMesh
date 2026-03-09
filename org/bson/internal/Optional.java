package org.bson.internal;

import java.util.NoSuchElementException;

/* JADX INFO: loaded from: classes4.dex */
abstract class Optional<T> {
    private static final Optional<Object> NONE = new Optional<Object>() { // from class: org.bson.internal.Optional.1
        @Override // org.bson.internal.Optional
        public boolean isEmpty() {
            return true;
        }

        @Override // org.bson.internal.Optional
        public Object get() {
            throw new NoSuchElementException(".get call on None!");
        }
    };

    public abstract T get();

    public abstract boolean isEmpty();

    Optional() {
    }

    public static <T> Optional<T> empty() {
        return (Optional<T>) NONE;
    }

    public static <T> Optional<T> of(T t) {
        if (t == null) {
            return (Optional<T>) NONE;
        }
        return new Some(t);
    }

    public String toString() {
        return "None";
    }

    public boolean isDefined() {
        return !isEmpty();
    }

    public static class Some<T> extends Optional<T> {
        private final T value;

        @Override // org.bson.internal.Optional
        public boolean isEmpty() {
            return false;
        }

        @Override // org.bson.internal.Optional
        public /* bridge */ /* synthetic */ boolean isDefined() {
            return super.isDefined();
        }

        Some(T t) {
            this.value = t;
        }

        @Override // org.bson.internal.Optional
        public T get() {
            return this.value;
        }

        @Override // org.bson.internal.Optional
        public String toString() {
            return String.format("Some(%s)", this.value);
        }
    }
}
