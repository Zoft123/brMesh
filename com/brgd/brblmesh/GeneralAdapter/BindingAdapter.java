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
public class BindingAdapter extends RecyclerView.Adapter<ViewHolder> {
    private final Context context;
    List<BleDevice> list;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, int i);

        void onSetMainClick(View view, int i);

        void optionClick(View view, int i);
    }

    public BindingAdapter(Context context, List<BleDevice> list) {
        this.list = list;
        this.context = context;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(this.context).inflate(R.layout.new_group_add_list_item, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {
        BleDevice bleDevice = this.list.get(i);
        viewHolder.nameView.setText(bleDevice.getName());
        if (bleDevice.isGroupStatus) {
            viewHolder.deleteView.setVisibility(8);
            viewHolder.mainTextView.setVisibility(8);
            viewHolder.setBtView.setVisibility(8);
            viewHolder.optionView.setVisibility(0);
            viewHolder.optionView.setSelected(bleDevice.isSelect);
            if (bleDevice.isSupportGroupMain()) {
                viewHolder.nameView.setTextColor(this.context.getColor(R.color.colorSelect));
            } else {
                viewHolder.nameView.setTextColor(this.context.getColor(R.color.colorText));
            }
            viewHolder.bgView.setOnClickListener(new DisDoubleClickListener() { // from class: com.brgd.brblmesh.GeneralAdapter.BindingAdapter.1
                @Override // com.brgd.brblmesh.GeneralClass.DisDoubleClickListener
                protected void disDoubleClick(View view) {
                    int bindingAdapterPosition = viewHolder.getBindingAdapterPosition();
                    if (bindingAdapterPosition == -1 || BindingAdapter.this.onItemClickListener == null) {
                        return;
                    }
                    BindingAdapter.this.onItemClickListener.optionClick(view, bindingAdapterPosition);
                }
            });
            return;
        }
        viewHolder.deleteView.setVisibility(0);
        viewHolder.optionView.setVisibility(4);
        if (bleDevice.isSupportGroupMain()) {
            viewHolder.nameView.setTextColor(this.context.getColor(R.color.colorSelect));
            viewHolder.setBtView.setVisibility(0);
        } else {
            viewHolder.nameView.setTextColor(this.context.getColor(R.color.colorText));
            viewHolder.setBtView.setVisibility(8);
        }
        if (bleDevice.isCurrentFixedGroup) {
            viewHolder.setBtView.setSelected(true);
            viewHolder.mainTextView.setVisibility(0);
        } else {
            viewHolder.setBtView.setSelected(false);
            viewHolder.mainTextView.setVisibility(8);
        }
        viewHolder.setBtView.setOnClickListener(new DisDoubleClickListener() { // from class: com.brgd.brblmesh.GeneralAdapter.BindingAdapter.2
            @Override // com.brgd.brblmesh.GeneralClass.DisDoubleClickListener
            protected void disDoubleClick(View view) {
                int bindingAdapterPosition = viewHolder.getBindingAdapterPosition();
                if (bindingAdapterPosition == -1 || BindingAdapter.this.onItemClickListener == null) {
                    return;
                }
                BindingAdapter.this.onItemClickListener.onSetMainClick(view, bindingAdapterPosition);
            }
        });
        viewHolder.deleteView.setOnClickListener(new DisDoubleClickListener() { // from class: com.brgd.brblmesh.GeneralAdapter.BindingAdapter.3
            @Override // com.brgd.brblmesh.GeneralClass.DisDoubleClickListener
            protected void disDoubleClick(View view) {
                int bindingAdapterPosition = viewHolder.getBindingAdapterPosition();
                if (bindingAdapterPosition == -1 || BindingAdapter.this.onItemClickListener == null) {
                    return;
                }
                BindingAdapter.this.onItemClickListener.onItemClick(view, bindingAdapterPosition);
            }
        });
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView bgView;
        ImageView deleteView;
        TextView mainTextView;
        TextView nameView;
        ImageView optionView;
        ImageView setBtView;

        public ViewHolder(View view) {
            super(view);
            this.bgView = (ImageView) view.findViewById(R.id.add_list_item_bg);
            this.nameView = (TextView) view.findViewById(R.id.add_list_item_title);
            this.deleteView = (ImageView) view.findViewById(R.id.add_list_item_delete);
            this.mainTextView = (TextView) view.findViewById(R.id.add_list_item_setMain);
            this.setBtView = (ImageView) view.findViewById(R.id.add_list_item_setMain_bt);
            this.optionView = (ImageView) view.findViewById(R.id.add_list_item_option);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
