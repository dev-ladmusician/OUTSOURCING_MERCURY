package com.goqual.mercury.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.daimajia.swipe.implments.SwipeItemRecyclerMangerImpl;
import com.goqual.mercury.R;
import com.goqual.mercury.data.local.FeedDTO;
import com.goqual.mercury.presenter.FeedPresenter;
import com.goqual.mercury.util.Common;
import com.goqual.mercury.util.Constant;
import com.goqual.mercury.view.activity.ActivityDetailFeed;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ladmusician on 2/24/16.
 */
public class FeedsAdapter extends RecyclerSwipeAdapter<FeedsAdapter.FeedsViewHolder> {
    private static final String TAG = "ADAPTER_FEED";
    private List<FeedDTO> mFeedList = null;
    private Context mContext = null;
    private FeedPresenter mFeedPresenter = null;
    public SwipeItemRecyclerMangerImpl mItemManger = new SwipeItemRecyclerMangerImpl(this);

    public void setFeedList(List<FeedDTO> feeds) {
        this.mFeedList = feeds;
    }

    public FeedsAdapter(Context ctx, FeedPresenter presenter) {
        this.mContext = ctx;
        this.mFeedPresenter = presenter;
        this.mFeedList = new ArrayList<>();
    }

    public void updateFeedList(List<FeedDTO> list) {
        mFeedList = list;
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.feed_item_container;
    }

    @Override
    public FeedsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feed, parent, false);
        return new FeedsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FeedsViewHolder holder, int position) {
        holder.bindView(position, mFeedList.get(position));
    }

    @Override
    public int getItemCount() {
        return mFeedList.size();
    }

    class FeedsViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.feed_title)
        TextView mTitle;
        @Bind(R.id.feed_period)
        TextView mPeriod;
        @Bind(R.id.feed_bg)
        ImageView mBg;
        @Bind(R.id.feed_bg_transparend)
        RelativeLayout mBgTransparent;
        @Bind(R.id.feed_item_container)
        SwipeLayout mFeedItemContainer;
        @Bind(R.id.feed_swipe_menu_delete)
        TextView mSwipeMenuDelete;
        @Bind(R.id.feed_swipe_menu_container)
        LinearLayout mSwipeMenuContainer;

        public FeedsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindView(final int position, FeedDTO feed) {
            mTitle.setText(feed.getTitle());
            mPeriod.setText(feed.getPeriod());

            if (feed.getMain_img_url() != null &&
                    !feed.getMain_img_url().isEmpty()) {

                Glide.with(mContext)
                        .load(Constant.IMG_BASE_URL + feed.getMain_img_url())
                        .centerCrop()
                        .into(mBg);

                mBgTransparent.setVisibility(View.VISIBLE);
            }

            mFeedItemContainer.setShowMode(SwipeLayout.ShowMode.PullOut);
            mFeedItemContainer.addDrag(SwipeLayout.DragEdge.Right, mFeedItemContainer.findViewById(R.id.feed_swipe_menu_container));

            mFeedItemContainer.getSurfaceView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, ActivityDetailFeed.class);
                    intent.putExtra(mContext.getString(R.string.FEED_ID), mFeedList.get(position).get_feedid());
                    intent.putExtra(mContext.getString(R.string.FEED_TITLE), mFeedList.get(position).getTitle());
                    intent.putExtra(mContext.getString(R.string.FEED_PERIOD), mFeedList.get(position).getPeriod());
                    mContext.startActivity(intent);
                }
            });

//            mSwipeMenuEdit.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Toast.makeText(view.getContext(), "Clicked on Share ", Toast.LENGTH_SHORT).show();
//                }
//            });

            mSwipeMenuDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mFeedPresenter.deleteFeed(position, mFeedList.get(position).get_feedid());
                    //Toast.makeText(view.getContext(), "Click Deleted", Toast.LENGTH_SHORT).show();
                }
            });

            mItemManger.bindView(itemView, position);
        }
    }
}
