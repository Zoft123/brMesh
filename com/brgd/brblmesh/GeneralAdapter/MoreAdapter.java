package com.brgd.brblmesh.GeneralAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.brgd.brblmesh.GeneralAdapter.model.MoreModel;
import com.brgd.brblmesh.GeneralClass.DisDoubleClickListener;
import com.brgd.brblmesh.R;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class MoreAdapter extends RecyclerView.Adapter<ViewHolder> {
    private final Context context;
    List<MoreModel> list;
    private OnMoreClickListener onMoreClickListener;

    public interface OnMoreClickListener {
        void onMoreClick(View view, int i);
    }

    public MoreAdapter(Context context, List<MoreModel> list) {
        this.list = list;
        this.context = context;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(this.context).inflate(R.layout.more_list_item, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        MoreModel moreModel = this.list.get(i);
        viewHolder.leftImgView.setImageResource(moreModel.imgResourceId);
        viewHolder.titleNameView.setText(moreModel.titleNameId);
        if (moreModel.isSelect) {
            viewHolder.tipView.setVisibility(0);
            viewHolder.tipImgView.setVisibility(0);
        } else {
            viewHolder.tipView.setVisibility(4);
            viewHolder.tipImgView.setVisibility(4);
        }
        viewHolder.itemView.setTag(Integer.valueOf(i));
        viewHolder.itemView.setOnClickListener(new DisDoubleClickListener() { // from class: com.brgd.brblmesh.GeneralAdapter.MoreAdapter.1
            @Override // com.brgd.brblmesh.GeneralClass.DisDoubleClickListener
            protected void disDoubleClick(View view) {
                if (MoreAdapter.this.onMoreClickListener != null) {
                    MoreAdapter.this.onMoreClickListener.onMoreClick(view, ((Integer) view.getTag()).intValue());
                }
            }
        });
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView leftImgView;
        ImageView tipImgView;
        TextView tipView;
        TextView titleNameView;

        public ViewHolder(View view) {
            super(view);
            this.leftImgView = (ImageView) view.findViewById(R.id.more_list_item_img);
            this.titleNameView = (TextView) view.findViewById(R.id.more_list_item_title);
            this.tipView = (TextView) view.findViewById(R.id.more_list_item_tip);
            this.tipImgView = (ImageView) view.findViewById(R.id.more_list_item_tipImg);
        }
    }

    public void setOnMoreClickListener(OnMoreClickListener onMoreClickListener) {
        this.onMoreClickListener = onMoreClickListener;
    }
}
