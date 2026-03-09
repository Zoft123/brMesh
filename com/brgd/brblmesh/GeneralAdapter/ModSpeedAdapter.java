package com.brgd.brblmesh.GeneralAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.brgd.brblmesh.GeneralAdapter.model.ModSpeed;
import com.brgd.brblmesh.GeneralClass.DisDoubleClickListener;
import com.brgd.brblmesh.R;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class ModSpeedAdapter extends RecyclerView.Adapter<ViewHolder> {
    private final Context context;
    private final List<ModSpeed> list;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void OnItemClick(View view, int i);
    }

    public ModSpeedAdapter(Context context, List<ModSpeed> list) {
        this.context = context;
        this.list = list;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(this.context).inflate(R.layout.mod_speed_item, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        ModSpeed modSpeed = this.list.get(i);
        if (modSpeed.value != 0) {
            viewHolder.modSpeedText.setText(String.valueOf(modSpeed.value));
        } else {
            viewHolder.modSpeedText.setText(String.valueOf(modSpeed.speed));
        }
        viewHolder.selectView.setSelected(modSpeed.isSelect);
        viewHolder.selectView.setTag(Integer.valueOf(i));
        viewHolder.selectView.setOnClickListener(new DisDoubleClickListener() { // from class: com.brgd.brblmesh.GeneralAdapter.ModSpeedAdapter.1
            @Override // com.brgd.brblmesh.GeneralClass.DisDoubleClickListener
            protected void disDoubleClick(View view) {
                if (ModSpeedAdapter.this.onItemClickListener != null) {
                    ModSpeedAdapter.this.onItemClickListener.OnItemClick(view, ((Integer) view.getTag()).intValue());
                }
            }
        });
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView modSpeedText;
        ImageView selectView;

        ViewHolder(View view) {
            super(view);
            this.selectView = (ImageView) view.findViewById(R.id.mod_speed_item);
            this.modSpeedText = (TextView) view.findViewById(R.id.mod_speed_item_title);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
