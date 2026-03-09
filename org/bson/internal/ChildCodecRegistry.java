package org.bson.internal;

import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecRegistry;

/* JADX INFO: loaded from: classes4.dex */
class ChildCodecRegistry<T> implements CodecRegistry {
    private final Class<T> codecClass;
    private final ChildCodecRegistry<?> parent;
    private final CycleDetectingCodecRegistry registry;

    ChildCodecRegistry(CycleDetectingCodecRegistry cycleDetectingCodecRegistry, Class<T> cls) {
        this.codecClass = cls;
        this.parent = null;
        this.registry = cycleDetectingCodecRegistry;
    }

    private ChildCodecRegistry(ChildCodecRegistry<?> childCodecRegistry, Class<T> cls) {
        this.parent = childCodecRegistry;
        this.codecClass = cls;
        this.registry = childCodecRegistry.registry;
    }

    public Class<T> getCodecClass() {
        return this.codecClass;
    }

    @Override // org.bson.codecs.configuration.CodecRegistry
    public <U> Codec<U> get(Class<U> cls) {
        if (hasCycles(cls).booleanValue()) {
            return new LazyCodec(this.registry, cls);
        }
        return this.registry.get(new ChildCodecRegistry<>((ChildCodecRegistry<?>) this, (Class) cls));
    }

    private <U> Boolean hasCycles(Class<U> cls) {
        for (ChildCodecRegistry childCodecRegistry = this; childCodecRegistry != null; childCodecRegistry = childCodecRegistry.parent) {
            if (childCodecRegistry.codecClass.equals(cls)) {
                return true;
            }
        }
        return false;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ChildCodecRegistry childCodecRegistry = (ChildCodecRegistry) obj;
        if (!this.codecClass.equals(childCodecRegistry.codecClass)) {
            return false;
        }
        ChildCodecRegistry<?> childCodecRegistry2 = this.parent;
        if (childCodecRegistry2 == null ? childCodecRegistry.parent == null : childCodecRegistry2.equals(childCodecRegistry.parent)) {
            return this.registry.equals(childCodecRegistry.registry);
        }
        return false;
    }

    public int hashCode() {
        ChildCodecRegistry<?> childCodecRegistry = this.parent;
        return ((((childCodecRegistry != null ? childCodecRegistry.hashCode() : 0) * 31) + this.registry.hashCode()) * 31) + this.codecClass.hashCode();
    }
}
