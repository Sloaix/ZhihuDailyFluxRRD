package lsxiao.com.zhihudailyrrd;

import android.app.Application;
import android.preference.PreferenceManager;

import lsxiao.com.zhihudailyrrd.inject.component.AppLayerComponent;
import lsxiao.com.zhihudailyrrd.inject.component.DaggerAppLayerComponent;
import lsxiao.com.zhihudailyrrd.service.impl.DataLayerManager;
import lsxiao.com.zhihudailyrrd.util.AppContextUtil;
import lsxiao.com.zhihudailyrrd.util.SpUtil;

/**
 * @author lsxiao
 * @date 2015-11-03 22:24
 */
public class DailyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AppContextUtil.init(this);
        SpUtil.init(PreferenceManager.getDefaultSharedPreferences(this));
        DataLayerManager.init();
        AppLayerComponent.Instance.init(DaggerAppLayerComponent.builder().build());
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
