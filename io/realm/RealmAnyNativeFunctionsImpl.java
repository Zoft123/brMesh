package io.realm;

import io.realm.internal.RealmAnyNativeFunctions;
import io.realm.internal.TableQuery;
import io.realm.internal.objectstore.OsKeyPathMapping;
import io.realm.internal.objectstore.OsObjectBuilder;
import java.util.Map;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: classes4.dex */
public class RealmAnyNativeFunctionsImpl implements RealmAnyNativeFunctions {
    @Override // io.realm.internal.RealmAnyNativeFunctions
    public void handleItem(long j, RealmAny realmAny) {
        OsObjectBuilder.nativeAddRealmAnyListItem(j, realmAny.getNativePtr());
    }

    @Override // io.realm.internal.RealmAnyNativeFunctions
    public void handleItem(long j, Map.Entry<String, RealmAny> entry) {
        OsObjectBuilder.nativeAddRealmAnyDictionaryEntry(j, entry.getKey(), entry.getValue().getNativePtr());
    }

    @Override // io.realm.internal.RealmAnyNativeFunctions
    public void callRawPredicate(TableQuery tableQuery, @Nullable OsKeyPathMapping osKeyPathMapping, String str, RealmAny... realmAnyArr) {
        long[] jArr = new long[realmAnyArr.length];
        for (int i = 0; i < realmAnyArr.length; i++) {
            try {
                jArr[i] = realmAnyArr[i].getNativePtr();
            } catch (IllegalStateException e) {
                throw new IllegalArgumentException("Unmanaged Realm objects are not valid query arguments", e);
            }
        }
        tableQuery.rawPredicateWithPointers(osKeyPathMapping, str, jArr);
    }
}
