package com.brgd.brblmesh.Main.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.com.broadlink.blelight.BLSBleLight;
import cn.com.broadlink.blelight.bean.BLERoomSceneInfo;
import com.brgd.brblmesh.GeneralAdapter.SceneAdapter;
import com.brgd.brblmesh.GeneralAdapter.model.Scene;
import com.brgd.brblmesh.GeneralAdapter.model.SceneDevice;
import com.brgd.brblmesh.GeneralClass.GlobalApplication;
import com.brgd.brblmesh.GeneralClass.GlobalBluetooth;
import com.brgd.brblmesh.GeneralClass.GlobalToast;
import com.brgd.brblmesh.GeneralClass.GlobalVariable;
import com.brgd.brblmesh.GeneralClass.SharePreferenceUtils;
import com.brgd.brblmesh.GeneralClass.Tool;
import com.brgd.brblmesh.GeneralClass.loadDialogUtils;
import com.brgd.brblmesh.Main.Activity.AddSceneActivity;
import com.brgd.brblmesh.Main.Activity.MainActivity;
import com.brgd.brblmesh.R;
import io.realm.Realm;
import io.realm.RealmResults;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class SecondFragment extends Fragment {
    private Scene currentScene;
    private MainActivity mainActivity;
    private Realm realm;
    AlertDialog resetSceneAlertDialog;
    private SceneAdapter sceneAdapter;
    private List<Scene> sceneList;
    private final int[] sceneNameArr = {R.string.Scene1, R.string.Scene2, R.string.Scene3, R.string.Scene4, R.string.Scene5, R.string.Scene6, R.string.Scene7, R.string.Scene8, R.string.Scene9};
    RecyclerView sceneRecyclerView;

    @Override // androidx.fragment.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mainActivity = (MainActivity) context;
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View viewInflate = layoutInflater.inflate(R.layout.fragment_second, viewGroup, false);
        initView(viewInflate);
        return viewInflate;
    }

    @Override // androidx.fragment.app.Fragment
    public void onHiddenChanged(boolean z) {
        super.onHiddenChanged(z);
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        refreshScene();
    }

    @Override // androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        this.realm.close();
    }

    private void initView(View view) {
        Realm.init(this.mainActivity.getApplicationContext());
        this.realm = Realm.getDefaultInstance();
        this.sceneRecyclerView = (RecyclerView) view.findViewById(R.id.scene_preset_recyclerView);
        this.sceneList = new ArrayList();
        this.sceneAdapter = new SceneAdapter(getActivity(), this.sceneList);
        this.sceneRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        this.sceneRecyclerView.setAdapter(this.sceneAdapter);
        this.sceneAdapter.setOnSceneClickListener(new AnonymousClass1());
        refreshScene();
    }

    /* JADX INFO: renamed from: com.brgd.brblmesh.Main.Fragment.SecondFragment$1, reason: invalid class name */
    class AnonymousClass1 implements SceneAdapter.OnSceneClickListener {
        AnonymousClass1() {
        }

        @Override // com.brgd.brblmesh.GeneralAdapter.SceneAdapter.OnSceneClickListener
        public void onSceneClick(View view, int i, final View view2) {
            if (Tool.isAPI31() && !Tool.checkBleCASPermission()) {
                SecondFragment.this.mainActivity.bleCASDialog();
                return;
            }
            if (Tool.bleNotOnToast(SecondFragment.this.mainActivity.getApplicationContext())) {
                return;
            }
            SecondFragment secondFragment = SecondFragment.this;
            secondFragment.currentScene = (Scene) secondFragment.sceneList.get(i);
            SecondFragment.this.recallScene();
            view2.setVisibility(0);
            SecondFragment.this.mainActivity.myHandler.postDelayed(new Runnable() { // from class: com.brgd.brblmesh.Main.Fragment.SecondFragment$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    view2.setVisibility(4);
                }
            }, 300L);
        }

        @Override // com.brgd.brblmesh.GeneralAdapter.SceneAdapter.OnSceneClickListener
        public void onNameClick(View view, int i) {
            if (Tool.isAPI31() && !Tool.checkBleCASPermission()) {
                SecondFragment.this.mainActivity.bleCASDialog();
            } else {
                if (Tool.bleNotOnToast(SecondFragment.this.mainActivity.getApplicationContext())) {
                    return;
                }
                SecondFragment secondFragment = SecondFragment.this;
                secondFragment.currentScene = (Scene) secondFragment.sceneList.get(i);
                SecondFragment.this.editSceneDevice();
            }
        }
    }

    private void refreshScene() {
        this.sceneList.clear();
        RealmResults realmResultsFindAll = this.realm.where(Scene.class).findAll();
        if (realmResultsFindAll.isEmpty()) {
            BLSBleLight.removeAllRoomScenes();
            this.realm.beginTransaction();
            for (int i = 0; i < this.sceneNameArr.length; i++) {
                BLERoomSceneInfo bLERoomSceneInfo = new BLERoomSceneInfo();
                bLERoomSceneInfo.roomId = 0;
                bLERoomSceneInfo.sceneId = BLSBleLight.generateOneRoomSceneIdWithRoomId(bLERoomSceneInfo.roomId);
                bLERoomSceneInfo.type = 2;
                BLSBleLight.addRoomScene(bLERoomSceneInfo);
                Scene scene = (Scene) this.realm.createObject(Scene.class);
                scene.setIndex(i);
                scene.setSceneNumber(bLERoomSceneInfo.sceneId);
                scene.setSceneName(getString(this.sceneNameArr[i]));
                scene.isSelect = false;
                scene.isEdit = false;
                this.sceneList.add(scene);
            }
            this.realm.commitTransaction();
            if (TextUtils.isEmpty((String) SharePreferenceUtils.get(this.mainActivity.getApplicationContext(), GlobalVariable.resetScene, ""))) {
                SharePreferenceUtils.put(this.mainActivity.getApplicationContext(), GlobalVariable.resetScene, GlobalVariable.resetScene);
            }
        } else {
            if (!TextUtils.isEmpty((String) SharePreferenceUtils.get(this.mainActivity.getApplicationContext(), GlobalVariable.resetScene, ""))) {
                this.sceneList.addAll(realmResultsFindAll);
            } else {
                SharePreferenceUtils.put(this.mainActivity.getApplicationContext(), GlobalVariable.resetScene, GlobalVariable.resetScene);
                this.realm.beginTransaction();
                this.realm.where(SceneDevice.class).findAll().deleteAllFromRealm();
                this.realm.where(Scene.class).findAll().deleteAllFromRealm();
                for (int i2 = 0; i2 < this.sceneNameArr.length; i2++) {
                    BLERoomSceneInfo bLERoomSceneInfo2 = new BLERoomSceneInfo();
                    bLERoomSceneInfo2.roomId = 0;
                    bLERoomSceneInfo2.sceneId = BLSBleLight.generateOneRoomSceneIdWithRoomId(bLERoomSceneInfo2.roomId);
                    bLERoomSceneInfo2.type = 2;
                    BLSBleLight.addRoomScene(bLERoomSceneInfo2);
                    Scene scene2 = (Scene) this.realm.createObject(Scene.class);
                    scene2.setIndex(i2);
                    scene2.setSceneNumber(bLERoomSceneInfo2.sceneId);
                    scene2.setSceneName(getString(this.sceneNameArr[i2]));
                    scene2.isSelect = false;
                    scene2.isEdit = false;
                    this.sceneList.add(scene2);
                }
                this.realm.commitTransaction();
                resetSceneAlert();
            }
            for (Scene scene3 : this.sceneList) {
                scene3.isSelect = false;
                scene3.isEdit = false;
            }
        }
        this.sceneAdapter.notifyDataSetChanged();
    }

    private void controlLoading1200() {
        final Dialog dialogCreateLoadingDialog = loadDialogUtils.createLoadingDialog(getActivity(), R.string.Loading);
        this.mainActivity.myHandler.postDelayed(new Runnable() { // from class: com.brgd.brblmesh.Main.Fragment.SecondFragment$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                loadDialogUtils.closeDialog(dialogCreateLoadingDialog);
            }
        }, 1200L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void recallScene() {
        if (this.currentScene.getSceneDeviceRealmList().isEmpty()) {
            GlobalToast.showText(GlobalApplication.getMyApplication().getApplicationContext(), R.string.sceneNoDevices, 0);
        } else {
            controlLoading1200();
            GlobalBluetooth.getInstance().enableScene(this.currentScene.getSceneNumber());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void editSceneDevice() {
        Intent intent = new Intent(getActivity(), (Class<?>) AddSceneActivity.class);
        intent.putExtra(GlobalVariable.sceneNumber, this.currentScene.getSceneNumber());
        startActivity(intent);
    }

    private void resetSceneAlert() {
        if (this.resetSceneAlertDialog == null) {
            this.resetSceneAlertDialog = new AlertDialog.Builder(this.mainActivity).setMessage(R.string.resetScene).setPositiveButton(R.string.Sure, new DialogInterface.OnClickListener() { // from class: com.brgd.brblmesh.Main.Fragment.SecondFragment$$ExternalSyntheticLambda0
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    this.f$0.lambda$resetSceneAlert$1(dialogInterface, i);
                }
            }).create();
        }
        this.resetSceneAlertDialog.show();
        this.resetSceneAlertDialog.setCanceledOnTouchOutside(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$resetSceneAlert$1(DialogInterface dialogInterface, int i) {
        this.resetSceneAlertDialog.cancel();
        this.resetSceneAlertDialog = null;
    }
}
