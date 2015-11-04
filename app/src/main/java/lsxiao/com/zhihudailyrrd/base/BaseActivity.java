package lsxiao.com.zhihudailyrrd.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import butterknife.ButterKnife;
import lsxiao.com.zhihudailyrrd.inject.component.DataLayerComponent;
import lsxiao.com.zhihudailyrrd.service.DataLayer;

/**
 * @author lsxiao
 * @date 2015-11-03 22:28
 */
public abstract class BaseActivity extends AppCompatActivity {
    @Inject
    DataLayer mDataLayer;

    public BaseActivity() {
        DataLayerComponent.Instance.get().inject(this);
    }

    public DataLayer getDataLayer() {
        return mDataLayer;
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
