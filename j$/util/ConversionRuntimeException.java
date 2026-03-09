package j$.util;

/* JADX INFO: loaded from: classes3.dex */
public class ConversionRuntimeException extends RuntimeException {
    public ConversionRuntimeException(String str) {
        super(str);
    }

    public static RuntimeException exception(String str, Object obj) {
        throw new ConversionRuntimeException("Unsupported " + str + " :" + obj);
    }
}
