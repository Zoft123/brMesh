package j$.time.format;

import j$.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/* JADX INFO: loaded from: classes3.dex */
public final class DecimalStyle {
    private final char decimalSeparator;
    private final char negativeSign;
    private final char positiveSign;
    private final char zeroDigit;
    public static final DecimalStyle STANDARD = new DecimalStyle('0', '+', '-', '.');
    private static final ConcurrentMap CACHE = new ConcurrentHashMap(16, 0.75f, 2);

    private DecimalStyle(char c, char c2, char c3, char c4) {
        this.zeroDigit = c;
        this.positiveSign = c2;
        this.negativeSign = c3;
        this.decimalSeparator = c4;
    }

    public char getZeroDigit() {
        return this.zeroDigit;
    }

    public char getPositiveSign() {
        return this.positiveSign;
    }

    public char getNegativeSign() {
        return this.negativeSign;
    }

    public char getDecimalSeparator() {
        return this.decimalSeparator;
    }

    int convertToDigit(char c) {
        int i = c - this.zeroDigit;
        if (i < 0 || i > 9) {
            return -1;
        }
        return i;
    }

    String convertNumberToI18N(String str) {
        char c = this.zeroDigit;
        if (c == '0') {
            return str;
        }
        int i = c - '0';
        char[] charArray = str.toCharArray();
        for (int i2 = 0; i2 < charArray.length; i2++) {
            charArray[i2] = (char) (charArray[i2] + i);
        }
        return new String(charArray);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof DecimalStyle) {
            DecimalStyle decimalStyle = (DecimalStyle) obj;
            if (this.zeroDigit == decimalStyle.zeroDigit && this.positiveSign == decimalStyle.positiveSign && this.negativeSign == decimalStyle.negativeSign && this.decimalSeparator == decimalStyle.decimalSeparator) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return this.zeroDigit + this.positiveSign + this.negativeSign + this.decimalSeparator;
    }

    public String toString() {
        return "DecimalStyle[" + this.zeroDigit + this.positiveSign + this.negativeSign + this.decimalSeparator + "]";
    }
}
