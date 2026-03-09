package org.bson;

/* JADX INFO: loaded from: classes4.dex */
public abstract class BsonValue {
    public abstract BsonType getBsonType();

    BsonValue() {
    }

    public BsonDocument asDocument() {
        throwIfInvalidType(BsonType.DOCUMENT);
        return (BsonDocument) this;
    }

    public BsonArray asArray() {
        throwIfInvalidType(BsonType.ARRAY);
        return (BsonArray) this;
    }

    public BsonString asString() {
        throwIfInvalidType(BsonType.STRING);
        return (BsonString) this;
    }

    public BsonNumber asNumber() {
        if (getBsonType() != BsonType.INT32 && getBsonType() != BsonType.INT64 && getBsonType() != BsonType.DOUBLE) {
            throw new BsonInvalidOperationException(String.format("Value expected to be of a numerical BSON type is of unexpected type %s", getBsonType()));
        }
        return (BsonNumber) this;
    }

    public BsonInt32 asInt32() {
        throwIfInvalidType(BsonType.INT32);
        return (BsonInt32) this;
    }

    public BsonInt64 asInt64() {
        throwIfInvalidType(BsonType.INT64);
        return (BsonInt64) this;
    }

    public BsonDecimal128 asDecimal128() {
        throwIfInvalidType(BsonType.DECIMAL128);
        return (BsonDecimal128) this;
    }

    public BsonDouble asDouble() {
        throwIfInvalidType(BsonType.DOUBLE);
        return (BsonDouble) this;
    }

    public BsonBoolean asBoolean() {
        throwIfInvalidType(BsonType.BOOLEAN);
        return (BsonBoolean) this;
    }

    public BsonObjectId asObjectId() {
        throwIfInvalidType(BsonType.OBJECT_ID);
        return (BsonObjectId) this;
    }

    public BsonDbPointer asDBPointer() {
        throwIfInvalidType(BsonType.DB_POINTER);
        return (BsonDbPointer) this;
    }

    public BsonTimestamp asTimestamp() {
        throwIfInvalidType(BsonType.TIMESTAMP);
        return (BsonTimestamp) this;
    }

    public BsonBinary asBinary() {
        throwIfInvalidType(BsonType.BINARY);
        return (BsonBinary) this;
    }

    public BsonDateTime asDateTime() {
        throwIfInvalidType(BsonType.DATE_TIME);
        return (BsonDateTime) this;
    }

    public BsonSymbol asSymbol() {
        throwIfInvalidType(BsonType.SYMBOL);
        return (BsonSymbol) this;
    }

    public BsonRegularExpression asRegularExpression() {
        throwIfInvalidType(BsonType.REGULAR_EXPRESSION);
        return (BsonRegularExpression) this;
    }

    public BsonJavaScript asJavaScript() {
        throwIfInvalidType(BsonType.JAVASCRIPT);
        return (BsonJavaScript) this;
    }

    public BsonJavaScriptWithScope asJavaScriptWithScope() {
        throwIfInvalidType(BsonType.JAVASCRIPT_WITH_SCOPE);
        return (BsonJavaScriptWithScope) this;
    }

    public boolean isNull() {
        return this instanceof BsonNull;
    }

    public boolean isDocument() {
        return this instanceof BsonDocument;
    }

    public boolean isArray() {
        return this instanceof BsonArray;
    }

    public boolean isString() {
        return this instanceof BsonString;
    }

    public boolean isNumber() {
        return isInt32() || isInt64() || isDouble();
    }

    public boolean isInt32() {
        return this instanceof BsonInt32;
    }

    public boolean isInt64() {
        return this instanceof BsonInt64;
    }

    public boolean isDecimal128() {
        return this instanceof BsonDecimal128;
    }

    public boolean isDouble() {
        return this instanceof BsonDouble;
    }

    public boolean isBoolean() {
        return this instanceof BsonBoolean;
    }

    public boolean isObjectId() {
        return this instanceof BsonObjectId;
    }

    public boolean isDBPointer() {
        return this instanceof BsonDbPointer;
    }

    public boolean isTimestamp() {
        return this instanceof BsonTimestamp;
    }

    public boolean isBinary() {
        return this instanceof BsonBinary;
    }

    public boolean isDateTime() {
        return this instanceof BsonDateTime;
    }

    public boolean isSymbol() {
        return this instanceof BsonSymbol;
    }

    public boolean isRegularExpression() {
        return this instanceof BsonRegularExpression;
    }

    public boolean isJavaScript() {
        return this instanceof BsonJavaScript;
    }

    public boolean isJavaScriptWithScope() {
        return this instanceof BsonJavaScriptWithScope;
    }

    private void throwIfInvalidType(BsonType bsonType) {
        if (getBsonType() != bsonType) {
            throw new BsonInvalidOperationException(String.format("Value expected to be of type %s is of unexpected type %s", bsonType, getBsonType()));
        }
    }
}
