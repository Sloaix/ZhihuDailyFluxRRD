package lsxiao.com.zhihudailyrrd.flux.action;

import android.os.Bundle;

import lsxiao.com.zhihudailyrrd.flux.action.base.BaseAction;

/**
 * 新闻Action
 * author lsxiao
 * date 2016-05-09 18:26
 */
public class NewsAction extends BaseAction {
    public final static String ACTION_LIST_NEWS_FETCH_START = "list_news_refresh_start";
    public final static String ACTION_LIST_NEWS_FETCH_FINISH = "list_news_refresh_finish";
    public final static String ACTION_LIST_NEWS_FETCH_ERROR = "list_news_refresh_error";
    public final static String ACTION_NEWS_ITEM_LIST_CLICK = "list_news_item_click";
    public final static String ACTION_NEWS_SLIDER_LIST_CLICK = "list_news_slider_click";

    public final static String ACTION_DETAIL_NEWS_FETCH_START = "detail_news_refresh_start";
    public final static String ACTION_DETAIL_NEWS_FETCH_FINISH = "detail_news_refresh_finish";
    public final static String ACTION_DETAIL_NEWS_FETCH_ERROR = "detail_news_refresh_error";

    public NewsAction(String type, Bundle data) {
        super(type, data);
    }

    public NewsAction(String type) {
        super(type);
    }

}
