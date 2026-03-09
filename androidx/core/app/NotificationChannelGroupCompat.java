package androidx.core.app;

import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.os.Build;
import androidx.activity.ComponentDialog$$ExternalSyntheticApiModelOutline0;
import androidx.core.util.Preconditions;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class NotificationChannelGroupCompat {
    private boolean mBlocked;
    private List<NotificationChannelCompat> mChannels;
    String mDescription;
    final String mId;
    CharSequence mName;

    public static class Builder {
        final NotificationChannelGroupCompat mGroup;

        public Builder(String str) {
            this.mGroup = new NotificationChannelGroupCompat(str);
        }

        public Builder setName(CharSequence charSequence) {
            this.mGroup.mName = charSequence;
            return this;
        }

        public Builder setDescription(String str) {
            this.mGroup.mDescription = str;
            return this;
        }

        public NotificationChannelGroupCompat build() {
            return this.mGroup;
        }
    }

    NotificationChannelGroupCompat(String str) {
        this.mChannels = Collections.EMPTY_LIST;
        this.mId = (String) Preconditions.checkNotNull(str);
    }

    NotificationChannelGroupCompat(NotificationChannelGroup notificationChannelGroup) {
        this(notificationChannelGroup, Collections.EMPTY_LIST);
    }

    NotificationChannelGroupCompat(NotificationChannelGroup notificationChannelGroup, List<NotificationChannel> list) {
        this(notificationChannelGroup.getId());
        this.mName = notificationChannelGroup.getName();
        if (Build.VERSION.SDK_INT >= 28) {
            this.mDescription = notificationChannelGroup.getDescription();
        }
        if (Build.VERSION.SDK_INT >= 28) {
            this.mBlocked = notificationChannelGroup.isBlocked();
            this.mChannels = getChannelsCompat(notificationChannelGroup.getChannels());
        } else {
            this.mChannels = getChannelsCompat(list);
        }
    }

    private List<NotificationChannelCompat> getChannelsCompat(List<NotificationChannel> list) {
        ArrayList arrayList = new ArrayList();
        Iterator<NotificationChannel> it = list.iterator();
        while (it.hasNext()) {
            NotificationChannel notificationChannelM = ComponentDialog$$ExternalSyntheticApiModelOutline0.m((Object) it.next());
            if (this.mId.equals(notificationChannelM.getGroup())) {
                arrayList.add(new NotificationChannelCompat(notificationChannelM));
            }
        }
        return arrayList;
    }

    NotificationChannelGroup getNotificationChannelGroup() {
        if (Build.VERSION.SDK_INT < 26) {
            return null;
        }
        ComponentDialog$$ExternalSyntheticApiModelOutline0.m35m$2();
        NotificationChannelGroup notificationChannelGroupM = ComponentDialog$$ExternalSyntheticApiModelOutline0.m(this.mId, this.mName);
        if (Build.VERSION.SDK_INT >= 28) {
            notificationChannelGroupM.setDescription(this.mDescription);
        }
        return notificationChannelGroupM;
    }

    public Builder toBuilder() {
        return new Builder(this.mId).setName(this.mName).setDescription(this.mDescription);
    }

    public String getId() {
        return this.mId;
    }

    public CharSequence getName() {
        return this.mName;
    }

    public String getDescription() {
        return this.mDescription;
    }

    public boolean isBlocked() {
        return this.mBlocked;
    }

    public List<NotificationChannelCompat> getChannels() {
        return this.mChannels;
    }
}
