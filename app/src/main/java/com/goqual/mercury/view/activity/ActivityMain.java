package com.goqual.mercury.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.util.Attributes;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.goqual.mercury.R;
import com.goqual.mercury.data.local.FeedDTO;
import com.goqual.mercury.presenter.FeedPresenter;
import com.goqual.mercury.util.Common;
import com.goqual.mercury.view.adapter.FeedsAdapter;
import com.goqual.mercury.view.base.BaseActivity;
import com.goqual.mercury.view.mvp.MvpView;

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
    @Bind(R.id.fab_expand_transparent)
    RelativeLayout mFabTransparent;
    @Bind(R.id.main_feed_no_report)
    RelativeLayout mNoFeedTitle;

    private FeedPresenter mFeedPresenter = null;
    private FeedsAdapter mFeedAdapter = null;
    private List<FeedDTO> mFeedList = null;
    private int REQUEST_CODE = 0;
    private int RESULT_CODE = 100;
    private FloatingActionsMenu mFloatingMenu;

    @OnClick({R.id.main_logout})
    void onClick(View v) {
        switch (v.getId()) {
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
    public void onFailDelete() {
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

        mFeedAdapter = new FeedsAdapter(this, getFeedPresenter());
        mFeedAdapter.setMode(Attributes.Mode.Single);
        mContainer.setAdapter(mFeedAdapter);
        mContainer.setLayoutManager(new LinearLayoutManager(this));
        getFeedPresenter().attachView(this);

        init();
    }

    private void init() {
        mFloatingMenu =
                (FloatingActionsMenu)findViewById(R.id.fab_container);
        mFloatingMenu.setOnFloatingActionsMenuUpdateListener(new FloatingActionsMenu.OnFloatingActionsMenuUpdateListener() {
            @Override
            public void onMenuExpanded() {
                mFabTransparent.setVisibility(View.VISIBLE);
            }

            @Override
            public void onMenuCollapsed() {
                mFabTransparent.setVisibility(View.GONE);
            }
        });


        /** feed 추가 **/
        final com.getbase.floatingactionbutton.FloatingActionButton addFeed =
                (com.getbase.floatingactionbutton.FloatingActionButton) findViewById(R.id.fab_add_feed);
        addFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //handleClickFab();
                startActivity(new Intent(getApplicationContext(), ActivityAddFeed.class));
            }
        });

        /** report 추가 **/
        final com.getbase.floatingactionbutton.FloatingActionButton addReport =
                (com.getbase.floatingactionbutton.FloatingActionButton) findViewById(R.id.fab_add_report);
        addReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ActivityAddReport.class);
                intent.putExtra(getString(R.string.FEED_ID), mFeedList.get(0).get_feedid());
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_CODE) {
            Intent intent = new Intent(getApplicationContext(), ActivityDetailFeed.class);
            intent.putExtra(getString(R.string.FEED_ID), mFeedList.get(0).get_feedid());
            intent.putExtra(getString(R.string.FEED_TITLE), mFeedList.get(0).getTitle());
            intent.putExtra(getString(R.string.FEED_PERIOD), mFeedList.get(0).getPeriod());
            startActivity(intent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mFloatingMenu.collapse();
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
