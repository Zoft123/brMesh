package okhttp3.logging.internal;

import androidx.constraintlayout.widget.ConstraintLayout;
import java.io.EOFException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import okio.Buffer;

/* JADX INFO: compiled from: IsProbablyUtf8.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0002¨\u0006\u0003"}, d2 = {"isProbablyUtf8", "", "Lokio/Buffer;", "logging-interceptor"}, k = 2, mv = {2, 2, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
public final class IsProbablyUtf8Kt {
    public static final boolean isProbablyUtf8(Buffer buffer) {
        Intrinsics.checkNotNullParameter(buffer, "<this>");
        try {
            Buffer buffer2 = new Buffer();
            buffer.copyTo(buffer2, 0L, RangesKt.coerceAtMost(buffer.size(), 64L));
            for (int i = 0; i < 16; i++) {
                if (buffer2.exhausted()) {
                    return true;
                }
                int utf8CodePoint = buffer2.readUtf8CodePoint();
                if (Character.isISOControl(utf8CodePoint) && !Character.isWhitespace(utf8CodePoint)) {
                    return false;
                }
            }
            return true;
        } catch (EOFException unused) {
            return false;
        }
    }
}
