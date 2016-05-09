package lsxiao.com.zhihudailyrrd.flux.action.creator;

import lsxiao.com.zhihudailyrrd.flux.action.NewsAction;
import lsxiao.com.zhihudailyrrd.flux.action.creator.base.BaseActionCreator;
import lsxiao.com.zhihudailyrrd.flux.dispatcher.Dispatcher;
import lsxiao.com.zhihudailyrrd.service.DataLayer;

/**
 * author lsxiao
 * date 2016-05-09 17:25
 */
public class NewsActionCreator extends BaseActionCreator {
    public NewsActionCreator(Dispatcher dispatcher, DataLayer dataLayer) {
        super(dispatcher, dataLayer);
    }

    public void refresNewsList() {
        final NewsAction action = new NewsAction(NewsAction.ACTION_REFRESH_NEWS_LIST);
        getDispatcher().dispatch(action);
    }
}
