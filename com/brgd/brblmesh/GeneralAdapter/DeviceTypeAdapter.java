package com.brgd.brblmesh.GeneralAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.brgd.brblmesh.GeneralAdapter.model.DeviceType;
import com.brgd.brblmesh.GeneralClass.DisDoubleClickListener;
import com.brgd.brblmesh.R;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class DeviceTypeAdapter extends RecyclerView.Adapter<ViewHolder> {
    private final Context context;
    private final List<DeviceType> list;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void OnItemClick(View view, int i);
    }

    public DeviceTypeAdapter(Context context, List<DeviceType> list) {
        this.context = context;
        this.list = list;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(this.context).inflate(R.layout.device_type_item, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        DeviceType deviceType = this.list.get(i);
        viewHolder.nameText.setText(deviceType.name);
        viewHolder.selectView.setSelected(deviceType.isSelect);
        if (deviceType.isSelect) {
            viewHolder.nameText.setTextColor(this.context.getColor(R.color.colorWhite));
        } else {
            viewHolder.nameText.setTextColor(this.context.getColor(R.color.colorText));
        }
        viewHolder.selectView.setTag(Integer.valueOf(i));
        viewHolder.selectView.setOnClickListener(new DisDoubleClickListener() { // from class: com.brgd.brblmesh.GeneralAdapter.DeviceTypeAdapter.1
            @Override // com.brgd.brblmesh.GeneralClass.DisDoubleClickListener
            protected void disDoubleClick(View view) {
                if (DeviceTypeAdapter.this.onItemClickListener != null) {
                    DeviceTypeAdapter.this.onItemClickListener.OnItemClick(view, ((Integer) view.getTag()).intValue());
                }
            }
        });
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameText;
        ImageView selectView;

        ViewHolder(View view) {
            super(view);
            this.selectView = (ImageView) view.findViewById(R.id.device_type_item);
            this.nameText = (TextView) view.findViewById(R.id.device_type_item_title);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
