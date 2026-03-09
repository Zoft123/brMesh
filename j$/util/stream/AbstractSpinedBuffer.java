package j$.util.stream;

/* JADX INFO: loaded from: classes3.dex */
abstract class AbstractSpinedBuffer {
    protected int elementIndex;
    protected final int initialChunkPower;
    protected long[] priorElementCount;
    protected int spineIndex;

    public abstract void clear();

    protected AbstractSpinedBuffer() {
        this.initialChunkPower = 4;
    }

    protected AbstractSpinedBuffer(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("Illegal Capacity: " + i);
        }
        this.initialChunkPower = Math.max(4, 32 - Integer.numberOfLeadingZeros(i - 1));
    }

    public long count() {
        int i = this.spineIndex;
        if (i == 0) {
            return this.elementIndex;
        }
        return this.priorElementCount[i] + ((long) this.elementIndex);
    }

    protected int chunkSize(int i) {
        int iMin;
        if (i == 0 || i == 1) {
            iMin = this.initialChunkPower;
        } else {
            iMin = Math.min((this.initialChunkPower + i) - 1, 30);
        }
        return 1 << iMin;
    }
}
