package cn.com.broadlink.blelight.bean;

/* JADX INFO: loaded from: classes.dex */
public class BLEGWTimerCmdInfo {
    public String cscene_idx;
    public String raw;
    public int type = 0;

    public static class TYPE {
        public static final int CYCLE_SCENE = 3;
        public static final int GROUP_CTRL = 1;
        public static final int SCENE = 2;
        public static final int SINGLE_CTRL = 0;
    }
}
