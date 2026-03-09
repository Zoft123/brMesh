package io.realm;

import io.realm.RealmModel;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: classes4.dex */
public interface RealmObjectChangeListener<T extends RealmModel> {
    void onChange(T t, @Nullable ObjectChangeSet objectChangeSet);
}
