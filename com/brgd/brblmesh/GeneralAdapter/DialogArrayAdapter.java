package com.brgd.brblmesh.GeneralAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.brgd.brblmesh.R;

/* JADX INFO: loaded from: classes.dex */
public class DialogArrayAdapter extends ArrayAdapter<String> {
    private final int mSource;

    public static class ViewHolder {
        TextView text;
    }

    public DialogArrayAdapter(Context context, int i, String[] strArr) {
        super(context, i, strArr);
        this.mSource = i;
    }

    @Override // android.widget.ArrayAdapter, android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        String item = getItem(i);
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(this.mSource, viewGroup, false);
            viewHolder = new ViewHolder();
            viewHolder.text = (TextView) view.findViewById(R.id.dialog_list_item_title);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.text.setText(item);
        return view;
    }
}
