package com.brgd.brblmesh.GeneralClass;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import com.brgd.brblmesh.R;

/* JADX INFO: loaded from: classes.dex */
public class MyService extends Service {
    private static final String CHANNEL_ID = "brmeshMusicId";
    private static final String CHANNEL_NAME = "BRmesh is in use.";

    @Override // android.app.Service
    public void onCreate() {
        Notification.Builder builder;
        super.onCreate();
        NotificationManager notificationManager = (NotificationManager) getSystemService("notification");
        if (Build.VERSION.SDK_INT >= 26) {
            notificationManager.createNotificationChannel(MyService$$ExternalSyntheticApiModelOutline0.m(CHANNEL_ID, CHANNEL_NAME, 2));
            builder = MyService$$ExternalSyntheticApiModelOutline0.m(this, CHANNEL_ID);
        } else {
            builder = new Notification.Builder(this);
        }
        startForeground(1, builder.setContentTitle("BRmesh").setContentText("BRmesh is using a microphone.").setWhen(System.currentTimeMillis()).setSmallIcon(R.mipmap.ic_launcher_foreground).build());
    }

    @Override // android.app.Service
    public void onDestroy() {
        super.onDestroy();
        stopForeground(true);
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
