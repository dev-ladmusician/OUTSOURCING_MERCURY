package com.goqual.mercury.presenter;

import com.goqual.mercury.data.local.FeedDTO;
import com.goqual.mercury.data.local.ReportDTO;
import com.goqual.mercury.ui.mvp.DetailFeedMvpView;
import com.goqual.mercury.util.Common;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ReportPresenter extends BasePresenter<DetailFeedMvpView>{
    private int mFeedId;
    private final String TAG = "PRESENTER_REPORT";

    public ReportPresenter(int mFeedId) {
        this.mFeedId = mFeedId;
    }

    @Override
    public void attachView(DetailFeedMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
    }

    public void getFeed() {
        getFeedService().getFeedApi().getFeedById(mFeedId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<FeedDTO>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Common.log(TAG, "There was an error loading the feed.");
                        getMvpView().showFeedError();
                    }

                    @Override
                    public void onNext(FeedDTO feed) {
                        Common.log(TAG, "get feed successfully!");
                        if (feed != null && feed.get_feedid() != 0) {
                            getMvpView().showFeed(feed);
                        } else {
                            getMvpView().showFeedError();
                        }
                    }
                });
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
                        Common.log(TAG, "There was an error loading the reports.");
                        getMvpView().showError();
                    }

                    @Override
                    public void onNext(List<ReportDTO> reports) {
                        Common.log(TAG, "get reports successfully!");
                        if (reports.isEmpty()) {
                            getMvpView().showEmptyItems();
                        } else {
                            getMvpView().showItems(reports);
                        }
                    }
                });
    }
}
