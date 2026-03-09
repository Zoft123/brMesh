package cn.com.broadlink.blelight.util.okhttp;

import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class EOkHttpConfig {
    private int readTimeout = 5;
    private int connectTimeout = 5;
    private int writeTimeout = 5;
    private String logTag = "EOkHttpUtils";
    private boolean debugOn = true;
    private Map<String, String> commonHeader = new HashMap();

    public int getReadTimeout() {
        return this.readTimeout;
    }

    public void setReadTimeout(int i) {
        this.readTimeout = i;
    }

    public int getConnectTimeout() {
        return this.connectTimeout;
    }

    public void setConnectTimeout(int i) {
        this.connectTimeout = i;
    }

    public int getWriteTimeout() {
        return this.writeTimeout;
    }

    public void setWriteTimeout(int i) {
        this.writeTimeout = i;
    }

    public String getLogTag() {
        return this.logTag;
    }

    public void setLogTag(String str) {
        this.logTag = str;
    }

    public Map<String, String> getCommonHeader() {
        return this.commonHeader;
    }

    public void setCommonHeader(Map<String, String> map) {
        this.commonHeader = map;
    }

    public boolean isDebugOn() {
        return this.debugOn;
    }

    public void setDebugOn(boolean z) {
        this.debugOn = z;
    }
}
