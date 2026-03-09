package com.brgd.brblmesh.GeneralAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.recyclerview.widget.RecyclerView;
import com.brgd.brblmesh.GeneralAdapter.model.ModDiyColor;
import com.brgd.brblmesh.GeneralClass.DisDoubleClickListener;
import com.brgd.brblmesh.GeneralClass.Tool;
import com.brgd.brblmesh.GeneralClass.colorpicker.ColorSelector;
import com.brgd.brblmesh.R;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class ModDiyColorAdapter extends RecyclerView.Adapter<ViewHolder> {
    private final Context context;
    private final List<ModDiyColor> list;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void OnItemAddClick(View view, int i);

        void OnItemDeleteClick(View view, int i);
    }

    public ModDiyColorAdapter(Context context, List<ModDiyColor> list) {
        this.context = context;
        this.list = list;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(this.context).inflate(R.layout.mod_diycolor_item, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {
        ModDiyColor modDiyColor = this.list.get(i);
        if (modDiyColor.getDiyColor() != -2) {
            viewHolder.colorView.setVisibility(0);
            if (modDiyColor.getDiyColor() == -3 || Tool.isRGBW(modDiyColor.getDiyColor())) {
                viewHolder.itemImg.setVisibility(0);
                viewHolder.colorView.setSelectorColor(-1);
                viewHolder.colorView.setBorderSelectorColor(-1);
            } else {
                viewHolder.itemImg.setVisibility(8);
                viewHolder.colorView.setSelectorColor(modDiyColor.getDiyColor());
                viewHolder.colorView.setBorderSelectorColor(modDiyColor.getDiyColor());
            }
            viewHolder.colorDeleteView.setVisibility(0);
        } else {
            viewHolder.itemImg.setVisibility(8);
            viewHolder.colorView.setVisibility(4);
            viewHolder.colorDeleteView.setVisibility(4);
        }
        viewHolder.colorAddView.setOnClickListener(new DisDoubleClickListener() { // from class: com.brgd.brblmesh.GeneralAdapter.ModDiyColorAdapter.1
            @Override // com.brgd.brblmesh.GeneralClass.DisDoubleClickListener
            protected void disDoubleClick(View view) {
                int bindingAdapterPosition = viewHolder.getBindingAdapterPosition();
                if (bindingAdapterPosition == -1 || ModDiyColorAdapter.this.onItemClickListener == null) {
                    return;
                }
                ModDiyColorAdapter.this.onItemClickListener.OnItemAddClick(view, bindingAdapterPosition);
            }
        });
        viewHolder.colorDeleteView.setOnClickListener(new DisDoubleClickListener() { // from class: com.brgd.brblmesh.GeneralAdapter.ModDiyColorAdapter.2
            @Override // com.brgd.brblmesh.GeneralClass.DisDoubleClickListener
            protected void disDoubleClick(View view) {
                int bindingAdapterPosition = viewHolder.getBindingAdapterPosition();
                if (bindingAdapterPosition == -1 || ModDiyColorAdapter.this.onItemClickListener == null) {
                    return;
                }
                ModDiyColorAdapter.this.onItemClickListener.OnItemDeleteClick(view, bindingAdapterPosition);
            }
        });
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView colorAddView;
        ImageView colorDeleteView;
        ColorSelector colorView;
        ImageView itemImg;

        ViewHolder(View view) {
            super(view);
            this.colorAddView = (ImageView) view.findViewById(R.id.mod_diyColor_add);
            this.colorView = (ColorSelector) view.findViewById(R.id.mod_diyColor_item);
            this.itemImg = (ImageView) view.findViewById(R.id.mod_diyColor_item_img);
            this.colorDeleteView = (ImageView) view.findViewById(R.id.mod_diyColor_delete);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
