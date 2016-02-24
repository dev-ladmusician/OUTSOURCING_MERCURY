package com.goqual.mercury.presenter;

import com.goqual.mercury.data.local.dto.FeedDTO;
import com.goqual.mercury.ui.MainMvpView;
import com.goqual.mercury.util.Common;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class FeedPresenter extends BasePresenter<MainMvpView>{
    private final String TAG = "PRESENTER_FEED";

    @Override
    public void attachView(MainMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
    }

    public void loadFeeds() {
        getFeedService().getFeedApi().getFeeds()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<FeedDTO>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Common.log(TAG, "There was an error loading the feeds.");
                        getMvpView().showError();
                    }

                    @Override
                    public void onNext(List<FeedDTO> feeds) {
                        Common.log(TAG, "get feed successfully!");
                        if (feeds.isEmpty()) {
                            getMvpView().showFeedssEmpty();
                        } else {
                            getMvpView().showFeeds(feeds);
                        }
                    }
                });
    }


}
