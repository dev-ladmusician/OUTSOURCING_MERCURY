package com.goqual.mercury.presenter;

import com.goqual.mercury.data.local.UserDTO;
import com.goqual.mercury.ui.mvp.AuthMvpView;
import com.goqual.mercury.util.Common;
import com.goqual.mercury.util.Constant;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AuthPresenter extends BasePresenter<AuthMvpView>{
    private final String TAG = "PRESENTER_AUTH";

    @Override
    public void attachView(AuthMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
    }

    public void login(UserDTO user) {
        getAuthService().getAuthApi().login(user.getUsername(), user.getPassword())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UserDTO>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        getMvpView().onFail();
                    }

                    @Override
                    public void onNext(UserDTO user) {
                        if (user.get_userid() != -1) {
                            getMvpView().onSuccess(
                                    new UserDTO (
                                            user.get_userid(),
                                            user.getName()
                                    ));
                        } else {
                            getMvpView().onFail();
                        }
                    }
                });
    }

    public void join(UserDTO user) {
        getAuthService().getAuthApi().join(user.getName(),user.getUsername(), user.getPassword(), user.getPhone())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UserDTO>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        getMvpView().onFail();
                    }

                    @Override
                    public void onNext(UserDTO user) {
                        if (user.get_userid() > 0) {
                            getMvpView().onSuccess(
                                    new UserDTO(user.get_userid()));
                        } else {
                            getMvpView().onFail();
                        }
                    }
                });
    }

    public void checkUsername(String username) {
        getAuthService().getAuthApi().checkUsername(username)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UserDTO>() {
                    @Override
                    public void onCompleted() {}
                    @Override
                    public void onError(Throwable e) {
                        Common.log(TAG, "ERROR!");
                        e.printStackTrace();
                        getMvpView().onFailReceiveCheckUsername();
                    }
                    @Override
                    public void onNext(UserDTO user) {
                        if (user.get_userid() == -1) {
                            getMvpView().onSuccessReceiveCheckUsername(Constant.CHECK_USERNAME_AVAILABLE);
                        } else {
                            getMvpView().onSuccessReceiveCheckUsername(Constant.CHECK_USERNAME_INAVAILABLE);
                        }
                    }
                });
    }
}
