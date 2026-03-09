package io.realm;

import io.realm.RealmObjectSchema;
import io.realm.internal.OsObjectStore;
import io.realm.internal.Table;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;

/* JADX INFO: loaded from: classes4.dex */
class MutableRealmSchema extends RealmSchema {
    MutableRealmSchema(BaseRealm baseRealm) {
        super(baseRealm, null);
    }

    @Override // io.realm.RealmSchema
    public RealmObjectSchema get(String str) {
        checkNotEmpty(str, "Null or empty class names are not allowed");
        String tableNameForClass = Table.getTableNameForClass(str);
        if (!this.realm.getSharedRealm().hasTable(tableNameForClass)) {
            return null;
        }
        return new MutableRealmObjectSchema(this.realm, this, this.realm.getSharedRealm().getTable(tableNameForClass));
    }

    @Override // io.realm.RealmSchema
    public Set<RealmObjectSchema> getAll() {
        String[] tablesNames = this.realm.getSharedRealm().getTablesNames();
        LinkedHashSet linkedHashSet = new LinkedHashSet(tablesNames.length);
        for (String str : tablesNames) {
            RealmObjectSchema realmObjectSchema = get(Table.getClassNameForTable(str));
            if (realmObjectSchema != null) {
                linkedHashSet.add(realmObjectSchema);
            }
        }
        return linkedHashSet;
    }

    @Override // io.realm.RealmSchema
    public RealmObjectSchema create(String str) {
        checkNotEmpty(str, "Null or empty class names are not allowed");
        String tableNameForClass = Table.getTableNameForClass(str);
        if (str.length() > Table.CLASS_NAME_MAX_LENGTH) {
            throw new IllegalArgumentException(String.format(Locale.US, "Class name is too long. Limit is %1$d characters: %2$s", Integer.valueOf(Table.CLASS_NAME_MAX_LENGTH), Integer.valueOf(str.length())));
        }
        return new MutableRealmObjectSchema(this.realm, this, this.realm.getSharedRealm().createTable(tableNameForClass));
    }

    @Override // io.realm.RealmSchema
    public RealmObjectSchema createWithPrimaryKeyField(String str, String str2, Class<?> cls, FieldAttribute... fieldAttributeArr) {
        checkNotEmpty(str, "Null or empty class names are not allowed");
        RealmObjectSchema.checkLegalName(str2);
        String strCheckAndGetTableNameFromClassName = checkAndGetTableNameFromClassName(str);
        RealmObjectSchema.FieldMetaData fieldMetaData = RealmObjectSchema.SUPPORTED_LIST_SIMPLE_FIELDS.get(cls);
        if (fieldMetaData == null || (fieldMetaData.fieldType != RealmFieldType.STRING && fieldMetaData.fieldType != RealmFieldType.INTEGER && fieldMetaData.fieldType != RealmFieldType.OBJECT_ID)) {
            throw new IllegalArgumentException(String.format("Realm doesn't support primary key field type '%s'.", cls));
        }
        return new MutableRealmObjectSchema(this.realm, this, this.realm.getSharedRealm().createTableWithPrimaryKey(strCheckAndGetTableNameFromClassName, str2, fieldMetaData.fieldType, MutableRealmObjectSchema.containsAttribute(fieldAttributeArr, FieldAttribute.REQUIRED) ? false : fieldMetaData.defaultNullable));
    }

    @Override // io.realm.RealmSchema
    public void remove(String str) {
        this.realm.checkNotInSync();
        checkNotEmpty(str, "Null or empty class names are not allowed");
        String tableNameForClass = Table.getTableNameForClass(str);
        if (!OsObjectStore.deleteTableForObject(this.realm.getSharedRealm(), str)) {
            throw new IllegalArgumentException("Cannot remove class because it is not in this Realm: " + str);
        }
        removeFromClassNameToSchemaMap(tableNameForClass);
    }

    @Override // io.realm.RealmSchema
    public RealmObjectSchema rename(String str, String str2) {
        this.realm.checkNotInSync();
        checkNotEmpty(str, "Class names cannot be empty or null");
        checkNotEmpty(str2, "Class names cannot be empty or null");
        String tableNameForClass = Table.getTableNameForClass(str);
        String tableNameForClass2 = Table.getTableNameForClass(str2);
        checkHasTable(str, "Cannot rename class because it doesn't exist in this Realm: " + str);
        if (this.realm.getSharedRealm().hasTable(tableNameForClass2)) {
            throw new IllegalArgumentException(str + " cannot be renamed because the new class already exists: " + str2);
        }
        this.realm.getSharedRealm().renameTable(tableNameForClass, tableNameForClass2);
        Table table = this.realm.getSharedRealm().getTable(tableNameForClass2);
        RealmObjectSchema realmObjectSchemaRemoveFromClassNameToSchemaMap = removeFromClassNameToSchemaMap(tableNameForClass);
        if (realmObjectSchemaRemoveFromClassNameToSchemaMap == null || !realmObjectSchemaRemoveFromClassNameToSchemaMap.getTable().isValid() || !realmObjectSchemaRemoveFromClassNameToSchemaMap.getClassName().equals(str2)) {
            realmObjectSchemaRemoveFromClassNameToSchemaMap = new MutableRealmObjectSchema(this.realm, this, table);
        }
        putToClassNameToSchemaMap(tableNameForClass2, realmObjectSchemaRemoveFromClassNameToSchemaMap);
        return realmObjectSchemaRemoveFromClassNameToSchemaMap;
    }

    private String checkAndGetTableNameFromClassName(String str) {
        if (str.length() > Table.CLASS_NAME_MAX_LENGTH) {
            throw new IllegalArgumentException(String.format(Locale.US, "Class name is too long. Limit is %1$d characters: %2$s", Integer.valueOf(Table.CLASS_NAME_MAX_LENGTH), Integer.valueOf(str.length())));
        }
        return Table.getTableNameForClass(str);
    }
}
