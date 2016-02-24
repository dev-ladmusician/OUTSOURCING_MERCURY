package com.goqual.mercury.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.goqual.mercury.data.DataManager;
import com.goqual.mercury.data.local.FeedDTO;
import com.goqual.mercury.receiver.SyncDataOnConnectionAvailable;
import com.goqual.mercury.util.AndroidComponentUtil;
import com.goqual.mercury.util.Common;
import com.goqual.mercury.util.NetworkUtil;

import rx.Observer;
import rx.Subscription;
import rx.schedulers.Schedulers;

/**
 * Created by ladmusician on 2/23/16.
 */
public class SyncDataService extends Service {
    private final String TAG = "MERCURY_SYNC_DATA_SERVICE";
    private Subscription mSubscription;
    private DataManager mDataManager;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, SyncDataService.class);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, final int startId) {
        if (!NetworkUtil.isNetworkConnected(this)) {
            Common.log(TAG, "Sync canceled, connection not available");
            AndroidComponentUtil.toggleComponent(this, SyncDataOnConnectionAvailable.class, true);
            stopSelf(startId);
            return START_NOT_STICKY;
        }

        if (mSubscription != null && !mSubscription.isUnsubscribed())
            mSubscription.unsubscribe();

        Common.log(TAG, "THREAD ID : " + Thread.currentThread().getId());

        mSubscription = getDataManager()
                .syncFeeds()
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<FeedDTO>() {
                    @Override
                    public void onCompleted() {
                        Common.log(TAG, "SYNC SUCCESSFULLY!");
                        stopSelf(startId);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Common.log(TAG, e.getMessage());
                        Common.log(TAG, "Error syncing!");
                        stopSelf(startId);
                    }

                    @Override
                    public void onNext(FeedDTO feedDTO) {
                    }
                });

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        if (mSubscription != null) mSubscription.unsubscribe();
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private DataManager getDataManager() {
        if (mDataManager == null) mDataManager = new DataManager();
        return mDataManager;
    }
}
