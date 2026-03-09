package org.bson.json;

import cn.com.broadlink.blelight.util.EListUtils;
import java.io.Reader;
import kotlin.text.Typography;
import org.bson.BsonRegularExpression;

/* JADX INFO: loaded from: classes4.dex */
class JsonScanner {
    private final JsonBuffer buffer;

    private enum NumberState {
        SAW_LEADING_MINUS,
        SAW_LEADING_ZERO,
        SAW_INTEGER_DIGITS,
        SAW_DECIMAL_POINT,
        SAW_FRACTION_DIGITS,
        SAW_EXPONENT_LETTER,
        SAW_EXPONENT_SIGN,
        SAW_EXPONENT_DIGITS,
        SAW_MINUS_I,
        DONE,
        INVALID
    }

    private enum RegularExpressionState {
        IN_PATTERN,
        IN_ESCAPE_SEQUENCE,
        IN_OPTIONS,
        DONE,
        INVALID
    }

    JsonScanner(JsonBuffer jsonBuffer) {
        this.buffer = jsonBuffer;
    }

    JsonScanner(String str) {
        this(new JsonStringBuffer(str));
    }

    JsonScanner(Reader reader) {
        this(new JsonStreamBuffer(reader));
    }

    public void reset(int i) {
        this.buffer.reset(i);
    }

    public int mark() {
        return this.buffer.mark();
    }

    public void discard(int i) {
        this.buffer.discard(i);
    }

    public JsonToken nextToken() {
        int i = this.buffer.read();
        while (i != -1 && Character.isWhitespace(i)) {
            i = this.buffer.read();
        }
        if (i == -1) {
            return new JsonToken(JsonTokenType.END_OF_FILE, "<eof>");
        }
        if (i != 34) {
            if (i == 44) {
                return new JsonToken(JsonTokenType.COMMA, EListUtils.DEFAULT_JOIN_SEPARATOR);
            }
            if (i == 47) {
                return scanRegularExpression();
            }
            if (i == 58) {
                return new JsonToken(JsonTokenType.COLON, ":");
            }
            if (i == 91) {
                return new JsonToken(JsonTokenType.BEGIN_ARRAY, "[");
            }
            if (i == 93) {
                return new JsonToken(JsonTokenType.END_ARRAY, "]");
            }
            if (i == 123) {
                return new JsonToken(JsonTokenType.BEGIN_OBJECT, "{");
            }
            if (i == 125) {
                return new JsonToken(JsonTokenType.END_OBJECT, "}");
            }
            switch (i) {
                case 39:
                    break;
                case 40:
                    return new JsonToken(JsonTokenType.LEFT_PAREN, "(");
                case 41:
                    return new JsonToken(JsonTokenType.RIGHT_PAREN, ")");
                default:
                    if (i == 45 || Character.isDigit(i)) {
                        return scanNumber((char) i);
                    }
                    if (i == 36 || i == 95 || Character.isLetter(i)) {
                        return scanUnquotedString((char) i);
                    }
                    int position = this.buffer.getPosition();
                    this.buffer.unread(i);
                    throw new JsonParseException("Invalid JSON input. Position: %d. Character: '%c'.", Integer.valueOf(position), Integer.valueOf(i));
            }
        }
        return scanString((char) i);
    }

    private JsonToken scanRegularExpression() {
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        RegularExpressionState regularExpressionState = RegularExpressionState.IN_PATTERN;
        while (true) {
            int i = this.buffer.read();
            int i2 = AnonymousClass1.$SwitchMap$org$bson$json$JsonScanner$RegularExpressionState[regularExpressionState.ordinal()];
            if (i2 != 1) {
                if (i2 == 2) {
                    regularExpressionState = RegularExpressionState.IN_PATTERN;
                } else if (i2 == 3) {
                    if (i == -1 || i == 41 || i == 44 || i == 93) {
                        regularExpressionState = RegularExpressionState.DONE;
                    } else if (i == 105 || i == 109 || i == 115 || i == 120) {
                        regularExpressionState = RegularExpressionState.IN_OPTIONS;
                    } else if (i == 125 || Character.isWhitespace(i)) {
                        regularExpressionState = RegularExpressionState.DONE;
                    } else {
                        regularExpressionState = RegularExpressionState.INVALID;
                    }
                }
            } else if (i == -1) {
                regularExpressionState = RegularExpressionState.INVALID;
            } else if (i == 47) {
                regularExpressionState = RegularExpressionState.IN_OPTIONS;
            } else if (i == 92) {
                regularExpressionState = RegularExpressionState.IN_ESCAPE_SEQUENCE;
            } else {
                regularExpressionState = RegularExpressionState.IN_PATTERN;
            }
            int i3 = AnonymousClass1.$SwitchMap$org$bson$json$JsonScanner$RegularExpressionState[regularExpressionState.ordinal()];
            if (i3 == 4) {
                this.buffer.unread(i);
                return new JsonToken(JsonTokenType.REGULAR_EXPRESSION, new BsonRegularExpression(sb.toString(), sb2.toString()));
            }
            if (i3 == 5) {
                throw new JsonParseException("Invalid JSON regular expression. Position: %d.", Integer.valueOf(this.buffer.getPosition()));
            }
            if (AnonymousClass1.$SwitchMap$org$bson$json$JsonScanner$RegularExpressionState[regularExpressionState.ordinal()] != 3) {
                sb.append((char) i);
            } else if (i != 47) {
                sb2.append((char) i);
            }
        }
    }

    private JsonToken scanUnquotedString(char c) {
        StringBuilder sb = new StringBuilder();
        sb.append(c);
        int i = this.buffer.read();
        while (true) {
            if (i == 36 || i == 95 || Character.isLetterOrDigit(i)) {
                sb.append((char) i);
                i = this.buffer.read();
            } else {
                this.buffer.unread(i);
                return new JsonToken(JsonTokenType.UNQUOTED_STRING, sb.toString());
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:107:0x015b  */
    /* JADX WARN: Removed duplicated region for block: B:109:0x0161  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x00eb  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x00ef  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x012d  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x0133  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private org.bson.json.JsonToken scanNumber(char r18) {
        /*
            Method dump skipped, instruction units count: 515
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bson.json.JsonScanner.scanNumber(char):org.bson.json.JsonToken");
    }

    /* JADX INFO: renamed from: org.bson.json.JsonScanner$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$bson$json$JsonScanner$NumberState;
        static final /* synthetic */ int[] $SwitchMap$org$bson$json$JsonScanner$RegularExpressionState;

        static {
            int[] iArr = new int[NumberState.values().length];
            $SwitchMap$org$bson$json$JsonScanner$NumberState = iArr;
            try {
                iArr[NumberState.SAW_LEADING_MINUS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$bson$json$JsonScanner$NumberState[NumberState.SAW_LEADING_ZERO.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$bson$json$JsonScanner$NumberState[NumberState.SAW_INTEGER_DIGITS.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$org$bson$json$JsonScanner$NumberState[NumberState.SAW_DECIMAL_POINT.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$org$bson$json$JsonScanner$NumberState[NumberState.SAW_FRACTION_DIGITS.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$org$bson$json$JsonScanner$NumberState[NumberState.SAW_EXPONENT_LETTER.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$org$bson$json$JsonScanner$NumberState[NumberState.SAW_EXPONENT_SIGN.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$org$bson$json$JsonScanner$NumberState[NumberState.SAW_EXPONENT_DIGITS.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$org$bson$json$JsonScanner$NumberState[NumberState.SAW_MINUS_I.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$org$bson$json$JsonScanner$NumberState[NumberState.INVALID.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$org$bson$json$JsonScanner$NumberState[NumberState.DONE.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            int[] iArr2 = new int[RegularExpressionState.values().length];
            $SwitchMap$org$bson$json$JsonScanner$RegularExpressionState = iArr2;
            try {
                iArr2[RegularExpressionState.IN_PATTERN.ordinal()] = 1;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$org$bson$json$JsonScanner$RegularExpressionState[RegularExpressionState.IN_ESCAPE_SEQUENCE.ordinal()] = 2;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$org$bson$json$JsonScanner$RegularExpressionState[RegularExpressionState.IN_OPTIONS.ordinal()] = 3;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                $SwitchMap$org$bson$json$JsonScanner$RegularExpressionState[RegularExpressionState.DONE.ordinal()] = 4;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                $SwitchMap$org$bson$json$JsonScanner$RegularExpressionState[RegularExpressionState.INVALID.ordinal()] = 5;
            } catch (NoSuchFieldError unused16) {
            }
        }
    }

    private JsonToken scanString(char c) {
        int i;
        StringBuilder sb = new StringBuilder();
        do {
            i = this.buffer.read();
            if (i == 92) {
                i = this.buffer.read();
                if (i == 34) {
                    sb.append(Typography.quote);
                } else if (i == 39) {
                    sb.append('\'');
                } else if (i == 47) {
                    sb.append('/');
                } else if (i == 92) {
                    sb.append('\\');
                } else if (i == 98) {
                    sb.append('\b');
                } else if (i == 102) {
                    sb.append('\f');
                } else if (i == 110) {
                    sb.append('\n');
                } else if (i == 114) {
                    sb.append('\r');
                } else if (i == 116) {
                    sb.append('\t');
                } else if (i == 117) {
                    int i2 = this.buffer.read();
                    int i3 = this.buffer.read();
                    int i4 = this.buffer.read();
                    int i5 = this.buffer.read();
                    if (i5 != -1) {
                        sb.append((char) Integer.parseInt(new String(new char[]{(char) i2, (char) i3, (char) i4, (char) i5}), 16));
                    }
                } else {
                    throw new JsonParseException("Invalid escape sequence in JSON string '\\%c'.", Integer.valueOf(i));
                }
            } else {
                if (i == c) {
                    return new JsonToken(JsonTokenType.STRING, sb.toString());
                }
                if (i != -1) {
                    sb.append((char) i);
                }
            }
        } while (i != -1);
        throw new JsonParseException("End of file in JSON string.");
    }
}
