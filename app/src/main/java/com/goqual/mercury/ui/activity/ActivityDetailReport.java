package com.goqual.mercury.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.goqual.mercury.R;
import com.goqual.mercury.data.local.ReportDTO;
import com.goqual.mercury.presenter.ReportPresenter;
import com.goqual.mercury.ui.base.BaseActivity;
import com.goqual.mercury.ui.mvp.DetailMvpView;
import com.goqual.mercury.util.Constant;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ladmusician on 2/25/16.
 */
public class ActivityDetailReport extends BaseActivity implements DetailMvpView<ReportDTO> {
    private final String TAG = "ACTIVITY_DETAIL_REPORT";

    @Bind(R.id.report_content_title)
    TextView mTitle;
    @Bind(R.id.report_content_description)
    TextView mContent;
    @Bind(R.id.report_content_position)
    TextView mPosition;
    @Bind(R.id.report_content_date)
    TextView mDate;
    @Bind(R.id.report_content_img)
    ImageView mImage;

    private Context mContext = null;
    private ReportPresenter mReportPresenter = null;
    private ReportDTO mReport = null;

    @OnClick({R.id.detail_report_btn_back, R.id.detail_report_btn_delete})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.detail_report_btn_back:
                finish();
                break;
            case R.id.detail_report_btn_delete:
                getReportPresenter().deleteReport(mReport.get_reportid());
                break;
        }
    }

    @Override
    public void showItem(ReportDTO item) {
        mTitle.setText(item.getTitle());
        mContent.setText(item.getContent());
        mPosition.setText(item.getLocation());
        mDate.setText(item.getDate());
    }

    @Override
    public void showItems(List<ReportDTO> items) {}

    @Override
    public void showEmptyItems() {
        Toast.makeText(this, "게시물을 로딩하는데 오류가 발생했습니다.", Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    public void showError() {
        Toast.makeText(this, "게시물을 로딩하는데 오류가 발생했습니다.", Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    public void onSuccessDelete() {
        finish();
    }

    @Override
    public void onFailDelete() {
        Toast.makeText(this, "게시물을 삭제하는데 오류가 발생했습니다.", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_report);
        ButterKnife.bind(this);
        getReportPresenter().attachView(this);
        mContext = getApplicationContext();

        mReport = new ReportDTO(
                getIntent().getIntExtra(getString(R.string.REPORT_ID), -1),
                getIntent().getStringExtra(getString(R.string.REPORT_IMG_URL))
        );

        Glide.with(mContext)
                .load(Constant.IMG_BASE_URL + mReport.getImage_url())
                .into(mImage);

        getReportPresenter().loadReport(mReport.get_reportid());
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }

    private ReportPresenter getReportPresenter() {
        if (mReportPresenter == null) mReportPresenter = new ReportPresenter();
        return mReportPresenter;
    }
}
