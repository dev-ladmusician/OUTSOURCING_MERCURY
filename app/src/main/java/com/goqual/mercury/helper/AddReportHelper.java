package com.goqual.mercury.helper;

import com.goqual.mercury.data.local.ReportDTO;
import com.goqual.mercury.presenter.BasePresenter;
import com.goqual.mercury.ui.AddMvpView;
import com.goqual.mercury.util.Common;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AddReportHelper extends BasePresenter<AddMvpView> {
    private final String TAG = "HELPER_ADD_REPORT";
    @Override
    public void attachView(AddMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
    }

    public void saveReport(String title, String location, String content, RequestBody img) {
        RequestBody requestBodyUserId = RequestBody.create(MediaType.parse("text/plain"), "1");
        RequestBody requestBodyTitle = RequestBody.create(MediaType.parse("text/plain"), title);
        RequestBody requestBodyContent = RequestBody.create(MediaType.parse("text/plain"), content);
        RequestBody requestBodyLocation = RequestBody.create(MediaType.parse("text/plain"), location);

        getReportService().getReportApi().addReport(img, requestBodyUserId, requestBodyTitle, requestBodyContent, requestBodyLocation)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ReportDTO>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Common.log(TAG, "There was an error add the feed.");
                        e.printStackTrace();
                        getMvpView().addFail();
                    }

                    @Override
                    public void onNext(ReportDTO report) {
                        Common.log(TAG, "add report successfully!");
                        if (report.get_reportid() > 0) {
                            getMvpView().addSuccess(report.get_reportid());
                        } else {
                            getMvpView().addFail();
                        }
                    }
                });
    }
}
