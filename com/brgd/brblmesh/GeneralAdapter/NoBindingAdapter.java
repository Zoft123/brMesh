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
public class NoBindingAdapter extends RecyclerView.Adapter<ViewHolder> {
    private final Context context;
    List<BleDevice> list;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, int i);

        void optionClick(View view, int i);
    }

    public NoBindingAdapter(Context context, List<BleDevice> list) {
        this.list = list;
        this.context = context;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(this.context).inflate(R.layout.new_group_all_list_item, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {
        BleDevice bleDevice = this.list.get(i);
        viewHolder.nameView.setText(bleDevice.getName());
        if (bleDevice.isGroupStatus) {
            viewHolder.addView.setVisibility(4);
            viewHolder.optionView.setVisibility(0);
            viewHolder.optionView.setSelected(bleDevice.isSelect);
            if (bleDevice.isSupportGroupMain()) {
                viewHolder.nameView.setTextColor(this.context.getColor(R.color.colorSelect));
            } else {
                viewHolder.nameView.setTextColor(this.context.getColor(R.color.colorText));
            }
            viewHolder.bgView.setOnClickListener(new DisDoubleClickListener() { // from class: com.brgd.brblmesh.GeneralAdapter.NoBindingAdapter.1
                @Override // com.brgd.brblmesh.GeneralClass.DisDoubleClickListener
                protected void disDoubleClick(View view) {
                    int bindingAdapterPosition = viewHolder.getBindingAdapterPosition();
                    if (bindingAdapterPosition == -1 || NoBindingAdapter.this.onItemClickListener == null) {
                        return;
                    }
                    NoBindingAdapter.this.onItemClickListener.optionClick(view, bindingAdapterPosition);
                }
            });
            return;
        }
        viewHolder.addView.setVisibility(0);
        viewHolder.optionView.setVisibility(4);
        if (bleDevice.isSupportGroupMain()) {
            viewHolder.nameView.setTextColor(this.context.getColor(R.color.colorSelect));
        } else {
            viewHolder.nameView.setTextColor(this.context.getColor(R.color.colorText));
        }
        if (bleDevice.groupName != null) {
            viewHolder.groupNameView.setText(bleDevice.groupName);
        }
        viewHolder.addView.setOnClickListener(new DisDoubleClickListener() { // from class: com.brgd.brblmesh.GeneralAdapter.NoBindingAdapter.2
            @Override // com.brgd.brblmesh.GeneralClass.DisDoubleClickListener
            protected void disDoubleClick(View view) {
                int bindingAdapterPosition = viewHolder.getBindingAdapterPosition();
                if (bindingAdapterPosition == -1 || NoBindingAdapter.this.onItemClickListener == null) {
                    return;
                }
                NoBindingAdapter.this.onItemClickListener.onItemClick(view, bindingAdapterPosition);
            }
        });
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView addView;
        ImageView bgView;
        TextView groupNameView;
        TextView nameView;
        ImageView optionView;

        public ViewHolder(View view) {
            super(view);
            this.bgView = (ImageView) view.findViewById(R.id.all_list_item_bg);
            this.nameView = (TextView) view.findViewById(R.id.all_list_item_title);
            this.groupNameView = (TextView) view.findViewById(R.id.all_list_item_group);
            this.addView = (ImageView) view.findViewById(R.id.all_list_item_add);
            this.optionView = (ImageView) view.findViewById(R.id.all_list_item_option);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
