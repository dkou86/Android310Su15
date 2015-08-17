package com.uw.android310.lesson6.service;


import android.content.Context;
import android.util.Log;

import com.uw.android310.lesson6.api.ImgurRestApi;
import com.uw.android310.lesson6.model.Image;
import com.uw.android310.lesson6.model.ImageUpload;
import com.uw.android310.lesson6.util.Constants;
import com.uw.android310.lesson6.util.NetworkUtils;

import java.lang.ref.WeakReference;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;

public class ImageUploadService {
    public final static String TAG = ImageUploadService.class.getSimpleName();

    private WeakReference<Context> mContext;

    public ImageUploadService(Context context) {
        this.mContext = new WeakReference<>(context);
    }

    public void execute(ImageUpload upload, final Callback<Image> callback) {

        // Check network connection before making a call
        if (!NetworkUtils.isConnected(mContext.get())) {
            callback.failure(null);
            return;
        }

        RestAdapter restAdapter = buildRestAdapter();

        restAdapter.create(ImgurRestApi.class).uploadImage(
                Constants.getClientAuth(),                  // Client auth
                upload.title,                               // Image title
                upload.description,                         // Image description
                upload.albumId,                             // Image album ID
                null,                                       // Anonymous image upload, no username
                new TypedFile("image/*", upload.image),     // Image file
                new Callback<Image>() {
                    @Override
                    public void success(Image imageResponse, Response response) {
                        if (callback != null) {
                            callback.success(imageResponse, response);
                        }

                        /**
                         * TODO: Notify image was NOT uploaded successfully
                         */
                        if (response == null) {
                            Log.e(TAG, "Image failed to upload successfully.");
                        }
                        /**
                         * TODO: Notify image was uploaded successfully
                         */
                        if (imageResponse.success) {
                            Log.d(TAG, "Image was uploaded successfully: " + imageResponse.toString());
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        if (callback != null) {
                            callback.failure(error);
                        }
                        /**
                         * TODO: Notify image was NOT uploaded successfully
                         */
                    }
                });
    }

    private RestAdapter buildRestAdapter() {
        RestAdapter imgurAdapter = new RestAdapter.Builder()
                .setEndpoint(ImgurRestApi.server)
                .build();

        /*
        Set rest adapter logging if we're already logging
        */
        if (Constants.LOGGING) {
            imgurAdapter.setLogLevel(RestAdapter.LogLevel.FULL);
        }
        return imgurAdapter;
    }
}
