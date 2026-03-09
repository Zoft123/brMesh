package org.bson.json;

import cn.com.broadlink.blelight.util.EListUtils;
import com.brgd.brblmesh.GeneralClass.GlobalVariable;
import java.io.IOException;
import java.io.Writer;
import org.bson.BSONException;
import org.bson.BsonInvalidOperationException;
import org.bson.assertions.Assertions;

/* JADX INFO: loaded from: classes4.dex */
public final class StrictCharacterStreamJsonWriter implements StrictJsonWriter {
    private int curLength;
    private boolean isTruncated;
    private final StrictCharacterStreamJsonWriterSettings settings;
    private final Writer writer;
    private StrictJsonContext context = new StrictJsonContext(null, JsonContextType.TOP_LEVEL, "");
    private State state = State.INITIAL;

    private enum JsonContextType {
        TOP_LEVEL,
        DOCUMENT,
        ARRAY
    }

    private enum State {
        INITIAL,
        NAME,
        VALUE,
        DONE
    }

    private static class StrictJsonContext {
        private final JsonContextType contextType;
        private boolean hasElements;
        private final String indentation;
        private final StrictJsonContext parentContext;

        StrictJsonContext(StrictJsonContext strictJsonContext, JsonContextType jsonContextType, String str) {
            this.parentContext = strictJsonContext;
            this.contextType = jsonContextType;
            if (strictJsonContext != null) {
                str = strictJsonContext.indentation + str;
            }
            this.indentation = str;
        }
    }

    public StrictCharacterStreamJsonWriter(Writer writer, StrictCharacterStreamJsonWriterSettings strictCharacterStreamJsonWriterSettings) {
        this.writer = writer;
        this.settings = strictCharacterStreamJsonWriterSettings;
    }

    public int getCurrentLength() {
        return this.curLength;
    }

    @Override // org.bson.json.StrictJsonWriter
    public void writeStartObject(String str) {
        writeName(str);
        writeStartObject();
    }

    @Override // org.bson.json.StrictJsonWriter
    public void writeStartArray(String str) {
        writeName(str);
        writeStartArray();
    }

    @Override // org.bson.json.StrictJsonWriter
    public void writeBoolean(String str, boolean z) {
        Assertions.notNull(GlobalVariable.NAME, str);
        writeName(str);
        writeBoolean(z);
    }

    @Override // org.bson.json.StrictJsonWriter
    public void writeNumber(String str, String str2) {
        Assertions.notNull(GlobalVariable.NAME, str);
        Assertions.notNull("value", str2);
        writeName(str);
        writeNumber(str2);
    }

    @Override // org.bson.json.StrictJsonWriter
    public void writeString(String str, String str2) {
        Assertions.notNull(GlobalVariable.NAME, str);
        Assertions.notNull("value", str2);
        writeName(str);
        writeString(str2);
    }

    @Override // org.bson.json.StrictJsonWriter
    public void writeRaw(String str, String str2) {
        Assertions.notNull(GlobalVariable.NAME, str);
        Assertions.notNull("value", str2);
        writeName(str);
        writeRaw(str2);
    }

    @Override // org.bson.json.StrictJsonWriter
    public void writeNull(String str) {
        writeName(str);
        writeNull();
    }

    @Override // org.bson.json.StrictJsonWriter
    public void writeName(String str) {
        Assertions.notNull(GlobalVariable.NAME, str);
        checkState(State.NAME);
        if (this.context.hasElements) {
            write(EListUtils.DEFAULT_JOIN_SEPARATOR);
        }
        if (!this.settings.isIndent()) {
            if (this.context.hasElements) {
                write(" ");
            }
        } else {
            write(this.settings.getNewLineCharacters());
            write(this.context.indentation);
        }
        writeStringHelper(str);
        write(": ");
        this.state = State.VALUE;
    }

    @Override // org.bson.json.StrictJsonWriter
    public void writeBoolean(boolean z) {
        checkState(State.VALUE);
        preWriteValue();
        write(z ? "true" : "false");
        setNextState();
    }

    @Override // org.bson.json.StrictJsonWriter
    public void writeNumber(String str) {
        Assertions.notNull("value", str);
        checkState(State.VALUE);
        preWriteValue();
        write(str);
        setNextState();
    }

    @Override // org.bson.json.StrictJsonWriter
    public void writeString(String str) {
        Assertions.notNull("value", str);
        checkState(State.VALUE);
        preWriteValue();
        writeStringHelper(str);
        setNextState();
    }

    @Override // org.bson.json.StrictJsonWriter
    public void writeRaw(String str) {
        Assertions.notNull("value", str);
        checkState(State.VALUE);
        preWriteValue();
        write(str);
        setNextState();
    }

    @Override // org.bson.json.StrictJsonWriter
    public void writeNull() {
        checkState(State.VALUE);
        preWriteValue();
        write(GlobalVariable.nullColor);
        setNextState();
    }

    @Override // org.bson.json.StrictJsonWriter
    public void writeStartObject() {
        if (this.state != State.INITIAL && this.state != State.VALUE) {
            throw new BsonInvalidOperationException("Invalid state " + this.state);
        }
        preWriteValue();
        write("{");
        this.context = new StrictJsonContext(this.context, JsonContextType.DOCUMENT, this.settings.getIndentCharacters());
        this.state = State.NAME;
    }

    @Override // org.bson.json.StrictJsonWriter
    public void writeStartArray() {
        preWriteValue();
        write("[");
        this.context = new StrictJsonContext(this.context, JsonContextType.ARRAY, this.settings.getIndentCharacters());
        this.state = State.VALUE;
    }

    @Override // org.bson.json.StrictJsonWriter
    public void writeEndObject() {
        checkState(State.NAME);
        if (this.settings.isIndent() && this.context.hasElements) {
            write(this.settings.getNewLineCharacters());
            write(this.context.parentContext.indentation);
        }
        write("}");
        StrictJsonContext strictJsonContext = this.context.parentContext;
        this.context = strictJsonContext;
        if (strictJsonContext.contextType == JsonContextType.TOP_LEVEL) {
            this.state = State.DONE;
        } else {
            setNextState();
        }
    }

    @Override // org.bson.json.StrictJsonWriter
    public void writeEndArray() {
        checkState(State.VALUE);
        if (this.context.contextType != JsonContextType.ARRAY) {
            throw new BsonInvalidOperationException("Can't end an array if not in an array");
        }
        if (this.settings.isIndent() && this.context.hasElements) {
            write(this.settings.getNewLineCharacters());
            write(this.context.parentContext.indentation);
        }
        write("]");
        StrictJsonContext strictJsonContext = this.context.parentContext;
        this.context = strictJsonContext;
        if (strictJsonContext.contextType == JsonContextType.TOP_LEVEL) {
            this.state = State.DONE;
        } else {
            setNextState();
        }
    }

    @Override // org.bson.json.StrictJsonWriter
    public boolean isTruncated() {
        return this.isTruncated;
    }

    void flush() {
        try {
            this.writer.flush();
        } catch (IOException e) {
            throwBSONException(e);
        }
    }

    Writer getWriter() {
        return this.writer;
    }

    private void preWriteValue() {
        if (this.context.contextType == JsonContextType.ARRAY) {
            if (this.context.hasElements) {
                write(EListUtils.DEFAULT_JOIN_SEPARATOR);
            }
            if (!this.settings.isIndent()) {
                if (this.context.hasElements) {
                    write(" ");
                }
            } else {
                write(this.settings.getNewLineCharacters());
                write(this.context.indentation);
            }
        }
        this.context.hasElements = true;
    }

    private void setNextState() {
        if (this.context.contextType == JsonContextType.ARRAY) {
            this.state = State.VALUE;
        } else {
            this.state = State.NAME;
        }
    }

    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Failed to find switch 'out' block (already processed)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.calcSwitchOut(SwitchRegionMaker.java:217)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.process(SwitchRegionMaker.java:68)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:112)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:102)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:106)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.addCases(SwitchRegionMaker.java:123)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.process(SwitchRegionMaker.java:71)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:112)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:96)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:106)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:96)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:106)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:96)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:106)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:96)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:106)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.process(LoopRegionMaker.java:125)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:89)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeMthRegion(RegionMaker.java:48)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:25)
        */
    private void writeStringHelper(java.lang.String r7) {
        /*
            Method dump skipped, instruction units count: 206
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bson.json.StrictCharacterStreamJsonWriter.writeStringHelper(java.lang.String):void");
    }

    private void write(String str) {
        try {
            if (this.settings.getMaxLength() != 0 && str.length() + this.curLength >= this.settings.getMaxLength()) {
                this.writer.write(str.substring(0, this.settings.getMaxLength() - this.curLength));
                this.curLength = this.settings.getMaxLength();
                this.isTruncated = true;
                return;
            }
            this.writer.write(str);
            this.curLength += str.length();
        } catch (IOException e) {
            throwBSONException(e);
        }
    }

    private void write(char c) {
        try {
            if (this.settings.getMaxLength() != 0 && this.curLength >= this.settings.getMaxLength()) {
                this.isTruncated = true;
                return;
            }
            this.writer.write(c);
            this.curLength++;
        } catch (IOException e) {
            throwBSONException(e);
        }
    }

    private void checkState(State state) {
        if (this.state == state) {
            return;
        }
        throw new BsonInvalidOperationException("Invalid state " + this.state);
    }

    private void throwBSONException(IOException iOException) {
        throw new BSONException("Wrapping IOException", iOException);
    }
}
