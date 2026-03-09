package io.realm;

import io.realm.internal.OsCollection;
import io.realm.internal.OsList;
import io.realm.internal.OsResults;
import io.realm.internal.OsSet;
import io.realm.internal.PendingRow;
import io.realm.internal.RealmObjectProxy;
import io.realm.internal.RealmProxyMediator;
import io.realm.internal.Row;
import io.realm.internal.Table;
import io.realm.internal.TableQuery;
import io.realm.internal.Util;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import javax.annotation.Nullable;
import org.bson.types.Decimal128;
import org.bson.types.ObjectId;

/* JADX INFO: loaded from: classes4.dex */
public class RealmQuery<E> {
    private static final String ASYNC_QUERY_WRONG_THREAD_MESSAGE = "Async query cannot be created on current thread.";
    private static final String EMPTY_VALUES = "Non-empty 'values' must be provided.";
    private static final String PRIMITIVE_LISTS_NOT_SUPPORTED_ERROR_MESSAGE = "Queries on primitive lists are not yet supported";
    private static final String TYPE_MISMATCH = "Field '%s': type mismatch - %s expected.";
    private String className;
    private Class<E> clazz;
    private final boolean forValues;
    private final OsCollection osCollection;
    private final TableQuery query;
    private final BaseRealm realm;
    private final RealmObjectSchema schema;
    private final Table table;

    private static native String nativeSerializeQuery(long j);

    static <E extends RealmModel> RealmQuery<E> createQuery(Realm realm, Class<E> cls) {
        return new RealmQuery<>(realm, cls);
    }

    static <E extends RealmModel> RealmQuery<E> createDynamicQuery(DynamicRealm dynamicRealm, String str) {
        return new RealmQuery<>(dynamicRealm, str);
    }

    static <E> RealmQuery<E> createQueryFromResult(RealmResults<E> realmResults) {
        if (realmResults.classSpec == null) {
            return new RealmQuery<>((RealmResults<DynamicRealmObject>) realmResults, realmResults.className);
        }
        return new RealmQuery<>(realmResults, realmResults.classSpec);
    }

    static <E> RealmQuery<E> createQueryFromList(RealmList<E> realmList) {
        if (realmList.clazz == null) {
            return new RealmQuery<>(realmList.baseRealm, realmList.getOsList(), realmList.className);
        }
        return new RealmQuery<>(realmList.baseRealm, realmList.getOsList(), realmList.clazz);
    }

    private static boolean isClassForRealmModel(Class<?> cls) {
        return RealmModel.class.isAssignableFrom(cls);
    }

    private RealmQuery(Realm realm, Class<E> cls) {
        this.realm = realm;
        this.clazz = cls;
        boolean zIsClassForRealmModel = isClassForRealmModel(cls);
        this.forValues = !zIsClassForRealmModel;
        if (!zIsClassForRealmModel) {
            throw new UnsupportedOperationException(PRIMITIVE_LISTS_NOT_SUPPORTED_ERROR_MESSAGE);
        }
        RealmObjectSchema schemaForClass = realm.getSchema().getSchemaForClass((Class<? extends RealmModel>) cls);
        this.schema = schemaForClass;
        Table table = schemaForClass.getTable();
        this.table = table;
        this.osCollection = null;
        this.query = table.where();
    }

    private RealmQuery(RealmResults<E> realmResults, Class<E> cls) {
        BaseRealm baseRealm = realmResults.baseRealm;
        this.realm = baseRealm;
        this.clazz = cls;
        boolean zIsClassForRealmModel = isClassForRealmModel(cls);
        this.forValues = !zIsClassForRealmModel;
        if (!zIsClassForRealmModel) {
            throw new UnsupportedOperationException(PRIMITIVE_LISTS_NOT_SUPPORTED_ERROR_MESSAGE);
        }
        this.schema = baseRealm.getSchema().getSchemaForClass((Class<? extends RealmModel>) cls);
        this.table = realmResults.getTable();
        this.osCollection = null;
        this.query = realmResults.getOsResults().where();
    }

    private RealmQuery(BaseRealm baseRealm, OsList osList, Class<E> cls) {
        this.realm = baseRealm;
        this.clazz = cls;
        boolean zIsClassForRealmModel = isClassForRealmModel(cls);
        this.forValues = !zIsClassForRealmModel;
        if (!zIsClassForRealmModel) {
            throw new UnsupportedOperationException(PRIMITIVE_LISTS_NOT_SUPPORTED_ERROR_MESSAGE);
        }
        RealmObjectSchema schemaForClass = baseRealm.getSchema().getSchemaForClass((Class<? extends RealmModel>) cls);
        this.schema = schemaForClass;
        this.table = schemaForClass.getTable();
        this.osCollection = osList;
        this.query = osList.getQuery();
    }

    RealmQuery(BaseRealm baseRealm, OsSet osSet, Class<E> cls) {
        this.realm = baseRealm;
        this.clazz = cls;
        boolean zIsClassForRealmModel = isClassForRealmModel(cls);
        this.forValues = !zIsClassForRealmModel;
        if (!zIsClassForRealmModel) {
            throw new UnsupportedOperationException(PRIMITIVE_LISTS_NOT_SUPPORTED_ERROR_MESSAGE);
        }
        RealmObjectSchema schemaForClass = baseRealm.getSchema().getSchemaForClass((Class<? extends RealmModel>) cls);
        this.schema = schemaForClass;
        this.table = schemaForClass.getTable();
        this.osCollection = osSet;
        this.query = osSet.getQuery();
    }

    private RealmQuery(BaseRealm baseRealm, String str) {
        this.realm = baseRealm;
        this.className = str;
        this.forValues = false;
        RealmObjectSchema schemaForClass = baseRealm.getSchema().getSchemaForClass(str);
        this.schema = schemaForClass;
        Table table = schemaForClass.getTable();
        this.table = table;
        this.query = table.where();
        this.osCollection = null;
    }

    private RealmQuery(RealmResults<DynamicRealmObject> realmResults, String str) {
        BaseRealm baseRealm = realmResults.baseRealm;
        this.realm = baseRealm;
        this.className = str;
        this.forValues = false;
        RealmObjectSchema schemaForClass = baseRealm.getSchema().getSchemaForClass(str);
        this.schema = schemaForClass;
        this.table = schemaForClass.getTable();
        this.query = realmResults.getOsResults().where();
        this.osCollection = null;
    }

    private RealmQuery(BaseRealm baseRealm, OsList osList, String str) {
        this.realm = baseRealm;
        this.className = str;
        this.forValues = false;
        RealmObjectSchema schemaForClass = baseRealm.getSchema().getSchemaForClass(str);
        this.schema = schemaForClass;
        this.table = schemaForClass.getTable();
        this.query = osList.getQuery();
        this.osCollection = osList;
    }

    public boolean isValid() {
        BaseRealm baseRealm = this.realm;
        if (baseRealm != null && !baseRealm.isClosed()) {
            OsCollection osCollection = this.osCollection;
            if (osCollection != null) {
                return osCollection.isValid();
            }
            Table table = this.table;
            if (table != null && table.isValid()) {
                return true;
            }
        }
        return false;
    }

    public RealmQuery<E> isNull(String str) {
        this.realm.checkIfValid();
        this.query.isNull(this.realm.getSchema().getKeyPathMapping(), str);
        return this;
    }

    public RealmQuery<E> isNotNull(String str) {
        this.realm.checkIfValid();
        this.query.isNotNull(this.realm.getSchema().getKeyPathMapping(), str);
        return this;
    }

    public RealmQuery<E> equalTo(String str, @Nullable String str2) {
        return equalTo(str, str2, Case.SENSITIVE);
    }

    public RealmQuery<E> equalTo(String str, @Nullable String str2, Case r4) {
        this.realm.checkIfValid();
        equalTo(str, RealmAny.valueOf(str2), r4);
        return this;
    }

    public RealmQuery<E> equalTo(String str, RealmAny realmAny, Case r4) {
        this.realm.checkIfValid();
        if (r4 == Case.SENSITIVE) {
            this.query.equalTo(this.realm.getSchema().getKeyPathMapping(), str, realmAny);
            return this;
        }
        this.query.equalToInsensitive(this.realm.getSchema().getKeyPathMapping(), str, realmAny);
        return this;
    }

    public RealmQuery<E> equalTo(String str, @Nullable Decimal128 decimal128) {
        this.realm.checkIfValid();
        this.query.equalTo(this.realm.getSchema().getKeyPathMapping(), str, RealmAny.valueOf(decimal128));
        return this;
    }

    public RealmQuery<E> equalTo(String str, @Nullable ObjectId objectId) {
        this.realm.checkIfValid();
        this.query.equalTo(this.realm.getSchema().getKeyPathMapping(), str, RealmAny.valueOf(objectId));
        return this;
    }

    public RealmQuery<E> equalTo(String str, @Nullable UUID uuid) {
        this.realm.checkIfValid();
        this.query.equalTo(this.realm.getSchema().getKeyPathMapping(), str, RealmAny.valueOf(uuid));
        return this;
    }

    public RealmQuery<E> equalTo(String str, @Nullable Byte b) {
        this.realm.checkIfValid();
        this.query.equalTo(this.realm.getSchema().getKeyPathMapping(), str, RealmAny.valueOf(b));
        return this;
    }

    public RealmQuery<E> equalTo(String str, @Nullable byte[] bArr) {
        this.realm.checkIfValid();
        this.query.equalTo(this.realm.getSchema().getKeyPathMapping(), str, RealmAny.valueOf(bArr));
        return this;
    }

    public RealmQuery<E> equalTo(String str, @Nullable Short sh) {
        this.realm.checkIfValid();
        this.query.equalTo(this.realm.getSchema().getKeyPathMapping(), str, RealmAny.valueOf(sh));
        return this;
    }

    public RealmQuery<E> equalTo(String str, @Nullable Integer num) {
        this.realm.checkIfValid();
        this.query.equalTo(this.realm.getSchema().getKeyPathMapping(), str, RealmAny.valueOf(num));
        return this;
    }

    public RealmQuery<E> equalTo(String str, @Nullable Long l) {
        this.realm.checkIfValid();
        this.query.equalTo(this.realm.getSchema().getKeyPathMapping(), str, RealmAny.valueOf(l));
        return this;
    }

    public RealmQuery<E> equalTo(String str, @Nullable Double d) {
        this.realm.checkIfValid();
        this.query.equalTo(this.realm.getSchema().getKeyPathMapping(), str, RealmAny.valueOf(d));
        return this;
    }

    public RealmQuery<E> equalTo(String str, @Nullable Float f) {
        this.realm.checkIfValid();
        this.query.equalTo(this.realm.getSchema().getKeyPathMapping(), str, RealmAny.valueOf(f));
        return this;
    }

    public RealmQuery<E> equalTo(String str, @Nullable Boolean bool) {
        this.realm.checkIfValid();
        this.query.equalTo(this.realm.getSchema().getKeyPathMapping(), str, RealmAny.valueOf(bool));
        return this;
    }

    public RealmQuery<E> equalTo(String str, @Nullable Date date) {
        this.realm.checkIfValid();
        this.query.equalTo(this.realm.getSchema().getKeyPathMapping(), str, RealmAny.valueOf(date));
        return this;
    }

    public RealmQuery<E> equalTo(String str, RealmAny realmAny) {
        this.realm.checkIfValid();
        this.query.equalTo(this.realm.getSchema().getKeyPathMapping(), str, realmAny);
        return this;
    }

    public RealmQuery<E> in(String str, @Nullable String[] strArr) {
        return in(str, strArr, Case.SENSITIVE);
    }

    public RealmQuery<E> in(String str, @Nullable String[] strArr, Case r6) {
        this.realm.checkIfValid();
        if (strArr == null || strArr.length == 0) {
            alwaysFalse();
            return this;
        }
        RealmAny[] realmAnyArr = new RealmAny[strArr.length];
        for (int i = 0; i < strArr.length; i++) {
            String str2 = strArr[i];
            if (str2 != null) {
                realmAnyArr[i] = RealmAny.valueOf(str2);
            } else {
                realmAnyArr[i] = null;
            }
        }
        if (r6 == Case.SENSITIVE) {
            this.query.in(this.realm.getSchema().getKeyPathMapping(), str, realmAnyArr);
            return this;
        }
        this.query.inInsensitive(this.realm.getSchema().getKeyPathMapping(), str, realmAnyArr);
        return this;
    }

    public RealmQuery<E> in(String str, @Nullable Byte[] bArr) {
        this.realm.checkIfValid();
        if (bArr == null || bArr.length == 0) {
            alwaysFalse();
            return this;
        }
        RealmAny[] realmAnyArr = new RealmAny[bArr.length];
        for (int i = 0; i < bArr.length; i++) {
            realmAnyArr[i] = RealmAny.valueOf(bArr[i]);
        }
        this.query.in(this.realm.getSchema().getKeyPathMapping(), str, realmAnyArr);
        return this;
    }

    public RealmQuery<E> in(String str, @Nullable Short[] shArr) {
        this.realm.checkIfValid();
        if (shArr == null || shArr.length == 0) {
            alwaysFalse();
            return this;
        }
        RealmAny[] realmAnyArr = new RealmAny[shArr.length];
        for (int i = 0; i < shArr.length; i++) {
            realmAnyArr[i] = RealmAny.valueOf(shArr[i]);
        }
        this.query.in(this.realm.getSchema().getKeyPathMapping(), str, realmAnyArr);
        return this;
    }

    public RealmQuery<E> in(String str, @Nullable Integer[] numArr) {
        this.realm.checkIfValid();
        if (numArr == null || numArr.length == 0) {
            alwaysFalse();
            return this;
        }
        RealmAny[] realmAnyArr = new RealmAny[numArr.length];
        for (int i = 0; i < numArr.length; i++) {
            realmAnyArr[i] = RealmAny.valueOf(numArr[i]);
        }
        this.query.in(this.realm.getSchema().getKeyPathMapping(), str, realmAnyArr);
        return this;
    }

    public RealmQuery<E> in(String str, @Nullable Long[] lArr) {
        this.realm.checkIfValid();
        if (lArr == null || lArr.length == 0) {
            alwaysFalse();
            return this;
        }
        RealmAny[] realmAnyArr = new RealmAny[lArr.length];
        for (int i = 0; i < lArr.length; i++) {
            realmAnyArr[i] = RealmAny.valueOf(lArr[i]);
        }
        this.query.in(this.realm.getSchema().getKeyPathMapping(), str, realmAnyArr);
        return this;
    }

    public RealmQuery<E> in(String str, @Nullable Double[] dArr) {
        this.realm.checkIfValid();
        if (dArr == null || dArr.length == 0) {
            alwaysFalse();
            return this;
        }
        RealmAny[] realmAnyArr = new RealmAny[dArr.length];
        for (int i = 0; i < dArr.length; i++) {
            realmAnyArr[i] = RealmAny.valueOf(dArr[i]);
        }
        this.query.in(this.realm.getSchema().getKeyPathMapping(), str, realmAnyArr);
        return this;
    }

    public RealmQuery<E> in(String str, @Nullable Float[] fArr) {
        this.realm.checkIfValid();
        if (fArr == null || fArr.length == 0) {
            alwaysFalse();
            return this;
        }
        RealmAny[] realmAnyArr = new RealmAny[fArr.length];
        for (int i = 0; i < fArr.length; i++) {
            realmAnyArr[i] = RealmAny.valueOf(fArr[i]);
        }
        this.query.in(this.realm.getSchema().getKeyPathMapping(), str, realmAnyArr);
        return this;
    }

    public RealmQuery<E> in(String str, @Nullable Boolean[] boolArr) {
        this.realm.checkIfValid();
        if (boolArr == null || boolArr.length == 0) {
            alwaysFalse();
            return this;
        }
        RealmAny[] realmAnyArr = new RealmAny[boolArr.length];
        for (int i = 0; i < boolArr.length; i++) {
            realmAnyArr[i] = RealmAny.valueOf(boolArr[i]);
        }
        this.query.in(this.realm.getSchema().getKeyPathMapping(), str, realmAnyArr);
        return this;
    }

    public RealmQuery<E> in(String str, @Nullable Date[] dateArr) {
        this.realm.checkIfValid();
        if (dateArr == null || dateArr.length == 0) {
            alwaysFalse();
            return this;
        }
        RealmAny[] realmAnyArr = new RealmAny[dateArr.length];
        for (int i = 0; i < dateArr.length; i++) {
            realmAnyArr[i] = RealmAny.valueOf(dateArr[i]);
        }
        this.query.in(this.realm.getSchema().getKeyPathMapping(), str, realmAnyArr);
        return this;
    }

    public RealmQuery<E> in(String str, RealmAny[] realmAnyArr) {
        this.realm.checkIfValid();
        if (realmAnyArr == null || realmAnyArr.length == 0) {
            alwaysFalse();
            return this;
        }
        RealmAny[] realmAnyArr2 = new RealmAny[realmAnyArr.length];
        for (int i = 0; i < realmAnyArr.length; i++) {
            RealmAny realmAnyNullValue = realmAnyArr[i];
            if (realmAnyNullValue == null) {
                realmAnyNullValue = RealmAny.nullValue();
            }
            realmAnyArr2[i] = realmAnyNullValue;
        }
        this.query.in(this.realm.getSchema().getKeyPathMapping(), str, realmAnyArr2);
        return this;
    }

    public RealmQuery<E> notEqualTo(String str, @Nullable String str2) {
        return notEqualTo(str, str2, Case.SENSITIVE);
    }

    public RealmQuery<E> notEqualTo(String str, @Nullable String str2, Case r4) {
        this.realm.checkIfValid();
        notEqualTo(str, RealmAny.valueOf(str2), r4);
        return this;
    }

    public RealmQuery<E> notEqualTo(String str, RealmAny realmAny, Case r4) {
        this.realm.checkIfValid();
        if (r4 == Case.SENSITIVE) {
            this.query.notEqualTo(this.realm.getSchema().getKeyPathMapping(), str, realmAny);
            return this;
        }
        this.query.notEqualToInsensitive(this.realm.getSchema().getKeyPathMapping(), str, realmAny);
        return this;
    }

    public RealmQuery<E> notEqualTo(String str, Decimal128 decimal128) {
        this.realm.checkIfValid();
        this.query.notEqualTo(this.realm.getSchema().getKeyPathMapping(), str, RealmAny.valueOf(decimal128));
        return this;
    }

    public RealmQuery<E> notEqualTo(String str, ObjectId objectId) {
        this.realm.checkIfValid();
        this.query.notEqualTo(this.realm.getSchema().getKeyPathMapping(), str, RealmAny.valueOf(objectId));
        return this;
    }

    public RealmQuery<E> notEqualTo(String str, UUID uuid) {
        this.realm.checkIfValid();
        this.query.notEqualTo(this.realm.getSchema().getKeyPathMapping(), str, RealmAny.valueOf(uuid));
        return this;
    }

    public RealmQuery<E> notEqualTo(String str, RealmAny realmAny) {
        this.realm.checkIfValid();
        notEqualTo(str, realmAny, Case.SENSITIVE);
        return this;
    }

    public RealmQuery<E> notEqualTo(String str, @Nullable Byte b) {
        this.realm.checkIfValid();
        this.query.notEqualTo(this.realm.getSchema().getKeyPathMapping(), str, RealmAny.valueOf(b));
        return this;
    }

    public RealmQuery<E> notEqualTo(String str, @Nullable byte[] bArr) {
        this.realm.checkIfValid();
        this.query.notEqualTo(this.realm.getSchema().getKeyPathMapping(), str, RealmAny.valueOf(bArr));
        return this;
    }

    public RealmQuery<E> notEqualTo(String str, @Nullable Short sh) {
        this.realm.checkIfValid();
        this.query.notEqualTo(this.realm.getSchema().getKeyPathMapping(), str, RealmAny.valueOf(sh));
        return this;
    }

    public RealmQuery<E> notEqualTo(String str, @Nullable Integer num) {
        this.realm.checkIfValid();
        this.query.notEqualTo(this.realm.getSchema().getKeyPathMapping(), str, RealmAny.valueOf(num));
        return this;
    }

    public RealmQuery<E> notEqualTo(String str, @Nullable Long l) {
        this.realm.checkIfValid();
        this.query.notEqualTo(this.realm.getSchema().getKeyPathMapping(), str, RealmAny.valueOf(l));
        return this;
    }

    public RealmQuery<E> notEqualTo(String str, @Nullable Double d) {
        this.realm.checkIfValid();
        this.query.notEqualTo(this.realm.getSchema().getKeyPathMapping(), str, RealmAny.valueOf(d));
        return this;
    }

    public RealmQuery<E> notEqualTo(String str, @Nullable Float f) {
        this.realm.checkIfValid();
        this.query.notEqualTo(this.realm.getSchema().getKeyPathMapping(), str, RealmAny.valueOf(f));
        return this;
    }

    public RealmQuery<E> notEqualTo(String str, @Nullable Boolean bool) {
        this.realm.checkIfValid();
        this.query.notEqualTo(this.realm.getSchema().getKeyPathMapping(), str, RealmAny.valueOf(bool));
        return this;
    }

    public RealmQuery<E> notEqualTo(String str, @Nullable Date date) {
        this.realm.checkIfValid();
        this.query.notEqualTo(this.realm.getSchema().getKeyPathMapping(), str, RealmAny.valueOf(date));
        return this;
    }

    public RealmQuery<E> greaterThan(String str, int i) {
        this.realm.checkIfValid();
        this.query.greaterThan(this.realm.getSchema().getKeyPathMapping(), str, RealmAny.valueOf(Integer.valueOf(i)));
        return this;
    }

    public RealmQuery<E> greaterThan(String str, long j) {
        this.realm.checkIfValid();
        this.query.greaterThan(this.realm.getSchema().getKeyPathMapping(), str, RealmAny.valueOf(Long.valueOf(j)));
        return this;
    }

    public RealmQuery<E> greaterThan(String str, double d) {
        this.realm.checkIfValid();
        this.query.greaterThan(this.realm.getSchema().getKeyPathMapping(), str, RealmAny.valueOf(Double.valueOf(d)));
        return this;
    }

    public RealmQuery<E> greaterThan(String str, float f) {
        this.realm.checkIfValid();
        this.query.greaterThan(this.realm.getSchema().getKeyPathMapping(), str, RealmAny.valueOf(Float.valueOf(f)));
        return this;
    }

    public RealmQuery<E> greaterThan(String str, Date date) {
        this.realm.checkIfValid();
        this.query.greaterThan(this.realm.getSchema().getKeyPathMapping(), str, RealmAny.valueOf(date));
        return this;
    }

    public RealmQuery<E> greaterThan(String str, RealmAny realmAny) {
        this.realm.checkIfValid();
        this.query.greaterThan(this.realm.getSchema().getKeyPathMapping(), str, realmAny);
        return this;
    }

    public RealmQuery<E> greaterThan(String str, Decimal128 decimal128) {
        this.realm.checkIfValid();
        this.query.greaterThan(this.realm.getSchema().getKeyPathMapping(), str, RealmAny.valueOf(decimal128));
        return this;
    }

    public RealmQuery<E> greaterThan(String str, ObjectId objectId) {
        this.realm.checkIfValid();
        this.query.greaterThan(this.realm.getSchema().getKeyPathMapping(), str, RealmAny.valueOf(objectId));
        return this;
    }

    public RealmQuery<E> greaterThan(String str, UUID uuid) {
        this.realm.checkIfValid();
        this.query.greaterThan(this.realm.getSchema().getKeyPathMapping(), str, RealmAny.valueOf(uuid));
        return this;
    }

    public RealmQuery<E> greaterThanOrEqualTo(String str, int i) {
        this.realm.checkIfValid();
        this.query.greaterThanOrEqual(this.realm.getSchema().getKeyPathMapping(), str, RealmAny.valueOf(Integer.valueOf(i)));
        return this;
    }

    public RealmQuery<E> greaterThanOrEqualTo(String str, long j) {
        this.realm.checkIfValid();
        this.query.greaterThanOrEqual(this.realm.getSchema().getKeyPathMapping(), str, RealmAny.valueOf(Long.valueOf(j)));
        return this;
    }

    public RealmQuery<E> greaterThanOrEqualTo(String str, double d) {
        this.realm.checkIfValid();
        this.query.greaterThanOrEqual(this.realm.getSchema().getKeyPathMapping(), str, RealmAny.valueOf(Double.valueOf(d)));
        return this;
    }

    public RealmQuery<E> greaterThanOrEqualTo(String str, float f) {
        this.realm.checkIfValid();
        this.query.greaterThanOrEqual(this.realm.getSchema().getKeyPathMapping(), str, RealmAny.valueOf(Float.valueOf(f)));
        return this;
    }

    public RealmQuery<E> greaterThanOrEqualTo(String str, Date date) {
        this.realm.checkIfValid();
        this.query.greaterThanOrEqual(this.realm.getSchema().getKeyPathMapping(), str, RealmAny.valueOf(date));
        return this;
    }

    public RealmQuery<E> greaterThanOrEqualTo(String str, Decimal128 decimal128) {
        this.realm.checkIfValid();
        this.query.greaterThanOrEqual(this.realm.getSchema().getKeyPathMapping(), str, RealmAny.valueOf(decimal128));
        return this;
    }

    public RealmQuery<E> greaterThanOrEqualTo(String str, ObjectId objectId) {
        this.realm.checkIfValid();
        this.query.greaterThanOrEqual(this.realm.getSchema().getKeyPathMapping(), str, RealmAny.valueOf(objectId));
        return this;
    }

    public RealmQuery<E> greaterThanOrEqualTo(String str, UUID uuid) {
        this.realm.checkIfValid();
        this.query.greaterThanOrEqual(this.realm.getSchema().getKeyPathMapping(), str, RealmAny.valueOf(uuid));
        return this;
    }

    public RealmQuery<E> greaterThanOrEqualTo(String str, RealmAny realmAny) {
        this.realm.checkIfValid();
        this.query.greaterThanOrEqual(this.realm.getSchema().getKeyPathMapping(), str, realmAny);
        return this;
    }

    public RealmQuery<E> lessThan(String str, int i) {
        this.realm.checkIfValid();
        this.query.lessThan(this.realm.getSchema().getKeyPathMapping(), str, RealmAny.valueOf(Integer.valueOf(i)));
        return this;
    }

    public RealmQuery<E> lessThan(String str, long j) {
        this.realm.checkIfValid();
        this.query.lessThan(this.realm.getSchema().getKeyPathMapping(), str, RealmAny.valueOf(Long.valueOf(j)));
        return this;
    }

    public RealmQuery<E> lessThan(String str, Decimal128 decimal128) {
        this.realm.checkIfValid();
        this.query.lessThan(this.realm.getSchema().getKeyPathMapping(), str, RealmAny.valueOf(decimal128));
        return this;
    }

    public RealmQuery<E> lessThan(String str, ObjectId objectId) {
        this.realm.checkIfValid();
        this.query.lessThan(this.realm.getSchema().getKeyPathMapping(), str, RealmAny.valueOf(objectId));
        return this;
    }

    public RealmQuery<E> lessThan(String str, UUID uuid) {
        this.realm.checkIfValid();
        this.query.lessThan(this.realm.getSchema().getKeyPathMapping(), str, RealmAny.valueOf(uuid));
        return this;
    }

    public RealmQuery<E> lessThan(String str, double d) {
        this.realm.checkIfValid();
        this.query.lessThan(this.realm.getSchema().getKeyPathMapping(), str, RealmAny.valueOf(Double.valueOf(d)));
        return this;
    }

    public RealmQuery<E> lessThan(String str, float f) {
        this.realm.checkIfValid();
        this.query.lessThan(this.realm.getSchema().getKeyPathMapping(), str, RealmAny.valueOf(Float.valueOf(f)));
        return this;
    }

    public RealmQuery<E> lessThan(String str, Date date) {
        this.realm.checkIfValid();
        this.query.lessThan(this.realm.getSchema().getKeyPathMapping(), str, RealmAny.valueOf(date));
        return this;
    }

    public RealmQuery<E> lessThan(String str, RealmAny realmAny) {
        this.realm.checkIfValid();
        this.query.lessThan(this.realm.getSchema().getKeyPathMapping(), str, realmAny);
        return this;
    }

    public RealmQuery<E> lessThanOrEqualTo(String str, int i) {
        this.realm.checkIfValid();
        this.query.lessThanOrEqual(this.realm.getSchema().getKeyPathMapping(), str, RealmAny.valueOf(Integer.valueOf(i)));
        return this;
    }

    public RealmQuery<E> lessThanOrEqualTo(String str, long j) {
        this.realm.checkIfValid();
        this.query.lessThanOrEqual(this.realm.getSchema().getKeyPathMapping(), str, RealmAny.valueOf(Long.valueOf(j)));
        return this;
    }

    public RealmQuery<E> lessThanOrEqualTo(String str, Decimal128 decimal128) {
        this.realm.checkIfValid();
        this.query.lessThanOrEqual(this.realm.getSchema().getKeyPathMapping(), str, RealmAny.valueOf(decimal128));
        return this;
    }

    public RealmQuery<E> lessThanOrEqualTo(String str, ObjectId objectId) {
        this.realm.checkIfValid();
        this.query.lessThanOrEqual(this.realm.getSchema().getKeyPathMapping(), str, RealmAny.valueOf(objectId));
        return this;
    }

    public RealmQuery<E> lessThanOrEqualTo(String str, UUID uuid) {
        this.realm.checkIfValid();
        this.query.lessThanOrEqual(this.realm.getSchema().getKeyPathMapping(), str, RealmAny.valueOf(uuid));
        return this;
    }

    public RealmQuery<E> lessThanOrEqualTo(String str, double d) {
        this.realm.checkIfValid();
        this.query.lessThanOrEqual(this.realm.getSchema().getKeyPathMapping(), str, RealmAny.valueOf(Double.valueOf(d)));
        return this;
    }

    public RealmQuery<E> lessThanOrEqualTo(String str, float f) {
        this.realm.checkIfValid();
        this.query.lessThanOrEqual(this.realm.getSchema().getKeyPathMapping(), str, RealmAny.valueOf(Float.valueOf(f)));
        return this;
    }

    public RealmQuery<E> lessThanOrEqualTo(String str, Date date) {
        this.realm.checkIfValid();
        this.query.lessThanOrEqual(this.realm.getSchema().getKeyPathMapping(), str, RealmAny.valueOf(date));
        return this;
    }

    public RealmQuery<E> lessThanOrEqualTo(String str, RealmAny realmAny) {
        this.realm.checkIfValid();
        this.query.lessThanOrEqual(this.realm.getSchema().getKeyPathMapping(), str, realmAny);
        return this;
    }

    public RealmQuery<E> between(String str, int i, int i2) {
        this.realm.checkIfValid();
        this.query.between(this.realm.getSchema().getKeyPathMapping(), str, RealmAny.valueOf(Integer.valueOf(i)), RealmAny.valueOf(Integer.valueOf(i2)));
        return this;
    }

    public RealmQuery<E> between(String str, long j, long j2) {
        this.realm.checkIfValid();
        this.query.between(this.realm.getSchema().getKeyPathMapping(), str, RealmAny.valueOf(Long.valueOf(j)), RealmAny.valueOf(Long.valueOf(j2)));
        return this;
    }

    public RealmQuery<E> between(String str, double d, double d2) {
        this.realm.checkIfValid();
        this.query.between(this.realm.getSchema().getKeyPathMapping(), str, RealmAny.valueOf(Double.valueOf(d)), RealmAny.valueOf(Double.valueOf(d2)));
        return this;
    }

    public RealmQuery<E> between(String str, float f, float f2) {
        this.realm.checkIfValid();
        this.query.between(this.realm.getSchema().getKeyPathMapping(), str, RealmAny.valueOf(Float.valueOf(f)), RealmAny.valueOf(Float.valueOf(f2)));
        return this;
    }

    public RealmQuery<E> between(String str, Date date, Date date2) {
        this.realm.checkIfValid();
        this.query.between(this.realm.getSchema().getKeyPathMapping(), str, RealmAny.valueOf(date), RealmAny.valueOf(date2));
        return this;
    }

    public RealmQuery<E> between(String str, RealmAny realmAny, RealmAny realmAny2) {
        this.realm.checkIfValid();
        this.query.between(this.realm.getSchema().getKeyPathMapping(), str, realmAny, realmAny2);
        return this;
    }

    public RealmQuery<E> between(String str, Decimal128 decimal128, Decimal128 decimal1282) {
        this.realm.checkIfValid();
        this.query.between(this.realm.getSchema().getKeyPathMapping(), str, RealmAny.valueOf(decimal128), RealmAny.valueOf(decimal1282));
        return this;
    }

    public RealmQuery<E> contains(String str, String str2) {
        return contains(str, str2, Case.SENSITIVE);
    }

    public RealmQuery<E> contains(String str, RealmAny realmAny) {
        return contains(str, realmAny, Case.SENSITIVE);
    }

    public RealmQuery<E> contains(String str, String str2, Case r4) {
        Util.checkNull(str2, "value");
        this.realm.checkIfValid();
        contains(str, RealmAny.valueOf(str2), r4);
        return this;
    }

    public RealmQuery<E> contains(String str, RealmAny realmAny, Case r4) {
        this.realm.checkIfValid();
        if (r4 == Case.SENSITIVE) {
            this.query.contains(this.realm.getSchema().getKeyPathMapping(), str, realmAny);
            return this;
        }
        this.query.containsInsensitive(this.realm.getSchema().getKeyPathMapping(), str, realmAny);
        return this;
    }

    public RealmQuery<E> beginsWith(String str, String str2) {
        return beginsWith(str, str2, Case.SENSITIVE);
    }

    public RealmQuery<E> beginsWith(String str, RealmAny realmAny) {
        return beginsWith(str, realmAny, Case.SENSITIVE);
    }

    public RealmQuery<E> beginsWith(String str, String str2, Case r4) {
        Util.checkNull(str2, "value");
        this.realm.checkIfValid();
        beginsWith(str, RealmAny.valueOf(str2), r4);
        return this;
    }

    public RealmQuery<E> beginsWith(String str, RealmAny realmAny, Case r4) {
        this.realm.checkIfValid();
        if (r4 == Case.SENSITIVE) {
            this.query.beginsWith(this.realm.getSchema().getKeyPathMapping(), str, realmAny);
            return this;
        }
        this.query.beginsWithInsensitive(this.realm.getSchema().getKeyPathMapping(), str, realmAny);
        return this;
    }

    public RealmQuery<E> endsWith(String str, String str2) {
        return endsWith(str, str2, Case.SENSITIVE);
    }

    public RealmQuery<E> endsWith(String str, RealmAny realmAny) {
        return endsWith(str, realmAny, Case.SENSITIVE);
    }

    public RealmQuery<E> endsWith(String str, String str2, Case r4) {
        Util.checkNull(str2, "value");
        this.realm.checkIfValid();
        endsWith(str, RealmAny.valueOf(str2), r4);
        return this;
    }

    public RealmQuery<E> endsWith(String str, RealmAny realmAny, Case r4) {
        this.realm.checkIfValid();
        if (r4 == Case.SENSITIVE) {
            this.query.endsWith(this.realm.getSchema().getKeyPathMapping(), str, realmAny);
            return this;
        }
        this.query.endsWithInsensitive(this.realm.getSchema().getKeyPathMapping(), str, realmAny);
        return this;
    }

    public RealmQuery<E> like(String str, String str2) {
        return like(str, str2, Case.SENSITIVE);
    }

    public RealmQuery<E> like(String str, RealmAny realmAny) {
        return like(str, realmAny, Case.SENSITIVE);
    }

    public RealmQuery<E> like(String str, String str2, Case r4) {
        Util.checkNull(str2, "value");
        this.realm.checkIfValid();
        like(str, RealmAny.valueOf(str2), r4);
        return this;
    }

    public RealmQuery<E> like(String str, RealmAny realmAny, Case r4) {
        this.realm.checkIfValid();
        if (r4 == Case.SENSITIVE) {
            this.query.like(this.realm.getSchema().getKeyPathMapping(), str, realmAny);
            return this;
        }
        this.query.likeInsensitive(this.realm.getSchema().getKeyPathMapping(), str, realmAny);
        return this;
    }

    public RealmQuery<E> beginGroup() {
        this.realm.checkIfValid();
        this.query.beginGroup();
        return this;
    }

    public RealmQuery<E> endGroup() {
        this.realm.checkIfValid();
        this.query.endGroup();
        return this;
    }

    public RealmQuery<E> or() {
        this.realm.checkIfValid();
        this.query.or();
        return this;
    }

    public RealmQuery<E> and() {
        this.realm.checkIfValid();
        return this;
    }

    public RealmQuery<E> not() {
        this.realm.checkIfValid();
        this.query.not();
        return this;
    }

    public RealmQuery<E> isEmpty(String str) {
        this.realm.checkIfValid();
        this.query.isEmpty(this.realm.getSchema().getKeyPathMapping(), str);
        return this;
    }

    public RealmQuery<E> isNotEmpty(String str) {
        this.realm.checkIfValid();
        this.query.isNotEmpty(this.realm.getSchema().getKeyPathMapping(), str);
        return this;
    }

    public RealmQuery<E> containsKey(String str, @Nullable String str2) {
        this.realm.checkIfValid();
        this.query.containsKey(this.realm.getSchema().getKeyPathMapping(), str, RealmAny.valueOf(str2));
        return this;
    }

    public RealmQuery<E> containsValue(String str, @Nullable Boolean bool) {
        this.realm.checkIfValid();
        this.query.containsValue(this.realm.getSchema().getKeyPathMapping(), str, RealmAny.valueOf(bool));
        return this;
    }

    public RealmQuery<E> containsValue(String str, @Nullable Byte b) {
        this.realm.checkIfValid();
        this.query.containsValue(this.realm.getSchema().getKeyPathMapping(), str, RealmAny.valueOf(b));
        return this;
    }

    public RealmQuery<E> containsValue(String str, @Nullable Short sh) {
        this.realm.checkIfValid();
        this.query.containsValue(this.realm.getSchema().getKeyPathMapping(), str, RealmAny.valueOf(sh));
        return this;
    }

    public RealmQuery<E> containsValue(String str, @Nullable Integer num) {
        this.realm.checkIfValid();
        this.query.containsValue(this.realm.getSchema().getKeyPathMapping(), str, RealmAny.valueOf(num));
        return this;
    }

    public RealmQuery<E> containsValue(String str, @Nullable Long l) {
        this.realm.checkIfValid();
        this.query.containsValue(this.realm.getSchema().getKeyPathMapping(), str, RealmAny.valueOf(l));
        return this;
    }

    public RealmQuery<E> containsValue(String str, @Nullable Double d) {
        this.realm.checkIfValid();
        this.query.containsValue(this.realm.getSchema().getKeyPathMapping(), str, RealmAny.valueOf(d));
        return this;
    }

    public RealmQuery<E> containsValue(String str, @Nullable Float f) {
        this.realm.checkIfValid();
        this.query.containsValue(this.realm.getSchema().getKeyPathMapping(), str, RealmAny.valueOf(f));
        return this;
    }

    public RealmQuery<E> containsValue(String str, @Nullable String str2) {
        this.realm.checkIfValid();
        this.query.containsValue(this.realm.getSchema().getKeyPathMapping(), str, RealmAny.valueOf(str2));
        return this;
    }

    public RealmQuery<E> containsValue(String str, @Nullable byte[] bArr) {
        this.realm.checkIfValid();
        this.query.containsValue(this.realm.getSchema().getKeyPathMapping(), str, RealmAny.valueOf(bArr));
        return this;
    }

    public RealmQuery<E> containsValue(String str, @Nullable Date date) {
        this.realm.checkIfValid();
        this.query.containsValue(this.realm.getSchema().getKeyPathMapping(), str, RealmAny.valueOf(date));
        return this;
    }

    public RealmQuery<E> containsValue(String str, @Nullable Decimal128 decimal128) {
        this.realm.checkIfValid();
        this.query.containsValue(this.realm.getSchema().getKeyPathMapping(), str, RealmAny.valueOf(decimal128));
        return this;
    }

    public RealmQuery<E> containsValue(String str, @Nullable ObjectId objectId) {
        this.realm.checkIfValid();
        this.query.containsValue(this.realm.getSchema().getKeyPathMapping(), str, RealmAny.valueOf(objectId));
        return this;
    }

    public RealmQuery<E> containsValue(String str, @Nullable UUID uuid) {
        this.realm.checkIfValid();
        this.query.containsValue(this.realm.getSchema().getKeyPathMapping(), str, RealmAny.valueOf(uuid));
        return this;
    }

    public RealmQuery<E> containsValue(String str, RealmAny realmAny) {
        this.realm.checkIfValid();
        this.query.containsValue(this.realm.getSchema().getKeyPathMapping(), str, realmAny);
        return this;
    }

    public RealmQuery<E> containsValue(String str, RealmModel realmModel) {
        this.realm.checkIfValid();
        this.query.containsValue(this.realm.getSchema().getKeyPathMapping(), str, RealmAny.valueOf(realmModel));
        return this;
    }

    public RealmQuery<E> containsEntry(String str, Map.Entry<String, ?> entry) {
        Util.checkNull(entry, "entry");
        this.realm.checkIfValid();
        this.query.containsEntry(this.realm.getSchema().getKeyPathMapping(), str, RealmAny.valueOf(entry.getKey()), RealmAny.valueOf(entry.getValue()));
        return this;
    }

    public Number sum(String str) {
        this.realm.checkIfValid();
        this.realm.checkAllowQueriesOnUiThread();
        long andCheckFieldColumnKey = this.schema.getAndCheckFieldColumnKey(str);
        int i = AnonymousClass1.$SwitchMap$io$realm$RealmFieldType[this.table.getColumnType(andCheckFieldColumnKey).ordinal()];
        if (i == 1) {
            return Long.valueOf(this.query.sumInt(andCheckFieldColumnKey));
        }
        if (i == 2) {
            return Double.valueOf(this.query.sumFloat(andCheckFieldColumnKey));
        }
        if (i == 3) {
            return Double.valueOf(this.query.sumDouble(andCheckFieldColumnKey));
        }
        if (i == 4) {
            return this.query.sumDecimal128(andCheckFieldColumnKey);
        }
        if (i == 5) {
            return this.query.sumRealmAny(andCheckFieldColumnKey);
        }
        throw new IllegalArgumentException(String.format(Locale.US, TYPE_MISMATCH, str, "int, float or double"));
    }

    /* JADX INFO: renamed from: io.realm.RealmQuery$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$io$realm$RealmFieldType;

        static {
            int[] iArr = new int[RealmFieldType.values().length];
            $SwitchMap$io$realm$RealmFieldType = iArr;
            try {
                iArr[RealmFieldType.INTEGER.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.FLOAT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.DOUBLE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.DECIMAL128.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.MIXED.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    public double average(String str) {
        this.realm.checkIfValid();
        this.realm.checkAllowQueriesOnUiThread();
        long andCheckFieldColumnKey = this.schema.getAndCheckFieldColumnKey(str);
        int i = AnonymousClass1.$SwitchMap$io$realm$RealmFieldType[this.table.getColumnType(andCheckFieldColumnKey).ordinal()];
        if (i == 1) {
            return this.query.averageInt(andCheckFieldColumnKey);
        }
        if (i == 2) {
            return this.query.averageFloat(andCheckFieldColumnKey);
        }
        if (i == 3) {
            return this.query.averageDouble(andCheckFieldColumnKey);
        }
        throw new IllegalArgumentException(String.format(Locale.US, TYPE_MISMATCH, str, "int, float or double. For Decimal128 use `averageDecimal128` method."));
    }

    @Nullable
    public Decimal128 averageDecimal128(String str) {
        this.realm.checkIfValid();
        this.realm.checkAllowQueriesOnUiThread();
        return this.query.averageDecimal128(this.schema.getAndCheckFieldColumnKey(str));
    }

    @Nullable
    public Decimal128 averageRealmAny(String str) {
        this.realm.checkIfValid();
        this.realm.checkAllowQueriesOnUiThread();
        return this.query.averageRealmAny(this.schema.getAndCheckFieldColumnKey(str));
    }

    @Nullable
    public Number min(String str) {
        this.realm.checkIfValid();
        this.realm.checkAllowQueriesOnUiThread();
        long andCheckFieldColumnKey = this.schema.getAndCheckFieldColumnKey(str);
        int i = AnonymousClass1.$SwitchMap$io$realm$RealmFieldType[this.table.getColumnType(andCheckFieldColumnKey).ordinal()];
        if (i == 1) {
            return this.query.minimumInt(andCheckFieldColumnKey);
        }
        if (i == 2) {
            return this.query.minimumFloat(andCheckFieldColumnKey);
        }
        if (i == 3) {
            return this.query.minimumDouble(andCheckFieldColumnKey);
        }
        if (i == 4) {
            return this.query.minimumDecimal128(andCheckFieldColumnKey);
        }
        throw new IllegalArgumentException(String.format(Locale.US, TYPE_MISMATCH, str, "int, float or double"));
    }

    @Nullable
    public Date minimumDate(String str) {
        this.realm.checkIfValid();
        this.realm.checkAllowQueriesOnUiThread();
        return this.query.minimumDate(this.schema.getAndCheckFieldColumnKey(str));
    }

    public RealmAny minRealmAny(String str) {
        this.realm.checkIfValid();
        this.realm.checkAllowQueriesOnUiThread();
        return new RealmAny(RealmAnyOperator.fromNativeRealmAny(this.realm, this.query.minimumRealmAny(this.schema.getAndCheckFieldColumnKey(str))));
    }

    @Nullable
    public Number max(String str) {
        this.realm.checkIfValid();
        this.realm.checkAllowQueriesOnUiThread();
        long andCheckFieldColumnKey = this.schema.getAndCheckFieldColumnKey(str);
        int i = AnonymousClass1.$SwitchMap$io$realm$RealmFieldType[this.table.getColumnType(andCheckFieldColumnKey).ordinal()];
        if (i == 1) {
            return this.query.maximumInt(andCheckFieldColumnKey);
        }
        if (i == 2) {
            return this.query.maximumFloat(andCheckFieldColumnKey);
        }
        if (i == 3) {
            return this.query.maximumDouble(andCheckFieldColumnKey);
        }
        if (i == 4) {
            return this.query.maximumDecimal128(andCheckFieldColumnKey);
        }
        throw new IllegalArgumentException(String.format(Locale.US, TYPE_MISMATCH, str, "int, float or double"));
    }

    @Nullable
    public Date maximumDate(String str) {
        this.realm.checkIfValid();
        this.realm.checkAllowQueriesOnUiThread();
        return this.query.maximumDate(this.schema.getAndCheckFieldColumnKey(str));
    }

    public RealmAny maxRealmAny(String str) {
        this.realm.checkIfValid();
        this.realm.checkAllowQueriesOnUiThread();
        return new RealmAny(RealmAnyOperator.fromNativeRealmAny(this.realm, this.query.maximumRealmAny(this.schema.getAndCheckFieldColumnKey(str))));
    }

    public long count() {
        this.realm.checkIfValid();
        this.realm.checkAllowQueriesOnUiThread();
        return lazyFindAll().size();
    }

    public RealmResults<E> findAll() {
        this.realm.checkIfValid();
        this.realm.checkAllowQueriesOnUiThread();
        return createRealmResults(this.query, true);
    }

    private OsResults lazyFindAll() {
        this.realm.checkIfValid();
        return createRealmResults(this.query, false).osResults;
    }

    public RealmResults<E> findAllAsync() {
        this.realm.checkIfValid();
        this.realm.sharedRealm.capabilities.checkCanDeliverNotification(ASYNC_QUERY_WRONG_THREAD_MESSAGE);
        return createRealmResults(this.query, false);
    }

    public RealmQuery<E> sort(String str) {
        this.realm.checkIfValid();
        return sort(str, Sort.ASCENDING);
    }

    public RealmQuery<E> sort(String str, Sort sort) {
        this.realm.checkIfValid();
        return sort(new String[]{str}, new Sort[]{sort});
    }

    public RealmQuery<E> sort(String str, Sort sort, String str2, Sort sort2) {
        this.realm.checkIfValid();
        return sort(new String[]{str, str2}, new Sort[]{sort, sort2});
    }

    public RealmQuery<E> sort(String[] strArr, Sort[] sortArr) {
        if (sortArr == null || sortArr.length == 0) {
            throw new IllegalArgumentException("You must provide at least one sort order.");
        }
        if (strArr.length != sortArr.length) {
            throw new IllegalArgumentException("Number of fields and sort orders do not match.");
        }
        this.realm.checkIfValid();
        this.query.sort(this.realm.getSchema().getKeyPathMapping(), strArr, sortArr);
        return this;
    }

    public RealmQuery<E> distinct(String str, String... strArr) {
        this.realm.checkIfValid();
        String[] strArr2 = new String[strArr.length + 1];
        int i = 0;
        strArr2[0] = str;
        while (i < strArr.length) {
            int i2 = i + 1;
            strArr2[i2] = strArr[i];
            i = i2;
        }
        this.query.distinct(this.realm.getSchema().getKeyPathMapping(), strArr2);
        return this;
    }

    public RealmQuery<E> limit(long j) {
        this.realm.checkIfValid();
        this.query.limit(j);
        return this;
    }

    public RealmQuery<E> alwaysTrue() {
        this.realm.checkIfValid();
        this.query.alwaysTrue();
        return this;
    }

    public RealmQuery<E> alwaysFalse() {
        this.realm.checkIfValid();
        this.query.alwaysFalse();
        return this;
    }

    public RealmQuery<E> rawPredicate(String str, Object... objArr) {
        this.realm.checkIfValid();
        if (Util.isEmptyString(str)) {
            throw new IllegalArgumentException("Non-null 'predicate' required.");
        }
        RealmAny[] realmAnyArr = new RealmAny[objArr.length];
        for (int i = 0; i < objArr.length; i++) {
            realmAnyArr[i] = RealmAny.valueOf(objArr[i]);
        }
        try {
            this.query.rawPredicate(this.realm.getSchema().getKeyPathMapping(), str, realmAnyArr);
            return this;
        } catch (IllegalArgumentException e) {
            if (e.getMessage().startsWith("Illegal Argument: Request for argument at index")) {
                throw new IllegalStateException(e.getMessage());
            }
            throw e;
        }
    }

    public Realm getRealm() {
        BaseRealm baseRealm = this.realm;
        if (baseRealm == null) {
            return null;
        }
        baseRealm.checkIfValid();
        BaseRealm baseRealm2 = this.realm;
        if (!(baseRealm2 instanceof Realm)) {
            throw new IllegalStateException("This method is only available for typed Realms");
        }
        return (Realm) baseRealm2;
    }

    public String getDescription() {
        this.query.validateQuery();
        return nativeSerializeQuery(this.query.getNativePtr());
    }

    public String getTypeQueried() {
        return this.table.getClassName();
    }

    public long getQueryPointer() {
        return this.query.getNativePtr();
    }

    private boolean isDynamicQuery() {
        return this.className != null;
    }

    @Nullable
    public E findFirst() {
        this.realm.checkIfValid();
        this.realm.checkAllowQueriesOnUiThread();
        if (this.forValues) {
            return null;
        }
        long sourceRowIndexForFirstObject = getSourceRowIndexForFirstObject();
        if (sourceRowIndexForFirstObject < 0) {
            return null;
        }
        return (E) this.realm.get(this.clazz, this.className, sourceRowIndexForFirstObject);
    }

    public E findFirstAsync() {
        Row pendingRow;
        RealmObjectProxy realmObjectProxy;
        this.realm.checkIfValid();
        if (this.forValues) {
            throw new UnsupportedOperationException("findFirstAsync() available only when type parameter 'E' is implementing RealmModel.");
        }
        this.realm.sharedRealm.capabilities.checkCanDeliverNotification(ASYNC_QUERY_WRONG_THREAD_MESSAGE);
        if (this.realm.isInTransaction()) {
            pendingRow = OsResults.createFromQuery(this.realm.sharedRealm, this.query).firstUncheckedRow();
        } else {
            pendingRow = new PendingRow(this.realm.sharedRealm, this.query, isDynamicQuery());
        }
        Row row = pendingRow;
        if (isDynamicQuery()) {
            realmObjectProxy = (E) new DynamicRealmObject(this.realm, row);
        } else {
            Class<E> cls = this.clazz;
            RealmProxyMediator schemaMediator = this.realm.getConfiguration().getSchemaMediator();
            BaseRealm baseRealm = this.realm;
            realmObjectProxy = (E) schemaMediator.newInstance(cls, baseRealm, row, baseRealm.getSchema().getColumnInfo((Class<? extends RealmModel>) cls), false, Collections.EMPTY_LIST);
        }
        if (row instanceof PendingRow) {
            ((PendingRow) row).setFrontEnd(realmObjectProxy.realmGet$proxyState());
        }
        return (E) realmObjectProxy;
    }

    private RealmResults<E> createRealmResults(TableQuery tableQuery, boolean z) {
        RealmResults<E> realmResults;
        OsResults osResultsCreateFromQuery = OsResults.createFromQuery(this.realm.sharedRealm, tableQuery);
        if (isDynamicQuery()) {
            realmResults = new RealmResults<>(this.realm, osResultsCreateFromQuery, this.className);
        } else {
            realmResults = new RealmResults<>(this.realm, osResultsCreateFromQuery, this.clazz);
        }
        if (z) {
            realmResults.load();
        }
        return realmResults;
    }

    private long getSourceRowIndexForFirstObject() {
        return this.query.find();
    }
}
