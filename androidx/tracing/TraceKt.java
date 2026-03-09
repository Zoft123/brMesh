package androidx.tracing;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.exifinterface.media.ExifInterface;
import com.alibaba.fastjson.asm.Opcodes;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: Trace.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000(\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\u001a3\u0010\u0000\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00010\u0003H\u0086\bø\u0001\u0000¢\u0006\u0002\u0010\u0006\u001a/\u0010\u0000\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u00012\u0006\u0010\u0007\u001a\u00020\u00042\u000e\b\u0004\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00010\u0003H\u0086\bø\u0001\u0000¢\u0006\u0002\u0010\b\u001aA\u0010\t\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u00012\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\u00032\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00010\u0003H\u0086\bø\u0001\u0000¢\u0006\u0002\u0010\r\u001aG\u0010\t\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u00012\u0006\u0010\u000e\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\f2\u001e\b\u0004\u0010\u0005\u001a\u0018\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00010\u0011\u0012\u0006\u0012\u0004\u0018\u00010\u00120\u0010H\u0086Hø\u0001\u0001¢\u0006\u0002\u0010\u0013\u0082\u0002\u000b\n\u0005\b\u009920\u0001\n\u0002\b\u0019¨\u0006\u0014"}, d2 = {"trace", ExifInterface.GPS_DIRECTION_TRUE, "lazyLabel", "Lkotlin/Function0;", "", "block", "(Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "label", "(Ljava/lang/String;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "traceAsync", "lazyMethodName", "lazyCookie", "", "(Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "methodName", "cookie", "Lkotlin/Function1;", "Lkotlin/coroutines/Continuation;", "", "(Ljava/lang/String;ILkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "tracing-ktx_release"}, k = 2, mv = {1, 8, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
public final class TraceKt {

    /* JADX INFO: renamed from: androidx.tracing.TraceKt$traceAsync$1, reason: invalid class name */
    /* JADX INFO: compiled from: Trace.kt */
    @Metadata(k = 3, mv = {1, 8, 0}, xi = Opcodes.ARETURN)
    @DebugMetadata(c = "androidx.tracing.TraceKt", f = "Trace.kt", i = {0, 0}, l = {76}, m = "traceAsync", n = {"methodName", "cookie"}, s = {"L$0", "I$0"})
    static final class AnonymousClass1<T> extends ContinuationImpl {
        int I$0;
        Object L$0;
        int label;
        /* synthetic */ Object result;

        AnonymousClass1(Continuation<? super AnonymousClass1> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return TraceKt.traceAsync(null, 0, null, this);
        }
    }

    public static final <T> T trace(String label, Function0<? extends T> block) {
        Intrinsics.checkNotNullParameter(label, "label");
        Intrinsics.checkNotNullParameter(block, "block");
        Trace.beginSection(label);
        try {
            return block.invoke();
        } finally {
            Trace.endSection();
        }
    }

    public static final <T> T trace(Function0<String> lazyLabel, Function0<? extends T> block) {
        Intrinsics.checkNotNullParameter(lazyLabel, "lazyLabel");
        Intrinsics.checkNotNullParameter(block, "block");
        boolean zIsEnabled = Trace.isEnabled();
        if (zIsEnabled) {
            Trace.beginSection(lazyLabel.invoke());
        }
        try {
            return block.invoke();
        } finally {
            if (zIsEnabled) {
                Trace.endSection();
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0014  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final <T> java.lang.Object traceAsync(java.lang.String r4, int r5, kotlin.jvm.functions.Function1<? super kotlin.coroutines.Continuation<? super T>, ? extends java.lang.Object> r6, kotlin.coroutines.Continuation<? super T> r7) throws java.lang.Throwable {
        /*
            boolean r0 = r7 instanceof androidx.tracing.TraceKt.AnonymousClass1
            if (r0 == 0) goto L14
            r0 = r7
            androidx.tracing.TraceKt$traceAsync$1 r0 = (androidx.tracing.TraceKt.AnonymousClass1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L14
            int r7 = r0.label
            int r7 = r7 - r2
            r0.label = r7
            goto L19
        L14:
            androidx.tracing.TraceKt$traceAsync$1 r0 = new androidx.tracing.TraceKt$traceAsync$1
            r0.<init>(r7)
        L19:
            java.lang.Object r7 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L38
            if (r2 != r3) goto L30
            int r5 = r0.I$0
            java.lang.Object r4 = r0.L$0
            java.lang.String r4 = (java.lang.String) r4
            kotlin.ResultKt.throwOnFailure(r7)     // Catch: java.lang.Throwable -> L4f
            goto L4b
        L30:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L38:
            kotlin.ResultKt.throwOnFailure(r7)
            androidx.tracing.Trace.beginAsyncSection(r4, r5)
            r0.L$0 = r4     // Catch: java.lang.Throwable -> L4f
            r0.I$0 = r5     // Catch: java.lang.Throwable -> L4f
            r0.label = r3     // Catch: java.lang.Throwable -> L4f
            java.lang.Object r7 = r6.invoke(r0)     // Catch: java.lang.Throwable -> L4f
            if (r7 != r1) goto L4b
            return r1
        L4b:
            androidx.tracing.Trace.endAsyncSection(r4, r5)
            return r7
        L4f:
            r6 = move-exception
            androidx.tracing.Trace.endAsyncSection(r4, r5)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.tracing.TraceKt.traceAsync(java.lang.String, int, kotlin.jvm.functions.Function1, kotlin.coroutines.Continuation):java.lang.Object");
    }

    private static final <T> Object traceAsync$$forInline(String str, int i, Function1<? super Continuation<? super T>, ? extends Object> function1, Continuation<? super T> continuation) {
        Trace.beginAsyncSection(str, i);
        try {
            return function1.invoke(continuation);
        } finally {
            Trace.endAsyncSection(str, i);
        }
    }

    public static final <T> T traceAsync(Function0<String> lazyMethodName, Function0<Integer> lazyCookie, Function0<? extends T> block) {
        String strInvoke;
        int iIntValue;
        Intrinsics.checkNotNullParameter(lazyMethodName, "lazyMethodName");
        Intrinsics.checkNotNullParameter(lazyCookie, "lazyCookie");
        Intrinsics.checkNotNullParameter(block, "block");
        if (Trace.isEnabled()) {
            strInvoke = lazyMethodName.invoke();
            iIntValue = lazyCookie.invoke().intValue();
            Trace.beginAsyncSection(strInvoke, iIntValue);
        } else {
            strInvoke = null;
            iIntValue = 0;
        }
        try {
            return block.invoke();
        } finally {
            if (strInvoke != null) {
                Trace.endAsyncSection(strInvoke, iIntValue);
            }
        }
    }
}
