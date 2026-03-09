package io.realm;

import io.realm.internal.OsList;
import io.realm.internal.RealmObjectProxy;
import java.util.Locale;
import javax.annotation.Nullable;

/* JADX INFO: compiled from: ManagedListOperator.java */
/* JADX INFO: loaded from: classes4.dex */
final class RealmModelListOperator<T> extends ManagedListOperator<T> {

    @Nullable
    private final String className;

    @Override // io.realm.ManagedListOperator
    public boolean forRealmModel() {
        return true;
    }

    RealmModelListOperator(BaseRealm baseRealm, OsList osList, @Nullable Class<T> cls, @Nullable String str) {
        super(baseRealm, osList, cls);
        this.className = str;
    }

    @Override // io.realm.ManagedListOperator
    public T get(int i) {
        return (T) this.realm.get(this.clazz, this.className, this.osList.getUncheckedRow(i));
    }

    @Override // io.realm.ManagedListOperator
    protected void checkValidValue(@Nullable Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException("RealmList does not accept null values.");
        }
        if (!(obj instanceof RealmModel)) {
            throw new IllegalArgumentException(String.format(Locale.ENGLISH, "Unacceptable value type. Acceptable: %1$s, actual: %2$s .", "java.lang.String", obj.getClass().getName()));
        }
    }

    @Override // io.realm.ManagedListOperator
    public void appendValue(Object obj) {
        RealmModel realmModelCopyToRealm = (RealmModel) obj;
        boolean zCheckCanObjectBeCopied = CollectionUtils.checkCanObjectBeCopied(this.realm, realmModelCopyToRealm, this.className, CollectionUtils.LIST_TYPE);
        if (CollectionUtils.isEmbedded(this.realm, realmModelCopyToRealm)) {
            if (obj instanceof DynamicRealmObject) {
                throw new IllegalArgumentException("Embedded objects are not supported by RealmLists of DynamicRealmObjects yet.");
            }
            CollectionUtils.updateEmbeddedObject((Realm) this.realm, realmModelCopyToRealm, this.osList.createAndAddEmbeddedObject());
            return;
        }
        if (zCheckCanObjectBeCopied) {
            realmModelCopyToRealm = CollectionUtils.copyToRealm(this.realm, realmModelCopyToRealm);
        }
        this.osList.addRow(((RealmObjectProxy) realmModelCopyToRealm).realmGet$proxyState().getRow$realm().getObjectKey());
    }

    @Override // io.realm.ManagedListOperator
    protected void insertNull(int i) {
        throw new RuntimeException("Should not reach here.");
    }

    @Override // io.realm.ManagedListOperator
    public void insertValue(int i, Object obj) {
        checkInsertIndex(i);
        RealmModel realmModelCopyToRealm = (RealmModel) obj;
        boolean zCheckCanObjectBeCopied = CollectionUtils.checkCanObjectBeCopied(this.realm, realmModelCopyToRealm, this.className, CollectionUtils.LIST_TYPE);
        if (CollectionUtils.isEmbedded(this.realm, realmModelCopyToRealm)) {
            if (obj instanceof DynamicRealmObject) {
                throw new IllegalArgumentException("Embedded objects are not supported by RealmLists of DynamicRealmObjects yet.");
            }
            CollectionUtils.updateEmbeddedObject((Realm) this.realm, realmModelCopyToRealm, this.osList.createAndAddEmbeddedObject(i));
            return;
        }
        if (zCheckCanObjectBeCopied) {
            realmModelCopyToRealm = CollectionUtils.copyToRealm(this.realm, realmModelCopyToRealm);
        }
        this.osList.insertRow(i, ((RealmObjectProxy) realmModelCopyToRealm).realmGet$proxyState().getRow$realm().getObjectKey());
    }

    @Override // io.realm.ManagedListOperator
    protected void setNull(int i) {
        throw new RuntimeException("Should not reach here.");
    }

    @Override // io.realm.ManagedListOperator
    protected void setValue(int i, Object obj) {
        RealmModel realmModelCopyToRealm = (RealmModel) obj;
        boolean zCheckCanObjectBeCopied = CollectionUtils.checkCanObjectBeCopied(this.realm, realmModelCopyToRealm, this.className, CollectionUtils.LIST_TYPE);
        if (CollectionUtils.isEmbedded(this.realm, realmModelCopyToRealm)) {
            if (obj instanceof DynamicRealmObject) {
                throw new IllegalArgumentException("Embedded objects are not supported by RealmLists of DynamicRealmObjects yet.");
            }
            CollectionUtils.updateEmbeddedObject((Realm) this.realm, realmModelCopyToRealm, this.osList.createAndSetEmbeddedObject(i));
            return;
        }
        if (zCheckCanObjectBeCopied) {
            realmModelCopyToRealm = CollectionUtils.copyToRealm(this.realm, realmModelCopyToRealm);
        }
        this.osList.setRow(i, ((RealmObjectProxy) realmModelCopyToRealm).realmGet$proxyState().getRow$realm().getObjectKey());
    }
}
