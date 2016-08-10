package lsxiao.com.zhihudailyrrd.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lsxiao.apllo.Apollo;
import com.lsxiao.apllo.entity.SubscriptionBinder;
import com.trello.rxlifecycle.components.support.RxFragment;

import javax.inject.Inject;

import butterknife.ButterKnife;
import lsxiao.com.zhihudailyrrd.flux.action.creator.ActionCreatorManager;
import lsxiao.com.zhihudailyrrd.flux.dispatcher.Dispatcher;
import lsxiao.com.zhihudailyrrd.flux.store.base.BaseStore;
import lsxiao.com.zhihudailyrrd.inject.component.ApplicationComponent;

/**
 * @author lsxiao
 * @date 2015-11-03 22:28
 */
public abstract class BaseFragment extends RxFragment {
    public static final String TAG = BaseFragment.class.getSimpleName();
    protected View mRootView;
    @Inject
    Dispatcher mDispatcher;
    @Inject
    ActionCreatorManager mActionCreatorManager;

    private SubscriptionBinder binder;

    public BaseFragment() {
        ApplicationComponent.Instance.get().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(getLayoutId(), container, false);
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        binder = Apollo.get().bind(this);
        getDispatcher().register(getStore());//订阅Action
        afterCreate(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        //取消订阅Action
        getDispatcher().unregister(getStore());
        if (null != binder) {
            binder.unbind();
        }
    }

    public Dispatcher getDispatcher() {
        return mDispatcher;
    }

    public ActionCreatorManager getActionCreatorManager() {
        return mActionCreatorManager;
    }

    protected abstract int getLayoutId();

    protected abstract BaseStore getStore();

    protected abstract void afterCreate(Bundle savedInstanceState);

}
