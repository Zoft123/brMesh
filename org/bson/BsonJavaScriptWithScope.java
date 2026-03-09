package org.bson;

/* JADX INFO: loaded from: classes4.dex */
public class BsonJavaScriptWithScope extends BsonValue {
    private final String code;
    private final BsonDocument scope;

    public BsonJavaScriptWithScope(String str, BsonDocument bsonDocument) {
        if (str == null) {
            throw new IllegalArgumentException("code can not be null");
        }
        if (bsonDocument == null) {
            throw new IllegalArgumentException("scope can not be null");
        }
        this.code = str;
        this.scope = bsonDocument;
    }

    @Override // org.bson.BsonValue
    public BsonType getBsonType() {
        return BsonType.JAVASCRIPT_WITH_SCOPE;
    }

    public String getCode() {
        return this.code;
    }

    public BsonDocument getScope() {
        return this.scope;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        BsonJavaScriptWithScope bsonJavaScriptWithScope = (BsonJavaScriptWithScope) obj;
        return this.code.equals(bsonJavaScriptWithScope.code) && this.scope.equals(bsonJavaScriptWithScope.scope);
    }

    public int hashCode() {
        return (this.code.hashCode() * 31) + this.scope.hashCode();
    }

    public String toString() {
        return "BsonJavaScriptWithScope{code=" + getCode() + "scope=" + this.scope + '}';
    }

    static BsonJavaScriptWithScope clone(BsonJavaScriptWithScope bsonJavaScriptWithScope) {
        return new BsonJavaScriptWithScope(bsonJavaScriptWithScope.code, bsonJavaScriptWithScope.scope.clone());
    }
}
