package com.brgd.brblmesh.GeneralAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.recyclerview.widget.RecyclerView;
import com.brgd.brblmesh.GeneralAdapter.model.DiyColor;
import com.brgd.brblmesh.GeneralClass.DisDoubleClickListener;
import com.brgd.brblmesh.GeneralClass.Tool;
import com.brgd.brblmesh.GeneralClass.colorpicker.ColorSelector;
import com.brgd.brblmesh.R;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class DiyColorAdapter extends RecyclerView.Adapter<ViewHolder> {
    private final Context context;
    private final List<DiyColor> list;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void OnItemClick(View view, int i);
    }

    public DiyColorAdapter(Context context, List<DiyColor> list) {
        this.context = context;
        this.list = list;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(this.context).inflate(R.layout.diy_color_item, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        DiyColor diyColor = this.list.get(i);
        if (diyColor.getColorValue() == -3) {
            viewHolder.itemImg.setVisibility(0);
            viewHolder.colorView.setSelectorColor(-1);
        } else if (Tool.isRGBW(diyColor.getColorValue())) {
            viewHolder.itemImg.setVisibility(8);
            viewHolder.colorView.setSelectorColor(-1);
        } else {
            viewHolder.itemImg.setVisibility(8);
            viewHolder.colorView.setSelectorColor(diyColor.getColorValue());
        }
        viewHolder.colorView.setTag(Integer.valueOf(i));
        viewHolder.colorView.setOnClickListener(new DisDoubleClickListener() { // from class: com.brgd.brblmesh.GeneralAdapter.DiyColorAdapter.1
            @Override // com.brgd.brblmesh.GeneralClass.DisDoubleClickListener
            protected void disDoubleClick(View view) {
                if (DiyColorAdapter.this.onItemClickListener != null) {
                    DiyColorAdapter.this.onItemClickListener.OnItemClick(view, ((Integer) view.getTag()).intValue());
                }
            }
        });
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ColorSelector colorView;
        ImageView itemImg;

        ViewHolder(View view) {
            super(view);
            this.colorView = (ColorSelector) view.findViewById(R.id.diy_color_item_bt);
            this.itemImg = (ImageView) view.findViewById(R.id.diy_color_item_img);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
