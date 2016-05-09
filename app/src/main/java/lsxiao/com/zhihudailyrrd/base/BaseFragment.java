package lsxiao.com.zhihudailyrrd.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle.components.support.RxFragment;

import javax.inject.Inject;

import butterknife.ButterKnife;
import lsxiao.com.zhihudailyrrd.flux.action.creator.ActionCreatorLayer;
import lsxiao.com.zhihudailyrrd.flux.dispatcher.Dispatcher;
import lsxiao.com.zhihudailyrrd.inject.component.ApplicationComponent;
import lsxiao.com.zhihudailyrrd.service.DataLayer;

/**
 * @author lsxiao
 * @date 2015-11-03 22:28
 */
public abstract class BaseFragment extends RxFragment {
    public static final String TAG = BaseFragment.class.getSimpleName();
    protected View mRootView;
    @Inject
    DataLayer mDataLayer;
    @Inject
    Dispatcher mDispatcher;
//    @Inject
    ActionCreatorLayer mActionCreatorLayer;

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
        afterCreate(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public DataLayer getDataLayer() {
        return mDataLayer;
    }

    public Dispatcher getDispatcher() {
        return mDispatcher;
    }

    public ActionCreatorLayer getActionCreatorLayer() {
        return mActionCreatorLayer;
    }

    protected abstract int getLayoutId();

    protected abstract void afterCreate(Bundle savedInstanceState);

}
