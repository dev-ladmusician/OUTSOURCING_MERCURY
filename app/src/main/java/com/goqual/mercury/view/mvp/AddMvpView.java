package com.goqual.mercury.view.mvp;

import com.goqual.mercury.view.base.BaseMvpView;

/**
 * Created by ladmusician on 2/23/16.
 */
public interface AddMvpView extends BaseMvpView {
    void addSuccess(int feedId);
    void addFail();
}
