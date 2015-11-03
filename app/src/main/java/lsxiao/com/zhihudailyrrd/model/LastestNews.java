package lsxiao.com.zhihudailyrrd.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * @author lsxiao
 * @date 2015-11-03 22:32
 */
public class LastestNews extends BaseModel {
    //日期
    @JsonProperty("date")
    private String mDate;

    //当日新闻
    @JsonProperty("stories")
    private List<Story> mStories;

    //顶部ViewPager滚动显示的新闻
    @JsonProperty("top_stories")
    private List<Story> mTopStories;
}
