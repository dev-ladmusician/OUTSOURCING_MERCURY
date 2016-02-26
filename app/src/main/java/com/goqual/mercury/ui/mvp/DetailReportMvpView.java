package com.goqual.mercury.ui.mvp;

import com.goqual.mercury.ui.base.BaseMvpView;

/**
 * Created by ladmusician on 2/23/16.
 */
public interface DetailReportMvpView<T> extends BaseMvpView {
    void showItem(T item);
    void showEmptyItem();
    void showError();
}
