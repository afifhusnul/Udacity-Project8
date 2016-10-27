package guardiansapis.chemplung.com.udacity_project8;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.List;

/**
 * Created by NUSNAFIF on 10/25/2016.
 */

public class NewsLoader extends AsyncTaskLoader<List<News>> {
    /** Tag for the log messages */
    private static final String LOG_TAG = NewsLoader.class.getSimpleName();

    /** Query URL */
    private String mUrl;

    /**
     * Constructs a new {@link NewsLoader}.
     *
     * @param context of the activity
     * @param url to load data from
     */
    public NewsLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    /**
     * This is on a background thread.
     */
    @Override
    public List<News> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of earthquakes.
        List<News> news = QueryUtils.fetchNewsData(mUrl);
        Log.i(LOG_TAG,"Url is --> "+mUrl);
        return news;
    }
}
