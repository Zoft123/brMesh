package j$.util.stream;

import j$.util.Objects;
import j$.util.Spliterator;
import j$.util.concurrent.ConcurrentHashMap;
import j$.util.function.BiConsumer$CC;
import j$.util.function.Consumer$CC;
import j$.util.stream.DistinctOps;
import j$.util.stream.ReferencePipeline;
import j$.util.stream.Sink;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Supplier;

/* JADX INFO: loaded from: classes3.dex */
abstract class DistinctOps {

    /* JADX INFO: renamed from: j$.util.stream.DistinctOps$1, reason: invalid class name */
    class AnonymousClass1 extends ReferencePipeline.StatefulOp {
        AnonymousClass1(AbstractPipeline abstractPipeline, StreamShape streamShape, int i) {
            super(abstractPipeline, streamShape, i);
        }

        Node reduce(PipelineHelper pipelineHelper, Spliterator spliterator) {
            return Nodes.node((Collection) ReduceOps.makeRef(new Supplier() { // from class: j$.util.stream.DistinctOps$1$$ExternalSyntheticLambda1
                @Override // java.util.function.Supplier
                public final Object get() {
                    return new LinkedHashSet();
                }
            }, new BiConsumer() { // from class: j$.util.stream.DistinctOps$1$$ExternalSyntheticLambda2
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    ((LinkedHashSet) obj).add(obj2);
                }

                public /* synthetic */ BiConsumer andThen(BiConsumer biConsumer) {
                    return BiConsumer$CC.$default$andThen(this, biConsumer);
                }
            }, new BiConsumer() { // from class: j$.util.stream.DistinctOps$1$$ExternalSyntheticLambda3
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    ((LinkedHashSet) obj).addAll((LinkedHashSet) obj2);
                }

                public /* synthetic */ BiConsumer andThen(BiConsumer biConsumer) {
                    return BiConsumer$CC.$default$andThen(this, biConsumer);
                }
            }).evaluateParallel(pipelineHelper, spliterator));
        }

        @Override // j$.util.stream.AbstractPipeline
        Node opEvaluateParallel(PipelineHelper pipelineHelper, Spliterator spliterator, IntFunction intFunction) {
            if (StreamOpFlag.DISTINCT.isKnown(pipelineHelper.getStreamAndOpFlags())) {
                return pipelineHelper.evaluate(spliterator, false, intFunction);
            }
            if (StreamOpFlag.ORDERED.isKnown(pipelineHelper.getStreamAndOpFlags())) {
                return reduce(pipelineHelper, spliterator);
            }
            final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
            final ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
            ForEachOps.makeRef(new Consumer() { // from class: j$.util.stream.DistinctOps$1$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    DistinctOps.AnonymousClass1.lambda$opEvaluateParallel$0(atomicBoolean, concurrentHashMap, obj);
                }

                public /* synthetic */ Consumer andThen(Consumer consumer) {
                    return Consumer$CC.$default$andThen(this, consumer);
                }
            }, false).evaluateParallel(pipelineHelper, spliterator);
            Collection collectionKeySet = concurrentHashMap.keySet();
            if (atomicBoolean.get()) {
                HashSet hashSet = new HashSet(collectionKeySet);
                hashSet.add(null);
                collectionKeySet = hashSet;
            }
            return Nodes.node(collectionKeySet);
        }

        static /* synthetic */ void lambda$opEvaluateParallel$0(AtomicBoolean atomicBoolean, ConcurrentHashMap concurrentHashMap, Object obj) {
            if (obj == null) {
                atomicBoolean.set(true);
            } else {
                concurrentHashMap.putIfAbsent(obj, Boolean.TRUE);
            }
        }

        @Override // j$.util.stream.AbstractPipeline
        Spliterator opEvaluateParallelLazy(PipelineHelper pipelineHelper, Spliterator spliterator) {
            if (StreamOpFlag.DISTINCT.isKnown(pipelineHelper.getStreamAndOpFlags())) {
                return pipelineHelper.wrapSpliterator(spliterator);
            }
            if (StreamOpFlag.ORDERED.isKnown(pipelineHelper.getStreamAndOpFlags())) {
                return reduce(pipelineHelper, spliterator).spliterator();
            }
            return new StreamSpliterators$DistinctSpliterator(pipelineHelper.wrapSpliterator(spliterator));
        }

        @Override // j$.util.stream.AbstractPipeline
        Sink opWrapSink(int i, Sink sink) {
            Objects.requireNonNull(sink);
            if (StreamOpFlag.DISTINCT.isKnown(i)) {
                return sink;
            }
            if (StreamOpFlag.SORTED.isKnown(i)) {
                return new Sink.ChainedReference(sink) { // from class: j$.util.stream.DistinctOps.1.1
                    Object lastSeen;
                    boolean seenNull;

                    @Override // j$.util.stream.Sink.ChainedReference, j$.util.stream.Sink
                    public void begin(long j) {
                        this.seenNull = false;
                        this.lastSeen = null;
                        this.downstream.begin(-1L);
                    }

                    @Override // j$.util.stream.Sink.ChainedReference, j$.util.stream.Sink
                    public void end() {
                        this.seenNull = false;
                        this.lastSeen = null;
                        this.downstream.end();
                    }

                    @Override // java.util.function.Consumer
                    public void accept(Object obj) {
                        if (obj == null) {
                            if (this.seenNull) {
                                return;
                            }
                            this.seenNull = true;
                            Sink sink2 = this.downstream;
                            this.lastSeen = null;
                            sink2.accept((Object) null);
                            return;
                        }
                        Object obj2 = this.lastSeen;
                        if (obj2 == null || !obj.equals(obj2)) {
                            Sink sink3 = this.downstream;
                            this.lastSeen = obj;
                            sink3.accept(obj);
                        }
                    }
                };
            }
            return new Sink.ChainedReference(sink) { // from class: j$.util.stream.DistinctOps.1.2
                Set seen;

                @Override // j$.util.stream.Sink.ChainedReference, j$.util.stream.Sink
                public void begin(long j) {
                    this.seen = new HashSet();
                    this.downstream.begin(-1L);
                }

                @Override // j$.util.stream.Sink.ChainedReference, j$.util.stream.Sink
                public void end() {
                    this.seen = null;
                    this.downstream.end();
                }

                @Override // java.util.function.Consumer
                public void accept(Object obj) {
                    if (this.seen.contains(obj)) {
                        return;
                    }
                    this.seen.add(obj);
                    this.downstream.accept(obj);
                }
            };
        }
    }

    static ReferencePipeline makeRef(AbstractPipeline abstractPipeline) {
        return new AnonymousClass1(abstractPipeline, StreamShape.REFERENCE, StreamOpFlag.IS_DISTINCT | StreamOpFlag.NOT_SIZED);
    }
}
