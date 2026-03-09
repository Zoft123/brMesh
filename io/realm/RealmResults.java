package io.realm;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.realm.internal.OsResults;
import io.realm.internal.RealmObjectProxy;
import io.realm.internal.Row;
import io.realm.internal.Table;
import io.realm.internal.UncheckedRow;
import io.realm.internal.Util;
import io.realm.internal.android.JsonUtils;
import io.realm.log.RealmLog;
import io.realm.rx.CollectionChange;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Locale;
import java.util.UUID;
import javax.annotation.Nullable;
import org.bson.types.Decimal128;
import org.bson.types.ObjectId;

/* JADX INFO: loaded from: classes4.dex */
public class RealmResults<E> extends OrderedRealmCollectionImpl<E> {
    /* JADX WARN: Multi-variable type inference failed */
    @Override // io.realm.OrderedRealmCollectionImpl, java.util.AbstractList, java.util.List
    @Deprecated
    public /* bridge */ /* synthetic */ void add(int i, Object obj) {
        super.add(i, obj);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // io.realm.OrderedRealmCollectionImpl, java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    @Deprecated
    public /* bridge */ /* synthetic */ boolean add(Object obj) {
        return super.add(obj);
    }

    @Override // io.realm.OrderedRealmCollectionImpl, java.util.AbstractList, java.util.List
    @Deprecated
    public /* bridge */ /* synthetic */ boolean addAll(int i, Collection collection) {
        return super.addAll(i, collection);
    }

    @Override // io.realm.OrderedRealmCollectionImpl, java.util.AbstractCollection, java.util.Collection, java.util.List
    @Deprecated
    public /* bridge */ /* synthetic */ boolean addAll(Collection collection) {
        return super.addAll(collection);
    }

    @Override // io.realm.OrderedRealmCollectionImpl, io.realm.RealmCollection
    public /* bridge */ /* synthetic */ double average(String str) {
        return super.average(str);
    }

    @Override // io.realm.OrderedRealmCollectionImpl, java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    @Deprecated
    public /* bridge */ /* synthetic */ void clear() {
        super.clear();
    }

    @Override // io.realm.OrderedRealmCollectionImpl, java.util.AbstractCollection, java.util.Collection, java.util.List, io.realm.RealmCollection
    public /* bridge */ /* synthetic */ boolean contains(@Nullable Object obj) {
        return super.contains(obj);
    }

    @Override // io.realm.OrderedRealmCollectionImpl, io.realm.OrderedRealmCollection
    public /* bridge */ /* synthetic */ OrderedRealmCollectionSnapshot createSnapshot() {
        return super.createSnapshot();
    }

    @Override // io.realm.OrderedRealmCollectionImpl, io.realm.RealmCollection
    public /* bridge */ /* synthetic */ boolean deleteAllFromRealm() {
        return super.deleteAllFromRealm();
    }

    @Override // io.realm.OrderedRealmCollectionImpl, io.realm.OrderedRealmCollection
    public /* bridge */ /* synthetic */ boolean deleteFirstFromRealm() {
        return super.deleteFirstFromRealm();
    }

    @Override // io.realm.OrderedRealmCollectionImpl, io.realm.OrderedRealmCollection
    public /* bridge */ /* synthetic */ void deleteFromRealm(int i) {
        super.deleteFromRealm(i);
    }

    @Override // io.realm.OrderedRealmCollectionImpl, io.realm.OrderedRealmCollection
    public /* bridge */ /* synthetic */ boolean deleteLastFromRealm() {
        return super.deleteLastFromRealm();
    }

    @Override // io.realm.OrderedRealmCollectionImpl, io.realm.OrderedRealmCollection
    @Nullable
    public /* bridge */ /* synthetic */ Object first() {
        return super.first();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // io.realm.OrderedRealmCollectionImpl, io.realm.OrderedRealmCollection
    @Nullable
    public /* bridge */ /* synthetic */ Object first(@Nullable Object obj) {
        return super.first(obj);
    }

    @Override // io.realm.OrderedRealmCollectionImpl, java.util.AbstractList, java.util.List
    @Nullable
    public /* bridge */ /* synthetic */ Object get(int i) {
        return super.get(i);
    }

    @Override // io.realm.OrderedRealmCollectionImpl
    public /* bridge */ /* synthetic */ Realm getRealm() {
        return super.getRealm();
    }

    @Override // io.realm.OrderedRealmCollectionImpl, io.realm.RealmCollection, io.realm.internal.ManageableObject
    public /* bridge */ /* synthetic */ boolean isManaged() {
        return super.isManaged();
    }

    @Override // io.realm.OrderedRealmCollectionImpl, io.realm.RealmCollection, io.realm.internal.ManageableObject
    public /* bridge */ /* synthetic */ boolean isValid() {
        return super.isValid();
    }

    @Override // io.realm.OrderedRealmCollectionImpl, java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.List
    public /* bridge */ /* synthetic */ Iterator iterator() {
        return super.iterator();
    }

    @Override // io.realm.OrderedRealmCollectionImpl, io.realm.OrderedRealmCollection
    @Nullable
    public /* bridge */ /* synthetic */ Object last() {
        return super.last();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // io.realm.OrderedRealmCollectionImpl, io.realm.OrderedRealmCollection
    @Nullable
    public /* bridge */ /* synthetic */ Object last(@Nullable Object obj) {
        return super.last(obj);
    }

    @Override // io.realm.OrderedRealmCollectionImpl, java.util.AbstractList, java.util.List
    public /* bridge */ /* synthetic */ ListIterator listIterator() {
        return super.listIterator();
    }

    @Override // io.realm.OrderedRealmCollectionImpl, java.util.AbstractList, java.util.List
    public /* bridge */ /* synthetic */ ListIterator listIterator(int i) {
        return super.listIterator(i);
    }

    @Override // io.realm.OrderedRealmCollectionImpl, io.realm.RealmCollection
    public /* bridge */ /* synthetic */ Number max(String str) {
        return super.max(str);
    }

    @Override // io.realm.OrderedRealmCollectionImpl, io.realm.RealmCollection
    @Nullable
    public /* bridge */ /* synthetic */ Date maxDate(String str) {
        return super.maxDate(str);
    }

    @Override // io.realm.OrderedRealmCollectionImpl, io.realm.RealmCollection
    public /* bridge */ /* synthetic */ Number min(String str) {
        return super.min(str);
    }

    @Override // io.realm.OrderedRealmCollectionImpl, io.realm.RealmCollection
    public /* bridge */ /* synthetic */ Date minDate(String str) {
        return super.minDate(str);
    }

    @Override // io.realm.OrderedRealmCollectionImpl, java.util.AbstractList, java.util.List
    @Deprecated
    public /* bridge */ /* synthetic */ Object remove(int i) {
        return super.remove(i);
    }

    @Override // io.realm.OrderedRealmCollectionImpl, java.util.AbstractCollection, java.util.Collection, java.util.List
    @Deprecated
    public /* bridge */ /* synthetic */ boolean remove(Object obj) {
        return super.remove(obj);
    }

    @Override // io.realm.OrderedRealmCollectionImpl, java.util.AbstractCollection, java.util.Collection, java.util.List
    @Deprecated
    public /* bridge */ /* synthetic */ boolean removeAll(Collection collection) {
        return super.removeAll(collection);
    }

    @Override // io.realm.OrderedRealmCollectionImpl, java.util.AbstractCollection, java.util.Collection, java.util.List
    @Deprecated
    public /* bridge */ /* synthetic */ boolean retainAll(Collection collection) {
        return super.retainAll(collection);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // io.realm.OrderedRealmCollectionImpl, java.util.AbstractList, java.util.List
    @Deprecated
    public /* bridge */ /* synthetic */ Object set(int i, Object obj) {
        return super.set(i, obj);
    }

    @Override // io.realm.OrderedRealmCollectionImpl, java.util.AbstractCollection, java.util.Collection, java.util.List
    public /* bridge */ /* synthetic */ int size() {
        return super.size();
    }

    @Override // io.realm.OrderedRealmCollectionImpl, io.realm.OrderedRealmCollection
    public /* bridge */ /* synthetic */ RealmResults sort(String str) {
        return super.sort(str);
    }

    @Override // io.realm.OrderedRealmCollectionImpl, io.realm.OrderedRealmCollection
    public /* bridge */ /* synthetic */ RealmResults sort(String str, Sort sort) {
        return super.sort(str, sort);
    }

    @Override // io.realm.OrderedRealmCollectionImpl, io.realm.OrderedRealmCollection
    public /* bridge */ /* synthetic */ RealmResults sort(String[] strArr, Sort[] sortArr) {
        return super.sort(strArr, sortArr);
    }

    @Override // io.realm.OrderedRealmCollectionImpl, io.realm.RealmCollection
    public /* bridge */ /* synthetic */ Number sum(String str) {
        return super.sum(str);
    }

    static <T extends RealmModel> RealmResults<T> createBacklinkResults(BaseRealm baseRealm, Row row, Class<T> cls, String str) {
        Table table = baseRealm.getSchema().getTable((Class<? extends RealmModel>) cls);
        return new RealmResults<>(baseRealm, OsResults.createForBacklinks(baseRealm.sharedRealm, (UncheckedRow) row, table, str), cls);
    }

    static RealmResults<DynamicRealmObject> createDynamicBacklinkResults(DynamicRealm dynamicRealm, UncheckedRow uncheckedRow, Table table, String str) {
        return new RealmResults<>(dynamicRealm, OsResults.createForBacklinks(dynamicRealm.sharedRealm, uncheckedRow, table, str), Table.getClassNameForTable(table.getName()));
    }

    RealmResults(BaseRealm baseRealm, OsResults osResults, Class<E> cls) {
        this(baseRealm, osResults, (Class) cls, false);
    }

    RealmResults(BaseRealm baseRealm, OsResults osResults, Class<E> cls, boolean z) {
        super(baseRealm, osResults, cls, getCollectionOperator(z, baseRealm, osResults, cls, null));
    }

    RealmResults(BaseRealm baseRealm, OsResults osResults, String str) {
        this(baseRealm, osResults, str, false);
    }

    RealmResults(BaseRealm baseRealm, OsResults osResults, String str, boolean z) {
        super(baseRealm, osResults, str, getCollectionOperator(z, baseRealm, osResults, null, str));
    }

    @Override // io.realm.RealmCollection
    public RealmQuery<E> where() {
        this.baseRealm.checkIfValid();
        return RealmQuery.createQueryFromResult(this);
    }

    @Override // io.realm.OrderedRealmCollectionImpl, io.realm.OrderedRealmCollection
    public RealmResults<E> sort(String str, Sort sort, String str2, Sort sort2) {
        return sort(new String[]{str, str2}, new Sort[]{sort, sort2});
    }

    @Override // io.realm.RealmCollection
    public boolean isLoaded() {
        this.baseRealm.checkIfValid();
        return this.osResults.isLoaded();
    }

    @Override // io.realm.RealmCollection
    public boolean load() {
        this.baseRealm.checkIfValid();
        this.osResults.load();
        return true;
    }

    public void setValue(String str, @Nullable Object obj) {
        checkNonEmptyFieldName(str);
        this.baseRealm.checkIfValidAndInTransaction();
        String strMapFieldNameToInternalName = mapFieldNameToInternalName(str);
        boolean z = obj instanceof String;
        String str2 = z ? (String) obj : null;
        String className = this.osResults.getTable().getClassName();
        RealmObjectSchema realmObjectSchema = getRealm().getSchema().get(className);
        if (!realmObjectSchema.hasField(strMapFieldNameToInternalName)) {
            throw new IllegalArgumentException(String.format("Field '%s' could not be found in class '%s'", strMapFieldNameToInternalName, className));
        }
        if (obj == null) {
            this.osResults.setNull(strMapFieldNameToInternalName);
            return;
        }
        RealmFieldType fieldType = realmObjectSchema.getFieldType(strMapFieldNameToInternalName);
        if (z && fieldType != RealmFieldType.STRING) {
            switch (AnonymousClass1.$SwitchMap$io$realm$RealmFieldType[fieldType.ordinal()]) {
                case 1:
                    obj = Boolean.valueOf(Boolean.parseBoolean(str2));
                    break;
                case 2:
                    obj = Long.valueOf(Long.parseLong(str2));
                    break;
                case 3:
                    obj = Float.valueOf(Float.parseFloat(str2));
                    break;
                case 4:
                    obj = Double.valueOf(Double.parseDouble(str2));
                    break;
                case 5:
                    obj = JsonUtils.stringToDate(str2);
                    break;
                case 6:
                    obj = Decimal128.parse(str2);
                    break;
                case 7:
                    obj = new ObjectId(str2);
                    break;
                case 8:
                    obj = UUID.fromString(str2);
                    break;
                default:
                    throw new IllegalArgumentException(String.format(Locale.US, "Field %s is not a String field, and the provide value could not be automatically converted: %s. Use a typedsetter instead", strMapFieldNameToInternalName, obj));
            }
        }
        Class<?> cls = obj.getClass();
        if (cls == Boolean.class) {
            setBoolean(strMapFieldNameToInternalName, ((Boolean) obj).booleanValue());
            return;
        }
        if (cls == Short.class) {
            setShort(strMapFieldNameToInternalName, ((Short) obj).shortValue());
            return;
        }
        if (cls == Integer.class) {
            setInt(strMapFieldNameToInternalName, ((Integer) obj).intValue());
            return;
        }
        if (cls == Long.class) {
            setLong(strMapFieldNameToInternalName, ((Long) obj).longValue());
            return;
        }
        if (cls == Byte.class) {
            setByte(strMapFieldNameToInternalName, ((Byte) obj).byteValue());
            return;
        }
        if (cls == Float.class) {
            setFloat(strMapFieldNameToInternalName, ((Float) obj).floatValue());
            return;
        }
        if (cls == Double.class) {
            setDouble(strMapFieldNameToInternalName, ((Double) obj).doubleValue());
            return;
        }
        if (cls == String.class) {
            setString(strMapFieldNameToInternalName, (String) obj);
            return;
        }
        if (obj instanceof Date) {
            setDate(strMapFieldNameToInternalName, (Date) obj);
            return;
        }
        if (obj instanceof Decimal128) {
            setDecimal128(strMapFieldNameToInternalName, (Decimal128) obj);
            return;
        }
        if (obj instanceof ObjectId) {
            setObjectId(strMapFieldNameToInternalName, (ObjectId) obj);
            return;
        }
        if (obj instanceof UUID) {
            setUUID(strMapFieldNameToInternalName, (UUID) obj);
            return;
        }
        if (obj instanceof byte[]) {
            setBlob(strMapFieldNameToInternalName, (byte[]) obj);
            return;
        }
        if (obj instanceof RealmModel) {
            setObject(strMapFieldNameToInternalName, (RealmModel) obj);
        } else if (cls == RealmList.class) {
            setList(strMapFieldNameToInternalName, (RealmList) obj);
        } else {
            throw new IllegalArgumentException("Value is of a type not supported: " + obj.getClass());
        }
    }

    public void setNull(String str) {
        checkNonEmptyFieldName(str);
        this.baseRealm.checkIfValidAndInTransaction();
        this.osResults.setNull(str);
    }

    public void setBoolean(String str, boolean z) {
        checkNonEmptyFieldName(str);
        this.baseRealm.checkIfValidAndInTransaction();
        String strMapFieldNameToInternalName = mapFieldNameToInternalName(str);
        checkType(strMapFieldNameToInternalName, RealmFieldType.BOOLEAN);
        this.osResults.setBoolean(strMapFieldNameToInternalName, z);
    }

    public void setByte(String str, byte b) {
        checkNonEmptyFieldName(str);
        this.baseRealm.checkIfValidAndInTransaction();
        String strMapFieldNameToInternalName = mapFieldNameToInternalName(str);
        checkType(strMapFieldNameToInternalName, RealmFieldType.INTEGER);
        this.osResults.setInt(strMapFieldNameToInternalName, b);
    }

    public void setShort(String str, short s) {
        checkNonEmptyFieldName(str);
        this.baseRealm.checkIfValidAndInTransaction();
        String strMapFieldNameToInternalName = mapFieldNameToInternalName(str);
        checkType(strMapFieldNameToInternalName, RealmFieldType.INTEGER);
        this.osResults.setInt(strMapFieldNameToInternalName, s);
    }

    public void setInt(String str, int i) {
        checkNonEmptyFieldName(str);
        String strMapFieldNameToInternalName = mapFieldNameToInternalName(str);
        checkType(strMapFieldNameToInternalName, RealmFieldType.INTEGER);
        this.baseRealm.checkIfValidAndInTransaction();
        this.osResults.setInt(strMapFieldNameToInternalName, i);
    }

    public void setLong(String str, long j) {
        checkNonEmptyFieldName(str);
        this.baseRealm.checkIfValidAndInTransaction();
        String strMapFieldNameToInternalName = mapFieldNameToInternalName(str);
        checkType(strMapFieldNameToInternalName, RealmFieldType.INTEGER);
        this.osResults.setInt(strMapFieldNameToInternalName, j);
    }

    public void setFloat(String str, float f) {
        checkNonEmptyFieldName(str);
        this.baseRealm.checkIfValidAndInTransaction();
        String strMapFieldNameToInternalName = mapFieldNameToInternalName(str);
        checkType(strMapFieldNameToInternalName, RealmFieldType.FLOAT);
        this.osResults.setFloat(strMapFieldNameToInternalName, f);
    }

    public void setDouble(String str, double d) {
        checkNonEmptyFieldName(str);
        this.baseRealm.checkIfValidAndInTransaction();
        String strMapFieldNameToInternalName = mapFieldNameToInternalName(str);
        checkType(strMapFieldNameToInternalName, RealmFieldType.DOUBLE);
        this.osResults.setDouble(strMapFieldNameToInternalName, d);
    }

    public void setString(String str, @Nullable String str2) {
        checkNonEmptyFieldName(str);
        this.baseRealm.checkIfValidAndInTransaction();
        String strMapFieldNameToInternalName = mapFieldNameToInternalName(str);
        checkType(strMapFieldNameToInternalName, RealmFieldType.STRING);
        this.osResults.setString(strMapFieldNameToInternalName, str2);
    }

    public void setBlob(String str, @Nullable byte[] bArr) {
        checkNonEmptyFieldName(str);
        this.baseRealm.checkIfValidAndInTransaction();
        String strMapFieldNameToInternalName = mapFieldNameToInternalName(str);
        checkType(strMapFieldNameToInternalName, RealmFieldType.BINARY);
        this.osResults.setBlob(strMapFieldNameToInternalName, bArr);
    }

    public void setDate(String str, @Nullable Date date) {
        checkNonEmptyFieldName(str);
        this.baseRealm.checkIfValidAndInTransaction();
        String strMapFieldNameToInternalName = mapFieldNameToInternalName(str);
        checkType(strMapFieldNameToInternalName, RealmFieldType.DATE);
        this.osResults.setDate(strMapFieldNameToInternalName, date);
    }

    public void setObject(String str, @Nullable RealmModel realmModel) {
        checkNonEmptyFieldName(str);
        this.baseRealm.checkIfValidAndInTransaction();
        String strMapFieldNameToInternalName = mapFieldNameToInternalName(str);
        checkType(strMapFieldNameToInternalName, RealmFieldType.OBJECT);
        this.osResults.setObject(strMapFieldNameToInternalName, checkRealmObjectConstraints(strMapFieldNameToInternalName, realmModel));
    }

    public void setDecimal128(String str, @Nullable Decimal128 decimal128) {
        checkNonEmptyFieldName(str);
        this.baseRealm.checkIfValidAndInTransaction();
        String strMapFieldNameToInternalName = mapFieldNameToInternalName(str);
        checkType(strMapFieldNameToInternalName, RealmFieldType.DECIMAL128);
        this.osResults.setDecimal128(strMapFieldNameToInternalName, decimal128);
    }

    public void setObjectId(String str, @Nullable ObjectId objectId) {
        checkNonEmptyFieldName(str);
        this.baseRealm.checkIfValidAndInTransaction();
        String strMapFieldNameToInternalName = mapFieldNameToInternalName(str);
        checkType(strMapFieldNameToInternalName, RealmFieldType.OBJECT_ID);
        this.osResults.setObjectId(strMapFieldNameToInternalName, objectId);
    }

    public void setUUID(String str, @Nullable UUID uuid) {
        checkNonEmptyFieldName(str);
        this.baseRealm.checkIfValidAndInTransaction();
        String strMapFieldNameToInternalName = mapFieldNameToInternalName(str);
        checkType(strMapFieldNameToInternalName, RealmFieldType.UUID);
        this.osResults.setUUID(strMapFieldNameToInternalName, uuid);
    }

    private Row checkRealmObjectConstraints(String str, @Nullable RealmModel realmModel) {
        if (realmModel == null) {
            return null;
        }
        if (!RealmObject.isManaged(realmModel) || !RealmObject.isValid(realmModel)) {
            throw new IllegalArgumentException("'value' is not a valid, managed Realm object.");
        }
        ProxyState proxyStateRealmGet$proxyState = ((RealmObjectProxy) realmModel).realmGet$proxyState();
        if (!proxyStateRealmGet$proxyState.getRealm$realm().getPath().equals(this.baseRealm.getPath())) {
            throw new IllegalArgumentException("'value' does not belong to the same Realm as the RealmResults.");
        }
        Table table = this.osResults.getTable();
        Table linkTarget = table.getLinkTarget(table.getColumnKey(str));
        Table table2 = proxyStateRealmGet$proxyState.getRow$realm().getTable();
        if (!linkTarget.hasSameSchema(table2)) {
            throw new IllegalArgumentException(String.format(Locale.US, "Type of object is wrong. Was '%s', expected '%s'", table2.getClassName(), linkTarget.getClassName()));
        }
        return proxyStateRealmGet$proxyState.getRow$realm();
    }

    public <T> void setList(String str, RealmList<T> realmList) {
        checkNonEmptyFieldName(str);
        String strMapFieldNameToInternalName = mapFieldNameToInternalName(str);
        this.baseRealm.checkIfValidAndInTransaction();
        if (realmList == null) {
            throw new IllegalArgumentException("Non-null 'list' required");
        }
        RealmFieldType fieldType = this.baseRealm.getSchema().getSchemaForClass(this.osResults.getTable().getClassName()).getFieldType(strMapFieldNameToInternalName);
        switch (fieldType) {
            case LIST:
                checkTypeOfListElements(realmList, RealmModel.class);
                checkRealmObjectConstraints(strMapFieldNameToInternalName, (RealmModel) realmList.first(null));
                this.osResults.setModelList(strMapFieldNameToInternalName, realmList);
                return;
            case INTEGER_LIST:
                Class<?> listType = getListType(realmList);
                if (listType.equals(Integer.class)) {
                    this.osResults.setIntegerList(strMapFieldNameToInternalName, realmList);
                    return;
                }
                if (listType.equals(Long.class)) {
                    this.osResults.setLongList(strMapFieldNameToInternalName, realmList);
                    return;
                } else if (listType.equals(Short.class)) {
                    this.osResults.setShortList(strMapFieldNameToInternalName, realmList);
                    return;
                } else {
                    if (listType.equals(Byte.class)) {
                        this.osResults.setByteList(strMapFieldNameToInternalName, realmList);
                        return;
                    }
                    throw new IllegalArgumentException(String.format("List contained the wrong type of elements. Elements that can be mapped to Integers was expected, but the actual type is '%s'", listType));
                }
            case BOOLEAN_LIST:
                checkTypeOfListElements(realmList, Boolean.class);
                this.osResults.setBooleanList(strMapFieldNameToInternalName, realmList);
                return;
            case STRING_LIST:
                checkTypeOfListElements(realmList, String.class);
                this.osResults.setStringList(strMapFieldNameToInternalName, realmList);
                return;
            case BINARY_LIST:
                checkTypeOfListElements(realmList, byte[].class);
                this.osResults.setByteArrayList(strMapFieldNameToInternalName, realmList);
                return;
            case DATE_LIST:
                checkTypeOfListElements(realmList, Date.class);
                this.osResults.setDateList(strMapFieldNameToInternalName, realmList);
                return;
            case DECIMAL128_LIST:
                checkTypeOfListElements(realmList, Decimal128.class);
                this.osResults.setDecimal128List(strMapFieldNameToInternalName, realmList);
                return;
            case OBJECT_ID_LIST:
                checkTypeOfListElements(realmList, ObjectId.class);
                this.osResults.setObjectIdList(strMapFieldNameToInternalName, realmList);
                return;
            case UUID_LIST:
                checkTypeOfListElements(realmList, UUID.class);
                this.osResults.setUUIDList(strMapFieldNameToInternalName, realmList);
                return;
            case FLOAT_LIST:
                checkTypeOfListElements(realmList, Float.class);
                this.osResults.setFloatList(strMapFieldNameToInternalName, realmList);
                return;
            case DOUBLE_LIST:
                checkTypeOfListElements(realmList, Double.class);
                this.osResults.setDoubleList(strMapFieldNameToInternalName, realmList);
                return;
            default:
                throw new IllegalArgumentException(String.format("Field '%s' is not a list but a %s", strMapFieldNameToInternalName, fieldType));
        }
    }

    @Override // io.realm.internal.ManageableObject
    public boolean isFrozen() {
        return this.baseRealm != null && this.baseRealm.isFrozen();
    }

    @Override // io.realm.internal.Freezable
    public RealmResults<E> freeze() {
        if (!isValid()) {
            throw new IllegalStateException("Only valid, managed RealmResults can be frozen.");
        }
        BaseRealm baseRealmFreeze = this.baseRealm.freeze();
        OsResults osResultsFreeze = this.osResults.freeze(baseRealmFreeze.sharedRealm);
        if (this.className != null) {
            return new RealmResults<>(baseRealmFreeze, osResultsFreeze, this.className);
        }
        return new RealmResults<>(baseRealmFreeze, osResultsFreeze, this.classSpec);
    }

    private Class<?> getListType(RealmList realmList) {
        if (!realmList.isEmpty()) {
            return realmList.first().getClass();
        }
        return Long.class;
    }

    private <T> void checkTypeOfListElements(RealmList<T> realmList, Class<?> cls) {
        if (realmList.isEmpty()) {
            return;
        }
        Class<?> cls2 = realmList.first().getClass();
        if (!cls.isAssignableFrom(cls2)) {
            throw new IllegalArgumentException(String.format("List contained the wrong type of elements. Elements of type '%s' was expected, but the actual type is '%s'", cls, cls2));
        }
    }

    public void addChangeListener(RealmChangeListener<RealmResults<E>> realmChangeListener) {
        checkForAddListener(realmChangeListener);
        this.osResults.addListener(this, realmChangeListener);
    }

    public void addChangeListener(OrderedRealmCollectionChangeListener<RealmResults<E>> orderedRealmCollectionChangeListener) {
        checkForAddListener(orderedRealmCollectionChangeListener);
        this.osResults.addListener(this, orderedRealmCollectionChangeListener);
    }

    private void checkForAddListener(@Nullable Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException("Listener should not be null");
        }
        this.baseRealm.checkIfValid();
        this.baseRealm.sharedRealm.capabilities.checkCanDeliverNotification("Listeners cannot be used on current thread.");
    }

    private void checkForRemoveListener(@Nullable Object obj, boolean z) {
        if (z && obj == null) {
            throw new IllegalArgumentException("Listener should not be null");
        }
        if (this.baseRealm.isClosed()) {
            RealmLog.warn("Calling removeChangeListener on a closed Realm %s, make sure to close all listeners before closing the Realm.", this.baseRealm.configuration.getPath());
        }
    }

    public void removeAllChangeListeners() {
        checkForRemoveListener(null, false);
        this.osResults.removeAllListeners();
    }

    public void removeChangeListener(RealmChangeListener<RealmResults<E>> realmChangeListener) {
        checkForRemoveListener(realmChangeListener, true);
        this.osResults.removeListener(this, realmChangeListener);
    }

    public void removeChangeListener(OrderedRealmCollectionChangeListener<RealmResults<E>> orderedRealmCollectionChangeListener) {
        checkForRemoveListener(orderedRealmCollectionChangeListener, true);
        this.osResults.removeListener(this, orderedRealmCollectionChangeListener);
    }

    public Flowable<RealmResults<E>> asFlowable() {
        if (this.baseRealm instanceof Realm) {
            return this.baseRealm.configuration.getRxFactory().from((Realm) this.baseRealm, this);
        }
        if (this.baseRealm instanceof DynamicRealm) {
            return this.baseRealm.configuration.getRxFactory().from((DynamicRealm) this.baseRealm, this);
        }
        throw new UnsupportedOperationException(this.baseRealm.getClass() + " does not support RxJava2.");
    }

    public Observable<CollectionChange<RealmResults<E>>> asChangesetObservable() {
        if (this.baseRealm instanceof Realm) {
            return this.baseRealm.configuration.getRxFactory().changesetsFrom((Realm) this.baseRealm, this);
        }
        if (this.baseRealm instanceof DynamicRealm) {
            return this.baseRealm.configuration.getRxFactory().changesetsFrom((DynamicRealm) this.baseRealm, this);
        }
        throw new UnsupportedOperationException(this.baseRealm.getClass() + " does not support RxJava2.");
    }

    public String asJSON() {
        return this.osResults.toJSON(-1);
    }

    private void checkNonEmptyFieldName(String str) {
        if (Util.isEmptyString(str)) {
            throw new IllegalArgumentException("Non-empty 'fieldname' required.");
        }
    }

    private void checkNotNull(@Nullable Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException("Non-null 'value' required. Use 'setNull(fieldName)' instead.");
        }
    }

    private void checkType(String str, RealmFieldType realmFieldType) {
        String className = this.osResults.getTable().getClassName();
        RealmFieldType fieldType = this.baseRealm.getSchema().get(className).getFieldType(str);
        if (fieldType != realmFieldType) {
            throw new IllegalArgumentException(String.format("The field '%s.%s' is not of the expected type. Actual: %s, Expected: %s", className, str, fieldType, realmFieldType));
        }
    }

    private String mapFieldNameToInternalName(String str) {
        if (!(this.baseRealm instanceof Realm)) {
            return str;
        }
        String internalFieldName = this.baseRealm.getSchema().getColumnInfo(this.osResults.getTable().getClassName()).getInternalFieldName(str);
        if (internalFieldName != null) {
            return internalFieldName;
        }
        throw new IllegalArgumentException(String.format("Field '%s' does not exists.", str));
    }
}
