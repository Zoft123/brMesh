package org.bson.codecs.pojo;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.bson.codecs.configuration.CodecConfigurationException;

/* JADX INFO: loaded from: classes4.dex */
final class InstanceCreatorImpl<T> implements InstanceCreator<T> {
    private final Map<PropertyModel<?>, Object> cachedValues;
    private final CreatorExecutable<T> creatorExecutable;
    private T newInstance;
    private final Object[] params;
    private final Map<String, Integer> properties;

    InstanceCreatorImpl(CreatorExecutable<T> creatorExecutable) {
        this.creatorExecutable = creatorExecutable;
        if (creatorExecutable.getProperties().isEmpty()) {
            this.cachedValues = null;
            this.properties = null;
            this.params = null;
            this.newInstance = creatorExecutable.getInstance();
            return;
        }
        this.cachedValues = new HashMap();
        this.properties = new HashMap();
        for (int i = 0; i < creatorExecutable.getProperties().size(); i++) {
            if (creatorExecutable.getIdPropertyIndex() != null && creatorExecutable.getIdPropertyIndex().intValue() == i) {
                this.properties.put("_id", creatorExecutable.getIdPropertyIndex());
            } else {
                this.properties.put(creatorExecutable.getProperties().get(i).value(), Integer.valueOf(i));
            }
        }
        this.params = new Object[this.properties.size()];
    }

    @Override // org.bson.codecs.pojo.InstanceCreator
    public <S> void set(S s, PropertyModel<S> propertyModel) {
        if (this.newInstance != null) {
            propertyModel.getPropertyAccessor().set(this.newInstance, s);
            return;
        }
        if (!this.properties.isEmpty()) {
            String writeName = propertyModel.getWriteName();
            if (!this.properties.containsKey(writeName)) {
                writeName = propertyModel.getName();
            }
            Integer num = this.properties.get(writeName);
            if (num != null) {
                this.params[num.intValue()] = s;
            }
            this.properties.remove(writeName);
        }
        if (this.properties.isEmpty()) {
            constructInstanceAndProcessCachedValues();
        } else {
            this.cachedValues.put(propertyModel, s);
        }
    }

    @Override // org.bson.codecs.pojo.InstanceCreator
    public T getInstance() {
        if (this.newInstance == null) {
            try {
                Iterator<Map.Entry<String, Integer>> it = this.properties.entrySet().iterator();
                while (it.hasNext()) {
                    this.params[it.next().getValue().intValue()] = null;
                }
                constructInstanceAndProcessCachedValues();
            } catch (CodecConfigurationException e) {
                throw new CodecConfigurationException(String.format("Could not construct new instance of: %s. Missing the following properties: %s", this.creatorExecutable.getType().getSimpleName(), this.properties.keySet()), e);
            }
        }
        return this.newInstance;
    }

    private void constructInstanceAndProcessCachedValues() {
        try {
            this.newInstance = this.creatorExecutable.getInstance(this.params);
            for (Map.Entry<PropertyModel<?>, Object> entry : this.cachedValues.entrySet()) {
                setPropertyValue((PropertyModel) entry.getKey(), entry.getValue());
            }
        } catch (Exception e) {
            throw new CodecConfigurationException(e.getMessage(), e);
        }
    }

    private <S> void setPropertyValue(PropertyModel<S> propertyModel, Object obj) {
        set(obj, propertyModel);
    }
}
