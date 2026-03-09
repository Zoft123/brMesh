package com.brgd.brblmesh.GeneralClass;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import androidx.exifinterface.media.ExifInterface;
import com.alibaba.fastjson.asm.Opcodes;
import j$.util.Objects;
import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: loaded from: classes.dex */
public class ImageUtils {
    public static final String TAG = "ImageUtils";

    public static int getExifRotation(Uri uri, Context context) {
        try {
            InputStream inputStreamOpenInputStream = context.getContentResolver().openInputStream(uri);
            if (inputStreamOpenInputStream == null) {
                return 0;
            }
            int attributeInt = ((ExifInterface) Objects.requireNonNull(Build.VERSION.SDK_INT >= 24 ? new ExifInterface(inputStreamOpenInputStream) : null)).getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
            int i = attributeInt != 3 ? attributeInt != 6 ? attributeInt != 8 ? 0 : 270 : 90 : Opcodes.GETFIELD;
            inputStreamOpenInputStream.close();
            return i;
        } catch (IOException e) {
            Log.e(TAG, "读取 EXIF 信息失败", e);
            return 0;
        }
    }

    public static Bitmap rotateBitmap(Bitmap bitmap, int i) {
        if (i == 0 || bitmap == null) {
            return bitmap;
        }
        Matrix matrix = new Matrix();
        matrix.postRotate(i);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    public static Bitmap processImage(Uri uri, Context context) throws IOException {
        ContentResolver contentResolver = context.getContentResolver();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        InputStream inputStreamOpenInputStream = contentResolver.openInputStream(uri);
        BitmapFactory.decodeStream(inputStreamOpenInputStream, null, options);
        ((InputStream) Objects.requireNonNull(inputStreamOpenInputStream)).close();
        int iMin = Math.min(options.outWidth / Opcodes.GETFIELD, options.outHeight / Opcodes.GETFIELD);
        options.inSampleSize = iMin > 0 ? iMin : 1;
        options.inJustDecodeBounds = false;
        InputStream inputStreamOpenInputStream2 = contentResolver.openInputStream(uri);
        Bitmap bitmapDecodeStream = BitmapFactory.decodeStream(inputStreamOpenInputStream2, null, options);
        ((InputStream) Objects.requireNonNull(inputStreamOpenInputStream2)).close();
        int exifRotation = getExifRotation(uri, context);
        return exifRotation != 0 ? rotateBitmap(bitmapDecodeStream, exifRotation) : bitmapDecodeStream;
    }
}
