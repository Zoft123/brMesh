package io.realm.internal;

import io.realm.RealmFieldType;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: classes4.dex */
public class OsObjectSchemaInfo implements NativeObject {
    private static final long nativeFinalizerPtr = nativeGetFinalizerPtr();
    private long nativePtr;

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeAddProperties(long j, long[] jArr, long[] jArr2);

    private static native long nativeCreateRealmObjectSchema(String str, String str2, boolean z);

    private static native String nativeGetClassName(long j);

    private static native long nativeGetFinalizerPtr();

    private static native long nativeGetPrimaryKeyProperty(long j);

    private static native long nativeGetProperty(long j, String str);

    private static native boolean nativeIsEmbedded(long j);

    public static class Builder {
        private final long[] computedPropertyPtrArray;
        private int computedPropertyPtrCurPos;
        private final boolean embedded;
        private final String internalClassName;
        private final long[] persistedPropertyPtrArray;
        private int persistedPropertyPtrCurPos;
        private final String publicClassName;

        public Builder(String str, boolean z, int i, int i2) {
            this.persistedPropertyPtrCurPos = 0;
            this.computedPropertyPtrCurPos = 0;
            this.publicClassName = "";
            this.internalClassName = str;
            this.embedded = z;
            this.persistedPropertyPtrArray = new long[i];
            this.computedPropertyPtrArray = new long[i2];
        }

        public Builder(String str, String str2, boolean z, int i, int i2) {
            this.persistedPropertyPtrCurPos = 0;
            this.computedPropertyPtrCurPos = 0;
            this.publicClassName = str;
            this.internalClassName = str2;
            this.embedded = z;
            this.persistedPropertyPtrArray = new long[i];
            this.computedPropertyPtrArray = new long[i2];
        }

        public Builder addPersistedProperty(String str, String str2, RealmFieldType realmFieldType, boolean z, boolean z2, boolean z3) {
            long jNativeCreatePersistedProperty = Property.nativeCreatePersistedProperty(str2, str, Property.convertFromRealmFieldType(realmFieldType, z3), z, z2);
            long[] jArr = this.persistedPropertyPtrArray;
            int i = this.persistedPropertyPtrCurPos;
            jArr[i] = jNativeCreatePersistedProperty;
            this.persistedPropertyPtrCurPos = i + 1;
            return this;
        }

        public Builder addPersistedValueListProperty(String str, String str2, RealmFieldType realmFieldType, boolean z) {
            long jNativeCreatePersistedProperty = Property.nativeCreatePersistedProperty(str2, str, Property.convertFromRealmFieldType(realmFieldType, z), false, false);
            long[] jArr = this.persistedPropertyPtrArray;
            int i = this.persistedPropertyPtrCurPos;
            jArr[i] = jNativeCreatePersistedProperty;
            this.persistedPropertyPtrCurPos = i + 1;
            return this;
        }

        public Builder addPersistedMapProperty(String str, String str2, RealmFieldType realmFieldType, boolean z) {
            long jNativeCreatePersistedProperty = Property.nativeCreatePersistedProperty(str2, str, Property.convertFromRealmFieldType(realmFieldType, z), false, false);
            long[] jArr = this.persistedPropertyPtrArray;
            int i = this.persistedPropertyPtrCurPos;
            jArr[i] = jNativeCreatePersistedProperty;
            this.persistedPropertyPtrCurPos = i + 1;
            return this;
        }

        public Builder addPersistedSetProperty(String str, String str2, RealmFieldType realmFieldType, boolean z) {
            long jNativeCreatePersistedProperty = Property.nativeCreatePersistedProperty(str2, str, Property.convertFromRealmFieldType(realmFieldType, z), false, false);
            long[] jArr = this.persistedPropertyPtrArray;
            int i = this.persistedPropertyPtrCurPos;
            jArr[i] = jNativeCreatePersistedProperty;
            this.persistedPropertyPtrCurPos = i + 1;
            return this;
        }

        public Builder addPersistedLinkProperty(String str, String str2, RealmFieldType realmFieldType, String str3) {
            long jNativeCreatePersistedLinkProperty = Property.nativeCreatePersistedLinkProperty(str2, str, Property.convertFromRealmFieldType(realmFieldType, false), str3);
            long[] jArr = this.persistedPropertyPtrArray;
            int i = this.persistedPropertyPtrCurPos;
            jArr[i] = jNativeCreatePersistedLinkProperty;
            this.persistedPropertyPtrCurPos = i + 1;
            return this;
        }

        public Builder addComputedLinkProperty(String str, String str2, String str3) {
            long jNativeCreateComputedLinkProperty = Property.nativeCreateComputedLinkProperty(str, str2, str3);
            long[] jArr = this.computedPropertyPtrArray;
            int i = this.computedPropertyPtrCurPos;
            jArr[i] = jNativeCreateComputedLinkProperty;
            this.computedPropertyPtrCurPos = i + 1;
            return this;
        }

        public OsObjectSchemaInfo build() {
            if (this.persistedPropertyPtrCurPos == -1 || this.computedPropertyPtrCurPos == -1) {
                throw new IllegalStateException("'OsObjectSchemaInfo.build()' has been called before on this object.");
            }
            OsObjectSchemaInfo osObjectSchemaInfo = new OsObjectSchemaInfo(this.publicClassName, this.internalClassName, this.embedded);
            OsObjectSchemaInfo.nativeAddProperties(osObjectSchemaInfo.nativePtr, this.persistedPropertyPtrArray, this.computedPropertyPtrArray);
            this.persistedPropertyPtrCurPos = -1;
            this.computedPropertyPtrCurPos = -1;
            return osObjectSchemaInfo;
        }
    }

    private OsObjectSchemaInfo(String str, String str2, boolean z) {
        this(nativeCreateRealmObjectSchema(str, str2, z));
    }

    OsObjectSchemaInfo(long j) {
        this.nativePtr = j;
        NativeContext.dummyContext.addReference(this);
    }

    public String getClassName() {
        return nativeGetClassName(this.nativePtr);
    }

    public Property getProperty(String str) {
        return new Property(nativeGetProperty(this.nativePtr, str));
    }

    @Nullable
    public Property getPrimaryKeyProperty() {
        if (nativeGetPrimaryKeyProperty(this.nativePtr) == 0) {
            return null;
        }
        return new Property(nativeGetPrimaryKeyProperty(this.nativePtr));
    }

    public boolean isEmbedded() {
        return nativeIsEmbedded(this.nativePtr);
    }

    @Override // io.realm.internal.NativeObject
    public long getNativePtr() {
        return this.nativePtr;
    }

    @Override // io.realm.internal.NativeObject
    public long getNativeFinalizerPtr() {
        return nativeFinalizerPtr;
    }
}
