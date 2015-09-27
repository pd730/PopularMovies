package com.myapp.popularmovies;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Paul on 9/13/2015.
 *
 * Parses a JSON string
 */
public class JsonParser {

    private String _jsonStr;

    public JsonParser(String jsonStr)
    {
        this._jsonStr = jsonStr;
    }

    public ArrayList<MovieInfo> GetMovieInfoFromJson() throws JSONException
    {
        ArrayList<MovieInfo> movieList = new ArrayList<MovieInfo>();
        JSONObject moviesJson = new JSONObject(_jsonStr);
        JSONArray moviesArray = moviesJson.getJSONArray("results");

        for (int i = 0; i < moviesArray.length(); i++)
        {
            JSONObject movieObject = moviesArray.getJSONObject(i);
            MovieInfo movieInfo = new MovieInfo();
            movieInfo.title = movieObject.getString("title");
            movieInfo.overview = movieObject.getString("overview");
            movieInfo.relDate = movieObject.getString("release_date");
            movieInfo.imagePath = movieObject.getString("poster_path");
            movieInfo.popularity = movieObject.getLong("popularity");
            movieInfo.voteAvg = movieObject.getDouble("vote_average");
            movieInfo.voteCnt = movieObject.getInt("vote_count");
            movieList.add(movieInfo);
        }
        return movieList;
    }
}
