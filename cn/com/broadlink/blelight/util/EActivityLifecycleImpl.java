package cn.com.broadlink.blelight.util;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import cn.com.broadlink.blelight.util.EPermissionUtils;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class EActivityLifecycleImpl implements Application.ActivityLifecycleCallbacks {
    final LinkedList<Activity> mActivityList = new LinkedList<>();
    final HashMap<Object, OnAppStatusChangedListener> mStatusListenerMap = new HashMap<>();
    private int mForegroundCount = 0;
    private int mConfigCount = 0;

    public interface OnAppStatusChangedListener {
        void onBackground();

        void onForeground();
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityPaused(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    void addListener(Object obj, OnAppStatusChangedListener onAppStatusChangedListener) {
        this.mStatusListenerMap.put(obj, onAppStatusChangedListener);
    }

    void removeListener(Object obj) {
        this.mStatusListenerMap.remove(obj);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityCreated(Activity activity, Bundle bundle) {
        setTopActivity(activity);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStarted(Activity activity) {
        setTopActivity(activity);
        if (this.mForegroundCount <= 0) {
            postStatus(true);
        }
        int i = this.mConfigCount;
        if (i < 0) {
            this.mConfigCount = i + 1;
        } else {
            this.mForegroundCount++;
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityResumed(Activity activity) {
        setTopActivity(activity);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStopped(Activity activity) {
        if (activity.isChangingConfigurations()) {
            this.mConfigCount--;
            return;
        }
        int i = this.mForegroundCount - 1;
        this.mForegroundCount = i;
        if (i <= 0) {
            postStatus(false);
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityDestroyed(Activity activity) {
        this.mActivityList.remove(activity);
    }

    private void postStatus(boolean z) {
        OnAppStatusChangedListener next;
        if (this.mStatusListenerMap.isEmpty()) {
            return;
        }
        Iterator<OnAppStatusChangedListener> it = this.mStatusListenerMap.values().iterator();
        while (it.hasNext() && (next = it.next()) != null) {
            if (z) {
                next.onForeground();
            } else {
                next.onBackground();
            }
        }
    }

    private void setTopActivity(Activity activity) {
        if (activity.getClass() == EPermissionUtils.PermissionActivity.class) {
            return;
        }
        if (this.mActivityList.contains(activity)) {
            if (this.mActivityList.getLast().equals(activity)) {
                return;
            }
            this.mActivityList.remove(activity);
            this.mActivityList.addLast(activity);
            return;
        }
        this.mActivityList.addLast(activity);
    }

    Activity getTopActivity() {
        Activity last;
        if (!this.mActivityList.isEmpty() && (last = this.mActivityList.getLast()) != null) {
            return last;
        }
        try {
            Class<?> cls = Class.forName("android.app.ActivityThread");
            Object objInvoke = cls.getMethod("currentActivityThread", null).invoke(null, null);
            Field declaredField = cls.getDeclaredField("mActivityList");
            declaredField.setAccessible(true);
            Map map = (Map) declaredField.get(objInvoke);
            if (map == null) {
                return null;
            }
            for (Object obj : map.values()) {
                Class<?> cls2 = obj.getClass();
                Field declaredField2 = cls2.getDeclaredField("paused");
                declaredField2.setAccessible(true);
                if (!declaredField2.getBoolean(obj)) {
                    Field declaredField3 = cls2.getDeclaredField("activity");
                    declaredField3.setAccessible(true);
                    Activity activity = (Activity) declaredField3.get(obj);
                    setTopActivity(activity);
                    return activity;
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e2) {
            e2.printStackTrace();
        } catch (NoSuchFieldException e3) {
            e3.printStackTrace();
        } catch (NoSuchMethodException e4) {
            e4.printStackTrace();
        } catch (InvocationTargetException e5) {
            e5.printStackTrace();
        }
        return null;
    }
}
