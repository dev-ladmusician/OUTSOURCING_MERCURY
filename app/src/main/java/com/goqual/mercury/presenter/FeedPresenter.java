package com.goqual.mercury.presenter;

import com.goqual.mercury.data.local.FeedDTO;
import com.goqual.mercury.view.mvp.MvpView;

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

    public void deleteFeed(final int position, int feedId) {
        getFeedService().getFeedApi().deleteById(feedId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<FeedDTO>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        getMvpView().onFailDelete();
                    }

                    @Override
                    public void onNext(FeedDTO feed) {
                        if (feed != null && feed.get_feedid() != 0) {
                            getMvpView().onSuccessDelete(position);
                        } else {
                            getMvpView().onFailDelete();
                        }
                    }
                });
    }

    public void loadFeeds(int userId) {
        getFeedService().getFeedApi().getFeeds(userId)
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
