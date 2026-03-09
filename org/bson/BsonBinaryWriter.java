package org.bson;

import java.util.List;
import java.util.Stack;
import org.bson.AbstractBsonReader;
import org.bson.AbstractBsonWriter;
import org.bson.assertions.Assertions;
import org.bson.io.BsonInput;
import org.bson.io.BsonOutput;
import org.bson.types.Decimal128;
import org.bson.types.ObjectId;

/* JADX INFO: loaded from: classes4.dex */
public class BsonBinaryWriter extends AbstractBsonWriter {
    private final BsonBinaryWriterSettings binaryWriterSettings;
    private final BsonOutput bsonOutput;
    private Mark mark;
    private final Stack<Integer> maxDocumentSizeStack;

    @Override // org.bson.BsonWriter
    public void flush() {
    }

    public BsonBinaryWriter(BsonOutput bsonOutput, FieldNameValidator fieldNameValidator) {
        this(new BsonWriterSettings(), new BsonBinaryWriterSettings(), bsonOutput, fieldNameValidator);
    }

    public BsonBinaryWriter(BsonOutput bsonOutput) {
        this(new BsonWriterSettings(), new BsonBinaryWriterSettings(), bsonOutput);
    }

    public BsonBinaryWriter(BsonWriterSettings bsonWriterSettings, BsonBinaryWriterSettings bsonBinaryWriterSettings, BsonOutput bsonOutput) {
        this(bsonWriterSettings, bsonBinaryWriterSettings, bsonOutput, new NoOpFieldNameValidator());
    }

    public BsonBinaryWriter(BsonWriterSettings bsonWriterSettings, BsonBinaryWriterSettings bsonBinaryWriterSettings, BsonOutput bsonOutput, FieldNameValidator fieldNameValidator) {
        super(bsonWriterSettings, fieldNameValidator);
        Stack<Integer> stack = new Stack<>();
        this.maxDocumentSizeStack = stack;
        this.binaryWriterSettings = bsonBinaryWriterSettings;
        this.bsonOutput = bsonOutput;
        stack.push(Integer.valueOf(bsonBinaryWriterSettings.getMaxDocumentSize()));
    }

    @Override // org.bson.AbstractBsonWriter, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        super.close();
    }

    public BsonOutput getBsonOutput() {
        return this.bsonOutput;
    }

    public BsonBinaryWriterSettings getBinaryWriterSettings() {
        return this.binaryWriterSettings;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.bson.AbstractBsonWriter
    public Context getContext() {
        return (Context) super.getContext();
    }

    @Override // org.bson.AbstractBsonWriter
    protected void doWriteStartDocument() {
        if (getState() == AbstractBsonWriter.State.VALUE) {
            this.bsonOutput.writeByte(BsonType.DOCUMENT.getValue());
            writeCurrentName();
        }
        setContext(new Context(getContext(), BsonContextType.DOCUMENT, this.bsonOutput.getPosition()));
        this.bsonOutput.writeInt32(0);
    }

    @Override // org.bson.AbstractBsonWriter
    protected void doWriteEndDocument() {
        this.bsonOutput.writeByte(0);
        backpatchSize();
        setContext(getContext().getParentContext());
        if (getContext() == null || getContext().getContextType() != BsonContextType.JAVASCRIPT_WITH_SCOPE) {
            return;
        }
        backpatchSize();
        setContext(getContext().getParentContext());
    }

    @Override // org.bson.AbstractBsonWriter
    protected void doWriteStartArray() {
        this.bsonOutput.writeByte(BsonType.ARRAY.getValue());
        writeCurrentName();
        setContext(new Context(getContext(), BsonContextType.ARRAY, this.bsonOutput.getPosition()));
        this.bsonOutput.writeInt32(0);
    }

    @Override // org.bson.AbstractBsonWriter
    protected void doWriteEndArray() {
        this.bsonOutput.writeByte(0);
        backpatchSize();
        setContext(getContext().getParentContext());
    }

    @Override // org.bson.AbstractBsonWriter
    protected void doWriteBinaryData(BsonBinary bsonBinary) {
        this.bsonOutput.writeByte(BsonType.BINARY.getValue());
        writeCurrentName();
        int length = bsonBinary.getData().length;
        if (bsonBinary.getType() == BsonBinarySubType.OLD_BINARY.getValue()) {
            length += 4;
        }
        this.bsonOutput.writeInt32(length);
        this.bsonOutput.writeByte(bsonBinary.getType());
        if (bsonBinary.getType() == BsonBinarySubType.OLD_BINARY.getValue()) {
            this.bsonOutput.writeInt32(length - 4);
        }
        this.bsonOutput.writeBytes(bsonBinary.getData());
    }

    @Override // org.bson.AbstractBsonWriter
    public void doWriteBoolean(boolean z) {
        this.bsonOutput.writeByte(BsonType.BOOLEAN.getValue());
        writeCurrentName();
        this.bsonOutput.writeByte(z ? 1 : 0);
    }

    @Override // org.bson.AbstractBsonWriter
    protected void doWriteDateTime(long j) {
        this.bsonOutput.writeByte(BsonType.DATE_TIME.getValue());
        writeCurrentName();
        this.bsonOutput.writeInt64(j);
    }

    @Override // org.bson.AbstractBsonWriter
    protected void doWriteDBPointer(BsonDbPointer bsonDbPointer) {
        this.bsonOutput.writeByte(BsonType.DB_POINTER.getValue());
        writeCurrentName();
        this.bsonOutput.writeString(bsonDbPointer.getNamespace());
        this.bsonOutput.writeBytes(bsonDbPointer.getId().toByteArray());
    }

    @Override // org.bson.AbstractBsonWriter
    protected void doWriteDouble(double d) {
        this.bsonOutput.writeByte(BsonType.DOUBLE.getValue());
        writeCurrentName();
        this.bsonOutput.writeDouble(d);
    }

    @Override // org.bson.AbstractBsonWriter
    protected void doWriteInt32(int i) {
        this.bsonOutput.writeByte(BsonType.INT32.getValue());
        writeCurrentName();
        this.bsonOutput.writeInt32(i);
    }

    @Override // org.bson.AbstractBsonWriter
    protected void doWriteInt64(long j) {
        this.bsonOutput.writeByte(BsonType.INT64.getValue());
        writeCurrentName();
        this.bsonOutput.writeInt64(j);
    }

    @Override // org.bson.AbstractBsonWriter
    protected void doWriteDecimal128(Decimal128 decimal128) {
        this.bsonOutput.writeByte(BsonType.DECIMAL128.getValue());
        writeCurrentName();
        this.bsonOutput.writeInt64(decimal128.getLow());
        this.bsonOutput.writeInt64(decimal128.getHigh());
    }

    @Override // org.bson.AbstractBsonWriter
    protected void doWriteJavaScript(String str) {
        this.bsonOutput.writeByte(BsonType.JAVASCRIPT.getValue());
        writeCurrentName();
        this.bsonOutput.writeString(str);
    }

    @Override // org.bson.AbstractBsonWriter
    protected void doWriteJavaScriptWithScope(String str) {
        this.bsonOutput.writeByte(BsonType.JAVASCRIPT_WITH_SCOPE.getValue());
        writeCurrentName();
        setContext(new Context(getContext(), BsonContextType.JAVASCRIPT_WITH_SCOPE, this.bsonOutput.getPosition()));
        this.bsonOutput.writeInt32(0);
        this.bsonOutput.writeString(str);
    }

    @Override // org.bson.AbstractBsonWriter
    protected void doWriteMaxKey() {
        this.bsonOutput.writeByte(BsonType.MAX_KEY.getValue());
        writeCurrentName();
    }

    @Override // org.bson.AbstractBsonWriter
    protected void doWriteMinKey() {
        this.bsonOutput.writeByte(BsonType.MIN_KEY.getValue());
        writeCurrentName();
    }

    @Override // org.bson.AbstractBsonWriter
    public void doWriteNull() {
        this.bsonOutput.writeByte(BsonType.NULL.getValue());
        writeCurrentName();
    }

    @Override // org.bson.AbstractBsonWriter
    public void doWriteObjectId(ObjectId objectId) {
        this.bsonOutput.writeByte(BsonType.OBJECT_ID.getValue());
        writeCurrentName();
        this.bsonOutput.writeBytes(objectId.toByteArray());
    }

    @Override // org.bson.AbstractBsonWriter
    public void doWriteRegularExpression(BsonRegularExpression bsonRegularExpression) {
        this.bsonOutput.writeByte(BsonType.REGULAR_EXPRESSION.getValue());
        writeCurrentName();
        this.bsonOutput.writeCString(bsonRegularExpression.getPattern());
        this.bsonOutput.writeCString(bsonRegularExpression.getOptions());
    }

    @Override // org.bson.AbstractBsonWriter
    public void doWriteString(String str) {
        this.bsonOutput.writeByte(BsonType.STRING.getValue());
        writeCurrentName();
        this.bsonOutput.writeString(str);
    }

    @Override // org.bson.AbstractBsonWriter
    public void doWriteSymbol(String str) {
        this.bsonOutput.writeByte(BsonType.SYMBOL.getValue());
        writeCurrentName();
        this.bsonOutput.writeString(str);
    }

    @Override // org.bson.AbstractBsonWriter
    public void doWriteTimestamp(BsonTimestamp bsonTimestamp) {
        this.bsonOutput.writeByte(BsonType.TIMESTAMP.getValue());
        writeCurrentName();
        this.bsonOutput.writeInt64(bsonTimestamp.getValue());
    }

    @Override // org.bson.AbstractBsonWriter
    public void doWriteUndefined() {
        this.bsonOutput.writeByte(BsonType.UNDEFINED.getValue());
        writeCurrentName();
    }

    @Override // org.bson.AbstractBsonWriter, org.bson.BsonWriter
    public void pipe(BsonReader bsonReader) {
        Assertions.notNull("reader", bsonReader);
        pipeDocument(bsonReader, null);
    }

    @Override // org.bson.AbstractBsonWriter
    public void pipe(BsonReader bsonReader, List<BsonElement> list) {
        Assertions.notNull("reader", bsonReader);
        Assertions.notNull("extraElements", list);
        pipeDocument(bsonReader, list);
    }

    private void pipeDocument(BsonReader bsonReader, List<BsonElement> list) {
        if (!(bsonReader instanceof BsonBinaryReader)) {
            if (list != null) {
                super.pipe(bsonReader, list);
                return;
            } else {
                super.pipe(bsonReader);
                return;
            }
        }
        BsonBinaryReader bsonBinaryReader = (BsonBinaryReader) bsonReader;
        if (getState() == AbstractBsonWriter.State.VALUE) {
            this.bsonOutput.writeByte(BsonType.DOCUMENT.getValue());
            writeCurrentName();
        }
        BsonInput bsonInput = bsonBinaryReader.getBsonInput();
        int int32 = bsonInput.readInt32();
        if (int32 < 5) {
            throw new BsonSerializationException("Document size must be at least 5");
        }
        int position = this.bsonOutput.getPosition();
        this.bsonOutput.writeInt32(int32);
        byte[] bArr = new byte[int32 - 4];
        bsonInput.readBytes(bArr);
        this.bsonOutput.writeBytes(bArr);
        bsonBinaryReader.setState(AbstractBsonReader.State.TYPE);
        if (list != null) {
            this.bsonOutput.truncateToPosition(r5.getPosition() - 1);
            setContext(new Context(getContext(), BsonContextType.DOCUMENT, position));
            setState(AbstractBsonWriter.State.NAME);
            pipeExtraElements(list);
            this.bsonOutput.writeByte(0);
            BsonOutput bsonOutput = this.bsonOutput;
            bsonOutput.writeInt32(position, bsonOutput.getPosition() - position);
            setContext(getContext().getParentContext());
        }
        if (getContext() == null) {
            setState(AbstractBsonWriter.State.DONE);
        } else {
            if (getContext().getContextType() == BsonContextType.JAVASCRIPT_WITH_SCOPE) {
                backpatchSize();
                setContext(getContext().getParentContext());
            }
            setState(getNextState());
        }
        validateSize(this.bsonOutput.getPosition() - position);
    }

    public void pushMaxDocumentSize(int i) {
        this.maxDocumentSizeStack.push(Integer.valueOf(i));
    }

    public void popMaxDocumentSize() {
        this.maxDocumentSizeStack.pop();
    }

    public void mark() {
        this.mark = new Mark();
    }

    public void reset() {
        Mark mark = this.mark;
        if (mark == null) {
            throw new IllegalStateException("Can not reset without first marking");
        }
        mark.reset();
        this.mark = null;
    }

    private void writeCurrentName() {
        if (getContext().getContextType() == BsonContextType.ARRAY) {
            this.bsonOutput.writeCString(Integer.toString(Context.access$008(getContext())));
        } else {
            this.bsonOutput.writeCString(getName());
        }
    }

    private void backpatchSize() {
        int position = this.bsonOutput.getPosition() - getContext().startPosition;
        validateSize(position);
        BsonOutput bsonOutput = this.bsonOutput;
        bsonOutput.writeInt32(bsonOutput.getPosition() - position, position);
    }

    private void validateSize(int i) {
        if (i > this.maxDocumentSizeStack.peek().intValue()) {
            throw new BsonMaximumSizeExceededException(String.format("Document size of %d is larger than maximum of %d.", Integer.valueOf(i), this.maxDocumentSizeStack.peek()));
        }
    }

    protected class Context extends AbstractBsonWriter.Context {
        private int index;
        private final int startPosition;

        static /* synthetic */ int access$008(Context context) {
            int i = context.index;
            context.index = i + 1;
            return i;
        }

        public Context(Context context, BsonContextType bsonContextType, int i) {
            super(context, bsonContextType);
            this.startPosition = i;
        }

        public Context(Context context) {
            super(context);
            this.startPosition = context.startPosition;
            this.index = context.index;
        }

        @Override // org.bson.AbstractBsonWriter.Context
        public Context getParentContext() {
            return (Context) super.getParentContext();
        }

        @Override // org.bson.AbstractBsonWriter.Context
        public Context copy() {
            return BsonBinaryWriter.this.new Context(this);
        }
    }

    protected class Mark extends AbstractBsonWriter.Mark {
        private final int position;

        protected Mark() {
            super();
            this.position = BsonBinaryWriter.this.bsonOutput.getPosition();
        }

        @Override // org.bson.AbstractBsonWriter.Mark
        protected void reset() {
            super.reset();
            BsonBinaryWriter.this.bsonOutput.truncateToPosition(BsonBinaryWriter.this.mark.position);
        }
    }
}
