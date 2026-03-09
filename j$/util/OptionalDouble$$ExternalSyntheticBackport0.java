package j$.util;

/* JADX INFO: loaded from: classes3.dex */
public abstract /* synthetic */ class OptionalDouble$$ExternalSyntheticBackport0 {
    public static /* synthetic */ int m(double d) {
        long jDoubleToLongBits = Double.doubleToLongBits(d);
        return (int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32));
    }
}
