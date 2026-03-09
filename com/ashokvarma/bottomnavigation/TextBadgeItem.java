package com.ashokvarma.bottomnavigation;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import androidx.core.content.ContextCompat;

/* JADX INFO: loaded from: classes.dex */
public class TextBadgeItem extends BadgeItem<TextBadgeItem> {
    private String mBackgroundColorCode;
    private int mBackgroundColorResource;
    private String mBorderColorCode;
    private int mBorderColorResource;
    private CharSequence mText;
    private String mTextColorCode;
    private int mTextColorResource;
    private int mBackgroundColor = -65536;
    private int mTextColor = -1;
    private int mBorderColor = -1;
    private int mBorderWidthInPixels = 0;
    private int radius = -1;

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.ashokvarma.bottomnavigation.BadgeItem
    public TextBadgeItem getSubInstance() {
        return this;
    }

    @Override // com.ashokvarma.bottomnavigation.BadgeItem
    public /* bridge */ /* synthetic */ boolean isHidden() {
        return super.isHidden();
    }

    public TextBadgeItem setBackgroundColorResource(int i) {
        this.mBackgroundColorResource = i;
        refreshDrawable();
        return this;
    }

    public TextBadgeItem setBackgroundColor(String str) {
        this.mBackgroundColorCode = str;
        refreshDrawable();
        return this;
    }

    public TextBadgeItem setBackgroundColor(int i) {
        this.mBackgroundColor = i;
        refreshDrawable();
        return this;
    }

    public TextBadgeItem setTextColorResource(int i) {
        this.mTextColorResource = i;
        setTextColor();
        return this;
    }

    public TextBadgeItem setTextColor(String str) {
        this.mTextColorCode = str;
        setTextColor();
        return this;
    }

    public TextBadgeItem setTextColor(int i) {
        this.mTextColor = i;
        setTextColor();
        return this;
    }

    public TextBadgeItem setText(CharSequence charSequence) {
        this.mText = charSequence;
        if (isWeakReferenceValid()) {
            BadgeTextView badgeTextView = getTextView().get();
            if (!TextUtils.isEmpty(charSequence)) {
                badgeTextView.setText(charSequence);
            }
        }
        return this;
    }

    public TextBadgeItem setBorderColorResource(int i) {
        this.mBorderColorResource = i;
        refreshDrawable();
        return this;
    }

    public TextBadgeItem setCornerRadius(int i) {
        this.radius = i;
        refreshDrawable();
        return this;
    }

    public TextBadgeItem setBorderColor(String str) {
        this.mBorderColorCode = str;
        refreshDrawable();
        return this;
    }

    public TextBadgeItem setBorderColor(int i) {
        this.mBorderColor = i;
        refreshDrawable();
        return this;
    }

    public TextBadgeItem setBorderWidth(int i) {
        this.mBorderWidthInPixels = i;
        refreshDrawable();
        return this;
    }

    @Override // com.ashokvarma.bottomnavigation.BadgeItem
    void bindToBottomTabInternal(BottomNavigationTab bottomNavigationTab) {
        Context context = bottomNavigationTab.getContext();
        bottomNavigationTab.badgeView.setBackgroundDrawable(getBadgeDrawable(context));
        bottomNavigationTab.badgeView.setTextColor(getTextColor(context));
        bottomNavigationTab.badgeView.setText(getText());
    }

    private int getBackgroundColor(Context context) {
        int i = this.mBackgroundColorResource;
        if (i != 0) {
            return ContextCompat.getColor(context, i);
        }
        if (!TextUtils.isEmpty(this.mBackgroundColorCode)) {
            return Color.parseColor(this.mBackgroundColorCode);
        }
        return this.mBackgroundColor;
    }

    private int getTextColor(Context context) {
        int i = this.mTextColorResource;
        if (i != 0) {
            return ContextCompat.getColor(context, i);
        }
        if (!TextUtils.isEmpty(this.mTextColorCode)) {
            return Color.parseColor(this.mTextColorCode);
        }
        return this.mTextColor;
    }

    private CharSequence getText() {
        return this.mText;
    }

    private int getBorderColor(Context context) {
        int i = this.mBorderColorResource;
        if (i != 0) {
            return ContextCompat.getColor(context, i);
        }
        if (!TextUtils.isEmpty(this.mBorderColorCode)) {
            return Color.parseColor(this.mBorderColorCode);
        }
        return this.mBorderColor;
    }

    private int getBorderWidth() {
        return this.mBorderWidthInPixels;
    }

    private int getRadius(Context context) {
        int i = this.radius;
        return i < 0 ? context.getResources().getDimensionPixelSize(R.dimen.badge_corner_radius) : i;
    }

    private void refreshDrawable() {
        if (isWeakReferenceValid()) {
            BadgeTextView badgeTextView = getTextView().get();
            badgeTextView.setBackgroundDrawable(getBadgeDrawable(badgeTextView.getContext()));
        }
    }

    private void setTextColor() {
        if (isWeakReferenceValid()) {
            BadgeTextView badgeTextView = getTextView().get();
            badgeTextView.setTextColor(getTextColor(badgeTextView.getContext()));
        }
    }

    private GradientDrawable getBadgeDrawable(Context context) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(0);
        gradientDrawable.setCornerRadius(getRadius(context));
        gradientDrawable.setColor(getBackgroundColor(context));
        gradientDrawable.setStroke(getBorderWidth(), getBorderColor(context));
        return gradientDrawable;
    }
}
