package org.bson.codecs;

import org.bson.UuidRepresentation;

/* JADX INFO: loaded from: classes4.dex */
public interface OverridableUuidRepresentationCodec<T> {
    Codec<T> withUuidRepresentation(UuidRepresentation uuidRepresentation);
}
