package lsxiao.com.zhihudailyrrd.utils;

import java.util.List;

/**
 * @author lsxiao
 * @date 2015-11-05 10:45
 */
public class HtmlUtil {
    //隐藏headLine
    public static final String hideHeadLine = "<style>div.headline{display:none;}</style>";

    private HtmlUtil() {
    }

    public static String createCssTag(String url) {
        String tag = "<link rel=\"stylesheet\" type=\"text/css\" href=\"%s\"/>";
        return String.format(tag, url);
    }

    public static String createCssTag(List<String> urls) {
        StringBuilder sb = new StringBuilder();
        sb.append(hideHeadLine);
        for (String url : urls) {
            sb.append(createCssTag(url));
        }
        return sb.toString();
    }

    public static String createJsTag(String url) {
        String tag = "<script src=\"%s\"></script>";
        return String.format(tag, url);
    }

    public static String createJsTag(List<String> urls) {
        StringBuilder sb = new StringBuilder();
        for (String url : urls) {
            sb.append(createJsTag(url));
        }
        return sb.toString();
    }

}
