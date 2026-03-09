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
public class OtaDeviceAdapter extends RecyclerView.Adapter<ViewHolder> {
    private final Context context;
    List<BleDevice> list;
    private OnUpgradeClickListener onUpgradeClickListener;

    public interface OnUpgradeClickListener {
        void onUpgradeClick(View view, int i);
    }

    public OtaDeviceAdapter(Context context, List<BleDevice> list) {
        this.list = list;
        this.context = context;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(this.context).inflate(R.layout.ota_list_item, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        BleDevice bleDevice = this.list.get(i);
        viewHolder.deviceNameTextView.setText(bleDevice.getName());
        if (bleDevice.getOtaTag() == 2) {
            viewHolder.updateTagView.setText(R.string.upgrade_fail);
        } else if (bleDevice.getOtaTag() == 1) {
            viewHolder.updateTagView.setText(R.string.notUpgrade);
        }
        viewHolder.updateTagView.setTextColor(-65536);
        viewHolder.upgradeView.setTag(Integer.valueOf(i));
        viewHolder.upgradeView.setOnClickListener(new DisDoubleClickListener() { // from class: com.brgd.brblmesh.GeneralAdapter.OtaDeviceAdapter.1
            @Override // com.brgd.brblmesh.GeneralClass.DisDoubleClickListener
            protected void disDoubleClick(View view) {
                if (OtaDeviceAdapter.this.onUpgradeClickListener != null) {
                    OtaDeviceAdapter.this.onUpgradeClickListener.onUpgradeClick(view, ((Integer) view.getTag()).intValue());
                }
            }
        });
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView deviceNameTextView;
        ImageView itemBgView;
        TextView updateTagView;
        TextView upgradeView;

        public ViewHolder(View view) {
            super(view);
            this.itemBgView = (ImageView) view.findViewById(R.id.ota_list_item_bg);
            this.upgradeView = (TextView) view.findViewById(R.id.ota_list_upgrade);
            this.deviceNameTextView = (TextView) view.findViewById(R.id.ota_list_item_title);
            this.updateTagView = (TextView) view.findViewById(R.id.ota_list_item_upgradeTag);
        }
    }

    public void setOnUpgradeClickListener(OnUpgradeClickListener onUpgradeClickListener) {
        this.onUpgradeClickListener = onUpgradeClickListener;
    }
}
