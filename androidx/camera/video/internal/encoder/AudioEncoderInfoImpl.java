package androidx.camera.video.internal.encoder;

import android.media.MediaCodecInfo;
import android.util.Range;
import androidx.camera.video.internal.utils.CodecUtil;
import j$.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class AudioEncoderInfoImpl extends EncoderInfoImpl implements AudioEncoderInfo {
    private final MediaCodecInfo.AudioCapabilities mAudioCapabilities;

    public static AudioEncoderInfoImpl from(AudioEncoderConfig audioEncoderConfig) throws InvalidConfigException {
        return new AudioEncoderInfoImpl(CodecUtil.findCodecAndGetCodecInfo(audioEncoderConfig), audioEncoderConfig.getMimeType());
    }

    AudioEncoderInfoImpl(MediaCodecInfo mediaCodecInfo, String str) throws InvalidConfigException {
        super(mediaCodecInfo, str);
        this.mAudioCapabilities = (MediaCodecInfo.AudioCapabilities) Objects.requireNonNull(this.mCodecCapabilities.getAudioCapabilities());
    }

    @Override // androidx.camera.video.internal.encoder.AudioEncoderInfo
    public Range<Integer> getBitrateRange() {
        return this.mAudioCapabilities.getBitrateRange();
    }
}
