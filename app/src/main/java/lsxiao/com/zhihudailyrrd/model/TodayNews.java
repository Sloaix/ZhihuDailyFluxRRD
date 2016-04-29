package lsxiao.com.zhihudailyrrd.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * 今日热文
 *
 * @author lsxiao
 * @date 2015-11-03 22:32
 */
public class TodayNews implements Serializable {
    //日期,唯一,重复的直接覆盖
    @SerializedName("date")
    private String mDate;

    //当日新闻
    @SerializedName("stories")
    private List<Story> mStories;

    //顶部ViewPager滚动显示的新闻
    @SerializedName("top_stories")
    private List<Story> mTopStories;

    public String getDate() {
        return mDate;
    }

    public List<Story> getStories() {
        return mStories;
    }

    public List<Story> getTopStories() {
        return mTopStories;
    }

    @Override
    public String toString() {
        return "TodayNews{" +
                "mDate='" + mDate + '\'' +
                ", mStories=" + mStories +
                ", mTopStories=" + mTopStories +
                '}';
    }

    public static class Story implements Serializable {
        //id
        @SerializedName("id")
        private long mId;

        //标题
        @SerializedName("title")
        private String mTitle;

        //图像地址（官方 API 使用数组形式。目前暂未有使用多张图片的情形出现，曾见无 images 属性的情况，请在使用中注意 ）
        @SerializedName("images")
        private List<String> mImageUrls;

        //图像地址 只有topStories才有
        @SerializedName("image")
        private String mImageUrl;

        //类型,作用未知
        @SerializedName("type")
        private int mType;

        //供 Google Analytics 使用
        @SerializedName("ga_prefix")
        private String mGaPrefix;

        //消息是否包含多张图片（仅出现在包含多图的新闻中)
        @SerializedName("multipic")
        private boolean mMultiPic;

        public long getId() {
            return mId;
        }

        public String getTitle() {
            return mTitle;
        }

        public List<String> getImageUrls() {
            return mImageUrls;
        }

        public String getImageUrl() {
            return mImageUrl;
        }

        public int getType() {
            return mType;
        }

        public String getGaPrefix() {
            return mGaPrefix;
        }

        public boolean isMultiPic() {
            return mMultiPic;
        }

        @Override
        public String toString() {
            return "Story{" +
                    "mId=" + mId +
                    ", mTitle='" + mTitle + '\'' +
                    ", mImageUrls=" + mImageUrls +
                    ", mImageUrl='" + mImageUrl + '\'' +
                    ", mType=" + mType +
                    ", mGaPrefix='" + mGaPrefix + '\'' +
                    ", mMultiPic=" + mMultiPic +
                    '}';
        }
    }

}
