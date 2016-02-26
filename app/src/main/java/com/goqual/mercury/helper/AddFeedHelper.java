package com.goqual.mercury.helper;

import com.goqual.mercury.data.local.FeedDTO;
import com.goqual.mercury.presenter.BasePresenter;
import com.goqual.mercury.ui.mvp.AddMvpView;
import com.goqual.mercury.util.Common;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AddFeedHelper extends BasePresenter<AddMvpView> {
    private final String TAG = "PRESENTER_ADD_FEED";
    @Override
    public void attachView(AddMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
    }

    public void saveFeed(String title, String started, String ended) {
        getFeedService().getFeedApi().addFeed(1, title, started, ended)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<FeedDTO>() {
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
                    public void onNext(FeedDTO feed) {
                        Common.log(TAG, "add feed successfully!");
                        if (feed.get_feedid() > 0) {
                            getMvpView().addSuccess(feed.get_feedid());
                        } else {
                            getMvpView().addFail();
                        }
                    }
                });
    }
}
