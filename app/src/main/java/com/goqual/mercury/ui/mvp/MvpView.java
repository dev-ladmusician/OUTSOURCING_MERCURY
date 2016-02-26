package com.goqual.mercury.ui.mvp;

import com.goqual.mercury.ui.base.BaseMvpView;

import java.util.List;

/**
 * Created by ladmusician on 2/23/16.
 */
public interface MvpView<T> extends BaseMvpView {
    void showItems(List<T> items);
    void showEmptyItems();
    void showError();

    void onSuccessDelete(int position);
    void onFauleDelete();
}
