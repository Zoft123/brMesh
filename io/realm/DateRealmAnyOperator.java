package io.realm;

import io.realm.RealmAny;
import io.realm.internal.core.NativeRealmAny;
import java.util.Date;

/* JADX INFO: compiled from: RealmAnyOperator.java */
/* JADX INFO: loaded from: classes4.dex */
final class DateRealmAnyOperator extends PrimitiveRealmAnyOperator {
    DateRealmAnyOperator(Date date) {
        super(date, RealmAny.Type.DATE);
    }

    DateRealmAnyOperator(NativeRealmAny nativeRealmAny) {
        super(nativeRealmAny.asDate(), RealmAny.Type.DATE, nativeRealmAny);
    }

    @Override // io.realm.RealmAnyOperator
    protected NativeRealmAny createNativeRealmAny() {
        return new NativeRealmAny((Date) super.getValue(Date.class));
    }
}
