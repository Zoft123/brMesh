package com.brgd.brblmesh.GeneralClass.WaveView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/* JADX INFO: loaded from: classes.dex */
public class WaveView extends View {
    public float SPEED;
    public int SPEEDWAVE;
    public float SPEEDX;
    private boolean isMeasured;
    private float mLeftSide;
    private float mLevelLine;
    private float mMoveLen;
    private float mMoveLen1;
    private Paint mPaint;
    private Paint mPaint1;
    private List<Point> mPointsList;
    private List<Point> mPointsList1;
    private float mRightSide;
    private MyTimerTask mTask;
    private int mViewHeight;
    float mViewWidth;
    float mWaveHeight;
    private Path mWavePath;
    private Path mWavePath1;
    private float mWaveWidth;
    private Timer timer;
    private final MyHandler updateHandler;

    /* JADX INFO: Access modifiers changed from: private */
    public void resetPoints() {
        this.mLeftSide = -this.mWaveWidth;
        for (int i = 0; i < this.mPointsList.size(); i++) {
            Point point = this.mPointsList.get(i);
            float f = this.mWaveWidth;
            point.setX(((i * f) / 4.0f) - f);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void resetPoints1() {
        this.mRightSide = this.mWaveWidth * 2.0f;
        for (int i = 0; i < this.mPointsList.size(); i++) {
            this.mPointsList1.get(i).setX((i * this.mWaveWidth) / 4.0f);
        }
    }

    public WaveView(Context context) {
        super(context);
        this.mWaveHeight = 20.0f;
        this.mWaveWidth = 200.0f;
        this.SPEED = 0.8f;
        this.SPEEDX = 1.2f;
        this.SPEEDWAVE = 3;
        this.isMeasured = false;
        this.updateHandler = new MyHandler(Looper.getMainLooper(), this);
        init();
    }

    public WaveView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mWaveHeight = 20.0f;
        this.mWaveWidth = 200.0f;
        this.SPEED = 0.8f;
        this.SPEEDX = 1.2f;
        this.SPEEDWAVE = 3;
        this.isMeasured = false;
        this.updateHandler = new MyHandler(Looper.getMainLooper(), this);
        init();
    }

    public WaveView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mWaveHeight = 20.0f;
        this.mWaveWidth = 200.0f;
        this.SPEED = 0.8f;
        this.SPEEDX = 1.2f;
        this.SPEEDWAVE = 3;
        this.isMeasured = false;
        this.updateHandler = new MyHandler(Looper.getMainLooper(), this);
        init();
    }

    private void init() {
        this.mPointsList = new ArrayList();
        this.mPointsList1 = new ArrayList();
        Paint paint = new Paint();
        this.mPaint = paint;
        paint.setAntiAlias(true);
        this.mPaint.setStyle(Paint.Style.FILL);
        this.mPaint.setColor(Color.parseColor("#ffffff"));
        Paint paint2 = new Paint();
        this.mPaint1 = paint2;
        paint2.setAntiAlias(true);
        this.mPaint1.setStyle(Paint.Style.FILL);
        this.mPaint1.setColor(Color.parseColor("#DFF2FF"));
        this.mWavePath = new Path();
        this.mWavePath1 = new Path();
    }

    public void startWaveLine() {
        MyTimerTask myTimerTask = this.mTask;
        if (myTimerTask != null) {
            myTimerTask.cancel();
            this.mTask = null;
        }
        Timer timer = this.timer;
        if (timer != null) {
            timer.cancel();
            this.timer = null;
        }
        this.timer = new Timer();
        MyTimerTask myTimerTask2 = new MyTimerTask(this.updateHandler);
        this.mTask = myTimerTask2;
        this.timer.schedule(myTimerTask2, 0L, this.SPEEDWAVE);
    }

    public void stopWaveLine() {
        MyTimerTask myTimerTask = this.mTask;
        if (myTimerTask != null) {
            myTimerTask.cancel();
            this.mTask = null;
        }
        Timer timer = this.timer;
        if (timer != null) {
            timer.cancel();
            this.timer = null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0065  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void onMeasure(int r9, int r10) {
        /*
            Method dump skipped, instruction units count: 238
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.brgd.brblmesh.GeneralClass.WaveView.WaveView.onMeasure(int, int):void");
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        this.mWavePath1.reset();
        this.mWavePath1.moveTo(this.mPointsList1.get(8).getX(), this.mPointsList1.get(8).getY());
        for (int i = 5; i >= -1; i -= 2) {
            int i2 = i + 2;
            int i3 = i + 1;
            this.mWavePath1.quadTo(this.mPointsList1.get(i2).getX(), this.mPointsList1.get(i2).getY(), this.mPointsList1.get(i3).getX(), this.mPointsList1.get(i3).getY());
        }
        int i4 = 0;
        this.mWavePath1.lineTo(this.mPointsList.get(0).getX(), this.mViewHeight);
        this.mWavePath1.lineTo(this.mRightSide, this.mViewHeight);
        this.mWavePath1.close();
        canvas.drawPath(this.mWavePath1, this.mPaint1);
        this.mWavePath.reset();
        this.mWavePath.moveTo(this.mPointsList.get(0).getX(), this.mPointsList.get(0).getY());
        while (i4 < this.mPointsList.size() - 2) {
            int i5 = i4 + 1;
            i4 += 2;
            this.mWavePath.quadTo(this.mPointsList.get(i5).getX(), this.mPointsList.get(i5).getY(), this.mPointsList.get(i4).getX(), this.mPointsList.get(i4).getY());
        }
        this.mWavePath.lineTo(this.mPointsList.get(8).getX(), this.mViewHeight);
        this.mWavePath.lineTo(this.mLeftSide, this.mViewHeight);
        this.mWavePath.close();
        canvas.drawPath(this.mWavePath, this.mPaint);
    }

    static class MyTimerTask extends TimerTask {
        Handler handler;

        public MyTimerTask(Handler handler) {
            this.handler = handler;
        }

        @Override // java.util.TimerTask, java.lang.Runnable
        public void run() {
            Handler handler = this.handler;
            handler.sendMessage(handler.obtainMessage());
        }
    }

    private static class MyHandler extends Handler {
        WeakReference<Object> weakReference;

        MyHandler(Looper looper, Object obj) {
            super(looper);
            this.weakReference = new WeakReference<>(obj);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            WaveView waveView = (WaveView) this.weakReference.get();
            waveView.mMoveLen += waveView.SPEED;
            waveView.mMoveLen1 += waveView.SPEEDX;
            if (waveView.mLevelLine < waveView.mViewHeight / 2.0f) {
                waveView.mLevelLine = waveView.mViewHeight / 2.0f;
            }
            waveView.mLeftSide += waveView.SPEED;
            waveView.mRightSide -= waveView.SPEEDX;
            for (int i = 0; i < waveView.mPointsList.size(); i++) {
                ((Point) waveView.mPointsList.get(i)).setX(((Point) waveView.mPointsList.get(i)).getX() + waveView.SPEED);
            }
            for (int i2 = 0; i2 < waveView.mPointsList1.size(); i2++) {
                ((Point) waveView.mPointsList1.get(i2)).setX(((Point) waveView.mPointsList1.get(i2)).getX() - waveView.SPEEDX);
            }
            if (waveView.mMoveLen >= waveView.mWaveWidth) {
                waveView.mMoveLen = 0.0f;
                waveView.resetPoints();
            }
            if (waveView.mMoveLen1 >= waveView.mWaveWidth) {
                waveView.mMoveLen1 = 0.0f;
                waveView.resetPoints1();
            }
            waveView.invalidate();
        }
    }

    static class Point {
        private float x;
        private float y;

        public float getX() {
            return this.x;
        }

        public void setX(float f) {
            this.x = f;
        }

        public float getY() {
            return this.y;
        }

        public void setY(float f) {
            this.y = f;
        }

        public Point(float f, float f2) {
            this.x = f;
            this.y = f2;
        }
    }
}
