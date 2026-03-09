package com.brgd.brblmesh.GeneralClass;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;

/* JADX INFO: loaded from: classes.dex */
public class BitmapUtils {
    public static Bitmap circleBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int width2 = bitmap.getWidth();
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(width, width2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        float f = width / 2.0f;
        canvas.drawCircle(f, width2 / 2.0f, f, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, paint);
        return bitmapCreateBitmap;
    }

    public static Bitmap zoom(Bitmap bitmap, float f, float f2) {
        Matrix matrix = new Matrix();
        matrix.postScale(f / bitmap.getWidth(), f2 / bitmap.getHeight());
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);
    }
}
