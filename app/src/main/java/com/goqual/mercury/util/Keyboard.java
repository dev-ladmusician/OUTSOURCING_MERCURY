package com.goqual.mercury.util;

import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Created by ladmusician on 16. 2. 16..
 */
public class Keyboard {
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    public static void showSoftKeyboard(Activity activity, final EditText editText){
        final InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        editText.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                editText.requestFocus();
                imm.showSoftInput(editText, InputMethodManager.HIDE_IMPLICIT_ONLY);
            }
        }, 100);
//        editText.requestFocus();
//        editText.setSelection(editText.length());
//        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.toggleSoftInputFromWindow(editText,
//                activity.getCurrentFocus().getWindowToken(),
//                InputMethodManager.SHOW_FORCED,
//                InputMethodManager.HIDE_IMPLICIT_ONLY);
    }
}
