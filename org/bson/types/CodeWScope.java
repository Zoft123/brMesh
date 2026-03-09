package org.bson.types;

import org.bson.BSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class CodeWScope extends Code {
    private static final long serialVersionUID = -6284832275113680002L;
    private final BSONObject scope;

    public CodeWScope(String str, BSONObject bSONObject) {
        super(str);
        this.scope = bSONObject;
    }

    public BSONObject getScope() {
        return this.scope;
    }

    @Override // org.bson.types.Code
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        CodeWScope codeWScope = (CodeWScope) obj;
        return getCode().equals(codeWScope.getCode()) && this.scope.equals(codeWScope.scope);
    }

    @Override // org.bson.types.Code
    public int hashCode() {
        return getCode().hashCode() ^ this.scope.hashCode();
    }
}
