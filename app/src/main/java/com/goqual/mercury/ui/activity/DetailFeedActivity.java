package com.goqual.mercury.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.goqual.mercury.R;
import com.goqual.mercury.data.local.ReportDTO;
import com.goqual.mercury.presenter.ReportPresenter;
import com.goqual.mercury.ui.MvpView;
import com.goqual.mercury.ui.adapter.ReportsAdapter;
import com.goqual.mercury.ui.base.BaseActivity;
import com.goqual.mercury.util.Common;

import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ladmusician on 2/24/16.
 */
public class DetailFeedActivity extends BaseActivity implements MvpView<ReportDTO> {
    private final String TAG = "ACTIVITY_DETAIL_FEED";
    @Bind(R.id.detail_feed_report_container)
    RecyclerView mContainer;
    @Bind(R.id.detail_feed_no_report)
    RelativeLayout mNoFeed;

    private ReportPresenter nReportPresenter = null;
    private ReportsAdapter mReportAdapter = null;
    private List<ReportDTO> mReportList = null;
    private int mFeedId;

    @OnClick({R.id.detail_feed_btn_add_report, R.id.detail_feed_btn_back})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.detail_feed_btn_add_report:

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

        mFeedId = getIntent().getIntExtra(getString(R.string.FEED_ID), -1);
        Common.log(TAG, "FEED ID : " + mFeedId);

        mReportAdapter = new ReportsAdapter(getApplicationContext());
        mContainer.setAdapter(mReportAdapter);
        mContainer.setLayoutManager(new LinearLayoutManager(this));
        getReportPresenter().attachView(this);
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
        if (nReportPresenter == null) nReportPresenter = new ReportPresenter(mFeedId);
        return nReportPresenter;
    }
}
