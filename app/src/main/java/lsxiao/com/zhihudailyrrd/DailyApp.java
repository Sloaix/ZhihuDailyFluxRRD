package lsxiao.com.zhihudailyrrd;

import android.app.Application;

import lsxiao.com.zhihudailyrrd.inject.component.ApplicationComponent;
import lsxiao.com.zhihudailyrrd.inject.component.DaggerApplicationComponent;
import lsxiao.com.zhihudailyrrd.inject.module.ApplicationModule;

/**
 * @author lsxiao
 * @date 2015-11-03 22:24
 */
public class DailyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initDagger();
    }

    private void initDagger() {
        ApplicationComponent.Instance.init(DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(this))
                .build());
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
