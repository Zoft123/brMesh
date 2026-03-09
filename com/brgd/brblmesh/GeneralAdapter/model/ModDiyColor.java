package com.brgd.brblmesh.GeneralAdapter.model;

import io.realm.RealmObject;
import io.realm.com_brgd_brblmesh_GeneralAdapter_model_ModDiyColorRealmProxyInterface;
import io.realm.internal.RealmObjectProxy;

/* JADX INFO: loaded from: classes.dex */
public class ModDiyColor extends RealmObject implements com_brgd_brblmesh_GeneralAdapter_model_ModDiyColorRealmProxyInterface {
    private int colorIndex;
    private String customModId;
    private int diyColor;
    private int diyColorR;
    private int modNumber;

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_ModDiyColorRealmProxyInterface
    public int realmGet$colorIndex() {
        return this.colorIndex;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_ModDiyColorRealmProxyInterface
    public String realmGet$customModId() {
        return this.customModId;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_ModDiyColorRealmProxyInterface
    public int realmGet$diyColor() {
        return this.diyColor;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_ModDiyColorRealmProxyInterface
    public int realmGet$diyColorR() {
        return this.diyColorR;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_ModDiyColorRealmProxyInterface
    public int realmGet$modNumber() {
        return this.modNumber;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_ModDiyColorRealmProxyInterface
    public void realmSet$colorIndex(int i) {
        this.colorIndex = i;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_ModDiyColorRealmProxyInterface
    public void realmSet$customModId(String str) {
        this.customModId = str;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_ModDiyColorRealmProxyInterface
    public void realmSet$diyColor(int i) {
        this.diyColor = i;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_ModDiyColorRealmProxyInterface
    public void realmSet$diyColorR(int i) {
        this.diyColorR = i;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_ModDiyColorRealmProxyInterface
    public void realmSet$modNumber(int i) {
        this.modNumber = i;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public ModDiyColor() {
        if (this instanceof RealmObjectProxy) {
            ((RealmObjectProxy) this).realm$injectObjectContext();
        }
    }

    public void setModNumber(int i) {
        realmSet$modNumber(i);
    }

    public int getModNumber() {
        return realmGet$modNumber();
    }

    public void setColorIndex(int i) {
        realmSet$colorIndex(i);
    }

    public int getColorIndex() {
        return realmGet$colorIndex();
    }

    public void setDiyColor(int i) {
        realmSet$diyColor(i);
    }

    public int getDiyColor() {
        return realmGet$diyColor();
    }

    public void setDiyColorR(int i) {
        realmSet$diyColorR(i);
    }

    public int getDiyColorR() {
        return realmGet$diyColorR();
    }

    public void setCustomModId(String str) {
        realmSet$customModId(str);
    }

    public String getCustomModId() {
        return realmGet$customModId();
    }
}
