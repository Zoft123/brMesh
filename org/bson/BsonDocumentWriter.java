package org.bson;

import org.bson.AbstractBsonWriter;
import org.bson.types.Decimal128;
import org.bson.types.ObjectId;

/* JADX INFO: loaded from: classes4.dex */
public class BsonDocumentWriter extends AbstractBsonWriter {
    private final BsonDocument document;

    @Override // org.bson.BsonWriter
    public void flush() {
    }

    public BsonDocumentWriter(BsonDocument bsonDocument) {
        super(new BsonWriterSettings());
        this.document = bsonDocument;
        setContext(new Context());
    }

    public BsonDocument getDocument() {
        return this.document;
    }

    /* JADX INFO: renamed from: org.bson.BsonDocumentWriter$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$bson$AbstractBsonWriter$State;

        static {
            int[] iArr = new int[AbstractBsonWriter.State.values().length];
            $SwitchMap$org$bson$AbstractBsonWriter$State = iArr;
            try {
                iArr[AbstractBsonWriter.State.INITIAL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$bson$AbstractBsonWriter$State[AbstractBsonWriter.State.VALUE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$bson$AbstractBsonWriter$State[AbstractBsonWriter.State.SCOPE_DOCUMENT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    @Override // org.bson.AbstractBsonWriter
    protected void doWriteStartDocument() {
        int i = AnonymousClass1.$SwitchMap$org$bson$AbstractBsonWriter$State[getState().ordinal()];
        if (i == 1) {
            setContext(new Context(this.document, BsonContextType.DOCUMENT, getContext()));
            return;
        }
        if (i == 2) {
            setContext(new Context(new BsonDocument(), BsonContextType.DOCUMENT, getContext()));
        } else if (i == 3) {
            setContext(new Context(new BsonDocument(), BsonContextType.SCOPE_DOCUMENT, getContext()));
        } else {
            throw new BsonInvalidOperationException("Unexpected state " + getState());
        }
    }

    @Override // org.bson.AbstractBsonWriter
    protected void doWriteEndDocument() {
        BsonValue bsonValue = getContext().container;
        setContext(getContext().getParentContext());
        if (getContext().getContextType() != BsonContextType.JAVASCRIPT_WITH_SCOPE) {
            if (getContext().getContextType() != BsonContextType.TOP_LEVEL) {
                write(bsonValue);
            }
        } else {
            BsonString bsonString = (BsonString) getContext().container;
            setContext(getContext().getParentContext());
            write(new BsonJavaScriptWithScope(bsonString.getValue(), (BsonDocument) bsonValue));
        }
    }

    @Override // org.bson.AbstractBsonWriter
    protected void doWriteStartArray() {
        setContext(new Context(new BsonArray(), BsonContextType.ARRAY, getContext()));
    }

    @Override // org.bson.AbstractBsonWriter
    protected void doWriteEndArray() {
        BsonValue bsonValue = getContext().container;
        setContext(getContext().getParentContext());
        write(bsonValue);
    }

    @Override // org.bson.AbstractBsonWriter
    protected void doWriteBinaryData(BsonBinary bsonBinary) {
        write(bsonBinary);
    }

    @Override // org.bson.AbstractBsonWriter
    public void doWriteBoolean(boolean z) {
        write(BsonBoolean.valueOf(z));
    }

    @Override // org.bson.AbstractBsonWriter
    protected void doWriteDateTime(long j) {
        write(new BsonDateTime(j));
    }

    @Override // org.bson.AbstractBsonWriter
    protected void doWriteDBPointer(BsonDbPointer bsonDbPointer) {
        write(bsonDbPointer);
    }

    @Override // org.bson.AbstractBsonWriter
    protected void doWriteDouble(double d) {
        write(new BsonDouble(d));
    }

    @Override // org.bson.AbstractBsonWriter
    protected void doWriteInt32(int i) {
        write(new BsonInt32(i));
    }

    @Override // org.bson.AbstractBsonWriter
    protected void doWriteInt64(long j) {
        write(new BsonInt64(j));
    }

    @Override // org.bson.AbstractBsonWriter
    protected void doWriteDecimal128(Decimal128 decimal128) {
        write(new BsonDecimal128(decimal128));
    }

    @Override // org.bson.AbstractBsonWriter
    protected void doWriteJavaScript(String str) {
        write(new BsonJavaScript(str));
    }

    @Override // org.bson.AbstractBsonWriter
    protected void doWriteJavaScriptWithScope(String str) {
        setContext(new Context(new BsonString(str), BsonContextType.JAVASCRIPT_WITH_SCOPE, getContext()));
    }

    @Override // org.bson.AbstractBsonWriter
    protected void doWriteMaxKey() {
        write(new BsonMaxKey());
    }

    @Override // org.bson.AbstractBsonWriter
    protected void doWriteMinKey() {
        write(new BsonMinKey());
    }

    @Override // org.bson.AbstractBsonWriter
    public void doWriteNull() {
        write(BsonNull.VALUE);
    }

    @Override // org.bson.AbstractBsonWriter
    public void doWriteObjectId(ObjectId objectId) {
        write(new BsonObjectId(objectId));
    }

    @Override // org.bson.AbstractBsonWriter
    public void doWriteRegularExpression(BsonRegularExpression bsonRegularExpression) {
        write(bsonRegularExpression);
    }

    @Override // org.bson.AbstractBsonWriter
    public void doWriteString(String str) {
        write(new BsonString(str));
    }

    @Override // org.bson.AbstractBsonWriter
    public void doWriteSymbol(String str) {
        write(new BsonSymbol(str));
    }

    @Override // org.bson.AbstractBsonWriter
    public void doWriteTimestamp(BsonTimestamp bsonTimestamp) {
        write(bsonTimestamp);
    }

    @Override // org.bson.AbstractBsonWriter
    public void doWriteUndefined() {
        write(new BsonUndefined());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.bson.AbstractBsonWriter
    public Context getContext() {
        return (Context) super.getContext();
    }

    private void write(BsonValue bsonValue) {
        getContext().add(bsonValue);
    }

    private class Context extends AbstractBsonWriter.Context {
        private BsonValue container;

        Context(BsonValue bsonValue, BsonContextType bsonContextType, Context context) {
            super(context, bsonContextType);
            this.container = bsonValue;
        }

        Context() {
            super(null, BsonContextType.TOP_LEVEL);
        }

        void add(BsonValue bsonValue) {
            BsonValue bsonValue2 = this.container;
            if (bsonValue2 instanceof BsonArray) {
                ((BsonArray) bsonValue2).add(bsonValue);
            } else {
                ((BsonDocument) bsonValue2).put(BsonDocumentWriter.this.getName(), bsonValue);
            }
        }
    }
}
