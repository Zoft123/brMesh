package com.brgd.brblmesh.GeneralAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.brgd.brblmesh.GeneralAdapter.model.AlexaDevice;
import com.brgd.brblmesh.GeneralClass.DisDoubleClickListener;
import com.brgd.brblmesh.R;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class AlexaDeviceAdapter extends RecyclerView.Adapter<ViewHolder> {
    private final Context context;
    List<AlexaDevice> list;
    private OnPowerClickListener onPowerClickListener;

    public interface OnPowerClickListener {
        void onOffClick(View view, int i, Boolean bool);

        void onOptionClick(View view, int i);

        void onSelectClick(View view, int i);
    }

    public AlexaDeviceAdapter(Context context, List<AlexaDevice> list) {
        this.list = list;
        this.context = context;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(this.context).inflate(R.layout.alexa_list_item, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {
        AlexaDevice alexaDevice = this.list.get(i);
        viewHolder.deviceNameTextView.setText(alexaDevice.name);
        if (alexaDevice.isBeenMain) {
            viewHolder.hubView.setVisibility(0);
        } else {
            viewHolder.hubView.setVisibility(4);
        }
        viewHolder.typeTextView.setText(R.string.UnKnow);
        if (alexaDevice.type == 43050) {
            viewHolder.typeTextView.setText(R.string.rgbcw);
        } else if (alexaDevice.type == 43169) {
            viewHolder.typeTextView.setText(R.string.rgbw);
        } else if (alexaDevice.type == 43168) {
            viewHolder.typeTextView.setText(R.string.rgb);
        } else if (alexaDevice.type == 43051) {
            viewHolder.typeTextView.setText(R.string.cct);
        } else if (alexaDevice.type == 43049) {
            viewHolder.typeTextView.setText(R.string.pwr);
        } else if (alexaDevice.type == 44601) {
            viewHolder.typeTextView.setText(R.string.radar);
        }
        if (alexaDevice.isConfig) {
            viewHolder.deviceNameTextView.setTextColor(this.context.getColor(R.color.colorSelect));
            viewHolder.typeTextView.setTextColor(this.context.getColor(R.color.colorSelect));
        } else {
            viewHolder.deviceNameTextView.setTextColor(this.context.getColor(R.color.colorText));
            viewHolder.typeTextView.setTextColor(this.context.getColor(R.color.colorText));
        }
        viewHolder.optionView.setSelected(alexaDevice.isSelect);
        viewHolder.optionView.setOnClickListener(new DisDoubleClickListener() { // from class: com.brgd.brblmesh.GeneralAdapter.AlexaDeviceAdapter.1
            @Override // com.brgd.brblmesh.GeneralClass.DisDoubleClickListener
            protected void disDoubleClick(View view) {
                int bindingAdapterPosition = viewHolder.getBindingAdapterPosition();
                if (bindingAdapterPosition == -1 || AlexaDeviceAdapter.this.onPowerClickListener == null) {
                    return;
                }
                AlexaDeviceAdapter.this.onPowerClickListener.onOptionClick(view, bindingAdapterPosition);
            }
        });
        viewHolder.itemBgView.setOnClickListener(new DisDoubleClickListener() { // from class: com.brgd.brblmesh.GeneralAdapter.AlexaDeviceAdapter.2
            @Override // com.brgd.brblmesh.GeneralClass.DisDoubleClickListener
            protected void disDoubleClick(View view) {
                int bindingAdapterPosition = viewHolder.getBindingAdapterPosition();
                if (bindingAdapterPosition == -1 || AlexaDeviceAdapter.this.onPowerClickListener == null) {
                    return;
                }
                AlexaDeviceAdapter.this.onPowerClickListener.onSelectClick(view, bindingAdapterPosition);
            }
        });
        viewHolder.onView.setOnClickListener(new DisDoubleClickListener() { // from class: com.brgd.brblmesh.GeneralAdapter.AlexaDeviceAdapter.3
            @Override // com.brgd.brblmesh.GeneralClass.DisDoubleClickListener
            protected void disDoubleClick(View view) {
                int bindingAdapterPosition = viewHolder.getBindingAdapterPosition();
                if (bindingAdapterPosition == -1 || AlexaDeviceAdapter.this.onPowerClickListener == null) {
                    return;
                }
                AlexaDeviceAdapter.this.onPowerClickListener.onOffClick(view, bindingAdapterPosition, true);
            }
        });
        viewHolder.offView.setOnClickListener(new DisDoubleClickListener() { // from class: com.brgd.brblmesh.GeneralAdapter.AlexaDeviceAdapter.4
            @Override // com.brgd.brblmesh.GeneralClass.DisDoubleClickListener
            protected void disDoubleClick(View view) {
                int bindingAdapterPosition = viewHolder.getBindingAdapterPosition();
                if (bindingAdapterPosition == -1 || AlexaDeviceAdapter.this.onPowerClickListener == null) {
                    return;
                }
                AlexaDeviceAdapter.this.onPowerClickListener.onOffClick(view, bindingAdapterPosition, false);
            }
        });
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView deviceNameTextView;
        TextView hubView;
        ImageView itemBgView;
        ImageView lineView;
        TextView offView;
        TextView onView;
        ImageView optionView;
        TextView typeTextView;

        public ViewHolder(View view) {
            super(view);
            this.itemBgView = (ImageView) view.findViewById(R.id.alexa_list_item_bg);
            this.onView = (TextView) view.findViewById(R.id.alexa_list_on);
            this.lineView = (ImageView) view.findViewById(R.id.alexa_list_line);
            this.offView = (TextView) view.findViewById(R.id.alexa_list_off);
            this.optionView = (ImageView) view.findViewById(R.id.alexa_list_option);
            this.deviceNameTextView = (TextView) view.findViewById(R.id.alexa_list_item_title);
            this.typeTextView = (TextView) view.findViewById(R.id.alexa_list_item_type);
            this.hubView = (TextView) view.findViewById(R.id.add_list_item_setMain);
        }
    }

    public void setOnPowerClickListener(OnPowerClickListener onPowerClickListener) {
        this.onPowerClickListener = onPowerClickListener;
    }
}
