package j$.sun.misc;

import j$.com.android.tools.r8.DesugarVarHandle$$ExternalSyntheticBackportWithForwarding0;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import sun.misc.Unsafe;

/* JADX INFO: loaded from: classes3.dex */
public final class DesugarUnsafe {
    private static final DesugarUnsafe theUnsafeWrapper;
    private final Unsafe theUnsafe;

    static {
        Field unsafeField = getUnsafeField();
        unsafeField.setAccessible(true);
        try {
            theUnsafeWrapper = new DesugarUnsafe((Unsafe) unsafeField.get(null));
        } catch (IllegalAccessException e) {
            throw new AssertionError("Couldn't get the Unsafe", e);
        }
    }

    DesugarUnsafe(Unsafe unsafe) {
        this.theUnsafe = unsafe;
    }

    private static Field getUnsafeField() {
        try {
            return Unsafe.class.getDeclaredField("theUnsafe");
        } catch (NoSuchFieldException e) {
            for (Field field : Unsafe.class.getDeclaredFields()) {
                if (Modifier.isStatic(field.getModifiers()) && Unsafe.class.isAssignableFrom(field.getType())) {
                    return field;
                }
            }
            throw new AssertionError("Couldn't find the Unsafe", e);
        }
    }

    public static DesugarUnsafe getUnsafe() {
        return theUnsafeWrapper;
    }

    public int getAndAddInt(Object obj, long j, int i) {
        while (true) {
            int intVolatile = this.theUnsafe.getIntVolatile(obj, j);
            Object obj2 = obj;
            long j2 = j;
            if (this.theUnsafe.compareAndSwapInt(obj2, j2, intVolatile, intVolatile + i)) {
                return intVolatile;
            }
            obj = obj2;
            j = j2;
        }
    }

    public long objectFieldOffset(Field field) {
        return this.theUnsafe.objectFieldOffset(field);
    }

    public long objectFieldOffset(Class cls, String str) {
        if (cls == null || str == null) {
            throw null;
        }
        try {
            return objectFieldOffset(cls.getDeclaredField(str));
        } catch (NoSuchFieldException e) {
            throw new AssertionError("Cannot find field:", e);
        }
    }

    public int arrayBaseOffset(Class cls) {
        return this.theUnsafe.arrayBaseOffset(cls);
    }

    public int arrayIndexScale(Class cls) {
        return this.theUnsafe.arrayIndexScale(cls);
    }

    public Object getObjectAcquire(Object obj, long j) {
        return this.theUnsafe.getObjectVolatile(obj, j);
    }

    public void putObjectRelease(Object obj, long j, Object obj2) {
        this.theUnsafe.putObjectVolatile(obj, j, obj2);
    }

    public boolean compareAndSetInt(Object obj, long j, int i, int i2) {
        return this.theUnsafe.compareAndSwapInt(obj, j, i, i2);
    }

    public boolean compareAndSetLong(Object obj, long j, long j2, long j3) {
        return this.theUnsafe.compareAndSwapLong(obj, j, j2, j3);
    }

    public boolean compareAndSetObject(Object obj, long j, Object obj2, Object obj3) {
        return DesugarVarHandle$$ExternalSyntheticBackportWithForwarding0.m(this.theUnsafe, obj, j, obj2, obj3);
    }
}
