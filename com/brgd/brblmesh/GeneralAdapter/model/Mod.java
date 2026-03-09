package com.brgd.brblmesh.GeneralAdapter.model;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.com_brgd_brblmesh_GeneralAdapter_model_ModRealmProxyInterface;
import io.realm.internal.RealmObjectProxy;

/* JADX INFO: loaded from: classes.dex */
public class Mod extends RealmObject implements com_brgd_brblmesh_GeneralAdapter_model_ModRealmProxyInterface {
    private int addr;
    private int brightness;
    private String customModId;
    private String customModName;
    private int endBrightness;
    private boolean isFlash;

    @Ignore
    public boolean isSelect;
    private boolean isSleep;
    private int minute;
    private int modNumber;
    private int speed;
    private int startBrightness;
    private int stateValue;
    private int warmValue;

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_ModRealmProxyInterface
    public int realmGet$addr() {
        return this.addr;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_ModRealmProxyInterface
    public int realmGet$brightness() {
        return this.brightness;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_ModRealmProxyInterface
    public String realmGet$customModId() {
        return this.customModId;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_ModRealmProxyInterface
    public String realmGet$customModName() {
        return this.customModName;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_ModRealmProxyInterface
    public int realmGet$endBrightness() {
        return this.endBrightness;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_ModRealmProxyInterface
    public boolean realmGet$isFlash() {
        return this.isFlash;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_ModRealmProxyInterface
    public boolean realmGet$isSleep() {
        return this.isSleep;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_ModRealmProxyInterface
    public int realmGet$minute() {
        return this.minute;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_ModRealmProxyInterface
    public int realmGet$modNumber() {
        return this.modNumber;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_ModRealmProxyInterface
    public int realmGet$speed() {
        return this.speed;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_ModRealmProxyInterface
    public int realmGet$startBrightness() {
        return this.startBrightness;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_ModRealmProxyInterface
    public int realmGet$stateValue() {
        return this.stateValue;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_ModRealmProxyInterface
    public int realmGet$warmValue() {
        return this.warmValue;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_ModRealmProxyInterface
    public void realmSet$addr(int i) {
        this.addr = i;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_ModRealmProxyInterface
    public void realmSet$brightness(int i) {
        this.brightness = i;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_ModRealmProxyInterface
    public void realmSet$customModId(String str) {
        this.customModId = str;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_ModRealmProxyInterface
    public void realmSet$customModName(String str) {
        this.customModName = str;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_ModRealmProxyInterface
    public void realmSet$endBrightness(int i) {
        this.endBrightness = i;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_ModRealmProxyInterface
    public void realmSet$isFlash(boolean z) {
        this.isFlash = z;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_ModRealmProxyInterface
    public void realmSet$isSleep(boolean z) {
        this.isSleep = z;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_ModRealmProxyInterface
    public void realmSet$minute(int i) {
        this.minute = i;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_ModRealmProxyInterface
    public void realmSet$modNumber(int i) {
        this.modNumber = i;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_ModRealmProxyInterface
    public void realmSet$speed(int i) {
        this.speed = i;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_ModRealmProxyInterface
    public void realmSet$startBrightness(int i) {
        this.startBrightness = i;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_ModRealmProxyInterface
    public void realmSet$stateValue(int i) {
        this.stateValue = i;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_ModRealmProxyInterface
    public void realmSet$warmValue(int i) {
        this.warmValue = i;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public Mod() {
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

    public void setSpeed(int i) {
        realmSet$speed(i);
    }

    public int getSpeed() {
        return realmGet$speed();
    }

    public void setAddr(int i) {
        realmSet$addr(i);
    }

    public int getAddr() {
        return realmGet$addr();
    }

    public void setStartBrightness(int i) {
        realmSet$startBrightness(i);
    }

    public int getStartBrightness() {
        return realmGet$startBrightness();
    }

    public void setEndBrightness(int i) {
        realmSet$endBrightness(i);
    }

    public int getEndBrightness() {
        return realmGet$endBrightness();
    }

    public void setMinute(int i) {
        realmSet$minute(i);
    }

    public int getMinute() {
        return realmGet$minute();
    }

    public void setStateValue(int i) {
        realmSet$stateValue(i);
    }

    public int getStateValue() {
        return realmGet$stateValue();
    }

    public void setWarmValue(int i) {
        realmSet$warmValue(i);
    }

    public int getWarmValue() {
        return realmGet$warmValue();
    }

    public void setSleep(boolean z) {
        realmSet$isSleep(z);
    }

    public boolean isSleep() {
        return realmGet$isSleep();
    }

    public void setFlash(boolean z) {
        realmSet$isFlash(z);
    }

    public boolean isFlash() {
        return realmGet$isFlash();
    }

    public void setCustomModId(String str) {
        realmSet$customModId(str);
    }

    public String getCustomModId() {
        return realmGet$customModId();
    }

    public void setCustomModName(String str) {
        realmSet$customModName(str);
    }

    public String getCustomModName() {
        return realmGet$customModName();
    }

    public void setBrightness(int i) {
        realmSet$brightness(i);
    }

    public int getBrightness() {
        return realmGet$brightness();
    }
}
