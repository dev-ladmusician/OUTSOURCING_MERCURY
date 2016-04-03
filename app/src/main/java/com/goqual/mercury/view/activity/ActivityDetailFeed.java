package com.goqual.mercury.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.goqual.mercury.R;
import com.goqual.mercury.data.local.FeedDTO;
import com.goqual.mercury.data.local.ReportDTO;
import com.goqual.mercury.presenter.ReportPresenter;
import com.goqual.mercury.view.adapter.ReportsAdapter;
import com.goqual.mercury.view.base.BaseActivity;
import com.goqual.mercury.view.mvp.DetailMvpView;

import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ladmusician on 2/24/16.
 */
public class ActivityDetailFeed extends BaseActivity implements DetailMvpView<ReportDTO>, RecyclerView.OnItemTouchListener {
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
    private GestureDetectorCompat gestureDetector;

    @OnClick({R.id.detail_feed_btn_add_report, R.id.detail_feed_btn_back})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.detail_feed_btn_add_report:
                Intent intent = new Intent(this, ActivityAddReport.class);
                intent.putExtra(getString(R.string.FEED_ID), mFeed.get_feedid());
                startActivity(intent);
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
    }

    @Override
    public void showError() {
        mReportAdapter.setReportList(Collections.<ReportDTO>emptyList());
        mReportAdapter.notifyDataSetChanged();
        Toast.makeText(this, R.string.ERROR_LOADING_FEEDS, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showItem(ReportDTO item) {}

    @Override
    public void onSuccessDelete() {}

    @Override
    public void onFailDelete() {}

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
        mContainer.addOnItemTouchListener(this);
        gestureDetector =
                new GestureDetectorCompat(getApplicationContext(), new RecyclerViewDemoOnGestureListener());
        getReportPresenter().attachView(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getReportPresenter().loadReports(
                getAppInfo().getValue(getString(R.string.USER_ID), -1),
                mFeed.get_feedid()
        );
    }

    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }

    private ReportPresenter getReportPresenter() {
        if (nReportPresenter == null) nReportPresenter = new ReportPresenter();
        return nReportPresenter;
    }

    private class RecyclerViewDemoOnGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            View view = mContainer.findChildViewUnder(e.getX(), e.getY());
            int position = mContainer.getChildAdapterPosition(view);
            handleItemClick(position);
            return super.onSingleTapConfirmed(e);
        }
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
    private void handleItemClick(int position) {
        if (position != -1 && mReportList != null) {
            Intent intent = new Intent(this, ActivityDetailReport.class);
            intent.putExtra(getString(R.string.REPORT_ID), mReportList.get(position).get_reportid());
            intent.putExtra(getString(R.string.REPORT_IMG_URL), mReportList.get(position).getImage_url());
            startActivity(intent);
        }
    }
}
