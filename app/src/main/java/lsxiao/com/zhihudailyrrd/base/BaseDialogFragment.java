package lsxiao.com.zhihudailyrrd.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
public abstract class BaseDialogFragment extends DialogFragment {
    public static final String TAG = BaseDialogFragment.TAG;
    protected View mRootView;

    @Inject
    DataLayer mDataLayer;
    @Inject
    Dispatcher mDispatcher;
    //    @Inject
    ActionCreatorManager mActionCreatorManager;


    public BaseDialogFragment() {
        ApplicationComponent.Instance.get().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(getLayoutId(), container, false);
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        afterCreate(savedInstanceState);
    }

    public DataLayer getDataLayer() {
        return mDataLayer;
    }

    public Dispatcher getDispatcher() {
        return mDispatcher;
    }

    public ActionCreatorManager getActionCreatorManager() {
        return mActionCreatorManager;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    protected abstract int getLayoutId();

    protected abstract void afterCreate(Bundle savedInstanceState);
}
