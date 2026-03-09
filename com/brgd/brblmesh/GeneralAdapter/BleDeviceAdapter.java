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
public class BleDeviceAdapter extends RecyclerView.Adapter<ViewHolder> {
    private final Context context;
    List<BleDevice> list;
    private OnPowerClickListener onPowerClickListener;

    public interface OnPowerClickListener {
        void onOffClick(View view, int i, Boolean bool);

        void onOptionClick(View view, int i, View view2);

        void onSelectClick(View view, int i, View view2);
    }

    public BleDeviceAdapter(Context context, List<BleDevice> list) {
        this.list = list;
        this.context = context;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(this.context).inflate(R.layout.device_list_item, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {
        BleDevice bleDevice = this.list.get(i);
        viewHolder.deviceNameTextView.setText(bleDevice.getName());
        viewHolder.typeTextView.setText(R.string.UnKnow);
        if (bleDevice.getType() == 43050) {
            viewHolder.typeTextView.setText(R.string.rgbcw);
        } else if (bleDevice.getType() == 43169) {
            viewHolder.typeTextView.setText(R.string.rgbw);
        } else if (bleDevice.getType() == 43168) {
            viewHolder.typeTextView.setText(R.string.rgb);
        } else if (bleDevice.getType() == 43051) {
            viewHolder.typeTextView.setText(R.string.cct);
        } else if (bleDevice.getType() == 43049) {
            viewHolder.typeTextView.setText(R.string.pwr);
        }
        if (bleDevice.isGroupStatus) {
            viewHolder.optionView.setVisibility(0);
            if (bleDevice.isSelect) {
                viewHolder.itemBgView.setSelected(true);
                viewHolder.optionView.setSelected(true);
            } else {
                viewHolder.itemBgView.setSelected(false);
                viewHolder.optionView.setSelected(false);
            }
            viewHolder.onView.setVisibility(4);
            viewHolder.offView.setVisibility(4);
            viewHolder.lineView.setVisibility(4);
        } else {
            viewHolder.optionView.setVisibility(4);
            viewHolder.itemBgView.setSelected(false);
            viewHolder.onView.setVisibility(0);
            viewHolder.offView.setVisibility(0);
            viewHolder.lineView.setVisibility(0);
        }
        viewHolder.optionView.setOnClickListener(new DisDoubleClickListener() { // from class: com.brgd.brblmesh.GeneralAdapter.BleDeviceAdapter.1
            @Override // com.brgd.brblmesh.GeneralClass.DisDoubleClickListener
            protected void disDoubleClick(View view) {
                int bindingAdapterPosition = viewHolder.getBindingAdapterPosition();
                if (bindingAdapterPosition == -1 || BleDeviceAdapter.this.onPowerClickListener == null) {
                    return;
                }
                BleDeviceAdapter.this.onPowerClickListener.onOptionClick(view, bindingAdapterPosition, viewHolder.itemBgView);
            }
        });
        viewHolder.onView.setOnClickListener(new DisDoubleClickListener() { // from class: com.brgd.brblmesh.GeneralAdapter.BleDeviceAdapter.2
            @Override // com.brgd.brblmesh.GeneralClass.DisDoubleClickListener
            protected void disDoubleClick(View view) {
                int bindingAdapterPosition = viewHolder.getBindingAdapterPosition();
                if (bindingAdapterPosition == -1 || BleDeviceAdapter.this.onPowerClickListener == null) {
                    return;
                }
                BleDeviceAdapter.this.onPowerClickListener.onOffClick(view, bindingAdapterPosition, true);
            }
        });
        viewHolder.offView.setOnClickListener(new DisDoubleClickListener() { // from class: com.brgd.brblmesh.GeneralAdapter.BleDeviceAdapter.3
            @Override // com.brgd.brblmesh.GeneralClass.DisDoubleClickListener
            protected void disDoubleClick(View view) {
                int bindingAdapterPosition = viewHolder.getBindingAdapterPosition();
                if (bindingAdapterPosition == -1 || BleDeviceAdapter.this.onPowerClickListener == null) {
                    return;
                }
                BleDeviceAdapter.this.onPowerClickListener.onOffClick(view, bindingAdapterPosition, false);
            }
        });
        viewHolder.itemBgView.setOnClickListener(new DisDoubleClickListener() { // from class: com.brgd.brblmesh.GeneralAdapter.BleDeviceAdapter.4
            @Override // com.brgd.brblmesh.GeneralClass.DisDoubleClickListener
            protected void disDoubleClick(View view) {
                int bindingAdapterPosition = viewHolder.getBindingAdapterPosition();
                if (bindingAdapterPosition == -1 || BleDeviceAdapter.this.onPowerClickListener == null) {
                    return;
                }
                BleDeviceAdapter.this.onPowerClickListener.onSelectClick(view, bindingAdapterPosition, viewHolder.optionView);
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
        ImageView lineView;
        TextView offView;
        TextView onView;
        ImageView optionView;
        TextView typeTextView;

        public ViewHolder(View view) {
            super(view);
            this.itemBgView = (ImageView) view.findViewById(R.id.device_list_item_bg);
            this.onView = (TextView) view.findViewById(R.id.device_list_on);
            this.lineView = (ImageView) view.findViewById(R.id.device_list_line);
            this.offView = (TextView) view.findViewById(R.id.device_list_off);
            this.optionView = (ImageView) view.findViewById(R.id.device_list_option);
            this.deviceNameTextView = (TextView) view.findViewById(R.id.device_list_item_title);
            this.typeTextView = (TextView) view.findViewById(R.id.device_list_item_type);
        }
    }

    public void setOnPowerClickListener(OnPowerClickListener onPowerClickListener) {
        this.onPowerClickListener = onPowerClickListener;
    }
}
