package com.uw.android310.lesson8.api;


import com.uw.android310.lesson8.model.Image;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Path;


public interface ImgurRestApi {
    static final String server = "https://api.imgur.com";

    /**
     * Get an image from imgur.
     *
     * @param id Image ID
     */
    @GET("/3/image/{id}")
    void getImage(
            @Header("Authorization") String auth,
            @Path("id") String id,
            Callback<Image> callback);
}
