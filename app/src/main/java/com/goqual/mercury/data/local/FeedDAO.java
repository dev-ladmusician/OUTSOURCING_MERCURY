package com.goqual.mercury.data.local;

import com.goqual.mercury.data.model.Feed;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by ladmusician on 2/23/16.
 */
public class FeedDAO {
    private Realm mRealm = null;

    public FeedDAO(Realm mRealm) {
        this.mRealm = mRealm;
    }

    public Observable<List<FeedDTO>> gets() {
        mRealm.beginTransaction();
        RealmResults<Feed> results = mRealm.where(Feed.class).findAll();
        Observable<List<FeedDTO>> observableResult = results.asObservable().map(new Func1<RealmResults<Feed>, List<FeedDTO>>() {
            @Override
            public List<FeedDTO> call(RealmResults<Feed> feeds) {
                List<FeedDTO> rtv = new ArrayList<FeedDTO>();
                for(Feed feed : feeds) {
                    rtv.add(convertToDTO(feed));
                }

                return rtv;
            }
        });
        mRealm.commitTransaction();
        return observableResult;
    }

    public FeedDTO getById(int id) {
        mRealm.beginTransaction();
        Feed result = mRealm.where(Feed.class)
                .equalTo("id", id)
                .findFirst();

        FeedDTO rtv = convertToDTO(result);
        mRealm.commitTransaction();
        return rtv;
    }

    public int add(final FeedDTO item) {
        mRealm.beginTransaction();
        Feed feed = convertToRealmObject(item);
        int realmId = 0;
        Number lastId = mRealm.where(Feed.class).max("id");
        if (lastId != null)
            realmId = lastId.intValue() + 1;
        feed.setId(realmId);
        mRealm.copyToRealmOrUpdate(feed);
        mRealm.commitTransaction();

        return realmId;
    }

    public void updateFeed(final FeedDTO item) {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                mRealm.copyToRealmOrUpdate(convertToRealmObject(item));
            }
        });
    }

    public void deleteAll() {
        RealmResults<Feed> results = mRealm.where(Feed.class).findAll();
        mRealm.beginTransaction();
        results.clear();
        mRealm.commitTransaction();
    }


     /* convert logic */
    private FeedDTO convertToDTO(Feed item) {
        return new FeedDTO();
    }
    private Feed convertToRealmObject(FeedDTO item) {
        return new Feed();
    }
}
