package lsxiao.com.zhihudailyrrd.protocol;

import lsxiao.com.zhihudailyrrd.model.TodayNews;
import lsxiao.com.zhihudailyrrd.model.News;
import lsxiao.com.zhihudailyrrd.model.StartImage;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * @author lsxiao
 * @date 2015-11-03 22:28
 */
public interface ClientAPI {

    /**
     * FIELD
     */

    String FIELD_NEWS_ID = "newsId";


    /**
     * URL
     */

    //获取启动页面图片
    String URL_GET_START_IMAGE = "start-image/1080*1776";

    //获取最新日报新闻列表
    String URL_GET_LATEST_NEWS = "news/latest";

    //获取新闻
    String URL_GET_NEWS = "news/{newsId}";


    /**
     * 获取今日日报新闻列表
     *
     * @return TodayNews
     */
    @GET(URL_GET_LATEST_NEWS)
    Observable<TodayNews> getTodayNews();

    /**
     * 获取启动图片
     *
     * @return StartImage
     */
    @GET(URL_GET_START_IMAGE)
    Observable<StartImage> getStartImage();

    /**
     * 获取新闻
     *
     * @param newsId long
     * @return News
     */
    @GET(URL_GET_NEWS)
    Observable<News> getNews(@Path(FIELD_NEWS_ID) long newsId);
}
