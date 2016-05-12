package lsxiao.com.zhihudailyrrd.inject.module;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * author lsxiao
 * date 2016-05-09 20:06
 */
@Module(includes = {DataLayerModule.class, ClientAPIModule.class, FluxModule.class})
public class ApplicationModule {
    Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    /**
     * 提供Application单例对象
     *
     * @return Application
     */
    @Singleton
    @Provides
    public Application provideApplication() {
        return mApplication;
    }
}
