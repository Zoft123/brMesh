package io.realm.internal;

import cn.com.broadlink.blelight.util.EListUtils;
import io.realm.RealmFieldType;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: classes4.dex */
public abstract class ColumnInfo {
    private final Map<String, ColumnDetails> columnKeysFromColumnNames;
    private final Map<String, ColumnDetails> columnkeysFromJavaFieldNames;
    private final Map<String, String> javaFieldNameToInternalNames;
    private final boolean mutable;

    protected abstract ColumnInfo copy(boolean z);

    protected abstract void copy(ColumnInfo columnInfo, ColumnInfo columnInfo2);

    public static final class ColumnDetails {
        public final long columnKey;
        public final RealmFieldType columnType;
        public final String linkedClassName;

        private ColumnDetails(long j, RealmFieldType realmFieldType, @Nullable String str) {
            this.columnKey = j;
            this.columnType = realmFieldType;
            this.linkedClassName = str;
        }

        ColumnDetails(Property property) {
            this(property.getColumnKey(), property.getType(), property.getLinkedObjectName());
        }

        public String toString() {
            return "ColumnDetails[" + this.columnKey + ", " + this.columnType + ", " + this.linkedClassName + "]";
        }
    }

    protected ColumnInfo(int i) {
        this(i, true);
    }

    protected ColumnInfo(@Nullable ColumnInfo columnInfo, boolean z) {
        this(columnInfo == null ? 0 : columnInfo.columnkeysFromJavaFieldNames.size(), z);
        if (columnInfo != null) {
            this.columnkeysFromJavaFieldNames.putAll(columnInfo.columnkeysFromJavaFieldNames);
        }
    }

    private ColumnInfo(int i, boolean z) {
        this.columnkeysFromJavaFieldNames = new HashMap(i);
        this.columnKeysFromColumnNames = new HashMap(i);
        this.javaFieldNameToInternalNames = new HashMap(i);
        this.mutable = z;
    }

    public final boolean isMutable() {
        return this.mutable;
    }

    public long getColumnKey(String str) {
        ColumnDetails columnDetails = this.columnkeysFromJavaFieldNames.get(str);
        if (columnDetails == null) {
            return -1L;
        }
        return columnDetails.columnKey;
    }

    @Nullable
    public ColumnDetails getColumnDetails(String str) {
        return this.columnkeysFromJavaFieldNames.get(str);
    }

    @Nullable
    public String getInternalFieldName(String str) {
        return this.javaFieldNameToInternalNames.get(str);
    }

    public void copyFrom(ColumnInfo columnInfo) {
        if (!this.mutable) {
            throw new UnsupportedOperationException("Attempt to modify an immutable ColumnInfo");
        }
        if (columnInfo == null) {
            throw new NullPointerException("Attempt to copy null ColumnInfo");
        }
        this.columnkeysFromJavaFieldNames.clear();
        this.columnkeysFromJavaFieldNames.putAll(columnInfo.columnkeysFromJavaFieldNames);
        this.columnKeysFromColumnNames.clear();
        this.columnKeysFromColumnNames.putAll(columnInfo.columnKeysFromColumnNames);
        this.javaFieldNameToInternalNames.clear();
        this.javaFieldNameToInternalNames.putAll(columnInfo.javaFieldNameToInternalNames);
        copy(columnInfo, this);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("ColumnInfo[");
        sb.append("mutable=" + this.mutable);
        sb.append(EListUtils.DEFAULT_JOIN_SEPARATOR);
        boolean z = false;
        if (this.columnkeysFromJavaFieldNames != null) {
            sb.append("JavaFieldNames=[");
            boolean z2 = false;
            for (Map.Entry<String, ColumnDetails> entry : this.columnkeysFromJavaFieldNames.entrySet()) {
                if (z2) {
                    sb.append(EListUtils.DEFAULT_JOIN_SEPARATOR);
                }
                sb.append(entry.getKey());
                sb.append("->");
                sb.append(entry.getValue());
                z2 = true;
            }
            sb.append("]");
        }
        if (this.columnKeysFromColumnNames != null) {
            sb.append(", InternalFieldNames=[");
            for (Map.Entry<String, ColumnDetails> entry2 : this.columnKeysFromColumnNames.entrySet()) {
                if (z) {
                    sb.append(EListUtils.DEFAULT_JOIN_SEPARATOR);
                }
                sb.append(entry2.getKey());
                sb.append("->");
                sb.append(entry2.getValue());
                z = true;
            }
            sb.append("]");
        }
        sb.append("]");
        return sb.toString();
    }

    protected final long addColumnDetails(String str, String str2, OsObjectSchemaInfo osObjectSchemaInfo) {
        Property property = osObjectSchemaInfo.getProperty(str2);
        ColumnDetails columnDetails = new ColumnDetails(property);
        this.columnkeysFromJavaFieldNames.put(str, columnDetails);
        this.columnKeysFromColumnNames.put(str2, columnDetails);
        this.javaFieldNameToInternalNames.put(str, str2);
        return property.getColumnKey();
    }

    protected final void addBacklinkDetails(OsSchemaInfo osSchemaInfo, String str, String str2, String str3) {
        this.columnkeysFromJavaFieldNames.put(str, new ColumnDetails(osSchemaInfo.getObjectSchemaInfo(str2).getProperty(str3).getColumnKey(), RealmFieldType.LINKING_OBJECTS, str2));
    }

    public Map<String, ColumnDetails> getColumnKeysMap() {
        return this.columnkeysFromJavaFieldNames;
    }
}
