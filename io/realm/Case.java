package io.realm;

/* JADX INFO: loaded from: classes4.dex */
public enum Case {
    SENSITIVE(true),
    INSENSITIVE(false);

    private final boolean value;

    Case(boolean z) {
        this.value = z;
    }

    public boolean getValue() {
        return this.value;
    }
}
