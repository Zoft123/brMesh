package org.bson.internal;

import org.bson.UuidRepresentation;
import org.bson.assertions.Assertions;
import org.bson.codecs.Codec;
import org.bson.codecs.OverridableUuidRepresentationCodec;
import org.bson.codecs.configuration.CodecProvider;

/* JADX INFO: loaded from: classes4.dex */
public class OverridableUuidRepresentationCodecRegistry implements CycleDetectingCodecRegistry {
    private final CodecCache codecCache = new CodecCache();
    private final UuidRepresentation uuidRepresentation;
    private final CodecProvider wrapped;

    OverridableUuidRepresentationCodecRegistry(CodecProvider codecProvider, UuidRepresentation uuidRepresentation) {
        this.uuidRepresentation = (UuidRepresentation) Assertions.notNull("uuidRepresentation", uuidRepresentation);
        this.wrapped = (CodecProvider) Assertions.notNull("wrapped", codecProvider);
    }

    public UuidRepresentation getUuidRepresentation() {
        return this.uuidRepresentation;
    }

    public CodecProvider getWrapped() {
        return this.wrapped;
    }

    @Override // org.bson.codecs.configuration.CodecRegistry
    public <T> Codec<T> get(Class<T> cls) {
        return get(new ChildCodecRegistry<>(this, cls));
    }

    @Override // org.bson.internal.CycleDetectingCodecRegistry
    public <T> Codec<T> get(ChildCodecRegistry<T> childCodecRegistry) {
        if (!this.codecCache.containsKey(childCodecRegistry.getCodecClass())) {
            Codec<T> codecWithUuidRepresentation = this.wrapped.get(childCodecRegistry.getCodecClass(), childCodecRegistry);
            if (codecWithUuidRepresentation instanceof OverridableUuidRepresentationCodec) {
                codecWithUuidRepresentation = ((OverridableUuidRepresentationCodec) codecWithUuidRepresentation).withUuidRepresentation(this.uuidRepresentation);
            }
            this.codecCache.put(childCodecRegistry.getCodecClass(), codecWithUuidRepresentation);
        }
        return this.codecCache.getOrThrow(childCodecRegistry.getCodecClass());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            OverridableUuidRepresentationCodecRegistry overridableUuidRepresentationCodecRegistry = (OverridableUuidRepresentationCodecRegistry) obj;
            if (this.wrapped.equals(overridableUuidRepresentationCodecRegistry.wrapped) && this.uuidRepresentation == overridableUuidRepresentationCodecRegistry.uuidRepresentation) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return (this.wrapped.hashCode() * 31) + this.uuidRepresentation.hashCode();
    }
}
