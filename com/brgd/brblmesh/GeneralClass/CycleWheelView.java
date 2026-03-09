package com.brgd.brblmesh.GeneralClass;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.brgd.brblmesh.GeneralClass.colorpicker.CustomColor;
import com.brgd.brblmesh.R;
import java.util.ArrayList;
import java.util.List;
import kotlinx.coroutines.internal.LockFreeTaskQueueCore;

/* JADX INFO: loaded from: classes.dex */
public class CycleWheelView extends ListView {
    private static final int COLOR_DIVIDER_DEFAULT = CustomColor.SELECT;
    private static final int COLOR_SOLID_DEFAULT = Color.alpha(0);
    private static final int COLOR_SOLID_SELECT_DEFAULT = Color.alpha(0);
    private static final int HEIGHT_DIVIDER_DEFAULT = 2;
    private static final int WHEEL_SIZE_DEFAULT = 3;
    private boolean cycleEnable;
    private int dividerColor;
    private int dividerHeight;
    private CycleWheelViewAdapter mAdapter;
    private float mAlphaGradual;
    private int mCurrentPosition;
    private Handler mHandler;
    private int mItemHeight;
    private int mItemLabelTvId;
    private int mItemLayoutId;
    private WheelItemSelectedListener mItemSelectedListener;
    private int mLabelColor;
    private int mLabelSelectColor;
    private List<String> mLabels;
    private int mWheelSize;
    private int selectedSolidColor;
    private int solidColor;

    public interface WheelItemSelectedListener {
        void onItemSelected(int i, String str);
    }

    public CycleWheelView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mLabelSelectColor = CustomColor.SELECT;
        this.mLabelColor = CustomColor.SELECT;
        this.mAlphaGradual = 0.6f;
        this.dividerColor = COLOR_DIVIDER_DEFAULT;
        this.dividerHeight = 2;
        this.selectedSolidColor = COLOR_SOLID_SELECT_DEFAULT;
        this.solidColor = COLOR_SOLID_DEFAULT;
        this.mWheelSize = 3;
    }

    public CycleWheelView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mLabelSelectColor = CustomColor.SELECT;
        this.mLabelColor = CustomColor.SELECT;
        this.mAlphaGradual = 0.6f;
        this.dividerColor = COLOR_DIVIDER_DEFAULT;
        this.dividerHeight = 2;
        this.selectedSolidColor = COLOR_SOLID_SELECT_DEFAULT;
        this.solidColor = COLOR_SOLID_DEFAULT;
        this.mWheelSize = 3;
        init();
    }

    public CycleWheelView(Context context) {
        super(context);
        this.mLabelSelectColor = CustomColor.SELECT;
        this.mLabelColor = CustomColor.SELECT;
        this.mAlphaGradual = 0.6f;
        this.dividerColor = COLOR_DIVIDER_DEFAULT;
        this.dividerHeight = 2;
        this.selectedSolidColor = COLOR_SOLID_SELECT_DEFAULT;
        this.solidColor = COLOR_SOLID_DEFAULT;
        this.mWheelSize = 3;
    }

    private void init() {
        this.mHandler = new Handler(getContext().getMainLooper());
        this.mItemLayoutId = R.layout.item_cyclewheel;
        this.mItemLabelTvId = R.id.tv_label_item_wheel;
        this.mAdapter = new CycleWheelViewAdapter();
        setVerticalScrollBarEnabled(false);
        setScrollingCacheEnabled(false);
        setCacheColorHint(0);
        setFadingEdgeLength(0);
        setOverScrollMode(2);
        setCycleEnable(false);
        setDividerHeight(0);
        setAdapter((ListAdapter) this.mAdapter);
        setOnScrollListener(new AbsListView.OnScrollListener() { // from class: com.brgd.brblmesh.GeneralClass.CycleWheelView.1
            @Override // android.widget.AbsListView.OnScrollListener
            public void onScrollStateChanged(AbsListView absListView, int i) {
                View childAt;
                if (i != 0 || (childAt = CycleWheelView.this.getChildAt(0)) == null) {
                    return;
                }
                float y = childAt.getY();
                if (y == 0.0f) {
                    return;
                }
                if (Math.abs(y) < CycleWheelView.this.mItemHeight / 2.0f) {
                    CycleWheelView cycleWheelView = CycleWheelView.this;
                    cycleWheelView.smoothScrollBy(cycleWheelView.getDistance(y), 50);
                } else {
                    CycleWheelView cycleWheelView2 = CycleWheelView.this;
                    cycleWheelView2.smoothScrollBy(cycleWheelView2.getDistance(cycleWheelView2.mItemHeight + y), 50);
                }
            }

            @Override // android.widget.AbsListView.OnScrollListener
            public void onScroll(AbsListView absListView, int i, int i2, int i3) {
                CycleWheelView.this.refreshItems();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getDistance(float f) {
        return Math.abs(f) <= 2.0f ? (int) f : Math.abs(f) < 12.0f ? f > 0.0f ? 2 : -2 : (int) (f / 6.0f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshItems() {
        int i = this.mWheelSize / 2;
        int firstVisiblePosition = getFirstVisiblePosition();
        if (getChildAt(0) == null) {
            return;
        }
        int i2 = Math.abs(getChildAt(0).getY()) <= ((float) this.mItemHeight) / 2.0f ? firstVisiblePosition + i : firstVisiblePosition + i + 1;
        if (i2 == this.mCurrentPosition) {
            return;
        }
        this.mCurrentPosition = i2;
        WheelItemSelectedListener wheelItemSelectedListener = this.mItemSelectedListener;
        if (wheelItemSelectedListener != null) {
            wheelItemSelectedListener.onItemSelected(getSelection(), getSelectLabel());
        }
        resetItems(firstVisiblePosition, i2, i);
    }

    private void resetItems(int i, int i2, int i3) {
        for (int i4 = (i2 - i3) - 1; i4 < i2 + i3 + 1; i4++) {
            View childAt = getChildAt(i4 - i);
            if (childAt != null) {
                TextView textView = (TextView) childAt.findViewById(this.mItemLabelTvId);
                if (i2 == i4) {
                    textView.setTextColor(this.mLabelSelectColor);
                    childAt.setAlpha(1.0f);
                } else {
                    textView.setTextColor(this.mLabelColor);
                    childAt.setAlpha((float) Math.pow(this.mAlphaGradual, Math.abs(i4 - i2)));
                }
            }
        }
    }

    public void setLabels(List<String> list) {
        this.mLabels = list;
        this.mAdapter.setData(list);
        this.mAdapter.notifyDataSetChanged();
        initView();
    }

    public void setOnWheelItemSelectedListener(WheelItemSelectedListener wheelItemSelectedListener) {
        this.mItemSelectedListener = wheelItemSelectedListener;
    }

    public List<String> getLabels() {
        return this.mLabels;
    }

    public void setCycleEnable(boolean z) {
        if (this.cycleEnable != z) {
            this.cycleEnable = z;
            this.mAdapter.notifyDataSetChanged();
            setSelection(getSelection());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setSelection$0(int i) {
        super.setSelection(getPosition(i));
    }

    @Override // android.widget.ListView, android.widget.AdapterView
    public void setSelection(final int i) {
        this.mHandler.post(new Runnable() { // from class: com.brgd.brblmesh.GeneralClass.CycleWheelView$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$setSelection$0(i);
            }
        });
    }

    private int getPosition(int i) {
        List<String> list = this.mLabels;
        if (list == null || list.size() == 0) {
            return 0;
        }
        return this.cycleEnable ? i + ((LockFreeTaskQueueCore.MAX_CAPACITY_MASK / this.mLabels.size()) * this.mLabels.size()) : i;
    }

    public int getSelection() {
        if (this.mCurrentPosition == 0) {
            this.mCurrentPosition = this.mWheelSize / 2;
        }
        return (this.mCurrentPosition - (this.mWheelSize / 2)) % this.mLabels.size();
    }

    public String getSelectLabel() {
        try {
            return this.mLabels.get(Math.max(getSelection(), 0));
        } catch (Exception unused) {
            return "";
        }
    }

    public void setWheelItemLayout(int i, int i2) {
        this.mItemLayoutId = i;
        this.mItemLabelTvId = i2;
        CycleWheelViewAdapter cycleWheelViewAdapter = new CycleWheelViewAdapter();
        this.mAdapter = cycleWheelViewAdapter;
        cycleWheelViewAdapter.setData(this.mLabels);
        setAdapter((ListAdapter) this.mAdapter);
        initView();
    }

    public void setLabelColor(int i) {
        this.mLabelColor = i;
        resetItems(getFirstVisiblePosition(), this.mCurrentPosition, this.mWheelSize / 2);
    }

    public void setLabelSelectColor(int i) {
        this.mLabelSelectColor = i;
        resetItems(getFirstVisiblePosition(), this.mCurrentPosition, this.mWheelSize / 2);
    }

    public void setAlphaGradual(float f) {
        this.mAlphaGradual = f;
        resetItems(getFirstVisiblePosition(), this.mCurrentPosition, this.mWheelSize / 2);
    }

    public void setWheelSize(int i) throws CycleWheelViewException {
        if (i < 3 || i % 2 != 1) {
            throw new CycleWheelViewException("Wheel Size Error , Must Be 3,5,7,9...");
        }
        this.mWheelSize = i;
        initView();
    }

    public void setSolid(int i, int i2) {
        this.solidColor = i;
        this.selectedSolidColor = i2;
        initView();
    }

    public void setDivider(int i, int i2) {
        this.dividerColor = i;
        this.dividerHeight = i2;
    }

    private void initView() {
        this.mItemHeight = measureHeight();
        getLayoutParams().height = this.mItemHeight * this.mWheelSize;
        this.mAdapter.setData(this.mLabels);
        this.mAdapter.notifyDataSetChanged();
        setBackgroundDrawable(new Drawable() { // from class: com.brgd.brblmesh.GeneralClass.CycleWheelView.2
            @Override // android.graphics.drawable.Drawable
            public int getOpacity() {
                return 0;
            }

            @Override // android.graphics.drawable.Drawable
            public void setAlpha(int i) {
            }

            @Override // android.graphics.drawable.Drawable
            public void setColorFilter(ColorFilter colorFilter) {
            }

            @Override // android.graphics.drawable.Drawable
            public void draw(Canvas canvas) {
                int width = CycleWheelView.this.getWidth();
                Paint paint = new Paint();
                paint.setColor(CycleWheelView.this.dividerColor);
                paint.setStrokeWidth(CycleWheelView.this.dividerHeight);
                Paint paint2 = new Paint();
                paint2.setColor(CycleWheelView.this.selectedSolidColor);
                Paint paint3 = new Paint();
                paint3.setColor(CycleWheelView.this.solidColor);
                float f = width;
                canvas.drawRect(0.0f, 0.0f, f, CycleWheelView.this.mItemHeight * (CycleWheelView.this.mWheelSize / 2), paint3);
                canvas.drawRect(0.0f, CycleWheelView.this.mItemHeight * ((CycleWheelView.this.mWheelSize / 2) + 1), f, CycleWheelView.this.mItemHeight * CycleWheelView.this.mWheelSize, paint3);
                canvas.drawRect(0.0f, CycleWheelView.this.mItemHeight * (CycleWheelView.this.mWheelSize / 2), f, CycleWheelView.this.mItemHeight * ((CycleWheelView.this.mWheelSize / 2) + 1), paint2);
                canvas.drawLine(0.0f, CycleWheelView.this.mItemHeight * (CycleWheelView.this.mWheelSize / 2), f, CycleWheelView.this.mItemHeight * (CycleWheelView.this.mWheelSize / 2), paint);
                canvas.drawLine(0.0f, CycleWheelView.this.mItemHeight * ((CycleWheelView.this.mWheelSize / 2) + 1), f, CycleWheelView.this.mItemHeight * ((CycleWheelView.this.mWheelSize / 2) + 1), paint);
            }
        });
    }

    private int measureHeight() {
        View viewInflate = LayoutInflater.from(getContext()).inflate(this.mItemLayoutId, (ViewGroup) null);
        viewInflate.setLayoutParams(new ViewGroup.LayoutParams(-1, -2));
        viewInflate.measure(View.MeasureSpec.makeMeasureSpec(0, 0), View.MeasureSpec.makeMeasureSpec(0, 0));
        return viewInflate.getMeasuredHeight();
    }

    public static class CycleWheelViewException extends Exception {
        private static final long serialVersionUID = 1;

        public CycleWheelViewException(String str) {
            super(str);
        }
    }

    public class CycleWheelViewAdapter extends BaseAdapter {
        private final List<String> mData = new ArrayList();

        @Override // android.widget.Adapter
        public long getItemId(int i) {
            return i;
        }

        @Override // android.widget.BaseAdapter, android.widget.ListAdapter
        public boolean isEnabled(int i) {
            return false;
        }

        public CycleWheelViewAdapter() {
        }

        public void setData(List<String> list) {
            this.mData.clear();
            this.mData.addAll(list);
        }

        @Override // android.widget.Adapter
        public int getCount() {
            if (CycleWheelView.this.cycleEnable) {
                return Integer.MAX_VALUE;
            }
            return (this.mData.size() + CycleWheelView.this.mWheelSize) - 1;
        }

        @Override // android.widget.Adapter
        public Object getItem(int i) {
            return "";
        }

        @Override // android.widget.Adapter
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = LayoutInflater.from(CycleWheelView.this.getContext()).inflate(CycleWheelView.this.mItemLayoutId, (ViewGroup) null);
            }
            TextView textView = (TextView) view.findViewById(CycleWheelView.this.mItemLabelTvId);
            if (i < CycleWheelView.this.mWheelSize / 2 || (!CycleWheelView.this.cycleEnable && i >= this.mData.size() + (CycleWheelView.this.mWheelSize / 2))) {
                textView.setText("");
                view.setVisibility(4);
                return view;
            }
            textView.setText(this.mData.get((i - (CycleWheelView.this.mWheelSize / 2)) % this.mData.size()));
            view.setVisibility(0);
            return view;
        }
    }
}
