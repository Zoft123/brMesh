package com.brgd.brblmesh.GeneralAdapter.model;

import io.realm.RealmObject;
import io.realm.com_brgd_brblmesh_GeneralAdapter_model_DeleteFixedGroupRealmProxyInterface;
import io.realm.internal.RealmObjectProxy;

/* JADX INFO: loaded from: classes.dex */
public class DeleteFixedGroup extends RealmObject implements com_brgd_brblmesh_GeneralAdapter_model_DeleteFixedGroupRealmProxyInterface {
    private int fixedId;

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_DeleteFixedGroupRealmProxyInterface
    public int realmGet$fixedId() {
        return this.fixedId;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_DeleteFixedGroupRealmProxyInterface
    public void realmSet$fixedId(int i) {
        this.fixedId = i;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public DeleteFixedGroup() {
        if (this instanceof RealmObjectProxy) {
            ((RealmObjectProxy) this).realm$injectObjectContext();
        }
    }

    public void setFixedId(int i) {
        realmSet$fixedId(i);
    }

    public int getFixedId() {
        return realmGet$fixedId();
    }
}
