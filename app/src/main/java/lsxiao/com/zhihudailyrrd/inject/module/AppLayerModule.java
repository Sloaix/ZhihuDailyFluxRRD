package lsxiao.com.zhihudailyrrd.inject.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import lsxiao.com.zhihudailyrrd.service.DataLayer;
import lsxiao.com.zhihudailyrrd.service.impl.DailyManager;

/**
 * @author lsxiao
 * @date 2015-11-04 00:44
 */
@Module
public class AppLayerModule {

    @Singleton
    @Provides
    public DataLayer provideDataLayer() {
        return new DataLayer(new DailyManager());
    }
}
