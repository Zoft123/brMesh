package io.realm;

import io.realm.internal.ColumnInfo;
import io.realm.internal.OsObjectStore;
import io.realm.internal.Table;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import javax.annotation.Nullable;
import org.bson.types.Decimal128;
import org.bson.types.ObjectId;

/* JADX INFO: loaded from: classes4.dex */
public abstract class RealmObjectSchema {
    static final Map<Class<?>, FieldMetaData> SUPPORTED_DICTIONARY_SIMPLE_FIELDS;
    static final Map<Class<?>, FieldMetaData> SUPPORTED_LINKED_FIELDS;
    static final Map<Class<?>, FieldMetaData> SUPPORTED_LIST_SIMPLE_FIELDS;
    static final Map<Class<?>, FieldMetaData> SUPPORTED_SET_SIMPLE_FIELDS;
    final ColumnInfo columnInfo;
    final BaseRealm realm;
    final RealmSchema schema;
    final Table table;

    public interface Function {
        void apply(DynamicRealmObject dynamicRealmObject);
    }

    public abstract RealmObjectSchema addField(String str, Class<?> cls, FieldAttribute... fieldAttributeArr);

    public abstract RealmObjectSchema addIndex(String str);

    public abstract RealmObjectSchema addPrimaryKey(String str);

    public abstract RealmObjectSchema addRealmDictionaryField(String str, RealmObjectSchema realmObjectSchema);

    public abstract RealmObjectSchema addRealmDictionaryField(String str, Class<?> cls);

    public abstract RealmObjectSchema addRealmListField(String str, RealmObjectSchema realmObjectSchema);

    public abstract RealmObjectSchema addRealmListField(String str, Class<?> cls);

    public abstract RealmObjectSchema addRealmObjectField(String str, RealmObjectSchema realmObjectSchema);

    public abstract RealmObjectSchema addRealmSetField(String str, RealmObjectSchema realmObjectSchema);

    public abstract RealmObjectSchema addRealmSetField(String str, Class<?> cls);

    abstract String getPropertyClassName(String str);

    public abstract RealmObjectSchema removeField(String str);

    public abstract RealmObjectSchema removeIndex(String str);

    public abstract RealmObjectSchema removePrimaryKey();

    public abstract RealmObjectSchema renameField(String str, String str2);

    public abstract RealmObjectSchema setClassName(String str);

    public abstract RealmObjectSchema setNullable(String str, boolean z);

    public abstract RealmObjectSchema setRequired(String str, boolean z);

    public abstract RealmObjectSchema transform(Function function);

    static {
        HashMap map = new HashMap();
        map.put(String.class, new FieldMetaData(RealmFieldType.STRING, RealmFieldType.STRING_LIST, true));
        map.put(Short.TYPE, new FieldMetaData(RealmFieldType.INTEGER, RealmFieldType.INTEGER_LIST, false));
        map.put(Short.class, new FieldMetaData(RealmFieldType.INTEGER, RealmFieldType.INTEGER_LIST, true));
        map.put(Integer.TYPE, new FieldMetaData(RealmFieldType.INTEGER, RealmFieldType.INTEGER_LIST, false));
        map.put(Integer.class, new FieldMetaData(RealmFieldType.INTEGER, RealmFieldType.INTEGER_LIST, true));
        map.put(Long.TYPE, new FieldMetaData(RealmFieldType.INTEGER, RealmFieldType.INTEGER_LIST, false));
        map.put(Long.class, new FieldMetaData(RealmFieldType.INTEGER, RealmFieldType.INTEGER_LIST, true));
        map.put(Float.TYPE, new FieldMetaData(RealmFieldType.FLOAT, RealmFieldType.FLOAT_LIST, false));
        map.put(Float.class, new FieldMetaData(RealmFieldType.FLOAT, RealmFieldType.FLOAT_LIST, true));
        map.put(Double.TYPE, new FieldMetaData(RealmFieldType.DOUBLE, RealmFieldType.DOUBLE_LIST, false));
        map.put(Double.class, new FieldMetaData(RealmFieldType.DOUBLE, RealmFieldType.DOUBLE_LIST, true));
        map.put(Boolean.TYPE, new FieldMetaData(RealmFieldType.BOOLEAN, RealmFieldType.BOOLEAN_LIST, false));
        map.put(Boolean.class, new FieldMetaData(RealmFieldType.BOOLEAN, RealmFieldType.BOOLEAN_LIST, true));
        map.put(Byte.TYPE, new FieldMetaData(RealmFieldType.INTEGER, RealmFieldType.INTEGER_LIST, false));
        map.put(Byte.class, new FieldMetaData(RealmFieldType.INTEGER, RealmFieldType.INTEGER_LIST, true));
        map.put(byte[].class, new FieldMetaData(RealmFieldType.BINARY, RealmFieldType.BINARY_LIST, true));
        map.put(Date.class, new FieldMetaData(RealmFieldType.DATE, RealmFieldType.DATE_LIST, true));
        map.put(ObjectId.class, new FieldMetaData(RealmFieldType.OBJECT_ID, RealmFieldType.OBJECT_ID_LIST, true));
        map.put(Decimal128.class, new FieldMetaData(RealmFieldType.DECIMAL128, RealmFieldType.DECIMAL128_LIST, true));
        map.put(UUID.class, new FieldMetaData(RealmFieldType.UUID, RealmFieldType.UUID_LIST, true));
        map.put(RealmAny.class, new FieldMetaData(RealmFieldType.MIXED, RealmFieldType.MIXED_LIST, true));
        SUPPORTED_LIST_SIMPLE_FIELDS = Collections.unmodifiableMap(map);
        HashMap map2 = new HashMap();
        map2.put(String.class, new FieldMetaData(RealmFieldType.STRING, RealmFieldType.STRING_TO_STRING_MAP, true));
        map2.put(Short.TYPE, new FieldMetaData(RealmFieldType.INTEGER, RealmFieldType.STRING_TO_INTEGER_MAP, false));
        map2.put(Short.class, new FieldMetaData(RealmFieldType.INTEGER, RealmFieldType.STRING_TO_INTEGER_MAP, true));
        map2.put(Integer.TYPE, new FieldMetaData(RealmFieldType.INTEGER, RealmFieldType.STRING_TO_INTEGER_MAP, false));
        map2.put(Integer.class, new FieldMetaData(RealmFieldType.INTEGER, RealmFieldType.STRING_TO_INTEGER_MAP, true));
        map2.put(Long.TYPE, new FieldMetaData(RealmFieldType.INTEGER, RealmFieldType.STRING_TO_INTEGER_MAP, false));
        map2.put(Long.class, new FieldMetaData(RealmFieldType.INTEGER, RealmFieldType.STRING_TO_INTEGER_MAP, true));
        map2.put(Float.TYPE, new FieldMetaData(RealmFieldType.FLOAT, RealmFieldType.STRING_TO_FLOAT_MAP, false));
        map2.put(Float.class, new FieldMetaData(RealmFieldType.FLOAT, RealmFieldType.STRING_TO_FLOAT_MAP, true));
        map2.put(Double.TYPE, new FieldMetaData(RealmFieldType.DOUBLE, RealmFieldType.STRING_TO_DOUBLE_MAP, false));
        map2.put(Double.class, new FieldMetaData(RealmFieldType.DOUBLE, RealmFieldType.STRING_TO_DOUBLE_MAP, true));
        map2.put(Boolean.TYPE, new FieldMetaData(RealmFieldType.BOOLEAN, RealmFieldType.STRING_TO_BOOLEAN_MAP, false));
        map2.put(Boolean.class, new FieldMetaData(RealmFieldType.BOOLEAN, RealmFieldType.STRING_TO_BOOLEAN_MAP, true));
        map2.put(Byte.TYPE, new FieldMetaData(RealmFieldType.INTEGER, RealmFieldType.STRING_TO_INTEGER_MAP, false));
        map2.put(Byte.class, new FieldMetaData(RealmFieldType.INTEGER, RealmFieldType.STRING_TO_INTEGER_MAP, true));
        map2.put(byte[].class, new FieldMetaData(RealmFieldType.BINARY, RealmFieldType.STRING_TO_BINARY_MAP, true));
        map2.put(Date.class, new FieldMetaData(RealmFieldType.DATE, RealmFieldType.STRING_TO_DATE_MAP, true));
        map2.put(ObjectId.class, new FieldMetaData(RealmFieldType.OBJECT_ID, RealmFieldType.STRING_TO_OBJECT_ID_MAP, true));
        map2.put(Decimal128.class, new FieldMetaData(RealmFieldType.DECIMAL128, RealmFieldType.STRING_TO_DECIMAL128_MAP, true));
        map2.put(UUID.class, new FieldMetaData(RealmFieldType.UUID, RealmFieldType.STRING_TO_UUID_MAP, true));
        map2.put(RealmAny.class, new FieldMetaData(RealmFieldType.MIXED, RealmFieldType.STRING_TO_MIXED_MAP, true));
        SUPPORTED_DICTIONARY_SIMPLE_FIELDS = Collections.unmodifiableMap(map2);
        HashMap map3 = new HashMap();
        map3.put(String.class, new FieldMetaData(RealmFieldType.STRING, RealmFieldType.STRING_SET, true));
        map3.put(Short.TYPE, new FieldMetaData(RealmFieldType.INTEGER, RealmFieldType.INTEGER_SET, false));
        map3.put(Short.class, new FieldMetaData(RealmFieldType.INTEGER, RealmFieldType.INTEGER_SET, true));
        map3.put(Integer.TYPE, new FieldMetaData(RealmFieldType.INTEGER, RealmFieldType.INTEGER_SET, false));
        map3.put(Integer.class, new FieldMetaData(RealmFieldType.INTEGER, RealmFieldType.INTEGER_SET, true));
        map3.put(Long.TYPE, new FieldMetaData(RealmFieldType.INTEGER, RealmFieldType.INTEGER_SET, false));
        map3.put(Long.class, new FieldMetaData(RealmFieldType.INTEGER, RealmFieldType.INTEGER_SET, true));
        map3.put(Float.TYPE, new FieldMetaData(RealmFieldType.FLOAT, RealmFieldType.FLOAT_SET, false));
        map3.put(Float.class, new FieldMetaData(RealmFieldType.FLOAT, RealmFieldType.FLOAT_SET, true));
        map3.put(Double.TYPE, new FieldMetaData(RealmFieldType.DOUBLE, RealmFieldType.DOUBLE_SET, false));
        map3.put(Double.class, new FieldMetaData(RealmFieldType.DOUBLE, RealmFieldType.DOUBLE_SET, true));
        map3.put(Boolean.TYPE, new FieldMetaData(RealmFieldType.BOOLEAN, RealmFieldType.BOOLEAN_SET, false));
        map3.put(Boolean.class, new FieldMetaData(RealmFieldType.BOOLEAN, RealmFieldType.BOOLEAN_SET, true));
        map3.put(Byte.TYPE, new FieldMetaData(RealmFieldType.INTEGER, RealmFieldType.INTEGER_SET, false));
        map3.put(Byte.class, new FieldMetaData(RealmFieldType.INTEGER, RealmFieldType.INTEGER_SET, true));
        map3.put(byte[].class, new FieldMetaData(RealmFieldType.BINARY, RealmFieldType.BINARY_SET, true));
        map3.put(Date.class, new FieldMetaData(RealmFieldType.DATE, RealmFieldType.DATE_SET, true));
        map3.put(ObjectId.class, new FieldMetaData(RealmFieldType.OBJECT_ID, RealmFieldType.OBJECT_ID_SET, true));
        map3.put(Decimal128.class, new FieldMetaData(RealmFieldType.DECIMAL128, RealmFieldType.DECIMAL128_SET, true));
        map3.put(UUID.class, new FieldMetaData(RealmFieldType.UUID, RealmFieldType.UUID_SET, true));
        map3.put(RealmAny.class, new FieldMetaData(RealmFieldType.MIXED, RealmFieldType.MIXED_SET, true));
        SUPPORTED_SET_SIMPLE_FIELDS = Collections.unmodifiableMap(map3);
        HashMap map4 = new HashMap();
        map4.put(RealmObject.class, new FieldMetaData(RealmFieldType.OBJECT, null, false));
        map4.put(RealmList.class, new FieldMetaData(RealmFieldType.LIST, null, false));
        map4.put(RealmDictionary.class, new FieldMetaData(RealmFieldType.STRING_TO_LINK_MAP, null, false));
        map4.put(RealmSet.class, new FieldMetaData(RealmFieldType.LINK_SET, null, false));
        SUPPORTED_LINKED_FIELDS = Collections.unmodifiableMap(map4);
    }

    RealmObjectSchema(BaseRealm baseRealm, RealmSchema realmSchema, Table table, ColumnInfo columnInfo) {
        this.schema = realmSchema;
        this.realm = baseRealm;
        this.table = table;
        this.columnInfo = columnInfo;
    }

    public String getClassName() {
        return this.table.getClassName();
    }

    public boolean hasField(String str) {
        return this.table.getColumnKey(str) != -1;
    }

    public boolean hasIndex(String str) {
        checkLegalName(str);
        checkFieldExists(str);
        Table table = this.table;
        return table.hasSearchIndex(table.getColumnKey(str));
    }

    public boolean isRequired(String str) {
        return !this.table.isColumnNullable(getColumnKey(str));
    }

    public boolean isNullable(String str) {
        return this.table.isColumnNullable(getColumnKey(str));
    }

    public boolean isPrimaryKey(String str) {
        checkFieldExists(str);
        return str.equals(OsObjectStore.getPrimaryKeyForObject(this.realm.sharedRealm, getClassName()));
    }

    public boolean hasPrimaryKey() {
        return OsObjectStore.getPrimaryKeyForObject(this.realm.sharedRealm, getClassName()) != null;
    }

    public String getPrimaryKey() {
        String primaryKeyForObject = OsObjectStore.getPrimaryKeyForObject(this.realm.sharedRealm, getClassName());
        if (primaryKeyForObject != null) {
            return primaryKeyForObject;
        }
        throw new IllegalStateException(getClassName() + " doesn't have a primary key.");
    }

    public Set<String> getFieldNames() {
        LinkedHashSet linkedHashSet = new LinkedHashSet((int) this.table.getColumnCount());
        for (String str : this.table.getColumnNames()) {
            linkedHashSet.add(str);
        }
        return linkedHashSet;
    }

    public RealmFieldType getFieldType(String str) {
        return this.table.getColumnType(getColumnKey(str));
    }

    public boolean isEmbedded() {
        return this.table.isEmbedded();
    }

    public void setEmbedded(boolean z) {
        setEmbedded(z, false);
    }

    public void setEmbedded(boolean z, boolean z2) {
        if (hasPrimaryKey()) {
            throw new IllegalStateException("Embedded classes cannot have primary keys. This class has a primary key defined so cannot be marked as embedded: " + getClassName());
        }
        if (!this.table.setEmbedded(z, z2) && z) {
            throw new IllegalStateException("The class could not be marked as embedded as some objects of this type break some of the Embedded Objects invariants. In order to convert all objects to be embedded, they must have one and exactly one parent objectpointing to them.");
        }
    }

    boolean isPropertyAcceptableForEmbeddedObject(RealmFieldType realmFieldType) {
        return realmFieldType == RealmFieldType.OBJECT || realmFieldType == RealmFieldType.LIST;
    }

    RealmObjectSchema add(String str, RealmFieldType realmFieldType, boolean z, boolean z2, boolean z3) {
        long jAddColumn = this.table.addColumn(realmFieldType, str, !z3);
        if (z2) {
            this.table.addSearchIndex(jAddColumn);
        }
        if (z) {
            OsObjectStore.setPrimaryKeyForObject(this.realm.sharedRealm, getClassName(), str);
        }
        return this;
    }

    RealmObjectSchema add(String str, RealmFieldType realmFieldType, RealmObjectSchema realmObjectSchema) {
        this.table.addColumnLink(realmFieldType, str, this.realm.getSharedRealm().getTable(Table.getTableNameForClass(realmObjectSchema.getClassName())));
        return this;
    }

    long getAndCheckFieldColumnKey(String str) {
        long columnKey = this.columnInfo.getColumnKey(str);
        if (columnKey >= 0) {
            return columnKey;
        }
        throw new IllegalArgumentException("Field does not exist: " + str);
    }

    Table getTable() {
        return this.table;
    }

    long getFieldColumnKey(String str) {
        return this.columnInfo.getColumnKey(str);
    }

    static void checkLegalName(String str) {
        if (str == null || str.isEmpty()) {
            throw new IllegalArgumentException("Field name can not be null or empty");
        }
        if (str.contains(".")) {
            throw new IllegalArgumentException("Field name can not contain '.'");
        }
        if (str.length() > 63) {
            throw new IllegalArgumentException("Field name is currently limited to max 63 characters.");
        }
    }

    void checkFieldExists(String str) {
        if (this.table.getColumnKey(str) != -1) {
            return;
        }
        throw new IllegalArgumentException("Field name doesn't exist on object '" + getClassName() + "': " + str);
    }

    long getColumnKey(String str) {
        long columnKey = this.table.getColumnKey(str);
        if (columnKey != -1) {
            return columnKey;
        }
        throw new IllegalArgumentException(String.format(Locale.US, "Field name '%s' does not exist on schema for '%s'", str, getClassName()));
    }

    static final class DynamicColumnIndices extends ColumnInfo {
        private final Table table;

        DynamicColumnIndices(Table table) {
            super((ColumnInfo) null, false);
            this.table = table;
        }

        @Override // io.realm.internal.ColumnInfo
        public long getColumnKey(String str) {
            return this.table.getColumnKey(str);
        }

        @Override // io.realm.internal.ColumnInfo
        public ColumnInfo.ColumnDetails getColumnDetails(String str) {
            throw new UnsupportedOperationException("DynamicColumnIndices do not support 'getColumnDetails'");
        }

        @Override // io.realm.internal.ColumnInfo
        public void copyFrom(ColumnInfo columnInfo) {
            throw new UnsupportedOperationException("DynamicColumnIndices cannot be copied");
        }

        @Override // io.realm.internal.ColumnInfo
        protected ColumnInfo copy(boolean z) {
            throw new UnsupportedOperationException("DynamicColumnIndices cannot be copied");
        }

        @Override // io.realm.internal.ColumnInfo
        protected void copy(ColumnInfo columnInfo, ColumnInfo columnInfo2) {
            throw new UnsupportedOperationException("DynamicColumnIndices cannot copy");
        }
    }

    static final class FieldMetaData {
        final RealmFieldType collectionType;
        final boolean defaultNullable;
        final RealmFieldType fieldType;

        FieldMetaData(RealmFieldType realmFieldType, @Nullable RealmFieldType realmFieldType2, boolean z) {
            this.fieldType = realmFieldType;
            this.collectionType = realmFieldType2;
            this.defaultNullable = z;
        }
    }
}
