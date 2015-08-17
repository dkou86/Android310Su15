package com.uw.android310.lesson8.service;


import android.content.Context;

import com.uw.android310.lesson8.api.ImgurRestApi;
import com.uw.android310.lesson8.model.Image;
import com.uw.android310.lesson8.util.Constants;

import java.lang.ref.WeakReference;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ImageService {
    public final static String TAG = ImageService.class.getSimpleName();

    private WeakReference<Context> mContext;

    public ImageService(Context context) {
        this.mContext = new WeakReference<>(context);
    }

    public void execute(final String id, final Callback<Image> callback) {
        RestAdapter restAdapter = buildRestAdapter();

        restAdapter.create(ImgurRestApi.class).getImage(
                Constants.getClientAuth(),                  // Client auth
                id,                                         // Image title
                new Callback<Image>() {
                    @Override
                    public void success(Image imageResponse, Response response) {
                        if (callback != null) {
                            callback.success(imageResponse, response);
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        if (callback != null) {
                            callback.failure(error);
                        }
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
