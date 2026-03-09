package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.util.IOUtils;
import com.alibaba.fastjson.util.RyuDouble;
import com.alibaba.fastjson.util.RyuFloat;
import com.brgd.brblmesh.GeneralClass.GlobalVariable;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.List;
import kotlin.text.Typography;
import okhttp3.internal.url._UrlKt;
import org.bson.BSON;

/* JADX INFO: loaded from: classes.dex */
public final class SerializeWriter extends Writer {
    private static int BUFFER_THRESHOLD;
    private static final ThreadLocal<char[]> bufLocal = new ThreadLocal<>();
    private static final ThreadLocal<byte[]> bytesBufLocal = new ThreadLocal<>();
    static final int nonDirectFeatures;
    protected boolean beanToArray;
    protected boolean browserSecure;
    protected char[] buf;
    protected int count;
    protected boolean disableCircularReferenceDetect;
    protected int features;
    protected char keySeperator;
    protected int maxBufSize;
    protected boolean notWriteDefaultValue;
    protected boolean quoteFieldNames;
    protected long sepcialBits;
    protected boolean sortField;
    protected boolean useSingleQuotes;
    protected boolean writeDirect;
    protected boolean writeEnumUsingName;
    protected boolean writeEnumUsingToString;
    protected boolean writeNonStringValueAsString;
    private final Writer writer;

    static {
        int i;
        BUFFER_THRESHOLD = 131072;
        try {
            String stringProperty = IOUtils.getStringProperty("fastjson.serializer_buffer_threshold");
            if (stringProperty != null && stringProperty.length() > 0 && (i = Integer.parseInt(stringProperty)) >= 64 && i <= 65536) {
                BUFFER_THRESHOLD = i * 1024;
            }
        } catch (Throwable unused) {
        }
        nonDirectFeatures = SerializerFeature.UseSingleQuotes.mask | SerializerFeature.BrowserCompatible.mask | SerializerFeature.PrettyFormat.mask | SerializerFeature.WriteEnumUsingToString.mask | SerializerFeature.WriteNonStringValueAsString.mask | SerializerFeature.WriteSlashAsSpecial.mask | SerializerFeature.IgnoreErrorGetter.mask | SerializerFeature.WriteClassName.mask | SerializerFeature.NotWriteDefaultValue.mask;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public SerializeWriter() {
        this((Writer) null);
    }

    public SerializeWriter(Writer writer) {
        this(writer, JSON.DEFAULT_GENERATE_FEATURE, SerializerFeature.EMPTY);
    }

    public SerializeWriter(SerializerFeature... serializerFeatureArr) {
        this((Writer) null, serializerFeatureArr);
    }

    public SerializeWriter(Writer writer, SerializerFeature... serializerFeatureArr) {
        this(writer, 0, serializerFeatureArr);
    }

    public SerializeWriter(Writer writer, int i, SerializerFeature... serializerFeatureArr) {
        this.maxBufSize = -1;
        this.writer = writer;
        ThreadLocal<char[]> threadLocal = bufLocal;
        char[] cArr = threadLocal.get();
        this.buf = cArr;
        if (cArr != null) {
            threadLocal.set(null);
        } else {
            this.buf = new char[2048];
        }
        for (SerializerFeature serializerFeature : serializerFeatureArr) {
            i |= serializerFeature.getMask();
        }
        this.features = i;
        computeFeatures();
    }

    public int getMaxBufSize() {
        return this.maxBufSize;
    }

    public void setMaxBufSize(int i) {
        if (i < this.buf.length) {
            throw new JSONException("must > " + this.buf.length);
        }
        this.maxBufSize = i;
    }

    public int getBufferLength() {
        return this.buf.length;
    }

    public SerializeWriter(int i) {
        this((Writer) null, i);
    }

    public SerializeWriter(Writer writer, int i) {
        this.maxBufSize = -1;
        this.writer = writer;
        if (i <= 0) {
            throw new IllegalArgumentException("Negative initial size: " + i);
        }
        this.buf = new char[i];
        computeFeatures();
    }

    public void config(SerializerFeature serializerFeature, boolean z) {
        if (z) {
            this.features |= serializerFeature.getMask();
            if (serializerFeature == SerializerFeature.WriteEnumUsingToString) {
                this.features &= ~SerializerFeature.WriteEnumUsingName.getMask();
            } else if (serializerFeature == SerializerFeature.WriteEnumUsingName) {
                this.features &= ~SerializerFeature.WriteEnumUsingToString.getMask();
            }
        } else {
            this.features = (~serializerFeature.getMask()) & this.features;
        }
        computeFeatures();
    }

    protected void computeFeatures() {
        long j;
        this.quoteFieldNames = (this.features & SerializerFeature.QuoteFieldNames.mask) != 0;
        this.useSingleQuotes = (this.features & SerializerFeature.UseSingleQuotes.mask) != 0;
        this.sortField = (this.features & SerializerFeature.SortField.mask) != 0;
        this.disableCircularReferenceDetect = (this.features & SerializerFeature.DisableCircularReferenceDetect.mask) != 0;
        this.beanToArray = (this.features & SerializerFeature.BeanToArray.mask) != 0;
        this.writeNonStringValueAsString = (this.features & SerializerFeature.WriteNonStringValueAsString.mask) != 0;
        this.notWriteDefaultValue = (this.features & SerializerFeature.NotWriteDefaultValue.mask) != 0;
        this.writeEnumUsingName = (this.features & SerializerFeature.WriteEnumUsingName.mask) != 0;
        this.writeEnumUsingToString = (this.features & SerializerFeature.WriteEnumUsingToString.mask) != 0;
        this.writeDirect = this.quoteFieldNames && (this.features & nonDirectFeatures) == 0 && (this.beanToArray || this.writeEnumUsingName);
        this.keySeperator = this.useSingleQuotes ? '\'' : Typography.quote;
        boolean z = (this.features & SerializerFeature.BrowserSecure.mask) != 0;
        this.browserSecure = z;
        if (z) {
            j = 5764610843043954687L;
        } else {
            j = (this.features & SerializerFeature.WriteSlashAsSpecial.mask) != 0 ? 140758963191807L : 21474836479L;
        }
        this.sepcialBits = j;
    }

    public boolean isSortField() {
        return this.sortField;
    }

    public boolean isNotWriteDefaultValue() {
        return this.notWriteDefaultValue;
    }

    public boolean isEnabled(SerializerFeature serializerFeature) {
        return (serializerFeature.mask & this.features) != 0;
    }

    public boolean isEnabled(int i) {
        return (i & this.features) != 0;
    }

    @Override // java.io.Writer
    public void write(int i) {
        int i2 = 1;
        int i3 = this.count + 1;
        if (i3 <= this.buf.length) {
            i2 = i3;
        } else if (this.writer == null) {
            expandCapacity(i3);
            i2 = i3;
        } else {
            flush();
        }
        this.buf[this.count] = (char) i;
        this.count = i2;
    }

    @Override // java.io.Writer
    public void write(char[] cArr, int i, int i2) {
        int i3;
        if (i < 0 || i > cArr.length || i2 < 0 || (i3 = i + i2) > cArr.length || i3 < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (i2 == 0) {
            return;
        }
        int i4 = this.count + i2;
        if (i4 > this.buf.length) {
            if (this.writer == null) {
                expandCapacity(i4);
            } else {
                do {
                    char[] cArr2 = this.buf;
                    int length = cArr2.length;
                    int i5 = this.count;
                    int i6 = length - i5;
                    System.arraycopy(cArr, i, cArr2, i5, i6);
                    this.count = this.buf.length;
                    flush();
                    i2 -= i6;
                    i += i6;
                } while (i2 > this.buf.length);
                i4 = i2;
            }
        }
        System.arraycopy(cArr, i, this.buf, this.count, i2);
        this.count = i4;
    }

    public void expandCapacity(int i) {
        ThreadLocal<char[]> threadLocal;
        char[] cArr;
        int i2 = this.maxBufSize;
        if (i2 != -1 && i >= i2) {
            throw new JSONException("serialize exceeded MAX_OUTPUT_LENGTH=" + this.maxBufSize + ", minimumCapacity=" + i);
        }
        char[] cArr2 = this.buf;
        int length = cArr2.length + (cArr2.length >> 1) + 1;
        if (length >= i) {
            i = length;
        }
        char[] cArr3 = new char[i];
        System.arraycopy(cArr2, 0, cArr3, 0, this.count);
        if (this.buf.length < BUFFER_THRESHOLD && ((cArr = (threadLocal = bufLocal).get()) == null || cArr.length < this.buf.length)) {
            threadLocal.set(this.buf);
        }
        this.buf = cArr3;
    }

    @Override // java.io.Writer, java.lang.Appendable
    public SerializeWriter append(CharSequence charSequence) {
        String string = charSequence == null ? GlobalVariable.nullColor : charSequence.toString();
        write(string, 0, string.length());
        return this;
    }

    @Override // java.io.Writer, java.lang.Appendable
    public SerializeWriter append(CharSequence charSequence, int i, int i2) {
        if (charSequence == null) {
            charSequence = GlobalVariable.nullColor;
        }
        String string = charSequence.subSequence(i, i2).toString();
        write(string, 0, string.length());
        return this;
    }

    @Override // java.io.Writer, java.lang.Appendable
    public SerializeWriter append(char c) {
        write(c);
        return this;
    }

    @Override // java.io.Writer
    public void write(String str, int i, int i2) {
        int i3;
        int i4 = this.count + i2;
        if (i4 > this.buf.length) {
            if (this.writer == null) {
                expandCapacity(i4);
            } else {
                while (true) {
                    char[] cArr = this.buf;
                    int length = cArr.length;
                    int i5 = this.count;
                    int i6 = length - i5;
                    i3 = i + i6;
                    str.getChars(i, i3, cArr, i5);
                    this.count = this.buf.length;
                    flush();
                    i2 -= i6;
                    if (i2 <= this.buf.length) {
                        break;
                    } else {
                        i = i3;
                    }
                }
                i4 = i2;
                i = i3;
            }
        }
        str.getChars(i, i2 + i, this.buf, this.count);
        this.count = i4;
    }

    public void writeTo(Writer writer) throws IOException {
        if (this.writer != null) {
            throw new UnsupportedOperationException("writer not null");
        }
        writer.write(this.buf, 0, this.count);
    }

    public void writeTo(OutputStream outputStream, String str) throws IOException {
        writeTo(outputStream, Charset.forName(str));
    }

    public void writeTo(OutputStream outputStream, Charset charset) throws IOException {
        writeToEx(outputStream, charset);
    }

    public int writeToEx(OutputStream outputStream, Charset charset) throws IOException {
        if (this.writer != null) {
            throw new UnsupportedOperationException("writer not null");
        }
        if (charset == IOUtils.UTF8) {
            return encodeToUTF8(outputStream);
        }
        byte[] bytes = new String(this.buf, 0, this.count).getBytes(charset);
        outputStream.write(bytes);
        return bytes.length;
    }

    public char[] toCharArray() {
        if (this.writer != null) {
            throw new UnsupportedOperationException("writer not null");
        }
        int i = this.count;
        char[] cArr = new char[i];
        System.arraycopy(this.buf, 0, cArr, 0, i);
        return cArr;
    }

    public char[] toCharArrayForSpringWebSocket() {
        if (this.writer != null) {
            throw new UnsupportedOperationException("writer not null");
        }
        int i = this.count;
        char[] cArr = new char[i - 2];
        System.arraycopy(this.buf, 1, cArr, 0, i - 2);
        return cArr;
    }

    public byte[] toBytes(String str) {
        Charset charsetForName;
        if (str == null || "UTF-8".equals(str)) {
            charsetForName = IOUtils.UTF8;
        } else {
            charsetForName = Charset.forName(str);
        }
        return toBytes(charsetForName);
    }

    public byte[] toBytes(Charset charset) {
        if (this.writer != null) {
            throw new UnsupportedOperationException("writer not null");
        }
        if (charset == IOUtils.UTF8) {
            return encodeToUTF8Bytes();
        }
        return new String(this.buf, 0, this.count).getBytes(charset);
    }

    private int encodeToUTF8(OutputStream outputStream) throws IOException {
        int i = (int) (((double) this.count) * 3.0d);
        ThreadLocal<byte[]> threadLocal = bytesBufLocal;
        byte[] bArr = threadLocal.get();
        if (bArr == null) {
            bArr = new byte[8192];
            threadLocal.set(bArr);
        }
        if (bArr.length < i) {
            bArr = new byte[i];
        }
        int iEncodeUTF8 = IOUtils.encodeUTF8(this.buf, 0, this.count, bArr);
        outputStream.write(bArr, 0, iEncodeUTF8);
        return iEncodeUTF8;
    }

    private byte[] encodeToUTF8Bytes() {
        int i = (int) (((double) this.count) * 3.0d);
        ThreadLocal<byte[]> threadLocal = bytesBufLocal;
        byte[] bArr = threadLocal.get();
        if (bArr == null) {
            bArr = new byte[8192];
            threadLocal.set(bArr);
        }
        if (bArr.length < i) {
            bArr = new byte[i];
        }
        int iEncodeUTF8 = IOUtils.encodeUTF8(this.buf, 0, this.count, bArr);
        byte[] bArr2 = new byte[iEncodeUTF8];
        System.arraycopy(bArr, 0, bArr2, 0, iEncodeUTF8);
        return bArr2;
    }

    public int size() {
        return this.count;
    }

    public String toString() {
        return new String(this.buf, 0, this.count);
    }

    @Override // java.io.Writer, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        if (this.writer != null && this.count > 0) {
            flush();
        }
        char[] cArr = this.buf;
        if (cArr.length <= BUFFER_THRESHOLD) {
            bufLocal.set(cArr);
        }
        this.buf = null;
    }

    @Override // java.io.Writer
    public void write(String str) {
        if (str == null) {
            writeNull();
        } else {
            write(str, 0, str.length());
        }
    }

    public void writeInt(int i) {
        if (i == Integer.MIN_VALUE) {
            write("-2147483648");
            return;
        }
        int iStringSize = i < 0 ? IOUtils.stringSize(-i) + 1 : IOUtils.stringSize(i);
        int i2 = this.count + iStringSize;
        if (i2 > this.buf.length) {
            if (this.writer == null) {
                expandCapacity(i2);
            } else {
                char[] cArr = new char[iStringSize];
                IOUtils.getChars(i, iStringSize, cArr);
                write(cArr, 0, iStringSize);
                return;
            }
        }
        IOUtils.getChars(i, i2, this.buf);
        this.count = i2;
    }

    public void writeByteArray(byte[] bArr) {
        if (isEnabled(SerializerFeature.WriteClassName.mask)) {
            writeHex(bArr);
            return;
        }
        int length = bArr.length;
        boolean z = this.useSingleQuotes;
        char c = z ? '\'' : Typography.quote;
        if (length == 0) {
            write(z ? "''" : "\"\"");
            return;
        }
        char[] cArr = IOUtils.CA;
        int i = (length / 3) * 3;
        int i2 = length - 1;
        int i3 = this.count;
        int i4 = (((i2 / 3) + 1) << 2) + i3;
        int i5 = i4 + 2;
        if (i5 > this.buf.length) {
            if (this.writer != null) {
                write(c);
                int i6 = 0;
                while (i6 < i) {
                    int i7 = i6 + 2;
                    int i8 = ((bArr[i6 + 1] & 255) << 8) | ((bArr[i6] & 255) << 16);
                    i6 += 3;
                    int i9 = i8 | (bArr[i7] & 255);
                    write(cArr[(i9 >>> 18) & 63]);
                    write(cArr[(i9 >>> 12) & 63]);
                    write(cArr[(i9 >>> 6) & 63]);
                    write(cArr[i9 & 63]);
                }
                int i10 = length - i;
                if (i10 > 0) {
                    int i11 = ((bArr[i] & 255) << 10) | (i10 == 2 ? (bArr[i2] & 255) << 2 : 0);
                    write(cArr[i11 >> 12]);
                    write(cArr[(i11 >>> 6) & 63]);
                    write(i10 == 2 ? cArr[i11 & 63] : '=');
                    write(61);
                }
                write(c);
                return;
            }
            expandCapacity(i5);
        }
        this.count = i5;
        int i12 = i3 + 1;
        this.buf[i3] = c;
        int i13 = 0;
        while (i13 < i) {
            int i14 = i13 + 2;
            int i15 = ((bArr[i13 + 1] & 255) << 8) | ((bArr[i13] & 255) << 16);
            i13 += 3;
            int i16 = i15 | (bArr[i14] & 255);
            char[] cArr2 = this.buf;
            cArr2[i12] = cArr[(i16 >>> 18) & 63];
            cArr2[i12 + 1] = cArr[(i16 >>> 12) & 63];
            int i17 = i12 + 3;
            cArr2[i12 + 2] = cArr[(i16 >>> 6) & 63];
            i12 += 4;
            cArr2[i17] = cArr[i16 & 63];
        }
        int i18 = length - i;
        if (i18 > 0) {
            int i19 = ((bArr[i] & 255) << 10) | (i18 == 2 ? (bArr[i2] & 255) << 2 : 0);
            char[] cArr3 = this.buf;
            cArr3[i4 - 3] = cArr[i19 >> 12];
            cArr3[i4 - 2] = cArr[(i19 >>> 6) & 63];
            cArr3[i4 - 1] = i18 == 2 ? cArr[i19 & 63] : '=';
            cArr3[i4] = '=';
        }
        this.buf[i4 + 1] = c;
    }

    public void writeHex(byte[] bArr) {
        int i = 2;
        int length = this.count + (bArr.length * 2) + 3;
        int i2 = 0;
        if (length > this.buf.length) {
            if (this.writer != null) {
                char[] cArr = new char[bArr.length + 3];
                cArr[0] = 'x';
                cArr[1] = '\'';
                while (i2 < bArr.length) {
                    byte b = bArr[i2];
                    int i3 = (b & 255) >> 4;
                    int i4 = b & BSON.CODE_W_SCOPE;
                    int i5 = i + 1;
                    cArr[i] = (char) (i3 + (i3 < 10 ? 48 : 55));
                    i += 2;
                    cArr[i5] = (char) (i4 + (i4 < 10 ? 48 : 55));
                    i2++;
                }
                cArr[i] = '\'';
                try {
                    this.writer.write(cArr);
                    return;
                } catch (IOException e) {
                    throw new JSONException("writeBytes error.", e);
                }
            }
            expandCapacity(length);
        }
        char[] cArr2 = this.buf;
        int i6 = this.count;
        int i7 = i6 + 1;
        this.count = i7;
        cArr2[i6] = 'x';
        this.count = i6 + 2;
        cArr2[i7] = '\'';
        while (i2 < bArr.length) {
            byte b2 = bArr[i2];
            int i8 = (b2 & 255) >> 4;
            int i9 = b2 & BSON.CODE_W_SCOPE;
            char[] cArr3 = this.buf;
            int i10 = this.count;
            int i11 = i10 + 1;
            this.count = i11;
            cArr3[i10] = (char) (i8 + (i8 < 10 ? 48 : 55));
            this.count = i10 + 2;
            cArr3[i11] = (char) (i9 + (i9 < 10 ? 48 : 55));
            i2++;
        }
        char[] cArr4 = this.buf;
        int i12 = this.count;
        this.count = i12 + 1;
        cArr4[i12] = '\'';
    }

    public void writeFloat(float f, boolean z) {
        if (f != f || f == Float.POSITIVE_INFINITY || f == Float.NEGATIVE_INFINITY) {
            writeNull();
            return;
        }
        int i = this.count + 15;
        if (i > this.buf.length) {
            if (this.writer == null) {
                expandCapacity(i);
            } else {
                String string = RyuFloat.toString(f);
                write(string, 0, string.length());
                if (z && isEnabled(SerializerFeature.WriteClassName)) {
                    write(70);
                    return;
                }
                return;
            }
        }
        this.count += RyuFloat.toString(f, this.buf, this.count);
        if (z && isEnabled(SerializerFeature.WriteClassName)) {
            write(70);
        }
    }

    public void writeDouble(double d, boolean z) {
        if (Double.isNaN(d) || Double.isInfinite(d)) {
            writeNull();
            return;
        }
        int i = this.count + 24;
        if (i > this.buf.length) {
            if (this.writer == null) {
                expandCapacity(i);
            } else {
                String string = RyuDouble.toString(d);
                write(string, 0, string.length());
                if (z && isEnabled(SerializerFeature.WriteClassName)) {
                    write(68);
                    return;
                }
                return;
            }
        }
        this.count += RyuDouble.toString(d, this.buf, this.count);
        if (z && isEnabled(SerializerFeature.WriteClassName)) {
            write(68);
        }
    }

    public void writeEnum(Enum<?> r2) {
        String string;
        if (r2 == null) {
            writeNull();
            return;
        }
        if (this.writeEnumUsingName && !this.writeEnumUsingToString) {
            string = r2.name();
        } else {
            string = this.writeEnumUsingToString ? r2.toString() : null;
        }
        if (string != null) {
            int i = isEnabled(SerializerFeature.UseSingleQuotes) ? 39 : 34;
            write(i);
            write(string);
            write(i);
            return;
        }
        writeInt(r2.ordinal());
    }

    public void writeLongAndChar(long j, char c) throws IOException {
        writeLong(j);
        write(c);
    }

    public void writeLong(long j) {
        boolean z = isEnabled(SerializerFeature.BrowserCompatible) && !isEnabled(SerializerFeature.WriteClassName) && (j > 9007199254740991L || j < -9007199254740991L);
        if (j == Long.MIN_VALUE) {
            if (z) {
                write("\"-9223372036854775808\"");
                return;
            } else {
                write("-9223372036854775808");
                return;
            }
        }
        int iStringSize = j < 0 ? IOUtils.stringSize(-j) + 1 : IOUtils.stringSize(j);
        int i = this.count + iStringSize;
        if (z) {
            i += 2;
        }
        if (i > this.buf.length) {
            if (this.writer == null) {
                expandCapacity(i);
            } else {
                char[] cArr = new char[iStringSize];
                IOUtils.getChars(j, iStringSize, cArr);
                if (z) {
                    write(34);
                    write(cArr, 0, iStringSize);
                    write(34);
                    return;
                }
                write(cArr, 0, iStringSize);
                return;
            }
        }
        if (z) {
            char[] cArr2 = this.buf;
            cArr2[this.count] = Typography.quote;
            int i2 = i - 1;
            IOUtils.getChars(j, i2, cArr2);
            this.buf[i2] = Typography.quote;
        } else {
            IOUtils.getChars(j, i, this.buf);
        }
        this.count = i;
    }

    public void writeNull() {
        write(GlobalVariable.nullColor);
    }

    public void writeNull(SerializerFeature serializerFeature) {
        writeNull(0, serializerFeature.mask);
    }

    public void writeNull(int i, int i2) {
        if ((i & i2) == 0 && (this.features & i2) == 0) {
            writeNull();
            return;
        }
        if (i2 == SerializerFeature.WriteNullListAsEmpty.mask) {
            write(_UrlKt.PATH_SEGMENT_ENCODE_SET_URI);
            return;
        }
        if (i2 == SerializerFeature.WriteNullStringAsEmpty.mask) {
            writeString("");
            return;
        }
        if (i2 == SerializerFeature.WriteNullBooleanAsFalse.mask) {
            write("false");
        } else if (i2 == SerializerFeature.WriteNullNumberAsZero.mask) {
            write(48);
        } else {
            writeNull();
        }
    }

    public void writeStringWithDoubleQuote(String str, char c) {
        int i;
        int i2;
        if (str == null) {
            writeNull();
            if (c != 0) {
                write(c);
                return;
            }
            return;
        }
        int length = str.length();
        int i3 = this.count + length;
        int i4 = i3 + 2;
        if (c != 0) {
            i4 = i3 + 3;
        }
        int length2 = this.buf.length;
        char c2 = Typography.quote;
        int i5 = 1;
        if (i4 <= length2) {
            i = 1;
        } else {
            if (this.writer != null) {
                write(34);
                int i6 = 0;
                while (i6 < str.length()) {
                    char cCharAt = str.charAt(i6);
                    if (isEnabled(SerializerFeature.BrowserSecure) && (cCharAt == '(' || cCharAt == ')' || cCharAt == '<' || cCharAt == '>')) {
                        write(92);
                        write(117);
                        write(IOUtils.DIGITS[(cCharAt >>> '\f') & 15]);
                        write(IOUtils.DIGITS[(cCharAt >>> '\b') & 15]);
                        write(IOUtils.DIGITS[(cCharAt >>> 4) & 15]);
                        write(IOUtils.DIGITS[cCharAt & 15]);
                    } else {
                        if (!isEnabled(SerializerFeature.BrowserCompatible)) {
                            if ((cCharAt < IOUtils.specicalFlags_doubleQuotes.length && IOUtils.specicalFlags_doubleQuotes[cCharAt] != 0) || (cCharAt == '/' && isEnabled(SerializerFeature.WriteSlashAsSpecial))) {
                                write(92);
                                i2 = i5;
                                if (IOUtils.specicalFlags_doubleQuotes[cCharAt] == 4) {
                                    write(117);
                                    write(IOUtils.DIGITS[(cCharAt >>> '\f') & 15]);
                                    write(IOUtils.DIGITS[(cCharAt >>> '\b') & 15]);
                                    write(IOUtils.DIGITS[(cCharAt >>> 4) & 15]);
                                    write(IOUtils.DIGITS[cCharAt & 15]);
                                } else {
                                    write(IOUtils.replaceChars[cCharAt]);
                                }
                            }
                            i6++;
                            i5 = i2;
                        } else if (cCharAt == '\b' || cCharAt == '\f' || cCharAt == '\n' || cCharAt == '\r' || cCharAt == '\t' || cCharAt == '\"' || cCharAt == '/' || cCharAt == '\\') {
                            write(92);
                            write(IOUtils.replaceChars[cCharAt]);
                        } else if (cCharAt < ' ') {
                            write(92);
                            write(117);
                            write(48);
                            write(48);
                            int i7 = cCharAt * 2;
                            write(IOUtils.ASCII_CHARS[i7]);
                            write(IOUtils.ASCII_CHARS[i7 + i5]);
                        } else if (cCharAt >= 127) {
                            write(92);
                            write(117);
                            write(IOUtils.DIGITS[(cCharAt >>> '\f') & 15]);
                            write(IOUtils.DIGITS[(cCharAt >>> '\b') & 15]);
                            write(IOUtils.DIGITS[(cCharAt >>> 4) & 15]);
                            write(IOUtils.DIGITS[cCharAt & 15]);
                        }
                        i2 = i5;
                        write(cCharAt);
                        i6++;
                        i5 = i2;
                    }
                    i2 = i5;
                    i6++;
                    i5 = i2;
                }
                write(34);
                if (c != 0) {
                    write(c);
                    return;
                }
                return;
            }
            i = 1;
            expandCapacity(i4);
        }
        int i8 = this.count;
        int i9 = i8 + 1;
        int i10 = i9 + length;
        char[] cArr = this.buf;
        cArr[i8] = Typography.quote;
        char c3 = 0;
        str.getChars(0, length, cArr, i9);
        this.count = i4;
        int i11 = -1;
        if (isEnabled(SerializerFeature.BrowserCompatible)) {
            for (int i12 = i9; i12 < i10; i12++) {
                char c4 = this.buf[i12];
                if (c4 == '\"' || c4 == '/' || c4 == '\\' || c4 == '\b' || c4 == '\f' || c4 == '\n' || c4 == '\r' || c4 == '\t') {
                    i4++;
                } else if (c4 < ' ' || c4 >= 127) {
                    i4 += 5;
                }
                i11 = i12;
            }
            if (i4 > this.buf.length) {
                expandCapacity(i4);
            }
            this.count = i4;
            while (i11 >= i9) {
                char[] cArr2 = this.buf;
                char c5 = cArr2[i11];
                if (c5 == '\b' || c5 == '\f' || c5 == '\n' || c5 == '\r' || c5 == '\t') {
                    int i13 = i11 + 1;
                    System.arraycopy(cArr2, i13, cArr2, i11 + 2, (i10 - i11) - 1);
                    char[] cArr3 = this.buf;
                    cArr3[i11] = '\\';
                    cArr3[i13] = IOUtils.replaceChars[c5];
                } else if (c5 == '\"' || c5 == '/' || c5 == '\\') {
                    int i14 = i11 + 1;
                    System.arraycopy(cArr2, i14, cArr2, i11 + 2, (i10 - i11) - 1);
                    char[] cArr4 = this.buf;
                    cArr4[i11] = '\\';
                    cArr4[i14] = c5;
                } else {
                    if (c5 < ' ') {
                        int i15 = i11 + 1;
                        System.arraycopy(cArr2, i15, cArr2, i11 + 6, (i10 - i11) - 1);
                        char[] cArr5 = this.buf;
                        cArr5[i11] = '\\';
                        cArr5[i15] = 'u';
                        cArr5[i11 + 2] = '0';
                        cArr5[i11 + 3] = '0';
                        int i16 = c5 * 2;
                        cArr5[i11 + 4] = IOUtils.ASCII_CHARS[i16];
                        this.buf[i11 + 5] = IOUtils.ASCII_CHARS[i16 + 1];
                    } else if (c5 >= 127) {
                        int i17 = i11 + 1;
                        System.arraycopy(cArr2, i17, cArr2, i11 + 6, (i10 - i11) - 1);
                        char[] cArr6 = this.buf;
                        cArr6[i11] = '\\';
                        cArr6[i17] = 'u';
                        cArr6[i11 + 2] = IOUtils.DIGITS[(c5 >>> '\f') & 15];
                        this.buf[i11 + 3] = IOUtils.DIGITS[(c5 >>> '\b') & 15];
                        this.buf[i11 + 4] = IOUtils.DIGITS[(c5 >>> 4) & 15];
                        this.buf[i11 + 5] = IOUtils.DIGITS[c5 & 15];
                    } else {
                        i11--;
                    }
                    i10 += 5;
                    i11--;
                }
                i10++;
                i11--;
            }
            if (c != 0) {
                char[] cArr7 = this.buf;
                int i18 = this.count;
                cArr7[i18 - 2] = Typography.quote;
                cArr7[i18 - 1] = c;
                return;
            }
            this.buf[this.count - 1] = Typography.quote;
            return;
        }
        int i19 = 0;
        int i20 = -1;
        int i21 = -1;
        int i22 = i9;
        while (i22 < i10) {
            char c6 = c2;
            char c7 = this.buf[i22];
            if (c7 >= ']') {
                if (c7 < 127 || !(c7 == 8232 || c7 == 8233 || c7 < 160)) {
                    i22++;
                    c2 = c6;
                } else {
                    if (i20 == i11) {
                        i20 = i22;
                    }
                    i19++;
                    i4 += 4;
                    i21 = i22;
                }
            } else if ((c7 >= '@' || (this.sepcialBits & (1 << c7)) == 0) && c7 != '\\') {
                i11 = -1;
                i22++;
                c2 = c6;
            } else {
                i19++;
                if (c7 == '(' || c7 == ')' || c7 == '<' || c7 == '>' || (c7 < IOUtils.specicalFlags_doubleQuotes.length && IOUtils.specicalFlags_doubleQuotes[c7] == 4)) {
                    i4 += 4;
                }
                i11 = -1;
                if (i20 == -1) {
                    i20 = i22;
                    i21 = i20;
                } else {
                    i21 = i22;
                }
            }
            c3 = c7;
            i22++;
            c2 = c6;
        }
        char c8 = c2;
        if (i19 > 0) {
            int i23 = i4 + i19;
            if (i23 > this.buf.length) {
                expandCapacity(i23);
            }
            this.count = i23;
            int i24 = i;
            if (i19 == i24) {
                if (c3 == 8232) {
                    int i25 = i21 + 1;
                    int i26 = (i10 - i21) - i24;
                    char[] cArr8 = this.buf;
                    System.arraycopy(cArr8, i25, cArr8, i21 + 6, i26);
                    char[] cArr9 = this.buf;
                    cArr9[i21] = '\\';
                    cArr9[i25] = 'u';
                    cArr9[i21 + 2] = '2';
                    cArr9[i21 + 3] = '0';
                    cArr9[i21 + 4] = '2';
                    cArr9[i21 + 5] = '8';
                } else if (c3 == 8233) {
                    int i27 = i21 + 1;
                    char[] cArr10 = this.buf;
                    System.arraycopy(cArr10, i27, cArr10, i21 + 6, (i10 - i21) - 1);
                    char[] cArr11 = this.buf;
                    cArr11[i21] = '\\';
                    cArr11[i27] = 'u';
                    cArr11[i21 + 2] = '2';
                    cArr11[i21 + 3] = '0';
                    cArr11[i21 + 4] = '2';
                    cArr11[i21 + 5] = '9';
                } else if (c3 == '(' || c3 == ')' || c3 == '<' || c3 == '>') {
                    int i28 = i21 + 1;
                    char[] cArr12 = this.buf;
                    System.arraycopy(cArr12, i28, cArr12, i21 + 6, (i10 - i21) - 1);
                    char[] cArr13 = this.buf;
                    cArr13[i21] = '\\';
                    cArr13[i28] = 'u';
                    cArr13[i21 + 2] = IOUtils.DIGITS[(c3 >>> '\f') & 15];
                    this.buf[i21 + 3] = IOUtils.DIGITS[(c3 >>> '\b') & 15];
                    this.buf[i21 + 4] = IOUtils.DIGITS[(c3 >>> 4) & 15];
                    this.buf[i21 + 5] = IOUtils.DIGITS[c3 & 15];
                } else if (c3 < IOUtils.specicalFlags_doubleQuotes.length && IOUtils.specicalFlags_doubleQuotes[c3] == 4) {
                    int i29 = i21 + 1;
                    char[] cArr14 = this.buf;
                    System.arraycopy(cArr14, i29, cArr14, i21 + 6, (i10 - i21) - 1);
                    char[] cArr15 = this.buf;
                    cArr15[i21] = '\\';
                    cArr15[i29] = 'u';
                    cArr15[i21 + 2] = IOUtils.DIGITS[(c3 >>> '\f') & 15];
                    this.buf[i21 + 3] = IOUtils.DIGITS[(c3 >>> '\b') & 15];
                    this.buf[i21 + 4] = IOUtils.DIGITS[(c3 >>> 4) & 15];
                    this.buf[i21 + 5] = IOUtils.DIGITS[c3 & 15];
                } else {
                    int i30 = i21 + 1;
                    char[] cArr16 = this.buf;
                    System.arraycopy(cArr16, i30, cArr16, i21 + 2, (i10 - i21) - 1);
                    char[] cArr17 = this.buf;
                    cArr17[i21] = '\\';
                    cArr17[i30] = IOUtils.replaceChars[c3];
                }
            } else if (i19 > i24) {
                for (int i31 = i20 - i9; i31 < str.length(); i31++) {
                    char cCharAt2 = str.charAt(i31);
                    if (this.browserSecure) {
                        if (cCharAt2 != '(' && cCharAt2 != ')') {
                            if (cCharAt2 == '<' || cCharAt2 == '>') {
                            }
                        }
                        char[] cArr18 = this.buf;
                        cArr18[i20] = '\\';
                        cArr18[i20 + 1] = 'u';
                        cArr18[i20 + 2] = IOUtils.DIGITS[(cCharAt2 >>> '\f') & 15];
                        this.buf[i20 + 3] = IOUtils.DIGITS[(cCharAt2 >>> '\b') & 15];
                        int i32 = i20 + 5;
                        this.buf[i20 + 4] = IOUtils.DIGITS[(cCharAt2 >>> 4) & 15];
                        i20 += 6;
                        this.buf[i32] = IOUtils.DIGITS[cCharAt2 & 15];
                    }
                    if ((cCharAt2 < IOUtils.specicalFlags_doubleQuotes.length && IOUtils.specicalFlags_doubleQuotes[cCharAt2] != 0) || (cCharAt2 == '/' && isEnabled(SerializerFeature.WriteSlashAsSpecial))) {
                        int i33 = i20 + 1;
                        this.buf[i20] = '\\';
                        if (IOUtils.specicalFlags_doubleQuotes[cCharAt2] == 4) {
                            char[] cArr19 = this.buf;
                            cArr19[i33] = 'u';
                            cArr19[i20 + 2] = IOUtils.DIGITS[(cCharAt2 >>> '\f') & 15];
                            this.buf[i20 + 3] = IOUtils.DIGITS[(cCharAt2 >>> '\b') & 15];
                            int i34 = i20 + 5;
                            this.buf[i20 + 4] = IOUtils.DIGITS[(cCharAt2 >>> 4) & 15];
                            i20 += 6;
                            this.buf[i34] = IOUtils.DIGITS[cCharAt2 & 15];
                        } else {
                            i20 += 2;
                            this.buf[i33] = IOUtils.replaceChars[cCharAt2];
                        }
                    } else if (cCharAt2 == 8232 || cCharAt2 == 8233) {
                        char[] cArr20 = this.buf;
                        cArr20[i20] = '\\';
                        cArr20[i20 + 1] = 'u';
                        cArr20[i20 + 2] = IOUtils.DIGITS[(cCharAt2 >>> '\f') & 15];
                        this.buf[i20 + 3] = IOUtils.DIGITS[(cCharAt2 >>> '\b') & 15];
                        int i35 = i20 + 5;
                        this.buf[i20 + 4] = IOUtils.DIGITS[(cCharAt2 >>> 4) & 15];
                        i20 += 6;
                        this.buf[i35] = IOUtils.DIGITS[cCharAt2 & 15];
                    } else {
                        this.buf[i20] = cCharAt2;
                        i20++;
                    }
                }
            }
        }
        if (c != 0) {
            char[] cArr21 = this.buf;
            int i36 = this.count;
            cArr21[i36 - 2] = c8;
            cArr21[i36 - 1] = c;
            return;
        }
        this.buf[this.count - 1] = c8;
    }

    public void writeStringWithDoubleQuote(char[] cArr, char c) {
        int i;
        int i2;
        int i3;
        int i4;
        if (cArr == null) {
            writeNull();
            if (c != 0) {
                write(c);
                return;
            }
            return;
        }
        int length = cArr.length;
        int i5 = this.count + length;
        int i6 = i5 + 2;
        if (c != 0) {
            i6 = i5 + 3;
        }
        int length2 = this.buf.length;
        char c2 = Typography.quote;
        int i7 = 1;
        if (i6 <= length2) {
            i = 1;
        } else {
            if (this.writer != null) {
                write(34);
                int i8 = 0;
                while (i8 < cArr.length) {
                    char c3 = cArr[i8];
                    if (isEnabled(SerializerFeature.BrowserSecure) && (c3 == '(' || c3 == ')' || c3 == '<' || c3 == '>')) {
                        write(92);
                        write(117);
                        write(IOUtils.DIGITS[(c3 >>> '\f') & 15]);
                        write(IOUtils.DIGITS[(c3 >>> '\b') & 15]);
                        write(IOUtils.DIGITS[(c3 >>> 4) & 15]);
                        write(IOUtils.DIGITS[c3 & 15]);
                    } else {
                        if (!isEnabled(SerializerFeature.BrowserCompatible)) {
                            if ((c3 < IOUtils.specicalFlags_doubleQuotes.length && IOUtils.specicalFlags_doubleQuotes[c3] != 0) || (c3 == '/' && isEnabled(SerializerFeature.WriteSlashAsSpecial))) {
                                write(92);
                                i4 = i7;
                                if (IOUtils.specicalFlags_doubleQuotes[c3] == 4) {
                                    write(117);
                                    write(IOUtils.DIGITS[(c3 >>> '\f') & 15]);
                                    write(IOUtils.DIGITS[(c3 >>> '\b') & 15]);
                                    write(IOUtils.DIGITS[(c3 >>> 4) & 15]);
                                    write(IOUtils.DIGITS[c3 & 15]);
                                } else {
                                    write(IOUtils.replaceChars[c3]);
                                }
                            }
                            i8++;
                            i7 = i4;
                        } else if (c3 == '\b' || c3 == '\f' || c3 == '\n' || c3 == '\r' || c3 == '\t' || c3 == '\"' || c3 == '/' || c3 == '\\') {
                            write(92);
                            write(IOUtils.replaceChars[c3]);
                        } else if (c3 < ' ') {
                            write(92);
                            write(117);
                            write(48);
                            write(48);
                            int i9 = c3 * 2;
                            write(IOUtils.ASCII_CHARS[i9]);
                            write(IOUtils.ASCII_CHARS[i9 + i7]);
                        } else if (c3 >= 127) {
                            write(92);
                            write(117);
                            write(IOUtils.DIGITS[(c3 >>> '\f') & 15]);
                            write(IOUtils.DIGITS[(c3 >>> '\b') & 15]);
                            write(IOUtils.DIGITS[(c3 >>> 4) & 15]);
                            write(IOUtils.DIGITS[c3 & 15]);
                        }
                        i4 = i7;
                        write(c3);
                        i8++;
                        i7 = i4;
                    }
                    i4 = i7;
                    i8++;
                    i7 = i4;
                }
                write(34);
                if (c != 0) {
                    write(c);
                    return;
                }
                return;
            }
            i = 1;
            expandCapacity(i6);
        }
        int i10 = this.count;
        int i11 = i10 + 1;
        int i12 = length + i11;
        char[] cArr2 = this.buf;
        cArr2[i10] = Typography.quote;
        char c4 = 0;
        System.arraycopy(cArr, 0, cArr2, i11, cArr.length);
        this.count = i6;
        int i13 = -1;
        if (isEnabled(SerializerFeature.BrowserCompatible)) {
            for (int i14 = i11; i14 < i12; i14++) {
                char c5 = this.buf[i14];
                if (c5 == '\"' || c5 == '/' || c5 == '\\' || c5 == '\b' || c5 == '\f' || c5 == '\n' || c5 == '\r' || c5 == '\t') {
                    i6++;
                } else if (c5 < ' ' || c5 >= 127) {
                    i6 += 5;
                }
                i13 = i14;
            }
            if (i6 > this.buf.length) {
                expandCapacity(i6);
            }
            this.count = i6;
            while (i13 >= i11) {
                char[] cArr3 = this.buf;
                char c6 = cArr3[i13];
                if (c6 == '\b' || c6 == '\f' || c6 == '\n' || c6 == '\r' || c6 == '\t') {
                    int i15 = i13 + 1;
                    System.arraycopy(cArr3, i15, cArr3, i13 + 2, (i12 - i13) - 1);
                    char[] cArr4 = this.buf;
                    cArr4[i13] = '\\';
                    cArr4[i15] = IOUtils.replaceChars[c6];
                } else if (c6 == '\"' || c6 == '/' || c6 == '\\') {
                    int i16 = i13 + 1;
                    System.arraycopy(cArr3, i16, cArr3, i13 + 2, (i12 - i13) - 1);
                    char[] cArr5 = this.buf;
                    cArr5[i13] = '\\';
                    cArr5[i16] = c6;
                } else {
                    if (c6 < ' ') {
                        int i17 = i13 + 1;
                        System.arraycopy(cArr3, i17, cArr3, i13 + 6, (i12 - i13) - 1);
                        char[] cArr6 = this.buf;
                        cArr6[i13] = '\\';
                        cArr6[i17] = 'u';
                        cArr6[i13 + 2] = '0';
                        cArr6[i13 + 3] = '0';
                        int i18 = c6 * 2;
                        cArr6[i13 + 4] = IOUtils.ASCII_CHARS[i18];
                        this.buf[i13 + 5] = IOUtils.ASCII_CHARS[i18 + 1];
                    } else if (c6 >= 127) {
                        int i19 = i13 + 1;
                        System.arraycopy(cArr3, i19, cArr3, i13 + 6, (i12 - i13) - 1);
                        char[] cArr7 = this.buf;
                        cArr7[i13] = '\\';
                        cArr7[i19] = 'u';
                        cArr7[i13 + 2] = IOUtils.DIGITS[(c6 >>> '\f') & 15];
                        this.buf[i13 + 3] = IOUtils.DIGITS[(c6 >>> '\b') & 15];
                        this.buf[i13 + 4] = IOUtils.DIGITS[(c6 >>> 4) & 15];
                        this.buf[i13 + 5] = IOUtils.DIGITS[c6 & 15];
                    } else {
                        i13--;
                    }
                    i12 += 5;
                    i13--;
                }
                i12++;
                i13--;
            }
            if (c != 0) {
                char[] cArr8 = this.buf;
                int i20 = this.count;
                cArr8[i20 - 2] = Typography.quote;
                cArr8[i20 - 1] = c;
                return;
            }
            this.buf[this.count - 1] = Typography.quote;
            return;
        }
        int i21 = 0;
        int i22 = -1;
        int i23 = -1;
        int i24 = i11;
        while (i24 < i12) {
            char c7 = c2;
            char c8 = this.buf[i24];
            if (c8 < ']') {
                i2 = i21;
                if ((c8 >= '@' || (this.sepcialBits & (1 << c8)) == 0) && c8 != '\\') {
                    i3 = -1;
                    i21 = i2;
                } else {
                    i21 = i2 + 1;
                    if (c8 == '(' || c8 == ')' || c8 == '<' || c8 == '>' || (c8 < IOUtils.specicalFlags_doubleQuotes.length && IOUtils.specicalFlags_doubleQuotes[c8] == 4)) {
                        i6 += 4;
                    }
                    i3 = -1;
                    if (i22 == -1) {
                        i22 = i24;
                        i23 = i22;
                    } else {
                        i23 = i24;
                    }
                    c4 = c8;
                }
            } else if (c8 < 127 || !(c8 == 8232 || c8 == 8233 || c8 < 160)) {
                i3 = i13;
                i2 = i21;
                i21 = i2;
            } else {
                if (i22 == i13) {
                    i22 = i24;
                }
                i21++;
                i6 += 4;
                i23 = i24;
                i3 = i13;
                c4 = c8;
            }
            i24++;
            i13 = i3;
            c2 = c7;
        }
        int i25 = i21;
        char c9 = c2;
        if (i25 > 0) {
            int i26 = i6 + i25;
            if (i26 > this.buf.length) {
                expandCapacity(i26);
            }
            this.count = i26;
            int i27 = i;
            if (i25 == i27) {
                if (c4 == 8232) {
                    int i28 = i23 + 1;
                    int i29 = (i12 - i23) - i27;
                    char[] cArr9 = this.buf;
                    System.arraycopy(cArr9, i28, cArr9, i23 + 6, i29);
                    char[] cArr10 = this.buf;
                    cArr10[i23] = '\\';
                    cArr10[i28] = 'u';
                    cArr10[i23 + 2] = '2';
                    cArr10[i23 + 3] = '0';
                    cArr10[i23 + 4] = '2';
                    cArr10[i23 + 5] = '8';
                } else if (c4 == 8233) {
                    int i30 = i23 + 1;
                    char[] cArr11 = this.buf;
                    System.arraycopy(cArr11, i30, cArr11, i23 + 6, (i12 - i23) - 1);
                    char[] cArr12 = this.buf;
                    cArr12[i23] = '\\';
                    cArr12[i30] = 'u';
                    cArr12[i23 + 2] = '2';
                    cArr12[i23 + 3] = '0';
                    cArr12[i23 + 4] = '2';
                    cArr12[i23 + 5] = '9';
                } else if (c4 == '(' || c4 == ')' || c4 == '<' || c4 == '>') {
                    int i31 = i23 + 1;
                    char[] cArr13 = this.buf;
                    System.arraycopy(cArr13, i31, cArr13, i23 + 6, (i12 - i23) - 1);
                    char[] cArr14 = this.buf;
                    cArr14[i23] = '\\';
                    cArr14[i31] = 'u';
                    cArr14[i23 + 2] = IOUtils.DIGITS[(c4 >>> '\f') & 15];
                    this.buf[i23 + 3] = IOUtils.DIGITS[(c4 >>> '\b') & 15];
                    this.buf[i23 + 4] = IOUtils.DIGITS[(c4 >>> 4) & 15];
                    this.buf[i23 + 5] = IOUtils.DIGITS[c4 & 15];
                } else if (c4 < IOUtils.specicalFlags_doubleQuotes.length && IOUtils.specicalFlags_doubleQuotes[c4] == 4) {
                    int i32 = i23 + 1;
                    char[] cArr15 = this.buf;
                    System.arraycopy(cArr15, i32, cArr15, i23 + 6, (i12 - i23) - 1);
                    char[] cArr16 = this.buf;
                    cArr16[i23] = '\\';
                    cArr16[i32] = 'u';
                    cArr16[i23 + 2] = IOUtils.DIGITS[(c4 >>> '\f') & 15];
                    this.buf[i23 + 3] = IOUtils.DIGITS[(c4 >>> '\b') & 15];
                    this.buf[i23 + 4] = IOUtils.DIGITS[(c4 >>> 4) & 15];
                    this.buf[i23 + 5] = IOUtils.DIGITS[c4 & 15];
                } else {
                    int i33 = i23 + 1;
                    char[] cArr17 = this.buf;
                    System.arraycopy(cArr17, i33, cArr17, i23 + 2, (i12 - i23) - 1);
                    char[] cArr18 = this.buf;
                    cArr18[i23] = '\\';
                    cArr18[i33] = IOUtils.replaceChars[c4];
                }
            } else if (i25 > i27) {
                for (int i34 = i22 - i11; i34 < cArr.length; i34++) {
                    char c10 = cArr[i34];
                    if (this.browserSecure) {
                        if (c10 != '(' && c10 != ')') {
                            if (c10 == '<' || c10 == '>') {
                            }
                        }
                        char[] cArr19 = this.buf;
                        cArr19[i22] = '\\';
                        cArr19[i22 + 1] = 'u';
                        cArr19[i22 + 2] = IOUtils.DIGITS[(c10 >>> '\f') & 15];
                        this.buf[i22 + 3] = IOUtils.DIGITS[(c10 >>> '\b') & 15];
                        int i35 = i22 + 5;
                        this.buf[i22 + 4] = IOUtils.DIGITS[(c10 >>> 4) & 15];
                        i22 += 6;
                        this.buf[i35] = IOUtils.DIGITS[c10 & 15];
                    }
                    if ((c10 < IOUtils.specicalFlags_doubleQuotes.length && IOUtils.specicalFlags_doubleQuotes[c10] != 0) || (c10 == '/' && isEnabled(SerializerFeature.WriteSlashAsSpecial))) {
                        int i36 = i22 + 1;
                        this.buf[i22] = '\\';
                        if (IOUtils.specicalFlags_doubleQuotes[c10] == 4) {
                            char[] cArr20 = this.buf;
                            cArr20[i36] = 'u';
                            cArr20[i22 + 2] = IOUtils.DIGITS[(c10 >>> '\f') & 15];
                            this.buf[i22 + 3] = IOUtils.DIGITS[(c10 >>> '\b') & 15];
                            int i37 = i22 + 5;
                            this.buf[i22 + 4] = IOUtils.DIGITS[(c10 >>> 4) & 15];
                            i22 += 6;
                            this.buf[i37] = IOUtils.DIGITS[c10 & 15];
                        } else {
                            i22 += 2;
                            this.buf[i36] = IOUtils.replaceChars[c10];
                        }
                    } else if (c10 == 8232 || c10 == 8233) {
                        char[] cArr21 = this.buf;
                        cArr21[i22] = '\\';
                        cArr21[i22 + 1] = 'u';
                        cArr21[i22 + 2] = IOUtils.DIGITS[(c10 >>> '\f') & 15];
                        this.buf[i22 + 3] = IOUtils.DIGITS[(c10 >>> '\b') & 15];
                        int i38 = i22 + 5;
                        this.buf[i22 + 4] = IOUtils.DIGITS[(c10 >>> 4) & 15];
                        i22 += 6;
                        this.buf[i38] = IOUtils.DIGITS[c10 & 15];
                    } else {
                        this.buf[i22] = c10;
                        i22++;
                    }
                }
            }
        }
        if (c != 0) {
            char[] cArr22 = this.buf;
            int i39 = this.count;
            cArr22[i39 - 2] = c9;
            cArr22[i39 - 1] = c;
            return;
        }
        this.buf[this.count - 1] = c9;
    }

    public void writeFieldNameDirect(String str) {
        int length = str.length();
        int i = this.count + length;
        int i2 = i + 3;
        if (i2 > this.buf.length) {
            expandCapacity(i2);
        }
        int i3 = this.count;
        char[] cArr = this.buf;
        cArr[i3] = Typography.quote;
        str.getChars(0, length, cArr, i3 + 1);
        this.count = i2;
        char[] cArr2 = this.buf;
        cArr2[i + 1] = Typography.quote;
        cArr2[i + 2] = ':';
    }

    public void write(List<String> list) {
        boolean z;
        int i;
        if (list.isEmpty()) {
            write(_UrlKt.PATH_SEGMENT_ENCODE_SET_URI);
            return;
        }
        int i2 = this.count;
        int size = list.size();
        int i3 = i2;
        int i4 = 0;
        while (i4 < size) {
            String str = list.get(i4);
            if (str == null) {
                z = true;
            } else {
                int length = str.length();
                z = false;
                for (int i5 = 0; i5 < length; i5++) {
                    char cCharAt = str.charAt(i5);
                    z = cCharAt < ' ' || cCharAt > '~' || cCharAt == '\"' || cCharAt == '\\';
                    if (z) {
                        break;
                    }
                }
            }
            if (z) {
                this.count = i2;
                write(91);
                for (int i6 = 0; i6 < list.size(); i6++) {
                    String str2 = list.get(i6);
                    if (i6 != 0) {
                        write(44);
                    }
                    if (str2 == null) {
                        write(GlobalVariable.nullColor);
                    } else {
                        writeStringWithDoubleQuote(str2, (char) 0);
                    }
                }
                write(93);
                return;
            }
            int length2 = str.length() + i3;
            int i7 = length2 + 3;
            if (i4 == list.size() - 1) {
                i7 = length2 + 4;
            }
            if (i7 > this.buf.length) {
                this.count = i3;
                expandCapacity(i7);
            }
            if (i4 == 0) {
                i = i3 + 1;
                this.buf[i3] = '[';
            } else {
                i = i3 + 1;
                this.buf[i3] = ',';
            }
            int i8 = i + 1;
            this.buf[i] = Typography.quote;
            str.getChars(0, str.length(), this.buf, i8);
            int length3 = i8 + str.length();
            this.buf[length3] = Typography.quote;
            i4++;
            i3 = length3 + 1;
        }
        this.buf[i3] = ']';
        this.count = i3 + 1;
    }

    public void writeFieldValue(char c, String str, char c2) {
        write(c);
        writeFieldName(str);
        if (c2 == 0) {
            writeString("\u0000");
        } else {
            writeString(Character.toString(c2));
        }
    }

    public void writeFieldValue(char c, String str, boolean z) {
        if (!this.quoteFieldNames) {
            write(c);
            writeFieldName(str);
            write(z);
            return;
        }
        int i = z ? 4 : 5;
        int length = str.length();
        int i2 = this.count + length + 4 + i;
        if (i2 > this.buf.length) {
            if (this.writer != null) {
                write(c);
                writeString(str);
                write(58);
                write(z);
                return;
            }
            expandCapacity(i2);
        }
        int i3 = this.count;
        this.count = i2;
        char[] cArr = this.buf;
        cArr[i3] = c;
        int i4 = i3 + length;
        cArr[i3 + 1] = this.keySeperator;
        str.getChars(0, length, cArr, i3 + 2);
        this.buf[i4 + 2] = this.keySeperator;
        if (z) {
            System.arraycopy(":true".toCharArray(), 0, this.buf, i4 + 3, 5);
        } else {
            System.arraycopy(":false".toCharArray(), 0, this.buf, i4 + 3, 6);
        }
    }

    public void write(boolean z) {
        if (z) {
            write("true");
        } else {
            write("false");
        }
    }

    public void writeFieldValue(char c, String str, int i) {
        if (i == Integer.MIN_VALUE || !this.quoteFieldNames) {
            write(c);
            writeFieldName(str);
            writeInt(i);
            return;
        }
        int iStringSize = i < 0 ? IOUtils.stringSize(-i) + 1 : IOUtils.stringSize(i);
        int length = str.length();
        int i2 = this.count + length + 4 + iStringSize;
        if (i2 > this.buf.length) {
            if (this.writer != null) {
                write(c);
                writeFieldName(str);
                writeInt(i);
                return;
            }
            expandCapacity(i2);
        }
        int i3 = this.count;
        this.count = i2;
        char[] cArr = this.buf;
        cArr[i3] = c;
        int i4 = i3 + length;
        cArr[i3 + 1] = this.keySeperator;
        str.getChars(0, length, cArr, i3 + 2);
        char[] cArr2 = this.buf;
        cArr2[i4 + 2] = this.keySeperator;
        cArr2[i4 + 3] = ':';
        IOUtils.getChars(i, this.count, cArr2);
    }

    public void writeFieldValue(char c, String str, long j) {
        if (j == Long.MIN_VALUE || !this.quoteFieldNames || isEnabled(SerializerFeature.BrowserCompatible.mask)) {
            write(c);
            writeFieldName(str);
            writeLong(j);
            return;
        }
        int iStringSize = j < 0 ? IOUtils.stringSize(-j) + 1 : IOUtils.stringSize(j);
        int length = str.length();
        int i = this.count + length + 4 + iStringSize;
        if (i > this.buf.length) {
            if (this.writer != null) {
                write(c);
                writeFieldName(str);
                writeLong(j);
                return;
            }
            expandCapacity(i);
        }
        int i2 = this.count;
        this.count = i;
        char[] cArr = this.buf;
        cArr[i2] = c;
        int i3 = i2 + length;
        cArr[i2 + 1] = this.keySeperator;
        str.getChars(0, length, cArr, i2 + 2);
        char[] cArr2 = this.buf;
        cArr2[i3 + 2] = this.keySeperator;
        cArr2[i3 + 3] = ':';
        IOUtils.getChars(j, this.count, cArr2);
    }

    public void writeFieldValue(char c, String str, float f) {
        write(c);
        writeFieldName(str);
        writeFloat(f, false);
    }

    public void writeFieldValue(char c, String str, double d) {
        write(c);
        writeFieldName(str);
        writeDouble(d, false);
    }

    public void writeFieldValue(char c, String str, String str2) {
        if (this.quoteFieldNames) {
            if (this.useSingleQuotes) {
                write(c);
                writeFieldName(str);
                if (str2 == null) {
                    writeNull();
                    return;
                } else {
                    writeString(str2);
                    return;
                }
            }
            if (isEnabled(SerializerFeature.BrowserCompatible)) {
                write(c);
                writeStringWithDoubleQuote(str, ':');
                writeStringWithDoubleQuote(str2, (char) 0);
                return;
            }
            writeFieldValueStringWithDoubleQuoteCheck(c, str, str2);
            return;
        }
        write(c);
        writeFieldName(str);
        if (str2 == null) {
            writeNull();
        } else {
            writeString(str2);
        }
    }

    public void writeFieldValueStringWithDoubleQuoteCheck(char c, String str, String str2) {
        int length;
        int i;
        char c2;
        char c3;
        int i2;
        int length2 = str.length();
        int i3 = this.count;
        if (str2 == null) {
            i = i3 + length2 + 8;
            length = 4;
        } else {
            length = str2.length();
            i = i3 + length2 + length + 6;
        }
        int i4 = 0;
        if (i > this.buf.length) {
            if (this.writer != null) {
                write(c);
                writeStringWithDoubleQuote(str, ':');
                writeStringWithDoubleQuote(str2, (char) 0);
                return;
            }
            expandCapacity(i);
        }
        char[] cArr = this.buf;
        int i5 = this.count;
        cArr[i5] = c;
        int i6 = i5 + 2;
        int i7 = i6 + length2;
        char c4 = Typography.quote;
        cArr[i5 + 1] = Typography.quote;
        str.getChars(0, length2, cArr, i6);
        this.count = i;
        char[] cArr2 = this.buf;
        cArr2[i7] = Typography.quote;
        int i8 = i7 + 2;
        cArr2[i7 + 1] = ':';
        char c5 = 'u';
        if (str2 == null) {
            cArr2[i8] = 'n';
            cArr2[i7 + 3] = 'u';
            cArr2[i7 + 4] = 'l';
            cArr2[i7 + 5] = 'l';
            return;
        }
        int i9 = i7 + 3;
        cArr2[i8] = Typography.quote;
        int i10 = i9 + length;
        str2.getChars(0, length, cArr2, i9);
        int i11 = -1;
        int i12 = -1;
        int i13 = -1;
        char c6 = 0;
        int i14 = i9;
        while (true) {
            c2 = c5;
            c3 = c4;
            if (i14 >= i10) {
                break;
            }
            char c7 = this.buf[i14];
            if (c7 >= ']') {
                if (c7 < 127 || !(c7 == 8232 || c7 == 8233 || c7 < 160)) {
                    i2 = i11;
                    i14++;
                    c5 = c2;
                    i11 = i2;
                    c4 = c3;
                } else {
                    if (i12 == i11) {
                        i12 = i14;
                    }
                    i4++;
                    i += 4;
                    i2 = i11;
                    i13 = i14;
                }
            } else if ((c7 >= '@' || (this.sepcialBits & (1 << c7)) == 0) && c7 != '\\') {
                i2 = -1;
                i14++;
                c5 = c2;
                i11 = i2;
                c4 = c3;
            } else {
                i4++;
                if (c7 == '(' || c7 == ')' || c7 == '<' || c7 == '>' || (c7 < IOUtils.specicalFlags_doubleQuotes.length && IOUtils.specicalFlags_doubleQuotes[c7] == 4)) {
                    i += 4;
                }
                i2 = -1;
                if (i12 == -1) {
                    i12 = i14;
                    i13 = i12;
                } else {
                    i13 = i14;
                }
            }
            c6 = c7;
            i14++;
            c5 = c2;
            i11 = i2;
            c4 = c3;
        }
        if (i4 > 0) {
            int i15 = i + i4;
            if (i15 > this.buf.length) {
                expandCapacity(i15);
            }
            this.count = i15;
            if (i4 == 1) {
                if (c6 == 8232) {
                    int i16 = i13 + 1;
                    char[] cArr3 = this.buf;
                    System.arraycopy(cArr3, i16, cArr3, i13 + 6, (i10 - i13) - 1);
                    char[] cArr4 = this.buf;
                    cArr4[i13] = '\\';
                    cArr4[i16] = c2;
                    cArr4[i13 + 2] = '2';
                    cArr4[i13 + 3] = '0';
                    cArr4[i13 + 4] = '2';
                    cArr4[i13 + 5] = '8';
                } else if (c6 == 8233) {
                    int i17 = i13 + 1;
                    char[] cArr5 = this.buf;
                    System.arraycopy(cArr5, i17, cArr5, i13 + 6, (i10 - i13) - 1);
                    char[] cArr6 = this.buf;
                    cArr6[i13] = '\\';
                    cArr6[i17] = c2;
                    cArr6[i13 + 2] = '2';
                    cArr6[i13 + 3] = '0';
                    cArr6[i13 + 4] = '2';
                    cArr6[i13 + 5] = '9';
                } else if (c6 == '(' || c6 == ')' || c6 == '<' || c6 == '>') {
                    int i18 = i13 + 1;
                    char[] cArr7 = this.buf;
                    System.arraycopy(cArr7, i18, cArr7, i13 + 6, (i10 - i13) - 1);
                    char[] cArr8 = this.buf;
                    cArr8[i13] = '\\';
                    cArr8[i18] = c2;
                    cArr8[i13 + 2] = IOUtils.DIGITS[(c6 >>> '\f') & 15];
                    this.buf[i13 + 3] = IOUtils.DIGITS[(c6 >>> '\b') & 15];
                    this.buf[i13 + 4] = IOUtils.DIGITS[(c6 >>> 4) & 15];
                    this.buf[i13 + 5] = IOUtils.DIGITS[c6 & 15];
                } else if (c6 < IOUtils.specicalFlags_doubleQuotes.length && IOUtils.specicalFlags_doubleQuotes[c6] == 4) {
                    int i19 = i13 + 1;
                    char[] cArr9 = this.buf;
                    System.arraycopy(cArr9, i19, cArr9, i13 + 6, (i10 - i13) - 1);
                    char[] cArr10 = this.buf;
                    cArr10[i13] = '\\';
                    cArr10[i19] = c2;
                    cArr10[i13 + 2] = IOUtils.DIGITS[(c6 >>> '\f') & 15];
                    this.buf[i13 + 3] = IOUtils.DIGITS[(c6 >>> '\b') & 15];
                    this.buf[i13 + 4] = IOUtils.DIGITS[(c6 >>> 4) & 15];
                    this.buf[i13 + 5] = IOUtils.DIGITS[c6 & 15];
                } else {
                    int i20 = i13 + 1;
                    char[] cArr11 = this.buf;
                    System.arraycopy(cArr11, i20, cArr11, i13 + 2, (i10 - i13) - 1);
                    char[] cArr12 = this.buf;
                    cArr12[i13] = '\\';
                    cArr12[i20] = IOUtils.replaceChars[c6];
                }
            } else if (i4 > 1) {
                for (int i21 = i12 - i9; i21 < str2.length(); i21++) {
                    char cCharAt = str2.charAt(i21);
                    if (this.browserSecure) {
                        if (cCharAt != '(' && cCharAt != ')') {
                            if (cCharAt == '<' || cCharAt == '>') {
                            }
                        }
                        char[] cArr13 = this.buf;
                        cArr13[i12] = '\\';
                        cArr13[i12 + 1] = c2;
                        cArr13[i12 + 2] = IOUtils.DIGITS[(cCharAt >>> '\f') & 15];
                        this.buf[i12 + 3] = IOUtils.DIGITS[(cCharAt >>> '\b') & 15];
                        int i22 = i12 + 5;
                        this.buf[i12 + 4] = IOUtils.DIGITS[(cCharAt >>> 4) & 15];
                        i12 += 6;
                        this.buf[i22] = IOUtils.DIGITS[cCharAt & 15];
                    }
                    if ((cCharAt < IOUtils.specicalFlags_doubleQuotes.length && IOUtils.specicalFlags_doubleQuotes[cCharAt] != 0) || (cCharAt == '/' && isEnabled(SerializerFeature.WriteSlashAsSpecial))) {
                        int i23 = i12 + 1;
                        this.buf[i12] = '\\';
                        if (IOUtils.specicalFlags_doubleQuotes[cCharAt] == 4) {
                            char[] cArr14 = this.buf;
                            cArr14[i23] = c2;
                            cArr14[i12 + 2] = IOUtils.DIGITS[(cCharAt >>> '\f') & 15];
                            this.buf[i12 + 3] = IOUtils.DIGITS[(cCharAt >>> '\b') & 15];
                            int i24 = i12 + 5;
                            this.buf[i12 + 4] = IOUtils.DIGITS[(cCharAt >>> 4) & 15];
                            i12 += 6;
                            this.buf[i24] = IOUtils.DIGITS[cCharAt & 15];
                        } else {
                            i12 += 2;
                            this.buf[i23] = IOUtils.replaceChars[cCharAt];
                        }
                    } else if (cCharAt == 8232 || cCharAt == 8233) {
                        char[] cArr15 = this.buf;
                        cArr15[i12] = '\\';
                        cArr15[i12 + 1] = c2;
                        cArr15[i12 + 2] = IOUtils.DIGITS[(cCharAt >>> '\f') & 15];
                        this.buf[i12 + 3] = IOUtils.DIGITS[(cCharAt >>> '\b') & 15];
                        int i25 = i12 + 5;
                        this.buf[i12 + 4] = IOUtils.DIGITS[(cCharAt >>> 4) & 15];
                        i12 += 6;
                        this.buf[i25] = IOUtils.DIGITS[cCharAt & 15];
                    } else {
                        this.buf[i12] = cCharAt;
                        i12++;
                    }
                }
            }
        }
        this.buf[this.count - 1] = c3;
    }

    public void writeFieldValueStringWithDoubleQuote(char c, String str, String str2) {
        int length = str.length();
        int i = this.count;
        int length2 = str2.length();
        int i2 = i + length + length2 + 6;
        if (i2 > this.buf.length) {
            if (this.writer != null) {
                write(c);
                writeStringWithDoubleQuote(str, ':');
                writeStringWithDoubleQuote(str2, (char) 0);
                return;
            }
            expandCapacity(i2);
        }
        char[] cArr = this.buf;
        int i3 = this.count;
        cArr[i3] = c;
        int i4 = i3 + 2;
        int i5 = i4 + length;
        cArr[i3 + 1] = Typography.quote;
        str.getChars(0, length, cArr, i4);
        this.count = i2;
        char[] cArr2 = this.buf;
        cArr2[i5] = Typography.quote;
        cArr2[i5 + 1] = ':';
        cArr2[i5 + 2] = Typography.quote;
        str2.getChars(0, length2, cArr2, i5 + 3);
        this.buf[this.count - 1] = Typography.quote;
    }

    public void writeFieldValue(char c, String str, Enum<?> r4) {
        if (r4 == null) {
            write(c);
            writeFieldName(str);
            writeNull();
        } else if (this.writeEnumUsingName && !this.writeEnumUsingToString) {
            writeEnumFieldValue(c, str, r4.name());
        } else if (this.writeEnumUsingToString) {
            writeEnumFieldValue(c, str, r4.toString());
        } else {
            writeFieldValue(c, str, r4.ordinal());
        }
    }

    private void writeEnumFieldValue(char c, String str, String str2) {
        if (this.useSingleQuotes) {
            writeFieldValue(c, str, str2);
        } else {
            writeFieldValueStringWithDoubleQuote(c, str, str2);
        }
    }

    public void writeFieldValue(char c, String str, BigDecimal bigDecimal) {
        String string;
        write(c);
        writeFieldName(str);
        if (bigDecimal == null) {
            writeNull();
            return;
        }
        int iScale = bigDecimal.scale();
        if (isEnabled(SerializerFeature.WriteBigDecimalAsPlain) && iScale >= -100 && iScale < 100) {
            string = bigDecimal.toPlainString();
        } else {
            string = bigDecimal.toString();
        }
        write(string);
    }

    public void writeString(String str, char c) {
        if (this.useSingleQuotes) {
            writeStringWithSingleQuote(str);
            write(c);
        } else {
            writeStringWithDoubleQuote(str, c);
        }
    }

    public void writeString(String str) {
        if (this.useSingleQuotes) {
            writeStringWithSingleQuote(str);
        } else {
            writeStringWithDoubleQuote(str, (char) 0);
        }
    }

    public void writeString(char[] cArr) {
        if (this.useSingleQuotes) {
            writeStringWithSingleQuote(cArr);
        } else {
            writeStringWithDoubleQuote(new String(cArr), (char) 0);
        }
    }

    protected void writeStringWithSingleQuote(String str) {
        int i = 0;
        if (str == null) {
            int i2 = this.count + 4;
            if (i2 > this.buf.length) {
                expandCapacity(i2);
            }
            GlobalVariable.nullColor.getChars(0, 4, this.buf, this.count);
            this.count = i2;
            return;
        }
        int length = str.length();
        int i3 = this.count + length + 2;
        if (i3 > this.buf.length) {
            if (this.writer != null) {
                write(39);
                while (i < str.length()) {
                    char cCharAt = str.charAt(i);
                    if (cCharAt <= '\r' || cCharAt == '\\' || cCharAt == '\'' || (cCharAt == '/' && isEnabled(SerializerFeature.WriteSlashAsSpecial))) {
                        write(92);
                        write(IOUtils.replaceChars[cCharAt]);
                    } else {
                        write(cCharAt);
                    }
                    i++;
                }
                write(39);
                return;
            }
            expandCapacity(i3);
        }
        int i4 = this.count;
        int i5 = i4 + 1;
        int i6 = i5 + length;
        char[] cArr = this.buf;
        cArr[i4] = '\'';
        str.getChars(0, length, cArr, i5);
        this.count = i3;
        int i7 = -1;
        char c = 0;
        for (int i8 = i5; i8 < i6; i8++) {
            char c2 = this.buf[i8];
            if (c2 <= '\r' || c2 == '\\' || c2 == '\'' || (c2 == '/' && isEnabled(SerializerFeature.WriteSlashAsSpecial))) {
                i++;
                i7 = i8;
                c = c2;
            }
        }
        int i9 = i3 + i;
        if (i9 > this.buf.length) {
            expandCapacity(i9);
        }
        this.count = i9;
        if (i == 1) {
            char[] cArr2 = this.buf;
            int i10 = i7 + 1;
            System.arraycopy(cArr2, i10, cArr2, i7 + 2, (i6 - i7) - 1);
            char[] cArr3 = this.buf;
            cArr3[i7] = '\\';
            cArr3[i10] = IOUtils.replaceChars[c];
        } else if (i > 1) {
            char[] cArr4 = this.buf;
            int i11 = i7 + 1;
            System.arraycopy(cArr4, i11, cArr4, i7 + 2, (i6 - i7) - 1);
            char[] cArr5 = this.buf;
            cArr5[i7] = '\\';
            cArr5[i11] = IOUtils.replaceChars[c];
            int i12 = i6 + 1;
            for (int i13 = i7 - 1; i13 >= i5; i13--) {
                char c3 = this.buf[i13];
                if (c3 <= '\r' || c3 == '\\' || c3 == '\'' || (c3 == '/' && isEnabled(SerializerFeature.WriteSlashAsSpecial))) {
                    char[] cArr6 = this.buf;
                    int i14 = i13 + 1;
                    System.arraycopy(cArr6, i14, cArr6, i13 + 2, (i12 - i13) - 1);
                    char[] cArr7 = this.buf;
                    cArr7[i13] = '\\';
                    cArr7[i14] = IOUtils.replaceChars[c3];
                    i12++;
                }
            }
        }
        this.buf[this.count - 1] = '\'';
    }

    protected void writeStringWithSingleQuote(char[] cArr) {
        int i = 0;
        if (cArr == null) {
            int i2 = this.count + 4;
            if (i2 > this.buf.length) {
                expandCapacity(i2);
            }
            GlobalVariable.nullColor.getChars(0, 4, this.buf, this.count);
            this.count = i2;
            return;
        }
        int length = cArr.length;
        int i3 = this.count + length + 2;
        if (i3 > this.buf.length) {
            if (this.writer != null) {
                write(39);
                while (i < cArr.length) {
                    char c = cArr[i];
                    if (c <= '\r' || c == '\\' || c == '\'' || (c == '/' && isEnabled(SerializerFeature.WriteSlashAsSpecial))) {
                        write(92);
                        write(IOUtils.replaceChars[c]);
                    } else {
                        write(c);
                    }
                    i++;
                }
                write(39);
                return;
            }
            expandCapacity(i3);
        }
        int i4 = this.count;
        int i5 = i4 + 1;
        int i6 = length + i5;
        char[] cArr2 = this.buf;
        cArr2[i4] = '\'';
        System.arraycopy(cArr, 0, cArr2, i5, cArr.length);
        this.count = i3;
        int i7 = -1;
        char c2 = 0;
        for (int i8 = i5; i8 < i6; i8++) {
            char c3 = this.buf[i8];
            if (c3 <= '\r' || c3 == '\\' || c3 == '\'' || (c3 == '/' && isEnabled(SerializerFeature.WriteSlashAsSpecial))) {
                i++;
                i7 = i8;
                c2 = c3;
            }
        }
        int i9 = i3 + i;
        if (i9 > this.buf.length) {
            expandCapacity(i9);
        }
        this.count = i9;
        if (i == 1) {
            char[] cArr3 = this.buf;
            int i10 = i7 + 1;
            System.arraycopy(cArr3, i10, cArr3, i7 + 2, (i6 - i7) - 1);
            char[] cArr4 = this.buf;
            cArr4[i7] = '\\';
            cArr4[i10] = IOUtils.replaceChars[c2];
        } else if (i > 1) {
            char[] cArr5 = this.buf;
            int i11 = i7 + 1;
            System.arraycopy(cArr5, i11, cArr5, i7 + 2, (i6 - i7) - 1);
            char[] cArr6 = this.buf;
            cArr6[i7] = '\\';
            cArr6[i11] = IOUtils.replaceChars[c2];
            int i12 = i6 + 1;
            for (int i13 = i7 - 1; i13 >= i5; i13--) {
                char c4 = this.buf[i13];
                if (c4 <= '\r' || c4 == '\\' || c4 == '\'' || (c4 == '/' && isEnabled(SerializerFeature.WriteSlashAsSpecial))) {
                    char[] cArr7 = this.buf;
                    int i14 = i13 + 1;
                    System.arraycopy(cArr7, i14, cArr7, i13 + 2, (i12 - i13) - 1);
                    char[] cArr8 = this.buf;
                    cArr8[i13] = '\\';
                    cArr8[i14] = IOUtils.replaceChars[c4];
                    i12++;
                }
            }
        }
        this.buf[this.count - 1] = '\'';
    }

    public void writeFieldName(String str) {
        writeFieldName(str, false);
    }

    public void writeFieldName(String str, boolean z) {
        if (str == null) {
            write("null:");
            return;
        }
        if (this.useSingleQuotes) {
            if (this.quoteFieldNames) {
                writeStringWithSingleQuote(str);
                write(58);
                return;
            } else {
                writeKeyWithSingleQuoteIfHasSpecial(str);
                return;
            }
        }
        if (this.quoteFieldNames) {
            writeStringWithDoubleQuote(str, ':');
            return;
        }
        boolean z2 = true;
        boolean z3 = str.length() == 0;
        int i = 0;
        while (true) {
            if (i >= str.length()) {
                z2 = z3;
                break;
            }
            char cCharAt = str.charAt(i);
            if ((cCharAt < '@' && (this.sepcialBits & (1 << cCharAt)) != 0) || cCharAt == '\\') {
                break;
            } else {
                i++;
            }
        }
        if (z2) {
            writeStringWithDoubleQuote(str, ':');
        } else {
            write(str);
            write(58);
        }
    }

    private void writeKeyWithSingleQuoteIfHasSpecial(String str) {
        int i;
        byte[] bArr = IOUtils.specicalFlags_singleQuotes;
        int length = str.length();
        int i2 = 1;
        int i3 = this.count + length + 1;
        if (i3 > this.buf.length) {
            if (this.writer != null) {
                if (length == 0) {
                    write(39);
                    write(39);
                    write(58);
                    return;
                }
                int i4 = 0;
                while (true) {
                    if (i4 >= length) {
                        i2 = 0;
                        break;
                    }
                    char cCharAt = str.charAt(i4);
                    if (cCharAt < bArr.length && bArr[cCharAt] != 0) {
                        break;
                    } else {
                        i4++;
                    }
                }
                if (i2 != 0) {
                    write(39);
                }
                for (int i5 = 0; i5 < length; i5++) {
                    char cCharAt2 = str.charAt(i5);
                    if (cCharAt2 < bArr.length && bArr[cCharAt2] != 0) {
                        write(92);
                        write(IOUtils.replaceChars[cCharAt2]);
                    } else {
                        write(cCharAt2);
                    }
                }
                if (i2 != 0) {
                    write(39);
                }
                write(58);
                return;
            }
            expandCapacity(i3);
        }
        if (length == 0) {
            int i6 = this.count;
            if (i6 + 3 > this.buf.length) {
                expandCapacity(i6 + 3);
            }
            char[] cArr = this.buf;
            int i7 = this.count;
            int i8 = i7 + 1;
            this.count = i8;
            cArr[i7] = '\'';
            int i9 = i7 + 2;
            this.count = i9;
            cArr[i8] = '\'';
            this.count = i7 + 3;
            cArr[i9] = ':';
            return;
        }
        int i10 = this.count;
        int i11 = i10 + length;
        str.getChars(0, length, this.buf, i10);
        this.count = i3;
        int i12 = i10;
        int i13 = 0;
        while (i12 < i11) {
            char[] cArr2 = this.buf;
            char c = cArr2[i12];
            if (c >= bArr.length || bArr[c] == 0) {
                i = i2;
            } else if (i13 == 0) {
                i3 += 3;
                if (i3 > cArr2.length) {
                    expandCapacity(i3);
                }
                this.count = i3;
                char[] cArr3 = this.buf;
                int i14 = i12 + 1;
                System.arraycopy(cArr3, i14, cArr3, i12 + 3, (i11 - i12) - i2);
                char[] cArr4 = this.buf;
                System.arraycopy(cArr4, 0, cArr4, i2, i12);
                char[] cArr5 = this.buf;
                cArr5[i10] = '\'';
                cArr5[i14] = '\\';
                i12 += 2;
                cArr5[i12] = IOUtils.replaceChars[c];
                i11 += 2;
                this.buf[this.count - 2] = '\'';
                i13 = i2;
                i = i13;
            } else {
                i3++;
                if (i3 > cArr2.length) {
                    expandCapacity(i3);
                }
                this.count = i3;
                char[] cArr6 = this.buf;
                int i15 = i12 + 1;
                i = i2;
                System.arraycopy(cArr6, i15, cArr6, i12 + 2, i11 - i12);
                char[] cArr7 = this.buf;
                cArr7[i12] = '\\';
                cArr7[i15] = IOUtils.replaceChars[c];
                i11++;
                i12 = i15;
            }
            i12++;
            i2 = i;
        }
        this.buf[i3 - 1] = ':';
    }

    @Override // java.io.Writer, java.io.Flushable
    public void flush() {
        Writer writer = this.writer;
        if (writer == null) {
            return;
        }
        try {
            writer.write(this.buf, 0, this.count);
            this.writer.flush();
            this.count = 0;
        } catch (IOException e) {
            throw new JSONException(e.getMessage(), e);
        }
    }
}
