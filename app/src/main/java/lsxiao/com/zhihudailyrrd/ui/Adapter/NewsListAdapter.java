package lsxiao.com.zhihudailyrrd.ui.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import lsxiao.com.zhihudailyrrd.R;
import lsxiao.com.zhihudailyrrd.base.BundleKey;
import lsxiao.com.zhihudailyrrd.model.TodayNews;
import lsxiao.com.zhihudailyrrd.view.TextSliderView;

/**
 * @author lsxiao
 * @date 2015-11-04 23:07
 */
public class NewsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<TodayNews.Story> mStories;
    private Context mContext;
    private View.OnClickListener mListener;
    private BaseSliderView.OnSliderClickListener mSliderClickListener;
    private List<TodayNews.Story> mHeaderStories;
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    public NewsListAdapter(Context context, List<TodayNews.Story> stories, List<TodayNews.Story> headerStories, View.OnClickListener listener, BaseSliderView.OnSliderClickListener sliderClickListener) {
        mContext = context;
        mStories = stories;
        mListener = listener;
        mHeaderStories = headerStories;
        mSliderClickListener = sliderClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        if (viewType == TYPE_ITEM) {
            itemView = LayoutInflater.from(mContext).inflate(R.layout.list_item_news, parent, false);
            return new ItemVH(itemView);
        } else if (viewType == TYPE_HEADER) {
            itemView = LayoutInflater.from(mContext).inflate(R.layout.list_item_header, parent, false);
            return new HeaderVH(itemView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemVH) {
            ItemVH itemVH = (ItemVH) holder;
            final TodayNews.Story story = mStories.get(position - (mHeaderStories == null || mHeaderStories.isEmpty() ? 0 : 1));
            //设置新闻标题
            itemVH.mTvTitle.setText(story.getTitle());
            //加载图片
            Picasso.with(mContext)
                    .load(story.getImageUrls().get(0))
                    .placeholder(R.drawable.ic_placeholder)
                    .into(itemVH.mIvNewsThumbnail);
            itemVH.itemView.setOnClickListener(mListener);
        } else if (holder instanceof HeaderVH) {
            HeaderVH headerVH = (HeaderVH) holder;
            headerVH.mSlHeader.removeAllSliders();
            for (int i = 0; i < mHeaderStories.size(); i++) {
                final TodayNews.Story story = mHeaderStories.get(i);

                //附加信息
                Bundle bundle = new Bundle();
                bundle.putSerializable(BundleKey.STORY, story);

                TextSliderView textSliderView = new TextSliderView(mContext);
                textSliderView.setOnSliderClickListener(mSliderClickListener);
                textSliderView
                        .description(story.getTitle())
                        .setScaleType(BaseSliderView.ScaleType.CenterCrop)
                        .image(story.getImageUrl())
                        .bundle(bundle);
                headerVH.mSlHeader.addSlider(textSliderView);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mStories.size() + (mHeaderStories == null || mHeaderStories.isEmpty() ? 0 : 1);
    }

    public List<TodayNews.Story> getStories() {
        return mStories;
    }

    public TodayNews.Story getItemData(int position) {
        position = getItemCount() == mHeaderStories.size() ? position : position - 1;
        return getStories().get(position);
    }

    public TodayNews.Story getHeaderData(int position) {
        return getHeaderStories().get(position);
    }

    public List<TodayNews.Story> getHeaderStories() {
        return mHeaderStories;
    }

    public void setStories(List<TodayNews.Story> stories, List<TodayNews.Story> topStories) {
        mStories = stories;
        mHeaderStories = topStories;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        } else {
            return TYPE_ITEM;
        }
    }

    public static class ItemVH extends RecyclerView.ViewHolder {
        public CircleImageView mIvNewsThumbnail;
        public TextView mTvTitle;

        public ItemVH(View itemView) {
            super(itemView);
            mIvNewsThumbnail = (CircleImageView) itemView.findViewById(R.id.iv_story_thumbnail);
            mTvTitle = (TextView) itemView.findViewById(R.id.tv_title);
        }
    }


    public static class HeaderVH extends RecyclerView.ViewHolder {
        public SliderLayout mSlHeader;
        public PagerIndicator mPagerIndicator;

        public HeaderVH(View itemView) {
            super(itemView);
            mSlHeader = (SliderLayout) itemView.findViewById(R.id.sl_header);
            mPagerIndicator = (PagerIndicator) itemView.findViewById(R.id.pi_header);
            mSlHeader.setCustomIndicator(mPagerIndicator);
        }
    }
}
