package cn.com.broadlink.blelight.util.okhttp;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class EObjFieldParseUtils {
    public static List<Field> getFields(Class<?> cls, Class<?> cls2) {
        ArrayList arrayList = new ArrayList();
        if (!cls.equals(cls2)) {
            for (Field field : cls.getDeclaredFields()) {
                arrayList.add(field);
            }
            arrayList.addAll(getFields((Class) cls.getGenericSuperclass(), cls2));
        }
        return arrayList;
    }
}
