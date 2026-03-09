package io.realm.internal.core;

import io.realm.RealmAny;
import io.realm.RealmModel;
import io.realm.internal.NativeContext;
import io.realm.internal.NativeObject;
import io.realm.internal.OsSharedRealm;
import io.realm.internal.RealmObjectProxy;
import io.realm.internal.RealmProxyMediator;
import io.realm.internal.Row;
import io.realm.internal.Table;
import java.util.Date;
import java.util.UUID;
import org.bson.types.Decimal128;
import org.bson.types.ObjectId;

/* JADX INFO: loaded from: classes4.dex */
public class NativeRealmAny implements NativeObject {
    private static final long nativeFinalizerPtr = nativeGetFinalizerPtr();
    private final long nativePtr;

    private static native byte[] nativeAsBinary(long j);

    private static native boolean nativeAsBoolean(long j);

    private static native long nativeAsDate(long j);

    private static native long[] nativeAsDecimal128(long j);

    private static native double nativeAsDouble(long j);

    private static native float nativeAsFloat(long j);

    private static native long nativeAsLong(long j);

    private static native String nativeAsObjectId(long j);

    private static native String nativeAsString(long j);

    private static native String nativeAsUUID(long j);

    private static native long nativeCreateBinary(byte[] bArr);

    private static native long nativeCreateBoolean(boolean z);

    private static native long nativeCreateDate(long j);

    private static native long nativeCreateDecimal128(long j, long j2);

    private static native long nativeCreateDouble(double d);

    private static native long nativeCreateFloat(float f);

    private static native long nativeCreateLink(long j, long j2);

    private static native long nativeCreateLong(long j);

    private static native long nativeCreateNull();

    private static native long nativeCreateObjectId(String str);

    private static native long nativeCreateString(String str);

    private static native long nativeCreateUUID(String str);

    private static native boolean nativeEquals(long j, long j2);

    private static native long nativeGetFinalizerPtr();

    private static native long nativeGetRealmModelRowKey(long j);

    private static native String nativeGetRealmModelTableName(long j, long j2);

    private static native int nativeGetType(long j);

    public NativeRealmAny(long j) {
        this.nativePtr = j;
        NativeContext.dummyContext.addReference(this);
    }

    public NativeRealmAny(Boolean bool) {
        this(nativeCreateBoolean(bool.booleanValue()));
    }

    public NativeRealmAny(Number number) {
        this(nativeCreateLong(number.longValue()));
    }

    public NativeRealmAny(Float f) {
        this(nativeCreateFloat(f.floatValue()));
    }

    public NativeRealmAny(Double d) {
        this(nativeCreateDouble(d.doubleValue()));
    }

    public NativeRealmAny(String str) {
        this(nativeCreateString(str));
    }

    public NativeRealmAny(byte[] bArr) {
        this(nativeCreateBinary(bArr));
    }

    public NativeRealmAny(Date date) {
        this(nativeCreateDate(date.getTime()));
    }

    public NativeRealmAny(ObjectId objectId) {
        this(nativeCreateObjectId(objectId.toString()));
    }

    public NativeRealmAny(Decimal128 decimal128) {
        this(nativeCreateDecimal128(decimal128.getLow(), decimal128.getHigh()));
    }

    public NativeRealmAny(UUID uuid) {
        this(nativeCreateUUID(uuid.toString()));
    }

    public NativeRealmAny(RealmObjectProxy realmObjectProxy) {
        this(createRealmAnyLink(realmObjectProxy));
    }

    private static long createRealmAnyLink(RealmObjectProxy realmObjectProxy) {
        Row row$realm = realmObjectProxy.realmGet$proxyState().getRow$realm();
        return nativeCreateLink(row$realm.getTable().getNativePtr(), row$realm.getObjectKey());
    }

    public NativeRealmAny() {
        this(nativeCreateNull());
    }

    @Override // io.realm.internal.NativeObject
    public long getNativePtr() {
        return this.nativePtr;
    }

    @Override // io.realm.internal.NativeObject
    public long getNativeFinalizerPtr() {
        return nativeFinalizerPtr;
    }

    public RealmAny.Type getType() {
        return RealmAny.Type.fromNativeValue(nativeGetType(this.nativePtr));
    }

    public boolean asBoolean() {
        return nativeAsBoolean(this.nativePtr);
    }

    public long asLong() {
        return nativeAsLong(this.nativePtr);
    }

    public float asFloat() {
        return nativeAsFloat(this.nativePtr);
    }

    public double asDouble() {
        return nativeAsDouble(this.nativePtr);
    }

    public String asString() {
        return nativeAsString(this.nativePtr);
    }

    public byte[] asBinary() {
        return nativeAsBinary(this.nativePtr);
    }

    public Date asDate() {
        return new Date(nativeAsDate(this.nativePtr));
    }

    public ObjectId asObjectId() {
        return new ObjectId(nativeAsObjectId(this.nativePtr));
    }

    public Decimal128 asDecimal128() {
        long[] jArrNativeAsDecimal128 = nativeAsDecimal128(this.nativePtr);
        return Decimal128.fromIEEE754BIDEncoding(jArrNativeAsDecimal128[1], jArrNativeAsDecimal128[0]);
    }

    public UUID asUUID() {
        return UUID.fromString(nativeAsUUID(this.nativePtr));
    }

    public <T extends RealmModel> Class<T> getModelClass(OsSharedRealm osSharedRealm, RealmProxyMediator realmProxyMediator) {
        return realmProxyMediator.getClazz(Table.getClassNameForTable(nativeGetRealmModelTableName(this.nativePtr, osSharedRealm.getNativePtr())));
    }

    public String getRealmModelTableName(OsSharedRealm osSharedRealm) {
        return nativeGetRealmModelTableName(this.nativePtr, osSharedRealm.getNativePtr());
    }

    public long getRealmModelRowKey() {
        return nativeGetRealmModelRowKey(this.nativePtr);
    }

    public boolean coercedEquals(NativeRealmAny nativeRealmAny) {
        return nativeEquals(this.nativePtr, nativeRealmAny.nativePtr);
    }
}
