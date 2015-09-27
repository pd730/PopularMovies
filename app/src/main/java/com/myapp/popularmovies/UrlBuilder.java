package com.myapp.popularmovies;

import android.app.Activity;
import android.net.Uri;
import android.util.Log;

import java.net.URL;

/**
 * Created by Paul on 8/20/2015.
 *
 * Creates the URL needed to retrieve movie info from The Movie Database
 */

public class UrlBuilder {
    private final String LOG_TAG = UrlBuilder.class.getSimpleName();
    private String sortPref;
    private String apiKey;
    private String tmdbUrl;

    public UrlBuilder(Activity context, String sort) {
        tmdbUrl = context.getString(R.string.url_tmdb);
        apiKey = context.getString(R.string.api_key);

        if (sort == null) {
            sortPref = context.getString(R.string.default_sort_by);
        } else {
            sortPref = sort;
        }
    }

    public URL GetUrl() {

        String sortBy = "sort_by";
        String api = "api_key";
        URL url = null;

        try {
            Uri sbUrl = Uri.parse(tmdbUrl).buildUpon()
                    .appendQueryParameter(sortBy, sortPref)
                    .appendQueryParameter(api, apiKey)
                    .build();
            url = new URL(sbUrl.toString());
            Log.v(LOG_TAG, "Built URL: " + url);
        }
        catch (Exception ex) {
            Log.e(LOG_TAG, "URL exception: " + ex.toString());

        }
        return url;
    }
}
