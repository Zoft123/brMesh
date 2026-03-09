package org.bson.codecs.pojo;

import org.bson.codecs.configuration.CodecConfigurationException;

/* JADX INFO: loaded from: classes4.dex */
final class PropertyAccessorImpl<T> implements PropertyAccessor<T> {
    private final PropertyMetadata<T> propertyMetadata;

    PropertyAccessorImpl(PropertyMetadata<T> propertyMetadata) {
        this.propertyMetadata = propertyMetadata;
    }

    @Override // org.bson.codecs.pojo.PropertyAccessor
    public <S> T get(S s) {
        try {
            if (this.propertyMetadata.isSerializable()) {
                if (this.propertyMetadata.getGetter() != null) {
                    return (T) this.propertyMetadata.getGetter().invoke(s, null);
                }
                return (T) this.propertyMetadata.getField().get(s);
            }
            throw getError(null);
        } catch (Exception e) {
            throw getError(e);
        }
    }

    @Override // org.bson.codecs.pojo.PropertyAccessor
    public <S> void set(S s, T t) {
        try {
            if (this.propertyMetadata.isDeserializable()) {
                if (this.propertyMetadata.getSetter() != null) {
                    this.propertyMetadata.getSetter().invoke(s, t);
                } else {
                    this.propertyMetadata.getField().set(s, t);
                }
            }
        } catch (Exception e) {
            throw setError(e);
        }
    }

    PropertyMetadata<T> getPropertyMetadata() {
        return this.propertyMetadata;
    }

    private CodecConfigurationException getError(Exception exc) {
        return new CodecConfigurationException(String.format("Unable to get value for property '%s' in %s", this.propertyMetadata.getName(), this.propertyMetadata.getDeclaringClassName()), exc);
    }

    private CodecConfigurationException setError(Exception exc) {
        return new CodecConfigurationException(String.format("Unable to set value for property '%s' in %s", this.propertyMetadata.getName(), this.propertyMetadata.getDeclaringClassName()), exc);
    }
}
