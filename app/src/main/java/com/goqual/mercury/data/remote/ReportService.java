package com.goqual.mercury.data.remote;

import com.goqual.mercury.data.RetrofitManager;
import com.goqual.mercury.data.local.ReportDTO;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by ladmusician on 2/23/16.
 */
public class ReportService {
    private ReportApi mReportApi = null;

    public ReportService() {
        mReportApi = RetrofitManager.getRetrofitBuilder().create(ReportApi.class);
    }

    public ReportApi getReportApi() {
        if (mReportApi == null) {
            RetrofitManager.getRetrofitBuilder().create(ReportApi.class);
        }
        return mReportApi;
    }

    public interface ReportApi {
        @GET("api/report/gets")
        Observable<List<ReportDTO>> getReports(
                @Query("feedId") int feedId
        );

        @FormUrlEncoded
        @POST("api/report/add")
        Observable<ReportDTO> addReport(
                @Field("userId") int userId,
                @Field("member") int member,
                @Field("title") String title,
                @Field("content") String content,
                @Field("location") String location
        );
    }
}
