package lsxiao.com.zhihudailyrrd.ui.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import lsxiao.com.zhihudailyrrd.R;
import lsxiao.com.zhihudailyrrd.base.BaseActivity;
import lsxiao.com.zhihudailyrrd.util.ExitClickUtil;
import lsxiao.com.zhihudailyrrd.ui.Fragment.NewsListFragment;

/**
 * @author lsxiao
 * @date 2015-11-03 22:28
 */
public class HomeActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        Fragment fragment = NewsListFragment.newInstance();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fl_container, fragment, NewsListFragment.TAG);
        ft.commit();
    }

    @Override
    public void onBackPressed() {
        if (!ExitClickUtil.isClickAgain()) {
            Toast.makeText(this, getString(R.string.click_again_to_exit_app), Toast.LENGTH_SHORT).show();
            return;
        }
        super.onBackPressed();
    }
}
