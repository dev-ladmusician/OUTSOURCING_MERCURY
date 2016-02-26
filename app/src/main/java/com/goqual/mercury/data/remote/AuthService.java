package com.goqual.mercury.data.remote;

import com.goqual.mercury.data.RetrofitManager;
import com.goqual.mercury.data.local.UserDTO;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by ladmusician on 2/23/16.
 */
public class AuthService {
    private AuthApi mAuthApi = null;

    public AuthService() {
        mAuthApi = RetrofitManager.getRetrofitBuilder().create(AuthApi.class);
    }

    public AuthApi getAuthApi() {
        if (mAuthApi == null) {
            RetrofitManager.getRetrofitBuilder().create(AuthApi.class);
        }
        return mAuthApi;
    }

    public interface AuthApi {
        @FormUrlEncoded
        @POST("api/user/check_username")
        Observable<UserDTO> checkUsername(
                @Field("username") String username
        );

        @FormUrlEncoded
        @POST("api/user/login")
        Observable<UserDTO> login(
                @Field("username") String username,
                @Field("password") String password
        );

        @FormUrlEncoded
        @POST("api/user/join")
        Observable<UserDTO> join(
                @Field("name") String name,
                @Field("username") String username,
                @Field("password") String password,
                @Field("phone") String phone
        );
    }
}
