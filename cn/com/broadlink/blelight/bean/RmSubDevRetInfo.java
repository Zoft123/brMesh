package cn.com.broadlink.blelight.bean;

/* JADX INFO: loaded from: classes.dex */
public class RmSubDevRetInfo {
    public int address;
    public int code;
    public int content;
    public int frameIndex;
    public int frameSum;
    public int index;
    public int seq;
    public byte[] uploadData;
    public int uploadMsgType;

    public boolean isSuccess() {
        return this.code == 0;
    }
}
