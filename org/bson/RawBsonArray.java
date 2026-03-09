package org.bson;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.AbstractList;
import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import org.bson.assertions.Assertions;
import org.bson.io.ByteBufferBsonInput;

/* JADX INFO: loaded from: classes4.dex */
public class RawBsonArray extends BsonArray implements Serializable {
    private static final String IMMUTABLE_MSG = "RawBsonArray instances are immutable";
    private static final long serialVersionUID = 2;
    private final transient RawBsonArrayList delegate;

    public RawBsonArray(byte[] bArr) {
        this((byte[]) Assertions.notNull("bytes", bArr), 0, bArr.length);
    }

    public RawBsonArray(byte[] bArr, int i, int i2) {
        this(new RawBsonArrayList(bArr, i, i2));
    }

    private RawBsonArray(RawBsonArrayList rawBsonArrayList) {
        super(rawBsonArrayList, false);
        this.delegate = rawBsonArrayList;
    }

    ByteBuf getByteBuffer() {
        return this.delegate.getByteBuffer();
    }

    @Override // org.bson.BsonArray, java.util.List, java.util.Collection
    public boolean add(BsonValue bsonValue) {
        throw new UnsupportedOperationException(IMMUTABLE_MSG);
    }

    @Override // org.bson.BsonArray, java.util.List, java.util.Collection
    public boolean remove(Object obj) {
        throw new UnsupportedOperationException(IMMUTABLE_MSG);
    }

    @Override // org.bson.BsonArray, java.util.List, java.util.Collection
    public boolean addAll(Collection<? extends BsonValue> collection) {
        throw new UnsupportedOperationException(IMMUTABLE_MSG);
    }

    @Override // org.bson.BsonArray, java.util.List
    public boolean addAll(int i, Collection<? extends BsonValue> collection) {
        throw new UnsupportedOperationException(IMMUTABLE_MSG);
    }

    @Override // org.bson.BsonArray, java.util.List, java.util.Collection
    public boolean removeAll(Collection<?> collection) {
        throw new UnsupportedOperationException(IMMUTABLE_MSG);
    }

    @Override // org.bson.BsonArray, java.util.List, java.util.Collection
    public boolean retainAll(Collection<?> collection) {
        throw new UnsupportedOperationException(IMMUTABLE_MSG);
    }

    @Override // org.bson.BsonArray, java.util.List, java.util.Collection
    public void clear() {
        throw new UnsupportedOperationException(IMMUTABLE_MSG);
    }

    @Override // org.bson.BsonArray, java.util.List
    public BsonValue set(int i, BsonValue bsonValue) {
        throw new UnsupportedOperationException(IMMUTABLE_MSG);
    }

    @Override // org.bson.BsonArray, java.util.List
    public void add(int i, BsonValue bsonValue) {
        throw new UnsupportedOperationException(IMMUTABLE_MSG);
    }

    @Override // org.bson.BsonArray, java.util.List
    public BsonValue remove(int i) {
        throw new UnsupportedOperationException(IMMUTABLE_MSG);
    }

    @Override // org.bson.BsonArray
    public BsonArray clone() {
        return new RawBsonArray((byte[]) this.delegate.bytes.clone(), this.delegate.offset, this.delegate.length);
    }

    @Override // org.bson.BsonArray, java.util.List, java.util.Collection
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override // org.bson.BsonArray, java.util.List, java.util.Collection
    public int hashCode() {
        return super.hashCode();
    }

    private Object writeReplace() {
        return new SerializationProxy(this.delegate.bytes, this.delegate.offset, this.delegate.length);
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Proxy required");
    }

    private static class SerializationProxy implements Serializable {
        private static final long serialVersionUID = 1;
        private final byte[] bytes;

        SerializationProxy(byte[] bArr, int i, int i2) {
            if (bArr.length == i2) {
                this.bytes = bArr;
                return;
            }
            byte[] bArr2 = new byte[i2];
            this.bytes = bArr2;
            System.arraycopy(bArr, i, bArr2, 0, i2);
        }

        private Object readResolve() {
            return new RawBsonArray(this.bytes);
        }
    }

    static class RawBsonArrayList extends AbstractList<BsonValue> {
        private static final int MIN_BSON_ARRAY_SIZE = 5;
        private final byte[] bytes;
        private Integer cachedSize;
        private final int length;
        private final int offset;

        RawBsonArrayList(byte[] bArr, int i, int i2) {
            Assertions.notNull("bytes", bArr);
            Assertions.isTrueArgument("offset >= 0", i >= 0);
            Assertions.isTrueArgument("offset < bytes.length", i < bArr.length);
            Assertions.isTrueArgument("length <= bytes.length - offset", i2 <= bArr.length - i);
            Assertions.isTrueArgument("length >= 5", i2 >= 5);
            this.bytes = bArr;
            this.offset = i;
            this.length = i2;
        }

        @Override // java.util.AbstractList, java.util.List
        public BsonValue get(int i) {
            if (i < 0) {
                throw new IndexOutOfBoundsException();
            }
            BsonBinaryReader bsonBinaryReaderCreateReader = createReader();
            try {
                bsonBinaryReaderCreateReader.readStartDocument();
                int i2 = 0;
                while (bsonBinaryReaderCreateReader.readBsonType() != BsonType.END_OF_DOCUMENT) {
                    bsonBinaryReaderCreateReader.skipName();
                    if (i2 == i) {
                        return RawBsonValueHelper.decode(this.bytes, bsonBinaryReaderCreateReader);
                    }
                    bsonBinaryReaderCreateReader.skipValue();
                    i2++;
                }
                bsonBinaryReaderCreateReader.readEndDocument();
                bsonBinaryReaderCreateReader.close();
                throw new IndexOutOfBoundsException();
            } finally {
                bsonBinaryReaderCreateReader.close();
            }
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public int size() {
            Integer num = this.cachedSize;
            if (num != null) {
                return num.intValue();
            }
            BsonBinaryReader bsonBinaryReaderCreateReader = createReader();
            try {
                bsonBinaryReaderCreateReader.readStartDocument();
                int i = 0;
                while (bsonBinaryReaderCreateReader.readBsonType() != BsonType.END_OF_DOCUMENT) {
                    i++;
                    bsonBinaryReaderCreateReader.readName();
                    bsonBinaryReaderCreateReader.skipValue();
                }
                bsonBinaryReaderCreateReader.readEndDocument();
                bsonBinaryReaderCreateReader.close();
                Integer numValueOf = Integer.valueOf(i);
                this.cachedSize = numValueOf;
                return numValueOf.intValue();
            } catch (Throwable th) {
                bsonBinaryReaderCreateReader.close();
                throw th;
            }
        }

        @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.List
        public Iterator<BsonValue> iterator() {
            return new Itr(this);
        }

        @Override // java.util.AbstractList, java.util.List
        public ListIterator<BsonValue> listIterator() {
            return new ListItr(0);
        }

        @Override // java.util.AbstractList, java.util.List
        public ListIterator<BsonValue> listIterator(int i) {
            return new ListItr(i);
        }

        private class Itr implements Iterator<BsonValue> {
            private BsonBinaryReader bsonReader;
            private int currentPosition;
            private int cursor;

            Itr(RawBsonArrayList rawBsonArrayList) {
                this(0);
            }

            Itr(int i) {
                this.cursor = 0;
                this.currentPosition = 0;
                setIterator(i);
            }

            @Override // java.util.Iterator
            public boolean hasNext() {
                boolean z = this.cursor != RawBsonArrayList.this.size();
                if (!z) {
                    this.bsonReader.close();
                }
                return z;
            }

            @Override // java.util.Iterator
            public BsonValue next() {
                while (this.cursor > this.currentPosition && this.bsonReader.readBsonType() != BsonType.END_OF_DOCUMENT) {
                    this.bsonReader.skipName();
                    this.bsonReader.skipValue();
                    this.currentPosition++;
                }
                if (this.bsonReader.readBsonType() != BsonType.END_OF_DOCUMENT) {
                    this.bsonReader.skipName();
                    int i = this.cursor + 1;
                    this.cursor = i;
                    this.currentPosition = i;
                    return RawBsonValueHelper.decode(RawBsonArrayList.this.bytes, this.bsonReader);
                }
                this.bsonReader.close();
                throw new NoSuchElementException();
            }

            @Override // java.util.Iterator
            public void remove() {
                throw new UnsupportedOperationException(RawBsonArray.IMMUTABLE_MSG);
            }

            public int getCursor() {
                return this.cursor;
            }

            public void setCursor(int i) {
                this.cursor = i;
            }

            void setIterator(int i) {
                this.cursor = i;
                this.currentPosition = 0;
                BsonBinaryReader bsonBinaryReader = this.bsonReader;
                if (bsonBinaryReader != null) {
                    bsonBinaryReader.close();
                }
                BsonBinaryReader bsonBinaryReaderCreateReader = RawBsonArrayList.this.createReader();
                this.bsonReader = bsonBinaryReaderCreateReader;
                bsonBinaryReaderCreateReader.readStartDocument();
            }
        }

        private class ListItr extends Itr implements ListIterator<BsonValue> {
            ListItr(int i) {
                super(i);
            }

            @Override // java.util.ListIterator
            public boolean hasPrevious() {
                return getCursor() != 0;
            }

            @Override // java.util.ListIterator
            public BsonValue previous() {
                try {
                    BsonValue bsonValue = RawBsonArrayList.this.get(previousIndex());
                    setIterator(previousIndex());
                    return bsonValue;
                } catch (IndexOutOfBoundsException unused) {
                    throw new NoSuchElementException();
                }
            }

            @Override // java.util.ListIterator
            public int nextIndex() {
                return getCursor();
            }

            @Override // java.util.ListIterator
            public int previousIndex() {
                return getCursor() - 1;
            }

            @Override // java.util.ListIterator
            public void set(BsonValue bsonValue) {
                throw new UnsupportedOperationException(RawBsonArray.IMMUTABLE_MSG);
            }

            @Override // java.util.ListIterator
            public void add(BsonValue bsonValue) {
                throw new UnsupportedOperationException(RawBsonArray.IMMUTABLE_MSG);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public BsonBinaryReader createReader() {
            return new BsonBinaryReader(new ByteBufferBsonInput(getByteBuffer()));
        }

        ByteBuf getByteBuffer() {
            ByteBuffer byteBufferWrap = ByteBuffer.wrap(this.bytes, this.offset, this.length);
            byteBufferWrap.order(ByteOrder.LITTLE_ENDIAN);
            return new ByteBufNIO(byteBufferWrap);
        }
    }
}
