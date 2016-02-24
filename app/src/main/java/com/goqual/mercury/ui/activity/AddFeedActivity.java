package com.goqual.mercury.ui.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.borax12.materialdaterangepicker.date.DatePickerDialog;
import com.goqual.mercury.R;
import com.goqual.mercury.presenter.AddFeedPresenter;
import com.goqual.mercury.ui.AddFeedMvpView;
import com.goqual.mercury.util.Common;
import com.goqual.mercury.util.Dialog;
import com.goqual.mercury.util.Keyboard;

import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddFeedActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, AddFeedMvpView{
    private final String TAG = "ACTIVITY_ADD_FEED";
    private String mStarted = "";
    private String mEnded = "";
    private String mTitle = "";

    private Context mContext = null;
    private DatePickerDialog mDatePicker = null;
    private AddFeedPresenter mAddFeedPresenter = null;

    @Bind(R.id.add_feed_title)
    EditText mEditTitle;
    @Bind(R.id.add_feed_started)
    TextView mTxtStarted;
    @Bind(R.id.add_feed_ended)
    TextView mTxtEnded;
    @Bind(R.id.add_feed_container)
    LinearLayout mContainer;

    @OnClick({R.id.add_feed_submit, R.id.add_feed_container})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_feed_submit:
                mTitle = mEditTitle.getText().toString();
                if (!mTitle.equals(""))
                    getAddFeedPresenter().saveFeed(mTitle, mStarted, mEnded);
                break;
            case R.id.add_feed_container:
                Keyboard.hideSoftKeyboard(this);
                break;
            default:
                break;
        }
    }

    @Override
    public void addSuccess(int feedId) {
        if (feedId > 0) {
            finish();
        }
    }

    @Override
    public void addFail() {
        Dialog.simpleDialog(mContext,
                "게시물 추가 오류",
                "게시물 추가하는데 오류가 발생했습니다.",
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Dialog.dismissDialog();
                        getAddFeedPresenter().saveFeed(mTitle, mStarted, mEnded);
                    }
                },
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Dialog.dismissDialog();
                    }
                }, "저장");
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_feed);
        ButterKnife.bind(this);
        getAddFeedPresenter().attachView(this);
        mContext = getApplicationContext();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mStarted.equals("") || mEnded.equals(""))
            getDatePicker().show(getFragmentManager(), "Datepickerdialog");
    }

    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }

    @Override
    public void onDateSet(DatePickerDialog view, final int startYear, final int startMonth, final int startDay,
                          final int endYear, final int endMonth, final int endDay) {
        if  (startYear > endYear ||
                (startYear == endYear && startMonth > endMonth) ||
                (startYear == endYear && startMonth == endMonth && startDay > endDay) ||
                (startYear == endYear && startMonth == endMonth && startDay == endDay)) {
            mStarted = new StringBuffer().append(startYear).append('-').append(startMonth).append('-').append(startDay).toString();
            mEnded = mStarted;
        } else {
            mStarted = new StringBuffer().append(startYear).append('-').append(startMonth).append('-').append(startDay).toString();
            mEnded = new StringBuffer().append(endYear).append('-').append(endMonth).append('-').append(endDay).toString();
        }

        mTxtStarted.setText(mStarted);
        mTxtEnded.setText(mEnded);
        Keyboard.showSoftKeyboard(this, mEditTitle);
    }

    private DatePickerDialog getDatePicker() {
        if(mDatePicker == null) {
            Calendar now = Calendar.getInstance();
            mDatePicker = com.borax12.materialdaterangepicker.date.DatePickerDialog.newInstance(
                    AddFeedActivity.this,
                    now.get(Calendar.YEAR),
                    now.get(Calendar.MONTH),
                    now.get(Calendar.DAY_OF_MONTH)

            );

            mDatePicker.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    Common.log(TAG, "CANCEL");
                    finish();
                }
            });
        }

        return mDatePicker;
    }

    private AddFeedPresenter getAddFeedPresenter() {
        if (mAddFeedPresenter == null) mAddFeedPresenter = new AddFeedPresenter();
        return mAddFeedPresenter;
    }
}
