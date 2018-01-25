package net.doyouhike.app.bbs.base.util;

/**
 * Created by Hawaken on 2016/3/18.
 */
public class BaseAsyncTaskResult<T> {
    private T result;
    private Throwable error;

    public BaseAsyncTaskResult() {
    }

    public BaseAsyncTaskResult(T result) {
        this.result = result;
    }

    public BaseAsyncTaskResult(Throwable error) {
        this.error = error;
    }

    public boolean hasError() {
        return this.error != null;
    }

    public T getResult() {
        return this.result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public void setError(Exception error) {
        this.error = error;
    }

    public Throwable getError() {
        return this.error;
    }
}
