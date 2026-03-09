package io.realm;

import android.util.JsonReader;
import android.util.JsonToken;
import com.brgd.brblmesh.GeneralAdapter.model.ModDiyColor;
import com.brgd.brblmesh.GeneralClass.GlobalVariable;
import io.realm.BaseRealm;
import io.realm.internal.ColumnInfo;
import io.realm.internal.OsObject;
import io.realm.internal.OsObjectSchemaInfo;
import io.realm.internal.OsSchemaInfo;
import io.realm.internal.RealmObjectProxy;
import io.realm.internal.Row;
import io.realm.internal.Table;
import io.realm.internal.objectstore.OsObjectBuilder;
import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class com_brgd_brblmesh_GeneralAdapter_model_ModDiyColorRealmProxy extends ModDiyColor implements RealmObjectProxy, com_brgd_brblmesh_GeneralAdapter_model_ModDiyColorRealmProxyInterface {
    private static final String NO_ALIAS = "";
    private static final OsObjectSchemaInfo expectedObjectSchemaInfo = createExpectedObjectSchemaInfo();
    private ModDiyColorColumnInfo columnInfo;
    private ProxyState<ModDiyColor> proxyState;

    public static final class ClassNameHelper {
        public static final String INTERNAL_CLASS_NAME = "ModDiyColor";
    }

    static final class ModDiyColorColumnInfo extends ColumnInfo {
        long colorIndexColKey;
        long customModIdColKey;
        long diyColorColKey;
        long diyColorRColKey;
        long modNumberColKey;

        ModDiyColorColumnInfo(OsSchemaInfo osSchemaInfo) {
            super(5);
            OsObjectSchemaInfo objectSchemaInfo = osSchemaInfo.getObjectSchemaInfo(ClassNameHelper.INTERNAL_CLASS_NAME);
            this.modNumberColKey = addColumnDetails(GlobalVariable.MODNUM, GlobalVariable.MODNUM, objectSchemaInfo);
            this.colorIndexColKey = addColumnDetails(GlobalVariable.COLORINDEX, GlobalVariable.COLORINDEX, objectSchemaInfo);
            this.diyColorColKey = addColumnDetails("diyColor", "diyColor", objectSchemaInfo);
            this.diyColorRColKey = addColumnDetails("diyColorR", "diyColorR", objectSchemaInfo);
            this.customModIdColKey = addColumnDetails(GlobalVariable.customModId, GlobalVariable.customModId, objectSchemaInfo);
        }

        ModDiyColorColumnInfo(ColumnInfo columnInfo, boolean z) {
            super(columnInfo, z);
            copy(columnInfo, this);
        }

        @Override // io.realm.internal.ColumnInfo
        protected final ColumnInfo copy(boolean z) {
            return new ModDiyColorColumnInfo(this, z);
        }

        @Override // io.realm.internal.ColumnInfo
        protected final void copy(ColumnInfo columnInfo, ColumnInfo columnInfo2) {
            ModDiyColorColumnInfo modDiyColorColumnInfo = (ModDiyColorColumnInfo) columnInfo;
            ModDiyColorColumnInfo modDiyColorColumnInfo2 = (ModDiyColorColumnInfo) columnInfo2;
            modDiyColorColumnInfo2.modNumberColKey = modDiyColorColumnInfo.modNumberColKey;
            modDiyColorColumnInfo2.colorIndexColKey = modDiyColorColumnInfo.colorIndexColKey;
            modDiyColorColumnInfo2.diyColorColKey = modDiyColorColumnInfo.diyColorColKey;
            modDiyColorColumnInfo2.diyColorRColKey = modDiyColorColumnInfo.diyColorRColKey;
            modDiyColorColumnInfo2.customModIdColKey = modDiyColorColumnInfo.customModIdColKey;
        }
    }

    com_brgd_brblmesh_GeneralAdapter_model_ModDiyColorRealmProxy() {
        this.proxyState.setConstructionFinished();
    }

    @Override // io.realm.internal.RealmObjectProxy
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        BaseRealm.RealmObjectContext realmObjectContext = BaseRealm.objectContext.get();
        this.columnInfo = (ModDiyColorColumnInfo) realmObjectContext.getColumnInfo();
        ProxyState<ModDiyColor> proxyState = new ProxyState<>(this);
        this.proxyState = proxyState;
        proxyState.setRealm$realm(realmObjectContext.getRealm());
        this.proxyState.setRow$realm(realmObjectContext.getRow());
        this.proxyState.setAcceptDefaultValue$realm(realmObjectContext.getAcceptDefaultValue());
        this.proxyState.setExcludeFields$realm(realmObjectContext.getExcludeFields());
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.ModDiyColor, io.realm.com_brgd_brblmesh_GeneralAdapter_model_ModDiyColorRealmProxyInterface
    public int realmGet$modNumber() {
        this.proxyState.getRealm$realm().checkIfValid();
        return (int) this.proxyState.getRow$realm().getLong(this.columnInfo.modNumberColKey);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.ModDiyColor, io.realm.com_brgd_brblmesh_GeneralAdapter_model_ModDiyColorRealmProxyInterface
    public void realmSet$modNumber(int i) {
        if (this.proxyState.isUnderConstruction()) {
            if (this.proxyState.getAcceptDefaultValue$realm()) {
                Row row$realm = this.proxyState.getRow$realm();
                row$realm.getTable().setLong(this.columnInfo.modNumberColKey, row$realm.getObjectKey(), i, true);
                return;
            }
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        this.proxyState.getRow$realm().setLong(this.columnInfo.modNumberColKey, i);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.ModDiyColor, io.realm.com_brgd_brblmesh_GeneralAdapter_model_ModDiyColorRealmProxyInterface
    public int realmGet$colorIndex() {
        this.proxyState.getRealm$realm().checkIfValid();
        return (int) this.proxyState.getRow$realm().getLong(this.columnInfo.colorIndexColKey);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.ModDiyColor, io.realm.com_brgd_brblmesh_GeneralAdapter_model_ModDiyColorRealmProxyInterface
    public void realmSet$colorIndex(int i) {
        if (this.proxyState.isUnderConstruction()) {
            if (this.proxyState.getAcceptDefaultValue$realm()) {
                Row row$realm = this.proxyState.getRow$realm();
                row$realm.getTable().setLong(this.columnInfo.colorIndexColKey, row$realm.getObjectKey(), i, true);
                return;
            }
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        this.proxyState.getRow$realm().setLong(this.columnInfo.colorIndexColKey, i);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.ModDiyColor, io.realm.com_brgd_brblmesh_GeneralAdapter_model_ModDiyColorRealmProxyInterface
    public int realmGet$diyColor() {
        this.proxyState.getRealm$realm().checkIfValid();
        return (int) this.proxyState.getRow$realm().getLong(this.columnInfo.diyColorColKey);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.ModDiyColor, io.realm.com_brgd_brblmesh_GeneralAdapter_model_ModDiyColorRealmProxyInterface
    public void realmSet$diyColor(int i) {
        if (this.proxyState.isUnderConstruction()) {
            if (this.proxyState.getAcceptDefaultValue$realm()) {
                Row row$realm = this.proxyState.getRow$realm();
                row$realm.getTable().setLong(this.columnInfo.diyColorColKey, row$realm.getObjectKey(), i, true);
                return;
            }
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        this.proxyState.getRow$realm().setLong(this.columnInfo.diyColorColKey, i);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.ModDiyColor, io.realm.com_brgd_brblmesh_GeneralAdapter_model_ModDiyColorRealmProxyInterface
    public int realmGet$diyColorR() {
        this.proxyState.getRealm$realm().checkIfValid();
        return (int) this.proxyState.getRow$realm().getLong(this.columnInfo.diyColorRColKey);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.ModDiyColor, io.realm.com_brgd_brblmesh_GeneralAdapter_model_ModDiyColorRealmProxyInterface
    public void realmSet$diyColorR(int i) {
        if (this.proxyState.isUnderConstruction()) {
            if (this.proxyState.getAcceptDefaultValue$realm()) {
                Row row$realm = this.proxyState.getRow$realm();
                row$realm.getTable().setLong(this.columnInfo.diyColorRColKey, row$realm.getObjectKey(), i, true);
                return;
            }
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        this.proxyState.getRow$realm().setLong(this.columnInfo.diyColorRColKey, i);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.ModDiyColor, io.realm.com_brgd_brblmesh_GeneralAdapter_model_ModDiyColorRealmProxyInterface
    public String realmGet$customModId() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getString(this.columnInfo.customModIdColKey);
    }

    @Override // com.brgd.brblmesh.GeneralAdapter.model.ModDiyColor, io.realm.com_brgd_brblmesh_GeneralAdapter_model_ModDiyColorRealmProxyInterface
    public void realmSet$customModId(String str) {
        if (this.proxyState.isUnderConstruction()) {
            if (this.proxyState.getAcceptDefaultValue$realm()) {
                Row row$realm = this.proxyState.getRow$realm();
                if (str == null) {
                    row$realm.getTable().setNull(this.columnInfo.customModIdColKey, row$realm.getObjectKey(), true);
                    return;
                } else {
                    row$realm.getTable().setString(this.columnInfo.customModIdColKey, row$realm.getObjectKey(), str, true);
                    return;
                }
            }
            return;
        }
        this.proxyState.getRealm$realm().checkIfValid();
        if (str == null) {
            this.proxyState.getRow$realm().setNull(this.columnInfo.customModIdColKey);
        } else {
            this.proxyState.getRow$realm().setString(this.columnInfo.customModIdColKey, str);
        }
    }

    private static OsObjectSchemaInfo createExpectedObjectSchemaInfo() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder("", ClassNameHelper.INTERNAL_CLASS_NAME, false, 5, 0);
        builder.addPersistedProperty("", GlobalVariable.MODNUM, RealmFieldType.INTEGER, false, false, true);
        builder.addPersistedProperty("", GlobalVariable.COLORINDEX, RealmFieldType.INTEGER, false, false, true);
        builder.addPersistedProperty("", "diyColor", RealmFieldType.INTEGER, false, false, true);
        builder.addPersistedProperty("", "diyColorR", RealmFieldType.INTEGER, false, false, true);
        builder.addPersistedProperty("", GlobalVariable.customModId, RealmFieldType.STRING, false, false, false);
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return expectedObjectSchemaInfo;
    }

    public static ModDiyColorColumnInfo createColumnInfo(OsSchemaInfo osSchemaInfo) {
        return new ModDiyColorColumnInfo(osSchemaInfo);
    }

    public static String getSimpleClassName() {
        return ClassNameHelper.INTERNAL_CLASS_NAME;
    }

    public static ModDiyColor createOrUpdateUsingJsonObject(Realm realm, JSONObject jSONObject, boolean z) throws JSONException {
        ModDiyColor modDiyColor = (ModDiyColor) realm.createObjectInternal(ModDiyColor.class, true, Collections.EMPTY_LIST);
        ModDiyColor modDiyColor2 = modDiyColor;
        if (jSONObject.has(GlobalVariable.MODNUM)) {
            if (jSONObject.isNull(GlobalVariable.MODNUM)) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'modNumber' to null.");
            }
            modDiyColor2.realmSet$modNumber(jSONObject.getInt(GlobalVariable.MODNUM));
        }
        if (jSONObject.has(GlobalVariable.COLORINDEX)) {
            if (jSONObject.isNull(GlobalVariable.COLORINDEX)) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'colorIndex' to null.");
            }
            modDiyColor2.realmSet$colorIndex(jSONObject.getInt(GlobalVariable.COLORINDEX));
        }
        if (jSONObject.has("diyColor")) {
            if (jSONObject.isNull("diyColor")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'diyColor' to null.");
            }
            modDiyColor2.realmSet$diyColor(jSONObject.getInt("diyColor"));
        }
        if (jSONObject.has("diyColorR")) {
            if (jSONObject.isNull("diyColorR")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'diyColorR' to null.");
            }
            modDiyColor2.realmSet$diyColorR(jSONObject.getInt("diyColorR"));
        }
        if (jSONObject.has(GlobalVariable.customModId)) {
            if (jSONObject.isNull(GlobalVariable.customModId)) {
                modDiyColor2.realmSet$customModId(null);
                return modDiyColor;
            }
            modDiyColor2.realmSet$customModId(jSONObject.getString(GlobalVariable.customModId));
        }
        return modDiyColor;
    }

    public static ModDiyColor createUsingJsonStream(Realm realm, JsonReader jsonReader) throws IOException {
        ModDiyColor modDiyColor = new ModDiyColor();
        ModDiyColor modDiyColor2 = modDiyColor;
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            String strNextName = jsonReader.nextName();
            if (strNextName.equals(GlobalVariable.MODNUM)) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    modDiyColor2.realmSet$modNumber(jsonReader.nextInt());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'modNumber' to null.");
                }
            } else if (strNextName.equals(GlobalVariable.COLORINDEX)) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    modDiyColor2.realmSet$colorIndex(jsonReader.nextInt());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'colorIndex' to null.");
                }
            } else if (strNextName.equals("diyColor")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    modDiyColor2.realmSet$diyColor(jsonReader.nextInt());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'diyColor' to null.");
                }
            } else if (strNextName.equals("diyColorR")) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    modDiyColor2.realmSet$diyColorR(jsonReader.nextInt());
                } else {
                    jsonReader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'diyColorR' to null.");
                }
            } else if (strNextName.equals(GlobalVariable.customModId)) {
                if (jsonReader.peek() != JsonToken.NULL) {
                    modDiyColor2.realmSet$customModId(jsonReader.nextString());
                } else {
                    jsonReader.skipValue();
                    modDiyColor2.realmSet$customModId(null);
                }
            } else {
                jsonReader.skipValue();
            }
        }
        jsonReader.endObject();
        return (ModDiyColor) realm.copyToRealm(modDiyColor, new ImportFlag[0]);
    }

    static com_brgd_brblmesh_GeneralAdapter_model_ModDiyColorRealmProxy newProxyInstance(BaseRealm baseRealm, Row row) {
        BaseRealm.RealmObjectContext realmObjectContext = BaseRealm.objectContext.get();
        realmObjectContext.set(baseRealm, row, baseRealm.getSchema().getColumnInfo(ModDiyColor.class), false, Collections.EMPTY_LIST);
        com_brgd_brblmesh_GeneralAdapter_model_ModDiyColorRealmProxy com_brgd_brblmesh_generaladapter_model_moddiycolorrealmproxy = new com_brgd_brblmesh_GeneralAdapter_model_ModDiyColorRealmProxy();
        realmObjectContext.clear();
        return com_brgd_brblmesh_generaladapter_model_moddiycolorrealmproxy;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static ModDiyColor copyOrUpdate(Realm realm, ModDiyColorColumnInfo modDiyColorColumnInfo, ModDiyColor modDiyColor, boolean z, Map<RealmModel, RealmObjectProxy> map, Set<ImportFlag> set) {
        if ((modDiyColor instanceof RealmObjectProxy) && !RealmObject.isFrozen(modDiyColor)) {
            RealmObjectProxy realmObjectProxy = (RealmObjectProxy) modDiyColor;
            if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null) {
                BaseRealm realm$realm = realmObjectProxy.realmGet$proxyState().getRealm$realm();
                if (realm$realm.threadId != realm.threadId) {
                    throw new IllegalArgumentException("Objects which belong to Realm instances in other threads cannot be copied into this Realm instance.");
                }
                if (realm$realm.getPath().equals(realm.getPath())) {
                    return modDiyColor;
                }
            }
        }
        BaseRealm.objectContext.get();
        RealmModel realmModel = (RealmObjectProxy) map.get(modDiyColor);
        if (realmModel != null) {
            return (ModDiyColor) realmModel;
        }
        return copy(realm, modDiyColorColumnInfo, modDiyColor, z, map, set);
    }

    public static ModDiyColor copy(Realm realm, ModDiyColorColumnInfo modDiyColorColumnInfo, ModDiyColor modDiyColor, boolean z, Map<RealmModel, RealmObjectProxy> map, Set<ImportFlag> set) {
        RealmObjectProxy realmObjectProxy = map.get(modDiyColor);
        if (realmObjectProxy != null) {
            return (ModDiyColor) realmObjectProxy;
        }
        ModDiyColor modDiyColor2 = modDiyColor;
        OsObjectBuilder osObjectBuilder = new OsObjectBuilder(realm.getTable(ModDiyColor.class), set);
        osObjectBuilder.addInteger(modDiyColorColumnInfo.modNumberColKey, Integer.valueOf(modDiyColor2.realmGet$modNumber()));
        osObjectBuilder.addInteger(modDiyColorColumnInfo.colorIndexColKey, Integer.valueOf(modDiyColor2.realmGet$colorIndex()));
        osObjectBuilder.addInteger(modDiyColorColumnInfo.diyColorColKey, Integer.valueOf(modDiyColor2.realmGet$diyColor()));
        osObjectBuilder.addInteger(modDiyColorColumnInfo.diyColorRColKey, Integer.valueOf(modDiyColor2.realmGet$diyColorR()));
        osObjectBuilder.addString(modDiyColorColumnInfo.customModIdColKey, modDiyColor2.realmGet$customModId());
        com_brgd_brblmesh_GeneralAdapter_model_ModDiyColorRealmProxy com_brgd_brblmesh_generaladapter_model_moddiycolorrealmproxyNewProxyInstance = newProxyInstance(realm, osObjectBuilder.createNewObject());
        map.put(modDiyColor, com_brgd_brblmesh_generaladapter_model_moddiycolorrealmproxyNewProxyInstance);
        return com_brgd_brblmesh_generaladapter_model_moddiycolorrealmproxyNewProxyInstance;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static long insert(Realm realm, ModDiyColor modDiyColor, Map<RealmModel, Long> map) {
        if ((modDiyColor instanceof RealmObjectProxy) && !RealmObject.isFrozen(modDiyColor)) {
            RealmObjectProxy realmObjectProxy = (RealmObjectProxy) modDiyColor;
            if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                return realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey();
            }
        }
        Table table = realm.getTable(ModDiyColor.class);
        long nativePtr = table.getNativePtr();
        ModDiyColorColumnInfo modDiyColorColumnInfo = (ModDiyColorColumnInfo) realm.getSchema().getColumnInfo(ModDiyColor.class);
        long jCreateRow = OsObject.createRow(table);
        map.put(modDiyColor, Long.valueOf(jCreateRow));
        Table.nativeSetLong(nativePtr, modDiyColorColumnInfo.modNumberColKey, jCreateRow, r11.realmGet$modNumber(), false);
        Table.nativeSetLong(nativePtr, modDiyColorColumnInfo.colorIndexColKey, jCreateRow, r11.realmGet$colorIndex(), false);
        Table.nativeSetLong(nativePtr, modDiyColorColumnInfo.diyColorColKey, jCreateRow, r11.realmGet$diyColor(), false);
        Table.nativeSetLong(nativePtr, modDiyColorColumnInfo.diyColorRColKey, jCreateRow, r11.realmGet$diyColorR(), false);
        String strRealmGet$customModId = modDiyColor.realmGet$customModId();
        if (strRealmGet$customModId != null) {
            Table.nativeSetString(nativePtr, modDiyColorColumnInfo.customModIdColKey, jCreateRow, strRealmGet$customModId, false);
        }
        return jCreateRow;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> it, Map<RealmModel, Long> map) {
        Table table = realm.getTable(ModDiyColor.class);
        long nativePtr = table.getNativePtr();
        ModDiyColorColumnInfo modDiyColorColumnInfo = (ModDiyColorColumnInfo) realm.getSchema().getColumnInfo(ModDiyColor.class);
        while (it.hasNext()) {
            RealmModel realmModel = (ModDiyColor) it.next();
            if (!map.containsKey(realmModel)) {
                if ((realmModel instanceof RealmObjectProxy) && !RealmObject.isFrozen(realmModel)) {
                    RealmObjectProxy realmObjectProxy = (RealmObjectProxy) realmModel;
                    if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                        map.put(realmModel, Long.valueOf(realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey()));
                    }
                }
                long jCreateRow = OsObject.createRow(table);
                map.put(realmModel, Long.valueOf(jCreateRow));
                Table.nativeSetLong(nativePtr, modDiyColorColumnInfo.modNumberColKey, jCreateRow, r11.realmGet$modNumber(), false);
                Table.nativeSetLong(nativePtr, modDiyColorColumnInfo.colorIndexColKey, jCreateRow, r11.realmGet$colorIndex(), false);
                Table.nativeSetLong(nativePtr, modDiyColorColumnInfo.diyColorColKey, jCreateRow, r11.realmGet$diyColor(), false);
                Table.nativeSetLong(nativePtr, modDiyColorColumnInfo.diyColorRColKey, jCreateRow, r11.realmGet$diyColorR(), false);
                String strRealmGet$customModId = ((com_brgd_brblmesh_GeneralAdapter_model_ModDiyColorRealmProxyInterface) realmModel).realmGet$customModId();
                if (strRealmGet$customModId != null) {
                    Table.nativeSetString(nativePtr, modDiyColorColumnInfo.customModIdColKey, jCreateRow, strRealmGet$customModId, false);
                }
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static long insertOrUpdate(Realm realm, ModDiyColor modDiyColor, Map<RealmModel, Long> map) {
        if ((modDiyColor instanceof RealmObjectProxy) && !RealmObject.isFrozen(modDiyColor)) {
            RealmObjectProxy realmObjectProxy = (RealmObjectProxy) modDiyColor;
            if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                return realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey();
            }
        }
        Table table = realm.getTable(ModDiyColor.class);
        long nativePtr = table.getNativePtr();
        ModDiyColorColumnInfo modDiyColorColumnInfo = (ModDiyColorColumnInfo) realm.getSchema().getColumnInfo(ModDiyColor.class);
        long jCreateRow = OsObject.createRow(table);
        map.put(modDiyColor, Long.valueOf(jCreateRow));
        Table.nativeSetLong(nativePtr, modDiyColorColumnInfo.modNumberColKey, jCreateRow, r11.realmGet$modNumber(), false);
        Table.nativeSetLong(nativePtr, modDiyColorColumnInfo.colorIndexColKey, jCreateRow, r11.realmGet$colorIndex(), false);
        Table.nativeSetLong(nativePtr, modDiyColorColumnInfo.diyColorColKey, jCreateRow, r11.realmGet$diyColor(), false);
        Table.nativeSetLong(nativePtr, modDiyColorColumnInfo.diyColorRColKey, jCreateRow, r11.realmGet$diyColorR(), false);
        String strRealmGet$customModId = modDiyColor.realmGet$customModId();
        if (strRealmGet$customModId != null) {
            Table.nativeSetString(nativePtr, modDiyColorColumnInfo.customModIdColKey, jCreateRow, strRealmGet$customModId, false);
            return jCreateRow;
        }
        Table.nativeSetNull(nativePtr, modDiyColorColumnInfo.customModIdColKey, jCreateRow, false);
        return jCreateRow;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> it, Map<RealmModel, Long> map) {
        Table table = realm.getTable(ModDiyColor.class);
        long nativePtr = table.getNativePtr();
        ModDiyColorColumnInfo modDiyColorColumnInfo = (ModDiyColorColumnInfo) realm.getSchema().getColumnInfo(ModDiyColor.class);
        while (it.hasNext()) {
            RealmModel realmModel = (ModDiyColor) it.next();
            if (!map.containsKey(realmModel)) {
                if ((realmModel instanceof RealmObjectProxy) && !RealmObject.isFrozen(realmModel)) {
                    RealmObjectProxy realmObjectProxy = (RealmObjectProxy) realmModel;
                    if (realmObjectProxy.realmGet$proxyState().getRealm$realm() != null && realmObjectProxy.realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                        map.put(realmModel, Long.valueOf(realmObjectProxy.realmGet$proxyState().getRow$realm().getObjectKey()));
                    }
                }
                long jCreateRow = OsObject.createRow(table);
                map.put(realmModel, Long.valueOf(jCreateRow));
                Table.nativeSetLong(nativePtr, modDiyColorColumnInfo.modNumberColKey, jCreateRow, r11.realmGet$modNumber(), false);
                Table.nativeSetLong(nativePtr, modDiyColorColumnInfo.colorIndexColKey, jCreateRow, r11.realmGet$colorIndex(), false);
                Table.nativeSetLong(nativePtr, modDiyColorColumnInfo.diyColorColKey, jCreateRow, r11.realmGet$diyColor(), false);
                Table.nativeSetLong(nativePtr, modDiyColorColumnInfo.diyColorRColKey, jCreateRow, r11.realmGet$diyColorR(), false);
                String strRealmGet$customModId = ((com_brgd_brblmesh_GeneralAdapter_model_ModDiyColorRealmProxyInterface) realmModel).realmGet$customModId();
                if (strRealmGet$customModId != null) {
                    Table.nativeSetString(nativePtr, modDiyColorColumnInfo.customModIdColKey, jCreateRow, strRealmGet$customModId, false);
                } else {
                    Table.nativeSetNull(nativePtr, modDiyColorColumnInfo.customModIdColKey, jCreateRow, false);
                }
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static ModDiyColor createDetachedCopy(ModDiyColor modDiyColor, int i, int i2, Map<RealmModel, RealmObjectProxy.CacheData<RealmModel>> map) {
        ModDiyColor modDiyColor2;
        if (i > i2 || modDiyColor == 0) {
            return null;
        }
        RealmObjectProxy.CacheData<RealmModel> cacheData = map.get(modDiyColor);
        if (cacheData == null) {
            modDiyColor2 = new ModDiyColor();
            map.put(modDiyColor, new RealmObjectProxy.CacheData<>(i, modDiyColor2));
        } else {
            if (i >= cacheData.minDepth) {
                return (ModDiyColor) cacheData.object;
            }
            ModDiyColor modDiyColor3 = (ModDiyColor) cacheData.object;
            cacheData.minDepth = i;
            modDiyColor2 = modDiyColor3;
        }
        ModDiyColor modDiyColor4 = modDiyColor2;
        ModDiyColor modDiyColor5 = modDiyColor;
        modDiyColor4.realmSet$modNumber(modDiyColor5.realmGet$modNumber());
        modDiyColor4.realmSet$colorIndex(modDiyColor5.realmGet$colorIndex());
        modDiyColor4.realmSet$diyColor(modDiyColor5.realmGet$diyColor());
        modDiyColor4.realmSet$diyColorR(modDiyColor5.realmGet$diyColorR());
        modDiyColor4.realmSet$customModId(modDiyColor5.realmGet$customModId());
        return modDiyColor2;
    }

    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder sb = new StringBuilder("ModDiyColor = proxy[{modNumber:");
        sb.append(realmGet$modNumber());
        sb.append("},{colorIndex:");
        sb.append(realmGet$colorIndex());
        sb.append("},{diyColor:");
        sb.append(realmGet$diyColor());
        sb.append("},{diyColorR:");
        sb.append(realmGet$diyColorR());
        sb.append("},{customModId:");
        sb.append(realmGet$customModId() != null ? realmGet$customModId() : GlobalVariable.nullColor);
        sb.append("}]");
        return sb.toString();
    }

    @Override // io.realm.internal.RealmObjectProxy
    public ProxyState<?> realmGet$proxyState() {
        return this.proxyState;
    }

    public int hashCode() {
        String path = this.proxyState.getRealm$realm().getPath();
        String name = this.proxyState.getRow$realm().getTable().getName();
        long objectKey = this.proxyState.getRow$realm().getObjectKey();
        return ((((527 + (path != null ? path.hashCode() : 0)) * 31) + (name != null ? name.hashCode() : 0)) * 31) + ((int) ((objectKey >>> 32) ^ objectKey));
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        com_brgd_brblmesh_GeneralAdapter_model_ModDiyColorRealmProxy com_brgd_brblmesh_generaladapter_model_moddiycolorrealmproxy = (com_brgd_brblmesh_GeneralAdapter_model_ModDiyColorRealmProxy) obj;
        BaseRealm realm$realm = this.proxyState.getRealm$realm();
        BaseRealm realm$realm2 = com_brgd_brblmesh_generaladapter_model_moddiycolorrealmproxy.proxyState.getRealm$realm();
        String path = realm$realm.getPath();
        String path2 = realm$realm2.getPath();
        if (path == null ? path2 != null : !path.equals(path2)) {
            return false;
        }
        if (realm$realm.isFrozen() != realm$realm2.isFrozen() || !realm$realm.sharedRealm.getVersionID().equals(realm$realm2.sharedRealm.getVersionID())) {
            return false;
        }
        String name = this.proxyState.getRow$realm().getTable().getName();
        String name2 = com_brgd_brblmesh_generaladapter_model_moddiycolorrealmproxy.proxyState.getRow$realm().getTable().getName();
        if (name == null ? name2 == null : name.equals(name2)) {
            return this.proxyState.getRow$realm().getObjectKey() == com_brgd_brblmesh_generaladapter_model_moddiycolorrealmproxy.proxyState.getRow$realm().getObjectKey();
        }
        return false;
    }
}
