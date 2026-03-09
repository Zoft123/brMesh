package com.brgd.brblmesh.GeneralAdapter.model;

import io.realm.RealmObject;
import io.realm.com_brgd_brblmesh_GeneralAdapter_model_DeleteGroupsRealmProxyInterface;
import io.realm.internal.RealmObjectProxy;

/* JADX INFO: loaded from: classes.dex */
public class DeleteGroups extends RealmObject implements com_brgd_brblmesh_GeneralAdapter_model_DeleteGroupsRealmProxyInterface {
    private int groupId;

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_DeleteGroupsRealmProxyInterface
    public int realmGet$groupId() {
        return this.groupId;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_DeleteGroupsRealmProxyInterface
    public void realmSet$groupId(int i) {
        this.groupId = i;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public DeleteGroups() {
        if (this instanceof RealmObjectProxy) {
            ((RealmObjectProxy) this).realm$injectObjectContext();
        }
    }

    public void setGroupId(int i) {
        realmSet$groupId(i);
    }

    public int getGroupId() {
        return realmGet$groupId();
    }
}
