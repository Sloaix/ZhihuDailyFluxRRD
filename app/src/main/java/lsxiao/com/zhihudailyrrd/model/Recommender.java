package lsxiao.com.zhihudailyrrd.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author lsxiao
 * @date 2015-11-03 22:43
 */
public class Recommender extends BaseModel {
    //这篇文章的推荐者头像
    @JsonProperty("avatar")
    private String mAvatarUrl;
}
