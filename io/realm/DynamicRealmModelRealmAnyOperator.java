package io.realm;

import io.realm.internal.Table;
import io.realm.internal.core.NativeRealmAny;

/* JADX INFO: compiled from: RealmAnyOperator.java */
/* JADX INFO: loaded from: classes4.dex */
final class DynamicRealmModelRealmAnyOperator extends RealmModelOperator {
    private static <T extends RealmModel> T getRealmModel(BaseRealm baseRealm, NativeRealmAny nativeRealmAny) {
        return (T) baseRealm.get(DynamicRealmObject.class, Table.getClassNameForTable(nativeRealmAny.getRealmModelTableName(baseRealm.getSharedRealm())), nativeRealmAny.getRealmModelRowKey());
    }

    DynamicRealmModelRealmAnyOperator(BaseRealm baseRealm, NativeRealmAny nativeRealmAny) {
        super(getRealmModel(baseRealm, nativeRealmAny));
    }

    @Override // io.realm.RealmModelOperator, io.realm.RealmAnyOperator
    Class<?> getTypedClass() {
        return DynamicRealmObject.class;
    }
}
