package androidx.camera.core.impl;

import androidx.camera.core.CameraFilter;
import java.util.Collections;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class CameraFilters {
    public static final CameraFilter ANY = new CameraFilter() { // from class: androidx.camera.core.impl.CameraFilters$$ExternalSyntheticLambda0
        @Override // androidx.camera.core.CameraFilter
        public final List filter(List list) {
            return CameraFilters.lambda$static$0(list);
        }

        @Override // androidx.camera.core.CameraFilter
        public /* synthetic */ Identifier getIdentifier() {
            return CameraFilter.DEFAULT_ID;
        }
    };
    public static final CameraFilter NONE = new CameraFilter() { // from class: androidx.camera.core.impl.CameraFilters$$ExternalSyntheticLambda1
        @Override // androidx.camera.core.CameraFilter
        public final List filter(List list) {
            return Collections.EMPTY_LIST;
        }

        @Override // androidx.camera.core.CameraFilter
        public /* synthetic */ Identifier getIdentifier() {
            return CameraFilter.DEFAULT_ID;
        }
    };

    static /* synthetic */ List lambda$static$0(List list) {
        return list;
    }

    private CameraFilters() {
    }
}
