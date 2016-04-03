package com.goqual.mercury.view.mvp;

import com.goqual.mercury.view.base.BaseMvpView;

import java.util.List;

/**
 * Created by ladmusician on 2/23/16.
 */
public interface DetailMvpView<T> extends BaseMvpView {
    void showItems(List<T> items);
    void showItem(T item);
    void showEmptyItems();
    void showError();

    void onSuccessDelete();
    void onFailDelete();
}
