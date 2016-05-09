package lsxiao.com.zhihudailyrrd.flux.store;

import lsxiao.com.zhihudailyrrd.flux.action.base.BaseAction;
import lsxiao.com.zhihudailyrrd.flux.store.base.BaseStore;

/**
 * author lsxiao
 * date 2016-05-09 17:26
 */
public class NewsDetailStore extends BaseStore {
    @Override
    public void onAction(BaseAction baseAction) {
        emitStoreChange();
    }

    @Override
    protected ChangeEvent getChangeEvent() {
        return null;
    }
}
