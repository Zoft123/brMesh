package com.brgd.brblmesh.GeneralAdapter.model;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxyInterface;
import io.realm.internal.RealmObjectProxy;

/* JADX INFO: loaded from: classes.dex */
public class BleDevice extends RealmObject implements com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxyInterface {
    private int addr;
    private boolean beenMain;
    private long createTime;
    private String did;
    private int fixedId;
    private int groupId;
    private int groupIndex;

    @Ignore
    public String groupName;
    private int index;
    private boolean isConfigAlexa;

    @Ignore
    public boolean isCurrentFixedGroup;

    @Ignore
    public boolean isDelete;

    @Ignore
    public boolean isGroupStatus;

    @Ignore
    public boolean isSelect;
    private boolean isSupportAlexaEnable;
    private boolean isSupportFixedGroup;
    private boolean isSupportGroupMain;
    private boolean isSupportModeLightness;
    private boolean isSupportSceneControl;
    private boolean isSupportTimerStatus;
    private String key;
    private String name;
    private int otaTag;
    private int type;
    private String version;

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxyInterface
    public int realmGet$addr() {
        return this.addr;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxyInterface
    public boolean realmGet$beenMain() {
        return this.beenMain;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxyInterface
    public long realmGet$createTime() {
        return this.createTime;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxyInterface
    public String realmGet$did() {
        return this.did;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxyInterface
    public int realmGet$fixedId() {
        return this.fixedId;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxyInterface
    public int realmGet$groupId() {
        return this.groupId;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxyInterface
    public int realmGet$groupIndex() {
        return this.groupIndex;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxyInterface
    public int realmGet$index() {
        return this.index;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxyInterface
    public boolean realmGet$isConfigAlexa() {
        return this.isConfigAlexa;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxyInterface
    public boolean realmGet$isSupportAlexaEnable() {
        return this.isSupportAlexaEnable;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxyInterface
    public boolean realmGet$isSupportFixedGroup() {
        return this.isSupportFixedGroup;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxyInterface
    public boolean realmGet$isSupportGroupMain() {
        return this.isSupportGroupMain;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxyInterface
    public boolean realmGet$isSupportModeLightness() {
        return this.isSupportModeLightness;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxyInterface
    public boolean realmGet$isSupportSceneControl() {
        return this.isSupportSceneControl;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxyInterface
    public boolean realmGet$isSupportTimerStatus() {
        return this.isSupportTimerStatus;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxyInterface
    public String realmGet$key() {
        return this.key;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxyInterface
    public String realmGet$name() {
        return this.name;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxyInterface
    public int realmGet$otaTag() {
        return this.otaTag;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxyInterface
    public int realmGet$type() {
        return this.type;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxyInterface
    public String realmGet$version() {
        return this.version;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxyInterface
    public void realmSet$addr(int i) {
        this.addr = i;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxyInterface
    public void realmSet$beenMain(boolean z) {
        this.beenMain = z;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxyInterface
    public void realmSet$createTime(long j) {
        this.createTime = j;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxyInterface
    public void realmSet$did(String str) {
        this.did = str;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxyInterface
    public void realmSet$fixedId(int i) {
        this.fixedId = i;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxyInterface
    public void realmSet$groupId(int i) {
        this.groupId = i;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxyInterface
    public void realmSet$groupIndex(int i) {
        this.groupIndex = i;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxyInterface
    public void realmSet$index(int i) {
        this.index = i;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxyInterface
    public void realmSet$isConfigAlexa(boolean z) {
        this.isConfigAlexa = z;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxyInterface
    public void realmSet$isSupportAlexaEnable(boolean z) {
        this.isSupportAlexaEnable = z;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxyInterface
    public void realmSet$isSupportFixedGroup(boolean z) {
        this.isSupportFixedGroup = z;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxyInterface
    public void realmSet$isSupportGroupMain(boolean z) {
        this.isSupportGroupMain = z;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxyInterface
    public void realmSet$isSupportModeLightness(boolean z) {
        this.isSupportModeLightness = z;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxyInterface
    public void realmSet$isSupportSceneControl(boolean z) {
        this.isSupportSceneControl = z;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxyInterface
    public void realmSet$isSupportTimerStatus(boolean z) {
        this.isSupportTimerStatus = z;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxyInterface
    public void realmSet$key(String str) {
        this.key = str;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxyInterface
    public void realmSet$name(String str) {
        this.name = str;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxyInterface
    public void realmSet$otaTag(int i) {
        this.otaTag = i;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxyInterface
    public void realmSet$type(int i) {
        this.type = i;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxyInterface
    public void realmSet$version(String str) {
        this.version = str;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public BleDevice() {
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

    public void setAddr(int i) {
        realmSet$addr(i);
    }

    public int getAddr() {
        return realmGet$addr();
    }

    public void setCreateTime(long j) {
        realmSet$createTime(j);
    }

    public long getCreateTime() {
        return realmGet$createTime();
    }

    public void setGroupId(int i) {
        realmSet$groupId(i);
    }

    public int getGroupId() {
        return realmGet$groupId();
    }

    public void setKey(String str) {
        realmSet$key(str);
    }

    public String getKey() {
        return realmGet$key();
    }

    public void setName(String str) {
        realmSet$name(str);
    }

    public String getName() {
        return realmGet$name();
    }

    public void setType(int i) {
        realmSet$type(i);
    }

    public int getType() {
        return realmGet$type();
    }

    public void setVersion(String str) {
        realmSet$version(str);
    }

    public String getVersion() {
        return realmGet$version();
    }

    public void setIndex(int i) {
        realmSet$index(i);
    }

    public int getIndex() {
        return realmGet$index();
    }

    public void setGroupIndex(int i) {
        realmSet$groupIndex(i);
    }

    public int getGroupIndex() {
        return realmGet$groupIndex();
    }

    public void setSupportAlexaEnable(boolean z) {
        realmSet$isSupportAlexaEnable(z);
    }

    public boolean isSupportAlexaEnable() {
        return realmGet$isSupportAlexaEnable();
    }

    public void setConfigAlexa(boolean z) {
        realmSet$isConfigAlexa(z);
    }

    public boolean isConfigAlexa() {
        return realmGet$isConfigAlexa();
    }

    public void setSupportFixedGroup(boolean z) {
        realmSet$isSupportFixedGroup(z);
    }

    public boolean isSupportFixedGroup() {
        return realmGet$isSupportFixedGroup();
    }

    public void setSupportGroupMain(boolean z) {
        realmSet$isSupportGroupMain(z);
    }

    public boolean isSupportGroupMain() {
        return realmGet$isSupportGroupMain();
    }

    public void setBeenMain(boolean z) {
        realmSet$beenMain(z);
    }

    public boolean isBeenMain() {
        return realmGet$beenMain();
    }

    public void setFixedId(int i) {
        realmSet$fixedId(i);
    }

    public int getFixedId() {
        return realmGet$fixedId();
    }

    public void setSupportTimerStatus(boolean z) {
        realmSet$isSupportTimerStatus(z);
    }

    public boolean isSupportTimerStatus() {
        return realmGet$isSupportTimerStatus();
    }

    public void setSupportSceneControl(boolean z) {
        realmSet$isSupportSceneControl(z);
    }

    public boolean isSupportSceneControl() {
        return realmGet$isSupportSceneControl();
    }

    public void setOtaTag(int i) {
        realmSet$otaTag(i);
    }

    public int getOtaTag() {
        return realmGet$otaTag();
    }

    public void setSupportModeLightness(boolean z) {
        realmSet$isSupportModeLightness(z);
    }

    public boolean isSupportModeLightness() {
        return realmGet$isSupportModeLightness();
    }
}
