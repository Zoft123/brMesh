package com.brgd.brblmesh.GeneralAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.core.graphics.ColorUtils;
import androidx.recyclerview.widget.RecyclerView;
import com.brgd.brblmesh.GeneralAdapter.model.DiyColor;
import com.brgd.brblmesh.GeneralClass.DisDoubleClickListener;
import com.brgd.brblmesh.GeneralClass.colorpicker.ColorSelector;
import com.brgd.brblmesh.GeneralClass.colorpicker.CustomColor;
import com.brgd.brblmesh.R;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class ColorTempAdapter extends RecyclerView.Adapter<ViewHolder> {
    private final Context context;
    private final List<DiyColor> list;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void OnItemClick(View view, int i);
    }

    public ColorTempAdapter(Context context, List<DiyColor> list) {
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
        viewHolder.itemImg.setVisibility(8);
        viewHolder.colorView.setSelectorColor(ColorUtils.blendARGB(-1, CustomColor.W_WHITE, diyColor.getColorValue() / 100.0f));
        viewHolder.colorView.setTag(Integer.valueOf(i));
        viewHolder.colorView.setOnClickListener(new DisDoubleClickListener() { // from class: com.brgd.brblmesh.GeneralAdapter.ColorTempAdapter.1
            @Override // com.brgd.brblmesh.GeneralClass.DisDoubleClickListener
            protected void disDoubleClick(View view) {
                if (ColorTempAdapter.this.onItemClickListener != null) {
                    ColorTempAdapter.this.onItemClickListener.OnItemClick(view, ((Integer) view.getTag()).intValue());
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
