package com.brgd.brblmesh.GeneralAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.brgd.brblmesh.GeneralAdapter.model.Song;
import com.brgd.brblmesh.GeneralClass.DisDoubleClickListener;
import com.brgd.brblmesh.GeneralClass.Tool;
import com.brgd.brblmesh.R;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class MusicAdapter extends RecyclerView.Adapter<ViewHolder> {
    private final Context context;
    List<Song> list;
    private OnClickListener onClickListener;

    public interface OnClickListener {
        void onClick(View view, int i);
    }

    public MusicAdapter(Context context, List<Song> list) {
        this.list = list;
        this.context = context;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(this.context).inflate(R.layout.music_list_item, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Song song = this.list.get(i);
        viewHolder.songNameView.setText(song.name);
        viewHolder.singerView.setText(song.singer);
        viewHolder.durationView.setText(Tool.formatTime(song.duration));
        if (song.isSelect) {
            viewHolder.bgView.setSelected(true);
            viewHolder.songNameView.setTextColor(this.context.getColor(R.color.colorWhite));
            viewHolder.singerView.setTextColor(this.context.getColor(R.color.colorWhite));
            viewHolder.durationView.setTextColor(this.context.getColor(R.color.colorWhite));
        } else {
            viewHolder.bgView.setSelected(false);
            viewHolder.songNameView.setTextColor(this.context.getColor(R.color.colorSelect));
            viewHolder.singerView.setTextColor(this.context.getColor(R.color.colorSelect));
            viewHolder.durationView.setTextColor(this.context.getColor(R.color.colorSelect));
        }
        viewHolder.itemView.setTag(Integer.valueOf(i));
        viewHolder.itemView.setOnClickListener(new DisDoubleClickListener() { // from class: com.brgd.brblmesh.GeneralAdapter.MusicAdapter.1
            @Override // com.brgd.brblmesh.GeneralClass.DisDoubleClickListener
            protected void disDoubleClick(View view) {
                if (MusicAdapter.this.onClickListener != null) {
                    MusicAdapter.this.onClickListener.onClick(view, ((Integer) view.getTag()).intValue());
                }
            }
        });
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView bgView;
        TextView durationView;
        TextView singerView;
        TextView songNameView;

        public ViewHolder(View view) {
            super(view);
            this.bgView = (ImageView) view.findViewById(R.id.music_list_item_bg);
            this.songNameView = (TextView) view.findViewById(R.id.music_list_songName);
            this.singerView = (TextView) view.findViewById(R.id.music_list_singerName);
            this.durationView = (TextView) view.findViewById(R.id.music_list_duration);
        }
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
}
