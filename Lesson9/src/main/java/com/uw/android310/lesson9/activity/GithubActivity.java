package com.uw.android310.lesson9.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.uw.android310.lesson9.R;
import com.uw.android310.lesson9.model.Contributor;
import com.uw.android310.lesson9.service.GithubService;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import rx.Observable;
import rx.Subscriber;

public class GithubActivity extends AppCompatActivity {
    public static final String TAG = GithubActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_github);

//        new GithubService(this).execute("netflix", "rxjava", new UiCallback());

        new GithubService(this).execute("netflix", "rxjava")
                .lift(GithubActivity.<Contributor>flattenList())
                .forEach(c -> Log.d(TAG, "Login: " + c.login));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_github, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class UiCallback implements Callback<List<Contributor>> {

        @Override
        public void success(List<Contributor> contributorList, Response response) {
            Log.d(TAG, contributorList.toString());
        }

        @Override
        public void failure(RetrofitError error) {
            //Assume we have no connection, since error is null
            if (error == null) {
                Log.e(TAG, "No internet connection.");
            } else {
                Log.e(TAG, error.getMessage());
            }
        }
    }

    private static <T> Observable.Operator<T, List<T>> flattenList() {
        return new Observable.Operator<T, List<T>>() {
            @Override
            public Subscriber<? super List<T>> call(final Subscriber<? super T> subscriber) {
                return new Subscriber<List<T>>() {
                    @Override
                    public void onCompleted() {
                        subscriber.onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        subscriber.onError(e);
                    }

                    @Override
                    public void onNext(List<T> contributors) {
                        for (T c: contributors)
                            subscriber.onNext(c);
                    }
                };
            }
        };
    }
}
