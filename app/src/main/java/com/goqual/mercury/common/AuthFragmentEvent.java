package com.goqual.mercury.common;

/**
 * Created by ladmusician on 16. 2. 12..
 */
public class AuthFragmentEvent {
    private int EVENT;
    private int TARGET_FRAGMENT;

    public AuthFragmentEvent(int EVENT, int TARGET_FRAGMENT) {
        this.EVENT = EVENT;
        this.TARGET_FRAGMENT = TARGET_FRAGMENT;
    }

    public int getEVENT() {
        return EVENT;
    }

    public void setEVENT(int EVENT) {
        this.EVENT = EVENT;
    }

    public int getTARGET_FRAGMENT() {
        return TARGET_FRAGMENT;
    }

    public void setTARGET_FRAGMENT(int TARGET_FRAGMENT) {
        this.TARGET_FRAGMENT = TARGET_FRAGMENT;
    }
}
