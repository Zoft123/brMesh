package com.king.zxing.analyze;

import androidx.camera.core.ImageProxy;
import com.google.zxing.Result;
import com.king.camera.scan.AnalyzeResult;
import com.king.camera.scan.FrameMetadata;
import com.king.camera.scan.analyze.Analyzer;
import com.king.camera.scan.util.ImageUtils;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/* JADX INFO: loaded from: classes4.dex */
public abstract class ImageAnalyzer implements Analyzer<Result> {
    private final Queue<byte[]> queue = new ConcurrentLinkedQueue();
    private final AtomicBoolean joinQueue = new AtomicBoolean(false);

    public abstract Result analyze(byte[] bArr, int i, int i2);

    @Override // com.king.camera.scan.analyze.Analyzer
    public void analyze(ImageProxy imageProxy, Analyzer.OnAnalyzeListener<Result> onAnalyzeListener) {
        Result resultAnalyze;
        if (!this.joinQueue.get()) {
            int width = imageProxy.getWidth() * imageProxy.getHeight();
            this.queue.add(new byte[width + ((width / 4) * 2)]);
            this.joinQueue.set(true);
        }
        byte[] bArrPoll = this.queue.poll();
        if (bArrPoll == null) {
            return;
        }
        try {
            int rotationDegrees = imageProxy.getImageInfo().getRotationDegrees();
            int width2 = imageProxy.getWidth();
            int height = imageProxy.getHeight();
            ImageUtils.yuv_420_888toNv21(imageProxy, bArrPoll);
            if (rotationDegrees == 90 || rotationDegrees == 270) {
                byte[] bArr = new byte[bArrPoll.length];
                for (int i = 0; i < height; i++) {
                    for (int i2 = 0; i2 < width2; i2++) {
                        bArr[(((i2 * height) + height) - i) - 1] = bArrPoll[(i * width2) + i2];
                    }
                }
                resultAnalyze = analyze(bArr, height, width2);
            } else {
                resultAnalyze = analyze(bArrPoll, width2, height);
            }
            if (resultAnalyze != null) {
                FrameMetadata frameMetadata = new FrameMetadata(width2, height, rotationDegrees);
                this.joinQueue.set(false);
                onAnalyzeListener.onSuccess(new AnalyzeResult<>(bArrPoll, 17, frameMetadata, resultAnalyze));
            } else {
                this.queue.add(bArrPoll);
                onAnalyzeListener.onFailure(null);
            }
        } catch (Exception unused) {
            this.queue.add(bArrPoll);
            onAnalyzeListener.onFailure(null);
        }
    }
}
