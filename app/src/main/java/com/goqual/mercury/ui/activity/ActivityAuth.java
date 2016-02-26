package com.goqual.mercury.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.goqual.mercury.R;
import com.goqual.mercury.common.AuthFragmentEvent;
import com.goqual.mercury.common.BusProvider;
import com.goqual.mercury.ui.adapter.AuthFragmentAdapter;
import com.goqual.mercury.ui.fragment.FragmentAuthJoin;
import com.goqual.mercury.ui.fragment.FragmentAuthLogin;
import com.goqual.mercury.util.Constant;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ladmusician on 16. 2. 12..
 */
public class ActivityAuth extends AppCompatActivity {
    private Context mContext = null;
    private AuthFragmentAdapter mFragmentAdapter = null;
    private List<Fragment> mFragmentList = null;
    @Bind(R.id.auth_container)
    ViewPager mContainer;

    @Subscribe
    public void onReceivePushEvent(AuthFragmentEvent event) {
        if (event.getEVENT() == Constant.LOGIN_DONE) {
            startActivity(new Intent(this, ActivityMain.class));
            finish();
        } else {
            mContainer.setCurrentItem(event.getTARGET_FRAGMENT());
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_container);
        ButterKnife.bind(this);
        BusProvider.getEventBus().register(this);
        mContext = getApplicationContext();
        initElement();
    }

    private void initElement() {
        mFragmentList = new ArrayList<>();
        mFragmentList.add(new FragmentAuthLogin());
        mFragmentList.add(new FragmentAuthJoin());

        mFragmentAdapter = new AuthFragmentAdapter
                (getSupportFragmentManager(), mFragmentList.size(), mFragmentList);
        mContainer.setAdapter(mFragmentAdapter);
        mContainer.setOffscreenPageLimit(mFragmentList.size());
        mContainer.setCurrentItem(0);
    }

    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        BusProvider.getEventBus().unregister(this);
        super.onDestroy();
    }
}
