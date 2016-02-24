package com.goqual.mercury.util;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.goqual.mercury.R;

/**
 * Created by ladmusician on 16. 1. 22..
 */
public class Dialog {
    private static android.app.ProgressDialog progressDlg = null;
    private static AlertDialog customDialog = null;

    public static void simpleDialog(Context ctx, String title, String msg,
                                           View.OnClickListener ok, View.OnClickListener cancel) {
        if (customDialog == null) {
            try {
                LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View dialog = inflater.inflate(R.layout.dialog_custom, null);
                customDialog = new AlertDialog.Builder(ctx)
                        .setView(dialog)
                        .setTitle(title)
                        .setCancelable(false)
                        .create();
                customDialog.show();

                final TextView content = (TextView) dialog.findViewById(R.id.dialog_content);
                final Button btnOk = (Button) dialog.findViewById(R.id.dialog_btn_ok);
                final Button btnCancel = (Button) dialog.findViewById(R.id.dialog_btn_cancel);

                content.setText(msg);

                btnOk.setOnClickListener(ok);
                btnCancel.setOnClickListener(cancel);
            }catch(Exception e) {
                customDialog = null;
                Common.log("DIALOG", "CANNOT OPEN DIALOG : " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
    public static void simpleDialog(Context ctx, String title, String msg,
                                    View.OnClickListener ok, View.OnClickListener cancel, String txtOk) {
        if (customDialog == null) {
            try {
                LayoutInflater inflater = (LayoutInflater)
                        ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View dialog = inflater.inflate(R.layout.dialog_custom, null);
                customDialog = new AlertDialog.Builder(ctx)
                        .setView(dialog)
                        .setTitle(title)
                        .setCancelable(false)
                        .create();
                customDialog.show();

                final TextView content = (TextView) dialog.findViewById(R.id.dialog_content);
                final Button btnOk = (Button) dialog.findViewById(R.id.dialog_btn_ok);
                final Button btnCancel = (Button) dialog.findViewById(R.id.dialog_btn_cancel);

                btnOk.setText(txtOk);
                content.setText(msg);

                btnOk.setOnClickListener(ok);
                btnCancel.setOnClickListener(cancel);
            }catch(Exception e) {
                customDialog = null;
                Common.log("DIALOG", "CANNOT OPEN DIALOG : " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public static void simpleAlertDialog(Context ctx, String title, String msg,
                                    View.OnClickListener ok, String txtOk) {
        if (customDialog == null) {
            try {
                LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View dialog = inflater.inflate(R.layout.dialog_custom, null);
                customDialog = new AlertDialog.Builder(ctx)
                        .setView(dialog)
                        .setTitle(title)
                        .setCancelable(false)
                        .create();
                customDialog.show();

                final TextView content = (TextView) dialog.findViewById(R.id.dialog_content);
                final Button btnOk = (Button) dialog.findViewById(R.id.dialog_btn_ok);
                final Button btnCancel = (Button) dialog.findViewById(R.id.dialog_btn_cancel);
                btnCancel.setVisibility(View.GONE);

                btnOk.setText(txtOk);
                content.setText(msg);

                btnOk.setOnClickListener(ok);
            }catch(Exception e) {
                customDialog = null;
                Common.log("DIALOG", "CANNOT OPEN DIALOG : " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
    public static void dismissDialog() {
        if(customDialog != null) {
            try {
                customDialog.dismiss();
            }catch(Exception e) {
                Common.log("DIALOG", "ERROR WITH DISMISS DIALOG : " + e.getMessage());
                e.printStackTrace();
            }finally{
                customDialog = null;
            }
        }
    }

    public static void simpleProgressDialog(Context ctx, String title, String msg) {
        if(progressDlg == null) {
            try {
                progressDlg = android.app.ProgressDialog.show (
                        ctx,
                        title,
                        msg,
                        true
                );
                progressDlg.setCancelable(true);
            }catch(Exception e) {
                Common.log("DIALOG", "CANNOT OPEN DIALOG : " + e.getMessage());
                progressDlg = null;
                e.printStackTrace();
            }
        }
    }
    public static void dismissProgressDialog() {
        if(progressDlg != null) {
            try {
                progressDlg.dismiss();
            }catch(Exception e) {
                Common.log("DIALOG", "ERROR WITH DISMISS DIALOG : " + e.getMessage());
                e.printStackTrace();
            }finally{
                progressDlg = null;
            }
        }
    }
}
