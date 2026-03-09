package org.bson.codecs.pojo;

import j$.util.concurrent.ConcurrentHashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.bson.codecs.configuration.CodecConfigurationException;

/* JADX INFO: loaded from: classes4.dex */
final class DiscriminatorLookup {
    private final Map<String, Class<?>> discriminatorClassMap = new ConcurrentHashMap();
    private final Set<String> packages;

    DiscriminatorLookup(Map<Class<?>, ClassModel<?>> map, Set<String> set) {
        for (ClassModel<?> classModel : map.values()) {
            if (classModel.getDiscriminator() != null) {
                this.discriminatorClassMap.put(classModel.getDiscriminator(), classModel.getType());
            }
        }
        this.packages = set;
    }

    public Class<?> lookup(String str) {
        if (this.discriminatorClassMap.containsKey(str)) {
            return this.discriminatorClassMap.get(str);
        }
        Class<?> classForName = getClassForName(str);
        if (classForName == null) {
            classForName = searchPackages(str);
        }
        if (classForName == null) {
            throw new CodecConfigurationException(String.format("A class could not be found for the discriminator: '%s'.", str));
        }
        this.discriminatorClassMap.put(str, classForName);
        return classForName;
    }

    void addClassModel(ClassModel<?> classModel) {
        if (classModel.getDiscriminator() != null) {
            this.discriminatorClassMap.put(classModel.getDiscriminator(), classModel.getType());
        }
    }

    private Class<?> getClassForName(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException unused) {
            return null;
        }
    }

    private Class<?> searchPackages(String str) {
        Iterator<String> it = this.packages.iterator();
        Class<?> classForName = null;
        while (it.hasNext()) {
            classForName = getClassForName(it.next() + "." + str);
            if (classForName != null) {
                break;
            }
        }
        return classForName;
    }
}
