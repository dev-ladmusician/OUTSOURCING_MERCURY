package com.goqual.mercury.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.goqual.mercury.R;
import com.goqual.mercury.data.local.dto.FeedDTO;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ladmusician on 2/24/16.
 */
public class FeedsAdapter extends RecyclerView.Adapter<FeedsAdapter.FeedsViewHolder> {
    private List<FeedDTO> mFeedList = null;

    public void setFeedList(List<FeedDTO> feeds) {
        this.mFeedList = feeds;
    }

    public FeedsAdapter() {
        this.mFeedList = new ArrayList<>();
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
        @Bind(R.id.feed_seq)
        TextView mSeq;
        @Bind(R.id.feed_title)
        TextView mTitle;
        @Bind(R.id.feed_period)
        TextView mPeriod;

        public FeedsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindView(int position, FeedDTO feed) {
            mSeq.setText(position + 1 + "");
            mTitle.setText(feed.getTitle());
            mPeriod.setText(feed.getPeriod());
        }
    }
}
