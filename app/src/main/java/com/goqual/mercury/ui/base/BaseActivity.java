package com.goqual.mercury.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.goqual.mercury.common.InfoSharedPreference;

public class BaseActivity extends AppCompatActivity {
    public InfoSharedPreference mAppInfo = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public InfoSharedPreference getAppInfo() {
        if (mAppInfo == null) {
            mAppInfo = new InfoSharedPreference(getApplicationContext());
        }

        return mAppInfo;
    }
}
