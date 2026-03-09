package okio.internal;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.alibaba.fastjson.asm.Opcodes;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.error.GattError;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.ArrayDeque;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequenceScope;
import kotlin.sequences.SequencesKt;
import okio.FileMetadata;
import okio.Path;

/* JADX INFO: renamed from: okio.internal.-FileSystem, reason: invalid class name */
/* JADX INFO: compiled from: FileSystem.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00008\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a\u0014\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004H\u0000\u001a\u0014\u0010\u0005\u001a\u00020\u0006*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004H\u0000\u001a\u001c\u0010\u0007\u001a\u00020\b*\u00020\u00022\u0006\u0010\t\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u0006H\u0000\u001a\u001c\u0010\u000b\u001a\u00020\b*\u00020\u00022\u0006\u0010\f\u001a\u00020\u00042\u0006\u0010\r\u001a\u00020\u0004H\u0000\u001a\u001c\u0010\u000e\u001a\u00020\b*\u00020\u00022\u0006\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u0006H\u0000\u001a\"\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00040\u0012*\u00020\u00022\u0006\u0010\t\u001a\u00020\u00042\u0006\u0010\u0013\u001a\u00020\u0006H\u0000\u001aF\u0010\u0014\u001a\u00020\b*\b\u0012\u0004\u0012\u00020\u00040\u00152\u0006\u0010\u0016\u001a\u00020\u00022\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00040\u00182\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0013\u001a\u00020\u00062\u0006\u0010\u0019\u001a\u00020\u0006H\u0080@¢\u0006\u0002\u0010\u001a\u001a\u0016\u0010\u001b\u001a\u0004\u0018\u00010\u0004*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004H\u0000¨\u0006\u001c"}, d2 = {"commonMetadata", "Lokio/FileMetadata;", "Lokio/FileSystem;", "path", "Lokio/Path;", "commonExists", "", "commonCreateDirectories", "", "dir", "mustCreate", "commonCopy", "source", TypedValues.AttributesType.S_TARGET, "commonDeleteRecursively", "fileOrDirectory", "mustExist", "commonListRecursively", "Lkotlin/sequences/Sequence;", "followSymlinks", "collectRecursively", "Lkotlin/sequences/SequenceScope;", "fileSystem", "stack", "Lkotlin/collections/ArrayDeque;", "postorder", "(Lkotlin/sequences/SequenceScope;Lokio/FileSystem;Lkotlin/collections/ArrayDeque;Lokio/Path;ZZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "symlinkTarget", "okio"}, k = 2, mv = {2, 1, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
public final class FileSystem {

    /* JADX INFO: renamed from: okio.internal.-FileSystem$collectRecursively$1, reason: invalid class name */
    /* JADX INFO: compiled from: FileSystem.kt */
    @Metadata(k = 3, mv = {2, 1, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    @DebugMetadata(c = "okio.internal.-FileSystem", f = "FileSystem.kt", i = {0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1}, l = {116, GattError.GATT_ILLEGAL_PARAMETER, 145}, m = "collectRecursively", n = {"$this$collectRecursively", "fileSystem", "stack", "path", "followSymlinks", "postorder", "$this$collectRecursively", "fileSystem", "stack", "path", "followSymlinks", "postorder"}, s = {"L$0", "L$1", "L$2", "L$3", "Z$0", "Z$1", "L$0", "L$1", "L$2", "L$3", "Z$0", "Z$1"})
    static final class AnonymousClass1 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        Object L$4;
        boolean Z$0;
        boolean Z$1;
        int label;
        /* synthetic */ Object result;

        AnonymousClass1(Continuation<? super AnonymousClass1> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return FileSystem.collectRecursively(null, null, null, null, false, false, this);
        }
    }

    public static final FileMetadata commonMetadata(okio.FileSystem fileSystem, Path path) throws IOException {
        Intrinsics.checkNotNullParameter(fileSystem, "<this>");
        Intrinsics.checkNotNullParameter(path, "path");
        FileMetadata fileMetadataMetadataOrNull = fileSystem.metadataOrNull(path);
        if (fileMetadataMetadataOrNull != null) {
            return fileMetadataMetadataOrNull;
        }
        throw new FileNotFoundException("no such file: " + path);
    }

    public static final boolean commonExists(okio.FileSystem fileSystem, Path path) throws IOException {
        Intrinsics.checkNotNullParameter(fileSystem, "<this>");
        Intrinsics.checkNotNullParameter(path, "path");
        return fileSystem.metadataOrNull(path) != null;
    }

    public static final void commonCreateDirectories(okio.FileSystem fileSystem, Path dir, boolean z) throws IOException {
        Intrinsics.checkNotNullParameter(fileSystem, "<this>");
        Intrinsics.checkNotNullParameter(dir, "dir");
        ArrayDeque arrayDeque = new ArrayDeque();
        for (Path pathParent = dir; pathParent != null && !fileSystem.exists(pathParent); pathParent = pathParent.parent()) {
            arrayDeque.addFirst(pathParent);
        }
        if (z && arrayDeque.isEmpty()) {
            throw new IOException(dir + " already exists.");
        }
        Iterator it = arrayDeque.iterator();
        while (it.hasNext()) {
            okio.FileSystem.createDirectory$default(fileSystem, (Path) it.next(), false, 2, null);
        }
    }

    public static final void commonDeleteRecursively(okio.FileSystem fileSystem, Path fileOrDirectory, boolean z) throws IOException {
        Intrinsics.checkNotNullParameter(fileSystem, "<this>");
        Intrinsics.checkNotNullParameter(fileOrDirectory, "fileOrDirectory");
        Iterator it = SequencesKt.sequence(new FileSystem$commonDeleteRecursively$sequence$1(fileSystem, fileOrDirectory, null)).iterator();
        while (it.hasNext()) {
            fileSystem.delete((Path) it.next(), z && !it.hasNext());
        }
    }

    /* JADX INFO: renamed from: okio.internal.-FileSystem$commonListRecursively$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: FileSystem.kt */
    @Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00030\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlin/sequences/SequenceScope;", "Lokio/Path;"}, k = 3, mv = {2, 1, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    @DebugMetadata(c = "okio.internal.-FileSystem$commonListRecursively$1", f = "FileSystem.kt", i = {0, 0}, l = {Opcodes.IADD}, m = "invokeSuspend", n = {"$this$sequence", "stack"}, s = {"L$0", "L$1"})
    static final class C01391 extends RestrictedSuspendLambda implements Function2<SequenceScope<? super Path>, Continuation<? super Unit>, Object> {
        final /* synthetic */ Path $dir;
        final /* synthetic */ boolean $followSymlinks;
        final /* synthetic */ okio.FileSystem $this_commonListRecursively;
        private /* synthetic */ Object L$0;
        Object L$1;
        Object L$2;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C01391(Path path, okio.FileSystem fileSystem, boolean z, Continuation<? super C01391> continuation) {
            super(2, continuation);
            this.$dir = path;
            this.$this_commonListRecursively = fileSystem;
            this.$followSymlinks = z;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            C01391 c01391 = new C01391(this.$dir, this.$this_commonListRecursively, this.$followSymlinks, continuation);
            c01391.L$0 = obj;
            return c01391;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(SequenceScope<? super Path> sequenceScope, Continuation<? super Unit> continuation) {
            return ((C01391) create(sequenceScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            SequenceScope sequenceScope;
            ArrayDeque arrayDeque;
            Iterator<Path> it;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                SequenceScope sequenceScope2 = (SequenceScope) this.L$0;
                ArrayDeque arrayDeque2 = new ArrayDeque();
                arrayDeque2.addLast(this.$dir);
                sequenceScope = sequenceScope2;
                arrayDeque = arrayDeque2;
                it = this.$this_commonListRecursively.list(this.$dir).iterator();
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                it = (Iterator) this.L$2;
                ArrayDeque arrayDeque3 = (ArrayDeque) this.L$1;
                sequenceScope = (SequenceScope) this.L$0;
                ResultKt.throwOnFailure(obj);
                arrayDeque = arrayDeque3;
            }
            while (it.hasNext()) {
                Path next = it.next();
                this.L$0 = sequenceScope;
                this.L$1 = arrayDeque;
                this.L$2 = it;
                this.label = 1;
                if (FileSystem.collectRecursively(sequenceScope, this.$this_commonListRecursively, arrayDeque, next, this.$followSymlinks, false, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            }
            return Unit.INSTANCE;
        }
    }

    public static final Sequence<Path> commonListRecursively(okio.FileSystem fileSystem, Path dir, boolean z) throws IOException {
        Intrinsics.checkNotNullParameter(fileSystem, "<this>");
        Intrinsics.checkNotNullParameter(dir, "dir");
        return SequencesKt.sequence(new C01391(dir, fileSystem, z, null));
    }

    /* JADX WARN: Code restructure failed: missing block: B:26:0x009f, code lost:
    
        if (r15.yield(r1, r4) == r5) goto L68;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x00e4, code lost:
    
        if (r0 != false) goto L46;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x00e6, code lost:
    
        if (r11 != 0) goto L65;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x00e8, code lost:
    
        r6.addLast(r12);
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x00eb, code lost:
    
        r3 = r3.iterator();
        r3 = r1;
        r1 = r2;
        r2 = r3;
        r10 = r10;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x013f, code lost:
    
        r0 = th;
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x0140, code lost:
    
        r9 = r6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x015d, code lost:
    
        if (r10.yield(r1, r4) == r5) goto L68;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:50:0x00f9 A[Catch: all -> 0x013f, TRY_LEAVE, TryCatch #2 {all -> 0x013f, blocks: (B:48:0x00f3, B:50:0x00f9, B:47:0x00eb), top: B:77:0x00eb }] */
    /* JADX WARN: Removed duplicated region for block: B:59:0x0136  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x014c  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x0163  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x001a  */
    /* JADX WARN: Type inference failed for: r10v14 */
    /* JADX WARN: Type inference failed for: r10v15 */
    /* JADX WARN: Type inference failed for: r10v2 */
    /* JADX WARN: Type inference failed for: r10v4 */
    /* JADX WARN: Type inference failed for: r10v5, types: [kotlin.sequences.SequenceScope] */
    /* JADX WARN: Type inference failed for: r10v6, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r10v9 */
    /* JADX WARN: Type inference failed for: r11v10 */
    /* JADX WARN: Type inference failed for: r11v11 */
    /* JADX WARN: Type inference failed for: r11v14 */
    /* JADX WARN: Type inference failed for: r11v7 */
    /* JADX WARN: Type inference failed for: r15v0, types: [java.lang.Object, kotlin.sequences.SequenceScope, kotlin.sequences.SequenceScope<? super okio.Path>] */
    /* JADX WARN: Type inference failed for: r15v1, types: [kotlin.sequences.SequenceScope] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:56:0x012e -> B:19:0x005e). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object collectRecursively(kotlin.sequences.SequenceScope<? super okio.Path> r15, okio.FileSystem r16, kotlin.collections.ArrayDeque<okio.Path> r17, okio.Path r18, boolean r19, boolean r20, kotlin.coroutines.Continuation<? super kotlin.Unit> r21) throws java.lang.Throwable {
        /*
            Method dump skipped, instruction units count: 358
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: okio.internal.FileSystem.collectRecursively(kotlin.sequences.SequenceScope, okio.FileSystem, kotlin.collections.ArrayDeque, okio.Path, boolean, boolean, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public static final Path symlinkTarget(okio.FileSystem fileSystem, Path path) throws IOException {
        Intrinsics.checkNotNullParameter(fileSystem, "<this>");
        Intrinsics.checkNotNullParameter(path, "path");
        Path symlinkTarget = fileSystem.metadata(path).getSymlinkTarget();
        if (symlinkTarget == null) {
            return null;
        }
        Path pathParent = path.parent();
        Intrinsics.checkNotNull(pathParent);
        return pathParent.resolve(symlinkTarget);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0049 A[Catch: all -> 0x0061, TRY_LEAVE, TryCatch #1 {all -> 0x0061, blocks: (B:3:0x0016, B:19:0x0049, B:25:0x0060, B:16:0x0042, B:13:0x003d, B:4:0x0025), top: B:38:0x0016, inners: #0, #4 }] */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0060 A[Catch: all -> 0x0061, TRY_ENTER, TRY_LEAVE, TryCatch #1 {all -> 0x0061, blocks: (B:3:0x0016, B:19:0x0049, B:25:0x0060, B:16:0x0042, B:13:0x003d, B:4:0x0025), top: B:38:0x0016, inners: #0, #4 }] */
    /* JADX WARN: Type inference failed for: r5v1, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r5v10 */
    /* JADX WARN: Type inference failed for: r5v2, types: [java.lang.Throwable] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void commonCopy(okio.FileSystem r5, okio.Path r6, okio.Path r7) throws java.io.IOException {
        /*
            java.lang.String r0 = "<this>"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r5, r0)
            java.lang.String r0 = "source"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r6, r0)
            java.lang.String r0 = "target"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r7, r0)
            okio.Source r6 = r5.source(r6)
            java.io.Closeable r6 = (java.io.Closeable) r6
            r0 = 0
            r1 = r6
            okio.Source r1 = (okio.Source) r1     // Catch: java.lang.Throwable -> L61
            r2 = 0
            r3 = 2
            okio.Sink r5 = okio.FileSystem.sink$default(r5, r7, r2, r3, r0)     // Catch: java.lang.Throwable -> L61
            okio.BufferedSink r5 = okio.Okio.buffer(r5)     // Catch: java.lang.Throwable -> L61
            java.io.Closeable r5 = (java.io.Closeable) r5     // Catch: java.lang.Throwable -> L61
            r7 = r5
            okio.BufferedSink r7 = (okio.BufferedSink) r7     // Catch: java.lang.Throwable -> L3a
            long r1 = r7.writeAll(r1)     // Catch: java.lang.Throwable -> L3a
            java.lang.Long r7 = java.lang.Long.valueOf(r1)     // Catch: java.lang.Throwable -> L3a
            if (r5 == 0) goto L38
            r5.close()     // Catch: java.lang.Throwable -> L36
            goto L38
        L36:
            r5 = move-exception
            goto L47
        L38:
            r5 = r0
            goto L47
        L3a:
            r7 = move-exception
            if (r5 == 0) goto L45
            r5.close()     // Catch: java.lang.Throwable -> L41
            goto L45
        L41:
            r5 = move-exception
            kotlin.ExceptionsKt.addSuppressed(r7, r5)     // Catch: java.lang.Throwable -> L61
        L45:
            r5 = r7
            r7 = r0
        L47:
            if (r5 != 0) goto L60
            java.lang.Object r7 = (java.lang.Object) r7     // Catch: java.lang.Throwable -> L61
            java.lang.Number r7 = (java.lang.Number) r7     // Catch: java.lang.Throwable -> L61
            long r1 = r7.longValue()     // Catch: java.lang.Throwable -> L61
            java.lang.Long r5 = java.lang.Long.valueOf(r1)     // Catch: java.lang.Throwable -> L61
            if (r6 == 0) goto L5c
            r6.close()     // Catch: java.lang.Throwable -> L5b
            goto L5c
        L5b:
            r0 = move-exception
        L5c:
            r4 = r0
            r0 = r5
            r5 = r4
            goto L6c
        L60:
            throw r5     // Catch: java.lang.Throwable -> L61
        L61:
            r5 = move-exception
            if (r6 == 0) goto L6c
            r6.close()     // Catch: java.lang.Throwable -> L68
            goto L6c
        L68:
            r6 = move-exception
            kotlin.ExceptionsKt.addSuppressed(r5, r6)
        L6c:
            if (r5 != 0) goto L71
            java.lang.Object r0 = (java.lang.Object) r0
            return
        L71:
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: okio.internal.FileSystem.commonCopy(okio.FileSystem, okio.Path, okio.Path):void");
    }
}
