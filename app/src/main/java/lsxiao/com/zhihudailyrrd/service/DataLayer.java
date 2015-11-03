package lsxiao.com.zhihudailyrrd.service;

import android.database.Observable;

import lsxiao.com.zhihudailyrrd.model.LastestNews;
import lsxiao.com.zhihudailyrrd.model.News;
import lsxiao.com.zhihudailyrrd.model.StartImage;

/**
 * @author lsxiao
 * @date 2015-11-03 22:28
 */
public class DataLayer {

    DailyService mDailyService;

    public DataLayer(DailyService dailyService) {
        mDailyService = dailyService;
    }

    public DailyService getDailyService() {
        return mDailyService;
    }

    public interface DailyService {

        /**
         * 获取最新日报新闻列表
         *
         * @return LastestNews
         */
        Observable<LastestNews> getLatestNews();

        /**
         * 获取启动图片
         *
         * @return StartImage
         */
        Observable<StartImage> getStartImage();

        /**
         * 获取新闻
         *
         * @param newsId long
         * @return News
         */
        Observable<News> getNews(long newsId);
    }
}
