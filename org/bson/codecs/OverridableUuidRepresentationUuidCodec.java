package org.bson.codecs;

import java.util.UUID;
import org.bson.UuidRepresentation;

/* JADX INFO: loaded from: classes4.dex */
public class OverridableUuidRepresentationUuidCodec extends UuidCodec implements OverridableUuidRepresentationCodec<UUID> {
    public OverridableUuidRepresentationUuidCodec() {
    }

    public OverridableUuidRepresentationUuidCodec(UuidRepresentation uuidRepresentation) {
        super(uuidRepresentation);
    }

    @Override // org.bson.codecs.OverridableUuidRepresentationCodec
    public Codec<UUID> withUuidRepresentation(UuidRepresentation uuidRepresentation) {
        return new OverridableUuidRepresentationUuidCodec(uuidRepresentation);
    }
}
