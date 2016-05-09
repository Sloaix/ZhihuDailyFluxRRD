package lsxiao.com.zhihudailyrrd.flux.store.base;

import lsxiao.com.zhihudailyrrd.flux.action.base.BaseAction;
import lsxiao.com.zhihudailyrrd.util.RxBus;

/**
 * author lsxiao
 * date 2016-05-09 17:25
 */
public abstract class BaseStore {
    public abstract void onAction(BaseAction baseAction);

    /**
     * 发送Store改变事件,View接收到后进行相应的render
     */
    protected void emitStoreChange() {
        RxBus.instance().send(getChangeEvent());
    }

    protected abstract ChangeEvent getChangeEvent();

    public interface ChangeEvent {
    }
}
