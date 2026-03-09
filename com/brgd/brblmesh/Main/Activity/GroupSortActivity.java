package com.brgd.brblmesh.Main.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.brgd.brblmesh.GeneralActivity.FatherActivity;
import com.brgd.brblmesh.GeneralAdapter.GroupsListAdapter;
import com.brgd.brblmesh.GeneralAdapter.model.Groups;
import com.brgd.brblmesh.GeneralClass.DisDoubleClickListener;
import com.brgd.brblmesh.GeneralClass.GlobalVariable;
import com.brgd.brblmesh.GeneralClass.ListenerInterceptor;
import com.brgd.brblmesh.GeneralClass.MyItemTouchHelper;
import com.brgd.brblmesh.GeneralClass.MyLinearLayoutManager;
import com.brgd.brblmesh.R;
import io.realm.Realm;
import io.realm.RealmResults;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class GroupSortActivity extends FatherActivity implements MyItemTouchHelper.OnItemTouchListener {
    ImageView backView;
    private List<Groups> groupsList;
    private GroupsListAdapter groupsListAdapter;
    Realm realm;
    RecyclerView recyclerView;
    MyItemTouchHelper touchHelper;
    private final DisDoubleClickListener disDoubleClickListener = new DisDoubleClickListener() { // from class: com.brgd.brblmesh.Main.Activity.GroupSortActivity.1
        @Override // com.brgd.brblmesh.GeneralClass.DisDoubleClickListener
        protected void disDoubleClick(View view) {
            if (view.getId() == R.id.group_sort_back) {
                GroupSortActivity.this.finish();
            }
        }
    };
    private final MyHandler myHandler = new MyHandler(Looper.getMainLooper(), this);

    @Override // com.brgd.brblmesh.GeneralClass.MyItemTouchHelper.OnItemTouchListener
    public void onSwiped(int i) {
    }

    @Override // com.brgd.brblmesh.GeneralActivity.FatherActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_group_sort);
        initView();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
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
        ImageView imageView = (ImageView) findViewById(R.id.group_sort_back);
        this.backView = imageView;
        imageView.setOnClickListener(this.disDoubleClickListener);
        this.groupsList = new ArrayList();
        this.recyclerView = (RecyclerView) findViewById(R.id.group_sort_recyclerView);
        this.recyclerView.setLayoutManager(new MyLinearLayoutManager(this));
        GroupsListAdapter groupsListAdapter = new GroupsListAdapter(this, this.groupsList);
        this.groupsListAdapter = groupsListAdapter;
        this.recyclerView.setAdapter(groupsListAdapter);
        RealmResults realmResultsSort = this.realm.where(Groups.class).notEqualTo(GlobalVariable.groupId, (Integer) 0).findAll().sort(GlobalVariable.index);
        if (!realmResultsSort.isEmpty()) {
            this.groupsList.addAll(realmResultsSort);
            for (Groups groups : this.groupsList) {
                groups.isSort = true;
                groups.isDelete = false;
                groups.isRename = false;
            }
            this.groupsListAdapter.notifyDataSetChanged();
        }
        MyItemTouchHelper myItemTouchHelper = new MyItemTouchHelper(this);
        this.touchHelper = myItemTouchHelper;
        myItemTouchHelper.setSort(true);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(this.touchHelper);
        itemTouchHelper.attachToRecyclerView(this.recyclerView);
        new ListenerInterceptor(itemTouchHelper).setDoDrag(true);
    }

    @Override // com.brgd.brblmesh.GeneralClass.MyItemTouchHelper.OnItemTouchListener
    public boolean onMove(int i, int i2) {
        if (i < i2) {
            int i3 = i;
            while (i3 < i2) {
                int i4 = i3 + 1;
                Collections.swap(this.groupsList, i3, i4);
                i3 = i4;
            }
        } else {
            for (int i5 = i; i5 > i2; i5--) {
                Collections.swap(this.groupsList, i5, i5 - 1);
            }
        }
        this.groupsListAdapter.notifyItemMoved(i, i2);
        Groups groups = this.groupsList.get(i);
        int index = groups.getIndex();
        Groups groups2 = this.groupsList.get(i2);
        int index2 = groups2.getIndex();
        this.realm.beginTransaction();
        groups.setIndex(index2);
        groups2.setIndex(index);
        this.realm.commitTransaction();
        return true;
    }

    private static class MyHandler extends Handler {
        WeakReference<Object> weakReference;

        @Override // android.os.Handler
        public void handleMessage(Message message) {
        }

        MyHandler(Looper looper, Object obj) {
            super(looper);
            this.weakReference = new WeakReference<>(obj);
        }
    }
}
