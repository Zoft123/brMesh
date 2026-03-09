package cn.com.broadlink.blelight.util.okhttp;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import cn.com.broadlink.blelight.util.okhttp.EOkHttpDownloadUtil;
import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/* JADX INFO: loaded from: classes.dex */
public class ProgressResponseBody extends ResponseBody {
    public static final String TAG = "cn.com.broadlink.blelight.util.okhttp.ProgressResponseBody";
    public static final int UPDATE = 1;
    private BufferedSource bufferedSource;
    private EOkHttpDownloadUtil.ProgressListener mListener;
    private Handler myHandler;
    private ResponseBody responseBody;

    public ProgressResponseBody(ResponseBody responseBody, EOkHttpDownloadUtil.ProgressListener progressListener) {
        this.responseBody = responseBody;
        this.mListener = progressListener;
        if (this.myHandler == null) {
            this.myHandler = new MyHandler();
        }
    }

    class MyHandler extends Handler {
        public MyHandler() {
            super(Looper.getMainLooper());
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (message.what != 1) {
                return;
            }
            ProgressModel progressModel = (ProgressModel) message.obj;
            if (ProgressResponseBody.this.mListener != null) {
                ProgressResponseBody.this.mListener.onProgress(progressModel.currentBytes, progressModel.contentLength, progressModel.done);
            }
        }
    }

    @Override // okhttp3.ResponseBody
    /* JADX INFO: renamed from: contentType */
    public MediaType getMediaType() {
        return this.responseBody.getMediaType();
    }

    @Override // okhttp3.ResponseBody
    /* JADX INFO: renamed from: contentLength */
    public long getContentLength() {
        return this.responseBody.getContentLength();
    }

    @Override // okhttp3.ResponseBody
    /* JADX INFO: renamed from: source */
    public BufferedSource getSource() {
        if (this.bufferedSource == null) {
            this.bufferedSource = Okio.buffer(mySource(this.responseBody.getSource()));
        }
        return this.bufferedSource;
    }

    private Source mySource(Source source) {
        return new ForwardingSource(source) { // from class: cn.com.broadlink.blelight.util.okhttp.ProgressResponseBody.1
            long totalBytesRead = 0;

            @Override // okio.ForwardingSource, okio.Source
            public long read(Buffer buffer, long j) throws IOException {
                long j2 = super.read(buffer, j);
                this.totalBytesRead += j2 != -1 ? j2 : 0L;
                Message messageObtain = Message.obtain();
                messageObtain.what = 1;
                messageObtain.obj = new ProgressModel(this.totalBytesRead, ProgressResponseBody.this.getContentLength(), this.totalBytesRead == ProgressResponseBody.this.getContentLength());
                ProgressResponseBody.this.myHandler.sendMessage(messageObtain);
                return j2;
            }
        };
    }

    public static class ProgressModel {
        public long contentLength;
        public long currentBytes;
        public boolean done;

        public ProgressModel(long j, long j2, boolean z) {
            this.currentBytes = j;
            this.contentLength = j2;
            this.done = z;
        }
    }
}
