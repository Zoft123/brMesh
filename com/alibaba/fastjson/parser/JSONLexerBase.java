package com.alibaba.fastjson.parser;

import androidx.camera.video.AudioStats;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.util.IOUtils;
import com.brgd.brblmesh.GeneralClass.GlobalVariable;
import java.io.Closeable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.TimeZone;
import java.util.UUID;
import kotlin.text.Typography;

/* JADX INFO: loaded from: classes.dex */
public abstract class JSONLexerBase implements JSONLexer, Closeable {
    protected static final int INT_MULTMIN_RADIX_TEN = -214748364;
    protected static final long MULTMIN_RADIX_TEN = -922337203685477580L;
    protected int bp;
    protected char ch;
    protected int eofPos;
    protected int features;
    protected boolean hasSpecial;
    protected int np;
    protected int pos;
    protected char[] sbuf;
    protected int sp;
    protected String stringDefaultValue;
    protected int token;
    private static final ThreadLocal<char[]> SBUF_LOCAL = new ThreadLocal<>();
    protected static final char[] typeFieldName = ("\"" + JSON.DEFAULT_TYPE_KEY + "\":\"").toCharArray();
    protected static final int[] digits = new int[103];
    protected Calendar calendar = null;
    protected TimeZone timeZone = JSON.defaultTimeZone;
    protected Locale locale = JSON.defaultLocale;
    public int matchStat = 0;

    public static boolean isWhitespace(char c) {
        if (c <= ' ') {
            return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == '\f' || c == '\b';
        }
        return false;
    }

    public abstract String addSymbol(int i, int i2, int i3, SymbolTable symbolTable);

    protected abstract void arrayCopy(int i, char[] cArr, int i2, int i3);

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public abstract byte[] bytesValue();

    protected abstract boolean charArrayCompare(char[] cArr);

    public abstract char charAt(int i);

    protected abstract void copyTo(int i, int i2, char[] cArr);

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public abstract BigDecimal decimalValue();

    public abstract int indexOf(char c, int i);

    public abstract boolean isEOF();

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public abstract char next();

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public abstract String numberString();

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public abstract String stringVal();

    public abstract String subString(int i, int i2);

    protected abstract char[] sub_chars(int i, int i2);

    protected void lexError(String str, Object... objArr) {
        this.token = 1;
    }

    static {
        for (int i = 48; i <= 57; i++) {
            digits[i] = i - 48;
        }
        for (int i2 = 97; i2 <= 102; i2++) {
            digits[i2] = i2 - 87;
        }
        for (int i3 = 65; i3 <= 70; i3++) {
            digits[i3] = i3 - 55;
        }
    }

    public JSONLexerBase(int i) {
        this.stringDefaultValue = null;
        this.features = i;
        if ((i & Feature.InitStringFieldAsEmpty.mask) != 0) {
            this.stringDefaultValue = "";
        }
        char[] cArr = SBUF_LOCAL.get();
        this.sbuf = cArr;
        if (cArr == null) {
            this.sbuf = new char[512];
        }
    }

    public final int matchStat() {
        return this.matchStat;
    }

    public void setToken(int i) {
        this.token = i;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final void nextToken() {
        this.sp = 0;
        while (true) {
            this.pos = this.bp;
            char c = this.ch;
            if (c == '/') {
                skipComment();
            } else {
                if (c == '\"') {
                    scanString();
                    return;
                }
                if (c == ',') {
                    next();
                    this.token = 16;
                    return;
                }
                if (c >= '0' && c <= '9') {
                    scanNumber();
                    return;
                }
                if (c == '-') {
                    scanNumber();
                    return;
                }
                switch (c) {
                    case '\b':
                    case '\t':
                    case '\n':
                    case '\f':
                    case '\r':
                    case ' ':
                        next();
                        break;
                    case '\'':
                        if (!isEnabled(Feature.AllowSingleQuotes)) {
                            throw new JSONException("Feature.AllowSingleQuotes is false");
                        }
                        scanStringSingleQuote();
                        return;
                    case '(':
                        next();
                        this.token = 10;
                        return;
                    case ')':
                        next();
                        this.token = 11;
                        return;
                    case '+':
                        next();
                        scanNumber();
                        return;
                    case '.':
                        next();
                        this.token = 25;
                        return;
                    case ':':
                        next();
                        this.token = 17;
                        return;
                    case ';':
                        next();
                        this.token = 24;
                        return;
                    case 'N':
                    case 'S':
                    case 'T':
                    case 'u':
                        scanIdent();
                        return;
                    case '[':
                        next();
                        this.token = 14;
                        return;
                    case ']':
                        next();
                        this.token = 15;
                        return;
                    case 'f':
                        scanFalse();
                        return;
                    case 'n':
                        scanNullOrNew();
                        return;
                    case 't':
                        scanTrue();
                        return;
                    case 'x':
                        scanHex();
                        return;
                    case '{':
                        next();
                        this.token = 12;
                        return;
                    case '}':
                        next();
                        this.token = 13;
                        return;
                    default:
                        if (isEOF()) {
                            if (this.token == 20) {
                                throw new JSONException("EOF error");
                            }
                            this.token = 20;
                            int i = this.bp;
                            this.pos = i;
                            this.eofPos = i;
                            return;
                        }
                        char c2 = this.ch;
                        if (c2 <= 31 || c2 == 127) {
                            next();
                        } else {
                            lexError("illegal.char", String.valueOf((int) c2));
                            next();
                            return;
                        }
                        break;
                        break;
                }
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:108:0x007b A[SYNTHETIC] */
    @Override // com.alibaba.fastjson.parser.JSONLexer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void nextToken(int r11) {
        /*
            Method dump skipped, instruction units count: 270
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.JSONLexerBase.nextToken(int):void");
    }

    public final void nextIdent() {
        while (isWhitespace(this.ch)) {
            next();
        }
        char c = this.ch;
        if (c == '_' || c == '$' || Character.isLetter(c)) {
            scanIdent();
        } else {
            nextToken();
        }
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final void nextTokenWithColon() {
        nextTokenWithChar(':');
    }

    public final void nextTokenWithChar(char c) {
        this.sp = 0;
        while (true) {
            char c2 = this.ch;
            if (c2 == c) {
                next();
                nextToken();
                return;
            }
            if (c2 == ' ' || c2 == '\n' || c2 == '\r' || c2 == '\t' || c2 == '\f' || c2 == '\b') {
                next();
            } else {
                throw new JSONException("not match " + c + " - " + this.ch + ", info : " + info());
            }
        }
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final int token() {
        return this.token;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final String tokenName() {
        return JSONToken.name(this.token);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final int pos() {
        return this.pos;
    }

    public final String stringDefaultValue() {
        return this.stringDefaultValue;
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x0057  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0088  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:30:0x0086 -> B:19:0x0051). Please report as a decompilation issue!!! */
    @Override // com.alibaba.fastjson.parser.JSONLexer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Number integerValue() throws java.lang.NumberFormatException {
        /*
            Method dump skipped, instruction units count: 230
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.JSONLexerBase.integerValue():java.lang.Number");
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final void nextTokenWithColon(int i) {
        nextTokenWithChar(':');
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public float floatValue() {
        char cCharAt;
        String strNumberString = numberString();
        float f = Float.parseFloat(strNumberString);
        if ((f != 0.0f && f != Float.POSITIVE_INFINITY) || (cCharAt = strNumberString.charAt(0)) <= '0' || cCharAt > '9') {
            return f;
        }
        throw new JSONException("float overflow : " + strNumberString);
    }

    public double doubleValue() {
        return Double.parseDouble(numberString());
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public void config(Feature feature, boolean z) {
        int iConfig = Feature.config(this.features, feature, z);
        this.features = iConfig;
        if ((iConfig & Feature.InitStringFieldAsEmpty.mask) != 0) {
            this.stringDefaultValue = "";
        }
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final boolean isEnabled(Feature feature) {
        return isEnabled(feature.mask);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final boolean isEnabled(int i) {
        return (i & this.features) != 0;
    }

    public final boolean isEnabled(int i, int i2) {
        return ((this.features & i2) == 0 && (i & i2) == 0) ? false : true;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final char getCurrent() {
        return this.ch;
    }

    protected void skipComment() {
        char c;
        next();
        char c2 = this.ch;
        if (c2 == '/') {
            do {
                next();
                c = this.ch;
                if (c == '\n') {
                    next();
                    return;
                }
            } while (c != 26);
            return;
        }
        if (c2 == '*') {
            next();
            while (true) {
                char c3 = this.ch;
                if (c3 == 26) {
                    return;
                }
                if (c3 == '*') {
                    next();
                    if (this.ch == '/') {
                        next();
                        return;
                    }
                } else {
                    next();
                }
            }
        } else {
            throw new JSONException("invalid comment");
        }
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final String scanSymbol(SymbolTable symbolTable) {
        skipWhitespace();
        char c = this.ch;
        if (c == '\"') {
            return scanSymbol(symbolTable, Typography.quote);
        }
        if (c == '\'') {
            if (!isEnabled(Feature.AllowSingleQuotes)) {
                throw new JSONException("syntax error");
            }
            return scanSymbol(symbolTable, '\'');
        }
        if (c == '}') {
            next();
            this.token = 13;
            return null;
        }
        if (c == ',') {
            next();
            this.token = 16;
            return null;
        }
        if (c == 26) {
            this.token = 20;
            return null;
        }
        if (!isEnabled(Feature.AllowUnQuotedFieldNames)) {
            throw new JSONException("syntax error");
        }
        return scanSymbolUnQuoted(symbolTable);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final String scanSymbol(SymbolTable symbolTable, char c) {
        String strAddSymbol;
        this.np = this.bp;
        this.sp = 0;
        boolean z = false;
        int i = 0;
        while (true) {
            char next = next();
            if (next == c) {
                this.token = 4;
                if (!z) {
                    int i2 = this.np;
                    strAddSymbol = addSymbol(i2 == -1 ? 0 : i2 + 1, this.sp, i, symbolTable);
                } else {
                    strAddSymbol = symbolTable.addSymbol(this.sbuf, 0, this.sp, i);
                }
                this.sp = 0;
                next();
                return strAddSymbol;
            }
            if (next == 26) {
                throw new JSONException("unclosed.str");
            }
            if (next == '\\') {
                if (!z) {
                    int i3 = this.sp;
                    char[] cArr = this.sbuf;
                    if (i3 >= cArr.length) {
                        int length = cArr.length * 2;
                        if (i3 <= length) {
                            i3 = length;
                        }
                        char[] cArr2 = new char[i3];
                        System.arraycopy(cArr, 0, cArr2, 0, cArr.length);
                        this.sbuf = cArr2;
                    }
                    arrayCopy(this.np + 1, this.sbuf, 0, this.sp);
                    z = true;
                }
                char next2 = next();
                if (next2 == '\"') {
                    i = (i * 31) + 34;
                    putChar(Typography.quote);
                } else if (next2 != '\'') {
                    if (next2 != 'F') {
                        if (next2 == '\\') {
                            i = (i * 31) + 92;
                            putChar('\\');
                        } else if (next2 == 'b') {
                            i = (i * 31) + 8;
                            putChar('\b');
                        } else if (next2 != 'f') {
                            if (next2 == 'n') {
                                i = (i * 31) + 10;
                                putChar('\n');
                            } else if (next2 == 'r') {
                                i = (i * 31) + 13;
                                putChar('\r');
                            } else if (next2 != 'x') {
                                switch (next2) {
                                    case '/':
                                        i = (i * 31) + 47;
                                        putChar('/');
                                        break;
                                    case ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE /* 48 */:
                                        i = (i * 31) + next2;
                                        putChar((char) 0);
                                        break;
                                    case '1':
                                        i = (i * 31) + next2;
                                        putChar((char) 1);
                                        break;
                                    case '2':
                                        i = (i * 31) + next2;
                                        putChar((char) 2);
                                        break;
                                    case ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_TAG /* 51 */:
                                        i = (i * 31) + next2;
                                        putChar((char) 3);
                                        break;
                                    case '4':
                                        i = (i * 31) + next2;
                                        putChar((char) 4);
                                        break;
                                    case ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_BASELINE_TO_BOTTOM_OF /* 53 */:
                                        i = (i * 31) + next2;
                                        putChar((char) 5);
                                        break;
                                    case '6':
                                        i = (i * 31) + next2;
                                        putChar((char) 6);
                                        break;
                                    case '7':
                                        i = (i * 31) + next2;
                                        putChar((char) 7);
                                        break;
                                    default:
                                        switch (next2) {
                                            case 't':
                                                i = (i * 31) + 9;
                                                putChar('\t');
                                                break;
                                            case 'u':
                                                int i4 = Integer.parseInt(new String(new char[]{next(), next(), next(), next()}), 16);
                                                i = (i * 31) + i4;
                                                putChar((char) i4);
                                                break;
                                            case 'v':
                                                i = (i * 31) + 11;
                                                putChar((char) 11);
                                                break;
                                            default:
                                                this.ch = next2;
                                                throw new JSONException("unclosed.str.lit");
                                        }
                                        break;
                                }
                            } else {
                                char next3 = next();
                                this.ch = next3;
                                char next4 = next();
                                this.ch = next4;
                                int[] iArr = digits;
                                char c2 = (char) ((iArr[next3] * 16) + iArr[next4]);
                                i = (i * 31) + c2;
                                putChar(c2);
                            }
                        }
                    }
                    i = (i * 31) + 12;
                    putChar('\f');
                } else {
                    i = (i * 31) + 39;
                    putChar('\'');
                }
            } else {
                i = (i * 31) + next;
                if (!z) {
                    this.sp++;
                } else {
                    int i5 = this.sp;
                    char[] cArr3 = this.sbuf;
                    if (i5 == cArr3.length) {
                        putChar(next);
                    } else {
                        this.sp = i5 + 1;
                        cArr3[i5] = next;
                    }
                }
            }
        }
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final void resetStringPosition() {
        this.sp = 0;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public String info() {
        return "";
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final String scanSymbolUnQuoted(SymbolTable symbolTable) {
        if (this.token == 1 && this.pos == 0 && this.bp == 1) {
            this.bp = 0;
        }
        boolean[] zArr = IOUtils.firstIdentifierFlags;
        int i = this.ch;
        if (i < zArr.length && !zArr[i]) {
            throw new JSONException("illegal identifier : " + this.ch + info());
        }
        boolean[] zArr2 = IOUtils.identifierFlags;
        this.np = this.bp;
        this.sp = 1;
        while (true) {
            char next = next();
            if (next < zArr2.length && !zArr2[next]) {
                break;
            }
            i = (i * 31) + next;
            this.sp++;
        }
        this.ch = charAt(this.bp);
        this.token = 18;
        if (this.sp == 4 && i == 3392903 && charAt(this.np) == 'n' && charAt(this.np + 1) == 'u' && charAt(this.np + 2) == 'l' && charAt(this.np + 3) == 'l') {
            return null;
        }
        if (symbolTable == null) {
            return subString(this.np, this.sp);
        }
        return addSymbol(this.np, this.sp, i, symbolTable);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final void scanString() {
        this.np = this.bp;
        this.hasSpecial = false;
        while (true) {
            char next = next();
            if (next == '\"') {
                this.token = 4;
                this.ch = next();
                return;
            }
            if (next == 26) {
                if (isEOF()) {
                    throw new JSONException("unclosed string : " + next);
                }
                putChar(JSONLexer.EOI);
            } else if (next == '\\') {
                if (!this.hasSpecial) {
                    this.hasSpecial = true;
                    int i = this.sp;
                    char[] cArr = this.sbuf;
                    if (i >= cArr.length) {
                        int length = cArr.length * 2;
                        if (i <= length) {
                            i = length;
                        }
                        char[] cArr2 = new char[i];
                        System.arraycopy(cArr, 0, cArr2, 0, cArr.length);
                        this.sbuf = cArr2;
                    }
                    copyTo(this.np + 1, this.sp, this.sbuf);
                }
                char next2 = next();
                if (next2 == '\"') {
                    putChar(Typography.quote);
                } else if (next2 != '\'') {
                    if (next2 != 'F') {
                        if (next2 == '\\') {
                            putChar('\\');
                        } else if (next2 == 'b') {
                            putChar('\b');
                        } else if (next2 != 'f') {
                            if (next2 == 'n') {
                                putChar('\n');
                            } else if (next2 == 'r') {
                                putChar('\r');
                            } else if (next2 != 'x') {
                                switch (next2) {
                                    case '/':
                                        putChar('/');
                                        break;
                                    case ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE /* 48 */:
                                        putChar((char) 0);
                                        break;
                                    case '1':
                                        putChar((char) 1);
                                        break;
                                    case '2':
                                        putChar((char) 2);
                                        break;
                                    case ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_TAG /* 51 */:
                                        putChar((char) 3);
                                        break;
                                    case '4':
                                        putChar((char) 4);
                                        break;
                                    case ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_BASELINE_TO_BOTTOM_OF /* 53 */:
                                        putChar((char) 5);
                                        break;
                                    case '6':
                                        putChar((char) 6);
                                        break;
                                    case '7':
                                        putChar((char) 7);
                                        break;
                                    default:
                                        switch (next2) {
                                            case 't':
                                                putChar('\t');
                                                break;
                                            case 'u':
                                                putChar((char) Integer.parseInt(new String(new char[]{next(), next(), next(), next()}), 16));
                                                break;
                                            case 'v':
                                                putChar((char) 11);
                                                break;
                                            default:
                                                this.ch = next2;
                                                throw new JSONException("unclosed string : " + next2);
                                        }
                                        break;
                                }
                            } else {
                                char next3 = next();
                                char next4 = next();
                                int[] iArr = digits;
                                putChar((char) ((iArr[next3] * 16) + iArr[next4]));
                            }
                        }
                    }
                    putChar('\f');
                } else {
                    putChar('\'');
                }
            } else if (!this.hasSpecial) {
                this.sp++;
            } else {
                int i2 = this.sp;
                char[] cArr3 = this.sbuf;
                if (i2 == cArr3.length) {
                    putChar(next);
                } else {
                    this.sp = i2 + 1;
                    cArr3[i2] = next;
                }
            }
        }
    }

    public Calendar getCalendar() {
        return this.calendar;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public TimeZone getTimeZone() {
        return this.timeZone;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public void setTimeZone(TimeZone timeZone) {
        this.timeZone = timeZone;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public Locale getLocale() {
        return this.locale;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final int intValue() {
        int i;
        boolean z;
        int i2 = 0;
        if (this.np == -1) {
            this.np = 0;
        }
        int i3 = this.np;
        int i4 = this.sp + i3;
        if (charAt(i3) == '-') {
            i3++;
            i = Integer.MIN_VALUE;
            z = true;
        } else {
            i = -2147483647;
            z = false;
        }
        if (i3 < i4) {
            i2 = -(charAt(i3) - '0');
            i3++;
        }
        while (i3 < i4) {
            int i5 = i3 + 1;
            char cCharAt = charAt(i3);
            if (cCharAt == 'L' || cCharAt == 'S' || cCharAt == 'B') {
                i3 = i5;
                break;
            }
            int i6 = cCharAt - '0';
            if (i2 < -214748364) {
                throw new NumberFormatException(numberString());
            }
            int i7 = i2 * 10;
            if (i7 < i + i6) {
                throw new NumberFormatException(numberString());
            }
            i2 = i7 - i6;
            i3 = i5;
        }
        if (!z) {
            return -i2;
        }
        if (i3 > this.np + 1) {
            return i2;
        }
        throw new NumberFormatException(numberString());
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        char[] cArr = this.sbuf;
        if (cArr.length <= 8192) {
            SBUF_LOCAL.set(cArr);
        }
        this.sbuf = null;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final boolean isRef() {
        return this.sp == 4 && charAt(this.np + 1) == '$' && charAt(this.np + 2) == 'r' && charAt(this.np + 3) == 'e' && charAt(this.np + 4) == 'f';
    }

    public final int scanType(String str) {
        int i;
        this.matchStat = 0;
        char[] cArr = typeFieldName;
        if (!charArrayCompare(cArr)) {
            return -2;
        }
        int length = this.bp + cArr.length;
        int length2 = str.length();
        for (int i2 = 0; i2 < length2; i2++) {
            if (str.charAt(i2) != charAt(length + i2)) {
                return -1;
            }
        }
        int i3 = length + length2;
        if (charAt(i3) != '\"') {
            return -1;
        }
        int i4 = i3 + 1;
        char cCharAt = charAt(i4);
        this.ch = cCharAt;
        if (cCharAt == ',') {
            int i5 = i3 + 2;
            this.ch = charAt(i5);
            this.bp = i5;
            this.token = 16;
            return 3;
        }
        if (cCharAt == '}') {
            i4 = i3 + 2;
            char cCharAt2 = charAt(i4);
            this.ch = cCharAt2;
            if (cCharAt2 == ',') {
                this.token = 16;
                i = i3 + 3;
                this.ch = charAt(i);
            } else if (cCharAt2 == ']') {
                this.token = 15;
                i = i3 + 3;
                this.ch = charAt(i);
            } else if (cCharAt2 == '}') {
                this.token = 13;
                i = i3 + 3;
                this.ch = charAt(i);
            } else {
                if (cCharAt2 != 26) {
                    return -1;
                }
                this.token = 20;
                this.matchStat = 4;
            }
            i4 = i;
            this.matchStat = 4;
        }
        this.bp = i4;
        return this.matchStat;
    }

    public final boolean matchField(char[] cArr) {
        while (!charArrayCompare(cArr)) {
            if (!isWhitespace(this.ch)) {
                return false;
            }
            next();
        }
        int length = this.bp + cArr.length;
        this.bp = length;
        char cCharAt = charAt(length);
        this.ch = cCharAt;
        if (cCharAt == '{') {
            next();
            this.token = 12;
        } else if (cCharAt == '[') {
            next();
            this.token = 14;
        } else if (cCharAt == 'S' && charAt(this.bp + 1) == 'e' && charAt(this.bp + 2) == 't' && charAt(this.bp + 3) == '[') {
            int i = this.bp + 3;
            this.bp = i;
            this.ch = charAt(i);
            this.token = 21;
        } else {
            nextToken();
        }
        return true;
    }

    public int matchField(long j) {
        throw new UnsupportedOperationException();
    }

    public boolean seekArrayToItem(int i) {
        throw new UnsupportedOperationException();
    }

    public int seekObjectToField(long j, boolean z) {
        throw new UnsupportedOperationException();
    }

    public int seekObjectToField(long[] jArr) {
        throw new UnsupportedOperationException();
    }

    public int seekObjectToFieldDeepScan(long j) {
        throw new UnsupportedOperationException();
    }

    public void skipObject() {
        throw new UnsupportedOperationException();
    }

    public void skipObject(boolean z) {
        throw new UnsupportedOperationException();
    }

    public void skipArray() {
        throw new UnsupportedOperationException();
    }

    public String scanFieldString(char[] cArr) {
        this.matchStat = 0;
        if (!charArrayCompare(cArr)) {
            this.matchStat = -2;
            return stringDefaultValue();
        }
        int length = cArr.length;
        int i = length + 1;
        if (charAt(this.bp + length) != '\"') {
            this.matchStat = -1;
            return stringDefaultValue();
        }
        int iIndexOf = indexOf(Typography.quote, this.bp + cArr.length + 1);
        if (iIndexOf == -1) {
            throw new JSONException("unclosed str");
        }
        int length2 = this.bp + cArr.length + 1;
        String strSubString = subString(length2, iIndexOf - length2);
        if (strSubString.indexOf(92) != -1) {
            while (true) {
                int i2 = 0;
                for (int i3 = iIndexOf - 1; i3 >= 0 && charAt(i3) == '\\'; i3--) {
                    i2++;
                }
                if (i2 % 2 == 0) {
                    break;
                }
                iIndexOf = indexOf(Typography.quote, iIndexOf + 1);
            }
            int i4 = this.bp;
            int length3 = iIndexOf - ((cArr.length + i4) + 1);
            strSubString = readString(sub_chars(i4 + cArr.length + 1, length3), length3);
        }
        int i5 = this.bp;
        int length4 = i + (iIndexOf - ((cArr.length + i5) + 1)) + 1;
        int i6 = length4 + 1;
        char cCharAt = charAt(i5 + length4);
        if (cCharAt == ',') {
            int i7 = this.bp + i6;
            this.bp = i7;
            this.ch = charAt(i7);
            this.matchStat = 3;
            return strSubString;
        }
        if (cCharAt == '}') {
            int i8 = length4 + 2;
            char cCharAt2 = charAt(this.bp + i6);
            if (cCharAt2 == ',') {
                this.token = 16;
                int i9 = this.bp + i8;
                this.bp = i9;
                this.ch = charAt(i9);
            } else if (cCharAt2 == ']') {
                this.token = 15;
                int i10 = this.bp + i8;
                this.bp = i10;
                this.ch = charAt(i10);
            } else if (cCharAt2 == '}') {
                this.token = 13;
                int i11 = this.bp + i8;
                this.bp = i11;
                this.ch = charAt(i11);
            } else if (cCharAt2 == 26) {
                this.token = 20;
                this.bp += length4 + 1;
                this.ch = JSONLexer.EOI;
            } else {
                this.matchStat = -1;
                return stringDefaultValue();
            }
            this.matchStat = 4;
            return strSubString;
        }
        this.matchStat = -1;
        return stringDefaultValue();
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public String scanString(char c) {
        this.matchStat = 0;
        char cCharAt = charAt(this.bp);
        if (cCharAt == 'n') {
            if (charAt(this.bp + 1) == 'u' && charAt(this.bp + 2) == 'l' && charAt(this.bp + 3) == 'l') {
                if (charAt(this.bp + 4) == c) {
                    int i = this.bp + 5;
                    this.bp = i;
                    this.ch = charAt(i);
                    this.matchStat = 3;
                    return null;
                }
                this.matchStat = -1;
                return null;
            }
            this.matchStat = -1;
            return null;
        }
        int i2 = 1;
        while (cCharAt != '\"') {
            if (isWhitespace(cCharAt)) {
                cCharAt = charAt(this.bp + i2);
                i2++;
            } else {
                this.matchStat = -1;
                return stringDefaultValue();
            }
        }
        int i3 = this.bp + i2;
        int iIndexOf = indexOf(Typography.quote, i3);
        if (iIndexOf == -1) {
            throw new JSONException("unclosed str");
        }
        String strSubString = subString(this.bp + i2, iIndexOf - i3);
        if (strSubString.indexOf(92) != -1) {
            while (true) {
                int i4 = 0;
                for (int i5 = iIndexOf - 1; i5 >= 0 && charAt(i5) == '\\'; i5--) {
                    i4++;
                }
                if (i4 % 2 == 0) {
                    break;
                }
                iIndexOf = indexOf(Typography.quote, iIndexOf + 1);
            }
            int i6 = iIndexOf - i3;
            strSubString = readString(sub_chars(this.bp + 1, i6), i6);
        }
        int i7 = i2 + (iIndexOf - i3) + 1;
        int i8 = i7 + 1;
        char cCharAt2 = charAt(this.bp + i7);
        while (cCharAt2 != c) {
            if (isWhitespace(cCharAt2)) {
                cCharAt2 = charAt(this.bp + i8);
                i8++;
            } else {
                this.matchStat = -1;
                return strSubString;
            }
        }
        int i9 = this.bp + i8;
        this.bp = i9;
        this.ch = charAt(i9);
        this.matchStat = 3;
        this.token = 16;
        return strSubString;
    }

    public long scanFieldSymbol(char[] cArr) {
        this.matchStat = 0;
        if (!charArrayCompare(cArr)) {
            this.matchStat = -2;
            return 0L;
        }
        int length = cArr.length;
        int i = length + 1;
        if (charAt(this.bp + length) != '\"') {
            this.matchStat = -1;
            return 0L;
        }
        long j = -3750763034362895579L;
        while (true) {
            int i2 = i + 1;
            char cCharAt = charAt(this.bp + i);
            if (cCharAt == '\"') {
                int i3 = i + 2;
                char cCharAt2 = charAt(this.bp + i2);
                if (cCharAt2 == ',') {
                    int i4 = this.bp + i3;
                    this.bp = i4;
                    this.ch = charAt(i4);
                    this.matchStat = 3;
                    return j;
                }
                if (cCharAt2 == '}') {
                    int i5 = i + 3;
                    char cCharAt3 = charAt(this.bp + i3);
                    if (cCharAt3 == ',') {
                        this.token = 16;
                        int i6 = this.bp + i5;
                        this.bp = i6;
                        this.ch = charAt(i6);
                    } else if (cCharAt3 == ']') {
                        this.token = 15;
                        int i7 = this.bp + i5;
                        this.bp = i7;
                        this.ch = charAt(i7);
                    } else if (cCharAt3 == '}') {
                        this.token = 13;
                        int i8 = this.bp + i5;
                        this.bp = i8;
                        this.ch = charAt(i8);
                    } else if (cCharAt3 == 26) {
                        this.token = 20;
                        this.bp += i + 2;
                        this.ch = JSONLexer.EOI;
                    } else {
                        this.matchStat = -1;
                        return 0L;
                    }
                    this.matchStat = 4;
                    return j;
                }
                this.matchStat = -1;
                return 0L;
            }
            j = (j ^ ((long) cCharAt)) * 1099511628211L;
            if (cCharAt == '\\') {
                this.matchStat = -1;
                return 0L;
            }
            i = i2;
        }
    }

    public long scanEnumSymbol(char[] cArr) {
        this.matchStat = 0;
        if (!charArrayCompare(cArr)) {
            this.matchStat = -2;
            return 0L;
        }
        int length = cArr.length;
        int i = length + 1;
        if (charAt(this.bp + length) != '\"') {
            this.matchStat = -1;
            return 0L;
        }
        long j = -3750763034362895579L;
        while (true) {
            int i2 = i + 1;
            char cCharAt = charAt(this.bp + i);
            if (cCharAt == '\"') {
                int i3 = i + 2;
                char cCharAt2 = charAt(this.bp + i2);
                if (cCharAt2 == ',') {
                    int i4 = this.bp + i3;
                    this.bp = i4;
                    this.ch = charAt(i4);
                    this.matchStat = 3;
                    return j;
                }
                if (cCharAt2 == '}') {
                    int i5 = i + 3;
                    char cCharAt3 = charAt(this.bp + i3);
                    if (cCharAt3 == ',') {
                        this.token = 16;
                        int i6 = this.bp + i5;
                        this.bp = i6;
                        this.ch = charAt(i6);
                    } else if (cCharAt3 == ']') {
                        this.token = 15;
                        int i7 = this.bp + i5;
                        this.bp = i7;
                        this.ch = charAt(i7);
                    } else if (cCharAt3 == '}') {
                        this.token = 13;
                        int i8 = this.bp + i5;
                        this.bp = i8;
                        this.ch = charAt(i8);
                    } else if (cCharAt3 == 26) {
                        this.token = 20;
                        this.bp += i + 2;
                        this.ch = JSONLexer.EOI;
                    } else {
                        this.matchStat = -1;
                        return 0L;
                    }
                    this.matchStat = 4;
                    return j;
                }
                this.matchStat = -1;
                return 0L;
            }
            j = (j ^ ((long) ((cCharAt < 'A' || cCharAt > 'Z') ? cCharAt : cCharAt + ' '))) * 1099511628211L;
            if (cCharAt == '\\') {
                this.matchStat = -1;
                return 0L;
            }
            i = i2;
        }
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public Enum<?> scanEnum(Class<?> cls, SymbolTable symbolTable, char c) {
        String strScanSymbolWithSeperator = scanSymbolWithSeperator(symbolTable, c);
        if (strScanSymbolWithSeperator == null) {
            return null;
        }
        return Enum.valueOf(cls, strScanSymbolWithSeperator);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public String scanSymbolWithSeperator(SymbolTable symbolTable, char c) {
        int i = 0;
        this.matchStat = 0;
        char cCharAt = charAt(this.bp);
        if (cCharAt == 'n') {
            if (charAt(this.bp + 1) == 'u' && charAt(this.bp + 2) == 'l' && charAt(this.bp + 3) == 'l') {
                if (charAt(this.bp + 4) == c) {
                    int i2 = this.bp + 5;
                    this.bp = i2;
                    this.ch = charAt(i2);
                    this.matchStat = 3;
                    return null;
                }
                this.matchStat = -1;
                return null;
            }
            this.matchStat = -1;
            return null;
        }
        if (cCharAt != '\"') {
            this.matchStat = -1;
            return null;
        }
        int i3 = 1;
        while (true) {
            int i4 = i3 + 1;
            char cCharAt2 = charAt(this.bp + i3);
            if (cCharAt2 == '\"') {
                int i5 = this.bp;
                int i6 = i5 + 1;
                String strAddSymbol = addSymbol(i6, ((i5 + i4) - i6) - 1, i, symbolTable);
                int i7 = i3 + 2;
                char cCharAt3 = charAt(this.bp + i4);
                while (cCharAt3 != c) {
                    if (isWhitespace(cCharAt3)) {
                        cCharAt3 = charAt(this.bp + i7);
                        i7++;
                    } else {
                        this.matchStat = -1;
                        return strAddSymbol;
                    }
                }
                int i8 = this.bp + i7;
                this.bp = i8;
                this.ch = charAt(i8);
                this.matchStat = 3;
                return strAddSymbol;
            }
            i = (i * 31) + cCharAt2;
            if (cCharAt2 == '\\') {
                this.matchStat = -1;
                return null;
            }
            i3 = i4;
        }
    }

    public Collection<String> newCollectionByType(Class<?> cls) {
        if (cls.isAssignableFrom(HashSet.class)) {
            return new HashSet();
        }
        if (cls.isAssignableFrom(ArrayList.class)) {
            return new ArrayList();
        }
        try {
            return (Collection) cls.newInstance();
        } catch (Exception e) {
            throw new JSONException(e.getMessage(), e);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:44:0x00eb, code lost:
    
        if (r1 != ']') goto L69;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x00f1, code lost:
    
        if (r14.size() != 0) goto L69;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x00f3, code lost:
    
        r1 = r13 + 1;
        r13 = charAt(r12.bp + r13);
        r0 = r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x0171, code lost:
    
        throw new com.alibaba.fastjson.JSONException("illega str");
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.util.Collection<java.lang.String> scanFieldStringArray(char[] r13, java.lang.Class<?> r14) {
        /*
            Method dump skipped, instruction units count: 370
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.JSONLexerBase.scanFieldStringArray(char[], java.lang.Class):java.util.Collection");
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public void scanStringArray(Collection<String> collection, char c) {
        char cCharAt;
        int i;
        int i2;
        char cCharAt2;
        this.matchStat = 0;
        char cCharAt3 = charAt(this.bp);
        char c2 = 'u';
        char c3 = 'l';
        if (cCharAt3 == 'n' && charAt(this.bp + 1) == 'u' && charAt(this.bp + 2) == 'l' && charAt(this.bp + 3) == 'l' && charAt(this.bp + 4) == c) {
            int i3 = this.bp + 5;
            this.bp = i3;
            this.ch = charAt(i3);
            this.matchStat = 5;
            return;
        }
        if (cCharAt3 != '[') {
            this.matchStat = -1;
            return;
        }
        char cCharAt4 = charAt(this.bp + 1);
        int i4 = 2;
        while (true) {
            if (cCharAt4 == 'n' && charAt(this.bp + i4) == c2 && charAt(this.bp + i4 + 1) == c3 && charAt(this.bp + i4 + 2) == c3) {
                int i5 = i4 + 3;
                i = i4 + 4;
                cCharAt = charAt(this.bp + i5);
                collection.add(null);
            } else {
                if (cCharAt4 == ']' && collection.size() == 0) {
                    i2 = i4 + 1;
                    cCharAt2 = charAt(this.bp + i4);
                    break;
                }
                if (cCharAt4 != '\"') {
                    this.matchStat = -1;
                    return;
                }
                int i6 = this.bp + i4;
                int iIndexOf = indexOf(Typography.quote, i6);
                if (iIndexOf == -1) {
                    throw new JSONException("unclosed str");
                }
                String strSubString = subString(this.bp + i4, iIndexOf - i6);
                if (strSubString.indexOf(92) != -1) {
                    while (true) {
                        int i7 = 0;
                        for (int i8 = iIndexOf - 1; i8 >= 0 && charAt(i8) == '\\'; i8--) {
                            i7++;
                        }
                        if (i7 % 2 == 0) {
                            break;
                        } else {
                            iIndexOf = indexOf(Typography.quote, iIndexOf + 1);
                        }
                    }
                    int i9 = iIndexOf - i6;
                    strSubString = readString(sub_chars(this.bp + i4, i9), i9);
                }
                int i10 = this.bp;
                int i11 = i4 + (iIndexOf - (i10 + i4)) + 1;
                cCharAt = charAt(i10 + i11);
                collection.add(strSubString);
                i = i11 + 1;
            }
            if (cCharAt == ',') {
                char cCharAt5 = charAt(this.bp + i);
                i4 = i + 1;
                c2 = 'u';
                c3 = 'l';
                cCharAt4 = cCharAt5;
            } else if (cCharAt == ']') {
                i2 = i + 1;
                cCharAt2 = charAt(this.bp + i);
            } else {
                this.matchStat = -1;
                return;
            }
        }
        if (cCharAt2 == c) {
            int i12 = this.bp + i2;
            this.bp = i12;
            this.ch = charAt(i12);
            this.matchStat = 3;
            return;
        }
        this.matchStat = -1;
    }

    public int scanFieldInt(char[] cArr) {
        int i;
        char cCharAt;
        this.matchStat = 0;
        if (!charArrayCompare(cArr)) {
            this.matchStat = -2;
            return 0;
        }
        int length = cArr.length;
        int i2 = length + 1;
        char cCharAt2 = charAt(this.bp + length);
        boolean z = cCharAt2 == '-';
        if (z) {
            cCharAt2 = charAt(this.bp + i2);
            i2 = length + 2;
        }
        if (cCharAt2 < '0' || cCharAt2 > '9') {
            this.matchStat = -1;
            return 0;
        }
        int i3 = cCharAt2 - '0';
        while (true) {
            i = i2 + 1;
            cCharAt = charAt(this.bp + i2);
            if (cCharAt < '0' || cCharAt > '9') {
                break;
            }
            i3 = (i3 * 10) + (cCharAt - '0');
            i2 = i;
        }
        if (cCharAt == '.') {
            this.matchStat = -1;
            return 0;
        }
        if ((i3 < 0 || i > cArr.length + 14) && !(i3 == Integer.MIN_VALUE && i == 17 && z)) {
            this.matchStat = -1;
            return 0;
        }
        if (cCharAt == ',') {
            int i4 = this.bp + i;
            this.bp = i4;
            this.ch = charAt(i4);
            this.matchStat = 3;
            this.token = 16;
            if (z) {
                return -i3;
            }
        } else if (cCharAt == '}') {
            int i5 = i2 + 2;
            char cCharAt3 = charAt(this.bp + i);
            if (cCharAt3 == ',') {
                this.token = 16;
                int i6 = this.bp + i5;
                this.bp = i6;
                this.ch = charAt(i6);
            } else if (cCharAt3 == ']') {
                this.token = 15;
                int i7 = this.bp + i5;
                this.bp = i7;
                this.ch = charAt(i7);
            } else if (cCharAt3 == '}') {
                this.token = 13;
                int i8 = this.bp + i5;
                this.bp = i8;
                this.ch = charAt(i8);
            } else if (cCharAt3 == 26) {
                this.token = 20;
                this.bp += i2 + 1;
                this.ch = JSONLexer.EOI;
            } else {
                this.matchStat = -1;
                return 0;
            }
            this.matchStat = 4;
            if (z) {
                return -i3;
            }
        } else {
            this.matchStat = -1;
            return 0;
        }
        return i3;
    }

    /* JADX WARN: Code restructure failed: missing block: B:64:0x011d, code lost:
    
        r16 = r4;
        r19.matchStat = -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x0121, code lost:
    
        return r16;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int[] scanFieldIntArray(char[] r20) {
        /*
            Method dump skipped, instruction units count: 290
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.JSONLexerBase.scanFieldIntArray(char[]):int[]");
    }

    /* JADX WARN: Removed duplicated region for block: B:37:0x00a6  */
    @Override // com.alibaba.fastjson.parser.JSONLexer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean scanBoolean(char r10) {
        /*
            r9 = this;
            r0 = 0
            r9.matchStat = r0
            int r1 = r9.bp
            char r1 = r9.charAt(r1)
            r2 = 116(0x74, float:1.63E-43)
            r3 = 5
            r4 = 101(0x65, float:1.42E-43)
            r5 = 3
            r6 = -1
            r7 = 2
            r8 = 1
            if (r1 != r2) goto L3f
            int r1 = r9.bp
            int r1 = r1 + r8
            char r1 = r9.charAt(r1)
            r2 = 114(0x72, float:1.6E-43)
            if (r1 != r2) goto L3c
            int r1 = r9.bp
            int r1 = r1 + r7
            char r1 = r9.charAt(r1)
            r2 = 117(0x75, float:1.64E-43)
            if (r1 != r2) goto L3c
            int r1 = r9.bp
            int r1 = r1 + r5
            char r1 = r9.charAt(r1)
            if (r1 != r4) goto L3c
            int r0 = r9.bp
            int r0 = r0 + 4
            char r1 = r9.charAt(r0)
            goto L86
        L3c:
            r9.matchStat = r6
            return r0
        L3f:
            r2 = 102(0x66, float:1.43E-43)
            if (r1 != r2) goto L7a
            int r1 = r9.bp
            int r1 = r1 + r8
            char r1 = r9.charAt(r1)
            r2 = 97
            if (r1 != r2) goto L77
            int r1 = r9.bp
            int r1 = r1 + r7
            char r1 = r9.charAt(r1)
            r2 = 108(0x6c, float:1.51E-43)
            if (r1 != r2) goto L77
            int r1 = r9.bp
            int r1 = r1 + r5
            char r1 = r9.charAt(r1)
            r2 = 115(0x73, float:1.61E-43)
            if (r1 != r2) goto L77
            int r1 = r9.bp
            int r1 = r1 + 4
            char r1 = r9.charAt(r1)
            if (r1 != r4) goto L77
            int r1 = r9.bp
            int r1 = r1 + r3
            char r1 = r9.charAt(r1)
            r3 = 6
            goto L96
        L77:
            r9.matchStat = r6
            return r0
        L7a:
            r2 = 49
            if (r1 != r2) goto L88
            int r0 = r9.bp
            int r0 = r0 + r8
            char r1 = r9.charAt(r0)
            r3 = r7
        L86:
            r0 = r8
            goto L96
        L88:
            r2 = 48
            if (r1 != r2) goto L95
            int r1 = r9.bp
            int r1 = r1 + r8
            char r1 = r9.charAt(r1)
            r3 = r7
            goto L96
        L95:
            r3 = r8
        L96:
            if (r1 != r10) goto La6
            int r10 = r9.bp
            int r10 = r10 + r3
            r9.bp = r10
            char r10 = r9.charAt(r10)
            r9.ch = r10
            r9.matchStat = r5
            return r0
        La6:
            boolean r1 = isWhitespace(r1)
            if (r1 == 0) goto Lb7
            int r1 = r9.bp
            int r2 = r3 + 1
            int r1 = r1 + r3
            char r1 = r9.charAt(r1)
            r3 = r2
            goto L96
        Lb7:
            r9.matchStat = r6
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.JSONLexerBase.scanBoolean(char):boolean");
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public int scanInt(char c) {
        int i;
        int i2;
        char cCharAt;
        this.matchStat = 0;
        char cCharAt2 = charAt(this.bp);
        boolean z = cCharAt2 == '\"';
        if (z) {
            cCharAt2 = charAt(this.bp + 1);
            i = 2;
        } else {
            i = 1;
        }
        boolean z2 = cCharAt2 == '-';
        if (z2) {
            cCharAt2 = charAt(this.bp + i);
            i++;
        }
        if (cCharAt2 >= '0' && cCharAt2 <= '9') {
            int i3 = cCharAt2 - '0';
            while (true) {
                i2 = i + 1;
                cCharAt = charAt(this.bp + i);
                if (cCharAt < '0' || cCharAt > '9') {
                    break;
                }
                i3 = (i3 * 10) + (cCharAt - '0');
                i = i2;
            }
            if (cCharAt == '.') {
                this.matchStat = -1;
                return 0;
            }
            if (i3 < 0) {
                this.matchStat = -1;
                return 0;
            }
            while (true) {
                if (cCharAt == c) {
                    int i4 = this.bp + i2;
                    this.bp = i4;
                    this.ch = charAt(i4);
                    this.matchStat = 3;
                    this.token = 16;
                    if (z2) {
                        return -i3;
                    }
                } else if (isWhitespace(cCharAt)) {
                    char cCharAt3 = charAt(this.bp + i2);
                    i2++;
                    cCharAt = cCharAt3;
                } else {
                    this.matchStat = -1;
                    if (z2) {
                        return -i3;
                    }
                }
            }
            return i3;
        }
        if (cCharAt2 == 'n' && charAt(this.bp + i) == 'u' && charAt(this.bp + i + 1) == 'l' && charAt(this.bp + i + 2) == 'l') {
            this.matchStat = 5;
            int i5 = i + 4;
            char cCharAt4 = charAt(this.bp + i + 3);
            if (z && cCharAt4 == '\"') {
                cCharAt4 = charAt(this.bp + i5);
                i5 = i + 5;
            }
            while (cCharAt4 != ',') {
                if (cCharAt4 == ']') {
                    int i6 = this.bp + i5;
                    this.bp = i6;
                    this.ch = charAt(i6);
                    this.matchStat = 5;
                    this.token = 15;
                    return 0;
                }
                if (isWhitespace(cCharAt4)) {
                    cCharAt4 = charAt(this.bp + i5);
                    i5++;
                } else {
                    this.matchStat = -1;
                    return 0;
                }
            }
            int i7 = this.bp + i5;
            this.bp = i7;
            this.ch = charAt(i7);
            this.matchStat = 5;
            this.token = 16;
            return 0;
        }
        this.matchStat = -1;
        return 0;
    }

    public boolean scanFieldBoolean(char[] cArr) {
        int i;
        boolean z;
        this.matchStat = 0;
        if (!charArrayCompare(cArr)) {
            this.matchStat = -2;
            return false;
        }
        int length = cArr.length;
        int i2 = length + 1;
        char cCharAt = charAt(this.bp + length);
        if (cCharAt == 't') {
            int i3 = length + 2;
            if (charAt(this.bp + i2) != 'r') {
                this.matchStat = -1;
                return false;
            }
            int i4 = length + 3;
            if (charAt(this.bp + i3) != 'u') {
                this.matchStat = -1;
                return false;
            }
            i = length + 4;
            if (charAt(this.bp + i4) != 'e') {
                this.matchStat = -1;
                return false;
            }
            z = true;
        } else if (cCharAt == 'f') {
            int i5 = length + 2;
            if (charAt(this.bp + i2) != 'a') {
                this.matchStat = -1;
                return false;
            }
            int i6 = length + 3;
            if (charAt(this.bp + i5) != 'l') {
                this.matchStat = -1;
                return false;
            }
            int i7 = length + 4;
            if (charAt(this.bp + i6) != 's') {
                this.matchStat = -1;
                return false;
            }
            i = length + 5;
            if (charAt(this.bp + i7) != 'e') {
                this.matchStat = -1;
                return false;
            }
            z = false;
        } else {
            this.matchStat = -1;
            return false;
        }
        int i8 = i + 1;
        char cCharAt2 = charAt(this.bp + i);
        if (cCharAt2 == ',') {
            int i9 = this.bp + i8;
            this.bp = i9;
            this.ch = charAt(i9);
            this.matchStat = 3;
            this.token = 16;
            return z;
        }
        if (cCharAt2 == '}') {
            int i10 = i + 2;
            char cCharAt3 = charAt(this.bp + i8);
            if (cCharAt3 == ',') {
                this.token = 16;
                int i11 = this.bp + i10;
                this.bp = i11;
                this.ch = charAt(i11);
            } else if (cCharAt3 == ']') {
                this.token = 15;
                int i12 = this.bp + i10;
                this.bp = i12;
                this.ch = charAt(i12);
            } else if (cCharAt3 == '}') {
                this.token = 13;
                int i13 = this.bp + i10;
                this.bp = i13;
                this.ch = charAt(i13);
            } else if (cCharAt3 == 26) {
                this.token = 20;
                this.bp += i + 1;
                this.ch = JSONLexer.EOI;
            } else {
                this.matchStat = -1;
                return false;
            }
            this.matchStat = 4;
            return z;
        }
        this.matchStat = -1;
        return false;
    }

    public long scanFieldLong(char[] cArr) {
        int i;
        char cCharAt;
        boolean z = false;
        this.matchStat = 0;
        if (!charArrayCompare(cArr)) {
            this.matchStat = -2;
            return 0L;
        }
        int length = cArr.length;
        int i2 = length + 1;
        char cCharAt2 = charAt(this.bp + length);
        if (cCharAt2 == '-') {
            cCharAt2 = charAt(this.bp + i2);
            i2 = length + 2;
            z = true;
        }
        if (cCharAt2 < '0' || cCharAt2 > '9') {
            this.matchStat = -1;
            return 0L;
        }
        long j = cCharAt2 - '0';
        while (true) {
            i = i2 + 1;
            cCharAt = charAt(this.bp + i2);
            if (cCharAt < '0' || cCharAt > '9') {
                break;
            }
            j = (j * 10) + ((long) (cCharAt - '0'));
            i2 = i;
        }
        if (cCharAt == '.') {
            this.matchStat = -1;
            return 0L;
        }
        if (i - cArr.length >= 21 || (j < 0 && !(j == Long.MIN_VALUE && z))) {
            this.matchStat = -1;
            return 0L;
        }
        if (cCharAt == ',') {
            int i3 = this.bp + i;
            this.bp = i3;
            this.ch = charAt(i3);
            this.matchStat = 3;
            this.token = 16;
            if (z) {
                return -j;
            }
        } else if (cCharAt == '}') {
            int i4 = i2 + 2;
            char cCharAt3 = charAt(this.bp + i);
            if (cCharAt3 == ',') {
                this.token = 16;
                int i5 = this.bp + i4;
                this.bp = i5;
                this.ch = charAt(i5);
            } else if (cCharAt3 == ']') {
                this.token = 15;
                int i6 = this.bp + i4;
                this.bp = i6;
                this.ch = charAt(i6);
            } else if (cCharAt3 == '}') {
                this.token = 13;
                int i7 = this.bp + i4;
                this.bp = i7;
                this.ch = charAt(i7);
            } else if (cCharAt3 == 26) {
                this.token = 20;
                this.bp += i2 + 1;
                this.ch = JSONLexer.EOI;
            } else {
                this.matchStat = -1;
                return 0L;
            }
            this.matchStat = 4;
            if (z) {
                return -j;
            }
        } else {
            this.matchStat = -1;
            return 0L;
        }
        return j;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public long scanLong(char c) {
        int i;
        int i2;
        char cCharAt;
        char c2;
        this.matchStat = 0;
        char cCharAt2 = charAt(this.bp);
        boolean z = cCharAt2 == '\"';
        if (z) {
            cCharAt2 = charAt(this.bp + 1);
            i = 2;
        } else {
            i = 1;
        }
        boolean z2 = cCharAt2 == '-';
        if (z2) {
            cCharAt2 = charAt(this.bp + i);
            i++;
        }
        if (cCharAt2 < '0' || cCharAt2 > '9') {
            if (cCharAt2 == 'n' && charAt(this.bp + i) == 'u' && charAt(this.bp + i + 1) == 'l' && charAt(this.bp + i + 2) == 'l') {
                this.matchStat = 5;
                int i3 = i + 4;
                char cCharAt3 = charAt(this.bp + i + 3);
                if (z && cCharAt3 == '\"') {
                    cCharAt3 = charAt(this.bp + i3);
                    i3 = i + 5;
                }
                while (cCharAt3 != ',') {
                    if (cCharAt3 == ']') {
                        int i4 = this.bp + i3;
                        this.bp = i4;
                        this.ch = charAt(i4);
                        this.matchStat = 5;
                        this.token = 15;
                        return 0L;
                    }
                    if (isWhitespace(cCharAt3)) {
                        cCharAt3 = charAt(this.bp + i3);
                        i3++;
                    } else {
                        this.matchStat = -1;
                        return 0L;
                    }
                }
                int i5 = this.bp + i3;
                this.bp = i5;
                this.ch = charAt(i5);
                this.matchStat = 5;
                this.token = 16;
                return 0L;
            }
            this.matchStat = -1;
            return 0L;
        }
        long j = cCharAt2 - '0';
        while (true) {
            i2 = i + 1;
            cCharAt = charAt(this.bp + i);
            if (cCharAt < '0' || cCharAt > '9') {
                break;
            }
            j = (j * 10) + ((long) (cCharAt - '0'));
            i = i2;
        }
        if (cCharAt == '.') {
            this.matchStat = -1;
            return 0L;
        }
        if (j < 0 && (j != Long.MIN_VALUE || !z2)) {
            throw new NumberFormatException(subString(this.bp, i));
        }
        if (!z) {
            c2 = c;
        } else {
            if (cCharAt != '\"') {
                this.matchStat = -1;
                return 0L;
            }
            cCharAt = charAt(this.bp + i2);
            c2 = c;
            i2 = i + 2;
        }
        while (cCharAt != c2) {
            if (isWhitespace(cCharAt)) {
                cCharAt = charAt(this.bp + i2);
                i2++;
            } else {
                this.matchStat = -1;
                return j;
            }
        }
        int i6 = this.bp + i2;
        this.bp = i6;
        this.ch = charAt(i6);
        this.matchStat = 3;
        this.token = 16;
        return z2 ? -j : j;
    }

    public final float scanFieldFloat(char[] cArr) {
        int i;
        char cCharAt;
        long j;
        int length;
        int i2;
        float f;
        this.matchStat = 0;
        if (!charArrayCompare(cArr)) {
            this.matchStat = -2;
            return 0.0f;
        }
        int length2 = cArr.length;
        int i3 = length2 + 1;
        char cCharAt2 = charAt(this.bp + length2);
        boolean z = cCharAt2 == '\"';
        if (z) {
            cCharAt2 = charAt(this.bp + i3);
            i3 = length2 + 2;
        }
        boolean z2 = cCharAt2 == '-';
        if (z2) {
            cCharAt2 = charAt(this.bp + i3);
            i3++;
        }
        if (cCharAt2 < '0' || cCharAt2 > '9') {
            boolean z3 = z;
            if (cCharAt2 == 'n' && charAt(this.bp + i3) == 'u' && charAt(this.bp + i3 + 1) == 'l' && charAt(this.bp + i3 + 2) == 'l') {
                this.matchStat = 5;
                int i4 = i3 + 4;
                char cCharAt3 = charAt(this.bp + i3 + 3);
                if (z3 && cCharAt3 == '\"') {
                    int i5 = i3 + 5;
                    cCharAt3 = charAt(this.bp + i4);
                    i4 = i5;
                }
                while (cCharAt3 != ',') {
                    if (cCharAt3 == '}') {
                        int i6 = this.bp + i4;
                        this.bp = i6;
                        this.ch = charAt(i6);
                        this.matchStat = 5;
                        this.token = 13;
                        return 0.0f;
                    }
                    if (isWhitespace(cCharAt3)) {
                        int i7 = i4 + 1;
                        cCharAt3 = charAt(this.bp + i4);
                        i4 = i7;
                    } else {
                        this.matchStat = -1;
                        return 0.0f;
                    }
                }
                int i8 = this.bp + i4;
                this.bp = i8;
                this.ch = charAt(i8);
                this.matchStat = 5;
                this.token = 16;
                return 0.0f;
            }
            this.matchStat = -1;
            return 0.0f;
        }
        boolean z4 = z;
        long j2 = cCharAt2 - '0';
        while (true) {
            i = i3 + 1;
            cCharAt = charAt(this.bp + i3);
            if (cCharAt < '0' || cCharAt > '9') {
                break;
            }
            j2 = (j2 * 10) + ((long) (cCharAt - '0'));
            i3 = i;
        }
        if (cCharAt == '.') {
            int i9 = i3 + 2;
            char cCharAt4 = charAt(this.bp + i);
            if (cCharAt4 < '0' || cCharAt4 > '9') {
                this.matchStat = -1;
                return 0.0f;
            }
            j2 = (j2 * 10) + ((long) (cCharAt4 - '0'));
            j = 10;
            while (true) {
                i = i9 + 1;
                cCharAt = charAt(this.bp + i9);
                if (cCharAt < '0' || cCharAt > '9') {
                    break;
                }
                j2 = (j2 * 10) + ((long) (cCharAt - '0'));
                j *= 10;
                i9 = i;
            }
        } else {
            j = 1;
        }
        boolean z5 = cCharAt == 'e' || cCharAt == 'E';
        if (z5) {
            int i10 = i + 1;
            cCharAt = charAt(this.bp + i);
            if (cCharAt == '+' || cCharAt == '-') {
                i += 2;
                cCharAt = charAt(this.bp + i10);
            } else {
                i = i10;
            }
            while (cCharAt >= '0' && cCharAt <= '9') {
                char cCharAt5 = charAt(this.bp + i);
                i++;
                cCharAt = cCharAt5;
            }
        }
        if (!z4) {
            int i11 = this.bp;
            length = cArr.length + i11;
            i2 = ((i11 + i) - length) - 1;
        } else {
            if (cCharAt != '\"') {
                this.matchStat = -1;
                return 0.0f;
            }
            int i12 = i + 1;
            cCharAt = charAt(this.bp + i);
            int i13 = this.bp;
            length = cArr.length + i13 + 1;
            i2 = ((i13 + i12) - length) - 2;
            i = i12;
        }
        if (z5 || i2 >= 17) {
            f = Float.parseFloat(subString(length, i2));
        } else {
            f = (float) (j2 / j);
            if (z2) {
                f = -f;
            }
        }
        if (cCharAt == ',') {
            int i14 = this.bp + i;
            this.bp = i14;
            this.ch = charAt(i14);
            this.matchStat = 3;
            this.token = 16;
            return f;
        }
        if (cCharAt == '}') {
            int i15 = i + 1;
            char cCharAt6 = charAt(this.bp + i);
            if (cCharAt6 == ',') {
                this.token = 16;
                int i16 = this.bp + i15;
                this.bp = i16;
                this.ch = charAt(i16);
            } else if (cCharAt6 == ']') {
                this.token = 15;
                int i17 = this.bp + i15;
                this.bp = i17;
                this.ch = charAt(i17);
            } else if (cCharAt6 == '}') {
                this.token = 13;
                int i18 = this.bp + i15;
                this.bp = i18;
                this.ch = charAt(i18);
            } else if (cCharAt6 == 26) {
                this.bp += i;
                this.token = 20;
                this.ch = JSONLexer.EOI;
            } else {
                this.matchStat = -1;
                return 0.0f;
            }
            this.matchStat = 4;
            return f;
        }
        this.matchStat = -1;
        return 0.0f;
    }

    /* JADX WARN: Removed duplicated region for block: B:52:0x00c5 A[ADDED_TO_REGION] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:53:0x00c7 -> B:49:0x00b7). Please report as a decompilation issue!!! */
    @Override // com.alibaba.fastjson.parser.JSONLexer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final float scanFloat(char r23) {
        /*
            Method dump skipped, instruction units count: 435
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.JSONLexerBase.scanFloat(char):float");
    }

    /* JADX WARN: Removed duplicated region for block: B:52:0x00c8 A[ADDED_TO_REGION] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:53:0x00ca -> B:49:0x00ba). Please report as a decompilation issue!!! */
    @Override // com.alibaba.fastjson.parser.JSONLexer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public double scanDouble(char r25) {
        /*
            Method dump skipped, instruction units count: 431
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.JSONLexerBase.scanDouble(char):double");
    }

    /* JADX WARN: Removed duplicated region for block: B:42:0x009b A[ADDED_TO_REGION] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:43:0x009d -> B:39:0x008d). Please report as a decompilation issue!!! */
    @Override // com.alibaba.fastjson.parser.JSONLexer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.math.BigDecimal scanDecimal(char r18) {
        /*
            Method dump skipped, instruction units count: 449
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.JSONLexerBase.scanDecimal(char):java.math.BigDecimal");
    }

    /* JADX WARN: Code restructure failed: missing block: B:36:0x00a1, code lost:
    
        r20.matchStat = -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x00a3, code lost:
    
        return r17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:97:0x01af, code lost:
    
        r17 = r4;
        r20.matchStat = -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:98:0x01b4, code lost:
    
        return r17;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final float[] scanFieldFloatArray(char[] r21) {
        /*
            Method dump skipped, instruction units count: 437
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.JSONLexerBase.scanFieldFloatArray(char[]):float[]");
    }

    /* JADX WARN: Code restructure failed: missing block: B:39:0x00b7, code lost:
    
        r22.matchStat = r11;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x00bd, code lost:
    
        return r16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x013e, code lost:
    
        r5 = r20 + 1;
        r2 = charAt(r22.bp + r20);
     */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x0149, code lost:
    
        if (r4 == r3.length) goto L77;
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x014b, code lost:
    
        r7 = new float[r4];
        java.lang.System.arraycopy(r3, 0, r7, 0, r4);
        r3 = r7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:78:0x0152, code lost:
    
        if (r8 < r6.length) goto L80;
     */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x0154, code lost:
    
        r6 = new float[(r6.length * 3) / 2][];
        java.lang.System.arraycopy(r3, 0, r6, 0, r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:80:0x015e, code lost:
    
        r4 = r8 + 1;
        r6[r8] = r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:81:0x0164, code lost:
    
        if (r2 != ',') goto L83;
     */
    /* JADX WARN: Code restructure failed: missing block: B:82:0x0166, code lost:
    
        r3 = charAt(r22.bp + r5);
        r2 = r20 + 2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:84:0x0175, code lost:
    
        if (r2 != ']') goto L86;
     */
    /* JADX WARN: Code restructure failed: missing block: B:85:0x0177, code lost:
    
        r3 = charAt(r22.bp + r5);
        r2 = r20 + 2;
        r8 = r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:86:0x0185, code lost:
    
        r3 = r2;
        r2 = r5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:90:0x019d, code lost:
    
        r22.matchStat = r11;
     */
    /* JADX WARN: Code restructure failed: missing block: B:91:0x01a4, code lost:
    
        return r16;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final float[][] scanFieldFloatArray2(char[] r23) {
        /*
            Method dump skipped, instruction units count: 554
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.JSONLexerBase.scanFieldFloatArray2(char[]):float[][]");
    }

    public final double scanFieldDouble(char[] cArr) {
        int i;
        char cCharAt;
        long j;
        int length;
        int i2;
        double d;
        this.matchStat = 0;
        if (!charArrayCompare(cArr)) {
            this.matchStat = -2;
            return AudioStats.AUDIO_AMPLITUDE_NONE;
        }
        int length2 = cArr.length;
        int i3 = length2 + 1;
        char cCharAt2 = charAt(this.bp + length2);
        boolean z = cCharAt2 == '\"';
        if (z) {
            cCharAt2 = charAt(this.bp + i3);
            i3 = length2 + 2;
        }
        boolean z2 = cCharAt2 == '-';
        if (z2) {
            cCharAt2 = charAt(this.bp + i3);
            i3++;
        }
        if (cCharAt2 < '0' || cCharAt2 > '9') {
            boolean z3 = z;
            if (cCharAt2 == 'n' && charAt(this.bp + i3) == 'u' && charAt(this.bp + i3 + 1) == 'l' && charAt(this.bp + i3 + 2) == 'l') {
                this.matchStat = 5;
                int i4 = i3 + 4;
                char cCharAt3 = charAt(this.bp + i3 + 3);
                if (z3 && cCharAt3 == '\"') {
                    int i5 = i3 + 5;
                    cCharAt3 = charAt(this.bp + i4);
                    i4 = i5;
                }
                while (cCharAt3 != ',') {
                    if (cCharAt3 == '}') {
                        int i6 = this.bp + i4;
                        this.bp = i6;
                        this.ch = charAt(i6);
                        this.matchStat = 5;
                        this.token = 13;
                        return AudioStats.AUDIO_AMPLITUDE_NONE;
                    }
                    if (isWhitespace(cCharAt3)) {
                        int i7 = i4 + 1;
                        cCharAt3 = charAt(this.bp + i4);
                        i4 = i7;
                    } else {
                        this.matchStat = -1;
                        return AudioStats.AUDIO_AMPLITUDE_NONE;
                    }
                }
                int i8 = this.bp + i4;
                this.bp = i8;
                this.ch = charAt(i8);
                this.matchStat = 5;
                this.token = 16;
                return AudioStats.AUDIO_AMPLITUDE_NONE;
            }
            this.matchStat = -1;
            return AudioStats.AUDIO_AMPLITUDE_NONE;
        }
        boolean z4 = z;
        long j2 = cCharAt2 - '0';
        while (true) {
            i = i3 + 1;
            cCharAt = charAt(this.bp + i3);
            if (cCharAt < '0' || cCharAt > '9') {
                break;
            }
            j2 = (j2 * 10) + ((long) (cCharAt - '0'));
            i3 = i;
        }
        if (cCharAt == '.') {
            int i9 = i3 + 2;
            char cCharAt4 = charAt(this.bp + i);
            if (cCharAt4 < '0' || cCharAt4 > '9') {
                this.matchStat = -1;
                return AudioStats.AUDIO_AMPLITUDE_NONE;
            }
            j2 = (j2 * 10) + ((long) (cCharAt4 - '0'));
            j = 10;
            while (true) {
                i = i9 + 1;
                cCharAt = charAt(this.bp + i9);
                if (cCharAt < '0' || cCharAt > '9') {
                    break;
                }
                j2 = (j2 * 10) + ((long) (cCharAt - '0'));
                j *= 10;
                i9 = i;
            }
        } else {
            j = 1;
        }
        boolean z5 = cCharAt == 'e' || cCharAt == 'E';
        if (z5) {
            int i10 = i + 1;
            cCharAt = charAt(this.bp + i);
            if (cCharAt == '+' || cCharAt == '-') {
                i += 2;
                cCharAt = charAt(this.bp + i10);
            } else {
                i = i10;
            }
            while (cCharAt >= '0' && cCharAt <= '9') {
                char cCharAt5 = charAt(this.bp + i);
                i++;
                cCharAt = cCharAt5;
            }
        }
        if (!z4) {
            int i11 = this.bp;
            length = cArr.length + i11;
            i2 = ((i11 + i) - length) - 1;
        } else {
            if (cCharAt != '\"') {
                this.matchStat = -1;
                return AudioStats.AUDIO_AMPLITUDE_NONE;
            }
            int i12 = i + 1;
            cCharAt = charAt(this.bp + i);
            int i13 = this.bp;
            length = cArr.length + i13 + 1;
            i2 = ((i13 + i12) - length) - 2;
            i = i12;
        }
        if (z5 || i2 >= 17) {
            d = Double.parseDouble(subString(length, i2));
        } else {
            d = j2 / j;
            if (z2) {
                d = -d;
            }
        }
        if (cCharAt == ',') {
            int i14 = this.bp + i;
            this.bp = i14;
            this.ch = charAt(i14);
            this.matchStat = 3;
            this.token = 16;
            return d;
        }
        if (cCharAt == '}') {
            int i15 = i + 1;
            char cCharAt6 = charAt(this.bp + i);
            if (cCharAt6 == ',') {
                this.token = 16;
                int i16 = this.bp + i15;
                this.bp = i16;
                this.ch = charAt(i16);
            } else if (cCharAt6 == ']') {
                this.token = 15;
                int i17 = this.bp + i15;
                this.bp = i17;
                this.ch = charAt(i17);
            } else if (cCharAt6 == '}') {
                this.token = 13;
                int i18 = this.bp + i15;
                this.bp = i18;
                this.ch = charAt(i18);
            } else if (cCharAt6 == 26) {
                this.token = 20;
                this.bp += i;
                this.ch = JSONLexer.EOI;
            } else {
                this.matchStat = -1;
                return AudioStats.AUDIO_AMPLITUDE_NONE;
            }
            this.matchStat = 4;
            return d;
        }
        this.matchStat = -1;
        return AudioStats.AUDIO_AMPLITUDE_NONE;
    }

    public BigDecimal scanFieldDecimal(char[] cArr) {
        int i;
        char cCharAt;
        int length;
        int i2;
        this.matchStat = 0;
        if (!charArrayCompare(cArr)) {
            this.matchStat = -2;
            return null;
        }
        int length2 = cArr.length;
        int i3 = length2 + 1;
        char cCharAt2 = charAt(this.bp + length2);
        boolean z = cCharAt2 == '\"';
        if (z) {
            cCharAt2 = charAt(this.bp + i3);
            i3 = length2 + 2;
        }
        if (cCharAt2 == '-') {
            cCharAt2 = charAt(this.bp + i3);
            i3++;
        }
        if (cCharAt2 >= '0' && cCharAt2 <= '9') {
            while (true) {
                i = i3 + 1;
                cCharAt = charAt(this.bp + i3);
                if (cCharAt < '0' || cCharAt > '9') {
                    break;
                }
                i3 = i;
            }
            if (cCharAt == '.') {
                int i4 = i3 + 2;
                char cCharAt3 = charAt(this.bp + i);
                if (cCharAt3 < '0' || cCharAt3 > '9') {
                    this.matchStat = -1;
                    return null;
                }
                while (true) {
                    i = i4 + 1;
                    cCharAt = charAt(this.bp + i4);
                    if (cCharAt < '0' || cCharAt > '9') {
                        break;
                    }
                    i4 = i;
                }
            }
            if (cCharAt == 'e' || cCharAt == 'E') {
                int i5 = i + 1;
                char cCharAt4 = charAt(this.bp + i);
                if (cCharAt4 == '+' || cCharAt4 == '-') {
                    i += 2;
                    cCharAt = charAt(this.bp + i5);
                } else {
                    i = i5;
                    cCharAt = cCharAt4;
                }
                while (cCharAt >= '0' && cCharAt <= '9') {
                    cCharAt = charAt(this.bp + i);
                    i++;
                }
            }
            if (!z) {
                int i6 = this.bp;
                length = cArr.length + i6;
                i2 = ((i6 + i) - length) - 1;
            } else {
                if (cCharAt != '\"') {
                    this.matchStat = -1;
                    return null;
                }
                int i7 = i + 1;
                cCharAt = charAt(this.bp + i);
                int i8 = this.bp;
                length = cArr.length + i8 + 1;
                i2 = ((i8 + i7) - length) - 2;
                i = i7;
            }
            BigDecimal bigDecimal = new BigDecimal(sub_chars(length, i2));
            if (cCharAt == ',') {
                int i9 = this.bp + i;
                this.bp = i9;
                this.ch = charAt(i9);
                this.matchStat = 3;
                this.token = 16;
                return bigDecimal;
            }
            if (cCharAt == '}') {
                int i10 = i + 1;
                char cCharAt5 = charAt(this.bp + i);
                if (cCharAt5 == ',') {
                    this.token = 16;
                    int i11 = this.bp + i10;
                    this.bp = i11;
                    this.ch = charAt(i11);
                } else if (cCharAt5 == ']') {
                    this.token = 15;
                    int i12 = this.bp + i10;
                    this.bp = i12;
                    this.ch = charAt(i12);
                } else if (cCharAt5 == '}') {
                    this.token = 13;
                    int i13 = this.bp + i10;
                    this.bp = i13;
                    this.ch = charAt(i13);
                } else if (cCharAt5 == 26) {
                    this.token = 20;
                    this.bp += i;
                    this.ch = JSONLexer.EOI;
                } else {
                    this.matchStat = -1;
                    return null;
                }
                this.matchStat = 4;
                return bigDecimal;
            }
            this.matchStat = -1;
            return null;
        }
        if (cCharAt2 == 'n' && charAt(this.bp + i3) == 'u' && charAt(this.bp + i3 + 1) == 'l' && charAt(this.bp + i3 + 2) == 'l') {
            this.matchStat = 5;
            int i14 = i3 + 4;
            char cCharAt6 = charAt(this.bp + i3 + 3);
            if (z && cCharAt6 == '\"') {
                cCharAt6 = charAt(this.bp + i14);
                i14 = i3 + 5;
            }
            while (cCharAt6 != ',') {
                if (cCharAt6 == '}') {
                    int i15 = this.bp + i14;
                    this.bp = i15;
                    this.ch = charAt(i15);
                    this.matchStat = 5;
                    this.token = 13;
                    return null;
                }
                if (isWhitespace(cCharAt6)) {
                    char cCharAt7 = charAt(this.bp + i14);
                    i14++;
                    cCharAt6 = cCharAt7;
                } else {
                    this.matchStat = -1;
                    return null;
                }
            }
            int i16 = this.bp + i14;
            this.bp = i16;
            this.ch = charAt(i16);
            this.matchStat = 5;
            this.token = 16;
            return null;
        }
        this.matchStat = -1;
        return null;
    }

    public BigInteger scanFieldBigInteger(char[] cArr) {
        int i;
        char cCharAt;
        int length;
        int i2;
        BigInteger bigIntegerValueOf;
        this.matchStat = 0;
        if (!charArrayCompare(cArr)) {
            this.matchStat = -2;
            return null;
        }
        int length2 = cArr.length;
        int i3 = length2 + 1;
        char cCharAt2 = charAt(this.bp + length2);
        boolean z = cCharAt2 == '\"';
        if (z) {
            cCharAt2 = charAt(this.bp + i3);
            i3 = length2 + 2;
        }
        boolean z2 = cCharAt2 == '-';
        if (z2) {
            char cCharAt3 = charAt(this.bp + i3);
            i3++;
            cCharAt2 = cCharAt3;
        }
        if (cCharAt2 < '0' || cCharAt2 > '9') {
            if (cCharAt2 == 'n' && charAt(this.bp + i3) == 'u' && charAt(this.bp + i3 + 1) == 'l' && charAt(this.bp + i3 + 2) == 'l') {
                this.matchStat = 5;
                int i4 = i3 + 4;
                char cCharAt4 = charAt(this.bp + i3 + 3);
                if (z && cCharAt4 == '\"') {
                    cCharAt4 = charAt(this.bp + i4);
                    i4 = i3 + 5;
                }
                while (cCharAt4 != ',') {
                    if (cCharAt4 == '}') {
                        int i5 = this.bp + i4;
                        this.bp = i5;
                        this.ch = charAt(i5);
                        this.matchStat = 5;
                        this.token = 13;
                        return null;
                    }
                    if (isWhitespace(cCharAt4)) {
                        cCharAt4 = charAt(this.bp + i4);
                        i4++;
                    } else {
                        this.matchStat = -1;
                        return null;
                    }
                }
                int i6 = this.bp + i4;
                this.bp = i6;
                this.ch = charAt(i6);
                this.matchStat = 5;
                this.token = 16;
                return null;
            }
            this.matchStat = -1;
            return null;
        }
        long j = cCharAt2 - '0';
        while (true) {
            i = i3 + 1;
            cCharAt = charAt(this.bp + i3);
            if (cCharAt < '0' || cCharAt > '9') {
                break;
            }
            j = (j * 10) + ((long) (cCharAt - '0'));
            i3 = i;
        }
        if (!z) {
            int i7 = this.bp;
            length = cArr.length + i7;
            i2 = ((i7 + i) - length) - 1;
        } else {
            if (cCharAt != '\"') {
                this.matchStat = -1;
                return null;
            }
            int i8 = i3 + 2;
            cCharAt = charAt(this.bp + i);
            int i9 = this.bp;
            length = cArr.length + i9 + 1;
            i2 = ((i9 + i8) - length) - 2;
            i = i8;
        }
        if (i2 < 20 || (z2 && i2 < 21)) {
            if (z2) {
                j = -j;
            }
            bigIntegerValueOf = BigInteger.valueOf(j);
        } else {
            bigIntegerValueOf = new BigInteger(subString(length, i2));
        }
        if (cCharAt == ',') {
            int i10 = this.bp + i;
            this.bp = i10;
            this.ch = charAt(i10);
            this.matchStat = 3;
            this.token = 16;
            return bigIntegerValueOf;
        }
        if (cCharAt == '}') {
            int i11 = i + 1;
            char cCharAt5 = charAt(this.bp + i);
            if (cCharAt5 == ',') {
                this.token = 16;
                int i12 = this.bp + i11;
                this.bp = i12;
                this.ch = charAt(i12);
            } else if (cCharAt5 == ']') {
                this.token = 15;
                int i13 = this.bp + i11;
                this.bp = i13;
                this.ch = charAt(i13);
            } else if (cCharAt5 == '}') {
                this.token = 13;
                int i14 = this.bp + i11;
                this.bp = i14;
                this.ch = charAt(i14);
            } else if (cCharAt5 == 26) {
                this.token = 20;
                this.bp += i;
                this.ch = JSONLexer.EOI;
            } else {
                this.matchStat = -1;
                return null;
            }
            this.matchStat = 4;
            return bigIntegerValueOf;
        }
        this.matchStat = -1;
        return null;
    }

    public Date scanFieldDate(char[] cArr) {
        char cCharAt;
        int i;
        long j;
        Date date;
        int i2;
        char cCharAt2;
        boolean z = false;
        this.matchStat = 0;
        if (!charArrayCompare(cArr)) {
            this.matchStat = -2;
            return null;
        }
        int length = cArr.length;
        int i3 = length + 1;
        char cCharAt3 = charAt(this.bp + length);
        if (cCharAt3 == '\"') {
            int iIndexOf = indexOf(Typography.quote, this.bp + cArr.length + 1);
            if (iIndexOf == -1) {
                throw new JSONException("unclosed str");
            }
            int length2 = this.bp + cArr.length + 1;
            String strSubString = subString(length2, iIndexOf - length2);
            if (strSubString.indexOf(92) != -1) {
                while (true) {
                    int i4 = 0;
                    for (int i5 = iIndexOf - 1; i5 >= 0 && charAt(i5) == '\\'; i5--) {
                        i4++;
                    }
                    if (i4 % 2 == 0) {
                        break;
                    }
                    iIndexOf = indexOf(Typography.quote, iIndexOf + 1);
                }
                int i6 = this.bp;
                int length3 = iIndexOf - ((cArr.length + i6) + 1);
                strSubString = readString(sub_chars(i6 + cArr.length + 1, length3), length3);
            }
            int i7 = this.bp;
            int length4 = i3 + (iIndexOf - ((cArr.length + i7) + 1)) + 1;
            i = length4 + 1;
            cCharAt = charAt(i7 + length4);
            JSONScanner jSONScanner = new JSONScanner(strSubString);
            try {
                if (jSONScanner.scanISO8601DateIfMatch(false)) {
                    date = jSONScanner.getCalendar().getTime();
                } else {
                    this.matchStat = -1;
                    return null;
                }
            } finally {
                jSONScanner.close();
            }
        } else {
            if (cCharAt3 != '-' && (cCharAt3 < '0' || cCharAt3 > '9')) {
                this.matchStat = -1;
                return null;
            }
            if (cCharAt3 == '-') {
                cCharAt3 = charAt(this.bp + i3);
                i3 = length + 2;
                z = true;
            }
            if (cCharAt3 < '0' || cCharAt3 > '9') {
                cCharAt = cCharAt3;
                i = i3;
                j = 0;
            } else {
                j = cCharAt3 - '0';
                while (true) {
                    i2 = i3 + 1;
                    cCharAt2 = charAt(this.bp + i3);
                    if (cCharAt2 < '0' || cCharAt2 > '9') {
                        break;
                    }
                    j = (j * 10) + ((long) (cCharAt2 - '0'));
                    i3 = i2;
                }
                cCharAt = cCharAt2;
                i = i2;
            }
            if (j < 0) {
                this.matchStat = -1;
                return null;
            }
            if (z) {
                j = -j;
            }
            date = new Date(j);
        }
        if (cCharAt == ',') {
            int i8 = this.bp + i;
            this.bp = i8;
            this.ch = charAt(i8);
            this.matchStat = 3;
            return date;
        }
        if (cCharAt == '}') {
            int i9 = i + 1;
            char cCharAt4 = charAt(this.bp + i);
            if (cCharAt4 == ',') {
                this.token = 16;
                int i10 = this.bp + i9;
                this.bp = i10;
                this.ch = charAt(i10);
            } else if (cCharAt4 == ']') {
                this.token = 15;
                int i11 = this.bp + i9;
                this.bp = i11;
                this.ch = charAt(i11);
            } else if (cCharAt4 == '}') {
                this.token = 13;
                int i12 = this.bp + i9;
                this.bp = i12;
                this.ch = charAt(i12);
            } else if (cCharAt4 == 26) {
                this.token = 20;
                this.bp += i;
                this.ch = JSONLexer.EOI;
            } else {
                this.matchStat = -1;
                return null;
            }
            this.matchStat = 4;
            return date;
        }
        this.matchStat = -1;
        return null;
    }

    public Date scanDate(char c) {
        char cCharAt;
        int i;
        long j;
        Date date;
        int i2;
        char cCharAt2;
        boolean z = false;
        this.matchStat = 0;
        char cCharAt3 = charAt(this.bp);
        int i3 = 1;
        if (cCharAt3 == '\"') {
            int iIndexOf = indexOf(Typography.quote, this.bp + 1);
            if (iIndexOf == -1) {
                throw new JSONException("unclosed str");
            }
            int i4 = this.bp + 1;
            String strSubString = subString(i4, iIndexOf - i4);
            if (strSubString.indexOf(92) != -1) {
                while (true) {
                    int i5 = 0;
                    for (int i6 = iIndexOf - 1; i6 >= 0 && charAt(i6) == '\\'; i6--) {
                        i5++;
                    }
                    if (i5 % 2 == 0) {
                        break;
                    }
                    iIndexOf = indexOf(Typography.quote, iIndexOf + 1);
                }
                int i7 = this.bp;
                int i8 = iIndexOf - (i7 + 1);
                strSubString = readString(sub_chars(i7 + 1, i8), i8);
            }
            int i9 = this.bp;
            int i10 = iIndexOf - (i9 + 1);
            int i11 = i10 + 2;
            i = i10 + 3;
            cCharAt = charAt(i9 + i11);
            JSONScanner jSONScanner = new JSONScanner(strSubString);
            try {
                if (jSONScanner.scanISO8601DateIfMatch(false)) {
                    date = jSONScanner.getCalendar().getTime();
                } else {
                    this.matchStat = -1;
                    return null;
                }
            } finally {
                jSONScanner.close();
            }
        } else {
            char c2 = '9';
            if (cCharAt3 == '-' || (cCharAt3 >= '0' && cCharAt3 <= '9')) {
                if (cCharAt3 == '-') {
                    cCharAt3 = charAt(this.bp + 1);
                    z = true;
                    i3 = 2;
                }
                if (cCharAt3 < '0' || cCharAt3 > '9') {
                    int i12 = i3;
                    cCharAt = cCharAt3;
                    i = i12;
                    j = 0;
                } else {
                    j = cCharAt3 - '0';
                    while (true) {
                        i2 = i3 + 1;
                        cCharAt2 = charAt(this.bp + i3);
                        if (cCharAt2 < '0' || cCharAt2 > c2) {
                            break;
                        }
                        j = (j * 10) + ((long) (cCharAt2 - '0'));
                        i3 = i2;
                        c2 = '9';
                    }
                    cCharAt = cCharAt2;
                    i = i2;
                }
                if (j < 0) {
                    this.matchStat = -1;
                    return null;
                }
                if (z) {
                    j = -j;
                }
                date = new Date(j);
            } else if (cCharAt3 == 'n' && charAt(this.bp + 1) == 'u' && charAt(this.bp + 2) == 'l' && charAt(this.bp + 3) == 'l') {
                i = 5;
                this.matchStat = 5;
                cCharAt = charAt(this.bp + 4);
                date = null;
            } else {
                this.matchStat = -1;
                return null;
            }
        }
        if (cCharAt == ',') {
            int i13 = this.bp + i;
            this.bp = i13;
            this.ch = charAt(i13);
            this.matchStat = 3;
            this.token = 16;
            return date;
        }
        if (cCharAt == ']') {
            int i14 = i + 1;
            char cCharAt4 = charAt(this.bp + i);
            if (cCharAt4 == ',') {
                this.token = 16;
                int i15 = this.bp + i14;
                this.bp = i15;
                this.ch = charAt(i15);
            } else if (cCharAt4 == ']') {
                this.token = 15;
                int i16 = this.bp + i14;
                this.bp = i16;
                this.ch = charAt(i16);
            } else if (cCharAt4 == '}') {
                this.token = 13;
                int i17 = this.bp + i14;
                this.bp = i17;
                this.ch = charAt(i17);
            } else if (cCharAt4 == 26) {
                this.token = 20;
                this.bp += i;
                this.ch = JSONLexer.EOI;
            } else {
                this.matchStat = -1;
                return null;
            }
            this.matchStat = 4;
            return date;
        }
        this.matchStat = -1;
        return null;
    }

    public UUID scanFieldUUID(char[] cArr) {
        UUID uuid;
        int i;
        char cCharAt;
        int i2;
        UUID uuid2;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        this.matchStat = 0;
        if (!charArrayCompare(cArr)) {
            this.matchStat = -2;
            return null;
        }
        int length = cArr.length;
        int i10 = length + 1;
        char cCharAt2 = charAt(this.bp + length);
        if (cCharAt2 == '\"') {
            int iIndexOf = indexOf(Typography.quote, this.bp + cArr.length + 1);
            if (iIndexOf == -1) {
                throw new JSONException("unclosed str");
            }
            int length2 = this.bp + cArr.length + 1;
            int i11 = iIndexOf - length2;
            char c = 'F';
            char c2 = 'f';
            uuid = null;
            char c3 = 'A';
            char c4 = 'a';
            i = 4;
            if (i11 == 36) {
                long j = 0;
                for (int i12 = 0; i12 < 8; i12++) {
                    char cCharAt3 = charAt(length2 + i12);
                    if (cCharAt3 >= '0' && cCharAt3 <= '9') {
                        i9 = cCharAt3 - '0';
                    } else if (cCharAt3 >= 'a' && cCharAt3 <= 'f') {
                        i9 = cCharAt3 - 'W';
                    } else {
                        if (cCharAt3 < 'A' || cCharAt3 > 'F') {
                            this.matchStat = -2;
                            return null;
                        }
                        i9 = cCharAt3 - '7';
                    }
                    j = (j << 4) | ((long) i9);
                }
                for (int i13 = 9; i13 < 13; i13++) {
                    char cCharAt4 = charAt(length2 + i13);
                    if (cCharAt4 >= '0' && cCharAt4 <= '9') {
                        i8 = cCharAt4 - '0';
                    } else if (cCharAt4 >= 'a' && cCharAt4 <= 'f') {
                        i8 = cCharAt4 - 'W';
                    } else {
                        if (cCharAt4 < 'A' || cCharAt4 > 'F') {
                            this.matchStat = -2;
                            return null;
                        }
                        i8 = cCharAt4 - '7';
                    }
                    j = (j << 4) | ((long) i8);
                }
                int i14 = 14;
                long j2 = j;
                while (i14 < 18) {
                    char cCharAt5 = charAt(length2 + i14);
                    if (cCharAt5 >= '0' && cCharAt5 <= '9') {
                        i7 = cCharAt5 - '0';
                    } else if (cCharAt5 >= 'a' && cCharAt5 <= 'f') {
                        i7 = cCharAt5 - 'W';
                    } else {
                        if (cCharAt5 < c3 || cCharAt5 > 'F') {
                            this.matchStat = -2;
                            return null;
                        }
                        i7 = cCharAt5 - '7';
                    }
                    j2 = (j2 << 4) | ((long) i7);
                    i14++;
                    length2 = length2;
                    c3 = 'A';
                }
                int i15 = length2;
                int i16 = 19;
                long j3 = 0;
                while (i16 < 23) {
                    char cCharAt6 = charAt(i15 + i16);
                    if (cCharAt6 >= '0' && cCharAt6 <= '9') {
                        i6 = cCharAt6 - '0';
                    } else if (cCharAt6 >= 'a' && cCharAt6 <= c2) {
                        i6 = cCharAt6 - 'W';
                    } else {
                        if (cCharAt6 < 'A' || cCharAt6 > 'F') {
                            this.matchStat = -2;
                            return null;
                        }
                        i6 = cCharAt6 - '7';
                    }
                    j3 = (j3 << 4) | ((long) i6);
                    i16++;
                    iIndexOf = iIndexOf;
                    c2 = 'f';
                }
                int i17 = iIndexOf;
                int i18 = 24;
                long j4 = j3;
                while (i18 < 36) {
                    char cCharAt7 = charAt(i15 + i18);
                    if (cCharAt7 >= '0' && cCharAt7 <= '9') {
                        i5 = cCharAt7 - '0';
                    } else if (cCharAt7 >= c4 && cCharAt7 <= 'f') {
                        i5 = cCharAt7 - 'W';
                    } else {
                        if (cCharAt7 < 'A' || cCharAt7 > c) {
                            this.matchStat = -2;
                            return null;
                        }
                        i5 = cCharAt7 - '7';
                    }
                    j4 = (j4 << 4) | ((long) i5);
                    i18++;
                    c4 = 'a';
                    c = 'F';
                }
                uuid2 = new UUID(j2, j4);
                int i19 = this.bp;
                int length3 = i10 + (i17 - ((cArr.length + i19) + 1)) + 1;
                i2 = length3 + 1;
                cCharAt = charAt(i19 + length3);
            } else {
                if (i11 == 32) {
                    long j5 = 0;
                    for (int i20 = 0; i20 < 16; i20++) {
                        char cCharAt8 = charAt(length2 + i20);
                        if (cCharAt8 >= '0' && cCharAt8 <= '9') {
                            i4 = cCharAt8 - '0';
                        } else if (cCharAt8 >= 'a' && cCharAt8 <= 'f') {
                            i4 = cCharAt8 - 'W';
                        } else {
                            if (cCharAt8 < 'A' || cCharAt8 > 'F') {
                                this.matchStat = -2;
                                return null;
                            }
                            i4 = cCharAt8 - '7';
                        }
                        j5 = (j5 << 4) | ((long) i4);
                    }
                    int i21 = 16;
                    long j6 = 0;
                    for (int i22 = 32; i21 < i22; i22 = 32) {
                        char cCharAt9 = charAt(length2 + i21);
                        if (cCharAt9 >= '0' && cCharAt9 <= '9') {
                            i3 = cCharAt9 - '0';
                        } else if (cCharAt9 >= 'a' && cCharAt9 <= 'f') {
                            i3 = cCharAt9 - 'W';
                        } else {
                            if (cCharAt9 < 'A' || cCharAt9 > 'F') {
                                this.matchStat = -2;
                                return null;
                            }
                            i3 = cCharAt9 - '7';
                        }
                        j6 = (j6 << 4) | ((long) i3);
                        i21++;
                    }
                    uuid2 = new UUID(j5, j6);
                    int i23 = this.bp;
                    int length4 = i10 + (iIndexOf - ((cArr.length + i23) + 1)) + 1;
                    i2 = length4 + 1;
                    cCharAt = charAt(i23 + length4);
                } else {
                    this.matchStat = -1;
                    return null;
                }
            }
        } else {
            uuid = null;
            i = 4;
            if (cCharAt2 == 'n') {
                int i24 = length + 2;
                if (charAt(this.bp + i10) == 'u') {
                    int i25 = length + 3;
                    if (charAt(this.bp + i24) == 'l') {
                        int i26 = length + 4;
                        if (charAt(this.bp + i25) == 'l') {
                            int i27 = length + 5;
                            cCharAt = charAt(this.bp + i26);
                            i2 = i27;
                            uuid2 = null;
                        }
                    }
                }
            }
            this.matchStat = -1;
            return null;
        }
        if (cCharAt == ',') {
            int i28 = this.bp + i2;
            this.bp = i28;
            this.ch = charAt(i28);
            this.matchStat = 3;
            return uuid2;
        }
        if (cCharAt == '}') {
            int i29 = i2 + 1;
            char cCharAt10 = charAt(this.bp + i2);
            if (cCharAt10 == ',') {
                this.token = 16;
                int i30 = this.bp + i29;
                this.bp = i30;
                this.ch = charAt(i30);
            } else if (cCharAt10 == ']') {
                this.token = 15;
                int i31 = this.bp + i29;
                this.bp = i31;
                this.ch = charAt(i31);
            } else if (cCharAt10 == '}') {
                this.token = 13;
                int i32 = this.bp + i29;
                this.bp = i32;
                this.ch = charAt(i32);
            } else if (cCharAt10 == 26) {
                this.token = 20;
                this.bp += i2;
                this.ch = JSONLexer.EOI;
            } else {
                this.matchStat = -1;
                return uuid;
            }
            this.matchStat = i;
            return uuid2;
        }
        this.matchStat = -1;
        return uuid;
    }

    public UUID scanUUID(char c) {
        UUID uuid;
        int i;
        int i2;
        char cCharAt;
        int i3;
        UUID uuid2;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        this.matchStat = 0;
        char cCharAt2 = charAt(this.bp);
        if (cCharAt2 == '\"') {
            int iIndexOf = indexOf(Typography.quote, this.bp + 1);
            if (iIndexOf == -1) {
                throw new JSONException("unclosed str");
            }
            int i11 = this.bp + 1;
            int i12 = iIndexOf - i11;
            int i13 = 36;
            char c2 = 'A';
            uuid = null;
            char c3 = 'a';
            if (i12 == 36) {
                long j = 0;
                i = 4;
                for (int i14 = 0; i14 < 8; i14++) {
                    char cCharAt3 = charAt(i11 + i14);
                    if (cCharAt3 >= '0' && cCharAt3 <= '9') {
                        i10 = cCharAt3 - '0';
                    } else if (cCharAt3 >= 'a' && cCharAt3 <= 'f') {
                        i10 = cCharAt3 - 'W';
                    } else {
                        if (cCharAt3 < 'A' || cCharAt3 > 'F') {
                            this.matchStat = -2;
                            return null;
                        }
                        i10 = cCharAt3 - '7';
                    }
                    j = (j << 4) | ((long) i10);
                }
                i2 = 3;
                for (int i15 = 9; i15 < 13; i15++) {
                    char cCharAt4 = charAt(i11 + i15);
                    if (cCharAt4 >= '0' && cCharAt4 <= '9') {
                        i9 = cCharAt4 - '0';
                    } else if (cCharAt4 >= 'a' && cCharAt4 <= 'f') {
                        i9 = cCharAt4 - 'W';
                    } else {
                        if (cCharAt4 < 'A' || cCharAt4 > 'F') {
                            this.matchStat = -2;
                            return null;
                        }
                        i9 = cCharAt4 - '7';
                    }
                    j = (j << 4) | ((long) i9);
                }
                long j2 = j;
                for (int i16 = 14; i16 < 18; i16++) {
                    char cCharAt5 = charAt(i11 + i16);
                    if (cCharAt5 >= '0' && cCharAt5 <= '9') {
                        i8 = cCharAt5 - '0';
                    } else if (cCharAt5 >= 'a' && cCharAt5 <= 'f') {
                        i8 = cCharAt5 - 'W';
                    } else {
                        if (cCharAt5 < 'A' || cCharAt5 > 'F') {
                            this.matchStat = -2;
                            return null;
                        }
                        i8 = cCharAt5 - '7';
                    }
                    j2 = (j2 << 4) | ((long) i8);
                }
                int i17 = 19;
                long j3 = 0;
                while (i17 < 23) {
                    char cCharAt6 = charAt(i11 + i17);
                    if (cCharAt6 >= '0' && cCharAt6 <= '9') {
                        i7 = cCharAt6 - '0';
                    } else if (cCharAt6 >= 'a' && cCharAt6 <= 'f') {
                        i7 = cCharAt6 - 'W';
                    } else {
                        if (cCharAt6 < c2 || cCharAt6 > 'F') {
                            this.matchStat = -2;
                            return null;
                        }
                        i7 = cCharAt6 - '7';
                    }
                    j3 = (j3 << 4) | ((long) i7);
                    i17++;
                    iIndexOf = iIndexOf;
                    c2 = 'A';
                }
                int i18 = iIndexOf;
                int i19 = 24;
                long j4 = j3;
                while (i19 < i13) {
                    char cCharAt7 = charAt(i11 + i19);
                    if (cCharAt7 >= '0' && cCharAt7 <= '9') {
                        i6 = cCharAt7 - '0';
                    } else if (cCharAt7 >= c3 && cCharAt7 <= 'f') {
                        i6 = cCharAt7 - 'W';
                    } else {
                        if (cCharAt7 < 'A' || cCharAt7 > 'F') {
                            this.matchStat = -2;
                            return null;
                        }
                        i6 = cCharAt7 - '7';
                    }
                    j4 = (j4 << 4) | ((long) i6);
                    i19++;
                    i13 = 36;
                    c3 = 'a';
                }
                uuid2 = new UUID(j2, j4);
                int i20 = this.bp;
                int i21 = i18 - (i20 + 1);
                int i22 = i21 + 2;
                i3 = i21 + 3;
                cCharAt = charAt(i20 + i22);
            } else {
                i = 4;
                i2 = 3;
                if (i12 == 32) {
                    long j5 = 0;
                    for (int i23 = 0; i23 < 16; i23++) {
                        char cCharAt8 = charAt(i11 + i23);
                        if (cCharAt8 >= '0' && cCharAt8 <= '9') {
                            i5 = cCharAt8 - '0';
                        } else if (cCharAt8 >= 'a' && cCharAt8 <= 'f') {
                            i5 = cCharAt8 - 'W';
                        } else {
                            if (cCharAt8 < 'A' || cCharAt8 > 'F') {
                                this.matchStat = -2;
                                return null;
                            }
                            i5 = cCharAt8 - '7';
                        }
                        j5 = (j5 << 4) | ((long) i5);
                    }
                    int i24 = 16;
                    long j6 = 0;
                    for (int i25 = 32; i24 < i25; i25 = 32) {
                        char cCharAt9 = charAt(i11 + i24);
                        if (cCharAt9 >= '0' && cCharAt9 <= '9') {
                            i4 = cCharAt9 - '0';
                        } else if (cCharAt9 >= 'a' && cCharAt9 <= 'f') {
                            i4 = cCharAt9 - 'W';
                        } else {
                            if (cCharAt9 < 'A' || cCharAt9 > 'F') {
                                this.matchStat = -2;
                                return null;
                            }
                            i4 = cCharAt9 - '7';
                            j6 = (j6 << 4) | ((long) i4);
                            i24++;
                        }
                        j6 = (j6 << 4) | ((long) i4);
                        i24++;
                    }
                    uuid2 = new UUID(j5, j6);
                    int i26 = this.bp;
                    int i27 = iIndexOf - (i26 + 1);
                    int i28 = i27 + 2;
                    i3 = i27 + 3;
                    cCharAt = charAt(i26 + i28);
                } else {
                    this.matchStat = -1;
                    return null;
                }
            }
        } else {
            uuid = null;
            i = 4;
            i2 = 3;
            if (cCharAt2 == 'n' && charAt(this.bp + 1) == 'u' && charAt(this.bp + 2) == 'l' && charAt(this.bp + 3) == 'l') {
                cCharAt = charAt(this.bp + 4);
                i3 = 5;
                uuid2 = null;
            } else {
                this.matchStat = -1;
                return null;
            }
        }
        if (cCharAt == ',') {
            int i29 = this.bp + i3;
            this.bp = i29;
            this.ch = charAt(i29);
            this.matchStat = i2;
            return uuid2;
        }
        if (cCharAt == ']') {
            int i30 = i3 + 1;
            char cCharAt10 = charAt(this.bp + i3);
            if (cCharAt10 == ',') {
                this.token = 16;
                int i31 = this.bp + i30;
                this.bp = i31;
                this.ch = charAt(i31);
            } else if (cCharAt10 == ']') {
                this.token = 15;
                int i32 = this.bp + i30;
                this.bp = i32;
                this.ch = charAt(i32);
            } else if (cCharAt10 == '}') {
                this.token = 13;
                int i33 = this.bp + i30;
                this.bp = i33;
                this.ch = charAt(i33);
            } else if (cCharAt10 == 26) {
                this.token = 20;
                this.bp += i3;
                this.ch = JSONLexer.EOI;
            } else {
                this.matchStat = -1;
                return uuid;
            }
            this.matchStat = i;
            return uuid2;
        }
        this.matchStat = -1;
        return uuid;
    }

    public final void scanTrue() {
        if (this.ch != 't') {
            throw new JSONException("error parse true");
        }
        next();
        if (this.ch != 'r') {
            throw new JSONException("error parse true");
        }
        next();
        if (this.ch != 'u') {
            throw new JSONException("error parse true");
        }
        next();
        if (this.ch != 'e') {
            throw new JSONException("error parse true");
        }
        next();
        char c = this.ch;
        if (c == ' ' || c == ',' || c == '}' || c == ']' || c == '\n' || c == '\r' || c == '\t' || c == 26 || c == '\f' || c == '\b' || c == ':' || c == '/') {
            this.token = 6;
            return;
        }
        throw new JSONException("scan true error");
    }

    public final void scanNullOrNew() {
        scanNullOrNew(true);
    }

    public final void scanNullOrNew(boolean z) {
        if (this.ch != 'n') {
            throw new JSONException("error parse null or new");
        }
        next();
        char c = this.ch;
        if (c != 'u') {
            if (c != 'e') {
                throw new JSONException("error parse new");
            }
            next();
            if (this.ch != 'w') {
                throw new JSONException("error parse new");
            }
            next();
            char c2 = this.ch;
            if (c2 == ' ' || c2 == ',' || c2 == '}' || c2 == ']' || c2 == '\n' || c2 == '\r' || c2 == '\t' || c2 == 26 || c2 == '\f' || c2 == '\b') {
                this.token = 9;
                return;
            }
            throw new JSONException("scan new error");
        }
        next();
        if (this.ch != 'l') {
            throw new JSONException("error parse null");
        }
        next();
        if (this.ch != 'l') {
            throw new JSONException("error parse null");
        }
        next();
        char c3 = this.ch;
        if (c3 == ' ' || c3 == ',' || c3 == '}' || c3 == ']' || c3 == '\n' || c3 == '\r' || c3 == '\t' || c3 == 26 || ((c3 == ':' && z) || c3 == '\f' || c3 == '\b')) {
            this.token = 8;
            return;
        }
        throw new JSONException("scan null error");
    }

    public final void scanFalse() {
        if (this.ch != 'f') {
            throw new JSONException("error parse false");
        }
        next();
        if (this.ch != 'a') {
            throw new JSONException("error parse false");
        }
        next();
        if (this.ch != 'l') {
            throw new JSONException("error parse false");
        }
        next();
        if (this.ch != 's') {
            throw new JSONException("error parse false");
        }
        next();
        if (this.ch != 'e') {
            throw new JSONException("error parse false");
        }
        next();
        char c = this.ch;
        if (c == ' ' || c == ',' || c == '}' || c == ']' || c == '\n' || c == '\r' || c == '\t' || c == 26 || c == '\f' || c == '\b' || c == ':' || c == '/') {
            this.token = 7;
            return;
        }
        throw new JSONException("scan false error");
    }

    public final void scanIdent() {
        this.np = this.bp - 1;
        this.hasSpecial = false;
        do {
            this.sp++;
            next();
        } while (Character.isLetterOrDigit(this.ch));
        String strStringVal = stringVal();
        if (GlobalVariable.nullColor.equalsIgnoreCase(strStringVal)) {
            this.token = 8;
            return;
        }
        if ("new".equals(strStringVal)) {
            this.token = 9;
            return;
        }
        if ("true".equals(strStringVal)) {
            this.token = 6;
            return;
        }
        if ("false".equals(strStringVal)) {
            this.token = 7;
            return;
        }
        if ("undefined".equals(strStringVal)) {
            this.token = 23;
            return;
        }
        if ("Set".equals(strStringVal)) {
            this.token = 21;
        } else if ("TreeSet".equals(strStringVal)) {
            this.token = 22;
        } else {
            this.token = 18;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:47:0x00f0  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String readString(char[] r16, int r17) {
        /*
            Method dump skipped, instruction units count: 300
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.JSONLexerBase.readString(char[], int):java.lang.String");
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public boolean isBlankInput() {
        int i = 0;
        while (true) {
            char cCharAt = charAt(i);
            if (cCharAt == 26) {
                this.token = 20;
                return true;
            }
            if (!isWhitespace(cCharAt)) {
                return false;
            }
            i++;
        }
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final void skipWhitespace() {
        while (true) {
            char c = this.ch;
            if (c > '/') {
                return;
            }
            if (c == ' ' || c == '\r' || c == '\n' || c == '\t' || c == '\f' || c == '\b') {
                next();
            } else if (c != '/') {
                return;
            } else {
                skipComment();
            }
        }
    }

    private void scanStringSingleQuote() {
        this.np = this.bp;
        this.hasSpecial = false;
        while (true) {
            char next = next();
            if (next == '\'') {
                this.token = 4;
                next();
                return;
            }
            if (next == 26) {
                if (!isEOF()) {
                    putChar(JSONLexer.EOI);
                } else {
                    throw new JSONException("unclosed single-quote string");
                }
            } else if (next == '\\') {
                if (!this.hasSpecial) {
                    this.hasSpecial = true;
                    int i = this.sp;
                    char[] cArr = this.sbuf;
                    if (i > cArr.length) {
                        char[] cArr2 = new char[i * 2];
                        System.arraycopy(cArr, 0, cArr2, 0, cArr.length);
                        this.sbuf = cArr2;
                    }
                    copyTo(this.np + 1, this.sp, this.sbuf);
                }
                char next2 = next();
                if (next2 == '\"') {
                    putChar(Typography.quote);
                } else if (next2 != '\'') {
                    if (next2 != 'F') {
                        if (next2 == '\\') {
                            putChar('\\');
                        } else if (next2 == 'b') {
                            putChar('\b');
                        } else if (next2 != 'f') {
                            if (next2 == 'n') {
                                putChar('\n');
                            } else if (next2 == 'r') {
                                putChar('\r');
                            } else if (next2 != 'x') {
                                switch (next2) {
                                    case '/':
                                        putChar('/');
                                        break;
                                    case ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE /* 48 */:
                                        putChar((char) 0);
                                        break;
                                    case '1':
                                        putChar((char) 1);
                                        break;
                                    case '2':
                                        putChar((char) 2);
                                        break;
                                    case ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_TAG /* 51 */:
                                        putChar((char) 3);
                                        break;
                                    case '4':
                                        putChar((char) 4);
                                        break;
                                    case ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_BASELINE_TO_BOTTOM_OF /* 53 */:
                                        putChar((char) 5);
                                        break;
                                    case '6':
                                        putChar((char) 6);
                                        break;
                                    case '7':
                                        putChar((char) 7);
                                        break;
                                    default:
                                        switch (next2) {
                                            case 't':
                                                putChar('\t');
                                                break;
                                            case 'u':
                                                putChar((char) Integer.parseInt(new String(new char[]{next(), next(), next(), next()}), 16));
                                                break;
                                            case 'v':
                                                putChar((char) 11);
                                                break;
                                            default:
                                                this.ch = next2;
                                                throw new JSONException("unclosed single-quote string");
                                        }
                                        break;
                                }
                            } else {
                                int[] iArr = digits;
                                putChar((char) ((iArr[next()] * 16) + iArr[next()]));
                            }
                        }
                    }
                    putChar('\f');
                } else {
                    putChar('\'');
                }
            } else if (!this.hasSpecial) {
                this.sp++;
            } else {
                int i2 = this.sp;
                char[] cArr3 = this.sbuf;
                if (i2 == cArr3.length) {
                    putChar(next);
                } else {
                    this.sp = i2 + 1;
                    cArr3[i2] = next;
                }
            }
        }
    }

    protected final void putChar(char c) {
        int i = this.sp;
        char[] cArr = this.sbuf;
        if (i == cArr.length) {
            char[] cArr2 = new char[cArr.length * 2];
            System.arraycopy(cArr, 0, cArr2, 0, cArr.length);
            this.sbuf = cArr2;
        }
        char[] cArr3 = this.sbuf;
        int i2 = this.sp;
        this.sp = i2 + 1;
        cArr3[i2] = c;
    }

    public final void scanHex() {
        char next;
        if (this.ch != 'x') {
            throw new JSONException("illegal state. " + this.ch);
        }
        next();
        if (this.ch != '\'') {
            throw new JSONException("illegal state. " + this.ch);
        }
        this.np = this.bp;
        next();
        if (this.ch == '\'') {
            next();
            this.token = 26;
            return;
        }
        while (true) {
            next = next();
            if ((next < '0' || next > '9') && (next < 'A' || next > 'F')) {
                break;
            } else {
                this.sp++;
            }
        }
        if (next == '\'') {
            this.sp++;
            next();
            this.token = 26;
        } else {
            throw new JSONException("illegal state. " + next);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:51:0x00c7  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x00cb  */
    @Override // com.alibaba.fastjson.parser.JSONLexer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void scanNumber() {
        /*
            Method dump skipped, instruction units count: 207
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.JSONLexerBase.scanNumber():void");
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0034  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0075  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0085  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:25:0x005c -> B:11:0x002e). Please report as a decompilation issue!!! */
    @Override // com.alibaba.fastjson.parser.JSONLexer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final long longValue() throws java.lang.NumberFormatException {
        /*
            r13 = this;
            int r0 = r13.np
            r1 = -1
            r2 = 0
            if (r0 != r1) goto L8
            r13.np = r2
        L8:
            int r0 = r13.np
            int r1 = r13.sp
            int r1 = r1 + r0
            char r3 = r13.charAt(r0)
            r4 = 45
            r5 = 1
            if (r3 != r4) goto L1d
            int r0 = r0 + 1
            r2 = -9223372036854775808
            r3 = r2
            r2 = r5
            goto L22
        L1d:
            r3 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
        L22:
            if (r0 >= r1) goto L30
            int r6 = r0 + 1
            char r0 = r13.charAt(r0)
            int r0 = r0 + (-48)
            int r0 = -r0
            long r7 = (long) r0
        L2e:
            r0 = r6
            goto L32
        L30:
            r7 = 0
        L32:
            if (r0 >= r1) goto L73
            int r6 = r0 + 1
            char r0 = r13.charAt(r0)
            r9 = 76
            if (r0 == r9) goto L72
            r9 = 83
            if (r0 == r9) goto L72
            r9 = 66
            if (r0 != r9) goto L47
            goto L72
        L47:
            int r0 = r0 + (-48)
            r9 = -922337203685477580(0xf333333333333334, double:-8.390303882365713E246)
            int r9 = (r7 > r9 ? 1 : (r7 == r9 ? 0 : -1))
            if (r9 < 0) goto L68
            r9 = 10
            long r7 = r7 * r9
            long r9 = (long) r0
            long r11 = r3 + r9
            int r0 = (r7 > r11 ? 1 : (r7 == r11 ? 0 : -1))
            if (r0 < 0) goto L5e
            long r7 = r7 - r9
            goto L2e
        L5e:
            java.lang.NumberFormatException r0 = new java.lang.NumberFormatException
            java.lang.String r1 = r13.numberString()
            r0.<init>(r1)
            throw r0
        L68:
            java.lang.NumberFormatException r0 = new java.lang.NumberFormatException
            java.lang.String r1 = r13.numberString()
            r0.<init>(r1)
            throw r0
        L72:
            r0 = r6
        L73:
            if (r2 == 0) goto L85
            int r1 = r13.np
            int r1 = r1 + r5
            if (r0 <= r1) goto L7b
            return r7
        L7b:
            java.lang.NumberFormatException r0 = new java.lang.NumberFormatException
            java.lang.String r1 = r13.numberString()
            r0.<init>(r1)
            throw r0
        L85:
            long r0 = -r7
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.JSONLexerBase.longValue():long");
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public final Number decimalValue(boolean z) {
        char cCharAt = charAt((this.np + this.sp) - 1);
        try {
            if (cCharAt == 'F') {
                return Float.valueOf(Float.parseFloat(numberString()));
            }
            if (cCharAt == 'D') {
                return Double.valueOf(Double.parseDouble(numberString()));
            }
            if (z) {
                return decimalValue();
            }
            return Double.valueOf(doubleValue());
        } catch (NumberFormatException e) {
            throw new JSONException(e.getMessage() + ", " + info());
        }
    }

    public String[] scanFieldStringArray(char[] cArr, int i, SymbolTable symbolTable) {
        throw new UnsupportedOperationException();
    }

    public boolean matchField2(char[] cArr) {
        throw new UnsupportedOperationException();
    }

    @Override // com.alibaba.fastjson.parser.JSONLexer
    public int getFeatures() {
        return this.features;
    }
}
