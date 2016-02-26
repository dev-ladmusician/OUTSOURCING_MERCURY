package com.goqual.mercury.data.remote;

import com.goqual.mercury.data.RetrofitManager;
import com.goqual.mercury.data.local.FeedDTO;

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
public class FeedService {
    private FeedApi mFeedApi = null;

    public FeedService() {
        mFeedApi = RetrofitManager.getRetrofitBuilder().create(FeedApi.class);
    }

    public FeedApi getFeedApi() {
        if (mFeedApi == null) {
            RetrofitManager.getRetrofitBuilder().create(FeedApi.class);
        }
        return mFeedApi;
    }

    public interface FeedApi {
        @GET("api/feed/gets")
        Observable<List<FeedDTO>> getFeeds();

        @GET("api/feed/get_by_id")
        Observable<FeedDTO> getFeedById(
                @Query("feedId") int feedId
        );

        @FormUrlEncoded
        @POST("api/feed/add")
        Observable<FeedDTO> addFeed (
                @Field("userId") int userId,
                @Field("title") String title,
                @Field("started") String started,
                @Field("ended") String ened
        );
    }
}
