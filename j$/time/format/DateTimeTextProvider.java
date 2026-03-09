package j$.time.format;

import j$.time.chrono.Chronology;
import j$.time.chrono.IsoChronology;
import j$.time.temporal.ChronoField;
import j$.time.temporal.TemporalField;
import j$.util.concurrent.ConcurrentHashMap;
import java.text.DateFormatSymbols;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

/* JADX INFO: loaded from: classes3.dex */
class DateTimeTextProvider {
    private static final ConcurrentMap CACHE = new ConcurrentHashMap(16, 0.75f, 2);
    private static final Comparator COMPARATOR = new Comparator() { // from class: j$.time.format.DateTimeTextProvider.1
        @Override // java.util.Comparator
        public int compare(Map.Entry entry, Map.Entry entry2) {
            return ((String) entry2.getKey()).length() - ((String) entry.getKey()).length();
        }
    };
    private static final DateTimeTextProvider INSTANCE = new DateTimeTextProvider();

    DateTimeTextProvider() {
    }

    static DateTimeTextProvider getInstance() {
        return INSTANCE;
    }

    public String getText(TemporalField temporalField, long j, TextStyle textStyle, Locale locale) {
        Object objFindStore = findStore(temporalField, locale);
        if (objFindStore instanceof LocaleStore) {
            return ((LocaleStore) objFindStore).getText(j, textStyle);
        }
        return null;
    }

    public String getText(Chronology chronology, TemporalField temporalField, long j, TextStyle textStyle, Locale locale) {
        if (chronology == IsoChronology.INSTANCE || !(temporalField instanceof ChronoField)) {
            return getText(temporalField, j, textStyle, locale);
        }
        return null;
    }

    public Iterator getTextIterator(TemporalField temporalField, TextStyle textStyle, Locale locale) {
        Object objFindStore = findStore(temporalField, locale);
        if (objFindStore instanceof LocaleStore) {
            return ((LocaleStore) objFindStore).getTextIterator(textStyle);
        }
        return null;
    }

    public Iterator getTextIterator(Chronology chronology, TemporalField temporalField, TextStyle textStyle, Locale locale) {
        if (chronology == IsoChronology.INSTANCE || !(temporalField instanceof ChronoField)) {
            return getTextIterator(temporalField, textStyle, locale);
        }
        return null;
    }

    private Object findStore(TemporalField temporalField, Locale locale) {
        Map.Entry entryCreateEntry = createEntry(temporalField, locale);
        ConcurrentMap concurrentMap = CACHE;
        Object obj = concurrentMap.get(entryCreateEntry);
        if (obj != null) {
            return obj;
        }
        concurrentMap.putIfAbsent(entryCreateEntry, createStore(temporalField, locale));
        return concurrentMap.get(entryCreateEntry);
    }

    private Object createStore(TemporalField temporalField, Locale locale) {
        HashMap map = new HashMap();
        int i = 0;
        if (temporalField == ChronoField.ERA) {
            DateFormatSymbols dateFormatSymbols = DateFormatSymbols.getInstance(locale);
            HashMap map2 = new HashMap();
            HashMap map3 = new HashMap();
            String[] eras = dateFormatSymbols.getEras();
            while (i < eras.length) {
                if (!eras[i].isEmpty()) {
                    long j = i;
                    map2.put(Long.valueOf(j), eras[i]);
                    map3.put(Long.valueOf(j), firstCodePoint(eras[i]));
                }
                i++;
            }
            if (!map2.isEmpty()) {
                map.put(TextStyle.FULL, map2);
                map.put(TextStyle.SHORT, map2);
                map.put(TextStyle.NARROW, map3);
            }
            return new LocaleStore(map);
        }
        if (temporalField == ChronoField.MONTH_OF_YEAR) {
            DateFormatSymbols dateFormatSymbols2 = DateFormatSymbols.getInstance(locale);
            HashMap map4 = new HashMap();
            HashMap map5 = new HashMap();
            String[] months = dateFormatSymbols2.getMonths();
            for (int i2 = 0; i2 < months.length; i2++) {
                if (!months[i2].isEmpty()) {
                    long j2 = ((long) i2) + 1;
                    map4.put(Long.valueOf(j2), months[i2]);
                    map5.put(Long.valueOf(j2), firstCodePoint(months[i2]));
                }
            }
            if (!map4.isEmpty()) {
                map.put(TextStyle.FULL, map4);
                map.put(TextStyle.NARROW, map5);
            }
            HashMap map6 = new HashMap();
            String[] shortMonths = dateFormatSymbols2.getShortMonths();
            while (i < shortMonths.length) {
                if (!shortMonths[i].isEmpty()) {
                    map6.put(Long.valueOf(((long) i) + 1), shortMonths[i]);
                }
                i++;
            }
            if (!map6.isEmpty()) {
                map.put(TextStyle.SHORT, map6);
            }
            return new LocaleStore(map);
        }
        if (temporalField == ChronoField.DAY_OF_WEEK) {
            DateFormatSymbols dateFormatSymbols3 = DateFormatSymbols.getInstance(locale);
            HashMap map7 = new HashMap();
            String[] weekdays = dateFormatSymbols3.getWeekdays();
            map7.put(1L, weekdays[2]);
            map7.put(2L, weekdays[3]);
            map7.put(3L, weekdays[4]);
            map7.put(4L, weekdays[5]);
            map7.put(5L, weekdays[6]);
            map7.put(6L, weekdays[7]);
            map7.put(7L, weekdays[1]);
            map.put(TextStyle.FULL, map7);
            HashMap map8 = new HashMap();
            map8.put(1L, firstCodePoint(weekdays[2]));
            map8.put(2L, firstCodePoint(weekdays[3]));
            map8.put(3L, firstCodePoint(weekdays[4]));
            map8.put(4L, firstCodePoint(weekdays[5]));
            map8.put(5L, firstCodePoint(weekdays[6]));
            map8.put(6L, firstCodePoint(weekdays[7]));
            map8.put(7L, firstCodePoint(weekdays[1]));
            map.put(TextStyle.NARROW, map8);
            HashMap map9 = new HashMap();
            String[] shortWeekdays = dateFormatSymbols3.getShortWeekdays();
            map9.put(1L, shortWeekdays[2]);
            map9.put(2L, shortWeekdays[3]);
            map9.put(3L, shortWeekdays[4]);
            map9.put(4L, shortWeekdays[5]);
            map9.put(5L, shortWeekdays[6]);
            map9.put(6L, shortWeekdays[7]);
            map9.put(7L, shortWeekdays[1]);
            map.put(TextStyle.SHORT, map9);
            return new LocaleStore(map);
        }
        if (temporalField == ChronoField.AMPM_OF_DAY) {
            DateFormatSymbols dateFormatSymbols4 = DateFormatSymbols.getInstance(locale);
            HashMap map10 = new HashMap();
            HashMap map11 = new HashMap();
            String[] amPmStrings = dateFormatSymbols4.getAmPmStrings();
            while (i < amPmStrings.length) {
                if (!amPmStrings[i].isEmpty()) {
                    long j3 = i;
                    map10.put(Long.valueOf(j3), amPmStrings[i]);
                    map11.put(Long.valueOf(j3), firstCodePoint(amPmStrings[i]));
                }
                i++;
            }
            if (!map10.isEmpty()) {
                map.put(TextStyle.FULL, map10);
                map.put(TextStyle.SHORT, map10);
                map.put(TextStyle.NARROW, map11);
            }
            return new LocaleStore(map);
        }
        return "";
    }

    private static String firstCodePoint(String str) {
        return str.substring(0, Character.charCount(str.codePointAt(0)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Map.Entry createEntry(Object obj, Object obj2) {
        return new AbstractMap.SimpleImmutableEntry(obj, obj2);
    }

    static final class LocaleStore {
        private final Map parsable;
        private final Map valueTextMap;

        LocaleStore(Map map) {
            this.valueTextMap = map;
            HashMap map2 = new HashMap();
            ArrayList arrayList = new ArrayList();
            for (Map.Entry entry : map.entrySet()) {
                HashMap map3 = new HashMap();
                for (Map.Entry entry2 : ((Map) entry.getValue()).entrySet()) {
                    map3.put((String) entry2.getValue(), DateTimeTextProvider.createEntry((String) entry2.getValue(), (Long) entry2.getKey()));
                }
                ArrayList arrayList2 = new ArrayList(map3.values());
                Collections.sort(arrayList2, DateTimeTextProvider.COMPARATOR);
                map2.put((TextStyle) entry.getKey(), arrayList2);
                arrayList.addAll(arrayList2);
                map2.put(null, arrayList);
            }
            Collections.sort(arrayList, DateTimeTextProvider.COMPARATOR);
            this.parsable = map2;
        }

        String getText(long j, TextStyle textStyle) {
            Map map = (Map) this.valueTextMap.get(textStyle);
            if (map != null) {
                return (String) map.get(Long.valueOf(j));
            }
            return null;
        }

        Iterator getTextIterator(TextStyle textStyle) {
            List list = (List) this.parsable.get(textStyle);
            if (list != null) {
                return list.iterator();
            }
            return null;
        }
    }
}
