package lsxiao.com.zhihudailyrrd.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @author lsxiao
 * @date 2015-11-03 23:06
 */
public class StartImage implements Serializable {
    //图像的 URL
    @SerializedName("img")
    private String url;

    //供显示的图片版权信息
    @SerializedName("text")
    private String mText;

    public String getUrl() {
        return url;
    }

    public String getText() {
        return mText;
    }

    @Override
    public String toString() {
        return "StartImage{" +
                "mText='" + mText + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
