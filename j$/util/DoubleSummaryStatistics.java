package j$.util;

import androidx.camera.video.AudioStats;
import j$.util.function.DoubleConsumer$CC;
import java.util.function.DoubleConsumer;

/* JADX INFO: loaded from: classes3.dex */
public class DoubleSummaryStatistics implements DoubleConsumer {
    private long count;
    private double simpleSum;
    private double sum;
    private double sumCompensation;
    private double min = Double.POSITIVE_INFINITY;
    private double max = Double.NEGATIVE_INFINITY;

    public /* synthetic */ DoubleConsumer andThen(DoubleConsumer doubleConsumer) {
        return DoubleConsumer$CC.$default$andThen(this, doubleConsumer);
    }

    @Override // java.util.function.DoubleConsumer
    public void accept(double d) {
        this.count++;
        this.simpleSum += d;
        sumWithCompensation(d);
        this.min = Math.min(this.min, d);
        this.max = Math.max(this.max, d);
    }

    public void combine(DoubleSummaryStatistics doubleSummaryStatistics) {
        this.count += doubleSummaryStatistics.count;
        this.simpleSum += doubleSummaryStatistics.simpleSum;
        sumWithCompensation(doubleSummaryStatistics.sum);
        sumWithCompensation(doubleSummaryStatistics.sumCompensation);
        this.min = Math.min(this.min, doubleSummaryStatistics.min);
        this.max = Math.max(this.max, doubleSummaryStatistics.max);
    }

    private void sumWithCompensation(double d) {
        double d2 = d - this.sumCompensation;
        double d3 = this.sum;
        double d4 = d3 + d2;
        this.sumCompensation = (d4 - d3) - d2;
        this.sum = d4;
    }

    public final long getCount() {
        return this.count;
    }

    public final double getSum() {
        double d = this.sum + this.sumCompensation;
        return (Double.isNaN(d) && Double.isInfinite(this.simpleSum)) ? this.simpleSum : d;
    }

    public final double getMin() {
        return this.min;
    }

    public final double getMax() {
        return this.max;
    }

    public final double getAverage() {
        return getCount() > 0 ? getSum() / getCount() : AudioStats.AUDIO_AMPLITUDE_NONE;
    }

    public String toString() {
        return String.format("%s{count=%d, sum=%f, min=%f, average=%f, max=%f}", getClass().getSimpleName(), Long.valueOf(getCount()), Double.valueOf(getSum()), Double.valueOf(getMin()), Double.valueOf(getAverage()), Double.valueOf(getMax()));
    }
}
