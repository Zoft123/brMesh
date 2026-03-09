package com.brgd.brblmesh.GeneralAdapter.model;

import io.realm.RealmObject;
import io.realm.com_brgd_brblmesh_GeneralAdapter_model_SceneDeviceRealmProxyInterface;
import io.realm.internal.RealmObjectProxy;

/* JADX INFO: loaded from: classes.dex */
public class SceneDevice extends RealmObject implements com_brgd_brblmesh_GeneralAdapter_model_SceneDeviceRealmProxyInterface {
    private int color;
    private String customModId;
    private String did;
    private int lightness;
    private int modBrightness;
    private int modNumber;
    private int modSpeed;
    private int sceneNumber;
    private int temp;
    private int tempSpeed;

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_SceneDeviceRealmProxyInterface
    public int realmGet$color() {
        return this.color;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_SceneDeviceRealmProxyInterface
    public String realmGet$customModId() {
        return this.customModId;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_SceneDeviceRealmProxyInterface
    public String realmGet$did() {
        return this.did;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_SceneDeviceRealmProxyInterface
    public int realmGet$lightness() {
        return this.lightness;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_SceneDeviceRealmProxyInterface
    public int realmGet$modBrightness() {
        return this.modBrightness;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_SceneDeviceRealmProxyInterface
    public int realmGet$modNumber() {
        return this.modNumber;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_SceneDeviceRealmProxyInterface
    public int realmGet$modSpeed() {
        return this.modSpeed;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_SceneDeviceRealmProxyInterface
    public int realmGet$sceneNumber() {
        return this.sceneNumber;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_SceneDeviceRealmProxyInterface
    public int realmGet$temp() {
        return this.temp;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_SceneDeviceRealmProxyInterface
    public int realmGet$tempSpeed() {
        return this.tempSpeed;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_SceneDeviceRealmProxyInterface
    public void realmSet$color(int i) {
        this.color = i;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_SceneDeviceRealmProxyInterface
    public void realmSet$customModId(String str) {
        this.customModId = str;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_SceneDeviceRealmProxyInterface
    public void realmSet$did(String str) {
        this.did = str;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_SceneDeviceRealmProxyInterface
    public void realmSet$lightness(int i) {
        this.lightness = i;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_SceneDeviceRealmProxyInterface
    public void realmSet$modBrightness(int i) {
        this.modBrightness = i;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_SceneDeviceRealmProxyInterface
    public void realmSet$modNumber(int i) {
        this.modNumber = i;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_SceneDeviceRealmProxyInterface
    public void realmSet$modSpeed(int i) {
        this.modSpeed = i;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_SceneDeviceRealmProxyInterface
    public void realmSet$sceneNumber(int i) {
        this.sceneNumber = i;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_SceneDeviceRealmProxyInterface
    public void realmSet$temp(int i) {
        this.temp = i;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_SceneDeviceRealmProxyInterface
    public void realmSet$tempSpeed(int i) {
        this.tempSpeed = i;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public SceneDevice() {
        if (this instanceof RealmObjectProxy) {
            ((RealmObjectProxy) this).realm$injectObjectContext();
        }
    }

    public void setDid(String str) {
        realmSet$did(str);
    }

    public String getDid() {
        return realmGet$did();
    }

    public void setSceneNumber(int i) {
        realmSet$sceneNumber(i);
    }

    public int getSceneNumber() {
        return realmGet$sceneNumber();
    }

    public void setColor(int i) {
        realmSet$color(i);
    }

    public int getColor() {
        return realmGet$color();
    }

    public void setLightness(int i) {
        realmSet$lightness(i);
    }

    public int getLightness() {
        return realmGet$lightness();
    }

    public void setTemp(int i) {
        realmSet$temp(i);
    }

    public int getTemp() {
        return realmGet$temp();
    }

    public void setModNumber(int i) {
        realmSet$modNumber(i);
    }

    public int getModNumber() {
        return realmGet$modNumber();
    }

    public void setModSpeed(int i) {
        realmSet$modSpeed(i);
    }

    public int getModSpeed() {
        return realmGet$modSpeed();
    }

    public void setCustomModId(String str) {
        realmSet$customModId(str);
    }

    public String getCustomModId() {
        return realmGet$customModId();
    }

    public void setTempSpeed(int i) {
        realmSet$tempSpeed(i);
    }

    public int getTempSpeed() {
        return realmGet$tempSpeed();
    }

    public void setModBrightness(int i) {
        realmSet$modBrightness(i);
    }

    public int getModBrightness() {
        return realmGet$modBrightness();
    }
}
