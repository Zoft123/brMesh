package org.bson.internal;

import org.bson.UuidRepresentation;
import org.bson.codecs.configuration.CodecConfigurationException;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;

/* JADX INFO: loaded from: classes4.dex */
public final class CodecRegistryHelper {
    public static CodecRegistry createRegistry(CodecRegistry codecRegistry, UuidRepresentation uuidRepresentation) {
        if (uuidRepresentation == UuidRepresentation.JAVA_LEGACY) {
            return codecRegistry;
        }
        if (codecRegistry instanceof CodecProvider) {
            return new OverridableUuidRepresentationCodecRegistry((CodecProvider) codecRegistry, uuidRepresentation);
        }
        throw new CodecConfigurationException("Changing the default UuidRepresentation requires a CodecRegistry that also implements the CodecProvider interface");
    }

    private CodecRegistryHelper() {
    }
}
