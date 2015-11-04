package lsxiao.com.zhihudailyrrd.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * @author lsxiao
 * @date 2015-11-03 22:32
 */
public class LatestNews extends BaseModel {
    //日期
    @JsonProperty("date")
    private String mDate;

    //当日新闻
    @JsonProperty("stories")
    private List<Story> mStories;

    //顶部ViewPager滚动显示的新闻
    @JsonProperty("top_stories")
    private List<Story> mTopStories;


    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public List<Story> getStories() {
        return mStories;
    }

    public void setStories(List<Story> stories) {
        mStories = stories;
    }

    public List<Story> getTopStories() {
        return mTopStories;
    }

    public void setTopStories(List<Story> topStories) {
        mTopStories = topStories;
    }

    @Override
    public String toString() {
        return "LatestNews{" +
                "mDate='" + mDate + '\'' +
                ", mStories=" + mStories +
                ", mTopStories=" + mTopStories +
                '}';
    }
}
