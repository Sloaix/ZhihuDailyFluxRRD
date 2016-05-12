package lsxiao.com.zhihudailyrrd.inject.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import lsxiao.com.zhihudailyrrd.flux.action.creator.ActionCreatorManager;
import lsxiao.com.zhihudailyrrd.flux.action.creator.NewsActionCreator;
import lsxiao.com.zhihudailyrrd.flux.dispatcher.Dispatcher;
import lsxiao.com.zhihudailyrrd.service.base.DataLayer;

/**
 * Flux 依赖
 *
 * @author lsxiao
 * @date 2015-11-04 00:44
 */
@Module
public class FluxModule {


    /**
     * 提供分发器单例对象
     *
     * @return Dispatcher
     */
    @Singleton
    @Provides
    public Dispatcher provideDispatcher() {
        return Dispatcher.instance();
    }


    /**
     * 提供NewsActionCreator单例对象
     *
     * @param dispatcher Dispatcher
     * @param dataLayer  DataLayer
     * @return NewsActionCreator
     */
    @Singleton
    @Provides
    public NewsActionCreator provideNewsActionCreator(Dispatcher dispatcher, DataLayer dataLayer) {
        return new NewsActionCreator(dispatcher, dataLayer);
    }

    /**
     * 提供ActionCreatorManager单例对象
     *
     * @param newsActionCreator NewsActionCreator
     * @return ActionCreatorManager
     */
    @Singleton
    @Provides
    public ActionCreatorManager provideActionCreatorManager(NewsActionCreator newsActionCreator) {
        return new ActionCreatorManager(newsActionCreator);
    }

}
