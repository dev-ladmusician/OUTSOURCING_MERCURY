package com.goqual.mercury.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.goqual.mercury.R;
import com.goqual.mercury.data.local.dto.FeedDTO;
import com.goqual.mercury.presenter.FeedPresenter;
import com.goqual.mercury.ui.MainMvpView;
import com.goqual.mercury.ui.adapter.FeedsAdapter;
import com.goqual.mercury.ui.base.BaseActivity;
import com.goqual.mercury.util.Common;

import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements MainMvpView {
    private final String TAG = "ACTIVITY_MAIN";
    @BindString(R.string.EXTRA_TRIGGER_SYNC_FLAG)
    String EXTRA_TRIGGER_SYNC_FLAG;
    @Bind(R.id.main_feed_container)
    RecyclerView mContainer;
    @Bind(R.id.main_total_count_feed)
    TextView mTxtFeedCount;
    @Bind(R.id.fab_add_feed)
    com.melnykov.fab.FloatingActionButton mFabAddFeed;

    private FeedPresenter mFeedPresenter = null;
    private FeedsAdapter mFeedAdapter = null;

    @OnClick(R.id.fab_add_feed)
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_add_feed:
                Common.log(TAG, "FAB CLICK");
                startActivity(new Intent(this, AddFeedActivity.class));
                break;
            default:
                break;
        }
    }

    @Override
    public void showFeeds(List<FeedDTO> feeds) {
        Common.log(TAG, "SHOW FEEDS");
        mTxtFeedCount.setText(feeds.size() + "");
        mFeedAdapter.setFeedList(feeds);
        mFeedAdapter.notifyDataSetChanged();
    }

    @Override
    public void showFeedssEmpty() {
        Common.log(TAG, "SHOW EMPTY FEEDS");
        mTxtFeedCount.setText("0");
        mFeedAdapter.setFeedList(Collections.<FeedDTO>emptyList());
        mFeedAdapter.notifyDataSetChanged();
        Toast.makeText(this, R.string.ERROR_LOADING_FEEDS, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showError() {
        Common.log(TAG, "ERROR!");
        Toast.makeText(this, R.string.ERROR_LOADING_FEEDS, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mFeedAdapter = new FeedsAdapter();
        mContainer.setAdapter(mFeedAdapter);
        mContainer.setLayoutManager(new LinearLayoutManager(this));
        getFeedPresenter().attachView(this);
        getFeedPresenter().loadFeeds();

        mFabAddFeed.attachToRecyclerView(mContainer);

//        if (getIntent().getBooleanExtra(EXTRA_TRIGGER_SYNC_FLAG, true)) {
//            startService(SyncDataService.getStartIntent(this));
//        }
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
