package io.realm.internal;

import io.realm.internal.ObserverPairList.ObserverPair;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/* JADX INFO: loaded from: classes4.dex */
public class ObserverPairList<T extends ObserverPair> {
    private List<T> pairs = new CopyOnWriteArrayList();
    private boolean cleared = false;

    public interface Callback<T extends ObserverPair> {
        void onCalled(T t, Object obj);
    }

    public static abstract class ObserverPair<T, S> {
        protected final S listener;
        final WeakReference<T> observerRef;
        boolean removed = false;

        public ObserverPair(T t, S s) {
            this.listener = s;
            this.observerRef = new WeakReference<>(t);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj instanceof ObserverPair) {
                ObserverPair observerPair = (ObserverPair) obj;
                if (this.listener.equals(observerPair.listener) && this.observerRef.get() == observerPair.observerRef.get()) {
                    return true;
                }
            }
            return false;
        }

        public int hashCode() {
            T t = this.observerRef.get();
            int iHashCode = (527 + (t != null ? t.hashCode() : 0)) * 31;
            S s = this.listener;
            return iHashCode + (s != null ? s.hashCode() : 0);
        }
    }

    public void foreach(Callback<T> callback) {
        for (T t : this.pairs) {
            if (this.cleared) {
                return;
            }
            Object obj = t.observerRef.get();
            if (obj == null) {
                this.pairs.remove(t);
            } else if (!t.removed) {
                callback.onCalled(t, obj);
            }
        }
    }

    public boolean isEmpty() {
        return this.pairs.isEmpty();
    }

    public void clear() {
        this.cleared = true;
        this.pairs.clear();
    }

    public void add(T t) {
        if (!this.pairs.contains(t)) {
            this.pairs.add(t);
            t.removed = false;
        }
        if (this.cleared) {
            this.cleared = false;
        }
    }

    public <S, U> void remove(S s, U u) {
        for (T t : this.pairs) {
            if (s == t.observerRef.get() && u.equals(t.listener)) {
                t.removed = true;
                this.pairs.remove(t);
                return;
            }
        }
    }

    void removeByObserver(Object obj) {
        for (T t : this.pairs) {
            Object obj2 = t.observerRef.get();
            if (obj2 == null || obj2 == obj) {
                t.removed = true;
                this.pairs.remove(t);
            }
        }
    }

    public int size() {
        return this.pairs.size();
    }
}
