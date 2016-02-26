package com.goqual.mercury.presenter;

import com.goqual.mercury.data.local.FeedDTO;
import com.goqual.mercury.ui.mvp.MvpView;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class FeedPresenter extends BasePresenter<MvpView>{
    private final String TAG = "PRESENTER_FEED";

    @Override
    public void attachView(MvpView mvpView) {
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
                        e.printStackTrace();
                        getMvpView().showError();
                    }

                    @Override
                    public void onNext(List<FeedDTO> feeds) {
                        if (feeds.isEmpty()) {
                            getMvpView().showEmptyItems();
                        } else {
                            getMvpView().showItems(feeds);
                        }
                    }
                });
    }


}
