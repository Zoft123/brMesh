package com.brgd.brblmesh.GeneralAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.brgd.brblmesh.GeneralAdapter.model.MusicTheme;
import com.brgd.brblmesh.GeneralClass.DisDoubleClickListener;
import com.brgd.brblmesh.R;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class MusicThemeAdapter extends RecyclerView.Adapter<ViewHolder> {
    private final Context context;
    List<MusicTheme> list;
    private OnItemClickListener onItemClickListener;
    int[] themeArr = {R.string.MyColorTemp, R.string.Hallowmas1, R.string.Hallowmas2, R.string.Hallowmas3, R.string.Hallowmas4, R.string.Hallowmas5};

    public interface OnItemClickListener {
        void editClick(View view, int i);

        void onClick(View view, int i);
    }

    public MusicThemeAdapter(Context context, List<MusicTheme> list) {
        this.list = list;
        this.context = context;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(this.context).inflate(R.layout.music_theme_list_item, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {
        MusicTheme musicTheme = this.list.get(i);
        viewHolder.nameView.setText(this.themeArr[musicTheme.index]);
        viewHolder.optionView.setSelected(musicTheme.isSelect);
        if (i > 0 && musicTheme.isSelect) {
            viewHolder.editView.setVisibility(0);
        } else {
            viewHolder.editView.setVisibility(4);
        }
        viewHolder.editView.setOnClickListener(new DisDoubleClickListener() { // from class: com.brgd.brblmesh.GeneralAdapter.MusicThemeAdapter.1
            @Override // com.brgd.brblmesh.GeneralClass.DisDoubleClickListener
            protected void disDoubleClick(View view) {
                int bindingAdapterPosition = viewHolder.getBindingAdapterPosition();
                if (bindingAdapterPosition == -1 || MusicThemeAdapter.this.onItemClickListener == null) {
                    return;
                }
                MusicThemeAdapter.this.onItemClickListener.editClick(view, bindingAdapterPosition);
            }
        });
        viewHolder.selectView.setOnClickListener(new DisDoubleClickListener() { // from class: com.brgd.brblmesh.GeneralAdapter.MusicThemeAdapter.2
            @Override // com.brgd.brblmesh.GeneralClass.DisDoubleClickListener
            protected void disDoubleClick(View view) {
                int bindingAdapterPosition = viewHolder.getBindingAdapterPosition();
                if (bindingAdapterPosition == -1 || MusicThemeAdapter.this.onItemClickListener == null) {
                    return;
                }
                MusicThemeAdapter.this.onItemClickListener.onClick(view, bindingAdapterPosition);
            }
        });
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView editView;
        TextView nameView;
        ImageView optionView;
        ImageView selectView;

        public ViewHolder(View view) {
            super(view);
            this.optionView = (ImageView) view.findViewById(R.id.music_theme_list_item_option);
            this.nameView = (TextView) view.findViewById(R.id.music_theme_list_item_title);
            this.editView = (TextView) view.findViewById(R.id.music_theme_list_edit);
            this.selectView = (ImageView) view.findViewById(R.id.music_theme_list_select);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
