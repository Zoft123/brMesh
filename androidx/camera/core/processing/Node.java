package androidx.camera.core.processing;

/* JADX INFO: loaded from: classes.dex */
public interface Node<I, O> {
    void release();

    O transform(I i);
}
