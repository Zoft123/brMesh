package com.brgd.brblmesh.GeneralAdapter.model;

import cn.com.broadlink.blelight.bean.BLEDeviceInfo;

/* JADX INFO: loaded from: classes.dex */
public class MyDevWrapperBean implements Cloneable {
    public static final int STATE_INIT = 0;
    public static final int STATE_SENT = 1;
    public static final int STATE_SUCC = 2;
    public BLEDeviceInfo deviceInfo;
    public int state = 0;
    public boolean isUpdateState = false;

    public MyDevWrapperBean(BLEDeviceInfo bLEDeviceInfo) {
        this.deviceInfo = bLEDeviceInfo;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX INFO: renamed from: clone, reason: merged with bridge method [inline-methods] */
    public MyDevWrapperBean m531clone() throws CloneNotSupportedException {
        return (MyDevWrapperBean) super.clone();
    }
}
