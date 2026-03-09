package com.brgd.brblmesh.GeneralAdapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.brgd.brblmesh.GeneralAdapter.model.FixedGroup;
import com.brgd.brblmesh.GeneralClass.DisDoubleClickListener;
import com.brgd.brblmesh.GeneralClass.GlobalVariable;
import com.brgd.brblmesh.GeneralClass.Tool;
import com.brgd.brblmesh.R;
import java.io.File;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class BindAdapter extends RecyclerView.Adapter<ViewHolder> {
    private final Context context;
    private final int[] iconImgArr = {R.drawable.scene1, R.drawable.scene2, R.drawable.scene3, R.drawable.scene4, R.drawable.scene5, R.drawable.scene6, R.drawable.scene7, R.drawable.scene8, R.drawable.scene9, R.drawable.scene10, R.drawable.scene11, R.drawable.scene12, R.drawable.scene13, R.drawable.scene14, R.drawable.scene15, R.drawable.scene16, R.drawable.scene17, R.drawable.scene18, R.drawable.scene19, R.drawable.scene20, R.drawable.scene21, R.drawable.scene22, R.drawable.scene23, R.drawable.scene24, R.drawable.scene25, R.drawable.scene26, R.drawable.scene27, R.drawable.scene28, R.drawable.scene29, R.drawable.scene30};
    List<FixedGroup> list;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, int i);
    }

    public BindAdapter(Context context, List<FixedGroup> list) {
        this.list = list;
        this.context = context;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(this.context).inflate(R.layout.bind_list_item, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        FixedGroup fixedGroup = this.list.get(i);
        if (fixedGroup.getFixedId() != 0) {
            viewHolder.nameView.setVisibility(0);
            viewHolder.editView.setVisibility(0);
            viewHolder.itemImgView.setVisibility(0);
            viewHolder.addView.setVisibility(4);
            viewHolder.nameView.setText(fixedGroup.getFixedName());
            if (fixedGroup.getFileName() != null) {
                File file = new File(Tool.getAppFile(this.context), fixedGroup.getFileName() + GlobalVariable.sceneImgFileName);
                if (file.exists()) {
                    viewHolder.itemImgView.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
                }
            } else if (fixedGroup.getIconIndex() != 0) {
                viewHolder.itemImgView.setImageResource(this.iconImgArr[fixedGroup.getIconIndex() - 1]);
            }
        } else {
            viewHolder.nameView.setVisibility(4);
            viewHolder.editView.setVisibility(4);
            viewHolder.itemImgView.setVisibility(4);
            viewHolder.addView.setVisibility(0);
        }
        viewHolder.itemView.setTag(Integer.valueOf(i));
        viewHolder.itemView.setOnClickListener(new DisDoubleClickListener() { // from class: com.brgd.brblmesh.GeneralAdapter.BindAdapter.1
            @Override // com.brgd.brblmesh.GeneralClass.DisDoubleClickListener
            protected void disDoubleClick(View view) {
                if (BindAdapter.this.onItemClickListener != null) {
                    BindAdapter.this.onItemClickListener.onItemClick(view, ((Integer) view.getTag()).intValue());
                }
            }
        });
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView addView;
        ImageView editView;
        ImageView itemImgView;
        TextView nameView;

        public ViewHolder(View view) {
            super(view);
            this.nameView = (TextView) view.findViewById(R.id.scene_list_item_name);
            this.editView = (ImageView) view.findViewById(R.id.scene_list_item_edit);
            this.itemImgView = (ImageView) view.findViewById(R.id.scene_list_item_img);
            this.addView = (ImageView) view.findViewById(R.id.bind_add_img);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
