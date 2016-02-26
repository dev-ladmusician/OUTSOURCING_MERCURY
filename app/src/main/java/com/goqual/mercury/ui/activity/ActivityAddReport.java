package com.goqual.mercury.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goqual.mercury.R;
import com.goqual.mercury.helper.AddReportHelper;
import com.goqual.mercury.ui.mvp.AddMvpView;
import com.goqual.mercury.ui.base.BaseActivity;
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
    TextView mTitle;
    @Bind(R.id.add_report_location)
    TextView mLocation;
    @Bind(R.id.add_report_content)
    TextView mContent;

    private Context mContext = null;
    private AddReportHelper mAddReportHelper = null;
    private RequestBody mImageRequestBody = null;

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
//        Dialog.simpleDialog(mContext,
//                "게시물 추가 오류",
//                "게시물 추가하는데 오류가 발생했습니다.",
//                new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Dialog.dismissDialog();
//                        saveReport();
//                    }
//                },
//                new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Dialog.dismissDialog();
//                    }
//                }, "저장");
    }

    private void saveReport() {
        getAddReportPresenter().saveReport(
                mTitle.getText().toString(),
                mLocation.getText().toString(),
                mContent.getText().toString(),
                mImageRequestBody
        );

        if (mTitle.getText().length() != 0 &&
                mContent.getText().length() != 0 &&
                mLocation.getText().length() != 0) {

        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_report);
        ButterKnife.bind(this);
        getAddReportPresenter().attachView(this);
        mContext = getApplicationContext();

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
            if (requestCode == FLAG_CAMERA && !data.equals(null)) {
                Bundle extras = data.getExtras();
                Bitmap bitmap = (Bitmap) extras.get("data");

                Uri selectedImageUri = data.getData();
                File sourceFile = new File(getRealPathFromURI(selectedImageUri));
                mImageRequestBody = RequestBody.create(MediaType.parse("image/*"), sourceFile);

                try {
                    Bitmap bitmapData = rotateImage(bitmap, 90);
                    mImage.setImageBitmap(bitmapData);
                } catch (Exception e) {
                    return;
                }
            }
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

    public static Bitmap rotateImage(Bitmap source, float angle) {
        Bitmap retVal;
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        retVal = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
        return retVal;
    }

    private AddReportHelper getAddReportPresenter() {
        if (mAddReportHelper == null) mAddReportHelper = new AddReportHelper();
        return mAddReportHelper;
    }
}
