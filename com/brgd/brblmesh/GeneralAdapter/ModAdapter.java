package com.brgd.brblmesh.GeneralAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.brgd.brblmesh.GeneralAdapter.model.Mod;
import com.brgd.brblmesh.GeneralClass.DisDoubleClickListener;
import com.brgd.brblmesh.R;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class ModAdapter extends RecyclerView.Adapter<ViewHolder> {
    private final Context context;
    List<Mod> list;
    int[] modImgArr = {R.drawable.mod3_select, R.drawable.mod5_select, R.drawable.mod6_select, R.drawable.mod4_select, R.drawable.mod7_select, R.drawable.mod8_select, R.drawable.mod9_select, R.drawable.mod9_select, R.drawable.mod9_select, R.drawable.mod9_select, R.drawable.mod10_select, R.drawable.mod10_select, R.drawable.mod10_select, R.drawable.mod12_select, R.drawable.mod12_select, R.drawable.mod12_select, R.drawable.mod13_select, R.drawable.mod3_select, R.drawable.mod3_select, R.drawable.mod3_select, R.drawable.mod3_select, R.drawable.mod11_select, R.drawable.mod11_select, R.drawable.mod11_select, R.drawable.mod11_select, R.drawable.mod11_select, R.drawable.mod11_select, R.drawable.mod11_select, R.drawable.mod11_select, R.drawable.mod1_select, R.drawable.mod1_select};
    int[] modNameArr = {R.string.Mod1, R.string.Mod2, R.string.Mod3, R.string.Mod4, R.string.Mod5, R.string.Mod6, R.string.Mod7, R.string.Mod8, R.string.Mod9, R.string.Mod10, R.string.Mod11, R.string.Mod12, R.string.Mod13, R.string.Mod14, R.string.Mod15, R.string.Mod16, R.string.Mod17, R.string.Mod18, R.string.Mod19, R.string.Mod20, R.string.Mod21, R.string.Mod22, R.string.ModFlash2, R.string.ModFlash3, R.string.ModFlash4, R.string.Mod23, R.string.ModFade2, R.string.ModFade3, R.string.ModFade4, R.string.SleepMode, R.string.WakeUpMode};
    private OnModClickListener onModClickListener;

    public interface OnModClickListener {
        void onAddClick();

        void onModClick(View view, int i, View view2);

        void onNameClick(View view, int i);
    }

    public ModAdapter(Context context, List<Mod> list) {
        this.list = list;
        this.context = context;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(this.context).inflate(R.layout.mod_list_item, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {
        Mod mod = this.list.get(i);
        if (mod.getModNumber() == 0 && mod.getCustomModId() == null) {
            viewHolder.nameClickView.setVisibility(4);
            viewHolder.modNameView.setVisibility(4);
            viewHolder.editView.setVisibility(4);
            viewHolder.itemImgView.setVisibility(4);
            viewHolder.addView.setVisibility(0);
            viewHolder.addView.setOnClickListener(new DisDoubleClickListener() { // from class: com.brgd.brblmesh.GeneralAdapter.ModAdapter.1
                @Override // com.brgd.brblmesh.GeneralClass.DisDoubleClickListener
                protected void disDoubleClick(View view) {
                    if (ModAdapter.this.onModClickListener != null) {
                        ModAdapter.this.onModClickListener.onAddClick();
                    }
                }
            });
            return;
        }
        viewHolder.nameClickView.setVisibility(0);
        viewHolder.modNameView.setVisibility(0);
        viewHolder.editView.setVisibility(0);
        viewHolder.itemImgView.setVisibility(0);
        viewHolder.addView.setVisibility(4);
        if (mod.getCustomModId() != null) {
            viewHolder.modNameView.setText(mod.getCustomModName());
            viewHolder.itemImgView.setImageResource(R.drawable.mod11_select);
        } else {
            viewHolder.modNameView.setText(this.modNameArr[mod.getModNumber() - 1]);
            viewHolder.itemImgView.setImageResource(this.modImgArr[mod.getModNumber() - 1]);
        }
        viewHolder.itemImgView.setSelected(mod.isSelect);
        viewHolder.itemImgView.setOnClickListener(new DisDoubleClickListener() { // from class: com.brgd.brblmesh.GeneralAdapter.ModAdapter.2
            @Override // com.brgd.brblmesh.GeneralClass.DisDoubleClickListener
            protected void disDoubleClick(View view) {
                int bindingAdapterPosition = viewHolder.getBindingAdapterPosition();
                if (bindingAdapterPosition == -1 || ModAdapter.this.onModClickListener == null) {
                    return;
                }
                ModAdapter.this.onModClickListener.onModClick(view, bindingAdapterPosition, viewHolder.alphaView);
            }
        });
        viewHolder.nameClickView.setOnClickListener(new DisDoubleClickListener() { // from class: com.brgd.brblmesh.GeneralAdapter.ModAdapter.3
            @Override // com.brgd.brblmesh.GeneralClass.DisDoubleClickListener
            protected void disDoubleClick(View view) {
                int bindingAdapterPosition = viewHolder.getBindingAdapterPosition();
                if (bindingAdapterPosition == -1 || ModAdapter.this.onModClickListener == null) {
                    return;
                }
                ModAdapter.this.onModClickListener.onNameClick(view, bindingAdapterPosition);
            }
        });
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView addView;
        ImageView alphaView;
        ImageView editView;
        ImageView itemImgView;
        TextView modNameView;
        ImageView nameClickView;

        public ViewHolder(View view) {
            super(view);
            this.nameClickView = (ImageView) view.findViewById(R.id.mod_list_item_name_bg);
            this.modNameView = (TextView) view.findViewById(R.id.mod_list_item_name);
            this.editView = (ImageView) view.findViewById(R.id.mod_list_item_edit);
            this.itemImgView = (ImageView) view.findViewById(R.id.mod_list_item_img);
            this.alphaView = (ImageView) view.findViewById(R.id.mod_list_item_img_alpha);
            this.addView = (ImageView) view.findViewById(R.id.mod_list_item_add_img);
        }
    }

    public void setOnModClickListener(OnModClickListener onModClickListener) {
        this.onModClickListener = onModClickListener;
    }
}
