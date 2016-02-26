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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.goqual.mercury.R;
import com.goqual.mercury.common.AuthFragmentEvent;
import com.goqual.mercury.common.BusProvider;
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
public class FragmentAuthJoin extends Fragment implements AuthMvpView {
    private Context mContext = null;
    private AuthPresenter mAuthPresenter = null;
    private boolean mIsCheckUsername = false;

    @Bind(R.id.auth_join_name)
    EditText mInputName;
    @Bind(R.id.auth_join_username)
    EditText mInputUsername;
    @Bind(R.id.auth_join_pass)
    EditText mInputPass;
    @Bind(R.id.auth_join_pass_verify)
    EditText mInputPassVerify;
    @Bind(R.id.auth_join_phone)
    EditText mInputNum;
    @Bind(R.id.auth_join_username_check)
    Button mBtnUsernameCheck;
    @Bind(R.id.auth_join_pass_delete)
    ImageView mImgPassVerifyError;

    @OnClick({R.id.join_btn_back, R.id.auth_join_container, R.id.auth_join_username_check, R.id.auth_join_submit})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.join_btn_back:
                BusProvider.getEventBus().post(
                        new AuthFragmentEvent(
                                Constant.BACK_STEP,
                                Constant.FRAGMENT_LOGIN
                        )
                );
                break;
            case R.id.auth_join_container:
                Keyboard.hideSoftKeyboard(getActivity());
                break;
            case R.id.auth_join_username_check:
                if (mInputUsername.getText().length() != 0) {
                    getAuthPresenter().checkUsername(mInputUsername.getText().toString());
                } else {
                    Toast.makeText(getActivity(), "아이디를 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.auth_join_submit:
                if (mInputUsername.getText().length() != 0 &&
                        mInputName.getText().length() != 0 &&
                        mInputPass.getText().length() != 0 &&
                        mInputPassVerify.getText().length() != 0 &&
                        mInputNum.getText().length() != 0) {
                    if (mInputPass.getText().toString().equals(mInputPassVerify.getText().toString())) {
                        if (mIsCheckUsername == Constant.CHECK_USERNAME_AVAILABLE) {
                            getAuthPresenter().join(
                                    new UserDTO (
                                        mInputUsername.getText().toString(),
                                            mInputName.getText().toString(),
                                            mInputPass.getText().toString(),
                                            mInputNum.getText().toString()
                                    )
                            );
                        } else {
                            Toast.makeText(getActivity(), "중복확인을 해주세요.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), "정확한 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                        mImgPassVerifyError.setVisibility(View.VISIBLE);
                        mInputPassVerify.setText("");
                        mInputPassVerify.requestFocus();
                    }
                }
                break;
        }
    }

    @Override
    public void onSuccess(UserDTO user) {
        Toast.makeText(getActivity(), "가입하는데 성공하였습니다.", Toast.LENGTH_SHORT).show();
        mInputName.setText("");
        mInputUsername.setText("");
        mInputPass.setText("");
        mInputPassVerify.setText("");
        mInputNum.setText("");
        BusProvider.getEventBus().post(
                new AuthFragmentEvent(
                        Constant.BACK_STEP,
                        Constant.FRAGMENT_LOGIN
                )
        );
    }

    @Override
    public void onFail() {
        Toast.makeText(getActivity(), "회원가입하는데 실패하였습니다.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccessReceiveCheckUsername(boolean rtv) {
        if (rtv == Constant.CHECK_USERNAME_AVAILABLE) {
            Toast.makeText(getActivity(), "사용가능한 아이디 입니다.", Toast.LENGTH_SHORT).show();
            mIsCheckUsername = Constant.CHECK_USERNAME_AVAILABLE;
        } else {
            Toast.makeText(getActivity(), "이미 등록된 아이디 입니다.", Toast.LENGTH_SHORT).show();
            mInputUsername.setText("");
            mIsCheckUsername = Constant.CHECK_USERNAME_INAVAILABLE;
        }
    }

    @Override
    public void onFailReceiveCheckUsername() {
        Toast.makeText(getActivity(), "중복검사를 하는데 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_auth_join, container, false);
        ButterKnife.bind(this, view);
        mContext = getContext();
        getAuthPresenter().attachView(this);

        mInputPass.setInputType( InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD );
        mInputPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
        mInputPassVerify.setInputType( InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD );
        mInputPassVerify.setTransformationMethod(PasswordTransformationMethod.getInstance());

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
}
