package org.bson;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.bson.codecs.BsonArrayCodec;
import org.bson.codecs.DecoderContext;
import org.bson.json.JsonReader;

/* JADX INFO: loaded from: classes4.dex */
public class BsonArray extends BsonValue implements List<BsonValue>, Cloneable {
    private final List<BsonValue> values;

    public BsonArray(List<? extends BsonValue> list) {
        this(list, true);
    }

    public BsonArray() {
        this(new ArrayList(), false);
    }

    /* JADX WARN: Multi-variable type inference failed */
    BsonArray(List<? extends BsonValue> list, boolean z) {
        if (z) {
            this.values = new ArrayList(list);
        } else {
            this.values = list;
        }
    }

    public static BsonArray parse(String str) {
        return new BsonArrayCodec().decode((BsonReader) new JsonReader(str), DecoderContext.builder().build());
    }

    public List<BsonValue> getValues() {
        return Collections.unmodifiableList(this.values);
    }

    @Override // org.bson.BsonValue
    public BsonType getBsonType() {
        return BsonType.ARRAY;
    }

    @Override // java.util.List, java.util.Collection
    public int size() {
        return this.values.size();
    }

    @Override // java.util.List, java.util.Collection
    public boolean isEmpty() {
        return this.values.isEmpty();
    }

    @Override // java.util.List, java.util.Collection
    public boolean contains(Object obj) {
        return this.values.contains(obj);
    }

    @Override // java.util.List, java.util.Collection, java.lang.Iterable
    public Iterator<BsonValue> iterator() {
        return this.values.iterator();
    }

    @Override // java.util.List, java.util.Collection
    public Object[] toArray() {
        return this.values.toArray();
    }

    @Override // java.util.List, java.util.Collection
    public <T> T[] toArray(T[] tArr) {
        return (T[]) this.values.toArray(tArr);
    }

    @Override // java.util.List, java.util.Collection
    public boolean add(BsonValue bsonValue) {
        return this.values.add(bsonValue);
    }

    @Override // java.util.List, java.util.Collection
    public boolean remove(Object obj) {
        return this.values.remove(obj);
    }

    @Override // java.util.List, java.util.Collection
    public boolean containsAll(Collection<?> collection) {
        return this.values.containsAll(collection);
    }

    @Override // java.util.List, java.util.Collection
    public boolean addAll(Collection<? extends BsonValue> collection) {
        return this.values.addAll(collection);
    }

    @Override // java.util.List
    public boolean addAll(int i, Collection<? extends BsonValue> collection) {
        return this.values.addAll(i, collection);
    }

    @Override // java.util.List, java.util.Collection
    public boolean removeAll(Collection<?> collection) {
        return this.values.removeAll(collection);
    }

    @Override // java.util.List, java.util.Collection
    public boolean retainAll(Collection<?> collection) {
        return this.values.retainAll(collection);
    }

    @Override // java.util.List, java.util.Collection
    public void clear() {
        this.values.clear();
    }

    @Override // java.util.List
    public BsonValue get(int i) {
        return this.values.get(i);
    }

    @Override // java.util.List
    public BsonValue set(int i, BsonValue bsonValue) {
        return this.values.set(i, bsonValue);
    }

    @Override // java.util.List
    public void add(int i, BsonValue bsonValue) {
        this.values.add(i, bsonValue);
    }

    @Override // java.util.List
    public BsonValue remove(int i) {
        return this.values.remove(i);
    }

    @Override // java.util.List
    public int indexOf(Object obj) {
        return this.values.indexOf(obj);
    }

    @Override // java.util.List
    public int lastIndexOf(Object obj) {
        return this.values.lastIndexOf(obj);
    }

    @Override // java.util.List
    public ListIterator<BsonValue> listIterator() {
        return this.values.listIterator();
    }

    @Override // java.util.List
    public ListIterator<BsonValue> listIterator(int i) {
        return this.values.listIterator(i);
    }

    @Override // java.util.List
    public List<BsonValue> subList(int i, int i2) {
        return this.values.subList(i, i2);
    }

    @Override // java.util.List, java.util.Collection
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof BsonArray) {
            return getValues().equals(((BsonArray) obj).getValues());
        }
        return false;
    }

    @Override // java.util.List, java.util.Collection
    public int hashCode() {
        return this.values.hashCode();
    }

    public String toString() {
        return "BsonArray{values=" + this.values + '}';
    }

    @Override // 
    public BsonArray clone() {
        BsonArray bsonArray = new BsonArray();
        for (BsonValue bsonValue : this) {
            int i = AnonymousClass1.$SwitchMap$org$bson$BsonType[bsonValue.getBsonType().ordinal()];
            if (i == 1) {
                bsonArray.add((BsonValue) bsonValue.asDocument().clone());
            } else if (i == 2) {
                bsonArray.add((BsonValue) bsonValue.asArray().clone());
            } else if (i == 3) {
                bsonArray.add((BsonValue) BsonBinary.clone(bsonValue.asBinary()));
            } else if (i == 4) {
                bsonArray.add((BsonValue) BsonJavaScriptWithScope.clone(bsonValue.asJavaScriptWithScope()));
            } else {
                bsonArray.add(bsonValue);
            }
        }
        return bsonArray;
    }

    /* JADX INFO: renamed from: org.bson.BsonArray$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$bson$BsonType;

        static {
            int[] iArr = new int[BsonType.values().length];
            $SwitchMap$org$bson$BsonType = iArr;
            try {
                iArr[BsonType.DOCUMENT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$bson$BsonType[BsonType.ARRAY.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$bson$BsonType[BsonType.BINARY.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$org$bson$BsonType[BsonType.JAVASCRIPT_WITH_SCOPE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }
}
