package com.brgd.brblmesh.GeneralAdapter.model;

import io.realm.RealmObject;
import io.realm.com_brgd_brblmesh_GeneralAdapter_model_RadarPhoneTypeRealmProxyInterface;
import io.realm.internal.RealmObjectProxy;

/* JADX INFO: loaded from: classes.dex */
public class RadarPhoneType extends RealmObject implements com_brgd_brblmesh_GeneralAdapter_model_RadarPhoneTypeRealmProxyInterface {
    private int phoneType;

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_RadarPhoneTypeRealmProxyInterface
    public int realmGet$phoneType() {
        return this.phoneType;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_RadarPhoneTypeRealmProxyInterface
    public void realmSet$phoneType(int i) {
        this.phoneType = i;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public RadarPhoneType() {
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
