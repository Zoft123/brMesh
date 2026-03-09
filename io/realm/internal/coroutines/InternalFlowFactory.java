package io.realm.internal.coroutines;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.exifinterface.media.ExifInterface;
import com.alibaba.fastjson.asm.Opcodes;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.error.GattError;
import io.realm.CollectionUtils;
import io.realm.DynamicRealm;
import io.realm.DynamicRealmObject;
import io.realm.ObjectChangeSet;
import io.realm.OrderedCollectionChangeSet;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.coroutines.FlowFactory;
import io.realm.internal.coroutines.InternalFlowFactory;
import io.realm.rx.CollectionChange;
import io.realm.rx.ObjectChange;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.channels.SendChannel;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;

/* JADX INFO: compiled from: InternalFlowFactory.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u000f\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J$\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u00062\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\bH\u0016J6\u0010\u0005\u001a\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u000e0\r0\f0\u0006\"\u0004\b\u0000\u0010\u000e2\u0006\u0010\t\u001a\u00020\n2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u0002H\u000e0\rH\u0016J6\u0010\u0005\u001a\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u000e0\u00100\f0\u0006\"\u0004\b\u0000\u0010\u000e2\u0006\u0010\t\u001a\u00020\n2\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u0002H\u000e0\u0010H\u0016J3\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u000e0\u00070\u0006\"\b\b\u0000\u0010\u000e*\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u0002H\u000eH\u0016¢\u0006\u0002\u0010\u0016J6\u0010\u0005\u001a\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u000e0\r0\f0\u0006\"\u0004\b\u0000\u0010\u000e2\u0006\u0010\u0013\u001a\u00020\u00142\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u0002H\u000e0\rH\u0016J6\u0010\u0005\u001a\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u000e0\u00100\f0\u0006\"\u0004\b\u0000\u0010\u000e2\u0006\u0010\u0013\u001a\u00020\u00142\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u0002H\u000e0\u0010H\u0016J\u0016\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\n0\u00062\u0006\u0010\t\u001a\u00020\nH\u0016J\u001e\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\b0\u00062\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\bH\u0016J0\u0010\u0017\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u000e0\r0\u0006\"\u0004\b\u0000\u0010\u000e2\u0006\u0010\t\u001a\u00020\n2\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u0002H\u000e0\rH\u0016J0\u0010\u0017\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u000e0\u00100\u0006\"\u0004\b\u0000\u0010\u000e2\u0006\u0010\t\u001a\u00020\n2\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u0002H\u000e0\u0010H\u0016J\u0016\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00140\u00062\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J-\u0010\u0017\u001a\b\u0012\u0004\u0012\u0002H\u000e0\u0006\"\b\b\u0000\u0010\u000e*\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u0002H\u000eH\u0016¢\u0006\u0002\u0010\u0016J0\u0010\u0017\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u000e0\r0\u0006\"\u0004\b\u0000\u0010\u000e2\u0006\u0010\u0013\u001a\u00020\u00142\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u0002H\u000e0\rH\u0016J0\u0010\u0017\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u000e0\u00100\u0006\"\u0004\b\u0000\u0010\u000e2\u0006\u0010\u0013\u001a\u00020\u00142\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u0002H\u000e0\u0010H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0019"}, d2 = {"Lio/realm/internal/coroutines/InternalFlowFactory;", "Lio/realm/coroutines/FlowFactory;", "returnFrozenObjects", "", "(Z)V", "changesetFrom", "Lkotlinx/coroutines/flow/Flow;", "Lio/realm/rx/ObjectChange;", "Lio/realm/DynamicRealmObject;", "dynamicRealm", "Lio/realm/DynamicRealm;", "dynamicRealmObject", "Lio/realm/rx/CollectionChange;", "Lio/realm/RealmList;", ExifInterface.GPS_DIRECTION_TRUE, CollectionUtils.LIST_TYPE, "Lio/realm/RealmResults;", "results", "Lio/realm/RealmModel;", "realm", "Lio/realm/Realm;", "realmObject", "(Lio/realm/Realm;Lio/realm/RealmModel;)Lkotlinx/coroutines/flow/Flow;", TypedValues.TransitionType.S_FROM, "realmList", "realm-android-library_baseRelease"}, k = 1, mv = {1, 6, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
public final class InternalFlowFactory implements FlowFactory {
    private final boolean returnFrozenObjects;

    public InternalFlowFactory() {
        this(false, 1, null);
    }

    public InternalFlowFactory(boolean z) {
        this.returnFrozenObjects = z;
    }

    public /* synthetic */ InternalFlowFactory(boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? true : z);
    }

    @Override // io.realm.coroutines.FlowFactory
    public Flow<Realm> from(Realm realm) {
        Intrinsics.checkNotNullParameter(realm, "realm");
        if (realm.isFrozen()) {
            return FlowKt.flowOf(realm);
        }
        return FlowKt.callbackFlow(new C00431(realm, this, null));
    }

    /* JADX INFO: renamed from: io.realm.internal.coroutines.InternalFlowFactory$from$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: InternalFlowFactory.kt */
    @Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00030\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/channels/ProducerScope;", "Lio/realm/Realm;"}, k = 3, mv = {1, 6, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    @DebugMetadata(c = "io.realm.internal.coroutines.InternalFlowFactory$from$1", f = "InternalFlowFactory.kt", i = {}, l = {64}, m = "invokeSuspend", n = {}, s = {})
    static final class C00431 extends SuspendLambda implements Function2<ProducerScope<? super Realm>, Continuation<? super Unit>, Object> {
        final /* synthetic */ Realm $realm;
        private /* synthetic */ Object L$0;
        int label;
        final /* synthetic */ InternalFlowFactory this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C00431(Realm realm, InternalFlowFactory internalFlowFactory, Continuation<? super C00431> continuation) {
            super(2, continuation);
            this.$realm = realm;
            this.this$0 = internalFlowFactory;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            C00431 c00431 = new C00431(this.$realm, this.this$0, continuation);
            c00431.L$0 = obj;
            return c00431;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(ProducerScope<? super Realm> producerScope, Continuation<? super Unit> continuation) {
            return ((C00431) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                final ProducerScope producerScope = (ProducerScope) this.L$0;
                final Realm flowRealm = Realm.getInstance(this.$realm.getConfiguration());
                final InternalFlowFactory internalFlowFactory = this.this$0;
                final Realm realm = this.$realm;
                final RealmChangeListener<Realm> realmChangeListener = new RealmChangeListener() { // from class: io.realm.internal.coroutines.InternalFlowFactory$from$1$$ExternalSyntheticLambda0
                    @Override // io.realm.RealmChangeListener
                    public final void onChange(Object obj2) {
                        InternalFlowFactory.C00431.m1298invokeSuspend$lambda0(producerScope, internalFlowFactory, realm, (Realm) obj2);
                    }
                };
                flowRealm.addChangeListener(realmChangeListener);
                if (this.this$0.returnFrozenObjects) {
                    Realm realmFreeze = flowRealm.freeze();
                    Intrinsics.checkNotNullExpressionValue(realmFreeze, "flowRealm.freeze()");
                    producerScope.mo2903trySendJP2dKIU(realmFreeze);
                } else {
                    Intrinsics.checkNotNullExpressionValue(flowRealm, "flowRealm");
                    producerScope.mo2903trySendJP2dKIU(flowRealm);
                }
                this.label = 1;
                if (ProduceKt.awaitClose(producerScope, new Function0<Unit>() { // from class: io.realm.internal.coroutines.InternalFlowFactory.from.1.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public /* bridge */ /* synthetic */ Unit invoke() {
                        invoke2();
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2() {
                        flowRealm.removeChangeListener(realmChangeListener);
                        flowRealm.close();
                    }
                }, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX INFO: renamed from: invokeSuspend$lambda-0, reason: not valid java name */
        public static final void m1298invokeSuspend$lambda0(ProducerScope producerScope, InternalFlowFactory internalFlowFactory, Realm realm, Realm realm2) {
            if (CoroutineScopeKt.isActive(producerScope)) {
                if (internalFlowFactory.returnFrozenObjects) {
                    Realm realmFreeze = realm.freeze();
                    Intrinsics.checkNotNullExpressionValue(realmFreeze, "realm.freeze()");
                    producerScope.mo2903trySendJP2dKIU(realmFreeze);
                    return;
                }
                producerScope.mo2903trySendJP2dKIU(realm2);
            }
        }
    }

    @Override // io.realm.coroutines.FlowFactory
    public Flow<DynamicRealm> from(DynamicRealm dynamicRealm) {
        Intrinsics.checkNotNullParameter(dynamicRealm, "dynamicRealm");
        if (dynamicRealm.isFrozen()) {
            return FlowKt.flowOf(dynamicRealm);
        }
        return FlowKt.callbackFlow(new C00442(dynamicRealm, this, null));
    }

    /* JADX INFO: renamed from: io.realm.internal.coroutines.InternalFlowFactory$from$2, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: InternalFlowFactory.kt */
    @Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00030\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/channels/ProducerScope;", "Lio/realm/DynamicRealm;"}, k = 3, mv = {1, 6, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    @DebugMetadata(c = "io.realm.internal.coroutines.InternalFlowFactory$from$2", f = "InternalFlowFactory.kt", i = {}, l = {97}, m = "invokeSuspend", n = {}, s = {})
    static final class C00442 extends SuspendLambda implements Function2<ProducerScope<? super DynamicRealm>, Continuation<? super Unit>, Object> {
        final /* synthetic */ DynamicRealm $dynamicRealm;
        private /* synthetic */ Object L$0;
        int label;
        final /* synthetic */ InternalFlowFactory this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C00442(DynamicRealm dynamicRealm, InternalFlowFactory internalFlowFactory, Continuation<? super C00442> continuation) {
            super(2, continuation);
            this.$dynamicRealm = dynamicRealm;
            this.this$0 = internalFlowFactory;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            C00442 c00442 = new C00442(this.$dynamicRealm, this.this$0, continuation);
            c00442.L$0 = obj;
            return c00442;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(ProducerScope<? super DynamicRealm> producerScope, Continuation<? super Unit> continuation) {
            return ((C00442) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                final ProducerScope producerScope = (ProducerScope) this.L$0;
                final DynamicRealm flowRealm = DynamicRealm.getInstance(this.$dynamicRealm.getConfiguration());
                final InternalFlowFactory internalFlowFactory = this.this$0;
                final DynamicRealm dynamicRealm = this.$dynamicRealm;
                final RealmChangeListener<DynamicRealm> realmChangeListener = new RealmChangeListener() { // from class: io.realm.internal.coroutines.InternalFlowFactory$from$2$$ExternalSyntheticLambda0
                    @Override // io.realm.RealmChangeListener
                    public final void onChange(Object obj2) {
                        InternalFlowFactory.C00442.m1300invokeSuspend$lambda0(producerScope, internalFlowFactory, dynamicRealm, (DynamicRealm) obj2);
                    }
                };
                flowRealm.addChangeListener(realmChangeListener);
                if (this.this$0.returnFrozenObjects) {
                    DynamicRealm dynamicRealmFreeze = flowRealm.freeze();
                    Intrinsics.checkNotNullExpressionValue(dynamicRealmFreeze, "flowRealm.freeze()");
                    producerScope.mo2903trySendJP2dKIU(dynamicRealmFreeze);
                } else {
                    Intrinsics.checkNotNullExpressionValue(flowRealm, "flowRealm");
                    producerScope.mo2903trySendJP2dKIU(flowRealm);
                }
                this.label = 1;
                if (ProduceKt.awaitClose(producerScope, new Function0<Unit>() { // from class: io.realm.internal.coroutines.InternalFlowFactory.from.2.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public /* bridge */ /* synthetic */ Unit invoke() {
                        invoke2();
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2() {
                        flowRealm.removeChangeListener(realmChangeListener);
                        flowRealm.close();
                    }
                }, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX INFO: renamed from: invokeSuspend$lambda-0, reason: not valid java name */
        public static final void m1300invokeSuspend$lambda0(ProducerScope producerScope, InternalFlowFactory internalFlowFactory, DynamicRealm dynamicRealm, DynamicRealm dynamicRealm2) {
            if (CoroutineScopeKt.isActive(producerScope)) {
                if (internalFlowFactory.returnFrozenObjects) {
                    DynamicRealm dynamicRealmFreeze = dynamicRealm.freeze();
                    Intrinsics.checkNotNullExpressionValue(dynamicRealmFreeze, "dynamicRealm.freeze()");
                    producerScope.mo2903trySendJP2dKIU(dynamicRealmFreeze);
                    return;
                }
                producerScope.mo2903trySendJP2dKIU(dynamicRealm2);
            }
        }
    }

    @Override // io.realm.coroutines.FlowFactory
    public <T> Flow<RealmResults<T>> from(Realm realm, RealmResults<T> results) {
        Intrinsics.checkNotNullParameter(realm, "realm");
        Intrinsics.checkNotNullParameter(results, "results");
        if (realm.isFrozen()) {
            return FlowKt.flowOf(results);
        }
        return FlowKt.callbackFlow(new C00453(results, realm.getConfiguration(), this, null));
    }

    /* JADX INFO: Add missing generic type declarations: [T] */
    /* JADX INFO: renamed from: io.realm.internal.coroutines.InternalFlowFactory$from$3, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: InternalFlowFactory.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u00040\u0003H\u008a@"}, d2 = {"<anonymous>", "", ExifInterface.GPS_DIRECTION_TRUE, "Lkotlinx/coroutines/channels/ProducerScope;", "Lio/realm/RealmResults;"}, k = 3, mv = {1, 6, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    @DebugMetadata(c = "io.realm.internal.coroutines.InternalFlowFactory$from$3", f = "InternalFlowFactory.kt", i = {}, l = {116, GattError.GATT_NOT_ENCRYPTED}, m = "invokeSuspend", n = {}, s = {})
    static final class C00453<T> extends SuspendLambda implements Function2<ProducerScope<? super RealmResults<T>>, Continuation<? super Unit>, Object> {
        final /* synthetic */ RealmConfiguration $config;
        final /* synthetic */ RealmResults<T> $results;
        private /* synthetic */ Object L$0;
        int label;
        final /* synthetic */ InternalFlowFactory this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C00453(RealmResults<T> realmResults, RealmConfiguration realmConfiguration, InternalFlowFactory internalFlowFactory, Continuation<? super C00453> continuation) {
            super(2, continuation);
            this.$results = realmResults;
            this.$config = realmConfiguration;
            this.this$0 = internalFlowFactory;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            C00453 c00453 = new C00453(this.$results, this.$config, this.this$0, continuation);
            c00453.L$0 = obj;
            return c00453;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(ProducerScope<? super RealmResults<T>> producerScope, Continuation<? super Unit> continuation) {
            return ((C00453) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code restructure failed: missing block: B:13:0x003a, code lost:
        
            if (kotlinx.coroutines.channels.ProduceKt.awaitClose(r7, io.realm.internal.coroutines.InternalFlowFactory.C00453.AnonymousClass1.INSTANCE, r6) == r0) goto L23;
         */
        /* JADX WARN: Code restructure failed: missing block: B:22:0x0080, code lost:
        
            if (kotlinx.coroutines.channels.ProduceKt.awaitClose(r7, new io.realm.internal.coroutines.InternalFlowFactory.C00453.AnonymousClass2(), r6) == r0) goto L23;
         */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r7) throws java.lang.Throwable {
            /*
                r6 = this;
                java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                int r1 = r6.label
                r2 = 2
                r3 = 1
                if (r1 == 0) goto L1e
                if (r1 == r3) goto L1a
                if (r1 != r2) goto L12
                kotlin.ResultKt.throwOnFailure(r7)
                goto L83
            L12:
                java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
                java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
                r7.<init>(r0)
                throw r7
            L1a:
                kotlin.ResultKt.throwOnFailure(r7)
                goto L3d
            L1e:
                kotlin.ResultKt.throwOnFailure(r7)
                java.lang.Object r7 = r6.L$0
                kotlinx.coroutines.channels.ProducerScope r7 = (kotlinx.coroutines.channels.ProducerScope) r7
                io.realm.RealmResults<T> r1 = r6.$results
                boolean r1 = r1.isValid()
                if (r1 != 0) goto L40
                io.realm.internal.coroutines.InternalFlowFactory$from$3$1 r1 = new kotlin.jvm.functions.Function0<kotlin.Unit>() { // from class: io.realm.internal.coroutines.InternalFlowFactory.from.3.1
                    static {
                        /*
                            io.realm.internal.coroutines.InternalFlowFactory$from$3$1 r0 = new io.realm.internal.coroutines.InternalFlowFactory$from$3$1
                            r0.<init>()
                            
                            // error: 0x0005: SPUT (r0 I:io.realm.internal.coroutines.InternalFlowFactory$from$3$1) io.realm.internal.coroutines.InternalFlowFactory.from.3.1.INSTANCE io.realm.internal.coroutines.InternalFlowFactory$from$3$1
                            return
                        */
                        throw new UnsupportedOperationException("Method not decompiled: io.realm.internal.coroutines.InternalFlowFactory.C00453.AnonymousClass1.<clinit>():void");
                    }

                    {
                        /*
                            r1 = this;
                            r0 = 0
                            r1.<init>(r0)
                            return
                        */
                        throw new UnsupportedOperationException("Method not decompiled: io.realm.internal.coroutines.InternalFlowFactory.C00453.AnonymousClass1.<init>():void");
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2() {
                        /*
                            r0 = this;
                            return
                        */
                        throw new UnsupportedOperationException("Method not decompiled: io.realm.internal.coroutines.InternalFlowFactory.C00453.AnonymousClass1.invoke2():void");
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public /* bridge */ /* synthetic */ kotlin.Unit invoke() {
                        /*
                            r1 = this;
                            r1.invoke2()
                            kotlin.Unit r0 = kotlin.Unit.INSTANCE
                            return r0
                        */
                        throw new UnsupportedOperationException("Method not decompiled: io.realm.internal.coroutines.InternalFlowFactory.C00453.AnonymousClass1.invoke():java.lang.Object");
                    }
                }
                kotlin.jvm.functions.Function0 r1 = (kotlin.jvm.functions.Function0) r1
                r2 = r6
                kotlin.coroutines.Continuation r2 = (kotlin.coroutines.Continuation) r2
                r6.label = r3
                java.lang.Object r7 = kotlinx.coroutines.channels.ProduceKt.awaitClose(r7, r1, r2)
                if (r7 != r0) goto L3d
                goto L82
            L3d:
                kotlin.Unit r7 = kotlin.Unit.INSTANCE
                return r7
            L40:
                io.realm.RealmConfiguration r1 = r6.$config
                io.realm.Realm r1 = io.realm.Realm.getInstance(r1)
                io.realm.internal.coroutines.InternalFlowFactory r3 = r6.this$0
                io.realm.internal.coroutines.InternalFlowFactory$from$3$$ExternalSyntheticLambda0 r4 = new io.realm.internal.coroutines.InternalFlowFactory$from$3$$ExternalSyntheticLambda0
                r4.<init>()
                io.realm.RealmResults<T> r3 = r6.$results
                r3.addChangeListener(r4)
                io.realm.internal.coroutines.InternalFlowFactory r3 = r6.this$0
                boolean r3 = io.realm.internal.coroutines.InternalFlowFactory.access$getReturnFrozenObjects$p(r3)
                if (r3 == 0) goto L69
                io.realm.RealmResults<T> r3 = r6.$results
                io.realm.RealmResults r3 = r3.freeze()
                java.lang.String r5 = "results.freeze()"
                kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r3, r5)
                r7.mo2903trySendJP2dKIU(r3)
                goto L6e
            L69:
                io.realm.RealmResults<T> r3 = r6.$results
                r7.mo2903trySendJP2dKIU(r3)
            L6e:
                io.realm.internal.coroutines.InternalFlowFactory$from$3$2 r3 = new io.realm.internal.coroutines.InternalFlowFactory$from$3$2
                io.realm.RealmResults<T> r5 = r6.$results
                r3.<init>()
                kotlin.jvm.functions.Function0 r3 = (kotlin.jvm.functions.Function0) r3
                r1 = r6
                kotlin.coroutines.Continuation r1 = (kotlin.coroutines.Continuation) r1
                r6.label = r2
                java.lang.Object r7 = kotlinx.coroutines.channels.ProduceKt.awaitClose(r7, r3, r1)
                if (r7 != r0) goto L83
            L82:
                return r0
            L83:
                kotlin.Unit r7 = kotlin.Unit.INSTANCE
                return r7
            */
            throw new UnsupportedOperationException("Method not decompiled: io.realm.internal.coroutines.InternalFlowFactory.C00453.invokeSuspend(java.lang.Object):java.lang.Object");
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX INFO: renamed from: invokeSuspend$lambda-0, reason: not valid java name */
        public static final void m1301invokeSuspend$lambda0(ProducerScope producerScope, InternalFlowFactory internalFlowFactory, RealmResults realmResults) {
            if (CoroutineScopeKt.isActive(producerScope)) {
                if (internalFlowFactory.returnFrozenObjects) {
                    RealmResults realmResultsFreeze = realmResults.freeze();
                    Intrinsics.checkNotNullExpressionValue(realmResultsFreeze, "listenerResults.freeze()");
                    producerScope.mo2903trySendJP2dKIU(realmResultsFreeze);
                    return;
                }
                producerScope.mo2903trySendJP2dKIU(realmResults);
            }
        }
    }

    @Override // io.realm.coroutines.FlowFactory
    public <T> Flow<CollectionChange<RealmResults<T>>> changesetFrom(Realm realm, RealmResults<T> results) {
        Intrinsics.checkNotNullParameter(realm, "realm");
        Intrinsics.checkNotNullParameter(results, "results");
        if (realm.isFrozen()) {
            return FlowKt.flowOf(new CollectionChange(results, null));
        }
        return FlowKt.callbackFlow(new AnonymousClass1(results, realm.getConfiguration(), this, null));
    }

    /* JADX INFO: Add missing generic type declarations: [T] */
    /* JADX INFO: renamed from: io.realm.internal.coroutines.InternalFlowFactory$changesetFrom$1, reason: invalid class name */
    /* JADX INFO: compiled from: InternalFlowFactory.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u00050\u00040\u0003H\u008a@"}, d2 = {"<anonymous>", "", ExifInterface.GPS_DIRECTION_TRUE, "Lkotlinx/coroutines/channels/ProducerScope;", "Lio/realm/rx/CollectionChange;", "Lio/realm/RealmResults;"}, k = 3, mv = {1, 6, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    @DebugMetadata(c = "io.realm.internal.coroutines.InternalFlowFactory$changesetFrom$1", f = "InternalFlowFactory.kt", i = {}, l = {Opcodes.IF_ACMPNE, Opcodes.CHECKCAST}, m = "invokeSuspend", n = {}, s = {})
    static final class AnonymousClass1<T> extends SuspendLambda implements Function2<ProducerScope<? super CollectionChange<RealmResults<T>>>, Continuation<? super Unit>, Object> {
        final /* synthetic */ RealmConfiguration $config;
        final /* synthetic */ RealmResults<T> $results;
        private /* synthetic */ Object L$0;
        int label;
        final /* synthetic */ InternalFlowFactory this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(RealmResults<T> realmResults, RealmConfiguration realmConfiguration, InternalFlowFactory internalFlowFactory, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.$results = realmResults;
            this.$config = realmConfiguration;
            this.this$0 = internalFlowFactory;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$results, this.$config, this.this$0, continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(ProducerScope<? super CollectionChange<RealmResults<T>>> producerScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code restructure failed: missing block: B:13:0x003b, code lost:
        
            if (kotlinx.coroutines.channels.ProduceKt.awaitClose(r8, io.realm.internal.coroutines.InternalFlowFactory.AnonymousClass1.C00131.INSTANCE, r7) == r0) goto L23;
         */
        /* JADX WARN: Code restructure failed: missing block: B:22:0x008b, code lost:
        
            if (kotlinx.coroutines.channels.ProduceKt.awaitClose(r8, new io.realm.internal.coroutines.InternalFlowFactory.AnonymousClass1.AnonymousClass2(), r7) == r0) goto L23;
         */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r8) throws java.lang.Throwable {
            /*
                r7 = this;
                java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                int r1 = r7.label
                r2 = 2
                r3 = 1
                if (r1 == 0) goto L1f
                if (r1 == r3) goto L1b
                if (r1 != r2) goto L13
                kotlin.ResultKt.throwOnFailure(r8)
                goto L8e
            L13:
                java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
                java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
                r8.<init>(r0)
                throw r8
            L1b:
                kotlin.ResultKt.throwOnFailure(r8)
                goto L3e
            L1f:
                kotlin.ResultKt.throwOnFailure(r8)
                java.lang.Object r8 = r7.L$0
                kotlinx.coroutines.channels.ProducerScope r8 = (kotlinx.coroutines.channels.ProducerScope) r8
                io.realm.RealmResults<T> r1 = r7.$results
                boolean r1 = r1.isValid()
                if (r1 != 0) goto L41
                io.realm.internal.coroutines.InternalFlowFactory$changesetFrom$1$1 r1 = new kotlin.jvm.functions.Function0<kotlin.Unit>() { // from class: io.realm.internal.coroutines.InternalFlowFactory.changesetFrom.1.1
                    static {
                        /*
                            io.realm.internal.coroutines.InternalFlowFactory$changesetFrom$1$1 r0 = new io.realm.internal.coroutines.InternalFlowFactory$changesetFrom$1$1
                            r0.<init>()
                            
                            // error: 0x0005: SPUT (r0 I:io.realm.internal.coroutines.InternalFlowFactory$changesetFrom$1$1) io.realm.internal.coroutines.InternalFlowFactory.changesetFrom.1.1.INSTANCE io.realm.internal.coroutines.InternalFlowFactory$changesetFrom$1$1
                            return
                        */
                        throw new UnsupportedOperationException("Method not decompiled: io.realm.internal.coroutines.InternalFlowFactory.AnonymousClass1.C00131.<clinit>():void");
                    }

                    {
                        /*
                            r1 = this;
                            r0 = 0
                            r1.<init>(r0)
                            return
                        */
                        throw new UnsupportedOperationException("Method not decompiled: io.realm.internal.coroutines.InternalFlowFactory.AnonymousClass1.C00131.<init>():void");
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2() {
                        /*
                            r0 = this;
                            return
                        */
                        throw new UnsupportedOperationException("Method not decompiled: io.realm.internal.coroutines.InternalFlowFactory.AnonymousClass1.C00131.invoke2():void");
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public /* bridge */ /* synthetic */ kotlin.Unit invoke() {
                        /*
                            r1 = this;
                            r1.invoke2()
                            kotlin.Unit r0 = kotlin.Unit.INSTANCE
                            return r0
                        */
                        throw new UnsupportedOperationException("Method not decompiled: io.realm.internal.coroutines.InternalFlowFactory.AnonymousClass1.C00131.invoke():java.lang.Object");
                    }
                }
                kotlin.jvm.functions.Function0 r1 = (kotlin.jvm.functions.Function0) r1
                r2 = r7
                kotlin.coroutines.Continuation r2 = (kotlin.coroutines.Continuation) r2
                r7.label = r3
                java.lang.Object r8 = kotlinx.coroutines.channels.ProduceKt.awaitClose(r8, r1, r2)
                if (r8 != r0) goto L3e
                goto L8d
            L3e:
                kotlin.Unit r8 = kotlin.Unit.INSTANCE
                return r8
            L41:
                io.realm.RealmConfiguration r1 = r7.$config
                io.realm.Realm r1 = io.realm.Realm.getInstance(r1)
                io.realm.internal.coroutines.InternalFlowFactory r3 = r7.this$0
                io.realm.internal.coroutines.InternalFlowFactory$changesetFrom$1$$ExternalSyntheticLambda0 r4 = new io.realm.internal.coroutines.InternalFlowFactory$changesetFrom$1$$ExternalSyntheticLambda0
                r4.<init>()
                io.realm.RealmResults<T> r3 = r7.$results
                r3.addChangeListener(r4)
                io.realm.internal.coroutines.InternalFlowFactory r3 = r7.this$0
                boolean r3 = io.realm.internal.coroutines.InternalFlowFactory.access$getReturnFrozenObjects$p(r3)
                r5 = 0
                if (r3 == 0) goto L6d
                io.realm.rx.CollectionChange r3 = new io.realm.rx.CollectionChange
                io.realm.RealmResults<T> r6 = r7.$results
                io.realm.RealmResults r6 = r6.freeze()
                io.realm.OrderedRealmCollection r6 = (io.realm.OrderedRealmCollection) r6
                r3.<init>(r6, r5)
                r8.mo2903trySendJP2dKIU(r3)
                goto L79
            L6d:
                io.realm.rx.CollectionChange r3 = new io.realm.rx.CollectionChange
                io.realm.RealmResults<T> r6 = r7.$results
                io.realm.OrderedRealmCollection r6 = (io.realm.OrderedRealmCollection) r6
                r3.<init>(r6, r5)
                r8.mo2903trySendJP2dKIU(r3)
            L79:
                io.realm.internal.coroutines.InternalFlowFactory$changesetFrom$1$2 r3 = new io.realm.internal.coroutines.InternalFlowFactory$changesetFrom$1$2
                io.realm.RealmResults<T> r5 = r7.$results
                r3.<init>()
                kotlin.jvm.functions.Function0 r3 = (kotlin.jvm.functions.Function0) r3
                r1 = r7
                kotlin.coroutines.Continuation r1 = (kotlin.coroutines.Continuation) r1
                r7.label = r2
                java.lang.Object r8 = kotlinx.coroutines.channels.ProduceKt.awaitClose(r8, r3, r1)
                if (r8 != r0) goto L8e
            L8d:
                return r0
            L8e:
                kotlin.Unit r8 = kotlin.Unit.INSTANCE
                return r8
            */
            throw new UnsupportedOperationException("Method not decompiled: io.realm.internal.coroutines.InternalFlowFactory.AnonymousClass1.invokeSuspend(java.lang.Object):java.lang.Object");
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX INFO: renamed from: invokeSuspend$lambda-0, reason: not valid java name */
        public static final void m1290invokeSuspend$lambda0(ProducerScope producerScope, InternalFlowFactory internalFlowFactory, RealmResults realmResults, OrderedCollectionChangeSet orderedCollectionChangeSet) {
            if (CoroutineScopeKt.isActive(producerScope)) {
                if (internalFlowFactory.returnFrozenObjects) {
                    producerScope.mo2903trySendJP2dKIU(new CollectionChange(realmResults.freeze(), orderedCollectionChangeSet));
                } else {
                    producerScope.mo2903trySendJP2dKIU(new CollectionChange(realmResults, orderedCollectionChangeSet));
                }
            }
        }
    }

    @Override // io.realm.coroutines.FlowFactory
    public <T> Flow<RealmResults<T>> from(DynamicRealm dynamicRealm, RealmResults<T> results) {
        Intrinsics.checkNotNullParameter(dynamicRealm, "dynamicRealm");
        Intrinsics.checkNotNullParameter(results, "results");
        if (dynamicRealm.isFrozen()) {
            return FlowKt.flowOf(results);
        }
        return FlowKt.callbackFlow(new C00464(results, dynamicRealm.getConfiguration(), this, null));
    }

    /* JADX INFO: Add missing generic type declarations: [T] */
    /* JADX INFO: renamed from: io.realm.internal.coroutines.InternalFlowFactory$from$4, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: InternalFlowFactory.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u00040\u0003H\u008a@"}, d2 = {"<anonymous>", "", ExifInterface.GPS_DIRECTION_TRUE, "Lkotlinx/coroutines/channels/ProducerScope;", "Lio/realm/RealmResults;"}, k = 3, mv = {1, 6, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    @DebugMetadata(c = "io.realm.internal.coroutines.InternalFlowFactory$from$4", f = "InternalFlowFactory.kt", i = {}, l = {216, 242}, m = "invokeSuspend", n = {}, s = {})
    static final class C00464<T> extends SuspendLambda implements Function2<ProducerScope<? super RealmResults<T>>, Continuation<? super Unit>, Object> {
        final /* synthetic */ RealmConfiguration $config;
        final /* synthetic */ RealmResults<T> $results;
        private /* synthetic */ Object L$0;
        int label;
        final /* synthetic */ InternalFlowFactory this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C00464(RealmResults<T> realmResults, RealmConfiguration realmConfiguration, InternalFlowFactory internalFlowFactory, Continuation<? super C00464> continuation) {
            super(2, continuation);
            this.$results = realmResults;
            this.$config = realmConfiguration;
            this.this$0 = internalFlowFactory;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            C00464 c00464 = new C00464(this.$results, this.$config, this.this$0, continuation);
            c00464.L$0 = obj;
            return c00464;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(ProducerScope<? super RealmResults<T>> producerScope, Continuation<? super Unit> continuation) {
            return ((C00464) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code restructure failed: missing block: B:13:0x003a, code lost:
        
            if (kotlinx.coroutines.channels.ProduceKt.awaitClose(r7, io.realm.internal.coroutines.InternalFlowFactory.C00464.AnonymousClass1.INSTANCE, r6) == r0) goto L23;
         */
        /* JADX WARN: Code restructure failed: missing block: B:22:0x0080, code lost:
        
            if (kotlinx.coroutines.channels.ProduceKt.awaitClose(r7, new io.realm.internal.coroutines.InternalFlowFactory.C00464.AnonymousClass2(), r6) == r0) goto L23;
         */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r7) throws java.lang.Throwable {
            /*
                r6 = this;
                java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                int r1 = r6.label
                r2 = 2
                r3 = 1
                if (r1 == 0) goto L1e
                if (r1 == r3) goto L1a
                if (r1 != r2) goto L12
                kotlin.ResultKt.throwOnFailure(r7)
                goto L83
            L12:
                java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
                java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
                r7.<init>(r0)
                throw r7
            L1a:
                kotlin.ResultKt.throwOnFailure(r7)
                goto L3d
            L1e:
                kotlin.ResultKt.throwOnFailure(r7)
                java.lang.Object r7 = r6.L$0
                kotlinx.coroutines.channels.ProducerScope r7 = (kotlinx.coroutines.channels.ProducerScope) r7
                io.realm.RealmResults<T> r1 = r6.$results
                boolean r1 = r1.isValid()
                if (r1 != 0) goto L40
                io.realm.internal.coroutines.InternalFlowFactory$from$4$1 r1 = new kotlin.jvm.functions.Function0<kotlin.Unit>() { // from class: io.realm.internal.coroutines.InternalFlowFactory.from.4.1
                    static {
                        /*
                            io.realm.internal.coroutines.InternalFlowFactory$from$4$1 r0 = new io.realm.internal.coroutines.InternalFlowFactory$from$4$1
                            r0.<init>()
                            
                            // error: 0x0005: SPUT (r0 I:io.realm.internal.coroutines.InternalFlowFactory$from$4$1) io.realm.internal.coroutines.InternalFlowFactory.from.4.1.INSTANCE io.realm.internal.coroutines.InternalFlowFactory$from$4$1
                            return
                        */
                        throw new UnsupportedOperationException("Method not decompiled: io.realm.internal.coroutines.InternalFlowFactory.C00464.AnonymousClass1.<clinit>():void");
                    }

                    {
                        /*
                            r1 = this;
                            r0 = 0
                            r1.<init>(r0)
                            return
                        */
                        throw new UnsupportedOperationException("Method not decompiled: io.realm.internal.coroutines.InternalFlowFactory.C00464.AnonymousClass1.<init>():void");
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2() {
                        /*
                            r0 = this;
                            return
                        */
                        throw new UnsupportedOperationException("Method not decompiled: io.realm.internal.coroutines.InternalFlowFactory.C00464.AnonymousClass1.invoke2():void");
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public /* bridge */ /* synthetic */ kotlin.Unit invoke() {
                        /*
                            r1 = this;
                            r1.invoke2()
                            kotlin.Unit r0 = kotlin.Unit.INSTANCE
                            return r0
                        */
                        throw new UnsupportedOperationException("Method not decompiled: io.realm.internal.coroutines.InternalFlowFactory.C00464.AnonymousClass1.invoke():java.lang.Object");
                    }
                }
                kotlin.jvm.functions.Function0 r1 = (kotlin.jvm.functions.Function0) r1
                r2 = r6
                kotlin.coroutines.Continuation r2 = (kotlin.coroutines.Continuation) r2
                r6.label = r3
                java.lang.Object r7 = kotlinx.coroutines.channels.ProduceKt.awaitClose(r7, r1, r2)
                if (r7 != r0) goto L3d
                goto L82
            L3d:
                kotlin.Unit r7 = kotlin.Unit.INSTANCE
                return r7
            L40:
                io.realm.RealmConfiguration r1 = r6.$config
                io.realm.DynamicRealm r1 = io.realm.DynamicRealm.getInstance(r1)
                io.realm.internal.coroutines.InternalFlowFactory r3 = r6.this$0
                io.realm.internal.coroutines.InternalFlowFactory$from$4$$ExternalSyntheticLambda0 r4 = new io.realm.internal.coroutines.InternalFlowFactory$from$4$$ExternalSyntheticLambda0
                r4.<init>()
                io.realm.RealmResults<T> r3 = r6.$results
                r3.addChangeListener(r4)
                io.realm.internal.coroutines.InternalFlowFactory r3 = r6.this$0
                boolean r3 = io.realm.internal.coroutines.InternalFlowFactory.access$getReturnFrozenObjects$p(r3)
                if (r3 == 0) goto L69
                io.realm.RealmResults<T> r3 = r6.$results
                io.realm.RealmResults r3 = r3.freeze()
                java.lang.String r5 = "results.freeze()"
                kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r3, r5)
                r7.mo2903trySendJP2dKIU(r3)
                goto L6e
            L69:
                io.realm.RealmResults<T> r3 = r6.$results
                r7.mo2903trySendJP2dKIU(r3)
            L6e:
                io.realm.internal.coroutines.InternalFlowFactory$from$4$2 r3 = new io.realm.internal.coroutines.InternalFlowFactory$from$4$2
                io.realm.RealmResults<T> r5 = r6.$results
                r3.<init>()
                kotlin.jvm.functions.Function0 r3 = (kotlin.jvm.functions.Function0) r3
                r1 = r6
                kotlin.coroutines.Continuation r1 = (kotlin.coroutines.Continuation) r1
                r6.label = r2
                java.lang.Object r7 = kotlinx.coroutines.channels.ProduceKt.awaitClose(r7, r3, r1)
                if (r7 != r0) goto L83
            L82:
                return r0
            L83:
                kotlin.Unit r7 = kotlin.Unit.INSTANCE
                return r7
            */
            throw new UnsupportedOperationException("Method not decompiled: io.realm.internal.coroutines.InternalFlowFactory.C00464.invokeSuspend(java.lang.Object):java.lang.Object");
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX INFO: renamed from: invokeSuspend$lambda-0, reason: not valid java name */
        public static final void m1302invokeSuspend$lambda0(ProducerScope producerScope, InternalFlowFactory internalFlowFactory, RealmResults realmResults) {
            if (CoroutineScopeKt.isActive(producerScope)) {
                if (internalFlowFactory.returnFrozenObjects) {
                    RealmResults realmResultsFreeze = realmResults.freeze();
                    Intrinsics.checkNotNullExpressionValue(realmResultsFreeze, "listenerResults.freeze()");
                    producerScope.mo2903trySendJP2dKIU(realmResultsFreeze);
                    return;
                }
                producerScope.mo2903trySendJP2dKIU(realmResults);
            }
        }
    }

    @Override // io.realm.coroutines.FlowFactory
    public <T> Flow<CollectionChange<RealmResults<T>>> changesetFrom(DynamicRealm dynamicRealm, RealmResults<T> results) {
        Intrinsics.checkNotNullParameter(dynamicRealm, "dynamicRealm");
        Intrinsics.checkNotNullParameter(results, "results");
        if (dynamicRealm.isFrozen()) {
            return FlowKt.flowOf(new CollectionChange(results, null));
        }
        return FlowKt.callbackFlow(new AnonymousClass2(results, dynamicRealm.getConfiguration(), this, null));
    }

    /* JADX INFO: Add missing generic type declarations: [T] */
    /* JADX INFO: renamed from: io.realm.internal.coroutines.InternalFlowFactory$changesetFrom$2, reason: invalid class name */
    /* JADX INFO: compiled from: InternalFlowFactory.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u00050\u00040\u0003H\u008a@"}, d2 = {"<anonymous>", "", ExifInterface.GPS_DIRECTION_TRUE, "Lkotlinx/coroutines/channels/ProducerScope;", "Lio/realm/rx/CollectionChange;", "Lio/realm/RealmResults;"}, k = 3, mv = {1, 6, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    @DebugMetadata(c = "io.realm.internal.coroutines.InternalFlowFactory$changesetFrom$2", f = "InternalFlowFactory.kt", i = {}, l = {266, 292}, m = "invokeSuspend", n = {}, s = {})
    static final class AnonymousClass2<T> extends SuspendLambda implements Function2<ProducerScope<? super CollectionChange<RealmResults<T>>>, Continuation<? super Unit>, Object> {
        final /* synthetic */ RealmConfiguration $config;
        final /* synthetic */ RealmResults<T> $results;
        private /* synthetic */ Object L$0;
        int label;
        final /* synthetic */ InternalFlowFactory this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass2(RealmResults<T> realmResults, RealmConfiguration realmConfiguration, InternalFlowFactory internalFlowFactory, Continuation<? super AnonymousClass2> continuation) {
            super(2, continuation);
            this.$results = realmResults;
            this.$config = realmConfiguration;
            this.this$0 = internalFlowFactory;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.$results, this.$config, this.this$0, continuation);
            anonymousClass2.L$0 = obj;
            return anonymousClass2;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(ProducerScope<? super CollectionChange<RealmResults<T>>> producerScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass2) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code restructure failed: missing block: B:13:0x003b, code lost:
        
            if (kotlinx.coroutines.channels.ProduceKt.awaitClose(r8, io.realm.internal.coroutines.InternalFlowFactory.AnonymousClass2.AnonymousClass1.INSTANCE, r7) == r0) goto L23;
         */
        /* JADX WARN: Code restructure failed: missing block: B:22:0x008b, code lost:
        
            if (kotlinx.coroutines.channels.ProduceKt.awaitClose(r8, new io.realm.internal.coroutines.InternalFlowFactory.AnonymousClass2.C00142(), r7) == r0) goto L23;
         */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r8) throws java.lang.Throwable {
            /*
                r7 = this;
                java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                int r1 = r7.label
                r2 = 2
                r3 = 1
                if (r1 == 0) goto L1f
                if (r1 == r3) goto L1b
                if (r1 != r2) goto L13
                kotlin.ResultKt.throwOnFailure(r8)
                goto L8e
            L13:
                java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
                java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
                r8.<init>(r0)
                throw r8
            L1b:
                kotlin.ResultKt.throwOnFailure(r8)
                goto L3e
            L1f:
                kotlin.ResultKt.throwOnFailure(r8)
                java.lang.Object r8 = r7.L$0
                kotlinx.coroutines.channels.ProducerScope r8 = (kotlinx.coroutines.channels.ProducerScope) r8
                io.realm.RealmResults<T> r1 = r7.$results
                boolean r1 = r1.isValid()
                if (r1 != 0) goto L41
                io.realm.internal.coroutines.InternalFlowFactory$changesetFrom$2$1 r1 = new kotlin.jvm.functions.Function0<kotlin.Unit>() { // from class: io.realm.internal.coroutines.InternalFlowFactory.changesetFrom.2.1
                    static {
                        /*
                            io.realm.internal.coroutines.InternalFlowFactory$changesetFrom$2$1 r0 = new io.realm.internal.coroutines.InternalFlowFactory$changesetFrom$2$1
                            r0.<init>()
                            
                            // error: 0x0005: SPUT (r0 I:io.realm.internal.coroutines.InternalFlowFactory$changesetFrom$2$1) io.realm.internal.coroutines.InternalFlowFactory.changesetFrom.2.1.INSTANCE io.realm.internal.coroutines.InternalFlowFactory$changesetFrom$2$1
                            return
                        */
                        throw new UnsupportedOperationException("Method not decompiled: io.realm.internal.coroutines.InternalFlowFactory.AnonymousClass2.AnonymousClass1.<clinit>():void");
                    }

                    {
                        /*
                            r1 = this;
                            r0 = 0
                            r1.<init>(r0)
                            return
                        */
                        throw new UnsupportedOperationException("Method not decompiled: io.realm.internal.coroutines.InternalFlowFactory.AnonymousClass2.AnonymousClass1.<init>():void");
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2() {
                        /*
                            r0 = this;
                            return
                        */
                        throw new UnsupportedOperationException("Method not decompiled: io.realm.internal.coroutines.InternalFlowFactory.AnonymousClass2.AnonymousClass1.invoke2():void");
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public /* bridge */ /* synthetic */ kotlin.Unit invoke() {
                        /*
                            r1 = this;
                            r1.invoke2()
                            kotlin.Unit r0 = kotlin.Unit.INSTANCE
                            return r0
                        */
                        throw new UnsupportedOperationException("Method not decompiled: io.realm.internal.coroutines.InternalFlowFactory.AnonymousClass2.AnonymousClass1.invoke():java.lang.Object");
                    }
                }
                kotlin.jvm.functions.Function0 r1 = (kotlin.jvm.functions.Function0) r1
                r2 = r7
                kotlin.coroutines.Continuation r2 = (kotlin.coroutines.Continuation) r2
                r7.label = r3
                java.lang.Object r8 = kotlinx.coroutines.channels.ProduceKt.awaitClose(r8, r1, r2)
                if (r8 != r0) goto L3e
                goto L8d
            L3e:
                kotlin.Unit r8 = kotlin.Unit.INSTANCE
                return r8
            L41:
                io.realm.RealmConfiguration r1 = r7.$config
                io.realm.DynamicRealm r1 = io.realm.DynamicRealm.getInstance(r1)
                io.realm.internal.coroutines.InternalFlowFactory r3 = r7.this$0
                io.realm.internal.coroutines.InternalFlowFactory$changesetFrom$2$$ExternalSyntheticLambda0 r4 = new io.realm.internal.coroutines.InternalFlowFactory$changesetFrom$2$$ExternalSyntheticLambda0
                r4.<init>()
                io.realm.RealmResults<T> r3 = r7.$results
                r3.addChangeListener(r4)
                io.realm.internal.coroutines.InternalFlowFactory r3 = r7.this$0
                boolean r3 = io.realm.internal.coroutines.InternalFlowFactory.access$getReturnFrozenObjects$p(r3)
                r5 = 0
                if (r3 == 0) goto L6d
                io.realm.rx.CollectionChange r3 = new io.realm.rx.CollectionChange
                io.realm.RealmResults<T> r6 = r7.$results
                io.realm.RealmResults r6 = r6.freeze()
                io.realm.OrderedRealmCollection r6 = (io.realm.OrderedRealmCollection) r6
                r3.<init>(r6, r5)
                r8.mo2903trySendJP2dKIU(r3)
                goto L79
            L6d:
                io.realm.rx.CollectionChange r3 = new io.realm.rx.CollectionChange
                io.realm.RealmResults<T> r6 = r7.$results
                io.realm.OrderedRealmCollection r6 = (io.realm.OrderedRealmCollection) r6
                r3.<init>(r6, r5)
                r8.mo2903trySendJP2dKIU(r3)
            L79:
                io.realm.internal.coroutines.InternalFlowFactory$changesetFrom$2$2 r3 = new io.realm.internal.coroutines.InternalFlowFactory$changesetFrom$2$2
                io.realm.RealmResults<T> r5 = r7.$results
                r3.<init>()
                kotlin.jvm.functions.Function0 r3 = (kotlin.jvm.functions.Function0) r3
                r1 = r7
                kotlin.coroutines.Continuation r1 = (kotlin.coroutines.Continuation) r1
                r7.label = r2
                java.lang.Object r8 = kotlinx.coroutines.channels.ProduceKt.awaitClose(r8, r3, r1)
                if (r8 != r0) goto L8e
            L8d:
                return r0
            L8e:
                kotlin.Unit r8 = kotlin.Unit.INSTANCE
                return r8
            */
            throw new UnsupportedOperationException("Method not decompiled: io.realm.internal.coroutines.InternalFlowFactory.AnonymousClass2.invokeSuspend(java.lang.Object):java.lang.Object");
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX INFO: renamed from: invokeSuspend$lambda-0, reason: not valid java name */
        public static final void m1292invokeSuspend$lambda0(ProducerScope producerScope, InternalFlowFactory internalFlowFactory, RealmResults realmResults, OrderedCollectionChangeSet orderedCollectionChangeSet) {
            if (CoroutineScopeKt.isActive(producerScope)) {
                if (internalFlowFactory.returnFrozenObjects) {
                    producerScope.mo2903trySendJP2dKIU(new CollectionChange(realmResults.freeze(), orderedCollectionChangeSet));
                } else {
                    producerScope.mo2903trySendJP2dKIU(new CollectionChange(realmResults, orderedCollectionChangeSet));
                }
            }
        }
    }

    @Override // io.realm.coroutines.FlowFactory
    public <T> Flow<RealmList<T>> from(Realm realm, RealmList<T> realmList) {
        Intrinsics.checkNotNullParameter(realm, "realm");
        Intrinsics.checkNotNullParameter(realmList, "realmList");
        if (realm.isFrozen()) {
            return FlowKt.flowOf(realmList);
        }
        return FlowKt.callbackFlow(new C00475(realmList, realm.getConfiguration(), this, null));
    }

    /* JADX INFO: Add missing generic type declarations: [T] */
    /* JADX INFO: renamed from: io.realm.internal.coroutines.InternalFlowFactory$from$5, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: InternalFlowFactory.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u00040\u0003H\u008a@"}, d2 = {"<anonymous>", "", ExifInterface.GPS_DIRECTION_TRUE, "Lkotlinx/coroutines/channels/ProducerScope;", "Lio/realm/RealmList;"}, k = 3, mv = {1, 6, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    @DebugMetadata(c = "io.realm.internal.coroutines.InternalFlowFactory$from$5", f = "InternalFlowFactory.kt", i = {}, l = {314, 342}, m = "invokeSuspend", n = {}, s = {})
    static final class C00475<T> extends SuspendLambda implements Function2<ProducerScope<? super RealmList<T>>, Continuation<? super Unit>, Object> {
        final /* synthetic */ RealmConfiguration $config;
        final /* synthetic */ RealmList<T> $realmList;
        private /* synthetic */ Object L$0;
        int label;
        final /* synthetic */ InternalFlowFactory this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C00475(RealmList<T> realmList, RealmConfiguration realmConfiguration, InternalFlowFactory internalFlowFactory, Continuation<? super C00475> continuation) {
            super(2, continuation);
            this.$realmList = realmList;
            this.$config = realmConfiguration;
            this.this$0 = internalFlowFactory;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            C00475 c00475 = new C00475(this.$realmList, this.$config, this.this$0, continuation);
            c00475.L$0 = obj;
            return c00475;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(ProducerScope<? super RealmList<T>> producerScope, Continuation<? super Unit> continuation) {
            return ((C00475) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code restructure failed: missing block: B:13:0x003a, code lost:
        
            if (kotlinx.coroutines.channels.ProduceKt.awaitClose(r7, io.realm.internal.coroutines.InternalFlowFactory.C00475.AnonymousClass1.INSTANCE, r6) == r0) goto L23;
         */
        /* JADX WARN: Code restructure failed: missing block: B:22:0x0080, code lost:
        
            if (kotlinx.coroutines.channels.ProduceKt.awaitClose(r7, new io.realm.internal.coroutines.InternalFlowFactory.C00475.AnonymousClass2(), r6) == r0) goto L23;
         */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r7) throws java.lang.Throwable {
            /*
                r6 = this;
                java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                int r1 = r6.label
                r2 = 2
                r3 = 1
                if (r1 == 0) goto L1e
                if (r1 == r3) goto L1a
                if (r1 != r2) goto L12
                kotlin.ResultKt.throwOnFailure(r7)
                goto L83
            L12:
                java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
                java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
                r7.<init>(r0)
                throw r7
            L1a:
                kotlin.ResultKt.throwOnFailure(r7)
                goto L3d
            L1e:
                kotlin.ResultKt.throwOnFailure(r7)
                java.lang.Object r7 = r6.L$0
                kotlinx.coroutines.channels.ProducerScope r7 = (kotlinx.coroutines.channels.ProducerScope) r7
                io.realm.RealmList<T> r1 = r6.$realmList
                boolean r1 = r1.isValid()
                if (r1 != 0) goto L40
                io.realm.internal.coroutines.InternalFlowFactory$from$5$1 r1 = new kotlin.jvm.functions.Function0<kotlin.Unit>() { // from class: io.realm.internal.coroutines.InternalFlowFactory.from.5.1
                    static {
                        /*
                            io.realm.internal.coroutines.InternalFlowFactory$from$5$1 r0 = new io.realm.internal.coroutines.InternalFlowFactory$from$5$1
                            r0.<init>()
                            
                            // error: 0x0005: SPUT (r0 I:io.realm.internal.coroutines.InternalFlowFactory$from$5$1) io.realm.internal.coroutines.InternalFlowFactory.from.5.1.INSTANCE io.realm.internal.coroutines.InternalFlowFactory$from$5$1
                            return
                        */
                        throw new UnsupportedOperationException("Method not decompiled: io.realm.internal.coroutines.InternalFlowFactory.C00475.AnonymousClass1.<clinit>():void");
                    }

                    {
                        /*
                            r1 = this;
                            r0 = 0
                            r1.<init>(r0)
                            return
                        */
                        throw new UnsupportedOperationException("Method not decompiled: io.realm.internal.coroutines.InternalFlowFactory.C00475.AnonymousClass1.<init>():void");
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2() {
                        /*
                            r0 = this;
                            return
                        */
                        throw new UnsupportedOperationException("Method not decompiled: io.realm.internal.coroutines.InternalFlowFactory.C00475.AnonymousClass1.invoke2():void");
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public /* bridge */ /* synthetic */ kotlin.Unit invoke() {
                        /*
                            r1 = this;
                            r1.invoke2()
                            kotlin.Unit r0 = kotlin.Unit.INSTANCE
                            return r0
                        */
                        throw new UnsupportedOperationException("Method not decompiled: io.realm.internal.coroutines.InternalFlowFactory.C00475.AnonymousClass1.invoke():java.lang.Object");
                    }
                }
                kotlin.jvm.functions.Function0 r1 = (kotlin.jvm.functions.Function0) r1
                r2 = r6
                kotlin.coroutines.Continuation r2 = (kotlin.coroutines.Continuation) r2
                r6.label = r3
                java.lang.Object r7 = kotlinx.coroutines.channels.ProduceKt.awaitClose(r7, r1, r2)
                if (r7 != r0) goto L3d
                goto L82
            L3d:
                kotlin.Unit r7 = kotlin.Unit.INSTANCE
                return r7
            L40:
                io.realm.RealmConfiguration r1 = r6.$config
                io.realm.Realm r1 = io.realm.Realm.getInstance(r1)
                io.realm.internal.coroutines.InternalFlowFactory r3 = r6.this$0
                io.realm.internal.coroutines.InternalFlowFactory$from$5$$ExternalSyntheticLambda0 r4 = new io.realm.internal.coroutines.InternalFlowFactory$from$5$$ExternalSyntheticLambda0
                r4.<init>()
                io.realm.RealmList<T> r3 = r6.$realmList
                r3.addChangeListener(r4)
                io.realm.internal.coroutines.InternalFlowFactory r3 = r6.this$0
                boolean r3 = io.realm.internal.coroutines.InternalFlowFactory.access$getReturnFrozenObjects$p(r3)
                if (r3 == 0) goto L69
                io.realm.RealmList<T> r3 = r6.$realmList
                io.realm.RealmList r3 = r3.freeze()
                java.lang.String r5 = "realmList.freeze()"
                kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r3, r5)
                r7.mo2903trySendJP2dKIU(r3)
                goto L6e
            L69:
                io.realm.RealmList<T> r3 = r6.$realmList
                r7.mo2903trySendJP2dKIU(r3)
            L6e:
                io.realm.internal.coroutines.InternalFlowFactory$from$5$2 r3 = new io.realm.internal.coroutines.InternalFlowFactory$from$5$2
                io.realm.RealmList<T> r5 = r6.$realmList
                r3.<init>()
                kotlin.jvm.functions.Function0 r3 = (kotlin.jvm.functions.Function0) r3
                r1 = r6
                kotlin.coroutines.Continuation r1 = (kotlin.coroutines.Continuation) r1
                r6.label = r2
                java.lang.Object r7 = kotlinx.coroutines.channels.ProduceKt.awaitClose(r7, r3, r1)
                if (r7 != r0) goto L83
            L82:
                return r0
            L83:
                kotlin.Unit r7 = kotlin.Unit.INSTANCE
                return r7
            */
            throw new UnsupportedOperationException("Method not decompiled: io.realm.internal.coroutines.InternalFlowFactory.C00475.invokeSuspend(java.lang.Object):java.lang.Object");
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX INFO: renamed from: invokeSuspend$lambda-0, reason: not valid java name */
        public static final void m1303invokeSuspend$lambda0(ProducerScope producerScope, InternalFlowFactory internalFlowFactory, RealmList realmList) {
            if (CoroutineScopeKt.isActive(producerScope)) {
                if (realmList.isValid()) {
                    if (internalFlowFactory.returnFrozenObjects) {
                        RealmList realmListFreeze = realmList.freeze();
                        Intrinsics.checkNotNullExpressionValue(realmListFreeze, "listenerResults.freeze()");
                        producerScope.mo2903trySendJP2dKIU(realmListFreeze);
                        return;
                    }
                    producerScope.mo2903trySendJP2dKIU(realmList);
                    return;
                }
                SendChannel.DefaultImpls.close$default(producerScope, null, 1, null);
            }
        }
    }

    @Override // io.realm.coroutines.FlowFactory
    public <T> Flow<CollectionChange<RealmList<T>>> changesetFrom(Realm realm, RealmList<T> list) {
        Intrinsics.checkNotNullParameter(realm, "realm");
        Intrinsics.checkNotNullParameter(list, "list");
        if (realm.isFrozen()) {
            return FlowKt.flowOf(new CollectionChange(list, null));
        }
        return FlowKt.callbackFlow(new AnonymousClass3(list, realm.getConfiguration(), this, null));
    }

    /* JADX INFO: Add missing generic type declarations: [T] */
    /* JADX INFO: renamed from: io.realm.internal.coroutines.InternalFlowFactory$changesetFrom$3, reason: invalid class name */
    /* JADX INFO: compiled from: InternalFlowFactory.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u00050\u00040\u0003H\u008a@"}, d2 = {"<anonymous>", "", ExifInterface.GPS_DIRECTION_TRUE, "Lkotlinx/coroutines/channels/ProducerScope;", "Lio/realm/rx/CollectionChange;", "Lio/realm/RealmList;"}, k = 3, mv = {1, 6, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    @DebugMetadata(c = "io.realm.internal.coroutines.InternalFlowFactory$changesetFrom$3", f = "InternalFlowFactory.kt", i = {}, l = {366, 394}, m = "invokeSuspend", n = {}, s = {})
    static final class AnonymousClass3<T> extends SuspendLambda implements Function2<ProducerScope<? super CollectionChange<RealmList<T>>>, Continuation<? super Unit>, Object> {
        final /* synthetic */ RealmConfiguration $config;
        final /* synthetic */ RealmList<T> $list;
        private /* synthetic */ Object L$0;
        int label;
        final /* synthetic */ InternalFlowFactory this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass3(RealmList<T> realmList, RealmConfiguration realmConfiguration, InternalFlowFactory internalFlowFactory, Continuation<? super AnonymousClass3> continuation) {
            super(2, continuation);
            this.$list = realmList;
            this.$config = realmConfiguration;
            this.this$0 = internalFlowFactory;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            AnonymousClass3 anonymousClass3 = new AnonymousClass3(this.$list, this.$config, this.this$0, continuation);
            anonymousClass3.L$0 = obj;
            return anonymousClass3;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(ProducerScope<? super CollectionChange<RealmList<T>>> producerScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass3) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code restructure failed: missing block: B:13:0x003b, code lost:
        
            if (kotlinx.coroutines.channels.ProduceKt.awaitClose(r8, io.realm.internal.coroutines.InternalFlowFactory.AnonymousClass3.AnonymousClass1.INSTANCE, r7) == r0) goto L23;
         */
        /* JADX WARN: Code restructure failed: missing block: B:22:0x008b, code lost:
        
            if (kotlinx.coroutines.channels.ProduceKt.awaitClose(r8, new io.realm.internal.coroutines.InternalFlowFactory.AnonymousClass3.AnonymousClass2(), r7) == r0) goto L23;
         */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r8) throws java.lang.Throwable {
            /*
                r7 = this;
                java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                int r1 = r7.label
                r2 = 2
                r3 = 1
                if (r1 == 0) goto L1f
                if (r1 == r3) goto L1b
                if (r1 != r2) goto L13
                kotlin.ResultKt.throwOnFailure(r8)
                goto L8e
            L13:
                java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
                java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
                r8.<init>(r0)
                throw r8
            L1b:
                kotlin.ResultKt.throwOnFailure(r8)
                goto L3e
            L1f:
                kotlin.ResultKt.throwOnFailure(r8)
                java.lang.Object r8 = r7.L$0
                kotlinx.coroutines.channels.ProducerScope r8 = (kotlinx.coroutines.channels.ProducerScope) r8
                io.realm.RealmList<T> r1 = r7.$list
                boolean r1 = r1.isValid()
                if (r1 != 0) goto L41
                io.realm.internal.coroutines.InternalFlowFactory$changesetFrom$3$1 r1 = new kotlin.jvm.functions.Function0<kotlin.Unit>() { // from class: io.realm.internal.coroutines.InternalFlowFactory.changesetFrom.3.1
                    static {
                        /*
                            io.realm.internal.coroutines.InternalFlowFactory$changesetFrom$3$1 r0 = new io.realm.internal.coroutines.InternalFlowFactory$changesetFrom$3$1
                            r0.<init>()
                            
                            // error: 0x0005: SPUT (r0 I:io.realm.internal.coroutines.InternalFlowFactory$changesetFrom$3$1) io.realm.internal.coroutines.InternalFlowFactory.changesetFrom.3.1.INSTANCE io.realm.internal.coroutines.InternalFlowFactory$changesetFrom$3$1
                            return
                        */
                        throw new UnsupportedOperationException("Method not decompiled: io.realm.internal.coroutines.InternalFlowFactory.AnonymousClass3.AnonymousClass1.<clinit>():void");
                    }

                    {
                        /*
                            r1 = this;
                            r0 = 0
                            r1.<init>(r0)
                            return
                        */
                        throw new UnsupportedOperationException("Method not decompiled: io.realm.internal.coroutines.InternalFlowFactory.AnonymousClass3.AnonymousClass1.<init>():void");
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2() {
                        /*
                            r0 = this;
                            return
                        */
                        throw new UnsupportedOperationException("Method not decompiled: io.realm.internal.coroutines.InternalFlowFactory.AnonymousClass3.AnonymousClass1.invoke2():void");
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public /* bridge */ /* synthetic */ kotlin.Unit invoke() {
                        /*
                            r1 = this;
                            r1.invoke2()
                            kotlin.Unit r0 = kotlin.Unit.INSTANCE
                            return r0
                        */
                        throw new UnsupportedOperationException("Method not decompiled: io.realm.internal.coroutines.InternalFlowFactory.AnonymousClass3.AnonymousClass1.invoke():java.lang.Object");
                    }
                }
                kotlin.jvm.functions.Function0 r1 = (kotlin.jvm.functions.Function0) r1
                r2 = r7
                kotlin.coroutines.Continuation r2 = (kotlin.coroutines.Continuation) r2
                r7.label = r3
                java.lang.Object r8 = kotlinx.coroutines.channels.ProduceKt.awaitClose(r8, r1, r2)
                if (r8 != r0) goto L3e
                goto L8d
            L3e:
                kotlin.Unit r8 = kotlin.Unit.INSTANCE
                return r8
            L41:
                io.realm.RealmConfiguration r1 = r7.$config
                io.realm.Realm r1 = io.realm.Realm.getInstance(r1)
                io.realm.internal.coroutines.InternalFlowFactory r3 = r7.this$0
                io.realm.internal.coroutines.InternalFlowFactory$changesetFrom$3$$ExternalSyntheticLambda0 r4 = new io.realm.internal.coroutines.InternalFlowFactory$changesetFrom$3$$ExternalSyntheticLambda0
                r4.<init>()
                io.realm.RealmList<T> r3 = r7.$list
                r3.addChangeListener(r4)
                io.realm.internal.coroutines.InternalFlowFactory r3 = r7.this$0
                boolean r3 = io.realm.internal.coroutines.InternalFlowFactory.access$getReturnFrozenObjects$p(r3)
                r5 = 0
                if (r3 == 0) goto L6d
                io.realm.rx.CollectionChange r3 = new io.realm.rx.CollectionChange
                io.realm.RealmList<T> r6 = r7.$list
                io.realm.RealmList r6 = r6.freeze()
                io.realm.OrderedRealmCollection r6 = (io.realm.OrderedRealmCollection) r6
                r3.<init>(r6, r5)
                r8.mo2903trySendJP2dKIU(r3)
                goto L79
            L6d:
                io.realm.rx.CollectionChange r3 = new io.realm.rx.CollectionChange
                io.realm.RealmList<T> r6 = r7.$list
                io.realm.OrderedRealmCollection r6 = (io.realm.OrderedRealmCollection) r6
                r3.<init>(r6, r5)
                r8.mo2903trySendJP2dKIU(r3)
            L79:
                io.realm.internal.coroutines.InternalFlowFactory$changesetFrom$3$2 r3 = new io.realm.internal.coroutines.InternalFlowFactory$changesetFrom$3$2
                io.realm.RealmList<T> r5 = r7.$list
                r3.<init>()
                kotlin.jvm.functions.Function0 r3 = (kotlin.jvm.functions.Function0) r3
                r1 = r7
                kotlin.coroutines.Continuation r1 = (kotlin.coroutines.Continuation) r1
                r7.label = r2
                java.lang.Object r8 = kotlinx.coroutines.channels.ProduceKt.awaitClose(r8, r3, r1)
                if (r8 != r0) goto L8e
            L8d:
                return r0
            L8e:
                kotlin.Unit r8 = kotlin.Unit.INSTANCE
                return r8
            */
            throw new UnsupportedOperationException("Method not decompiled: io.realm.internal.coroutines.InternalFlowFactory.AnonymousClass3.invokeSuspend(java.lang.Object):java.lang.Object");
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX INFO: renamed from: invokeSuspend$lambda-0, reason: not valid java name */
        public static final void m1293invokeSuspend$lambda0(ProducerScope producerScope, InternalFlowFactory internalFlowFactory, RealmList realmList, OrderedCollectionChangeSet orderedCollectionChangeSet) {
            if (CoroutineScopeKt.isActive(producerScope)) {
                if (realmList.isValid()) {
                    if (internalFlowFactory.returnFrozenObjects) {
                        producerScope.mo2903trySendJP2dKIU(new CollectionChange(realmList.freeze(), orderedCollectionChangeSet));
                        return;
                    } else {
                        producerScope.mo2903trySendJP2dKIU(new CollectionChange(realmList, orderedCollectionChangeSet));
                        return;
                    }
                }
                SendChannel.DefaultImpls.close$default(producerScope, null, 1, null);
            }
        }
    }

    @Override // io.realm.coroutines.FlowFactory
    public <T> Flow<RealmList<T>> from(DynamicRealm dynamicRealm, RealmList<T> realmList) {
        Intrinsics.checkNotNullParameter(dynamicRealm, "dynamicRealm");
        Intrinsics.checkNotNullParameter(realmList, "realmList");
        if (dynamicRealm.isFrozen()) {
            return FlowKt.flowOf(realmList);
        }
        return FlowKt.callbackFlow(new C00486(realmList, dynamicRealm.getConfiguration(), this, null));
    }

    /* JADX INFO: Add missing generic type declarations: [T] */
    /* JADX INFO: renamed from: io.realm.internal.coroutines.InternalFlowFactory$from$6, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: InternalFlowFactory.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u00040\u0003H\u008a@"}, d2 = {"<anonymous>", "", ExifInterface.GPS_DIRECTION_TRUE, "Lkotlinx/coroutines/channels/ProducerScope;", "Lio/realm/RealmList;"}, k = 3, mv = {1, 6, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    @DebugMetadata(c = "io.realm.internal.coroutines.InternalFlowFactory$from$6", f = "InternalFlowFactory.kt", i = {}, l = {415, 443}, m = "invokeSuspend", n = {}, s = {})
    static final class C00486<T> extends SuspendLambda implements Function2<ProducerScope<? super RealmList<T>>, Continuation<? super Unit>, Object> {
        final /* synthetic */ RealmConfiguration $config;
        final /* synthetic */ RealmList<T> $realmList;
        private /* synthetic */ Object L$0;
        int label;
        final /* synthetic */ InternalFlowFactory this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C00486(RealmList<T> realmList, RealmConfiguration realmConfiguration, InternalFlowFactory internalFlowFactory, Continuation<? super C00486> continuation) {
            super(2, continuation);
            this.$realmList = realmList;
            this.$config = realmConfiguration;
            this.this$0 = internalFlowFactory;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            C00486 c00486 = new C00486(this.$realmList, this.$config, this.this$0, continuation);
            c00486.L$0 = obj;
            return c00486;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(ProducerScope<? super RealmList<T>> producerScope, Continuation<? super Unit> continuation) {
            return ((C00486) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code restructure failed: missing block: B:13:0x003a, code lost:
        
            if (kotlinx.coroutines.channels.ProduceKt.awaitClose(r7, io.realm.internal.coroutines.InternalFlowFactory.C00486.AnonymousClass1.INSTANCE, r6) == r0) goto L23;
         */
        /* JADX WARN: Code restructure failed: missing block: B:22:0x0080, code lost:
        
            if (kotlinx.coroutines.channels.ProduceKt.awaitClose(r7, new io.realm.internal.coroutines.InternalFlowFactory.C00486.AnonymousClass2(), r6) == r0) goto L23;
         */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r7) throws java.lang.Throwable {
            /*
                r6 = this;
                java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                int r1 = r6.label
                r2 = 2
                r3 = 1
                if (r1 == 0) goto L1e
                if (r1 == r3) goto L1a
                if (r1 != r2) goto L12
                kotlin.ResultKt.throwOnFailure(r7)
                goto L83
            L12:
                java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
                java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
                r7.<init>(r0)
                throw r7
            L1a:
                kotlin.ResultKt.throwOnFailure(r7)
                goto L3d
            L1e:
                kotlin.ResultKt.throwOnFailure(r7)
                java.lang.Object r7 = r6.L$0
                kotlinx.coroutines.channels.ProducerScope r7 = (kotlinx.coroutines.channels.ProducerScope) r7
                io.realm.RealmList<T> r1 = r6.$realmList
                boolean r1 = r1.isValid()
                if (r1 != 0) goto L40
                io.realm.internal.coroutines.InternalFlowFactory$from$6$1 r1 = new kotlin.jvm.functions.Function0<kotlin.Unit>() { // from class: io.realm.internal.coroutines.InternalFlowFactory.from.6.1
                    static {
                        /*
                            io.realm.internal.coroutines.InternalFlowFactory$from$6$1 r0 = new io.realm.internal.coroutines.InternalFlowFactory$from$6$1
                            r0.<init>()
                            
                            // error: 0x0005: SPUT (r0 I:io.realm.internal.coroutines.InternalFlowFactory$from$6$1) io.realm.internal.coroutines.InternalFlowFactory.from.6.1.INSTANCE io.realm.internal.coroutines.InternalFlowFactory$from$6$1
                            return
                        */
                        throw new UnsupportedOperationException("Method not decompiled: io.realm.internal.coroutines.InternalFlowFactory.C00486.AnonymousClass1.<clinit>():void");
                    }

                    {
                        /*
                            r1 = this;
                            r0 = 0
                            r1.<init>(r0)
                            return
                        */
                        throw new UnsupportedOperationException("Method not decompiled: io.realm.internal.coroutines.InternalFlowFactory.C00486.AnonymousClass1.<init>():void");
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2() {
                        /*
                            r0 = this;
                            return
                        */
                        throw new UnsupportedOperationException("Method not decompiled: io.realm.internal.coroutines.InternalFlowFactory.C00486.AnonymousClass1.invoke2():void");
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public /* bridge */ /* synthetic */ kotlin.Unit invoke() {
                        /*
                            r1 = this;
                            r1.invoke2()
                            kotlin.Unit r0 = kotlin.Unit.INSTANCE
                            return r0
                        */
                        throw new UnsupportedOperationException("Method not decompiled: io.realm.internal.coroutines.InternalFlowFactory.C00486.AnonymousClass1.invoke():java.lang.Object");
                    }
                }
                kotlin.jvm.functions.Function0 r1 = (kotlin.jvm.functions.Function0) r1
                r2 = r6
                kotlin.coroutines.Continuation r2 = (kotlin.coroutines.Continuation) r2
                r6.label = r3
                java.lang.Object r7 = kotlinx.coroutines.channels.ProduceKt.awaitClose(r7, r1, r2)
                if (r7 != r0) goto L3d
                goto L82
            L3d:
                kotlin.Unit r7 = kotlin.Unit.INSTANCE
                return r7
            L40:
                io.realm.RealmConfiguration r1 = r6.$config
                io.realm.DynamicRealm r1 = io.realm.DynamicRealm.getInstance(r1)
                io.realm.internal.coroutines.InternalFlowFactory r3 = r6.this$0
                io.realm.internal.coroutines.InternalFlowFactory$from$6$$ExternalSyntheticLambda0 r4 = new io.realm.internal.coroutines.InternalFlowFactory$from$6$$ExternalSyntheticLambda0
                r4.<init>()
                io.realm.RealmList<T> r3 = r6.$realmList
                r3.addChangeListener(r4)
                io.realm.internal.coroutines.InternalFlowFactory r3 = r6.this$0
                boolean r3 = io.realm.internal.coroutines.InternalFlowFactory.access$getReturnFrozenObjects$p(r3)
                if (r3 == 0) goto L69
                io.realm.RealmList<T> r3 = r6.$realmList
                io.realm.RealmList r3 = r3.freeze()
                java.lang.String r5 = "realmList.freeze()"
                kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r3, r5)
                r7.mo2903trySendJP2dKIU(r3)
                goto L6e
            L69:
                io.realm.RealmList<T> r3 = r6.$realmList
                r7.mo2903trySendJP2dKIU(r3)
            L6e:
                io.realm.internal.coroutines.InternalFlowFactory$from$6$2 r3 = new io.realm.internal.coroutines.InternalFlowFactory$from$6$2
                io.realm.RealmList<T> r5 = r6.$realmList
                r3.<init>()
                kotlin.jvm.functions.Function0 r3 = (kotlin.jvm.functions.Function0) r3
                r1 = r6
                kotlin.coroutines.Continuation r1 = (kotlin.coroutines.Continuation) r1
                r6.label = r2
                java.lang.Object r7 = kotlinx.coroutines.channels.ProduceKt.awaitClose(r7, r3, r1)
                if (r7 != r0) goto L83
            L82:
                return r0
            L83:
                kotlin.Unit r7 = kotlin.Unit.INSTANCE
                return r7
            */
            throw new UnsupportedOperationException("Method not decompiled: io.realm.internal.coroutines.InternalFlowFactory.C00486.invokeSuspend(java.lang.Object):java.lang.Object");
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX INFO: renamed from: invokeSuspend$lambda-0, reason: not valid java name */
        public static final void m1304invokeSuspend$lambda0(ProducerScope producerScope, InternalFlowFactory internalFlowFactory, RealmList realmList) {
            if (CoroutineScopeKt.isActive(producerScope)) {
                if (realmList.isValid()) {
                    if (internalFlowFactory.returnFrozenObjects) {
                        RealmList realmListFreeze = realmList.freeze();
                        Intrinsics.checkNotNullExpressionValue(realmListFreeze, "listenerResults.freeze()");
                        producerScope.mo2903trySendJP2dKIU(realmListFreeze);
                        return;
                    }
                    producerScope.mo2903trySendJP2dKIU(realmList);
                    return;
                }
                SendChannel.DefaultImpls.close$default(producerScope, null, 1, null);
            }
        }
    }

    @Override // io.realm.coroutines.FlowFactory
    public <T> Flow<CollectionChange<RealmList<T>>> changesetFrom(DynamicRealm dynamicRealm, RealmList<T> list) {
        Intrinsics.checkNotNullParameter(dynamicRealm, "dynamicRealm");
        Intrinsics.checkNotNullParameter(list, "list");
        if (dynamicRealm.isFrozen()) {
            return FlowKt.flowOf(new CollectionChange(list, null));
        }
        return FlowKt.callbackFlow(new AnonymousClass4(list, dynamicRealm.getConfiguration(), this, null));
    }

    /* JADX INFO: Add missing generic type declarations: [T] */
    /* JADX INFO: renamed from: io.realm.internal.coroutines.InternalFlowFactory$changesetFrom$4, reason: invalid class name */
    /* JADX INFO: compiled from: InternalFlowFactory.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u00050\u00040\u0003H\u008a@"}, d2 = {"<anonymous>", "", ExifInterface.GPS_DIRECTION_TRUE, "Lkotlinx/coroutines/channels/ProducerScope;", "Lio/realm/rx/CollectionChange;", "Lio/realm/RealmList;"}, k = 3, mv = {1, 6, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    @DebugMetadata(c = "io.realm.internal.coroutines.InternalFlowFactory$changesetFrom$4", f = "InternalFlowFactory.kt", i = {}, l = {467, 495}, m = "invokeSuspend", n = {}, s = {})
    static final class AnonymousClass4<T> extends SuspendLambda implements Function2<ProducerScope<? super CollectionChange<RealmList<T>>>, Continuation<? super Unit>, Object> {
        final /* synthetic */ RealmConfiguration $config;
        final /* synthetic */ RealmList<T> $list;
        private /* synthetic */ Object L$0;
        int label;
        final /* synthetic */ InternalFlowFactory this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass4(RealmList<T> realmList, RealmConfiguration realmConfiguration, InternalFlowFactory internalFlowFactory, Continuation<? super AnonymousClass4> continuation) {
            super(2, continuation);
            this.$list = realmList;
            this.$config = realmConfiguration;
            this.this$0 = internalFlowFactory;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            AnonymousClass4 anonymousClass4 = new AnonymousClass4(this.$list, this.$config, this.this$0, continuation);
            anonymousClass4.L$0 = obj;
            return anonymousClass4;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(ProducerScope<? super CollectionChange<RealmList<T>>> producerScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass4) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code restructure failed: missing block: B:13:0x003b, code lost:
        
            if (kotlinx.coroutines.channels.ProduceKt.awaitClose(r8, io.realm.internal.coroutines.InternalFlowFactory.AnonymousClass4.AnonymousClass1.INSTANCE, r7) == r0) goto L23;
         */
        /* JADX WARN: Code restructure failed: missing block: B:22:0x008b, code lost:
        
            if (kotlinx.coroutines.channels.ProduceKt.awaitClose(r8, new io.realm.internal.coroutines.InternalFlowFactory.AnonymousClass4.AnonymousClass2(), r7) == r0) goto L23;
         */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r8) throws java.lang.Throwable {
            /*
                r7 = this;
                java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                int r1 = r7.label
                r2 = 2
                r3 = 1
                if (r1 == 0) goto L1f
                if (r1 == r3) goto L1b
                if (r1 != r2) goto L13
                kotlin.ResultKt.throwOnFailure(r8)
                goto L8e
            L13:
                java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
                java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
                r8.<init>(r0)
                throw r8
            L1b:
                kotlin.ResultKt.throwOnFailure(r8)
                goto L3e
            L1f:
                kotlin.ResultKt.throwOnFailure(r8)
                java.lang.Object r8 = r7.L$0
                kotlinx.coroutines.channels.ProducerScope r8 = (kotlinx.coroutines.channels.ProducerScope) r8
                io.realm.RealmList<T> r1 = r7.$list
                boolean r1 = r1.isValid()
                if (r1 != 0) goto L41
                io.realm.internal.coroutines.InternalFlowFactory$changesetFrom$4$1 r1 = new kotlin.jvm.functions.Function0<kotlin.Unit>() { // from class: io.realm.internal.coroutines.InternalFlowFactory.changesetFrom.4.1
                    static {
                        /*
                            io.realm.internal.coroutines.InternalFlowFactory$changesetFrom$4$1 r0 = new io.realm.internal.coroutines.InternalFlowFactory$changesetFrom$4$1
                            r0.<init>()
                            
                            // error: 0x0005: SPUT (r0 I:io.realm.internal.coroutines.InternalFlowFactory$changesetFrom$4$1) io.realm.internal.coroutines.InternalFlowFactory.changesetFrom.4.1.INSTANCE io.realm.internal.coroutines.InternalFlowFactory$changesetFrom$4$1
                            return
                        */
                        throw new UnsupportedOperationException("Method not decompiled: io.realm.internal.coroutines.InternalFlowFactory.AnonymousClass4.AnonymousClass1.<clinit>():void");
                    }

                    {
                        /*
                            r1 = this;
                            r0 = 0
                            r1.<init>(r0)
                            return
                        */
                        throw new UnsupportedOperationException("Method not decompiled: io.realm.internal.coroutines.InternalFlowFactory.AnonymousClass4.AnonymousClass1.<init>():void");
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2() {
                        /*
                            r0 = this;
                            return
                        */
                        throw new UnsupportedOperationException("Method not decompiled: io.realm.internal.coroutines.InternalFlowFactory.AnonymousClass4.AnonymousClass1.invoke2():void");
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public /* bridge */ /* synthetic */ kotlin.Unit invoke() {
                        /*
                            r1 = this;
                            r1.invoke2()
                            kotlin.Unit r0 = kotlin.Unit.INSTANCE
                            return r0
                        */
                        throw new UnsupportedOperationException("Method not decompiled: io.realm.internal.coroutines.InternalFlowFactory.AnonymousClass4.AnonymousClass1.invoke():java.lang.Object");
                    }
                }
                kotlin.jvm.functions.Function0 r1 = (kotlin.jvm.functions.Function0) r1
                r2 = r7
                kotlin.coroutines.Continuation r2 = (kotlin.coroutines.Continuation) r2
                r7.label = r3
                java.lang.Object r8 = kotlinx.coroutines.channels.ProduceKt.awaitClose(r8, r1, r2)
                if (r8 != r0) goto L3e
                goto L8d
            L3e:
                kotlin.Unit r8 = kotlin.Unit.INSTANCE
                return r8
            L41:
                io.realm.RealmConfiguration r1 = r7.$config
                io.realm.DynamicRealm r1 = io.realm.DynamicRealm.getInstance(r1)
                io.realm.internal.coroutines.InternalFlowFactory r3 = r7.this$0
                io.realm.internal.coroutines.InternalFlowFactory$changesetFrom$4$$ExternalSyntheticLambda0 r4 = new io.realm.internal.coroutines.InternalFlowFactory$changesetFrom$4$$ExternalSyntheticLambda0
                r4.<init>()
                io.realm.RealmList<T> r3 = r7.$list
                r3.addChangeListener(r4)
                io.realm.internal.coroutines.InternalFlowFactory r3 = r7.this$0
                boolean r3 = io.realm.internal.coroutines.InternalFlowFactory.access$getReturnFrozenObjects$p(r3)
                r5 = 0
                if (r3 == 0) goto L6d
                io.realm.rx.CollectionChange r3 = new io.realm.rx.CollectionChange
                io.realm.RealmList<T> r6 = r7.$list
                io.realm.RealmList r6 = r6.freeze()
                io.realm.OrderedRealmCollection r6 = (io.realm.OrderedRealmCollection) r6
                r3.<init>(r6, r5)
                r8.mo2903trySendJP2dKIU(r3)
                goto L79
            L6d:
                io.realm.rx.CollectionChange r3 = new io.realm.rx.CollectionChange
                io.realm.RealmList<T> r6 = r7.$list
                io.realm.OrderedRealmCollection r6 = (io.realm.OrderedRealmCollection) r6
                r3.<init>(r6, r5)
                r8.mo2903trySendJP2dKIU(r3)
            L79:
                io.realm.internal.coroutines.InternalFlowFactory$changesetFrom$4$2 r3 = new io.realm.internal.coroutines.InternalFlowFactory$changesetFrom$4$2
                io.realm.RealmList<T> r5 = r7.$list
                r3.<init>()
                kotlin.jvm.functions.Function0 r3 = (kotlin.jvm.functions.Function0) r3
                r1 = r7
                kotlin.coroutines.Continuation r1 = (kotlin.coroutines.Continuation) r1
                r7.label = r2
                java.lang.Object r8 = kotlinx.coroutines.channels.ProduceKt.awaitClose(r8, r3, r1)
                if (r8 != r0) goto L8e
            L8d:
                return r0
            L8e:
                kotlin.Unit r8 = kotlin.Unit.INSTANCE
                return r8
            */
            throw new UnsupportedOperationException("Method not decompiled: io.realm.internal.coroutines.InternalFlowFactory.AnonymousClass4.invokeSuspend(java.lang.Object):java.lang.Object");
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX INFO: renamed from: invokeSuspend$lambda-0, reason: not valid java name */
        public static final void m1294invokeSuspend$lambda0(ProducerScope producerScope, InternalFlowFactory internalFlowFactory, RealmList realmList, OrderedCollectionChangeSet orderedCollectionChangeSet) {
            if (CoroutineScopeKt.isActive(producerScope)) {
                if (realmList.isValid()) {
                    if (internalFlowFactory.returnFrozenObjects) {
                        producerScope.mo2903trySendJP2dKIU(new CollectionChange(realmList.freeze(), orderedCollectionChangeSet));
                        return;
                    } else {
                        producerScope.mo2903trySendJP2dKIU(new CollectionChange(realmList, orderedCollectionChangeSet));
                        return;
                    }
                }
                SendChannel.DefaultImpls.close$default(producerScope, null, 1, null);
            }
        }
    }

    @Override // io.realm.coroutines.FlowFactory
    public <T extends RealmModel> Flow<T> from(Realm realm, T realmObject) {
        Intrinsics.checkNotNullParameter(realm, "realm");
        Intrinsics.checkNotNullParameter(realmObject, "realmObject");
        if (realm.isFrozen()) {
            return FlowKt.flowOf(realmObject);
        }
        return FlowKt.callbackFlow(new AnonymousClass7(realm, realm.getConfiguration(), realmObject, this, null));
    }

    /* JADX INFO: Add missing generic type declarations: [T] */
    /* JADX INFO: renamed from: io.realm.internal.coroutines.InternalFlowFactory$from$7, reason: invalid class name */
    /* JADX INFO: compiled from: InternalFlowFactory.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020\u0003*\b\u0012\u0004\u0012\u0002H\u00020\u0004H\u008a@"}, d2 = {"<anonymous>", "", ExifInterface.GPS_DIRECTION_TRUE, "Lio/realm/RealmModel;", "Lkotlinx/coroutines/channels/ProducerScope;"}, k = 3, mv = {1, 6, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    @DebugMetadata(c = "io.realm.internal.coroutines.InternalFlowFactory$from$7", f = "InternalFlowFactory.kt", i = {}, l = {517, 545}, m = "invokeSuspend", n = {}, s = {})
    static final class AnonymousClass7<T> extends SuspendLambda implements Function2<ProducerScope<? super T>, Continuation<? super Unit>, Object> {
        final /* synthetic */ RealmConfiguration $config;
        final /* synthetic */ Realm $realm;

        /* JADX INFO: Incorrect field signature: TT; */
        final /* synthetic */ RealmModel $realmObject;
        private /* synthetic */ Object L$0;
        int label;
        final /* synthetic */ InternalFlowFactory this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Incorrect types in method signature: (Lio/realm/Realm;Lio/realm/RealmConfiguration;TT;Lio/realm/internal/coroutines/InternalFlowFactory;Lkotlin/coroutines/Continuation<-Lio/realm/internal/coroutines/InternalFlowFactory$from$7;>;)V */
        AnonymousClass7(Realm realm, RealmConfiguration realmConfiguration, RealmModel realmModel, InternalFlowFactory internalFlowFactory, Continuation continuation) {
            super(2, continuation);
            this.$realm = realm;
            this.$config = realmConfiguration;
            this.$realmObject = realmModel;
            this.this$0 = internalFlowFactory;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            AnonymousClass7 anonymousClass7 = new AnonymousClass7(this.$realm, this.$config, this.$realmObject, this.this$0, continuation);
            anonymousClass7.L$0 = obj;
            return anonymousClass7;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(ProducerScope<? super T> producerScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass7) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code restructure failed: missing block: B:13:0x003b, code lost:
        
            if (kotlinx.coroutines.channels.ProduceKt.awaitClose(r7, io.realm.internal.coroutines.InternalFlowFactory.AnonymousClass7.AnonymousClass1.INSTANCE, r6) == r0) goto L25;
         */
        /* JADX WARN: Code restructure failed: missing block: B:24:0x0089, code lost:
        
            if (kotlinx.coroutines.channels.ProduceKt.awaitClose(r7, new io.realm.internal.coroutines.InternalFlowFactory.AnonymousClass7.AnonymousClass2(), r6) == r0) goto L25;
         */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r7) throws java.lang.Throwable {
            /*
                r6 = this;
                java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                int r1 = r6.label
                r2 = 2
                r3 = 1
                if (r1 == 0) goto L1f
                if (r1 == r3) goto L1b
                if (r1 != r2) goto L13
                kotlin.ResultKt.throwOnFailure(r7)
                goto L8c
            L13:
                java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
                java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
                r7.<init>(r0)
                throw r7
            L1b:
                kotlin.ResultKt.throwOnFailure(r7)
                goto L3e
            L1f:
                kotlin.ResultKt.throwOnFailure(r7)
                java.lang.Object r7 = r6.L$0
                kotlinx.coroutines.channels.ProducerScope r7 = (kotlinx.coroutines.channels.ProducerScope) r7
                io.realm.Realm r1 = r6.$realm
                boolean r1 = r1.isClosed()
                if (r1 == 0) goto L41
                io.realm.internal.coroutines.InternalFlowFactory$from$7$1 r1 = new kotlin.jvm.functions.Function0<kotlin.Unit>() { // from class: io.realm.internal.coroutines.InternalFlowFactory.from.7.1
                    static {
                        /*
                            io.realm.internal.coroutines.InternalFlowFactory$from$7$1 r0 = new io.realm.internal.coroutines.InternalFlowFactory$from$7$1
                            r0.<init>()
                            
                            // error: 0x0005: SPUT (r0 I:io.realm.internal.coroutines.InternalFlowFactory$from$7$1) io.realm.internal.coroutines.InternalFlowFactory.from.7.1.INSTANCE io.realm.internal.coroutines.InternalFlowFactory$from$7$1
                            return
                        */
                        throw new UnsupportedOperationException("Method not decompiled: io.realm.internal.coroutines.InternalFlowFactory.AnonymousClass7.AnonymousClass1.<clinit>():void");
                    }

                    {
                        /*
                            r1 = this;
                            r0 = 0
                            r1.<init>(r0)
                            return
                        */
                        throw new UnsupportedOperationException("Method not decompiled: io.realm.internal.coroutines.InternalFlowFactory.AnonymousClass7.AnonymousClass1.<init>():void");
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2() {
                        /*
                            r0 = this;
                            return
                        */
                        throw new UnsupportedOperationException("Method not decompiled: io.realm.internal.coroutines.InternalFlowFactory.AnonymousClass7.AnonymousClass1.invoke2():void");
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public /* bridge */ /* synthetic */ kotlin.Unit invoke() {
                        /*
                            r1 = this;
                            r1.invoke2()
                            kotlin.Unit r0 = kotlin.Unit.INSTANCE
                            return r0
                        */
                        throw new UnsupportedOperationException("Method not decompiled: io.realm.internal.coroutines.InternalFlowFactory.AnonymousClass7.AnonymousClass1.invoke():java.lang.Object");
                    }
                }
                kotlin.jvm.functions.Function0 r1 = (kotlin.jvm.functions.Function0) r1
                r2 = r6
                kotlin.coroutines.Continuation r2 = (kotlin.coroutines.Continuation) r2
                r6.label = r3
                java.lang.Object r7 = kotlinx.coroutines.channels.ProduceKt.awaitClose(r7, r1, r2)
                if (r7 != r0) goto L3e
                goto L8b
            L3e:
                kotlin.Unit r7 = kotlin.Unit.INSTANCE
                return r7
            L41:
                io.realm.RealmConfiguration r1 = r6.$config
                io.realm.Realm r1 = io.realm.Realm.getInstance(r1)
                io.realm.internal.coroutines.InternalFlowFactory r3 = r6.this$0
                io.realm.internal.coroutines.InternalFlowFactory$from$7$$ExternalSyntheticLambda0 r4 = new io.realm.internal.coroutines.InternalFlowFactory$from$7$$ExternalSyntheticLambda0
                r4.<init>()
                io.realm.RealmModel r3 = r6.$realmObject
                io.realm.RealmObject.addChangeListener(r3, r4)
                io.realm.RealmModel r3 = r6.$realmObject
                boolean r3 = io.realm.RealmObject.isLoaded(r3)
                if (r3 == 0) goto L77
                io.realm.internal.coroutines.InternalFlowFactory r3 = r6.this$0
                boolean r3 = io.realm.internal.coroutines.InternalFlowFactory.access$getReturnFrozenObjects$p(r3)
                if (r3 == 0) goto L72
                io.realm.RealmModel r3 = r6.$realmObject
                io.realm.RealmModel r3 = io.realm.RealmObject.freeze(r3)
                java.lang.String r5 = "freeze(realmObject)"
                kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r3, r5)
                r7.mo2903trySendJP2dKIU(r3)
                goto L77
            L72:
                io.realm.RealmModel r3 = r6.$realmObject
                r7.mo2903trySendJP2dKIU(r3)
            L77:
                io.realm.internal.coroutines.InternalFlowFactory$from$7$2 r3 = new io.realm.internal.coroutines.InternalFlowFactory$from$7$2
                io.realm.RealmModel r5 = r6.$realmObject
                r3.<init>()
                kotlin.jvm.functions.Function0 r3 = (kotlin.jvm.functions.Function0) r3
                r1 = r6
                kotlin.coroutines.Continuation r1 = (kotlin.coroutines.Continuation) r1
                r6.label = r2
                java.lang.Object r7 = kotlinx.coroutines.channels.ProduceKt.awaitClose(r7, r3, r1)
                if (r7 != r0) goto L8c
            L8b:
                return r0
            L8c:
                kotlin.Unit r7 = kotlin.Unit.INSTANCE
                return r7
            */
            throw new UnsupportedOperationException("Method not decompiled: io.realm.internal.coroutines.InternalFlowFactory.AnonymousClass7.invokeSuspend(java.lang.Object):java.lang.Object");
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX INFO: renamed from: invokeSuspend$lambda-0, reason: not valid java name */
        public static final void m1306invokeSuspend$lambda0(ProducerScope producerScope, InternalFlowFactory internalFlowFactory, RealmModel realmModel) {
            if (CoroutineScopeKt.isActive(producerScope)) {
                if (internalFlowFactory.returnFrozenObjects) {
                    RealmModel realmModelFreeze = RealmObject.freeze(realmModel);
                    if (realmModelFreeze == null) {
                        throw new NullPointerException("null cannot be cast to non-null type T of io.realm.internal.coroutines.InternalFlowFactory.from.<no name provided>.invokeSuspend$lambda-0");
                    }
                    producerScope.mo2903trySendJP2dKIU(realmModelFreeze);
                    return;
                }
                producerScope.mo2903trySendJP2dKIU(realmModel);
            }
        }
    }

    @Override // io.realm.coroutines.FlowFactory
    public <T extends RealmModel> Flow<ObjectChange<T>> changesetFrom(Realm realm, T realmObject) {
        Intrinsics.checkNotNullParameter(realm, "realm");
        Intrinsics.checkNotNullParameter(realmObject, "realmObject");
        if (realm.isFrozen()) {
            return FlowKt.flowOf(new ObjectChange(realmObject, null));
        }
        return FlowKt.callbackFlow(new AnonymousClass5(realm, realm.getConfiguration(), realmObject, this, null));
    }

    /* JADX INFO: Add missing generic type declarations: [T] */
    /* JADX INFO: renamed from: io.realm.internal.coroutines.InternalFlowFactory$changesetFrom$5, reason: invalid class name */
    /* JADX INFO: compiled from: InternalFlowFactory.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020\u0003*\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u00050\u0004H\u008a@"}, d2 = {"<anonymous>", "", ExifInterface.GPS_DIRECTION_TRUE, "Lio/realm/RealmModel;", "Lkotlinx/coroutines/channels/ProducerScope;", "Lio/realm/rx/ObjectChange;"}, k = 3, mv = {1, 6, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    @DebugMetadata(c = "io.realm.internal.coroutines.InternalFlowFactory$changesetFrom$5", f = "InternalFlowFactory.kt", i = {}, l = {569, 597}, m = "invokeSuspend", n = {}, s = {})
    static final class AnonymousClass5<T> extends SuspendLambda implements Function2<ProducerScope<? super ObjectChange<T>>, Continuation<? super Unit>, Object> {
        final /* synthetic */ RealmConfiguration $config;
        final /* synthetic */ Realm $realm;

        /* JADX INFO: Incorrect field signature: TT; */
        final /* synthetic */ RealmModel $realmObject;
        private /* synthetic */ Object L$0;
        int label;
        final /* synthetic */ InternalFlowFactory this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Incorrect types in method signature: (Lio/realm/Realm;Lio/realm/RealmConfiguration;TT;Lio/realm/internal/coroutines/InternalFlowFactory;Lkotlin/coroutines/Continuation<-Lio/realm/internal/coroutines/InternalFlowFactory$changesetFrom$5;>;)V */
        AnonymousClass5(Realm realm, RealmConfiguration realmConfiguration, RealmModel realmModel, InternalFlowFactory internalFlowFactory, Continuation continuation) {
            super(2, continuation);
            this.$realm = realm;
            this.$config = realmConfiguration;
            this.$realmObject = realmModel;
            this.this$0 = internalFlowFactory;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            AnonymousClass5 anonymousClass5 = new AnonymousClass5(this.$realm, this.$config, this.$realmObject, this.this$0, continuation);
            anonymousClass5.L$0 = obj;
            return anonymousClass5;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(ProducerScope<? super ObjectChange<T>> producerScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass5) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code restructure failed: missing block: B:13:0x003b, code lost:
        
            if (kotlinx.coroutines.channels.ProduceKt.awaitClose(r8, io.realm.internal.coroutines.InternalFlowFactory.AnonymousClass5.AnonymousClass1.INSTANCE, r7) == r0) goto L25;
         */
        /* JADX WARN: Code restructure failed: missing block: B:24:0x008f, code lost:
        
            if (kotlinx.coroutines.channels.ProduceKt.awaitClose(r8, new io.realm.internal.coroutines.InternalFlowFactory.AnonymousClass5.AnonymousClass2(), r7) == r0) goto L25;
         */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r8) throws java.lang.Throwable {
            /*
                r7 = this;
                java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                int r1 = r7.label
                r2 = 2
                r3 = 1
                if (r1 == 0) goto L1f
                if (r1 == r3) goto L1b
                if (r1 != r2) goto L13
                kotlin.ResultKt.throwOnFailure(r8)
                goto L92
            L13:
                java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
                java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
                r8.<init>(r0)
                throw r8
            L1b:
                kotlin.ResultKt.throwOnFailure(r8)
                goto L3e
            L1f:
                kotlin.ResultKt.throwOnFailure(r8)
                java.lang.Object r8 = r7.L$0
                kotlinx.coroutines.channels.ProducerScope r8 = (kotlinx.coroutines.channels.ProducerScope) r8
                io.realm.Realm r1 = r7.$realm
                boolean r1 = r1.isClosed()
                if (r1 == 0) goto L41
                io.realm.internal.coroutines.InternalFlowFactory$changesetFrom$5$1 r1 = new kotlin.jvm.functions.Function0<kotlin.Unit>() { // from class: io.realm.internal.coroutines.InternalFlowFactory.changesetFrom.5.1
                    static {
                        /*
                            io.realm.internal.coroutines.InternalFlowFactory$changesetFrom$5$1 r0 = new io.realm.internal.coroutines.InternalFlowFactory$changesetFrom$5$1
                            r0.<init>()
                            
                            // error: 0x0005: SPUT (r0 I:io.realm.internal.coroutines.InternalFlowFactory$changesetFrom$5$1) io.realm.internal.coroutines.InternalFlowFactory.changesetFrom.5.1.INSTANCE io.realm.internal.coroutines.InternalFlowFactory$changesetFrom$5$1
                            return
                        */
                        throw new UnsupportedOperationException("Method not decompiled: io.realm.internal.coroutines.InternalFlowFactory.AnonymousClass5.AnonymousClass1.<clinit>():void");
                    }

                    {
                        /*
                            r1 = this;
                            r0 = 0
                            r1.<init>(r0)
                            return
                        */
                        throw new UnsupportedOperationException("Method not decompiled: io.realm.internal.coroutines.InternalFlowFactory.AnonymousClass5.AnonymousClass1.<init>():void");
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2() {
                        /*
                            r0 = this;
                            return
                        */
                        throw new UnsupportedOperationException("Method not decompiled: io.realm.internal.coroutines.InternalFlowFactory.AnonymousClass5.AnonymousClass1.invoke2():void");
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public /* bridge */ /* synthetic */ kotlin.Unit invoke() {
                        /*
                            r1 = this;
                            r1.invoke2()
                            kotlin.Unit r0 = kotlin.Unit.INSTANCE
                            return r0
                        */
                        throw new UnsupportedOperationException("Method not decompiled: io.realm.internal.coroutines.InternalFlowFactory.AnonymousClass5.AnonymousClass1.invoke():java.lang.Object");
                    }
                }
                kotlin.jvm.functions.Function0 r1 = (kotlin.jvm.functions.Function0) r1
                r2 = r7
                kotlin.coroutines.Continuation r2 = (kotlin.coroutines.Continuation) r2
                r7.label = r3
                java.lang.Object r8 = kotlinx.coroutines.channels.ProduceKt.awaitClose(r8, r1, r2)
                if (r8 != r0) goto L3e
                goto L91
            L3e:
                kotlin.Unit r8 = kotlin.Unit.INSTANCE
                return r8
            L41:
                io.realm.RealmConfiguration r1 = r7.$config
                io.realm.Realm r1 = io.realm.Realm.getInstance(r1)
                io.realm.internal.coroutines.InternalFlowFactory r3 = r7.this$0
                io.realm.internal.coroutines.InternalFlowFactory$changesetFrom$5$$ExternalSyntheticLambda0 r4 = new io.realm.internal.coroutines.InternalFlowFactory$changesetFrom$5$$ExternalSyntheticLambda0
                r4.<init>()
                io.realm.RealmModel r3 = r7.$realmObject
                io.realm.RealmObject.addChangeListener(r3, r4)
                io.realm.RealmModel r3 = r7.$realmObject
                boolean r3 = io.realm.RealmObject.isLoaded(r3)
                if (r3 == 0) goto L7d
                io.realm.internal.coroutines.InternalFlowFactory r3 = r7.this$0
                boolean r3 = io.realm.internal.coroutines.InternalFlowFactory.access$getReturnFrozenObjects$p(r3)
                r5 = 0
                if (r3 == 0) goto L73
                io.realm.rx.ObjectChange r3 = new io.realm.rx.ObjectChange
                io.realm.RealmModel r6 = r7.$realmObject
                io.realm.RealmModel r6 = io.realm.RealmObject.freeze(r6)
                r3.<init>(r6, r5)
                r8.mo2903trySendJP2dKIU(r3)
                goto L7d
            L73:
                io.realm.rx.ObjectChange r3 = new io.realm.rx.ObjectChange
                io.realm.RealmModel r6 = r7.$realmObject
                r3.<init>(r6, r5)
                r8.mo2903trySendJP2dKIU(r3)
            L7d:
                io.realm.internal.coroutines.InternalFlowFactory$changesetFrom$5$2 r3 = new io.realm.internal.coroutines.InternalFlowFactory$changesetFrom$5$2
                io.realm.RealmModel r5 = r7.$realmObject
                r3.<init>()
                kotlin.jvm.functions.Function0 r3 = (kotlin.jvm.functions.Function0) r3
                r1 = r7
                kotlin.coroutines.Continuation r1 = (kotlin.coroutines.Continuation) r1
                r7.label = r2
                java.lang.Object r8 = kotlinx.coroutines.channels.ProduceKt.awaitClose(r8, r3, r1)
                if (r8 != r0) goto L92
            L91:
                return r0
            L92:
                kotlin.Unit r8 = kotlin.Unit.INSTANCE
                return r8
            */
            throw new UnsupportedOperationException("Method not decompiled: io.realm.internal.coroutines.InternalFlowFactory.AnonymousClass5.invokeSuspend(java.lang.Object):java.lang.Object");
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX INFO: renamed from: invokeSuspend$lambda-0, reason: not valid java name */
        public static final void m1295invokeSuspend$lambda0(ProducerScope producerScope, InternalFlowFactory internalFlowFactory, RealmModel realmModel, ObjectChangeSet objectChangeSet) {
            if (CoroutineScopeKt.isActive(producerScope)) {
                if (internalFlowFactory.returnFrozenObjects) {
                    producerScope.mo2903trySendJP2dKIU(new ObjectChange(RealmObject.freeze(realmModel), objectChangeSet));
                } else {
                    producerScope.mo2903trySendJP2dKIU(new ObjectChange(realmModel, objectChangeSet));
                }
            }
        }
    }

    @Override // io.realm.coroutines.FlowFactory
    public Flow<DynamicRealmObject> from(DynamicRealm dynamicRealm, DynamicRealmObject dynamicRealmObject) {
        Intrinsics.checkNotNullParameter(dynamicRealm, "dynamicRealm");
        Intrinsics.checkNotNullParameter(dynamicRealmObject, "dynamicRealmObject");
        if (dynamicRealm.isFrozen()) {
            return FlowKt.flowOf(dynamicRealmObject);
        }
        return FlowKt.callbackFlow(new AnonymousClass8(dynamicRealm, dynamicRealm.getConfiguration(), dynamicRealmObject, this, null));
    }

    /* JADX INFO: renamed from: io.realm.internal.coroutines.InternalFlowFactory$from$8, reason: invalid class name */
    /* JADX INFO: compiled from: InternalFlowFactory.kt */
    @Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00030\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/channels/ProducerScope;", "Lio/realm/DynamicRealmObject;"}, k = 3, mv = {1, 6, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    @DebugMetadata(c = "io.realm.internal.coroutines.InternalFlowFactory$from$8", f = "InternalFlowFactory.kt", i = {}, l = {622, 650}, m = "invokeSuspend", n = {}, s = {})
    static final class AnonymousClass8 extends SuspendLambda implements Function2<ProducerScope<? super DynamicRealmObject>, Continuation<? super Unit>, Object> {
        final /* synthetic */ RealmConfiguration $config;
        final /* synthetic */ DynamicRealm $dynamicRealm;
        final /* synthetic */ DynamicRealmObject $dynamicRealmObject;
        private /* synthetic */ Object L$0;
        int label;
        final /* synthetic */ InternalFlowFactory this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass8(DynamicRealm dynamicRealm, RealmConfiguration realmConfiguration, DynamicRealmObject dynamicRealmObject, InternalFlowFactory internalFlowFactory, Continuation<? super AnonymousClass8> continuation) {
            super(2, continuation);
            this.$dynamicRealm = dynamicRealm;
            this.$config = realmConfiguration;
            this.$dynamicRealmObject = dynamicRealmObject;
            this.this$0 = internalFlowFactory;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            AnonymousClass8 anonymousClass8 = new AnonymousClass8(this.$dynamicRealm, this.$config, this.$dynamicRealmObject, this.this$0, continuation);
            anonymousClass8.L$0 = obj;
            return anonymousClass8;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(ProducerScope<? super DynamicRealmObject> producerScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass8) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code restructure failed: missing block: B:13:0x003b, code lost:
        
            if (kotlinx.coroutines.channels.ProduceKt.awaitClose(r7, io.realm.internal.coroutines.InternalFlowFactory.AnonymousClass8.AnonymousClass1.INSTANCE, r6) == r0) goto L25;
         */
        /* JADX WARN: Code restructure failed: missing block: B:24:0x008d, code lost:
        
            if (kotlinx.coroutines.channels.ProduceKt.awaitClose(r7, new io.realm.internal.coroutines.InternalFlowFactory.AnonymousClass8.AnonymousClass2(), r6) == r0) goto L25;
         */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r7) throws java.lang.Throwable {
            /*
                r6 = this;
                java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                int r1 = r6.label
                r2 = 2
                r3 = 1
                if (r1 == 0) goto L1f
                if (r1 == r3) goto L1b
                if (r1 != r2) goto L13
                kotlin.ResultKt.throwOnFailure(r7)
                goto L90
            L13:
                java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
                java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
                r7.<init>(r0)
                throw r7
            L1b:
                kotlin.ResultKt.throwOnFailure(r7)
                goto L3e
            L1f:
                kotlin.ResultKt.throwOnFailure(r7)
                java.lang.Object r7 = r6.L$0
                kotlinx.coroutines.channels.ProducerScope r7 = (kotlinx.coroutines.channels.ProducerScope) r7
                io.realm.DynamicRealm r1 = r6.$dynamicRealm
                boolean r1 = r1.isClosed()
                if (r1 == 0) goto L41
                io.realm.internal.coroutines.InternalFlowFactory$from$8$1 r1 = new kotlin.jvm.functions.Function0<kotlin.Unit>() { // from class: io.realm.internal.coroutines.InternalFlowFactory.from.8.1
                    static {
                        /*
                            io.realm.internal.coroutines.InternalFlowFactory$from$8$1 r0 = new io.realm.internal.coroutines.InternalFlowFactory$from$8$1
                            r0.<init>()
                            
                            // error: 0x0005: SPUT (r0 I:io.realm.internal.coroutines.InternalFlowFactory$from$8$1) io.realm.internal.coroutines.InternalFlowFactory.from.8.1.INSTANCE io.realm.internal.coroutines.InternalFlowFactory$from$8$1
                            return
                        */
                        throw new UnsupportedOperationException("Method not decompiled: io.realm.internal.coroutines.InternalFlowFactory.AnonymousClass8.AnonymousClass1.<clinit>():void");
                    }

                    {
                        /*
                            r1 = this;
                            r0 = 0
                            r1.<init>(r0)
                            return
                        */
                        throw new UnsupportedOperationException("Method not decompiled: io.realm.internal.coroutines.InternalFlowFactory.AnonymousClass8.AnonymousClass1.<init>():void");
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2() {
                        /*
                            r0 = this;
                            return
                        */
                        throw new UnsupportedOperationException("Method not decompiled: io.realm.internal.coroutines.InternalFlowFactory.AnonymousClass8.AnonymousClass1.invoke2():void");
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public /* bridge */ /* synthetic */ kotlin.Unit invoke() {
                        /*
                            r1 = this;
                            r1.invoke2()
                            kotlin.Unit r0 = kotlin.Unit.INSTANCE
                            return r0
                        */
                        throw new UnsupportedOperationException("Method not decompiled: io.realm.internal.coroutines.InternalFlowFactory.AnonymousClass8.AnonymousClass1.invoke():java.lang.Object");
                    }
                }
                kotlin.jvm.functions.Function0 r1 = (kotlin.jvm.functions.Function0) r1
                r2 = r6
                kotlin.coroutines.Continuation r2 = (kotlin.coroutines.Continuation) r2
                r6.label = r3
                java.lang.Object r7 = kotlinx.coroutines.channels.ProduceKt.awaitClose(r7, r1, r2)
                if (r7 != r0) goto L3e
                goto L8f
            L3e:
                kotlin.Unit r7 = kotlin.Unit.INSTANCE
                return r7
            L41:
                io.realm.RealmConfiguration r1 = r6.$config
                io.realm.DynamicRealm r1 = io.realm.DynamicRealm.getInstance(r1)
                io.realm.internal.coroutines.InternalFlowFactory r3 = r6.this$0
                io.realm.internal.coroutines.InternalFlowFactory$from$8$$ExternalSyntheticLambda0 r4 = new io.realm.internal.coroutines.InternalFlowFactory$from$8$$ExternalSyntheticLambda0
                r4.<init>()
                io.realm.DynamicRealmObject r3 = r6.$dynamicRealmObject
                r3.addChangeListener(r4)
                io.realm.DynamicRealmObject r3 = r6.$dynamicRealmObject
                io.realm.RealmModel r3 = (io.realm.RealmModel) r3
                boolean r3 = io.realm.RealmObject.isLoaded(r3)
                if (r3 == 0) goto L7b
                io.realm.internal.coroutines.InternalFlowFactory r3 = r6.this$0
                boolean r3 = io.realm.internal.coroutines.InternalFlowFactory.access$getReturnFrozenObjects$p(r3)
                if (r3 == 0) goto L76
                io.realm.DynamicRealmObject r3 = r6.$dynamicRealmObject
                io.realm.RealmModel r3 = (io.realm.RealmModel) r3
                io.realm.RealmModel r3 = io.realm.RealmObject.freeze(r3)
                java.lang.String r5 = "freeze(dynamicRealmObject)"
                kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r3, r5)
                r7.mo2903trySendJP2dKIU(r3)
                goto L7b
            L76:
                io.realm.DynamicRealmObject r3 = r6.$dynamicRealmObject
                r7.mo2903trySendJP2dKIU(r3)
            L7b:
                io.realm.internal.coroutines.InternalFlowFactory$from$8$2 r3 = new io.realm.internal.coroutines.InternalFlowFactory$from$8$2
                io.realm.DynamicRealmObject r5 = r6.$dynamicRealmObject
                r3.<init>()
                kotlin.jvm.functions.Function0 r3 = (kotlin.jvm.functions.Function0) r3
                r1 = r6
                kotlin.coroutines.Continuation r1 = (kotlin.coroutines.Continuation) r1
                r6.label = r2
                java.lang.Object r7 = kotlinx.coroutines.channels.ProduceKt.awaitClose(r7, r3, r1)
                if (r7 != r0) goto L90
            L8f:
                return r0
            L90:
                kotlin.Unit r7 = kotlin.Unit.INSTANCE
                return r7
            */
            throw new UnsupportedOperationException("Method not decompiled: io.realm.internal.coroutines.InternalFlowFactory.AnonymousClass8.invokeSuspend(java.lang.Object):java.lang.Object");
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX INFO: renamed from: invokeSuspend$lambda-0, reason: not valid java name */
        public static final void m1308invokeSuspend$lambda0(ProducerScope producerScope, InternalFlowFactory internalFlowFactory, DynamicRealmObject dynamicRealmObject) {
            if (CoroutineScopeKt.isActive(producerScope)) {
                if (internalFlowFactory.returnFrozenObjects) {
                    RealmModel realmModelFreeze = dynamicRealmObject.freeze();
                    Intrinsics.checkNotNullExpressionValue(realmModelFreeze, "listenerObj.freeze()");
                    producerScope.mo2903trySendJP2dKIU(realmModelFreeze);
                    return;
                }
                producerScope.mo2903trySendJP2dKIU(dynamicRealmObject);
            }
        }
    }

    @Override // io.realm.coroutines.FlowFactory
    public Flow<ObjectChange<DynamicRealmObject>> changesetFrom(DynamicRealm dynamicRealm, DynamicRealmObject dynamicRealmObject) {
        Intrinsics.checkNotNullParameter(dynamicRealm, "dynamicRealm");
        Intrinsics.checkNotNullParameter(dynamicRealmObject, "dynamicRealmObject");
        if (dynamicRealm.isFrozen()) {
            return FlowKt.flowOf(new ObjectChange(dynamicRealmObject, null));
        }
        return FlowKt.callbackFlow(new AnonymousClass6(dynamicRealmObject, dynamicRealm.getConfiguration(), this, null));
    }

    /* JADX INFO: renamed from: io.realm.internal.coroutines.InternalFlowFactory$changesetFrom$6, reason: invalid class name */
    /* JADX INFO: compiled from: InternalFlowFactory.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/channels/ProducerScope;", "Lio/realm/rx/ObjectChange;", "Lio/realm/DynamicRealmObject;"}, k = 3, mv = {1, 6, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    @DebugMetadata(c = "io.realm.internal.coroutines.InternalFlowFactory$changesetFrom$6", f = "InternalFlowFactory.kt", i = {}, l = {674, TypedValues.TransitionType.TYPE_TO}, m = "invokeSuspend", n = {}, s = {})
    static final class AnonymousClass6 extends SuspendLambda implements Function2<ProducerScope<? super ObjectChange<DynamicRealmObject>>, Continuation<? super Unit>, Object> {
        final /* synthetic */ RealmConfiguration $config;
        final /* synthetic */ DynamicRealmObject $dynamicRealmObject;
        private /* synthetic */ Object L$0;
        int label;
        final /* synthetic */ InternalFlowFactory this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass6(DynamicRealmObject dynamicRealmObject, RealmConfiguration realmConfiguration, InternalFlowFactory internalFlowFactory, Continuation<? super AnonymousClass6> continuation) {
            super(2, continuation);
            this.$dynamicRealmObject = dynamicRealmObject;
            this.$config = realmConfiguration;
            this.this$0 = internalFlowFactory;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            AnonymousClass6 anonymousClass6 = new AnonymousClass6(this.$dynamicRealmObject, this.$config, this.this$0, continuation);
            anonymousClass6.L$0 = obj;
            return anonymousClass6;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(ProducerScope<? super ObjectChange<DynamicRealmObject>> producerScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass6) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code restructure failed: missing block: B:13:0x003d, code lost:
        
            if (kotlinx.coroutines.channels.ProduceKt.awaitClose(r8, io.realm.internal.coroutines.InternalFlowFactory.AnonymousClass6.AnonymousClass1.INSTANCE, r7) == r0) goto L25;
         */
        /* JADX WARN: Code restructure failed: missing block: B:24:0x0099, code lost:
        
            if (kotlinx.coroutines.channels.ProduceKt.awaitClose(r8, new io.realm.internal.coroutines.InternalFlowFactory.AnonymousClass6.AnonymousClass2(), r7) == r0) goto L25;
         */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r8) throws java.lang.Throwable {
            /*
                r7 = this;
                java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                int r1 = r7.label
                r2 = 2
                r3 = 1
                if (r1 == 0) goto L1f
                if (r1 == r3) goto L1b
                if (r1 != r2) goto L13
                kotlin.ResultKt.throwOnFailure(r8)
                goto L9c
            L13:
                java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
                java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
                r8.<init>(r0)
                throw r8
            L1b:
                kotlin.ResultKt.throwOnFailure(r8)
                goto L40
            L1f:
                kotlin.ResultKt.throwOnFailure(r8)
                java.lang.Object r8 = r7.L$0
                kotlinx.coroutines.channels.ProducerScope r8 = (kotlinx.coroutines.channels.ProducerScope) r8
                io.realm.DynamicRealmObject r1 = r7.$dynamicRealmObject
                io.realm.RealmModel r1 = (io.realm.RealmModel) r1
                boolean r1 = io.realm.RealmObject.isValid(r1)
                if (r1 != 0) goto L43
                io.realm.internal.coroutines.InternalFlowFactory$changesetFrom$6$1 r1 = new kotlin.jvm.functions.Function0<kotlin.Unit>() { // from class: io.realm.internal.coroutines.InternalFlowFactory.changesetFrom.6.1
                    static {
                        /*
                            io.realm.internal.coroutines.InternalFlowFactory$changesetFrom$6$1 r0 = new io.realm.internal.coroutines.InternalFlowFactory$changesetFrom$6$1
                            r0.<init>()
                            
                            // error: 0x0005: SPUT (r0 I:io.realm.internal.coroutines.InternalFlowFactory$changesetFrom$6$1) io.realm.internal.coroutines.InternalFlowFactory.changesetFrom.6.1.INSTANCE io.realm.internal.coroutines.InternalFlowFactory$changesetFrom$6$1
                            return
                        */
                        throw new UnsupportedOperationException("Method not decompiled: io.realm.internal.coroutines.InternalFlowFactory.AnonymousClass6.AnonymousClass1.<clinit>():void");
                    }

                    {
                        /*
                            r1 = this;
                            r0 = 0
                            r1.<init>(r0)
                            return
                        */
                        throw new UnsupportedOperationException("Method not decompiled: io.realm.internal.coroutines.InternalFlowFactory.AnonymousClass6.AnonymousClass1.<init>():void");
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2() {
                        /*
                            r0 = this;
                            return
                        */
                        throw new UnsupportedOperationException("Method not decompiled: io.realm.internal.coroutines.InternalFlowFactory.AnonymousClass6.AnonymousClass1.invoke2():void");
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public /* bridge */ /* synthetic */ kotlin.Unit invoke() {
                        /*
                            r1 = this;
                            r1.invoke2()
                            kotlin.Unit r0 = kotlin.Unit.INSTANCE
                            return r0
                        */
                        throw new UnsupportedOperationException("Method not decompiled: io.realm.internal.coroutines.InternalFlowFactory.AnonymousClass6.AnonymousClass1.invoke():java.lang.Object");
                    }
                }
                kotlin.jvm.functions.Function0 r1 = (kotlin.jvm.functions.Function0) r1
                r2 = r7
                kotlin.coroutines.Continuation r2 = (kotlin.coroutines.Continuation) r2
                r7.label = r3
                java.lang.Object r8 = kotlinx.coroutines.channels.ProduceKt.awaitClose(r8, r1, r2)
                if (r8 != r0) goto L40
                goto L9b
            L40:
                kotlin.Unit r8 = kotlin.Unit.INSTANCE
                return r8
            L43:
                io.realm.RealmConfiguration r1 = r7.$config
                io.realm.Realm r1 = io.realm.Realm.getInstance(r1)
                io.realm.internal.coroutines.InternalFlowFactory r3 = r7.this$0
                io.realm.internal.coroutines.InternalFlowFactory$changesetFrom$6$$ExternalSyntheticLambda0 r4 = new io.realm.internal.coroutines.InternalFlowFactory$changesetFrom$6$$ExternalSyntheticLambda0
                r4.<init>()
                io.realm.DynamicRealmObject r3 = r7.$dynamicRealmObject
                io.realm.RealmModel r3 = (io.realm.RealmModel) r3
                io.realm.RealmObject.addChangeListener(r3, r4)
                io.realm.DynamicRealmObject r3 = r7.$dynamicRealmObject
                io.realm.RealmModel r3 = (io.realm.RealmModel) r3
                boolean r3 = io.realm.RealmObject.isLoaded(r3)
                if (r3 == 0) goto L87
                io.realm.internal.coroutines.InternalFlowFactory r3 = r7.this$0
                boolean r3 = io.realm.internal.coroutines.InternalFlowFactory.access$getReturnFrozenObjects$p(r3)
                r5 = 0
                if (r3 == 0) goto L7b
                io.realm.rx.ObjectChange r3 = new io.realm.rx.ObjectChange
                io.realm.DynamicRealmObject r6 = r7.$dynamicRealmObject
                io.realm.RealmModel r6 = (io.realm.RealmModel) r6
                io.realm.RealmModel r6 = io.realm.RealmObject.freeze(r6)
                r3.<init>(r6, r5)
                r8.mo2903trySendJP2dKIU(r3)
                goto L87
            L7b:
                io.realm.rx.ObjectChange r3 = new io.realm.rx.ObjectChange
                io.realm.DynamicRealmObject r6 = r7.$dynamicRealmObject
                io.realm.RealmModel r6 = (io.realm.RealmModel) r6
                r3.<init>(r6, r5)
                r8.mo2903trySendJP2dKIU(r3)
            L87:
                io.realm.internal.coroutines.InternalFlowFactory$changesetFrom$6$2 r3 = new io.realm.internal.coroutines.InternalFlowFactory$changesetFrom$6$2
                io.realm.DynamicRealmObject r5 = r7.$dynamicRealmObject
                r3.<init>()
                kotlin.jvm.functions.Function0 r3 = (kotlin.jvm.functions.Function0) r3
                r1 = r7
                kotlin.coroutines.Continuation r1 = (kotlin.coroutines.Continuation) r1
                r7.label = r2
                java.lang.Object r8 = kotlinx.coroutines.channels.ProduceKt.awaitClose(r8, r3, r1)
                if (r8 != r0) goto L9c
            L9b:
                return r0
            L9c:
                kotlin.Unit r8 = kotlin.Unit.INSTANCE
                return r8
            */
            throw new UnsupportedOperationException("Method not decompiled: io.realm.internal.coroutines.InternalFlowFactory.AnonymousClass6.invokeSuspend(java.lang.Object):java.lang.Object");
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX INFO: renamed from: invokeSuspend$lambda-0, reason: not valid java name */
        public static final void m1296invokeSuspend$lambda0(ProducerScope producerScope, InternalFlowFactory internalFlowFactory, DynamicRealmObject dynamicRealmObject, ObjectChangeSet objectChangeSet) {
            if (CoroutineScopeKt.isActive(producerScope)) {
                if (internalFlowFactory.returnFrozenObjects) {
                    producerScope.mo2903trySendJP2dKIU(new ObjectChange(RealmObject.freeze(dynamicRealmObject), objectChangeSet));
                } else {
                    producerScope.mo2903trySendJP2dKIU(new ObjectChange(dynamicRealmObject, objectChangeSet));
                }
            }
        }
    }
}
