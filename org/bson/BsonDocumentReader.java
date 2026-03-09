package org.bson;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.bson.AbstractBsonReader;
import org.bson.types.Decimal128;
import org.bson.types.ObjectId;

/* JADX INFO: loaded from: classes4.dex */
public class BsonDocumentReader extends AbstractBsonReader {
    private BsonValue currentValue;
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

    @Override // org.bson.AbstractBsonReader
    protected void doSkipValue() {
    }

    public BsonDocumentReader(BsonDocument bsonDocument) {
        setContext(new Context((Context) null, BsonContextType.TOP_LEVEL, bsonDocument));
        this.currentValue = bsonDocument;
    }

    @Override // org.bson.AbstractBsonReader
    protected BsonBinary doReadBinaryData() {
        return this.currentValue.asBinary();
    }

    @Override // org.bson.AbstractBsonReader
    protected byte doPeekBinarySubType() {
        return this.currentValue.asBinary().getType();
    }

    @Override // org.bson.AbstractBsonReader
    protected int doPeekBinarySize() {
        return this.currentValue.asBinary().getData().length;
    }

    @Override // org.bson.AbstractBsonReader
    protected boolean doReadBoolean() {
        return this.currentValue.asBoolean().getValue();
    }

    @Override // org.bson.AbstractBsonReader
    protected long doReadDateTime() {
        return this.currentValue.asDateTime().getValue();
    }

    @Override // org.bson.AbstractBsonReader
    protected double doReadDouble() {
        return this.currentValue.asDouble().getValue();
    }

    @Override // org.bson.AbstractBsonReader
    protected void doReadEndArray() {
        setContext(getContext().getParentContext());
    }

    /* JADX INFO: renamed from: org.bson.BsonDocumentReader$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$bson$BsonContextType;

        static {
            int[] iArr = new int[BsonContextType.values().length];
            $SwitchMap$org$bson$BsonContextType = iArr;
            try {
                iArr[BsonContextType.ARRAY.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$bson$BsonContextType[BsonContextType.DOCUMENT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$bson$BsonContextType[BsonContextType.TOP_LEVEL.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    @Override // org.bson.AbstractBsonReader
    protected void doReadEndDocument() {
        setContext(getContext().getParentContext());
        int i = AnonymousClass1.$SwitchMap$org$bson$BsonContextType[getContext().getContextType().ordinal()];
        if (i == 1 || i == 2) {
            setState(AbstractBsonReader.State.TYPE);
        } else {
            if (i == 3) {
                setState(AbstractBsonReader.State.DONE);
                return;
            }
            throw new BSONException("Unexpected ContextType.");
        }
    }

    @Override // org.bson.AbstractBsonReader
    protected int doReadInt32() {
        return this.currentValue.asInt32().getValue();
    }

    @Override // org.bson.AbstractBsonReader
    protected long doReadInt64() {
        return this.currentValue.asInt64().getValue();
    }

    @Override // org.bson.AbstractBsonReader
    public Decimal128 doReadDecimal128() {
        return this.currentValue.asDecimal128().getValue();
    }

    @Override // org.bson.AbstractBsonReader
    protected String doReadJavaScript() {
        return this.currentValue.asJavaScript().getCode();
    }

    @Override // org.bson.AbstractBsonReader
    protected String doReadJavaScriptWithScope() {
        return this.currentValue.asJavaScriptWithScope().getCode();
    }

    @Override // org.bson.AbstractBsonReader
    protected ObjectId doReadObjectId() {
        return this.currentValue.asObjectId().getValue();
    }

    @Override // org.bson.AbstractBsonReader
    protected BsonRegularExpression doReadRegularExpression() {
        return this.currentValue.asRegularExpression();
    }

    @Override // org.bson.AbstractBsonReader
    protected BsonDbPointer doReadDBPointer() {
        return this.currentValue.asDBPointer();
    }

    @Override // org.bson.AbstractBsonReader
    protected void doReadStartArray() {
        setContext(new Context(getContext(), BsonContextType.ARRAY, this.currentValue.asArray()));
    }

    @Override // org.bson.AbstractBsonReader
    protected void doReadStartDocument() {
        BsonDocument bsonDocumentAsDocument;
        if (this.currentValue.getBsonType() == BsonType.JAVASCRIPT_WITH_SCOPE) {
            bsonDocumentAsDocument = this.currentValue.asJavaScriptWithScope().getScope();
        } else {
            bsonDocumentAsDocument = this.currentValue.asDocument();
        }
        setContext(new Context(getContext(), BsonContextType.DOCUMENT, bsonDocumentAsDocument));
    }

    @Override // org.bson.AbstractBsonReader
    protected String doReadString() {
        return this.currentValue.asString().getValue();
    }

    @Override // org.bson.AbstractBsonReader
    protected String doReadSymbol() {
        return this.currentValue.asSymbol().getSymbol();
    }

    @Override // org.bson.AbstractBsonReader
    protected BsonTimestamp doReadTimestamp() {
        return this.currentValue.asTimestamp();
    }

    @Override // org.bson.AbstractBsonReader, org.bson.BsonReader
    public BsonType readBsonType() {
        if (getState() == AbstractBsonReader.State.INITIAL || getState() == AbstractBsonReader.State.SCOPE_DOCUMENT) {
            setCurrentBsonType(BsonType.DOCUMENT);
            setState(AbstractBsonReader.State.VALUE);
            return getCurrentBsonType();
        }
        if (getState() != AbstractBsonReader.State.TYPE) {
            throwInvalidState("ReadBSONType", AbstractBsonReader.State.TYPE);
        }
        int i = AnonymousClass1.$SwitchMap$org$bson$BsonContextType[getContext().getContextType().ordinal()];
        if (i == 1) {
            BsonValue nextValue = getContext().getNextValue();
            this.currentValue = nextValue;
            if (nextValue == null) {
                setState(AbstractBsonReader.State.END_OF_ARRAY);
                return BsonType.END_OF_DOCUMENT;
            }
            setState(AbstractBsonReader.State.VALUE);
        } else if (i == 2) {
            Map.Entry<String, BsonValue> nextElement = getContext().getNextElement();
            if (nextElement == null) {
                setState(AbstractBsonReader.State.END_OF_DOCUMENT);
                return BsonType.END_OF_DOCUMENT;
            }
            setCurrentName(nextElement.getKey());
            this.currentValue = nextElement.getValue();
            setState(AbstractBsonReader.State.NAME);
        } else {
            throw new BSONException("Invalid ContextType.");
        }
        setCurrentBsonType(this.currentValue.getBsonType());
        return getCurrentBsonType();
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

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.bson.AbstractBsonReader
    public Context getContext() {
        return (Context) super.getContext();
    }

    protected class Mark extends AbstractBsonReader.Mark {
        private final Context context;
        private final BsonValue currentValue;

        protected Mark() {
            super();
            this.currentValue = BsonDocumentReader.this.currentValue;
            Context context = BsonDocumentReader.this.getContext();
            this.context = context;
            context.mark();
        }

        @Override // org.bson.AbstractBsonReader.Mark, org.bson.BsonReaderMark
        public void reset() {
            super.reset();
            BsonDocumentReader.this.currentValue = this.currentValue;
            BsonDocumentReader.this.setContext(this.context);
            this.context.reset();
        }
    }

    private static class BsonDocumentMarkableIterator<T> implements Iterator<T> {
        private Iterator<T> baseIterator;
        private List<T> markIterator = new ArrayList();
        private int curIndex = 0;
        private boolean marking = false;

        @Override // java.util.Iterator
        public void remove() {
        }

        protected BsonDocumentMarkableIterator(Iterator<T> it) {
            this.baseIterator = it;
        }

        protected void mark() {
            this.marking = true;
        }

        protected void reset() {
            this.curIndex = 0;
            this.marking = false;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.baseIterator.hasNext() || this.curIndex < this.markIterator.size();
        }

        @Override // java.util.Iterator
        public T next() {
            if (this.curIndex < this.markIterator.size()) {
                T t = this.markIterator.get(this.curIndex);
                if (this.marking) {
                    this.curIndex++;
                    return t;
                }
                this.markIterator.remove(0);
                return t;
            }
            T next = this.baseIterator.next();
            if (this.marking) {
                this.markIterator.add(next);
                this.curIndex++;
            }
            return next;
        }
    }

    protected class Context extends AbstractBsonReader.Context {
        private BsonDocumentMarkableIterator<BsonValue> arrayIterator;
        private BsonDocumentMarkableIterator<Map.Entry<String, BsonValue>> documentIterator;

        protected Context(Context context, BsonContextType bsonContextType, BsonArray bsonArray) {
            super(context, bsonContextType);
            this.arrayIterator = new BsonDocumentMarkableIterator<>(bsonArray.iterator());
        }

        protected Context(Context context, BsonContextType bsonContextType, BsonDocument bsonDocument) {
            super(context, bsonContextType);
            this.documentIterator = new BsonDocumentMarkableIterator<>(bsonDocument.entrySet().iterator());
        }

        public Map.Entry<String, BsonValue> getNextElement() {
            if (this.documentIterator.hasNext()) {
                return this.documentIterator.next();
            }
            return null;
        }

        protected void mark() {
            BsonDocumentMarkableIterator<Map.Entry<String, BsonValue>> bsonDocumentMarkableIterator = this.documentIterator;
            if (bsonDocumentMarkableIterator != null) {
                bsonDocumentMarkableIterator.mark();
            } else {
                this.arrayIterator.mark();
            }
            if (getParentContext() != null) {
                ((Context) getParentContext()).mark();
            }
        }

        protected void reset() {
            BsonDocumentMarkableIterator<Map.Entry<String, BsonValue>> bsonDocumentMarkableIterator = this.documentIterator;
            if (bsonDocumentMarkableIterator != null) {
                bsonDocumentMarkableIterator.reset();
            } else {
                this.arrayIterator.reset();
            }
            if (getParentContext() != null) {
                ((Context) getParentContext()).reset();
            }
        }

        public BsonValue getNextValue() {
            if (this.arrayIterator.hasNext()) {
                return this.arrayIterator.next();
            }
            return null;
        }
    }
}
