package lsxiao.com.zhihudailyrrd;

import android.app.Application;

import lsxiao.com.zhihudailyrrd.inject.component.DaggerDataLayerComponent;
import lsxiao.com.zhihudailyrrd.inject.component.DataLayerComponent;
import lsxiao.com.zhihudailyrrd.service.impl.DataLayerManager;

/**
 * @author lsxiao
 * @date 2015-11-03 22:24
 */
public class App extends Application {
    public App() {
        DataLayerManager.init();
        DataLayerComponent.Instance.init(DaggerDataLayerComponent.builder().build());
    }
}
