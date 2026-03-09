package j$.time.zone;

import j$.util.Objects;
import j$.util.concurrent.ConcurrentHashMap;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;

/* JADX INFO: loaded from: classes3.dex */
public abstract class ZoneRulesProvider {
    private static final CopyOnWriteArrayList PROVIDERS;
    private static final ConcurrentMap ZONES;
    private static volatile Set ZONE_IDS;

    protected abstract ZoneRules provideRules(String str, boolean z);

    protected abstract Set provideZoneIds();

    static {
        CopyOnWriteArrayList copyOnWriteArrayList = new CopyOnWriteArrayList();
        PROVIDERS = copyOnWriteArrayList;
        ZONES = new ConcurrentHashMap(512, 0.75f, 2);
        final ArrayList arrayList = new ArrayList();
        AccessController.doPrivileged(new PrivilegedAction() { // from class: j$.time.zone.ZoneRulesProvider.1
            @Override // java.security.PrivilegedAction
            public Object run() {
                String property = System.getProperty("java.time.zone.DefaultZoneRulesProvider");
                if (property != null) {
                    try {
                        ZoneRulesProvider zoneRulesProvider = (ZoneRulesProvider) ZoneRulesProvider.class.cast(Class.forName(property, true, ZoneRulesProvider.class.getClassLoader()).newInstance());
                        ZoneRulesProvider.registerProvider(zoneRulesProvider);
                        arrayList.add(zoneRulesProvider);
                        return null;
                    } catch (Exception e) {
                        throw new Error(e);
                    }
                }
                ZoneRulesProvider.registerProvider(new TimeZoneRulesProvider());
                return null;
            }
        });
        copyOnWriteArrayList.addAll(arrayList);
    }

    public static Set getAvailableZoneIds() {
        return ZONE_IDS;
    }

    public static ZoneRules getRules(String str, boolean z) {
        Objects.requireNonNull(str, "zoneId");
        return getProvider(str).provideRules(str, z);
    }

    private static ZoneRulesProvider getProvider(String str) {
        ConcurrentMap concurrentMap = ZONES;
        ZoneRulesProvider zoneRulesProvider = (ZoneRulesProvider) concurrentMap.get(str);
        if (zoneRulesProvider != null) {
            return zoneRulesProvider;
        }
        if (concurrentMap.isEmpty()) {
            throw new ZoneRulesException("No time-zone data files registered");
        }
        throw new ZoneRulesException("Unknown time-zone ID: " + str);
    }

    public static void registerProvider(ZoneRulesProvider zoneRulesProvider) {
        Objects.requireNonNull(zoneRulesProvider, "provider");
        registerProvider0(zoneRulesProvider);
        PROVIDERS.add(zoneRulesProvider);
    }

    private static synchronized void registerProvider0(ZoneRulesProvider zoneRulesProvider) {
        try {
            for (String str : zoneRulesProvider.provideZoneIds()) {
                Objects.requireNonNull(str, "zoneId");
                if (((ZoneRulesProvider) ZONES.putIfAbsent(str, zoneRulesProvider)) != null) {
                    throw new ZoneRulesException("Unable to register zone as one already registered with that ID: " + str + ", currently loading from provider: " + zoneRulesProvider);
                }
            }
            ZONE_IDS = Collections.unmodifiableSet(new HashSet(ZONES.keySet()));
        } catch (Throwable th) {
            throw th;
        }
    }

    protected ZoneRulesProvider() {
    }

    private static final class TimeZoneRulesProvider extends ZoneRulesProvider {
        private final Set zoneIds;

        TimeZoneRulesProvider() {
            LinkedHashSet linkedHashSet = new LinkedHashSet();
            for (String str : TimeZone.getAvailableIDs()) {
                linkedHashSet.add(str);
            }
            this.zoneIds = Collections.unmodifiableSet(linkedHashSet);
        }

        @Override // j$.time.zone.ZoneRulesProvider
        protected Set provideZoneIds() {
            return this.zoneIds;
        }

        @Override // j$.time.zone.ZoneRulesProvider
        protected ZoneRules provideRules(String str, boolean z) {
            if (this.zoneIds.contains(str)) {
                return new ZoneRules(TimeZone.getTimeZone(str));
            }
            throw new ZoneRulesException("Not a built-in time zone: " + str);
        }
    }
}
