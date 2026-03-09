package org.bson.util;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
@Deprecated
public class ClassMap<T> {
    private final Map<Class<?>, T> map = CopyOnWriteMap.newHashMap();
    private final Map<Class<?>, T> cache = ComputingMap.create(new ComputeFunction());

    public static <T> List<Class<?>> getAncestry(Class<T> cls) {
        return ClassAncestry.getAncestry(cls);
    }

    private final class ComputeFunction implements Function<Class<?>, T> {
        private ComputeFunction() {
        }

        @Override // org.bson.util.Function
        public T apply(Class<?> cls) {
            Iterator<Class<?>> it = ClassMap.getAncestry(cls).iterator();
            while (it.hasNext()) {
                T t = (T) ClassMap.this.map.get(it.next());
                if (t != null) {
                    return t;
                }
            }
            return null;
        }
    }

    public T get(Object obj) {
        return this.cache.get(obj);
    }

    public T put(Class<?> cls, T t) {
        try {
            return this.map.put(cls, t);
        } finally {
            this.cache.clear();
        }
    }

    public T remove(Object obj) {
        try {
            return this.map.remove(obj);
        } finally {
            this.cache.clear();
        }
    }

    public void clear() {
        this.map.clear();
        this.cache.clear();
    }

    public int size() {
        return this.map.size();
    }

    public boolean isEmpty() {
        return this.map.isEmpty();
    }
}
