package com.brgd.brblmesh.GeneralAdapter.model;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.com_brgd_brblmesh_GeneralAdapter_model_RadarDeviceRealmProxyInterface;
import io.realm.internal.RealmObjectProxy;

/* JADX INFO: loaded from: classes.dex */
public class RadarDevice extends RealmObject implements com_brgd_brblmesh_GeneralAdapter_model_RadarDeviceRealmProxyInterface {
    private int addr;
    private String did;
    private int groupId;
    private int index;
    private boolean isConfigAlexa;

    @Ignore
    public boolean isDelete;

    @Ignore
    public boolean isGroupStatus;

    @Ignore
    public boolean isSelect;
    private boolean isSupportAlexaEnable;
    private boolean isSupportFixedGroup;
    private String key;
    private String name;
    private int type;
    private String version;

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_RadarDeviceRealmProxyInterface
    public int realmGet$addr() {
        return this.addr;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_RadarDeviceRealmProxyInterface
    public String realmGet$did() {
        return this.did;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_RadarDeviceRealmProxyInterface
    public int realmGet$groupId() {
        return this.groupId;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_RadarDeviceRealmProxyInterface
    public int realmGet$index() {
        return this.index;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_RadarDeviceRealmProxyInterface
    public boolean realmGet$isConfigAlexa() {
        return this.isConfigAlexa;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_RadarDeviceRealmProxyInterface
    public boolean realmGet$isSupportAlexaEnable() {
        return this.isSupportAlexaEnable;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_RadarDeviceRealmProxyInterface
    public boolean realmGet$isSupportFixedGroup() {
        return this.isSupportFixedGroup;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_RadarDeviceRealmProxyInterface
    public String realmGet$key() {
        return this.key;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_RadarDeviceRealmProxyInterface
    public String realmGet$name() {
        return this.name;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_RadarDeviceRealmProxyInterface
    public int realmGet$type() {
        return this.type;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_RadarDeviceRealmProxyInterface
    public String realmGet$version() {
        return this.version;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_RadarDeviceRealmProxyInterface
    public void realmSet$addr(int i) {
        this.addr = i;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_RadarDeviceRealmProxyInterface
    public void realmSet$did(String str) {
        this.did = str;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_RadarDeviceRealmProxyInterface
    public void realmSet$groupId(int i) {
        this.groupId = i;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_RadarDeviceRealmProxyInterface
    public void realmSet$index(int i) {
        this.index = i;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_RadarDeviceRealmProxyInterface
    public void realmSet$isConfigAlexa(boolean z) {
        this.isConfigAlexa = z;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_RadarDeviceRealmProxyInterface
    public void realmSet$isSupportAlexaEnable(boolean z) {
        this.isSupportAlexaEnable = z;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_RadarDeviceRealmProxyInterface
    public void realmSet$isSupportFixedGroup(boolean z) {
        this.isSupportFixedGroup = z;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_RadarDeviceRealmProxyInterface
    public void realmSet$key(String str) {
        this.key = str;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_RadarDeviceRealmProxyInterface
    public void realmSet$name(String str) {
        this.name = str;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_RadarDeviceRealmProxyInterface
    public void realmSet$type(int i) {
        this.type = i;
    }

    @Override // io.realm.com_brgd_brblmesh_GeneralAdapter_model_RadarDeviceRealmProxyInterface
    public void realmSet$version(String str) {
        this.version = str;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public RadarDevice() {
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
}
