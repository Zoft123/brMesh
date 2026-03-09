package com.brgd.brblmesh.GeneralAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.brgd.brblmesh.GeneralAdapter.model.Timer;
import com.brgd.brblmesh.GeneralClass.DisDoubleClickListener;
import com.brgd.brblmesh.GeneralClass.GlobalVariable;
import com.brgd.brblmesh.GeneralClass.colorpicker.ColorSelector;
import com.brgd.brblmesh.R;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class TimerAdapter extends RecyclerView.Adapter<ViewHolder> {
    private final Context context;
    private final List<Timer> list;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void OnActionClick(View view, int i);

        void OnItemClick(View view, int i);

        void OnItemLongClick(View view, int i);
    }

    public TimerAdapter(Context context, List<Timer> list) {
        this.context = context;
        this.list = list;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(this.context).inflate(R.layout.timer_list_item, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {
        String str;
        String str2;
        Timer timer = this.list.get(i);
        int hour = timer.getHour();
        int minute = timer.getMinute();
        if (hour < 10) {
            str = GlobalVariable.ILLUMINATION + hour;
        } else {
            str = "" + hour;
        }
        String str3 = str + ":";
        if (minute < 10) {
            str2 = str3 + GlobalVariable.ILLUMINATION + minute;
        } else {
            str2 = str3 + minute;
        }
        viewHolder.timeText.setText(str2);
        if (timer.isRepeat()) {
            viewHolder.repeatText.setText(R.string.repeat);
        } else {
            viewHolder.repeatText.setText(R.string.once);
        }
        if (timer.getAction() == 1) {
            viewHolder.actionText.setText(R.string.turnOnLight);
        } else if (timer.getAction() == 0) {
            viewHolder.actionText.setText(R.string.turnOffLight);
        }
        if (timer.isEnable()) {
            viewHolder.timeText.setTextColor(ContextCompat.getColor(this.context, R.color.colorText));
            viewHolder.repeatText.setTextColor(ContextCompat.getColor(this.context, R.color.colorSelect));
            viewHolder.actionText.setTextColor(ContextCompat.getColor(this.context, R.color.colorSelect));
        } else {
            viewHolder.timeText.setTextColor(ContextCompat.getColor(this.context, R.color.colorTextOffline));
            viewHolder.repeatText.setTextColor(ContextCompat.getColor(this.context, R.color.colorTextOffline));
            viewHolder.actionText.setTextColor(ContextCompat.getColor(this.context, R.color.colorTextOffline));
        }
        viewHolder.enableView.setSelected(timer.isEnable());
        viewHolder.enableView.setOnClickListener(new DisDoubleClickListener() { // from class: com.brgd.brblmesh.GeneralAdapter.TimerAdapter.1
            @Override // com.brgd.brblmesh.GeneralClass.DisDoubleClickListener
            protected void disDoubleClick(View view) {
                int bindingAdapterPosition = viewHolder.getBindingAdapterPosition();
                if (bindingAdapterPosition == -1 || TimerAdapter.this.onItemClickListener == null) {
                    return;
                }
                TimerAdapter.this.onItemClickListener.OnActionClick(view, bindingAdapterPosition);
            }
        });
        viewHolder.brightnessText.setVisibility(4);
        viewHolder.colorSelector.setVisibility(4);
        viewHolder.inView.setOnClickListener(new DisDoubleClickListener() { // from class: com.brgd.brblmesh.GeneralAdapter.TimerAdapter.2
            @Override // com.brgd.brblmesh.GeneralClass.DisDoubleClickListener
            protected void disDoubleClick(View view) {
                int bindingAdapterPosition = viewHolder.getBindingAdapterPosition();
                if (bindingAdapterPosition == -1 || TimerAdapter.this.onItemClickListener == null) {
                    return;
                }
                TimerAdapter.this.onItemClickListener.OnItemClick(view, bindingAdapterPosition);
            }
        });
        viewHolder.inView.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.brgd.brblmesh.GeneralAdapter.TimerAdapter$$ExternalSyntheticLambda0
            @Override // android.view.View.OnLongClickListener
            public final boolean onLongClick(View view) {
                return this.f$0.lambda$onBindViewHolder$0(viewHolder, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$onBindViewHolder$0(ViewHolder viewHolder, View view) {
        OnItemClickListener onItemClickListener;
        int bindingAdapterPosition = viewHolder.getBindingAdapterPosition();
        if (bindingAdapterPosition == -1 || (onItemClickListener = this.onItemClickListener) == null) {
            return true;
        }
        onItemClickListener.OnItemLongClick(view, bindingAdapterPosition);
        return true;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView actionText;
        TextView brightnessText;
        ColorSelector colorSelector;
        ImageView enableView;
        ImageView inView;
        TextView repeatText;
        TextView timeText;

        ViewHolder(View view) {
            super(view);
            this.timeText = (TextView) view.findViewById(R.id.timer_list_item_time);
            this.repeatText = (TextView) view.findViewById(R.id.timer_list_item_repeat);
            this.actionText = (TextView) view.findViewById(R.id.timer_list_item_action);
            this.enableView = (ImageView) view.findViewById(R.id.timer_list_item_enable);
            this.inView = (ImageView) view.findViewById(R.id.timer_list_item_in);
            this.brightnessText = (TextView) view.findViewById(R.id.timer_list_item_brightness);
            this.colorSelector = (ColorSelector) view.findViewById(R.id.timer_list_item_selector);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
