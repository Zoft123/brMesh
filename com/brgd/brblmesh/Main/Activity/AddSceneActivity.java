package com.brgd.brblmesh.Main.Activity;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;
import com.brgd.brblmesh.GeneralActivity.FatherActivity;
import com.brgd.brblmesh.GeneralAdapter.AddSceneBleDeviceAdapter;
import com.brgd.brblmesh.GeneralAdapter.DialogArrayAdapter;
import com.brgd.brblmesh.GeneralAdapter.SceneAllBleDeviceAdapter;
import com.brgd.brblmesh.GeneralAdapter.model.BleDevice;
import com.brgd.brblmesh.GeneralAdapter.model.Scene;
import com.brgd.brblmesh.GeneralAdapter.model.SceneDevice;
import com.brgd.brblmesh.GeneralClass.BitmapUtils;
import com.brgd.brblmesh.GeneralClass.DisDoubleClickListener;
import com.brgd.brblmesh.GeneralClass.GlobalApplication;
import com.brgd.brblmesh.GeneralClass.GlobalBluetooth;
import com.brgd.brblmesh.GeneralClass.GlobalToast;
import com.brgd.brblmesh.GeneralClass.GlobalVariable;
import com.brgd.brblmesh.GeneralClass.ImageUtils;
import com.brgd.brblmesh.GeneralClass.MyLinearLayoutManager;
import com.brgd.brblmesh.GeneralClass.Tool;
import com.brgd.brblmesh.GeneralClass.loadDialogUtils;
import com.brgd.brblmesh.R;
import io.realm.Realm;
import j$.util.Objects;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class AddSceneActivity extends FatherActivity {
    public static final int CHECK_PERMISSION = 101;
    public static final int IMG_RESULT = 102;
    ActivityResultLauncher<String[]> CMPermissionLauncher;
    ActivityResultLauncher<String[]> IMGPermissionLauncher;
    RecyclerView addDeviceRecyclerView;
    private List<BleDevice> addDevicesList;
    private AddSceneBleDeviceAdapter addSceneBleDeviceAdapter;
    AlertDialog alertDialog;
    RecyclerView allDeviceRecyclerView;
    private List<BleDevice> allDevicesList;
    ImageView backView;
    ActivityResultLauncher<Intent> cameraLauncher;
    private BleDevice currentBleDevice;
    private Scene currentScene;
    private int currentSceneNumber;
    private boolean delete_deviceName_tag;
    private ImageView deviceNameDelete;
    private EditText deviceNameEdit;
    private TextView deviceNameText;
    ImageView dismissView;
    ActivityResultLauncher<Intent> getPermissionLauncher;
    ImageView iconBg;
    ImageView iconView;
    private Uri imageUri;
    private boolean isUsePhoto;
    ImageView nameBg;
    TextView nameValueText;
    ActivityResultLauncher<String> photoLauncher;
    private Realm realm;
    private ConstraintLayout renameLayout;
    private Dialog saveSceneDialog;
    ImageView saveView;
    private SceneAllBleDeviceAdapter sceneAllBleDeviceAdapter;
    AlertDialog scenePermissionAlertDialog;
    private final int[] sceneImgArr = {R.drawable.scene1, R.drawable.scene2, R.drawable.scene3, R.drawable.scene4, R.drawable.scene5, R.drawable.scene6, R.drawable.scene7, R.drawable.scene8, R.drawable.scene9};
    private final int[] iconImgArr = {R.drawable.scene1, R.drawable.scene2, R.drawable.scene3, R.drawable.scene4, R.drawable.scene5, R.drawable.scene6, R.drawable.scene7, R.drawable.scene8, R.drawable.scene9, R.drawable.scene10, R.drawable.scene11, R.drawable.scene12, R.drawable.scene13, R.drawable.scene14, R.drawable.scene15, R.drawable.scene16, R.drawable.scene17, R.drawable.scene18, R.drawable.scene19, R.drawable.scene20, R.drawable.scene21, R.drawable.scene22, R.drawable.scene23, R.drawable.scene24, R.drawable.scene25, R.drawable.scene26, R.drawable.scene27, R.drawable.scene28, R.drawable.scene29, R.drawable.scene30};
    private final DisDoubleClickListener disDoubleClickListener = new DisDoubleClickListener() { // from class: com.brgd.brblmesh.Main.Activity.AddSceneActivity.3
        @Override // com.brgd.brblmesh.GeneralClass.DisDoubleClickListener
        protected void disDoubleClick(View view) {
            int id = view.getId();
            if (id == R.id.add_scene_back) {
                AddSceneActivity.this.finish();
                return;
            }
            if (id == R.id.add_scene_iconBg) {
                AddSceneActivity.this.showImgChoice();
                return;
            }
            if (id == R.id.add_scene_nameBg) {
                AddSceneActivity.this.deviceNameText.setText(AddSceneActivity.this.currentScene.getSceneName());
                AddSceneActivity.this.deviceNameEdit.setText(AddSceneActivity.this.currentScene.getSceneName());
                AddSceneActivity.this.renameLayout.setVisibility(0);
                return;
            }
            if (id == R.id.scene_rename_delete) {
                AddSceneActivity.this.deviceNameEdit.setText("");
                AddSceneActivity.this.deviceNameDelete.setSelected(false);
                return;
            }
            if (id == R.id.scene_rename_save) {
                String string = AddSceneActivity.this.deviceNameEdit.getText().toString();
                if (string.isEmpty()) {
                    GlobalToast.showText(GlobalApplication.getMyApplication().getApplicationContext(), R.string.enterValid, 0);
                    return;
                }
                if (Tool.checkStringIsSpaces(string)) {
                    GlobalToast.showText(GlobalApplication.getMyApplication().getApplicationContext(), R.string.canNotSpaces, 0);
                    return;
                }
                String strTrim = string.trim();
                if (Tool.checkStringIsSpaces(strTrim)) {
                    GlobalToast.showText(GlobalApplication.getMyApplication().getApplicationContext(), R.string.canNotSpaces, 0);
                    return;
                }
                if (strTrim.length() > 26) {
                    GlobalToast.showText(GlobalApplication.getMyApplication().getApplicationContext(), R.string.InputTip, 0);
                    return;
                }
                if (!strTrim.equalsIgnoreCase(AddSceneActivity.this.currentScene.getSceneName())) {
                    Iterator it = AddSceneActivity.this.realm.where(Scene.class).findAll().iterator();
                    while (it.hasNext()) {
                        if (strTrim.equalsIgnoreCase(((Scene) it.next()).getSceneName())) {
                            GlobalToast.showText(GlobalApplication.getMyApplication().getApplicationContext(), R.string.nameExist, 0);
                            return;
                        }
                    }
                    AddSceneActivity.this.realm.beginTransaction();
                    AddSceneActivity.this.currentScene.setSceneName(strTrim);
                    AddSceneActivity.this.realm.commitTransaction();
                    AddSceneActivity.this.nameValueText.setText(AddSceneActivity.this.currentScene.getSceneName());
                }
                AddSceneActivity.this.closeRenameView();
                GlobalToast.showText(GlobalApplication.getMyApplication().getApplicationContext(), R.string.SaveSuccess, 0);
                return;
            }
            if (id == R.id.scene_rename_dismiss) {
                AddSceneActivity.this.closeRenameView();
            }
        }
    };
    private final MyHandler myHandler = new MyHandler(Looper.getMainLooper(), this);

    static /* synthetic */ void lambda$registerActivityResultLauncher$2(ActivityResult activityResult) {
    }

    @Override // com.brgd.brblmesh.GeneralActivity.FatherActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_add_scene);
        initView();
        registerActivityResultLauncher();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        refreshIcon();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onPause() {
        super.onPause();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        this.myHandler.removeCallbacksAndMessages(this);
        this.realm.close();
    }

    private void initView() {
        Realm.init(getApplicationContext());
        this.realm = Realm.getDefaultInstance();
        ImageView imageView = (ImageView) findViewById(R.id.add_scene_back);
        this.backView = imageView;
        imageView.setOnClickListener(this.disDoubleClickListener);
        ImageView imageView2 = (ImageView) findViewById(R.id.add_scene_iconBg);
        this.iconBg = imageView2;
        imageView2.setOnClickListener(this.disDoubleClickListener);
        this.iconView = (ImageView) findViewById(R.id.add_scene_icon);
        ImageView imageView3 = (ImageView) findViewById(R.id.add_scene_nameBg);
        this.nameBg = imageView3;
        imageView3.setOnClickListener(this.disDoubleClickListener);
        this.nameValueText = (TextView) findViewById(R.id.add_scene_nameValue);
        initAddDeviceView();
        notInSceneDevicesView();
        handleData();
        this.renameLayout = (ConstraintLayout) findViewById(R.id.scene_rename_editView);
        this.deviceNameText = (TextView) findViewById(R.id.scene_rename_name);
        this.deviceNameEdit = (EditText) findViewById(R.id.scene_rename_edit);
        ImageView imageView4 = (ImageView) findViewById(R.id.scene_rename_delete);
        this.deviceNameDelete = imageView4;
        imageView4.setSelected(false);
        this.deviceNameDelete.setOnClickListener(this.disDoubleClickListener);
        this.delete_deviceName_tag = true;
        this.deviceNameEdit.addTextChangedListener(new TextWatcher() { // from class: com.brgd.brblmesh.Main.Activity.AddSceneActivity.1
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                if (charSequence.length() > 0) {
                    if (AddSceneActivity.this.delete_deviceName_tag) {
                        AddSceneActivity.this.deviceNameDelete.setSelected(true);
                        AddSceneActivity.this.deviceNameDelete.setEnabled(true);
                        AddSceneActivity.this.delete_deviceName_tag = false;
                    }
                    if (charSequence.length() == 26) {
                        GlobalToast.showText(GlobalApplication.getMyApplication().getApplicationContext(), R.string.InputTip, 0);
                        return;
                    }
                    return;
                }
                AddSceneActivity.this.deviceNameDelete.setSelected(false);
                AddSceneActivity.this.deviceNameDelete.setEnabled(false);
                AddSceneActivity.this.delete_deviceName_tag = true;
            }
        });
        ImageView imageView5 = (ImageView) findViewById(R.id.scene_rename_save);
        this.saveView = imageView5;
        imageView5.setOnClickListener(this.disDoubleClickListener);
        ImageView imageView6 = (ImageView) findViewById(R.id.scene_rename_dismiss);
        this.dismissView = imageView6;
        imageView6.setOnClickListener(this.disDoubleClickListener);
    }

    private void initAddDeviceView() {
        this.addDevicesList = new ArrayList();
        this.addDeviceRecyclerView = (RecyclerView) findViewById(R.id.add_scene_addDevice_recyclerView);
        this.addDeviceRecyclerView.setLayoutManager(new MyLinearLayoutManager(this));
        AddSceneBleDeviceAdapter addSceneBleDeviceAdapter = new AddSceneBleDeviceAdapter(this, this.addDevicesList);
        this.addSceneBleDeviceAdapter = addSceneBleDeviceAdapter;
        this.addDeviceRecyclerView.setAdapter(addSceneBleDeviceAdapter);
        this.addSceneBleDeviceAdapter.setOnItemClickListener(new AddSceneBleDeviceAdapter.OnItemClickListener() { // from class: com.brgd.brblmesh.Main.Activity.AddSceneActivity.2
            @Override // com.brgd.brblmesh.GeneralAdapter.AddSceneBleDeviceAdapter.OnItemClickListener
            public void onItemClick(View view, int i) {
                AddSceneActivity addSceneActivity = AddSceneActivity.this;
                addSceneActivity.currentBleDevice = (BleDevice) addSceneActivity.addDevicesList.get(i);
                AddSceneActivity.this.deleteDeviceFromScene();
            }

            @Override // com.brgd.brblmesh.GeneralAdapter.AddSceneBleDeviceAdapter.OnItemClickListener
            public void onInCtrlClick(View view, int i) {
                BleDevice bleDevice = (BleDevice) AddSceneActivity.this.addDevicesList.get(i);
                Intent intent = new Intent(AddSceneActivity.this, (Class<?>) DeviceCtrlActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt(GlobalVariable.sceneNumber, AddSceneActivity.this.currentSceneNumber);
                bundle.putInt(GlobalVariable.sceneDeviceSelect, bleDevice.getAddr());
                bundle.putInt(GlobalVariable.viewType, bleDevice.getType());
                bundle.putBoolean(GlobalVariable.timerStatus, bleDevice.isSupportTimerStatus());
                bundle.putBoolean(GlobalVariable.isInclude0912, Tool.isChip0912_20(bleDevice.getVersion()));
                bundle.putBoolean(GlobalVariable.S_MODE_LIGHTNESS, bleDevice.isSupportModeLightness());
                intent.putExtra(GlobalVariable.sceneToCtrl, bundle);
                AddSceneActivity.this.startActivity(intent);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void deleteDeviceFromScene() {
        if (this.saveSceneDialog == null) {
            this.saveSceneDialog = loadDialogUtils.createLoadingDialog(this, R.string.Loading);
        }
        this.myHandler.postDelayed(new Runnable() { // from class: com.brgd.brblmesh.Main.Activity.AddSceneActivity$$ExternalSyntheticLambda12
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.handleDeleteSceneDevices();
            }
        }, 3000L);
        GlobalBluetooth.getInstance().deleteScene(this.currentBleDevice.getAddr(), this.currentSceneNumber);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleDeleteSceneDevices() {
        SceneDevice sceneDevice = (SceneDevice) this.realm.where(SceneDevice.class).equalTo(GlobalVariable.sceneNumber, Integer.valueOf(this.currentScene.getSceneNumber())).equalTo(GlobalVariable.did, this.currentBleDevice.getDid()).findFirst();
        if (sceneDevice != null) {
            this.realm.beginTransaction();
            this.currentScene.getSceneDeviceRealmList().remove(sceneDevice);
            sceneDevice.deleteFromRealm();
            this.realm.commitTransaction();
        }
        this.currentBleDevice = null;
        refreshNotInSceneDevices();
        Dialog dialog = this.saveSceneDialog;
        if (dialog != null) {
            loadDialogUtils.closeDialog(dialog);
            this.saveSceneDialog = null;
        }
    }

    private void notInSceneDevicesView() {
        this.allDevicesList = new ArrayList();
        this.sceneAllBleDeviceAdapter = new SceneAllBleDeviceAdapter(this, this.allDevicesList);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.add_scene_allDevices_recyclerView);
        this.allDeviceRecyclerView = recyclerView;
        recyclerView.setAdapter(this.sceneAllBleDeviceAdapter);
        this.allDeviceRecyclerView.setLayoutManager(new MyLinearLayoutManager(this));
        this.sceneAllBleDeviceAdapter.setOnItemClickListener(new SceneAllBleDeviceAdapter.OnItemClickListener() { // from class: com.brgd.brblmesh.Main.Activity.AddSceneActivity$$ExternalSyntheticLambda13
            @Override // com.brgd.brblmesh.GeneralAdapter.SceneAllBleDeviceAdapter.OnItemClickListener
            public final void onItemClick(View view, int i) {
                this.f$0.lambda$notInSceneDevicesView$0(view, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$notInSceneDevicesView$0(View view, int i) {
        this.currentBleDevice = this.allDevicesList.get(i);
        addDeviceToScene();
    }

    public void addDeviceToScene() {
        if (this.saveSceneDialog == null) {
            this.saveSceneDialog = loadDialogUtils.createLoadingDialog(this, R.string.Loading);
        }
        this.myHandler.postDelayed(new Runnable() { // from class: com.brgd.brblmesh.Main.Activity.AddSceneActivity$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.handleAddSceneDevices();
            }
        }, 3000L);
        GlobalBluetooth.getInstance().addScene(this.currentBleDevice.getAddr(), this.currentSceneNumber);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleAddSceneDevices() {
        SceneDevice sceneDevice = (SceneDevice) this.realm.where(SceneDevice.class).equalTo(GlobalVariable.sceneNumber, Integer.valueOf(this.currentSceneNumber)).equalTo(GlobalVariable.did, this.currentBleDevice.getDid()).findFirst();
        this.realm.beginTransaction();
        if (sceneDevice == null) {
            sceneDevice = (SceneDevice) this.realm.createObject(SceneDevice.class);
            sceneDevice.setColor(-2);
            sceneDevice.setLightness(-1);
            sceneDevice.setTemp(-1);
            sceneDevice.setModNumber(0);
            sceneDevice.setModSpeed(-1);
        }
        sceneDevice.setSceneNumber(this.currentSceneNumber);
        sceneDevice.setDid(this.currentBleDevice.getDid());
        if (!this.currentScene.getSceneDeviceRealmList().contains(sceneDevice)) {
            this.currentScene.getSceneDeviceRealmList().add(sceneDevice);
        }
        this.realm.commitTransaction();
        this.currentBleDevice = null;
        refreshNotInSceneDevices();
        Dialog dialog = this.saveSceneDialog;
        if (dialog != null) {
            loadDialogUtils.closeDialog(dialog);
            this.saveSceneDialog = null;
        }
    }

    private void handleData() {
        this.currentSceneNumber = getIntent().getIntExtra(GlobalVariable.sceneNumber, 0);
        this.currentScene = (Scene) this.realm.where(Scene.class).equalTo(GlobalVariable.sceneNumber, Integer.valueOf(this.currentSceneNumber)).findFirst();
        refreshIcon();
        Scene scene = this.currentScene;
        if (scene != null) {
            this.nameValueText.setText(scene.getSceneName());
        }
        refreshNotInSceneDevices();
    }

    private void refreshNotInSceneDevices() {
        this.addDevicesList.clear();
        Iterator<SceneDevice> it = this.currentScene.getSceneDeviceRealmList().iterator();
        while (it.hasNext()) {
            BleDevice bleDevice = (BleDevice) this.realm.where(BleDevice.class).equalTo(GlobalVariable.did, it.next().getDid()).findFirst();
            if (bleDevice != null) {
                this.addDevicesList.add(bleDevice);
            }
        }
        this.addSceneBleDeviceAdapter.notifyDataSetChanged();
        this.allDevicesList.clear();
        this.allDevicesList.addAll(this.realm.where(BleDevice.class).findAll().sort(GlobalVariable.index));
        Iterator<BleDevice> it2 = this.addDevicesList.iterator();
        while (it2.hasNext()) {
            this.allDevicesList.remove(it2.next());
        }
        this.sceneAllBleDeviceAdapter.notifyDataSetChanged();
    }

    private void refreshIcon() {
        Scene scene = this.currentScene;
        if (scene != null) {
            if (scene.getIconIndex() != 0) {
                this.iconView.setImageResource(this.iconImgArr[this.currentScene.getIconIndex() - 1]);
            } else if (this.currentScene.getSceneFileName() != null) {
                File file = new File(Tool.getAppFile(this), this.currentScene.getSceneFileName() + GlobalVariable.sceneImgFileName);
                if (file.exists()) {
                    this.iconView.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
                } else {
                    this.iconView.setImageResource(this.sceneImgArr[this.currentScene.getIndex()]);
                }
            } else {
                this.iconView.setImageResource(this.sceneImgArr[this.currentScene.getIndex()]);
            }
            this.nameValueText.setText(this.currentScene.getSceneName());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void closeRenameView() {
        this.deviceNameEdit.setText("");
        this.renameLayout.setVisibility(8);
    }

    private void usePhoto() {
        if (Tool.checkReadStoragePermission()) {
            openAlbum();
        } else {
            this.isUsePhoto = true;
            dialog();
        }
    }

    private void useCamera() {
        if (Tool.checkCameraPermission()) {
            takePhoto();
        } else {
            this.isUsePhoto = false;
            dialog();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sceneModifyImg() {
        if (this.isUsePhoto) {
            if (Tool.checkReadStoragePermission()) {
                openAlbum();
                return;
            } else {
                IMGPermission();
                return;
            }
        }
        if (Tool.checkCameraPermission()) {
            takePhoto();
        } else {
            CMPermission();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showImgChoice() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String string = getString(R.string.takePicture);
        builder.setAdapter(new DialogArrayAdapter(this, R.layout.dialog_list_item, new String[]{getString(R.string.templates), getString(R.string.Picture), string}), new DialogInterface.OnClickListener() { // from class: com.brgd.brblmesh.Main.Activity.AddSceneActivity$$ExternalSyntheticLambda8
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                this.f$0.lambda$showImgChoice$1(builder, dialogInterface, i);
            }
        });
        builder.create().show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showImgChoice$1(AlertDialog.Builder builder, DialogInterface dialogInterface, int i) {
        if (i == 0) {
            toTemplates();
        } else if (i == 1) {
            usePhoto();
        } else if (i == 2) {
            useCamera();
        }
        builder.create().dismiss();
    }

    private void takePhoto() {
        String str = System.currentTimeMillis() + ".jpg";
        if (Build.VERSION.SDK_INT >= 29) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("_display_name", str);
            contentValues.put("mime_type", "image/jpeg");
            contentValues.put("relative_path", Environment.DIRECTORY_DCIM);
            this.imageUri = getApplicationContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        } else {
            this.imageUri = FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName() + ".fileProvider", new File(((File) Objects.requireNonNull(getApplicationContext().getExternalCacheDir())).getAbsolutePath(), str));
        }
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra("output", this.imageUri);
        this.cameraLauncher.launch(intent);
    }

    private void openAlbum() {
        this.photoLauncher.launch("image/*");
    }

    public void toTemplates() {
        Intent intent = new Intent(this, (Class<?>) TemplatesActivity.class);
        intent.putExtra(GlobalVariable.sceneNumber, this.currentScene.getSceneNumber());
        startActivity(intent);
    }

    public void handleSceneImg(Bitmap bitmap) throws Throwable {
        saveImage(BitmapUtils.circleBitmap(BitmapUtils.zoom(bitmap, 202.0f, 202.0f)));
        GlobalToast.showText(GlobalApplication.getMyApplication().getApplicationContext(), R.string.setSuccess, 0);
        refreshIcon();
    }

    private void saveImage(Bitmap bitmap) throws Throwable {
        StringBuilder sb;
        File appFile = Tool.getAppFile(this);
        String fileName = Tool.getFileName();
        FileOutputStream fileOutputStream = null;
        try {
            try {
                FileOutputStream fileOutputStream2 = new FileOutputStream(new File(appFile, fileName + GlobalVariable.sceneImgFileName));
                try {
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream2);
                    if (this.currentScene.getSceneFileName() != null) {
                        Tool.deleteFile(this, this.currentScene.getSceneFileName() + GlobalVariable.sceneImgFileName);
                    }
                    this.realm.beginTransaction();
                    this.currentScene.setSceneFileName(fileName);
                    this.currentScene.setIconIndex(0);
                    this.realm.commitTransaction();
                    try {
                        fileOutputStream2.close();
                    } catch (IOException e) {
                        e = e;
                        sb = new StringBuilder("文件输出流关闭出错：");
                        sb.append(e);
                        Log.d("fos.close", sb.toString());
                    }
                } catch (FileNotFoundException e2) {
                    e = e2;
                    fileOutputStream = fileOutputStream2;
                    Log.d("saveImage", "文件写入出错：" + e);
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (IOException e3) {
                            e = e3;
                            sb = new StringBuilder("文件输出流关闭出错：");
                            sb.append(e);
                            Log.d("fos.close", sb.toString());
                        }
                    }
                } catch (Throwable th) {
                    th = th;
                    fileOutputStream = fileOutputStream2;
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (IOException e4) {
                            Log.d("fos.close", "文件输出流关闭出错：" + e4);
                        }
                    }
                    throw th;
                }
            } catch (FileNotFoundException e5) {
                e = e5;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    private static class MyHandler extends Handler {
        WeakReference<Object> weakReference;

        MyHandler(Looper looper, Object obj) {
            super(looper);
            this.weakReference = new WeakReference<>(obj);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) throws Throwable {
            AddSceneActivity addSceneActivity = (AddSceneActivity) this.weakReference.get();
            if (message.what == 101) {
                addSceneActivity.sceneModifyImg();
            } else if (message.what == 102) {
                addSceneActivity.handleSceneImg((Bitmap) message.obj);
            }
        }
    }

    private void toGetPermission() {
        this.getPermissionLauncher.launch(new Intent("android.settings.APPLICATION_DETAILS_SETTINGS", Uri.parse("package:" + getPackageName())));
    }

    private void IMGPermission() {
        if (Build.VERSION.SDK_INT >= 34) {
            this.IMGPermissionLauncher.launch(new String[]{"android.permission.READ_MEDIA_IMAGES", "android.permission.READ_MEDIA_VISUAL_USER_SELECTED"});
        } else if (Build.VERSION.SDK_INT == 33) {
            this.IMGPermissionLauncher.launch(new String[]{"android.permission.READ_MEDIA_IMAGES"});
        } else {
            this.IMGPermissionLauncher.launch(new String[]{"android.permission.READ_EXTERNAL_STORAGE"});
        }
    }

    private void CMPermission() {
        this.CMPermissionLauncher.launch(new String[]{"android.permission.CAMERA"});
    }

    private void registerActivityResultLauncher() {
        this.getPermissionLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback() { // from class: com.brgd.brblmesh.Main.Activity.AddSceneActivity$$ExternalSyntheticLambda1
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                AddSceneActivity.lambda$registerActivityResultLauncher$2((ActivityResult) obj);
            }
        });
        this.IMGPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), new ActivityResultCallback() { // from class: com.brgd.brblmesh.Main.Activity.AddSceneActivity$$ExternalSyntheticLambda2
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                this.f$0.lambda$registerActivityResultLauncher$3((Map) obj);
            }
        });
        this.CMPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), new ActivityResultCallback() { // from class: com.brgd.brblmesh.Main.Activity.AddSceneActivity$$ExternalSyntheticLambda3
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                this.f$0.lambda$registerActivityResultLauncher$4((Map) obj);
            }
        });
        this.cameraLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback() { // from class: com.brgd.brblmesh.Main.Activity.AddSceneActivity$$ExternalSyntheticLambda4
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                this.f$0.lambda$registerActivityResultLauncher$6((ActivityResult) obj);
            }
        });
        this.photoLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback() { // from class: com.brgd.brblmesh.Main.Activity.AddSceneActivity$$ExternalSyntheticLambda5
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                this.f$0.lambda$registerActivityResultLauncher$8((Uri) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$registerActivityResultLauncher$3(Map map) {
        if (Build.VERSION.SDK_INT >= 34) {
            if (map.get("android.permission.READ_MEDIA_VISUAL_USER_SELECTED") != null) {
                if (((Boolean) Objects.requireNonNull((Boolean) map.get("android.permission.READ_MEDIA_VISUAL_USER_SELECTED"))).equals(true)) {
                    openAlbum();
                    return;
                } else {
                    scenePermissionDialog();
                    return;
                }
            }
            return;
        }
        if (Build.VERSION.SDK_INT == 33) {
            if (map.get("android.permission.READ_MEDIA_IMAGES") != null) {
                if (((Boolean) Objects.requireNonNull((Boolean) map.get("android.permission.READ_MEDIA_IMAGES"))).equals(true)) {
                    openAlbum();
                    return;
                } else {
                    scenePermissionDialog();
                    return;
                }
            }
            return;
        }
        if (map.get("android.permission.READ_EXTERNAL_STORAGE") != null) {
            if (((Boolean) Objects.requireNonNull((Boolean) map.get("android.permission.READ_EXTERNAL_STORAGE"))).equals(true)) {
                openAlbum();
            } else {
                scenePermissionDialog();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$registerActivityResultLauncher$4(Map map) {
        if (map.get("android.permission.CAMERA") != null) {
            if (((Boolean) Objects.requireNonNull((Boolean) map.get("android.permission.CAMERA"))).equals(true)) {
                takePhoto();
            } else {
                scenePermissionDialog();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$registerActivityResultLauncher$6(ActivityResult activityResult) {
        if (activityResult == null || activityResult.getResultCode() == 0) {
            return;
        }
        new Thread(new Runnable() { // from class: com.brgd.brblmesh.Main.Activity.AddSceneActivity$$ExternalSyntheticLambda14
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$registerActivityResultLauncher$5();
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$registerActivityResultLauncher$5() {
        try {
            this.myHandler.sendMessage(this.myHandler.obtainMessage(102, ImageUtils.processImage(this.imageUri, this)));
        } catch (IOException e) {
            Log.e("IOException: ", String.valueOf(e));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$registerActivityResultLauncher$8(final Uri uri) {
        if (uri == null) {
            return;
        }
        new Thread(new Runnable() { // from class: com.brgd.brblmesh.Main.Activity.AddSceneActivity$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$registerActivityResultLauncher$7(uri);
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$registerActivityResultLauncher$7(Uri uri) {
        try {
            this.myHandler.sendMessage(this.myHandler.obtainMessage(102, ImageUtils.processImage(uri, this)));
        } catch (IOException e) {
            Log.e("IOException: ", String.valueOf(e));
        }
    }

    private void dialog() {
        if (this.alertDialog == null) {
            this.alertDialog = new AlertDialog.Builder(this).setMessage(R.string.sceneRequestMessage2).setPositiveButton(R.string.ACCEPT, new DialogInterface.OnClickListener() { // from class: com.brgd.brblmesh.Main.Activity.AddSceneActivity$$ExternalSyntheticLambda0
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    this.f$0.lambda$dialog$9(dialogInterface, i);
                }
            }).setNegativeButton(R.string.DENY, new DialogInterface.OnClickListener() { // from class: com.brgd.brblmesh.Main.Activity.AddSceneActivity$$ExternalSyntheticLambda6
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    this.f$0.lambda$dialog$10(dialogInterface, i);
                }
            }).create();
        }
        this.alertDialog.show();
        this.alertDialog.setCanceledOnTouchOutside(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dialog$9(DialogInterface dialogInterface, int i) {
        this.myHandler.sendEmptyMessage(101);
        this.alertDialog.cancel();
        this.alertDialog = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dialog$10(DialogInterface dialogInterface, int i) {
        this.alertDialog.cancel();
        this.alertDialog = null;
    }

    private void scenePermissionDialog() {
        if (this.scenePermissionAlertDialog == null) {
            this.scenePermissionAlertDialog = new AlertDialog.Builder(this).setTitle(R.string.sceneRequestMessage).setMessage(R.string.sceneRequestMessage1).setPositiveButton(R.string.Setting, new DialogInterface.OnClickListener() { // from class: com.brgd.brblmesh.Main.Activity.AddSceneActivity$$ExternalSyntheticLambda10
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    this.f$0.lambda$scenePermissionDialog$11(dialogInterface, i);
                }
            }).setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() { // from class: com.brgd.brblmesh.Main.Activity.AddSceneActivity$$ExternalSyntheticLambda11
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    this.f$0.lambda$scenePermissionDialog$12(dialogInterface, i);
                }
            }).create();
        }
        this.scenePermissionAlertDialog.show();
        this.scenePermissionAlertDialog.setCanceledOnTouchOutside(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scenePermissionDialog$11(DialogInterface dialogInterface, int i) {
        toGetPermission();
        this.scenePermissionAlertDialog.cancel();
        this.scenePermissionAlertDialog = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scenePermissionDialog$12(DialogInterface dialogInterface, int i) {
        this.scenePermissionAlertDialog.cancel();
        this.scenePermissionAlertDialog = null;
    }
}
