package lsxiao.com.zhihudailyrrd.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * @author lsxiao
 * @date 2015-11-03 22:40
 */
public class News extends BaseModel {
    //新闻的 id
    @JsonProperty("id")
    private Long mNewsId;

    // HTML 格式的新闻
    @JsonProperty("body")
    private String mBody;

    //图片的内容提供方。为了避免被起诉非法使用图片，在显示图片时最好附上其版权信息。
    @JsonProperty("image_source")
    private String mImageSource;
    //新闻标题
    @JsonProperty("title")
    private String mTitle;

    //获得的图片同 最新消息 获得的图片分辨率不同。这里获得的是在文章浏览界面中使用的大图。
    @JsonProperty("image")
    private String mImage;

    //供在线查看内容与分享至 SNS 用的 URL
    @JsonProperty("share_url")
    private String mShareUrl;

    // 供手机端的 WebView(UIWebView) 使用
    @JsonProperty("js")
    private List<String> mJsList;

    //供手机端的 WebView(UIWebView) 使用
    @JsonProperty("css")
    private List<String> mCssList;

    //供 Google Analytics 使用
    @JsonProperty("ga_prefix")
    private String mGaPrefix;

    //这篇文章的推荐者
    @JsonProperty("recommenders")
    private List<Recommender> mRecommenders;


    // 新闻的类型
    @JsonProperty("type")
    private String mType;

    /**
     * 在较为特殊的情况下，知乎日报可能将某个主题日报的站外文章推送至知乎日报首页。
     * 此时返回的 JSON 数据缺少 body，image-source，image，js 属性。
     * 多出 theme_name，editor_name，theme_id 三个属性。type 由 0 变为 1。
     */

    //主题id
    @JsonProperty("theme_id")
    private String mThemeId;

    //主题名
    @JsonProperty("theme_name")
    private String mThemeName;

    //编辑者名
    @JsonProperty("editor_name")
    private String mEditorName;

    //栏目
    @JsonProperty("section")
    private Section mSection;

    /**
     * 栏目的信息
     */
    public static class Section {
        //该栏目的 id
        @JsonProperty("id")
        private Long mId;

        //该栏目的名称
        @JsonProperty("name")
        private String mName;

        //栏目的缩略图
        @JsonProperty("thumbnail")
        private String mThumbnail;

        @Override
        public String toString() {
            return "Section{" +
                    "mId=" + mId +
                    ", mName='" + mName + '\'' +
                    ", mThumbnail='" + mThumbnail + '\'' +
                    '}';
        }
    }

    public Long getNewsId() {
        return mNewsId;
    }

    public void setNewsId(Long newsId) {
        mNewsId = newsId;
    }

    public String getBody() {
        return mBody;
    }

    public void setBody(String body) {
        mBody = body;
    }

    public String getImageSource() {
        return mImageSource;
    }

    public void setImageSource(String imageSource) {
        mImageSource = imageSource;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        mImage = image;
    }

    public String getShareUrl() {
        return mShareUrl;
    }

    public void setShareUrl(String shareUrl) {
        mShareUrl = shareUrl;
    }

    public List<String> getJsList() {
        return mJsList;
    }

    public void setJsList(List<String> jsList) {
        mJsList = jsList;
    }

    public List<String> getCssList() {
        return mCssList;
    }

    public void setCssList(List<String> cssList) {
        mCssList = cssList;
    }

    public String getGaPrefix() {
        return mGaPrefix;
    }

    public void setGaPrefix(String gaPrefix) {
        mGaPrefix = gaPrefix;
    }

    public List<Recommender> getRecommenders() {
        return mRecommenders;
    }

    public void setRecommenders(List<Recommender> recommenders) {
        mRecommenders = recommenders;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

    public String getThemeId() {
        return mThemeId;
    }

    public void setThemeId(String themeId) {
        mThemeId = themeId;
    }

    public String getThemeName() {
        return mThemeName;
    }

    public void setThemeName(String themeName) {
        mThemeName = themeName;
    }

    public String getEditorName() {
        return mEditorName;
    }

    public void setEditorName(String editorName) {
        mEditorName = editorName;
    }

    @Override
    public String toString() {
        return "News{" +
                "mNewsId=" + mNewsId +
                ", mBody='" + mBody + '\'' +
                ", mImageSource='" + mImageSource + '\'' +
                ", mTitle='" + mTitle + '\'' +
                ", mImage='" + mImage + '\'' +
                ", mShareUrl='" + mShareUrl + '\'' +
                ", mJsList=" + mJsList +
                ", mCssList=" + mCssList +
                ", mGaPrefix='" + mGaPrefix + '\'' +
                ", mRecommenders=" + mRecommenders +
                ", mType='" + mType + '\'' +
                ", mThemeId='" + mThemeId + '\'' +
                ", mThemeName='" + mThemeName + '\'' +
                ", mEditorName='" + mEditorName + '\'' +
                '}';
    }
}
