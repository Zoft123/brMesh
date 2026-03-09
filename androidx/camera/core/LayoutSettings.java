package androidx.camera.core;

/* JADX INFO: loaded from: classes.dex */
public class LayoutSettings {
    public static final LayoutSettings DEFAULT = new Builder().setAlpha(1.0f).setOffsetX(0.0f).setOffsetY(0.0f).setWidth(1.0f).setHeight(1.0f).build();
    private final float mAlpha;
    private final float mHeight;
    private final float mOffsetX;
    private final float mOffsetY;
    private final float mWidth;

    private LayoutSettings(float f, float f2, float f3, float f4, float f5) {
        this.mAlpha = f;
        this.mOffsetX = f2;
        this.mOffsetY = f3;
        this.mWidth = f4;
        this.mHeight = f5;
    }

    public float getAlpha() {
        return this.mAlpha;
    }

    public float getOffsetX() {
        return this.mOffsetX;
    }

    public float getOffsetY() {
        return this.mOffsetY;
    }

    public float getWidth() {
        return this.mWidth;
    }

    public float getHeight() {
        return this.mHeight;
    }

    public static final class Builder {
        private float mAlpha = 1.0f;
        private float mOffsetX = 0.0f;
        private float mOffsetY = 0.0f;
        private float mWidth = 0.0f;
        private float mHeight = 0.0f;

        public Builder setAlpha(float f) {
            this.mAlpha = f;
            return this;
        }

        public Builder setOffsetX(float f) {
            this.mOffsetX = f;
            return this;
        }

        public Builder setOffsetY(float f) {
            this.mOffsetY = f;
            return this;
        }

        public Builder setWidth(float f) {
            this.mWidth = f;
            return this;
        }

        public Builder setHeight(float f) {
            this.mHeight = f;
            return this;
        }

        public LayoutSettings build() {
            return new LayoutSettings(this.mAlpha, this.mOffsetX, this.mOffsetY, this.mWidth, this.mHeight);
        }
    }
}
