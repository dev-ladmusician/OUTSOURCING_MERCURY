package com.goqual.mercury.data.remote;

import com.goqual.mercury.data.RetrofitManager;
import com.goqual.mercury.data.local.ReportDTO;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
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
                @Query("userId") int userId,
                @Query("feedId") int feedId
        );

        @GET("api/report/get_by_id")
        Observable<ReportDTO> getReportById(
                @Query("reportId") int reportId
        );

        @GET("api/report/delete_by_id")
        Observable<ReportDTO> deleteById(
                @Query("reportId") int reportId
        );

        @Multipart
        @POST ("api/report/add")
        Observable<ReportDTO> addReport (
                @Part("img\"; filename=\"mercury.png\" ") RequestBody file,
                @Part("feedId") RequestBody feedId,
                @Part("userId") RequestBody userId,
                @Part("title") RequestBody title,
                @Part("content") RequestBody content,
                @Part("location") RequestBody location
        );
    }
}
