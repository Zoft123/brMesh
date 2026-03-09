package com.brgd.brblmesh.GeneralAdapter.model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.com_brgd_brblmesh_GeneralAdapter_model_FixedGroupRealmProxyInterface;
import io.realm.internal.RealmObjectProxy;

/* JADX INFO: loaded from: classes.dex */
public class FixedGroup extends RealmObject implements com_brgd_brblmesh_GeneralAdapter_model_FixedGroupRealmProxyInterface {
    private RealmList<BleDevice> bleDeviceRealmList;
    private String fileName;
    private int fixedId;
    private String fixedName;
    private int iconIndex;
    private int index;

    @Ignore
    public boolean isDelete;

    @Ignore
    public boolean isRename;

    @Ignore
    public boolean isSelect;

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_FixedGroupRealmProxyInterface
    public RealmList realmGet$bleDeviceRealmList() {
        return this.bleDeviceRealmList;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_FixedGroupRealmProxyInterface
    public String realmGet$fileName() {
        return this.fileName;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_FixedGroupRealmProxyInterface
    public int realmGet$fixedId() {
        return this.fixedId;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_FixedGroupRealmProxyInterface
    public String realmGet$fixedName() {
        return this.fixedName;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_FixedGroupRealmProxyInterface
    public int realmGet$iconIndex() {
        return this.iconIndex;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_FixedGroupRealmProxyInterface
    public int realmGet$index() {
        return this.index;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_FixedGroupRealmProxyInterface
    public void realmSet$bleDeviceRealmList(RealmList realmList) {
        this.bleDeviceRealmList = realmList;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_FixedGroupRealmProxyInterface
    public void realmSet$fileName(String str) {
        this.fileName = str;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_FixedGroupRealmProxyInterface
    public void realmSet$fixedId(int i) {
        this.fixedId = i;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_FixedGroupRealmProxyInterface
    public void realmSet$fixedName(String str) {
        this.fixedName = str;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_FixedGroupRealmProxyInterface
    public void realmSet$iconIndex(int i) {
        this.iconIndex = i;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_FixedGroupRealmProxyInterface
    public void realmSet$index(int i) {
        this.index = i;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public FixedGroup() {
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

    public void setFixedName(String str) {
        realmSet$fixedName(str);
    }

    public String getFixedName() {
        return realmGet$fixedName();
    }

    public void setBleDeviceRealmList(RealmList<BleDevice> realmList) {
        realmSet$bleDeviceRealmList(realmList);
    }

    public RealmList<BleDevice> getBleDeviceRealmList() {
        return realmGet$bleDeviceRealmList();
    }

    public void setIndex(int i) {
        realmSet$index(i);
    }

    public int getIndex() {
        return realmGet$index();
    }

    public void setFileName(String str) {
        realmSet$fileName(str);
    }

    public String getFileName() {
        return realmGet$fileName();
    }

    public void setIconIndex(int i) {
        realmSet$iconIndex(i);
    }

    public int getIconIndex() {
        return realmGet$iconIndex();
    }
}
