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
public class AddSceneBleDeviceAdapter extends RecyclerView.Adapter<ViewHolder> {
    private final Context context;
    List<BleDevice> list;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onInCtrlClick(View view, int i);

        void onItemClick(View view, int i);
    }

    public AddSceneBleDeviceAdapter(Context context, List<BleDevice> list) {
        this.list = list;
        this.context = context;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(this.context).inflate(R.layout.new_group_add_list_item, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {
        viewHolder.nameView.setText(this.list.get(i).getName());
        viewHolder.inCtrlView.setVisibility(0);
        viewHolder.clickView.setVisibility(0);
        viewHolder.clickView.setOnClickListener(new DisDoubleClickListener() { // from class: com.brgd.brblmesh.GeneralAdapter.AddSceneBleDeviceAdapter.1
            @Override // com.brgd.brblmesh.GeneralClass.DisDoubleClickListener
            protected void disDoubleClick(View view) {
                int bindingAdapterPosition = viewHolder.getBindingAdapterPosition();
                if (bindingAdapterPosition == -1 || AddSceneBleDeviceAdapter.this.onItemClickListener == null) {
                    return;
                }
                AddSceneBleDeviceAdapter.this.onItemClickListener.onInCtrlClick(view, bindingAdapterPosition);
            }
        });
        viewHolder.deleteView.setOnClickListener(new DisDoubleClickListener() { // from class: com.brgd.brblmesh.GeneralAdapter.AddSceneBleDeviceAdapter.2
            @Override // com.brgd.brblmesh.GeneralClass.DisDoubleClickListener
            protected void disDoubleClick(View view) {
                int bindingAdapterPosition = viewHolder.getBindingAdapterPosition();
                if (bindingAdapterPosition == -1 || AddSceneBleDeviceAdapter.this.onItemClickListener == null) {
                    return;
                }
                AddSceneBleDeviceAdapter.this.onItemClickListener.onItemClick(view, bindingAdapterPosition);
            }
        });
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView clickView;
        ImageView deleteView;
        ImageView inCtrlView;
        TextView nameView;

        public ViewHolder(View view) {
            super(view);
            this.nameView = (TextView) view.findViewById(R.id.add_list_item_title);
            this.inCtrlView = (ImageView) view.findViewById(R.id.add_list_item_in);
            this.clickView = (ImageView) view.findViewById(R.id.add_list_item_click);
            this.deleteView = (ImageView) view.findViewById(R.id.add_list_item_delete);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
