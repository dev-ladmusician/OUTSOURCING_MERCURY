package com.goqual.mercury.presenter;

import com.goqual.mercury.data.local.ReportDTO;
import com.goqual.mercury.view.mvp.DetailMvpView;
import com.goqual.mercury.util.Common;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ReportPresenter extends BasePresenter<DetailMvpView>{
    private final String TAG = "PRESENTER_REPORT";

    @Override
    public void attachView(DetailMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
    }

    public void loadReports(int userId, int feedId) {
        getReportService().getReportApi().getReports(userId, feedId)
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

    public void loadReport(int reportId) {
        getReportService().getReportApi().getReportById(reportId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ReportDTO>() {
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
                    public void onNext(ReportDTO report) {
                        Common.log(TAG, "get reports successfully!");
                        if (report != null && report.get_reportid() > 0) {
                            getMvpView().showItem(report);
                        } else {
                            getMvpView().showError();
                        }
                    }
                });
    }

    public void deleteReport(int reportId) {
        getReportService().getReportApi().deleteById(reportId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ReportDTO>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Common.log(TAG, "There was an error loading the reports.");
                        getMvpView().onFailDelete();
                    }

                    @Override
                    public void onNext(ReportDTO report) {
                        if (report != null && report.get_reportid() != 0) {
                            getMvpView().onSuccessDelete();
                        } else {
                            getMvpView().onFailDelete();
                        }
                    }
                });
    }
}
