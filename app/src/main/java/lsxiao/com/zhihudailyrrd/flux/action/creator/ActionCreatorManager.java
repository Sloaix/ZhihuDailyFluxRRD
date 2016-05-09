package lsxiao.com.zhihudailyrrd.flux.action.creator;

/**
 * author lsxiao
 * date 2016-05-09 18:29
 */
public class ActionCreatorManager {
    private NewsActionCreator mNewsActionCreator;

    public ActionCreatorManager(NewsActionCreator newsActionCreator) {
        mNewsActionCreator = newsActionCreator;
    }

    public NewsActionCreator getNewsActionCreator() {
        return mNewsActionCreator;
    }

}
