package com.king.logx.util;

import android.content.Context;
import android.os.Build;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.exifinterface.media.ExifInterface;
import com.brgd.brblmesh.GeneralClass.GlobalVariable;
import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import kotlin.Metadata;
import kotlin.io.FilesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import kotlin.text.Typography;

/* JADX INFO: compiled from: Utils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\b\u0000\u0018\u0000 \u00032\u00020\u0001:\u0001\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0004"}, d2 = {"Lcom/king/logx/util/Utils;", "", "()V", "Companion", "logx_release"}, k = 1, mv = {1, 8, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
public final class Utils {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final int MAX_TAG_LENGTH = 23;

    private Utils() {
        throw new AssertionError("No instances");
    }

    /* JADX INFO: compiled from: Utils.kt */
    @Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010\f\n\u0002\u0010\t\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bJ\u0016\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u0006J\u000e\u0010\u000e\u001a\u00020\u00062\u0006\u0010\u000f\u001a\u00020\u0004J\u000e\u0010\u0010\u001a\u00020\u00062\u0006\u0010\u0011\u001a\u00020\u0012J\n\u0010\u0013\u001a\u00020\u0004*\u00020\u0014J\n\u0010\u0013\u001a\u00020\u0015*\u00020\u0006R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0016"}, d2 = {"Lcom/king/logx/util/Utils$Companion;", "", "()V", "MAX_TAG_LENGTH", "", "createStackElementTag", "", "element", "Ljava/lang/StackTraceElement;", "getCacheFileDir", "Ljava/io/File;", "context", "Landroid/content/Context;", "childDir", "getLogLevel", "priority", "getStackTraceString", GlobalVariable.t, "", "utf8ByteSize", "", "", "logx_release"}, k = 1, mv = {1, 8, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final int utf8ByteSize(char c) {
            if (c < 0 || c >= 128) {
                return (128 > c || c >= 2048) ? 3 : 2;
            }
            return 1;
        }

        private Companion() {
        }

        public final String getStackTraceString(Throwable t) {
            Intrinsics.checkNotNullParameter(t, "t");
            StringWriter stringWriter = new StringWriter(256);
            PrintWriter printWriter = new PrintWriter((Writer) stringWriter, false);
            t.printStackTrace(printWriter);
            printWriter.flush();
            String string = stringWriter.toString();
            Intrinsics.checkNotNullExpressionValue(string, "sw.toString()");
            return string;
        }

        public final String createStackElementTag(StackTraceElement element) {
            Intrinsics.checkNotNullParameter(element, "element");
            String className = element.getClassName();
            Intrinsics.checkNotNullExpressionValue(className, "element.className");
            String strSubstringBefore$default = StringsKt.substringBefore$default(StringsKt.substringAfterLast$default(className, '.', (String) null, 2, (Object) null), Typography.dollar, (String) null, 2, (Object) null);
            if (strSubstringBefore$default.length() <= 23 || Build.VERSION.SDK_INT >= 26) {
                return strSubstringBefore$default;
            }
            String strSubstring = strSubstringBefore$default.substring(0, 23);
            Intrinsics.checkNotNullExpressionValue(strSubstring, "this as java.lang.String…ing(startIndex, endIndex)");
            return strSubstring;
        }

        public final String getLogLevel(int priority) {
            switch (priority) {
                case 2:
                    return ExifInterface.GPS_MEASUREMENT_INTERRUPTED;
                case 3:
                    return "D";
                case 4:
                    return "I";
                case 5:
                    return ExifInterface.LONGITUDE_WEST;
                case 6:
                    return ExifInterface.LONGITUDE_EAST;
                case 7:
                    return ExifInterface.GPS_MEASUREMENT_IN_PROGRESS;
                default:
                    return String.valueOf(priority);
            }
        }

        public final long utf8ByteSize(String str) {
            Intrinsics.checkNotNullParameter(str, "<this>");
            int length = str.length();
            long jUtf8ByteSize = 0;
            for (int i = 0; i < length; i++) {
                jUtf8ByteSize += (long) utf8ByteSize(str.charAt(i));
            }
            return jUtf8ByteSize;
        }

        public final File getCacheFileDir(Context context, String childDir) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(childDir, "childDir");
            File externalFilesDir = context.getExternalFilesDir(childDir);
            if (externalFilesDir != null) {
                return externalFilesDir;
            }
            File filesDir = context.getFilesDir();
            Intrinsics.checkNotNullExpressionValue(filesDir, "context.filesDir");
            return FilesKt.resolve(filesDir, childDir);
        }
    }
}
