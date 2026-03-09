package com.brgd.brblmesh.GeneralClass;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import androidx.core.view.GestureDetectorCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import java.lang.reflect.Field;

/* JADX INFO: loaded from: classes.dex */
public class ListenerInterceptor {
    private boolean mDoDrag = true;
    private GestureDetector.OnGestureListener mListener2BeIntercept;

    public ListenerInterceptor(ItemTouchHelper itemTouchHelper) {
        Field declaredField;
        InterceptListener interceptListener = new InterceptListener();
        try {
            Field declaredField2 = ItemTouchHelper.class.getDeclaredField("mGestureDetector");
            declaredField2.setAccessible(true);
            Object obj = declaredField2.get(itemTouchHelper);
            Field declaredField3 = GestureDetectorCompat.class.getDeclaredField("mImpl");
            declaredField3.setAccessible(true);
            Object obj2 = declaredField3.get(obj);
            if (obj2 == null) {
                return;
            }
            Object obj3 = null;
            try {
                declaredField = obj2.getClass().getDeclaredField("mListener");
            } catch (Exception e) {
                Log.d("printStackTrace", "printStackTrace" + e);
                declaredField = null;
            }
            if (declaredField == null) {
                Field declaredField4 = obj2.getClass().getDeclaredField("mDetector");
                declaredField4.setAccessible(true);
                obj3 = declaredField4.get(obj2);
                if (obj3 == null) {
                    return;
                } else {
                    declaredField = obj3.getClass().getDeclaredField("mListener");
                }
            }
            declaredField.setAccessible(true);
            this.mListener2BeIntercept = (GestureDetector.OnGestureListener) declaredField.get(obj3);
            declaredField.set(obj3, interceptListener);
        } catch (Exception e2) {
            Log.d("printStackTrace", "printStackTrace" + e2);
        }
    }

    public void setDoDrag(boolean z) {
        this.mDoDrag = z;
    }

    public class InterceptListener extends GestureDetector.SimpleOnGestureListener {
        public InterceptListener() {
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public void onShowPress(MotionEvent motionEvent) {
            if (ListenerInterceptor.this.mDoDrag) {
                ListenerInterceptor.this.mListener2BeIntercept.onLongPress(motionEvent);
            }
        }
    }
}
