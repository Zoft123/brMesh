package org.bson.json;

import org.bson.assertions.Assertions;

/* JADX INFO: loaded from: classes4.dex */
public final class StrictCharacterStreamJsonWriterSettings {
    private final boolean indent;
    private final String indentCharacters;
    private final int maxLength;
    private final String newLineCharacters;

    public static Builder builder() {
        return new Builder();
    }

    private StrictCharacterStreamJsonWriterSettings(Builder builder) {
        this.indent = builder.indent;
        this.newLineCharacters = builder.newLineCharacters != null ? builder.newLineCharacters : System.getProperty("line.separator");
        this.indentCharacters = builder.indentCharacters;
        this.maxLength = builder.maxLength;
    }

    public boolean isIndent() {
        return this.indent;
    }

    public String getNewLineCharacters() {
        return this.newLineCharacters;
    }

    public String getIndentCharacters() {
        return this.indentCharacters;
    }

    public int getMaxLength() {
        return this.maxLength;
    }

    public static final class Builder {
        private boolean indent;
        private String indentCharacters;
        private int maxLength;
        private String newLineCharacters;

        public StrictCharacterStreamJsonWriterSettings build() {
            return new StrictCharacterStreamJsonWriterSettings(this);
        }

        public Builder indent(boolean z) {
            this.indent = z;
            return this;
        }

        public Builder newLineCharacters(String str) {
            Assertions.notNull("newLineCharacters", str);
            this.newLineCharacters = str;
            return this;
        }

        public Builder indentCharacters(String str) {
            Assertions.notNull("indentCharacters", str);
            this.indentCharacters = str;
            return this;
        }

        public Builder maxLength(int i) {
            this.maxLength = i;
            return this;
        }

        private Builder() {
            this.newLineCharacters = System.getProperty("line.separator");
            this.indentCharacters = "  ";
        }
    }
}
