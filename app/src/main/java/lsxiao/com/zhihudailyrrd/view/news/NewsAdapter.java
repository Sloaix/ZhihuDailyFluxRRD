package lsxiao.com.zhihudailyrrd.view.news;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import lsxiao.com.zhihudailyrrd.R;
import lsxiao.com.zhihudailyrrd.model.Story;

/**
 * @author lsxiao
 * @date 2015-11-04 23:07
 */
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    private List<Story> mStories;
    private Context mContext;
    private View.OnClickListener mListener;

    public NewsAdapter(Context context, List<Story> stories, View.OnClickListener listener) {
        mContext = context;
        mStories = stories;
        mListener = listener;
    }

    /**
     * Item 类型,普通的新闻,顶部滚动的新闻
     */
    private enum ViewType {
        NORMAL(0), TOP_PAGER(1);

        ViewType(int nativeInt) {
            this.nativeInt = nativeInt;
        }

        int val() {
            return nativeInt;
        }

        final int nativeInt;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_news, parent, false);
        return new ViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Story story = mStories.get(position);
        //设置新闻标题
        holder.mTvTitle.setText(story.getTitle());
        //加载图片
        Picasso.with(mContext)
                .load(story.getImageUrls().get(0))
                .placeholder(R.drawable.ic_placeholder)
                .into(holder.mIvNewsThumbnail);

        //
        holder.itemView.setOnClickListener(mListener);
    }

    @Override
    public int getItemCount() {
        return mStories.size();
    }

    public List<Story> getStories() {
        return mStories;
    }

    public void setStories(List<Story> stories) {
        mStories = stories;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView mIvNewsThumbnail;
        public TextView mTvTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            mIvNewsThumbnail = (ImageView) itemView.findViewById(R.id.iv_story_thumbnail);
            mTvTitle = (TextView) itemView.findViewById(R.id.tv_title);
        }
    }
}
