package com.myapp.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment {

    public DetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        Intent intent = getActivity().getIntent();
        if (intent != null && intent.hasExtra("Movie_Info"))
        {
            MovieInfo movie = (MovieInfo) intent.getParcelableExtra("Movie_Info");

            TextView tvTitle = (TextView) rootView.findViewById(R.id.detail_tv_title);
            tvTitle.setText(movie.title);

            String url = "http://image.tmdb.org/t/p/w185/";
            ImageView ivPoster = (ImageView) rootView.findViewById(R.id.detail_iv_poster);
            url = url + movie.imagePath;
            Picasso.with(getActivity()).load(url).into(ivPoster);
        }
        return rootView;
    }
}
