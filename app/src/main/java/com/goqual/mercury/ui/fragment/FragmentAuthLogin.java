package com.goqual.mercury.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.goqual.mercury.R;
import com.goqual.mercury.common.AuthFragmentEvent;
import com.goqual.mercury.common.BusProvider;
import com.goqual.mercury.common.InfoSharedPreference;
import com.goqual.mercury.data.local.UserDTO;
import com.goqual.mercury.presenter.AuthPresenter;
import com.goqual.mercury.ui.mvp.AuthMvpView;
import com.goqual.mercury.util.Constant;
import com.goqual.mercury.util.Keyboard;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ladmusician on 16. 2. 12..
 */
public class FragmentAuthLogin extends Fragment implements AuthMvpView {
    private Context mContext = null;
    private AuthPresenter mAuthPresenter = null;
    private InfoSharedPreference mAppInfo = null;

    @Bind(R.id.auth_login_username)
    EditText mEditUsername;
    @Bind(R.id.auth_login_pass)
    EditText mEditPassword;

    @OnClick({R.id.login_btn_join, R.id.auth_login_submit, R.id.login_container})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_container:
                Keyboard.hideSoftKeyboard(getActivity());
                break;
            case R.id.login_btn_join:
                BusProvider.getEventBus().post(
                        new AuthFragmentEvent(
                                Constant.NEXT_STEP,
                                Constant.FRAGMENT_JOIN
                        )
                );
                break;
            case R.id.auth_login_submit:
                if (mEditUsername.getText().length() != 0 && mEditPassword.getText().length() != 0) {
                    getAuthPresenter().login(
                            new UserDTO(mEditUsername.getText().toString(),
                                    mEditPassword.getText().toString()));
                }
                break;
        }
    }

    @Override
    public void onSuccess(UserDTO user) {
        getAppInfo().put(getString(R.string.USER_ID), user.get_userid());
        getAppInfo().put(getString(R.string.USER_NAME), user.getName());
        BusProvider.getEventBus().post(
                new AuthFragmentEvent(
                        Constant.LOGIN_DONE,
                        Constant.FRAGMENT_LOGIN
                )
        );
    }

    @Override
    public void onFail() {
        Toast.makeText(getActivity(), "로그인에 실패하였습니다.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccessReceiveCheckUsername(boolean rtv) {
    }

    @Override
    public void onFailReceiveCheckUsername() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_auth_login, container, false);
        ButterKnife.bind(this, view);
        mContext = getContext();
        getAuthPresenter().attachView(this);

        mEditPassword.setInputType( InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD );
        mEditPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

        return view;
    }

    @Override
    public void onDestroyView() {
        ButterKnife.unbind(this);
        super.onDestroyView();
    }

    private AuthPresenter getAuthPresenter() {
        if (mAuthPresenter == null) mAuthPresenter = new AuthPresenter();
        return mAuthPresenter;
    }

    public InfoSharedPreference getAppInfo() {
        if (mAppInfo == null) {
            mAppInfo = new InfoSharedPreference(getContext());
        }

        return mAppInfo;
    }
}
