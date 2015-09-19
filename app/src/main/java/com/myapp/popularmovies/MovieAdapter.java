package com.myapp.popularmovies;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Paul on 9/9/2015.
 */
public class MovieAdapter extends ArrayAdapter<MovieInfo> {
    private static final String LOG_TAG = MovieAdapter.class.getSimpleName();

    public MovieAdapter(Activity context, ArrayList<MovieInfo> movieList)
    {
        super(context, 0, movieList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        try {
            // Get the current movie object
            MovieInfo movie = getItem(position);
            if (movie != null) {
                String url = "http://image.tmdb.org/t/p/w185/";

                // If the view doesn't exist yet, create it
                if (convertView == null) {
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.grid_item_movie, parent, false);
                }

                // Set the movie's poster image to the ImageView in the GridLayout
                ImageView ivMovie = (ImageView) convertView.findViewById(R.id.grid_item_image);
                url = url + movie.imagePath;
                Picasso.with(getContext()).load(url).into(ivMovie);

                TextView tvMovie = (TextView) convertView.findViewById(R.id.grid_item_text);
                tvMovie.setText(movie.title);
            }
        }
        catch (Exception ex) {
            Log.e(LOG_TAG, "Error ", ex);
        }
        finally {
            return convertView;
        }
    }
}
