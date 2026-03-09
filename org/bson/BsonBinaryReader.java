package org.bson;

import java.nio.ByteBuffer;
import org.bson.AbstractBsonReader;
import org.bson.assertions.Assertions;
import org.bson.io.BsonInput;
import org.bson.io.BsonInputMark;
import org.bson.io.ByteBufferBsonInput;
import org.bson.types.Decimal128;
import org.bson.types.ObjectId;

/* JADX INFO: loaded from: classes4.dex */
public class BsonBinaryReader extends AbstractBsonReader {
    private final BsonInput bsonInput;
    private Mark mark;

    @Override // org.bson.AbstractBsonReader
    protected void doReadMaxKey() {
    }

    @Override // org.bson.AbstractBsonReader
    protected void doReadMinKey() {
    }

    @Override // org.bson.AbstractBsonReader
    protected void doReadNull() {
    }

    @Override // org.bson.AbstractBsonReader
    protected void doReadUndefined() {
    }

    @Override // org.bson.AbstractBsonReader
    protected void doSkipName() {
    }

    public BsonBinaryReader(ByteBuffer byteBuffer) {
        this(new ByteBufferBsonInput(new ByteBufNIO((ByteBuffer) Assertions.notNull("byteBuffer", byteBuffer))));
    }

    public BsonBinaryReader(BsonInput bsonInput) {
        if (bsonInput == null) {
            throw new IllegalArgumentException("bsonInput is null");
        }
        this.bsonInput = bsonInput;
        setContext(new Context(null, BsonContextType.TOP_LEVEL, 0, 0));
    }

    @Override // org.bson.AbstractBsonReader, org.bson.BsonReader, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        super.close();
    }

    public BsonInput getBsonInput() {
        return this.bsonInput;
    }

    @Override // org.bson.AbstractBsonReader, org.bson.BsonReader
    public BsonType readBsonType() {
        if (isClosed()) {
            throw new IllegalStateException("BSONBinaryWriter");
        }
        if (getState() == AbstractBsonReader.State.INITIAL || getState() == AbstractBsonReader.State.DONE || getState() == AbstractBsonReader.State.SCOPE_DOCUMENT) {
            setCurrentBsonType(BsonType.DOCUMENT);
            setState(AbstractBsonReader.State.VALUE);
            return getCurrentBsonType();
        }
        if (getState() != AbstractBsonReader.State.TYPE) {
            throwInvalidState("ReadBSONType", AbstractBsonReader.State.TYPE);
        }
        byte b = this.bsonInput.readByte();
        BsonType bsonTypeFindByValue = BsonType.findByValue(b);
        if (bsonTypeFindByValue == null) {
            throw new BsonSerializationException(String.format("Detected unknown BSON type \"\\x%x\" for fieldname \"%s\". Are you using the latest driver version?", Byte.valueOf(b), this.bsonInput.readCString()));
        }
        setCurrentBsonType(bsonTypeFindByValue);
        if (getCurrentBsonType() == BsonType.END_OF_DOCUMENT) {
            int i = AnonymousClass1.$SwitchMap$org$bson$BsonContextType[getContext().getContextType().ordinal()];
            if (i == 1) {
                setState(AbstractBsonReader.State.END_OF_ARRAY);
                return BsonType.END_OF_DOCUMENT;
            }
            if (i == 2 || i == 3) {
                setState(AbstractBsonReader.State.END_OF_DOCUMENT);
                return BsonType.END_OF_DOCUMENT;
            }
            throw new BsonSerializationException(String.format("BSONType EndOfDocument is not valid when ContextType is %s.", getContext().getContextType()));
        }
        int i2 = AnonymousClass1.$SwitchMap$org$bson$BsonContextType[getContext().getContextType().ordinal()];
        if (i2 == 1) {
            this.bsonInput.skipCString();
            setState(AbstractBsonReader.State.VALUE);
        } else if (i2 == 2 || i2 == 3) {
            setCurrentName(this.bsonInput.readCString());
            setState(AbstractBsonReader.State.NAME);
        } else {
            throw new BSONException("Unexpected ContextType.");
        }
        return getCurrentBsonType();
    }

    @Override // org.bson.AbstractBsonReader
    protected BsonBinary doReadBinaryData() {
        int size = readSize();
        byte b = this.bsonInput.readByte();
        if (b == BsonBinarySubType.OLD_BINARY.getValue()) {
            if (this.bsonInput.readInt32() != size - 4) {
                throw new BsonSerializationException("Binary sub type OldBinary has inconsistent sizes");
            }
            size -= 4;
        }
        byte[] bArr = new byte[size];
        this.bsonInput.readBytes(bArr);
        return new BsonBinary(b, bArr);
    }

    @Override // org.bson.AbstractBsonReader
    protected byte doPeekBinarySubType() {
        mark();
        readSize();
        byte b = this.bsonInput.readByte();
        reset();
        return b;
    }

    @Override // org.bson.AbstractBsonReader
    protected int doPeekBinarySize() {
        mark();
        int size = readSize();
        reset();
        return size;
    }

    @Override // org.bson.AbstractBsonReader
    protected boolean doReadBoolean() {
        byte b = this.bsonInput.readByte();
        if (b == 0 || b == 1) {
            return b == 1;
        }
        throw new BsonSerializationException(String.format("Expected a boolean value but found %d", Byte.valueOf(b)));
    }

    @Override // org.bson.AbstractBsonReader
    protected long doReadDateTime() {
        return this.bsonInput.readInt64();
    }

    @Override // org.bson.AbstractBsonReader
    protected double doReadDouble() {
        return this.bsonInput.readDouble();
    }

    @Override // org.bson.AbstractBsonReader
    protected int doReadInt32() {
        return this.bsonInput.readInt32();
    }

    @Override // org.bson.AbstractBsonReader
    protected long doReadInt64() {
        return this.bsonInput.readInt64();
    }

    @Override // org.bson.AbstractBsonReader
    public Decimal128 doReadDecimal128() {
        return Decimal128.fromIEEE754BIDEncoding(this.bsonInput.readInt64(), this.bsonInput.readInt64());
    }

    @Override // org.bson.AbstractBsonReader
    protected String doReadJavaScript() {
        return this.bsonInput.readString();
    }

    @Override // org.bson.AbstractBsonReader
    protected String doReadJavaScriptWithScope() {
        setContext(new Context(getContext(), BsonContextType.JAVASCRIPT_WITH_SCOPE, this.bsonInput.getPosition(), readSize()));
        return this.bsonInput.readString();
    }

    @Override // org.bson.AbstractBsonReader
    protected ObjectId doReadObjectId() {
        return this.bsonInput.readObjectId();
    }

    @Override // org.bson.AbstractBsonReader
    protected BsonRegularExpression doReadRegularExpression() {
        return new BsonRegularExpression(this.bsonInput.readCString(), this.bsonInput.readCString());
    }

    @Override // org.bson.AbstractBsonReader
    protected BsonDbPointer doReadDBPointer() {
        return new BsonDbPointer(this.bsonInput.readString(), this.bsonInput.readObjectId());
    }

    @Override // org.bson.AbstractBsonReader
    protected String doReadString() {
        return this.bsonInput.readString();
    }

    @Override // org.bson.AbstractBsonReader
    protected String doReadSymbol() {
        return this.bsonInput.readString();
    }

    @Override // org.bson.AbstractBsonReader
    protected BsonTimestamp doReadTimestamp() {
        return new BsonTimestamp(this.bsonInput.readInt64());
    }

    @Override // org.bson.AbstractBsonReader
    public void doReadStartArray() {
        setContext(new Context(getContext(), BsonContextType.ARRAY, this.bsonInput.getPosition(), readSize()));
    }

    @Override // org.bson.AbstractBsonReader
    protected void doReadStartDocument() {
        setContext(new Context(getContext(), getState() == AbstractBsonReader.State.SCOPE_DOCUMENT ? BsonContextType.SCOPE_DOCUMENT : BsonContextType.DOCUMENT, this.bsonInput.getPosition(), readSize()));
    }

    @Override // org.bson.AbstractBsonReader
    protected void doReadEndArray() {
        setContext(getContext().popContext(this.bsonInput.getPosition()));
    }

    @Override // org.bson.AbstractBsonReader
    protected void doReadEndDocument() {
        setContext(getContext().popContext(this.bsonInput.getPosition()));
        if (getContext().getContextType() == BsonContextType.JAVASCRIPT_WITH_SCOPE) {
            setContext(getContext().popContext(this.bsonInput.getPosition()));
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // org.bson.AbstractBsonReader
    protected void doSkipValue() {
        int size;
        if (isClosed()) {
            throw new IllegalStateException("BSONBinaryWriter");
        }
        int size2 = 1;
        if (getState() != AbstractBsonReader.State.VALUE) {
            throwInvalidState("skipValue", AbstractBsonReader.State.VALUE);
        }
        switch (AnonymousClass1.$SwitchMap$org$bson$BsonType[getCurrentBsonType().ordinal()]) {
            case 1:
                size = readSize();
                size2 = size - 4;
                this.bsonInput.skip(size2);
                setState(AbstractBsonReader.State.TYPE);
                return;
            case 2:
                size2 = 1 + readSize();
                this.bsonInput.skip(size2);
                setState(AbstractBsonReader.State.TYPE);
                return;
            case 3:
                this.bsonInput.skip(size2);
                setState(AbstractBsonReader.State.TYPE);
                return;
            case 4:
            case 6:
            case 8:
            case 19:
                size2 = 8;
                this.bsonInput.skip(size2);
                setState(AbstractBsonReader.State.TYPE);
                return;
            case 5:
                size = readSize();
                size2 = size - 4;
                this.bsonInput.skip(size2);
                setState(AbstractBsonReader.State.TYPE);
                return;
            case 7:
                size2 = 4;
                this.bsonInput.skip(size2);
                setState(AbstractBsonReader.State.TYPE);
                return;
            case 9:
                size2 = 16;
                this.bsonInput.skip(size2);
                setState(AbstractBsonReader.State.TYPE);
                return;
            case 10:
                size2 = readSize();
                this.bsonInput.skip(size2);
                setState(AbstractBsonReader.State.TYPE);
                return;
            case 11:
                size = readSize();
                size2 = size - 4;
                this.bsonInput.skip(size2);
                setState(AbstractBsonReader.State.TYPE);
                return;
            case 12:
            case 13:
            case 14:
            case 20:
                size2 = 0;
                this.bsonInput.skip(size2);
                setState(AbstractBsonReader.State.TYPE);
                return;
            case 15:
                size2 = 12;
                this.bsonInput.skip(size2);
                setState(AbstractBsonReader.State.TYPE);
                return;
            case 16:
                this.bsonInput.skipCString();
                this.bsonInput.skipCString();
                size2 = 0;
                this.bsonInput.skip(size2);
                setState(AbstractBsonReader.State.TYPE);
                return;
            case 17:
                size2 = readSize();
                this.bsonInput.skip(size2);
                setState(AbstractBsonReader.State.TYPE);
                return;
            case 18:
                size2 = readSize();
                this.bsonInput.skip(size2);
                setState(AbstractBsonReader.State.TYPE);
                return;
            case 21:
                size2 = readSize() + 12;
                this.bsonInput.skip(size2);
                setState(AbstractBsonReader.State.TYPE);
                return;
            default:
                throw new BSONException("Unexpected BSON type: " + getCurrentBsonType());
        }
    }

    /* JADX INFO: renamed from: org.bson.BsonBinaryReader$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$bson$BsonContextType;
        static final /* synthetic */ int[] $SwitchMap$org$bson$BsonType;

        static {
            int[] iArr = new int[BsonType.values().length];
            $SwitchMap$org$bson$BsonType = iArr;
            try {
                iArr[BsonType.ARRAY.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$bson$BsonType[BsonType.BINARY.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$bson$BsonType[BsonType.BOOLEAN.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$org$bson$BsonType[BsonType.DATE_TIME.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$org$bson$BsonType[BsonType.DOCUMENT.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$org$bson$BsonType[BsonType.DOUBLE.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$org$bson$BsonType[BsonType.INT32.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$org$bson$BsonType[BsonType.INT64.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$org$bson$BsonType[BsonType.DECIMAL128.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$org$bson$BsonType[BsonType.JAVASCRIPT.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$org$bson$BsonType[BsonType.JAVASCRIPT_WITH_SCOPE.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$org$bson$BsonType[BsonType.MAX_KEY.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$org$bson$BsonType[BsonType.MIN_KEY.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$org$bson$BsonType[BsonType.NULL.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                $SwitchMap$org$bson$BsonType[BsonType.OBJECT_ID.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                $SwitchMap$org$bson$BsonType[BsonType.REGULAR_EXPRESSION.ordinal()] = 16;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                $SwitchMap$org$bson$BsonType[BsonType.STRING.ordinal()] = 17;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                $SwitchMap$org$bson$BsonType[BsonType.SYMBOL.ordinal()] = 18;
            } catch (NoSuchFieldError unused18) {
            }
            try {
                $SwitchMap$org$bson$BsonType[BsonType.TIMESTAMP.ordinal()] = 19;
            } catch (NoSuchFieldError unused19) {
            }
            try {
                $SwitchMap$org$bson$BsonType[BsonType.UNDEFINED.ordinal()] = 20;
            } catch (NoSuchFieldError unused20) {
            }
            try {
                $SwitchMap$org$bson$BsonType[BsonType.DB_POINTER.ordinal()] = 21;
            } catch (NoSuchFieldError unused21) {
            }
            int[] iArr2 = new int[BsonContextType.values().length];
            $SwitchMap$org$bson$BsonContextType = iArr2;
            try {
                iArr2[BsonContextType.ARRAY.ordinal()] = 1;
            } catch (NoSuchFieldError unused22) {
            }
            try {
                $SwitchMap$org$bson$BsonContextType[BsonContextType.DOCUMENT.ordinal()] = 2;
            } catch (NoSuchFieldError unused23) {
            }
            try {
                $SwitchMap$org$bson$BsonContextType[BsonContextType.SCOPE_DOCUMENT.ordinal()] = 3;
            } catch (NoSuchFieldError unused24) {
            }
        }
    }

    private int readSize() {
        int int32 = this.bsonInput.readInt32();
        if (int32 >= 0) {
            return int32;
        }
        throw new BsonSerializationException(String.format("Size %s is not valid because it is negative.", Integer.valueOf(int32)));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.bson.AbstractBsonReader
    public Context getContext() {
        return (Context) super.getContext();
    }

    @Override // org.bson.BsonReader
    @Deprecated
    public void mark() {
        if (this.mark != null) {
            throw new BSONException("A mark already exists; it needs to be reset before creating a new one");
        }
        this.mark = new Mark();
    }

    @Override // org.bson.BsonReader
    public BsonReaderMark getMark() {
        return new Mark();
    }

    @Override // org.bson.BsonReader
    @Deprecated
    public void reset() {
        Mark mark = this.mark;
        if (mark == null) {
            throw new BSONException("trying to reset a mark before creating it");
        }
        mark.reset();
        this.mark = null;
    }

    protected class Mark extends AbstractBsonReader.Mark {
        private final BsonInputMark bsonInputMark;
        private final int size;
        private final int startPosition;

        protected Mark() {
            super();
            this.startPosition = BsonBinaryReader.this.getContext().startPosition;
            this.size = BsonBinaryReader.this.getContext().size;
            this.bsonInputMark = BsonBinaryReader.this.bsonInput.getMark(Integer.MAX_VALUE);
        }

        @Override // org.bson.AbstractBsonReader.Mark, org.bson.BsonReaderMark
        public void reset() {
            super.reset();
            this.bsonInputMark.reset();
            BsonBinaryReader.this.setContext(BsonBinaryReader.this.new Context((Context) getParentContext(), getContextType(), this.startPosition, this.size));
        }
    }

    protected class Context extends AbstractBsonReader.Context {
        private final int size;
        private final int startPosition;

        Context(Context context, BsonContextType bsonContextType, int i, int i2) {
            super(context, bsonContextType);
            this.startPosition = i;
            this.size = i2;
        }

        Context popContext(int i) {
            int i2 = i - this.startPosition;
            if (i2 != this.size) {
                throw new BsonSerializationException(String.format("Expected size to be %d, not %d.", Integer.valueOf(this.size), Integer.valueOf(i2)));
            }
            return getParentContext();
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // org.bson.AbstractBsonReader.Context
        public Context getParentContext() {
            return (Context) super.getParentContext();
        }
    }
}
