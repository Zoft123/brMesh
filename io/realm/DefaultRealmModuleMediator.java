package io.realm;

import android.util.JsonReader;
import com.brgd.brblmesh.GeneralAdapter.model.BleDevice;
import com.brgd.brblmesh.GeneralAdapter.model.DeleteFixedGroup;
import com.brgd.brblmesh.GeneralAdapter.model.DeleteGroups;
import com.brgd.brblmesh.GeneralAdapter.model.DeleteRadarGroup;
import com.brgd.brblmesh.GeneralAdapter.model.DiyColor;
import com.brgd.brblmesh.GeneralAdapter.model.FixedGroup;
import com.brgd.brblmesh.GeneralAdapter.model.Groups;
import com.brgd.brblmesh.GeneralAdapter.model.Mod;
import com.brgd.brblmesh.GeneralAdapter.model.ModDiyColor;
import com.brgd.brblmesh.GeneralAdapter.model.PhoneType;
import com.brgd.brblmesh.GeneralAdapter.model.RadarDevice;
import com.brgd.brblmesh.GeneralAdapter.model.RadarGroup;
import com.brgd.brblmesh.GeneralAdapter.model.RadarPhoneType;
import com.brgd.brblmesh.GeneralAdapter.model.RadarTimer;
import com.brgd.brblmesh.GeneralAdapter.model.Scene;
import com.brgd.brblmesh.GeneralAdapter.model.SceneDevice;
import com.brgd.brblmesh.GeneralAdapter.model.Timer;
import io.realm.BaseRealm;
import io.realm.annotations.RealmModule;
import io.realm.com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxy;
import io.realm.com_brgd_brblmesh_GeneralAdapter_model_DeleteFixedGroupRealmProxy;
import io.realm.com_brgd_brblmesh_GeneralAdapter_model_DeleteGroupsRealmProxy;
import io.realm.com_brgd_brblmesh_GeneralAdapter_model_DeleteRadarGroupRealmProxy;
import io.realm.com_brgd_brblmesh_GeneralAdapter_model_DiyColorRealmProxy;
import io.realm.com_brgd_brblmesh_GeneralAdapter_model_FixedGroupRealmProxy;
import io.realm.com_brgd_brblmesh_GeneralAdapter_model_GroupsRealmProxy;
import io.realm.com_brgd_brblmesh_GeneralAdapter_model_ModDiyColorRealmProxy;
import io.realm.com_brgd_brblmesh_GeneralAdapter_model_ModRealmProxy;
import io.realm.com_brgd_brblmesh_GeneralAdapter_model_PhoneTypeRealmProxy;
import io.realm.com_brgd_brblmesh_GeneralAdapter_model_RadarDeviceRealmProxy;
import io.realm.com_brgd_brblmesh_GeneralAdapter_model_RadarGroupRealmProxy;
import io.realm.com_brgd_brblmesh_GeneralAdapter_model_RadarPhoneTypeRealmProxy;
import io.realm.com_brgd_brblmesh_GeneralAdapter_model_RadarTimerRealmProxy;
import io.realm.com_brgd_brblmesh_GeneralAdapter_model_SceneDeviceRealmProxy;
import io.realm.com_brgd_brblmesh_GeneralAdapter_model_SceneRealmProxy;
import io.realm.com_brgd_brblmesh_GeneralAdapter_model_TimerRealmProxy;
import io.realm.internal.ColumnInfo;
import io.realm.internal.OsObjectSchemaInfo;
import io.realm.internal.OsSchemaInfo;
import io.realm.internal.RealmObjectProxy;
import io.realm.internal.RealmProxyMediator;
import io.realm.internal.Row;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
@RealmModule
class DefaultRealmModuleMediator extends RealmProxyMediator {
    private static final Set<Class<? extends RealmModel>> MODEL_CLASSES;

    @Override // io.realm.internal.RealmProxyMediator
    public boolean hasPrimaryKeyImpl(Class<? extends RealmModel> cls) {
        return false;
    }

    @Override // io.realm.internal.RealmProxyMediator
    public boolean transformerApplied() {
        return true;
    }

    DefaultRealmModuleMediator() {
    }

    static {
        HashSet hashSet = new HashSet(17);
        hashSet.add(Timer.class);
        hashSet.add(SceneDevice.class);
        hashSet.add(Scene.class);
        hashSet.add(RadarTimer.class);
        hashSet.add(RadarPhoneType.class);
        hashSet.add(RadarGroup.class);
        hashSet.add(RadarDevice.class);
        hashSet.add(PhoneType.class);
        hashSet.add(ModDiyColor.class);
        hashSet.add(Mod.class);
        hashSet.add(Groups.class);
        hashSet.add(FixedGroup.class);
        hashSet.add(DiyColor.class);
        hashSet.add(DeleteRadarGroup.class);
        hashSet.add(DeleteGroups.class);
        hashSet.add(DeleteFixedGroup.class);
        hashSet.add(BleDevice.class);
        MODEL_CLASSES = Collections.unmodifiableSet(hashSet);
    }

    @Override // io.realm.internal.RealmProxyMediator
    public Map<Class<? extends RealmModel>, OsObjectSchemaInfo> getExpectedObjectSchemaInfoMap() {
        HashMap map = new HashMap(17);
        map.put(Timer.class, com_brgd_brblmesh_GeneralAdapter_model_TimerRealmProxy.getExpectedObjectSchemaInfo());
        map.put(SceneDevice.class, com_brgd_brblmesh_GeneralAdapter_model_SceneDeviceRealmProxy.getExpectedObjectSchemaInfo());
        map.put(Scene.class, com_brgd_brblmesh_GeneralAdapter_model_SceneRealmProxy.getExpectedObjectSchemaInfo());
        map.put(RadarTimer.class, com_brgd_brblmesh_GeneralAdapter_model_RadarTimerRealmProxy.getExpectedObjectSchemaInfo());
        map.put(RadarPhoneType.class, com_brgd_brblmesh_GeneralAdapter_model_RadarPhoneTypeRealmProxy.getExpectedObjectSchemaInfo());
        map.put(RadarGroup.class, com_brgd_brblmesh_GeneralAdapter_model_RadarGroupRealmProxy.getExpectedObjectSchemaInfo());
        map.put(RadarDevice.class, com_brgd_brblmesh_GeneralAdapter_model_RadarDeviceRealmProxy.getExpectedObjectSchemaInfo());
        map.put(PhoneType.class, com_brgd_brblmesh_GeneralAdapter_model_PhoneTypeRealmProxy.getExpectedObjectSchemaInfo());
        map.put(ModDiyColor.class, com_brgd_brblmesh_GeneralAdapter_model_ModDiyColorRealmProxy.getExpectedObjectSchemaInfo());
        map.put(Mod.class, com_brgd_brblmesh_GeneralAdapter_model_ModRealmProxy.getExpectedObjectSchemaInfo());
        map.put(Groups.class, com_brgd_brblmesh_GeneralAdapter_model_GroupsRealmProxy.getExpectedObjectSchemaInfo());
        map.put(FixedGroup.class, com_brgd_brblmesh_GeneralAdapter_model_FixedGroupRealmProxy.getExpectedObjectSchemaInfo());
        map.put(DiyColor.class, com_brgd_brblmesh_GeneralAdapter_model_DiyColorRealmProxy.getExpectedObjectSchemaInfo());
        map.put(DeleteRadarGroup.class, com_brgd_brblmesh_GeneralAdapter_model_DeleteRadarGroupRealmProxy.getExpectedObjectSchemaInfo());
        map.put(DeleteGroups.class, com_brgd_brblmesh_GeneralAdapter_model_DeleteGroupsRealmProxy.getExpectedObjectSchemaInfo());
        map.put(DeleteFixedGroup.class, com_brgd_brblmesh_GeneralAdapter_model_DeleteFixedGroupRealmProxy.getExpectedObjectSchemaInfo());
        map.put(BleDevice.class, com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxy.getExpectedObjectSchemaInfo());
        return map;
    }

    @Override // io.realm.internal.RealmProxyMediator
    public ColumnInfo createColumnInfo(Class<? extends RealmModel> cls, OsSchemaInfo osSchemaInfo) {
        checkClass(cls);
        if (cls.equals(Timer.class)) {
            return com_brgd_brblmesh_GeneralAdapter_model_TimerRealmProxy.createColumnInfo(osSchemaInfo);
        }
        if (cls.equals(SceneDevice.class)) {
            return com_brgd_brblmesh_GeneralAdapter_model_SceneDeviceRealmProxy.createColumnInfo(osSchemaInfo);
        }
        if (cls.equals(Scene.class)) {
            return com_brgd_brblmesh_GeneralAdapter_model_SceneRealmProxy.createColumnInfo(osSchemaInfo);
        }
        if (cls.equals(RadarTimer.class)) {
            return com_brgd_brblmesh_GeneralAdapter_model_RadarTimerRealmProxy.createColumnInfo(osSchemaInfo);
        }
        if (cls.equals(RadarPhoneType.class)) {
            return com_brgd_brblmesh_GeneralAdapter_model_RadarPhoneTypeRealmProxy.createColumnInfo(osSchemaInfo);
        }
        if (cls.equals(RadarGroup.class)) {
            return com_brgd_brblmesh_GeneralAdapter_model_RadarGroupRealmProxy.createColumnInfo(osSchemaInfo);
        }
        if (cls.equals(RadarDevice.class)) {
            return com_brgd_brblmesh_GeneralAdapter_model_RadarDeviceRealmProxy.createColumnInfo(osSchemaInfo);
        }
        if (cls.equals(PhoneType.class)) {
            return com_brgd_brblmesh_GeneralAdapter_model_PhoneTypeRealmProxy.createColumnInfo(osSchemaInfo);
        }
        if (cls.equals(ModDiyColor.class)) {
            return com_brgd_brblmesh_GeneralAdapter_model_ModDiyColorRealmProxy.createColumnInfo(osSchemaInfo);
        }
        if (cls.equals(Mod.class)) {
            return com_brgd_brblmesh_GeneralAdapter_model_ModRealmProxy.createColumnInfo(osSchemaInfo);
        }
        if (cls.equals(Groups.class)) {
            return com_brgd_brblmesh_GeneralAdapter_model_GroupsRealmProxy.createColumnInfo(osSchemaInfo);
        }
        if (cls.equals(FixedGroup.class)) {
            return com_brgd_brblmesh_GeneralAdapter_model_FixedGroupRealmProxy.createColumnInfo(osSchemaInfo);
        }
        if (cls.equals(DiyColor.class)) {
            return com_brgd_brblmesh_GeneralAdapter_model_DiyColorRealmProxy.createColumnInfo(osSchemaInfo);
        }
        if (cls.equals(DeleteRadarGroup.class)) {
            return com_brgd_brblmesh_GeneralAdapter_model_DeleteRadarGroupRealmProxy.createColumnInfo(osSchemaInfo);
        }
        if (cls.equals(DeleteGroups.class)) {
            return com_brgd_brblmesh_GeneralAdapter_model_DeleteGroupsRealmProxy.createColumnInfo(osSchemaInfo);
        }
        if (cls.equals(DeleteFixedGroup.class)) {
            return com_brgd_brblmesh_GeneralAdapter_model_DeleteFixedGroupRealmProxy.createColumnInfo(osSchemaInfo);
        }
        if (cls.equals(BleDevice.class)) {
            return com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxy.createColumnInfo(osSchemaInfo);
        }
        throw getMissingProxyClassException(cls);
    }

    @Override // io.realm.internal.RealmProxyMediator
    public String getSimpleClassNameImpl(Class<? extends RealmModel> cls) {
        checkClass(cls);
        if (cls.equals(Timer.class)) {
            return "Timer";
        }
        if (cls.equals(SceneDevice.class)) {
            return com_brgd_brblmesh_GeneralAdapter_model_SceneDeviceRealmProxy.ClassNameHelper.INTERNAL_CLASS_NAME;
        }
        if (cls.equals(Scene.class)) {
            return "Scene";
        }
        if (cls.equals(RadarTimer.class)) {
            return com_brgd_brblmesh_GeneralAdapter_model_RadarTimerRealmProxy.ClassNameHelper.INTERNAL_CLASS_NAME;
        }
        if (cls.equals(RadarPhoneType.class)) {
            return com_brgd_brblmesh_GeneralAdapter_model_RadarPhoneTypeRealmProxy.ClassNameHelper.INTERNAL_CLASS_NAME;
        }
        if (cls.equals(RadarGroup.class)) {
            return com_brgd_brblmesh_GeneralAdapter_model_RadarGroupRealmProxy.ClassNameHelper.INTERNAL_CLASS_NAME;
        }
        if (cls.equals(RadarDevice.class)) {
            return com_brgd_brblmesh_GeneralAdapter_model_RadarDeviceRealmProxy.ClassNameHelper.INTERNAL_CLASS_NAME;
        }
        if (cls.equals(PhoneType.class)) {
            return com_brgd_brblmesh_GeneralAdapter_model_PhoneTypeRealmProxy.ClassNameHelper.INTERNAL_CLASS_NAME;
        }
        if (cls.equals(ModDiyColor.class)) {
            return com_brgd_brblmesh_GeneralAdapter_model_ModDiyColorRealmProxy.ClassNameHelper.INTERNAL_CLASS_NAME;
        }
        if (cls.equals(Mod.class)) {
            return "Mod";
        }
        if (cls.equals(Groups.class)) {
            return com_brgd_brblmesh_GeneralAdapter_model_GroupsRealmProxy.ClassNameHelper.INTERNAL_CLASS_NAME;
        }
        if (cls.equals(FixedGroup.class)) {
            return com_brgd_brblmesh_GeneralAdapter_model_FixedGroupRealmProxy.ClassNameHelper.INTERNAL_CLASS_NAME;
        }
        if (cls.equals(DiyColor.class)) {
            return "DiyColor";
        }
        if (cls.equals(DeleteRadarGroup.class)) {
            return com_brgd_brblmesh_GeneralAdapter_model_DeleteRadarGroupRealmProxy.ClassNameHelper.INTERNAL_CLASS_NAME;
        }
        if (cls.equals(DeleteGroups.class)) {
            return com_brgd_brblmesh_GeneralAdapter_model_DeleteGroupsRealmProxy.ClassNameHelper.INTERNAL_CLASS_NAME;
        }
        if (cls.equals(DeleteFixedGroup.class)) {
            return com_brgd_brblmesh_GeneralAdapter_model_DeleteFixedGroupRealmProxy.ClassNameHelper.INTERNAL_CLASS_NAME;
        }
        if (cls.equals(BleDevice.class)) {
            return com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxy.ClassNameHelper.INTERNAL_CLASS_NAME;
        }
        throw getMissingProxyClassException(cls);
    }

    @Override // io.realm.internal.RealmProxyMediator
    public Class<? extends RealmModel> getClazzImpl(String str) {
        checkClassName(str);
        if (str.equals("Timer")) {
            return Timer.class;
        }
        if (str.equals(com_brgd_brblmesh_GeneralAdapter_model_SceneDeviceRealmProxy.ClassNameHelper.INTERNAL_CLASS_NAME)) {
            return SceneDevice.class;
        }
        if (str.equals("Scene")) {
            return Scene.class;
        }
        if (str.equals(com_brgd_brblmesh_GeneralAdapter_model_RadarTimerRealmProxy.ClassNameHelper.INTERNAL_CLASS_NAME)) {
            return RadarTimer.class;
        }
        if (str.equals(com_brgd_brblmesh_GeneralAdapter_model_RadarPhoneTypeRealmProxy.ClassNameHelper.INTERNAL_CLASS_NAME)) {
            return RadarPhoneType.class;
        }
        if (str.equals(com_brgd_brblmesh_GeneralAdapter_model_RadarGroupRealmProxy.ClassNameHelper.INTERNAL_CLASS_NAME)) {
            return RadarGroup.class;
        }
        if (str.equals(com_brgd_brblmesh_GeneralAdapter_model_RadarDeviceRealmProxy.ClassNameHelper.INTERNAL_CLASS_NAME)) {
            return RadarDevice.class;
        }
        if (str.equals(com_brgd_brblmesh_GeneralAdapter_model_PhoneTypeRealmProxy.ClassNameHelper.INTERNAL_CLASS_NAME)) {
            return PhoneType.class;
        }
        if (str.equals(com_brgd_brblmesh_GeneralAdapter_model_ModDiyColorRealmProxy.ClassNameHelper.INTERNAL_CLASS_NAME)) {
            return ModDiyColor.class;
        }
        if (str.equals("Mod")) {
            return Mod.class;
        }
        if (str.equals(com_brgd_brblmesh_GeneralAdapter_model_GroupsRealmProxy.ClassNameHelper.INTERNAL_CLASS_NAME)) {
            return Groups.class;
        }
        if (str.equals(com_brgd_brblmesh_GeneralAdapter_model_FixedGroupRealmProxy.ClassNameHelper.INTERNAL_CLASS_NAME)) {
            return FixedGroup.class;
        }
        if (str.equals("DiyColor")) {
            return DiyColor.class;
        }
        if (str.equals(com_brgd_brblmesh_GeneralAdapter_model_DeleteRadarGroupRealmProxy.ClassNameHelper.INTERNAL_CLASS_NAME)) {
            return DeleteRadarGroup.class;
        }
        if (str.equals(com_brgd_brblmesh_GeneralAdapter_model_DeleteGroupsRealmProxy.ClassNameHelper.INTERNAL_CLASS_NAME)) {
            return DeleteGroups.class;
        }
        if (str.equals(com_brgd_brblmesh_GeneralAdapter_model_DeleteFixedGroupRealmProxy.ClassNameHelper.INTERNAL_CLASS_NAME)) {
            return DeleteFixedGroup.class;
        }
        if (str.equals(com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxy.ClassNameHelper.INTERNAL_CLASS_NAME)) {
            return BleDevice.class;
        }
        throw getMissingProxyClassException(str);
    }

    @Override // io.realm.internal.RealmProxyMediator
    public <E extends RealmModel> E newInstance(Class<E> cls, Object obj, Row row, ColumnInfo columnInfo, boolean z, List<String> list) {
        BaseRealm.RealmObjectContext realmObjectContext = BaseRealm.objectContext.get();
        try {
            realmObjectContext.set((BaseRealm) obj, row, columnInfo, z, list);
            checkClass(cls);
            if (cls.equals(Timer.class)) {
                return cls.cast(new com_brgd_brblmesh_GeneralAdapter_model_TimerRealmProxy());
            }
            if (cls.equals(SceneDevice.class)) {
                return cls.cast(new com_brgd_brblmesh_GeneralAdapter_model_SceneDeviceRealmProxy());
            }
            if (cls.equals(Scene.class)) {
                return cls.cast(new com_brgd_brblmesh_GeneralAdapter_model_SceneRealmProxy());
            }
            if (cls.equals(RadarTimer.class)) {
                return cls.cast(new com_brgd_brblmesh_GeneralAdapter_model_RadarTimerRealmProxy());
            }
            if (cls.equals(RadarPhoneType.class)) {
                return cls.cast(new com_brgd_brblmesh_GeneralAdapter_model_RadarPhoneTypeRealmProxy());
            }
            if (cls.equals(RadarGroup.class)) {
                return cls.cast(new com_brgd_brblmesh_GeneralAdapter_model_RadarGroupRealmProxy());
            }
            if (cls.equals(RadarDevice.class)) {
                return cls.cast(new com_brgd_brblmesh_GeneralAdapter_model_RadarDeviceRealmProxy());
            }
            if (cls.equals(PhoneType.class)) {
                return cls.cast(new com_brgd_brblmesh_GeneralAdapter_model_PhoneTypeRealmProxy());
            }
            if (cls.equals(ModDiyColor.class)) {
                return cls.cast(new com_brgd_brblmesh_GeneralAdapter_model_ModDiyColorRealmProxy());
            }
            if (cls.equals(Mod.class)) {
                return cls.cast(new com_brgd_brblmesh_GeneralAdapter_model_ModRealmProxy());
            }
            if (cls.equals(Groups.class)) {
                return cls.cast(new com_brgd_brblmesh_GeneralAdapter_model_GroupsRealmProxy());
            }
            if (cls.equals(FixedGroup.class)) {
                return cls.cast(new com_brgd_brblmesh_GeneralAdapter_model_FixedGroupRealmProxy());
            }
            if (cls.equals(DiyColor.class)) {
                return cls.cast(new com_brgd_brblmesh_GeneralAdapter_model_DiyColorRealmProxy());
            }
            if (cls.equals(DeleteRadarGroup.class)) {
                return cls.cast(new com_brgd_brblmesh_GeneralAdapter_model_DeleteRadarGroupRealmProxy());
            }
            if (cls.equals(DeleteGroups.class)) {
                return cls.cast(new com_brgd_brblmesh_GeneralAdapter_model_DeleteGroupsRealmProxy());
            }
            if (cls.equals(DeleteFixedGroup.class)) {
                return cls.cast(new com_brgd_brblmesh_GeneralAdapter_model_DeleteFixedGroupRealmProxy());
            }
            if (cls.equals(BleDevice.class)) {
                return cls.cast(new com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxy());
            }
            throw getMissingProxyClassException((Class<? extends RealmModel>) cls);
        } finally {
            realmObjectContext.clear();
        }
    }

    @Override // io.realm.internal.RealmProxyMediator
    public Set<Class<? extends RealmModel>> getModelClasses() {
        return MODEL_CLASSES;
    }

    @Override // io.realm.internal.RealmProxyMediator
    public <E extends RealmModel> E copyOrUpdate(Realm realm, E e, boolean z, Map<RealmModel, RealmObjectProxy> map, Set<ImportFlag> set) {
        Class<?> superclass = e instanceof RealmObjectProxy ? e.getClass().getSuperclass() : e.getClass();
        if (superclass.equals(Timer.class)) {
            return (E) superclass.cast(com_brgd_brblmesh_GeneralAdapter_model_TimerRealmProxy.copyOrUpdate(realm, (com_brgd_brblmesh_GeneralAdapter_model_TimerRealmProxy.TimerColumnInfo) realm.getSchema().getColumnInfo(Timer.class), (Timer) e, z, map, set));
        }
        if (superclass.equals(SceneDevice.class)) {
            return (E) superclass.cast(com_brgd_brblmesh_GeneralAdapter_model_SceneDeviceRealmProxy.copyOrUpdate(realm, (com_brgd_brblmesh_GeneralAdapter_model_SceneDeviceRealmProxy.SceneDeviceColumnInfo) realm.getSchema().getColumnInfo(SceneDevice.class), (SceneDevice) e, z, map, set));
        }
        if (superclass.equals(Scene.class)) {
            return (E) superclass.cast(com_brgd_brblmesh_GeneralAdapter_model_SceneRealmProxy.copyOrUpdate(realm, (com_brgd_brblmesh_GeneralAdapter_model_SceneRealmProxy.SceneColumnInfo) realm.getSchema().getColumnInfo(Scene.class), (Scene) e, z, map, set));
        }
        if (superclass.equals(RadarTimer.class)) {
            return (E) superclass.cast(com_brgd_brblmesh_GeneralAdapter_model_RadarTimerRealmProxy.copyOrUpdate(realm, (com_brgd_brblmesh_GeneralAdapter_model_RadarTimerRealmProxy.RadarTimerColumnInfo) realm.getSchema().getColumnInfo(RadarTimer.class), (RadarTimer) e, z, map, set));
        }
        if (superclass.equals(RadarPhoneType.class)) {
            return (E) superclass.cast(com_brgd_brblmesh_GeneralAdapter_model_RadarPhoneTypeRealmProxy.copyOrUpdate(realm, (com_brgd_brblmesh_GeneralAdapter_model_RadarPhoneTypeRealmProxy.RadarPhoneTypeColumnInfo) realm.getSchema().getColumnInfo(RadarPhoneType.class), (RadarPhoneType) e, z, map, set));
        }
        if (superclass.equals(RadarGroup.class)) {
            return (E) superclass.cast(com_brgd_brblmesh_GeneralAdapter_model_RadarGroupRealmProxy.copyOrUpdate(realm, (com_brgd_brblmesh_GeneralAdapter_model_RadarGroupRealmProxy.RadarGroupColumnInfo) realm.getSchema().getColumnInfo(RadarGroup.class), (RadarGroup) e, z, map, set));
        }
        if (superclass.equals(RadarDevice.class)) {
            return (E) superclass.cast(com_brgd_brblmesh_GeneralAdapter_model_RadarDeviceRealmProxy.copyOrUpdate(realm, (com_brgd_brblmesh_GeneralAdapter_model_RadarDeviceRealmProxy.RadarDeviceColumnInfo) realm.getSchema().getColumnInfo(RadarDevice.class), (RadarDevice) e, z, map, set));
        }
        if (superclass.equals(PhoneType.class)) {
            return (E) superclass.cast(com_brgd_brblmesh_GeneralAdapter_model_PhoneTypeRealmProxy.copyOrUpdate(realm, (com_brgd_brblmesh_GeneralAdapter_model_PhoneTypeRealmProxy.PhoneTypeColumnInfo) realm.getSchema().getColumnInfo(PhoneType.class), (PhoneType) e, z, map, set));
        }
        if (superclass.equals(ModDiyColor.class)) {
            return (E) superclass.cast(com_brgd_brblmesh_GeneralAdapter_model_ModDiyColorRealmProxy.copyOrUpdate(realm, (com_brgd_brblmesh_GeneralAdapter_model_ModDiyColorRealmProxy.ModDiyColorColumnInfo) realm.getSchema().getColumnInfo(ModDiyColor.class), (ModDiyColor) e, z, map, set));
        }
        if (superclass.equals(Mod.class)) {
            return (E) superclass.cast(com_brgd_brblmesh_GeneralAdapter_model_ModRealmProxy.copyOrUpdate(realm, (com_brgd_brblmesh_GeneralAdapter_model_ModRealmProxy.ModColumnInfo) realm.getSchema().getColumnInfo(Mod.class), (Mod) e, z, map, set));
        }
        if (superclass.equals(Groups.class)) {
            return (E) superclass.cast(com_brgd_brblmesh_GeneralAdapter_model_GroupsRealmProxy.copyOrUpdate(realm, (com_brgd_brblmesh_GeneralAdapter_model_GroupsRealmProxy.GroupsColumnInfo) realm.getSchema().getColumnInfo(Groups.class), (Groups) e, z, map, set));
        }
        if (superclass.equals(FixedGroup.class)) {
            return (E) superclass.cast(com_brgd_brblmesh_GeneralAdapter_model_FixedGroupRealmProxy.copyOrUpdate(realm, (com_brgd_brblmesh_GeneralAdapter_model_FixedGroupRealmProxy.FixedGroupColumnInfo) realm.getSchema().getColumnInfo(FixedGroup.class), (FixedGroup) e, z, map, set));
        }
        if (superclass.equals(DiyColor.class)) {
            return (E) superclass.cast(com_brgd_brblmesh_GeneralAdapter_model_DiyColorRealmProxy.copyOrUpdate(realm, (com_brgd_brblmesh_GeneralAdapter_model_DiyColorRealmProxy.DiyColorColumnInfo) realm.getSchema().getColumnInfo(DiyColor.class), (DiyColor) e, z, map, set));
        }
        if (superclass.equals(DeleteRadarGroup.class)) {
            return (E) superclass.cast(com_brgd_brblmesh_GeneralAdapter_model_DeleteRadarGroupRealmProxy.copyOrUpdate(realm, (com_brgd_brblmesh_GeneralAdapter_model_DeleteRadarGroupRealmProxy.DeleteRadarGroupColumnInfo) realm.getSchema().getColumnInfo(DeleteRadarGroup.class), (DeleteRadarGroup) e, z, map, set));
        }
        if (superclass.equals(DeleteGroups.class)) {
            return (E) superclass.cast(com_brgd_brblmesh_GeneralAdapter_model_DeleteGroupsRealmProxy.copyOrUpdate(realm, (com_brgd_brblmesh_GeneralAdapter_model_DeleteGroupsRealmProxy.DeleteGroupsColumnInfo) realm.getSchema().getColumnInfo(DeleteGroups.class), (DeleteGroups) e, z, map, set));
        }
        if (superclass.equals(DeleteFixedGroup.class)) {
            return (E) superclass.cast(com_brgd_brblmesh_GeneralAdapter_model_DeleteFixedGroupRealmProxy.copyOrUpdate(realm, (com_brgd_brblmesh_GeneralAdapter_model_DeleteFixedGroupRealmProxy.DeleteFixedGroupColumnInfo) realm.getSchema().getColumnInfo(DeleteFixedGroup.class), (DeleteFixedGroup) e, z, map, set));
        }
        if (superclass.equals(BleDevice.class)) {
            return (E) superclass.cast(com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxy.copyOrUpdate(realm, (com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxy.BleDeviceColumnInfo) realm.getSchema().getColumnInfo(BleDevice.class), (BleDevice) e, z, map, set));
        }
        throw getMissingProxyClassException((Class<? extends RealmModel>) superclass);
    }

    @Override // io.realm.internal.RealmProxyMediator
    public long insert(Realm realm, RealmModel realmModel, Map<RealmModel, Long> map) {
        Class<?> superclass = realmModel instanceof RealmObjectProxy ? realmModel.getClass().getSuperclass() : realmModel.getClass();
        if (superclass.equals(Timer.class)) {
            return com_brgd_brblmesh_GeneralAdapter_model_TimerRealmProxy.insert(realm, (Timer) realmModel, map);
        }
        if (superclass.equals(SceneDevice.class)) {
            return com_brgd_brblmesh_GeneralAdapter_model_SceneDeviceRealmProxy.insert(realm, (SceneDevice) realmModel, map);
        }
        if (superclass.equals(Scene.class)) {
            return com_brgd_brblmesh_GeneralAdapter_model_SceneRealmProxy.insert(realm, (Scene) realmModel, map);
        }
        if (superclass.equals(RadarTimer.class)) {
            return com_brgd_brblmesh_GeneralAdapter_model_RadarTimerRealmProxy.insert(realm, (RadarTimer) realmModel, map);
        }
        if (superclass.equals(RadarPhoneType.class)) {
            return com_brgd_brblmesh_GeneralAdapter_model_RadarPhoneTypeRealmProxy.insert(realm, (RadarPhoneType) realmModel, map);
        }
        if (superclass.equals(RadarGroup.class)) {
            return com_brgd_brblmesh_GeneralAdapter_model_RadarGroupRealmProxy.insert(realm, (RadarGroup) realmModel, map);
        }
        if (superclass.equals(RadarDevice.class)) {
            return com_brgd_brblmesh_GeneralAdapter_model_RadarDeviceRealmProxy.insert(realm, (RadarDevice) realmModel, map);
        }
        if (superclass.equals(PhoneType.class)) {
            return com_brgd_brblmesh_GeneralAdapter_model_PhoneTypeRealmProxy.insert(realm, (PhoneType) realmModel, map);
        }
        if (superclass.equals(ModDiyColor.class)) {
            return com_brgd_brblmesh_GeneralAdapter_model_ModDiyColorRealmProxy.insert(realm, (ModDiyColor) realmModel, map);
        }
        if (superclass.equals(Mod.class)) {
            return com_brgd_brblmesh_GeneralAdapter_model_ModRealmProxy.insert(realm, (Mod) realmModel, map);
        }
        if (superclass.equals(Groups.class)) {
            return com_brgd_brblmesh_GeneralAdapter_model_GroupsRealmProxy.insert(realm, (Groups) realmModel, map);
        }
        if (superclass.equals(FixedGroup.class)) {
            return com_brgd_brblmesh_GeneralAdapter_model_FixedGroupRealmProxy.insert(realm, (FixedGroup) realmModel, map);
        }
        if (superclass.equals(DiyColor.class)) {
            return com_brgd_brblmesh_GeneralAdapter_model_DiyColorRealmProxy.insert(realm, (DiyColor) realmModel, map);
        }
        if (superclass.equals(DeleteRadarGroup.class)) {
            return com_brgd_brblmesh_GeneralAdapter_model_DeleteRadarGroupRealmProxy.insert(realm, (DeleteRadarGroup) realmModel, map);
        }
        if (superclass.equals(DeleteGroups.class)) {
            return com_brgd_brblmesh_GeneralAdapter_model_DeleteGroupsRealmProxy.insert(realm, (DeleteGroups) realmModel, map);
        }
        if (superclass.equals(DeleteFixedGroup.class)) {
            return com_brgd_brblmesh_GeneralAdapter_model_DeleteFixedGroupRealmProxy.insert(realm, (DeleteFixedGroup) realmModel, map);
        }
        if (superclass.equals(BleDevice.class)) {
            return com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxy.insert(realm, (BleDevice) realmModel, map);
        }
        throw getMissingProxyClassException((Class<? extends RealmModel>) superclass);
    }

    @Override // io.realm.internal.RealmProxyMediator
    public void insert(Realm realm, Collection<? extends RealmModel> collection) {
        Iterator<? extends RealmModel> it = collection.iterator();
        HashMap map = new HashMap(collection.size());
        if (it.hasNext()) {
            RealmModel next = it.next();
            Class<?> superclass = next instanceof RealmObjectProxy ? next.getClass().getSuperclass() : next.getClass();
            if (superclass.equals(Timer.class)) {
                com_brgd_brblmesh_GeneralAdapter_model_TimerRealmProxy.insert(realm, (Timer) next, map);
            } else if (superclass.equals(SceneDevice.class)) {
                com_brgd_brblmesh_GeneralAdapter_model_SceneDeviceRealmProxy.insert(realm, (SceneDevice) next, map);
            } else if (superclass.equals(Scene.class)) {
                com_brgd_brblmesh_GeneralAdapter_model_SceneRealmProxy.insert(realm, (Scene) next, map);
            } else if (superclass.equals(RadarTimer.class)) {
                com_brgd_brblmesh_GeneralAdapter_model_RadarTimerRealmProxy.insert(realm, (RadarTimer) next, map);
            } else if (superclass.equals(RadarPhoneType.class)) {
                com_brgd_brblmesh_GeneralAdapter_model_RadarPhoneTypeRealmProxy.insert(realm, (RadarPhoneType) next, map);
            } else if (superclass.equals(RadarGroup.class)) {
                com_brgd_brblmesh_GeneralAdapter_model_RadarGroupRealmProxy.insert(realm, (RadarGroup) next, map);
            } else if (superclass.equals(RadarDevice.class)) {
                com_brgd_brblmesh_GeneralAdapter_model_RadarDeviceRealmProxy.insert(realm, (RadarDevice) next, map);
            } else if (superclass.equals(PhoneType.class)) {
                com_brgd_brblmesh_GeneralAdapter_model_PhoneTypeRealmProxy.insert(realm, (PhoneType) next, map);
            } else if (superclass.equals(ModDiyColor.class)) {
                com_brgd_brblmesh_GeneralAdapter_model_ModDiyColorRealmProxy.insert(realm, (ModDiyColor) next, map);
            } else if (superclass.equals(Mod.class)) {
                com_brgd_brblmesh_GeneralAdapter_model_ModRealmProxy.insert(realm, (Mod) next, map);
            } else if (superclass.equals(Groups.class)) {
                com_brgd_brblmesh_GeneralAdapter_model_GroupsRealmProxy.insert(realm, (Groups) next, map);
            } else if (superclass.equals(FixedGroup.class)) {
                com_brgd_brblmesh_GeneralAdapter_model_FixedGroupRealmProxy.insert(realm, (FixedGroup) next, map);
            } else if (superclass.equals(DiyColor.class)) {
                com_brgd_brblmesh_GeneralAdapter_model_DiyColorRealmProxy.insert(realm, (DiyColor) next, map);
            } else if (superclass.equals(DeleteRadarGroup.class)) {
                com_brgd_brblmesh_GeneralAdapter_model_DeleteRadarGroupRealmProxy.insert(realm, (DeleteRadarGroup) next, map);
            } else if (superclass.equals(DeleteGroups.class)) {
                com_brgd_brblmesh_GeneralAdapter_model_DeleteGroupsRealmProxy.insert(realm, (DeleteGroups) next, map);
            } else if (superclass.equals(DeleteFixedGroup.class)) {
                com_brgd_brblmesh_GeneralAdapter_model_DeleteFixedGroupRealmProxy.insert(realm, (DeleteFixedGroup) next, map);
            } else if (superclass.equals(BleDevice.class)) {
                com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxy.insert(realm, (BleDevice) next, map);
            } else {
                throw getMissingProxyClassException((Class<? extends RealmModel>) superclass);
            }
            if (it.hasNext()) {
                if (superclass.equals(Timer.class)) {
                    com_brgd_brblmesh_GeneralAdapter_model_TimerRealmProxy.insert(realm, it, map);
                    return;
                }
                if (superclass.equals(SceneDevice.class)) {
                    com_brgd_brblmesh_GeneralAdapter_model_SceneDeviceRealmProxy.insert(realm, it, map);
                    return;
                }
                if (superclass.equals(Scene.class)) {
                    com_brgd_brblmesh_GeneralAdapter_model_SceneRealmProxy.insert(realm, it, map);
                    return;
                }
                if (superclass.equals(RadarTimer.class)) {
                    com_brgd_brblmesh_GeneralAdapter_model_RadarTimerRealmProxy.insert(realm, it, map);
                    return;
                }
                if (superclass.equals(RadarPhoneType.class)) {
                    com_brgd_brblmesh_GeneralAdapter_model_RadarPhoneTypeRealmProxy.insert(realm, it, map);
                    return;
                }
                if (superclass.equals(RadarGroup.class)) {
                    com_brgd_brblmesh_GeneralAdapter_model_RadarGroupRealmProxy.insert(realm, it, map);
                    return;
                }
                if (superclass.equals(RadarDevice.class)) {
                    com_brgd_brblmesh_GeneralAdapter_model_RadarDeviceRealmProxy.insert(realm, it, map);
                    return;
                }
                if (superclass.equals(PhoneType.class)) {
                    com_brgd_brblmesh_GeneralAdapter_model_PhoneTypeRealmProxy.insert(realm, it, map);
                    return;
                }
                if (superclass.equals(ModDiyColor.class)) {
                    com_brgd_brblmesh_GeneralAdapter_model_ModDiyColorRealmProxy.insert(realm, it, map);
                    return;
                }
                if (superclass.equals(Mod.class)) {
                    com_brgd_brblmesh_GeneralAdapter_model_ModRealmProxy.insert(realm, it, map);
                    return;
                }
                if (superclass.equals(Groups.class)) {
                    com_brgd_brblmesh_GeneralAdapter_model_GroupsRealmProxy.insert(realm, it, map);
                    return;
                }
                if (superclass.equals(FixedGroup.class)) {
                    com_brgd_brblmesh_GeneralAdapter_model_FixedGroupRealmProxy.insert(realm, it, map);
                    return;
                }
                if (superclass.equals(DiyColor.class)) {
                    com_brgd_brblmesh_GeneralAdapter_model_DiyColorRealmProxy.insert(realm, it, map);
                    return;
                }
                if (superclass.equals(DeleteRadarGroup.class)) {
                    com_brgd_brblmesh_GeneralAdapter_model_DeleteRadarGroupRealmProxy.insert(realm, it, map);
                    return;
                }
                if (superclass.equals(DeleteGroups.class)) {
                    com_brgd_brblmesh_GeneralAdapter_model_DeleteGroupsRealmProxy.insert(realm, it, map);
                } else if (superclass.equals(DeleteFixedGroup.class)) {
                    com_brgd_brblmesh_GeneralAdapter_model_DeleteFixedGroupRealmProxy.insert(realm, it, map);
                } else {
                    if (superclass.equals(BleDevice.class)) {
                        com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxy.insert(realm, it, map);
                        return;
                    }
                    throw getMissingProxyClassException((Class<? extends RealmModel>) superclass);
                }
            }
        }
    }

    @Override // io.realm.internal.RealmProxyMediator
    public long insertOrUpdate(Realm realm, RealmModel realmModel, Map<RealmModel, Long> map) {
        Class<?> superclass = realmModel instanceof RealmObjectProxy ? realmModel.getClass().getSuperclass() : realmModel.getClass();
        if (superclass.equals(Timer.class)) {
            return com_brgd_brblmesh_GeneralAdapter_model_TimerRealmProxy.insertOrUpdate(realm, (Timer) realmModel, map);
        }
        if (superclass.equals(SceneDevice.class)) {
            return com_brgd_brblmesh_GeneralAdapter_model_SceneDeviceRealmProxy.insertOrUpdate(realm, (SceneDevice) realmModel, map);
        }
        if (superclass.equals(Scene.class)) {
            return com_brgd_brblmesh_GeneralAdapter_model_SceneRealmProxy.insertOrUpdate(realm, (Scene) realmModel, map);
        }
        if (superclass.equals(RadarTimer.class)) {
            return com_brgd_brblmesh_GeneralAdapter_model_RadarTimerRealmProxy.insertOrUpdate(realm, (RadarTimer) realmModel, map);
        }
        if (superclass.equals(RadarPhoneType.class)) {
            return com_brgd_brblmesh_GeneralAdapter_model_RadarPhoneTypeRealmProxy.insertOrUpdate(realm, (RadarPhoneType) realmModel, map);
        }
        if (superclass.equals(RadarGroup.class)) {
            return com_brgd_brblmesh_GeneralAdapter_model_RadarGroupRealmProxy.insertOrUpdate(realm, (RadarGroup) realmModel, map);
        }
        if (superclass.equals(RadarDevice.class)) {
            return com_brgd_brblmesh_GeneralAdapter_model_RadarDeviceRealmProxy.insertOrUpdate(realm, (RadarDevice) realmModel, map);
        }
        if (superclass.equals(PhoneType.class)) {
            return com_brgd_brblmesh_GeneralAdapter_model_PhoneTypeRealmProxy.insertOrUpdate(realm, (PhoneType) realmModel, map);
        }
        if (superclass.equals(ModDiyColor.class)) {
            return com_brgd_brblmesh_GeneralAdapter_model_ModDiyColorRealmProxy.insertOrUpdate(realm, (ModDiyColor) realmModel, map);
        }
        if (superclass.equals(Mod.class)) {
            return com_brgd_brblmesh_GeneralAdapter_model_ModRealmProxy.insertOrUpdate(realm, (Mod) realmModel, map);
        }
        if (superclass.equals(Groups.class)) {
            return com_brgd_brblmesh_GeneralAdapter_model_GroupsRealmProxy.insertOrUpdate(realm, (Groups) realmModel, map);
        }
        if (superclass.equals(FixedGroup.class)) {
            return com_brgd_brblmesh_GeneralAdapter_model_FixedGroupRealmProxy.insertOrUpdate(realm, (FixedGroup) realmModel, map);
        }
        if (superclass.equals(DiyColor.class)) {
            return com_brgd_brblmesh_GeneralAdapter_model_DiyColorRealmProxy.insertOrUpdate(realm, (DiyColor) realmModel, map);
        }
        if (superclass.equals(DeleteRadarGroup.class)) {
            return com_brgd_brblmesh_GeneralAdapter_model_DeleteRadarGroupRealmProxy.insertOrUpdate(realm, (DeleteRadarGroup) realmModel, map);
        }
        if (superclass.equals(DeleteGroups.class)) {
            return com_brgd_brblmesh_GeneralAdapter_model_DeleteGroupsRealmProxy.insertOrUpdate(realm, (DeleteGroups) realmModel, map);
        }
        if (superclass.equals(DeleteFixedGroup.class)) {
            return com_brgd_brblmesh_GeneralAdapter_model_DeleteFixedGroupRealmProxy.insertOrUpdate(realm, (DeleteFixedGroup) realmModel, map);
        }
        if (superclass.equals(BleDevice.class)) {
            return com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxy.insertOrUpdate(realm, (BleDevice) realmModel, map);
        }
        throw getMissingProxyClassException((Class<? extends RealmModel>) superclass);
    }

    @Override // io.realm.internal.RealmProxyMediator
    public void insertOrUpdate(Realm realm, Collection<? extends RealmModel> collection) {
        Iterator<? extends RealmModel> it = collection.iterator();
        HashMap map = new HashMap(collection.size());
        if (it.hasNext()) {
            RealmModel next = it.next();
            Class<?> superclass = next instanceof RealmObjectProxy ? next.getClass().getSuperclass() : next.getClass();
            if (superclass.equals(Timer.class)) {
                com_brgd_brblmesh_GeneralAdapter_model_TimerRealmProxy.insertOrUpdate(realm, (Timer) next, map);
            } else if (superclass.equals(SceneDevice.class)) {
                com_brgd_brblmesh_GeneralAdapter_model_SceneDeviceRealmProxy.insertOrUpdate(realm, (SceneDevice) next, map);
            } else if (superclass.equals(Scene.class)) {
                com_brgd_brblmesh_GeneralAdapter_model_SceneRealmProxy.insertOrUpdate(realm, (Scene) next, map);
            } else if (superclass.equals(RadarTimer.class)) {
                com_brgd_brblmesh_GeneralAdapter_model_RadarTimerRealmProxy.insertOrUpdate(realm, (RadarTimer) next, map);
            } else if (superclass.equals(RadarPhoneType.class)) {
                com_brgd_brblmesh_GeneralAdapter_model_RadarPhoneTypeRealmProxy.insertOrUpdate(realm, (RadarPhoneType) next, map);
            } else if (superclass.equals(RadarGroup.class)) {
                com_brgd_brblmesh_GeneralAdapter_model_RadarGroupRealmProxy.insertOrUpdate(realm, (RadarGroup) next, map);
            } else if (superclass.equals(RadarDevice.class)) {
                com_brgd_brblmesh_GeneralAdapter_model_RadarDeviceRealmProxy.insertOrUpdate(realm, (RadarDevice) next, map);
            } else if (superclass.equals(PhoneType.class)) {
                com_brgd_brblmesh_GeneralAdapter_model_PhoneTypeRealmProxy.insertOrUpdate(realm, (PhoneType) next, map);
            } else if (superclass.equals(ModDiyColor.class)) {
                com_brgd_brblmesh_GeneralAdapter_model_ModDiyColorRealmProxy.insertOrUpdate(realm, (ModDiyColor) next, map);
            } else if (superclass.equals(Mod.class)) {
                com_brgd_brblmesh_GeneralAdapter_model_ModRealmProxy.insertOrUpdate(realm, (Mod) next, map);
            } else if (superclass.equals(Groups.class)) {
                com_brgd_brblmesh_GeneralAdapter_model_GroupsRealmProxy.insertOrUpdate(realm, (Groups) next, map);
            } else if (superclass.equals(FixedGroup.class)) {
                com_brgd_brblmesh_GeneralAdapter_model_FixedGroupRealmProxy.insertOrUpdate(realm, (FixedGroup) next, map);
            } else if (superclass.equals(DiyColor.class)) {
                com_brgd_brblmesh_GeneralAdapter_model_DiyColorRealmProxy.insertOrUpdate(realm, (DiyColor) next, map);
            } else if (superclass.equals(DeleteRadarGroup.class)) {
                com_brgd_brblmesh_GeneralAdapter_model_DeleteRadarGroupRealmProxy.insertOrUpdate(realm, (DeleteRadarGroup) next, map);
            } else if (superclass.equals(DeleteGroups.class)) {
                com_brgd_brblmesh_GeneralAdapter_model_DeleteGroupsRealmProxy.insertOrUpdate(realm, (DeleteGroups) next, map);
            } else if (superclass.equals(DeleteFixedGroup.class)) {
                com_brgd_brblmesh_GeneralAdapter_model_DeleteFixedGroupRealmProxy.insertOrUpdate(realm, (DeleteFixedGroup) next, map);
            } else if (superclass.equals(BleDevice.class)) {
                com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxy.insertOrUpdate(realm, (BleDevice) next, map);
            } else {
                throw getMissingProxyClassException((Class<? extends RealmModel>) superclass);
            }
            if (it.hasNext()) {
                if (superclass.equals(Timer.class)) {
                    com_brgd_brblmesh_GeneralAdapter_model_TimerRealmProxy.insertOrUpdate(realm, it, map);
                    return;
                }
                if (superclass.equals(SceneDevice.class)) {
                    com_brgd_brblmesh_GeneralAdapter_model_SceneDeviceRealmProxy.insertOrUpdate(realm, it, map);
                    return;
                }
                if (superclass.equals(Scene.class)) {
                    com_brgd_brblmesh_GeneralAdapter_model_SceneRealmProxy.insertOrUpdate(realm, it, map);
                    return;
                }
                if (superclass.equals(RadarTimer.class)) {
                    com_brgd_brblmesh_GeneralAdapter_model_RadarTimerRealmProxy.insertOrUpdate(realm, it, map);
                    return;
                }
                if (superclass.equals(RadarPhoneType.class)) {
                    com_brgd_brblmesh_GeneralAdapter_model_RadarPhoneTypeRealmProxy.insertOrUpdate(realm, it, map);
                    return;
                }
                if (superclass.equals(RadarGroup.class)) {
                    com_brgd_brblmesh_GeneralAdapter_model_RadarGroupRealmProxy.insertOrUpdate(realm, it, map);
                    return;
                }
                if (superclass.equals(RadarDevice.class)) {
                    com_brgd_brblmesh_GeneralAdapter_model_RadarDeviceRealmProxy.insertOrUpdate(realm, it, map);
                    return;
                }
                if (superclass.equals(PhoneType.class)) {
                    com_brgd_brblmesh_GeneralAdapter_model_PhoneTypeRealmProxy.insertOrUpdate(realm, it, map);
                    return;
                }
                if (superclass.equals(ModDiyColor.class)) {
                    com_brgd_brblmesh_GeneralAdapter_model_ModDiyColorRealmProxy.insertOrUpdate(realm, it, map);
                    return;
                }
                if (superclass.equals(Mod.class)) {
                    com_brgd_brblmesh_GeneralAdapter_model_ModRealmProxy.insertOrUpdate(realm, it, map);
                    return;
                }
                if (superclass.equals(Groups.class)) {
                    com_brgd_brblmesh_GeneralAdapter_model_GroupsRealmProxy.insertOrUpdate(realm, it, map);
                    return;
                }
                if (superclass.equals(FixedGroup.class)) {
                    com_brgd_brblmesh_GeneralAdapter_model_FixedGroupRealmProxy.insertOrUpdate(realm, it, map);
                    return;
                }
                if (superclass.equals(DiyColor.class)) {
                    com_brgd_brblmesh_GeneralAdapter_model_DiyColorRealmProxy.insertOrUpdate(realm, it, map);
                    return;
                }
                if (superclass.equals(DeleteRadarGroup.class)) {
                    com_brgd_brblmesh_GeneralAdapter_model_DeleteRadarGroupRealmProxy.insertOrUpdate(realm, it, map);
                    return;
                }
                if (superclass.equals(DeleteGroups.class)) {
                    com_brgd_brblmesh_GeneralAdapter_model_DeleteGroupsRealmProxy.insertOrUpdate(realm, it, map);
                } else if (superclass.equals(DeleteFixedGroup.class)) {
                    com_brgd_brblmesh_GeneralAdapter_model_DeleteFixedGroupRealmProxy.insertOrUpdate(realm, it, map);
                } else {
                    if (superclass.equals(BleDevice.class)) {
                        com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxy.insertOrUpdate(realm, it, map);
                        return;
                    }
                    throw getMissingProxyClassException((Class<? extends RealmModel>) superclass);
                }
            }
        }
    }

    @Override // io.realm.internal.RealmProxyMediator
    public <E extends RealmModel> E createOrUpdateUsingJsonObject(Class<E> cls, Realm realm, JSONObject jSONObject, boolean z) throws JSONException {
        checkClass(cls);
        if (cls.equals(Timer.class)) {
            return cls.cast(com_brgd_brblmesh_GeneralAdapter_model_TimerRealmProxy.createOrUpdateUsingJsonObject(realm, jSONObject, z));
        }
        if (cls.equals(SceneDevice.class)) {
            return cls.cast(com_brgd_brblmesh_GeneralAdapter_model_SceneDeviceRealmProxy.createOrUpdateUsingJsonObject(realm, jSONObject, z));
        }
        if (cls.equals(Scene.class)) {
            return cls.cast(com_brgd_brblmesh_GeneralAdapter_model_SceneRealmProxy.createOrUpdateUsingJsonObject(realm, jSONObject, z));
        }
        if (cls.equals(RadarTimer.class)) {
            return cls.cast(com_brgd_brblmesh_GeneralAdapter_model_RadarTimerRealmProxy.createOrUpdateUsingJsonObject(realm, jSONObject, z));
        }
        if (cls.equals(RadarPhoneType.class)) {
            return cls.cast(com_brgd_brblmesh_GeneralAdapter_model_RadarPhoneTypeRealmProxy.createOrUpdateUsingJsonObject(realm, jSONObject, z));
        }
        if (cls.equals(RadarGroup.class)) {
            return cls.cast(com_brgd_brblmesh_GeneralAdapter_model_RadarGroupRealmProxy.createOrUpdateUsingJsonObject(realm, jSONObject, z));
        }
        if (cls.equals(RadarDevice.class)) {
            return cls.cast(com_brgd_brblmesh_GeneralAdapter_model_RadarDeviceRealmProxy.createOrUpdateUsingJsonObject(realm, jSONObject, z));
        }
        if (cls.equals(PhoneType.class)) {
            return cls.cast(com_brgd_brblmesh_GeneralAdapter_model_PhoneTypeRealmProxy.createOrUpdateUsingJsonObject(realm, jSONObject, z));
        }
        if (cls.equals(ModDiyColor.class)) {
            return cls.cast(com_brgd_brblmesh_GeneralAdapter_model_ModDiyColorRealmProxy.createOrUpdateUsingJsonObject(realm, jSONObject, z));
        }
        if (cls.equals(Mod.class)) {
            return cls.cast(com_brgd_brblmesh_GeneralAdapter_model_ModRealmProxy.createOrUpdateUsingJsonObject(realm, jSONObject, z));
        }
        if (cls.equals(Groups.class)) {
            return cls.cast(com_brgd_brblmesh_GeneralAdapter_model_GroupsRealmProxy.createOrUpdateUsingJsonObject(realm, jSONObject, z));
        }
        if (cls.equals(FixedGroup.class)) {
            return cls.cast(com_brgd_brblmesh_GeneralAdapter_model_FixedGroupRealmProxy.createOrUpdateUsingJsonObject(realm, jSONObject, z));
        }
        if (cls.equals(DiyColor.class)) {
            return cls.cast(com_brgd_brblmesh_GeneralAdapter_model_DiyColorRealmProxy.createOrUpdateUsingJsonObject(realm, jSONObject, z));
        }
        if (cls.equals(DeleteRadarGroup.class)) {
            return cls.cast(com_brgd_brblmesh_GeneralAdapter_model_DeleteRadarGroupRealmProxy.createOrUpdateUsingJsonObject(realm, jSONObject, z));
        }
        if (cls.equals(DeleteGroups.class)) {
            return cls.cast(com_brgd_brblmesh_GeneralAdapter_model_DeleteGroupsRealmProxy.createOrUpdateUsingJsonObject(realm, jSONObject, z));
        }
        if (cls.equals(DeleteFixedGroup.class)) {
            return cls.cast(com_brgd_brblmesh_GeneralAdapter_model_DeleteFixedGroupRealmProxy.createOrUpdateUsingJsonObject(realm, jSONObject, z));
        }
        if (cls.equals(BleDevice.class)) {
            return cls.cast(com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxy.createOrUpdateUsingJsonObject(realm, jSONObject, z));
        }
        throw getMissingProxyClassException((Class<? extends RealmModel>) cls);
    }

    @Override // io.realm.internal.RealmProxyMediator
    public <E extends RealmModel> E createUsingJsonStream(Class<E> cls, Realm realm, JsonReader jsonReader) throws IOException {
        checkClass(cls);
        if (cls.equals(Timer.class)) {
            return cls.cast(com_brgd_brblmesh_GeneralAdapter_model_TimerRealmProxy.createUsingJsonStream(realm, jsonReader));
        }
        if (cls.equals(SceneDevice.class)) {
            return cls.cast(com_brgd_brblmesh_GeneralAdapter_model_SceneDeviceRealmProxy.createUsingJsonStream(realm, jsonReader));
        }
        if (cls.equals(Scene.class)) {
            return cls.cast(com_brgd_brblmesh_GeneralAdapter_model_SceneRealmProxy.createUsingJsonStream(realm, jsonReader));
        }
        if (cls.equals(RadarTimer.class)) {
            return cls.cast(com_brgd_brblmesh_GeneralAdapter_model_RadarTimerRealmProxy.createUsingJsonStream(realm, jsonReader));
        }
        if (cls.equals(RadarPhoneType.class)) {
            return cls.cast(com_brgd_brblmesh_GeneralAdapter_model_RadarPhoneTypeRealmProxy.createUsingJsonStream(realm, jsonReader));
        }
        if (cls.equals(RadarGroup.class)) {
            return cls.cast(com_brgd_brblmesh_GeneralAdapter_model_RadarGroupRealmProxy.createUsingJsonStream(realm, jsonReader));
        }
        if (cls.equals(RadarDevice.class)) {
            return cls.cast(com_brgd_brblmesh_GeneralAdapter_model_RadarDeviceRealmProxy.createUsingJsonStream(realm, jsonReader));
        }
        if (cls.equals(PhoneType.class)) {
            return cls.cast(com_brgd_brblmesh_GeneralAdapter_model_PhoneTypeRealmProxy.createUsingJsonStream(realm, jsonReader));
        }
        if (cls.equals(ModDiyColor.class)) {
            return cls.cast(com_brgd_brblmesh_GeneralAdapter_model_ModDiyColorRealmProxy.createUsingJsonStream(realm, jsonReader));
        }
        if (cls.equals(Mod.class)) {
            return cls.cast(com_brgd_brblmesh_GeneralAdapter_model_ModRealmProxy.createUsingJsonStream(realm, jsonReader));
        }
        if (cls.equals(Groups.class)) {
            return cls.cast(com_brgd_brblmesh_GeneralAdapter_model_GroupsRealmProxy.createUsingJsonStream(realm, jsonReader));
        }
        if (cls.equals(FixedGroup.class)) {
            return cls.cast(com_brgd_brblmesh_GeneralAdapter_model_FixedGroupRealmProxy.createUsingJsonStream(realm, jsonReader));
        }
        if (cls.equals(DiyColor.class)) {
            return cls.cast(com_brgd_brblmesh_GeneralAdapter_model_DiyColorRealmProxy.createUsingJsonStream(realm, jsonReader));
        }
        if (cls.equals(DeleteRadarGroup.class)) {
            return cls.cast(com_brgd_brblmesh_GeneralAdapter_model_DeleteRadarGroupRealmProxy.createUsingJsonStream(realm, jsonReader));
        }
        if (cls.equals(DeleteGroups.class)) {
            return cls.cast(com_brgd_brblmesh_GeneralAdapter_model_DeleteGroupsRealmProxy.createUsingJsonStream(realm, jsonReader));
        }
        if (cls.equals(DeleteFixedGroup.class)) {
            return cls.cast(com_brgd_brblmesh_GeneralAdapter_model_DeleteFixedGroupRealmProxy.createUsingJsonStream(realm, jsonReader));
        }
        if (cls.equals(BleDevice.class)) {
            return cls.cast(com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxy.createUsingJsonStream(realm, jsonReader));
        }
        throw getMissingProxyClassException((Class<? extends RealmModel>) cls);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // io.realm.internal.RealmProxyMediator
    public <E extends RealmModel> E createDetachedCopy(E e, int i, Map<RealmModel, RealmObjectProxy.CacheData<RealmModel>> map) {
        Class<? super Object> superclass = e.getClass().getSuperclass();
        if (superclass.equals(Timer.class)) {
            return (E) superclass.cast(com_brgd_brblmesh_GeneralAdapter_model_TimerRealmProxy.createDetachedCopy((Timer) e, 0, i, map));
        }
        if (superclass.equals(SceneDevice.class)) {
            return (E) superclass.cast(com_brgd_brblmesh_GeneralAdapter_model_SceneDeviceRealmProxy.createDetachedCopy((SceneDevice) e, 0, i, map));
        }
        if (superclass.equals(Scene.class)) {
            return (E) superclass.cast(com_brgd_brblmesh_GeneralAdapter_model_SceneRealmProxy.createDetachedCopy((Scene) e, 0, i, map));
        }
        if (superclass.equals(RadarTimer.class)) {
            return (E) superclass.cast(com_brgd_brblmesh_GeneralAdapter_model_RadarTimerRealmProxy.createDetachedCopy((RadarTimer) e, 0, i, map));
        }
        if (superclass.equals(RadarPhoneType.class)) {
            return (E) superclass.cast(com_brgd_brblmesh_GeneralAdapter_model_RadarPhoneTypeRealmProxy.createDetachedCopy((RadarPhoneType) e, 0, i, map));
        }
        if (superclass.equals(RadarGroup.class)) {
            return (E) superclass.cast(com_brgd_brblmesh_GeneralAdapter_model_RadarGroupRealmProxy.createDetachedCopy((RadarGroup) e, 0, i, map));
        }
        if (superclass.equals(RadarDevice.class)) {
            return (E) superclass.cast(com_brgd_brblmesh_GeneralAdapter_model_RadarDeviceRealmProxy.createDetachedCopy((RadarDevice) e, 0, i, map));
        }
        if (superclass.equals(PhoneType.class)) {
            return (E) superclass.cast(com_brgd_brblmesh_GeneralAdapter_model_PhoneTypeRealmProxy.createDetachedCopy((PhoneType) e, 0, i, map));
        }
        if (superclass.equals(ModDiyColor.class)) {
            return (E) superclass.cast(com_brgd_brblmesh_GeneralAdapter_model_ModDiyColorRealmProxy.createDetachedCopy((ModDiyColor) e, 0, i, map));
        }
        if (superclass.equals(Mod.class)) {
            return (E) superclass.cast(com_brgd_brblmesh_GeneralAdapter_model_ModRealmProxy.createDetachedCopy((Mod) e, 0, i, map));
        }
        if (superclass.equals(Groups.class)) {
            return (E) superclass.cast(com_brgd_brblmesh_GeneralAdapter_model_GroupsRealmProxy.createDetachedCopy((Groups) e, 0, i, map));
        }
        if (superclass.equals(FixedGroup.class)) {
            return (E) superclass.cast(com_brgd_brblmesh_GeneralAdapter_model_FixedGroupRealmProxy.createDetachedCopy((FixedGroup) e, 0, i, map));
        }
        if (superclass.equals(DiyColor.class)) {
            return (E) superclass.cast(com_brgd_brblmesh_GeneralAdapter_model_DiyColorRealmProxy.createDetachedCopy((DiyColor) e, 0, i, map));
        }
        if (superclass.equals(DeleteRadarGroup.class)) {
            return (E) superclass.cast(com_brgd_brblmesh_GeneralAdapter_model_DeleteRadarGroupRealmProxy.createDetachedCopy((DeleteRadarGroup) e, 0, i, map));
        }
        if (superclass.equals(DeleteGroups.class)) {
            return (E) superclass.cast(com_brgd_brblmesh_GeneralAdapter_model_DeleteGroupsRealmProxy.createDetachedCopy((DeleteGroups) e, 0, i, map));
        }
        if (superclass.equals(DeleteFixedGroup.class)) {
            return (E) superclass.cast(com_brgd_brblmesh_GeneralAdapter_model_DeleteFixedGroupRealmProxy.createDetachedCopy((DeleteFixedGroup) e, 0, i, map));
        }
        if (superclass.equals(BleDevice.class)) {
            return (E) superclass.cast(com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxy.createDetachedCopy((BleDevice) e, 0, i, map));
        }
        throw getMissingProxyClassException((Class<? extends RealmModel>) superclass);
    }

    @Override // io.realm.internal.RealmProxyMediator
    public <E extends RealmModel> boolean isEmbedded(Class<E> cls) {
        if (cls.equals(Timer.class) || cls.equals(SceneDevice.class) || cls.equals(Scene.class) || cls.equals(RadarTimer.class) || cls.equals(RadarPhoneType.class) || cls.equals(RadarGroup.class) || cls.equals(RadarDevice.class) || cls.equals(PhoneType.class) || cls.equals(ModDiyColor.class) || cls.equals(Mod.class) || cls.equals(Groups.class) || cls.equals(FixedGroup.class) || cls.equals(DiyColor.class) || cls.equals(DeleteRadarGroup.class) || cls.equals(DeleteGroups.class) || cls.equals(DeleteFixedGroup.class) || cls.equals(BleDevice.class)) {
            return false;
        }
        throw getMissingProxyClassException((Class<? extends RealmModel>) cls);
    }

    @Override // io.realm.internal.RealmProxyMediator
    public <E extends RealmModel> void updateEmbeddedObject(Realm realm, E e, E e2, Map<RealmModel, RealmObjectProxy> map, Set<ImportFlag> set) {
        Class<? super Object> superclass = e2.getClass().getSuperclass();
        if (superclass.equals(Timer.class)) {
            throw getNotEmbeddedClassException("com.brgd.brblmesh.GeneralAdapter.model.Timer");
        }
        if (superclass.equals(SceneDevice.class)) {
            throw getNotEmbeddedClassException("com.brgd.brblmesh.GeneralAdapter.model.SceneDevice");
        }
        if (superclass.equals(Scene.class)) {
            throw getNotEmbeddedClassException("com.brgd.brblmesh.GeneralAdapter.model.Scene");
        }
        if (superclass.equals(RadarTimer.class)) {
            throw getNotEmbeddedClassException("com.brgd.brblmesh.GeneralAdapter.model.RadarTimer");
        }
        if (superclass.equals(RadarPhoneType.class)) {
            throw getNotEmbeddedClassException("com.brgd.brblmesh.GeneralAdapter.model.RadarPhoneType");
        }
        if (superclass.equals(RadarGroup.class)) {
            throw getNotEmbeddedClassException("com.brgd.brblmesh.GeneralAdapter.model.RadarGroup");
        }
        if (superclass.equals(RadarDevice.class)) {
            throw getNotEmbeddedClassException("com.brgd.brblmesh.GeneralAdapter.model.RadarDevice");
        }
        if (superclass.equals(PhoneType.class)) {
            throw getNotEmbeddedClassException("com.brgd.brblmesh.GeneralAdapter.model.PhoneType");
        }
        if (superclass.equals(ModDiyColor.class)) {
            throw getNotEmbeddedClassException("com.brgd.brblmesh.GeneralAdapter.model.ModDiyColor");
        }
        if (superclass.equals(Mod.class)) {
            throw getNotEmbeddedClassException("com.brgd.brblmesh.GeneralAdapter.model.Mod");
        }
        if (superclass.equals(Groups.class)) {
            throw getNotEmbeddedClassException("com.brgd.brblmesh.GeneralAdapter.model.Groups");
        }
        if (superclass.equals(FixedGroup.class)) {
            throw getNotEmbeddedClassException("com.brgd.brblmesh.GeneralAdapter.model.FixedGroup");
        }
        if (superclass.equals(DiyColor.class)) {
            throw getNotEmbeddedClassException("com.brgd.brblmesh.GeneralAdapter.model.DiyColor");
        }
        if (superclass.equals(DeleteRadarGroup.class)) {
            throw getNotEmbeddedClassException("com.brgd.brblmesh.GeneralAdapter.model.DeleteRadarGroup");
        }
        if (superclass.equals(DeleteGroups.class)) {
            throw getNotEmbeddedClassException("com.brgd.brblmesh.GeneralAdapter.model.DeleteGroups");
        }
        if (superclass.equals(DeleteFixedGroup.class)) {
            throw getNotEmbeddedClassException("com.brgd.brblmesh.GeneralAdapter.model.DeleteFixedGroup");
        }
        if (superclass.equals(BleDevice.class)) {
            throw getNotEmbeddedClassException("com.brgd.brblmesh.GeneralAdapter.model.BleDevice");
        }
        throw getMissingProxyClassException((Class<? extends RealmModel>) superclass);
    }
}
