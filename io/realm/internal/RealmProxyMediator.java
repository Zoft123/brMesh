package io.realm.internal;

import android.util.JsonReader;
import io.realm.ImportFlag;
import io.realm.Realm;
import io.realm.RealmModel;
import io.realm.exceptions.RealmException;
import io.realm.internal.RealmObjectProxy;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public abstract class RealmProxyMediator {
    public abstract <E extends RealmModel> E copyOrUpdate(Realm realm, E e, boolean z, Map<RealmModel, RealmObjectProxy> map, Set<ImportFlag> set);

    public abstract ColumnInfo createColumnInfo(Class<? extends RealmModel> cls, OsSchemaInfo osSchemaInfo);

    public abstract <E extends RealmModel> E createDetachedCopy(E e, int i, Map<RealmModel, RealmObjectProxy.CacheData<RealmModel>> map);

    public abstract <E extends RealmModel> E createOrUpdateUsingJsonObject(Class<E> cls, Realm realm, JSONObject jSONObject, boolean z) throws JSONException;

    public abstract <E extends RealmModel> E createUsingJsonStream(Class<E> cls, Realm realm, JsonReader jsonReader) throws java.io.IOException;

    protected abstract <T extends RealmModel> Class<T> getClazzImpl(String str);

    public abstract Map<Class<? extends RealmModel>, OsObjectSchemaInfo> getExpectedObjectSchemaInfoMap();

    public abstract Set<Class<? extends RealmModel>> getModelClasses();

    protected abstract String getSimpleClassNameImpl(Class<? extends RealmModel> cls);

    protected abstract boolean hasPrimaryKeyImpl(Class<? extends RealmModel> cls);

    public abstract long insert(Realm realm, RealmModel realmModel, Map<RealmModel, Long> map);

    public abstract void insert(Realm realm, Collection<? extends RealmModel> collection);

    public abstract long insertOrUpdate(Realm realm, RealmModel realmModel, Map<RealmModel, Long> map);

    public abstract void insertOrUpdate(Realm realm, Collection<? extends RealmModel> collection);

    public abstract <E extends RealmModel> boolean isEmbedded(Class<E> cls);

    public abstract <E extends RealmModel> E newInstance(Class<E> cls, Object obj, Row row, ColumnInfo columnInfo, boolean z, List<String> list);

    public boolean transformerApplied() {
        return false;
    }

    public abstract <E extends RealmModel> void updateEmbeddedObject(Realm realm, E e, E e2, Map<RealmModel, RealmObjectProxy> map, Set<ImportFlag> set);

    public final String getSimpleClassName(Class<? extends RealmModel> cls) {
        return getSimpleClassNameImpl(Util.getOriginalModelClass(cls));
    }

    public final <T extends RealmModel> Class<T> getClazz(String str) {
        return getClazzImpl(str);
    }

    public boolean hasPrimaryKey(Class<? extends RealmModel> cls) {
        return hasPrimaryKeyImpl(cls);
    }

    public boolean equals(Object obj) {
        if (obj instanceof RealmProxyMediator) {
            return getModelClasses().equals(((RealmProxyMediator) obj).getModelClasses());
        }
        return false;
    }

    public int hashCode() {
        return getModelClasses().hashCode();
    }

    protected static void checkClass(Class<? extends RealmModel> cls) {
        if (cls == null) {
            throw new NullPointerException("A class extending RealmObject must be provided");
        }
    }

    protected static void checkClassName(String str) {
        if (str == null || str.isEmpty()) {
            throw new NullPointerException("A class extending RealmObject must be provided");
        }
    }

    protected static RealmException getMissingProxyClassException(Class<? extends RealmModel> cls) {
        return new RealmException(String.format("'%s' is not part of the schema for this Realm.", cls.toString()));
    }

    protected static RealmException getMissingProxyClassException(String str) {
        return new RealmException(String.format("'%s' is not part of the schema for this Realm.", str));
    }

    protected static IllegalStateException getNotEmbeddedClassException(String str) {
        return new IllegalStateException("This class is not marked embedded: " + str);
    }
}
