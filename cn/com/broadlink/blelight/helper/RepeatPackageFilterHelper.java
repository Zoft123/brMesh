package cn.com.broadlink.blelight.helper;

import java.util.LinkedList;

/* JADX INFO: loaded from: classes.dex */
public class RepeatPackageFilterHelper {
    private static final int MAX_HISTORY = 5;
    private LinkedList<String> mLastFiveInputs = new LinkedList<>();

    public boolean filter(String str) {
        if (this.mLastFiveInputs.contains(str)) {
            return false;
        }
        this.mLastFiveInputs.addLast(str);
        if (this.mLastFiveInputs.size() <= 5) {
            return true;
        }
        this.mLastFiveInputs.removeFirst();
        return true;
    }
}
