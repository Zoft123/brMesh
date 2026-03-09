package com.brgd.brblmesh.GeneralAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.brgd.brblmesh.GeneralAdapter.model.Groups;
import com.brgd.brblmesh.GeneralClass.DisDoubleClickListener;
import com.brgd.brblmesh.R;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class GroupsListAdapter extends RecyclerView.Adapter<ViewHolder> {
    private final Context context;
    List<Groups> list;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onClick(View view, int i);
    }

    public GroupsListAdapter(Context context, List<Groups> list) {
        this.list = list;
        this.context = context;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(this.context).inflate(R.layout.group_list_item, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {
        Groups groups = this.list.get(i);
        viewHolder.groupNameView.setText(groups.getGroupName());
        if (groups.isSort) {
            viewHolder.sortView.setVisibility(0);
        } else {
            viewHolder.sortView.setVisibility(4);
        }
        if (groups.isDelete) {
            viewHolder.deleteView.setVisibility(0);
        } else {
            viewHolder.deleteView.setVisibility(4);
        }
        if (groups.isRename) {
            viewHolder.renameView.setVisibility(0);
        } else {
            viewHolder.renameView.setVisibility(4);
        }
        viewHolder.deleteView.setOnClickListener(new DisDoubleClickListener() { // from class: com.brgd.brblmesh.GeneralAdapter.GroupsListAdapter.1
            @Override // com.brgd.brblmesh.GeneralClass.DisDoubleClickListener
            protected void disDoubleClick(View view) {
                int bindingAdapterPosition = viewHolder.getBindingAdapterPosition();
                if (bindingAdapterPosition == -1 || GroupsListAdapter.this.onItemClickListener == null) {
                    return;
                }
                GroupsListAdapter.this.onItemClickListener.onClick(view, bindingAdapterPosition);
            }
        });
        viewHolder.renameView.setOnClickListener(new DisDoubleClickListener() { // from class: com.brgd.brblmesh.GeneralAdapter.GroupsListAdapter.2
            @Override // com.brgd.brblmesh.GeneralClass.DisDoubleClickListener
            protected void disDoubleClick(View view) {
                int bindingAdapterPosition = viewHolder.getBindingAdapterPosition();
                if (bindingAdapterPosition == -1 || GroupsListAdapter.this.onItemClickListener == null) {
                    return;
                }
                GroupsListAdapter.this.onItemClickListener.onClick(view, bindingAdapterPosition);
            }
        });
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView deleteView;
        TextView groupNameView;
        ImageView renameView;
        ImageView sortView;

        public ViewHolder(View view) {
            super(view);
            this.groupNameView = (TextView) view.findViewById(R.id.group_list_item_title);
            this.sortView = (ImageView) view.findViewById(R.id.group_list_item_img);
            this.deleteView = (ImageView) view.findViewById(R.id.group_list_item_delete);
            this.renameView = (ImageView) view.findViewById(R.id.group_list_item_rename);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
