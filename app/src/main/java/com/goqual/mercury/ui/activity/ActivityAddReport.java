package com.goqual.mercury.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.goqual.mercury.R;
import com.goqual.mercury.data.local.FeedDTO;
import com.goqual.mercury.helper.AddReportHelper;
import com.goqual.mercury.ui.base.BaseActivity;
import com.goqual.mercury.ui.mvp.AddMvpView;
import com.goqual.mercury.util.Dialog;
import com.goqual.mercury.util.Keyboard;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by ladmusician on 2/25/16.
 */
public class ActivityAddReport extends BaseActivity implements AddMvpView {
    private final String TAG = "ACTIVITY_ADD_REPORT";
    private final int FLAG_CAMERA = 1;

    @Bind(R.id.add_report_container)
    LinearLayout mContainer;
    @Bind(R.id.add_report_img)
    ImageView mImage;
    @Bind(R.id.add_report_title)
    EditText mTitle;
    @Bind(R.id.add_report_location)
    EditText mLocation;
    @Bind(R.id.add_report_content)
    EditText mContent;

    private Context mContext = null;
    private Activity mActivity = null;
    private AddReportHelper mAddReportHelper = null;
    private RequestBody mImageRequestBody = null;
    private FeedDTO mFeed = null;

    @OnClick({R.id.add_report_btn_back, R.id.add_report_submit, R.id.add_report_container})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_report_btn_back:
                finish();
                break;
            case R.id.add_report_submit:
                saveReport();
                break;
            case R.id.add_report_container:
                Keyboard.hideSoftKeyboard(this);
                break;
        }
    }

    @Override
    public void addSuccess(int reportId) {
        if (reportId > 0) {
            finish();
        }
    }

    @Override
    public void addFail() {
        Toast.makeText(this, "게시물 작성하는데 오류가 발생했습니다.", Toast.LENGTH_LONG).show();
    }

    private void saveReport() {
        if (mTitle.getText().length() != 0) {
            if (mLocation.getText().length() != 0) {
                if (mContent.getText().length() != 0) {
                    getAddReportPresenter().saveReport(
                            getAppInfo().getValue(getString(R.string.USER_ID), -1),
                            mFeed.get_feedid(),
                            mTitle.getText().toString(),
                            mLocation.getText().toString(),
                            mContent.getText().toString(),
                            mImageRequestBody);
                } else {
                    Toast.makeText(this, "내용을 입력해주세요.", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "위치를 입력해주세요.", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "제목을 입력해주세요.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_report);
        ButterKnife.bind(this);
        getAddReportPresenter().attachView(this);
        mContext = getApplicationContext();

        mFeed = new FeedDTO(
                getIntent().getIntExtra(getString(R.string.FEED_ID), -1)
        );

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION, ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        startActivityForResult(cameraIntent, FLAG_CAMERA);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != 0) {
            if (requestCode == FLAG_CAMERA && data != null) {
                // create requestBody
                Uri selectedImageUri = data.getData();
                String realPath = getRealPathFromURI(selectedImageUri);
                File sourceFile = new File(realPath);
                mImageRequestBody = RequestBody.create(MediaType.parse("image/*"), sourceFile);
                mActivity = this;

                Dialog.simpleProgressDialog(
                        getApplicationContext(),
                        "이미지 로딩",
                        "잠시만 기다려주세요."
                );
                Glide.with(mContext)
                        .load(realPath)
                        .listener(new RequestListener<String, GlideDrawable>() {
                            @Override
                            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                Dialog.dismissProgressDialog();
                                Keyboard.showSoftKeyboard(mActivity, mTitle);
                                return false;
                            }
                        })
                        .into(mImage);
            } else {
                finish();
            }
        } else {
            finish();
        }
    }

    public String getRealPathFromURI (Uri contentUri) {
        String path = null;
        String[] proj = { MediaStore.MediaColumns.DATA };
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            path = cursor.getString(column_index);
        }
        cursor.close();
        return path;
    }

    private AddReportHelper getAddReportPresenter() {
        if (mAddReportHelper == null) mAddReportHelper = new AddReportHelper();
        return mAddReportHelper;
    }
}
