package lsxiao.com.zhihudailyrrd.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * @author lsxiao
 * @date 2015-11-03 22:40
 */
public class News implements Serializable {
    //新闻的 id
    @SerializedName("id")
    private Long mId;

    // HTML 格式的新闻
    @SerializedName("body")
    private String mBody;

    //图片的内容提供方。为了避免被起诉非法使用图片，在显示图片时最好附上其版权信息。
    @SerializedName("image_source")
    private String mImageSource;

    //新闻标题
    @SerializedName("title")
    private String mTitle;

    //获得的图片同 最新消息 获得的图片分辨率不同。这里获得的是在文章浏览界面中使用的大图。
    @SerializedName("image")
    private String mImage;

    //供在线查看内容与分享至 SNS 用的 URL
    @SerializedName("share_url")
    private String mShareUrl;

    // 供手机端的 WebView(UIWebView) 使用
    @SerializedName("js")
    private List<String> mJsList;

    //供手机端的 WebView(UIWebView) 使用
    @SerializedName("css")
    private List<String> mCssList;

    //供 Google Analytics 使用
    @SerializedName("ga_prefix")
    private String mGaPrefix;

    //这篇文章的推荐者
    @SerializedName("recommenders")
    private List<Recommender> mRecommenderList;

    // 新闻的类型
    @SerializedName("type")
    private String mType;

    /**
     * 在较为特殊的情况下，知乎日报可能将某个主题日报的站外文章推送至知乎日报首页。
     * 此时返回的 JSON 数据缺少 body，image-source，image，js 属性。
     * 多出 theme_name，editor_name，theme_id 三个属性。type 由 0 变为 1。
     */

    //主题id
    @SerializedName("theme_id")
    private String mThemeId;

    //主题名
    @SerializedName("theme_name")
    private String mThemeName;

    //编辑者名
    @SerializedName("editor_name")
    private String mEditorName;

    //栏目
    @SerializedName("section")
    private Section mSection;

    /**
     * 栏目的信息
     */
    public static class Section implements Serializable {
        //该栏目的 id
        @SerializedName("id")
        private Long mSectionId;

        //该栏目的名称
        @SerializedName("name")
        private String mName;

        //栏目的缩略图
        @SerializedName("thumbnail")
        private String mThumbnail;

        public Long getSectionId() {
            return mSectionId;
        }

        public String getName() {
            return mName;
        }

        public String getThumbnail() {
            return mThumbnail;
        }

        @Override
        public String toString() {
            return "Section{" +
                    "mSectionId=" + mSectionId +
                    ", mName='" + mName + '\'' +
                    ", mThumbnail='" + mThumbnail + '\'' +
                    '}';
        }
    }

    public static class Recommender implements Serializable {
        //这篇文章的推荐者头像
        @SerializedName("avatar")
        private String mAvatarUrl;

        public String getAvatarUrl() {
            return mAvatarUrl;
        }
    }

    public Long getId() {
        return mId;
    }

    public String getBody() {
        return mBody;
    }

    public String getImageSource() {
        return mImageSource;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getImage() {
        return mImage;
    }

    public String getShareUrl() {
        return mShareUrl;
    }

    public List<String> getJsList() {
        return mJsList;
    }

    public List<String> getCssList() {
        return mCssList;
    }


    public List<Recommender> getRecommenderList() {
        return mRecommenderList;
    }

    public String getType() {
        return mType;
    }

    public String getThemeId() {
        return mThemeId;
    }

    public String getThemeName() {
        return mThemeName;
    }

    public String getEditorName() {
        return mEditorName;
    }

    public Section getSection() {
        return mSection;
    }

    @Override
    public String toString() {
        return "News{" +
                "mId=" + mId +
                ", mBody='" + mBody + '\'' +
                ", mImageSource='" + mImageSource + '\'' +
                ", mTitle='" + mTitle + '\'' +
                ", mImage='" + mImage + '\'' +
                ", mShareUrl='" + mShareUrl + '\'' +
                ", mJsList=" + mJsList +
                ", mCssList=" + mCssList +
                ", mRecommenderList=" + mRecommenderList +
                ", mType='" + mType + '\'' +
                ", mThemeId='" + mThemeId + '\'' +
                ", mThemeName='" + mThemeName + '\'' +
                ", mEditorName='" + mEditorName + '\'' +
                ", mSection=" + mSection +
                '}';
    }
}
