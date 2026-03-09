package org.bson.codecs.configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.bson.codecs.Codec;
import org.bson.internal.ProvidersCodecRegistry;

/* JADX INFO: loaded from: classes4.dex */
public final class CodecRegistries {
    public static CodecRegistry fromCodecs(Codec<?>... codecArr) {
        return fromCodecs((List<? extends Codec<?>>) Arrays.asList(codecArr));
    }

    public static CodecRegistry fromCodecs(List<? extends Codec<?>> list) {
        return fromProviders(new MapOfCodecsProvider(list));
    }

    public static CodecRegistry fromProviders(CodecProvider... codecProviderArr) {
        return fromProviders((List<? extends CodecProvider>) Arrays.asList(codecProviderArr));
    }

    public static CodecRegistry fromProviders(List<? extends CodecProvider> list) {
        return new ProvidersCodecRegistry(list);
    }

    public static CodecRegistry fromRegistries(CodecRegistry... codecRegistryArr) {
        return fromRegistries((List<? extends CodecRegistry>) Arrays.asList(codecRegistryArr));
    }

    public static CodecRegistry fromRegistries(List<? extends CodecRegistry> list) {
        ArrayList arrayList = new ArrayList();
        Iterator<? extends CodecRegistry> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(providerFromRegistry(it.next()));
        }
        return new ProvidersCodecRegistry(arrayList);
    }

    private static CodecProvider providerFromRegistry(final CodecRegistry codecRegistry) {
        if (codecRegistry instanceof CodecProvider) {
            return (CodecProvider) codecRegistry;
        }
        return new CodecProvider() { // from class: org.bson.codecs.configuration.CodecRegistries.1
            @Override // org.bson.codecs.configuration.CodecProvider
            public <T> Codec<T> get(Class<T> cls, CodecRegistry codecRegistry2) {
                try {
                    return codecRegistry.get(cls);
                } catch (CodecConfigurationException unused) {
                    return null;
                }
            }
        };
    }

    private CodecRegistries() {
    }
}
