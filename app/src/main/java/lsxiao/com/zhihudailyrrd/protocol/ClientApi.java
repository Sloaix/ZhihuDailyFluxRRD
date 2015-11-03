package lsxiao.com.zhihudailyrrd.protocol;

import android.database.Observable;

import lsxiao.com.zhihudailyrrd.model.LastestNews;
import lsxiao.com.zhihudailyrrd.model.News;
import lsxiao.com.zhihudailyrrd.model.StartImage;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * @author lsxiao
 * @date 2015-11-03 22:28
 */
public interface ClientApi {

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
    String URL_GET_LASTEST_NEWS = "news/latest";

    //获取新闻
    String URL_GET_NEWS = "news/{" + FIELD_NEWS_ID + "}";


    /**
     * 获取最新日报新闻列表
     *
     * @return LastestNews
     */
    @GET(URL_GET_NEWS)
    Observable<LastestNews> getLatestNews();

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
