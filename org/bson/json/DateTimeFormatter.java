package org.bson.json;

import j$.time.Instant;
import j$.time.ZoneId;
import j$.time.ZonedDateTime;
import j$.time.format.DateTimeParseException;
import j$.time.temporal.TemporalAccessor;
import j$.time.temporal.TemporalQuery;
import j$.util.DesugarTimeZone;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Calendar;

/* JADX INFO: loaded from: classes4.dex */
final class DateTimeFormatter {
    private static final FormatterImpl FORMATTER_IMPL;

    private interface FormatterImpl {
        String format(long j);

        long parse(String str);
    }

    static {
        FormatterImpl formatterImplLoadDateTimeFormatter;
        try {
            formatterImplLoadDateTimeFormatter = loadDateTimeFormatter("org.bson.json.DateTimeFormatter$Java8DateTimeFormatter");
        } catch (LinkageError unused) {
            formatterImplLoadDateTimeFormatter = loadDateTimeFormatter("org.bson.json.DateTimeFormatter$JaxbDateTimeFormatter");
        }
        FORMATTER_IMPL = formatterImplLoadDateTimeFormatter;
    }

    private static FormatterImpl loadDateTimeFormatter(String str) {
        try {
            return (FormatterImpl) Class.forName(str).getDeclaredConstructor(null).newInstance(null);
        } catch (ClassNotFoundException e) {
            throw new ExceptionInInitializerError(e);
        } catch (IllegalAccessException e2) {
            throw new ExceptionInInitializerError(e2);
        } catch (InstantiationException e3) {
            throw new ExceptionInInitializerError(e3);
        } catch (NoSuchMethodException e4) {
            throw new ExceptionInInitializerError(e4);
        } catch (InvocationTargetException e5) {
            throw new ExceptionInInitializerError(e5);
        }
    }

    static long parse(String str) {
        return FORMATTER_IMPL.parse(str);
    }

    static String format(long j) {
        return FORMATTER_IMPL.format(j);
    }

    static class JaxbDateTimeFormatter implements FormatterImpl {
        private static final Method DATATYPE_CONVERTER_PARSE_DATE_TIME_METHOD;
        private static final Method DATATYPE_CONVERTER_PRINT_DATE_TIME_METHOD;

        JaxbDateTimeFormatter() {
        }

        static {
            try {
                DATATYPE_CONVERTER_PARSE_DATE_TIME_METHOD = Class.forName("javax.xml.bind.DatatypeConverter").getDeclaredMethod("parseDateTime", String.class);
                DATATYPE_CONVERTER_PRINT_DATE_TIME_METHOD = Class.forName("javax.xml.bind.DatatypeConverter").getDeclaredMethod("printDateTime", Calendar.class);
            } catch (ClassNotFoundException e) {
                throw new ExceptionInInitializerError(e);
            } catch (NoSuchMethodException e2) {
                throw new ExceptionInInitializerError(e2);
            }
        }

        @Override // org.bson.json.DateTimeFormatter.FormatterImpl
        public long parse(String str) {
            try {
                return ((Calendar) DATATYPE_CONVERTER_PARSE_DATE_TIME_METHOD.invoke(null, str)).getTimeInMillis();
            } catch (IllegalAccessException e) {
                throw new IllegalStateException(e);
            } catch (InvocationTargetException e2) {
                throw ((RuntimeException) e2.getCause());
            }
        }

        @Override // org.bson.json.DateTimeFormatter.FormatterImpl
        public String format(long j) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(j);
            calendar.setTimeZone(DesugarTimeZone.getTimeZone("Z"));
            try {
                return (String) DATATYPE_CONVERTER_PRINT_DATE_TIME_METHOD.invoke(null, calendar);
            } catch (IllegalAccessException unused) {
                throw new IllegalStateException();
            } catch (InvocationTargetException e) {
                throw ((RuntimeException) e.getCause());
            }
        }
    }

    static class Java8DateTimeFormatter implements FormatterImpl {
        Java8DateTimeFormatter() {
        }

        static {
            try {
                Class.forName("java.time.format.DateTimeFormatter");
            } catch (ClassNotFoundException e) {
                throw new ExceptionInInitializerError(e);
            }
        }

        @Override // org.bson.json.DateTimeFormatter.FormatterImpl
        public long parse(String str) {
            try {
                return ((Instant) j$.time.format.DateTimeFormatter.ISO_OFFSET_DATE_TIME.parse(str, new TemporalQuery<Instant>() { // from class: org.bson.json.DateTimeFormatter.Java8DateTimeFormatter.1
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // j$.time.temporal.TemporalQuery
                    public Instant queryFrom(TemporalAccessor temporalAccessor) {
                        return Instant.from(temporalAccessor);
                    }
                })).toEpochMilli();
            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException(e.getMessage());
            }
        }

        @Override // org.bson.json.DateTimeFormatter.FormatterImpl
        public String format(long j) {
            return ZonedDateTime.ofInstant(Instant.ofEpochMilli(j), ZoneId.of("Z")).format(j$.time.format.DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        }
    }

    private DateTimeFormatter() {
    }
}
