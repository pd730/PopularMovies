package com.myapp.popularmovies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private MovieAdapter movieAdapter;
    private ArrayList<MovieInfo> movieList;
    private View rootView;
    private GridView mgv;
    private String defSortBy;

    public MainActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        defSortBy = getActivity().getString(R.string.default_sort_by);
        if (savedInstanceState == null || !savedInstanceState.containsKey(getActivity().getString(R.string.instance_movies))) {
            getMovies();
        }
        else
        {
            // Get movieList from saved Parcel
            movieList = savedInstanceState.getParcelableArrayList(getActivity().getString(R.string.instance_movies));
//            mgv.onRestoreInstanceState(savedInstanceState.getParcelable("gvPosition"));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_main, container, false);
        mgv = (GridView) rootView.findViewById(R.id.gv_movies);

        // When creating from a savedInstanceState, can set the ArrayAdapter now instead of waiting
        //  for the async task to complete
        if (movieList != null)
        {
            setAdapterForGridView();
        }
        return rootView;
    }

    // Solution to adjust imageview grid size on screen rotation
    //  From http://stackoverflow.com/questions/12927373/android-gridview-change-number-of-columns-depending-on-orientation
    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {

    }

    @Override
    public void onResume()
    {
        super.onResume();
        // Coming back from settings, refresh list to show new sort preference
        getMovies();
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        outState.putParcelableArrayList(getActivity().getString(R.string.instance_movies), movieList);
        // Attempting to save the scroll position of the gridview, didn't work
        //outState.putParcelable("gvPosition", mgv.onSaveInstanceState());
        super.onSaveInstanceState(outState);
    }

    // Get the saved sort preference and execute the async query to get the movies
    private void getMovies() {
        GetMoviesTask movieTask = new GetMoviesTask();
        SharedPreferences sortPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String sortBy = sortPref.getString("sortBy", defSortBy);
        movieTask.execute(sortBy);
    }

    // Sets the custom ArrayAdapter and onClickListener to the gridview
    private void setAdapterForGridView()
    {
        movieAdapter = new MovieAdapter(getActivity(), movieList);
        mgv.setAdapter(movieAdapter);
        mgv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int i, long pos) {
               MovieInfo movie = movieAdapter.getItem((int) pos);
               Intent intent = new Intent(getActivity(), DetailActivity.class)
                       .putExtra(getActivity().getString(R.string.intent_movie_info), movie);
               startActivity(intent);
           }
        });
    }

    public class GetMoviesTask extends AsyncTask<String, Integer, ArrayList<MovieInfo>> {

        private final String LOG_TAG = GetMoviesTask.class.getSimpleName();

        @Override
        public ArrayList<MovieInfo> doInBackground(String... parms) {

            HttpURLConnection conn = null;
            BufferedReader reader = null;
            String movieJsonStr;
            String[] results;

            try {
                UrlBuilder builder = new UrlBuilder(getActivity(), parms[0]);
                URL url = builder.GetUrl();

                if (url == null) {
                    return null;
                }

                // Open connection
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();

                // Read the input stream to a string
                InputStream inputStream = conn.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                movieJsonStr = buffer.toString();

                JsonParser parser = new JsonParser(movieJsonStr);
                movieList = parser.GetMovieInfoFromJson();
                return movieList;
            }
            catch(Exception ex) {
                Log.e(LOG_TAG, "Error ", ex);
            }
            finally {

            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList result)
        {
            setAdapterForGridView();
        }
    }
}
