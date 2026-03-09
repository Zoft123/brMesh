package com.google.zxing.client.result;

import androidx.constraintlayout.widget.ConstraintLayout;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import java.util.HashMap;
import org.bson.BSON;

/* JADX INFO: loaded from: classes.dex */
public final class ExpandedProductResultParser extends ResultParser {
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    @Override // com.google.zxing.client.result.ResultParser
    public ExpandedProductParsedResult parse(Result result) {
        HashMap map;
        if (result.getBarcodeFormat() != BarcodeFormat.RSS_EXPANDED) {
            return null;
        }
        String massagedText = getMassagedText(result);
        HashMap map2 = new HashMap();
        String str = null;
        String str2 = null;
        String str3 = null;
        String str4 = null;
        String str5 = null;
        String str6 = null;
        String str7 = null;
        String str8 = null;
        String str9 = null;
        String strSubstring = null;
        String strSubstring2 = null;
        String strSubstring3 = null;
        String strSubstring4 = null;
        int i = 0;
        while (i < massagedText.length()) {
            String strFindAIvalue = findAIvalue(i, massagedText);
            if (strFindAIvalue == null) {
                return null;
            }
            byte b = 2;
            int length = i + strFindAIvalue.length() + 2;
            String strFindValue = findValue(length, massagedText);
            int length2 = length + strFindValue.length();
            strFindAIvalue.hashCode();
            switch (strFindAIvalue.hashCode()) {
                case 1536:
                    b = !strFindAIvalue.equals("00") ? (byte) -1 : (byte) 0;
                    break;
                case 1537:
                    b = !strFindAIvalue.equals("01") ? (byte) -1 : (byte) 1;
                    break;
                case 1567:
                    if (!strFindAIvalue.equals("10")) {
                        b = -1;
                    }
                    break;
                case 1568:
                    b = !strFindAIvalue.equals("11") ? (byte) -1 : (byte) 3;
                    break;
                case 1570:
                    b = !strFindAIvalue.equals("13") ? (byte) -1 : (byte) 4;
                    break;
                case 1572:
                    b = !strFindAIvalue.equals("15") ? (byte) -1 : (byte) 5;
                    break;
                case 1574:
                    b = !strFindAIvalue.equals("17") ? (byte) -1 : (byte) 6;
                    break;
                case 1567966:
                    b = !strFindAIvalue.equals("3100") ? (byte) -1 : (byte) 7;
                    break;
                case 1567967:
                    b = !strFindAIvalue.equals("3101") ? (byte) -1 : (byte) 8;
                    break;
                case 1567968:
                    b = !strFindAIvalue.equals("3102") ? (byte) -1 : (byte) 9;
                    break;
                case 1567969:
                    b = !strFindAIvalue.equals("3103") ? (byte) -1 : (byte) 10;
                    break;
                case 1567970:
                    b = !strFindAIvalue.equals("3104") ? (byte) -1 : BSON.REGEX;
                    break;
                case 1567971:
                    b = !strFindAIvalue.equals("3105") ? (byte) -1 : BSON.REF;
                    break;
                case 1567972:
                    b = !strFindAIvalue.equals("3106") ? (byte) -1 : BSON.CODE;
                    break;
                case 1567973:
                    b = !strFindAIvalue.equals("3107") ? (byte) -1 : BSON.SYMBOL;
                    break;
                case 1567974:
                    b = !strFindAIvalue.equals("3108") ? (byte) -1 : BSON.CODE_W_SCOPE;
                    break;
                case 1567975:
                    b = !strFindAIvalue.equals("3109") ? (byte) -1 : BSON.NUMBER_INT;
                    break;
                case 1568927:
                    b = !strFindAIvalue.equals("3200") ? (byte) -1 : BSON.TIMESTAMP;
                    break;
                case 1568928:
                    b = !strFindAIvalue.equals("3201") ? (byte) -1 : BSON.NUMBER_LONG;
                    break;
                case 1568929:
                    b = !strFindAIvalue.equals("3202") ? (byte) -1 : (byte) 19;
                    break;
                case 1568930:
                    b = !strFindAIvalue.equals("3203") ? (byte) -1 : (byte) 20;
                    break;
                case 1568931:
                    b = !strFindAIvalue.equals("3204") ? (byte) -1 : (byte) 21;
                    break;
                case 1568932:
                    b = !strFindAIvalue.equals("3205") ? (byte) -1 : (byte) 22;
                    break;
                case 1568933:
                    b = !strFindAIvalue.equals("3206") ? (byte) -1 : (byte) 23;
                    break;
                case 1568934:
                    b = !strFindAIvalue.equals("3207") ? (byte) -1 : (byte) 24;
                    break;
                case 1568935:
                    b = !strFindAIvalue.equals("3208") ? (byte) -1 : (byte) 25;
                    break;
                case 1568936:
                    b = !strFindAIvalue.equals("3209") ? (byte) -1 : (byte) 26;
                    break;
                case 1575716:
                    b = !strFindAIvalue.equals("3920") ? (byte) -1 : (byte) 27;
                    break;
                case 1575717:
                    b = !strFindAIvalue.equals("3921") ? (byte) -1 : (byte) 28;
                    break;
                case 1575718:
                    b = !strFindAIvalue.equals("3922") ? (byte) -1 : (byte) 29;
                    break;
                case 1575719:
                    b = !strFindAIvalue.equals("3923") ? (byte) -1 : (byte) 30;
                    break;
                case 1575747:
                    b = !strFindAIvalue.equals("3930") ? (byte) -1 : (byte) 31;
                    break;
                case 1575748:
                    b = !strFindAIvalue.equals("3931") ? (byte) -1 : (byte) 32;
                    break;
                case 1575749:
                    b = !strFindAIvalue.equals("3932") ? (byte) -1 : (byte) 33;
                    break;
                case 1575750:
                    b = !strFindAIvalue.equals("3933") ? (byte) -1 : (byte) 34;
                    break;
                default:
                    b = -1;
                    break;
            }
            switch (b) {
                case 0:
                    map = map2;
                    str2 = strFindValue;
                    continue;
                    map2 = map;
                    i = length2;
                    break;
                case 1:
                    map = map2;
                    str = strFindValue;
                    continue;
                    map2 = map;
                    i = length2;
                    break;
                case 2:
                    map = map2;
                    str3 = strFindValue;
                    continue;
                    map2 = map;
                    i = length2;
                    break;
                case 3:
                    map = map2;
                    str4 = strFindValue;
                    continue;
                    map2 = map;
                    i = length2;
                    break;
                case 4:
                    map = map2;
                    str5 = strFindValue;
                    continue;
                    map2 = map;
                    i = length2;
                    break;
                case 5:
                    map = map2;
                    str6 = strFindValue;
                    continue;
                    map2 = map;
                    i = length2;
                    break;
                case 6:
                    map = map2;
                    str7 = strFindValue;
                    continue;
                    map2 = map;
                    i = length2;
                    break;
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                case 15:
                case 16:
                    map = map2;
                    strSubstring = strFindAIvalue.substring(3);
                    str9 = ExpandedProductParsedResult.KILOGRAM;
                    break;
                case 17:
                case 18:
                case 19:
                case 20:
                case 21:
                case 22:
                case 23:
                case 24:
                case 25:
                case 26:
                    map = map2;
                    strSubstring = strFindAIvalue.substring(3);
                    str9 = ExpandedProductParsedResult.POUND;
                    break;
                case 27:
                case 28:
                case ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_HORIZONTAL_BIAS /* 29 */:
                case 30:
                    map = map2;
                    strSubstring3 = strFindAIvalue.substring(3);
                    strSubstring2 = strFindValue;
                    continue;
                    map2 = map;
                    i = length2;
                    break;
                case ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_WIDTH_DEFAULT /* 31 */:
                case 32:
                case 33:
                case 34:
                    if (strFindValue.length() < 4) {
                        return null;
                    }
                    strSubstring2 = strFindValue.substring(3);
                    map = map2;
                    strSubstring4 = strFindValue.substring(0, 3);
                    strSubstring3 = strFindAIvalue.substring(3);
                    continue;
                    map2 = map;
                    i = length2;
                    break;
                default:
                    map2.put(strFindAIvalue, strFindValue);
                    map = map2;
                    continue;
                    map2 = map;
                    i = length2;
                    break;
            }
            str8 = strFindValue;
            map2 = map;
            i = length2;
        }
        return new ExpandedProductParsedResult(massagedText, str, str2, str3, str4, str5, str6, str7, str8, str9, strSubstring, strSubstring2, strSubstring3, strSubstring4, map2);
    }

    private static String findAIvalue(int i, String str) {
        if (str.charAt(i) != '(') {
            return null;
        }
        String strSubstring = str.substring(i + 1);
        StringBuilder sb = new StringBuilder();
        for (int i2 = 0; i2 < strSubstring.length(); i2++) {
            char cCharAt = strSubstring.charAt(i2);
            if (cCharAt == ')') {
                return sb.toString();
            }
            if (cCharAt < '0' || cCharAt > '9') {
                return null;
            }
            sb.append(cCharAt);
        }
        return sb.toString();
    }

    private static String findValue(int i, String str) {
        StringBuilder sb = new StringBuilder();
        String strSubstring = str.substring(i);
        for (int i2 = 0; i2 < strSubstring.length(); i2++) {
            char cCharAt = strSubstring.charAt(i2);
            if (cCharAt == '(') {
                if (findAIvalue(i2, strSubstring) != null) {
                    break;
                }
                sb.append('(');
            } else {
                sb.append(cCharAt);
            }
        }
        return sb.toString();
    }
}
