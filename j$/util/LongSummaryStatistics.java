package j$.util;

import androidx.camera.video.AudioStats;
import j$.util.function.IntConsumer$CC;
import j$.util.function.LongConsumer$CC;
import java.util.function.IntConsumer;
import java.util.function.LongConsumer;

/* JADX INFO: loaded from: classes3.dex */
public class LongSummaryStatistics implements LongConsumer, IntConsumer {
    private long count;
    private long sum;
    private long min = Long.MAX_VALUE;
    private long max = Long.MIN_VALUE;

    public /* synthetic */ IntConsumer andThen(IntConsumer intConsumer) {
        return IntConsumer$CC.$default$andThen(this, intConsumer);
    }

    public /* synthetic */ LongConsumer andThen(LongConsumer longConsumer) {
        return LongConsumer$CC.$default$andThen(this, longConsumer);
    }

    @Override // java.util.function.IntConsumer
    public void accept(int i) {
        accept(i);
    }

    @Override // java.util.function.LongConsumer
    public void accept(long j) {
        this.count++;
        this.sum += j;
        this.min = Math.min(this.min, j);
        this.max = Math.max(this.max, j);
    }

    public void combine(LongSummaryStatistics longSummaryStatistics) {
        this.count += longSummaryStatistics.count;
        this.sum += longSummaryStatistics.sum;
        this.min = Math.min(this.min, longSummaryStatistics.min);
        this.max = Math.max(this.max, longSummaryStatistics.max);
    }

    public final long getCount() {
        return this.count;
    }

    public final long getSum() {
        return this.sum;
    }

    public final long getMin() {
        return this.min;
    }

    public final long getMax() {
        return this.max;
    }

    public final double getAverage() {
        return getCount() > 0 ? getSum() / getCount() : AudioStats.AUDIO_AMPLITUDE_NONE;
    }

    public String toString() {
        return String.format("%s{count=%d, sum=%d, min=%d, average=%f, max=%d}", getClass().getSimpleName(), Long.valueOf(getCount()), Long.valueOf(getSum()), Long.valueOf(getMin()), Double.valueOf(getAverage()), Long.valueOf(getMax()));
    }
}
