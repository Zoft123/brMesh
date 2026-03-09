package com.brgd.brblmesh.GeneralAdapter.model;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.com_brgd_brblmesh_GeneralAdapter_model_GroupsRealmProxyInterface;
import io.realm.internal.RealmObjectProxy;

/* JADX INFO: loaded from: classes.dex */
public class Groups extends RealmObject implements com_brgd_brblmesh_GeneralAdapter_model_GroupsRealmProxyInterface {
    private int groupId;
    private String groupName;
    private int index;

    @Ignore
    public boolean isDelete;

    @Ignore
    public boolean isRename;

    @Ignore
    public boolean isSelect;

    @Ignore
    public boolean isSort;

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_GroupsRealmProxyInterface
    public int realmGet$groupId() {
        return this.groupId;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_GroupsRealmProxyInterface
    public String realmGet$groupName() {
        return this.groupName;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_GroupsRealmProxyInterface
    public int realmGet$index() {
        return this.index;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_GroupsRealmProxyInterface
    public void realmSet$groupId(int i) {
        this.groupId = i;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_GroupsRealmProxyInterface
    public void realmSet$groupName(String str) {
        this.groupName = str;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_GroupsRealmProxyInterface
    public void realmSet$index(int i) {
        this.index = i;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public Groups() {
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

    public void setGroupName(String str) {
        realmSet$groupName(str);
    }

    public String getGroupName() {
        return realmGet$groupName();
    }

    public void setIndex(int i) {
        realmSet$index(i);
    }

    public int getIndex() {
        return realmGet$index();
    }
}
