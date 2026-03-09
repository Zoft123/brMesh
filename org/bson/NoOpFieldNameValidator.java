package org.bson;

/* JADX INFO: loaded from: classes4.dex */
class NoOpFieldNameValidator implements FieldNameValidator {
    @Override // org.bson.FieldNameValidator
    public FieldNameValidator getValidatorForField(String str) {
        return this;
    }

    @Override // org.bson.FieldNameValidator
    public boolean validate(String str) {
        return true;
    }

    NoOpFieldNameValidator() {
    }
}
