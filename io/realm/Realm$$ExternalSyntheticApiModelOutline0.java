package io.realm;

import android.app.Notification;
import android.media.ExifInterface;
import android.media.session.MediaSessionManager;
import android.os.VibratorManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.inputmethod.InputContentInfo;
import java.io.InputStream;
import java.nio.file.FileVisitor;
import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.atomic.DoubleAdder;
import java.util.concurrent.atomic.LongAdder;

/* JADX INFO: compiled from: D8$$SyntheticClass */
/* JADX INFO: loaded from: classes4.dex */
public final /* synthetic */ class Realm$$ExternalSyntheticApiModelOutline0 {
    public static /* synthetic */ Notification.DecoratedMediaCustomViewStyle m() {
        return new Notification.DecoratedMediaCustomViewStyle();
    }

    public static /* synthetic */ ExifInterface m(InputStream inputStream) {
        return new ExifInterface(inputStream);
    }

    public static /* synthetic */ MediaSessionManager.RemoteUserInfo m(String str, int i, int i2) {
        return new MediaSessionManager.RemoteUserInfo(str, i, i2);
    }

    public static /* bridge */ /* synthetic */ VibratorManager m(Object obj) {
        return (VibratorManager) obj;
    }

    public static /* synthetic */ AccessibilityNodeInfo.TouchDelegateInfo m(Map map) {
        return new AccessibilityNodeInfo.TouchDelegateInfo(map);
    }

    /* JADX INFO: renamed from: m, reason: collision with other method in class */
    public static /* bridge */ /* synthetic */ InputContentInfo m1274m(Object obj) {
        return (InputContentInfo) obj;
    }

    /* JADX INFO: renamed from: m, reason: collision with other method in class */
    public static /* bridge */ /* synthetic */ FileVisitor m1279m(Object obj) {
        return (FileVisitor) obj;
    }

    /* JADX INFO: renamed from: m, reason: collision with other method in class */
    public static /* bridge */ /* synthetic */ Path m1281m(Object obj) {
        return (Path) obj;
    }

    /* JADX INFO: renamed from: m, reason: collision with other method in class */
    public static /* bridge */ /* synthetic */ DoubleAdder m1283m(Object obj) {
        return (DoubleAdder) obj;
    }

    /* JADX INFO: renamed from: m, reason: collision with other method in class */
    public static /* bridge */ /* synthetic */ LongAdder m1284m(Object obj) {
        return (LongAdder) obj;
    }

    /* JADX INFO: renamed from: m, reason: collision with other method in class */
    public static /* synthetic */ void m1285m() {
    }

    /* JADX INFO: renamed from: m, reason: collision with other method in class */
    public static /* bridge */ /* synthetic */ boolean m1287m(Object obj) {
        return obj instanceof LongAdder;
    }

    public static /* bridge */ /* synthetic */ boolean m$1(Object obj) {
        return obj instanceof DoubleAdder;
    }
}
