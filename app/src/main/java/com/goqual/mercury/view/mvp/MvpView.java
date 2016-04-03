package com.goqual.mercury.view.mvp;

import com.goqual.mercury.view.base.BaseMvpView;

import java.util.List;

/**
 * Created by ladmusician on 2/23/16.
 */
public interface MvpView<T> extends BaseMvpView {
    void showItems(List<T> items);
    void showEmptyItems();
    void showError();

    void onSuccessDelete(int position);
    void onFailDelete();
}
