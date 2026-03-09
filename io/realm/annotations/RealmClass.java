package io.realm.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/* JADX INFO: loaded from: classes4.dex */
@Inherited
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RealmClass {
    boolean embedded() default false;

    RealmNamingPolicy fieldNamingPolicy() default RealmNamingPolicy.NO_POLICY;

    String name() default "";

    String value() default "";
}
