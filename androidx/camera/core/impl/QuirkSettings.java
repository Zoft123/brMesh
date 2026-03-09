package androidx.camera.core.impl;

import j$.util.Objects;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public class QuirkSettings {
    private final boolean mEnabledWhenDeviceHasQuirk;
    private final Set<Class<? extends Quirk>> mForceDisabledQuirks;
    private final Set<Class<? extends Quirk>> mForceEnabledQuirks;

    private QuirkSettings(boolean z, Set<Class<? extends Quirk>> set, Set<Class<? extends Quirk>> set2) {
        this.mEnabledWhenDeviceHasQuirk = z;
        this.mForceEnabledQuirks = set == null ? Collections.EMPTY_SET : new HashSet<>(set);
        this.mForceDisabledQuirks = set2 == null ? Collections.EMPTY_SET : new HashSet<>(set2);
    }

    public static QuirkSettings withDefaultBehavior() {
        return new Builder().setEnabledWhenDeviceHasQuirk(true).build();
    }

    public static QuirkSettings withAllQuirksDisabled() {
        return new Builder().setEnabledWhenDeviceHasQuirk(false).build();
    }

    public static QuirkSettings withQuirksForceEnabled(Set<Class<? extends Quirk>> set) {
        return new Builder().forceEnableQuirks(set).build();
    }

    public static QuirkSettings withQuirksForceDisabled(Set<Class<? extends Quirk>> set) {
        return new Builder().forceDisableQuirks(set).build();
    }

    public boolean isEnabledWhenDeviceHasQuirk() {
        return this.mEnabledWhenDeviceHasQuirk;
    }

    public Set<Class<? extends Quirk>> getForceEnabledQuirks() {
        return Collections.unmodifiableSet(this.mForceEnabledQuirks);
    }

    public Set<Class<? extends Quirk>> getForceDisabledQuirks() {
        return Collections.unmodifiableSet(this.mForceDisabledQuirks);
    }

    public boolean shouldEnableQuirk(Class<? extends Quirk> cls, boolean z) {
        if (this.mForceEnabledQuirks.contains(cls)) {
            return true;
        }
        return !this.mForceDisabledQuirks.contains(cls) && this.mEnabledWhenDeviceHasQuirk && z;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof QuirkSettings)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        QuirkSettings quirkSettings = (QuirkSettings) obj;
        return this.mEnabledWhenDeviceHasQuirk == quirkSettings.mEnabledWhenDeviceHasQuirk && Objects.equals(this.mForceEnabledQuirks, quirkSettings.mForceEnabledQuirks) && Objects.equals(this.mForceDisabledQuirks, quirkSettings.mForceDisabledQuirks);
    }

    public int hashCode() {
        return Objects.hash(Boolean.valueOf(this.mEnabledWhenDeviceHasQuirk), this.mForceEnabledQuirks, this.mForceDisabledQuirks);
    }

    public String toString() {
        return "QuirkSettings{enabledWhenDeviceHasQuirk=" + this.mEnabledWhenDeviceHasQuirk + ", forceEnabledQuirks=" + this.mForceEnabledQuirks + ", forceDisabledQuirks=" + this.mForceDisabledQuirks + '}';
    }

    public static class Builder {
        private boolean mEnabledWhenDeviceHasQuirk = true;
        private Set<Class<? extends Quirk>> mForceDisabledQuirks;
        private Set<Class<? extends Quirk>> mForceEnabledQuirks;

        public Builder setEnabledWhenDeviceHasQuirk(boolean z) {
            this.mEnabledWhenDeviceHasQuirk = z;
            return this;
        }

        public Builder forceEnableQuirks(Set<Class<? extends Quirk>> set) {
            this.mForceEnabledQuirks = new HashSet(set);
            return this;
        }

        public Builder forceDisableQuirks(Set<Class<? extends Quirk>> set) {
            this.mForceDisabledQuirks = new HashSet(set);
            return this;
        }

        public QuirkSettings build() {
            return new QuirkSettings(this.mEnabledWhenDeviceHasQuirk, this.mForceEnabledQuirks, this.mForceDisabledQuirks);
        }
    }
}
