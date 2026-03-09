package androidx.camera.view.internal;

import androidx.camera.core.ImageCapture;
import j$.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class ScreenFlashUiInfo {
    private final ProviderType mProviderType;
    private final ImageCapture.ScreenFlash mScreenFlash;

    public enum ProviderType {
        PREVIEW_VIEW,
        SCREEN_FLASH_VIEW
    }

    public ScreenFlashUiInfo(ProviderType providerType, ImageCapture.ScreenFlash screenFlash) {
        this.mProviderType = providerType;
        this.mScreenFlash = screenFlash;
    }

    public ProviderType getProviderType() {
        return this.mProviderType;
    }

    public ImageCapture.ScreenFlash getScreenFlash() {
        return this.mScreenFlash;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ScreenFlashUiInfo)) {
            return false;
        }
        ScreenFlashUiInfo screenFlashUiInfo = (ScreenFlashUiInfo) obj;
        return this.mProviderType == screenFlashUiInfo.mProviderType && Objects.equals(this.mScreenFlash, screenFlashUiInfo.mScreenFlash);
    }

    public int hashCode() {
        return Objects.hash(this.mProviderType, this.mScreenFlash);
    }
}
