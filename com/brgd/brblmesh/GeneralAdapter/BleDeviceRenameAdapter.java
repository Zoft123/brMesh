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
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class BleDeviceRenameAdapter extends RecyclerView.Adapter<ViewHolder> {
    private final Context context;
    List<BleDevice> list;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onOffClick(View view, int i, boolean z);

        void onRenameClick(View view, int i);
    }

    public BleDeviceRenameAdapter(Context context, List<BleDevice> list) {
        this.list = list;
        this.context = context;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(this.context).inflate(R.layout.device_rename_list_item, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {
        viewHolder.deviceNameView.setText(this.list.get(i).getName());
        viewHolder.onView.setOnClickListener(new DisDoubleClickListener() { // from class: com.brgd.brblmesh.GeneralAdapter.BleDeviceRenameAdapter.1
            @Override // com.brgd.brblmesh.GeneralClass.DisDoubleClickListener
            protected void disDoubleClick(View view) {
                int bindingAdapterPosition = viewHolder.getBindingAdapterPosition();
                if (bindingAdapterPosition == -1 || BleDeviceRenameAdapter.this.onItemClickListener == null) {
                    return;
                }
                BleDeviceRenameAdapter.this.onItemClickListener.onOffClick(view, bindingAdapterPosition, true);
            }
        });
        viewHolder.offView.setOnClickListener(new DisDoubleClickListener() { // from class: com.brgd.brblmesh.GeneralAdapter.BleDeviceRenameAdapter.2
            @Override // com.brgd.brblmesh.GeneralClass.DisDoubleClickListener
            protected void disDoubleClick(View view) {
                int bindingAdapterPosition = viewHolder.getBindingAdapterPosition();
                if (bindingAdapterPosition == -1 || BleDeviceRenameAdapter.this.onItemClickListener == null) {
                    return;
                }
                BleDeviceRenameAdapter.this.onItemClickListener.onOffClick(view, bindingAdapterPosition, false);
            }
        });
        viewHolder.editView.setOnClickListener(new DisDoubleClickListener() { // from class: com.brgd.brblmesh.GeneralAdapter.BleDeviceRenameAdapter.3
            @Override // com.brgd.brblmesh.GeneralClass.DisDoubleClickListener
            protected void disDoubleClick(View view) {
                int bindingAdapterPosition = viewHolder.getBindingAdapterPosition();
                if (bindingAdapterPosition == -1 || BleDeviceRenameAdapter.this.onItemClickListener == null) {
                    return;
                }
                BleDeviceRenameAdapter.this.onItemClickListener.onRenameClick(view, bindingAdapterPosition);
            }
        });
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView deviceNameView;
        ImageView editView;
        ImageView lineView;
        TextView offView;
        TextView onView;

        public ViewHolder(View view) {
            super(view);
            this.editView = (ImageView) view.findViewById(R.id.device_rename_list_item_img);
            this.onView = (TextView) view.findViewById(R.id.device_rename_list_on);
            this.lineView = (ImageView) view.findViewById(R.id.device_rename_list_line);
            this.offView = (TextView) view.findViewById(R.id.device_rename_list_off);
            this.deviceNameView = (TextView) view.findViewById(R.id.device_rename_list_item_title);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
