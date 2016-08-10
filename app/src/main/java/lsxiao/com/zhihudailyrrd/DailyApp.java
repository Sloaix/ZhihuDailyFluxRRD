package lsxiao.com.zhihudailyrrd;

import android.app.Application;

import com.lsxiao.apllo.Apollo;
import com.lsxiao.apollo.generate.SubscriberBinderImplement;

import lsxiao.com.zhihudailyrrd.inject.component.ApplicationComponent;
import lsxiao.com.zhihudailyrrd.inject.component.DaggerApplicationComponent;
import lsxiao.com.zhihudailyrrd.inject.module.ApplicationModule;
import rx.android.schedulers.AndroidSchedulers;

/**
 * @author lsxiao
 * @date 2015-11-03 22:24
 */
public class DailyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initDagger();
        initApollo();
    }

    private void initDagger() {
        ApplicationComponent.Instance.init(DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(this))
                .build());
    }

    private void initApollo() {
        Apollo.get().init(SubscriberBinderImplement.instance(), AndroidSchedulers.mainThread());
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
