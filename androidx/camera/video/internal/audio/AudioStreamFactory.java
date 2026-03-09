package androidx.camera.video.internal.audio;

import android.content.Context;
import androidx.camera.video.internal.audio.AudioStream;

/* JADX INFO: loaded from: classes.dex */
interface AudioStreamFactory {
    AudioStream create(AudioSettings audioSettings, Context context) throws AudioStream.AudioStreamException;
}
