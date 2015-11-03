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
    private Long mId;

    // HTML 格式的新闻
    @JsonProperty("body")
    private String mBody;

    //图片的内容提供方。为了避免被起诉非法使用图片，在显示图片时最好附上其版权信息。
    @JsonProperty("image-source")
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
    private String mJs;

    //供手机端的 WebView(UIWebView) 使用
    @JsonProperty("css")
    private String mCss;

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
    }
}
