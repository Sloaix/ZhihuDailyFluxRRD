package lsxiao.com.zhihudailyrrd.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * @author lsxiao
 * @date 2015-11-03 22:33
 */
public class Story extends BaseModel {
    //id
    @JsonProperty("id")
    private Long mStoryId;

    //标题
    @JsonProperty("title")
    private String mTitle;

    //图像地址（官方 API 使用数组形式。目前暂未有使用多张图片的情形出现，曾见无 images 属性的情况，请在使用中注意 ）
    @JsonProperty("images")
    private List<String> mImageUrls;

    //图像地址 只有topStories才有
    @JsonProperty("image")
    private String mImageUrl;

    //类型,作用未知
    @JsonProperty("type")
    private Integer mType;

    //供 Google Analytics 使用
    @JsonProperty("ga_prefix")
    private String mGaPrefix;

    //消息是否包含多张图片（仅出现在包含多图的新闻中)
    @JsonProperty("multipic")
    private Boolean mMultiPic;

    public Long getStoryId() {
        return mStoryId;
    }

    public void setStoryId(Long storyId) {
        mStoryId = storyId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public List<String> getImageUrls() {
        return mImageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        mImageUrls = imageUrls;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }

    public Integer getType() {
        return mType;
    }

    public void setType(Integer type) {
        mType = type;
    }

    public String getGaPrefix() {
        return mGaPrefix;
    }

    public void setGaPrefix(String gaPrefix) {
        mGaPrefix = gaPrefix;
    }

    public Boolean getMultiPic() {
        return mMultiPic;
    }

    public void setMultiPic(Boolean multiPic) {
        mMultiPic = multiPic;
    }

    @Override
    public String toString() {
        return "Story{" +
                "mStoryId=" + mStoryId +
                ", mTitle='" + mTitle + '\'' +
                ", mImageUrls=" + mImageUrls +
                ", mImageUrl='" + mImageUrl + '\'' +
                ", mType=" + mType +
                ", mGaPrefix='" + mGaPrefix + '\'' +
                ", mMultiPic=" + mMultiPic +
                '}';
    }
}
