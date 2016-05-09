package lsxiao.com.zhihudailyrrd.flux.action;

import android.os.Bundle;

import lsxiao.com.zhihudailyrrd.flux.action.base.BaseAction;

/**
 * author lsxiao
 * date 2016-05-09 18:26
 */
public class NewsAction extends BaseAction {
    public static String ACTION_REFRESH_NEWS_LIST = "action_refresh_news_list";

    public NewsAction(String type, Bundle data) {
        super(type, data);
    }

    public NewsAction(String type) {
        super(type);
    }

}
