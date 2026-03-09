package androidx.camera.core.impl;

import androidx.camera.core.DynamicRange;
import androidx.camera.core.impl.Config;
import androidx.core.util.Preconditions;

/* JADX INFO: loaded from: classes.dex */
public interface ImageInputConfig extends ReadableConfig {
    public static final Config.Option<Integer> OPTION_INPUT_FORMAT = Config.Option.create("camerax.core.imageInput.inputFormat", Integer.TYPE);
    public static final Config.Option<DynamicRange> OPTION_INPUT_DYNAMIC_RANGE = Config.Option.create("camerax.core.imageInput.inputDynamicRange", DynamicRange.class);

    public interface Builder<B> {
        B setDynamicRange(DynamicRange dynamicRange);
    }

    DynamicRange getDynamicRange();

    int getInputFormat();

    boolean hasDynamicRange();

    /* JADX INFO: renamed from: androidx.camera.core.impl.ImageInputConfig$-CC, reason: invalid class name */
    public final /* synthetic */ class CC {
        public static DynamicRange $default$getDynamicRange(ImageInputConfig _this) {
            return (DynamicRange) Preconditions.checkNotNull((DynamicRange) _this.retrieveOption(ImageInputConfig.OPTION_INPUT_DYNAMIC_RANGE, DynamicRange.UNSPECIFIED));
        }
    }
}
