package com.goqual.mercury.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.goqual.mercury.R;
import com.goqual.mercury.data.local.FeedDTO;
import com.goqual.mercury.presenter.FeedPresenter;
import com.goqual.mercury.ui.MvpView;
import com.goqual.mercury.ui.adapter.FeedsAdapter;
import com.goqual.mercury.ui.base.BaseActivity;
import com.goqual.mercury.util.Common;

import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements MvpView<FeedDTO>, RecyclerView.OnItemTouchListener {
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
    private GestureDetectorCompat gestureDetector;
    private List<FeedDTO> mFeedList = null;

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
    public void showItems(List<FeedDTO> feeds) {
        Common.log(TAG, "SHOW FEEDS");
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
        mContainer.addOnItemTouchListener(this);
        gestureDetector =
                new GestureDetectorCompat(getApplicationContext(), new RecyclerViewDemoOnGestureListener());
        getFeedPresenter().attachView(this);

        mFabAddFeed.attachToRecyclerView(mContainer);

//        if (getIntent().getBooleanExtra(EXTRA_TRIGGER_SYNC_FLAG, true)) {
//            startService(SyncDataService.getStartIntent(this));
//        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getFeedPresenter().loadFeeds();
    }

    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        gestureDetector.onTouchEvent(e);
        return false;
    }
    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {}
    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {}
    private class RecyclerViewDemoOnGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            View view = mContainer.findChildViewUnder(e.getX(), e.getY());
            int position = mContainer.getChildAdapterPosition(view);
            handleClickFeed(position);
            return super.onSingleTapConfirmed(e);
        }
    }
    private void handleClickFeed(int position) {
        Intent intent = new Intent(this, DetailFeedActivity.class);
        intent.putExtra(getString(R.string.FEED_ID), mFeedList.get(position).get_feedid());
        startActivity(intent);
    }

    private FeedPresenter getFeedPresenter() {
        if (mFeedPresenter == null) mFeedPresenter = new FeedPresenter();
        return mFeedPresenter;
    }
}
