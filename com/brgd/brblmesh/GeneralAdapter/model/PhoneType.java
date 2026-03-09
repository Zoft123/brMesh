package com.brgd.brblmesh.GeneralAdapter.model;

import io.realm.RealmObject;
import io.realm.com_brgd_brblmesh_GeneralAdapter_model_PhoneTypeRealmProxyInterface;
import io.realm.internal.RealmObjectProxy;

/* JADX INFO: loaded from: classes.dex */
public class PhoneType extends RealmObject implements com_brgd_brblmesh_GeneralAdapter_model_PhoneTypeRealmProxyInterface {
    private int phoneType;

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_PhoneTypeRealmProxyInterface
    public int realmGet$phoneType() {
        return this.phoneType;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_PhoneTypeRealmProxyInterface
    public void realmSet$phoneType(int i) {
        this.phoneType = i;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public PhoneType() {
        if (this instanceof RealmObjectProxy) {
            ((RealmObjectProxy) this).realm$injectObjectContext();
        }
    }

    public void setPhoneType(int i) {
        realmSet$phoneType(i);
    }

    public int getPhoneType() {
        return realmGet$phoneType();
    }
}
