package androidx.camera.video.internal.utils;

import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.util.LruCache;
import androidx.camera.video.internal.encoder.EncoderConfig;
import androidx.camera.video.internal.encoder.InvalidConfigException;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public final class CodecUtil {
    private static final int MAX_CODEC_INFO_CACHE_COUNT = 10;
    private static final LruCache<String, MediaCodecInfo> sCodecInfoCache = new LruCache<>(10);

    private CodecUtil() {
    }

    public static MediaCodec createCodec(EncoderConfig encoderConfig) throws InvalidConfigException {
        return createCodec(encoderConfig.getMimeType());
    }

    public static MediaCodecInfo findCodecAndGetCodecInfo(EncoderConfig encoderConfig) throws Throwable {
        MediaCodecInfo mediaCodecInfo;
        MediaCodec mediaCodecCreateCodec;
        String mimeType = encoderConfig.getMimeType();
        LruCache<String, MediaCodecInfo> lruCache = sCodecInfoCache;
        synchronized (lruCache) {
            mediaCodecInfo = lruCache.get(mimeType);
        }
        if (mediaCodecInfo != null) {
            return mediaCodecInfo;
        }
        try {
            mediaCodecCreateCodec = createCodec(mimeType);
        } catch (Throwable th) {
            th = th;
            mediaCodecCreateCodec = null;
        }
        try {
            MediaCodecInfo codecInfo = mediaCodecCreateCodec.getCodecInfo();
            synchronized (lruCache) {
                lruCache.put(mimeType, codecInfo);
            }
            if (mediaCodecCreateCodec != null) {
                mediaCodecCreateCodec.release();
            }
            return codecInfo;
        } catch (Throwable th2) {
            th = th2;
            if (mediaCodecCreateCodec != null) {
                mediaCodecCreateCodec.release();
            }
            throw th;
        }
    }

    private static MediaCodec createCodec(String str) throws InvalidConfigException {
        try {
            return MediaCodec.createEncoderByType(str);
        } catch (IOException | IllegalArgumentException e) {
            throw new InvalidConfigException(e);
        }
    }
}
