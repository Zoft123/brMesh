package com.brgd.brblmesh.GeneralAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.brgd.brblmesh.GeneralAdapter.model.BleDevice;
import com.brgd.brblmesh.GeneralClass.DisDoubleClickListener;
import com.brgd.brblmesh.R;
import j$.util.Objects;
import java.util.List;
import java.util.function.Supplier;

/* JADX INFO: loaded from: classes.dex */
public class AllBleDeviceAdapter extends RecyclerView.Adapter<ViewHolder> {
    private final Context context;
    List<BleDevice> list;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, int i);
    }

    public AllBleDeviceAdapter(Context context, List<BleDevice> list) {
        this.list = list;
        this.context = context;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(this.context).inflate(R.layout.new_group_all_list_item, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        BleDevice bleDevice = this.list.get(i);
        viewHolder.nameView.setText(bleDevice.getName());
        viewHolder.groupNameView.setText((CharSequence) Objects.requireNonNullElseGet(bleDevice.groupName, new Supplier() { // from class: com.brgd.brblmesh.GeneralAdapter.AllBleDeviceAdapter$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return this.f$0.lambda$onBindViewHolder$0();
            }
        }));
        viewHolder.addView.setTag(Integer.valueOf(i));
        viewHolder.addView.setOnClickListener(new DisDoubleClickListener() { // from class: com.brgd.brblmesh.GeneralAdapter.AllBleDeviceAdapter.1
            @Override // com.brgd.brblmesh.GeneralClass.DisDoubleClickListener
            protected void disDoubleClick(View view) {
                if (AllBleDeviceAdapter.this.onItemClickListener != null) {
                    AllBleDeviceAdapter.this.onItemClickListener.onItemClick(view, ((Integer) view.getTag()).intValue());
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ String lambda$onBindViewHolder$0() {
        return this.context.getString(R.string.noGroup);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView addView;
        TextView groupNameView;
        TextView nameView;

        public ViewHolder(View view) {
            super(view);
            this.nameView = (TextView) view.findViewById(R.id.all_list_item_title);
            this.groupNameView = (TextView) view.findViewById(R.id.all_list_item_group);
            this.addView = (ImageView) view.findViewById(R.id.all_list_item_add);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
