package com.brgd.brblmesh.GeneralAdapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.brgd.brblmesh.GeneralAdapter.model.Scene;
import com.brgd.brblmesh.GeneralClass.DisDoubleClickListener;
import com.brgd.brblmesh.GeneralClass.GlobalVariable;
import com.brgd.brblmesh.GeneralClass.Tool;
import com.brgd.brblmesh.R;
import java.io.File;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class SceneAdapter extends RecyclerView.Adapter<ViewHolder> {
    private final Context context;
    List<Scene> list;
    private OnSceneClickListener onSceneClickListener;
    private final int[] sceneImgArr = {R.drawable.scene1, R.drawable.scene2, R.drawable.scene3, R.drawable.scene4, R.drawable.scene5, R.drawable.scene6, R.drawable.scene7, R.drawable.scene8, R.drawable.scene9};
    private final int[] iconImgArr = {R.drawable.scene1, R.drawable.scene2, R.drawable.scene3, R.drawable.scene4, R.drawable.scene5, R.drawable.scene6, R.drawable.scene7, R.drawable.scene8, R.drawable.scene9, R.drawable.scene10, R.drawable.scene11, R.drawable.scene12, R.drawable.scene13, R.drawable.scene14, R.drawable.scene15, R.drawable.scene16, R.drawable.scene17, R.drawable.scene18, R.drawable.scene19, R.drawable.scene20, R.drawable.scene21, R.drawable.scene22, R.drawable.scene23, R.drawable.scene24, R.drawable.scene25, R.drawable.scene26, R.drawable.scene27, R.drawable.scene28, R.drawable.scene29, R.drawable.scene30};

    public interface OnSceneClickListener {
        void onNameClick(View view, int i);

        void onSceneClick(View view, int i, View view2);
    }

    public SceneAdapter(Context context, List<Scene> list) {
        this.list = list;
        this.context = context;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(this.context).inflate(R.layout.scene_list_item, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {
        Scene scene = this.list.get(i);
        viewHolder.sceneNameView.setText(scene.getSceneName());
        if (scene.getIconIndex() != 0) {
            viewHolder.itemImgView.setImageResource(this.iconImgArr[scene.getIconIndex() - 1]);
        } else if (scene.getSceneFileName() != null) {
            File file = new File(Tool.getAppFile(this.context), scene.getSceneFileName() + GlobalVariable.sceneImgFileName);
            if (file.exists()) {
                viewHolder.itemImgView.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
            } else {
                viewHolder.itemImgView.setImageResource(this.sceneImgArr[scene.getIndex()]);
            }
        } else {
            viewHolder.itemImgView.setImageResource(this.sceneImgArr[scene.getIndex()]);
        }
        viewHolder.itemImgView.setOnClickListener(new DisDoubleClickListener() { // from class: com.brgd.brblmesh.GeneralAdapter.SceneAdapter.1
            @Override // com.brgd.brblmesh.GeneralClass.DisDoubleClickListener
            protected void disDoubleClick(View view) {
                int bindingAdapterPosition = viewHolder.getBindingAdapterPosition();
                if (bindingAdapterPosition == -1 || SceneAdapter.this.onSceneClickListener == null) {
                    return;
                }
                SceneAdapter.this.onSceneClickListener.onSceneClick(view, bindingAdapterPosition, viewHolder.alphaView);
            }
        });
        viewHolder.nameClickView.setOnClickListener(new DisDoubleClickListener() { // from class: com.brgd.brblmesh.GeneralAdapter.SceneAdapter.2
            @Override // com.brgd.brblmesh.GeneralClass.DisDoubleClickListener
            protected void disDoubleClick(View view) {
                int bindingAdapterPosition = viewHolder.getBindingAdapterPosition();
                if (bindingAdapterPosition == -1 || SceneAdapter.this.onSceneClickListener == null) {
                    return;
                }
                SceneAdapter.this.onSceneClickListener.onNameClick(view, bindingAdapterPosition);
            }
        });
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView alphaView;
        ImageView itemImgView;
        ImageView nameClickView;
        TextView sceneNameView;

        public ViewHolder(View view) {
            super(view);
            this.sceneNameView = (TextView) view.findViewById(R.id.scene_list_item_name);
            this.nameClickView = (ImageView) view.findViewById(R.id.scene_list_item_name_bg);
            this.itemImgView = (ImageView) view.findViewById(R.id.scene_list_item_img);
            this.alphaView = (ImageView) view.findViewById(R.id.scene_list_item_img_alpha);
        }
    }

    public void setOnSceneClickListener(OnSceneClickListener onSceneClickListener) {
        this.onSceneClickListener = onSceneClickListener;
    }
}
