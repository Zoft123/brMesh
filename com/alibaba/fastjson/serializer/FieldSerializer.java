package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
import com.alibaba.fastjson.util.FieldInfo;
import com.alibaba.fastjson.util.TypeUtils;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;

/* JADX INFO: loaded from: classes.dex */
public class FieldSerializer implements Comparable<FieldSerializer> {
    protected boolean browserCompatible;
    protected boolean disableCircularReferenceDetect;
    private final String double_quoted_fieldPrefix;
    protected int features;
    protected BeanContext fieldContext;
    public final FieldInfo fieldInfo;
    private String format;
    protected boolean persistenceXToMany;
    private RuntimeSerializerInfo runtimeInfo;
    protected boolean serializeUsing = false;
    private String single_quoted_fieldPrefix;
    private String un_quoted_fieldPrefix;
    protected boolean writeEnumUsingName;
    protected boolean writeEnumUsingToString;
    protected final boolean writeNull;

    public FieldSerializer(Class<?> cls, FieldInfo fieldInfo) {
        boolean z;
        JSONType jSONType;
        this.writeEnumUsingToString = false;
        this.writeEnumUsingName = false;
        this.disableCircularReferenceDetect = false;
        this.persistenceXToMany = false;
        this.fieldInfo = fieldInfo;
        this.fieldContext = new BeanContext(cls, fieldInfo);
        if (cls != null && ((fieldInfo.isEnum || fieldInfo.fieldClass == Long.TYPE || fieldInfo.fieldClass == Long.class || fieldInfo.fieldClass == BigInteger.class || fieldInfo.fieldClass == BigDecimal.class) && (jSONType = (JSONType) TypeUtils.getAnnotation(cls, JSONType.class)) != null)) {
            for (SerializerFeature serializerFeature : jSONType.serialzeFeatures()) {
                if (serializerFeature == SerializerFeature.WriteEnumUsingToString) {
                    this.writeEnumUsingToString = true;
                } else if (serializerFeature == SerializerFeature.WriteEnumUsingName) {
                    this.writeEnumUsingName = true;
                } else if (serializerFeature == SerializerFeature.DisableCircularReferenceDetect) {
                    this.disableCircularReferenceDetect = true;
                } else if (serializerFeature == SerializerFeature.BrowserCompatible) {
                    this.features |= SerializerFeature.BrowserCompatible.mask;
                    this.browserCompatible = true;
                }
            }
        }
        fieldInfo.setAccessible();
        this.double_quoted_fieldPrefix = "\"" + fieldInfo.name + "\":";
        JSONField annotation = fieldInfo.getAnnotation();
        if (annotation != null) {
            SerializerFeature[] serializerFeatureArrSerialzeFeatures = annotation.serialzeFeatures();
            int length = serializerFeatureArrSerialzeFeatures.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    z = false;
                    break;
                } else {
                    if ((serializerFeatureArrSerialzeFeatures[i].getMask() & SerializerFeature.WRITE_MAP_NULL_FEATURES) != 0) {
                        z = true;
                        break;
                    }
                    i++;
                }
            }
            String str = annotation.format();
            this.format = str;
            if (str.trim().length() == 0) {
                this.format = null;
            }
            for (SerializerFeature serializerFeature2 : annotation.serialzeFeatures()) {
                if (serializerFeature2 == SerializerFeature.WriteEnumUsingToString) {
                    this.writeEnumUsingToString = true;
                } else if (serializerFeature2 == SerializerFeature.WriteEnumUsingName) {
                    this.writeEnumUsingName = true;
                } else if (serializerFeature2 == SerializerFeature.DisableCircularReferenceDetect) {
                    this.disableCircularReferenceDetect = true;
                } else if (serializerFeature2 == SerializerFeature.BrowserCompatible) {
                    this.browserCompatible = true;
                }
            }
            this.features = SerializerFeature.of(annotation.serialzeFeatures());
        } else {
            z = false;
        }
        this.writeNull = z;
        this.persistenceXToMany = TypeUtils.isAnnotationPresentOneToMany(fieldInfo.method) || TypeUtils.isAnnotationPresentManyToMany(fieldInfo.method);
    }

    public void writePrefix(JSONSerializer jSONSerializer) throws IOException {
        SerializeWriter serializeWriter = jSONSerializer.out;
        if (serializeWriter.quoteFieldNames) {
            if (serializeWriter.useSingleQuotes) {
                if (this.single_quoted_fieldPrefix == null) {
                    this.single_quoted_fieldPrefix = "'" + this.fieldInfo.name + "':";
                }
                serializeWriter.write(this.single_quoted_fieldPrefix);
                return;
            }
            serializeWriter.write(this.double_quoted_fieldPrefix);
            return;
        }
        if (this.un_quoted_fieldPrefix == null) {
            this.un_quoted_fieldPrefix = this.fieldInfo.name + ":";
        }
        serializeWriter.write(this.un_quoted_fieldPrefix);
    }

    public Object getPropertyValueDirect(Object obj) throws IllegalAccessException, InvocationTargetException {
        Object obj2 = this.fieldInfo.get(obj);
        if (!this.persistenceXToMany || TypeUtils.isHibernateInitialized(obj2)) {
            return obj2;
        }
        return null;
    }

    public Object getPropertyValue(Object obj) throws IllegalAccessException, InvocationTargetException {
        Object obj2 = this.fieldInfo.get(obj);
        if (this.format == null || obj2 == null || this.fieldInfo.fieldClass != Date.class) {
            return obj2;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(this.format, JSON.defaultLocale);
        simpleDateFormat.setTimeZone(JSON.defaultTimeZone);
        return simpleDateFormat.format(obj2);
    }

    @Override // java.lang.Comparable
    public int compareTo(FieldSerializer fieldSerializer) {
        return this.fieldInfo.compareTo(fieldSerializer.fieldInfo);
    }

    /* JADX WARN: Removed duplicated region for block: B:45:0x0082  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x0085  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void writeValue(com.alibaba.fastjson.serializer.JSONSerializer r11, java.lang.Object r12) throws java.lang.Exception {
        /*
            Method dump skipped, instruction units count: 534
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.serializer.FieldSerializer.writeValue(com.alibaba.fastjson.serializer.JSONSerializer, java.lang.Object):void");
    }

    static class RuntimeSerializerInfo {
        final ObjectSerializer fieldSerializer;
        final Class<?> runtimeFieldClass;

        public RuntimeSerializerInfo(ObjectSerializer objectSerializer, Class<?> cls) {
            this.fieldSerializer = objectSerializer;
            this.runtimeFieldClass = cls;
        }
    }
}
