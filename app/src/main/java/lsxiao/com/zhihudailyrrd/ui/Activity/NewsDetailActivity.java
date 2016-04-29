package lsxiao.com.zhihudailyrrd.ui.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;

import lsxiao.com.zhihudailyrrd.R;
import lsxiao.com.zhihudailyrrd.base.BaseActivity;
import lsxiao.com.zhihudailyrrd.base.BundleKey;
import lsxiao.com.zhihudailyrrd.model.TodayNews;
import lsxiao.com.zhihudailyrrd.ui.Fragment.NewsDetailFragment;

public class NewsDetailActivity extends BaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_news;
    }

    public static void start(Context context, TodayNews.Story story) {
        Intent intent = new Intent(context, NewsDetailActivity.class);
        intent.putExtra(BundleKey.STORY, story);
        context.startActivity(intent);
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        TodayNews.Story story = (TodayNews.Story) getIntent().getSerializableExtra(BundleKey.STORY);
        showNewsFragment(story);
    }

    private void showNewsFragment(TodayNews.Story story) {
        Fragment fragment = NewsDetailFragment.newInstance(story);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.rl_news_container, fragment, NewsDetailFragment.TAG);
        ft.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
