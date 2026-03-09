package org.bson.codecs.pojo;

import com.brgd.brblmesh.GeneralClass.GlobalVariable;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.bson.assertions.Assertions;
import org.bson.codecs.configuration.CodecConfigurationException;

/* JADX INFO: loaded from: classes4.dex */
public class ClassModelBuilder<T> {
    static final String ID_PROPERTY_NAME = "_id";
    private String discriminator;
    private boolean discriminatorEnabled;
    private String discriminatorKey;
    private IdGenerator<?> idGenerator;
    private String idPropertyName;
    private InstanceCreatorFactory<T> instanceCreatorFactory;
    private Class<T> type;
    private final List<PropertyModelBuilder<?>> propertyModelBuilders = new ArrayList();
    private Map<String, TypeParameterMap> propertyNameToTypeParameterMap = Collections.EMPTY_MAP;
    private List<Convention> conventions = Conventions.DEFAULT_CONVENTIONS;
    private List<Annotation> annotations = Collections.EMPTY_LIST;

    ClassModelBuilder(Class<T> cls) {
        PojoBuilderHelper.configureClassModelBuilder(this, (Class) Assertions.notNull(GlobalVariable.TYPE, cls));
    }

    public ClassModelBuilder<T> idGenerator(IdGenerator<?> idGenerator) {
        this.idGenerator = idGenerator;
        return this;
    }

    public IdGenerator<?> getIdGenerator() {
        return this.idGenerator;
    }

    public ClassModelBuilder<T> instanceCreatorFactory(InstanceCreatorFactory<T> instanceCreatorFactory) {
        this.instanceCreatorFactory = (InstanceCreatorFactory) Assertions.notNull("instanceCreatorFactory", instanceCreatorFactory);
        return this;
    }

    public InstanceCreatorFactory<T> getInstanceCreatorFactory() {
        return this.instanceCreatorFactory;
    }

    public ClassModelBuilder<T> type(Class<T> cls) {
        this.type = (Class) Assertions.notNull(GlobalVariable.TYPE, cls);
        return this;
    }

    public Class<T> getType() {
        return this.type;
    }

    public ClassModelBuilder<T> conventions(List<Convention> list) {
        this.conventions = (List) Assertions.notNull("conventions", list);
        return this;
    }

    public List<Convention> getConventions() {
        return this.conventions;
    }

    public ClassModelBuilder<T> annotations(List<Annotation> list) {
        this.annotations = (List) Assertions.notNull("annotations", list);
        return this;
    }

    public List<Annotation> getAnnotations() {
        return this.annotations;
    }

    public ClassModelBuilder<T> discriminator(String str) {
        this.discriminator = str;
        return this;
    }

    public String getDiscriminator() {
        return this.discriminator;
    }

    public ClassModelBuilder<T> discriminatorKey(String str) {
        this.discriminatorKey = str;
        return this;
    }

    public String getDiscriminatorKey() {
        return this.discriminatorKey;
    }

    public ClassModelBuilder<T> enableDiscriminator(boolean z) {
        this.discriminatorEnabled = z;
        return this;
    }

    public Boolean useDiscriminator() {
        return Boolean.valueOf(this.discriminatorEnabled);
    }

    public ClassModelBuilder<T> idPropertyName(String str) {
        this.idPropertyName = str;
        return this;
    }

    public String getIdPropertyName() {
        return this.idPropertyName;
    }

    public boolean removeProperty(String str) {
        return this.propertyModelBuilders.remove(getProperty((String) Assertions.notNull("propertyName", str)));
    }

    public PropertyModelBuilder<?> getProperty(String str) {
        Assertions.notNull("propertyName", str);
        for (PropertyModelBuilder<?> propertyModelBuilder : this.propertyModelBuilders) {
            if (propertyModelBuilder.getName().equals(str)) {
                return propertyModelBuilder;
            }
        }
        return null;
    }

    public List<PropertyModelBuilder<?>> getPropertyModelBuilders() {
        return Collections.unmodifiableList(this.propertyModelBuilders);
    }

    public ClassModel<T> build() {
        ArrayList arrayList = new ArrayList();
        PojoBuilderHelper.stateNotNull(GlobalVariable.TYPE, this.type);
        Iterator<Convention> it = this.conventions.iterator();
        while (it.hasNext()) {
            it.next().apply(this);
        }
        PojoBuilderHelper.stateNotNull("instanceCreatorFactory", this.instanceCreatorFactory);
        if (this.discriminatorEnabled) {
            PojoBuilderHelper.stateNotNull("discriminatorKey", this.discriminatorKey);
            PojoBuilderHelper.stateNotNull("discriminator", this.discriminator);
        }
        PropertyModel<?> propertyModel = null;
        for (PropertyModelBuilder<?> propertyModelBuilder : this.propertyModelBuilders) {
            boolean zEquals = propertyModelBuilder.getName().equals(this.idPropertyName);
            if (zEquals) {
                propertyModelBuilder.readName(ID_PROPERTY_NAME).writeName(ID_PROPERTY_NAME);
            }
            PropertyModel<?> propertyModelBuild = propertyModelBuilder.build();
            arrayList.add(propertyModelBuild);
            if (zEquals) {
                propertyModel = propertyModelBuild;
            }
        }
        validatePropertyModels(this.type.getSimpleName(), arrayList);
        return new ClassModel<>(this.type, this.propertyNameToTypeParameterMap, this.instanceCreatorFactory, Boolean.valueOf(this.discriminatorEnabled), this.discriminatorKey, this.discriminator, IdPropertyModelHolder.create(this.type, propertyModel, this.idGenerator), Collections.unmodifiableList(arrayList));
    }

    public String toString() {
        return String.format("ClassModelBuilder{type=%s}", this.type);
    }

    Map<String, TypeParameterMap> getPropertyNameToTypeParameterMap() {
        return this.propertyNameToTypeParameterMap;
    }

    ClassModelBuilder<T> propertyNameToTypeParameterMap(Map<String, TypeParameterMap> map) {
        this.propertyNameToTypeParameterMap = Collections.unmodifiableMap(new HashMap(map));
        return this;
    }

    ClassModelBuilder<T> addProperty(PropertyModelBuilder<?> propertyModelBuilder) {
        this.propertyModelBuilders.add((PropertyModelBuilder<?>) Assertions.notNull("propertyModelBuilder", propertyModelBuilder));
        return this;
    }

    private void validatePropertyModels(String str, List<PropertyModel<?>> list) {
        HashMap map = new HashMap();
        HashMap map2 = new HashMap();
        HashMap map3 = new HashMap();
        for (PropertyModel<?> propertyModel : list) {
            if (propertyModel.hasError()) {
                throw new CodecConfigurationException(propertyModel.getError());
            }
            checkForDuplicates("property", propertyModel.getName(), map, str);
            if (propertyModel.isReadable()) {
                checkForDuplicates("read property", propertyModel.getReadName(), map2, str);
            }
            if (propertyModel.isWritable()) {
                checkForDuplicates("write property", propertyModel.getWriteName(), map3, str);
            }
        }
        String str2 = this.idPropertyName;
        if (str2 != null && !map.containsKey(str2)) {
            throw new CodecConfigurationException(String.format("Invalid id property, property named '%s' can not be found.", this.idPropertyName));
        }
    }

    private void checkForDuplicates(String str, String str2, Map<String, Integer> map, String str3) {
        if (map.containsKey(str2)) {
            throw new CodecConfigurationException(String.format("Duplicate %s named '%s' found in %s.", str, str2, str3));
        }
        map.put(str2, 1);
    }
}
