package com.uw.android310.lesson9.api;


import com.uw.android310.lesson9.model.Contributor;
import com.uw.android310.lesson9.model.Repository;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Path;
import rx.Observable;


public interface GithubRestApi {
    static final String server = "https://api.github.com";

    /**
     * Get a repository's list of collaborators
     *
     * @param owner Repository owner
     * @param repo Repository name
     */
    @GET("/repos/{owner}/{repo}/contributors")
    void contributors(
            @Header("User-Agent") String userAgent,
            @Path("owner") String owner,
            @Path("repo") String repo,
            Callback<List<Contributor>> callback);

    /**
     * Get a repository's list of collaborators
     *
     * @param owner Repository owner
     * @param repo Repository name
     */
    @GET("/repos/{owner}/{repo}/contributors")
    Observable<List<Contributor>> contributors(
            @Header("User-Agent") String userAgent,
            @Path("owner") String owner,
            @Path("repo") String repo);

    /**
     * Get a list of repositories starred by a user
     *
     * @param user Github user
     */
    @GET("/users/{user}/starred")
    Observable<List<Repository>> starred(
            @Header("User-Agent") String userAgent,
            @Path("owner") String user);
}
