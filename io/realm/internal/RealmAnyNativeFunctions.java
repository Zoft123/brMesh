package io.realm.internal;

import io.realm.RealmAny;
import io.realm.internal.objectstore.OsKeyPathMapping;
import java.util.Map;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: classes4.dex */
public interface RealmAnyNativeFunctions {
    void callRawPredicate(TableQuery tableQuery, @Nullable OsKeyPathMapping osKeyPathMapping, String str, RealmAny... realmAnyArr);

    void handleItem(long j, RealmAny realmAny);

    void handleItem(long j, Map.Entry<String, RealmAny> entry);
}
