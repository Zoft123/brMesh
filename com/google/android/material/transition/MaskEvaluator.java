package com.google.android.material.transition;

import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import androidx.core.util.Preconditions;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.shape.ShapeAppearancePathProvider;
import com.google.android.material.transition.MaterialContainerTransform;

/* JADX INFO: loaded from: classes.dex */
class MaskEvaluator {
    private final Path path = new Path();
    private final Path startPath = new Path();
    private final Path endPath = new Path();
    private final ShapeAppearancePathProvider pathProvider = new ShapeAppearancePathProvider();

    MaskEvaluator() {
    }

    void evaluate(float f, ShapeAppearanceModel shapeAppearanceModel, ShapeAppearanceModel shapeAppearanceModel2, RectF rectF, RectF rectF2, RectF rectF3, MaterialContainerTransform.ProgressThresholds progressThresholds) {
        ShapeAppearanceModel shapeAppearanceModelLerp = TransitionUtils.lerp(shapeAppearanceModel, shapeAppearanceModel2, rectF, rectF3, ((Float) Preconditions.checkNotNull(Float.valueOf(progressThresholds.start))).floatValue(), ((Float) Preconditions.checkNotNull(Float.valueOf(progressThresholds.end))).floatValue(), f);
        this.pathProvider.calculatePath(shapeAppearanceModelLerp, 1.0f, rectF2, this.startPath);
        this.pathProvider.calculatePath(shapeAppearanceModelLerp, 1.0f, rectF3, this.endPath);
        this.path.op(this.startPath, this.endPath, Path.Op.UNION);
    }

    void clip(Canvas canvas) {
        canvas.clipPath(this.path);
    }
}
