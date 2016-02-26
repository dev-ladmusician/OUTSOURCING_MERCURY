package com.goqual.mercury.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.util.Attributes;
import com.goqual.mercury.R;
import com.goqual.mercury.data.local.FeedDTO;
import com.goqual.mercury.presenter.FeedPresenter;
import com.goqual.mercury.ui.adapter.FeedsAdapter;
import com.goqual.mercury.ui.base.BaseActivity;
import com.goqual.mercury.ui.mvp.MvpView;
import com.goqual.mercury.util.Common;

import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ActivityMain extends BaseActivity implements MvpView<FeedDTO> {
    private final String TAG = "ACTIVITY_MAIN";
    @BindString(R.string.EXTRA_TRIGGER_SYNC_FLAG)
    String EXTRA_TRIGGER_SYNC_FLAG;
    @Bind(R.id.main_feed_container)
    RecyclerView mContainer;
    @Bind(R.id.main_total_count_feed)
    TextView mTxtFeedCount;
    @Bind(R.id.main_title_user_name)
    TextView mTitle;
    @Bind(R.id.fab_add_feed)
    com.melnykov.fab.FloatingActionButton mFabAddFeed;
    @Bind(R.id.main_feed_no_report)
    RelativeLayout mNoFeedTitle;

    private FeedPresenter mFeedPresenter = null;
    private FeedsAdapter mFeedAdapter = null;
    private List<FeedDTO> mFeedList = null;

    @OnClick({R.id.fab_add_feed, R.id.main_logout})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_add_feed:
                Common.log(TAG, "FAB CLICK");
                startActivity(new Intent(this, ActivityAddFeed.class));
                break;
            case R.id.main_logout:
                getAppInfo().put(getString(R.string.USER_ID), -1);
                startActivity(new Intent(this, ActivityAuth.class));
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void showItems(List<FeedDTO> feeds) {
        Common.log(TAG, "SHOW FEEDS");
        mNoFeedTitle.setVisibility(View.GONE);
        mFeedList = feeds;
        mTxtFeedCount.setText(mFeedList.size() + "");
        mFeedAdapter.setFeedList(mFeedList);
        mFeedAdapter.notifyDataSetChanged();
    }

    @Override
    public void showEmptyItems() {
        Common.log(TAG, "SHOW EMPTY FEEDS");
        mTxtFeedCount.setText("0");
        mFeedAdapter.setFeedList(Collections.<FeedDTO>emptyList());
        mFeedAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError() {
        Common.log(TAG, "ERROR!");
        Toast.makeText(this, R.string.ERROR_LOADING_FEEDS, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSuccessDelete(int position) {
        mFeedList.remove(position);
        mFeedAdapter.notifyItemRemoved(position);
        mFeedAdapter.updateFeedList(mFeedList);
    }

    @Override
    public void onFauleDelete() {
        Common.log(TAG, "DELETE FEED FAIL!");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if (getAppInfo().getValue(getString(R.string.USER_ID), -1) == -1) {
            startActivity(new Intent(this, ActivityAuth.class));
            finish();
        }
        mTitle.setText(getAppInfo().getValue(getString(R.string.USER_NAME)));

        mFeedAdapter = new FeedsAdapter(this, getFeedPresenter());
        mFeedAdapter.setMode(Attributes.Mode.Single);
        mContainer.setAdapter(mFeedAdapter);
        mContainer.setLayoutManager(new LinearLayoutManager(this));
        getFeedPresenter().attachView(this);

        mFabAddFeed.attachToRecyclerView(mContainer);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getFeedPresenter().loadFeeds(getAppInfo().getValue(getString(R.string.USER_ID), -1));
    }

    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }

    private FeedPresenter getFeedPresenter() {
        if (mFeedPresenter == null) mFeedPresenter = new FeedPresenter();
        return mFeedPresenter;
    }
}
