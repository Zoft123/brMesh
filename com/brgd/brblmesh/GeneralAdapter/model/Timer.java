package com.brgd.brblmesh.GeneralAdapter.model;

import io.realm.RealmObject;
import io.realm.com_brgd_brblmesh_GeneralAdapter_model_TimerRealmProxyInterface;
import io.realm.internal.RealmObjectProxy;

/* JADX INFO: loaded from: classes.dex */
public class Timer extends RealmObject implements com_brgd_brblmesh_GeneralAdapter_model_TimerRealmProxyInterface {
    private int action;
    private int addr;
    private int blue;
    private int brightness;
    private int cold;
    private int color;
    private int green;
    private int hour;
    private int indexNum;
    private boolean isEnable;
    private boolean isRepeat;
    private int minute;
    private int red;
    private int warm;

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_TimerRealmProxyInterface
    public int realmGet$action() {
        return this.action;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_TimerRealmProxyInterface
    public int realmGet$addr() {
        return this.addr;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_TimerRealmProxyInterface
    public int realmGet$blue() {
        return this.blue;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_TimerRealmProxyInterface
    public int realmGet$brightness() {
        return this.brightness;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_TimerRealmProxyInterface
    public int realmGet$cold() {
        return this.cold;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_TimerRealmProxyInterface
    public int realmGet$color() {
        return this.color;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_TimerRealmProxyInterface
    public int realmGet$green() {
        return this.green;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_TimerRealmProxyInterface
    public int realmGet$hour() {
        return this.hour;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_TimerRealmProxyInterface
    public int realmGet$indexNum() {
        return this.indexNum;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_TimerRealmProxyInterface
    public boolean realmGet$isEnable() {
        return this.isEnable;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_TimerRealmProxyInterface
    public boolean realmGet$isRepeat() {
        return this.isRepeat;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_TimerRealmProxyInterface
    public int realmGet$minute() {
        return this.minute;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_TimerRealmProxyInterface
    public int realmGet$red() {
        return this.red;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_TimerRealmProxyInterface
    public int realmGet$warm() {
        return this.warm;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_TimerRealmProxyInterface
    public void realmSet$action(int i) {
        this.action = i;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_TimerRealmProxyInterface
    public void realmSet$addr(int i) {
        this.addr = i;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_TimerRealmProxyInterface
    public void realmSet$blue(int i) {
        this.blue = i;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_TimerRealmProxyInterface
    public void realmSet$brightness(int i) {
        this.brightness = i;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_TimerRealmProxyInterface
    public void realmSet$cold(int i) {
        this.cold = i;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_TimerRealmProxyInterface
    public void realmSet$color(int i) {
        this.color = i;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_TimerRealmProxyInterface
    public void realmSet$green(int i) {
        this.green = i;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_TimerRealmProxyInterface
    public void realmSet$hour(int i) {
        this.hour = i;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_TimerRealmProxyInterface
    public void realmSet$indexNum(int i) {
        this.indexNum = i;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_TimerRealmProxyInterface
    public void realmSet$isEnable(boolean z) {
        this.isEnable = z;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_TimerRealmProxyInterface
    public void realmSet$isRepeat(boolean z) {
        this.isRepeat = z;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_TimerRealmProxyInterface
    public void realmSet$minute(int i) {
        this.minute = i;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_TimerRealmProxyInterface
    public void realmSet$red(int i) {
        this.red = i;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_TimerRealmProxyInterface
    public void realmSet$warm(int i) {
        this.warm = i;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public Timer() {
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

    public void setHour(int i) {
        realmSet$hour(i);
    }

    public int getHour() {
        return realmGet$hour();
    }

    public void setMinute(int i) {
        realmSet$minute(i);
    }

    public int getMinute() {
        return realmGet$minute();
    }

    public void setAction(int i) {
        realmSet$action(i);
    }

    public int getAction() {
        return realmGet$action();
    }

    public void setRepeat(boolean z) {
        realmSet$isRepeat(z);
    }

    public boolean isRepeat() {
        return realmGet$isRepeat();
    }

    public void setEnable(boolean z) {
        realmSet$isEnable(z);
    }

    public boolean isEnable() {
        return realmGet$isEnable();
    }

    public void setAddr(int i) {
        realmSet$addr(i);
    }

    public int getAddr() {
        return realmGet$addr();
    }

    public void setBrightness(int i) {
        realmSet$brightness(i);
    }

    public int getBrightness() {
        return realmGet$brightness();
    }

    public void setRed(int i) {
        realmSet$red(i);
    }

    public int getRed() {
        return realmGet$red();
    }

    public void setGreen(int i) {
        realmSet$green(i);
    }

    public int getGreen() {
        return realmGet$green();
    }

    public void setBlue(int i) {
        realmSet$blue(i);
    }

    public int getBlue() {
        return realmGet$blue();
    }

    public void setCold(int i) {
        realmSet$cold(i);
    }

    public int getCold() {
        return realmGet$cold();
    }

    public void setWarm(int i) {
        realmSet$warm(i);
    }

    public int getWarm() {
        return realmGet$warm();
    }

    public void setColor(int i) {
        realmSet$color(i);
    }

    public int getColor() {
        return realmGet$color();
    }
}
