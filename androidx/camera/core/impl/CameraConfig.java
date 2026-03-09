package androidx.camera.core.impl;

import androidx.camera.core.impl.Config;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* JADX INFO: loaded from: classes.dex */
public interface CameraConfig extends ReadableConfig {
    public static final int REQUIRED_RULE_COEXISTING_PREVIEW_AND_IMAGE_CAPTURE = 1;
    public static final int REQUIRED_RULE_NONE = 0;
    public static final Config.Option<UseCaseConfigFactory> OPTION_USECASE_CONFIG_FACTORY = Config.Option.create("camerax.core.camera.useCaseConfigFactory", UseCaseConfigFactory.class);
    public static final Config.Option<Identifier> OPTION_COMPATIBILITY_ID = Config.Option.create("camerax.core.camera.compatibilityId", Identifier.class);
    public static final Config.Option<Integer> OPTION_USE_CASE_COMBINATION_REQUIRED_RULE = Config.Option.create("camerax.core.camera.useCaseCombinationRequiredRule", Integer.class);
    public static final Config.Option<SessionProcessor> OPTION_SESSION_PROCESSOR = Config.Option.create("camerax.core.camera.SessionProcessor", SessionProcessor.class);
    public static final Config.Option<Boolean> OPTION_ZSL_DISABLED = Config.Option.create("camerax.core.camera.isZslDisabled", Boolean.class);
    public static final Config.Option<Boolean> OPTION_POSTVIEW_SUPPORTED = Config.Option.create("camerax.core.camera.isPostviewSupported", Boolean.class);
    public static final Config.Option<Boolean> OPTION_CAPTURE_PROCESS_PROGRESS_SUPPORTED = Config.Option.create("camerax.core.camera.isCaptureProcessProgressSupported", Boolean.class);

    public interface Builder<B> {
        B setCaptureProcessProgressSupported(boolean z);

        B setCompatibilityId(Identifier identifier);

        B setPostviewSupported(boolean z);

        B setSessionProcessor(SessionProcessor sessionProcessor);

        B setUseCaseCombinationRequiredRule(int i);

        B setUseCaseConfigFactory(UseCaseConfigFactory useCaseConfigFactory);

        B setZslDisabled(boolean z);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface RequiredRule {
    }

    Identifier getCompatibilityId();

    SessionProcessor getSessionProcessor();

    SessionProcessor getSessionProcessor(SessionProcessor sessionProcessor);

    int getUseCaseCombinationRequiredRule();

    UseCaseConfigFactory getUseCaseConfigFactory();

    boolean isCaptureProcessProgressSupported();

    boolean isPostviewSupported();

    /* JADX INFO: renamed from: androidx.camera.core.impl.CameraConfig$-CC, reason: invalid class name */
    public final /* synthetic */ class CC {
        public static UseCaseConfigFactory $default$getUseCaseConfigFactory(CameraConfig _this) {
            return (UseCaseConfigFactory) _this.retrieveOption(CameraConfig.OPTION_USECASE_CONFIG_FACTORY, UseCaseConfigFactory.EMPTY_INSTANCE);
        }

        public static SessionProcessor $default$getSessionProcessor(CameraConfig _this, SessionProcessor sessionProcessor) {
            return (SessionProcessor) _this.retrieveOption(CameraConfig.OPTION_SESSION_PROCESSOR, sessionProcessor);
        }

        public static SessionProcessor $default$getSessionProcessor(CameraConfig _this) {
            return (SessionProcessor) _this.retrieveOption(CameraConfig.OPTION_SESSION_PROCESSOR);
        }
    }
}
