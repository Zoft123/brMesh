package com.brgd.brblmesh.GeneralClass;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import io.realm.DynamicRealm;
import io.realm.DynamicRealmObject;
import io.realm.FieldAttribute;
import io.realm.RealmMigration;
import io.realm.RealmObjectSchema;
import io.realm.RealmSchema;
import io.realm.com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxy;
import io.realm.com_brgd_brblmesh_GeneralAdapter_model_DeleteFixedGroupRealmProxy;
import io.realm.com_brgd_brblmesh_GeneralAdapter_model_DeleteRadarGroupRealmProxy;
import io.realm.com_brgd_brblmesh_GeneralAdapter_model_FixedGroupRealmProxy;
import io.realm.com_brgd_brblmesh_GeneralAdapter_model_ModDiyColorRealmProxy;
import io.realm.com_brgd_brblmesh_GeneralAdapter_model_RadarDeviceRealmProxy;
import io.realm.com_brgd_brblmesh_GeneralAdapter_model_RadarGroupRealmProxy;
import io.realm.com_brgd_brblmesh_GeneralAdapter_model_RadarPhoneTypeRealmProxy;
import io.realm.com_brgd_brblmesh_GeneralAdapter_model_RadarTimerRealmProxy;
import io.realm.com_brgd_brblmesh_GeneralAdapter_model_SceneDeviceRealmProxy;

/* JADX INFO: loaded from: classes.dex */
class CustomMigration implements RealmMigration {
    static /* synthetic */ void lambda$migrate$0(DynamicRealmObject dynamicRealmObject) {
    }

    static /* synthetic */ void lambda$migrate$1(DynamicRealmObject dynamicRealmObject) {
    }

    static /* synthetic */ void lambda$migrate$10(DynamicRealmObject dynamicRealmObject) {
    }

    static /* synthetic */ void lambda$migrate$11(DynamicRealmObject dynamicRealmObject) {
    }

    static /* synthetic */ void lambda$migrate$12(DynamicRealmObject dynamicRealmObject) {
    }

    static /* synthetic */ void lambda$migrate$13(DynamicRealmObject dynamicRealmObject) {
    }

    static /* synthetic */ void lambda$migrate$14(DynamicRealmObject dynamicRealmObject) {
    }

    static /* synthetic */ void lambda$migrate$15(DynamicRealmObject dynamicRealmObject) {
    }

    static /* synthetic */ void lambda$migrate$16(DynamicRealmObject dynamicRealmObject) {
    }

    static /* synthetic */ void lambda$migrate$17(DynamicRealmObject dynamicRealmObject) {
    }

    static /* synthetic */ void lambda$migrate$18(DynamicRealmObject dynamicRealmObject) {
    }

    static /* synthetic */ void lambda$migrate$19(DynamicRealmObject dynamicRealmObject) {
    }

    static /* synthetic */ void lambda$migrate$2(DynamicRealmObject dynamicRealmObject) {
    }

    static /* synthetic */ void lambda$migrate$20(DynamicRealmObject dynamicRealmObject) {
    }

    static /* synthetic */ void lambda$migrate$21(DynamicRealmObject dynamicRealmObject) {
    }

    static /* synthetic */ void lambda$migrate$22(DynamicRealmObject dynamicRealmObject) {
    }

    static /* synthetic */ void lambda$migrate$23(DynamicRealmObject dynamicRealmObject) {
    }

    static /* synthetic */ void lambda$migrate$24(DynamicRealmObject dynamicRealmObject) {
    }

    static /* synthetic */ void lambda$migrate$25(DynamicRealmObject dynamicRealmObject) {
    }

    static /* synthetic */ void lambda$migrate$3(DynamicRealmObject dynamicRealmObject) {
    }

    static /* synthetic */ void lambda$migrate$4(DynamicRealmObject dynamicRealmObject) {
    }

    static /* synthetic */ void lambda$migrate$5(DynamicRealmObject dynamicRealmObject) {
    }

    static /* synthetic */ void lambda$migrate$6(DynamicRealmObject dynamicRealmObject) {
    }

    static /* synthetic */ void lambda$migrate$7(DynamicRealmObject dynamicRealmObject) {
    }

    static /* synthetic */ void lambda$migrate$8(DynamicRealmObject dynamicRealmObject) {
    }

    static /* synthetic */ void lambda$migrate$9(DynamicRealmObject dynamicRealmObject) {
    }

    CustomMigration() {
    }

    @Override // io.realm.RealmMigration
    public void migrate(DynamicRealm dynamicRealm, long j, long j2) {
        RealmSchema schema = dynamicRealm.getSchema();
        RealmObjectSchema realmObjectSchema = schema.get(com_brgd_brblmesh_GeneralAdapter_model_SceneDeviceRealmProxy.ClassNameHelper.INTERNAL_CLASS_NAME);
        if (realmObjectSchema != null && !realmObjectSchema.hasField(GlobalVariable.TEMP)) {
            realmObjectSchema.addField(GlobalVariable.TEMP, Integer.TYPE, new FieldAttribute[0]).transform(new RealmObjectSchema.Function() { // from class: com.brgd.brblmesh.GeneralClass.CustomMigration$$ExternalSyntheticLambda0
                @Override // io.realm.RealmObjectSchema.Function
                public final void apply(DynamicRealmObject dynamicRealmObject) {
                    CustomMigration.lambda$migrate$0(dynamicRealmObject);
                }
            });
        }
        if (realmObjectSchema != null && !realmObjectSchema.hasField(GlobalVariable.customModId)) {
            realmObjectSchema.addField(GlobalVariable.customModId, String.class, new FieldAttribute[0]).addField("tempSpeed", Integer.TYPE, new FieldAttribute[0]).transform(new RealmObjectSchema.Function() { // from class: com.brgd.brblmesh.GeneralClass.CustomMigration$$ExternalSyntheticLambda2
                @Override // io.realm.RealmObjectSchema.Function
                public final void apply(DynamicRealmObject dynamicRealmObject) {
                    CustomMigration.lambda$migrate$1(dynamicRealmObject);
                }
            });
        }
        if (realmObjectSchema != null && !realmObjectSchema.hasField(GlobalVariable.MOD_BRIGHTNESS)) {
            realmObjectSchema.addField(GlobalVariable.MOD_BRIGHTNESS, Integer.TYPE, new FieldAttribute[0]).transform(new RealmObjectSchema.Function() { // from class: com.brgd.brblmesh.GeneralClass.CustomMigration$$ExternalSyntheticLambda9
                @Override // io.realm.RealmObjectSchema.Function
                public final void apply(DynamicRealmObject dynamicRealmObject) {
                    CustomMigration.lambda$migrate$2(dynamicRealmObject);
                }
            });
        }
        RealmObjectSchema realmObjectSchema2 = schema.get(com_brgd_brblmesh_GeneralAdapter_model_BleDeviceRealmProxy.ClassNameHelper.INTERNAL_CLASS_NAME);
        if (realmObjectSchema2 != null && !realmObjectSchema2.hasField("isSupportAlexaEnable")) {
            realmObjectSchema2.addField("isSupportAlexaEnable", Boolean.TYPE, new FieldAttribute[0]).addField("isConfigAlexa", Boolean.TYPE, new FieldAttribute[0]).transform(new RealmObjectSchema.Function() { // from class: com.brgd.brblmesh.GeneralClass.CustomMigration$$ExternalSyntheticLambda10
                @Override // io.realm.RealmObjectSchema.Function
                public final void apply(DynamicRealmObject dynamicRealmObject) {
                    CustomMigration.lambda$migrate$3(dynamicRealmObject);
                }
            });
        }
        if (realmObjectSchema2 != null && !realmObjectSchema2.hasField("isSupportFixedGroup")) {
            realmObjectSchema2.addField("isSupportFixedGroup", Boolean.TYPE, new FieldAttribute[0]).addField("isSupportGroupMain", Boolean.TYPE, new FieldAttribute[0]).addField(GlobalVariable.BEENMAIN, Boolean.TYPE, new FieldAttribute[0]).addField("fixedId", Integer.TYPE, new FieldAttribute[0]).transform(new RealmObjectSchema.Function() { // from class: com.brgd.brblmesh.GeneralClass.CustomMigration$$ExternalSyntheticLambda12
                @Override // io.realm.RealmObjectSchema.Function
                public final void apply(DynamicRealmObject dynamicRealmObject) {
                    CustomMigration.lambda$migrate$4(dynamicRealmObject);
                }
            });
        }
        if (realmObjectSchema2 != null && !realmObjectSchema2.hasField(GlobalVariable.S_T_STATUS)) {
            realmObjectSchema2.addField(GlobalVariable.S_T_STATUS, Boolean.TYPE, new FieldAttribute[0]).transform(new RealmObjectSchema.Function() { // from class: com.brgd.brblmesh.GeneralClass.CustomMigration$$ExternalSyntheticLambda13
                @Override // io.realm.RealmObjectSchema.Function
                public final void apply(DynamicRealmObject dynamicRealmObject) {
                    CustomMigration.lambda$migrate$5(dynamicRealmObject);
                }
            });
        }
        if (realmObjectSchema2 != null && !realmObjectSchema2.hasField(GlobalVariable.S_SCENE_CTRL)) {
            realmObjectSchema2.addField(GlobalVariable.S_SCENE_CTRL, Boolean.TYPE, new FieldAttribute[0]).transform(new RealmObjectSchema.Function() { // from class: com.brgd.brblmesh.GeneralClass.CustomMigration$$ExternalSyntheticLambda14
                @Override // io.realm.RealmObjectSchema.Function
                public final void apply(DynamicRealmObject dynamicRealmObject) {
                    CustomMigration.lambda$migrate$6(dynamicRealmObject);
                }
            });
        }
        if (realmObjectSchema2 != null && !realmObjectSchema2.hasField("otaTag")) {
            realmObjectSchema2.addField("otaTag", Integer.TYPE, new FieldAttribute[0]).transform(new RealmObjectSchema.Function() { // from class: com.brgd.brblmesh.GeneralClass.CustomMigration$$ExternalSyntheticLambda15
                @Override // io.realm.RealmObjectSchema.Function
                public final void apply(DynamicRealmObject dynamicRealmObject) {
                    CustomMigration.lambda$migrate$7(dynamicRealmObject);
                }
            });
        }
        if (realmObjectSchema2 != null && !realmObjectSchema2.hasField(GlobalVariable.S_MODE_LIGHTNESS)) {
            realmObjectSchema2.addField(GlobalVariable.S_MODE_LIGHTNESS, Boolean.TYPE, new FieldAttribute[0]).transform(new RealmObjectSchema.Function() { // from class: com.brgd.brblmesh.GeneralClass.CustomMigration$$ExternalSyntheticLambda16
                @Override // io.realm.RealmObjectSchema.Function
                public final void apply(DynamicRealmObject dynamicRealmObject) {
                    CustomMigration.lambda$migrate$8(dynamicRealmObject);
                }
            });
        }
        RealmObjectSchema realmObjectSchema3 = schema.get("Mod");
        if (realmObjectSchema3 != null && realmObjectSchema3.hasField("modName")) {
            realmObjectSchema3.removeField("modName").transform(new RealmObjectSchema.Function() { // from class: com.brgd.brblmesh.GeneralClass.CustomMigration$$ExternalSyntheticLambda17
                @Override // io.realm.RealmObjectSchema.Function
                public final void apply(DynamicRealmObject dynamicRealmObject) {
                    CustomMigration.lambda$migrate$9(dynamicRealmObject);
                }
            });
        }
        if (realmObjectSchema3 != null && !realmObjectSchema3.hasField(GlobalVariable.STARTBRI)) {
            realmObjectSchema3.addField(GlobalVariable.ADDR, Integer.TYPE, new FieldAttribute[0]).addField(GlobalVariable.STARTBRI, Integer.TYPE, new FieldAttribute[0]).addField(GlobalVariable.ENDBRI, Integer.TYPE, new FieldAttribute[0]).addField(GlobalVariable.MINUTE, Integer.TYPE, new FieldAttribute[0]).addField("stateValue", Integer.TYPE, new FieldAttribute[0]).addField(GlobalVariable.WARMVALUE, Integer.TYPE, new FieldAttribute[0]).addField("isSleep", Boolean.TYPE, new FieldAttribute[0]).transform(new RealmObjectSchema.Function() { // from class: com.brgd.brblmesh.GeneralClass.CustomMigration$$ExternalSyntheticLambda11
                @Override // io.realm.RealmObjectSchema.Function
                public final void apply(DynamicRealmObject dynamicRealmObject) {
                    CustomMigration.lambda$migrate$10(dynamicRealmObject);
                }
            });
        }
        if (realmObjectSchema3 != null && !realmObjectSchema3.hasField(GlobalVariable.ISFLASH)) {
            realmObjectSchema3.addField(GlobalVariable.ISFLASH, Boolean.TYPE, new FieldAttribute[0]).addField(GlobalVariable.customModId, String.class, new FieldAttribute[0]).addField(GlobalVariable.CUSTOMMODNAME, String.class, new FieldAttribute[0]).transform(new RealmObjectSchema.Function() { // from class: com.brgd.brblmesh.GeneralClass.CustomMigration$$ExternalSyntheticLambda18
                @Override // io.realm.RealmObjectSchema.Function
                public final void apply(DynamicRealmObject dynamicRealmObject) {
                    CustomMigration.lambda$migrate$11(dynamicRealmObject);
                }
            });
        }
        if (realmObjectSchema3 != null && !realmObjectSchema3.hasField(GlobalVariable.BRIGHTNESS)) {
            realmObjectSchema3.addField(GlobalVariable.BRIGHTNESS, Integer.TYPE, new FieldAttribute[0]).transform(new RealmObjectSchema.Function() { // from class: com.brgd.brblmesh.GeneralClass.CustomMigration$$ExternalSyntheticLambda19
                @Override // io.realm.RealmObjectSchema.Function
                public final void apply(DynamicRealmObject dynamicRealmObject) {
                    CustomMigration.lambda$migrate$12(dynamicRealmObject);
                }
            });
        }
        RealmObjectSchema realmObjectSchemaCreate = schema.get(com_brgd_brblmesh_GeneralAdapter_model_FixedGroupRealmProxy.ClassNameHelper.INTERNAL_CLASS_NAME);
        if (realmObjectSchema2 != null && realmObjectSchemaCreate == null) {
            realmObjectSchemaCreate = schema.create(com_brgd_brblmesh_GeneralAdapter_model_FixedGroupRealmProxy.ClassNameHelper.INTERNAL_CLASS_NAME);
            realmObjectSchemaCreate.addField("fixedId", Integer.TYPE, new FieldAttribute[0]).addField(GlobalVariable.FIXEDNAME, String.class, new FieldAttribute[0]).addRealmListField("bleDeviceRealmList", realmObjectSchema2).transform(new RealmObjectSchema.Function() { // from class: com.brgd.brblmesh.GeneralClass.CustomMigration$$ExternalSyntheticLambda20
                @Override // io.realm.RealmObjectSchema.Function
                public final void apply(DynamicRealmObject dynamicRealmObject) {
                    CustomMigration.lambda$migrate$13(dynamicRealmObject);
                }
            });
        }
        if (realmObjectSchemaCreate != null && !realmObjectSchemaCreate.hasField("fileName")) {
            realmObjectSchemaCreate.addField(GlobalVariable.INDEX, Integer.TYPE, new FieldAttribute[0]).addField("fileName", String.class, new FieldAttribute[0]).addField(GlobalVariable.ICONINDEX, Integer.TYPE, new FieldAttribute[0]).transform(new RealmObjectSchema.Function() { // from class: com.brgd.brblmesh.GeneralClass.CustomMigration$$ExternalSyntheticLambda21
                @Override // io.realm.RealmObjectSchema.Function
                public final void apply(DynamicRealmObject dynamicRealmObject) {
                    CustomMigration.lambda$migrate$14(dynamicRealmObject);
                }
            });
        }
        if (schema.get(com_brgd_brblmesh_GeneralAdapter_model_DeleteFixedGroupRealmProxy.ClassNameHelper.INTERNAL_CLASS_NAME) == null) {
            schema.create(com_brgd_brblmesh_GeneralAdapter_model_DeleteFixedGroupRealmProxy.ClassNameHelper.INTERNAL_CLASS_NAME).addField("fixedId", Integer.TYPE, new FieldAttribute[0]).transform(new RealmObjectSchema.Function() { // from class: com.brgd.brblmesh.GeneralClass.CustomMigration$$ExternalSyntheticLambda22
                @Override // io.realm.RealmObjectSchema.Function
                public final void apply(DynamicRealmObject dynamicRealmObject) {
                    CustomMigration.lambda$migrate$15(dynamicRealmObject);
                }
            });
        }
        RealmObjectSchema realmObjectSchema4 = schema.get(com_brgd_brblmesh_GeneralAdapter_model_ModDiyColorRealmProxy.ClassNameHelper.INTERNAL_CLASS_NAME);
        if (realmObjectSchema4 != null && !realmObjectSchema4.hasField("diyColorR")) {
            realmObjectSchema4.addField("diyColorR", Integer.TYPE, new FieldAttribute[0]).transform(new RealmObjectSchema.Function() { // from class: com.brgd.brblmesh.GeneralClass.CustomMigration$$ExternalSyntheticLambda23
                @Override // io.realm.RealmObjectSchema.Function
                public final void apply(DynamicRealmObject dynamicRealmObject) {
                    CustomMigration.lambda$migrate$16(dynamicRealmObject);
                }
            });
        }
        if (realmObjectSchema4 != null && !realmObjectSchema4.hasField(GlobalVariable.customModId)) {
            realmObjectSchema4.addField(GlobalVariable.customModId, String.class, new FieldAttribute[0]).transform(new RealmObjectSchema.Function() { // from class: com.brgd.brblmesh.GeneralClass.CustomMigration$$ExternalSyntheticLambda24
                @Override // io.realm.RealmObjectSchema.Function
                public final void apply(DynamicRealmObject dynamicRealmObject) {
                    CustomMigration.lambda$migrate$17(dynamicRealmObject);
                }
            });
        }
        RealmObjectSchema realmObjectSchema5 = schema.get("DiyColor");
        if (realmObjectSchema5 != null && !realmObjectSchema5.hasField("diyColorR")) {
            realmObjectSchema5.addField("diyColorR", Integer.TYPE, new FieldAttribute[0]).transform(new RealmObjectSchema.Function() { // from class: com.brgd.brblmesh.GeneralClass.CustomMigration$$ExternalSyntheticLambda25
                @Override // io.realm.RealmObjectSchema.Function
                public final void apply(DynamicRealmObject dynamicRealmObject) {
                    CustomMigration.lambda$migrate$18(dynamicRealmObject);
                }
            });
        }
        RealmObjectSchema realmObjectSchema6 = schema.get("Timer");
        if (realmObjectSchema6 != null && !realmObjectSchema6.hasField(GlobalVariable.BRIGHTNESS)) {
            realmObjectSchema6.addField(GlobalVariable.BRIGHTNESS, Integer.TYPE, new FieldAttribute[0]).addField(GlobalVariable.RED, Integer.TYPE, new FieldAttribute[0]).addField(GlobalVariable.GREEN, Integer.TYPE, new FieldAttribute[0]).addField(GlobalVariable.BLUE, Integer.TYPE, new FieldAttribute[0]).addField(GlobalVariable.COLD, Integer.TYPE, new FieldAttribute[0]).addField(GlobalVariable.WARM, Integer.TYPE, new FieldAttribute[0]).addField(TypedValues.Custom.S_COLOR, Integer.TYPE, new FieldAttribute[0]).transform(new RealmObjectSchema.Function() { // from class: com.brgd.brblmesh.GeneralClass.CustomMigration$$ExternalSyntheticLambda1
                @Override // io.realm.RealmObjectSchema.Function
                public final void apply(DynamicRealmObject dynamicRealmObject) {
                    CustomMigration.lambda$migrate$19(dynamicRealmObject);
                }
            });
        }
        RealmObjectSchema realmObjectSchema7 = schema.get("Scene");
        if (realmObjectSchema7 != null && !realmObjectSchema7.hasField(GlobalVariable.ICONINDEX)) {
            realmObjectSchema7.addField(GlobalVariable.ICONINDEX, Integer.TYPE, new FieldAttribute[0]).transform(new RealmObjectSchema.Function() { // from class: com.brgd.brblmesh.GeneralClass.CustomMigration$$ExternalSyntheticLambda3
                @Override // io.realm.RealmObjectSchema.Function
                public final void apply(DynamicRealmObject dynamicRealmObject) {
                    CustomMigration.lambda$migrate$20(dynamicRealmObject);
                }
            });
        }
        RealmObjectSchema realmObjectSchemaCreate2 = schema.get(com_brgd_brblmesh_GeneralAdapter_model_RadarDeviceRealmProxy.ClassNameHelper.INTERNAL_CLASS_NAME);
        if (realmObjectSchemaCreate2 == null) {
            realmObjectSchemaCreate2 = schema.create(com_brgd_brblmesh_GeneralAdapter_model_RadarDeviceRealmProxy.ClassNameHelper.INTERNAL_CLASS_NAME);
            realmObjectSchemaCreate2.addField(GlobalVariable.DID, String.class, new FieldAttribute[0]).addField(GlobalVariable.ADDR, Integer.TYPE, new FieldAttribute[0]).addField(GlobalVariable.GROUPID, Integer.TYPE, new FieldAttribute[0]).addField(GlobalVariable.KEY, String.class, new FieldAttribute[0]).addField(GlobalVariable.NAME, String.class, new FieldAttribute[0]).addField(GlobalVariable.TYPE, Integer.TYPE, new FieldAttribute[0]).addField(GlobalVariable.VERSION, String.class, new FieldAttribute[0]).addField(GlobalVariable.INDEX, Integer.TYPE, new FieldAttribute[0]).addField("isSupportAlexaEnable", Boolean.TYPE, new FieldAttribute[0]).addField("isConfigAlexa", Boolean.TYPE, new FieldAttribute[0]).addField("isSupportFixedGroup", Boolean.TYPE, new FieldAttribute[0]).transform(new RealmObjectSchema.Function() { // from class: com.brgd.brblmesh.GeneralClass.CustomMigration$$ExternalSyntheticLambda4
                @Override // io.realm.RealmObjectSchema.Function
                public final void apply(DynamicRealmObject dynamicRealmObject) {
                    CustomMigration.lambda$migrate$21(dynamicRealmObject);
                }
            });
        }
        if (schema.get(com_brgd_brblmesh_GeneralAdapter_model_RadarGroupRealmProxy.ClassNameHelper.INTERNAL_CLASS_NAME) == null) {
            schema.create(com_brgd_brblmesh_GeneralAdapter_model_RadarGroupRealmProxy.ClassNameHelper.INTERNAL_CLASS_NAME).addField("fixedId", Integer.TYPE, new FieldAttribute[0]).addField(GlobalVariable.FIXEDNAME, String.class, new FieldAttribute[0]).addField(GlobalVariable.INDEX, Integer.TYPE, new FieldAttribute[0]).addRealmListField("bleDeviceRealmList", realmObjectSchemaCreate2).transform(new RealmObjectSchema.Function() { // from class: com.brgd.brblmesh.GeneralClass.CustomMigration$$ExternalSyntheticLambda5
                @Override // io.realm.RealmObjectSchema.Function
                public final void apply(DynamicRealmObject dynamicRealmObject) {
                    CustomMigration.lambda$migrate$22(dynamicRealmObject);
                }
            });
        }
        if (schema.get(com_brgd_brblmesh_GeneralAdapter_model_DeleteRadarGroupRealmProxy.ClassNameHelper.INTERNAL_CLASS_NAME) == null) {
            schema.create(com_brgd_brblmesh_GeneralAdapter_model_DeleteRadarGroupRealmProxy.ClassNameHelper.INTERNAL_CLASS_NAME).addField("fixedId", Integer.TYPE, new FieldAttribute[0]).transform(new RealmObjectSchema.Function() { // from class: com.brgd.brblmesh.GeneralClass.CustomMigration$$ExternalSyntheticLambda6
                @Override // io.realm.RealmObjectSchema.Function
                public final void apply(DynamicRealmObject dynamicRealmObject) {
                    CustomMigration.lambda$migrate$23(dynamicRealmObject);
                }
            });
        }
        if (schema.get(com_brgd_brblmesh_GeneralAdapter_model_RadarTimerRealmProxy.ClassNameHelper.INTERNAL_CLASS_NAME) == null) {
            schema.create(com_brgd_brblmesh_GeneralAdapter_model_RadarTimerRealmProxy.ClassNameHelper.INTERNAL_CLASS_NAME).addField("indexNum", Integer.TYPE, new FieldAttribute[0]).addField(GlobalVariable.HOUR1, Integer.TYPE, new FieldAttribute[0]).addField(GlobalVariable.MIN1, Integer.TYPE, new FieldAttribute[0]).addField(GlobalVariable.HOUR2, Integer.TYPE, new FieldAttribute[0]).addField(GlobalVariable.MIN2, Integer.TYPE, new FieldAttribute[0]).addField(GlobalVariable.ISENABLE, Boolean.TYPE, new FieldAttribute[0]).addField(GlobalVariable.OUT_BRI, Integer.TYPE, new FieldAttribute[0]).addField(GlobalVariable.ADDR, Integer.TYPE, new FieldAttribute[0]).transform(new RealmObjectSchema.Function() { // from class: com.brgd.brblmesh.GeneralClass.CustomMigration$$ExternalSyntheticLambda7
                @Override // io.realm.RealmObjectSchema.Function
                public final void apply(DynamicRealmObject dynamicRealmObject) {
                    CustomMigration.lambda$migrate$24(dynamicRealmObject);
                }
            });
        }
        if (schema.get(com_brgd_brblmesh_GeneralAdapter_model_RadarPhoneTypeRealmProxy.ClassNameHelper.INTERNAL_CLASS_NAME) == null) {
            schema.create(com_brgd_brblmesh_GeneralAdapter_model_RadarPhoneTypeRealmProxy.ClassNameHelper.INTERNAL_CLASS_NAME).addField(GlobalVariable.P_TYPE, Integer.TYPE, new FieldAttribute[0]).transform(new RealmObjectSchema.Function() { // from class: com.brgd.brblmesh.GeneralClass.CustomMigration$$ExternalSyntheticLambda8
                @Override // io.realm.RealmObjectSchema.Function
                public final void apply(DynamicRealmObject dynamicRealmObject) {
                    CustomMigration.lambda$migrate$25(dynamicRealmObject);
                }
            });
        }
    }
}
