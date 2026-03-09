package com.ashokvarma.bottomnavigation;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;
import com.ashokvarma.bottomnavigation.behaviour.BottomNavBarFabBehaviour;
import com.ashokvarma.bottomnavigation.behaviour.BottomVerticalScrollBehavior;
import com.ashokvarma.bottomnavigation.utils.Utils;
import com.brgd.brblmesh.GeneralClass.DisDoubleClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
@CoordinatorLayout.DefaultBehavior(BottomVerticalScrollBehavior.class)
public class BottomNavigationBar extends FrameLayout {
    public static final int BACKGROUND_STYLE_DEFAULT = 0;
    public static final int BACKGROUND_STYLE_RIPPLE = 2;
    public static final int BACKGROUND_STYLE_STATIC = 1;
    private static final int DEFAULT_ANIMATION_DURATION = 200;
    private static final int DEFAULT_SELECTED_POSITION = -1;
    private static final int FAB_BEHAVIOUR_DISAPPEAR = 1;
    private static final int FAB_BEHAVIOUR_TRANSLATE_AND_STICK = 0;
    private static final int FAB_BEHAVIOUR_TRANSLATE_OUT = 2;
    private static final Interpolator INTERPOLATOR = new LinearOutSlowInInterpolator();
    private static final int MAX_SIZE = 5;
    private static final int MIN_SIZE = 3;
    public static final int MODE_DEFAULT = 0;
    public static final int MODE_FIXED = 1;
    public static final int MODE_FIXED_NO_TITLE = 3;
    public static final int MODE_SHIFTING = 2;
    public static final int MODE_SHIFTING_NO_TITLE = 4;
    private int mActiveColor;
    private int mAnimationDuration;
    private boolean mAutoHideEnabled;
    private int mBackgroundColor;
    private FrameLayout mBackgroundOverlay;
    private int mBackgroundStyle;
    ArrayList<BottomNavigationItem> mBottomNavigationItems;
    ArrayList<BottomNavigationTab> mBottomNavigationTabs;
    private FrameLayout mContainer;
    private float mElevation;
    private int mFirstSelectedPosition;
    private int mInActiveColor;
    private boolean mIsHidden;
    private int mMode;
    private int mRippleAnimationDuration;
    private boolean mScrollable;
    private int mSelectedPosition;
    private LinearLayout mTabContainer;
    private OnTabSelectedListener mTabSelectedListener;
    private ViewPropertyAnimatorCompat mTranslationAnimator;

    public interface OnTabSelectedListener {
        void onTabReselected(int i);

        void onTabSelected(int i);

        void onTabUnselected(int i);
    }

    public static class SimpleOnTabSelectedListener implements OnTabSelectedListener {
        @Override // com.ashokvarma.bottomnavigation.BottomNavigationBar.OnTabSelectedListener
        public void onTabReselected(int i) {
        }

        @Override // com.ashokvarma.bottomnavigation.BottomNavigationBar.OnTabSelectedListener
        public void onTabSelected(int i) {
        }

        @Override // com.ashokvarma.bottomnavigation.BottomNavigationBar.OnTabSelectedListener
        public void onTabUnselected(int i) {
        }
    }

    public BottomNavigationBar(Context context) {
        this(context, null);
    }

    public BottomNavigationBar(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public BottomNavigationBar(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mMode = 0;
        this.mBackgroundStyle = 0;
        this.mScrollable = false;
        this.mBottomNavigationItems = new ArrayList<>();
        this.mBottomNavigationTabs = new ArrayList<>();
        this.mSelectedPosition = -1;
        this.mFirstSelectedPosition = 0;
        this.mAnimationDuration = 200;
        this.mRippleAnimationDuration = DisDoubleClickListener.MIN_CLICK_DELAY_TIME;
        this.mIsHidden = false;
        parseAttrs(context, attributeSet);
        init();
    }

    public BottomNavigationBar(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mMode = 0;
        this.mBackgroundStyle = 0;
        this.mScrollable = false;
        this.mBottomNavigationItems = new ArrayList<>();
        this.mBottomNavigationTabs = new ArrayList<>();
        this.mSelectedPosition = -1;
        this.mFirstSelectedPosition = 0;
        this.mAnimationDuration = 200;
        this.mRippleAnimationDuration = DisDoubleClickListener.MIN_CLICK_DELAY_TIME;
        this.mIsHidden = false;
        parseAttrs(context, attributeSet);
        init();
    }

    private void parseAttrs(Context context, AttributeSet attributeSet) {
        if (attributeSet != null) {
            TypedArray typedArrayObtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.BottomNavigationBar, 0, 0);
            this.mActiveColor = typedArrayObtainStyledAttributes.getColor(R.styleable.BottomNavigationBar_bnbActiveColor, Utils.fetchContextColor(context, R.attr.colorAccent));
            this.mInActiveColor = typedArrayObtainStyledAttributes.getColor(R.styleable.BottomNavigationBar_bnbInactiveColor, -3355444);
            this.mBackgroundColor = typedArrayObtainStyledAttributes.getColor(R.styleable.BottomNavigationBar_bnbBackgroundColor, -1);
            this.mAutoHideEnabled = typedArrayObtainStyledAttributes.getBoolean(R.styleable.BottomNavigationBar_bnbAutoHideEnabled, true);
            this.mElevation = typedArrayObtainStyledAttributes.getDimension(R.styleable.BottomNavigationBar_bnbElevation, getResources().getDimension(R.dimen.bottom_navigation_elevation));
            setAnimationDuration(typedArrayObtainStyledAttributes.getInt(R.styleable.BottomNavigationBar_bnbAnimationDuration, 200));
            int i = typedArrayObtainStyledAttributes.getInt(R.styleable.BottomNavigationBar_bnbMode, 0);
            if (i == 1) {
                this.mMode = 1;
            } else if (i == 2) {
                this.mMode = 2;
            } else if (i == 3) {
                this.mMode = 3;
            } else if (i == 4) {
                this.mMode = 4;
            } else {
                this.mMode = 0;
            }
            int i2 = typedArrayObtainStyledAttributes.getInt(R.styleable.BottomNavigationBar_bnbBackgroundStyle, 0);
            if (i2 == 1) {
                this.mBackgroundStyle = 1;
            } else if (i2 == 2) {
                this.mBackgroundStyle = 2;
            } else {
                this.mBackgroundStyle = 0;
            }
            typedArrayObtainStyledAttributes.recycle();
            return;
        }
        this.mActiveColor = Utils.fetchContextColor(context, R.attr.colorAccent);
        this.mInActiveColor = -3355444;
        this.mBackgroundColor = -1;
        this.mElevation = getResources().getDimension(R.dimen.bottom_navigation_elevation);
    }

    private void init() {
        setLayoutParams(new ViewGroup.LayoutParams(new ViewGroup.LayoutParams(-1, -2)));
        View viewInflate = LayoutInflater.from(getContext()).inflate(R.layout.bottom_navigation_bar_container, (ViewGroup) this, true);
        this.mBackgroundOverlay = (FrameLayout) viewInflate.findViewById(R.id.bottom_navigation_bar_overLay);
        this.mContainer = (FrameLayout) viewInflate.findViewById(R.id.bottom_navigation_bar_container);
        this.mTabContainer = (LinearLayout) viewInflate.findViewById(R.id.bottom_navigation_bar_item_container);
        setOutlineProvider(ViewOutlineProvider.BOUNDS);
        ViewCompat.setElevation(this, this.mElevation);
        setClipToPadding(false);
    }

    public BottomNavigationBar addItem(BottomNavigationItem bottomNavigationItem) {
        this.mBottomNavigationItems.add(bottomNavigationItem);
        return this;
    }

    public BottomNavigationBar removeItem(BottomNavigationItem bottomNavigationItem) {
        this.mBottomNavigationItems.remove(bottomNavigationItem);
        return this;
    }

    public BottomNavigationBar setMode(int i) {
        this.mMode = i;
        return this;
    }

    public BottomNavigationBar setBackgroundStyle(int i) {
        this.mBackgroundStyle = i;
        return this;
    }

    public BottomNavigationBar setActiveColor(int i) {
        this.mActiveColor = ContextCompat.getColor(getContext(), i);
        return this;
    }

    public BottomNavigationBar setActiveColor(String str) {
        this.mActiveColor = Color.parseColor(str);
        return this;
    }

    public BottomNavigationBar setInActiveColor(int i) {
        this.mInActiveColor = ContextCompat.getColor(getContext(), i);
        return this;
    }

    public BottomNavigationBar setInActiveColor(String str) {
        this.mInActiveColor = Color.parseColor(str);
        return this;
    }

    public BottomNavigationBar setBarBackgroundColor(int i) {
        this.mBackgroundColor = ContextCompat.getColor(getContext(), i);
        return this;
    }

    public BottomNavigationBar setBarBackgroundColor(String str) {
        this.mBackgroundColor = Color.parseColor(str);
        return this;
    }

    public BottomNavigationBar setFirstSelectedPosition(int i) {
        this.mFirstSelectedPosition = i;
        return this;
    }

    private BottomNavigationBar setScrollable(boolean z) {
        this.mScrollable = z;
        return this;
    }

    public void initialise() {
        this.mSelectedPosition = -1;
        this.mBottomNavigationTabs.clear();
        if (this.mBottomNavigationItems.isEmpty()) {
            return;
        }
        this.mTabContainer.removeAllViews();
        if (this.mMode == 0) {
            if (this.mBottomNavigationItems.size() <= 3) {
                this.mMode = 1;
            } else {
                this.mMode = 2;
            }
        }
        if (this.mBackgroundStyle == 0) {
            if (this.mMode == 1) {
                this.mBackgroundStyle = 1;
            } else {
                this.mBackgroundStyle = 2;
            }
        }
        if (this.mBackgroundStyle == 1) {
            this.mBackgroundOverlay.setVisibility(8);
            this.mContainer.setBackgroundColor(this.mBackgroundColor);
        }
        int screenWidth = Utils.getScreenWidth(getContext());
        int i = this.mMode;
        if (i == 1 || i == 3) {
            int i2 = BottomNavigationHelper.getMeasurementsForFixedMode(getContext(), screenWidth, this.mBottomNavigationItems.size(), this.mScrollable)[0];
            for (BottomNavigationItem bottomNavigationItem : this.mBottomNavigationItems) {
                setUpTab(this.mMode == 3, new FixedBottomNavigationTab(getContext()), bottomNavigationItem, i2, i2);
            }
        } else if (i == 2 || i == 4) {
            int[] measurementsForShiftingMode = BottomNavigationHelper.getMeasurementsForShiftingMode(getContext(), screenWidth, this.mBottomNavigationItems.size(), this.mScrollable);
            int i3 = measurementsForShiftingMode[0];
            int i4 = measurementsForShiftingMode[1];
            for (BottomNavigationItem bottomNavigationItem2 : this.mBottomNavigationItems) {
                setUpTab(this.mMode == 4, new ShiftingBottomNavigationTab(getContext()), bottomNavigationItem2, i3, i4);
            }
        }
        int size = this.mBottomNavigationTabs.size();
        int i5 = this.mFirstSelectedPosition;
        if (size > i5) {
            selectTabInternal(i5, true, false, false);
        } else {
            if (this.mBottomNavigationTabs.isEmpty()) {
                return;
            }
            selectTabInternal(0, true, false, false);
        }
    }

    public BottomNavigationBar setTabSelectedListener(OnTabSelectedListener onTabSelectedListener) {
        this.mTabSelectedListener = onTabSelectedListener;
        return this;
    }

    public BottomNavigationBar setAnimationDuration(int i) {
        this.mAnimationDuration = i;
        this.mRippleAnimationDuration = (int) (((double) i) * 2.5d);
        return this;
    }

    public void clearAll() {
        this.mTabContainer.removeAllViews();
        this.mBottomNavigationTabs.clear();
        this.mBottomNavigationItems.clear();
        this.mBackgroundOverlay.setVisibility(8);
        this.mContainer.setBackgroundColor(0);
        this.mSelectedPosition = -1;
    }

    public void selectTab(int i) {
        selectTab(i, true);
    }

    public void selectTab(int i, boolean z) {
        selectTabInternal(i, false, z, z);
    }

    private void setUpTab(boolean z, BottomNavigationTab bottomNavigationTab, BottomNavigationItem bottomNavigationItem, int i, int i2) {
        bottomNavigationTab.setIsNoTitleMode(z);
        bottomNavigationTab.setInactiveWidth(i);
        bottomNavigationTab.setActiveWidth(i2);
        bottomNavigationTab.setPosition(this.mBottomNavigationItems.indexOf(bottomNavigationItem));
        bottomNavigationTab.setOnClickListener(new View.OnClickListener() { // from class: com.ashokvarma.bottomnavigation.BottomNavigationBar.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                BottomNavigationBar.this.selectTabInternal(((BottomNavigationTab) view).getPosition(), false, true, false);
            }
        });
        this.mBottomNavigationTabs.add(bottomNavigationTab);
        BottomNavigationHelper.bindTabWithData(bottomNavigationItem, bottomNavigationTab, this);
        bottomNavigationTab.initialise(this.mBackgroundStyle == 1);
        this.mTabContainer.addView(bottomNavigationTab);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void selectTabInternal(int i, boolean z, boolean z2, boolean z3) {
        int i2 = this.mSelectedPosition;
        if (i2 != i) {
            int i3 = this.mBackgroundStyle;
            if (i3 == 1) {
                if (i2 != -1) {
                    this.mBottomNavigationTabs.get(i2).unSelect(true, this.mAnimationDuration);
                }
                this.mBottomNavigationTabs.get(i).select(true, this.mAnimationDuration);
            } else if (i3 == 2) {
                if (i2 != -1) {
                    this.mBottomNavigationTabs.get(i2).unSelect(false, this.mAnimationDuration);
                }
                this.mBottomNavigationTabs.get(i).select(false, this.mAnimationDuration);
                final BottomNavigationTab bottomNavigationTab = this.mBottomNavigationTabs.get(i);
                if (z) {
                    this.mContainer.setBackgroundColor(bottomNavigationTab.getActiveColor());
                    this.mBackgroundOverlay.setVisibility(8);
                } else {
                    this.mBackgroundOverlay.post(new Runnable() { // from class: com.ashokvarma.bottomnavigation.BottomNavigationBar.2
                        @Override // java.lang.Runnable
                        public void run() {
                            BottomNavigationHelper.setBackgroundWithRipple(bottomNavigationTab, BottomNavigationBar.this.mContainer, BottomNavigationBar.this.mBackgroundOverlay, bottomNavigationTab.getActiveColor(), BottomNavigationBar.this.mRippleAnimationDuration);
                        }
                    });
                }
            }
            this.mSelectedPosition = i;
        }
        if (z2) {
            sendListenerCall(i2, i, z3);
        }
    }

    private void sendListenerCall(int i, int i2, boolean z) {
        OnTabSelectedListener onTabSelectedListener = this.mTabSelectedListener;
        if (onTabSelectedListener != null) {
            if (z) {
                onTabSelectedListener.onTabSelected(i2);
                return;
            }
            if (i == i2) {
                onTabSelectedListener.onTabReselected(i2);
                return;
            }
            onTabSelectedListener.onTabSelected(i2);
            if (i != -1) {
                this.mTabSelectedListener.onTabUnselected(i);
            }
        }
    }

    public void toggle() {
        toggle(true);
    }

    public void toggle(boolean z) {
        if (this.mIsHidden) {
            show(z);
        } else {
            hide(z);
        }
    }

    public void hide() {
        hide(true);
    }

    public void hide(boolean z) {
        this.mIsHidden = true;
        setTranslationY(getHeight(), z);
    }

    public void show() {
        show(true);
    }

    public void show(boolean z) {
        this.mIsHidden = false;
        setTranslationY(0, z);
    }

    private void setTranslationY(int i, boolean z) {
        if (z) {
            animateOffset(i);
            return;
        }
        ViewPropertyAnimatorCompat viewPropertyAnimatorCompat = this.mTranslationAnimator;
        if (viewPropertyAnimatorCompat != null) {
            viewPropertyAnimatorCompat.cancel();
        }
        setTranslationY(i);
    }

    private void animateOffset(int i) {
        ViewPropertyAnimatorCompat viewPropertyAnimatorCompat = this.mTranslationAnimator;
        if (viewPropertyAnimatorCompat == null) {
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompatAnimate = ViewCompat.animate(this);
            this.mTranslationAnimator = viewPropertyAnimatorCompatAnimate;
            viewPropertyAnimatorCompatAnimate.setDuration(this.mRippleAnimationDuration);
            this.mTranslationAnimator.setInterpolator(INTERPOLATOR);
        } else {
            viewPropertyAnimatorCompat.cancel();
        }
        this.mTranslationAnimator.translationY(i).start();
    }

    public boolean isHidden() {
        return this.mIsHidden;
    }

    public boolean isAutoHideEnabled() {
        return this.mAutoHideEnabled;
    }

    public void setAutoHideEnabled(boolean z) {
        this.mAutoHideEnabled = z;
    }

    public void setFab(FloatingActionButton floatingActionButton) {
        ViewGroup.LayoutParams layoutParams = floatingActionButton.getLayoutParams();
        if (layoutParams == null || !(layoutParams instanceof CoordinatorLayout.LayoutParams)) {
            return;
        }
        ((CoordinatorLayout.LayoutParams) layoutParams).setBehavior(new BottomNavBarFabBehaviour());
    }

    private void setFab(FloatingActionButton floatingActionButton, int i) {
        ViewGroup.LayoutParams layoutParams = floatingActionButton.getLayoutParams();
        if (layoutParams == null || !(layoutParams instanceof CoordinatorLayout.LayoutParams)) {
            return;
        }
        ((CoordinatorLayout.LayoutParams) layoutParams).setBehavior(new BottomNavBarFabBehaviour());
    }

    public int getActiveColor() {
        return this.mActiveColor;
    }

    public int getInActiveColor() {
        return this.mInActiveColor;
    }

    public int getBackgroundColor() {
        return this.mBackgroundColor;
    }

    public int getCurrentSelectedPosition() {
        return this.mSelectedPosition;
    }

    public int getAnimationDuration() {
        return this.mAnimationDuration;
    }
}
