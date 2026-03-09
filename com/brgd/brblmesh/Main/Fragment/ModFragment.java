package com.brgd.brblmesh.Main.Fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import cn.com.broadlink.blelight.bean.BLESceneInfo;
import com.brgd.brblmesh.GeneralAdapter.DialogArrayAdapter;
import com.brgd.brblmesh.GeneralAdapter.ModAdapter;
import com.brgd.brblmesh.GeneralAdapter.model.BleDevice;
import com.brgd.brblmesh.GeneralAdapter.model.Mod;
import com.brgd.brblmesh.GeneralAdapter.model.ModDiyColor;
import com.brgd.brblmesh.GeneralAdapter.model.SceneDevice;
import com.brgd.brblmesh.GeneralClass.DisDoubleClickListener;
import com.brgd.brblmesh.GeneralClass.GlobalApplication;
import com.brgd.brblmesh.GeneralClass.GlobalBluetooth;
import com.brgd.brblmesh.GeneralClass.GlobalToast;
import com.brgd.brblmesh.GeneralClass.GlobalVariable;
import com.brgd.brblmesh.GeneralClass.Tool;
import com.brgd.brblmesh.Main.Activity.DeviceCtrlActivity;
import com.brgd.brblmesh.Main.Activity.ModSetActivity;
import com.brgd.brblmesh.Main.Activity.SleepModActivity;
import com.brgd.brblmesh.R;
import io.realm.Realm;
import io.realm.RealmResults;
import j$.util.Objects;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class ModFragment extends Fragment {
    public Mod currentMod;
    private boolean delete_deviceName_tag;
    private DeviceCtrlActivity deviceCtrlActivity;
    private ImageView deviceNameDelete;
    private EditText deviceNameEdit;
    ImageView dismissView;
    private boolean isFlash;
    public boolean isInclude0912;
    public boolean isSupportModeLightness;
    public boolean isSupportSleepMod;
    public boolean isSupportTimerStatus;
    private Mod lastClickMod;
    private Mod lastEditMod;
    ModAdapter modAdapterAdapter;
    private List<ModDiyColor> modDiyColorList;
    private List<ModDiyColor> modDiyJumpColorList;
    private List<Mod> modList;
    RecyclerView modView;
    private Realm realm;
    private ConstraintLayout renameLayout;
    ImageView saveView;
    private SceneDevice sceneDevice;
    public int addr = -1;
    public int groupId = -1;
    public int tempGroupId = -1;
    private int type = 43050;
    public int sceneNumber = -1;
    int[] modNameArr = {R.string.Mod1, R.string.Mod2, R.string.Mod3, R.string.Mod4, R.string.Mod5, R.string.Mod6, R.string.Mod7, R.string.Mod8, R.string.Mod9, R.string.Mod10, R.string.Mod11, R.string.Mod12, R.string.Mod13, R.string.Mod14, R.string.Mod15, R.string.Mod16, R.string.Mod17, R.string.Mod18, R.string.Mod19, R.string.Mod20, R.string.Mod21, R.string.Mod22, R.string.ModFlash2, R.string.ModFlash3, R.string.ModFlash4, R.string.Mod23, R.string.ModFade2, R.string.ModFade3, R.string.ModFade4};
    int maxJump = ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION;
    int minFade = 1;
    int step = 2;
    private final DisDoubleClickListener disDoubleClickListener = new DisDoubleClickListener() { // from class: com.brgd.brblmesh.Main.Fragment.ModFragment.3
        @Override // com.brgd.brblmesh.GeneralClass.DisDoubleClickListener
        protected void disDoubleClick(View view) {
            int id = view.getId();
            if (id == R.id.new_group_rename_delete) {
                ModFragment.this.deviceNameEdit.setText("");
                ModFragment.this.deviceNameDelete.setSelected(false);
            } else if (id == R.id.new_group_rename_save) {
                ModFragment.this.saveNewCustomMod();
            } else if (id == R.id.new_group_rename_dismiss) {
                ModFragment.this.closeCreateView();
            }
        }
    };

    @Override // androidx.fragment.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
        this.deviceCtrlActivity = (DeviceCtrlActivity) context;
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View viewInflate = layoutInflater.inflate(R.layout.fragment_mod, viewGroup, false);
        initView(viewInflate);
        return viewInflate;
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        refreshMod();
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        this.realm.close();
    }

    private void initView(View view) {
        int i;
        Realm.init(GlobalApplication.getMyApplication().getApplicationContext());
        Realm defaultInstance = Realm.getDefaultInstance();
        this.realm = defaultInstance;
        BleDevice bleDevice = (BleDevice) defaultInstance.where(BleDevice.class).equalTo(GlobalVariable.addr, Integer.valueOf(this.addr)).findFirst();
        if (bleDevice != null) {
            this.type = bleDevice.getType();
        }
        this.modView = (RecyclerView) view.findViewById(R.id.mod_recyclerView);
        this.modList = new ArrayList();
        this.modAdapterAdapter = new ModAdapter(getActivity(), this.modList);
        this.modView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        this.modView.setAdapter(this.modAdapterAdapter);
        this.modAdapterAdapter.setOnModClickListener(new AnonymousClass1());
        if (this.addr != -1 && this.isSupportSleepMod && this.sceneNumber == -1) {
            Mod mod = (Mod) this.realm.where(Mod.class).equalTo(GlobalVariable.addr, Integer.valueOf(this.addr)).equalTo("isSleep", (Boolean) true).findFirst();
            Mod mod2 = (Mod) this.realm.where(Mod.class).equalTo(GlobalVariable.addr, Integer.valueOf(this.addr)).equalTo("isSleep", (Boolean) false).findFirst();
            this.realm.beginTransaction();
            if (mod == null) {
                mod = (Mod) this.realm.createObject(Mod.class);
                mod.setAddr(this.addr);
                mod.setStartBrightness(50);
                mod.setEndBrightness(1);
                mod.setMinute(10);
                mod.setStateValue(-2);
                mod.setWarmValue(0);
                mod.setSleep(true);
                mod.setModNumber(30);
            }
            if (mod2 == null) {
                mod2 = (Mod) this.realm.createObject(Mod.class);
                mod2.setAddr(this.addr);
                mod2.setStartBrightness(1);
                mod2.setEndBrightness(100);
                mod2.setMinute(10);
                mod2.setStateValue(-2);
                mod2.setWarmValue(0);
                mod2.setSleep(false);
                mod2.setModNumber(31);
            }
            this.realm.commitTransaction();
            this.modList.add(mod);
            this.modList.add(mod2);
        }
        int i2 = this.type;
        if (i2 != 43049 && i2 != 43051) {
            RealmResults<Mod> realmResultsFindAll = this.realm.where(Mod.class).equalTo(GlobalVariable.addr, (Integer) 0).findAll();
            if (realmResultsFindAll.size() == 14 || realmResultsFindAll.size() == 19) {
                this.realm.beginTransaction();
                realmResultsFindAll.deleteAllFromRealm();
                this.realm.commitTransaction();
            }
            if (realmResultsFindAll.isEmpty()) {
                this.realm.beginTransaction();
                int i3 = 0;
                while (i3 < this.modNameArr.length) {
                    Mod mod3 = (Mod) this.realm.createObject(Mod.class);
                    i3++;
                    mod3.setModNumber(i3);
                    mod3.setSpeed(1);
                    if (this.isSupportTimerStatus) {
                        this.modList.add(mod3);
                    } else if (mod3.getModNumber() != 24 && mod3.getModNumber() != 25 && mod3.getModNumber() != 28 && mod3.getModNumber() != 29 && mod3.getModNumber() != 0) {
                        this.modList.add(mod3);
                    }
                }
                this.realm.commitTransaction();
            } else if (realmResultsFindAll.size() == 23) {
                this.realm.beginTransaction();
                int i4 = 0;
                while (true) {
                    i = 22;
                    if (i4 >= 22) {
                        break;
                    }
                    this.modList.add((Mod) realmResultsFindAll.get(i4));
                    i4++;
                }
                Mod mod4 = (Mod) this.realm.where(Mod.class).equalTo(GlobalVariable.modNumber, (Integer) 23).findFirst();
                if (mod4 != null) {
                    mod4.deleteFromRealm();
                }
                while (i < this.modNameArr.length) {
                    Mod mod5 = (Mod) this.realm.createObject(Mod.class);
                    i++;
                    mod5.setModNumber(i);
                    mod5.setSpeed(1);
                    if (this.isSupportTimerStatus) {
                        this.modList.add(mod5);
                    } else if (mod5.getModNumber() != 24 && mod5.getModNumber() != 25 && mod5.getModNumber() != 28 && mod5.getModNumber() != 29 && mod5.getModNumber() != 0) {
                        this.modList.add(mod5);
                    }
                }
                this.realm.commitTransaction();
            } else {
                for (Mod mod6 : realmResultsFindAll) {
                    if (this.isSupportTimerStatus) {
                        this.modList.add(mod6);
                    } else if (mod6.getModNumber() != 24 && mod6.getModNumber() != 25 && mod6.getModNumber() != 28 && mod6.getModNumber() != 29 && mod6.getModNumber() != 0) {
                        this.modList.add(mod6);
                    }
                }
            }
            this.modDiyJumpColorList = new ArrayList();
            this.modDiyColorList = new ArrayList();
        }
        this.modAdapterAdapter.notifyDataSetChanged();
        refreshMod();
        if (this.sceneNumber != -1 && bleDevice != null) {
            this.sceneDevice = (SceneDevice) this.realm.where(SceneDevice.class).equalTo(GlobalVariable.sceneNumber, Integer.valueOf(this.sceneNumber)).equalTo(GlobalVariable.did, bleDevice.getDid()).findFirst();
        }
        this.renameLayout = (ConstraintLayout) view.findViewById(R.id.new_group_editView);
        this.deviceNameEdit = (EditText) view.findViewById(R.id.new_group_rename_edit);
        ImageView imageView = (ImageView) view.findViewById(R.id.new_group_rename_delete);
        this.deviceNameDelete = imageView;
        imageView.setSelected(false);
        this.deviceNameDelete.setOnClickListener(this.disDoubleClickListener);
        this.delete_deviceName_tag = true;
        this.deviceNameEdit.addTextChangedListener(new TextWatcher() { // from class: com.brgd.brblmesh.Main.Fragment.ModFragment.2
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i5, int i6, int i7) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i5, int i6, int i7) {
                if (charSequence.length() > 0) {
                    if (ModFragment.this.delete_deviceName_tag) {
                        ModFragment.this.deviceNameDelete.setSelected(true);
                        ModFragment.this.deviceNameDelete.setEnabled(true);
                        ModFragment.this.delete_deviceName_tag = false;
                    }
                    if (charSequence.length() == 26) {
                        GlobalToast.showText(ModFragment.this.deviceCtrlActivity.getApplicationContext(), R.string.InputTip, 0);
                        return;
                    }
                    return;
                }
                ModFragment.this.deviceNameDelete.setSelected(false);
                ModFragment.this.deviceNameDelete.setEnabled(false);
                ModFragment.this.delete_deviceName_tag = true;
            }
        });
        ImageView imageView2 = (ImageView) view.findViewById(R.id.new_group_rename_save);
        this.saveView = imageView2;
        imageView2.setOnClickListener(this.disDoubleClickListener);
        ImageView imageView3 = (ImageView) view.findViewById(R.id.new_group_rename_dismiss);
        this.dismissView = imageView3;
        imageView3.setOnClickListener(this.disDoubleClickListener);
    }

    /* JADX INFO: renamed from: com.brgd.brblmesh.Main.Fragment.ModFragment$1, reason: invalid class name */
    class AnonymousClass1 implements ModAdapter.OnModClickListener {
        AnonymousClass1() {
        }

        @Override // com.brgd.brblmesh.GeneralAdapter.ModAdapter.OnModClickListener
        public void onModClick(View view, int i, final View view2) {
            ModFragment modFragment = ModFragment.this;
            modFragment.currentMod = (Mod) modFragment.modList.get(i);
            if (ModFragment.this.currentMod.getAddr() == 0) {
                if (ModFragment.this.sceneNumber != -1) {
                    if (ModFragment.this.lastEditMod != null) {
                        if (Objects.equals(ModFragment.this.currentMod.getCustomModId(), ModFragment.this.lastEditMod.getCustomModId()) && ModFragment.this.currentMod.getModNumber() == ModFragment.this.lastEditMod.getModNumber() && ModFragment.this.sceneDevice.getTempSpeed() != 0) {
                            if (ModFragment.this.currentMod.getCustomModId() != null) {
                                ModFragment modFragment2 = ModFragment.this;
                                modFragment2.customModCommand(modFragment2.speedExchange(modFragment2.sceneDevice.getTempSpeed()));
                            } else {
                                ModFragment modFragment3 = ModFragment.this;
                                modFragment3.enableModCommand(modFragment3.speedExchange(modFragment3.sceneDevice.getTempSpeed()));
                            }
                        } else if (Objects.equals(ModFragment.this.currentMod.getCustomModId(), ModFragment.this.sceneDevice.getCustomModId()) && ModFragment.this.currentMod.getModNumber() == ModFragment.this.sceneDevice.getModNumber() && ModFragment.this.sceneDevice.getModSpeed() != -1) {
                            if (ModFragment.this.currentMod.getCustomModId() != null) {
                                ModFragment modFragment4 = ModFragment.this;
                                modFragment4.customModCommand(modFragment4.speedExchange(modFragment4.sceneDevice.getModSpeed()));
                            } else {
                                ModFragment modFragment5 = ModFragment.this;
                                modFragment5.enableModCommand(modFragment5.speedExchange(modFragment5.sceneDevice.getModSpeed()));
                            }
                            ModFragment.this.realm.beginTransaction();
                            ModFragment.this.sceneDevice.setTempSpeed(ModFragment.this.sceneDevice.getModSpeed());
                            ModFragment.this.realm.commitTransaction();
                        } else {
                            if (ModFragment.this.currentMod.getCustomModId() != null) {
                                ModFragment modFragment6 = ModFragment.this;
                                modFragment6.customModCommand(modFragment6.speedExchange(modFragment6.currentMod.getSpeed()));
                            } else {
                                ModFragment modFragment7 = ModFragment.this;
                                modFragment7.enableModCommand(modFragment7.speedExchange(modFragment7.currentMod.getSpeed()));
                            }
                            ModFragment.this.realm.beginTransaction();
                            ModFragment.this.sceneDevice.setTempSpeed(0);
                            ModFragment.this.realm.commitTransaction();
                        }
                    } else if (Objects.equals(ModFragment.this.currentMod.getCustomModId(), ModFragment.this.sceneDevice.getCustomModId()) && ModFragment.this.currentMod.getModNumber() == ModFragment.this.sceneDevice.getModNumber() && ModFragment.this.sceneDevice.getModSpeed() != -1) {
                        if (ModFragment.this.currentMod.getCustomModId() != null) {
                            ModFragment modFragment8 = ModFragment.this;
                            modFragment8.customModCommand(modFragment8.speedExchange(modFragment8.sceneDevice.getModSpeed()));
                        } else {
                            ModFragment modFragment9 = ModFragment.this;
                            modFragment9.enableModCommand(modFragment9.speedExchange(modFragment9.sceneDevice.getModSpeed()));
                        }
                        ModFragment.this.realm.beginTransaction();
                        ModFragment.this.sceneDevice.setTempSpeed(ModFragment.this.sceneDevice.getModSpeed());
                        ModFragment.this.realm.commitTransaction();
                    } else {
                        if (ModFragment.this.currentMod.getCustomModId() != null) {
                            ModFragment modFragment10 = ModFragment.this;
                            modFragment10.customModCommand(modFragment10.speedExchange(modFragment10.currentMod.getSpeed()));
                        } else {
                            ModFragment modFragment11 = ModFragment.this;
                            modFragment11.enableModCommand(modFragment11.speedExchange(modFragment11.currentMod.getSpeed()));
                        }
                        ModFragment.this.realm.beginTransaction();
                        ModFragment.this.sceneDevice.setTempSpeed(0);
                        ModFragment.this.realm.commitTransaction();
                    }
                    ModFragment modFragment12 = ModFragment.this;
                    modFragment12.lastClickMod = modFragment12.currentMod;
                } else if (ModFragment.this.currentMod.getCustomModId() != null) {
                    ModFragment modFragment13 = ModFragment.this;
                    modFragment13.customModCommand(modFragment13.speedExchange(modFragment13.currentMod.getSpeed()));
                } else {
                    ModFragment modFragment14 = ModFragment.this;
                    modFragment14.enableModCommand(modFragment14.speedExchange(modFragment14.currentMod.getSpeed()));
                }
            } else {
                GlobalBluetooth.getInstance().controlDelaySceneWithDevice(ModFragment.this.addr, ModFragment.this.currentMod.getStartBrightness(), ModFragment.this.currentMod.getEndBrightness(), ModFragment.this.currentMod.getMinute());
            }
            view2.setVisibility(0);
            ModFragment.this.deviceCtrlActivity.myHandler.postDelayed(new Runnable() { // from class: com.brgd.brblmesh.Main.Fragment.ModFragment$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    view2.setVisibility(4);
                }
            }, 300L);
        }

        @Override // com.brgd.brblmesh.GeneralAdapter.ModAdapter.OnModClickListener
        public void onNameClick(View view, int i) {
            Intent intent;
            ModFragment modFragment = ModFragment.this;
            modFragment.currentMod = (Mod) modFragment.modList.get(i);
            if (ModFragment.this.sceneNumber != -1) {
                if (ModFragment.this.lastEditMod != null) {
                    if (!Objects.equals(ModFragment.this.currentMod.getCustomModId(), ModFragment.this.lastEditMod.getCustomModId()) || ModFragment.this.currentMod.getModNumber() != ModFragment.this.lastEditMod.getModNumber() || (ModFragment.this.lastClickMod != null && (!Objects.equals(ModFragment.this.lastClickMod.getCustomModId(), ModFragment.this.currentMod.getCustomModId()) || ModFragment.this.lastClickMod.getModNumber() != ModFragment.this.currentMod.getModNumber()))) {
                        if (Objects.equals(ModFragment.this.currentMod.getCustomModId(), ModFragment.this.sceneDevice.getCustomModId()) && ModFragment.this.currentMod.getModNumber() == ModFragment.this.sceneDevice.getModNumber() && ModFragment.this.sceneDevice.getModSpeed() != -1) {
                            ModFragment.this.realm.beginTransaction();
                            ModFragment.this.sceneDevice.setTempSpeed(ModFragment.this.sceneDevice.getModSpeed());
                            ModFragment.this.realm.commitTransaction();
                        } else {
                            ModFragment.this.realm.beginTransaction();
                            ModFragment.this.sceneDevice.setTempSpeed(0);
                            ModFragment.this.realm.commitTransaction();
                        }
                    }
                } else if (Objects.equals(ModFragment.this.currentMod.getCustomModId(), ModFragment.this.sceneDevice.getCustomModId()) && ModFragment.this.currentMod.getModNumber() == ModFragment.this.sceneDevice.getModNumber() && ModFragment.this.sceneDevice.getModSpeed() != -1) {
                    ModFragment.this.realm.beginTransaction();
                    ModFragment.this.sceneDevice.setTempSpeed(ModFragment.this.sceneDevice.getModSpeed());
                    ModFragment.this.realm.commitTransaction();
                } else {
                    ModFragment.this.realm.beginTransaction();
                    ModFragment.this.sceneDevice.setTempSpeed(0);
                    ModFragment.this.realm.commitTransaction();
                }
                ModFragment modFragment2 = ModFragment.this;
                modFragment2.lastEditMod = modFragment2.currentMod;
            }
            Bundle bundle = new Bundle();
            bundle.putInt(GlobalVariable.addr, ModFragment.this.addr);
            bundle.putInt(GlobalVariable.modNumber, ModFragment.this.currentMod.getModNumber());
            bundle.putInt(GlobalVariable.sceneNumber, ModFragment.this.sceneNumber);
            if (ModFragment.this.currentMod.getAddr() == 0) {
                intent = new Intent(ModFragment.this.deviceCtrlActivity, (Class<?>) ModSetActivity.class);
                bundle.putInt(GlobalVariable.groupId, ModFragment.this.groupId);
                bundle.putInt(GlobalVariable.tempGroupId, ModFragment.this.tempGroupId);
                if (ModFragment.this.currentMod.getCustomModId() != null) {
                    bundle.putString(GlobalVariable.customModId, ModFragment.this.currentMod.getCustomModId());
                }
                bundle.putBoolean(GlobalVariable.isInclude0912, ModFragment.this.isInclude0912);
                bundle.putBoolean(GlobalVariable.S_MODE_LIGHTNESS, ModFragment.this.isSupportModeLightness);
            } else {
                intent = new Intent(ModFragment.this.deviceCtrlActivity, (Class<?>) SleepModActivity.class);
            }
            intent.putExtra(GlobalVariable.toModSetCtrl, bundle);
            ModFragment.this.startActivity(intent);
        }

        @Override // com.brgd.brblmesh.GeneralAdapter.ModAdapter.OnModClickListener
        public void onAddClick() {
            ModFragment.this.customModType();
        }
    }

    private void refreshMod() {
        this.modList.clear();
        if (this.addr != -1 && this.isSupportSleepMod && this.sceneNumber == -1) {
            Mod mod = (Mod) this.realm.where(Mod.class).equalTo(GlobalVariable.addr, Integer.valueOf(this.addr)).equalTo("isSleep", (Boolean) true).findFirst();
            Mod mod2 = (Mod) this.realm.where(Mod.class).equalTo(GlobalVariable.addr, Integer.valueOf(this.addr)).equalTo("isSleep", (Boolean) false).findFirst();
            this.realm.beginTransaction();
            if (mod == null) {
                mod = (Mod) this.realm.createObject(Mod.class);
                mod.setAddr(this.addr);
                mod.setStartBrightness(50);
                mod.setEndBrightness(1);
                mod.setMinute(10);
                mod.setStateValue(-2);
                mod.setWarmValue(0);
                mod.setSleep(true);
                mod.setModNumber(30);
            }
            if (mod2 == null) {
                mod2 = (Mod) this.realm.createObject(Mod.class);
                mod2.setAddr(this.addr);
                mod2.setStartBrightness(1);
                mod2.setEndBrightness(100);
                mod2.setMinute(10);
                mod2.setStateValue(-2);
                mod2.setWarmValue(0);
                mod2.setSleep(false);
                mod2.setModNumber(31);
            }
            this.realm.commitTransaction();
            this.modList.add(mod);
            this.modList.add(mod2);
        }
        int i = this.type;
        if (i != 43049 && i != 43051) {
            for (Mod mod3 : this.realm.where(Mod.class).equalTo(GlobalVariable.addr, (Integer) 0).findAll()) {
                if (this.isSupportTimerStatus) {
                    this.modList.add(mod3);
                } else if (mod3.getModNumber() != 24 && mod3.getModNumber() != 25 && mod3.getModNumber() != 28 && mod3.getModNumber() != 29 && mod3.getModNumber() != 0) {
                    this.modList.add(mod3);
                }
            }
            if (this.isSupportTimerStatus && this.sceneNumber == -1) {
                this.modList.add(new Mod());
            }
        }
        this.modAdapterAdapter.notifyDataSetChanged();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void customModType() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this.deviceCtrlActivity);
        builder.setAdapter(new DialogArrayAdapter(this.deviceCtrlActivity, R.layout.dialog_list_item, new String[]{getString(R.string.diyFlash), getString(R.string.diyFade)}), new DialogInterface.OnClickListener() { // from class: com.brgd.brblmesh.Main.Fragment.ModFragment$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                this.f$0.lambda$customModType$0(builder, dialogInterface, i);
            }
        });
        builder.create().show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$customModType$0(AlertDialog.Builder builder, DialogInterface dialogInterface, int i) {
        if (i == 0) {
            createCustomMod(true);
        } else if (i == 1) {
            createCustomMod(false);
        }
        builder.create().dismiss();
    }

    private void createCustomMod(boolean z) {
        this.isFlash = z;
        showCreateView();
    }

    private void showCreateView() {
        this.deviceNameEdit.setText("");
        this.renameLayout.setVisibility(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void closeCreateView() {
        this.deviceNameEdit.setText("");
        this.renameLayout.setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void saveNewCustomMod() {
        String string = this.deviceNameEdit.getText().toString();
        if (string.isEmpty()) {
            GlobalToast.showText(this.deviceCtrlActivity.getApplicationContext(), R.string.enterValid, 0);
            return;
        }
        if (Tool.checkStringIsSpaces(string)) {
            GlobalToast.showText(this.deviceCtrlActivity.getApplicationContext(), R.string.canNotSpaces, 0);
            return;
        }
        String strTrim = string.trim();
        if (Tool.checkStringIsSpaces(strTrim)) {
            GlobalToast.showText(this.deviceCtrlActivity.getApplicationContext(), R.string.canNotSpaces, 0);
            return;
        }
        if (strTrim.length() > 26) {
            GlobalToast.showText(this.deviceCtrlActivity.getApplicationContext(), R.string.InputTip, 0);
            return;
        }
        Iterator it = this.realm.where(Mod.class).equalTo(GlobalVariable.modNumber, (Integer) 0).findAll().iterator();
        while (it.hasNext()) {
            if (strTrim.equalsIgnoreCase(((Mod) it.next()).getCustomModName())) {
                GlobalToast.showText(this.deviceCtrlActivity.getApplicationContext(), R.string.nameExist, 0);
                return;
            }
        }
        createCustomMod();
    }

    private void createCustomMod() {
        this.realm.beginTransaction();
        Mod mod = (Mod) this.realm.createObject(Mod.class);
        mod.setSpeed(1);
        mod.setFlash(this.isFlash);
        mod.setCustomModId(Tool.getFileName());
        mod.setCustomModName(this.deviceNameEdit.getText().toString());
        this.realm.commitTransaction();
        closeCreateView();
        Intent intent = new Intent(this.deviceCtrlActivity, (Class<?>) ModSetActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(GlobalVariable.addr, this.addr);
        bundle.putInt(GlobalVariable.modNumber, mod.getModNumber());
        bundle.putInt(GlobalVariable.sceneNumber, this.sceneNumber);
        bundle.putInt(GlobalVariable.groupId, this.groupId);
        bundle.putInt(GlobalVariable.tempGroupId, this.tempGroupId);
        bundle.putString(GlobalVariable.customModId, mod.getCustomModId());
        bundle.putBoolean(GlobalVariable.isInclude0912, this.isInclude0912);
        bundle.putBoolean(GlobalVariable.S_MODE_LIGHTNESS, this.isSupportModeLightness);
        intent.putExtra(GlobalVariable.toModSetCtrl, bundle);
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int speedExchange(int i) {
        if (this.currentMod.getCustomModId() != null) {
            if (this.currentMod.isFlash()) {
                return this.maxJump - ((i - 1) * this.step);
            }
            return this.minFade + (i - 1);
        }
        if (this.currentMod.getModNumber() < 5 || ((this.currentMod.getModNumber() > 17 && this.currentMod.getModNumber() < 22) || this.currentMod.getModNumber() > 25)) {
            return this.minFade + (i - 1);
        }
        return this.maxJump - ((i - 1) * this.step);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public void enableModCommand(int i) {
        BLESceneInfo bLESceneInfoFullColor;
        switch (this.currentMod.getModNumber()) {
            case 1:
                bLESceneInfoFullColor = GlobalBluetooth.getInstance().fullColor(i);
                break;
            case 2:
                bLESceneInfoFullColor = GlobalBluetooth.getInstance().prColor(i);
                break;
            case 3:
                bLESceneInfoFullColor = GlobalBluetooth.getInstance().gbColor(i);
                break;
            case 4:
                bLESceneInfoFullColor = GlobalBluetooth.getInstance().yrColor(i);
                break;
            case 5:
                bLESceneInfoFullColor = GlobalBluetooth.getInstance().jumpFullColor(i);
                break;
            case 6:
                bLESceneInfoFullColor = GlobalBluetooth.getInstance().rgbJumpColor(i);
                break;
            case 7:
                bLESceneInfoFullColor = GlobalBluetooth.getInstance().ygJumpColor(i);
                break;
            case 8:
                bLESceneInfoFullColor = GlobalBluetooth.getInstance().pgJumpColor(i);
                break;
            case 9:
                bLESceneInfoFullColor = GlobalBluetooth.getInstance().pbJumpColor(i);
                break;
            case 10:
                bLESceneInfoFullColor = GlobalBluetooth.getInstance().ypJumpColor(i);
                break;
            case 11:
                bLESceneInfoFullColor = GlobalBluetooth.getInstance().rgJumpColor(i);
                break;
            case 12:
                bLESceneInfoFullColor = GlobalBluetooth.getInstance().rbJumpColor(i);
                break;
            case 13:
                bLESceneInfoFullColor = GlobalBluetooth.getInstance().gbJumpColor(i);
                break;
            case 14:
                bLESceneInfoFullColor = GlobalBluetooth.getInstance().rJumpColor(i);
                break;
            case 15:
                bLESceneInfoFullColor = GlobalBluetooth.getInstance().gJumpColor(i);
                break;
            case 16:
                bLESceneInfoFullColor = GlobalBluetooth.getInstance().bJumpColor(i);
                break;
            case 17:
                bLESceneInfoFullColor = GlobalBluetooth.getInstance().rgbWhiteJumpColor(i);
                break;
            case 18:
                bLESceneInfoFullColor = GlobalBluetooth.getInstance().rgColor(i);
                break;
            case 19:
                bLESceneInfoFullColor = GlobalBluetooth.getInstance().bwColor(i);
                break;
            case 20:
                bLESceneInfoFullColor = GlobalBluetooth.getInstance().gwColor(i);
                break;
            case 21:
                bLESceneInfoFullColor = GlobalBluetooth.getInstance().rwColor(i);
                break;
            case 22:
            case 23:
                this.modDiyJumpColorList.clear();
                this.modDiyJumpColorList.addAll(this.realm.where(ModDiyColor.class).equalTo(GlobalVariable.modNumber, Integer.valueOf(this.currentMod.getModNumber())).findAll());
                bLESceneInfoFullColor = GlobalBluetooth.getInstance().diyJumpColor(this.modDiyJumpColorList, i);
                break;
            case 24:
            case 25:
                this.modDiyJumpColorList.clear();
                this.modDiyJumpColorList.addAll(this.realm.where(ModDiyColor.class).equalTo(GlobalVariable.modNumber, Integer.valueOf(this.currentMod.getModNumber())).findAll());
                if (this.modDiyJumpColorList.size() == 4 && (this.isInclude0912 || this.isSupportModeLightness)) {
                    this.modDiyJumpColorList.remove(3);
                }
                enableNewMod(1, i, this.modDiyJumpColorList);
                bLESceneInfoFullColor = null;
                break;
            case 26:
            case 27:
                this.modDiyColorList.clear();
                this.modDiyColorList.addAll(this.realm.where(ModDiyColor.class).equalTo(GlobalVariable.modNumber, Integer.valueOf(this.currentMod.getModNumber())).findAll());
                bLESceneInfoFullColor = GlobalBluetooth.getInstance().diyColor(this.modDiyColorList, i);
                break;
            case 28:
            case ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_HORIZONTAL_BIAS /* 29 */:
                this.modDiyColorList.clear();
                this.modDiyColorList.addAll(this.realm.where(ModDiyColor.class).equalTo(GlobalVariable.modNumber, Integer.valueOf(this.currentMod.getModNumber())).findAll());
                if (this.modDiyColorList.size() == 4 && (this.isInclude0912 || this.isSupportModeLightness)) {
                    this.modDiyColorList.remove(3);
                }
                enableNewMod(0, i, this.modDiyColorList);
                bLESceneInfoFullColor = null;
                break;
            default:
                bLESceneInfoFullColor = null;
                break;
        }
        if (bLESceneInfoFullColor != null) {
            enableMod(bLESceneInfoFullColor);
        }
    }

    private void enableMod(BLESceneInfo bLESceneInfo) {
        if (this.addr != -1) {
            GlobalBluetooth.getInstance().modSingleCtrl(this.addr, bLESceneInfo);
        } else if (this.tempGroupId != -1) {
            GlobalBluetooth.getInstance().modGroupCtrl(this.tempGroupId, bLESceneInfo);
        } else if (this.groupId != -1) {
            GlobalBluetooth.getInstance().modGroupCtrl(this.groupId, bLESceneInfo);
        }
    }

    private void enableNewMod(int i, int i2, List<ModDiyColor> list) {
        SceneDevice sceneDevice;
        boolean z = this.isSupportModeLightness && (this.currentMod.getModNumber() == 24 || this.currentMod.getModNumber() == 25 || (this.currentMod.getCustomModId() != null && this.currentMod.isFlash()));
        int brightness = 100;
        if (this.sceneNumber != -1 && (sceneDevice = this.sceneDevice) != null) {
            if (sceneDevice.getModBrightness() != 0) {
                brightness = this.sceneDevice.getModBrightness();
            } else if (this.currentMod.getBrightness() != 0) {
                brightness = this.currentMod.getBrightness();
            }
        } else if (this.currentMod.getBrightness() != 0) {
            brightness = this.currentMod.getBrightness();
        }
        int i3 = (int) ((brightness / 100.0f) * 127.0f);
        if (this.addr != -1) {
            if (z) {
                GlobalBluetooth.getInstance().diyColorLChange(0, this.addr, i, i2, i3, list);
                return;
            } else {
                GlobalBluetooth.getInstance().diyColorChange(0, this.addr, i, i2, list);
                return;
            }
        }
        if (this.tempGroupId != -1) {
            if (z) {
                GlobalBluetooth.getInstance().diyColorLChange(1, this.tempGroupId, i, i2, i3, list);
                return;
            } else {
                GlobalBluetooth.getInstance().diyColorChange(1, this.tempGroupId, i, i2, list);
                return;
            }
        }
        if (this.groupId != -1) {
            if (z) {
                GlobalBluetooth.getInstance().diyColorLChange(1, this.groupId, i, i2, i3, list);
            } else {
                GlobalBluetooth.getInstance().diyColorChange(1, this.groupId, i, i2, list);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void customModCommand(int i) {
        RealmResults realmResultsFindAll = this.realm.where(ModDiyColor.class).equalTo(GlobalVariable.customModId, this.currentMod.getCustomModId()).findAll();
        if (this.currentMod.isFlash()) {
            this.modDiyJumpColorList.clear();
            this.modDiyJumpColorList.addAll(realmResultsFindAll);
            if (this.modDiyJumpColorList.size() == 4 && (this.isInclude0912 || this.isSupportModeLightness)) {
                this.modDiyJumpColorList.remove(3);
            }
            enableNewMod(1, i, this.modDiyJumpColorList);
            return;
        }
        this.modDiyColorList.clear();
        this.modDiyColorList.addAll(realmResultsFindAll);
        if (this.modDiyColorList.size() == 4 && (this.isInclude0912 || this.isSupportModeLightness)) {
            this.modDiyColorList.remove(3);
        }
        enableNewMod(0, i, this.modDiyColorList);
    }
}
