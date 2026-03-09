package org.bson.util;

import j$.util.concurrent.ConcurrentMap;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import org.bson.assertions.Assertions;

/* JADX INFO: loaded from: classes4.dex */
abstract class AbstractCopyOnWriteMap<K, V, M extends Map<K, V>> implements ConcurrentMap<K, V>, j$.util.concurrent.ConcurrentMap {
    private volatile M delegate;
    private final transient Lock lock = new ReentrantLock();
    private final View<K, V> view;

    @Override // java.util.concurrent.ConcurrentMap, java.util.Map, j$.util.concurrent.ConcurrentMap, j$.util.Map
    public /* synthetic */ Object compute(Object obj, BiFunction biFunction) {
        return ConcurrentMap.CC.$default$compute(this, obj, biFunction);
    }

    @Override // java.util.concurrent.ConcurrentMap, java.util.Map, j$.util.concurrent.ConcurrentMap, j$.util.Map
    public /* synthetic */ Object computeIfAbsent(Object obj, java.util.function.Function function) {
        return ConcurrentMap.CC.$default$computeIfAbsent(this, obj, function);
    }

    @Override // java.util.concurrent.ConcurrentMap, java.util.Map, j$.util.concurrent.ConcurrentMap, j$.util.Map
    public /* synthetic */ Object computeIfPresent(Object obj, BiFunction biFunction) {
        return ConcurrentMap.CC.$default$computeIfPresent(this, obj, biFunction);
    }

    abstract <N extends Map<? extends K, ? extends V>> M copy(N n);

    @Override // java.util.concurrent.ConcurrentMap, java.util.Map, j$.util.concurrent.ConcurrentMap, j$.util.Map
    public /* synthetic */ void forEach(BiConsumer biConsumer) {
        ConcurrentMap.CC.$default$forEach(this, biConsumer);
    }

    @Override // java.util.concurrent.ConcurrentMap, java.util.Map, j$.util.concurrent.ConcurrentMap, j$.util.Map
    public /* synthetic */ Object getOrDefault(Object obj, Object obj2) {
        return ConcurrentMap.CC.$default$getOrDefault(this, obj, obj2);
    }

    @Override // java.util.concurrent.ConcurrentMap, java.util.Map, j$.util.concurrent.ConcurrentMap, j$.util.Map
    public /* synthetic */ Object merge(Object obj, Object obj2, BiFunction biFunction) {
        return ConcurrentMap.CC.$default$merge(this, obj, obj2, biFunction);
    }

    @Override // java.util.concurrent.ConcurrentMap, java.util.Map, j$.util.concurrent.ConcurrentMap, j$.util.Map
    public /* synthetic */ void replaceAll(BiFunction biFunction) {
        ConcurrentMap.CC.$default$replaceAll(this, biFunction);
    }

    protected <N extends Map<? extends K, ? extends V>> AbstractCopyOnWriteMap(N n, View.Type type) {
        this.delegate = (M) Assertions.notNull("delegate", copy((Map) Assertions.notNull("map", n)));
        this.view = ((View.Type) Assertions.notNull("viewType", type)).get(this);
    }

    @Override // java.util.Map
    public final void clear() {
        this.lock.lock();
        try {
            set(copy(Collections.EMPTY_MAP));
        } finally {
            this.lock.unlock();
        }
    }

    @Override // java.util.Map
    public final V remove(Object obj) {
        this.lock.lock();
        try {
            if (this.delegate.containsKey(obj)) {
                Map mapCopy = copy();
                try {
                    return (V) mapCopy.remove(obj);
                } finally {
                    set(mapCopy);
                }
            }
            this.lock.unlock();
            return null;
        } finally {
            this.lock.unlock();
        }
    }

    @Override // java.util.concurrent.ConcurrentMap, java.util.Map, j$.util.Map
    public boolean remove(Object obj, Object obj2) {
        this.lock.lock();
        try {
            if (!this.delegate.containsKey(obj) || !equals(obj2, this.delegate.get(obj))) {
                this.lock.unlock();
                return false;
            }
            Map mapCopy = copy();
            mapCopy.remove(obj);
            set(mapCopy);
            this.lock.unlock();
            return true;
        } catch (Throwable th) {
            this.lock.unlock();
            throw th;
        }
    }

    @Override // java.util.concurrent.ConcurrentMap, java.util.Map, j$.util.Map
    public boolean replace(K k, V v, V v2) {
        this.lock.lock();
        try {
            if (this.delegate.containsKey(k) && equals(v, this.delegate.get(k))) {
                Map mapCopy = copy();
                mapCopy.put(k, v2);
                set(mapCopy);
                this.lock.unlock();
                return true;
            }
            this.lock.unlock();
            return false;
        } catch (Throwable th) {
            this.lock.unlock();
            throw th;
        }
    }

    @Override // java.util.concurrent.ConcurrentMap, java.util.Map, j$.util.Map
    public V replace(K k, V v) {
        this.lock.lock();
        try {
            if (this.delegate.containsKey(k)) {
                Map mapCopy = copy();
                try {
                    return (V) mapCopy.put(k, v);
                } finally {
                    set(mapCopy);
                }
            }
            this.lock.unlock();
            return null;
        } finally {
            this.lock.unlock();
        }
    }

    @Override // java.util.Map
    public final V put(K k, V v) {
        this.lock.lock();
        try {
            Map mapCopy = copy();
            try {
                return (V) mapCopy.put(k, v);
            } finally {
                set(mapCopy);
            }
        } finally {
            this.lock.unlock();
        }
    }

    @Override // java.util.concurrent.ConcurrentMap, java.util.Map, j$.util.Map
    public V putIfAbsent(K k, V v) {
        V v2;
        this.lock.lock();
        try {
            if (!this.delegate.containsKey(k)) {
                Map mapCopy = copy();
                try {
                    v2 = (V) mapCopy.put(k, v);
                } finally {
                    set(mapCopy);
                }
            } else {
                v2 = (V) this.delegate.get(k);
            }
            return v2;
        } finally {
            this.lock.unlock();
        }
    }

    @Override // java.util.Map
    public final void putAll(Map<? extends K, ? extends V> map) {
        this.lock.lock();
        try {
            Map mapCopy = copy();
            mapCopy.putAll(map);
            set(mapCopy);
        } finally {
            this.lock.unlock();
        }
    }

    protected M copy() {
        this.lock.lock();
        try {
            return (M) copy(this.delegate);
        } finally {
            this.lock.unlock();
        }
    }

    protected void set(M m) {
        this.delegate = m;
    }

    @Override // java.util.Map
    public final Set<Map.Entry<K, V>> entrySet() {
        return this.view.entrySet();
    }

    @Override // java.util.Map
    public final Set<K> keySet() {
        return this.view.keySet();
    }

    @Override // java.util.Map
    public final Collection<V> values() {
        return this.view.values();
    }

    @Override // java.util.Map
    public final boolean containsKey(Object obj) {
        return this.delegate.containsKey(obj);
    }

    @Override // java.util.Map
    public final boolean containsValue(Object obj) {
        return this.delegate.containsValue(obj);
    }

    @Override // java.util.Map
    public final V get(Object obj) {
        return (V) this.delegate.get(obj);
    }

    @Override // java.util.Map
    public final boolean isEmpty() {
        return this.delegate.isEmpty();
    }

    @Override // java.util.Map
    public final int size() {
        return this.delegate.size();
    }

    @Override // java.util.Map
    public final boolean equals(Object obj) {
        return this.delegate.equals(obj);
    }

    @Override // java.util.Map
    public final int hashCode() {
        return this.delegate.hashCode();
    }

    protected final M getDelegate() {
        return this.delegate;
    }

    public String toString() {
        return this.delegate.toString();
    }

    private class KeySet extends CollectionView<K> implements Set<K> {
        private KeySet() {
        }

        @Override // org.bson.util.AbstractCopyOnWriteMap.CollectionView
        Collection<K> getDelegate() {
            return AbstractCopyOnWriteMap.this.delegate.keySet();
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // java.util.Collection, java.util.Set
        public void clear() {
            AbstractCopyOnWriteMap.this.lock.lock();
            try {
                Map mapCopy = AbstractCopyOnWriteMap.this.copy();
                mapCopy.keySet().clear();
                AbstractCopyOnWriteMap.this.set(mapCopy);
            } finally {
                AbstractCopyOnWriteMap.this.lock.unlock();
            }
        }

        @Override // java.util.Collection, java.util.Set
        public boolean remove(Object obj) {
            return AbstractCopyOnWriteMap.this.remove(obj) != null;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // java.util.Collection, java.util.Set
        public boolean removeAll(Collection<?> collection) {
            AbstractCopyOnWriteMap.this.lock.lock();
            try {
                Map mapCopy = AbstractCopyOnWriteMap.this.copy();
                try {
                    return mapCopy.keySet().removeAll(collection);
                } finally {
                    AbstractCopyOnWriteMap.this.set(mapCopy);
                }
            } finally {
                AbstractCopyOnWriteMap.this.lock.unlock();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // java.util.Collection, java.util.Set
        public boolean retainAll(Collection<?> collection) {
            AbstractCopyOnWriteMap.this.lock.lock();
            try {
                Map mapCopy = AbstractCopyOnWriteMap.this.copy();
                try {
                    return mapCopy.keySet().retainAll(collection);
                } finally {
                    AbstractCopyOnWriteMap.this.set(mapCopy);
                }
            } finally {
                AbstractCopyOnWriteMap.this.lock.unlock();
            }
        }
    }

    private final class Values extends CollectionView<V> {
        private Values() {
        }

        @Override // org.bson.util.AbstractCopyOnWriteMap.CollectionView
        Collection<V> getDelegate() {
            return AbstractCopyOnWriteMap.this.delegate.values();
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // java.util.Collection
        public void clear() {
            AbstractCopyOnWriteMap.this.lock.lock();
            try {
                Map mapCopy = AbstractCopyOnWriteMap.this.copy();
                mapCopy.values().clear();
                AbstractCopyOnWriteMap.this.set(mapCopy);
            } finally {
                AbstractCopyOnWriteMap.this.lock.unlock();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // java.util.Collection
        public boolean remove(Object obj) {
            AbstractCopyOnWriteMap.this.lock.lock();
            try {
                if (!contains(obj)) {
                    AbstractCopyOnWriteMap.this.lock.unlock();
                    return false;
                }
                Map mapCopy = AbstractCopyOnWriteMap.this.copy();
                try {
                    return mapCopy.values().remove(obj);
                } finally {
                    AbstractCopyOnWriteMap.this.set(mapCopy);
                }
            } finally {
                AbstractCopyOnWriteMap.this.lock.unlock();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // java.util.Collection
        public boolean removeAll(Collection<?> collection) {
            AbstractCopyOnWriteMap.this.lock.lock();
            try {
                Map mapCopy = AbstractCopyOnWriteMap.this.copy();
                try {
                    return mapCopy.values().removeAll(collection);
                } finally {
                    AbstractCopyOnWriteMap.this.set(mapCopy);
                }
            } finally {
                AbstractCopyOnWriteMap.this.lock.unlock();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // java.util.Collection
        public boolean retainAll(Collection<?> collection) {
            AbstractCopyOnWriteMap.this.lock.lock();
            try {
                Map mapCopy = AbstractCopyOnWriteMap.this.copy();
                try {
                    return mapCopy.values().retainAll(collection);
                } finally {
                    AbstractCopyOnWriteMap.this.set(mapCopy);
                }
            } finally {
                AbstractCopyOnWriteMap.this.lock.unlock();
            }
        }
    }

    private class EntrySet extends CollectionView<Map.Entry<K, V>> implements Set<Map.Entry<K, V>> {
        private EntrySet() {
        }

        @Override // org.bson.util.AbstractCopyOnWriteMap.CollectionView
        Collection<Map.Entry<K, V>> getDelegate() {
            return AbstractCopyOnWriteMap.this.delegate.entrySet();
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // java.util.Collection, java.util.Set
        public void clear() {
            AbstractCopyOnWriteMap.this.lock.lock();
            try {
                Map mapCopy = AbstractCopyOnWriteMap.this.copy();
                mapCopy.entrySet().clear();
                AbstractCopyOnWriteMap.this.set(mapCopy);
            } finally {
                AbstractCopyOnWriteMap.this.lock.unlock();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // java.util.Collection, java.util.Set
        public boolean remove(Object obj) {
            AbstractCopyOnWriteMap.this.lock.lock();
            try {
                if (!contains(obj)) {
                    AbstractCopyOnWriteMap.this.lock.unlock();
                    return false;
                }
                Map mapCopy = AbstractCopyOnWriteMap.this.copy();
                try {
                    return mapCopy.entrySet().remove(obj);
                } finally {
                    AbstractCopyOnWriteMap.this.set(mapCopy);
                }
            } finally {
                AbstractCopyOnWriteMap.this.lock.unlock();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // java.util.Collection, java.util.Set
        public boolean removeAll(Collection<?> collection) {
            AbstractCopyOnWriteMap.this.lock.lock();
            try {
                Map mapCopy = AbstractCopyOnWriteMap.this.copy();
                try {
                    return mapCopy.entrySet().removeAll(collection);
                } finally {
                    AbstractCopyOnWriteMap.this.set(mapCopy);
                }
            } finally {
                AbstractCopyOnWriteMap.this.lock.unlock();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // java.util.Collection, java.util.Set
        public boolean retainAll(Collection<?> collection) {
            AbstractCopyOnWriteMap.this.lock.lock();
            try {
                Map mapCopy = AbstractCopyOnWriteMap.this.copy();
                try {
                    return mapCopy.entrySet().retainAll(collection);
                } finally {
                    AbstractCopyOnWriteMap.this.set(mapCopy);
                }
            } finally {
                AbstractCopyOnWriteMap.this.lock.unlock();
            }
        }
    }

    private static class UnmodifiableIterator<T> implements Iterator<T> {
        private final Iterator<T> delegate;

        UnmodifiableIterator(Iterator<T> it) {
            this.delegate = it;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.delegate.hasNext();
        }

        @Override // java.util.Iterator
        public T next() {
            return this.delegate.next();
        }

        @Override // java.util.Iterator
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    protected static abstract class CollectionView<E> implements Collection<E> {
        abstract Collection<E> getDelegate();

        protected CollectionView() {
        }

        @Override // java.util.Collection
        public final boolean contains(Object obj) {
            return getDelegate().contains(obj);
        }

        @Override // java.util.Collection
        public final boolean containsAll(Collection<?> collection) {
            return getDelegate().containsAll(collection);
        }

        @Override // java.util.Collection, java.lang.Iterable
        public final Iterator<E> iterator() {
            return new UnmodifiableIterator(getDelegate().iterator());
        }

        @Override // java.util.Collection
        public final boolean isEmpty() {
            return getDelegate().isEmpty();
        }

        @Override // java.util.Collection
        public final int size() {
            return getDelegate().size();
        }

        @Override // java.util.Collection
        public final Object[] toArray() {
            return getDelegate().toArray();
        }

        @Override // java.util.Collection
        public final <T> T[] toArray(T[] tArr) {
            return (T[]) getDelegate().toArray(tArr);
        }

        @Override // java.util.Collection
        public int hashCode() {
            return getDelegate().hashCode();
        }

        @Override // java.util.Collection
        public boolean equals(Object obj) {
            return getDelegate().equals(obj);
        }

        public String toString() {
            return getDelegate().toString();
        }

        @Override // java.util.Collection
        public final boolean add(E e) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Collection
        public final boolean addAll(Collection<? extends E> collection) {
            throw new UnsupportedOperationException();
        }
    }

    private boolean equals(Object obj, Object obj2) {
        if (obj == null) {
            return obj2 == null;
        }
        return obj.equals(obj2);
    }

    public static abstract class View<K, V> {

        public enum Type {
            STABLE { // from class: org.bson.util.AbstractCopyOnWriteMap.View.Type.1
                @Override // org.bson.util.AbstractCopyOnWriteMap.View.Type
                <K, V, M extends Map<K, V>> View<K, V> get(AbstractCopyOnWriteMap<K, V, M> abstractCopyOnWriteMap) {
                    abstractCopyOnWriteMap.getClass();
                    return abstractCopyOnWriteMap.new Immutable();
                }
            },
            LIVE { // from class: org.bson.util.AbstractCopyOnWriteMap.View.Type.2
                @Override // org.bson.util.AbstractCopyOnWriteMap.View.Type
                <K, V, M extends Map<K, V>> View<K, V> get(AbstractCopyOnWriteMap<K, V, M> abstractCopyOnWriteMap) {
                    abstractCopyOnWriteMap.getClass();
                    return abstractCopyOnWriteMap.new Mutable();
                }
            };

            abstract <K, V, M extends Map<K, V>> View<K, V> get(AbstractCopyOnWriteMap<K, V, M> abstractCopyOnWriteMap);
        }

        abstract Set<Map.Entry<K, V>> entrySet();

        abstract Set<K> keySet();

        abstract Collection<V> values();

        View() {
        }
    }

    final class Immutable extends View<K, V> {
        Immutable() {
        }

        @Override // org.bson.util.AbstractCopyOnWriteMap.View
        public Set<K> keySet() {
            return Collections.unmodifiableSet(AbstractCopyOnWriteMap.this.delegate.keySet());
        }

        @Override // org.bson.util.AbstractCopyOnWriteMap.View
        public Set<Map.Entry<K, V>> entrySet() {
            return Collections.unmodifiableSet(AbstractCopyOnWriteMap.this.delegate.entrySet());
        }

        @Override // org.bson.util.AbstractCopyOnWriteMap.View
        public Collection<V> values() {
            return Collections.unmodifiableCollection(AbstractCopyOnWriteMap.this.delegate.values());
        }
    }

    final class Mutable extends View<K, V> {
        private final transient AbstractCopyOnWriteMap<K, V, M>.EntrySet entrySet;
        private final transient AbstractCopyOnWriteMap<K, V, M>.KeySet keySet;
        private final transient AbstractCopyOnWriteMap<K, V, M>.Values values;

        Mutable() {
            this.keySet = new KeySet();
            this.entrySet = new EntrySet();
            this.values = new Values();
        }

        @Override // org.bson.util.AbstractCopyOnWriteMap.View
        public Set<K> keySet() {
            return this.keySet;
        }

        @Override // org.bson.util.AbstractCopyOnWriteMap.View
        public Set<Map.Entry<K, V>> entrySet() {
            return this.entrySet;
        }

        @Override // org.bson.util.AbstractCopyOnWriteMap.View
        public Collection<V> values() {
            return this.values;
        }
    }
}
