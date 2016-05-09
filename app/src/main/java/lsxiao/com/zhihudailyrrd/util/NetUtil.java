package lsxiao.com.zhihudailyrrd.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * @author lsxiao
 * @date 2015-11-09 00:50
 */
public class NetUtil {

    private NetUtil() {
    }

    public static boolean isNetworkConnected() {
        if (AppContextUtil.instance() != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) AppContextUtil.instance()
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    public static boolean isWifiConnected() {
        if (AppContextUtil.instance() != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) AppContextUtil.instance()
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mWiFiNetworkInfo = mConnectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (mWiFiNetworkInfo != null) {
                return mWiFiNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    public static boolean isMobileConnected() {
        if (AppContextUtil.instance() != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) AppContextUtil.instance()
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mMobileNetworkInfo = mConnectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (mMobileNetworkInfo != null) {
                return mMobileNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    public static int getConnectedType() {
        if (AppContextUtil.instance() != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) AppContextUtil.instance()
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null && mNetworkInfo.isAvailable()) {
                return mNetworkInfo.getType();
            }
        }
        return -1;
    }
}
