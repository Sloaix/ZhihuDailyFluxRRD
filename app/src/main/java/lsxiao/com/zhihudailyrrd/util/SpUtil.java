package lsxiao.com.zhihudailyrrd.util;

import android.preference.PreferenceManager;

import lsxiao.com.zhihudailyrrd.inject.component.ApplicationComponent;

/**
 * @author lsxiao
 * @date 2015-11-08 23:21
 */
public class SpUtil {
    public static void saveOrUpdate(String key, String json) {
        PreferenceManager.getDefaultSharedPreferences(ApplicationComponent
                .Instance
                .get()
                .getApplication())
                .edit().putString(key, json).apply();
    }

    public static String find(String key) {
        return PreferenceManager.getDefaultSharedPreferences(ApplicationComponent
                .Instance
                .get()
                .getApplication()).getString(key, null);
    }

    public static void delete(String key) {
        PreferenceManager.getDefaultSharedPreferences(ApplicationComponent
                .Instance
                .get()
                .getApplication()).edit().remove(key).apply();
    }

    public static void clearAll() {
        PreferenceManager.getDefaultSharedPreferences(ApplicationComponent
                .Instance
                .get()
                .getApplication()).edit().clear().apply();
    }
}
