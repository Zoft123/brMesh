package androidx.camera.video.internal.encoder;

import android.media.MediaCodecInfo;
import j$.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public abstract class EncoderInfoImpl implements EncoderInfo {
    protected final MediaCodecInfo.CodecCapabilities mCodecCapabilities;
    private final MediaCodecInfo mMediaCodecInfo;

    EncoderInfoImpl(MediaCodecInfo mediaCodecInfo, String str) throws InvalidConfigException {
        this.mMediaCodecInfo = mediaCodecInfo;
        try {
            this.mCodecCapabilities = (MediaCodecInfo.CodecCapabilities) Objects.requireNonNull(mediaCodecInfo.getCapabilitiesForType(str));
        } catch (RuntimeException e) {
            throw new InvalidConfigException("Unable to get CodecCapabilities for mime: " + str, e);
        }
    }

    @Override // androidx.camera.video.internal.encoder.EncoderInfo
    public String getName() {
        return this.mMediaCodecInfo.getName();
    }
}
