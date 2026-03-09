package io.realm;

import io.realm.RealmObjectSchema;
import io.realm.internal.CheckedRow;
import io.realm.internal.OsObjectStore;
import io.realm.internal.OsResults;
import io.realm.internal.Table;
import io.realm.internal.Util;
import java.util.Date;
import java.util.Locale;

/* JADX INFO: loaded from: classes4.dex */
class MutableRealmObjectSchema extends RealmObjectSchema {
    MutableRealmObjectSchema(BaseRealm baseRealm, RealmSchema realmSchema, Table table) {
        super(baseRealm, realmSchema, table, new RealmObjectSchema.DynamicColumnIndices(table));
    }

    @Override // io.realm.RealmObjectSchema
    public RealmObjectSchema setClassName(String str) throws Exception {
        this.realm.checkNotInSync();
        checkEmpty(str);
        String tableNameForClass = Table.getTableNameForClass(str);
        if (str.length() > Table.CLASS_NAME_MAX_LENGTH) {
            throw new IllegalArgumentException(String.format(Locale.US, "Class name is too long. Limit is %1$d characters: '%2$s' (%3$d)", Integer.valueOf(Table.CLASS_NAME_MAX_LENGTH), str, Integer.valueOf(str.length())));
        }
        if (this.realm.sharedRealm.hasTable(tableNameForClass)) {
            throw new IllegalArgumentException("Class already exists: " + str);
        }
        String name = this.table.getName();
        String className = this.table.getClassName();
        String primaryKeyForObject = OsObjectStore.getPrimaryKeyForObject(this.realm.sharedRealm, className);
        if (primaryKeyForObject != null) {
            OsObjectStore.setPrimaryKeyForObject(this.realm.sharedRealm, className, null);
        }
        this.realm.sharedRealm.renameTable(name, tableNameForClass);
        if (primaryKeyForObject == null) {
            return this;
        }
        try {
            OsObjectStore.setPrimaryKeyForObject(this.realm.sharedRealm, str, primaryKeyForObject);
            return this;
        } catch (Exception e) {
            this.realm.sharedRealm.renameTable(this.table.getName(), name);
            throw e;
        }
    }

    private void checkEmpty(String str) {
        if (str == null || str.isEmpty()) {
            throw new IllegalArgumentException("Null or empty class names are not allowed");
        }
    }

    @Override // io.realm.RealmObjectSchema
    public RealmObjectSchema addField(String str, Class<?> cls, FieldAttribute... fieldAttributeArr) throws Exception {
        RealmObjectSchema.FieldMetaData fieldMetaData = SUPPORTED_LIST_SIMPLE_FIELDS.get(cls);
        if (fieldMetaData == null) {
            if (SUPPORTED_LINKED_FIELDS.containsKey(cls)) {
                throw new IllegalArgumentException("Use addRealmObjectField() instead to add fields that link to other RealmObjects: " + str);
            }
            if (RealmModel.class.isAssignableFrom(cls)) {
                throw new IllegalArgumentException(String.format(Locale.US, "Use 'addRealmObjectField()' instead to add fields that link to other RealmObjects: %s(%s)", str, cls));
            }
            throw new IllegalArgumentException(String.format(Locale.US, "Realm doesn't support this field type: %s(%s)", str, cls));
        }
        if (containsAttribute(fieldAttributeArr, FieldAttribute.PRIMARY_KEY)) {
            checkAddPrimaryKeyForSync();
            checkForObjectStoreInvalidPrimaryKeyTypes(str, cls);
        }
        checkNewFieldName(str);
        long jAddColumn = this.table.addColumn(fieldMetaData.fieldType, str, containsAttribute(fieldAttributeArr, FieldAttribute.REQUIRED) ? false : fieldMetaData.defaultNullable);
        try {
            addModifiers(str, fieldAttributeArr);
            return this;
        } catch (Exception e) {
            this.table.removeColumn(jAddColumn);
            throw e;
        }
    }

    @Override // io.realm.RealmObjectSchema
    public RealmObjectSchema addRealmObjectField(String str, RealmObjectSchema realmObjectSchema) {
        checkLegalName(str);
        checkFieldNameIsAvailable(str);
        this.table.addColumnLink(RealmFieldType.OBJECT, str, this.realm.sharedRealm.getTable(Table.getTableNameForClass(realmObjectSchema.getClassName())));
        return this;
    }

    @Override // io.realm.RealmObjectSchema
    public RealmObjectSchema addRealmListField(String str, RealmObjectSchema realmObjectSchema) {
        checkLegalName(str);
        checkFieldNameIsAvailable(str);
        this.table.addColumnLink(RealmFieldType.LIST, str, this.realm.sharedRealm.getTable(Table.getTableNameForClass(realmObjectSchema.getClassName())));
        return this;
    }

    @Override // io.realm.RealmObjectSchema
    public RealmObjectSchema addRealmListField(String str, Class<?> cls) {
        checkLegalName(str);
        checkFieldNameIsAvailable(str);
        RealmObjectSchema.FieldMetaData fieldMetaData = SUPPORTED_LIST_SIMPLE_FIELDS.get(cls);
        if (fieldMetaData == null) {
            if (cls.equals(RealmObjectSchema.class) || RealmModel.class.isAssignableFrom(cls)) {
                throw new IllegalArgumentException("Use 'addRealmListField(String name, RealmObjectSchema schema)' instead to add lists that link to other RealmObjects: " + str);
            }
            throw new IllegalArgumentException(String.format(Locale.US, "RealmList does not support lists with this type: %s(%s)", str, cls));
        }
        this.table.addColumn(fieldMetaData.collectionType, str, fieldMetaData.defaultNullable);
        return this;
    }

    @Override // io.realm.RealmObjectSchema
    public RealmObjectSchema addRealmDictionaryField(String str, Class<?> cls) {
        checkLegalName(str);
        checkFieldNameIsAvailable(str);
        RealmObjectSchema.FieldMetaData fieldMetaData = SUPPORTED_DICTIONARY_SIMPLE_FIELDS.get(cls);
        if (fieldMetaData == null) {
            if (cls.equals(RealmObjectSchema.class) || RealmModel.class.isAssignableFrom(cls)) {
                throw new IllegalArgumentException("Use 'addRealmDictionaryField(String name, RealmObjectSchema schema)' instead to add dictionaries that link to other RealmObjects: " + str);
            }
            throw new IllegalArgumentException(String.format(Locale.US, "RealmDictionary does not support dictionaries with this type: %s(%s)", str, cls));
        }
        this.table.addColumn(fieldMetaData.collectionType, str, fieldMetaData.defaultNullable);
        return this;
    }

    @Override // io.realm.RealmObjectSchema
    public RealmObjectSchema addRealmDictionaryField(String str, RealmObjectSchema realmObjectSchema) {
        checkLegalName(str);
        checkFieldNameIsAvailable(str);
        this.table.addColumnDictionaryLink(RealmFieldType.STRING_TO_LINK_MAP, str, this.realm.sharedRealm.getTable(Table.getTableNameForClass(realmObjectSchema.getClassName())));
        return this;
    }

    @Override // io.realm.RealmObjectSchema
    public RealmObjectSchema addRealmSetField(String str, RealmObjectSchema realmObjectSchema) {
        checkLegalName(str);
        checkFieldNameIsAvailable(str);
        this.table.addColumnSetLink(RealmFieldType.LINK_SET, str, this.realm.sharedRealm.getTable(Table.getTableNameForClass(realmObjectSchema.getClassName())));
        return this;
    }

    @Override // io.realm.RealmObjectSchema
    public RealmObjectSchema addRealmSetField(String str, Class<?> cls) {
        checkLegalName(str);
        checkFieldNameIsAvailable(str);
        RealmObjectSchema.FieldMetaData fieldMetaData = SUPPORTED_SET_SIMPLE_FIELDS.get(cls);
        if (fieldMetaData == null) {
            if (cls.equals(RealmObjectSchema.class) || RealmModel.class.isAssignableFrom(cls)) {
                throw new IllegalArgumentException("Use 'addRealmSetField(String name, RealmObjectSchema schema)' instead to add sets that link to other RealmObjects: " + str);
            }
            throw new IllegalArgumentException(String.format(Locale.US, "RealmSet does not support sets with this type: %s(%s)", str, cls));
        }
        this.table.addColumn(fieldMetaData.collectionType, str, fieldMetaData.defaultNullable);
        return this;
    }

    @Override // io.realm.RealmObjectSchema
    public RealmObjectSchema removeField(String str) {
        this.realm.checkNotInSync();
        checkLegalName(str);
        if (!hasField(str)) {
            throw new IllegalStateException(str + " does not exist.");
        }
        long columnKey = getColumnKey(str);
        String className = getClassName();
        if (str.equals(OsObjectStore.getPrimaryKeyForObject(this.realm.sharedRealm, className))) {
            OsObjectStore.setPrimaryKeyForObject(this.realm.sharedRealm, className, str);
        }
        this.table.removeColumn(columnKey);
        return this;
    }

    @Override // io.realm.RealmObjectSchema
    public RealmObjectSchema renameField(String str, String str2) {
        this.realm.checkNotInSync();
        checkLegalName(str);
        checkFieldExists(str);
        checkLegalName(str2);
        checkFieldNameIsAvailable(str2);
        this.table.renameColumn(getColumnKey(str), str2);
        return this;
    }

    @Override // io.realm.RealmObjectSchema
    public RealmObjectSchema addIndex(String str) {
        checkLegalName(str);
        checkFieldExists(str);
        long columnKey = getColumnKey(str);
        if (this.table.hasSearchIndex(columnKey)) {
            throw new IllegalStateException(str + " already has an index.");
        }
        this.table.addSearchIndex(columnKey);
        return this;
    }

    @Override // io.realm.RealmObjectSchema
    public RealmObjectSchema removeIndex(String str) {
        this.realm.checkNotInSync();
        checkLegalName(str);
        checkFieldExists(str);
        long columnKey = getColumnKey(str);
        if (!this.table.hasSearchIndex(columnKey)) {
            throw new IllegalStateException("Field is not indexed: " + str);
        }
        this.table.removeSearchIndex(columnKey);
        return this;
    }

    @Override // io.realm.RealmObjectSchema
    public RealmObjectSchema addPrimaryKey(String str) {
        checkAddPrimaryKeyForSync();
        checkLegalName(str);
        checkFieldExists(str);
        String primaryKeyForObject = OsObjectStore.getPrimaryKeyForObject(this.realm.sharedRealm, getClassName());
        if (primaryKeyForObject != null) {
            throw new IllegalStateException(String.format(Locale.ENGLISH, "Field '%s' has been already defined as primary key.", primaryKeyForObject));
        }
        long columnKey = getColumnKey(str);
        RealmFieldType fieldType = getFieldType(str);
        checkForObjectStoreInvalidPrimaryKeyTypes(str, fieldType);
        if (fieldType != RealmFieldType.STRING && !this.table.hasSearchIndex(columnKey)) {
            this.table.addSearchIndex(columnKey);
        }
        OsObjectStore.setPrimaryKeyForObject(this.realm.sharedRealm, getClassName(), str);
        return this;
    }

    @Override // io.realm.RealmObjectSchema
    public RealmObjectSchema removePrimaryKey() {
        this.realm.checkNotInSync();
        String primaryKeyForObject = OsObjectStore.getPrimaryKeyForObject(this.realm.sharedRealm, getClassName());
        if (primaryKeyForObject == null) {
            throw new IllegalStateException(getClassName() + " doesn't have a primary key.");
        }
        long columnKey = this.table.getColumnKey(primaryKeyForObject);
        if (this.table.hasSearchIndex(columnKey)) {
            this.table.removeSearchIndex(columnKey);
        }
        OsObjectStore.setPrimaryKeyForObject(this.realm.sharedRealm, getClassName(), null);
        return this;
    }

    @Override // io.realm.RealmObjectSchema
    public RealmObjectSchema setRequired(String str, boolean z) {
        long columnKey = this.table.getColumnKey(str);
        boolean zIsRequired = isRequired(str);
        RealmFieldType columnType = this.table.getColumnType(columnKey);
        if (columnType == RealmFieldType.OBJECT) {
            throw new IllegalArgumentException("Cannot modify the required state for RealmObject references: " + str);
        }
        if (columnType == RealmFieldType.LIST) {
            throw new IllegalArgumentException("Cannot modify the required state for RealmList references: " + str);
        }
        if (z && zIsRequired) {
            throw new IllegalStateException("Field is already required: " + str);
        }
        if (!z && !zIsRequired) {
            throw new IllegalStateException("Field is already nullable: " + str);
        }
        if (z) {
            try {
                this.table.convertColumnToNotNullable(columnKey);
                return this;
            } catch (RuntimeException e) {
                if (e.getMessage().contains("has null value(s) in property")) {
                    throw new IllegalStateException(e.getMessage());
                }
                throw e;
            }
        }
        this.table.convertColumnToNullable(columnKey);
        return this;
    }

    @Override // io.realm.RealmObjectSchema
    public RealmObjectSchema setNullable(String str, boolean z) {
        setRequired(str, !z);
        return this;
    }

    @Override // io.realm.RealmObjectSchema
    public RealmObjectSchema transform(RealmObjectSchema.Function function) {
        if (function != null) {
            OsResults osResultsCreateSnapshot = OsResults.createFromQuery(this.realm.sharedRealm, this.table.where()).createSnapshot();
            long size = osResultsCreateSnapshot.size();
            if (size > 2147483647L) {
                throw new UnsupportedOperationException("Too many results to iterate: " + size);
            }
            int size2 = (int) osResultsCreateSnapshot.size();
            for (int i = 0; i < size2; i++) {
                DynamicRealmObject dynamicRealmObject = new DynamicRealmObject(this.realm, new CheckedRow(osResultsCreateSnapshot.getUncheckedRow(i)));
                if (dynamicRealmObject.isValid()) {
                    function.apply(dynamicRealmObject);
                }
            }
        }
        return this;
    }

    @Override // io.realm.RealmObjectSchema
    String getPropertyClassName(String str) {
        String className = this.table.getLinkTarget(getColumnKey(str)).getClassName();
        if (Util.isEmptyString(className)) {
            throw new IllegalArgumentException(String.format("Property '%s' not found.", str));
        }
        return className;
    }

    private void addModifiers(String str, FieldAttribute[] fieldAttributeArr) {
        if (fieldAttributeArr != null) {
            boolean z = false;
            try {
                if (fieldAttributeArr.length > 0) {
                    if (containsAttribute(fieldAttributeArr, FieldAttribute.INDEXED)) {
                        addIndex(str);
                        z = true;
                    }
                    if (containsAttribute(fieldAttributeArr, FieldAttribute.PRIMARY_KEY)) {
                        addPrimaryKey(str);
                    }
                }
            } catch (Exception e) {
                long columnKey = getColumnKey(str);
                if (z) {
                    this.table.removeSearchIndex(columnKey);
                }
                throw ((RuntimeException) e);
            }
        }
    }

    static boolean containsAttribute(FieldAttribute[] fieldAttributeArr, FieldAttribute fieldAttribute) {
        if (fieldAttributeArr != null && fieldAttributeArr.length != 0) {
            for (FieldAttribute fieldAttribute2 : fieldAttributeArr) {
                if (fieldAttribute2 == fieldAttribute) {
                    return true;
                }
            }
        }
        return false;
    }

    private void checkNewFieldName(String str) {
        checkLegalName(str);
        checkFieldNameIsAvailable(str);
    }

    private void checkFieldNameIsAvailable(String str) {
        if (this.table.getColumnKey(str) == -1) {
            return;
        }
        throw new IllegalArgumentException("Field already exists in '" + getClassName() + "': " + str);
    }

    private void checkAddPrimaryKeyForSync() {
        if (this.realm.configuration.isSyncConfiguration()) {
            throw new UnsupportedOperationException("'addPrimaryKey' is not supported by synced Realms.");
        }
    }

    private void checkForObjectStoreInvalidPrimaryKeyTypes(String str, Class<?> cls) {
        if (cls == Boolean.TYPE || cls == Boolean.class) {
            checkForObjectStoreInvalidPrimaryKeyTypes(str, RealmFieldType.BOOLEAN);
        }
        if (cls == Date.class) {
            checkForObjectStoreInvalidPrimaryKeyTypes(str, RealmFieldType.DATE);
        }
    }

    /* JADX INFO: renamed from: io.realm.MutableRealmObjectSchema$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$io$realm$RealmFieldType;

        static {
            int[] iArr = new int[RealmFieldType.values().length];
            $SwitchMap$io$realm$RealmFieldType = iArr;
            try {
                iArr[RealmFieldType.BOOLEAN.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$io$realm$RealmFieldType[RealmFieldType.DATE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    private void checkForObjectStoreInvalidPrimaryKeyTypes(String str, RealmFieldType realmFieldType) {
        int i = AnonymousClass1.$SwitchMap$io$realm$RealmFieldType[realmFieldType.ordinal()];
        if (i == 1) {
            throw new IllegalArgumentException("Boolean fields cannot be marked as primary keys: " + str);
        }
        if (i != 2) {
            return;
        }
        throw new IllegalArgumentException("Date fields cannot be marked as primary keys: " + str);
    }
}
