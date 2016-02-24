package com.goqual.mercury.data.local;

import com.goqual.mercury.data.model.Report;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by ladmusician on 2/23/16.
 */
public class ReportDAO {
    private Realm mRealm = null;

    public ReportDAO(Realm mRealm) {
        this.mRealm = mRealm;
    }

    public Observable<List<ReportDTO>> gets() {
        mRealm.beginTransaction();
        RealmResults<Report> results = mRealm.where(Report.class).findAll();
        Observable<List<ReportDTO>> observableResult = results.asObservable().map(new Func1<RealmResults<Report>, List<ReportDTO>>() {
            @Override
            public List<ReportDTO> call(RealmResults<Report> Reports) {
                List<ReportDTO> rtv = new ArrayList<ReportDTO>();
                for(Report Report : Reports) {
                    rtv.add(convertToDTO(Report));
                }

                return rtv;
            }
        });
        mRealm.commitTransaction();
        return observableResult;
    }

    public ReportDTO getById(int id) {
        mRealm.beginTransaction();
        Report result = mRealm.where(Report.class)
                .equalTo("id", id)
                .findFirst();

        ReportDTO rtv = convertToDTO(result);
        mRealm.commitTransaction();
        return rtv;
    }

    public int add(final ReportDTO item) {
        mRealm.beginTransaction();
        Report Report = convertToRealmObject(item);
        int realmId = 0;
        Number lastId = mRealm.where(Report.class).max("id");
        if (lastId != null)
            realmId = lastId.intValue() + 1;
        Report.setId(realmId);
        mRealm.copyToRealmOrUpdate(Report);
        mRealm.commitTransaction();

        return realmId;
    }

    public void updateReport(final ReportDTO item) {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                mRealm.copyToRealmOrUpdate(convertToRealmObject(item));
            }
        });
    }

    public void deleteAll() {
        RealmResults<Report> results = mRealm.where(Report.class).findAll();
        mRealm.beginTransaction();
        results.clear();
        mRealm.commitTransaction();
    }


     /* convert logic */
    private ReportDTO convertToDTO(Report item) {
        return new ReportDTO();
    }
    private Report convertToRealmObject(ReportDTO item) {
        return new Report();
    }
}
