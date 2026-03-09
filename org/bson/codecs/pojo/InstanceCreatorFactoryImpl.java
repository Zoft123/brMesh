package org.bson.codecs.pojo;

/* JADX INFO: loaded from: classes4.dex */
final class InstanceCreatorFactoryImpl<T> implements InstanceCreatorFactory<T> {
    private final CreatorExecutable<T> creatorExecutable;

    InstanceCreatorFactoryImpl(CreatorExecutable<T> creatorExecutable) {
        this.creatorExecutable = creatorExecutable;
    }

    @Override // org.bson.codecs.pojo.InstanceCreatorFactory
    public InstanceCreator<T> create() {
        return new InstanceCreatorImpl(this.creatorExecutable);
    }
}
