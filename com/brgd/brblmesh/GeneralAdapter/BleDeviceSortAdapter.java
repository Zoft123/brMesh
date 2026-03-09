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
public class BleDeviceSortAdapter extends RecyclerView.Adapter<ViewHolder> {
    private final Context context;
    List<BleDevice> list;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onClick(View view, int i);
    }

    public BleDeviceSortAdapter(Context context, List<BleDevice> list) {
        this.list = list;
        this.context = context;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(this.context).inflate(R.layout.device_sort_list_item, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        BleDevice bleDevice = this.list.get(i);
        viewHolder.deviceNameView.setText(bleDevice.getName());
        if (bleDevice.isDelete) {
            viewHolder.deleteView.setVisibility(0);
            viewHolder.sortView.setVisibility(4);
        } else {
            viewHolder.deleteView.setVisibility(4);
            viewHolder.sortView.setVisibility(0);
        }
        viewHolder.deleteView.setTag(Integer.valueOf(i));
        viewHolder.deleteView.setOnClickListener(new DisDoubleClickListener() { // from class: com.brgd.brblmesh.GeneralAdapter.BleDeviceSortAdapter.1
            @Override // com.brgd.brblmesh.GeneralClass.DisDoubleClickListener
            protected void disDoubleClick(View view) {
                if (BleDeviceSortAdapter.this.onItemClickListener != null) {
                    BleDeviceSortAdapter.this.onItemClickListener.onClick(view, ((Integer) view.getTag()).intValue());
                }
            }
        });
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView deleteView;
        TextView deviceNameView;
        ImageView sortView;

        public ViewHolder(View view) {
            super(view);
            this.deviceNameView = (TextView) view.findViewById(R.id.device_sort_list_item_title);
            this.sortView = (ImageView) view.findViewById(R.id.device_sort_list_item_img);
            this.deleteView = (ImageView) view.findViewById(R.id.device_sort_list_item_delete);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
