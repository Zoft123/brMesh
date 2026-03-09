package com.brgd.brblmesh.GeneralClass;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.brgd.brblmesh.GeneralClass.colorpicker.CustomColor;

/* JADX INFO: loaded from: classes.dex */
public class MyItemTouchHelper extends ItemTouchHelper.Callback {
    private final OnItemTouchListener mListener;
    private boolean sort = false;

    public interface OnItemTouchListener {
        boolean onMove(int i, int i2);

        void onSwiped(int i);
    }

    @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
    public boolean isItemViewSwipeEnabled() {
        return false;
    }

    public MyItemTouchHelper(OnItemTouchListener onItemTouchListener) {
        this.mListener = onItemTouchListener;
    }

    @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        return makeMovementFlags(3, 32);
    }

    @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder2) {
        return this.mListener.onMove(viewHolder.getBindingAdapterPosition(), viewHolder2.getBindingAdapterPosition());
    }

    @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int i) {
        this.mListener.onSwiped(viewHolder.getBindingAdapterPosition());
    }

    public void setSort(boolean z) {
        this.sort = z;
    }

    @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
    public boolean isLongPressDragEnabled() {
        return this.sort;
    }

    @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int i) {
        super.onSelectedChanged(viewHolder, i);
        if (i == 2) {
            viewHolder.itemView.setBackgroundColor(CustomColor.SELECT);
        }
    }

    @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        viewHolder.itemView.setBackgroundColor(0);
    }
}
