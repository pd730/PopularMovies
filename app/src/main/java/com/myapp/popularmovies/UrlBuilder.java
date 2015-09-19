package com.myapp.popularmovies;

import android.net.Uri;
import android.util.Log;

import java.net.URL;

/**
 * Created by Paul on 8/20/2015.
 */
public class UrlBuilder {
    private final String LOG_TAG = UrlBuilder.class.getSimpleName();
    private String _sortBy;
    private String _apiKey = "";//Resources.getSystem().getString(R.string.api_key);

    public UrlBuilder(String sortBy) {
        if (sortBy == null) {
            _sortBy = "popularity.desc";
        } else {
            _sortBy = sortBy;
        }
    }

    public URL GetUrl() {

        //String baseUrl = Resources.getSystem().getString(R.string.url_tmdb);// getString(R.string.url_tmdb);
        String sortBy = "sort_by";
        String api = "api_key";
        URL url = null;

        try {
            Uri sbUrl = Uri.parse("https://api.themoviedb.org/3/discover/movie?").buildUpon()
                    .appendQueryParameter(sortBy, _sortBy)
                    .appendQueryParameter(api, _apiKey)
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
