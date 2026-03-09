package androidx.camera.video.internal.audio;

import android.content.Context;
import androidx.camera.core.Logger;
import androidx.camera.core.impl.Observable;
import androidx.camera.core.impl.utils.executor.CameraXExecutors;
import androidx.camera.core.impl.utils.futures.FutureCallback;
import androidx.camera.core.impl.utils.futures.Futures;
import androidx.camera.video.AudioStats;
import androidx.camera.video.internal.BufferProvider;
import androidx.camera.video.internal.audio.AudioStream;
import androidx.camera.video.internal.encoder.InputBuffer;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import androidx.core.util.Preconditions;
import com.google.common.util.concurrent.ListenableFuture;
import j$.util.Objects;
import java.nio.ByteBuffer;
import java.nio.ShortBuffer;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: loaded from: classes.dex */
public final class AudioSource {
    static final long DEFAULT_START_RETRY_INTERVAL_MS = 3000;
    private static final String TAG = "AudioSource";
    private FutureCallback<InputBuffer> mAcquireBufferCallback;
    long mAmplitudeTimestamp;
    double mAudioAmplitude;
    private final int mAudioFormat;
    public final int mAudioSource;
    AudioSourceCallback mAudioSourceCallback;
    final AudioStream mAudioStream;
    boolean mAudioStreamSilenced;
    BufferProvider<? extends InputBuffer> mBufferProvider;
    BufferProvider.State mBufferProviderState;
    Executor mCallbackExecutor;
    final Executor mExecutor;
    boolean mInSilentStartState;
    boolean mIsSendingAudio;
    private long mLatestFailedStartTimeNs;
    boolean mMuted;
    final AtomicReference<Boolean> mNotifiedSilenceState;
    final AtomicBoolean mNotifiedSuspendState;
    final SilentAudioStream mSilentAudioStream;
    private final long mStartRetryIntervalNs;
    InternalState mState;
    private Observable.Observer<BufferProvider.State> mStateObserver;
    private byte[] mZeroBytes;

    public interface AudioSourceCallback {

        /* JADX INFO: renamed from: androidx.camera.video.internal.audio.AudioSource$AudioSourceCallback$-CC, reason: invalid class name */
        public final /* synthetic */ class CC {
            public static void $default$onSuspendStateChanged(AudioSourceCallback _this, boolean z) {
            }
        }

        void onAmplitudeValue(double d);

        void onError(Throwable th);

        void onSilenceStateChanged(boolean z);

        void onSuspendStateChanged(boolean z);
    }

    enum InternalState {
        CONFIGURED,
        STARTED,
        RELEASED
    }

    public AudioSource(AudioSettings audioSettings, Executor executor, Context context) throws AudioSourceAccessException {
        this(audioSettings, executor, context, new AudioStreamFactory() { // from class: androidx.camera.video.internal.audio.AudioSource$$ExternalSyntheticLambda3
            @Override // androidx.camera.video.internal.audio.AudioStreamFactory
            public final AudioStream create(AudioSettings audioSettings2, Context context2) {
                return new AudioStreamImpl(audioSettings2, context2);
            }
        }, DEFAULT_START_RETRY_INTERVAL_MS);
    }

    AudioSource(AudioSettings audioSettings, Executor executor, Context context, AudioStreamFactory audioStreamFactory, long j) throws AudioSourceAccessException {
        this.mNotifiedSilenceState = new AtomicReference<>(null);
        this.mNotifiedSuspendState = new AtomicBoolean(false);
        this.mState = InternalState.CONFIGURED;
        this.mBufferProviderState = BufferProvider.State.INACTIVE;
        this.mAmplitudeTimestamp = 0L;
        Executor executorNewSequentialExecutor = CameraXExecutors.newSequentialExecutor(executor);
        this.mExecutor = executorNewSequentialExecutor;
        this.mStartRetryIntervalNs = TimeUnit.MILLISECONDS.toNanos(j);
        try {
            BufferedAudioStream bufferedAudioStream = new BufferedAudioStream(audioStreamFactory.create(audioSettings, context), audioSettings);
            this.mAudioStream = bufferedAudioStream;
            bufferedAudioStream.setCallback(new AudioStreamCallback(), executorNewSequentialExecutor);
            this.mSilentAudioStream = new SilentAudioStream(audioSettings);
            this.mAudioFormat = audioSettings.getAudioFormat();
            this.mAudioSource = audioSettings.getAudioSource();
        } catch (AudioStream.AudioStreamException | IllegalArgumentException e) {
            throw new AudioSourceAccessException("Unable to create AudioStream", e);
        }
    }

    class AudioStreamCallback implements AudioStream.AudioStreamCallback {
        AudioStreamCallback() {
        }

        @Override // androidx.camera.video.internal.audio.AudioStream.AudioStreamCallback
        public void onSilenceStateChanged(boolean z) {
            AudioSource.this.mAudioStreamSilenced = z;
            if (AudioSource.this.mState == InternalState.STARTED) {
                AudioSource.this.notifySilenced();
            }
        }
    }

    public void setBufferProvider(final BufferProvider<? extends InputBuffer> bufferProvider) {
        this.mExecutor.execute(new Runnable() { // from class: androidx.camera.video.internal.audio.AudioSource$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m285x885db6e2(bufferProvider);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$setBufferProvider$0$androidx-camera-video-internal-audio-AudioSource, reason: not valid java name */
    /* synthetic */ void m285x885db6e2(BufferProvider bufferProvider) {
        int iOrdinal = this.mState.ordinal();
        if (iOrdinal != 0 && iOrdinal != 1) {
            if (iOrdinal == 2) {
                throw new AssertionError("AudioSource is released");
            }
        } else if (this.mBufferProvider != bufferProvider) {
            resetBufferProvider(bufferProvider);
        }
    }

    /* JADX INFO: renamed from: lambda$start$1$androidx-camera-video-internal-audio-AudioSource, reason: not valid java name */
    /* synthetic */ void m286lambda$start$1$androidxcameravideointernalaudioAudioSource() {
        start(this.mMuted);
    }

    public void start() {
        this.mExecutor.execute(new Runnable() { // from class: androidx.camera.video.internal.audio.AudioSource$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m286lambda$start$1$androidxcameravideointernalaudioAudioSource();
            }
        });
    }

    public void start(final boolean z) {
        this.mExecutor.execute(new Runnable() { // from class: androidx.camera.video.internal.audio.AudioSource$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m287lambda$start$2$androidxcameravideointernalaudioAudioSource(z);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$start$2$androidx-camera-video-internal-audio-AudioSource, reason: not valid java name */
    /* synthetic */ void m287lambda$start$2$androidxcameravideointernalaudioAudioSource(boolean z) {
        int iOrdinal = this.mState.ordinal();
        if (iOrdinal != 0) {
            if (iOrdinal == 2) {
                throw new AssertionError("AudioSource is released");
            }
            return;
        }
        this.mNotifiedSilenceState.set(null);
        this.mNotifiedSuspendState.set(false);
        setState(InternalState.STARTED);
        mute(z);
        updateSendingAudio();
    }

    public void stop() {
        this.mExecutor.execute(new Runnable() { // from class: androidx.camera.video.internal.audio.AudioSource$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m288lambda$stop$3$androidxcameravideointernalaudioAudioSource();
            }
        });
    }

    /* JADX INFO: renamed from: lambda$stop$3$androidx-camera-video-internal-audio-AudioSource, reason: not valid java name */
    /* synthetic */ void m288lambda$stop$3$androidxcameravideointernalaudioAudioSource() {
        int iOrdinal = this.mState.ordinal();
        if (iOrdinal == 1) {
            setState(InternalState.CONFIGURED);
            updateSendingAudio();
        } else {
            if (iOrdinal != 2) {
                return;
            }
            Logger.w(TAG, "AudioSource is released. Calling stop() is a no-op.");
        }
    }

    public ListenableFuture<Void> release() {
        return CallbackToFutureAdapter.getFuture(new CallbackToFutureAdapter.Resolver() { // from class: androidx.camera.video.internal.audio.AudioSource$$ExternalSyntheticLambda5
            @Override // androidx.concurrent.futures.CallbackToFutureAdapter.Resolver
            public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
                return this.f$0.m283xa561a489(completer);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$release$5$androidx-camera-video-internal-audio-AudioSource, reason: not valid java name */
    /* synthetic */ Object m283xa561a489(final CallbackToFutureAdapter.Completer completer) throws Exception {
        this.mExecutor.execute(new Runnable() { // from class: androidx.camera.video.internal.audio.AudioSource$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m282xb3b7fe6a(completer);
            }
        });
        return "AudioSource-release";
    }

    /* JADX INFO: renamed from: lambda$release$4$androidx-camera-video-internal-audio-AudioSource, reason: not valid java name */
    /* synthetic */ void m282xb3b7fe6a(CallbackToFutureAdapter.Completer completer) {
        try {
            int iOrdinal = this.mState.ordinal();
            if (iOrdinal == 0 || iOrdinal == 1) {
                resetBufferProvider(null);
                this.mSilentAudioStream.release();
                this.mAudioStream.release();
                stopSendingAudio();
                setState(InternalState.RELEASED);
            }
            completer.set(null);
        } catch (Throwable th) {
            completer.setException(th);
        }
    }

    public void setAudioSourceCallback(final Executor executor, final AudioSourceCallback audioSourceCallback) {
        this.mExecutor.execute(new Runnable() { // from class: androidx.camera.video.internal.audio.AudioSource$$ExternalSyntheticLambda10
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m284xcbe526c1(executor, audioSourceCallback);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$setAudioSourceCallback$6$androidx-camera-video-internal-audio-AudioSource, reason: not valid java name */
    /* synthetic */ void m284xcbe526c1(Executor executor, AudioSourceCallback audioSourceCallback) {
        int iOrdinal = this.mState.ordinal();
        if (iOrdinal == 0) {
            this.mCallbackExecutor = executor;
            this.mAudioSourceCallback = audioSourceCallback;
        } else if (iOrdinal == 1 || iOrdinal == 2) {
            throw new AssertionError("The audio recording callback must be registered before the audio source is started.");
        }
    }

    public void mute(final boolean z) {
        this.mExecutor.execute(new Runnable() { // from class: androidx.camera.video.internal.audio.AudioSource$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m280lambda$mute$7$androidxcameravideointernalaudioAudioSource(z);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$mute$7$androidx-camera-video-internal-audio-AudioSource, reason: not valid java name */
    /* synthetic */ void m280lambda$mute$7$androidxcameravideointernalaudioAudioSource(boolean z) {
        int iOrdinal = this.mState.ordinal();
        if (iOrdinal != 0 && iOrdinal != 1) {
            if (iOrdinal == 2) {
                throw new AssertionError("AudioSource is released");
            }
        } else {
            if (this.mMuted == z) {
                return;
            }
            this.mMuted = z;
            if (this.mState == InternalState.STARTED) {
                notifySilenced();
            }
        }
    }

    private void resetBufferProvider(final BufferProvider<? extends InputBuffer> bufferProvider) {
        BufferProvider<? extends InputBuffer> bufferProvider2 = this.mBufferProvider;
        if (bufferProvider2 != null) {
            bufferProvider2.removeObserver((Observable.Observer) Objects.requireNonNull(this.mStateObserver));
            this.mBufferProvider = null;
            this.mStateObserver = null;
            this.mAcquireBufferCallback = null;
            this.mBufferProviderState = BufferProvider.State.INACTIVE;
            updateSendingAudio();
        }
        if (bufferProvider != null) {
            this.mBufferProvider = bufferProvider;
            this.mStateObserver = new Observable.Observer<BufferProvider.State>() { // from class: androidx.camera.video.internal.audio.AudioSource.1
                @Override // androidx.camera.core.impl.Observable.Observer
                public void onNewData(BufferProvider.State state) {
                    Objects.requireNonNull(state);
                    if (AudioSource.this.mBufferProvider == bufferProvider) {
                        Logger.d(AudioSource.TAG, "Receive BufferProvider state change: " + AudioSource.this.mBufferProviderState + " to " + state);
                        if (AudioSource.this.mBufferProviderState != state) {
                            AudioSource.this.mBufferProviderState = state;
                            AudioSource.this.updateSendingAudio();
                        }
                    }
                }

                @Override // androidx.camera.core.impl.Observable.Observer
                public void onError(Throwable th) {
                    if (AudioSource.this.mBufferProvider == bufferProvider) {
                        AudioSource.this.notifyError(th);
                    }
                }
            };
            this.mAcquireBufferCallback = new FutureCallback<InputBuffer>() { // from class: androidx.camera.video.internal.audio.AudioSource.2
                @Override // androidx.camera.core.impl.utils.futures.FutureCallback
                public void onSuccess(InputBuffer inputBuffer) {
                    if (!AudioSource.this.mIsSendingAudio || AudioSource.this.mBufferProvider != bufferProvider) {
                        inputBuffer.cancel();
                        return;
                    }
                    if (AudioSource.this.mInSilentStartState && AudioSource.this.isStartRetryIntervalReached()) {
                        AudioSource.this.retryStartAudioStream();
                    }
                    AudioStream currentAudioStream = AudioSource.this.getCurrentAudioStream();
                    ByteBuffer byteBuffer = inputBuffer.getByteBuffer();
                    AudioStream.PacketInfo packetInfo = currentAudioStream.read(byteBuffer);
                    if (packetInfo.getSizeInBytes() > 0) {
                        if (AudioSource.this.mMuted) {
                            AudioSource.this.overrideBySilence(byteBuffer, packetInfo.getSizeInBytes());
                        }
                        if (AudioSource.this.mCallbackExecutor != null && packetInfo.getTimestampNs() - AudioSource.this.mAmplitudeTimestamp >= 200) {
                            AudioSource.this.mAmplitudeTimestamp = packetInfo.getTimestampNs();
                            AudioSource.this.postMaxAmplitude(byteBuffer);
                        }
                        byteBuffer.limit(byteBuffer.position() + packetInfo.getSizeInBytes());
                        inputBuffer.setPresentationTimeUs(TimeUnit.NANOSECONDS.toMicros(packetInfo.getTimestampNs()));
                        inputBuffer.submit();
                    } else {
                        Logger.w(AudioSource.TAG, "Unable to read data from AudioStream.");
                        inputBuffer.cancel();
                    }
                    AudioSource.this.sendNextAudio();
                }

                @Override // androidx.camera.core.impl.utils.futures.FutureCallback
                public void onFailure(Throwable th) {
                    if (AudioSource.this.mBufferProvider != bufferProvider) {
                        return;
                    }
                    Logger.d(AudioSource.TAG, "Unable to get input buffer, the BufferProvider could be transitioning to INACTIVE state.");
                    if (th instanceof IllegalStateException) {
                        return;
                    }
                    AudioSource.this.notifyError(th);
                }
            };
            BufferProvider.State stateFetchBufferProviderState = fetchBufferProviderState(bufferProvider);
            if (stateFetchBufferProviderState != null) {
                this.mBufferProviderState = stateFetchBufferProviderState;
                updateSendingAudio();
            }
            this.mBufferProvider.addObserver(this.mExecutor, this.mStateObserver);
        }
    }

    AudioStream getCurrentAudioStream() {
        return this.mInSilentStartState ? this.mSilentAudioStream : this.mAudioStream;
    }

    void retryStartAudioStream() {
        Preconditions.checkState(this.mInSilentStartState);
        try {
            this.mAudioStream.start();
            Logger.d(TAG, "Retry start AudioStream succeed");
            this.mSilentAudioStream.stop();
            this.mInSilentStartState = false;
        } catch (AudioStream.AudioStreamException e) {
            Logger.w(TAG, "Retry start AudioStream failed", e);
            this.mLatestFailedStartTimeNs = getCurrentSystemTimeNs();
        }
    }

    boolean isStartRetryIntervalReached() {
        Preconditions.checkState(this.mLatestFailedStartTimeNs > 0);
        return getCurrentSystemTimeNs() - this.mLatestFailedStartTimeNs >= this.mStartRetryIntervalNs;
    }

    void notifyError(final Throwable th) {
        Executor executor = this.mCallbackExecutor;
        final AudioSourceCallback audioSourceCallback = this.mAudioSourceCallback;
        if (executor == null || audioSourceCallback == null) {
            return;
        }
        executor.execute(new Runnable() { // from class: androidx.camera.video.internal.audio.AudioSource$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                audioSourceCallback.onError(th);
            }
        });
    }

    void notifySilenced() {
        Executor executor = this.mCallbackExecutor;
        final AudioSourceCallback audioSourceCallback = this.mAudioSourceCallback;
        if (executor == null || audioSourceCallback == null) {
            return;
        }
        final boolean z = this.mMuted || this.mInSilentStartState || this.mAudioStreamSilenced;
        if (Objects.equals(this.mNotifiedSilenceState.getAndSet(Boolean.valueOf(z)), Boolean.valueOf(z))) {
            return;
        }
        executor.execute(new Runnable() { // from class: androidx.camera.video.internal.audio.AudioSource$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                audioSourceCallback.onSilenceStateChanged(z);
            }
        });
    }

    void notifySuspended(final boolean z) {
        Executor executor = this.mCallbackExecutor;
        final AudioSourceCallback audioSourceCallback = this.mAudioSourceCallback;
        if (executor == null || audioSourceCallback == null || this.mNotifiedSuspendState.getAndSet(z) == z) {
            return;
        }
        executor.execute(new Runnable() { // from class: androidx.camera.video.internal.audio.AudioSource$$ExternalSyntheticLambda12
            @Override // java.lang.Runnable
            public final void run() {
                audioSourceCallback.onSuspendStateChanged(z);
            }
        });
    }

    void overrideBySilence(ByteBuffer byteBuffer, int i) {
        byte[] bArr = this.mZeroBytes;
        if (bArr == null || bArr.length < i) {
            this.mZeroBytes = new byte[i];
        }
        int iPosition = byteBuffer.position();
        byteBuffer.put(this.mZeroBytes, 0, i);
        byteBuffer.limit(byteBuffer.position()).position(iPosition);
    }

    void updateSendingAudio() {
        if (this.mState == InternalState.STARTED) {
            boolean z = this.mBufferProviderState == BufferProvider.State.ACTIVE;
            notifySuspended(!z);
            if (z) {
                startSendingAudio();
                return;
            } else {
                stopSendingAudio();
                return;
            }
        }
        stopSendingAudio();
    }

    private void startSendingAudio() {
        if (this.mIsSendingAudio) {
            return;
        }
        try {
            Logger.d(TAG, "startSendingAudio");
            this.mAudioStream.start();
            this.mInSilentStartState = false;
        } catch (AudioStream.AudioStreamException e) {
            Logger.w(TAG, "Failed to start AudioStream", e);
            this.mInSilentStartState = true;
            this.mSilentAudioStream.start();
            this.mLatestFailedStartTimeNs = getCurrentSystemTimeNs();
            notifySilenced();
        }
        this.mIsSendingAudio = true;
        sendNextAudio();
    }

    private void stopSendingAudio() {
        if (this.mIsSendingAudio) {
            this.mIsSendingAudio = false;
            Logger.d(TAG, "stopSendingAudio");
            this.mAudioStream.stop();
        }
    }

    void sendNextAudio() {
        Futures.addCallback(((BufferProvider) Objects.requireNonNull(this.mBufferProvider)).acquireBuffer(), (FutureCallback) Objects.requireNonNull(this.mAcquireBufferCallback), this.mExecutor);
    }

    void setState(InternalState internalState) {
        Logger.d(TAG, "Transitioning internal state: " + this.mState + " --> " + internalState);
        this.mState = internalState;
    }

    void postMaxAmplitude(ByteBuffer byteBuffer) {
        Executor executor = this.mCallbackExecutor;
        final AudioSourceCallback audioSourceCallback = this.mAudioSourceCallback;
        if (this.mAudioFormat == 2) {
            ShortBuffer shortBufferAsShortBuffer = byteBuffer.asShortBuffer();
            double dMax = AudioStats.AUDIO_AMPLITUDE_NONE;
            while (shortBufferAsShortBuffer.hasRemaining()) {
                dMax = Math.max(dMax, Math.abs((int) shortBufferAsShortBuffer.get()));
            }
            this.mAudioAmplitude = dMax / 32767.0d;
            if (executor == null || audioSourceCallback == null) {
                return;
            }
            executor.execute(new Runnable() { // from class: androidx.camera.video.internal.audio.AudioSource$$ExternalSyntheticLambda11
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m281xdf9dee1c(audioSourceCallback);
                }
            });
        }
    }

    /* JADX INFO: renamed from: lambda$postMaxAmplitude$11$androidx-camera-video-internal-audio-AudioSource, reason: not valid java name */
    /* synthetic */ void m281xdf9dee1c(AudioSourceCallback audioSourceCallback) {
        audioSourceCallback.onAmplitudeValue(this.mAudioAmplitude);
    }

    private static BufferProvider.State fetchBufferProviderState(BufferProvider<? extends InputBuffer> bufferProvider) {
        try {
            ListenableFuture<? extends InputBuffer> listenableFutureFetchData = bufferProvider.fetchData();
            if (listenableFutureFetchData.isDone()) {
                return (BufferProvider.State) listenableFutureFetchData.get();
            }
        } catch (InterruptedException | ExecutionException unused) {
        }
        return null;
    }

    public static boolean isSettingsSupported(int i, int i2, int i3) {
        return AudioStreamImpl.isSettingsSupported(i, i2, i3);
    }

    private static long getCurrentSystemTimeNs() {
        return System.nanoTime();
    }
}
