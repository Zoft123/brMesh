package com.brgd.brblmesh.GeneralAdapter.model;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.com_brgd_brblmesh_GeneralAdapter_model_DiyColorRealmProxyInterface;
import io.realm.internal.RealmObjectProxy;

/* JADX INFO: loaded from: classes.dex */
public class DiyColor extends RealmObject implements com_brgd_brblmesh_GeneralAdapter_model_DiyColorRealmProxyInterface {
    private String colorIdentifier;
    private int colorValue;
    private int diyColorR;

    @Ignore
    public boolean isSelect;

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_DiyColorRealmProxyInterface
    public String realmGet$colorIdentifier() {
        return this.colorIdentifier;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_DiyColorRealmProxyInterface
    public int realmGet$colorValue() {
        return this.colorValue;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_DiyColorRealmProxyInterface
    public int realmGet$diyColorR() {
        return this.diyColorR;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_DiyColorRealmProxyInterface
    public void realmSet$colorIdentifier(String str) {
        this.colorIdentifier = str;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_DiyColorRealmProxyInterface
    public void realmSet$colorValue(int i) {
        this.colorValue = i;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_DiyColorRealmProxyInterface
    public void realmSet$diyColorR(int i) {
        this.diyColorR = i;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public DiyColor() {
        if (this instanceof RealmObjectProxy) {
            ((RealmObjectProxy) this).realm$injectObjectContext();
        }
    }

    public void setColorValue(int i) {
        realmSet$colorValue(i);
    }

    public int getColorValue() {
        return realmGet$colorValue();
    }

    public void setColorIdentifier(String str) {
        realmSet$colorIdentifier(str);
    }

    public String getColorIdentifier() {
        return realmGet$colorIdentifier();
    }

    public void setDiyColorR(int i) {
        realmSet$diyColorR(i);
    }

    public int getDiyColorR() {
        return realmGet$diyColorR();
    }
}
