package com.brgd.brblmesh.GeneralAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.brgd.brblmesh.GeneralAdapter.model.Groups;
import com.brgd.brblmesh.GeneralClass.DisDoubleClickListener;
import com.brgd.brblmesh.R;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class GroupsAdapter extends RecyclerView.Adapter<ViewHolder> {
    private final Context context;
    private final List<Groups> list;
    private OnItemButtonClickListener onItemButtonClickListener;

    public interface OnItemButtonClickListener {
        void OnItemButtonClick(View view, int i);
    }

    public GroupsAdapter(Context context, List<Groups> list) {
        this.context = context;
        this.list = list;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(this.context).inflate(R.layout.group_title_item, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Groups groups = this.list.get(i);
        viewHolder.groupNameView.setText(groups.getGroupName());
        if (groups.isSelect) {
            viewHolder.bottomView.setSelected(true);
            viewHolder.groupNameView.setTextColor(ContextCompat.getColor(this.context, R.color.colorSelect));
        } else {
            viewHolder.bottomView.setSelected(false);
            viewHolder.groupNameView.setTextColor(ContextCompat.getColor(this.context, R.color.colorText));
        }
        viewHolder.itemView.setTag(Integer.valueOf(i));
        viewHolder.itemView.setOnClickListener(new DisDoubleClickListener() { // from class: com.brgd.brblmesh.GeneralAdapter.GroupsAdapter.1
            @Override // com.brgd.brblmesh.GeneralClass.DisDoubleClickListener
            protected void disDoubleClick(View view) {
                if (GroupsAdapter.this.onItemButtonClickListener != null) {
                    GroupsAdapter.this.onItemButtonClickListener.OnItemButtonClick(view, ((Integer) view.getTag()).intValue());
                }
            }
        });
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView bottomView;
        private final TextView groupNameView;

        ViewHolder(View view) {
            super(view);
            this.groupNameView = (TextView) view.findViewById(R.id.group_title);
            this.bottomView = (ImageView) view.findViewById(R.id.group_title_bottom);
        }
    }

    public void setOnItemButtonClickListener(OnItemButtonClickListener onItemButtonClickListener) {
        this.onItemButtonClickListener = onItemButtonClickListener;
    }
}
