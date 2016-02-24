package com.goqual.mercury.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.borax12.materialdaterangepicker.date.DatePickerDialog;
import com.goqual.mercury.R;
import com.goqual.mercury.util.Common;

import java.util.Calendar;

import butterknife.ButterKnife;

/**
 * Created by ladmusician on 2/24/16.
 */
public class AddFeedActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    private final String TAG = "ACTIVITY_ADD_FEED";
    private String mStarted = "";
    private String mEnded = "";
    private String mTitle = "";

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth, int yearEnd, int monthOfYearEnd, int dayOfMonthEnd) {
        Common.log(TAG, "day : " + dayOfMonth);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_feed);
        ButterKnife.bind(this);

        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = com.borax12.materialdaterangepicker.date.DatePickerDialog.newInstance(
                AddFeedActivity.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.show(getFragmentManager(), "Datepickerdialog");
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        DatePickerDialog dpd = (DatePickerDialog) getFragmentManager().findFragmentByTag("Datepickerdialog");
        if(dpd != null) dpd.setOnDateSetListener(this);
    }

    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }
}
