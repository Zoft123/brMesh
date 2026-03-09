package j$.util;

import j$.time.Instant;
import java.util.Calendar;

/* JADX INFO: loaded from: classes3.dex */
public final /* synthetic */ class DesugarCalendar {
    public static final Instant toInstant(Calendar calendar) {
        return Instant.ofEpochMilli(calendar.getTimeInMillis());
    }
}
