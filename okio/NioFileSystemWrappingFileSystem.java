package okio;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.constraintlayout.widget.ConstraintLayout;
import io.realm.CollectionUtils;
import io.realm.Realm$$ExternalSyntheticApiModelOutline0;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.NoSuchFileException;
import java.nio.file.OpenOption;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.FileAttribute;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.io.path.PathsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import okio.Path;

/* JADX INFO: compiled from: NioFileSystemWrappingFileSystem.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0000\b\u0000\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\f\u0010\u0006\u001a\u00020\u0007*\u00020\bH\u0002J\u0010\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\bH\u0016J\u0012\u0010\u000b\u001a\u0004\u0018\u00010\f2\u0006\u0010\n\u001a\u00020\bH\u0016J\u0016\u0010\r\u001a\b\u0012\u0004\u0012\u00020\b0\u000e2\u0006\u0010\u000f\u001a\u00020\bH\u0016J\u0018\u0010\u0010\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u000e2\u0006\u0010\u000f\u001a\u00020\bH\u0016J \u0010\r\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u000e2\u0006\u0010\u000f\u001a\u00020\b2\u0006\u0010\u0011\u001a\u00020\u0012H\u0002J\u0010\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\bH\u0016J \u0010\u0016\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\b2\u0006\u0010\u0017\u001a\u00020\u00122\u0006\u0010\u0018\u001a\u00020\u0012H\u0016J\u0010\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u0015\u001a\u00020\bH\u0016J\u0018\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u0015\u001a\u00020\b2\u0006\u0010\u0017\u001a\u00020\u0012H\u0016J\u0018\u0010\u001d\u001a\u00020\u001c2\u0006\u0010\u0015\u001a\u00020\b2\u0006\u0010\u0018\u001a\u00020\u0012H\u0016J\u0018\u0010\u001e\u001a\u00020\u001f2\u0006\u0010\u000f\u001a\u00020\b2\u0006\u0010\u0017\u001a\u00020\u0012H\u0016J\u0018\u0010 \u001a\u00020\u001f2\u0006\u0010\u0019\u001a\u00020\b2\u0006\u0010!\u001a\u00020\bH\u0016J\u0018\u0010\"\u001a\u00020\u001f2\u0006\u0010\n\u001a\u00020\b2\u0006\u0010\u0018\u001a\u00020\u0012H\u0016J\u0018\u0010#\u001a\u00020\u001f2\u0006\u0010\u0019\u001a\u00020\b2\u0006\u0010!\u001a\u00020\bH\u0016J\b\u0010$\u001a\u00020\u001fH\u0016J\b\u0010%\u001a\u00020&H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006'"}, d2 = {"Lokio/NioFileSystemWrappingFileSystem;", "Lokio/NioSystemFileSystem;", "nioFileSystem", "Ljava/nio/file/FileSystem;", "<init>", "(Ljava/nio/file/FileSystem;)V", "resolve", "Ljava/nio/file/Path;", "Lokio/Path;", "canonicalize", "path", "metadataOrNull", "Lokio/FileMetadata;", CollectionUtils.LIST_TYPE, "", "dir", "listOrNull", "throwOnFailure", "", "openReadOnly", "Lokio/FileHandle;", "file", "openReadWrite", "mustCreate", "mustExist", "source", "Lokio/Source;", "sink", "Lokio/Sink;", "appendingSink", "createDirectory", "", "atomicMove", TypedValues.AttributesType.S_TARGET, "delete", "createSymlink", "close", "toString", "", "okio"}, k = 1, mv = {2, 1, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
public final class NioFileSystemWrappingFileSystem extends NioSystemFileSystem {
    private final java.nio.file.FileSystem nioFileSystem;

    public NioFileSystemWrappingFileSystem(java.nio.file.FileSystem nioFileSystem) {
        Intrinsics.checkNotNullParameter(nioFileSystem, "nioFileSystem");
        this.nioFileSystem = nioFileSystem;
    }

    private final java.nio.file.Path resolve(Path path) {
        java.nio.file.Path path2 = this.nioFileSystem.getPath(path.toString(), new String[0]);
        Intrinsics.checkNotNullExpressionValue(path2, "getPath(...)");
        return path2;
    }

    @Override // okio.JvmSystemFileSystem, okio.FileSystem
    public Path canonicalize(Path path) throws IOException {
        Intrinsics.checkNotNullParameter(path, "path");
        try {
            Path.Companion companion = Path.INSTANCE;
            java.nio.file.Path realPath = resolve(path).toRealPath(new LinkOption[0]);
            Intrinsics.checkNotNullExpressionValue(realPath, "toRealPath(...)");
            return Path.Companion.get$default(companion, realPath, false, 1, (Object) null);
        } catch (NoSuchFileException unused) {
            throw new FileNotFoundException("no such file: " + path);
        }
    }

    @Override // okio.NioSystemFileSystem, okio.JvmSystemFileSystem, okio.FileSystem
    public FileMetadata metadataOrNull(Path path) {
        Intrinsics.checkNotNullParameter(path, "path");
        return metadataOrNull(resolve(path));
    }

    @Override // okio.JvmSystemFileSystem, okio.FileSystem
    public List<Path> list(Path dir) throws IOException {
        Intrinsics.checkNotNullParameter(dir, "dir");
        List<Path> list = list(dir, true);
        Intrinsics.checkNotNull(list);
        return list;
    }

    @Override // okio.JvmSystemFileSystem, okio.FileSystem
    public List<Path> listOrNull(Path dir) {
        Intrinsics.checkNotNullParameter(dir, "dir");
        return list(dir, false);
    }

    private final List<Path> list(Path dir, boolean throwOnFailure) throws IOException {
        java.nio.file.Path pathResolve = resolve(dir);
        try {
            List listListDirectoryEntries$default = PathsKt.listDirectoryEntries$default(pathResolve, null, 1, null);
            ArrayList arrayList = new ArrayList();
            Iterator it = listListDirectoryEntries$default.iterator();
            while (it.hasNext()) {
                arrayList.add(Path.Companion.get$default(Path.INSTANCE, Realm$$ExternalSyntheticApiModelOutline0.m1281m(it.next()), false, 1, (Object) null));
            }
            ArrayList arrayList2 = arrayList;
            CollectionsKt.sort(arrayList2);
            return arrayList2;
        } catch (Exception unused) {
            if (!throwOnFailure) {
                return null;
            }
            if (Files.exists(pathResolve, (LinkOption[]) Arrays.copyOf(new LinkOption[0], 0))) {
                throw new IOException("failed to list " + dir);
            }
            throw new FileNotFoundException("no such file: " + dir);
        }
    }

    @Override // okio.JvmSystemFileSystem, okio.FileSystem
    public FileHandle openReadOnly(Path file) throws IOException {
        Intrinsics.checkNotNullParameter(file, "file");
        try {
            FileChannel fileChannelOpen = FileChannel.open(resolve(file), StandardOpenOption.READ);
            Intrinsics.checkNotNull(fileChannelOpen);
            return new NioFileSystemFileHandle(false, fileChannelOpen);
        } catch (NoSuchFileException unused) {
            throw new FileNotFoundException("no such file: " + file);
        }
    }

    @Override // okio.JvmSystemFileSystem, okio.FileSystem
    public FileHandle openReadWrite(Path file, boolean mustCreate, boolean mustExist) throws IOException {
        Intrinsics.checkNotNullParameter(file, "file");
        if (mustCreate && mustExist) {
            throw new IllegalArgumentException("Cannot require mustCreate and mustExist at the same time.".toString());
        }
        List listCreateListBuilder = CollectionsKt.createListBuilder();
        listCreateListBuilder.add(StandardOpenOption.READ);
        listCreateListBuilder.add(StandardOpenOption.WRITE);
        if (mustCreate) {
            listCreateListBuilder.add(StandardOpenOption.CREATE_NEW);
        } else if (!mustExist) {
            listCreateListBuilder.add(StandardOpenOption.CREATE);
        }
        List listBuild = CollectionsKt.build(listCreateListBuilder);
        try {
            java.nio.file.Path pathResolve = resolve(file);
            StandardOpenOption[] standardOpenOptionArr = (StandardOpenOption[]) listBuild.toArray(new StandardOpenOption[0]);
            FileChannel fileChannelOpen = FileChannel.open(pathResolve, (OpenOption[]) Arrays.copyOf(standardOpenOptionArr, standardOpenOptionArr.length));
            Intrinsics.checkNotNull(fileChannelOpen);
            return new NioFileSystemFileHandle(true, fileChannelOpen);
        } catch (NoSuchFileException unused) {
            throw new FileNotFoundException("no such file: " + file);
        }
    }

    @Override // okio.JvmSystemFileSystem, okio.FileSystem
    public Source source(Path file) throws IOException {
        Intrinsics.checkNotNullParameter(file, "file");
        try {
            InputStream inputStreamNewInputStream = Files.newInputStream(resolve(file), (OpenOption[]) Arrays.copyOf(new OpenOption[0], 0));
            Intrinsics.checkNotNullExpressionValue(inputStreamNewInputStream, "newInputStream(...)");
            return Okio.source(inputStreamNewInputStream);
        } catch (NoSuchFileException unused) {
            throw new FileNotFoundException("no such file: " + file);
        }
    }

    @Override // okio.JvmSystemFileSystem, okio.FileSystem
    public Sink sink(Path file, boolean mustCreate) throws IOException {
        Intrinsics.checkNotNullParameter(file, "file");
        List listCreateListBuilder = CollectionsKt.createListBuilder();
        if (mustCreate) {
            listCreateListBuilder.add(StandardOpenOption.CREATE_NEW);
        }
        List listBuild = CollectionsKt.build(listCreateListBuilder);
        try {
            java.nio.file.Path pathResolve = resolve(file);
            StandardOpenOption[] standardOpenOptionArr = (StandardOpenOption[]) listBuild.toArray(new StandardOpenOption[0]);
            OpenOption[] openOptionArr = (OpenOption[]) Arrays.copyOf(standardOpenOptionArr, standardOpenOptionArr.length);
            OutputStream outputStreamNewOutputStream = Files.newOutputStream(pathResolve, (OpenOption[]) Arrays.copyOf(openOptionArr, openOptionArr.length));
            Intrinsics.checkNotNullExpressionValue(outputStreamNewOutputStream, "newOutputStream(...)");
            return Okio.sink(outputStreamNewOutputStream);
        } catch (NoSuchFileException unused) {
            throw new FileNotFoundException("no such file: " + file);
        }
    }

    @Override // okio.JvmSystemFileSystem, okio.FileSystem
    public Sink appendingSink(Path file, boolean mustExist) throws IOException {
        Intrinsics.checkNotNullParameter(file, "file");
        List listCreateListBuilder = CollectionsKt.createListBuilder();
        listCreateListBuilder.add(StandardOpenOption.APPEND);
        if (!mustExist) {
            listCreateListBuilder.add(StandardOpenOption.CREATE);
        }
        List listBuild = CollectionsKt.build(listCreateListBuilder);
        java.nio.file.Path pathResolve = resolve(file);
        StandardOpenOption[] standardOpenOptionArr = (StandardOpenOption[]) listBuild.toArray(new StandardOpenOption[0]);
        OpenOption[] openOptionArr = (OpenOption[]) Arrays.copyOf(standardOpenOptionArr, standardOpenOptionArr.length);
        OutputStream outputStreamNewOutputStream = Files.newOutputStream(pathResolve, (OpenOption[]) Arrays.copyOf(openOptionArr, openOptionArr.length));
        Intrinsics.checkNotNullExpressionValue(outputStreamNewOutputStream, "newOutputStream(...)");
        return Okio.sink(outputStreamNewOutputStream);
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0014  */
    @Override // okio.JvmSystemFileSystem, okio.FileSystem
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void createDirectory(okio.Path r4, boolean r5) throws java.io.IOException {
        /*
            r3 = this;
            java.lang.String r0 = "dir"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r4, r0)
            okio.FileMetadata r0 = r3.metadataOrNull(r4)
            r1 = 0
            if (r0 == 0) goto L14
            boolean r0 = r0.getIsDirectory()
            r2 = 1
            if (r0 != r2) goto L14
            goto L15
        L14:
            r2 = r1
        L15:
            if (r2 == 0) goto L31
            if (r5 != 0) goto L1a
            goto L31
        L1a:
            java.io.IOException r5 = new java.io.IOException
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            r0.append(r4)
            java.lang.String r4 = " already exists."
            r0.append(r4)
            java.lang.String r4 = r0.toString()
            r5.<init>(r4)
            throw r5
        L31:
            java.nio.file.Path r5 = r3.resolve(r4)     // Catch: java.io.IOException -> L47
            java.nio.file.attribute.FileAttribute[] r0 = new java.nio.file.attribute.FileAttribute[r1]     // Catch: java.io.IOException -> L47
            java.lang.Object[] r0 = java.util.Arrays.copyOf(r0, r1)     // Catch: java.io.IOException -> L47
            java.nio.file.attribute.FileAttribute[] r0 = (java.nio.file.attribute.FileAttribute[]) r0     // Catch: java.io.IOException -> L47
            java.nio.file.Path r5 = kotlin.io.path.PathTreeWalk$$ExternalSyntheticApiModelOutline0.m$1(r5, r0)     // Catch: java.io.IOException -> L47
            java.lang.String r0 = "createDirectory(...)"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r5, r0)     // Catch: java.io.IOException -> L47
            return
        L47:
            r5 = move-exception
            if (r2 == 0) goto L4b
            return
        L4b:
            java.io.IOException r0 = new java.io.IOException
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "failed to create directory: "
            r1.<init>(r2)
            r1.append(r4)
            java.lang.String r4 = r1.toString()
            java.lang.Throwable r5 = (java.lang.Throwable) r5
            r0.<init>(r4, r5)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: okio.NioFileSystemWrappingFileSystem.createDirectory(okio.Path, boolean):void");
    }

    @Override // okio.NioSystemFileSystem, okio.JvmSystemFileSystem, okio.FileSystem
    public void atomicMove(Path source, Path target) throws IOException {
        Intrinsics.checkNotNullParameter(source, "source");
        Intrinsics.checkNotNullParameter(target, "target");
        try {
            Intrinsics.checkNotNullExpressionValue(Files.move(resolve(source), resolve(target), (CopyOption[]) Arrays.copyOf(new CopyOption[]{StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING}, 2)), "move(...)");
        } catch (UnsupportedOperationException unused) {
            throw new IOException("atomic move not supported");
        } catch (NoSuchFileException e) {
            throw new FileNotFoundException(e.getMessage());
        }
    }

    @Override // okio.JvmSystemFileSystem, okio.FileSystem
    public void delete(Path path, boolean mustExist) throws IOException {
        Intrinsics.checkNotNullParameter(path, "path");
        if (Thread.interrupted()) {
            throw new InterruptedIOException("interrupted");
        }
        java.nio.file.Path pathResolve = resolve(path);
        try {
            Files.delete(pathResolve);
        } catch (NoSuchFileException unused) {
            if (mustExist) {
                throw new FileNotFoundException("no such file: " + path);
            }
        } catch (IOException unused2) {
            if (Files.exists(pathResolve, (LinkOption[]) Arrays.copyOf(new LinkOption[0], 0))) {
                throw new IOException("failed to delete " + path);
            }
        }
    }

    @Override // okio.NioSystemFileSystem, okio.JvmSystemFileSystem, okio.FileSystem
    public void createSymlink(Path source, Path target) {
        Intrinsics.checkNotNullParameter(source, "source");
        Intrinsics.checkNotNullParameter(target, "target");
        Intrinsics.checkNotNullExpressionValue(Files.createSymbolicLink(resolve(source), resolve(target), (FileAttribute[]) Arrays.copyOf(new FileAttribute[0], 0)), "createSymbolicLink(...)");
    }

    @Override // okio.FileSystem, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.nioFileSystem.close();
    }

    @Override // okio.NioSystemFileSystem, okio.JvmSystemFileSystem
    public String toString() {
        String simpleName = Reflection.getOrCreateKotlinClass(this.nioFileSystem.getClass()).getSimpleName();
        Intrinsics.checkNotNull(simpleName);
        return simpleName;
    }
}
