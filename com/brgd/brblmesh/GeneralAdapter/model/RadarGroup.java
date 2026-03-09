package com.brgd.brblmesh.GeneralAdapter.model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.com_brgd_brblmesh_GeneralAdapter_model_RadarGroupRealmProxyInterface;
import io.realm.internal.RealmObjectProxy;

/* JADX INFO: loaded from: classes.dex */
public class RadarGroup extends RealmObject implements com_brgd_brblmesh_GeneralAdapter_model_RadarGroupRealmProxyInterface {
    private RealmList<RadarDevice> bleDeviceRealmList;
    private int fixedId;
    private String fixedName;
    private int index;

    @Ignore
    public boolean isDelete;

    @Ignore
    public boolean isRename;

    @Ignore
    public boolean isSelect;

    @Ignore
    public boolean isSort;

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_RadarGroupRealmProxyInterface
    public RealmList realmGet$bleDeviceRealmList() {
        return this.bleDeviceRealmList;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_RadarGroupRealmProxyInterface
    public int realmGet$fixedId() {
        return this.fixedId;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_RadarGroupRealmProxyInterface
    public String realmGet$fixedName() {
        return this.fixedName;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_RadarGroupRealmProxyInterface
    public int realmGet$index() {
        return this.index;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_RadarGroupRealmProxyInterface
    public void realmSet$bleDeviceRealmList(RealmList realmList) {
        this.bleDeviceRealmList = realmList;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_RadarGroupRealmProxyInterface
    public void realmSet$fixedId(int i) {
        this.fixedId = i;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_RadarGroupRealmProxyInterface
    public void realmSet$fixedName(String str) {
        this.fixedName = str;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_RadarGroupRealmProxyInterface
    public void realmSet$index(int i) {
        this.index = i;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public RadarGroup() {
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

    public void setIndex(int i) {
        realmSet$index(i);
    }

    public int getIndex() {
        return realmGet$index();
    }

    public void setBleDeviceRealmList(RealmList<RadarDevice> realmList) {
        realmSet$bleDeviceRealmList(realmList);
    }

    public RealmList<RadarDevice> getBleDeviceRealmList() {
        return realmGet$bleDeviceRealmList();
    }
}
