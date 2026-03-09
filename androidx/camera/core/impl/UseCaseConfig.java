package androidx.camera.core.impl;

import android.util.Range;
import androidx.camera.core.ExtendableBuilder;
import androidx.camera.core.UseCase;
import androidx.camera.core.impl.CaptureConfig;
import androidx.camera.core.impl.Config;
import androidx.camera.core.impl.SessionConfig;
import androidx.camera.core.impl.UseCaseConfigFactory;
import androidx.camera.core.internal.TargetConfig;

/* JADX INFO: loaded from: classes.dex */
public interface UseCaseConfig<T extends UseCase> extends TargetConfig<T>, ImageInputConfig {
    public static final Config.Option<SessionConfig> OPTION_DEFAULT_SESSION_CONFIG = Config.Option.create("camerax.core.useCase.defaultSessionConfig", SessionConfig.class);
    public static final Config.Option<CaptureConfig> OPTION_DEFAULT_CAPTURE_CONFIG = Config.Option.create("camerax.core.useCase.defaultCaptureConfig", CaptureConfig.class);
    public static final Config.Option<SessionConfig.OptionUnpacker> OPTION_SESSION_CONFIG_UNPACKER = Config.Option.create("camerax.core.useCase.sessionConfigUnpacker", SessionConfig.OptionUnpacker.class);
    public static final Config.Option<CaptureConfig.OptionUnpacker> OPTION_CAPTURE_CONFIG_UNPACKER = Config.Option.create("camerax.core.useCase.captureConfigUnpacker", CaptureConfig.OptionUnpacker.class);
    public static final Config.Option<Integer> OPTION_SURFACE_OCCUPANCY_PRIORITY = Config.Option.create("camerax.core.useCase.surfaceOccupancyPriority", Integer.TYPE);
    public static final Config.Option<Range<Integer>> OPTION_TARGET_FRAME_RATE = Config.Option.create("camerax.core.useCase.targetFrameRate", Range.class);
    public static final Config.Option<Boolean> OPTION_ZSL_DISABLED = Config.Option.create("camerax.core.useCase.zslDisabled", Boolean.TYPE);
    public static final Config.Option<Boolean> OPTION_HIGH_RESOLUTION_DISABLED = Config.Option.create("camerax.core.useCase.highResolutionDisabled", Boolean.TYPE);
    public static final Config.Option<UseCaseConfigFactory.CaptureType> OPTION_CAPTURE_TYPE = Config.Option.create("camerax.core.useCase.captureType", UseCaseConfigFactory.CaptureType.class);
    public static final Config.Option<Integer> OPTION_PREVIEW_STABILIZATION_MODE = Config.Option.create("camerax.core.useCase.previewStabilizationMode", Integer.TYPE);
    public static final Config.Option<Integer> OPTION_VIDEO_STABILIZATION_MODE = Config.Option.create("camerax.core.useCase.videoStabilizationMode", Integer.TYPE);

    public interface Builder<T extends UseCase, C extends UseCaseConfig<T>, B> extends TargetConfig.Builder<T, B>, ExtendableBuilder<T> {
        C getUseCaseConfig();

        B setCaptureOptionUnpacker(CaptureConfig.OptionUnpacker optionUnpacker);

        B setCaptureType(UseCaseConfigFactory.CaptureType captureType);

        B setDefaultCaptureConfig(CaptureConfig captureConfig);

        B setDefaultSessionConfig(SessionConfig sessionConfig);

        B setHighResolutionDisabled(boolean z);

        B setSessionOptionUnpacker(SessionConfig.OptionUnpacker optionUnpacker);

        B setSurfaceOccupancyPriority(int i);

        B setZslDisabled(boolean z);
    }

    CaptureConfig.OptionUnpacker getCaptureOptionUnpacker();

    CaptureConfig.OptionUnpacker getCaptureOptionUnpacker(CaptureConfig.OptionUnpacker optionUnpacker);

    UseCaseConfigFactory.CaptureType getCaptureType();

    CaptureConfig getDefaultCaptureConfig();

    CaptureConfig getDefaultCaptureConfig(CaptureConfig captureConfig);

    SessionConfig getDefaultSessionConfig();

    SessionConfig getDefaultSessionConfig(SessionConfig sessionConfig);

    int getPreviewStabilizationMode();

    SessionConfig.OptionUnpacker getSessionOptionUnpacker();

    SessionConfig.OptionUnpacker getSessionOptionUnpacker(SessionConfig.OptionUnpacker optionUnpacker);

    int getSurfaceOccupancyPriority();

    int getSurfaceOccupancyPriority(int i);

    Range<Integer> getTargetFrameRate();

    Range<Integer> getTargetFrameRate(Range<Integer> range);

    int getVideoStabilizationMode();

    boolean isHighResolutionDisabled(boolean z);

    boolean isZslDisabled(boolean z);

    /* JADX INFO: renamed from: androidx.camera.core.impl.UseCaseConfig$-CC, reason: invalid class name */
    public final /* synthetic */ class CC {
        public static SessionConfig $default$getDefaultSessionConfig(UseCaseConfig _this, SessionConfig sessionConfig) {
            return (SessionConfig) _this.retrieveOption(UseCaseConfig.OPTION_DEFAULT_SESSION_CONFIG, sessionConfig);
        }

        public static SessionConfig $default$getDefaultSessionConfig(UseCaseConfig _this) {
            return (SessionConfig) _this.retrieveOption(UseCaseConfig.OPTION_DEFAULT_SESSION_CONFIG);
        }

        public static CaptureConfig $default$getDefaultCaptureConfig(UseCaseConfig _this, CaptureConfig captureConfig) {
            return (CaptureConfig) _this.retrieveOption(UseCaseConfig.OPTION_DEFAULT_CAPTURE_CONFIG, captureConfig);
        }

        public static CaptureConfig $default$getDefaultCaptureConfig(UseCaseConfig _this) {
            return (CaptureConfig) _this.retrieveOption(UseCaseConfig.OPTION_DEFAULT_CAPTURE_CONFIG);
        }

        public static SessionConfig.OptionUnpacker $default$getSessionOptionUnpacker(UseCaseConfig _this, SessionConfig.OptionUnpacker optionUnpacker) {
            return (SessionConfig.OptionUnpacker) _this.retrieveOption(UseCaseConfig.OPTION_SESSION_CONFIG_UNPACKER, optionUnpacker);
        }

        public static SessionConfig.OptionUnpacker $default$getSessionOptionUnpacker(UseCaseConfig _this) {
            return (SessionConfig.OptionUnpacker) _this.retrieveOption(UseCaseConfig.OPTION_SESSION_CONFIG_UNPACKER);
        }

        public static CaptureConfig.OptionUnpacker $default$getCaptureOptionUnpacker(UseCaseConfig _this, CaptureConfig.OptionUnpacker optionUnpacker) {
            return (CaptureConfig.OptionUnpacker) _this.retrieveOption(UseCaseConfig.OPTION_CAPTURE_CONFIG_UNPACKER, optionUnpacker);
        }

        public static CaptureConfig.OptionUnpacker $default$getCaptureOptionUnpacker(UseCaseConfig _this) {
            return (CaptureConfig.OptionUnpacker) _this.retrieveOption(UseCaseConfig.OPTION_CAPTURE_CONFIG_UNPACKER);
        }

        public static Range $default$getTargetFrameRate(UseCaseConfig _this, Range range) {
            return (Range) _this.retrieveOption(UseCaseConfig.OPTION_TARGET_FRAME_RATE, range);
        }

        public static Range $default$getTargetFrameRate(UseCaseConfig _this) {
            return (Range) _this.retrieveOption(UseCaseConfig.OPTION_TARGET_FRAME_RATE);
        }

        public static UseCaseConfigFactory.CaptureType $default$getCaptureType(UseCaseConfig _this) {
            return (UseCaseConfigFactory.CaptureType) _this.retrieveOption(UseCaseConfig.OPTION_CAPTURE_TYPE);
        }
    }
}
