package com.goqual.mercury.data;

import com.goqual.mercury.data.local.FeedDAO;
import com.goqual.mercury.data.local.FeedDTO;
import com.goqual.mercury.util.Common;

import java.util.Collection;
import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by ladmusician on 2/23/16.
 */
public class DatabaseHelper {
    private final String TAG = "MERCURY_HELPER_DATABASE";

    public Observable<List<FeedDTO>> getFeeds() {
        return new RealmManager().createFeedDAO().gets();
    }

    public Observable<FeedDTO> setFeeds(final Collection<FeedDTO> newFeeds) {
        return Observable.create(new Observable.OnSubscribe<FeedDTO>() {
            @Override
            public void call(Subscriber<? super FeedDTO> subscriber) {
                if (subscriber.isUnsubscribed()) return;
                Common.log(TAG, "THREAD ID : " + Thread.currentThread().getId());
                try {
                    FeedDAO dao = new RealmManager().createFeedDAO();
                    dao.deleteAll();
                    Common.log(TAG, "TEST");
                    for(FeedDTO feed : newFeeds) {
                        int result = dao.add(feed);
                        if (result >= 0) subscriber.onNext(feed);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Common.log(TAG, "RETROFIT FAIL");
                    subscriber.onError(e);
                } finally {
                    subscriber.onCompleted();
                }
            }
        });
    }
}
