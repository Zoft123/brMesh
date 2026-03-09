package com.king.logx.util;

import androidx.constraintlayout.widget.ConstraintLayout;
import com.king.logx.LogX;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.io.CloseableKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: FormatUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\f\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001a\u0010\u0012\u001a\u00020\t2\u0006\u0010\u0013\u001a\u00020\t2\b\b\u0002\u0010\u0014\u001a\u00020\u0004H\u0007J\u001a\u0010\u0015\u001a\u00020\t2\u0006\u0010\u0016\u001a\u00020\t2\b\b\u0002\u0010\u0014\u001a\u00020\u0004H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\tX\u0082T¢\u0006\u0002\n\u0000R#\u0010\u000b\u001a\n \r*\u0004\u0018\u00010\f0\f8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0010\u0010\u0011\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u0017"}, d2 = {"Lcom/king/logx/util/FormatUtils;", "", "()V", "DEFAULT_INDENT_SPACES", "", "JSON_ARRAY_PREFIX", "", "JSON_OBJECT_PREFIX", "XSLT_INDENT_AMOUNT", "", "YES", "transformerFactory", "Ljavax/xml/transform/TransformerFactory;", "kotlin.jvm.PlatformType", "getTransformerFactory", "()Ljavax/xml/transform/TransformerFactory;", "transformerFactory$delegate", "Lkotlin/Lazy;", "formatJson", "json", "indentSpaces", "formatXml", "xml", "logx_release"}, k = 1, mv = {1, 8, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
public final class FormatUtils {
    private static final int DEFAULT_INDENT_SPACES = 4;
    private static final char JSON_ARRAY_PREFIX = '[';
    private static final char JSON_OBJECT_PREFIX = '{';
    private static final String XSLT_INDENT_AMOUNT = "{http://xml.apache.org/xslt}indent-amount";
    private static final String YES = "yes";
    public static final FormatUtils INSTANCE = new FormatUtils();

    /* JADX INFO: renamed from: transformerFactory$delegate, reason: from kotlin metadata */
    private static final Lazy transformerFactory = LazyKt.lazy(new Function0<TransformerFactory>() { // from class: com.king.logx.util.FormatUtils$transformerFactory$2
        @Override // kotlin.jvm.functions.Function0
        public final TransformerFactory invoke() {
            return TransformerFactory.newInstance();
        }
    });

    @JvmStatic
    public static final String formatJson(String json) {
        Intrinsics.checkNotNullParameter(json, "json");
        return formatJson$default(json, 0, 2, null);
    }

    @JvmStatic
    public static final String formatXml(String xml) {
        Intrinsics.checkNotNullParameter(xml, "xml");
        return formatXml$default(xml, 0, 2, null);
    }

    private FormatUtils() {
    }

    private final TransformerFactory getTransformerFactory() {
        return (TransformerFactory) transformerFactory.getValue();
    }

    public static /* synthetic */ String formatJson$default(String str, int i, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            i = 4;
        }
        return formatJson(str, i);
    }

    @JvmStatic
    public static final String formatJson(String json, int indentSpaces) {
        String string;
        Intrinsics.checkNotNullParameter(json, "json");
        if (StringsKt.isBlank(json)) {
            return json;
        }
        try {
            String string2 = StringsKt.trim((CharSequence) json).toString();
            char cFirst = StringsKt.first(string2);
            if (cFirst == '{') {
                string = new JSONObject(string2).toString(indentSpaces);
            } else if (cFirst == '[') {
                string = new JSONArray(string2).toString(indentSpaces);
            } else {
                throw new JSONException("Invalid JSON.");
            }
            Intrinsics.checkNotNullExpressionValue(string, "{\n            json.trim(…}\n            }\n        }");
            return string;
        } catch (JSONException e) {
            LogX.INSTANCE.w(e, "JSON formatting failed.", new Object[0]);
            return json;
        }
    }

    public static /* synthetic */ String formatXml$default(String str, int i, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            i = 4;
        }
        return formatXml(str, i);
    }

    @JvmStatic
    public static final String formatXml(String xml, int indentSpaces) throws IOException {
        Intrinsics.checkNotNullParameter(xml, "xml");
        if (StringsKt.isBlank(xml)) {
            return xml;
        }
        try {
            StringWriter stringWriter = new StringWriter();
            try {
                StringWriter stringWriter2 = stringWriter;
                Transformer transformerNewTransformer = INSTANCE.getTransformerFactory().newTransformer();
                transformerNewTransformer.setOutputProperty("omit-xml-declaration", YES);
                transformerNewTransformer.setOutputProperty("indent", YES);
                transformerNewTransformer.setOutputProperty(XSLT_INDENT_AMOUNT, String.valueOf(indentSpaces));
                transformerNewTransformer.transform(new StreamSource(new StringReader(xml)), new StreamResult(stringWriter2));
                String string = stringWriter2.toString();
                Intrinsics.checkNotNullExpressionValue(string, "writer.toString()");
                String string2 = StringsKt.trim((CharSequence) string).toString();
                CloseableKt.closeFinally(stringWriter, null);
                return string2;
            } finally {
            }
        } catch (TransformerException e) {
            LogX.INSTANCE.w(e, "XML transformation failed.", new Object[0]);
            return xml;
        }
    }
}
