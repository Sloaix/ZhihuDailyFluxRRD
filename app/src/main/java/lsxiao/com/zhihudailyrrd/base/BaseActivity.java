package lsxiao.com.zhihudailyrrd.base;

import android.os.Bundle;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import javax.inject.Inject;

import butterknife.ButterKnife;
import lsxiao.com.zhihudailyrrd.flux.action.creator.ActionCreatorManager;
import lsxiao.com.zhihudailyrrd.flux.dispatcher.Dispatcher;
import lsxiao.com.zhihudailyrrd.inject.component.ApplicationComponent;
import lsxiao.com.zhihudailyrrd.service.base.DataLayer;

/**
 * @author lsxiao
 * @date 2015-11-03 22:28
 */
public abstract class BaseActivity extends RxAppCompatActivity {
    @Inject
    DataLayer mDataLayer;
    @Inject
    Dispatcher mDispatcher;
    @Inject
    ActionCreatorManager mActionCreatorManager;

    public BaseActivity() {
        ApplicationComponent.Instance.get().inject(this);
    }

    public DataLayer getDataLayer() {
        return mDataLayer;
    }

    public ActionCreatorManager getActionCreatorManager() {
        return mActionCreatorManager;
    }

    public Dispatcher getDispatcher() {
        return mDispatcher;
    }

    protected abstract int getLayoutId();

    protected abstract void afterCreate(Bundle savedInstanceState);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        afterCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
