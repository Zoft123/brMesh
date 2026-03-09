package org.bson.codecs;

import org.bson.BsonReader;

/* JADX INFO: loaded from: classes4.dex */
public final class DecoderContext {
    private static final DecoderContext DEFAULT_CONTEXT = builder().build();
    private final boolean checkedDiscriminator;

    public boolean hasCheckedDiscriminator() {
        return this.checkedDiscriminator;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private boolean checkedDiscriminator;

        private Builder() {
        }

        public boolean hasCheckedDiscriminator() {
            return this.checkedDiscriminator;
        }

        public Builder checkedDiscriminator(boolean z) {
            this.checkedDiscriminator = z;
            return this;
        }

        public DecoderContext build() {
            return new DecoderContext(this);
        }
    }

    public <T> T decodeWithChildContext(Decoder<T> decoder, BsonReader bsonReader) {
        return decoder.decode(bsonReader, DEFAULT_CONTEXT);
    }

    private DecoderContext(Builder builder) {
        this.checkedDiscriminator = builder.hasCheckedDiscriminator();
    }
}
