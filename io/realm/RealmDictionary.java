package io.realm;

import io.realm.RealmMap;
import io.realm.RealmMapEntrySet;
import io.realm.internal.OsMap;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.bson.types.Decimal128;
import org.bson.types.ObjectId;

/* JADX INFO: loaded from: classes4.dex */
public class RealmDictionary<V> extends RealmMap<String, V> {
    public RealmDictionary() {
    }

    public RealmDictionary(Map<String, V> map) {
        super(map);
    }

    RealmDictionary(BaseRealm baseRealm, OsMap osMap, Class<V> cls) {
        super((RealmMap.MapStrategy) getStrategy(cls, baseRealm, osMap));
    }

    RealmDictionary(BaseRealm baseRealm, OsMap osMap, String str) {
        super((RealmMap.MapStrategy) getStrategy(str, baseRealm, osMap));
    }

    private Map<String, V> toMap() {
        HashMap map = new HashMap();
        for (Map.Entry<String, V> entry : entrySet()) {
            map.put(entry.getKey(), entry.getValue());
        }
        return map;
    }

    private static <V extends RealmModel> LinkSelectorForMap<String, V> getRealmSelector(Class<V> cls, BaseRealm baseRealm, OsMap osMap) {
        return new LinkSelectorForMap<>(baseRealm, osMap, String.class, cls);
    }

    private static <V> RealmMap.ManagedMapStrategy<String, V> getStrategy(Class<V> cls, BaseRealm baseRealm, OsMap osMap) {
        if (CollectionUtils.isClassForRealmModel(cls)) {
            LinkSelectorForMap realmSelector = getRealmSelector(cls, baseRealm, osMap);
            return new RealmMap.ManagedMapStrategy<>(new DictionaryManager(baseRealm, new RealmModelValueOperator(baseRealm, osMap, realmSelector), realmSelector));
        }
        return new RealmMap.ManagedMapStrategy<>(getManager(cls, baseRealm, osMap));
    }

    private static <V> RealmMap.ManagedMapStrategy<String, V> getStrategy(String str, BaseRealm baseRealm, OsMap osMap) {
        return new RealmMap.ManagedMapStrategy<>(getManager(str, baseRealm, osMap));
    }

    private static <V> DictionaryManager<V> getManager(Class<V> cls, BaseRealm baseRealm, OsMap osMap) {
        BaseRealm baseRealm2;
        GenericPrimitiveValueOperator genericPrimitiveValueOperator;
        MapValueOperator byteValueOperator;
        SelectorForMap selectorForMap = new SelectorForMap(baseRealm, osMap, String.class, cls);
        if (cls == RealmAny.class) {
            byteValueOperator = new RealmAnyValueOperator(baseRealm, osMap, selectorForMap);
            baseRealm2 = baseRealm;
        } else {
            if (cls == Long.class) {
                baseRealm2 = baseRealm;
                genericPrimitiveValueOperator = new GenericPrimitiveValueOperator(Long.class, baseRealm2, osMap, selectorForMap, RealmMapEntrySet.IteratorType.LONG);
            } else {
                baseRealm2 = baseRealm;
                if (cls == Float.class) {
                    genericPrimitiveValueOperator = new GenericPrimitiveValueOperator(Float.class, baseRealm2, osMap, selectorForMap, RealmMapEntrySet.IteratorType.FLOAT);
                } else if (cls == Double.class) {
                    genericPrimitiveValueOperator = new GenericPrimitiveValueOperator(Double.class, baseRealm2, osMap, selectorForMap, RealmMapEntrySet.IteratorType.DOUBLE);
                } else if (cls == String.class) {
                    genericPrimitiveValueOperator = new GenericPrimitiveValueOperator(String.class, baseRealm2, osMap, selectorForMap, RealmMapEntrySet.IteratorType.STRING);
                } else if (cls == Boolean.class) {
                    genericPrimitiveValueOperator = new GenericPrimitiveValueOperator(Boolean.class, baseRealm2, osMap, selectorForMap, RealmMapEntrySet.IteratorType.BOOLEAN);
                } else if (cls == Date.class) {
                    genericPrimitiveValueOperator = new GenericPrimitiveValueOperator(Date.class, baseRealm2, osMap, selectorForMap, RealmMapEntrySet.IteratorType.DATE);
                } else if (cls == Decimal128.class) {
                    genericPrimitiveValueOperator = new GenericPrimitiveValueOperator(Decimal128.class, baseRealm2, osMap, selectorForMap, RealmMapEntrySet.IteratorType.DECIMAL128);
                } else if (cls == Integer.class) {
                    byteValueOperator = new IntegerValueOperator(baseRealm2, osMap, selectorForMap);
                } else if (cls == Short.class) {
                    byteValueOperator = new ShortValueOperator(baseRealm2, osMap, selectorForMap);
                } else if (cls == Byte.class) {
                    byteValueOperator = new ByteValueOperator(baseRealm2, osMap, selectorForMap);
                } else if (cls == byte[].class) {
                    genericPrimitiveValueOperator = new GenericPrimitiveValueOperator(byte[].class, baseRealm2, osMap, selectorForMap, RealmMapEntrySet.IteratorType.BINARY, new BinaryEquals());
                } else if (cls == ObjectId.class) {
                    genericPrimitiveValueOperator = new GenericPrimitiveValueOperator(ObjectId.class, baseRealm2, osMap, selectorForMap, RealmMapEntrySet.IteratorType.OBJECT_ID);
                } else if (cls == UUID.class) {
                    genericPrimitiveValueOperator = new GenericPrimitiveValueOperator(UUID.class, baseRealm2, osMap, selectorForMap, RealmMapEntrySet.IteratorType.UUID);
                } else {
                    throw new IllegalArgumentException("Only Maps of RealmAny or one of the types that can be boxed inside RealmAny can be used.");
                }
            }
            byteValueOperator = genericPrimitiveValueOperator;
        }
        return new DictionaryManager<>(baseRealm2, byteValueOperator, selectorForMap);
    }

    private static <V> DictionaryManager<V> getManager(String str, BaseRealm baseRealm, OsMap osMap) {
        BaseRealm baseRealm2;
        MapValueOperator realmModelValueOperator;
        GenericPrimitiveValueOperator genericPrimitiveValueOperator;
        if (str.equals(RealmAny.class.getCanonicalName())) {
            realmModelValueOperator = new RealmAnyValueOperator(baseRealm, osMap, new SelectorForMap(baseRealm, osMap, String.class, RealmAny.class));
            baseRealm2 = baseRealm;
        } else if (str.equals(Long.class.getCanonicalName())) {
            realmModelValueOperator = new GenericPrimitiveValueOperator(Long.class, baseRealm, osMap, new SelectorForMap(baseRealm, osMap, String.class, Long.class), RealmMapEntrySet.IteratorType.LONG);
            baseRealm2 = baseRealm;
        } else {
            baseRealm2 = baseRealm;
            if (str.equals(Float.class.getCanonicalName())) {
                genericPrimitiveValueOperator = new GenericPrimitiveValueOperator(Float.class, baseRealm2, osMap, new SelectorForMap(baseRealm2, osMap, String.class, Float.class), RealmMapEntrySet.IteratorType.FLOAT);
            } else if (str.equals(Double.class.getCanonicalName())) {
                genericPrimitiveValueOperator = new GenericPrimitiveValueOperator(Double.class, baseRealm2, osMap, new SelectorForMap(baseRealm2, osMap, String.class, Double.class), RealmMapEntrySet.IteratorType.DOUBLE);
            } else if (str.equals(String.class.getCanonicalName())) {
                genericPrimitiveValueOperator = new GenericPrimitiveValueOperator(String.class, baseRealm2, osMap, new SelectorForMap(baseRealm2, osMap, String.class, String.class), RealmMapEntrySet.IteratorType.STRING);
            } else if (str.equals(Boolean.class.getCanonicalName())) {
                genericPrimitiveValueOperator = new GenericPrimitiveValueOperator(Boolean.class, baseRealm2, osMap, new SelectorForMap(baseRealm2, osMap, String.class, Boolean.class), RealmMapEntrySet.IteratorType.BOOLEAN);
            } else if (str.equals(Date.class.getCanonicalName())) {
                genericPrimitiveValueOperator = new GenericPrimitiveValueOperator(Date.class, baseRealm2, osMap, new SelectorForMap(baseRealm2, osMap, String.class, Date.class), RealmMapEntrySet.IteratorType.DATE);
            } else if (str.equals(Decimal128.class.getCanonicalName())) {
                genericPrimitiveValueOperator = new GenericPrimitiveValueOperator(Decimal128.class, baseRealm2, osMap, new SelectorForMap(baseRealm2, osMap, String.class, Decimal128.class), RealmMapEntrySet.IteratorType.DECIMAL128);
            } else if (str.equals(Integer.class.getCanonicalName())) {
                realmModelValueOperator = new IntegerValueOperator(baseRealm2, osMap, new SelectorForMap(baseRealm2, osMap, String.class, Integer.class));
            } else if (str.equals(Short.class.getCanonicalName())) {
                realmModelValueOperator = new ShortValueOperator(baseRealm2, osMap, new SelectorForMap(baseRealm2, osMap, String.class, Short.class));
            } else if (str.equals(Byte.class.getCanonicalName())) {
                realmModelValueOperator = new ByteValueOperator(baseRealm2, osMap, new SelectorForMap(baseRealm2, osMap, String.class, Byte.class));
            } else if (str.equals(byte[].class.getCanonicalName())) {
                genericPrimitiveValueOperator = new GenericPrimitiveValueOperator(byte[].class, baseRealm2, osMap, new SelectorForMap(baseRealm2, osMap, String.class, byte[].class), RealmMapEntrySet.IteratorType.BINARY, new BinaryEquals());
            } else if (str.equals(ObjectId.class.getCanonicalName())) {
                genericPrimitiveValueOperator = new GenericPrimitiveValueOperator(ObjectId.class, baseRealm2, osMap, new SelectorForMap(baseRealm2, osMap, String.class, ObjectId.class), RealmMapEntrySet.IteratorType.OBJECT_ID);
            } else if (str.equals(UUID.class.getCanonicalName())) {
                genericPrimitiveValueOperator = new GenericPrimitiveValueOperator(UUID.class, baseRealm2, osMap, new SelectorForMap(baseRealm2, osMap, String.class, UUID.class), RealmMapEntrySet.IteratorType.UUID);
            } else {
                realmModelValueOperator = new RealmModelValueOperator(baseRealm2, osMap, new DynamicSelectorForMap(baseRealm2, osMap, str));
            }
            realmModelValueOperator = genericPrimitiveValueOperator;
        }
        return new DictionaryManager<>(baseRealm2, realmModelValueOperator, realmModelValueOperator.typeSelectorForMap);
    }
}
