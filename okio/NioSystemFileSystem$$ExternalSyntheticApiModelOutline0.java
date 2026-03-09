package okio;

import android.util.CloseGuard;
import java.util.concurrent.CompletableFuture;
import javax.net.ssl.ExtendedSSLSession;
import javax.net.ssl.SNIHostName;
import javax.net.ssl.SNIServerName;

/* JADX INFO: compiled from: D8$$SyntheticClass */
/* JADX INFO: loaded from: classes4.dex */
public final /* synthetic */ class NioSystemFileSystem$$ExternalSyntheticApiModelOutline0 {
    public static /* synthetic */ CloseGuard m() {
        return new CloseGuard();
    }

    public static /* bridge */ /* synthetic */ CloseGuard m(Object obj) {
        return (CloseGuard) obj;
    }

    /* JADX INFO: renamed from: m, reason: collision with other method in class */
    public static /* synthetic */ CompletableFuture m3157m() {
        return new CompletableFuture();
    }

    /* JADX INFO: renamed from: m, reason: collision with other method in class */
    public static /* bridge */ /* synthetic */ ExtendedSSLSession m3158m(Object obj) {
        return (ExtendedSSLSession) obj;
    }

    /* JADX INFO: renamed from: m, reason: collision with other method in class */
    public static /* bridge */ /* synthetic */ SNIHostName m3159m(Object obj) {
        return (SNIHostName) obj;
    }

    /* JADX INFO: renamed from: m, reason: collision with other method in class */
    public static /* bridge */ /* synthetic */ SNIServerName m3160m(Object obj) {
        return (SNIServerName) obj;
    }

    /* JADX INFO: renamed from: m, reason: collision with other method in class */
    public static /* bridge */ /* synthetic */ boolean m3161m(Object obj) {
        return obj instanceof ExtendedSSLSession;
    }

    public static /* bridge */ /* synthetic */ boolean m$1(Object obj) {
        return obj instanceof SNIHostName;
    }
}
