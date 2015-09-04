package com.myapp.popularmovies;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private ArrayAdapter<String> movieAdapter;
    private View rootView;

    private final String defSortBy = "popularity.desc";

    public MainActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        getMovies();
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        getMovies();
//    }

    private void getMovies() {
        GetMoviesTask movieTask = new GetMoviesTask();
        SharedPreferences sortPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String sortBy = sortPref.getString("sortBy", defSortBy);
        movieTask.execute(sortBy);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_main, container, false);

        //TODO
        //movieAdapter = new ArrayAdapter<String>(getActivity(), R.layout.)

        return rootView;
    }

    public class GetMoviesTask extends AsyncTask<String, Integer, String[]> {

        private final String LOG_TAG = GetMoviesTask.class.getSimpleName();

        @Override
        public String[] doInBackground(String... parms) {

            HttpURLConnection conn = null;
            BufferedReader reader = null;
            String movieJsonStr;
            String[] results;

            try {
                UrlBuilder builder = new UrlBuilder(parms[0]);
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
            }
            catch(Exception ex) {

            }
            finally {

            }
            return null;
        }
    }
}
