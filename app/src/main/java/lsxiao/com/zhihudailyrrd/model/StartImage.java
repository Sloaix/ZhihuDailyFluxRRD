package lsxiao.com.zhihudailyrrd.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author lsxiao
 * @date 2015-11-03 23:06
 */
public class StartImage extends BaseModel {
    //供显示的图片版权信息
    @JsonProperty("text")
    private String mText;

    //图像的 URL
    @JsonProperty("img")
    private String url;


    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "StartImage{" +
                "mText='" + mText + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
