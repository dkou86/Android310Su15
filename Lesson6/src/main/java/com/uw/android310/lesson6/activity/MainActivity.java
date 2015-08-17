package com.uw.android310.lesson6.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.uw.android310.lesson6.R;
import com.uw.android310.lesson6.model.Image;
import com.uw.android310.lesson6.model.ImageUpload;
import com.uw.android310.lesson6.service.ImageUploadService;
import com.uw.android310.lesson6.util.DocumentUtils;
import com.uw.android310.lesson6.util.IntentUtils;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MainActivity extends AppCompatActivity {

    @Bind(R.id.imageview)
    ImageView mUploadImage;

    @Bind(R.id.editText_upload_title)
    EditText mUploadTitle;

    @Bind(R.id.editText_upload_desc)
    EditText mUploadDesc;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    /**
     * Upload object containging image and meta data
     */
    private ImageUpload mUpload;

    /**
     * Chosen file from intent
     */
    private File mChosenFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
    }

    @OnClick(R.id.imageview)
    public void onChooseImage() {
        mUploadDesc.clearFocus();
        mUploadTitle.clearFocus();
        IntentUtils.chooseFileIntent(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode != IntentUtils.FILE_PICK) {
            return;
        }

        if (resultCode != RESULT_OK) {
            return;
        }

        Uri returnUri = data.getData();
        String filePath = DocumentUtils.getPath(this, returnUri);

        //Safety check to prevent null pointer exception
        if (filePath == null || filePath.isEmpty()) {
            return;
        }

        mChosenFile = new File(filePath);

        Picasso.with(getBaseContext())
                .load(mChosenFile)
                .placeholder(R.drawable.ic_photo_library_black)
                .fit()
                .into(mUploadImage);

    }

    @OnClick(R.id.fab)
    public void uploadImage() {
        if (mChosenFile == null) {
            return;
        }

        // Wrap the chosen image in an upload object (to be sent to API).
        createUpload(mChosenFile);

        // Initiate upload
        new ImageUploadService(this).execute(mUpload, new UiCallback());
    }

    private void clearInput() {
        mUploadTitle.setText("");
        mUploadDesc.clearFocus();
        mUploadDesc.setText("");
        mUploadTitle.clearFocus();
        mUploadImage.setImageResource(R.drawable.ic_photo_library_black);
    }

    private void createUpload(File image) {
        mUpload = new ImageUpload();
        mUpload.image = image;
        mUpload.title = mUploadTitle.getText().toString();
        mUpload.description = mUploadDesc.getText().toString();
    }

    private class UiCallback implements Callback<Image> {

        @Override
        public void success(Image imageResponse, Response response) {
            // Reset the fields
            clearInput();
        }

        @Override
        public void failure(RetrofitError error) {
            //Assume we have no connection, since error is null
            if (error == null) {
                Snackbar.make(findViewById(R.id.rootView), "No internet connection", Snackbar.LENGTH_SHORT).show();
            }
        }
    }
}
