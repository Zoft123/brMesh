package io.realm.exceptions;

/* JADX INFO: loaded from: classes4.dex */
public final class RealmMigrationNeededException extends RuntimeException {
    private final String canonicalRealmPath;

    public RealmMigrationNeededException(String str, String str2) {
        super(str2);
        this.canonicalRealmPath = str;
    }

    public String getPath() {
        return this.canonicalRealmPath;
    }
}
