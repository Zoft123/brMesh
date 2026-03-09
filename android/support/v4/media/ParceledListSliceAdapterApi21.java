package android.support.v4.media;

import android.media.browse.MediaBrowser;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
class ParceledListSliceAdapterApi21 {
    private static Constructor sConstructor;

    static {
        try {
            try {
                sConstructor = Class.forName("android.content.pm.ParceledListSlice").getConstructor(List.class);
            } catch (NoSuchMethodException e) {
                e = e;
                e.printStackTrace();
            }
        } catch (ClassNotFoundException | NoSuchMethodException e2) {
            e = e2;
        }
    }

    static Object newInstance(List<MediaBrowser.MediaItem> list) {
        try {
            try {
                return sConstructor.newInstance(list);
            } catch (IllegalAccessException e) {
                e = e;
                e.printStackTrace();
                return null;
            } catch (InvocationTargetException e2) {
                e = e2;
                e.printStackTrace();
                return null;
            }
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e3) {
            e = e3;
        }
    }

    private ParceledListSliceAdapterApi21() {
    }
}
