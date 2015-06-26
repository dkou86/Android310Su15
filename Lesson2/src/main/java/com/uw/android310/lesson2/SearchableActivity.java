package com.uw.android310.lesson2;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.uw.android310.core.StringUtil;


public class SearchableActivity extends AppCompatActivity {
    private TextView mSearchQueryTextView;

    private String mSearchQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);

        mSearchQueryTextView = (TextView) findViewById(R.id.searchQuery);

        // Get the intent, verify the action and get the query
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String unSanitizedQuery = intent.getStringExtra(SearchManager.QUERY);

            if (StringUtil.isNullOrEmpty(unSanitizedQuery)) {
                handleEmptySearchQuery();
            }

            // Sanitize the search query
            String sanitizedQuery = StringUtil.Search.sanitizeQueryString(unSanitizedQuery);

            // Show the query on the device
            mSearchQueryTextView.setText(sanitizedQuery);

            // Store for later use
            mSearchQuery = sanitizedQuery;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_searchable, menu);
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

    private void handleEmptySearchQuery() {
        Toast.makeText(this, "Please provide a non-null, non-empty search query.", Toast.LENGTH_SHORT).show();
    }
}