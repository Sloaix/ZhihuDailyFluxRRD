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
    private Long mId;

    //标题
    @JsonProperty("title")
    private String mTitle;

    //图像地址（官方 API 使用数组形式。目前暂未有使用多张图片的情形出现，曾见无 images 属性的情况，请在使用中注意 ）
    @JsonProperty("images")
    private List<String> mImageUrls;

    //类型,作用未知
    @JsonProperty("type")
    private Integer mType;

    //供 Google Analytics 使用
    @JsonProperty("ga_prefix")
    private String mGaPrefix;

    //消息是否包含多张图片（仅出现在包含多图的新闻中)
    @JsonProperty("multipic")
    private Boolean mMultiPic;
}
