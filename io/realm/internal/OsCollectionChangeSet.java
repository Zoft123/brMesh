package io.realm.internal;

import io.realm.OrderedCollectionChangeSet;
import java.util.Arrays;

/* JADX INFO: loaded from: classes4.dex */
public class OsCollectionChangeSet implements OrderedCollectionChangeSet, NativeObject {
    public static final int MAX_ARRAY_LENGTH = 2147483639;
    public static final int TYPE_DELETION = 0;
    public static final int TYPE_INSERTION = 1;
    public static final int TYPE_MODIFICATION = 2;
    private static long finalizerPtr = nativeGetFinalizerPtr();
    private final boolean firstAsyncCallback;
    private final long nativePtr;

    private static native long nativeGetFinalizerPtr();

    private static native int[] nativeGetIndices(long j, int i);

    private static native int[] nativeGetRanges(long j, int i);

    @Override // io.realm.OrderedCollectionChangeSet
    public Throwable getError() {
        return null;
    }

    public OsCollectionChangeSet(long j, boolean z) {
        this.nativePtr = j;
        this.firstAsyncCallback = z;
        NativeContext.dummyContext.addReference(this);
    }

    @Override // io.realm.OrderedCollectionChangeSet
    public OrderedCollectionChangeSet.State getState() {
        throw new UnsupportedOperationException("This method should be overridden in a subclass");
    }

    @Override // io.realm.OrderedCollectionChangeSet
    public int[] getDeletions() {
        return nativeGetIndices(this.nativePtr, 0);
    }

    @Override // io.realm.OrderedCollectionChangeSet
    public int[] getInsertions() {
        return nativeGetIndices(this.nativePtr, 1);
    }

    @Override // io.realm.OrderedCollectionChangeSet
    public int[] getChanges() {
        return nativeGetIndices(this.nativePtr, 2);
    }

    @Override // io.realm.OrderedCollectionChangeSet
    public OrderedCollectionChangeSet.Range[] getDeletionRanges() {
        return longArrayToRangeArray(nativeGetRanges(this.nativePtr, 0));
    }

    @Override // io.realm.OrderedCollectionChangeSet
    public OrderedCollectionChangeSet.Range[] getInsertionRanges() {
        return longArrayToRangeArray(nativeGetRanges(this.nativePtr, 1));
    }

    @Override // io.realm.OrderedCollectionChangeSet
    public OrderedCollectionChangeSet.Range[] getChangeRanges() {
        return longArrayToRangeArray(nativeGetRanges(this.nativePtr, 2));
    }

    public boolean isFirstAsyncCallback() {
        return this.firstAsyncCallback;
    }

    public boolean isEmpty() {
        return this.nativePtr == 0;
    }

    private OrderedCollectionChangeSet.Range[] longArrayToRangeArray(int[] iArr) {
        if (iArr == null) {
            return new OrderedCollectionChangeSet.Range[0];
        }
        int length = iArr.length / 2;
        OrderedCollectionChangeSet.Range[] rangeArr = new OrderedCollectionChangeSet.Range[length];
        for (int i = 0; i < length; i++) {
            int i2 = i * 2;
            rangeArr[i] = new OrderedCollectionChangeSet.Range(iArr[i2], iArr[i2 + 1]);
        }
        return rangeArr;
    }

    public String toString() {
        if (this.nativePtr == 0) {
            return "Change set is empty.";
        }
        return "Deletion Ranges: " + Arrays.toString(getDeletionRanges()) + "\nInsertion Ranges: " + Arrays.toString(getInsertionRanges()) + "\nChange Ranges: " + Arrays.toString(getChangeRanges());
    }

    public long getNativePtr() {
        return this.nativePtr;
    }

    public long getNativeFinalizerPtr() {
        return finalizerPtr;
    }
}
