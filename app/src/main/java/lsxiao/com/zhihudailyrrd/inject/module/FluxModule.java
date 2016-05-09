package lsxiao.com.zhihudailyrrd.inject.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import lsxiao.com.zhihudailyrrd.flux.action.creator.ActionCreatorManager;
import lsxiao.com.zhihudailyrrd.flux.action.creator.NewsActionCreator;
import lsxiao.com.zhihudailyrrd.flux.dispatcher.Dispatcher;
import lsxiao.com.zhihudailyrrd.service.base.DataLayer;

/**
 * @author lsxiao
 * @date 2015-11-04 00:44
 */
@Module
public class FluxModule {


    @Singleton
    @Provides
    public Dispatcher provideDispatcher() {
        return Dispatcher.instance();
    }


    @Singleton
    @Provides
    public NewsActionCreator provideNewsActionCreator(Dispatcher dispatcher, DataLayer dataLayer) {
        return new NewsActionCreator(dispatcher, dataLayer);
    }

    @Singleton
    @Provides
    public ActionCreatorManager provideActionCreatorLayer(NewsActionCreator newsActionCreator) {
        return new ActionCreatorManager(newsActionCreator);
    }

}
