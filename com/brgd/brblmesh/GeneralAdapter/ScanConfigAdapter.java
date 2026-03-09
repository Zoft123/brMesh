package com.brgd.brblmesh.GeneralAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.brgd.brblmesh.GeneralAdapter.model.MyDevWrapperBean;
import com.brgd.brblmesh.R;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class ScanConfigAdapter extends RecyclerView.Adapter<ViewHolder> {
    private final Context context;
    List<MyDevWrapperBean> list;

    public ScanConfigAdapter(Context context, List<MyDevWrapperBean> list) {
        this.list = list;
        this.context = context;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(this.context).inflate(R.layout.search_list_item, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        MyDevWrapperBean myDevWrapperBean = this.list.get(i);
        viewHolder.deviceNameTextView.setText(myDevWrapperBean.deviceInfo.name);
        viewHolder.itemBgView.setSelected(true);
        viewHolder.stateTextView.setText(myDevWrapperBean.state == 2 ? R.string.AddSuccess : R.string.configuringNotAgain);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView deviceNameTextView;
        ImageView itemBgView;
        TextView stateTextView;

        public ViewHolder(View view) {
            super(view);
            this.itemBgView = (ImageView) view.findViewById(R.id.search_list_item_bg);
            this.deviceNameTextView = (TextView) view.findViewById(R.id.searchList_deviceName);
            this.stateTextView = (TextView) view.findViewById(R.id.searchList_state);
        }
    }
}
