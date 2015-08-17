package com.uw.android310.lesson9.service;

import android.content.Context;
import android.support.annotation.NonNull;

import com.uw.android310.lesson9.api.GithubRestApi;
import com.uw.android310.lesson9.model.Contributor;
import com.uw.android310.lesson9.model.Repository;
import com.uw.android310.lesson9.util.Constants;

import java.lang.ref.WeakReference;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import rx.Observable;


public class GithubService {
    public final static String TAG = GithubService.class.getSimpleName();

    private WeakReference<Context> mContext;

    public GithubService(Context context) {
        this.mContext = new WeakReference<>(context);
    }

    public void executeContributors(@NonNull final String owner, @NonNull final String repo,
                         final Callback<List<Contributor>> callback) {
        RestAdapter restAdapter = buildRestAdapter();

        restAdapter.create(GithubRestApi.class).contributors(
                Constants.USER_AGENT, owner, repo, new Callback<List<Contributor>>() {
                    @Override
                    public void success(List<Contributor> contributorList, Response response) {
                        if (callback != null) {
                            callback.success(contributorList, response);
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

    public Observable<List<Contributor>> executeContributors(@NonNull final String owner, @NonNull final String repo) {
        RestAdapter restAdapter = buildRestAdapter();

        return restAdapter.create(GithubRestApi.class).contributors(
                Constants.USER_AGENT, owner, repo);
    }

    public Observable<List<Repository>> executeStarred(@NonNull final String user) {
        RestAdapter restAdapter = buildRestAdapter();

        return restAdapter.create(GithubRestApi.class).starred(
                Constants.USER_AGENT, user);
    }

    private RestAdapter buildRestAdapter() {
        RestAdapter githubAdatper = new RestAdapter.Builder()
                .setEndpoint(GithubRestApi.server)
                .build();

        return githubAdatper;
    }
}
