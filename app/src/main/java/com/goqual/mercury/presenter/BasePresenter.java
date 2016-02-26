package com.goqual.mercury.presenter;

import com.goqual.mercury.data.DataManager;
import com.goqual.mercury.data.remote.AuthService;
import com.goqual.mercury.data.remote.FeedService;
import com.goqual.mercury.data.remote.ReportService;
import com.goqual.mercury.ui.base.BaseMvpView;

/**
 * Base class that implements the Presenter interface and provides a base implementation for
 * attachView() and detachView(). It also handles keeping a reference to the mvpView that
 * can be accessed from the children classes by calling getMvpView().
 */
public class BasePresenter<T extends BaseMvpView> implements Presenter<T> {
    private DataManager mDataManager = null;
    private FeedService mFeedService = null;
    private ReportService mReportService = null;
    private AuthService mAuthService = null;
    private T mMvpView;

    @Override
    public void attachView(T mvpView) {
        mMvpView = mvpView;
    }

    @Override
    public void detachView() {
        mMvpView = null;
    }

    public boolean isViewAttached() {
        return mMvpView != null;
    }

    public T getMvpView() {
        return mMvpView;
    }

    public void checkViewAttached() {
        if (!isViewAttached()) throw new MvpViewNotAttachedException();
    }

    public static class MvpViewNotAttachedException extends RuntimeException {
        public MvpViewNotAttachedException() {
            super("Please call Presenter.attachView(MvpView) before" +
                    " requesting data to the Presenter");
        }
    }

    public FeedService getFeedService() {
        if (mFeedService == null)
            mFeedService = new FeedService();

        return mFeedService;
    }

    public ReportService getReportService() {
        if (mReportService == null)
            mReportService = new ReportService();

        return mReportService;
    }

    public AuthService getAuthService() {
        if (mAuthService == null)
            mAuthService = new AuthService();

        return mAuthService;
    }

    public DataManager getDataManager() {
        if (mDataManager == null)
            mDataManager = new DataManager();
        return mDataManager;
    }
}

