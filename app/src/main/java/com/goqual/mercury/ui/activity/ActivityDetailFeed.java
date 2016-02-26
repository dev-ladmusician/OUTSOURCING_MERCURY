package com.goqual.mercury.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.goqual.mercury.R;
import com.goqual.mercury.data.local.FeedDTO;
import com.goqual.mercury.data.local.ReportDTO;
import com.goqual.mercury.presenter.ReportPresenter;
import com.goqual.mercury.ui.adapter.ReportsAdapter;
import com.goqual.mercury.ui.base.BaseActivity;
import com.goqual.mercury.ui.mvp.DetailFeedMvpView;

import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ladmusician on 2/24/16.
 */
public class ActivityDetailFeed extends BaseActivity implements DetailFeedMvpView<ReportDTO> {
    private final String TAG = "ACTIVITY_DETAIL_FEED";
    @Bind(R.id.detail_feed_report_container)
    RecyclerView mContainer;
    @Bind(R.id.detail_feed_no_report)
    RelativeLayout mNoFeed;
    @Bind(R.id.detail_feed_title)
    TextView mTitle;
    @Bind(R.id.detail_feed_period)
    TextView mPeriod;

    private ReportPresenter nReportPresenter = null;
    private ReportsAdapter mReportAdapter = null;
    private List<ReportDTO> mReportList = null;
    private FeedDTO mFeed = null;

    @OnClick({R.id.detail_feed_btn_add_report, R.id.detail_feed_btn_back})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.detail_feed_btn_add_report:
                startActivity(new Intent(this, ActivityAddReport.class));
                break;
            case R.id.detail_feed_btn_back:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void showItems(List<ReportDTO> reports) {
        mNoFeed.setVisibility(View.GONE);
        mReportList = reports;
        mReportAdapter.setReportList(mReportList);
        mReportAdapter.notifyDataSetChanged();
    }

    @Override
    public void showEmptyItems() {
        mNoFeed.setVisibility(View.VISIBLE);
        mReportAdapter.setReportList(Collections.<ReportDTO>emptyList());
        mReportAdapter.notifyDataSetChanged();
        //Toast.makeText(this, R.string.ERROR_LOADING_FEEDS, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showFeed(FeedDTO feed) {
        mTitle.setText(feed.getTitle());
        mPeriod.setText(feed.getPeriod());
    }

    @Override
    public void showFeedError() {

    }

    @Override
    public void showError() {
        mReportAdapter.setReportList(Collections.<ReportDTO>emptyList());
        mReportAdapter.notifyDataSetChanged();
        Toast.makeText(this, R.string.ERROR_LOADING_FEEDS, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_feed);
        ButterKnife.bind(this);

        mFeed = new FeedDTO(
                getIntent().getIntExtra(getString(R.string.FEED_ID), -1),
                getIntent().getStringExtra(getString(R.string.FEED_TITLE)),
                getIntent().getStringExtra(getString(R.string.FEED_PERIOD))
        );

        mTitle.setText(mFeed.getTitle());
        mPeriod.setText(mFeed.getPeriod());

        mReportAdapter = new ReportsAdapter(getApplicationContext());
        mContainer.setAdapter(mReportAdapter);
        mContainer.setLayoutManager(new LinearLayoutManager(this));
        getReportPresenter().attachView(this);
        //getReportPresenter().getFeed();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getReportPresenter().loadReports();
    }

    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }

    private ReportPresenter getReportPresenter() {
        if (nReportPresenter == null) nReportPresenter = new ReportPresenter(mFeed.get_feedid());
        return nReportPresenter;
    }
}
