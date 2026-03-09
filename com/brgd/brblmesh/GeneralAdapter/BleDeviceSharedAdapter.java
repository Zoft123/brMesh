package com.brgd.brblmesh.GeneralAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.brgd.brblmesh.GeneralAdapter.model.BleDevice;
import com.brgd.brblmesh.GeneralClass.DisDoubleClickListener;
import com.brgd.brblmesh.R;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class BleDeviceSharedAdapter extends RecyclerView.Adapter<ViewHolder> {
    private final Context context;
    List<BleDevice> list;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onClick(View view, int i, View view2, TextView textView);
    }

    public BleDeviceSharedAdapter(Context context, List<BleDevice> list) {
        this.list = list;
        this.context = context;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(this.context).inflate(R.layout.device_shared_list_item, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {
        BleDevice bleDevice = this.list.get(i);
        viewHolder.deviceNameView.setText(bleDevice.getName());
        if (bleDevice.isSelect) {
            viewHolder.itemBgView.setSelected(true);
            viewHolder.selectView.setSelected(true);
            viewHolder.deviceNameView.setTextColor(ContextCompat.getColor(this.context, R.color.colorWhite));
        } else {
            viewHolder.itemBgView.setSelected(false);
            viewHolder.selectView.setSelected(false);
            viewHolder.deviceNameView.setTextColor(ContextCompat.getColor(this.context, R.color.colorText));
        }
        viewHolder.itemBgView.setTag(Integer.valueOf(i));
        viewHolder.itemBgView.setOnClickListener(new DisDoubleClickListener() { // from class: com.brgd.brblmesh.GeneralAdapter.BleDeviceSharedAdapter.1
            @Override // com.brgd.brblmesh.GeneralClass.DisDoubleClickListener
            protected void disDoubleClick(View view) {
                if (BleDeviceSharedAdapter.this.onItemClickListener != null) {
                    BleDeviceSharedAdapter.this.onItemClickListener.onClick(view, ((Integer) view.getTag()).intValue(), viewHolder.selectView, viewHolder.deviceNameView);
                }
            }
        });
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView deviceNameView;
        ImageView itemBgView;
        ImageView selectView;

        public ViewHolder(View view) {
            super(view);
            this.itemBgView = (ImageView) view.findViewById(R.id.device_shared_list_item_bg);
            this.selectView = (ImageView) view.findViewById(R.id.device_shared_list_item_option);
            this.deviceNameView = (TextView) view.findViewById(R.id.device_shared_list_item_title);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
