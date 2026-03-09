package io.realm.internal;

import io.realm.FrozenPendingRow;
import io.realm.RealmChangeListener;
import io.realm.RealmFieldType;
import io.realm.internal.core.NativeRealmAny;
import java.lang.ref.WeakReference;
import java.util.Date;
import java.util.UUID;
import org.bson.types.Decimal128;
import org.bson.types.ObjectId;

/* JADX INFO: loaded from: classes4.dex */
public class PendingRow implements Row {
    private static final String PROXY_NOT_SET_MESSAGE = "The 'frontEnd' has not been set.";
    private static final String QUERY_EXECUTED_MESSAGE = "The query has been executed. This 'PendingRow' is not valid anymore.";
    private static final String QUERY_NOT_RETURNED_MESSAGE = "The pending query has not been executed.";
    private WeakReference<FrontEnd> frontEndRef;
    private RealmChangeListener<PendingRow> listener;
    private OsResults pendingOsResults;
    private boolean returnCheckedRow;
    private OsSharedRealm sharedRealm;

    public interface FrontEnd {
        void onQueryFinished(Row row);
    }

    @Override // io.realm.internal.Row
    public boolean isLoaded() {
        return false;
    }

    @Override // io.realm.internal.Row
    public boolean isValid() {
        return false;
    }

    public PendingRow(OsSharedRealm osSharedRealm, TableQuery tableQuery, boolean z) {
        this.sharedRealm = osSharedRealm;
        this.pendingOsResults = OsResults.createFromQuery(osSharedRealm, tableQuery);
        RealmChangeListener<PendingRow> realmChangeListener = new RealmChangeListener<PendingRow>() { // from class: io.realm.internal.PendingRow.1
            @Override // io.realm.RealmChangeListener
            public void onChange(PendingRow pendingRow) {
                PendingRow.this.notifyFrontEnd();
            }
        };
        this.listener = realmChangeListener;
        this.pendingOsResults.addListener(this, realmChangeListener);
        this.returnCheckedRow = z;
        osSharedRealm.addPendingRow(this);
    }

    public void setFrontEnd(FrontEnd frontEnd) {
        this.frontEndRef = new WeakReference<>(frontEnd);
    }

    @Override // io.realm.internal.Row
    public long getColumnCount() {
        throw new IllegalStateException(QUERY_NOT_RETURNED_MESSAGE);
    }

    @Override // io.realm.internal.Row
    public String[] getColumnNames() {
        throw new IllegalStateException(QUERY_NOT_RETURNED_MESSAGE);
    }

    @Override // io.realm.internal.Row
    public long getColumnKey(String str) {
        throw new IllegalStateException(QUERY_NOT_RETURNED_MESSAGE);
    }

    @Override // io.realm.internal.Row
    public RealmFieldType getColumnType(long j) {
        throw new IllegalStateException(QUERY_NOT_RETURNED_MESSAGE);
    }

    @Override // io.realm.internal.Row
    public Table getTable() {
        throw new IllegalStateException(QUERY_NOT_RETURNED_MESSAGE);
    }

    @Override // io.realm.internal.Row
    public long getObjectKey() {
        throw new IllegalStateException(QUERY_NOT_RETURNED_MESSAGE);
    }

    @Override // io.realm.internal.Row
    public long getLong(long j) {
        throw new IllegalStateException(QUERY_NOT_RETURNED_MESSAGE);
    }

    @Override // io.realm.internal.Row
    public boolean getBoolean(long j) {
        throw new IllegalStateException(QUERY_NOT_RETURNED_MESSAGE);
    }

    @Override // io.realm.internal.Row
    public float getFloat(long j) {
        throw new IllegalStateException(QUERY_NOT_RETURNED_MESSAGE);
    }

    @Override // io.realm.internal.Row
    public double getDouble(long j) {
        throw new IllegalStateException(QUERY_NOT_RETURNED_MESSAGE);
    }

    @Override // io.realm.internal.Row
    public Date getDate(long j) {
        throw new IllegalStateException(QUERY_NOT_RETURNED_MESSAGE);
    }

    @Override // io.realm.internal.Row
    public String getString(long j) {
        throw new IllegalStateException(QUERY_NOT_RETURNED_MESSAGE);
    }

    @Override // io.realm.internal.Row
    public byte[] getBinaryByteArray(long j) {
        throw new IllegalStateException(QUERY_NOT_RETURNED_MESSAGE);
    }

    @Override // io.realm.internal.Row
    public Decimal128 getDecimal128(long j) {
        throw new IllegalStateException(QUERY_NOT_RETURNED_MESSAGE);
    }

    @Override // io.realm.internal.Row
    public ObjectId getObjectId(long j) {
        throw new IllegalStateException(QUERY_NOT_RETURNED_MESSAGE);
    }

    @Override // io.realm.internal.Row
    public UUID getUUID(long j) {
        throw new IllegalStateException(QUERY_NOT_RETURNED_MESSAGE);
    }

    @Override // io.realm.internal.Row
    public NativeRealmAny getNativeRealmAny(long j) {
        throw new IllegalStateException(QUERY_NOT_RETURNED_MESSAGE);
    }

    @Override // io.realm.internal.Row
    public long getLink(long j) {
        throw new IllegalStateException(QUERY_NOT_RETURNED_MESSAGE);
    }

    @Override // io.realm.internal.Row
    public boolean isNullLink(long j) {
        throw new IllegalStateException(QUERY_NOT_RETURNED_MESSAGE);
    }

    @Override // io.realm.internal.Row
    public OsList getModelList(long j) {
        throw new IllegalStateException(QUERY_NOT_RETURNED_MESSAGE);
    }

    @Override // io.realm.internal.Row
    public OsList getValueList(long j, RealmFieldType realmFieldType) {
        throw new IllegalStateException(QUERY_NOT_RETURNED_MESSAGE);
    }

    @Override // io.realm.internal.Row
    public OsMap getRealmAnyMap(long j) {
        throw new IllegalStateException(QUERY_NOT_RETURNED_MESSAGE);
    }

    @Override // io.realm.internal.Row
    public OsMap getModelMap(long j) {
        throw new IllegalStateException(QUERY_NOT_RETURNED_MESSAGE);
    }

    @Override // io.realm.internal.Row
    public OsMap getValueMap(long j, RealmFieldType realmFieldType) {
        throw new IllegalStateException(QUERY_NOT_RETURNED_MESSAGE);
    }

    @Override // io.realm.internal.Row
    public OsSet getRealmAnySet(long j) {
        throw new IllegalStateException(QUERY_NOT_RETURNED_MESSAGE);
    }

    @Override // io.realm.internal.Row
    public OsSet getModelSet(long j) {
        throw new IllegalStateException(QUERY_NOT_RETURNED_MESSAGE);
    }

    @Override // io.realm.internal.Row
    public OsSet getValueSet(long j, RealmFieldType realmFieldType) {
        throw new IllegalStateException(QUERY_NOT_RETURNED_MESSAGE);
    }

    @Override // io.realm.internal.Row
    public void setLong(long j, long j2) {
        throw new IllegalStateException(QUERY_NOT_RETURNED_MESSAGE);
    }

    @Override // io.realm.internal.Row
    public void setBoolean(long j, boolean z) {
        throw new IllegalStateException(QUERY_NOT_RETURNED_MESSAGE);
    }

    @Override // io.realm.internal.Row
    public void setFloat(long j, float f) {
        throw new IllegalStateException(QUERY_NOT_RETURNED_MESSAGE);
    }

    @Override // io.realm.internal.Row
    public void setDouble(long j, double d) {
        throw new IllegalStateException(QUERY_NOT_RETURNED_MESSAGE);
    }

    @Override // io.realm.internal.Row
    public void setDate(long j, Date date) {
        throw new IllegalStateException(QUERY_NOT_RETURNED_MESSAGE);
    }

    @Override // io.realm.internal.Row
    public void setString(long j, String str) {
        throw new IllegalStateException(QUERY_NOT_RETURNED_MESSAGE);
    }

    @Override // io.realm.internal.Row
    public void setRealmAny(long j, long j2) {
        throw new IllegalStateException(QUERY_NOT_RETURNED_MESSAGE);
    }

    @Override // io.realm.internal.Row
    public void setBinaryByteArray(long j, byte[] bArr) {
        throw new IllegalStateException(QUERY_NOT_RETURNED_MESSAGE);
    }

    @Override // io.realm.internal.Row
    public void setLink(long j, long j2) {
        throw new IllegalStateException(QUERY_NOT_RETURNED_MESSAGE);
    }

    @Override // io.realm.internal.Row
    public void nullifyLink(long j) {
        throw new IllegalStateException(QUERY_NOT_RETURNED_MESSAGE);
    }

    @Override // io.realm.internal.Row
    public boolean isNull(long j) {
        throw new IllegalStateException(QUERY_NOT_RETURNED_MESSAGE);
    }

    @Override // io.realm.internal.Row
    public void setNull(long j) {
        throw new IllegalStateException(QUERY_NOT_RETURNED_MESSAGE);
    }

    @Override // io.realm.internal.Row
    public void setDecimal128(long j, Decimal128 decimal128) {
        throw new IllegalStateException(QUERY_NOT_RETURNED_MESSAGE);
    }

    @Override // io.realm.internal.Row
    public void setObjectId(long j, ObjectId objectId) {
        throw new IllegalStateException(QUERY_NOT_RETURNED_MESSAGE);
    }

    @Override // io.realm.internal.Row
    public void setUUID(long j, UUID uuid) {
        throw new IllegalStateException(QUERY_NOT_RETURNED_MESSAGE);
    }

    @Override // io.realm.internal.Row
    public long createEmbeddedObject(long j, RealmFieldType realmFieldType) {
        throw new IllegalStateException(QUERY_NOT_RETURNED_MESSAGE);
    }

    @Override // io.realm.internal.Row
    public void checkIfAttached() {
        throw new IllegalStateException(QUERY_NOT_RETURNED_MESSAGE);
    }

    @Override // io.realm.internal.Row
    public boolean hasColumn(String str) {
        throw new IllegalStateException(QUERY_NOT_RETURNED_MESSAGE);
    }

    @Override // io.realm.internal.Row
    public Row freeze(OsSharedRealm osSharedRealm) {
        return FrozenPendingRow.INSTANCE;
    }

    private void clearPendingCollection() {
        this.pendingOsResults.removeListener(this, this.listener);
        this.pendingOsResults = null;
        this.listener = null;
        this.sharedRealm.removePendingRow(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyFrontEnd() {
        WeakReference<FrontEnd> weakReference = this.frontEndRef;
        if (weakReference == null) {
            throw new IllegalStateException(PROXY_NOT_SET_MESSAGE);
        }
        FrontEnd frontEnd = weakReference.get();
        if (frontEnd == null) {
            clearPendingCollection();
            return;
        }
        if (this.pendingOsResults.isValid()) {
            UncheckedRow uncheckedRowFirstUncheckedRow = this.pendingOsResults.firstUncheckedRow();
            clearPendingCollection();
            if (uncheckedRowFirstUncheckedRow != null) {
                if (this.returnCheckedRow) {
                    uncheckedRowFirstUncheckedRow = CheckedRow.getFromRow(uncheckedRowFirstUncheckedRow);
                }
                frontEnd.onQueryFinished(uncheckedRowFirstUncheckedRow);
                return;
            }
            frontEnd.onQueryFinished(InvalidRow.INSTANCE);
            return;
        }
        clearPendingCollection();
    }

    public void executeQuery() {
        if (this.pendingOsResults == null) {
            throw new IllegalStateException(QUERY_EXECUTED_MESSAGE);
        }
        notifyFrontEnd();
    }
}
