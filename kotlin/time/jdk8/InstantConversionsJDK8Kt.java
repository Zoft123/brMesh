package kotlin.time.jdk8;

import androidx.constraintlayout.widget.ConstraintLayout;
import j$.time.Instant;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: InstantConversions.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\b\n\u0002\u0018\u0002\n\u0002\b\u0006\u001a\u0013\u0010\u0002\u001a\u00020\u0001*\u00020\u0000H\u0007¢\u0006\u0004\b\u0002\u0010\u0003\u001a\u0013\u0010\u0004\u001a\u00020\u0000*\u00020\u0001H\u0007¢\u0006\u0004\b\u0004\u0010\u0005¨\u0006\u0006"}, d2 = {"Lkotlin/time/Instant;", "j$/time/Instant", "toJavaInstant", "(Lkotlin/time/Instant;)Lj$/time/Instant;", "toKotlinInstant", "(Lj$/time/Instant;)Lkotlin/time/Instant;", "kotlin-stdlib-jdk8"}, k = 2, mv = {2, 2, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
public final class InstantConversionsJDK8Kt {
    public static final Instant toJavaInstant(kotlin.time.Instant instant) {
        Intrinsics.checkNotNullParameter(instant, "<this>");
        Instant instantOfEpochSecond = Instant.ofEpochSecond(instant.getEpochSeconds(), instant.getNanosecondsOfSecond());
        Intrinsics.checkNotNullExpressionValue(instantOfEpochSecond, "ofEpochSecond(...)");
        return instantOfEpochSecond;
    }

    public static final kotlin.time.Instant toKotlinInstant(Instant instant) {
        Intrinsics.checkNotNullParameter(instant, "<this>");
        return kotlin.time.Instant.INSTANCE.fromEpochSeconds(instant.getEpochSecond(), instant.getNano());
    }
}
