package com.brgd.brblmesh.GeneralClass.ble.bleTool;

import android.os.Handler;
import android.util.Log;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.Request;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.callback.BeforeCallback;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.callback.FailCallback;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.callback.InvalidRequestCallback;
import com.brgd.brblmesh.GeneralClass.ble.bleTool.callback.SuccessCallback;

/* JADX INFO: loaded from: classes.dex */
public final class ConditionalWaitRequest<T> extends AwaitingRequest<T> implements Operation {
    private final Condition<T> condition;
    private boolean expected;
    private final T parameter;

    public interface Condition<T> {
        boolean predicate(T t);
    }

    ConditionalWaitRequest(Request.Type type, Condition<T> condition, T t) {
        super(type);
        this.expected = false;
        this.condition = condition;
        this.parameter = t;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.TimeoutableRequest, com.brgd.brblmesh.GeneralClass.ble.bleTool.Request
    public ConditionalWaitRequest<T> setRequestHandler(RequestHandler requestHandler) {
        super.setRequestHandler(requestHandler);
        return this;
    }

    @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.TimeoutableRequest, com.brgd.brblmesh.GeneralClass.ble.bleTool.Request
    public ConditionalWaitRequest<T> setHandler(Handler handler) {
        super.setHandler(handler);
        return this;
    }

    @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.Request
    public ConditionalWaitRequest<T> done(SuccessCallback successCallback) {
        super.done(successCallback);
        return this;
    }

    @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.Request
    public ConditionalWaitRequest<T> fail(FailCallback failCallback) {
        super.fail(failCallback);
        return this;
    }

    @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.Request
    public ConditionalWaitRequest<T> invalid(InvalidRequestCallback invalidRequestCallback) {
        super.invalid(invalidRequestCallback);
        return this;
    }

    @Override // com.brgd.brblmesh.GeneralClass.ble.bleTool.Request
    public ConditionalWaitRequest<T> before(BeforeCallback beforeCallback) {
        super.before(beforeCallback);
        return this;
    }

    public ConditionalWaitRequest<T> negate() {
        this.expected = true;
        return this;
    }

    boolean isFulfilled() {
        try {
            return this.condition.predicate(this.parameter) == this.expected;
        } catch (Exception e) {
            Log.e("ConditionalWaitRequest", "Error while checking predicate", e);
            return true;
        }
    }
}
