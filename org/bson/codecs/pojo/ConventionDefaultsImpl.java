package org.bson.codecs.pojo;

/* JADX INFO: loaded from: classes4.dex */
final class ConventionDefaultsImpl implements Convention {
    ConventionDefaultsImpl() {
    }

    @Override // org.bson.codecs.pojo.Convention
    public void apply(ClassModelBuilder<?> classModelBuilder) {
        if (classModelBuilder.getDiscriminatorKey() == null) {
            classModelBuilder.discriminatorKey("_t");
        }
        if (classModelBuilder.getDiscriminator() == null && classModelBuilder.getType() != null) {
            classModelBuilder.discriminator(classModelBuilder.getType().getName());
        }
        for (PropertyModelBuilder<?> propertyModelBuilder : classModelBuilder.getPropertyModelBuilders()) {
            if (classModelBuilder.getIdPropertyName() == null) {
                String name = propertyModelBuilder.getName();
                if (name.equals("_id") || name.equals("id")) {
                    classModelBuilder.idPropertyName(name);
                }
            }
        }
    }
}
