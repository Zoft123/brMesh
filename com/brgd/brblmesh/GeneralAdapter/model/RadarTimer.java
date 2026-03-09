package com.brgd.brblmesh.GeneralAdapter.model;

import io.realm.RealmObject;
import io.realm.com_brgd_brblmesh_GeneralAdapter_model_RadarTimerRealmProxyInterface;
import io.realm.internal.RealmObjectProxy;

/* JADX INFO: loaded from: classes.dex */
public class RadarTimer extends RealmObject implements com_brgd_brblmesh_GeneralAdapter_model_RadarTimerRealmProxyInterface {
    private int addr;
    private int hour1;
    private int hour2;
    private int indexNum;
    private boolean isEnable;
    private int min1;
    private int min2;
    private int out_bri;

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_RadarTimerRealmProxyInterface
    public int realmGet$addr() {
        return this.addr;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_RadarTimerRealmProxyInterface
    public int realmGet$hour1() {
        return this.hour1;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_RadarTimerRealmProxyInterface
    public int realmGet$hour2() {
        return this.hour2;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_RadarTimerRealmProxyInterface
    public int realmGet$indexNum() {
        return this.indexNum;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_RadarTimerRealmProxyInterface
    public boolean realmGet$isEnable() {
        return this.isEnable;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_RadarTimerRealmProxyInterface
    public int realmGet$min1() {
        return this.min1;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_RadarTimerRealmProxyInterface
    public int realmGet$min2() {
        return this.min2;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_RadarTimerRealmProxyInterface
    public int realmGet$out_bri() {
        return this.out_bri;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_RadarTimerRealmProxyInterface
    public void realmSet$addr(int i) {
        this.addr = i;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_RadarTimerRealmProxyInterface
    public void realmSet$hour1(int i) {
        this.hour1 = i;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_RadarTimerRealmProxyInterface
    public void realmSet$hour2(int i) {
        this.hour2 = i;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_RadarTimerRealmProxyInterface
    public void realmSet$indexNum(int i) {
        this.indexNum = i;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_RadarTimerRealmProxyInterface
    public void realmSet$isEnable(boolean z) {
        this.isEnable = z;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_RadarTimerRealmProxyInterface
    public void realmSet$min1(int i) {
        this.min1 = i;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_RadarTimerRealmProxyInterface
    public void realmSet$min2(int i) {
        this.min2 = i;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_RadarTimerRealmProxyInterface
    public void realmSet$out_bri(int i) {
        this.out_bri = i;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public RadarTimer() {
        if (this instanceof RealmObjectProxy) {
            ((RealmObjectProxy) this).realm$injectObjectContext();
        }
    }

    public void setIndexNum(int i) {
        realmSet$indexNum(i);
    }

    public int getIndexNum() {
        return realmGet$indexNum();
    }

    public void setHour1(int i) {
        realmSet$hour1(i);
    }

    public int getHour1() {
        return realmGet$hour1();
    }

    public void setMin1(int i) {
        realmSet$min1(i);
    }

    public int getMin1() {
        return realmGet$min1();
    }

    public void setHour2(int i) {
        realmSet$hour2(i);
    }

    public int getHour2() {
        return realmGet$hour2();
    }

    public void setMin2(int i) {
        realmSet$min2(i);
    }

    public int getMin2() {
        return realmGet$min2();
    }

    public void setEnable(boolean z) {
        realmSet$isEnable(z);
    }

    public boolean isEnable() {
        return realmGet$isEnable();
    }

    public void setOut_bri(int i) {
        realmSet$out_bri(i);
    }

    public int getOut_bri() {
        return realmGet$out_bri();
    }

    public void setAddr(int i) {
        realmSet$addr(i);
    }

    public int getAddr() {
        return realmGet$addr();
    }
}
