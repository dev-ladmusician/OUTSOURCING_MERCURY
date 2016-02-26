package com.goqual.mercury.ui.mvp;

import com.goqual.mercury.data.local.UserDTO;
import com.goqual.mercury.ui.base.BaseMvpView;

/**
 * Created by ladmusician on 2/23/16.
 */
public interface AuthMvpView extends BaseMvpView {
    void onSuccess(UserDTO user);
    void onFail();

    void onSuccessReceiveCheckUsername(boolean rtv);
    void onFailReceiveCheckUsername();
}
