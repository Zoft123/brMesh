package com.brgd.brblmesh.GeneralAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.recyclerview.widget.RecyclerView;
import com.brgd.brblmesh.GeneralAdapter.model.MoreModel;
import com.brgd.brblmesh.GeneralClass.DisDoubleClickListener;
import com.brgd.brblmesh.R;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class IconAdapter extends RecyclerView.Adapter<ViewHolder> {
    private final Context context;
    List<MoreModel> list;
    private OnIconClickListener onIconClickListener;
    private final int[] sceneImgArr = {R.drawable.scene1, R.drawable.scene2, R.drawable.scene3, R.drawable.scene4, R.drawable.scene5, R.drawable.scene6, R.drawable.scene7, R.drawable.scene8, R.drawable.scene9, R.drawable.scene10, R.drawable.scene11, R.drawable.scene12, R.drawable.scene13, R.drawable.scene14, R.drawable.scene15, R.drawable.scene16, R.drawable.scene17, R.drawable.scene18, R.drawable.scene19, R.drawable.scene20, R.drawable.scene21, R.drawable.scene22, R.drawable.scene23, R.drawable.scene24, R.drawable.scene25, R.drawable.scene26, R.drawable.scene27, R.drawable.scene28, R.drawable.scene29, R.drawable.scene30};

    public interface OnIconClickListener {
        void onIconClick(View view, int i);
    }

    public IconAdapter(Context context, List<MoreModel> list) {
        this.list = list;
        this.context = context;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(this.context).inflate(R.layout.scene_icon_item, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        MoreModel moreModel = this.list.get(i);
        viewHolder.itemImgView.setImageResource(this.sceneImgArr[moreModel.titleNameId - 1]);
        if (moreModel.isSelect) {
            viewHolder.itemView.setBackgroundColor(this.context.getColor(R.color.colorTextOffline));
        } else {
            viewHolder.itemView.setBackgroundColor(this.context.getColor(R.color.colorWhite));
        }
        viewHolder.itemImgView.setTag(Integer.valueOf(i));
        viewHolder.itemImgView.setOnClickListener(new DisDoubleClickListener() { // from class: com.brgd.brblmesh.GeneralAdapter.IconAdapter.1
            @Override // com.brgd.brblmesh.GeneralClass.DisDoubleClickListener
            protected void disDoubleClick(View view) {
                if (IconAdapter.this.onIconClickListener != null) {
                    IconAdapter.this.onIconClickListener.onIconClick(view, ((Integer) view.getTag()).intValue());
                }
            }
        });
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView itemImgView;

        public ViewHolder(View view) {
            super(view);
            this.itemImgView = (ImageView) view.findViewById(R.id.scene_icon_item_img);
        }
    }

    public void setOnIconClickListener(OnIconClickListener onIconClickListener) {
        this.onIconClickListener = onIconClickListener;
    }
}
