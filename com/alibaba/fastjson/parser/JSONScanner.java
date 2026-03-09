package com.alibaba.fastjson.parser;

import androidx.camera.video.AudioStats;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.util.ASMUtils;
import com.alibaba.fastjson.util.IOUtils;
import j$.util.DesugarTimeZone;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.TimeZone;
import kotlin.text.Typography;

/* JADX INFO: loaded from: classes.dex */
public final class JSONScanner extends JSONLexerBase {
    private final int len;
    private final String text;

    static boolean checkDate(char c, char c2, char c3, char c4, char c5, char c6, int i, int i2) {
        if (c >= '0' && c <= '9' && c2 >= '0' && c2 <= '9' && c3 >= '0' && c3 <= '9' && c4 >= '0' && c4 <= '9') {
            if (c5 == '0') {
                if (c6 < '1' || c6 > '9') {
                    return false;
                }
            } else if (c5 != '1' || (c6 != '0' && c6 != '1' && c6 != '2')) {
                return false;
            }
            if (i == 48) {
                return i2 >= 49 && i2 <= 57;
            }
            if (i != 49 && i != 50) {
                return i == 51 && (i2 == 48 || i2 == 49);
            }
            if (i2 >= 48 && i2 <= 57) {
                return true;
            }
        }
        return false;
    }

    private boolean checkTime(char c, char c2, char c3, char c4, char c5, char c6) {
        if (c == '0') {
            if (c2 < '0' || c2 > '9') {
                return false;
            }
        } else {
            if (c != '1') {
                if (c == '2' && c2 >= '0' && c2 <= '4') {
                }
                return false;
            }
            if (c2 < '0' || c2 > '9') {
                return false;
            }
        }
        if (c3 < '0' || c3 > '5') {
            if (c3 != '6' || c4 != '0') {
                return false;
            }
        } else if (c4 < '0' || c4 > '9') {
            return false;
        }
        return (c5 < '0' || c5 > '5') ? c5 == '6' && c6 == '0' : c6 >= '0' && c6 <= '9';
    }

    public JSONScanner(String str) {
        this(str, JSON.DEFAULT_PARSER_FEATURE);
    }

    public JSONScanner(String str, int i) {
        super(i);
        this.text = str;
        this.len = str.length();
        this.bp = -1;
        next();
        if (this.ch == 65279) {
            next();
        }
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public final char charAt(int i) {
        return i >= this.len ? JSONLexer.EOI : this.text.charAt(i);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase, com.alibaba.fastjson.parser.JSONLexer
    public final char next() {
        int i = this.bp + 1;
        this.bp = i;
        char cCharAt = i >= this.len ? JSONLexer.EOI : this.text.charAt(i);
        this.ch = cCharAt;
        return cCharAt;
    }

    public JSONScanner(char[] cArr, int i) {
        this(cArr, i, JSON.DEFAULT_PARSER_FEATURE);
    }

    public JSONScanner(char[] cArr, int i, int i2) {
        this(new String(cArr, 0, i), i2);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    protected final void copyTo(int i, int i2, char[] cArr) {
        this.text.getChars(i, i2 + i, cArr, 0);
    }

    static boolean charArrayCompare(String str, int i, char[] cArr) {
        int length = cArr.length;
        if (length + i > str.length()) {
            return false;
        }
        for (int i2 = 0; i2 < length; i2++) {
            if (cArr[i2] != str.charAt(i + i2)) {
                return false;
            }
        }
        return true;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public final boolean charArrayCompare(char[] cArr) {
        return charArrayCompare(this.text, this.bp, cArr);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public final int indexOf(char c, int i) {
        return this.text.indexOf(c, i);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public final String addSymbol(int i, int i2, int i3, SymbolTable symbolTable) {
        return symbolTable.addSymbol(this.text, i, i2, i3);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase, com.alibaba.fastjson.parser.JSONLexer
    public byte[] bytesValue() {
        if (this.token == 26) {
            int i = this.np + 1;
            int i2 = this.sp;
            if (i2 % 2 != 0) {
                throw new JSONException("illegal state. " + i2);
            }
            int i3 = i2 / 2;
            byte[] bArr = new byte[i3];
            for (int i4 = 0; i4 < i3; i4++) {
                int i5 = (i4 * 2) + i;
                char cCharAt = this.text.charAt(i5);
                char cCharAt2 = this.text.charAt(i5 + 1);
                char c = '0';
                int i6 = cCharAt - (cCharAt <= '9' ? '0' : '7');
                if (cCharAt2 > '9') {
                    c = '7';
                }
                bArr[i4] = (byte) ((i6 << 4) | (cCharAt2 - c));
            }
            return bArr;
        }
        return IOUtils.decodeBase64(this.text, this.np + 1, this.sp);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase, com.alibaba.fastjson.parser.JSONLexer
    public final String stringVal() {
        if (!this.hasSpecial) {
            return subString(this.np + 1, this.sp);
        }
        return new String(this.sbuf, 0, this.sp);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public final String subString(int i, int i2) {
        if (ASMUtils.IS_ANDROID) {
            if (i2 < this.sbuf.length) {
                this.text.getChars(i, i + i2, this.sbuf, 0);
                return new String(this.sbuf, 0, i2);
            }
            char[] cArr = new char[i2];
            this.text.getChars(i, i2 + i, cArr, 0);
            return new String(cArr);
        }
        return this.text.substring(i, i2 + i);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public final char[] sub_chars(int i, int i2) {
        if (ASMUtils.IS_ANDROID && i2 < this.sbuf.length) {
            this.text.getChars(i, i2 + i, this.sbuf, 0);
            return this.sbuf;
        }
        char[] cArr = new char[i2];
        this.text.getChars(i, i2 + i, cArr, 0);
        return cArr;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase, com.alibaba.fastjson.parser.JSONLexer
    public final String numberString() {
        char cCharAt = charAt((this.np + this.sp) - 1);
        int i = this.sp;
        if (cCharAt == 'L' || cCharAt == 'S' || cCharAt == 'B' || cCharAt == 'F' || cCharAt == 'D') {
            i--;
        }
        return subString(this.np, i);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase, com.alibaba.fastjson.parser.JSONLexer
    public final BigDecimal decimalValue() {
        char cCharAt = charAt((this.np + this.sp) - 1);
        int i = this.sp;
        if (cCharAt == 'L' || cCharAt == 'S' || cCharAt == 'B' || cCharAt == 'F' || cCharAt == 'D') {
            i--;
        }
        int i2 = this.np;
        if (i < this.sbuf.length) {
            this.text.getChars(i2, i2 + i, this.sbuf, 0);
            return new BigDecimal(this.sbuf, 0, i);
        }
        char[] cArr = new char[i];
        this.text.getChars(i2, i + i2, cArr, 0);
        return new BigDecimal(cArr);
    }

    public boolean scanISO8601DateIfMatch() {
        return scanISO8601DateIfMatch(true);
    }

    public boolean scanISO8601DateIfMatch(boolean z) {
        return scanISO8601DateIfMatch(z, this.len - this.bp);
    }

    /* JADX WARN: Removed duplicated region for block: B:123:0x0246 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:124:0x0247  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean scanISO8601DateIfMatch(boolean r43, int r44) {
        /*
            Method dump skipped, instruction units count: 1833
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.JSONScanner.scanISO8601DateIfMatch(boolean, int):boolean");
    }

    protected void setTime(char c, char c2, char c3, char c4, char c5, char c6) {
        this.calendar.set(11, ((c - '0') * 10) + (c2 - '0'));
        this.calendar.set(12, ((c3 - '0') * 10) + (c4 - '0'));
        this.calendar.set(13, ((c5 - '0') * 10) + (c6 - '0'));
    }

    protected void setTimeZone(char c, char c2, char c3) {
        setTimeZone(c, c2, c3, '0', '0');
    }

    protected void setTimeZone(char c, char c2, char c3, char c4, char c5) {
        int i = ((((c2 - '0') * 10) + (c3 - '0')) * 3600000) + ((((c4 - '0') * 10) + (c5 - '0')) * 60000);
        if (c == '-') {
            i = -i;
        }
        if (this.calendar.getTimeZone().getRawOffset() != i) {
            String[] availableIDs = TimeZone.getAvailableIDs(i);
            if (availableIDs.length > 0) {
                this.calendar.setTimeZone(DesugarTimeZone.getTimeZone(availableIDs[0]));
            }
        }
    }

    private void setCalendar(char c, char c2, char c3, char c4, char c5, char c6, char c7, char c8) {
        this.calendar = Calendar.getInstance(this.timeZone, this.locale);
        this.calendar.set(1, ((c - '0') * 1000) + ((c2 - '0') * 100) + ((c3 - '0') * 10) + (c4 - '0'));
        this.calendar.set(2, (((c5 - '0') * 10) + (c6 - '0')) - 1);
        this.calendar.set(5, ((c7 - '0') * 10) + (c8 - '0'));
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public boolean isEOF() {
        return this.bp == this.len || (this.ch == 26 && this.bp + 1 == this.len);
    }

    /* JADX WARN: Code restructure failed: missing block: B:32:0x0065, code lost:
    
        if (r12 != '.') goto L35;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x0067, code lost:
    
        r14.matchStat = -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x0069, code lost:
    
        return 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x006a, code lost:
    
        if (r4 >= 0) goto L38;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x006c, code lost:
    
        r14.matchStat = -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x006e, code lost:
    
        return 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x006f, code lost:
    
        if (r7 == false) goto L90;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x0071, code lost:
    
        if (r12 == '\"') goto L42;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x0073, code lost:
    
        r14.matchStat = -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x0075, code lost:
    
        return 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x0076, code lost:
    
        r15 = r15 + 2;
        r12 = charAt(r11);
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x007c, code lost:
    
        r11 = r15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x0081, code lost:
    
        if (r12 == ',') goto L88;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x0083, code lost:
    
        if (r12 != '}') goto L48;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x008a, code lost:
    
        if (isWhitespace(r12) == false) goto L87;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x008c, code lost:
    
        r15 = r11 + 1;
        r12 = charAt(r11);
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x0093, code lost:
    
        r14.matchStat = -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x0095, code lost:
    
        return 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x0096, code lost:
    
        r11 = r11 - 1;
        r14.bp = r11;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x009b, code lost:
    
        if (r12 != ',') goto L59;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x009d, code lost:
    
        r15 = r14.bp + 1;
        r14.bp = r15;
        r14.ch = charAt(r15);
        r14.matchStat = 3;
        r14.token = 16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x00ad, code lost:
    
        if (r3 == false) goto L80;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x00b0, code lost:
    
        return -r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x00b1, code lost:
    
        if (r12 != '}') goto L77;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x00b3, code lost:
    
        r14.bp = r11;
        r8 = r14.bp + 1;
        r14.bp = r8;
        r8 = charAt(r8);
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x00be, code lost:
    
        if (r8 != ',') goto L63;
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x00c0, code lost:
    
        r14.token = 16;
        r15 = r14.bp + 1;
        r14.bp = r15;
        r14.ch = charAt(r15);
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x00d0, code lost:
    
        if (r8 != ']') goto L66;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x00d2, code lost:
    
        r14.token = 15;
        r15 = r14.bp + 1;
        r14.bp = r15;
        r14.ch = charAt(r15);
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x00e2, code lost:
    
        if (r8 != '}') goto L68;
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x00e4, code lost:
    
        r14.token = 13;
        r15 = r14.bp + 1;
        r14.bp = r15;
        r14.ch = charAt(r15);
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x00f6, code lost:
    
        if (r8 != 26) goto L72;
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x00f8, code lost:
    
        r14.token = 20;
     */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x00fc, code lost:
    
        r14.matchStat = 4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x0104, code lost:
    
        if (isWhitespace(r8) == false) goto L94;
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x0106, code lost:
    
        r8 = r14.bp + 1;
        r14.bp = r8;
        r8 = charAt(r8);
     */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x0110, code lost:
    
        r14.bp = r1;
        r14.ch = r2;
        r14.matchStat = -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x0116, code lost:
    
        return 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x0117, code lost:
    
        if (r3 == false) goto L80;
     */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x011a, code lost:
    
        return -r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:80:0x011b, code lost:
    
        return r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:83:0x007c, code lost:
    
        r11 = r15;
     */
    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int scanFieldInt(char[] r15) {
        /*
            Method dump skipped, instruction units count: 287
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.JSONScanner.scanFieldInt(char[]):int");
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public String scanFieldString(char[] cArr) {
        this.matchStat = 0;
        int i = this.bp;
        char c = this.ch;
        while (!charArrayCompare(this.text, this.bp, cArr)) {
            if (isWhitespace(this.ch)) {
                next();
            } else {
                this.matchStat = -2;
                return stringDefaultValue();
            }
        }
        int length = this.bp + cArr.length;
        int i2 = length + 1;
        if (charAt(length) != '\"') {
            this.matchStat = -1;
            return stringDefaultValue();
        }
        int iIndexOf = indexOf(Typography.quote, i2);
        if (iIndexOf == -1) {
            throw new JSONException("unclosed str");
        }
        String strSubString = subString(i2, iIndexOf - i2);
        if (strSubString.indexOf(92) != -1) {
            while (true) {
                int i3 = 0;
                for (int i4 = iIndexOf - 1; i4 >= 0 && charAt(i4) == '\\'; i4--) {
                    i3++;
                }
                if (i3 % 2 == 0) {
                    break;
                }
                iIndexOf = indexOf(Typography.quote, iIndexOf + 1);
            }
            int length2 = iIndexOf - ((this.bp + cArr.length) + 1);
            strSubString = readString(sub_chars(this.bp + cArr.length + 1, length2), length2);
        }
        char cCharAt = charAt(iIndexOf + 1);
        while (cCharAt != ',' && cCharAt != '}') {
            if (isWhitespace(cCharAt)) {
                char cCharAt2 = charAt(iIndexOf + 2);
                iIndexOf++;
                cCharAt = cCharAt2;
            } else {
                this.matchStat = -1;
                return stringDefaultValue();
            }
        }
        this.bp = iIndexOf + 1;
        this.ch = cCharAt;
        if (cCharAt == ',') {
            int i5 = this.bp + 1;
            this.bp = i5;
            this.ch = charAt(i5);
            this.matchStat = 3;
            return strSubString;
        }
        int i6 = this.bp + 1;
        this.bp = i6;
        char cCharAt3 = charAt(i6);
        if (cCharAt3 == ',') {
            this.token = 16;
            int i7 = this.bp + 1;
            this.bp = i7;
            this.ch = charAt(i7);
        } else if (cCharAt3 == ']') {
            this.token = 15;
            int i8 = this.bp + 1;
            this.bp = i8;
            this.ch = charAt(i8);
        } else if (cCharAt3 == '}') {
            this.token = 13;
            int i9 = this.bp + 1;
            this.bp = i9;
            this.ch = charAt(i9);
        } else if (cCharAt3 == 26) {
            this.token = 20;
        } else {
            this.bp = i;
            this.ch = c;
            this.matchStat = -1;
            return stringDefaultValue();
        }
        this.matchStat = 4;
        return strSubString;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public Date scanFieldDate(char[] cArr) {
        Date date;
        char cCharAt;
        long j;
        Date date2;
        char cCharAt2;
        boolean z = false;
        this.matchStat = 0;
        int i = this.bp;
        char c = this.ch;
        Date date3 = null;
        if (!charArrayCompare(this.text, this.bp, cArr)) {
            this.matchStat = -2;
            return null;
        }
        int length = this.bp + cArr.length;
        int i2 = length + 1;
        char cCharAt3 = charAt(length);
        boolean z2 = true;
        if (cCharAt3 == '\"') {
            int iIndexOf = indexOf(Typography.quote, i2);
            if (iIndexOf == -1) {
                throw new JSONException("unclosed str");
            }
            this.bp = i2;
            if (scanISO8601DateIfMatch(false, iIndexOf - i2)) {
                date2 = this.calendar.getTime();
                cCharAt2 = charAt(iIndexOf + 1);
                this.bp = i;
                while (cCharAt2 != ',' && cCharAt2 != '}') {
                    if (isWhitespace(cCharAt2)) {
                        int i3 = iIndexOf + 1;
                        char cCharAt4 = charAt(iIndexOf + 2);
                        iIndexOf = i3;
                        cCharAt2 = cCharAt4;
                    } else {
                        this.matchStat = -1;
                        return null;
                    }
                }
                this.bp = iIndexOf + 1;
                this.ch = cCharAt2;
                date = null;
            } else {
                this.bp = i;
                this.matchStat = -1;
                return null;
            }
        } else {
            char c2 = '9';
            if (cCharAt3 != '-' && (cCharAt3 < '0' || cCharAt3 > '9')) {
                this.matchStat = -1;
                return null;
            }
            if (cCharAt3 == '-') {
                cCharAt3 = charAt(i2);
                i2 = length + 2;
                z = true;
            }
            if (cCharAt3 < '0' || cCharAt3 > '9') {
                date = null;
                cCharAt = cCharAt3;
                j = 0;
            } else {
                j = cCharAt3 - '0';
                while (true) {
                    int i4 = i2 + 1;
                    date = date3;
                    cCharAt = charAt(i2);
                    if (cCharAt < '0' || cCharAt > c2) {
                        break;
                    }
                    j = (j * 10) + ((long) (cCharAt - '0'));
                    z2 = z2;
                    i2 = i4;
                    date3 = date;
                    c2 = '9';
                }
                if (cCharAt == ',' || cCharAt == '}') {
                    this.bp = i2;
                }
            }
            if (j < 0) {
                this.matchStat = -1;
                return date;
            }
            if (z) {
                j = -j;
            }
            date2 = new Date(j);
            cCharAt2 = cCharAt;
        }
        if (cCharAt2 == ',') {
            int i5 = this.bp + 1;
            this.bp = i5;
            this.ch = charAt(i5);
            this.matchStat = 3;
            this.token = 16;
            return date2;
        }
        int i6 = this.bp + 1;
        this.bp = i6;
        char cCharAt5 = charAt(i6);
        if (cCharAt5 == ',') {
            this.token = 16;
            int i7 = this.bp + 1;
            this.bp = i7;
            this.ch = charAt(i7);
        } else if (cCharAt5 == ']') {
            this.token = 15;
            int i8 = this.bp + 1;
            this.bp = i8;
            this.ch = charAt(i8);
        } else if (cCharAt5 == '}') {
            this.token = 13;
            int i9 = this.bp + 1;
            this.bp = i9;
            this.ch = charAt(i9);
        } else if (cCharAt5 == 26) {
            this.token = 20;
        } else {
            this.bp = i;
            this.ch = c;
            this.matchStat = -1;
            return date;
        }
        this.matchStat = 4;
        return date2;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public long scanFieldSymbol(char[] cArr) {
        this.matchStat = 0;
        if (!charArrayCompare(this.text, this.bp, cArr)) {
            this.matchStat = -2;
            return 0L;
        }
        int length = this.bp + cArr.length;
        int i = length + 1;
        if (charAt(length) != '\"') {
            this.matchStat = -1;
            return 0L;
        }
        long j = -3750763034362895579L;
        while (true) {
            int i2 = i + 1;
            char cCharAt = charAt(i);
            if (cCharAt == '\"') {
                this.bp = i2;
                char cCharAt2 = charAt(this.bp);
                this.ch = cCharAt2;
                while (cCharAt2 != ',') {
                    if (cCharAt2 == '}') {
                        next();
                        skipWhitespace();
                        char current = getCurrent();
                        if (current == ',') {
                            this.token = 16;
                            int i3 = this.bp + 1;
                            this.bp = i3;
                            this.ch = charAt(i3);
                        } else if (current == ']') {
                            this.token = 15;
                            int i4 = this.bp + 1;
                            this.bp = i4;
                            this.ch = charAt(i4);
                        } else if (current == '}') {
                            this.token = 13;
                            int i5 = this.bp + 1;
                            this.bp = i5;
                            this.ch = charAt(i5);
                        } else if (current == 26) {
                            this.token = 20;
                        } else {
                            this.matchStat = -1;
                            return 0L;
                        }
                        this.matchStat = 4;
                        return j;
                    }
                    if (isWhitespace(cCharAt2)) {
                        int i6 = this.bp + 1;
                        this.bp = i6;
                        cCharAt2 = charAt(i6);
                    } else {
                        this.matchStat = -1;
                        return 0L;
                    }
                }
                int i7 = this.bp + 1;
                this.bp = i7;
                this.ch = charAt(i7);
                this.matchStat = 3;
                return j;
            }
            if (i2 > this.len) {
                this.matchStat = -1;
                return 0L;
            }
            j = (j ^ ((long) cCharAt)) * 1099511628211L;
            i = i2;
        }
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
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

    /* JADX WARN: Code restructure failed: missing block: B:47:0x00e4, code lost:
    
        if (r1 != ']') goto L51;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x00ea, code lost:
    
        if (r3.size() != 0) goto L51;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x00ec, code lost:
    
        r2 = r9 + 1;
        r1 = charAt(r9);
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x00f3, code lost:
    
        r18.matchStat = -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x00f5, code lost:
    
        return null;
     */
    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.util.Collection<java.lang.String> scanFieldStringArray(char[] r19, java.lang.Class<?> r20) {
        /*
            Method dump skipped, instruction units count: 428
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.JSONScanner.scanFieldStringArray(char[], java.lang.Class):java.util.Collection");
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public long scanFieldLong(char[] cArr) {
        int i;
        long j;
        char cCharAt;
        boolean z = false;
        this.matchStat = 0;
        int i2 = this.bp;
        char c = this.ch;
        long j2 = 0;
        if (!charArrayCompare(this.text, this.bp, cArr)) {
            this.matchStat = -2;
            return 0L;
        }
        int length = this.bp + cArr.length;
        int i3 = length + 1;
        char cCharAt2 = charAt(length);
        boolean z2 = cCharAt2 == '\"';
        if (z2) {
            cCharAt2 = charAt(i3);
            i3 = length + 2;
        }
        if (cCharAt2 == '-') {
            cCharAt2 = charAt(i3);
            i3++;
            z = true;
        }
        if (cCharAt2 < '0' || cCharAt2 > '9') {
            this.bp = i2;
            this.ch = c;
            this.matchStat = -1;
            return 0L;
        }
        long j3 = cCharAt2 - '0';
        while (true) {
            i = i3 + 1;
            j = j2;
            cCharAt = charAt(i3);
            if (cCharAt < '0' || cCharAt > '9') {
                break;
            }
            j3 = (j3 * 10) + ((long) (cCharAt - '0'));
            i3 = i;
            j2 = j;
        }
        if (cCharAt == '.') {
            this.matchStat = -1;
            return j;
        }
        if (z2) {
            if (cCharAt != '\"') {
                this.matchStat = -1;
                return j;
            }
            cCharAt = charAt(i);
            i = i3 + 2;
        }
        if (cCharAt == ',' || cCharAt == '}') {
            this.bp = i - 1;
        }
        if (j3 < j && (j3 != Long.MIN_VALUE || !z)) {
            this.bp = i2;
            this.ch = c;
            this.matchStat = -1;
            return j;
        }
        while (true) {
            if (cCharAt == ',') {
                int i4 = this.bp + 1;
                this.bp = i4;
                this.ch = charAt(i4);
                this.matchStat = 3;
                this.token = 16;
                if (z) {
                    return -j3;
                }
            } else if (cCharAt == '}') {
                int i5 = this.bp + 1;
                this.bp = i5;
                char cCharAt3 = charAt(i5);
                while (true) {
                    if (cCharAt3 == ',') {
                        this.token = 16;
                        int i6 = this.bp + 1;
                        this.bp = i6;
                        this.ch = charAt(i6);
                        break;
                    }
                    if (cCharAt3 == ']') {
                        this.token = 15;
                        int i7 = this.bp + 1;
                        this.bp = i7;
                        this.ch = charAt(i7);
                        break;
                    }
                    if (cCharAt3 == '}') {
                        this.token = 13;
                        int i8 = this.bp + 1;
                        this.bp = i8;
                        this.ch = charAt(i8);
                        break;
                    }
                    if (cCharAt3 == 26) {
                        this.token = 20;
                        break;
                    }
                    if (isWhitespace(cCharAt3)) {
                        int i9 = this.bp + 1;
                        this.bp = i9;
                        cCharAt3 = charAt(i9);
                    } else {
                        this.bp = i2;
                        this.ch = c;
                        this.matchStat = -1;
                        return j;
                    }
                }
                this.matchStat = 4;
                if (z) {
                    return -j3;
                }
            } else if (isWhitespace(cCharAt)) {
                this.bp = i;
                char cCharAt4 = charAt(i);
                i++;
                cCharAt = cCharAt4;
            } else {
                this.matchStat = -1;
                return j;
            }
        }
        return j3;
    }

    /* JADX WARN: Removed duplicated region for block: B:82:0x010c  */
    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean scanFieldBoolean(char[] r12) {
        /*
            Method dump skipped, instruction units count: 395
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.JSONScanner.scanFieldBoolean(char[]):boolean");
    }

    /* JADX WARN: Code restructure failed: missing block: B:32:0x007d, code lost:
    
        if (r13 != '.') goto L35;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x007f, code lost:
    
        r16.matchStat = -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x0081, code lost:
    
        return 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x0082, code lost:
    
        if (r7 == false) goto L40;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x0084, code lost:
    
        if (r13 == '\"') goto L39;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x0086, code lost:
    
        r16.matchStat = -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x0088, code lost:
    
        return 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x0089, code lost:
    
        r13 = charAt(r12);
        r12 = r4 + 2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x0090, code lost:
    
        if (r3 >= 0) goto L43;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x0092, code lost:
    
        r16.matchStat = -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x0094, code lost:
    
        return 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x0097, code lost:
    
        if (r13 != r17) goto L49;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x0099, code lost:
    
        r16.bp = r12;
        r16.ch = charAt(r16.bp);
        r16.matchStat = 3;
        r16.token = 16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x00a8, code lost:
    
        if (r6 == false) goto L56;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x00ab, code lost:
    
        return -r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x00b0, code lost:
    
        if (isWhitespace(r13) == false) goto L88;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x00b2, code lost:
    
        r13 = charAt(r12);
        r12 = r12 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x00ba, code lost:
    
        r16.matchStat = -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x00bc, code lost:
    
        if (r6 == false) goto L56;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x00bf, code lost:
    
        return -r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x00c0, code lost:
    
        return r3;
     */
    @Override // com.alibaba.fastjson.parser.JSONLexerBase, com.alibaba.fastjson.parser.JSONLexer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int scanInt(char r17) {
        /*
            Method dump skipped, instruction units count: 305
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.JSONScanner.scanInt(char):int");
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase, com.alibaba.fastjson.parser.JSONLexer
    public double scanDouble(char c) {
        int i;
        char cCharAt;
        boolean z;
        boolean z2;
        long j;
        long j2;
        int i2;
        int i3;
        int i4;
        double d;
        int i5;
        char cCharAt2;
        this.matchStat = 0;
        int i6 = this.bp;
        int i7 = i6 + 1;
        char cCharAt3 = charAt(i6);
        boolean z3 = cCharAt3 == '\"';
        if (z3) {
            cCharAt3 = charAt(i7);
            i7 = i6 + 2;
        }
        boolean z4 = cCharAt3 == '-';
        if (z4) {
            cCharAt3 = charAt(i7);
            i7++;
        }
        if (cCharAt3 < '0' || cCharAt3 > '9') {
            boolean z5 = z3;
            if (cCharAt3 == 'n') {
                int i8 = i7 + 1;
                if (charAt(i7) == 'u') {
                    int i9 = i7 + 2;
                    if (charAt(i8) == 'l') {
                        int i10 = i7 + 3;
                        if (charAt(i9) == 'l') {
                            this.matchStat = 5;
                            int i11 = i7 + 4;
                            char cCharAt4 = charAt(i10);
                            if (z5 && cCharAt4 == '\"') {
                                cCharAt4 = charAt(i11);
                                i11 = i7 + 5;
                            }
                            while (cCharAt4 != ',') {
                                if (cCharAt4 == ']') {
                                    this.bp = i11;
                                    this.ch = charAt(this.bp);
                                    this.matchStat = 5;
                                    this.token = 15;
                                    return AudioStats.AUDIO_AMPLITUDE_NONE;
                                }
                                if (isWhitespace(cCharAt4)) {
                                    int i12 = i11 + 1;
                                    char cCharAt5 = charAt(i11);
                                    i11 = i12;
                                    cCharAt4 = cCharAt5;
                                } else {
                                    this.matchStat = -1;
                                    return AudioStats.AUDIO_AMPLITUDE_NONE;
                                }
                            }
                            this.bp = i11;
                            this.ch = charAt(this.bp);
                            this.matchStat = 5;
                            this.token = 16;
                            return AudioStats.AUDIO_AMPLITUDE_NONE;
                        }
                    }
                }
            }
            this.matchStat = -1;
            return AudioStats.AUDIO_AMPLITUDE_NONE;
        }
        long j3 = cCharAt3 - '0';
        while (true) {
            i = i7 + 1;
            cCharAt = charAt(i7);
            if (cCharAt < '0' || cCharAt > '9') {
                break;
            }
            i7 = i;
            j3 = (j3 * 10) + ((long) (cCharAt - '0'));
        }
        if (cCharAt == '.') {
            int i13 = i7 + 2;
            char cCharAt6 = charAt(i);
            if (cCharAt6 < '0' || cCharAt6 > '9') {
                this.matchStat = -1;
                return AudioStats.AUDIO_AMPLITUDE_NONE;
            }
            boolean z6 = true;
            long j4 = (j3 * 10) + ((long) (cCharAt6 - '0'));
            long j5 = 10;
            while (true) {
                i5 = i13 + 1;
                cCharAt2 = charAt(i13);
                if (cCharAt2 < '0' || cCharAt2 > '9') {
                    break;
                }
                boolean z7 = z6;
                long j6 = (j4 * 10) + ((long) (cCharAt2 - '0'));
                j5 *= 10;
                i13 = i5;
                j4 = j6;
                z6 = z7;
            }
            long j7 = j5;
            i = i5;
            cCharAt = cCharAt2;
            z = z6;
            z2 = z3;
            j = j4;
            j2 = j7;
        } else {
            z = true;
            z2 = z3;
            j = j3;
            j2 = 1;
        }
        boolean z8 = (cCharAt == 'e' || cCharAt == 'E') ? z : false;
        if (z8) {
            int i14 = i + 1;
            char cCharAt7 = charAt(i);
            if (cCharAt7 == '+' || cCharAt7 == '-') {
                i += 2;
                cCharAt = charAt(i14);
            } else {
                i = i14;
                cCharAt = cCharAt7;
            }
            while (cCharAt >= '0' && cCharAt <= '9') {
                char cCharAt8 = charAt(i);
                i++;
                cCharAt = cCharAt8;
            }
        }
        if (!z2) {
            i2 = this.bp;
            int i15 = i;
            i3 = (i - i2) - 1;
            i4 = i15;
        } else {
            if (cCharAt != '\"') {
                this.matchStat = -1;
                return AudioStats.AUDIO_AMPLITUDE_NONE;
            }
            int i16 = i + 1;
            char cCharAt9 = charAt(i);
            i2 = this.bp + 1;
            i3 = (i16 - i2) - 2;
            i4 = i16;
            cCharAt = cCharAt9;
        }
        if (z8 || i3 >= 18) {
            d = Double.parseDouble(subString(i2, i3));
        } else {
            d = j / j2;
            if (z4) {
                d = -d;
            }
        }
        if (cCharAt == c) {
            this.bp = i4;
            this.ch = charAt(this.bp);
            this.matchStat = 3;
            this.token = 16;
            return d;
        }
        this.matchStat = -1;
        return d;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase, com.alibaba.fastjson.parser.JSONLexer
    public long scanLong(char c) {
        int i;
        char cCharAt;
        this.matchStat = 0;
        int i2 = this.bp;
        int i3 = i2 + 1;
        char cCharAt2 = charAt(i2);
        boolean z = cCharAt2 == '\"';
        if (z) {
            cCharAt2 = charAt(i3);
            i3 = i2 + 2;
        }
        boolean z2 = cCharAt2 == '-';
        if (z2) {
            cCharAt2 = charAt(i3);
            i3++;
        }
        if (cCharAt2 >= '0' && cCharAt2 <= '9') {
            long j = cCharAt2 - '0';
            while (true) {
                i = i3 + 1;
                cCharAt = charAt(i3);
                if (cCharAt < '0' || cCharAt > '9') {
                    break;
                }
                j = (j * 10) + ((long) (cCharAt - '0'));
                i3 = i;
            }
            if (cCharAt == '.') {
                this.matchStat = -1;
                return 0L;
            }
            if (z) {
                if (cCharAt != '\"') {
                    this.matchStat = -1;
                    return 0L;
                }
                cCharAt = charAt(i);
                i = i3 + 2;
            }
            if (j < 0 && (j != Long.MIN_VALUE || !z2)) {
                this.matchStat = -1;
                return 0L;
            }
            while (cCharAt != c) {
                if (isWhitespace(cCharAt)) {
                    cCharAt = charAt(i);
                    i++;
                } else {
                    this.matchStat = -1;
                    return j;
                }
            }
            this.bp = i;
            this.ch = charAt(this.bp);
            this.matchStat = 3;
            this.token = 16;
            return z2 ? -j : j;
        }
        if (cCharAt2 == 'n') {
            int i4 = i3 + 1;
            if (charAt(i3) == 'u') {
                int i5 = i3 + 2;
                if (charAt(i4) == 'l') {
                    int i6 = i3 + 3;
                    if (charAt(i5) == 'l') {
                        this.matchStat = 5;
                        int i7 = i3 + 4;
                        char cCharAt3 = charAt(i6);
                        if (z && cCharAt3 == '\"') {
                            cCharAt3 = charAt(i7);
                            i7 = i3 + 5;
                        }
                        while (cCharAt3 != ',') {
                            if (cCharAt3 == ']') {
                                this.bp = i7;
                                this.ch = charAt(this.bp);
                                this.matchStat = 5;
                                this.token = 15;
                                return 0L;
                            }
                            if (isWhitespace(cCharAt3)) {
                                char cCharAt4 = charAt(i7);
                                i7++;
                                cCharAt3 = cCharAt4;
                            } else {
                                this.matchStat = -1;
                                return 0L;
                            }
                        }
                        this.bp = i7;
                        this.ch = charAt(this.bp);
                        this.matchStat = 5;
                        this.token = 16;
                        return 0L;
                    }
                }
            }
        }
        this.matchStat = -1;
        return 0L;
    }

    /* JADX WARN: Removed duplicated region for block: B:61:0x00f8  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x0108  */
    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.util.Date scanDate(char r22) {
        /*
            Method dump skipped, instruction units count: 353
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.JSONScanner.scanDate(char):java.util.Date");
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    protected final void arrayCopy(int i, char[] cArr, int i2, int i3) {
        this.text.getChars(i, i3 + i, cArr, i2);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase, com.alibaba.fastjson.parser.JSONLexer
    public String info() {
        StringBuilder sb = new StringBuilder();
        int i = 1;
        int i2 = 1;
        int i3 = 0;
        while (i3 < this.bp) {
            if (this.text.charAt(i3) == '\n') {
                i++;
                i2 = 1;
            }
            i3++;
            i2++;
        }
        sb.append("pos ");
        sb.append(this.bp);
        sb.append(", line ");
        sb.append(i);
        sb.append(", column ");
        sb.append(i2);
        if (this.text.length() < 65535) {
            sb.append(this.text);
        } else {
            sb.append(this.text.substring(0, 65535));
        }
        return sb.toString();
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public String[] scanFieldStringArray(char[] cArr, int i, SymbolTable symbolTable) {
        int i2;
        char cCharAt;
        int i3 = this.bp;
        char c = this.ch;
        while (isWhitespace(this.ch)) {
            next();
        }
        if (cArr != null) {
            this.matchStat = 0;
            if (!charArrayCompare(cArr)) {
                this.matchStat = -2;
                return null;
            }
            int length = this.bp + cArr.length;
            int i4 = length + 1;
            char cCharAt2 = this.text.charAt(length);
            while (isWhitespace(cCharAt2)) {
                cCharAt2 = this.text.charAt(i4);
                i4++;
            }
            if (cCharAt2 == ':') {
                i2 = i4 + 1;
                cCharAt = this.text.charAt(i4);
                while (isWhitespace(cCharAt)) {
                    cCharAt = this.text.charAt(i2);
                    i2++;
                }
            } else {
                this.matchStat = -1;
                return null;
            }
        } else {
            i2 = this.bp + 1;
            cCharAt = this.ch;
        }
        if (cCharAt == '[') {
            this.bp = i2;
            this.ch = this.text.charAt(this.bp);
            String[] strArr = i >= 0 ? new String[i] : new String[4];
            int i5 = 0;
            while (true) {
                if (isWhitespace(this.ch)) {
                    next();
                } else {
                    if (this.ch != '\"') {
                        this.bp = i3;
                        this.ch = c;
                        this.matchStat = -1;
                        return null;
                    }
                    String strScanSymbol = scanSymbol(symbolTable, Typography.quote);
                    if (i5 == strArr.length) {
                        String[] strArr2 = new String[strArr.length + (strArr.length >> 1) + 1];
                        System.arraycopy(strArr, 0, strArr2, 0, strArr.length);
                        strArr = strArr2;
                    }
                    int i6 = i5 + 1;
                    strArr[i5] = strScanSymbol;
                    while (isWhitespace(this.ch)) {
                        next();
                    }
                    if (this.ch == ',') {
                        next();
                        i5 = i6;
                    } else {
                        if (strArr.length != i6) {
                            String[] strArr3 = new String[i6];
                            System.arraycopy(strArr, 0, strArr3, 0, i6);
                            strArr = strArr3;
                        }
                        while (isWhitespace(this.ch)) {
                            next();
                        }
                        if (this.ch == ']') {
                            next();
                            return strArr;
                        }
                        this.bp = i3;
                        this.ch = c;
                        this.matchStat = -1;
                        return null;
                    }
                }
            }
        } else {
            if (cCharAt == 'n' && this.text.startsWith("ull", this.bp + 1)) {
                this.bp += 4;
                this.ch = this.text.charAt(this.bp);
                return null;
            }
            this.matchStat = -1;
            return null;
        }
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public boolean matchField2(char[] cArr) {
        while (isWhitespace(this.ch)) {
            next();
        }
        if (!charArrayCompare(cArr)) {
            this.matchStat = -2;
            return false;
        }
        int length = this.bp + cArr.length;
        int i = length + 1;
        char cCharAt = this.text.charAt(length);
        while (isWhitespace(cCharAt)) {
            cCharAt = this.text.charAt(i);
            i++;
        }
        if (cCharAt == ':') {
            this.bp = i;
            this.ch = charAt(this.bp);
            return true;
        }
        this.matchStat = -2;
        return false;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public final void skipObject() {
        skipObject(false);
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public final void skipObject(boolean z) {
        int i = this.bp;
        boolean z2 = false;
        int i2 = 0;
        while (i < this.text.length()) {
            char cCharAt = this.text.charAt(i);
            if (cCharAt == '\\') {
                if (i >= this.len - 1) {
                    this.ch = cCharAt;
                    this.bp = i;
                    throw new JSONException("illegal str, " + info());
                }
                i++;
            } else if (cCharAt == '\"') {
                z2 = !z2;
            } else if (cCharAt == '{') {
                if (!z2) {
                    i2++;
                }
            } else if (cCharAt == '}' && !z2 && i2 - 1 == -1) {
                this.bp = i + 1;
                int i3 = this.bp;
                int length = this.text.length();
                char cCharAt2 = JSONLexer.EOI;
                if (i3 == length) {
                    this.ch = JSONLexer.EOI;
                    this.token = 20;
                    return;
                }
                this.ch = this.text.charAt(this.bp);
                if (this.ch == ',') {
                    this.token = 16;
                    int i4 = this.bp + 1;
                    this.bp = i4;
                    if (i4 < this.text.length()) {
                        cCharAt2 = this.text.charAt(i4);
                    }
                    this.ch = cCharAt2;
                    return;
                }
                if (this.ch == '}') {
                    this.token = 13;
                    next();
                    return;
                } else if (this.ch == ']') {
                    this.token = 15;
                    next();
                    return;
                } else {
                    nextToken(16);
                    return;
                }
            }
            i++;
        }
        if (i != this.text.length()) {
            return;
        }
        throw new JSONException("illegal str, " + info());
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public final void skipArray() {
        skipArray(false);
    }

    public final void skipArray(boolean z) {
        int i = this.bp;
        boolean z2 = false;
        int i2 = 0;
        while (i < this.text.length()) {
            char cCharAt = this.text.charAt(i);
            if (cCharAt == '\\') {
                if (i >= this.len - 1) {
                    this.ch = cCharAt;
                    this.bp = i;
                    throw new JSONException("illegal str, " + info());
                }
                i++;
            } else if (cCharAt == '\"') {
                z2 = !z2;
            } else if (cCharAt != '[') {
                char cCharAt2 = JSONLexer.EOI;
                if (cCharAt == '{' && z) {
                    int i3 = this.bp + 1;
                    this.bp = i3;
                    if (i3 < this.text.length()) {
                        cCharAt2 = this.text.charAt(i3);
                    }
                    this.ch = cCharAt2;
                    skipObject(z);
                } else if (cCharAt == ']' && !z2 && i2 - 1 == -1) {
                    this.bp = i + 1;
                    if (this.bp == this.text.length()) {
                        this.ch = JSONLexer.EOI;
                        this.token = 20;
                        return;
                    } else {
                        this.ch = this.text.charAt(this.bp);
                        nextToken(16);
                        return;
                    }
                }
            } else if (!z2) {
                i2++;
            }
            i++;
        }
        if (i != this.text.length()) {
            return;
        }
        throw new JSONException("illegal str, " + info());
    }

    public final void skipString() {
        if (this.ch == '\"') {
            int i = this.bp;
            while (true) {
                i++;
                if (i < this.text.length()) {
                    char cCharAt = this.text.charAt(i);
                    if (cCharAt == '\\') {
                        if (i < this.len - 1) {
                            i++;
                        }
                    } else if (cCharAt == '\"') {
                        String str = this.text;
                        int i2 = i + 1;
                        this.bp = i2;
                        this.ch = str.charAt(i2);
                        return;
                    }
                } else {
                    throw new JSONException("unclosed str");
                }
            }
        } else {
            throw new UnsupportedOperationException();
        }
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public boolean seekArrayToItem(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("index must > 0, but " + i);
        }
        if (this.token == 20) {
            return false;
        }
        if (this.token != 14) {
            throw new UnsupportedOperationException();
        }
        for (int i2 = 0; i2 < i; i2++) {
            skipWhitespace();
            if (this.ch == '\"' || this.ch == '\'') {
                skipString();
                if (this.ch == ',') {
                    next();
                } else {
                    if (this.ch == ']') {
                        next();
                        nextToken(16);
                        return false;
                    }
                    throw new JSONException("illegal json.");
                }
            } else {
                if (this.ch == '{') {
                    next();
                    this.token = 12;
                    skipObject(false);
                } else if (this.ch == '[') {
                    next();
                    this.token = 14;
                    skipArray(false);
                } else {
                    for (int i3 = this.bp + 1; i3 < this.text.length(); i3++) {
                        char cCharAt = this.text.charAt(i3);
                        if (cCharAt == ',') {
                            this.bp = i3 + 1;
                            this.ch = charAt(this.bp);
                        } else {
                            if (cCharAt == ']') {
                                this.bp = i3 + 1;
                                this.ch = charAt(this.bp);
                                nextToken();
                                return false;
                            }
                        }
                    }
                    throw new JSONException("illegal json.");
                }
                if (this.token != 16) {
                    if (this.token == 15) {
                        return false;
                    }
                    throw new UnsupportedOperationException();
                }
            }
        }
        nextToken();
        return true;
    }

    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    public int seekObjectToField(long j, boolean z) {
        int i;
        int i2 = -1;
        if (this.token == 20) {
            return -1;
        }
        if (this.token != 13) {
            int i3 = 15;
            if (this.token != 15) {
                int i4 = 16;
                if (this.token != 12 && this.token != 16) {
                    throw new UnsupportedOperationException(JSONToken.name(this.token));
                }
                while (this.ch != '}') {
                    char c = this.ch;
                    char cCharAt = JSONLexer.EOI;
                    if (c == 26) {
                        return i2;
                    }
                    if (this.ch != '\"') {
                        skipWhitespace();
                    }
                    if (this.ch == '\"') {
                        int i5 = this.bp + 1;
                        long j2 = -3750763034362895579L;
                        while (true) {
                            if (i5 >= this.text.length()) {
                                break;
                            }
                            char cCharAt2 = this.text.charAt(i5);
                            if (cCharAt2 == '\\') {
                                i5++;
                                if (i5 == this.text.length()) {
                                    throw new JSONException("unclosed str, " + info());
                                }
                                cCharAt2 = this.text.charAt(i5);
                            }
                            if (cCharAt2 == '\"') {
                                this.bp = i5 + 1;
                                this.ch = this.bp >= this.text.length() ? (char) 26 : this.text.charAt(this.bp);
                            } else {
                                j2 = (j2 ^ ((long) cCharAt2)) * 1099511628211L;
                                i5++;
                            }
                        }
                        int i6 = i2;
                        if (j2 == j) {
                            if (this.ch != ':') {
                                skipWhitespace();
                            }
                            if (this.ch != ':') {
                                return 3;
                            }
                            int i7 = this.bp + 1;
                            this.bp = i7;
                            this.ch = i7 >= this.text.length() ? (char) 26 : this.text.charAt(i7);
                            if (this.ch == ',') {
                                int i8 = this.bp + 1;
                                this.bp = i8;
                                if (i8 < this.text.length()) {
                                    cCharAt = this.text.charAt(i8);
                                }
                                this.ch = cCharAt;
                                this.token = i4;
                                return 3;
                            }
                            if (this.ch == ']') {
                                int i9 = this.bp + 1;
                                this.bp = i9;
                                if (i9 < this.text.length()) {
                                    cCharAt = this.text.charAt(i9);
                                }
                                this.ch = cCharAt;
                                this.token = i3;
                                return 3;
                            }
                            if (this.ch == '}') {
                                int i10 = this.bp + 1;
                                this.bp = i10;
                                if (i10 < this.text.length()) {
                                    cCharAt = this.text.charAt(i10);
                                }
                                this.ch = cCharAt;
                                this.token = 13;
                                return 3;
                            }
                            if (this.ch >= '0' && this.ch <= '9') {
                                this.sp = 0;
                                this.pos = this.bp;
                                scanNumber();
                                return 3;
                            }
                            nextToken(2);
                            return 3;
                        }
                        if (this.ch != ':') {
                            skipWhitespace();
                        }
                        if (this.ch == ':') {
                            int i11 = this.bp + 1;
                            this.bp = i11;
                            this.ch = i11 >= this.text.length() ? (char) 26 : this.text.charAt(i11);
                            if (this.ch != '\"') {
                                i = 1;
                                if (this.ch != '\'' && this.ch != '{' && this.ch != '[' && this.ch != '0' && this.ch != '1' && this.ch != '2' && this.ch != '3' && this.ch != '4' && this.ch != '5' && this.ch != '6' && this.ch != '7' && this.ch != '8' && this.ch != '9' && this.ch != '+' && this.ch != '-') {
                                    skipWhitespace();
                                }
                            } else {
                                i = 1;
                            }
                            if (this.ch == '-' || this.ch == '+' || (this.ch >= '0' && this.ch <= '9')) {
                                next();
                                while (this.ch >= '0' && this.ch <= '9') {
                                    next();
                                }
                                if (this.ch == '.') {
                                    next();
                                    while (this.ch >= '0' && this.ch <= '9') {
                                        next();
                                    }
                                }
                                if (this.ch == 'E' || this.ch == 'e') {
                                    next();
                                    if (this.ch == '-' || this.ch == '+') {
                                        next();
                                    }
                                    while (this.ch >= '0' && this.ch <= '9') {
                                        next();
                                    }
                                }
                                if (this.ch != ',') {
                                    skipWhitespace();
                                }
                                if (this.ch == ',') {
                                    next();
                                }
                            } else if (this.ch == '\"') {
                                skipString();
                                if (this.ch != ',' && this.ch != '}') {
                                    skipWhitespace();
                                }
                                if (this.ch == ',') {
                                    next();
                                }
                            } else if (this.ch == 't') {
                                next();
                                if (this.ch == 'r') {
                                    next();
                                    if (this.ch == 'u') {
                                        next();
                                        if (this.ch == 'e') {
                                            next();
                                        }
                                    }
                                }
                                if (this.ch != ',' && this.ch != '}') {
                                    skipWhitespace();
                                }
                                if (this.ch == ',') {
                                    next();
                                }
                            } else if (this.ch == 'n') {
                                next();
                                if (this.ch == 'u') {
                                    next();
                                    if (this.ch == 'l') {
                                        next();
                                        if (this.ch == 'l') {
                                            next();
                                        }
                                    }
                                }
                                if (this.ch != ',' && this.ch != '}') {
                                    skipWhitespace();
                                }
                                if (this.ch == ',') {
                                    next();
                                }
                            } else if (this.ch == 'f') {
                                next();
                                if (this.ch == 'a') {
                                    next();
                                    if (this.ch == 'l') {
                                        next();
                                        if (this.ch == 's') {
                                            next();
                                            if (this.ch == 'e') {
                                                next();
                                            }
                                        }
                                    }
                                }
                                if (this.ch != ',' && this.ch != '}') {
                                    skipWhitespace();
                                }
                                if (this.ch == ',') {
                                    next();
                                }
                            } else if (this.ch == '{') {
                                int i12 = this.bp + 1;
                                this.bp = i12;
                                this.ch = i12 >= this.text.length() ? JSONLexer.EOI : this.text.charAt(i12);
                                if (z) {
                                    this.token = 12;
                                    return i;
                                }
                                skipObject(false);
                                if (this.token == 13) {
                                    return i6;
                                }
                            } else if (this.ch == '[') {
                                next();
                                if (z) {
                                    this.token = 14;
                                    return 2;
                                }
                                skipArray(false);
                                if (this.token == 13) {
                                    return i6;
                                }
                            } else {
                                throw new UnsupportedOperationException();
                            }
                            i2 = i6;
                            i3 = 15;
                            i4 = 16;
                        } else {
                            throw new JSONException("illegal json, " + info());
                        }
                    } else {
                        throw new UnsupportedOperationException();
                    }
                }
                next();
                nextToken();
                return i2;
            }
        }
        nextToken();
        return -1;
    }

    /* JADX WARN: Code restructure failed: missing block: B:101:0x0194, code lost:
    
        if (r14.ch == '{') goto L129;
     */
    /* JADX WARN: Code restructure failed: missing block: B:103:0x0198, code lost:
    
        if (r14.ch == '[') goto L129;
     */
    /* JADX WARN: Code restructure failed: missing block: B:105:0x019c, code lost:
    
        if (r14.ch == '0') goto L129;
     */
    /* JADX WARN: Code restructure failed: missing block: B:107:0x01a2, code lost:
    
        if (r14.ch == '1') goto L129;
     */
    /* JADX WARN: Code restructure failed: missing block: B:109:0x01a8, code lost:
    
        if (r14.ch == '2') goto L129;
     */
    /* JADX WARN: Code restructure failed: missing block: B:111:0x01ae, code lost:
    
        if (r14.ch == '3') goto L129;
     */
    /* JADX WARN: Code restructure failed: missing block: B:113:0x01b4, code lost:
    
        if (r14.ch == '4') goto L129;
     */
    /* JADX WARN: Code restructure failed: missing block: B:115:0x01ba, code lost:
    
        if (r14.ch == '5') goto L129;
     */
    /* JADX WARN: Code restructure failed: missing block: B:117:0x01c0, code lost:
    
        if (r14.ch == '6') goto L129;
     */
    /* JADX WARN: Code restructure failed: missing block: B:119:0x01c6, code lost:
    
        if (r14.ch == '7') goto L129;
     */
    /* JADX WARN: Code restructure failed: missing block: B:121:0x01cc, code lost:
    
        if (r14.ch == '8') goto L129;
     */
    /* JADX WARN: Code restructure failed: missing block: B:123:0x01d0, code lost:
    
        if (r14.ch == '9') goto L129;
     */
    /* JADX WARN: Code restructure failed: missing block: B:125:0x01d4, code lost:
    
        if (r14.ch == '+') goto L129;
     */
    /* JADX WARN: Code restructure failed: missing block: B:127:0x01d8, code lost:
    
        if (r14.ch == '-') goto L129;
     */
    /* JADX WARN: Code restructure failed: missing block: B:128:0x01da, code lost:
    
        skipWhitespace();
     */
    /* JADX WARN: Code restructure failed: missing block: B:130:0x01df, code lost:
    
        if (r14.ch == '-') goto L198;
     */
    /* JADX WARN: Code restructure failed: missing block: B:132:0x01e3, code lost:
    
        if (r14.ch == '+') goto L199;
     */
    /* JADX WARN: Code restructure failed: missing block: B:134:0x01e7, code lost:
    
        if (r14.ch < '0') goto L209;
     */
    /* JADX WARN: Code restructure failed: missing block: B:136:0x01eb, code lost:
    
        if (r14.ch > '9') goto L210;
     */
    /* JADX WARN: Code restructure failed: missing block: B:139:0x01f0, code lost:
    
        if (r14.ch != '\"') goto L202;
     */
    /* JADX WARN: Code restructure failed: missing block: B:140:0x01f2, code lost:
    
        skipString();
     */
    /* JADX WARN: Code restructure failed: missing block: B:141:0x01f7, code lost:
    
        if (r14.ch == ',') goto L145;
     */
    /* JADX WARN: Code restructure failed: missing block: B:143:0x01fb, code lost:
    
        if (r14.ch == '}') goto L145;
     */
    /* JADX WARN: Code restructure failed: missing block: B:144:0x01fd, code lost:
    
        skipWhitespace();
     */
    /* JADX WARN: Code restructure failed: missing block: B:146:0x0202, code lost:
    
        if (r14.ch != ',') goto L221;
     */
    /* JADX WARN: Code restructure failed: missing block: B:147:0x0204, code lost:
    
        next();
     */
    /* JADX WARN: Code restructure failed: missing block: B:149:0x020b, code lost:
    
        if (r14.ch != '{') goto L213;
     */
    /* JADX WARN: Code restructure failed: missing block: B:150:0x020d, code lost:
    
        r1 = r14.bp + 1;
        r14.bp = r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:151:0x0219, code lost:
    
        if (r1 < r14.text.length()) goto L153;
     */
    /* JADX WARN: Code restructure failed: missing block: B:153:0x021c, code lost:
    
        r4 = r14.text.charAt(r1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:154:0x0222, code lost:
    
        r14.ch = r4;
        skipObject(false);
     */
    /* JADX WARN: Code restructure failed: missing block: B:156:0x022b, code lost:
    
        if (r14.ch != '[') goto L215;
     */
    /* JADX WARN: Code restructure failed: missing block: B:157:0x022d, code lost:
    
        next();
        skipArray(false);
     */
    /* JADX WARN: Code restructure failed: missing block: B:159:0x023a, code lost:
    
        throw new java.lang.UnsupportedOperationException();
     */
    /* JADX WARN: Code restructure failed: missing block: B:160:0x023b, code lost:
    
        next();
     */
    /* JADX WARN: Code restructure failed: missing block: B:162:0x0240, code lost:
    
        if (r14.ch < '0') goto L228;
     */
    /* JADX WARN: Code restructure failed: missing block: B:164:0x0244, code lost:
    
        if (r14.ch > '9') goto L229;
     */
    /* JADX WARN: Code restructure failed: missing block: B:165:0x0246, code lost:
    
        next();
     */
    /* JADX WARN: Code restructure failed: missing block: B:167:0x024e, code lost:
    
        if (r14.ch != '.') goto L174;
     */
    /* JADX WARN: Code restructure failed: missing block: B:168:0x0250, code lost:
    
        next();
     */
    /* JADX WARN: Code restructure failed: missing block: B:170:0x0255, code lost:
    
        if (r14.ch < '0') goto L230;
     */
    /* JADX WARN: Code restructure failed: missing block: B:172:0x0259, code lost:
    
        if (r14.ch > '9') goto L231;
     */
    /* JADX WARN: Code restructure failed: missing block: B:173:0x025b, code lost:
    
        next();
     */
    /* JADX WARN: Code restructure failed: missing block: B:175:0x0263, code lost:
    
        if (r14.ch == 'E') goto L178;
     */
    /* JADX WARN: Code restructure failed: missing block: B:177:0x0269, code lost:
    
        if (r14.ch != 'e') goto L188;
     */
    /* JADX WARN: Code restructure failed: missing block: B:178:0x026b, code lost:
    
        next();
     */
    /* JADX WARN: Code restructure failed: missing block: B:179:0x0270, code lost:
    
        if (r14.ch == '-') goto L182;
     */
    /* JADX WARN: Code restructure failed: missing block: B:181:0x0274, code lost:
    
        if (r14.ch != '+') goto L234;
     */
    /* JADX WARN: Code restructure failed: missing block: B:182:0x0276, code lost:
    
        next();
     */
    /* JADX WARN: Code restructure failed: missing block: B:184:0x027b, code lost:
    
        if (r14.ch < '0') goto L232;
     */
    /* JADX WARN: Code restructure failed: missing block: B:186:0x027f, code lost:
    
        if (r14.ch > '9') goto L233;
     */
    /* JADX WARN: Code restructure failed: missing block: B:187:0x0281, code lost:
    
        next();
     */
    /* JADX WARN: Code restructure failed: missing block: B:189:0x0287, code lost:
    
        if (r14.ch == ',') goto L191;
     */
    /* JADX WARN: Code restructure failed: missing block: B:190:0x0289, code lost:
    
        skipWhitespace();
     */
    /* JADX WARN: Code restructure failed: missing block: B:192:0x028e, code lost:
    
        if (r14.ch != ',') goto L217;
     */
    /* JADX WARN: Code restructure failed: missing block: B:193:0x0290, code lost:
    
        next();
     */
    /* JADX WARN: Code restructure failed: missing block: B:195:0x02ac, code lost:
    
        throw new com.alibaba.fastjson.JSONException("illegal json, " + info());
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x00a6, code lost:
    
        r8 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x00a9, code lost:
    
        if (r8 >= r15.length) goto L226;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x00af, code lost:
    
        if (r6 != r15[r8]) goto L45;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x00b2, code lost:
    
        r8 = r8 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x00b5, code lost:
    
        r8 = -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x00be, code lost:
    
        if (r8 == (-1)) goto L87;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x00c2, code lost:
    
        if (r14.ch == ':') goto L52;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x00c4, code lost:
    
        skipWhitespace();
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x00c9, code lost:
    
        if (r14.ch != ':') goto L85;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x00cb, code lost:
    
        r15 = r14.bp + 1;
        r14.bp = r15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x00d7, code lost:
    
        if (r15 < r14.text.length()) goto L57;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x00d9, code lost:
    
        r15 = 26;
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x00db, code lost:
    
        r15 = r14.text.charAt(r15);
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x00e1, code lost:
    
        r14.ch = r15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x00e5, code lost:
    
        if (r14.ch != ',') goto L65;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x00e7, code lost:
    
        r15 = r14.bp + 1;
        r14.bp = r15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x00f3, code lost:
    
        if (r15 < r14.text.length()) goto L63;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x00f6, code lost:
    
        r4 = r14.text.charAt(r15);
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x00fc, code lost:
    
        r14.ch = r4;
        r14.token = 16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x0105, code lost:
    
        if (r14.ch != ']') goto L72;
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x0107, code lost:
    
        r15 = r14.bp + 1;
        r14.bp = r15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x0113, code lost:
    
        if (r15 < r14.text.length()) goto L70;
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x0116, code lost:
    
        r4 = r14.text.charAt(r15);
     */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x011c, code lost:
    
        r14.ch = r4;
        r14.token = 15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x0125, code lost:
    
        if (r14.ch != '}') goto L79;
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x0127, code lost:
    
        r15 = r14.bp + 1;
        r14.bp = r15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x0133, code lost:
    
        if (r15 < r14.text.length()) goto L77;
     */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x0136, code lost:
    
        r4 = r14.text.charAt(r15);
     */
    /* JADX WARN: Code restructure failed: missing block: B:78:0x013c, code lost:
    
        r14.ch = r4;
        r14.token = 13;
     */
    /* JADX WARN: Code restructure failed: missing block: B:80:0x0145, code lost:
    
        if (r14.ch < '0') goto L84;
     */
    /* JADX WARN: Code restructure failed: missing block: B:82:0x0149, code lost:
    
        if (r14.ch > '9') goto L84;
     */
    /* JADX WARN: Code restructure failed: missing block: B:83:0x014b, code lost:
    
        r14.sp = 0;
        r14.pos = r14.bp;
        scanNumber();
     */
    /* JADX WARN: Code restructure failed: missing block: B:84:0x0155, code lost:
    
        nextToken(2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:85:0x0159, code lost:
    
        r14.matchStat = 3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:86:0x015c, code lost:
    
        return r8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:88:0x015f, code lost:
    
        if (r14.ch == ':') goto L90;
     */
    /* JADX WARN: Code restructure failed: missing block: B:89:0x0161, code lost:
    
        skipWhitespace();
     */
    /* JADX WARN: Code restructure failed: missing block: B:91:0x0166, code lost:
    
        if (r14.ch != ':') goto L208;
     */
    /* JADX WARN: Code restructure failed: missing block: B:92:0x0168, code lost:
    
        r3 = r14.bp + 1;
        r14.bp = r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:93:0x0174, code lost:
    
        if (r3 < r14.text.length()) goto L95;
     */
    /* JADX WARN: Code restructure failed: missing block: B:94:0x0176, code lost:
    
        r3 = 26;
     */
    /* JADX WARN: Code restructure failed: missing block: B:95:0x0178, code lost:
    
        r3 = r14.text.charAt(r3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:96:0x017e, code lost:
    
        r14.ch = r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:97:0x018a, code lost:
    
        if (r14.ch == '\"') goto L129;
     */
    /* JADX WARN: Code restructure failed: missing block: B:99:0x0190, code lost:
    
        if (r14.ch == '\'') goto L129;
     */
    @Override // com.alibaba.fastjson.parser.JSONLexerBase
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int seekObjectToField(long[] r15) {
        /*
            Method dump skipped, instruction units count: 691
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.JSONScanner.seekObjectToField(long[]):int");
    }
}
