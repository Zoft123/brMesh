package io.realm;

import io.realm.internal.OsSet;
import java.util.Iterator;
import java.util.NoSuchElementException;

/* JADX INFO: compiled from: SetValueOperator.java */
/* JADX INFO: loaded from: classes4.dex */
abstract class SetIterator<E> implements Iterator<E> {
    protected final BaseRealm baseRealm;
    protected final OsSet osSet;
    private int pos = -1;

    SetIterator(OsSet osSet, BaseRealm baseRealm) {
        this.osSet = osSet;
        this.baseRealm = baseRealm;
    }

    @Override // java.util.Iterator
    public boolean hasNext() {
        return ((long) (this.pos + 1)) < this.osSet.size();
    }

    @Override // java.util.Iterator
    public E next() {
        this.pos++;
        long size = this.osSet.size();
        int i = this.pos;
        if (i >= size) {
            throw new NoSuchElementException("Cannot access index " + this.pos + " when size is " + size + ". Remember to check hasNext() before using next().");
        }
        return getValueAtIndex(i);
    }

    protected E getValueAtIndex(int i) {
        return (E) this.osSet.getValueAtIndex(i);
    }
}
