package com.brgd.brblmesh.GeneralAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.brgd.brblmesh.GeneralAdapter.model.RadarDevice;
import com.brgd.brblmesh.GeneralClass.DisDoubleClickListener;
import com.brgd.brblmesh.R;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class NotInGroupAdapter extends RecyclerView.Adapter<ViewHolder> {
    private final Context context;
    List<RadarDevice> list;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, int i);

        void optionClick(View view, int i);
    }

    public NotInGroupAdapter(Context context, List<RadarDevice> list) {
        this.list = list;
        this.context = context;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(this.context).inflate(R.layout.new_group_all_list_item, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {
        RadarDevice radarDevice = this.list.get(i);
        viewHolder.nameView.setText(radarDevice.getName());
        viewHolder.groupNameView.setVisibility(4);
        if (radarDevice.isGroupStatus) {
            viewHolder.addView.setVisibility(4);
            viewHolder.optionView.setVisibility(0);
            viewHolder.optionView.setSelected(radarDevice.isSelect);
            viewHolder.bgView.setOnClickListener(new DisDoubleClickListener() { // from class: com.brgd.brblmesh.GeneralAdapter.NotInGroupAdapter.1
                @Override // com.brgd.brblmesh.GeneralClass.DisDoubleClickListener
                protected void disDoubleClick(View view) {
                    int bindingAdapterPosition = viewHolder.getBindingAdapterPosition();
                    if (bindingAdapterPosition == -1 || NotInGroupAdapter.this.onItemClickListener == null) {
                        return;
                    }
                    NotInGroupAdapter.this.onItemClickListener.optionClick(view, bindingAdapterPosition);
                }
            });
            return;
        }
        viewHolder.addView.setVisibility(0);
        viewHolder.optionView.setVisibility(4);
        viewHolder.addView.setOnClickListener(new DisDoubleClickListener() { // from class: com.brgd.brblmesh.GeneralAdapter.NotInGroupAdapter.2
            @Override // com.brgd.brblmesh.GeneralClass.DisDoubleClickListener
            protected void disDoubleClick(View view) {
                int bindingAdapterPosition = viewHolder.getBindingAdapterPosition();
                if (bindingAdapterPosition == -1 || NotInGroupAdapter.this.onItemClickListener == null) {
                    return;
                }
                NotInGroupAdapter.this.onItemClickListener.onItemClick(view, bindingAdapterPosition);
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
