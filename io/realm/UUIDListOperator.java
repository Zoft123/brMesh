package io.realm;

import io.realm.internal.OsList;
import java.util.Locale;
import java.util.UUID;
import javax.annotation.Nullable;

/* JADX INFO: compiled from: ManagedListOperator.java */
/* JADX INFO: loaded from: classes4.dex */
final class UUIDListOperator extends ManagedListOperator<UUID> {
    @Override // io.realm.ManagedListOperator
    public boolean forRealmModel() {
        return false;
    }

    UUIDListOperator(BaseRealm baseRealm, OsList osList, Class<UUID> cls) {
        super(baseRealm, osList, cls);
    }

    @Override // io.realm.ManagedListOperator
    @Nullable
    public UUID get(int i) {
        return (UUID) this.osList.getValue(i);
    }

    @Override // io.realm.ManagedListOperator
    protected void checkValidValue(@Nullable Object obj) {
        if (obj != null && !(obj instanceof UUID)) {
            throw new IllegalArgumentException(String.format(Locale.ENGLISH, "Unacceptable value type. Acceptable: %1$s, actual: %2$s .", "java.util.UUID", obj.getClass().getName()));
        }
    }

    @Override // io.realm.ManagedListOperator
    public void appendValue(Object obj) {
        this.osList.addUUID((UUID) obj);
    }

    @Override // io.realm.ManagedListOperator
    public void insertValue(int i, Object obj) {
        this.osList.insertUUID(i, (UUID) obj);
    }

    @Override // io.realm.ManagedListOperator
    protected void setValue(int i, Object obj) {
        this.osList.setUUID(i, (UUID) obj);
    }
}
