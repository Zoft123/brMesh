package j$.util;

import j$.util.Collection;
import j$.util.Map;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

/* JADX INFO: loaded from: classes3.dex */
public class DesugarCollections {
    private static final Field COLLECTION_FIELD;
    private static final Field MUTEX_FIELD;
    public static final Class SYNCHRONIZED_COLLECTION;
    private static final Constructor SYNCHRONIZED_COLLECTION_CONSTRUCTOR;
    static final Class SYNCHRONIZED_LIST;
    private static final Constructor SYNCHRONIZED_SET_CONSTRUCTOR;

    static {
        Class<?> cls = Collections.synchronizedCollection(new ArrayList()).getClass();
        SYNCHRONIZED_COLLECTION = cls;
        SYNCHRONIZED_LIST = Collections.synchronizedList(new LinkedList()).getClass();
        Field field = getField(cls, "mutex");
        MUTEX_FIELD = field;
        if (field != null) {
            field.setAccessible(true);
        }
        Field field2 = getField(cls, "c");
        COLLECTION_FIELD = field2;
        if (field2 != null) {
            field2.setAccessible(true);
        }
        Constructor constructor = getConstructor(Collections.synchronizedSet(new HashSet()).getClass(), Set.class, Object.class);
        SYNCHRONIZED_SET_CONSTRUCTOR = constructor;
        if (constructor != null) {
            constructor.setAccessible(true);
        }
        Constructor constructor2 = getConstructor(cls, java.util.Collection.class, Object.class);
        SYNCHRONIZED_COLLECTION_CONSTRUCTOR = constructor2;
        if (constructor2 != null) {
            constructor2.setAccessible(true);
        }
    }

    private static Field getField(Class cls, String str) {
        try {
            return cls.getDeclaredField(str);
        } catch (NoSuchFieldException unused) {
            return null;
        }
    }

    private static Constructor getConstructor(Class cls, Class... clsArr) {
        try {
            return cls.getDeclaredConstructor(clsArr);
        } catch (NoSuchMethodException unused) {
            return null;
        }
    }

    static boolean removeIf(java.util.Collection collection, Predicate predicate) {
        boolean zRemoveIf;
        Field field = MUTEX_FIELD;
        if (field == null) {
            try {
                return Collection.EL.removeIf((java.util.Collection) COLLECTION_FIELD.get(collection), predicate);
            } catch (IllegalAccessException e) {
                throw new Error("Runtime illegal access in synchronized collection removeIf fall-back.", e);
            }
        }
        try {
            synchronized (field.get(collection)) {
                zRemoveIf = Collection.EL.removeIf((java.util.Collection) COLLECTION_FIELD.get(collection), predicate);
            }
            return zRemoveIf;
        } catch (IllegalAccessException e2) {
            throw new Error("Runtime illegal access in synchronized collection removeIf.", e2);
        }
    }

    public static <K, V> java.util.Map<K, V> synchronizedMap(java.util.Map<K, V> map) {
        return new SynchronizedMap(map);
    }

    private static class SynchronizedMap implements java.util.Map, Serializable, Map {
        private static final long serialVersionUID = 1978198479659022715L;
        private transient Set entrySet;
        private transient Set keySet;
        private final java.util.Map m;
        final Object mutex = this;
        private transient java.util.Collection values;

        SynchronizedMap(java.util.Map map) {
            this.m = (java.util.Map) Objects.requireNonNull(map);
        }

        @Override // java.util.Map
        public int size() {
            int size;
            synchronized (this.mutex) {
                size = this.m.size();
            }
            return size;
        }

        @Override // java.util.Map
        public boolean isEmpty() {
            boolean zIsEmpty;
            synchronized (this.mutex) {
                zIsEmpty = this.m.isEmpty();
            }
            return zIsEmpty;
        }

        @Override // java.util.Map
        public boolean containsKey(Object obj) {
            boolean zContainsKey;
            synchronized (this.mutex) {
                zContainsKey = this.m.containsKey(obj);
            }
            return zContainsKey;
        }

        @Override // java.util.Map
        public boolean containsValue(Object obj) {
            boolean zContainsValue;
            synchronized (this.mutex) {
                zContainsValue = this.m.containsValue(obj);
            }
            return zContainsValue;
        }

        @Override // java.util.Map
        public Object get(Object obj) {
            Object obj2;
            synchronized (this.mutex) {
                obj2 = this.m.get(obj);
            }
            return obj2;
        }

        @Override // java.util.Map
        public Object put(Object obj, Object obj2) {
            Object objPut;
            synchronized (this.mutex) {
                objPut = this.m.put(obj, obj2);
            }
            return objPut;
        }

        @Override // java.util.Map
        public Object remove(Object obj) {
            Object objRemove;
            synchronized (this.mutex) {
                objRemove = this.m.remove(obj);
            }
            return objRemove;
        }

        @Override // java.util.Map
        public void putAll(java.util.Map map) {
            synchronized (this.mutex) {
                this.m.putAll(map);
            }
        }

        @Override // java.util.Map
        public void clear() {
            synchronized (this.mutex) {
                this.m.clear();
            }
        }

        private Set instantiateSet(Set set, Object obj) {
            if (DesugarCollections.SYNCHRONIZED_SET_CONSTRUCTOR == null) {
                return Collections.synchronizedSet(set);
            }
            try {
                return (Set) DesugarCollections.SYNCHRONIZED_SET_CONSTRUCTOR.newInstance(set, obj);
            } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
                throw new Error("Unable to instantiate a synchronized list.", e);
            }
        }

        private java.util.Collection instantiateCollection(java.util.Collection collection, Object obj) {
            if (DesugarCollections.SYNCHRONIZED_COLLECTION_CONSTRUCTOR == null) {
                return Collections.synchronizedCollection(collection);
            }
            try {
                return (java.util.Collection) DesugarCollections.SYNCHRONIZED_COLLECTION_CONSTRUCTOR.newInstance(collection, obj);
            } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
                throw new Error("Unable to instantiate a synchronized list.", e);
            }
        }

        @Override // java.util.Map
        public Set keySet() {
            Set set;
            synchronized (this.mutex) {
                try {
                    if (this.keySet == null) {
                        this.keySet = instantiateSet(this.m.keySet(), this.mutex);
                    }
                    set = this.keySet;
                } catch (Throwable th) {
                    throw th;
                }
            }
            return set;
        }

        @Override // java.util.Map
        public Set entrySet() {
            Set set;
            synchronized (this.mutex) {
                try {
                    if (this.entrySet == null) {
                        this.entrySet = instantiateSet(this.m.entrySet(), this.mutex);
                    }
                    set = this.entrySet;
                } catch (Throwable th) {
                    throw th;
                }
            }
            return set;
        }

        @Override // java.util.Map
        public java.util.Collection values() {
            java.util.Collection collection;
            synchronized (this.mutex) {
                try {
                    if (this.values == null) {
                        this.values = instantiateCollection(this.m.values(), this.mutex);
                    }
                    collection = this.values;
                } catch (Throwable th) {
                    throw th;
                }
            }
            return collection;
        }

        @Override // java.util.Map
        public boolean equals(Object obj) {
            boolean zEquals;
            if (this == obj) {
                return true;
            }
            synchronized (this.mutex) {
                zEquals = this.m.equals(obj);
            }
            return zEquals;
        }

        @Override // java.util.Map
        public int hashCode() {
            int iHashCode;
            synchronized (this.mutex) {
                iHashCode = this.m.hashCode();
            }
            return iHashCode;
        }

        public String toString() {
            String string;
            synchronized (this.mutex) {
                string = this.m.toString();
            }
            return string;
        }

        @Override // java.util.Map, j$.util.Map
        public Object getOrDefault(Object obj, Object obj2) {
            Object orDefault;
            synchronized (this.mutex) {
                orDefault = Map.EL.getOrDefault(this.m, obj, obj2);
            }
            return orDefault;
        }

        @Override // java.util.Map, j$.util.Map
        public void forEach(BiConsumer biConsumer) {
            synchronized (this.mutex) {
                Map.EL.forEach(this.m, biConsumer);
            }
        }

        @Override // java.util.Map, j$.util.Map
        public void replaceAll(BiFunction biFunction) {
            synchronized (this.mutex) {
                Map.EL.replaceAll(this.m, biFunction);
            }
        }

        @Override // java.util.Map, j$.util.Map
        public Object putIfAbsent(Object obj, Object obj2) {
            Object objPutIfAbsent;
            synchronized (this.mutex) {
                objPutIfAbsent = Map.EL.putIfAbsent(this.m, obj, obj2);
            }
            return objPutIfAbsent;
        }

        @Override // java.util.Map, j$.util.Map
        public boolean remove(Object obj, Object obj2) {
            boolean zRemove;
            synchronized (this.mutex) {
                zRemove = Map.EL.remove(this.m, obj, obj2);
            }
            return zRemove;
        }

        @Override // java.util.Map, j$.util.Map
        public boolean replace(Object obj, Object obj2, Object obj3) {
            boolean zReplace;
            synchronized (this.mutex) {
                zReplace = Map.EL.replace(this.m, obj, obj2, obj3);
            }
            return zReplace;
        }

        @Override // java.util.Map, j$.util.Map
        public Object replace(Object obj, Object obj2) {
            Object objReplace;
            synchronized (this.mutex) {
                objReplace = Map.EL.replace(this.m, obj, obj2);
            }
            return objReplace;
        }

        @Override // java.util.Map, j$.util.Map
        public Object computeIfAbsent(Object obj, Function function) {
            Object objComputeIfAbsent;
            synchronized (this.mutex) {
                objComputeIfAbsent = Map.EL.computeIfAbsent(this.m, obj, function);
            }
            return objComputeIfAbsent;
        }

        @Override // java.util.Map, j$.util.Map
        public Object computeIfPresent(Object obj, BiFunction biFunction) {
            Object objComputeIfPresent;
            synchronized (this.mutex) {
                objComputeIfPresent = Map.EL.computeIfPresent(this.m, obj, biFunction);
            }
            return objComputeIfPresent;
        }

        @Override // java.util.Map, j$.util.Map
        public Object compute(Object obj, BiFunction biFunction) {
            Object objCompute;
            synchronized (this.mutex) {
                objCompute = Map.EL.compute(this.m, obj, biFunction);
            }
            return objCompute;
        }

        @Override // java.util.Map, j$.util.Map
        public Object merge(Object obj, Object obj2, BiFunction biFunction) {
            Object objMerge;
            synchronized (this.mutex) {
                objMerge = Map.EL.merge(this.m, obj, obj2, biFunction);
            }
            return objMerge;
        }

        private void writeObject(ObjectOutputStream objectOutputStream) {
            synchronized (this.mutex) {
                objectOutputStream.defaultWriteObject();
            }
        }
    }
}
