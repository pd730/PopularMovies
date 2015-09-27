package com.myapp.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Fragment to display the movie details as selected by the user in the MainActivityFragment
 */
public class DetailActivityFragment extends Fragment {

    public DetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        Intent intent = getActivity().getIntent();
        if (intent != null && intent.hasExtra(getActivity().getString(R.string.intent_movie_info)))
        {
            MovieInfo movie = (MovieInfo) intent.getParcelableExtra(getActivity().getString(R.string.intent_movie_info));

            TextView tvTitle = (TextView) rootView.findViewById(R.id.detail_tv_title);
            tvTitle.setText(movie.title);

            //String url = "http://image.tmdb.org/t/p/w185/";
            String url = getActivity().getString(R.string.url_posters);
            ImageView ivPoster = (ImageView) rootView.findViewById(R.id.detail_iv_poster);
            url = url + movie.imagePath;
            Picasso.with(getActivity())
                    .load(url)
                    .placeholder(R.drawable.hourglass)
                    .error(R.drawable.no_image)
                    .into(ivPoster);

            TextView tvRelDate = (TextView) rootView.findViewById(R.id.detail_tv_relDate);
            tvRelDate.setText(movie.relDate == "null" ? getActivity().getString(R.string.no_release) : movie.relDate);

            TextView tvRatings = (TextView) rootView.findViewById(R.id.detail_tv_ratings);
            tvRatings.setText(movie.voteAvg == 0 ? getActivity().getString(R.string.no_ratings) : "Ratings: " + movie.voteAvg.toString() + "/10");

            TextView tvOverview = (TextView) rootView.findViewById(R.id.detail_tv_overview);
            tvOverview.setText(movie.overview == "null" || movie.overview == "" ? getActivity().getString(R.string.no_overview) : movie.overview);
            tvOverview.setMovementMethod(new ScrollingMovementMethod());
        }
        return rootView;
    }
}
