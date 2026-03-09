package androidx.camera.lifecycle;

import android.content.Context;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.concurrent.futures.ListenableFutureKt;
import androidx.constraintlayout.widget.ConstraintLayout;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;

/* JADX INFO: compiled from: ProcessCameraProviderExt.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u001a\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004H\u0086@¢\u0006\u0002\u0010\u0005¨\u0006\u0006"}, d2 = {"awaitInstance", "Landroidx/camera/lifecycle/ProcessCameraProvider;", "Landroidx/camera/lifecycle/ProcessCameraProvider$Companion;", "context", "Landroid/content/Context;", "(Landroidx/camera/lifecycle/ProcessCameraProvider$Companion;Landroid/content/Context;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "camera-lifecycle_release"}, k = 2, mv = {1, 8, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
public final class ProcessCameraProviderExtKt {
    public static final Object awaitInstance(ProcessCameraProvider.Companion companion, Context context, Continuation<? super ProcessCameraProvider> continuation) {
        return ListenableFutureKt.await(companion.getInstance(context), continuation);
    }
}
