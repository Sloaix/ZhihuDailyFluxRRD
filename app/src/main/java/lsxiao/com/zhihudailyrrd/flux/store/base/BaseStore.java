package lsxiao.com.zhihudailyrrd.flux.store.base;

import android.view.View;

import com.lsxiao.apllo.Apollo;

import java.io.Serializable;

import lsxiao.com.zhihudailyrrd.flux.action.base.BaseAction;

/**
 * Store基类,所有Store都需要继承此类,Store主要对View的状态进行管理,以及发送Store改变事件给View
 * author lsxiao
 * date 2016-05-09 17:25
 */
public abstract class BaseStore implements Serializable {
    public static final int VIS = View.VISIBLE;
    public static final int GONE = View.GONE;
    public static final int INVIS = View.INVISIBLE;

    public abstract void onAction(BaseAction action);

    public ChangeEvent mChangeEvent;
    public String mTag;

    /**
     * 发送Store改变事件,View接收到后进行相应的render
     */
    protected void emitStoreChange() {
        Apollo.get().send(mTag, getChangeEvent());
    }

    protected abstract ChangeEvent getChangeEvent();

    public interface ChangeEvent extends Serializable {
    }
}
