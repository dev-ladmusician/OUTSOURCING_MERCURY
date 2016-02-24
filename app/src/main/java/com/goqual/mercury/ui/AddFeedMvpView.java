package com.goqual.mercury.ui;

import com.goqual.mercury.ui.base.BaseMvpView;

/**
 * Created by ladmusician on 2/23/16.
 */
public interface AddFeedMvpView extends BaseMvpView {
    void addSuccess(int feedId);
    void addFail();
}
