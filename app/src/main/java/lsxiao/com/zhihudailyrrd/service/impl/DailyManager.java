package lsxiao.com.zhihudailyrrd.service.impl;

import android.database.Observable;

import lsxiao.com.zhihudailyrrd.model.LastestNews;
import lsxiao.com.zhihudailyrrd.model.News;
import lsxiao.com.zhihudailyrrd.model.StartImage;
import lsxiao.com.zhihudailyrrd.service.DataLayer;

/**
 * @author lsxiao
 * @date 2015-11-03 22:28
 */
public class DailyManager extends BaseManager implements DataLayer.DailyService {
    @Override
    public Observable<LastestNews> getLatestNews() {
        return getApi().getLatestNews();
    }

    @Override
    public Observable<StartImage> getStartImage() {
        return getApi().getStartImage();
    }

    @Override
    public Observable<News> getNews(long newsId) {
        return getApi().getNews(newsId);
    }
}
