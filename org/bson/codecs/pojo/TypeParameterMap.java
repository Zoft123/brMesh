package org.bson.codecs.pojo;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
final class TypeParameterMap {
    private final Map<Integer, Integer> propertyToClassParamIndexMap;

    static Builder builder() {
        return new Builder();
    }

    Map<Integer, Integer> getPropertyToClassParamIndexMap() {
        return this.propertyToClassParamIndexMap;
    }

    boolean hasTypeParameters() {
        return !this.propertyToClassParamIndexMap.isEmpty();
    }

    static final class Builder {
        private final Map<Integer, Integer> propertyToClassParamIndexMap;

        private Builder() {
            this.propertyToClassParamIndexMap = new HashMap();
        }

        Builder addIndex(int i) {
            this.propertyToClassParamIndexMap.put(-1, Integer.valueOf(i));
            return this;
        }

        Builder addIndex(int i, int i2) {
            this.propertyToClassParamIndexMap.put(Integer.valueOf(i), Integer.valueOf(i2));
            return this;
        }

        TypeParameterMap build() {
            if (this.propertyToClassParamIndexMap.size() > 1 && this.propertyToClassParamIndexMap.containsKey(-1)) {
                throw new IllegalStateException("You cannot have a generic field that also has type parameters.");
            }
            return new TypeParameterMap(this.propertyToClassParamIndexMap);
        }
    }

    public String toString() {
        return "TypeParameterMap{fieldToClassParamIndexMap=" + this.propertyToClassParamIndexMap + "}";
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && getClass() == obj.getClass() && getPropertyToClassParamIndexMap().equals(((TypeParameterMap) obj).getPropertyToClassParamIndexMap());
    }

    public int hashCode() {
        return getPropertyToClassParamIndexMap().hashCode();
    }

    private TypeParameterMap(Map<Integer, Integer> map) {
        this.propertyToClassParamIndexMap = Collections.unmodifiableMap(map);
    }
}
