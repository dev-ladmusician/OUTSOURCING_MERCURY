package com.goqual.mercury.data;

import com.goqual.mercury.data.local.FeedDTO;
import com.goqual.mercury.data.remote.FeedService;

import java.util.List;

import rx.Observable;

/**
 * Created by ladmusician on 2/22/16.
 */
public class DataManager {
    private final String TAG = "MANAGER_DATA";
    private FeedService mFeedService = null;
    private DatabaseHelper mDatabaseHelper= null;

    public Observable<List<FeedDTO>> getFeeds() {
        return getDatabaseHelper().getFeeds().distinct();
        //return getDatabaseHelper().getFeeds();
    }

//    public Observable<FeedDTO> syncFeeds() {
//        return getFeedService().getFeedApi().getFeeds()
//                .concatMap(new Func1<List<FeedDTO>, Observable<FeedDTO>>() {
//                    @Override
//                    public Observable<FeedDTO> call(List<FeedDTO> feeds) {
//                        Common.log(TAG, "THREAD ID : " + Thread.currentThread().getId());
//                        return getDatabaseHelper().setFeeds(feeds);
//                    }
//                });
//    }

    private FeedService getFeedService() {
        if (mFeedService == null)
            mFeedService = new FeedService();

        return mFeedService;
    }

    private DatabaseHelper getDatabaseHelper() {
        if(mDatabaseHelper == null)
            mDatabaseHelper = new DatabaseHelper();

        return mDatabaseHelper;
    }


}
