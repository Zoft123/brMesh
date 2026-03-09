package org.bson.json;

import com.brgd.brblmesh.GeneralClass.GlobalVariable;
import j$.util.DesugarTimeZone;
import java.io.Reader;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import org.bson.AbstractBsonReader;
import org.bson.BSONException;
import org.bson.BsonBinary;
import org.bson.BsonBinarySubType;
import org.bson.BsonContextType;
import org.bson.BsonDbPointer;
import org.bson.BsonInvalidOperationException;
import org.bson.BsonReaderMark;
import org.bson.BsonRegularExpression;
import org.bson.BsonTimestamp;
import org.bson.BsonType;
import org.bson.BsonUndefined;
import org.bson.internal.Base64;
import org.bson.types.Decimal128;
import org.bson.types.MaxKey;
import org.bson.types.MinKey;
import org.bson.types.ObjectId;

/* JADX INFO: loaded from: classes4.dex */
public class JsonReader extends AbstractBsonReader {
    private Object currentValue;
    private Mark mark;
    private JsonToken pushedToken;
    private final JsonScanner scanner;

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

    public JsonReader(String str) {
        this(new JsonScanner(str));
    }

    public JsonReader(Reader reader) {
        this(new JsonScanner(reader));
    }

    private JsonReader(JsonScanner jsonScanner) {
        this.scanner = jsonScanner;
        setContext(new Context(null, BsonContextType.TOP_LEVEL));
    }

    @Override // org.bson.AbstractBsonReader
    protected BsonBinary doReadBinaryData() {
        return (BsonBinary) this.currentValue;
    }

    @Override // org.bson.AbstractBsonReader
    protected byte doPeekBinarySubType() {
        return doReadBinaryData().getType();
    }

    @Override // org.bson.AbstractBsonReader
    protected int doPeekBinarySize() {
        return doReadBinaryData().getData().length;
    }

    @Override // org.bson.AbstractBsonReader
    protected boolean doReadBoolean() {
        return ((Boolean) this.currentValue).booleanValue();
    }

    /* JADX WARN: Removed duplicated region for block: B:127:0x031f  */
    /* JADX WARN: Removed duplicated region for block: B:129:0x032b  */
    /* JADX WARN: Removed duplicated region for block: B:131:0x0337  */
    /* JADX WARN: Removed duplicated region for block: B:134:0x034c  */
    /* JADX WARN: Removed duplicated region for block: B:139:0x0358  */
    @Override // org.bson.AbstractBsonReader, org.bson.BsonReader
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public org.bson.BsonType readBsonType() {
        /*
            Method dump skipped, instruction units count: 914
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bson.json.JsonReader.readBsonType():org.bson.BsonType");
    }

    @Override // org.bson.AbstractBsonReader
    public Decimal128 doReadDecimal128() {
        return (Decimal128) this.currentValue;
    }

    @Override // org.bson.AbstractBsonReader
    protected long doReadDateTime() {
        return ((Long) this.currentValue).longValue();
    }

    @Override // org.bson.AbstractBsonReader
    protected double doReadDouble() {
        return ((Double) this.currentValue).doubleValue();
    }

    @Override // org.bson.AbstractBsonReader
    protected void doReadEndArray() {
        setContext(getContext().getParentContext());
        if (getContext().getContextType() == BsonContextType.ARRAY || getContext().getContextType() == BsonContextType.DOCUMENT) {
            JsonToken jsonTokenPopToken = popToken();
            if (jsonTokenPopToken.getType() != JsonTokenType.COMMA) {
                pushToken(jsonTokenPopToken);
            }
        }
    }

    @Override // org.bson.AbstractBsonReader
    protected void doReadEndDocument() {
        setContext(getContext().getParentContext());
        if (getContext() != null && getContext().getContextType() == BsonContextType.SCOPE_DOCUMENT) {
            setContext(getContext().getParentContext());
            verifyToken(JsonTokenType.END_OBJECT);
        }
        if (getContext() == null) {
            throw new JsonParseException("Unexpected end of document.");
        }
        if (getContext().getContextType() == BsonContextType.ARRAY || getContext().getContextType() == BsonContextType.DOCUMENT) {
            JsonToken jsonTokenPopToken = popToken();
            if (jsonTokenPopToken.getType() != JsonTokenType.COMMA) {
                pushToken(jsonTokenPopToken);
            }
        }
    }

    @Override // org.bson.AbstractBsonReader
    protected int doReadInt32() {
        return ((Integer) this.currentValue).intValue();
    }

    @Override // org.bson.AbstractBsonReader
    protected long doReadInt64() {
        return ((Long) this.currentValue).longValue();
    }

    @Override // org.bson.AbstractBsonReader
    protected String doReadJavaScript() {
        return (String) this.currentValue;
    }

    @Override // org.bson.AbstractBsonReader
    protected String doReadJavaScriptWithScope() {
        return (String) this.currentValue;
    }

    @Override // org.bson.AbstractBsonReader
    protected ObjectId doReadObjectId() {
        return (ObjectId) this.currentValue;
    }

    @Override // org.bson.AbstractBsonReader
    protected BsonRegularExpression doReadRegularExpression() {
        return (BsonRegularExpression) this.currentValue;
    }

    @Override // org.bson.AbstractBsonReader
    protected BsonDbPointer doReadDBPointer() {
        return (BsonDbPointer) this.currentValue;
    }

    @Override // org.bson.AbstractBsonReader
    protected void doReadStartArray() {
        setContext(new Context(getContext(), BsonContextType.ARRAY));
    }

    @Override // org.bson.AbstractBsonReader
    protected void doReadStartDocument() {
        setContext(new Context(getContext(), BsonContextType.DOCUMENT));
    }

    @Override // org.bson.AbstractBsonReader
    protected String doReadString() {
        return (String) this.currentValue;
    }

    @Override // org.bson.AbstractBsonReader
    protected String doReadSymbol() {
        return (String) this.currentValue;
    }

    @Override // org.bson.AbstractBsonReader
    protected BsonTimestamp doReadTimestamp() {
        return (BsonTimestamp) this.currentValue;
    }

    /* JADX INFO: renamed from: org.bson.json.JsonReader$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$bson$BsonContextType;
        static final /* synthetic */ int[] $SwitchMap$org$bson$BsonType;
        static final /* synthetic */ int[] $SwitchMap$org$bson$json$JsonTokenType;

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
            int[] iArr2 = new int[BsonContextType.values().length];
            $SwitchMap$org$bson$BsonContextType = iArr2;
            try {
                iArr2[BsonContextType.DOCUMENT.ordinal()] = 1;
            } catch (NoSuchFieldError unused21) {
            }
            try {
                $SwitchMap$org$bson$BsonContextType[BsonContextType.SCOPE_DOCUMENT.ordinal()] = 2;
            } catch (NoSuchFieldError unused22) {
            }
            try {
                $SwitchMap$org$bson$BsonContextType[BsonContextType.ARRAY.ordinal()] = 3;
            } catch (NoSuchFieldError unused23) {
            }
            try {
                $SwitchMap$org$bson$BsonContextType[BsonContextType.JAVASCRIPT_WITH_SCOPE.ordinal()] = 4;
            } catch (NoSuchFieldError unused24) {
            }
            try {
                $SwitchMap$org$bson$BsonContextType[BsonContextType.TOP_LEVEL.ordinal()] = 5;
            } catch (NoSuchFieldError unused25) {
            }
            int[] iArr3 = new int[JsonTokenType.values().length];
            $SwitchMap$org$bson$json$JsonTokenType = iArr3;
            try {
                iArr3[JsonTokenType.STRING.ordinal()] = 1;
            } catch (NoSuchFieldError unused26) {
            }
            try {
                $SwitchMap$org$bson$json$JsonTokenType[JsonTokenType.UNQUOTED_STRING.ordinal()] = 2;
            } catch (NoSuchFieldError unused27) {
            }
            try {
                $SwitchMap$org$bson$json$JsonTokenType[JsonTokenType.END_OBJECT.ordinal()] = 3;
            } catch (NoSuchFieldError unused28) {
            }
            try {
                $SwitchMap$org$bson$json$JsonTokenType[JsonTokenType.BEGIN_ARRAY.ordinal()] = 4;
            } catch (NoSuchFieldError unused29) {
            }
            try {
                $SwitchMap$org$bson$json$JsonTokenType[JsonTokenType.BEGIN_OBJECT.ordinal()] = 5;
            } catch (NoSuchFieldError unused30) {
            }
            try {
                $SwitchMap$org$bson$json$JsonTokenType[JsonTokenType.DOUBLE.ordinal()] = 6;
            } catch (NoSuchFieldError unused31) {
            }
            try {
                $SwitchMap$org$bson$json$JsonTokenType[JsonTokenType.END_OF_FILE.ordinal()] = 7;
            } catch (NoSuchFieldError unused32) {
            }
            try {
                $SwitchMap$org$bson$json$JsonTokenType[JsonTokenType.INT32.ordinal()] = 8;
            } catch (NoSuchFieldError unused33) {
            }
            try {
                $SwitchMap$org$bson$json$JsonTokenType[JsonTokenType.INT64.ordinal()] = 9;
            } catch (NoSuchFieldError unused34) {
            }
            try {
                $SwitchMap$org$bson$json$JsonTokenType[JsonTokenType.REGULAR_EXPRESSION.ordinal()] = 10;
            } catch (NoSuchFieldError unused35) {
            }
            try {
                $SwitchMap$org$bson$json$JsonTokenType[JsonTokenType.COMMA.ordinal()] = 11;
            } catch (NoSuchFieldError unused36) {
            }
        }
    }

    @Override // org.bson.AbstractBsonReader
    protected void doSkipValue() {
        switch (AnonymousClass1.$SwitchMap$org$bson$BsonType[getCurrentBsonType().ordinal()]) {
            case 1:
                readStartArray();
                while (readBsonType() != BsonType.END_OF_DOCUMENT) {
                    skipValue();
                }
                readEndArray();
                break;
            case 2:
                readBinaryData();
                break;
            case 3:
                readBoolean();
                break;
            case 4:
                readDateTime();
                break;
            case 5:
                readStartDocument();
                while (readBsonType() != BsonType.END_OF_DOCUMENT) {
                    skipName();
                    skipValue();
                }
                readEndDocument();
                break;
            case 6:
                readDouble();
                break;
            case 7:
                readInt32();
                break;
            case 8:
                readInt64();
                break;
            case 9:
                readDecimal128();
                break;
            case 10:
                readJavaScript();
                break;
            case 11:
                readJavaScriptWithScope();
                readStartDocument();
                while (readBsonType() != BsonType.END_OF_DOCUMENT) {
                    skipName();
                    skipValue();
                }
                readEndDocument();
                break;
            case 12:
                readMaxKey();
                break;
            case 13:
                readMinKey();
                break;
            case 14:
                readNull();
                break;
            case 15:
                readObjectId();
                break;
            case 16:
                readRegularExpression();
                break;
            case 17:
                readString();
                break;
            case 18:
                readSymbol();
                break;
            case 19:
                readTimestamp();
                break;
            case 20:
                readUndefined();
                break;
        }
    }

    private JsonToken popToken() {
        JsonToken jsonToken = this.pushedToken;
        if (jsonToken != null) {
            this.pushedToken = null;
            return jsonToken;
        }
        return this.scanner.nextToken();
    }

    private void pushToken(JsonToken jsonToken) {
        if (this.pushedToken == null) {
            this.pushedToken = jsonToken;
            return;
        }
        throw new BsonInvalidOperationException("There is already a pending token.");
    }

    private void verifyToken(JsonTokenType jsonTokenType) {
        JsonToken jsonTokenPopToken = popToken();
        if (jsonTokenType != jsonTokenPopToken.getType()) {
            throw new JsonParseException("JSON reader expected token type '%s' but found '%s'.", jsonTokenType, jsonTokenPopToken.getValue());
        }
    }

    private void verifyToken(JsonTokenType jsonTokenType, Object obj) {
        JsonToken jsonTokenPopToken = popToken();
        if (jsonTokenType != jsonTokenPopToken.getType()) {
            throw new JsonParseException("JSON reader expected token type '%s' but found '%s'.", jsonTokenType, jsonTokenPopToken.getValue());
        }
        if (!obj.equals(jsonTokenPopToken.getValue())) {
            throw new JsonParseException("JSON reader expected '%s' but found '%s'.", obj, jsonTokenPopToken.getValue());
        }
    }

    private void verifyString(String str) {
        if (str == null) {
            throw new IllegalArgumentException("Can't be null");
        }
        JsonToken jsonTokenPopToken = popToken();
        JsonTokenType type = jsonTokenPopToken.getType();
        if ((type != JsonTokenType.STRING && type != JsonTokenType.UNQUOTED_STRING) || !str.equals(jsonTokenPopToken.getValue())) {
            throw new JsonParseException("JSON reader expected '%s' but found '%s'.", str, jsonTokenPopToken.getValue());
        }
    }

    private void visitNew() {
        JsonToken jsonTokenPopToken = popToken();
        if (jsonTokenPopToken.getType() != JsonTokenType.UNQUOTED_STRING) {
            throw new JsonParseException("JSON reader expected a type name but found '%s'.", jsonTokenPopToken.getValue());
        }
        String str = (String) jsonTokenPopToken.getValue(String.class);
        if ("MinKey".equals(str)) {
            visitEmptyConstructor();
            setCurrentBsonType(BsonType.MIN_KEY);
            this.currentValue = new MinKey();
            return;
        }
        if ("MaxKey".equals(str)) {
            visitEmptyConstructor();
            setCurrentBsonType(BsonType.MAX_KEY);
            this.currentValue = new MaxKey();
            return;
        }
        if ("BinData".equals(str)) {
            this.currentValue = visitBinDataConstructor();
            setCurrentBsonType(BsonType.BINARY);
            return;
        }
        if ("Date".equals(str)) {
            this.currentValue = Long.valueOf(visitDateTimeConstructor());
            setCurrentBsonType(BsonType.DATE_TIME);
            return;
        }
        if ("HexData".equals(str)) {
            this.currentValue = visitHexDataConstructor();
            setCurrentBsonType(BsonType.BINARY);
            return;
        }
        if ("ISODate".equals(str)) {
            this.currentValue = Long.valueOf(visitISODateTimeConstructor());
            setCurrentBsonType(BsonType.DATE_TIME);
            return;
        }
        if ("NumberInt".equals(str)) {
            this.currentValue = Integer.valueOf(visitNumberIntConstructor());
            setCurrentBsonType(BsonType.INT32);
            return;
        }
        if ("NumberLong".equals(str)) {
            this.currentValue = Long.valueOf(visitNumberLongConstructor());
            setCurrentBsonType(BsonType.INT64);
            return;
        }
        if ("NumberDecimal".equals(str)) {
            this.currentValue = visitNumberDecimalConstructor();
            setCurrentBsonType(BsonType.DECIMAL128);
            return;
        }
        if ("ObjectId".equals(str)) {
            this.currentValue = visitObjectIdConstructor();
            setCurrentBsonType(BsonType.OBJECT_ID);
            return;
        }
        if ("RegExp".equals(str)) {
            this.currentValue = visitRegularExpressionConstructor();
            setCurrentBsonType(BsonType.REGULAR_EXPRESSION);
            return;
        }
        if ("DBPointer".equals(str)) {
            this.currentValue = visitDBPointerConstructor();
            setCurrentBsonType(BsonType.DB_POINTER);
            return;
        }
        if ("UUID".equals(str) || "GUID".equals(str) || "CSUUID".equals(str) || "CSGUID".equals(str) || "JUUID".equals(str) || "JGUID".equals(str) || "PYUUID".equals(str) || "PYGUID".equals(str)) {
            this.currentValue = visitUUIDConstructor(str);
            setCurrentBsonType(BsonType.BINARY);
            return;
        }
        throw new JsonParseException("JSON reader expected a type name but found '%s'.", str);
    }

    private void visitExtendedJSON() {
        JsonToken jsonTokenPopToken = popToken();
        String str = (String) jsonTokenPopToken.getValue(String.class);
        JsonTokenType type = jsonTokenPopToken.getType();
        if (type == JsonTokenType.STRING || type == JsonTokenType.UNQUOTED_STRING) {
            if ("$binary".equals(str) || "$type".equals(str)) {
                BsonBinary bsonBinaryVisitBinDataExtendedJson = visitBinDataExtendedJson(str);
                this.currentValue = bsonBinaryVisitBinDataExtendedJson;
                if (bsonBinaryVisitBinDataExtendedJson != null) {
                    setCurrentBsonType(BsonType.BINARY);
                    return;
                }
            } else if ("$regex".equals(str) || "$options".equals(str)) {
                BsonRegularExpression bsonRegularExpressionVisitRegularExpressionExtendedJson = visitRegularExpressionExtendedJson(str);
                this.currentValue = bsonRegularExpressionVisitRegularExpressionExtendedJson;
                if (bsonRegularExpressionVisitRegularExpressionExtendedJson != null) {
                    setCurrentBsonType(BsonType.REGULAR_EXPRESSION);
                    return;
                }
            } else {
                if ("$code".equals(str)) {
                    visitJavaScriptExtendedJson();
                    return;
                }
                if ("$date".equals(str)) {
                    this.currentValue = Long.valueOf(visitDateTimeExtendedJson());
                    setCurrentBsonType(BsonType.DATE_TIME);
                    return;
                }
                if ("$maxKey".equals(str)) {
                    this.currentValue = visitMaxKeyExtendedJson();
                    setCurrentBsonType(BsonType.MAX_KEY);
                    return;
                }
                if ("$minKey".equals(str)) {
                    this.currentValue = visitMinKeyExtendedJson();
                    setCurrentBsonType(BsonType.MIN_KEY);
                    return;
                }
                if ("$oid".equals(str)) {
                    this.currentValue = visitObjectIdExtendedJson();
                    setCurrentBsonType(BsonType.OBJECT_ID);
                    return;
                }
                if ("$regularExpression".equals(str)) {
                    this.currentValue = visitNewRegularExpressionExtendedJson();
                    setCurrentBsonType(BsonType.REGULAR_EXPRESSION);
                    return;
                }
                if ("$symbol".equals(str)) {
                    this.currentValue = visitSymbolExtendedJson();
                    setCurrentBsonType(BsonType.SYMBOL);
                    return;
                }
                if ("$timestamp".equals(str)) {
                    this.currentValue = visitTimestampExtendedJson();
                    setCurrentBsonType(BsonType.TIMESTAMP);
                    return;
                }
                if ("$undefined".equals(str)) {
                    this.currentValue = visitUndefinedExtendedJson();
                    setCurrentBsonType(BsonType.UNDEFINED);
                    return;
                }
                if ("$numberLong".equals(str)) {
                    this.currentValue = visitNumberLongExtendedJson();
                    setCurrentBsonType(BsonType.INT64);
                    return;
                }
                if ("$numberInt".equals(str)) {
                    this.currentValue = visitNumberIntExtendedJson();
                    setCurrentBsonType(BsonType.INT32);
                    return;
                }
                if ("$numberDouble".equals(str)) {
                    this.currentValue = visitNumberDoubleExtendedJson();
                    setCurrentBsonType(BsonType.DOUBLE);
                    return;
                } else if ("$numberDecimal".equals(str)) {
                    this.currentValue = visitNumberDecimalExtendedJson();
                    setCurrentBsonType(BsonType.DECIMAL128);
                    return;
                } else if ("$dbPointer".equals(str)) {
                    this.currentValue = visitDbPointerExtendedJson();
                    setCurrentBsonType(BsonType.DB_POINTER);
                    return;
                }
            }
        }
        pushToken(jsonTokenPopToken);
        setCurrentBsonType(BsonType.DOCUMENT);
    }

    private void visitEmptyConstructor() {
        JsonToken jsonTokenPopToken = popToken();
        if (jsonTokenPopToken.getType() == JsonTokenType.LEFT_PAREN) {
            verifyToken(JsonTokenType.RIGHT_PAREN);
        } else {
            pushToken(jsonTokenPopToken);
        }
    }

    private BsonBinary visitBinDataConstructor() {
        verifyToken(JsonTokenType.LEFT_PAREN);
        JsonToken jsonTokenPopToken = popToken();
        if (jsonTokenPopToken.getType() != JsonTokenType.INT32) {
            throw new JsonParseException("JSON reader expected a binary subtype but found '%s'.", jsonTokenPopToken.getValue());
        }
        verifyToken(JsonTokenType.COMMA);
        JsonToken jsonTokenPopToken2 = popToken();
        if (jsonTokenPopToken2.getType() != JsonTokenType.UNQUOTED_STRING && jsonTokenPopToken2.getType() != JsonTokenType.STRING) {
            throw new JsonParseException("JSON reader expected a string but found '%s'.", jsonTokenPopToken2.getValue());
        }
        verifyToken(JsonTokenType.RIGHT_PAREN);
        return new BsonBinary(((Integer) jsonTokenPopToken.getValue(Integer.class)).byteValue(), Base64.decode((String) jsonTokenPopToken2.getValue(String.class)));
    }

    private BsonBinary visitUUIDConstructor(String str) {
        verifyToken(JsonTokenType.LEFT_PAREN);
        String strReplaceAll = readStringFromExtendedJson().replaceAll("\\{", "").replaceAll("}", "").replaceAll("-", "");
        verifyToken(JsonTokenType.RIGHT_PAREN);
        byte[] bArrDecodeHex = decodeHex(strReplaceAll);
        BsonBinarySubType bsonBinarySubType = BsonBinarySubType.UUID_STANDARD;
        if (!"UUID".equals(str) || !"GUID".equals(str)) {
            bsonBinarySubType = BsonBinarySubType.UUID_LEGACY;
        }
        return new BsonBinary(bsonBinarySubType, bArrDecodeHex);
    }

    private BsonRegularExpression visitRegularExpressionConstructor() {
        String stringFromExtendedJson;
        verifyToken(JsonTokenType.LEFT_PAREN);
        String stringFromExtendedJson2 = readStringFromExtendedJson();
        JsonToken jsonTokenPopToken = popToken();
        if (jsonTokenPopToken.getType() == JsonTokenType.COMMA) {
            stringFromExtendedJson = readStringFromExtendedJson();
        } else {
            pushToken(jsonTokenPopToken);
            stringFromExtendedJson = "";
        }
        verifyToken(JsonTokenType.RIGHT_PAREN);
        return new BsonRegularExpression(stringFromExtendedJson2, stringFromExtendedJson);
    }

    private ObjectId visitObjectIdConstructor() {
        verifyToken(JsonTokenType.LEFT_PAREN);
        ObjectId objectId = new ObjectId(readStringFromExtendedJson());
        verifyToken(JsonTokenType.RIGHT_PAREN);
        return objectId;
    }

    private BsonTimestamp visitTimestampConstructor() {
        verifyToken(JsonTokenType.LEFT_PAREN);
        JsonToken jsonTokenPopToken = popToken();
        if (jsonTokenPopToken.getType() != JsonTokenType.INT32) {
            throw new JsonParseException("JSON reader expected an integer but found '%s'.", jsonTokenPopToken.getValue());
        }
        int iIntValue = ((Integer) jsonTokenPopToken.getValue(Integer.class)).intValue();
        verifyToken(JsonTokenType.COMMA);
        JsonToken jsonTokenPopToken2 = popToken();
        if (jsonTokenPopToken2.getType() != JsonTokenType.INT32) {
            throw new JsonParseException("JSON reader expected an integer but found '%s'.", jsonTokenPopToken.getValue());
        }
        int iIntValue2 = ((Integer) jsonTokenPopToken2.getValue(Integer.class)).intValue();
        verifyToken(JsonTokenType.RIGHT_PAREN);
        return new BsonTimestamp(iIntValue, iIntValue2);
    }

    private BsonDbPointer visitDBPointerConstructor() {
        verifyToken(JsonTokenType.LEFT_PAREN);
        String stringFromExtendedJson = readStringFromExtendedJson();
        verifyToken(JsonTokenType.COMMA);
        ObjectId objectId = new ObjectId(readStringFromExtendedJson());
        verifyToken(JsonTokenType.RIGHT_PAREN);
        return new BsonDbPointer(stringFromExtendedJson, objectId);
    }

    private int visitNumberIntConstructor() {
        int iIntValue;
        verifyToken(JsonTokenType.LEFT_PAREN);
        JsonToken jsonTokenPopToken = popToken();
        if (jsonTokenPopToken.getType() == JsonTokenType.INT32) {
            iIntValue = ((Integer) jsonTokenPopToken.getValue(Integer.class)).intValue();
        } else if (jsonTokenPopToken.getType() == JsonTokenType.STRING) {
            iIntValue = Integer.parseInt((String) jsonTokenPopToken.getValue(String.class));
        } else {
            throw new JsonParseException("JSON reader expected an integer or a string but found '%s'.", jsonTokenPopToken.getValue());
        }
        verifyToken(JsonTokenType.RIGHT_PAREN);
        return iIntValue;
    }

    private long visitNumberLongConstructor() {
        long jLongValue;
        verifyToken(JsonTokenType.LEFT_PAREN);
        JsonToken jsonTokenPopToken = popToken();
        if (jsonTokenPopToken.getType() == JsonTokenType.INT32 || jsonTokenPopToken.getType() == JsonTokenType.INT64) {
            jLongValue = ((Long) jsonTokenPopToken.getValue(Long.class)).longValue();
        } else if (jsonTokenPopToken.getType() == JsonTokenType.STRING) {
            jLongValue = Long.parseLong((String) jsonTokenPopToken.getValue(String.class));
        } else {
            throw new JsonParseException("JSON reader expected an integer or a string but found '%s'.", jsonTokenPopToken.getValue());
        }
        verifyToken(JsonTokenType.RIGHT_PAREN);
        return jLongValue;
    }

    private Decimal128 visitNumberDecimalConstructor() {
        Decimal128 decimal128;
        verifyToken(JsonTokenType.LEFT_PAREN);
        JsonToken jsonTokenPopToken = popToken();
        if (jsonTokenPopToken.getType() == JsonTokenType.INT32 || jsonTokenPopToken.getType() == JsonTokenType.INT64 || jsonTokenPopToken.getType() == JsonTokenType.DOUBLE) {
            decimal128 = (Decimal128) jsonTokenPopToken.getValue(Decimal128.class);
        } else if (jsonTokenPopToken.getType() == JsonTokenType.STRING) {
            decimal128 = Decimal128.parse((String) jsonTokenPopToken.getValue(String.class));
        } else {
            throw new JsonParseException("JSON reader expected a number or a string but found '%s'.", jsonTokenPopToken.getValue());
        }
        verifyToken(JsonTokenType.RIGHT_PAREN);
        return decimal128;
    }

    private long visitISODateTimeConstructor() {
        verifyToken(JsonTokenType.LEFT_PAREN);
        JsonToken jsonTokenPopToken = popToken();
        if (jsonTokenPopToken.getType() == JsonTokenType.RIGHT_PAREN) {
            return new Date().getTime();
        }
        if (jsonTokenPopToken.getType() != JsonTokenType.STRING) {
            throw new JsonParseException("JSON reader expected a string but found '%s'.", jsonTokenPopToken.getValue());
        }
        verifyToken(JsonTokenType.RIGHT_PAREN);
        String[] strArr = {"yyyy-MM-dd", "yyyy-MM-dd'T'HH:mm:ssz", "yyyy-MM-dd'T'HH:mm:ss.SSSz"};
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(strArr[0], Locale.ENGLISH);
        ParsePosition parsePosition = new ParsePosition(0);
        String str = (String) jsonTokenPopToken.getValue(String.class);
        if (str.endsWith("Z")) {
            str = str.substring(0, str.length() - 1) + "GMT-00:00";
        }
        for (int i = 0; i < 3; i++) {
            simpleDateFormat.applyPattern(strArr[i]);
            simpleDateFormat.setLenient(true);
            parsePosition.setIndex(0);
            Date date = simpleDateFormat.parse(str, parsePosition);
            if (date != null && parsePosition.getIndex() == str.length()) {
                return date.getTime();
            }
        }
        throw new JsonParseException("Invalid date format.");
    }

    private BsonBinary visitHexDataConstructor() {
        verifyToken(JsonTokenType.LEFT_PAREN);
        JsonToken jsonTokenPopToken = popToken();
        if (jsonTokenPopToken.getType() != JsonTokenType.INT32) {
            throw new JsonParseException("JSON reader expected a binary subtype but found '%s'.", jsonTokenPopToken.getValue());
        }
        verifyToken(JsonTokenType.COMMA);
        String stringFromExtendedJson = readStringFromExtendedJson();
        verifyToken(JsonTokenType.RIGHT_PAREN);
        if ((stringFromExtendedJson.length() & 1) != 0) {
            stringFromExtendedJson = GlobalVariable.ILLUMINATION + stringFromExtendedJson;
        }
        for (BsonBinarySubType bsonBinarySubType : BsonBinarySubType.values()) {
            if (bsonBinarySubType.getValue() == ((Integer) jsonTokenPopToken.getValue(Integer.class)).intValue()) {
                return new BsonBinary(bsonBinarySubType, decodeHex(stringFromExtendedJson));
            }
        }
        return new BsonBinary(decodeHex(stringFromExtendedJson));
    }

    private long visitDateTimeConstructor() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss z", Locale.ENGLISH);
        verifyToken(JsonTokenType.LEFT_PAREN);
        JsonToken jsonTokenPopToken = popToken();
        if (jsonTokenPopToken.getType() == JsonTokenType.RIGHT_PAREN) {
            return new Date().getTime();
        }
        if (jsonTokenPopToken.getType() == JsonTokenType.STRING) {
            verifyToken(JsonTokenType.RIGHT_PAREN);
            String str = (String) jsonTokenPopToken.getValue(String.class);
            ParsePosition parsePosition = new ParsePosition(0);
            Date date = simpleDateFormat.parse(str, parsePosition);
            if (date != null && parsePosition.getIndex() == str.length()) {
                return date.getTime();
            }
            throw new JsonParseException("JSON reader expected a date in 'EEE MMM dd yyyy HH:mm:ss z' format but found '%s'.", str);
        }
        if (jsonTokenPopToken.getType() == JsonTokenType.INT32 || jsonTokenPopToken.getType() == JsonTokenType.INT64) {
            long[] jArr = new long[7];
            int i = 0;
            while (true) {
                if (i < 7) {
                    jArr[i] = ((Long) jsonTokenPopToken.getValue(Long.class)).longValue();
                    i++;
                }
                JsonToken jsonTokenPopToken2 = popToken();
                if (jsonTokenPopToken2.getType() == JsonTokenType.RIGHT_PAREN) {
                    if (i == 1) {
                        return jArr[0];
                    }
                    if (i < 3 || i > 7) {
                        throw new JsonParseException("JSON reader expected 1 or 3-7 integers but found %d.", Integer.valueOf(i));
                    }
                    Calendar calendar = Calendar.getInstance(DesugarTimeZone.getTimeZone("UTC"));
                    calendar.set(1, (int) jArr[0]);
                    calendar.set(2, (int) jArr[1]);
                    calendar.set(5, (int) jArr[2]);
                    calendar.set(11, (int) jArr[3]);
                    calendar.set(12, (int) jArr[4]);
                    calendar.set(13, (int) jArr[5]);
                    calendar.set(14, (int) jArr[6]);
                    return calendar.getTimeInMillis();
                }
                if (jsonTokenPopToken2.getType() != JsonTokenType.COMMA) {
                    throw new JsonParseException("JSON reader expected a ',' or a ')' but found '%s'.", jsonTokenPopToken2.getValue());
                }
                jsonTokenPopToken = popToken();
                if (jsonTokenPopToken.getType() != JsonTokenType.INT32 && jsonTokenPopToken.getType() != JsonTokenType.INT64) {
                    throw new JsonParseException("JSON reader expected an integer but found '%s'.", jsonTokenPopToken.getValue());
                }
            }
        } else {
            throw new JsonParseException("JSON reader expected an integer or a string but found '%s'.", jsonTokenPopToken.getValue());
        }
    }

    private String visitDateTimeConstructorWithOutNew() {
        verifyToken(JsonTokenType.LEFT_PAREN);
        JsonToken jsonTokenPopToken = popToken();
        if (jsonTokenPopToken.getType() != JsonTokenType.RIGHT_PAREN) {
            while (jsonTokenPopToken.getType() != JsonTokenType.END_OF_FILE) {
                jsonTokenPopToken = popToken();
                if (jsonTokenPopToken.getType() == JsonTokenType.RIGHT_PAREN) {
                    break;
                }
            }
            if (jsonTokenPopToken.getType() != JsonTokenType.RIGHT_PAREN) {
                throw new JsonParseException("JSON reader expected a ')' but found '%s'.", jsonTokenPopToken.getValue());
            }
        }
        return new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss z", Locale.ENGLISH).format(new Date());
    }

    private BsonBinary visitBinDataExtendedJson(String str) {
        byte binarySubtypeFromExtendedJson;
        byte[] bArrDecode;
        Mark mark = new Mark();
        try {
            verifyToken(JsonTokenType.COLON);
            if (!str.equals("$binary")) {
                mark.reset();
                return visitLegacyBinaryExtendedJson(str);
            }
            if (popToken().getType() != JsonTokenType.BEGIN_OBJECT) {
                mark.reset();
                return visitLegacyBinaryExtendedJson(str);
            }
            String str2 = (String) popToken().getValue(String.class);
            if (str2.equals("base64")) {
                verifyToken(JsonTokenType.COLON);
                bArrDecode = Base64.decode(readStringFromExtendedJson());
                verifyToken(JsonTokenType.COMMA);
                verifyString("subType");
                verifyToken(JsonTokenType.COLON);
                binarySubtypeFromExtendedJson = readBinarySubtypeFromExtendedJson();
            } else {
                if (!str2.equals("subType")) {
                    throw new JsonParseException("Unexpected key for $binary: " + str2);
                }
                verifyToken(JsonTokenType.COLON);
                byte binarySubtypeFromExtendedJson2 = readBinarySubtypeFromExtendedJson();
                verifyToken(JsonTokenType.COMMA);
                verifyString("base64");
                verifyToken(JsonTokenType.COLON);
                binarySubtypeFromExtendedJson = binarySubtypeFromExtendedJson2;
                bArrDecode = Base64.decode(readStringFromExtendedJson());
            }
            verifyToken(JsonTokenType.END_OBJECT);
            verifyToken(JsonTokenType.END_OBJECT);
            return new BsonBinary(binarySubtypeFromExtendedJson, bArrDecode);
        } finally {
            mark.discard();
        }
    }

    private BsonBinary visitLegacyBinaryExtendedJson(String str) {
        byte binarySubtypeFromExtendedJson;
        byte[] bArrDecode;
        Mark mark = new Mark();
        try {
            verifyToken(JsonTokenType.COLON);
            if (str.equals("$binary")) {
                bArrDecode = Base64.decode(readStringFromExtendedJson());
                verifyToken(JsonTokenType.COMMA);
                verifyString("$type");
                verifyToken(JsonTokenType.COLON);
                binarySubtypeFromExtendedJson = readBinarySubtypeFromExtendedJson();
            } else {
                byte binarySubtypeFromExtendedJson2 = readBinarySubtypeFromExtendedJson();
                verifyToken(JsonTokenType.COMMA);
                verifyString("$binary");
                verifyToken(JsonTokenType.COLON);
                binarySubtypeFromExtendedJson = binarySubtypeFromExtendedJson2;
                bArrDecode = Base64.decode(readStringFromExtendedJson());
            }
            verifyToken(JsonTokenType.END_OBJECT);
            return new BsonBinary(binarySubtypeFromExtendedJson, bArrDecode);
        } catch (JsonParseException unused) {
            mark.reset();
            return null;
        } catch (NumberFormatException unused2) {
            mark.reset();
            return null;
        } finally {
            mark.discard();
        }
    }

    private byte readBinarySubtypeFromExtendedJson() {
        JsonToken jsonTokenPopToken = popToken();
        if (jsonTokenPopToken.getType() != JsonTokenType.STRING && jsonTokenPopToken.getType() != JsonTokenType.INT32) {
            throw new JsonParseException("JSON reader expected a string or number but found '%s'.", jsonTokenPopToken.getValue());
        }
        if (jsonTokenPopToken.getType() == JsonTokenType.STRING) {
            return (byte) Integer.parseInt((String) jsonTokenPopToken.getValue(String.class), 16);
        }
        return ((Integer) jsonTokenPopToken.getValue(Integer.class)).byteValue();
    }

    private long visitDateTimeExtendedJson() {
        long jLongValue;
        verifyToken(JsonTokenType.COLON);
        JsonToken jsonTokenPopToken = popToken();
        if (jsonTokenPopToken.getType() == JsonTokenType.BEGIN_OBJECT) {
            String str = (String) popToken().getValue(String.class);
            if (!str.equals("$numberLong")) {
                throw new JsonParseException(String.format("JSON reader expected $numberLong within $date, but found %s", str));
            }
            long jLongValue2 = visitNumberLongExtendedJson().longValue();
            verifyToken(JsonTokenType.END_OBJECT);
            return jLongValue2;
        }
        if (jsonTokenPopToken.getType() == JsonTokenType.INT32 || jsonTokenPopToken.getType() == JsonTokenType.INT64) {
            jLongValue = ((Long) jsonTokenPopToken.getValue(Long.class)).longValue();
        } else if (jsonTokenPopToken.getType() == JsonTokenType.STRING) {
            try {
                jLongValue = DateTimeFormatter.parse((String) jsonTokenPopToken.getValue(String.class));
            } catch (IllegalArgumentException e) {
                throw new JsonParseException("Failed to parse string as a date", e);
            }
        } else {
            throw new JsonParseException("JSON reader expected an integer or string but found '%s'.", jsonTokenPopToken.getValue());
        }
        verifyToken(JsonTokenType.END_OBJECT);
        return jLongValue;
    }

    private MaxKey visitMaxKeyExtendedJson() {
        verifyToken(JsonTokenType.COLON);
        verifyToken(JsonTokenType.INT32, 1);
        verifyToken(JsonTokenType.END_OBJECT);
        return new MaxKey();
    }

    private MinKey visitMinKeyExtendedJson() {
        verifyToken(JsonTokenType.COLON);
        verifyToken(JsonTokenType.INT32, 1);
        verifyToken(JsonTokenType.END_OBJECT);
        return new MinKey();
    }

    private ObjectId visitObjectIdExtendedJson() {
        verifyToken(JsonTokenType.COLON);
        ObjectId objectId = new ObjectId(readStringFromExtendedJson());
        verifyToken(JsonTokenType.END_OBJECT);
        return objectId;
    }

    private BsonRegularExpression visitNewRegularExpressionExtendedJson() {
        String stringFromExtendedJson;
        String stringFromExtendedJson2;
        verifyToken(JsonTokenType.COLON);
        verifyToken(JsonTokenType.BEGIN_OBJECT);
        String stringFromExtendedJson3 = readStringFromExtendedJson();
        if (stringFromExtendedJson3.equals("pattern")) {
            verifyToken(JsonTokenType.COLON);
            stringFromExtendedJson2 = readStringFromExtendedJson();
            verifyToken(JsonTokenType.COMMA);
            verifyString("options");
            verifyToken(JsonTokenType.COLON);
            stringFromExtendedJson = readStringFromExtendedJson();
        } else if (stringFromExtendedJson3.equals("options")) {
            verifyToken(JsonTokenType.COLON);
            String stringFromExtendedJson4 = readStringFromExtendedJson();
            verifyToken(JsonTokenType.COMMA);
            verifyString("pattern");
            verifyToken(JsonTokenType.COLON);
            stringFromExtendedJson = stringFromExtendedJson4;
            stringFromExtendedJson2 = readStringFromExtendedJson();
        } else {
            throw new JsonParseException("Expected 't' and 'i' fields in $timestamp document but found " + stringFromExtendedJson3);
        }
        verifyToken(JsonTokenType.END_OBJECT);
        verifyToken(JsonTokenType.END_OBJECT);
        return new BsonRegularExpression(stringFromExtendedJson2, stringFromExtendedJson);
    }

    private BsonRegularExpression visitRegularExpressionExtendedJson(String str) {
        String stringFromExtendedJson;
        String stringFromExtendedJson2;
        Mark mark = new Mark();
        try {
            try {
                verifyToken(JsonTokenType.COLON);
                if (str.equals("$regex")) {
                    stringFromExtendedJson2 = readStringFromExtendedJson();
                    verifyToken(JsonTokenType.COMMA);
                    verifyString("$options");
                    verifyToken(JsonTokenType.COLON);
                    stringFromExtendedJson = readStringFromExtendedJson();
                } else {
                    String stringFromExtendedJson3 = readStringFromExtendedJson();
                    verifyToken(JsonTokenType.COMMA);
                    verifyString("$regex");
                    verifyToken(JsonTokenType.COLON);
                    stringFromExtendedJson = stringFromExtendedJson3;
                    stringFromExtendedJson2 = readStringFromExtendedJson();
                }
                verifyToken(JsonTokenType.END_OBJECT);
                return new BsonRegularExpression(stringFromExtendedJson2, stringFromExtendedJson);
            } catch (JsonParseException unused) {
                mark.reset();
                mark.discard();
                return null;
            }
        } finally {
            mark.discard();
        }
    }

    private String readStringFromExtendedJson() {
        JsonToken jsonTokenPopToken = popToken();
        if (jsonTokenPopToken.getType() != JsonTokenType.STRING) {
            throw new JsonParseException("JSON reader expected a string but found '%s'.", jsonTokenPopToken.getValue());
        }
        return (String) jsonTokenPopToken.getValue(String.class);
    }

    private String visitSymbolExtendedJson() {
        verifyToken(JsonTokenType.COLON);
        String stringFromExtendedJson = readStringFromExtendedJson();
        verifyToken(JsonTokenType.END_OBJECT);
        return stringFromExtendedJson;
    }

    private BsonTimestamp visitTimestampExtendedJson() {
        int intFromExtendedJson;
        int intFromExtendedJson2;
        verifyToken(JsonTokenType.COLON);
        verifyToken(JsonTokenType.BEGIN_OBJECT);
        String stringFromExtendedJson = readStringFromExtendedJson();
        if (stringFromExtendedJson.equals(GlobalVariable.t)) {
            verifyToken(JsonTokenType.COLON);
            intFromExtendedJson2 = readIntFromExtendedJson();
            verifyToken(JsonTokenType.COMMA);
            verifyString("i");
            verifyToken(JsonTokenType.COLON);
            intFromExtendedJson = readIntFromExtendedJson();
        } else if (stringFromExtendedJson.equals("i")) {
            verifyToken(JsonTokenType.COLON);
            int intFromExtendedJson3 = readIntFromExtendedJson();
            verifyToken(JsonTokenType.COMMA);
            verifyString(GlobalVariable.t);
            verifyToken(JsonTokenType.COLON);
            intFromExtendedJson = intFromExtendedJson3;
            intFromExtendedJson2 = readIntFromExtendedJson();
        } else {
            throw new JsonParseException("Expected 't' and 'i' fields in $timestamp document but found " + stringFromExtendedJson);
        }
        verifyToken(JsonTokenType.END_OBJECT);
        verifyToken(JsonTokenType.END_OBJECT);
        return new BsonTimestamp(intFromExtendedJson2, intFromExtendedJson);
    }

    private int readIntFromExtendedJson() {
        JsonToken jsonTokenPopToken = popToken();
        if (jsonTokenPopToken.getType() == JsonTokenType.INT32) {
            return ((Integer) jsonTokenPopToken.getValue(Integer.class)).intValue();
        }
        if (jsonTokenPopToken.getType() == JsonTokenType.INT64) {
            return ((Long) jsonTokenPopToken.getValue(Long.class)).intValue();
        }
        throw new JsonParseException("JSON reader expected an integer but found '%s'.", jsonTokenPopToken.getValue());
    }

    private void visitJavaScriptExtendedJson() {
        verifyToken(JsonTokenType.COLON);
        String stringFromExtendedJson = readStringFromExtendedJson();
        JsonToken jsonTokenPopToken = popToken();
        int i = AnonymousClass1.$SwitchMap$org$bson$json$JsonTokenType[jsonTokenPopToken.getType().ordinal()];
        if (i == 3) {
            this.currentValue = stringFromExtendedJson;
            setCurrentBsonType(BsonType.JAVASCRIPT);
        } else {
            if (i == 11) {
                verifyString("$scope");
                verifyToken(JsonTokenType.COLON);
                setState(AbstractBsonReader.State.VALUE);
                this.currentValue = stringFromExtendedJson;
                setCurrentBsonType(BsonType.JAVASCRIPT_WITH_SCOPE);
                setContext(new Context(getContext(), BsonContextType.SCOPE_DOCUMENT));
                return;
            }
            throw new JsonParseException("JSON reader expected ',' or '}' but found '%s'.", jsonTokenPopToken);
        }
    }

    private BsonUndefined visitUndefinedExtendedJson() {
        verifyToken(JsonTokenType.COLON);
        JsonToken jsonTokenPopToken = popToken();
        if (!((String) jsonTokenPopToken.getValue(String.class)).equals("true")) {
            throw new JsonParseException("JSON reader requires $undefined to have the value of true but found '%s'.", jsonTokenPopToken.getValue());
        }
        verifyToken(JsonTokenType.END_OBJECT);
        return new BsonUndefined();
    }

    private Long visitNumberLongExtendedJson() {
        verifyToken(JsonTokenType.COLON);
        String stringFromExtendedJson = readStringFromExtendedJson();
        try {
            Long lValueOf = Long.valueOf(stringFromExtendedJson);
            verifyToken(JsonTokenType.END_OBJECT);
            return lValueOf;
        } catch (NumberFormatException e) {
            throw new JsonParseException(String.format("Exception converting value '%s' to type %s", stringFromExtendedJson, Long.class.getName()), e);
        }
    }

    private Integer visitNumberIntExtendedJson() {
        verifyToken(JsonTokenType.COLON);
        String stringFromExtendedJson = readStringFromExtendedJson();
        try {
            Integer numValueOf = Integer.valueOf(stringFromExtendedJson);
            verifyToken(JsonTokenType.END_OBJECT);
            return numValueOf;
        } catch (NumberFormatException e) {
            throw new JsonParseException(String.format("Exception converting value '%s' to type %s", stringFromExtendedJson, Integer.class.getName()), e);
        }
    }

    private Double visitNumberDoubleExtendedJson() {
        verifyToken(JsonTokenType.COLON);
        String stringFromExtendedJson = readStringFromExtendedJson();
        try {
            Double dValueOf = Double.valueOf(stringFromExtendedJson);
            verifyToken(JsonTokenType.END_OBJECT);
            return dValueOf;
        } catch (NumberFormatException e) {
            throw new JsonParseException(String.format("Exception converting value '%s' to type %s", stringFromExtendedJson, Double.class.getName()), e);
        }
    }

    private Decimal128 visitNumberDecimalExtendedJson() {
        verifyToken(JsonTokenType.COLON);
        String stringFromExtendedJson = readStringFromExtendedJson();
        try {
            Decimal128 decimal128 = Decimal128.parse(stringFromExtendedJson);
            verifyToken(JsonTokenType.END_OBJECT);
            return decimal128;
        } catch (NumberFormatException e) {
            throw new JsonParseException(String.format("Exception converting value '%s' to type %s", stringFromExtendedJson, Decimal128.class.getName()), e);
        }
    }

    private BsonDbPointer visitDbPointerExtendedJson() {
        ObjectId dbPointerIdFromExtendedJson;
        String stringFromExtendedJson;
        verifyToken(JsonTokenType.COLON);
        verifyToken(JsonTokenType.BEGIN_OBJECT);
        String stringFromExtendedJson2 = readStringFromExtendedJson();
        if (stringFromExtendedJson2.equals("$ref")) {
            verifyToken(JsonTokenType.COLON);
            stringFromExtendedJson = readStringFromExtendedJson();
            verifyToken(JsonTokenType.COMMA);
            verifyString("$id");
            dbPointerIdFromExtendedJson = readDbPointerIdFromExtendedJson();
            verifyToken(JsonTokenType.END_OBJECT);
        } else if (stringFromExtendedJson2.equals("$id")) {
            ObjectId dbPointerIdFromExtendedJson2 = readDbPointerIdFromExtendedJson();
            verifyToken(JsonTokenType.COMMA);
            verifyString("$ref");
            verifyToken(JsonTokenType.COLON);
            dbPointerIdFromExtendedJson = dbPointerIdFromExtendedJson2;
            stringFromExtendedJson = readStringFromExtendedJson();
        } else {
            throw new JsonParseException("Expected $ref and $id fields in $dbPointer document but found " + stringFromExtendedJson2);
        }
        verifyToken(JsonTokenType.END_OBJECT);
        return new BsonDbPointer(stringFromExtendedJson, dbPointerIdFromExtendedJson);
    }

    private ObjectId readDbPointerIdFromExtendedJson() {
        verifyToken(JsonTokenType.COLON);
        verifyToken(JsonTokenType.BEGIN_OBJECT);
        verifyToken(JsonTokenType.STRING, "$oid");
        return visitObjectIdExtendedJson();
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
        private final Object currentValue;
        private final int markPos;
        private final JsonToken pushedToken;

        protected Mark() {
            super();
            this.pushedToken = JsonReader.this.pushedToken;
            this.currentValue = JsonReader.this.currentValue;
            this.markPos = JsonReader.this.scanner.mark();
        }

        @Override // org.bson.AbstractBsonReader.Mark, org.bson.BsonReaderMark
        public void reset() {
            super.reset();
            JsonReader.this.pushedToken = this.pushedToken;
            JsonReader.this.currentValue = this.currentValue;
            JsonReader.this.scanner.reset(this.markPos);
            JsonReader.this.setContext(JsonReader.this.new Context(getParentContext(), getContextType()));
        }

        public void discard() {
            JsonReader.this.scanner.discard(this.markPos);
        }
    }

    protected class Context extends AbstractBsonReader.Context {
        protected Context(AbstractBsonReader.Context context, BsonContextType bsonContextType) {
            super(context, bsonContextType);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // org.bson.AbstractBsonReader.Context
        public Context getParentContext() {
            return (Context) super.getParentContext();
        }

        @Override // org.bson.AbstractBsonReader.Context
        protected BsonContextType getContextType() {
            return super.getContextType();
        }
    }

    private static byte[] decodeHex(String str) {
        if (str.length() % 2 != 0) {
            throw new IllegalArgumentException("A hex string must contain an even number of characters: " + str);
        }
        byte[] bArr = new byte[str.length() / 2];
        for (int i = 0; i < str.length(); i += 2) {
            int iDigit = Character.digit(str.charAt(i), 16);
            int iDigit2 = Character.digit(str.charAt(i + 1), 16);
            if (iDigit == -1 || iDigit2 == -1) {
                throw new IllegalArgumentException("A hex string can only contain the characters 0-9, A-F, a-f: " + str);
            }
            bArr[i / 2] = (byte) ((iDigit * 16) + iDigit2);
        }
        return bArr;
    }
}
