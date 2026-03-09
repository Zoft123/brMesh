package io.realm;

import android.util.JsonReader;
import android.util.JsonToken;
import io.realm.RealmAny;
import io.realm.internal.OsList;
import io.realm.internal.RealmObjectProxy;
import io.realm.internal.android.JsonUtils;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.bson.types.Decimal128;
import org.bson.types.ObjectId;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
class ProxyUtils {
    ProxyUtils() {
    }

    static <E> void setRealmListWithJsonObject(Realm realm, RealmList<E> realmList, JSONObject jSONObject, String str, boolean z) throws JSONException {
        RealmAny realmAnyCopyOrUpdate;
        if (jSONObject.has(str)) {
            OsList osList = realmList.getOsList();
            if (jSONObject.isNull(str)) {
                osList.removeAll();
                return;
            }
            JSONArray jSONArray = jSONObject.getJSONArray(str);
            osList.removeAll();
            int length = jSONArray.length();
            int i = 0;
            if (realmList.clazz == Boolean.class) {
                while (i < length) {
                    if (jSONArray.isNull(i)) {
                        osList.addNull();
                    } else {
                        osList.addBoolean(jSONArray.getBoolean(i));
                    }
                    i++;
                }
                return;
            }
            if (realmList.clazz == Float.class) {
                while (i < length) {
                    if (jSONArray.isNull(i)) {
                        osList.addNull();
                    } else {
                        osList.addFloat((float) jSONArray.getDouble(i));
                    }
                    i++;
                }
                return;
            }
            if (realmList.clazz == Double.class) {
                while (i < length) {
                    if (jSONArray.isNull(i)) {
                        osList.addNull();
                    } else {
                        osList.addDouble(jSONArray.getDouble(i));
                    }
                    i++;
                }
                return;
            }
            if (realmList.clazz == String.class) {
                while (i < length) {
                    if (jSONArray.isNull(i)) {
                        osList.addNull();
                    } else {
                        osList.addString(jSONArray.getString(i));
                    }
                    i++;
                }
                return;
            }
            if (realmList.clazz == byte[].class) {
                while (i < length) {
                    if (jSONArray.isNull(i)) {
                        osList.addNull();
                    } else {
                        osList.addBinary(JsonUtils.stringToBytes(jSONArray.getString(i)));
                    }
                    i++;
                }
                return;
            }
            if (realmList.clazz == Date.class) {
                while (i < length) {
                    if (jSONArray.isNull(i)) {
                        osList.addNull();
                    } else {
                        Object obj = jSONArray.get(i);
                        if (obj instanceof String) {
                            osList.addDate(JsonUtils.stringToDate((String) obj));
                        } else {
                            osList.addDate(new Date(jSONArray.getLong(i)));
                        }
                    }
                    i++;
                }
                return;
            }
            if (realmList.clazz == ObjectId.class) {
                while (i < length) {
                    if (jSONArray.isNull(i)) {
                        osList.addNull();
                    } else {
                        Object obj2 = jSONArray.get(i);
                        if (obj2 instanceof String) {
                            osList.addObjectId(new ObjectId((String) obj2));
                        } else {
                            osList.addObjectId((ObjectId) obj2);
                        }
                    }
                    i++;
                }
                return;
            }
            if (realmList.clazz == Decimal128.class) {
                while (i < length) {
                    if (jSONArray.isNull(i)) {
                        osList.addNull();
                    } else {
                        Object obj3 = jSONArray.get(i);
                        if (obj3 instanceof Decimal128) {
                            osList.addDecimal128((Decimal128) obj3);
                        } else if (obj3 instanceof String) {
                            osList.addDecimal128(Decimal128.parse((String) obj3));
                        } else if (obj3 instanceof Integer) {
                            osList.addDecimal128(new Decimal128(((Integer) obj3).intValue()));
                        } else if (obj3 instanceof Long) {
                            osList.addDecimal128(new Decimal128(((Long) obj3).longValue()));
                        } else if (obj3 instanceof Double) {
                            osList.addDecimal128(new Decimal128(new BigDecimal(((Double) obj3).doubleValue())));
                        } else {
                            osList.addDecimal128((Decimal128) obj3);
                        }
                    }
                    i++;
                }
                return;
            }
            if (realmList.clazz == UUID.class) {
                while (i < length) {
                    if (jSONArray.isNull(i)) {
                        osList.addNull();
                    } else {
                        Object obj4 = jSONArray.get(i);
                        if (obj4 instanceof UUID) {
                            osList.addUUID((UUID) obj4);
                        } else {
                            osList.addUUID(UUID.fromString((String) obj4));
                        }
                    }
                    i++;
                }
                return;
            }
            if (realmList.clazz != RealmAny.class) {
                if (realmList.clazz != Long.class && realmList.clazz != Integer.class && realmList.clazz != Short.class && realmList.clazz != Byte.class) {
                    throwWrongElementType(realmList.clazz);
                    return;
                }
                while (i < length) {
                    if (jSONArray.isNull(i)) {
                        osList.addNull();
                    } else {
                        osList.addLong(jSONArray.getLong(i));
                    }
                    i++;
                }
                return;
            }
            for (int i2 = 0; i2 < length; i2++) {
                if (jSONArray.isNull(i2)) {
                    osList.addNull();
                } else {
                    Object obj5 = jSONArray.get(i2);
                    if (obj5 instanceof String) {
                        realmAnyCopyOrUpdate = RealmAny.valueOf((String) obj5);
                    } else if (obj5 instanceof Integer) {
                        realmAnyCopyOrUpdate = RealmAny.valueOf((Integer) obj5);
                    } else if (obj5 instanceof Long) {
                        realmAnyCopyOrUpdate = RealmAny.valueOf((Long) obj5);
                    } else if (obj5 instanceof Double) {
                        realmAnyCopyOrUpdate = RealmAny.valueOf((Double) obj5);
                    } else if (obj5 instanceof Boolean) {
                        realmAnyCopyOrUpdate = RealmAny.valueOf((Boolean) obj5);
                    } else if (obj5 instanceof RealmAny) {
                        realmAnyCopyOrUpdate = copyOrUpdate((RealmAny) obj5, realm, z, new HashMap(), new HashSet());
                    } else {
                        throw new IllegalArgumentException(String.format("Unsupported JSON type: %s", obj5.getClass().getSimpleName()));
                    }
                    osList.addRealmAny(realmAnyCopyOrUpdate.getNativePtr());
                }
            }
        }
    }

    static <E> RealmList<E> createRealmListWithJsonStream(Class<E> cls, JsonReader jsonReader) throws IOException {
        if (jsonReader.peek() == null) {
            jsonReader.skipValue();
            return null;
        }
        jsonReader.beginArray();
        RealmList<E> realmList = new RealmList<>();
        if (cls == Boolean.class) {
            while (jsonReader.hasNext()) {
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.skipValue();
                    realmList.add(null);
                } else {
                    realmList.add(Boolean.valueOf(jsonReader.nextBoolean()));
                }
            }
        } else if (cls == Float.class) {
            while (jsonReader.hasNext()) {
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.skipValue();
                    realmList.add(null);
                } else {
                    realmList.add(Float.valueOf((float) jsonReader.nextDouble()));
                }
            }
        } else if (cls == Double.class) {
            while (jsonReader.hasNext()) {
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.skipValue();
                    realmList.add(null);
                } else {
                    realmList.add(Double.valueOf(jsonReader.nextDouble()));
                }
            }
        } else if (cls == String.class) {
            while (jsonReader.hasNext()) {
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.skipValue();
                    realmList.add(null);
                } else {
                    realmList.add(jsonReader.nextString());
                }
            }
        } else if (cls == byte[].class) {
            while (jsonReader.hasNext()) {
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.skipValue();
                    realmList.add(null);
                } else {
                    realmList.add(JsonUtils.stringToBytes(jsonReader.nextString()));
                }
            }
        } else if (cls == Date.class) {
            while (jsonReader.hasNext()) {
                JsonToken jsonTokenPeek = jsonReader.peek();
                if (jsonTokenPeek == JsonToken.NULL) {
                    jsonReader.skipValue();
                    realmList.add(null);
                } else if (jsonTokenPeek == JsonToken.NUMBER) {
                    realmList.add(new Date(jsonReader.nextLong()));
                } else {
                    realmList.add(JsonUtils.stringToDate(jsonReader.nextString()));
                }
            }
        } else if (cls == Long.class) {
            while (jsonReader.hasNext()) {
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.skipValue();
                    realmList.add(null);
                } else {
                    realmList.add(Long.valueOf(jsonReader.nextLong()));
                }
            }
        } else if (cls == Integer.class) {
            while (jsonReader.hasNext()) {
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.skipValue();
                    realmList.add(null);
                } else {
                    realmList.add(Integer.valueOf((int) jsonReader.nextLong()));
                }
            }
        } else if (cls == Short.class) {
            while (jsonReader.hasNext()) {
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.skipValue();
                    realmList.add(null);
                } else {
                    realmList.add(Short.valueOf((short) jsonReader.nextLong()));
                }
            }
        } else if (cls == Byte.class) {
            while (jsonReader.hasNext()) {
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.skipValue();
                    realmList.add(null);
                } else {
                    realmList.add(Byte.valueOf((byte) jsonReader.nextLong()));
                }
            }
        } else if (cls == ObjectId.class) {
            while (jsonReader.hasNext()) {
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.skipValue();
                    realmList.add(null);
                } else {
                    realmList.add(new ObjectId(jsonReader.nextString()));
                }
            }
        } else if (cls == Decimal128.class) {
            while (jsonReader.hasNext()) {
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.skipValue();
                    realmList.add(null);
                } else {
                    realmList.add(Decimal128.parse(jsonReader.nextString()));
                }
            }
        } else if (cls == UUID.class) {
            while (jsonReader.hasNext()) {
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.skipValue();
                    realmList.add(null);
                } else {
                    realmList.add(UUID.fromString(jsonReader.nextString()));
                }
            }
        } else if (cls == RealmAny.class) {
            while (jsonReader.hasNext()) {
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.skipValue();
                    realmList.add(RealmAny.nullValue());
                } else if (jsonReader.peek() == JsonToken.STRING) {
                    realmList.add(RealmAny.valueOf(jsonReader.nextString()));
                } else if (jsonReader.peek() == JsonToken.NUMBER) {
                    String strNextString = jsonReader.nextString();
                    if (strNextString.contains(".")) {
                        realmList.add(RealmAny.valueOf(Double.valueOf(Double.parseDouble(strNextString))));
                    } else {
                        realmList.add(RealmAny.valueOf(Long.valueOf(Long.parseLong(strNextString))));
                    }
                } else if (jsonReader.peek() == JsonToken.BOOLEAN) {
                    realmList.add(RealmAny.valueOf(Boolean.valueOf(jsonReader.nextBoolean())));
                }
            }
        } else if (cls == ObjectId.class) {
            while (jsonReader.hasNext()) {
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.skipValue();
                    realmList.add(null);
                } else {
                    realmList.add(new ObjectId(jsonReader.nextString()));
                }
            }
        } else if (cls == Decimal128.class) {
            while (jsonReader.hasNext()) {
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.skipValue();
                    realmList.add(null);
                } else {
                    realmList.add(Decimal128.parse(jsonReader.nextString()));
                }
            }
        } else {
            throwWrongElementType(cls);
        }
        jsonReader.endArray();
        return realmList;
    }

    private static void throwWrongElementType(@Nullable Class cls) {
        throw new IllegalArgumentException(String.format(Locale.ENGLISH, "Element type '%s' is not handled.", cls));
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Nullable
    static <T extends RealmModel> RealmAny copyToRealmIfNeeded(ProxyState<T> proxyState, @Nullable RealmAny realmAny) {
        Realm realm = (Realm) proxyState.getRealm$realm();
        if (realmAny == null) {
            return RealmAny.nullValue();
        }
        if (realmAny.getType() != RealmAny.Type.OBJECT) {
            return realmAny;
        }
        RealmModel realmModelAsRealmModel = realmAny.asRealmModel(RealmModel.class);
        if (realm.getSchema().getSchemaForClass((Class<? extends RealmModel>) realmModelAsRealmModel.getClass()).isEmbedded()) {
            throw new IllegalArgumentException("Embedded objects are not supported by RealmAny.");
        }
        if (!RealmObject.isManaged(realmModelAsRealmModel)) {
            if (realm.hasPrimaryKey(realmModelAsRealmModel.getClass())) {
                return RealmAny.valueOf(realm.copyToRealmOrUpdate(realmModelAsRealmModel, new ImportFlag[0]));
            }
            return RealmAny.valueOf(realm.copyToRealm(realmModelAsRealmModel, new ImportFlag[0]));
        }
        proxyState.checkValidObject(realmModelAsRealmModel);
        return realmAny;
    }

    static RealmAny copyOrUpdate(RealmAny realmAny, @Nonnull Realm realm, boolean z, @Nonnull Map<RealmModel, RealmObjectProxy> map, @Nonnull Set<ImportFlag> set) {
        if (realmAny == null) {
            return RealmAny.nullValue();
        }
        if (realmAny.getType() != RealmAny.Type.OBJECT) {
            return realmAny;
        }
        RealmModel realmModelAsRealmModel = realmAny.asRealmModel(realmAny.getValueClass());
        RealmObjectProxy realmObjectProxy = map.get(realmModelAsRealmModel);
        if (realmObjectProxy != null) {
            return RealmAny.valueOf((RealmModel) realmObjectProxy);
        }
        return RealmAny.valueOf(realm.getConfiguration().getSchemaMediator().copyOrUpdate(realm, realmModelAsRealmModel, z, map, set));
    }

    static RealmAny insert(RealmAny realmAny, @Nonnull Realm realm, @Nonnull Map<RealmModel, Long> map) {
        if (realmAny == null) {
            return RealmAny.nullValue();
        }
        if (realmAny.getType() != RealmAny.Type.OBJECT) {
            return realmAny;
        }
        Class<?> valueClass = realmAny.getValueClass();
        RealmModel realmModelAsRealmModel = realmAny.asRealmModel(valueClass);
        Long l = map.get(realmModelAsRealmModel);
        if (l != null) {
            return RealmAny.valueOf(l);
        }
        return RealmAny.valueOf(realm.get(valueClass, (String) null, realm.getConfiguration().getSchemaMediator().insert(realm, realmModelAsRealmModel, map)));
    }

    static RealmAny insertOrUpdate(RealmAny realmAny, @Nonnull Realm realm, @Nonnull Map<RealmModel, Long> map) {
        if (realmAny == null) {
            return RealmAny.nullValue();
        }
        if (realmAny.getType() != RealmAny.Type.OBJECT) {
            return realmAny;
        }
        Class<?> valueClass = realmAny.getValueClass();
        RealmModel realmModelAsRealmModel = realmAny.asRealmModel(valueClass);
        Long l = map.get(realmModelAsRealmModel);
        if (l != null) {
            return RealmAny.valueOf(l);
        }
        return RealmAny.valueOf(realm.get(valueClass, (String) null, realm.getConfiguration().getSchemaMediator().insertOrUpdate(realm, realmModelAsRealmModel, map)));
    }

    static RealmAny createDetachedCopy(RealmAny realmAny, @Nonnull Realm realm, int i, int i2, Map<RealmModel, RealmObjectProxy.CacheData<RealmModel>> map) {
        if (i > i2 || realmAny == null) {
            return RealmAny.nullValue();
        }
        if (realmAny.getType() != RealmAny.Type.OBJECT) {
            return realmAny;
        }
        return RealmAny.valueOf(realm.getConfiguration().getSchemaMediator().createDetachedCopy(realmAny.asRealmModel(realmAny.getValueClass()), i2 - 1, map));
    }

    static RealmAny createOrUpdateUsingJsonObject(RealmAny realmAny, @Nonnull Realm realm, int i, int i2, Map<RealmModel, RealmObjectProxy.CacheData<RealmModel>> map) {
        if (i > i2 || realmAny == null) {
            return RealmAny.nullValue();
        }
        if (realmAny.getType() != RealmAny.Type.OBJECT) {
            return realmAny;
        }
        return RealmAny.valueOf(realm.getConfiguration().getSchemaMediator().createDetachedCopy(realmAny.asRealmModel(realmAny.getValueClass()), i2 - 1, map));
    }
}
