package com.goqual.mercury.presenter;

import com.goqual.mercury.data.local.ReportDTO;
import com.goqual.mercury.ui.MvpView;
import com.goqual.mercury.util.Common;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ReportPresenter extends BasePresenter<MvpView>{
    private int mFeedId;
    private final String TAG = "PRESENTER_REPORT";

    public ReportPresenter(int mFeedId) {
        this.mFeedId = mFeedId;
    }

    @Override
    public void attachView(MvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
    }

    public void loadReports() {
        Common.log(TAG, "FEED ID : " + mFeedId);
        getReportService().getReportApi().getReports(mFeedId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<ReportDTO>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Common.log(TAG, "There was an error loading the feeds.");
                        getMvpView().showError();
                    }

                    @Override
                    public void onNext(List<ReportDTO> reports) {
                        Common.log(TAG, "get feed successfully!");
                        if (reports.isEmpty()) {
                            getMvpView().showEmptyItems();
                        } else {
                            getMvpView().showItems(reports);
                        }
                    }
                });
    }
}
