package cn.com.broadlink.blelight.util;

import android.text.TextUtils;
import j$.util.Objects;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class EListUtils {
    public static final String DEFAULT_JOIN_SEPARATOR = ",";

    private EListUtils() {
        throw new AssertionError();
    }

    public static <V> int getSize(List<V> list) {
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    public static <V> boolean isEmpty(List<V> list) {
        return list == null || list.size() == 0;
    }

    public static <V> boolean isEquals(ArrayList<V> arrayList, ArrayList<V> arrayList2) {
        if (arrayList == null) {
            return arrayList2 == null;
        }
        if (arrayList2 == null || arrayList.size() != arrayList2.size()) {
            return false;
        }
        for (int i = 0; i < arrayList.size(); i++) {
            if (!Objects.equals(arrayList.get(i), arrayList2.get(i))) {
                return false;
            }
        }
        return true;
    }

    public static String join(List<String> list) {
        return join(list, DEFAULT_JOIN_SEPARATOR);
    }

    public static String join(List<String> list, char c) {
        return join(list, new String(new char[]{c}));
    }

    public static String join(List<String> list, String str) {
        return list == null ? "" : TextUtils.join(str, list);
    }

    public static <V> boolean addDistinctEntry(List<V> list, V v) {
        if (list == null || list.contains(v)) {
            return false;
        }
        return list.add(v);
    }

    public static <V> int addDistinctList(List<V> list, List<V> list2) {
        if (list == null || isEmpty(list2)) {
            return 0;
        }
        int size = list.size();
        for (V v : list2) {
            if (!list.contains(v)) {
                list.add(v);
            }
        }
        return list.size() - size;
    }

    public static <V> int distinctList(List<V> list) {
        int i = 0;
        if (isEmpty(list)) {
            return 0;
        }
        int size = list.size();
        int size2 = list.size();
        while (i < size2) {
            int i2 = i + 1;
            int i3 = i2;
            while (i3 < size2) {
                if (list.get(i).equals(list.get(i3))) {
                    list.remove(i3);
                    size2 = list.size();
                    i3--;
                }
                i3++;
            }
            i = i2;
        }
        return size - list.size();
    }

    public static <V> List<V> invertList(List<V> list) {
        if (isEmpty(list)) {
            return list;
        }
        ArrayList arrayList = new ArrayList(list.size());
        for (int size = list.size() - 1; size >= 0; size--) {
            arrayList.add(list.get(size));
        }
        return arrayList;
    }
}
