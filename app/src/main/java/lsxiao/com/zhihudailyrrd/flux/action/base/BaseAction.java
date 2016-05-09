package lsxiao.com.zhihudailyrrd.flux.action.base;

import android.os.Bundle;

/**
 * author lsxiao
 * date 2016-05-09 17:29
 */
public abstract class BaseAction {
    private final String type;
    private final Bundle data;

    protected BaseAction(String type, Bundle data) {
        this.type = type;
        this.data = data;
    }

    protected BaseAction(String type) {
        this(type, null);
    }

    public String getType() {
        return type;
    }

    public Bundle getData() {
        return data;
    }
}
