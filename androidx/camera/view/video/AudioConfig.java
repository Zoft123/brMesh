package androidx.camera.view.video;

/* JADX INFO: loaded from: classes.dex */
public class AudioConfig {
    public static final AudioConfig AUDIO_DISABLED = new AudioConfig(false);
    private final boolean mIsAudioEnabled;

    AudioConfig(boolean z) {
        this.mIsAudioEnabled = z;
    }

    public static AudioConfig create(boolean z) {
        return new AudioConfig(z);
    }

    public boolean getAudioEnabled() {
        return this.mIsAudioEnabled;
    }
}
