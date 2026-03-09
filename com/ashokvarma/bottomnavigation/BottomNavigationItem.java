package com.ashokvarma.bottomnavigation;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import androidx.core.content.ContextCompat;

/* JADX INFO: loaded from: classes.dex */
public class BottomNavigationItem {
    private boolean inActiveIconAvailable = false;
    private int mActiveColor;
    private String mActiveColorCode;
    private int mActiveColorResource;
    private BadgeItem mBadgeItem;
    private Drawable mIcon;
    private int mIconResource;
    private int mInActiveColor;
    private String mInActiveColorCode;
    private int mInActiveColorResource;
    private Drawable mInactiveIcon;
    private int mInactiveIconResource;
    private String mTitle;
    private int mTitleResource;

    public BottomNavigationItem(int i, String str) {
        this.mIconResource = i;
        this.mTitle = str;
    }

    public BottomNavigationItem(Drawable drawable, String str) {
        this.mIcon = drawable;
        this.mTitle = str;
    }

    public BottomNavigationItem(Drawable drawable, int i) {
        this.mIcon = drawable;
        this.mTitleResource = i;
    }

    public BottomNavigationItem(int i, int i2) {
        this.mIconResource = i;
        this.mTitleResource = i2;
    }

    public BottomNavigationItem setInactiveIcon(Drawable drawable) {
        if (drawable != null) {
            this.mInactiveIcon = drawable;
            this.inActiveIconAvailable = true;
        }
        return this;
    }

    public BottomNavigationItem setInactiveIconResource(int i) {
        this.mInactiveIconResource = i;
        this.inActiveIconAvailable = true;
        return this;
    }

    public BottomNavigationItem setActiveColorResource(int i) {
        this.mActiveColorResource = i;
        return this;
    }

    public BottomNavigationItem setActiveColor(String str) {
        this.mActiveColorCode = str;
        return this;
    }

    public BottomNavigationItem setActiveColor(int i) {
        this.mActiveColor = i;
        return this;
    }

    public BottomNavigationItem setInActiveColorResource(int i) {
        this.mInActiveColorResource = i;
        return this;
    }

    public BottomNavigationItem setInActiveColor(String str) {
        this.mInActiveColorCode = str;
        return this;
    }

    public BottomNavigationItem setInActiveColor(int i) {
        this.mInActiveColor = i;
        return this;
    }

    public BottomNavigationItem setBadgeItem(ShapeBadgeItem shapeBadgeItem) {
        this.mBadgeItem = shapeBadgeItem;
        return this;
    }

    public BottomNavigationItem setBadgeItem(TextBadgeItem textBadgeItem) {
        this.mBadgeItem = textBadgeItem;
        return this;
    }

    Drawable getIcon(Context context) {
        int i = this.mIconResource;
        if (i != 0) {
            return ContextCompat.getDrawable(context, i);
        }
        return this.mIcon;
    }

    String getTitle(Context context) {
        int i = this.mTitleResource;
        if (i != 0) {
            return context.getString(i);
        }
        return this.mTitle;
    }

    Drawable getInactiveIcon(Context context) {
        int i = this.mInactiveIconResource;
        if (i != 0) {
            return ContextCompat.getDrawable(context, i);
        }
        return this.mInactiveIcon;
    }

    boolean isInActiveIconAvailable() {
        return this.inActiveIconAvailable;
    }

    int getActiveColor(Context context) {
        int i = this.mActiveColorResource;
        if (i != 0) {
            return ContextCompat.getColor(context, i);
        }
        if (!TextUtils.isEmpty(this.mActiveColorCode)) {
            return Color.parseColor(this.mActiveColorCode);
        }
        int i2 = this.mActiveColor;
        if (i2 != 0) {
            return i2;
        }
        return 0;
    }

    int getInActiveColor(Context context) {
        int i = this.mInActiveColorResource;
        if (i != 0) {
            return ContextCompat.getColor(context, i);
        }
        if (!TextUtils.isEmpty(this.mInActiveColorCode)) {
            return Color.parseColor(this.mInActiveColorCode);
        }
        int i2 = this.mInActiveColor;
        if (i2 != 0) {
            return i2;
        }
        return 0;
    }

    BadgeItem getBadgeItem() {
        return this.mBadgeItem;
    }
}
