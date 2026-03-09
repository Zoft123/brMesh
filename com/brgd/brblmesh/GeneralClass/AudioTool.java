package com.brgd.brblmesh.GeneralClass;

import android.media.AudioRecord;
import android.util.Log;

/* JADX INFO: loaded from: classes.dex */
public class AudioTool {
    boolean isGetVoiceRun;
    AudioRecord mAudioRecord;
    final Object mLock = new Object();
    public double volume;
    static final int SAMPLE_RATE_IN_HZ = 44100;
    static final int BUFFER_SIZE = AudioRecord.getMinBufferSize(SAMPLE_RATE_IN_HZ, 12, 2);

    public void getNoiseLevel() {
        if (this.isGetVoiceRun) {
            return;
        }
        if (Tool.checkMicroPhonePermission()) {
            this.mAudioRecord = new AudioRecord(1, SAMPLE_RATE_IN_HZ, 12, 2, BUFFER_SIZE);
        }
        this.isGetVoiceRun = true;
        new Thread(new Runnable() { // from class: com.brgd.brblmesh.GeneralClass.AudioTool$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$getNoiseLevel$0();
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getNoiseLevel$0() {
        this.mAudioRecord.startRecording();
        int i = BUFFER_SIZE;
        short[] sArr = new short[i];
        while (this.isGetVoiceRun) {
            int i2 = this.mAudioRecord.read(sArr, 0, BUFFER_SIZE);
            long j = 0;
            for (int i3 = 0; i3 < i; i3++) {
                short s = sArr[i3];
                j += (long) (s * s);
            }
            this.volume = Math.log10(j / ((double) i2)) * 10.0d;
            synchronized (this.mLock) {
                try {
                    this.mLock.wait(100L);
                } catch (InterruptedException e) {
                    Log.d("printStackTrace", "printStackTrace" + e);
                }
            }
        }
        this.mAudioRecord.stop();
        this.mAudioRecord.release();
        this.mAudioRecord = null;
    }

    public void stopNoiseLevel() {
        this.isGetVoiceRun = false;
    }
}
